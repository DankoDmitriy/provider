package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPageUrl(request.getContextPath());
        HttpSession session = request.getSession();
        session.invalidate();
        return router;
    }
}
