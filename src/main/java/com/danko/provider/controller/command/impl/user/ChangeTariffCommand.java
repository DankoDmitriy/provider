package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.USER_CHANGE_TARIFF_NEW_TARIFF_ID;
import static com.danko.provider.controller.command.RequestAttribute.USER_TARIFF_LIST;
import static com.danko.provider.controller.command.RequestAttribute.USER_TARIFF_LIST_RESULT_FOR_MESSAGE;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class ChangeTariffCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID_CHECK_REGEX = "^[1-9]{1}[0-9]{0,15}$";
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        String newTariffIdStr = request.getParameter(USER_CHANGE_TARIFF_NEW_TARIFF_ID);
        try {
            if (newTariffIdStr.matches(ID_CHECK_REGEX)) {
                long newTariffId = Long.parseLong(newTariffIdStr);
                boolean relust = userService.updateTariffPlan(user.getUserId(), newTariffId);
                if (relust) {
                    User userUpdate = userService.findById(user.getUserId()).get();
                    userUpdate.setTariff(tariffService.findById(newTariffId).get());
                    session.setAttribute(SESSION_USER, userUpdate);
                }
                router.setPageUrl(HOME_PAGE);
            } else {
                List<Tariff> tariffs = tariffService.findAllByStatus(TariffStatus.ACTIVE);
                request.setAttribute(USER_TARIFF_LIST_RESULT_FOR_MESSAGE, "false");
                request.setAttribute(USER_TARIFF_LIST, tariffs);
                router.setPageUrl(USER_TARIFFS_LIST);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
