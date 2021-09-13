package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.*;
import com.danko.provider.domain.entity.*;
import com.danko.provider.domain.service.*;
import com.danko.provider.util.PasswordGenerator;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final int CONTRACT_LENGTH = 11;
    private static final int PASSWORD_LENGTH = 11;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private UserDao userDao;
    private TariffDao tariffDao;
    private UserActionDao userActionDao;
    private PaymentCardDao paymentCardDao;
    private AccountTransactionDao accountTransactionDao;
    private TransactionManager transactionManager;


    public UserServiceImpl(UserDao userDao, TariffDao tariffDao, UserActionDao userActionDao, PaymentCardDao paymentCardDao, AccountTransactionDao accountTransactionDao, TransactionManager transactionManager) {
        this.userDao = userDao;
        this.tariffDao = tariffDao;
        this.userActionDao = userActionDao;
        this.paymentCardDao = paymentCardDao;
        this.accountTransactionDao = accountTransactionDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            transactionManager.startTransaction();
            return userDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all users in database: {}", e);
            throw new ServiceException("Could not find all users in database.", e);
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
    public List<User> findAllUsersByRole(UserRole role) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return userDao.findAllByRole(role);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all users in database: {}", e);
            throw new ServiceException("Could not find all users in database.", e);
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
    public Optional<User> findByNameAndPassword(String name, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            //TODO - ADD CHECK FOR VALIDATION LOGIN AND PASSWORD
            String passwordHash = PasswordHasher.hashString(password);
            transactionManager.startTransaction();
            optionalUser = userDao.findByNameAndPassword(name, passwordHash);
            return optionalUser;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find user by login and password: {}", e);
            throw new ServiceException("can't find user by login and password.", e);
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
    public boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl, long tariffId) throws ServiceException {
        try {
            boolean result = true;
            InputDataValidator inputDataValidator = InputDataValidator.getInstance();
            if (password != null && inputDataValidator.isPasswordValid(password)) {
                transactionManager.startTransaction();
                String passwordHash = PasswordHasher.hashString(password);
                String newActivateCode = UniqueStringGenerator.generationUniqueString();
                result = userDao.updatePassword(userId, passwordHash, newActivateCode, UserStatus.WAIT_ACTIVATE);
                UserAction userAction = UserAction.builder().
                        setActionType(UserAction.ActionType.CHANGE_PASSWORD)
                        .setDateTime(LocalDateTime.now()).build();
                userActionDao.add(userAction, userId, tariffId);
                transactionManager.commit();
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
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                logger.log(Level.ERROR, "Rollback error: {}", e1);
                throw new ServiceException("Rollback error.", e1);
            }
            throw new ServiceException("Password has not been updated.", e);
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
    public boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException {
        boolean result = false;
        try {
            transactionManager.startTransaction();
            if (userDao.verificationOfActivationCode(activateCode)) {
                result = userDao.updateActivationCodeStatus(activateCode, userStatus);
                transactionManager.commit();
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Activation code status has not been updated: {}", e);
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                logger.log(Level.ERROR, "Rollback error: {}", e1);
                throw new ServiceException("Rollback error.", e);
            }
            throw new ServiceException("Activation code status has not been updated.", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
        return result;
    }

    @Override
    public boolean verificationOfActivationCode(String activateCode) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return userDao.verificationOfActivationCode(activateCode);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Activation code has not been verification: {}", e);
            throw new ServiceException("Activation code has not been verification.", e);
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
    public BigDecimal updateTariffPlan(long userId, long tariffId) throws ServiceException {
        BigDecimal newUserTraffic = null;
        try {
            transactionManager.startTransaction();
            Optional<Tariff> tariffOptional = tariffDao.findById(tariffId);
            if (!tariffOptional.isEmpty()) {
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    Tariff tariff = tariffOptional.get();
                    User user = optionalUser.get();
                    if (tariff.getStatus().equals(TariffStatus.ACTIVE) & user.getStatus().equals(UserStatus.ACTIVE)) {
                        newUserTraffic = tariff.getTraffic().add(user.getTraffic());
                        userDao.updateTariffAndTrafficValue(userId, tariffId, newUserTraffic);
                        UserAction userAction = UserAction.builder().
                                setActionType(UserAction.ActionType.CHANGE_TARIFF)
                                .setDateTime(LocalDateTime.now()).build();
                        userActionDao.add(userAction, userId, tariffId);
                        transactionManager.commit();
                    }
                }
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Tariff has not been updated: {}", e);
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                logger.log(Level.ERROR, "Rollback error: {}", e1);
                throw new ServiceException("Rollback error.", e);
            }
            throw new ServiceException("Tariff has not been updated.", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
        return newUserTraffic;
    }

    @Override
    public BigDecimal activatePaymentCard(String cardNumber, String cardPin, long userId, BigDecimal userBalance, long tariffId) throws ServiceException {
        BigDecimal newUserBalance;
        try {
            transactionManager.startTransaction();

            String cardNumberHash = PasswordHasher.hashString(cardNumber);
            String cardPinHash = PasswordHasher.hashString(cardPin);

            Optional<PaymentCard> paymentCardOptional = paymentCardDao.findByCardNumberAndPin(cardNumberHash, cardPinHash);
            if (!paymentCardOptional.isEmpty()) {
                PaymentCard paymentCard = paymentCardOptional.get();
                if (paymentCard.getCardStatus().equals(PaymentCard.CardStatus.NOT_USED)) {

                    LocalDateTime localDateTimeNow = LocalDateTime.now();
                    paymentCard.setCardStatus(PaymentCard.CardStatus.USED);
                    paymentCard.setActivationDate(localDateTimeNow);

                    UserAction userAction = UserAction.builder().
                            setActionType(UserAction.ActionType.CARD_ACTIVATE)
                            .setDateTime(localDateTimeNow).build();

                    AccountTransaction accountTransaction = AccountTransaction.builder()
                            .setSum(paymentCard.getAmount())
                            .setDate(localDateTimeNow)
                            .setUserId(userId)
                            .setType(TransactionType.REFILL).build();

                    newUserBalance = userBalance.add(paymentCard.getAmount());

                    userActionDao.add(userAction, userId, tariffId);
                    accountTransactionDao.add(accountTransaction);
                    paymentCardDao.activateCard(paymentCard.getCardId(), userId, localDateTimeNow);
                    userDao.balanceReplenishment(userId, newUserBalance);

                    transactionManager.commit();
                } else {
                    throw new ServiceException("Balance has not been replenished.");
                }
            } else {
                throw new ServiceException("Balance has not been replenished.");
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Balance has not been replenished: {}", e);
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                logger.log(Level.ERROR, "Rollback error: {}", e1);
                throw new ServiceException("Rollback error.", e);
            }
            throw new ServiceException("Balance has not been replenished.", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
        return newUserBalance;
    }

    @Override
    public Optional<TransferObject> addUser(String firstName,
                                            String lastName,
                                            String patronymic,
                                            String contractDate,
                                            String tariffId,
                                            String email) throws ServiceException {

        InputDataValidator inputDataValidator = InputDataValidator.getInstance();
        if (inputDataValidator.isFirstNameValid(firstName) &&
                inputDataValidator.isLastNameValid(lastName) &&
                inputDataValidator.isPatronymic(patronymic) &&
                inputDataValidator.isEmailValid(email)) {

            PasswordGenerator strGenerator = new PasswordGenerator.Builder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();

            String password = strGenerator.generate(PASSWORD_LENGTH);
            String passwordHash = PasswordHasher.hashString(password);

            LocalDate contractDateLocalDate = LocalDate.parse(contractDate, dateTimeFormatter);
            LocalDateTime contractDateLocalDateTime = contractDateLocalDate.atStartOfDay();
            try {
                transactionManager.startTransaction();

                Optional<Tariff> optionalTariff = tariffDao.findById(Long.valueOf(tariffId));
                Tariff tariff = optionalTariff.get();

                User user = User.builder()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setPatronymic(patronymic)
                        .setContractDate(contractDateLocalDateTime)
                        .setBalance(new BigDecimal(0))
                        .setTraffic(tariff.getTraffic())
                        .setEmail(email)
                        .setTariff(tariff)
                        .setRole(UserRole.USER)
                        .setStatus(UserStatus.WAIT_ACTIVATE)
                        .setTariffId(Long.valueOf(tariffId)).build();

                long userId = userDao.add(user, passwordHash, UniqueStringGenerator.generationUniqueString());
                user.setUserId(userId);

                String contractNumberAndUserName = new StringBuilder()
                        .append(contractDateLocalDateTime.getYear())
                        .append(String.format("%0" + CONTRACT_LENGTH + "d", userId))
                        .toString();

                userDao.updateContractNumberAndUserName(userId, contractNumberAndUserName, contractNumberAndUserName);
                user.setContractNumber(contractNumberAndUserName);
                user.setName(contractNumberAndUserName);

                TransferObject newUser = new TransferObject();
                newUser.setUser(user);
                newUser.setPassword(password);

                transactionManager.commit();
                return Optional.of(newUser);
            } catch (DaoException e) {
                logger.log(Level.ERROR, "User has not added: {}", e);
                try {
                    transactionManager.rollback();
                } catch (DaoException e1) {
                    logger.log(Level.ERROR, "Rollback error: {}", e1);
                    throw new ServiceException("Rollback error.", e);
                }
                throw new ServiceException("User has not added.", e);
            } finally {
                try {
                    transactionManager.endTransaction();
                } catch (DaoException e) {
                    logger.log(Level.ERROR, "End transaction error: {}", e);
                    throw new ServiceException("End transaction error", e);
                }
            }
        }
        return Optional.empty();
    }
}
