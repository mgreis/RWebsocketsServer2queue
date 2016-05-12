package uc.fctuc.dei.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @name PassiveTransportHandle
 * @description Passive Transport Handle is a passive-mode Transport Handle and
 *              is bound to a network address (i.e. consist of an IP address and
 *              a port number). It is used by a server to receive and accept
 *              connection requests from clients. This PassiveTransportHandle
 *              supports both blocking and non-blocking accept operations.
 * 
 * @author Naghmeh Ivaki and Filipe Araujo
 * @contact naghmeh@dei.uc.pt
 * @Copyright
 */

public class PassiveTransportHandle {

	private static boolean blocking = true;

	/**
	 * ServerSocket is used for blocking accept operation.
	 */
	ServerSocket serverSocket = null;
	/**
	 * ServerSocket is used for non-blocking accept operation.
	 */
	ServerSocketChannel serverSocketChannel = null;

	/**
	 * Method configureBlocking is used to configure the passive transport handle as
	 * blocking or non-blocking. By default a passive transport handle is in blocking
	 * mode. This method must be used before initialization of a passive transport
	 * handle to be effective.
	 */
	public static void configureBlocking(boolean b) {
		blocking = b;
	}
	
	public PassiveTransportHandle(int port) throws IOException {
		if (blocking) {
			serverSocket = new ServerSocket(port);
		} else {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
		}
	}
	public static boolean isBlocking() {
		return blocking;
	}

	public TransportHandle accept() throws IOException {

		if (blocking) {
			Socket socket = serverSocket.accept();
			if (socket != null) {
				TransportHandle handle = new TransportHandle(socket);
				return handle;
			}
		} else {
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				TransportHandle handle = new TransportHandle(socketChannel);
				return handle;
			}
		}
		return null;
	}
}
