package dei.fwebsocket;


import java.util.concurrent.ConcurrentLinkedQueue;
import uc.fctuc.dei.chdp.Buffer;

public class MessageBuffer implements Buffer{
    private ConcurrentLinkedQueue < Object > buffer = new ConcurrentLinkedQueue < Object > ();


	@Override
	public void put(Object obj) {
		getBuffer().add(obj);
		
	}
        

	@Override
	public Object get(int n) {
		return getBuffer().element();
	}

	@Override
	public void remove(int n) {
		getBuffer().remove();
		
	}

	@Override
	public void clear() {
		getBuffer().clear();
		
	}

    /**
     * @return the buffer
     */
    public ConcurrentLinkedQueue < Object > getBuffer() {
        return buffer;
    }

    /**
     * @param buffer the buffer to set
     */
    public void setBuffer(ConcurrentLinkedQueue < Object > buffer) {
        this.buffer = buffer;
    }

}
