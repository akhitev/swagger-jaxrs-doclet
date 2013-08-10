package com.hypnoticocelot.jaxrs.doclet.sample.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * Taken from @link http://github.com/josrutten/dropwizard-examples
 */
public class CorsHeadersFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            if ("OPTIONS".equals(httpServletRequest.getMethod())) {
                httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
