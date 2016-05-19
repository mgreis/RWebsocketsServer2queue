/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dei.fwebsocket;

/**
 * Based on http://stackoverflow.com/questions/17936440/accessing-httpsession-from-httpservletrequest-in-a-web-socket-serverendpoint
 * @author Chad N B
 */
import java.security.Principal;
import javax.servlet.http.HttpSession;

public class PrincipalWithSession implements Principal {
    private final HttpSession session;

    public PrincipalWithSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    @Override
    public String getName() {
        return ""; // whatever is appropriate for your app, e.g., user ID
    }
}