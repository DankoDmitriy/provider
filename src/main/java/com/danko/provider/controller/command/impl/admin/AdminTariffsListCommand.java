package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminTariffsListCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final long ROWS_ON_PAGE = 5;
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        InputContent inputContent = new InputContent(request);
        try {
            tariffService.findPageTariff(inputContent, ROWS_ON_PAGE);
            router.setPageUrl(inputContent.getPageUrl());
            inputContent.getRequestAttributes().forEach((s, o) -> {
                request.setAttribute(s, o);
            });
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
