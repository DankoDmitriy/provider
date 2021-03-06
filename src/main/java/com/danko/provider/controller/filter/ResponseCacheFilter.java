package com.danko.provider.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * The filter disabled process of cache in browse.
 */
public class ResponseCacheFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, new AddExpiresHeaderResponse((HttpServletResponse) response));
    }

    private static class AddExpiresHeaderResponse extends HttpServletResponseWrapper {

        private static final String[] CACHEABLE_CONTENT_TYPES = new String[]{
                "text/css", "text/javascript", "image/png", "image/jpeg", "image/jpg"};

        static {
            Arrays.sort(CACHEABLE_CONTENT_TYPES);
        }

        private AddExpiresHeaderResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void setContentType(String contentType) {
            if (contentType != null && Arrays.binarySearch(CACHEABLE_CONTENT_TYPES, contentType) > -1) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.MINUTE, 30);
                super.setDateHeader("Expires", calendar.getTimeInMillis());
            } else {
                super.setHeader("Expires", "-1");
                super.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            }
            super.setContentType(contentType);
        }
    }
}
