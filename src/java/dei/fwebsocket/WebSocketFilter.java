package dei.fwebsocket;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Based on http://stackoverflow.com/questions/17936440/accessing-httpsession-from-httpservletrequest-in-a-web-socket-serverendpoint
 * @author Chad N B
 */

@WebFilter(value = "/FServerEndpoint")
public class WebSocketFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final PrincipalWithSession p = new PrincipalWithSession(httpRequest.getSession());
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public Principal getUserPrincipal() {
                return p;
            }
        };
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException { }
    @Override
    public void destroy() { }
}