package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.InputDataValidator;

import static com.danko.provider.controller.command.ParamName.USER_CHANGE_PASSWORD_NEW_PASSWORD;
import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.RequestAttribute.*;
import static com.danko.provider.controller.command.SessionAttribute.USER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String password = request.getParameter(USER_CHANGE_PASSWORD_NEW_PASSWORD);
        boolean updateResult = true;
//TODO - Подумать как сделать красиво...
        if (password != null) {
            if (InputDataValidator.newUserPasswordValid(password)) {
                try {
                    updateResult = userService.updatePassword(user.getUserId(), password, user.getEmail());
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, "Password has not been updated: {}", e);
                    router.setPageUrl(USER_CHANGE_PASSWORD_PAGE);
                    request.setAttribute(USER_PERSONAL_MESSAGE_ERROR, "Password did not update, please try again later");
                }
                if (updateResult) {
//              END SESSION. ANd RETURN TO LOGIN PAGE.
                    router.setPageUrl(request.getContextPath() + LOGOUT_PAGE);
                    router.setRouteType(Router.RouteType.REDIRECT);
                } else {
                    router.setPageUrl(USER_CHANGE_PASSWORD_PAGE);
                    request.setAttribute(USER_PERSONAL_MESSAGE_ERROR, "Password did not update, please try again later");
                }

            } else {
                request.setAttribute(NEW_PASSWORD, password);
                request.setAttribute(USER_PERSONAL_MESSAGE_ERROR, "Input password is not correct: ");
                router.setPageUrl(USER_CHANGE_PASSWORD_PAGE);
            }
        } else {
            router.setPageUrl(USER_CHANGE_PASSWORD_PAGE);
        }
        return router;
    }
}
