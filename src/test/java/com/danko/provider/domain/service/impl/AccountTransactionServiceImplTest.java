package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.TransactionType;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.exception.DaoException;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_FINANCES_OPERATION_PAGE;
import static com.danko.provider.controller.command.PageUrl.USER_ALL_FINANCE_OPERATIONS_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_NEXT_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_USER_ID;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;
import static org.testng.Assert.assertEquals;

public class AccountTransactionServiceImplTest {

    @DataProvider(name = "find_all_by_user_id_limit_data_provider")
    public Object[][] findAllByUserIdLimitProvider() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList(AccountTransaction.builder()
                        .setType(TransactionType.REFILL)
                        .setDate(LocalDateTime.now())
                        .setSum(new BigDecimal(10.00))
                        .setTransactionId(1)
                        .setUserId(1)
                        .build())), 1, true},
                {new ArrayList<>(), 2, true}
        };
    }

    @Test(dataProvider = "find_all_by_user_id_limit_data_provider")
    public void findAllByUserIdLimitTest(List transactionList, long id, boolean expected) throws Exception {
        AccountTransactionDao accountTransactionDaoMock = Mockito.mock(AccountTransactionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);

        Mockito.when(accountTransactionDaoMock.findAllByUserIdLimit(id)).thenReturn(transactionList);
        AccountTransactionService transactionService = new AccountTransactionServiceImpl(accountTransactionDaoMock,
                transactionManagerMock);

        List<AccountTransaction> result = transactionService.findAllByUserIdLimit(id);
        assertEquals(result.equals(transactionList), expected);
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
                        new ArrayList<>(Arrays.asList(AccountTransaction.builder()
                                .setType(TransactionType.REFILL)
                                .setDate(LocalDateTime.now())
                                .setSum(new BigDecimal(10.00))
                                .setTransactionId(1)
                                .setUserId(1)
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
                        new ArrayList<>(Arrays.asList(AccountTransaction.builder()
                                .setType(TransactionType.REFILL)
                                .setDate(LocalDateTime.now())
                                .setSum(new BigDecimal(10.00))
                                .setTransactionId(1)
                                .setUserId(1)
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
                        new ArrayList<>(Arrays.asList(AccountTransaction.builder()
                                .setType(TransactionType.REFILL)
                                .setDate(LocalDateTime.now())
                                .setSum(new BigDecimal(10.00))
                                .setTransactionId(1)
                                .setUserId(1)
                                .build())),
                        1,
                        0,
                        1,
                        5
                }
        };
    }

    @Test(dataProvider = "find_page_by_user_id_data_provider")
    public void findPageByUserIdTest(
            String[] strArrayUserId,
            String[] strNextPageStr,
            User user,
            List<AccountTransaction> transactions,
            long userId,
            long startPosition,
            long rowsInTable,
            long rowsOnPage
    ) throws Exception {
        AccountTransactionDao accountTransactionDaoMock = Mockito.mock(AccountTransactionDao.class);
        TransactionManager transactionManagerMock = Mockito.mock(TransactionManager.class);
        SessionRequestContent contentMock = Mockito.mock(SessionRequestContent.class);

        AccountTransactionService transactionService = new AccountTransactionServiceImpl(accountTransactionDaoMock,
                transactionManagerMock);

        Mockito.when(contentMock.getRequestParameter(PAGINATION_USER_ID)).thenReturn(strArrayUserId);
        Mockito.when(contentMock.getRequestParameter(PAGINATION_NEXT_PAGE)).thenReturn(strNextPageStr);
        Mockito.when(contentMock.getSessionAttribute(SESSION_USER)).thenReturn(user);
        Mockito.when(accountTransactionDaoMock.rowsInTableForUser(userId)).thenReturn(rowsInTable);
        Mockito.when(accountTransactionDaoMock.findAllByUserIdPageLimit(userId, startPosition, rowsInTable)).thenReturn(transactions);

        transactionService.findPageByUserId(contentMock, rowsOnPage);

        Mockito.verify(transactionManagerMock, Mockito.times(1)).startTransaction();
        Mockito.verify(transactionManagerMock, Mockito.times(1)).endTransaction();
        if (user.getRole().equals(UserRole.USER)) {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(USER_ALL_FINANCE_OPERATIONS_PAGE);
        } else {
            Mockito.verify(contentMock, Mockito.times(1)).setPageUrl(ADMIN_USER_FINANCES_OPERATION_PAGE);
        }
        Mockito.verify(contentMock, Mockito.times(4)).putRequestAttribute(Mockito.anyString(), Mockito.anyObject());
    }
}
