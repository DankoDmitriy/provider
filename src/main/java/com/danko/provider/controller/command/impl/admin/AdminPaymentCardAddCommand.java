package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.service.PaymentCardService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminPaymentCardAddCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final PaymentCardService paymentCardService = ServiceProvider.getInstance().getPaymentCardService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        SessionRequestContent content = new SessionRequestContent(request);
        try {
            paymentCardService.addCards(content);
            content.setResultParametersInRequestAndRouter(request, router);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
