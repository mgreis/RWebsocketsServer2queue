package dei.fwebsocket;

import dei.jsonobjectprocessing.JsonObjectProcessing;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.*;
import org.json.simple.JSONObject;

import uc.fctuc.dei.chdp.ConnectionHandler;

/**
 * @name FServerSession
 * @description
 *
 * @author Mário Alves Pereira
 * @contact mgreis@student.dei.uc.pt
 * @Copyright
 */

public class FServerSession extends ConnectionHandler {

    //private WebSocketContainer container = null;//
    private Session session = null;
    private MessageBuffer buffer = null;
    private int messageID = 0;
    private int receivedID = 0;
    private JsonObjectProcessing processor = new JsonObjectProcessing();

    public FServerSession(Session s) throws IOException {
        this.session = s;
        buffer = new MessageBuffer();

    }

    /**
     * Completes handshake with the client
     *
     * @throws IOException
     */
    @Override
    protected void handshake() throws IOException {
		// TODO Auto-generated method stub
        // has to be implemented differently for the client and the server. 
        // used to exchange handlerId, which let the server to identify if the connection is new or is created for recovery purposes
        String message = processor.getServerHandshakeMessage(handlerId);

        getSession().getBasicRemote().sendText(message);

    }

    /**
     * Completes handshake with the client in case of reconnection
     *
     * @throws IOException
     */
    @Override
    protected void reconnect() throws IOException {
        // TODO Auto-generated method stub
        String message = processor.getServerHandshakeMessage(handlerId);

        getSession().getBasicRemote().sendText(message);
        for (Object s: buffer.getBuffer()){
            this.session.getBasicRemote().sendText((String) s);
        }

    }

    // check to see which types of data are supported by the websocket to be send and received.
    public void send(Object message) {
        this.setMessageID(this.getMessageID() + 1);
        

            String fullMessage = processor.getMessage(this.handlerId, getMessageID(), (String) message);
            
            buffer.put(fullMessage);
           
        try {
            this.session.getBasicRemote().sendText(fullMessage);
            
           
        } catch (IOException ex) {
            Logger.getLogger(FServerSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onMessage(JSONObject messageObject) {
        int receivedID = Integer.valueOf((String) messageObject.get("IdMessage"));
        if (receivedID > this.receivedID) {
            this.receivedID++;
        }
        String fullMessage = processor.getAck(this.handlerId, receivedID);
        try {
            session.getBasicRemote().sendText(fullMessage);
        } catch (IOException ex) {
            Logger.getLogger(FServerSession.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void onAck(JSONObject messageObject) {
        
        int ackID = Integer.valueOf((String) messageObject.get("IdAck"));
        for (Object s: buffer.getBuffer()){
            JSONObject bufferObject = processor.decodeMessage((String)s);
            int messageID =  Integer.valueOf ((String) bufferObject.get("IdMessage"));
        
        
            if (messageID <= ackID) {

                buffer.remove(0);
            }
            else break;
        }

    }
    
    public void sendFirstOnBuffer(){
        if (!this.buffer.getBuffer().isEmpty()){
            try {
                this.session.getBasicRemote().sendText((String)this.buffer.get(0));
            } catch (IOException ex) {
                Logger.getLogger(FServerSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    

    //deletes already sent messages from the message buffer
    protected void delete() {
        buffer.remove(0);

    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the messageID
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * @param messageID the messageID to set
     */
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    /**
     * @return the receivedID
     */
    public int getReceivedID() {
        return receivedID;
    }

    /**
     * @param receivedID the receivedID to set
     */
    public void setReceivedID(int receivedID) {
        this.receivedID = receivedID;
    }
}
