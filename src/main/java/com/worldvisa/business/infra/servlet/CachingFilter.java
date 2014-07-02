package com.worldvisa.business.infra.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Defines CachingFilter
 */
public class CachingFilter
implements Filter {
    private static final long   DEFAULT_TTL = 8640000;
    private static final Logger log         = Logger.getLogger(CachingFilter.class);
    private long                ttl;

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // do nothing here
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
    throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setDateHeader("Last-Modified", System.currentTimeMillis());
        res.setDateHeader("Expires", System.currentTimeMillis() + CachingFilter.DEFAULT_TTL * 1000L);
        res.setHeader("Cache-Control", "public, max-age=" + CachingFilter.DEFAULT_TTL);
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig config)
    throws ServletException {
        final String ttlStr = config.getInitParameter("ttl");
        try {
            this.ttl = ttlStr == null ? CachingFilter.DEFAULT_TTL : Long.parseLong(ttlStr);
        } catch (final NumberFormatException e) {
            CachingFilter.log.error("ttl value is not correct", e);
            this.ttl = CachingFilter.DEFAULT_TTL;
        }
        CachingFilter.log.info("expiry limit for css js and images set to " + this.ttl + " seconds");
    }
}
