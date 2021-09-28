package com.danko.provider.controller.filter;

import com.danko.provider.controller.command.CommandName;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.danko.provider.controller.command.PageUrl.ACCESS_ERROR_403_PAGE;
import static com.danko.provider.controller.command.ParamName.COMMAND;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class CommandAccessFilter implements Filter {
    private static Logger logger = LogManager.getLogger();
    private final Set<String> commonCommand = new HashSet<>();
    private final Set<String> adminCommands = new HashSet<>();
    private final Set<String> userCommands = new HashSet<>();
    private final Set<String> guestCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commonCommand.addAll(CommandName.commonCommands);
        adminCommands.addAll(CommandName.adminCommands);
        userCommands.addAll(CommandName.userCommands);
        guestCommands.addAll(CommandName.guestCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String command = request.getParameter(COMMAND).toUpperCase();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        UserRole userRole;

        if (user == null) {
            userRole = UserRole.GUEST;
        } else {
            userRole = user.getRole();
        }

        if (userRole.equals(UserRole.GUEST) && command != null) {
            logger.log(Level.DEBUG, "GUEST IF: COMMAND {},ROLE {},User {}", command, userRole, user);
            if (!guestCommands.contains(command)) {
                response.sendRedirect(request.getContextPath() + ACCESS_ERROR_403_PAGE);
                return;
            }
        }

        if (!userRole.equals(UserRole.USER) && userCommands.contains(command)) {
            logger.log(Level.DEBUG, "USER IF: COMMAND {},ROLE {},User {}", command, userRole, user);
            response.sendRedirect(request.getContextPath() + ACCESS_ERROR_403_PAGE);
            return;
        }

        if (!userRole.equals(UserRole.ADMIN) && adminCommands.contains(command)) {
            logger.log(Level.DEBUG, "ADMIN IF: COMMAND {},ROLE {},User {}", command, userRole, user);
            response.sendRedirect(request.getContextPath() + ACCESS_ERROR_403_PAGE);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
