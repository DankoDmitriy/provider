package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.ADMIN_USERS_LIST_PAGE_REDIRECT;
import static com.danko.provider.controller.command.PageUrl.ADMIN_USER_FINANCES_OPERATION_PAGE;
import static com.danko.provider.controller.command.RequestAttribute.*;

public class AccountTransactionServiceImpl implements AccountTransactionService {
    private static final Logger logger = LogManager.getLogger();
    //    private AccountTransactionDao accountTransactionDao = new AccountTransactionDaoImpl();
    private final AccountTransactionDao accountTransactionDao;
    private final TransactionManager transactionManager;

    public AccountTransactionServiceImpl(AccountTransactionDao accountTransactionDao, TransactionManager transactionManager) {
        this.accountTransactionDao = accountTransactionDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<AccountTransaction> findAll() throws ServiceException {
        try {
            transactionManager.startTransaction();
            return accountTransactionDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all transactions in database: {}", e);
            throw new ServiceException("Could not find all transactions in database.", e);
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
    public List<AccountTransaction> findAllByUserId(long userId) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return accountTransactionDao.findAllByUserId(userId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all transactions in database: {}", e);
            throw new ServiceException("Could not find all transactions in database.", e);
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
    public List<AccountTransaction> findAllByUserIdLimit(long userId) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return accountTransactionDao.findAllByUserIdLimit(userId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all transactions in database: {}", e);
            throw new ServiceException("Could not find all transactions in database.", e);
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
    public Optional<AccountTransaction> findById(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return accountTransactionDao.findById(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find transaction by id: {}", e);
            throw new ServiceException("Can't find transaction by id.", e);
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
    public List<AccountTransaction> findPageByUserId(long userId, long rowsOnPage, long nextPage) throws ServiceException {
        List<AccountTransaction> transactions;
        try {
            transactionManager.startTransaction();
//            long rows = accountTransactionDao.rowsInTableForUser(userId);
            long startPosition = nextPage * rowsOnPage;
            return accountTransactionDao.findAllByUserIdPageLimit(userId, startPosition, rowsOnPage);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public long rowsInTableForUser(long userId) throws ServiceException {
        try {
            transactionManager.startTransaction();
            return accountTransactionDao.rowsInTableForUser(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void findPageByUserId(InputContent content, long rowsOnPage) throws ServiceException {
        List<AccountTransaction> transactions;
        long previewPage = 0;
        long rowsInTable = 0;
        long userId;
        long nextPage;
        long startPosition;
        String userIdStr = content.getRequestParameter(PAGINATION_USER_ID)[0];
        String nextPageStr = content.getRequestParameter(PAGINATION_NEXT_PAGE)[0];
        InputDataValidator inputDataValidator = InputDataValidator.getInstance();
        try {
            try {
                transactionManager.startTransaction();
                if (inputDataValidator.isIdValid(userIdStr)) {
                    userId = Long.parseLong(userIdStr);
                    nextPage = Long.parseLong(nextPageStr);
                    if (nextPage <= 0) {
                        rowsInTable = accountTransactionDao.rowsInTableForUser(userId);
                        nextPage = 0;
                        previewPage = -1;
                        startPosition = nextPage * rowsOnPage;
                        transactions = accountTransactionDao.findAllByUserIdPageLimit(userId, startPosition, rowsOnPage);
                        if (rowsInTable > rowsOnPage) {
                            nextPage++;
                        }
                    } else {
                        rowsInTable = accountTransactionDao.rowsInTableForUser(userId);
                        startPosition = nextPage * rowsOnPage;
                        transactions = accountTransactionDao.findAllByUserIdPageLimit(userId, startPosition, rowsOnPage);
                        if (rowsInTable > (rowsOnPage * (nextPage + 1))) {
                            previewPage = nextPage - 1;
                            nextPage++;
                        } else {
                            previewPage = nextPage - 1;
                        }
                    }
                    content.putRequestAttribute(PAGINATION_NEXT_PAGE, nextPage);
                    content.putRequestAttribute(PAGINATION_PREVIEW_PAGE, previewPage);
                    content.putRequestAttribute(PAGINATION_RESULT_LIST, transactions);
                    content.putRequestAttribute(PAGINATION_USER_ID, userId);
                    content.setPageUrl(ADMIN_USER_FINANCES_OPERATION_PAGE);
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
}
