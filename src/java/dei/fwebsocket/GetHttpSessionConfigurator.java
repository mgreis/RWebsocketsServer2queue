package dei.fwebsocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Based on http://stackoverflow.com/questions/17936440/accessing-httpsession-from-httpservletrequest-in-a-web-socket-serverendpoint
 * @author Chad N B
 */


public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator
{
@Override
public void modifyHandshake(ServerEndpointConfig config, 
                            HandshakeRequest request, 
                            HandshakeResponse response)
{
    HttpSession httpSession = (HttpSession)request.getHttpSession();
    config.getUserProperties().put(HttpSession.class.getName(),httpSession);
}
}