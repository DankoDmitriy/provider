package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.UserService;
import com.danko.provider.util.StringHasher;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.ParamName.LOGIN_FORM_NAME;
import static com.danko.provider.controller.command.ParamName.LOGIN_FORM_PASSWORD;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_NEXT_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_USER_ROLE;

public class UserServiceImplTest {

    @DataProvider(name = "find_page_by_user_role_positive_test_data_provider")
    public Object[][] findPageByUserRolePositiveTestDataProvider() {
        return new Object[][]{
                {
                        new String[]{"USER"},
                        new String[]{"0"},
                        new ArrayList<>(Arrays.asList(User.builder().setUserId(1))),
                        0,
                        1,
                        5
                }
        };
    }

    @Test(dataProvider = "find_page_by_user_role_positive_test_data_provider")
    public void findPageByUserRolePositiveTest(
            String[] userRoleStr,
            String[] strNextPageStr,
            List<User> users,
            long startPosition,
            long rowsInTable,
            long rowsOnPage

    ) throws Exception {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TariffDao tariffDaoMock = Mockito.mock(TariffDao.class);
        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        AccountTransactionDao accountTransactionDaoMock = Mockito.mock(AccountTransactionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);

        UserService userService = new UserServiceImpl(userDaoMock,
                tariffDaoMock,
                userActionDaoMock,
                paymentCardDaoMock,
                accountTransactionDaoMock,
                transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(PAGINATION_USER_ROLE)).thenReturn(userRoleStr);
        Mockito.when(contentMock.getRequestParameter(PAGINATION_NEXT_PAGE)).thenReturn(strNextPageStr);
        Mockito.when(userDaoMock.rowsInTableByUserRole(UserRole.valueOf(userRoleStr[0].toUpperCase()))).thenReturn(rowsInTable);
        Mockito.when(userDaoMock.findAllByUserRolePageLimit(UserRole.valueOf(userRoleStr[0].toUpperCase()), startPosition, rowsInTable)).thenReturn(users);


        userService.findPageByUserRole(contentMock, rowsOnPage);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        Mockito.verify(contentMock, Mockito.times(4)).putRequestAttribute(Mockito.anyString(), Mockito.anyObject());
        Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(ADMIN_USERS_LIST_PAGE);

    }

    @DataProvider(name = "find_page_by_user_role_negative_test_data_provider")
    public Object[][] findPageByUserRoleNegativeTestDataProvider() {
        return new Object[][]{
                {
                        new String[]{"USERasd"},
                        new String[]{"0"},
                        5
                }
        };
    }

    @Test(dataProvider = "find_page_by_user_role_negative_test_data_provider")
    public void findPageByUserRoleNegativeTest(
            String[] userRoleStr,
            String[] strNextPageStr,
            long rowsOnPage

    ) throws Exception {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TariffDao tariffDaoMock = Mockito.mock(TariffDao.class);
        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        AccountTransactionDao accountTransactionDaoMock = Mockito.mock(AccountTransactionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);

        UserService userService = new UserServiceImpl(userDaoMock,
                tariffDaoMock,
                userActionDaoMock,
                paymentCardDaoMock,
                accountTransactionDaoMock,
                transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(PAGINATION_USER_ROLE)).thenReturn(userRoleStr);
        Mockito.when(contentMock.getRequestParameter(PAGINATION_NEXT_PAGE)).thenReturn(strNextPageStr);

        userService.findPageByUserRole(contentMock, rowsOnPage);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
    }

    @DataProvider(name = "login_positive_test_data_provider")
    public Object[][] loginPositiveTestDataProvider() {
        return new Object[][]{
                {
                        new String[]{"202100000000028"},
                        new String[]{"1"},
                        Optional.of(User.builder().setUserId(1).setTariffId(1).build()),
                        Optional.of(Tariff.builder().setTariffId(1).setDescription("Base1").build()),
                        1
                },
                {
                        new String[]{"202100000000028"},
                        new String[]{"1"},
                        Optional.empty(),
                        Optional.empty(),
                        1
                }
        };
    }

    @Test(dataProvider = "login_positive_test_data_provider")
    public void loginPositiveAndNegativeAccessTest(
            String[] name,
            String[] password,
            Optional<User> optionalUser,
            Optional<Tariff> optionalTariff,
            long tariffId
    ) throws Exception {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TariffDao tariffDaoMock = Mockito.mock(TariffDao.class);
        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        AccountTransactionDao accountTransactionDaoMock = Mockito.mock(AccountTransactionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);
        UserService userService = new UserServiceImpl(userDaoMock,
                tariffDaoMock,
                userActionDaoMock,
                paymentCardDaoMock,
                accountTransactionDaoMock,
                transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(LOGIN_FORM_NAME)).thenReturn(name);
        Mockito.when(contentMock.getRequestParameter(LOGIN_FORM_PASSWORD)).thenReturn(password);
        Mockito.when(userDaoMock.findByNameAndPassword(name[0], StringHasher.hashString(password[0]))).thenReturn(optionalUser);
        Mockito.when(tariffDaoMock.findById(tariffId)).thenReturn(optionalTariff);

        userService.login(contentMock);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();

        if (!optionalUser.isEmpty()) {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(HOME_PAGE);
        } else {
            Mockito.verify(contentMock, Mockito.times(1)).setRedirect(true);
        }
    }

    @DataProvider(name = "login_incorrect_input_data_data_provider")
    public Object[][] loginIncorrectInputDataDataProvider() {
        return new Object[][]{
                {
                        new String[]{"202100000000028"},
                        null

                },
                {
                        null,
                        new String[]{"1"}
                },
                {
                        null,
                        null
                }
        };
    }

    @Test(dataProvider = "login_incorrect_input_data_data_provider")
    public void loginIncorrectInputDataTest(
            String[] name,
            String[] password
    ) throws Exception {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        TariffDao tariffDaoMock = Mockito.mock(TariffDao.class);
        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        PaymentCardDao paymentCardDaoMock = Mockito.mock(PaymentCardDao.class);
        AccountTransactionDao accountTransactionDaoMock = Mockito.mock(AccountTransactionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);
        UserService userService = new UserServiceImpl(userDaoMock,
                tariffDaoMock,
                userActionDaoMock,
                paymentCardDaoMock,
                accountTransactionDaoMock,
                transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(LOGIN_FORM_NAME)).thenReturn(name);
        Mockito.when(contentMock.getRequestParameter(LOGIN_FORM_PASSWORD)).thenReturn(password);

        userService.login(contentMock);
        Mockito.verify(transactionManagerMock, Mockito.times(0)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
    }
}

