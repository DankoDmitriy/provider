package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.PeriodicityWriteOff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.danko.provider.controller.command.PageUrl.ADMIN_TARIFFS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_TARIFF_ADD_PAGE;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_TARIFF_STATUS_LIST_FOR_NEW_TARIFF;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_TARIFF_WRITE_OFF_LIST_FOR_NEW_TARIFF;

public class AdminTariffAddCommand implements Command {
    private static TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        String tariffName = request.getParameter(TARIFF_ADD_NAME);
        String maxSpeed = request.getParameter(TARIFF_ADD_MAX_SPEED);
        String minSpeed = request.getParameter(TARIFF_ADD_MIN_SPEED);
        String traffic = request.getParameter(TARIFF_ADD_TRAFFIC);
        String price = request.getParameter(TARIFF_ADD_PRICE);
        String tariffStatusStr = request.getParameter(TARIFF_ADD_STATUS);
        String tariffPeriodWriteOfStr = request.getParameter(TARIFF_ADD_PERIOD);
        if (tariffName != null
                && maxSpeed != null
                && minSpeed != null
                && traffic != null
                && price != null
                && tariffStatusStr != null
                && tariffPeriodWriteOfStr != null) {
            try {
                Boolean result = tariffService.addTariff(
                        tariffName,
                        maxSpeed,
                        minSpeed,
                        traffic,
                        price,
                        tariffStatusStr,
                        tariffPeriodWriteOfStr);
                if (result) {
//                    FIXME - как от возврата  отбится....
//                    router.setPageUrl(ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
                    router.setRouteType(Router.RouteType.REDIRECT);
                    router.setPageUrl(request.getContextPath() + ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
                } else {
                    router.setPageUrl(ADMIN_TARIFF_ADD_PAGE);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            List<TariffStatus> tariffStatuses = Arrays.asList(TariffStatus.values());
            List<PeriodicityWriteOff> periodicityWriteOffs = Arrays.asList(PeriodicityWriteOff.values());
            request.setAttribute(ADMIN_TARIFF_STATUS_LIST_FOR_NEW_TARIFF, tariffStatuses);
            request.setAttribute(ADMIN_TARIFF_WRITE_OFF_LIST_FOR_NEW_TARIFF, periodicityWriteOffs);
            router.setPageUrl(ADMIN_TARIFF_ADD_PAGE);
        }
        return router;
    }
}
