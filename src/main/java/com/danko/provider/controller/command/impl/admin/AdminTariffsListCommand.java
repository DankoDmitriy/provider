package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.danko.provider.controller.command.PageUrl.ADMIN_TARIFFS_LIST_PAGE;
import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_TARIFFS_LIST;

public class AdminTariffsListCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPageUrl(HOME_PAGE);
        try {
            List<Tariff> tariffs = tariffService.findAllTariffs();
            request.setAttribute(ADMIN_TARIFFS_LIST, tariffs);
            router.setPageUrl(ADMIN_TARIFFS_LIST_PAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find all tariffs in database: {}", e);
            throw new CommandException(e);
        }
        return router;
    }
}
