package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.PeriodicityWriteOff;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.*;

public class AdminTariffEditCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final List<TariffStatus> TARIFF_STATUSES = Arrays.asList(TariffStatus.values());
    private static final List<PeriodicityWriteOff> PERIODICITY_WRITE_OFFS = Arrays.asList(PeriodicityWriteOff.values());
    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]*$";
    private TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String tariffEditIdStr = request.getParameter(TARIFF_EDIT_ID);
        String tariffId = request.getParameter(TARIFF_EDIT_ID);
        String tariffName = request.getParameter(TARIFF_EDIT_NAME);
        String maxSpeed = request.getParameter(TARIFF_EDIT_MAX_SPEED);
        String minSpeed = request.getParameter(TARIFF_EDIT_MIN_SPEED);
        String traffic = request.getParameter(TARIFF_EDIT_TRAFFIC);
        String price = request.getParameter(TARIFF_EDIT_PRICE);
        String tariffStatusStr = request.getParameter(TARIFF_EDIT_STATUS);
        String tariffPeriodWriteOfStr = request.getParameter(TARIFF_EDIT_PERIOD);
        try {
            if (tariffEditIdStr.matches(ID_CHECK_REGEX) &&
                    tariffName != null &&
                    maxSpeed != null &&
                    minSpeed != null &&
                    traffic != null &&
                    price != null &&
                    tariffStatusStr != null &&
                    tariffPeriodWriteOfStr != null) {
                Tariff tariffOrigin = stringToObjectTariff(request.getParameter(TARIFF_EDIT_ORIGIN));
                boolean result = tariffService.update(tariffId, tariffName, maxSpeed, minSpeed, traffic, price, tariffStatusStr, tariffPeriodWriteOfStr, tariffOrigin);
//                FIXME - сделать пользовательскую страницу для админа. и туда уже отправлять.
                List<Tariff> tariffs = Arrays.asList(tariffService.findById(Long.parseLong(tariffId)).get());
                request.setAttribute(ADMIN_TARIFFS_LIST, tariffs);
                request.setAttribute(ADMIN_TARIFFS_LIST_RESULT_WORK_FOR_MESSAGE, result);
                router.setPageUrl(ADMIN_TARIFFS_LIST_PAGE);
            } else {
                if (tariffEditIdStr.matches(ID_CHECK_REGEX)) {
                    Optional<Tariff> optionalTariff = tariffService.findById(Long.parseLong(tariffEditIdStr));
                    if (!optionalTariff.isEmpty()) {
                        request.setAttribute(ADMIN_TARIFF_STATUS_LIST, TARIFF_STATUSES);
                        request.setAttribute(ADMIN_TARIFF_WRITE_OFF_LIST, PERIODICITY_WRITE_OFFS);
                        request.setAttribute(ADMIN_TARIFF_EDIT, optionalTariff.get());
                        request.setAttribute(ADMIN_TARIFF_EDIT_ORIGINAL, objectTariffToString(optionalTariff.get()));
                        router.setPageUrl(ADMIN_TARIFF_EDIT_PAGE);
                    } else {
                        router.setRouteType(Router.RouteType.REDIRECT);
                        router.setPageUrl(request.getContextPath() + ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
                    }
                } else {
                    router.setRouteType(Router.RouteType.REDIRECT);
                    router.setPageUrl(request.getContextPath() + ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
                }
            }
        } catch (
                ServiceException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        }
        return router;
    }

    private String objectTariffToString(Tariff tariff) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(tariff);
            objectOutputStream.flush();
            return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }

    private Tariff stringToObjectTariff(String tariffStr) {
        try {
            byte[] objToBytes = Base64.getDecoder().decode(tariffStr);
            ByteArrayInputStream bais = new ByteArrayInputStream(objToBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Tariff) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }
}
