package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.dao.*;
import com.danko.provider.domain.entity.*;
import com.danko.provider.domain.service.EmailService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.util.PasswordGenerator;
import com.danko.provider.util.PasswordHasher;
import com.danko.provider.util.UniqueStringGenerator;
import com.danko.provider.util.UrlUtil;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final int CONTRACT_LENGTH = 11;
    private static final int PASSWORD_LENGTH = 11;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private final UserDao userDao;
    private final TariffDao tariffDao;
    private final UserActionDao userActionDao;
    private final PaymentCardDao paymentCardDao;
    private final AccountTransactionDao accountTransactionDao;
    private final TransactionManager transactionManager;
    private final InputDataValidator inputDataValidator;


    public UserServiceImpl(UserDao userDao, TariffDao tariffDao, UserActionDao userActionDao, PaymentCardDao paymentCardDao, AccountTransactionDao accountTransactionDao, TransactionManager transactionManager) {
        this.userDao = userDao;
        this.tariffDao = tariffDao;
        this.userActionDao = userActionDao;
        this.paymentCardDao = paymentCardDao;
        this.accountTransactionDao = accountTransactionDao;
        this.transactionManager = transactionManager;
        this.inputDataValidator = InputDataValidator.getInstance();
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userDao.findAll();
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public void findPageByUserRole(InputContent content, long rowsOnPage) throws ServiceException {
        List<User> users;
        long previewPage;
        long rowsInTable;
        long nextPage;
        long startPosition;
        String userRoleStr = content.getRequestParameter(PAGINATION_USER_ROLE)[0];
        String nextPageStr = content.getRequestParameter(PAGINATION_NEXT_PAGE)[0];
        try {
            try {
                transactionManager.startTransaction();
                if (inputDataValidator.isUserRoleValid(userRoleStr.toUpperCase())) {
                    nextPage = Long.parseLong(nextPageStr);
                    if (nextPage <= 0) {
                        rowsInTable = userDao.rowsInTableByUserRole(UserRole.valueOf(userRoleStr.toUpperCase()));
                        nextPage = 0;
                        previewPage = -1;
                        startPosition = nextPage * rowsOnPage;
                        users = userDao.findAllByUserRolePageLimit(UserRole.valueOf(userRoleStr.toUpperCase()), startPosition, rowsOnPage);
                        if (rowsInTable > rowsOnPage) {
                            nextPage++;
                        }
                    } else {
                        rowsInTable = userDao.rowsInTableByUserRole(UserRole.valueOf(userRoleStr.toUpperCase()));
                        startPosition = nextPage * rowsOnPage;
                        users = userDao.findAllByUserRolePageLimit(UserRole.valueOf(userRoleStr.toUpperCase()), startPosition, rowsOnPage);
                        if (rowsInTable > (rowsOnPage * (nextPage + 1))) {
                            previewPage = nextPage - 1;
                            nextPage++;
                        } else {
                            previewPage = nextPage - 1;
                        }
                    }
                    content.putRequestAttribute(PAGINATION_NEXT_PAGE, nextPage);
                    content.putRequestAttribute(PAGINATION_PREVIEW_PAGE, previewPage);
                    content.putRequestAttribute(PAGINATION_RESULT_LIST, users);
                    content.putRequestAttribute(PAGINATION_USER_ROLE, userRoleStr);
                    content.setPageUrl(ADMIN_USERS_LIST_PAGE);
                } else {
                    content.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByNameAndPassword(String name, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            try {
                //TODO - ADD CHECK FOR VALIDATION LOGIN AND PASSWORD
                String passwordHash = PasswordHasher.hashString(password);
                transactionManager.startTransaction();
                optionalUser = userDao.findByNameAndPassword(name, passwordHash);
                return optionalUser;
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl, long tariffId) throws ServiceException {
        boolean result = true;
        try {
            try {
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
                    result = false;
                }
                return result;
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }

    }

    @Override
    public boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException {
        boolean result = false;
        try {
            try {
                transactionManager.startTransaction();
                if (userDao.verificationOfActivationCode(activateCode)) {
                    result = userDao.updateActivationCodeStatus(activateCode, userStatus);
                    transactionManager.commit();
                }
                return result;
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }


    @Override
    public boolean verificationOfActivationCode(String activateCode) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userDao.verificationOfActivationCode(activateCode);
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public boolean updateTariffPlan(long userId, long tariffId) throws ServiceException {
        boolean result = true;
        try {
            try {
                transactionManager.startTransaction();
                Optional<Tariff> tariffOptional = tariffDao.findById(tariffId);
                if (!tariffOptional.isEmpty()) {
                    Optional<User> optionalUser = userDao.findById(userId);
                    if (!optionalUser.isEmpty()) {
                        Tariff tariff = tariffOptional.get();
                        User user = optionalUser.get();
                        if (tariff.getStatus().equals(TariffStatus.ACTIVE) & user.getStatus().equals(UserStatus.ACTIVE)) {
                            BigDecimal newUserTraffic = tariff.getTraffic().add(user.getTraffic());
                            BigDecimal newUserBalance = user.getBalance().subtract(tariff.getPrice());

                            LocalDateTime localDateTimeNow = LocalDateTime.now();
                            UserAction userAction = UserAction.builder().
                                    setActionType(UserAction.ActionType.CHANGE_TARIFF)
                                    .setDateTime(localDateTimeNow).build();

                            AccountTransaction accountTransaction = AccountTransaction.builder()
                                    .setSum(tariff.getPrice())
                                    .setDate(localDateTimeNow)
                                    .setUserId(userId)
                                    .setType(TransactionType.WRITE_OFF).build();

                            userDao.updateTariffAndTrafficAndBalanceValue(userId, tariffId, newUserTraffic, newUserBalance);
                            userActionDao.add(userAction, userId, tariffId);
                            accountTransactionDao.add(accountTransaction);
                            transactionManager.commit();
                        }
                    }
                } else {
                    result = false;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
        return result;
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
    public void addUser(InputContent content) throws ServiceException {
        String[] firstName = content.getRequestParameter(USER_ADD_FIRST_NAME);
        String[] lastName = content.getRequestParameter(USER_ADD_LAST_NAME);
        String[] patronymic = content.getRequestParameter(USER_ADD_PATRONYMIC);
        String[] contractDate = content.getRequestParameter(USER_ADD_CONTRACT_DATE);
        String[] tariffId = content.getRequestParameter(USER_ADD_TARIFF_ID);
        String[] email = content.getRequestParameter(USER_ADD_E_MAIL);
        try {
            try {
                if (firstName != null &&
                        lastName != null &&
                        patronymic != null &&
                        contractDate != null &&
                        tariffId != null &&
                        email != null &&
                        inputDataValidator.isFirstNameValid(firstName[0]) &&
                        inputDataValidator.isLastNameValid(lastName[0]) &&
                        inputDataValidator.isPatronymic(patronymic[0]) &&
                        inputDataValidator.isEmailValid(email[0]) &&
                        inputDataValidator.isIdValid(tariffId[0])) {
                    PasswordGenerator strGenerator = new PasswordGenerator.Builder()
                            .useDigits(true)
                            .useLower(true)
                            .useUpper(true)
                            .build();

                    String password = strGenerator.generate(PASSWORD_LENGTH);
                    String passwordHash = PasswordHasher.hashString(password);
                    LocalDateTime contractDateLocalDateTime = LocalDate.parse(contractDate[0], dateTimeFormatter).atStartOfDay();

                    transactionManager.startTransaction();
                    Optional<Tariff> optionalTariff = tariffDao.findById(Long.valueOf(tariffId[0]));
                    Tariff tariff = optionalTariff.get();

                    User user = User.builder()
                            .setFirstName(firstName[0])
                            .setLastName(lastName[0])
                            .setPatronymic(patronymic[0])
                            .setContractDate(contractDateLocalDateTime)
                            .setBalance(new BigDecimal(0).subtract(tariff.getPrice()))
                            .setTraffic(tariff.getTraffic())
                            .setEmail(email[0])
                            .setTariff(tariff)
                            .setRole(UserRole.USER)
                            .setStatus(UserStatus.WAIT_ACTIVATE)
                            .setTariffId(tariff.getTariffId()).build();

                    long userId = userDao.add(user, passwordHash, UniqueStringGenerator.generationUniqueString());
                    user.setUserId(userId);

                    String contractNumberAndUserName = new StringBuilder()
                            .append(contractDateLocalDateTime.getYear())
                            .append(String.format("%0" + CONTRACT_LENGTH + "d", userId))
                            .toString();

                    userDao.updateContractNumberAndUserName(userId, contractNumberAndUserName, contractNumberAndUserName);
                    user.setContractNumber(contractNumberAndUserName);
                    user.setName(contractNumberAndUserName);

                    UserAction userAction = UserAction.builder().
                            setActionType(UserAction.ActionType.CHANGE_TARIFF)
                            .setDateTime(contractDateLocalDateTime).build();

                    AccountTransaction accountTransaction = AccountTransaction.builder()
                            .setSum(tariff.getPrice())
                            .setDate(contractDateLocalDateTime)
                            .setUserId(userId)
                            .setType(TransactionType.WRITE_OFF).build();

                    userActionDao.add(userAction, userId, tariff.getTariffId());
                    accountTransactionDao.add(accountTransaction);

                    TransferObject newUser = new TransferObject();
                    newUser.setUser(user);
                    newUser.setPassword(password);
                    transactionManager.commit();
                    content.putRequestAttribute(ADMIN_NEW_USER_CARD_TRANSFER_OBJECT, newUser);
                    content.setPageUrl(ADMIN_USER_ADD_CARD);
                } else {
                    transactionManager.startTransaction();
                    content.setPageUrl(ADMIN_USER_ADD_PAGE);
                    List<Tariff> tariffs = tariffDao.findAllByStatus(TariffStatus.ACTIVE);
                    content.putRequestAttribute(ADMIN_TARIFFS_LIST_FOR_NEW_USER, tariffs);
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e1) {
                throw new ServiceException(e1);
            }
        }
    }

    @Override
    public void updateUserPersonalData(InputContent content) throws ServiceException {
        String[] firstName = content.getRequestParameter(USER_EDIT_FIRST_NAME);
        String[] lastName = content.getRequestParameter(USER_EDIT_LAST_NAME);
        String[] patronymic = content.getRequestParameter(USER_EDIT_PATRONYMIC);
        String[] email = content.getRequestParameter(USER_EDIT_E_MAIL);
        String[] userIdStr = content.getRequestParameter(USER_EDIT_ID);
        String[] userOriginStr = content.getRequestParameter(USER_EDIT_ORIGIN);
        User user = null;
        try {
            try {
                if (firstName != null &&
                        lastName != null &&
                        patronymic != null &&
                        email != null &&
                        userIdStr != null &&
                        userOriginStr != null &&
                        inputDataValidator.isFirstNameValid(firstName[0]) &&
                        inputDataValidator.isLastNameValid(lastName[0]) &&
                        inputDataValidator.isPatronymic(patronymic[0]) &&
                        inputDataValidator.isEmailValid(email[0]) &&
                        inputDataValidator.isIdValid(userIdStr[0])) {
                    long userId = Long.parseLong(userIdStr[0]);
                    user = stringToObjectUser(userOriginStr[0]);
                    if (userId == user.getUserId()) {
                        transactionManager.startTransaction();
                        if (!user.getFirstName().equals(firstName[0])) {
                            userDao.updateFirstName(userId, firstName[0]);
                            user.setFirstName(firstName[0]);
                        }
                        if (!user.getLastName().equals(lastName[0])) {
                            userDao.updateLastName(userId, lastName[0]);
                            user.setLastName(lastName[0]);
                        }
                        if (!user.getPatronymic().equals(patronymic[0])) {
                            userDao.updatePatronymic(userId, patronymic[0]);
                            user.setPatronymic(patronymic[0]);
                        }
                        if (!user.getEmail().equals(email[0])) {
                            userDao.updateEmail(userId, email[0]);
                            user.setEmail(email[0]);
                        }
                        transactionManager.commit();
                        content.putRequestAttribute(ADMIN_USERS_LIST, Arrays.asList(user));
                        content.putRequestAttribute(ADMIN_USERS_LIST_RESULT_WORK_FOR_MESSAGE, true);
                        content.setPageUrl(ADMIN_USERS_LIST_PAGE);
                    } else {
//              FIXME - тут, если подмени ID пользователя
                        content.putRequestAttribute(ADMIN_USERS_LIST, Arrays.asList(user));
                        content.putRequestAttribute(ADMIN_USERS_LIST_RESULT_WORK_FOR_MESSAGE, false);
                        content.setPageUrl(ADMIN_USERS_LIST_PAGE);
                    }
                } else {
                    if (userIdStr != null && inputDataValidator.isIdValid(userIdStr[0])) {
//                FIXME Тут выбираем пользователя для редакирования и необходимые данные для формы
                        transactionManager.startTransaction();
                        Optional<User> optionalUser = userDao.findById(Long.parseLong(userIdStr[0]));
                        if (!optionalUser.isEmpty()) {
                            user = optionalUser.get();
                            user.setTariff(tariffDao.findById(user.getTariffId()).get());
                            content.putRequestAttribute(ADMIN_USER_EDIT_ORIGINAL, objectUserToString(user));
                            content.putRequestAttribute(ADMIN_USER_EDIT, user);
                            content.setPageUrl(ADMIN_USER_EDIT_PAGE);
                        } else {
//                   FIXME Тут если Пользователя нет. отправляем на страницу пользователей.
                            content.setRedirect(true);
                            content.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
                        }
                    } else {
//                FIXME    Некорректный ID Пользователя на страницу с пользователями.
                        content.setRedirect(true);
                        content.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
                    }
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public boolean blockOrUnblock(long userId) throws ServiceException {
        boolean result = true;
        try {
            try {
                transactionManager.startTransaction();
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    User user = optionalUser.get();
                    UserStatus userStatus = user.getStatus();
                    switch (userStatus) {
                        case ACTIVE, WAIT_ACTIVATE: {
                            result = userDao.updateStatus(userId, UserStatus.BLOCK);
                            break;
                        }
                        case BLOCK: {
                            result = userDao.updateStatus(userId, UserStatus.WAIT_ACTIVATE);
                            break;
                        }
                        default: {
                            result = false;
                        }
                    }
                    Tariff tariff = tariffDao.findById(user.getTariffId()).get();
                    UserAction userAction = UserAction.builder()
                            .setDateTime(LocalDateTime.now())
                            .setActionType(UserAction.ActionType.CHANGE_STATUS)
                            .setTariffName(tariff.getDescription()).build();
                    userActionDao.add(userAction, userId, tariff.getTariffId());
                    transactionManager.commit();
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
        return result;
    }

    @Override
    public boolean changeRole(long userId) throws ServiceException {
        boolean result = true;
        try {
            try {
                transactionManager.startTransaction();
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    User user = optionalUser.get();
                    UserRole userRole = user.getRole();
                    switch (userRole) {
                        case USER: {
                            result = userDao.updateRole(userId, UserRole.ADMIN);
                            break;
                        }
                        case ADMIN: {
                            result = userDao.updateRole(userId, UserRole.USER);
                            break;
                        }
                        default: {
                            result = false;
                        }
                    }
                    Tariff tariff = tariffDao.findById(user.getTariffId()).get();
                    UserAction userAction = UserAction.builder()
                            .setDateTime(LocalDateTime.now())
                            .setActionType(UserAction.ActionType.CHANGE_ROLE)
                            .setTariffName(tariff.getDescription()).build();
                    userActionDao.add(userAction, userId, tariff.getTariffId());
                    transactionManager.commit();
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
        return result;
    }

    private String objectUserToString(User user) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }

    private User stringToObjectUser(String userStr) {
        try {
            byte[] objToBytes = Base64.getDecoder().decode(userStr);
            ByteArrayInputStream bais = new ByteArrayInputStream(objToBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }
}
