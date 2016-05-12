package uc.fctuc.dei.chdp;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @name HandlersSynchronizer
 * @description The Handlers Synchronizer provides an interface, allowing the
 *              connection handlers to: 1) register and dereg- ister themselves
 *              into/from the list of the handlers; 2) put an event for another
 *              handler (i.e., used by connection handler, created for
 *              recovery); and 3) wait for an event coming from a new connection
 *              handler (used by connection handler with a failed connection).
 *              When a connection handler is being registered, the Handlers
 *              Synchronizer generates a unique identifier and returns it back
 *              to the connection handler.
 *              
 * @author Naghmeh Ivaki and Filipe Araujo
 * @contact naghmeh@dei.uc.pt
 * @Copyright
 */

public class HandlersSynchronizer {

	private static ConcurrentHashMap<Integer, Event> events = new ConcurrentHashMap<Integer, Event>();
	private static ConcurrentHashMap<Integer, ConnectionHandler> handlers = new ConcurrentHashMap<Integer, ConnectionHandler>();
	private static int handler_identifer = 1;

	public static Event get_event(int identifier, int t) {

		long timeout = t * 60 * 1000; // converts t from minutes to milisecons
		Event event = null;
		synchronized (events) {
			long start = System.currentTimeMillis();
			event = events.remove(identifier);
			while (event == null && timeout > 0) {
				try {
					events.wait(timeout);
					event = events.remove(identifier);
					timeout = timeout - (System.currentTimeMillis() - start);
				} catch (InterruptedException e) {
					// nothing
				}
			}
		}
		return event;
	}

	public static void put_event(int handler_id, Event event) {
		/**
		 * a new connection arrived to be replaced with the recovering one
		 */
		synchronized (events) {
			if (handlers.contains(handler_id)) {
				events.put(handler_id, event);
				events.notifyAll();
			}
		}
	}

	public static int register_handler(ConnectionHandler h) {
		int id = generate_identifier();
		handlers.put(id, h);
		return id;
	}
	private synchronized static int generate_identifier() {
		return handler_identifer++;
	}


	public static void deregister_handler(int handler_id) {
		handlers.remove(handler_id);
		events.remove(handler_id);
	}

	public static ConnectionHandler getHandler(int handler_id) {
		return handlers.get(handler_id);
	}
	
	public static void clear() {
		events.clear();
		handlers.clear();
	}
}
