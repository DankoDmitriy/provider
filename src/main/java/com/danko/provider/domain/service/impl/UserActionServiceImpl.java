package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.service.UserActionService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.RequestAttribute.*;

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

    @Override
    public void findPageByUserId(InputContent content, long rowsOnPage) throws ServiceException {
        List<UserAction> actions;
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
                        rowsInTable = userActionDao.rowsInTableForUser(userId);
                        nextPage = 0;
                        previewPage = -1;
                        startPosition = nextPage * rowsOnPage;
                        actions = userActionDao.findAllByUserIdPageLimit(userId, startPosition, rowsOnPage);
                        if (rowsInTable > rowsOnPage) {
                            nextPage++;
                        }
                    } else {
                        rowsInTable = userActionDao.rowsInTableForUser(userId);
                        startPosition = nextPage * rowsOnPage;
                        actions = userActionDao.findAllByUserIdPageLimit(userId, startPosition, rowsOnPage);
                        if (rowsInTable > (rowsOnPage * (nextPage + 1))) {
                            previewPage = nextPage - 1;
                            nextPage++;
                        } else {
                            previewPage = nextPage - 1;
                        }
                    }
                    content.putRequestAttribute(PAGINATION_NEXT_PAGE, nextPage);
                    content.putRequestAttribute(PAGINATION_PREVIEW_PAGE, previewPage);
                    content.putRequestAttribute(PAGINATION_RESULT_LIST, actions);
                    content.putRequestAttribute(PAGINATION_USER_ID, userId);
                    content.setPageUrl(ADMIN_USER_ACTIONS_PAGE);
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
