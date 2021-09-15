package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserActionService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.ConnectException;
import java.util.List;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.RequestAttribute.USER_ACTION_LIST;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class ActionCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private UserActionService userActionService = ServiceProvider.getInstance().getUserActionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        if (user.getRole().equals(UserRole.USER)) {
            try {
                List<UserAction> actions = userActionService.findAllByUserId(user.getUserId());
                request.setAttribute(USER_ACTION_LIST, actions);
                router.setPageUrl(USER_ACTIONS_PAGE);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            router.setPageUrl(LOGIN_PAGE);
        }
        return router;
    }
}
