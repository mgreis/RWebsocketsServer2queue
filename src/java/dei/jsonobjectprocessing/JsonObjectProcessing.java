/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dei.jsonobjectprocessing;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author mgreis
 */
public class JsonObjectProcessing {

    JSONParser parser = new JSONParser();

    public JSONObject decodeMessage(String message) {

        Object obj;
        try {
            obj = parser.parse(message);
            return (JSONObject) obj;
        } catch (ParseException ex) {
            Logger.getLogger(JsonObjectProcessing.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Builds the client handshake message
     *
     * @param identifier
     * @param lastReceivedMessage
     * @param tcpSendBuffer
     * @param tcpReceiveBuffer
     * @return
     */
    public String getClientHandshakeMessage(int identifier) {
        JSONObject model = new JSONObject();
        model.put("Type", "Handshake");
        model.put("Identifier", Integer.toString(identifier));
        //model.put("TcpSendBuffer", Integer.toString(tcpSendBuffer));
        //model.put("TcpReceiveBuffer", Integer.toString(tcpReceiveBuffer));
        //model.put ("idAck" , Integer.toString(lastReceivedMessage));
        return model.toString();
    }

    /**
     * Builds the server handshake message
     *
     * @param identifier
     * @param proxy
     * @param tcpSendBuffer
     * @param lastReceivedMessage
     * @param tcpReceiveBuffer
     * @return
     */
    public String getServerHandshakeMessage(int identifier) {
        JSONObject model = new JSONObject();
        model.put("Type", "Handshake");
        model.put("Identifier", Integer.toString(identifier));
        return model.toString();
    }
    /**
     * builds message and piggy backs the acknowledge 
     * @param identifier
     * @param idMessage
     * @param lastReceivedMessage
     * @param message
     * @return 
     */
    public String getMessage(int identifier, int idMessage, String message) {
        JSONObject model = new JSONObject();
        model.put ("Type", "Message");
        model.put ("Identifier", Integer.toString(identifier));
        model.put ("IdMessage",Integer.toString(idMessage));
        model.put ("Message", message); 
        return model.toString();
    }
    
    /**
     * sends an acknowledge
     * @param identifier
     * @param lastReceivedMessage
     * @return 
     */
    public String getAck(int identifier, int lastReceivedMessage ) {
        JSONObject model = new JSONObject();
        model.put ("Type", "ACK");
        model.put ("Identifier", Integer.toString(identifier));
        model.put ("IdAck",Integer.toString(lastReceivedMessage));
        return model.toString();
    }

    /**
     * Validates if the message is well formed, does not validate its content
     *
     * @param message
     * @return
     */
    public boolean validateMessage(String message) {
        try {
            parser.parse(message);
            return true;
        } catch (ParseException ex) {
            Logger.getLogger(JsonObjectProcessing.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
