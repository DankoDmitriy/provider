package com.danko.provider.controller.filter;

import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.danko.provider.controller.command.PageUrl.LOGIN_PAGE;
import static com.danko.provider.controller.command.SessionAttribute.USER;

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
        logger.log(Level.DEBUG, "************** START FILTER USER ROLE");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
//        TODO - данный код никогда не выполнится.
        User user = (User) session.getAttribute(USER);
        System.out.println("========FILTER=================== User: " + user);
        if (user == null) {
            session.setAttribute(USER, User.builder().setRole(UserRole.GUEST));
            logger.log(Level.DEBUG, "**************Filter installed USER ROLE GUEST");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(LOGIN_PAGE);
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
