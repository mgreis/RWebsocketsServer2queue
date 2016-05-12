package uc.fctuc.dei.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @name TransportHandle
 * @description Transport Handle provides an interface to the applications sto
 *              establish a connection, write data, read data, and close the
 *              connection. TransportHandle supports both blocking and
 *              non-blocking mode for read operation.
 * 
 * @author Naghmeh Ivaki and Filipe Araujo
 * @contact naghmeh@dei.uc.pt
 * @Copyright
 */

public class TransportHandle {

	private static boolean blocking = true;

	/**
	 * SocketChannel is used when the connection is configures as non-blocking.
	 */
	private SocketChannel socketChannel = null;
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private BufferedReader br = null;

	/**
	 * Method configureBlocking is used to configure the transport handle as
	 * blocking or non-blocking. By default a transport handle is in blocking
	 * mode. This method must be used before initialization of a transport
	 * handle to be effective.
	 */
	public static void configureBlocking(boolean b) {
		blocking = b;
	}

	/**
	 * Constructor that is used by server with blocking handle.
	 */
	public TransportHandle(Socket sock) throws IOException {
		this.socket = sock;
		this.outputStream = this.socket.getOutputStream();
		this.outputStream.flush();
		this.inputStream = this.socket.getInputStream();
	}

	/**
	 * Constructor that is used by server with non-blocking handle.
	 */
	public TransportHandle(SocketChannel sockchanell) throws IOException {
		this.socketChannel = sockchanell;
		this.socket = this.socketChannel.socket();
		this.outputStream = this.socket.getOutputStream();
		this.outputStream.flush();
		this.inputStream = this.socket.getInputStream();
	}

	/**
	 * Constructor that is used by client.
	 */
	public TransportHandle(String address, int port) throws IOException {
		if (blocking)
			this.socket = new Socket(address, port);
		else {

			this.socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress(address, port));
			while (!socketChannel.finishConnect()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			this.socket = socketChannel.socket();
		}

		this.socket.setTcpNoDelay(true);
		this.outputStream = this.socket.getOutputStream();
		this.outputStream.flush();
		this.inputStream = this.socket.getInputStream();
	}

	public static boolean isBlocking() {
		return blocking;
	}

	public int read(byte[] inputData) throws IOException {
		return read(inputData, 0, inputData.length);
	}

	public int read(byte[] inputData, int off, int len) throws IOException {
		if (blocking)
			return inputStream.read(inputData, off, len);
		else {
			ByteBuffer inputBuffer = ByteBuffer.wrap(inputData, off, len);
			int read = socketChannel.read(inputBuffer);
			return read;
		}
	}

	public String readLine() throws IOException {
		if (this.br == null) {
			InputStreamReader isr = new InputStreamReader(this.inputStream);
			this.br = new BufferedReader(isr);
		}
		return this.br.readLine();
	}

	public void write(byte[] data) throws IOException {
		write(data, 0, data.length);
	}

	public void write(byte[] data, int off, int len) throws IOException {
		if (blocking) {
			outputStream.write(data, off, len);
			outputStream.flush();
		} else {
			ByteBuffer writeBuffer = ByteBuffer.allocate(len);
			writeBuffer.clear();
			writeBuffer.put(data, off, len);
			writeBuffer.flip();
			socketChannel.write(writeBuffer);
		}
	}

	public void write(int i) throws IOException {
		if (blocking) {
			outputStream.write(i);
		}
	}

	public void flush() throws IOException {
		if (blocking) {
			outputStream.flush();
		}
	}

	public void close() throws IOException {
		outputStream.close();
		inputStream.close();
		socket.close();
		if (!blocking)
			socketChannel.close();
	}

	public boolean isClosed() {
		if (blocking)
			return socket.isClosed();
		return !socketChannel.isOpen();

	}

	public boolean getTcpNoDelay() throws SocketException {
		return this.socket.getTcpNoDelay();
	}

	public int getSoTimeout() throws SocketException {
		return this.socket.getSoTimeout();
	}

	public int getSendBufferSize() throws SocketException {
		return this.socket.getSendBufferSize();
	}

	public int getReceiveBufferSize() throws SocketException {
		return this.socket.getReceiveBufferSize();
	}

	public void setTcpNoDelay(boolean b) throws SocketException {
		socket.setTcpNoDelay(b);
	}

	public InetAddress getLocalAddress() {
		return socket.getLocalAddress();
	}

	public void setSoTimeout(int t) throws SocketException {
		socket.setSoTimeout(t);

	}

	public int getSoLinger() throws SocketException {
		return socket.getSoLinger();
	}

	public void setSoLinger(boolean on, int t) throws SocketException {
		socket.setSoLinger(on, t);
	}

	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	public Object getInputStream() throws IOException {
		return socket.getInputStream();
	}

	public SocketAddress getRemoteSocketAddress() {
		return socket.getRemoteSocketAddress();
	}

	public void shutdownOutput() throws IOException {
		socket.shutdownOutput();
	}

	public Socket getSocket() {
		return this.socket;
	}

	public int getPort() {
		return socket.getPort();
	}

	public int getLocalPort() {
		return socket.getLocalPort();
	}

	public int read() {
		
		return 0;
	}

	
	
}
