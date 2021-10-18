package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class AdminTariffAddCommand implements Command {
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        SessionRequestContent content = new SessionRequestContent(request);
        try {
            tariffService.addTariff(content);
            content.setResultParametersInRequestAndRouter(request, router);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
