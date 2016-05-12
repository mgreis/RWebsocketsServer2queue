package uc.fctuc.dei.chdp;

/**
 * @name Buffer
 * @description Each Reliable Endpoint owns a buffer to keep the data sent. The
 *              Buffer provide an interface that must be implemented by the
 *              Reliable Endpoint's concrete buffer. The interface of Buffer
 *              allows saving, retrieving, and removing the acknowledged data
 *              respectively through the methods put(), get() and remove(). The
 *              method clear() is used to remove all data from the buffer when
 *              it is not needed anymore. The concrete buffer must be
 *              implemented properly depending on the type of data (e.g., bytes
 *              or message) that the peers use for communication. For this
 *              reason, the term "Object" is used as the type of the data,
 *              stored in the Buffer, to generalize the design for all kinds of
 *              applications including stream-based, message-based, or
 *              object-based.
 *              
 * @author Naghmeh Ivaki and Filipe Araujo
 * @contact naghmeh@dei.uc.pt
 * @Copyright
 */

public interface Buffer {
	public void put(Object obj);

	public Object get(int n);

	public void remove(int n);

	public void clear();
}
