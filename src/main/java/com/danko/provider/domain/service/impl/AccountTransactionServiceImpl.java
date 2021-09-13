package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.impl.AccountTransactionDaoImpl;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.service.AccountTransactionService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class AccountTransactionServiceImpl implements AccountTransactionService {
    private static Logger logger = LogManager.getLogger();
    //    private AccountTransactionDao accountTransactionDao = new AccountTransactionDaoImpl();
    private AccountTransactionDao accountTransactionDao;
    private TransactionManager transactionManager;

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
}
