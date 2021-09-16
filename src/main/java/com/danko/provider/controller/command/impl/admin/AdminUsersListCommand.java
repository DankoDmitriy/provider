package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE;
import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_USERS_LIST;

public class AdminUsersListCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPageUrl(HOME_PAGE);
        try {
            List<User> users = userService.findAllUsersByRole(UserRole.USER);
            router.setPageUrl(ADMIN_USERS_LIST_PAGE);
            request.setAttribute(ADMIN_USERS_LIST, users);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find all users in database: {}", e);
            throw new CommandException(e);
        }
        return router;
    }
}
