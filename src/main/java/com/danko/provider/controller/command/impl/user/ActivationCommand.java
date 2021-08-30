package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.danko.provider.controller.command.RequestAttribute.ACTIVATION_CODE;

public class ActivationCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

//        http://localhost:8080/provider_war_exploded/controller?command=ACTIVATION&activationCode=1234567898asd76543234567

        Router router = new Router();
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPageUrl(request.getContextPath());
        String activationCode = request.getParameter(ACTIVATION_CODE);

        logger.log(Level.DEBUG,"*******ACTIVATION_CODE = " + activationCode);
        try {
            if (userService.verificationOfActivationCode(activationCode)) {
                userService.updateActivationCodeStatus(activationCode, 1, UserStatus.ACTIVE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error with activation code or it status update: {}", e);
        }

        return router;
    }
}
