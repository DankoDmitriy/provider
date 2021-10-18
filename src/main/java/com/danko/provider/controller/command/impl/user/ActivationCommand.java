package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

import static com.danko.provider.controller.command.RequestAttribute.ACTIVATION_CODE;

public class ActivationCommand implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPageUrl(request.getContextPath());
        String activationCode = request.getParameter(ACTIVATION_CODE);
        try {
            userService.updateActivationCodeStatus(activationCode, UserStatus.ACTIVE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
