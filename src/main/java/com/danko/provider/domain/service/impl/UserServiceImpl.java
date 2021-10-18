package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.TransactionType;
import com.danko.provider.domain.entity.TransferObject;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.domain.service.EmailService;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.util.PaginationCalculate;
import com.danko.provider.util.PasswordGenerator;
import com.danko.provider.util.SearchUserSqlGeneration;
import com.danko.provider.util.StringHasher;
import com.danko.provider.util.UniqueStringGenerator;
import com.danko.provider.util.UrlUtil;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_ADD_CARD;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_ADD_PAGE;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_EDIT_PAGE;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_SEARCH;
import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.PageUrl.START_PAGE;
import static com.danko.provider.controller.command.PageUrl.USER_ACTIVATE_PAYMENT_CARD;
import static com.danko.provider.controller.command.ParamName.CARD_NUMBER;
import static com.danko.provider.controller.command.ParamName.CARD_PIN;
import static com.danko.provider.controller.command.ParamName.COMMAND;
import static com.danko.provider.controller.command.ParamName.LOGIN_FORM_NAME;
import static com.danko.provider.controller.command.ParamName.LOGIN_FORM_PASSWORD;
import static com.danko.provider.controller.command.ParamName.USER_ADD_CONTRACT_DATE;
import static com.danko.provider.controller.command.ParamName.USER_ADD_E_MAIL;
import static com.danko.provider.controller.command.ParamName.USER_ADD_FIRST_NAME;
import static com.danko.provider.controller.command.ParamName.USER_ADD_LAST_NAME;
import static com.danko.provider.controller.command.ParamName.USER_ADD_PATRONYMIC;
import static com.danko.provider.controller.command.ParamName.USER_ADD_TARIFF_ID;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_E_MAIL;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_FIRST_NAME;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_ID;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_LAST_NAME;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_ORIGIN;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_PATRONYMIC;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_NEW_USER_CARD_TRANSFER_OBJECT;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_TARIFFS_LIST_FOR_NEW_USER;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_USERS_LIST;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_USERS_LIST_RESULT_WORK_FOR_MESSAGE;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_USER_EDIT;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_USER_EDIT_ORIGINAL;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_NEXT_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_PREVIEW_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_RESULT_LIST;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_USER_ROLE;
import static com.danko.provider.controller.command.RequestAttribute.USER_RESULT_ACTION;
import static com.danko.provider.controller.command.SessionAttribute.IS_LOGIN_ERROR;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final int CONTRACT_LENGTH = 11;
    private static final int PASSWORD_LENGTH = 11;
    private static final int PAYMENT_CARD_PIN_LENGTH = 8;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private final UserDao userDao;
    private final TariffDao tariffDao;
    private final UserActionDao userActionDao;
    private final PaymentCardDao paymentCardDao;
    private final AccountTransactionDao accountTransactionDao;
    private final TransactionManager transactionManager;
    private final InputDataValidator inputDataValidator;
    private final PaginationCalculate paginationCalculate;
    private final SearchUserSqlGeneration searchUserSqlGeneration;


    public UserServiceImpl(UserDao userDao,
                           TariffDao tariffDao,
                           UserActionDao userActionDao,
                           PaymentCardDao paymentCardDao,
                           AccountTransactionDao accountTransactionDao,
                           TransactionManager transactionManager) {
        this.userDao = userDao;
        this.tariffDao = tariffDao;
        this.userActionDao = userActionDao;
        this.paymentCardDao = paymentCardDao;
        this.accountTransactionDao = accountTransactionDao;
        this.transactionManager = transactionManager;
        this.inputDataValidator = InputDataValidator.getInstance();
        this.paginationCalculate = PaginationCalculate.getInstance();
        this.searchUserSqlGeneration = SearchUserSqlGeneration.getInstance();
    }

    @Override
    public void findPageByUserRole(SessionRequestContent content, long rowsOnPage) throws ServiceException {
        String userRoleStr = content.getRequestParameter(PAGINATION_USER_ROLE)[0];
        String nextPageStr = content.getRequestParameter(PAGINATION_NEXT_PAGE)[0];
        try {
            try {
                transactionManager.startTransaction();
                if (inputDataValidator.isUserRoleValid(userRoleStr.toUpperCase())) {
                    long nextPage = Long.parseLong(nextPageStr);

                    long rowsInTable = userDao.rowsInTableByUserRole(UserRole.valueOf(userRoleStr.toUpperCase()));
                    Map<String, Long> calculatedData = paginationCalculate.calculateNextAndPreviewPageValue(
                            nextPage, rowsInTable, rowsOnPage);

                    List<User> users = userDao.findAllByUserRolePageLimit(
                            UserRole.valueOf(userRoleStr.toUpperCase()),
                            calculatedData.get(PaginationCalculate.START_POSITION),
                            rowsOnPage);

                    content.putRequestAttribute(PAGINATION_NEXT_PAGE,
                            calculatedData.get(PaginationCalculate.NEXT_PAGE));
                    content.putRequestAttribute(PAGINATION_PREVIEW_PAGE,
                            calculatedData.get(PaginationCalculate.PREVIEW_PAGE));
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
    public void login(SessionRequestContent content) throws ServiceException {
        String[] name = content.getRequestParameter(LOGIN_FORM_NAME);
        String[] password = content.getRequestParameter(LOGIN_FORM_PASSWORD);
        try {
            try {
                if (name != null && password != null) {
                    String passwordHash = StringHasher.hashString(password[0]);
                    transactionManager.startTransaction();
                    Optional<User> optionalUser = userDao.findByNameAndPassword(name[0], passwordHash);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        Optional<Tariff> tariffOptional = tariffDao.findById(user.getTariffId());
                        user.setTariff(tariffOptional.get());
                        content.putSessionAttribute(SESSION_USER, user);
                        content.putSessionAttribute(IS_LOGIN_ERROR, false);
                        content.setPageUrl(HOME_PAGE);
                    } else {
                        content.putSessionAttribute(IS_LOGIN_ERROR, true);
                        content.setRedirect(true);
                        content.setPageUrl(START_PAGE);
                    }
                }
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
    public boolean updatePassword(long userId,
                                  String password,
                                  String email,
                                  String contextPath,
                                  String requestUrl,
                                  long tariffId) throws ServiceException {
        boolean result = true;
        try {
            try {
                if (password != null && inputDataValidator.isPasswordValid(password)) {
                    transactionManager.startTransaction();

                    String passwordHash = StringHasher.hashString(password);
                    String newActivateCode = UniqueStringGenerator.generationUniqueString();
                    result = userDao.updatePassword(userId, passwordHash, newActivateCode, UserStatus.WAIT_ACTIVATE);

                    UserAction userAction = UserAction.builder().
                            setActionType(UserAction.ActionType.CHANGE_PASSWORD)
                            .setDateTime(LocalDateTime.now()).build();
                    userActionDao.add(userAction, userId, tariffId);
                    transactionManager.commit();

                    EmailService emailService = ServiceProvider.getInstance().getEmailService();
                    String domain = UrlUtil.requestUrlToDomain(requestUrl) + contextPath;
                    emailService.sendActivateMail(email, domain, newActivateCode);
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
    public void updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                if (userDao.verificationOfActivationCode(activateCode)) {
                    userDao.updateActivationCodeStatus(activateCode, userStatus);
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
    public void activatePaymentCard(SessionRequestContent content) throws ServiceException {
        User user = (User) content.getSessionAttribute(SESSION_USER);
        String[] cardNumber = content.getRequestParameter(CARD_NUMBER);
        String[] cardPin = content.getRequestParameter(CARD_PIN);
        try {
            try {
                transactionManager.startTransaction();
                if (cardNumber != null &&
                        cardPin != null &&
                        inputDataValidator.isPaymentCardNumberValid(cardNumber[0]) &&
                        cardPin[0].length() == PAYMENT_CARD_PIN_LENGTH) {
                    String cardNumberHash = StringHasher.hashString(cardNumber[0]);
                    String cardPinHash = StringHasher.hashString(cardPin[0]);
                    Optional<PaymentCard> paymentCardOptional = paymentCardDao.findByCardNumberAndPin(cardNumberHash, cardPinHash);
                    if (!paymentCardOptional.isEmpty() &&
                            paymentCardOptional.get().getCardStatus().equals(PaymentCard.CardStatus.NOT_USED)) {

                        PaymentCard paymentCard = paymentCardOptional.get();
                        LocalDateTime localDateTimeNow = LocalDateTime.now();
                        paymentCard.setCardStatus(PaymentCard.CardStatus.USED);
                        paymentCard.setActivationDate(localDateTimeNow);

                        UserAction userAction = UserAction.builder().
                                setActionType(UserAction.ActionType.CARD_ACTIVATE)
                                .setDateTime(localDateTimeNow).build();

                        AccountTransaction accountTransaction = AccountTransaction.builder()
                                .setSum(paymentCard.getAmount())
                                .setDate(localDateTimeNow)
                                .setUserId(user.getUserId())
                                .setType(TransactionType.REFILL).build();

                        BigDecimal newUserBalance = user.getBalance().add(paymentCard.getAmount());

                        userActionDao.add(userAction, user.getUserId(), user.getTariffId());
                        accountTransactionDao.add(accountTransaction);
                        paymentCardDao.activateCard(paymentCard.getCardId(), user.getUserId(), localDateTimeNow);
                        userDao.balanceReplenishment(user.getUserId(), newUserBalance);
                        transactionManager.commit();
                        user.setBalance(newUserBalance);
                        content.putSessionAttribute(SESSION_USER, user);
                        content.setPageUrl(HOME_PAGE);

                    } else {
                        content.putRequestAttribute(USER_RESULT_ACTION, false);
                        content.setPageUrl(USER_ACTIVATE_PAYMENT_CARD);
                    }
                } else {
                    content.putRequestAttribute(USER_RESULT_ACTION, true);
                    content.setPageUrl(USER_ACTIVATE_PAYMENT_CARD);
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
    public void addUser(SessionRequestContent content) throws ServiceException {
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
                        inputDataValidator.isPatronymicValid(patronymic[0]) &&
                        inputDataValidator.isEmailValid(email[0]) &&
                        inputDataValidator.isIdValid(tariffId[0])) {
                    PasswordGenerator strGenerator = new PasswordGenerator.Builder()
                            .useDigits(true)
                            .useLower(true)
                            .useUpper(true)
                            .build();

                    String password = strGenerator.generate(PASSWORD_LENGTH);
                    String passwordHash = StringHasher.hashString(password);
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
    public void updateUserPersonalData(SessionRequestContent content) throws ServiceException {
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
                        inputDataValidator.isPatronymicValid(patronymic[0]) &&
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
//              TODO - if spoofing user ID
                        content.putRequestAttribute(ADMIN_USERS_LIST, Arrays.asList(user));
                        content.putRequestAttribute(ADMIN_USERS_LIST_RESULT_WORK_FOR_MESSAGE, false);
                        content.setPageUrl(ADMIN_USERS_LIST_PAGE);
                    }
                } else {
                    if (userIdStr != null && inputDataValidator.isIdValid(userIdStr[0])) {
//                TODO select the user for editing and the necessary data for the form
                        transactionManager.startTransaction();
                        Optional<User> optionalUser = userDao.findById(Long.parseLong(userIdStr[0]));
                        if (!optionalUser.isEmpty()) {
                            user = optionalUser.get();
                            user.setTariff(tariffDao.findById(user.getTariffId()).get());
                            content.putRequestAttribute(ADMIN_USER_EDIT_ORIGINAL, objectUserToString(user));
                            content.putRequestAttribute(ADMIN_USER_EDIT, user);
                            content.setPageUrl(ADMIN_USER_EDIT_PAGE);
                        } else {
//                   TODO Here if there is no User. send to the users page.
                            content.setRedirect(true);
                            content.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
                        }
                    } else {
//                TODO    Invalid User ID for the page with users.
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
    public void blockOrUnblockUser(long userId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    User user = optionalUser.get();
                    UserStatus userStatus = user.getStatus();
                    switch (userStatus) {
                        case ACTIVE, WAIT_ACTIVATE: {
                            userDao.updateStatus(userId, UserStatus.BLOCK);
                            break;
                        }
                        case BLOCK: {
                            userDao.updateStatus(userId, UserStatus.WAIT_ACTIVATE);
                            break;
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
    }

    @Override
    public void changeUserRole(long userId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                Optional<User> optionalUser = userDao.findById(userId);
                if (!optionalUser.isEmpty()) {
                    User user = optionalUser.get();
                    UserRole userRole = user.getRole();
                    switch (userRole) {
                        case USER: {
                            userDao.updateRole(userId, UserRole.ADMIN);
                            break;
                        }
                        case ADMIN: {
                            userDao.updateRole(userId, UserRole.USER);
                            break;
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
    }

    @Override
    public void searchUsers(SessionRequestContent content) throws ServiceException {
        if (content.getRequestParameters().size() == 1 &&
                content.getRequestParameters().containsKey(COMMAND)) {
            content.setPageUrl(ADMIN_USER_SEARCH);
        } else {
            String criteriaForDao = searchUserSqlGeneration.generateSearchSqlCriteria(content.getRequestParameters());
            System.out.println(criteriaForDao);
            if (criteriaForDao != null) {
                try {
                    try {
                        transactionManager.startTransaction();
                        List<User> users = userDao.searchUsersByParameters(criteriaForDao);
                        content.putRequestAttribute(PAGINATION_RESULT_LIST, users);
                        content.setPageUrl(ADMIN_USERS_LIST_PAGE);
                    } catch (DaoException e) {
                        throw new ServiceException(e);
                    } finally {
                        transactionManager.endTransaction();
                    }
                } catch (DaoException | ServiceException e1) {
                    throw new ServiceException(e1);
                }
            } else {
                content.setPageUrl(ADMIN_USER_SEARCH);
            }
        }
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
