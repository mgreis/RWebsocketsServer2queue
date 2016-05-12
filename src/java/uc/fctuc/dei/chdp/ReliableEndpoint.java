package uc.fctuc.dei.chdp;

import java.io.IOException;

import uc.fctuc.dei.transport.TransportHandle;

/**
 * @name ReliableEndpoint
 * @description Reliable Endpoint owns a transport handle to communicate and
 *              exchange data with its remote peer. According to the design of
 *              the connection- based application, the Reliable Endpoint can be
 *              a Service Handler, or even a middleware underneath the Service
 *              Handler. This component stores the data (e.g., bytes) sent into
 *              a buffer to be able to retransmit it when lost due to crashes. 
 *              To reestablish a failed connection and retransmit the data that
 *              has been lost due to connection crashes, the Reliable Endpoint
 *              needs to implement some extra actions that are defined and
 *              encapsulated in a new component, named Connection Handler.
 * 
 * @author Naghmeh Ivaki and Filipe Araujo
 * @contact naghmeh@dei.uc.pt
 * @Copyright
 */

public class ReliableEndpoint extends ConnectionHandler {
	TransportHandle transportHandle = null;

	/**
	 * The Reliable Endpoint keeps the track of the data sent and received
	 * (i.e., in dataWritten and dataRead respectively). Depending on the type
	 * of data exchanged between the application’s peers, the value of these
	 * attributes may represent different meanings; for example, it may
	 * represent the number of bytes sent or received when the data type is an
	 * array of bytes, or the identifier of the last message sent and received,
	 * when the data type is a message.
	 */
	int dataRead = 0;
	int dataWritten = 0;
	Buffer buffer = null;

	@Override
	protected void handshake() {
	}

	@Override
	protected void reconnect() {
	}

	@Override
	public void close() {
		try {
			this.transportHandle.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.close();
		this.buffer.clear();
	}
}
