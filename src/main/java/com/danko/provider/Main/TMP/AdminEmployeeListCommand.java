package com.danko.provider.Main.TMP;

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

public class AdminEmployeeListCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final long ROWS_ON_PAGE = 5;
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        SessionRequestContent sessionRequestContent = new SessionRequestContent(request);
        try {
            userService.findPageByUserRole(sessionRequestContent, ROWS_ON_PAGE);
            router.setPageUrl(sessionRequestContent.getPageUrl());
            sessionRequestContent.getRequestAttributes().forEach((s, o) -> {
                request.setAttribute(s, o);
            });
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
