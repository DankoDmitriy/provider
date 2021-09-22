package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.entity.PaymentCardSerial;
import com.danko.provider.domain.service.PaymentCardService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.exception.CommandException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.danko.provider.controller.command.PageUrl.ADMIN_PAYMENTS_CARD_ADD_PAGE;
import static com.danko.provider.controller.command.PageUrl.ADMIN_PAYMENTS_CARD_GENERATED_PAGE;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_NEW_PAYMENT_CARDS_EXPIRED_DATE;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_NEW_PAYMENT_CARDS_LIST;

public class AdminPaymentCardAddCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private final PaymentCardService paymentCardService = ServiceProvider.getInstance().getPaymentCardService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        InputContent content = new InputContent(request);
//                FIXME - положить объект в сессию и отправить польлзователя редиректом. ОТ F5
        try {
            paymentCardService.addCards(content);
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
