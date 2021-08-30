package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.AccountTransactionDao;
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
    private AccountTransactionDao accountTransactionDao = new AccountTransactionDaoImpl();

    @Override
    public List<AccountTransaction> findAll() throws ServiceException {
        List<AccountTransaction> list;
        try {
            list = accountTransactionDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all transactions in database: {}", e);
            throw new ServiceException("Could not find all transactions in database.", e);
        }
        return list;
    }

    @Override
    public List<AccountTransaction> findAllByUserId(long userId) throws ServiceException {
        List<AccountTransaction> list;
        try {
            list = accountTransactionDao.findAllByUserId(userId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all transactions in database: {}", e);
            throw new ServiceException("Could not find all transactions in database.", e);
        }
        return list;
    }

    @Override
    public List<AccountTransaction> findAllByUserIdLimit(long userId) throws ServiceException {
        List<AccountTransaction> list;
        try {
            list = accountTransactionDao.findAllByUserIdLimit(userId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find all transactions in database: {}", e);
            throw new ServiceException("Could not find all transactions in database.", e);
        }
        return list;
    }

    @Override
    public Optional<AccountTransaction> findById(long id) throws ServiceException {
        Optional<AccountTransaction> accountTransaction = Optional.empty();
        try {
            accountTransaction = accountTransactionDao.findById(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find transaction by id: {}", e);
            throw new ServiceException("Can't find transaction by id.", e);
        }
        return Optional.empty();
    }
}
