package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.util.PaginationCalculate;
import com.danko.provider.validator.InputDataValidator;

import java.util.List;
import java.util.Map;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_FINANCES_OPERATION_PAGE;
import static com.danko.provider.controller.command.PageUrl.HOME_PAGE;
import static com.danko.provider.controller.command.PageUrl.USER_ALL_FINANCE_OPERATIONS_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_NEXT_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_PREVIEW_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_RESULT_LIST;
import static com.danko.provider.controller.command.RequestAttribute.PAGINATION_USER_ID;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class AccountTransactionServiceImpl implements AccountTransactionService {
    private final AccountTransactionDao accountTransactionDao;
    private final TransactionManager transactionManager;
    private final PaginationCalculate paginationCalculate;

    public AccountTransactionServiceImpl(AccountTransactionDao accountTransactionDao,
                                         TransactionManager transactionManager) {
        this.accountTransactionDao = accountTransactionDao;
        this.transactionManager = transactionManager;
        this.paginationCalculate = PaginationCalculate.getInstance();
    }

    @Override
    public List<AccountTransaction> findAllByUserIdLimit(long userId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return accountTransactionDao.findAllByUserIdLimit(userId);
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
    public void findPageByUserId(SessionRequestContent content, long rowsOnPage) throws ServiceException {
        String userIdStr = content.getRequestParameter(PAGINATION_USER_ID)[0];
        String nextPageStr = content.getRequestParameter(PAGINATION_NEXT_PAGE)[0];
        InputDataValidator inputDataValidator = InputDataValidator.getInstance();
        User user = (User) content.getSessionAttribute(SESSION_USER);
        try {
            try {
                transactionManager.startTransaction();
                long userId;
                if (inputDataValidator.isIdValid(userIdStr)) {
                    if (user.getRole().equals(UserRole.USER)) {
                        userId = user.getUserId();
                        content.setPageUrl(USER_ALL_FINANCE_OPERATIONS_PAGE);
                    } else {
                        userId = Long.parseLong(userIdStr);
                        content.setPageUrl(ADMIN_USER_FINANCES_OPERATION_PAGE);
                    }
                    long nextPage = Long.parseLong(nextPageStr);
                    long rowsInTable = accountTransactionDao.rowsInTableForUser(userId);
                    Map<String, Long> calculatedData = paginationCalculate.calculateNextAndPreviewPageValue(
                            nextPage, rowsInTable, rowsOnPage);

                    List<AccountTransaction> transactions = accountTransactionDao.findAllByUserIdPageLimit(
                            userId, calculatedData.get(PaginationCalculate.START_POSITION), rowsOnPage);

                    content.putRequestAttribute(PAGINATION_NEXT_PAGE,
                            calculatedData.get(PaginationCalculate.NEXT_PAGE));
                    content.putRequestAttribute(PAGINATION_PREVIEW_PAGE,
                            calculatedData.get(PaginationCalculate.PREVIEW_PAGE));
                    content.putRequestAttribute(PAGINATION_RESULT_LIST, transactions);
                    content.putRequestAttribute(PAGINATION_USER_ID, userId);
                } else {
                    if (user.getRole().equals(UserRole.USER)) {
                        content.setPageUrl(HOME_PAGE);
                    } else {
                        content.setPageUrl(ADMIN_USERS_LIST_PAGE_REDIRECT);
                    }
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
}
