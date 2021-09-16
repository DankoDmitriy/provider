package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.PaymentCardSerialDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.impl.PaymentCardDaoImpl;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.domain.entity.PaymentCardSerial;
import com.danko.provider.domain.service.PaymentCardService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.util.PasswordGenerator;
import com.danko.provider.util.PasswordHasher;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaymentCardServiceImpl implements PaymentCardService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private static final int PAYMENT_CARD_NUMBER_LENGTH = 7;
    private static final int PAYMENT_CARD_PIN_LENGTH = 8;
    private final PaymentCardDao paymentCardDao;
    private final PaymentCardSerialDao paymentCardSerialDao;
    private final TransactionManager transactionManager;


    public PaymentCardServiceImpl(PaymentCardDao paymentCardDao, PaymentCardSerialDao paymentCardSerialDao, TransactionManager transactionManager) {
        this.paymentCardDao = paymentCardDao;
        this.paymentCardSerialDao = paymentCardSerialDao;
        this.transactionManager = transactionManager;
    }

    //    FIXME - Наверное вообще не нужен будет этот метод.
    @Override
    public Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws ServiceException {
        try {
            //        TODO - Добавить валидацию номера и пина карты.
            transactionManager.startTransaction();
            String cardNumberHash = PasswordHasher.hashString(cardNumber);
            String cardPinHash = PasswordHasher.hashString(cardPin);
            return paymentCardDao.findByCardNumberAndPin(cardNumberHash, cardPinHash);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find payment card in database: {}", e);
            throw new ServiceException("Could not find payment card in database.", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
    }

    @Override
    public Map<String, String> addCards(String series, String amount, String count, String dateExpiredStr) throws ServiceException {
        InputDataValidator inputDataValidator = InputDataValidator.getInstance();
        if (inputDataValidator.isPaymentCardSeriesValid(series) &&
                inputDataValidator.isPaymentCardAmountValid(amount) &&
                inputDataValidator.isPaymentCardCountValid(count) &&
                inputDataValidator.isPaymentCardDateExpiredValid(dateExpiredStr)) {

            try {
                transactionManager.startTransaction();
                Optional<PaymentCardSerial> optionalPaymentCardSerial = paymentCardSerialDao.findBySeries(series);
                if (optionalPaymentCardSerial.isEmpty()) {
                    LocalDateTime cardExpiredDate = LocalDate.parse(dateExpiredStr, dateTimeFormatter).atStartOfDay();
                    PasswordGenerator passwordGenerator = new PasswordGenerator.Builder()
                            .useDigits(true)
                            .useLower(false)
                            .useUpper(false)
                            .build();
                    Map<String, String> cardsMap = new HashMap<>();
                    for (int i = 1; i <= Integer.parseInt(count); i++) {
                        String cardNumber = new StringBuilder()
                                .append(series)
                                .append(String.format("%0" + PAYMENT_CARD_NUMBER_LENGTH + "d", i)).toString();
                        String cardPin = passwordGenerator.generate(PAYMENT_CARD_PIN_LENGTH);
                        cardsMap.put(cardNumber, cardPin);
                        String cardNumberHash = PasswordHasher.hashString(cardNumber);
                        String cardPinHash = PasswordHasher.hashString(cardPin);
                        paymentCardDao.add(new BigDecimal(amount), cardNumberHash, cardPinHash, PaymentCard.CardStatus.NOT_USED, cardExpiredDate);
                    }
                    paymentCardSerialDao.add(PasswordHasher.hashString(series));
                    transactionManager.commit();
                    return cardsMap;
                }
            } catch (DaoException e) {
                try {
                    transactionManager.rollback();
                } catch (DaoException e1) {
                    logger.log(Level.ERROR, "Rollback transaction error: {}", e);
                    throw new ServiceException(e);
                }
                throw new ServiceException(e);
            } finally {
                try {
                    transactionManager.endTransaction();
                } catch (DaoException e) {
                    logger.log(Level.ERROR, "End transaction error: {}", e);
                    throw new ServiceException("End transaction error", e);
                }
            }
        }
        return null;
    }
}
