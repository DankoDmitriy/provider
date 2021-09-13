package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.impl.UserActionDaoImpl;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.service.UserActionService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserActionServiceImpl implements UserActionService {
    private static Logger logger = LogManager.getLogger();
    //    private UserActionDao userActionDao = new UserActionDaoImpl();
    private UserActionDao userActionDao;
    private TransactionManager transactionManager;

    public UserActionServiceImpl(UserActionDao userActionDao, TransactionManager transactionManager) {
        this.userActionDao = userActionDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<UserAction> findAll() throws ServiceException {
        try {
            transactionManager.startTransaction();
            List<UserAction> list = userActionDao.findAll();
            return list;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find users actions in database: {}", e);
            throw new ServiceException("Could not find users actions in database.", e);
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
    public Optional<UserAction> findById(Long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            Optional<UserAction> userActionOptional = userActionDao.findById(id);
            return userActionOptional;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find users action in database: {}", e);
            throw new ServiceException("Could not find users action in database.", e);
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
    public List<UserAction> findAllByUserId(long userId) throws ServiceException {
        try {
            transactionManager.startTransaction();
            List<UserAction> list = userActionDao.findAllByUserId(userId);
            return list;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find user actions in database: {}", e);
            throw new ServiceException("Could not find user actions in database.", e);
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
    public long add(UserAction userAction, long userId, long tariffId) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long generatedId = userActionDao.add(userAction, userId, tariffId);
            transactionManager.commit();
            return generatedId;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not add user action in database: {}", e);
            try {
                transactionManager.rollback();
            } catch (DaoException e1) {
                logger.log(Level.ERROR, "Rollback error: {}", e1);
                throw new ServiceException("Rollback error.", e);
            }
            throw new ServiceException("Could not add user action in database.", e);
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
