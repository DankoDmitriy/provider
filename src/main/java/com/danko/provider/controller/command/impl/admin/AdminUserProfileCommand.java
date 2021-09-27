package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.service.*;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_PROFILE_PAGE;
import static com.danko.provider.controller.command.ParamName.USER_PROFILE_ID;
import static com.danko.provider.controller.command.RequestAttribute.*;

public class AdminUserProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]{0,15}$";
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final AccountTransactionService accountTransactionService = ServiceProvider.getInstance().getAccountTransactionService();
    private final UserActionService userActionService = ServiceProvider.getInstance().getUserActionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
        String userIdStr = request.getParameter(USER_PROFILE_ID);
        try {
            if (userIdStr.matches(ID_CHECK_REGEX)) {
                long userId = Long.parseLong(userIdStr);
                Optional<User> optionalUser = userService.findById(userId);
                if (!optionalUser.isEmpty()) {
                    User user = optionalUser.get();
                    Tariff tariff = tariffService.findById(user.getTariffId()).get();
                    List<AccountTransaction> transactionList = accountTransactionService.findAllByUserIdLimit(userId);
                    List<UserAction> userActionList = userActionService.findAllByUserIdLimit(userId);
                    user.setTariff(tariff);
                    request.setAttribute(ADMIN_USER_PROFILE_USER, user);
                    request.setAttribute(ADMIN_USER_PROFILE_TRANSACTIONS, transactionList);
                    request.setAttribute(ADMIN_USER_PROFILE_ACTIONS, userActionList);
                    router.setPageUrl(ADMIN_USER_PROFILE_PAGE);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
