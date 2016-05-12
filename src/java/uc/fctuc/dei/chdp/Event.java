package uc.fctuc.dei.chdp;

import uc.fctuc.dei.transport.TransportHandle;

/**
 * @name Event
 * @description The events that are exchanged between the Connection Handlers
 *              through the Handlers Synchronizer in the server, are from the
 *              type of an object, named Event, which contains a transport
 *              handle and information about the data read in the remote peer.
 *              
 * @author Naghmeh Ivaki and Filipe Araujo
 * @contact naghmeh@dei.uc.pt
 * @Copyright
 */

public class Event {
	TransportHandle handle;
	int dataRead;

	public Event(TransportHandle h, int r) {
		this.handle = h;
		this.dataRead = r;
	}

	public TransportHandle getHandle() {
		return handle;
	}

	public int getDataRead() {
		return dataRead;
	}

}
