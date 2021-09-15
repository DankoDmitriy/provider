package com.danko.provider.controller.command.impl.admin;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.Command;
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
    private static Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private PaymentCardService paymentCardService = ServiceProvider.getInstance().getPaymentCardService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();

        String series = request.getParameter(PAYMENT_CARD_ADD_SERIES);
        String amount = request.getParameter(PAYMENT_CARD_ADD_AMOUNT);
        String count = request.getParameter(PAYMENT_CARD_ADD_COUNT);
        String dateExpiredStr = request.getParameter(PAYMENT_CARD_ADD_DATE_EXPIRED);

        if (series != null && amount != null && count != null && dateExpiredStr != null) {
            try {
                Map<String, String> cardsMap = paymentCardService.addCards(series, amount, count, dateExpiredStr);
                LocalDateTime cardExpiredDate = LocalDate.parse(dateExpiredStr, dateTimeFormatter).atStartOfDay();
                //                FIXME - положить объект в сессию и отправить польлзователя редиректом. ОТ F5
                request.setAttribute(ADMIN_NEW_PAYMENT_CARDS_LIST, cardsMap);
                request.setAttribute(ADMIN_NEW_PAYMENT_CARDS_EXPIRED_DATE, cardExpiredDate);
                router.setPageUrl(ADMIN_PAYMENTS_CARD_GENERATED_PAGE);

            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            router.setPageUrl(ADMIN_PAYMENTS_CARD_ADD_PAGE);
        }
        return router;
    }
}
