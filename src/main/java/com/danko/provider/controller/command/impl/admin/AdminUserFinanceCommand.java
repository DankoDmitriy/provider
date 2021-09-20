package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_FINANCES_OPERATION_PAGE;

public class AdminUserFinanceCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]{0-16}}$";
    public static final long ROWS_ON_PAGE = 5;
    private final AccountTransactionService accountTransactionService = ServiceProvider.getInstance().getAccountTransactionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPageUrl(ADMIN_USER_FINANCES_OPERATION_PAGE);
        long rowsInTable = 0;
        List<AccountTransaction> transactions;
        String userIdStr = request.getParameter("user_id");
        String nextPageStr = request.getParameter("nextPage");

        long nextPage = Long.parseLong(nextPageStr);
        long previewPage = 0;
        long userId = Long.parseLong(userIdStr);
        try {
            if (nextPage <= 0) {
                rowsInTable = accountTransactionService.rowsInTableForUser(userId);
                nextPage = 0;
                previewPage = -1;
                transactions = accountTransactionService.findPageByUserId(userId, ROWS_ON_PAGE, nextPage);
                if (rowsInTable > ROWS_ON_PAGE) {
                    nextPage++;
                }
            } else {
                rowsInTable = accountTransactionService.rowsInTableForUser(userId);
                transactions = accountTransactionService.findPageByUserId(userId, ROWS_ON_PAGE, nextPage);
                if (rowsInTable > (ROWS_ON_PAGE * (nextPage + 1))) {
                    previewPage = nextPage - 1;
                    nextPage++;
                } else {
                    previewPage = nextPage - 1;
                }

            }
            request.setAttribute("nextPage", nextPage);
            request.setAttribute("previewPage", previewPage);
            request.setAttribute("transactions", transactions);
            request.setAttribute("user_id", userId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
