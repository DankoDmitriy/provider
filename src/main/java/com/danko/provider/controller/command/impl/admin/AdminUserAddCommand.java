package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.InputContent;
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
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        InputContent content = new InputContent(request);
        try {
            userService.addUser(content);
            router.setPageUrl(content.getPageUrl());
            content.getRequestAttributes().forEach((s, o) -> {
                request.setAttribute(s, o);
            });
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}