package com.danko.provider.controller.command.impl.user;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.RequestAttribute.USER_TARIFF_LIST;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class TariffListCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        if (user.getRole().equals(UserRole.USER)) {
            List<Tariff> tariffs = null;
            try {
                tariffs = tariffService.findAllByStatus(TariffStatus.ACTIVE);
                request.setAttribute(USER_TARIFF_LIST, tariffs);
                router.setPageUrl(USER_TARIFFS_LIST);
            } catch (ServiceException e) {
//                FIXME - переписать. нельзя использовать исключение, как флаг для действия.
                logger.log(Level.ERROR, "Could not find tariffs in database: {}", e);
                router.setPageUrl(HOME_PAGE);
            }
        } else {
            router.setPageUrl(LOGIN_PAGE);
        }
        return router;
    }
}
