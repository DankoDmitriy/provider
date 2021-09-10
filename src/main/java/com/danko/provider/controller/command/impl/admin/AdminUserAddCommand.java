package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.TransferObject;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_NEW_USER_CARD_TRANSFER_OBJECT;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_TARIFFS_LIST_FOR_NEW_USER;

public class AdminUserAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private static UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String firstName = request.getParameter(USER_ADD_FIRST_NAME);
        String lastName = request.getParameter(USER_ADD_LAST_NAME);
        String patronymic = request.getParameter(USER_ADD_PATRONYMIC);
        String contractDate = request.getParameter(USER_ADD_CONTRACT_DATE);
        String tariffId = request.getParameter(USER_ADD_TARIFF_ID);
        String email = request.getParameter(USER_ADD_E_MAIL);

        if (firstName != null && lastName != null && patronymic != null && contractDate != null && tariffId != null && email != null) {
            try {
                Optional<TransferObject> createdUserOptional = userService.addUser(firstName, lastName, patronymic, contractDate, tariffId, email);
                TransferObject createdUserTr = createdUserOptional.get();
                request.setAttribute(ADMIN_NEW_USER_CARD_TRANSFER_OBJECT, createdUserTr);
                router.setPageUrl(ADMIN_USER_ADD_CARD);
            } catch (ServiceException e) {
                logger.log(Level.WARN, "User has not added: {}", e);
                throw new CommandException("User has not added.", e);
            }
        } else {
            router.setPageUrl(ADMIN_USER_ADD_PAGE);
            try {
                List<Tariff> tariffs = tariffService.findAllByStatus(TariffStatus.ACTIVE);
                request.setAttribute(ADMIN_TARIFFS_LIST_FOR_NEW_USER, tariffs);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Could not find all tariffs in database: {}", e);
                throw new CommandException(e);
            }
        }
        return router;
    }
}
