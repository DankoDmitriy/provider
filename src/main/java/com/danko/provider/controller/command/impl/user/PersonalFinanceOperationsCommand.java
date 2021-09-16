package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.domain.service.ServiceProvider;
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

public class PersonalFinanceOperationsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final AccountTransactionService accountTransactionService = ServiceProvider.getInstance().getAccountTransactionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        try {
            switch (user.getRole()) {
                case USER:
                    List<AccountTransaction> transactions;
                    router.setPageUrl(USER_ALL_FINANCE_OPERATIONS_PAGE);
                    transactions = accountTransactionService.findAllByUserId(user.getUserId());
                    request.setAttribute(USER_PERSONAL_TRANSACTIONS_ALL, transactions);
                    break;
                case ADMIN:
//                    TODO - тут будет админ смотреть фин. операции пользователя.
                    router.setPageUrl(HOME_PAGE);
                    break;
                default:
                    router.setPageUrl(LOGIN_PAGE);
                    break;
            }
            return router;
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Could not find all transactions in database: {}", e);
            throw new CommandException("Could not find all transactions in database.", e);
        }
    }
}
