package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
import com.danko.provider.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        switch (user.getRole()) {
            case USER:
            case ADMIN:
                router.setPageUrl(HOME_PAGE);
                break;
            default:
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPageUrl(request.getContextPath());
        }
        return router;
    }
}
