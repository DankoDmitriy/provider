package com.danko.provider.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets request and response encoding.
 */
public class EncodingFilter implements Filter {
    private static final String ENCODING_PARAMETER = "encoding";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(ENCODING_PARAMETER);
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String encodingCodeRequest = servletRequest.getCharacterEncoding();
        if (!encoding.equals(encodingCodeRequest)) {
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
