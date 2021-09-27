package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminUsersListCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final long ROWS_ON_PAGE = 5;
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        SessionRequestContent content = new SessionRequestContent(request);
        try {
            userService.findPageByUserRole(content, ROWS_ON_PAGE);
            router.setPageUrl(content.getPageUrl());
            content.setResultParametersInRequestAndRouter(request, router);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
