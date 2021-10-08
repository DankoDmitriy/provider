package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.UserActionService;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_ACTIONS_PAGE;
import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.PageUrl.USER_ACTIONS_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_NEXT_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_USER_ID;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class UserActionServiceImplTest {
    @DataProvider(name = "find_all_by_user_id_limit_data_provider")
    public Object[][] findAllByUserIdLimitDataProvider() {
        return new Object[][]{
                {
                        1,
                        new ArrayList<>(Arrays.asList(UserAction.builder().setActionId(1).build()))
                },
                {
                        1,
                        null
                }
        };
    }

    @Test(dataProvider = "find_all_by_user_id_limit_data_provider")
    public void findAllByUserIdLimitTest(long userId, List<UserAction> actions) throws Exception {
        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        UserActionService userActionService = new UserActionServiceImpl(userActionDaoMock, transactionManagerMock);

        Mockito.when(userActionDaoMock.findAllByUserIdLimit(userId)).thenReturn(actions);

        userActionService.findAllByUserIdLimit(userId);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
    }

    @DataProvider(name = "find_page_by_user_id_data_provider")
    public Object[][] findPageByUserIdDataProvider() {
        return new Object[][]{
                {
                        new String[]{"1"},
                        new String[]{"0"},
                        User.builder().setRole(UserRole.USER).setUserId(1).build(),
                        new ArrayList<>(Arrays.asList(UserAction.builder()
                                .setActionType(UserAction.ActionType.CHANGE_TARIFF)
                                .setDateTime(LocalDateTime.now())
                                .setTariffName("Tariff title")
                                .setActionId(1)
                                .build())),
                        1,
                        0,
                        1,
                        5
                },
                {
                        new String[]{"2"},
                        new String[]{"0"},
                        User.builder().setRole(UserRole.USER).setUserId(1).build(),
                        new ArrayList<>(Arrays.asList(UserAction.builder()
                                .setActionType(UserAction.ActionType.CHANGE_TARIFF)
                                .setDateTime(LocalDateTime.now())
                                .setTariffName("Tariff title")
                                .setActionId(1)
                                .build())),
                        1,
                        0,
                        1,
                        5
                },
                {
                        new String[]{"2"},
                        new String[]{"0"},
                        User.builder().setRole(UserRole.ADMIN).setUserId(100).build(),
                        new ArrayList<>(Arrays.asList(UserAction.builder()
                                .setActionType(UserAction.ActionType.CHANGE_TARIFF)
                                .setDateTime(LocalDateTime.now())
                                .setTariffName("Tariff title")
                                .setActionId(1)
                                .build())),
                        1,
                        0,
                        1,
                        5
                }
        };
    }

    @Test(dataProvider = "find_page_by_user_id_data_provider")
    public void findPageByUserIdPositiveTest(
            String[] strArrayUserId,
            String[] strNextPageStr,
            User user,
            List<UserAction> actions,
            long userId,
            long startPosition,
            long rowsInTable,
            long rowsOnPage
    ) throws Exception {
        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);

        UserActionService userActionService = new UserActionServiceImpl(userActionDaoMock, transactionManagerMock);


        Mockito.when(contentMock.getRequestParameter(PAGINATION_USER_ID)).thenReturn(strArrayUserId);
        Mockito.when(contentMock.getRequestParameter(PAGINATION_NEXT_PAGE)).thenReturn(strNextPageStr);
        Mockito.when(contentMock.getSessionAttribute(SESSION_USER)).thenReturn(user);
        Mockito.when(userActionDaoMock.rowsInTableForUser(userId)).thenReturn(rowsInTable);
        Mockito.when(userActionDaoMock.findAllByUserIdPageLimit(userId, startPosition, rowsInTable)).thenReturn(actions);

        userActionService.findPageByUserId(contentMock, rowsOnPage);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        if (user.getRole().equals(UserRole.USER)) {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(USER_ACTIONS_PAGE);
        } else {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(ADMIN_USER_ACTIONS_PAGE);
        }
        Mockito.verify(contentMock, Mockito.times(4)).putRequestAttribute(Mockito.anyString(), Mockito.anyObject());
    }

    @DataProvider(name = "find_page_by_user_id_incorrect_input_data_data_provider")
    public Object[][] findPageByUserIdIncorrectInputDataDataProvider() {
        return new Object[][]{
                {
                        new String[]{"1a"},
                        new String[]{"0"},
                        User.builder().setRole(UserRole.USER).setUserId(1).build(),
                        5
                },
                {
                        new String[]{""},
                        new String[]{"0"},
                        User.builder().setRole(UserRole.USER).setUserId(1).build(),
                        5
                }
        };
    }

    @Test(dataProvider = "find_page_by_user_id_incorrect_input_data_data_provider")
    public void findPageByUserIdNegativeTest(
            String[] strArrayUserId,
            String[] strNextPageStr,
            User user,
            long rowsOnPage
    ) throws Exception {

        UserActionDao userActionDaoMock = Mockito.mock(UserActionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);

        UserActionService userActionService = new UserActionServiceImpl(userActionDaoMock, transactionManagerMock);


        Mockito.when(contentMock.getRequestParameter(PAGINATION_USER_ID)).thenReturn(strArrayUserId);
        Mockito.when(contentMock.getRequestParameter(PAGINATION_NEXT_PAGE)).thenReturn(strNextPageStr);
        Mockito.when(contentMock.getSessionAttribute(SESSION_USER)).thenReturn(user);

        userActionService.findPageByUserId(contentMock, rowsOnPage);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        if (user.getRole().equals(UserRole.USER)) {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(HOME_PAGE);
        } else {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
        }

    }
}
