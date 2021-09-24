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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.CARD_NUMBER;
import static com.danko.provider.controller.command.ParamName.CARD_PIN;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class PayCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);

        String cardNumber = request.getParameter(CARD_NUMBER);
        String cardPin = request.getParameter(CARD_PIN);
        if (cardNumber != null && cardPin != null) {
            try {
                BigDecimal newBalance = userService.activatePaymentCard
                        (cardNumber, cardPin, user.getUserId(), user.getBalance(), user.getTariffId());
                user.setBalance(newBalance);
                session.setAttribute(SESSION_USER, user);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            router.setPageUrl(HOME_PAGE);
        } else {
            router.setPageUrl(USER_ACTIVATE_PAYMENT_CARD);
        }
        return router;
    }
}
