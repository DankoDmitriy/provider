package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        SessionRequestContent content = new SessionRequestContent(request);
        try {
            userService.login(content);
            content.setResultParametersInRequestAndRouter(request, router);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
