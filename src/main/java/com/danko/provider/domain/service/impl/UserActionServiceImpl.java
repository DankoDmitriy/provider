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
    private static final Logger logger = LogManager.getLogger();
    private final UserActionDao userActionDao;
    private final TransactionManager transactionManager;

    public UserActionServiceImpl(UserActionDao userActionDao, TransactionManager transactionManager) {
        this.userActionDao = userActionDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<UserAction> findAll() throws ServiceException {
        List<UserAction> list = null;
        try {
            try {
                transactionManager.startTransaction();
                list = userActionDao.findAll();
            } catch (DaoException e) {
                transactionManager.rollback();
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException e1) {
            throw new ServiceException(e1);
        }
        return list;
    }

    @Override
    public Optional<UserAction> findById(Long id) throws ServiceException {
        Optional<UserAction> userActionOptional = Optional.empty();
        try {
            try {
                transactionManager.startTransaction();
                userActionOptional = userActionDao.findById(id);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException e1) {
            throw new ServiceException(e1);
        }
        return userActionOptional;
    }

    @Override
    public List<UserAction> findAllByUserId(long userId) throws ServiceException {
        List<UserAction> list = null;
        try {
            try {
                transactionManager.startTransaction();
                list = userActionDao.findAllByUserId(userId);
            } catch (DaoException e) {
                transactionManager.rollback();
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return list;
    }

    @Override
    public List<UserAction> findAllByUserIdLimit(long userId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userActionDao.findAllByUserIdLimit(userId);
            } catch (DaoException e) {
                throw new DaoException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public long add(UserAction userAction, long userId, long tariffId) throws ServiceException {
        long generatedId = 0;
        try {
            try {
                transactionManager.startTransaction();
                generatedId = userActionDao.add(userAction, userId, tariffId);
                transactionManager.commit();
            } catch (DaoException e) {
                generatedId = 0;
                transactionManager.rollback();
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException e1) {
            throw new ServiceException(e1);
        }
        return generatedId;
    }
}
