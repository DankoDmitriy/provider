package com.danko.provider.controller.command.impl.common;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.Tariff;
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

import java.util.Optional;

import static com.danko.provider.controller.command.ParamName.LOGIN_FORM_NAME;
import static com.danko.provider.controller.command.ParamName.LOGIN_FORM_PASSWORD;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.SessionAttribute.*;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private final AccountTransactionService accountTransactionService = ServiceProvider.getInstance().getAccountTransactionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();

        String name = request.getParameter(LOGIN_FORM_NAME);
        String password = request.getParameter(LOGIN_FORM_PASSWORD);

        try {
            Optional<User> optionalUser = userService.findByNameAndPassword(name, password);
            if (optionalUser.isPresent()) {

                User user = optionalUser.get();
                Optional<Tariff> tariffOptional = tariffService.findById(user.getTariffId());
                user.setTariff(tariffOptional.get());

                session.setAttribute(SESSION_USER, user);
                session.setAttribute(IS_LOGIN_ERROR, false);

                router.setPageUrl(HOME_PAGE);
            } else {
                session.setAttribute(IS_LOGIN_ERROR, true);
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPageUrl(request.getContextPath());
            }
            return router;
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Logon process has not finished: {}", e);
            throw new CommandException("Logon process has not finished.", e);
        }
    }
}
