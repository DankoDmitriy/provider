package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.dao.impl.UserDaoImpl;
import com.danko.provider.domain.entity.*;
import com.danko.provider.domain.service.*;
import com.danko.provider.util.UniqueStringGenerator;
import com.danko.provider.util.UrlUtil;
import com.danko.provider.util.PasswordHasher;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> list;
        try {
            list = userDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all users in database: {}", e);
            throw new ServiceException("Could not find all users in database.", e);
        }
        return list;
    }

    @Override
    public List<User> findAllUsersByRole(UserRole role) throws ServiceException {
        List<User> list;
        try {
            list = userDao.findAllByRole(role);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all users in database: {}", e);
            throw new ServiceException("Could not find all users in database.", e);
        }
        return list;
    }

    @Override
    public Optional<User> findByNameAndPassword(String name, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            //TODO - ADD CHECK FOR VALIDATION LOGIN AND PASSWORD
            String passwordHash = PasswordHasher.hashString(password);
            optionalUser = userDao.findByNameAndPassword(name, passwordHash);
            return optionalUser;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find user by login and password: {}", e);
            throw new ServiceException("can't find user by login and password.", e);
        }
    }

    @Override
    public boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl, long tariffId) throws ServiceException {
        try {
            boolean result = true;
            InputDataValidator inputDataValidator = InputDataValidator.getInstance();
            if (password != null && inputDataValidator.newUserPasswordValid(password)) {
                String passwordHash = PasswordHasher.hashString(password);
                String newActivateCode = UniqueStringGenerator.generationUniqueString();
                result = userDao.updatePassword(userId, passwordHash, newActivateCode, UserStatus.WAIT_ACTIVATE);

                UserActionService userActionService = ServiceProvider.getInstance().getUserActionService();
                UserAction userAction = UserAction.builder().
                        setActionType(UserAction.ActionType.CHANGE_PASSWORD)
                        .setDateTime(LocalDateTime.now()).build();
                userActionService.add(userAction, userId, tariffId);

                EmailService emailService = ServiceProvider.getInstance().getEmailService();
                String domain = UrlUtil.requestUrlToDomain(requestUrl) + contextPath;
//                TODO - Расскомментировать отправку почты. Убрарно что бы не спамить самому себе.
//                emailService.sendActivateMail(email, domain, newActivateCode);
            } else {
                throw new ServiceException("Input password is not correct.");
            }
            return result;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Password has not been updated: {}", e);
            throw new ServiceException("Password has not been updated.", e);
        }
    }

    @Override
    public boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException {
        try {
            if (verificationOfActivationCode(activateCode)) {
                return userDao.updateActivationCodeStatus(activateCode, userStatus);
            }
            return false;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Activation code status has not been updated: {}", e);
            throw new ServiceException("Activation code status has not been updated.", e);
        }
    }

    @Override
    public boolean verificationOfActivationCode(String activateCode) throws ServiceException {
        try {
            return userDao.verificationOfActivationCode(activateCode);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Activation code has not been verification: {}", e);
            throw new ServiceException("Activation code has not been verification.", e);
        }
    }

    @Override
    public BigDecimal updateTariffPlan(long userId, long tariffId) throws ServiceException {
        BigDecimal newUserTraffic = null;
        TariffService tariffService = ServiceProvider.getInstance().getTariffService();
        try {
            Optional<Tariff> tariffOptional = tariffService.findById(tariffId);
            if (!tariffOptional.isEmpty()) {
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    Tariff tariff = tariffOptional.get();
                    User user = optionalUser.get();
                    if (tariff.getStatus().equals(TariffStatus.ACTIVE) & user.getStatus().equals(UserStatus.ACTIVE)) {
                        newUserTraffic = tariff.getTraffic().add(user.getTraffic());
                        userDao.updateTariffAndTrafficValue(userId, tariffId, newUserTraffic);

                        UserActionService userActionService = ServiceProvider.getInstance().getUserActionService();
                        UserAction userAction = UserAction.builder().
                                setActionType(UserAction.ActionType.CHANGE_TARIFF)
                                .setDateTime(LocalDateTime.now()).build();
                        userActionService.add(userAction, userId, tariffId);
                    }
                }
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Tariff has not been updated: {}", e);
            throw new ServiceException("Tariff has not been updated.", e);
        }
        return newUserTraffic;
    }

    @Override
    public BigDecimal activatePaymentCard(String cardNumber, String cardPin, long userId, BigDecimal userBalance, long tariffId) throws ServiceException {
        BigDecimal newUserBalance;
        PaymentCardService paymentCardService = ServiceProvider.getInstance().getPaymentCardService();
        Optional<PaymentCard> paymentCardOptional = paymentCardService.findByCardNumberAndPin(cardNumber, cardPin);
        if (!paymentCardOptional.isEmpty()) {
            PaymentCard paymentCard = paymentCardOptional.get();
            if (paymentCard.getCardStatus().equals(PaymentCard.CardStatus.NOT_USED)) {
                paymentCard.setCardStatus(PaymentCard.CardStatus.USED);

                LocalDateTime localDateTimeNow = LocalDateTime.now();

                UserAction userAction = UserAction.builder().
                        setActionType(UserAction.ActionType.CARD_ACTIVATE)
                        .setDateTime(localDateTimeNow).build();

                AccountTransaction accountTransaction = AccountTransaction.builder()
                        .setSum(paymentCard.getAmount())
                        .setDate(localDateTimeNow)
                        .setUserId(userId)
                        .setType(TransactionType.REFILL).build();

                paymentCard.setActivationDate(localDateTimeNow);

                newUserBalance = userBalance.add(paymentCard.getAmount());

                try {
                    userDao.balanceReplenishment(userId, newUserBalance, tariffId, paymentCard, userAction, accountTransaction);
                } catch (DaoException e) {
                    logger.log(Level.ERROR, "Balance has not been replenished: {}", e);
                    throw new ServiceException("Balance has not been replenished.", e);
                }

            } else {
                throw new ServiceException("Balance has not been replenished.");
            }
        } else {
            throw new ServiceException("Balance has not been replenished.");
        }
        return newUserBalance;
    }
}
