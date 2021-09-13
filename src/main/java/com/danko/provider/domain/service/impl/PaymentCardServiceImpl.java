package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.impl.PaymentCardDaoImpl;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.domain.service.PaymentCardService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.util.PasswordHasher;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class PaymentCardServiceImpl implements PaymentCardService {
    private static Logger logger = LogManager.getLogger();
    //    private PaymentCardDao paymentCardDao = new PaymentCardDaoImpl();
    private PaymentCardDao paymentCardDao;
    private TransactionManager transactionManager;

    public PaymentCardServiceImpl(PaymentCardDao paymentCardDao, TransactionManager transactionManager) {
        this.paymentCardDao = paymentCardDao;
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
}
