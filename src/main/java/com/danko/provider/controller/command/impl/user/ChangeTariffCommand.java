package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.User;
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

import java.math.BigDecimal;

import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.ParamName.USER_CHANGE_TARIFF_NEW_TARIFF_ID;
import static com.danko.provider.controller.command.SessionAttribute.USER;

public class ChangeTariffCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static UserService userService = ServiceProvider.getInstance().getUserService();
    private static TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long newTariffId = Long.valueOf(request.getParameter(USER_CHANGE_TARIFF_NEW_TARIFF_ID));
        try {
            BigDecimal newUserTraffic = userService.updateTariffPlan(user.getUserId(), newTariffId);
            user.setTariff(tariffService.findById(newTariffId).get());
            user.setTraffic(newUserTraffic);
            session.setAttribute(USER, user);
            router.setPageUrl(HOME_PAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Tariff has not been updated: {}", e);
            router.setPageUrl(HOME_PAGE);
        }
        return router;
    }
}
