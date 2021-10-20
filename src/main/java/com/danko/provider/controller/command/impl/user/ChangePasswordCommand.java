package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.danko.provider.controller.command.PageUrl.LOGOUT_PAGE;
import static com.danko.provider.controller.command.PageUrl.USER_CHANGE_PASSWORD_PAGE;
import static com.danko.provider.controller.command.ParamName.USER_CHANGE_PASSWORD_NEW_PASSWORD;
import static com.danko.provider.controller.command.RequestAttribute.USER_PERSONAL_MESSAGE_ERROR;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class ChangePasswordCommand implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final InputDataValidator validator = InputDataValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        String newPassword = request.getParameter(USER_CHANGE_PASSWORD_NEW_PASSWORD);
        String contextPath = request.getContextPath();
        String requestUrl = request.getRequestURL().toString();
        try {
            if (newPassword != null && validator.isPasswordValid(newPassword)) {
                if (userService.updatePassword(user.getUserId(),
                        newPassword,
                        user.getEmail(),
                        contextPath,
                        requestUrl,
                        user.getTariffId())) {
                    router.setPageUrl(request.getContextPath() + LOGOUT_PAGE);
                    router.setRouteType(Router.RouteType.REDIRECT);
                } else {
                    router.setPageUrl(USER_CHANGE_PASSWORD_PAGE);
                    request.setAttribute(USER_PERSONAL_MESSAGE_ERROR, true);
                }
            } else {
                router.setPageUrl(USER_CHANGE_PASSWORD_PAGE);
            }
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
