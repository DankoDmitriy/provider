package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.service.PaymentCardService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class AdminPaymentCardSearchCommand implements Command {
    private final PaymentCardService paymentCardService = ServiceProvider.getInstance().getPaymentCardService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        SessionRequestContent content = new SessionRequestContent(request);
        try {
            paymentCardService.findByNumber(content);
            content.setResultParametersInRequestAndRouter(request, router);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
