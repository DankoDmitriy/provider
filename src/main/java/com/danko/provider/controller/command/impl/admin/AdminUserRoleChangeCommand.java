package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_PROFILE_PAGE_REDIRECT;
import static com.danko.provider.controller.command.ParamName.USER_PROFILE_ID;

public class AdminUserRoleChangeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]*$";
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
        String userIdStr = request.getParameter(USER_PROFILE_ID);
        try {
            if (userIdStr.matches(ID_CHECK_REGEX)) {
                long userId = Long.parseLong(userIdStr);
                if (userService.changeRole(userId)) {
                    router.setRouteType(Router.RouteType.REDIRECT);
                    router.setPageUrl(request.getContextPath() + ADMIN_USER_PROFILE_PAGE_REDIRECT + userId);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
