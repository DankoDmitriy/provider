package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.SessionAttribute.*;
import static com.danko.provider.controller.command.RequestAttribute.*;

public class HomeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private final AccountTransactionService accountTransactionService = ServiceProvider.getInstance().getAccountTransactionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        try {
            switch (user.getRole()) {
                case USER:
                    List<AccountTransaction> transactions = accountTransactionService.findAllByUserIdLimit(user.getUserId());
                    request.setAttribute(USER_PERSONAL_TRANSACTIONS_LIMIT, transactions);
                    router.setPageUrl(USER_PROFILE_PAGE);
                    break;
                case ADMIN:
//       TODO - подумать куда будет уходить админ.
                    router.setPageUrl(ADMIN_MAIN_PAGE);
//                    List<User> users = userService.findAllUsers();
//                    request.setAttribute("users", users);
                    break;
                default:
                    router.setRouteType(Router.RouteType.REDIRECT);
                    router.setPageUrl(request.getContextPath());
//                    FIXME - или так или так.
//                    router.setPageUrl(LOGIN_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find information database: {}", e);
            throw new CommandException(e);
        }
        return router;
    }
}
