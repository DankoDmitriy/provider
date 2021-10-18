package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.danko.provider.controller.command.PageUrl.ADMIN_MAIN_PAGE;
import static com.danko.provider.controller.command.PageUrl.USER_PROFILE_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.USER_PERSONAL_TRANSACTIONS_LIMIT;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class HomeCommand implements Command {
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
                    router.setPageUrl(ADMIN_MAIN_PAGE);
                    break;
                default:
                    router.setRouteType(Router.RouteType.REDIRECT);
                    router.setPageUrl(request.getContextPath());
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
