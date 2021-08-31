package com.danko.provider.controller.filter;

import com.danko.provider.domain.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_LOCAL;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;


@WebFilter(urlPatterns = {"/*"})
public class UserRoleInstallFilter implements Filter {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//      FIXME - подумать как разделить доступ. Для пользователей. тут это делать или в каждой комманде.
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
