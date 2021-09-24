package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.service.UserActionService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.util.PaginationCalculate;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.RequestAttribute.*;
import static com.danko.provider.controller.command.SessionAttribute.SESSION_USER;

public class UserActionServiceImpl implements UserActionService {
    private static final Logger logger = LogManager.getLogger();
    private final UserActionDao userActionDao;
    private final TransactionManager transactionManager;
    private final PaginationCalculate paginationCalculate;

    public UserActionServiceImpl(UserActionDao userActionDao, TransactionManager transactionManager) {
        this.userActionDao = userActionDao;
        this.transactionManager = transactionManager;
        this.paginationCalculate = PaginationCalculate.getInstance();
    }

    @Override
    public List<UserAction> findAll() throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userActionDao.findAll();
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
    public Optional<UserAction> findById(Long id) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userActionDao.findById(id);
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
    public List<UserAction> findAllByUserId(long userId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userActionDao.findAllByUserId(userId);
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserAction> findAllByUserIdLimit(long userId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                return userActionDao.findAllByUserIdLimit(userId);
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
    public long add(UserAction userAction, long userId, long tariffId) throws ServiceException {
        try {
            try {
                transactionManager.startTransaction();
                long generatedId = userActionDao.add(userAction, userId, tariffId);
                transactionManager.commit();
                return generatedId;
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public void findPageByUserId(InputContent content, long rowsOnPage) throws ServiceException {
        String userIdStr = content.getRequestParameter(PAGINATION_USER_ID)[0];
        String nextPageStr = content.getRequestParameter(PAGINATION_NEXT_PAGE)[0];
        InputDataValidator inputDataValidator = InputDataValidator.getInstance();
        User user = (User) content.getSessionAttribute(SESSION_USER);
        try {
            try {
                transactionManager.startTransaction();
                if (inputDataValidator.isIdValid(userIdStr)) {
                    long userId;
                    if (user.getRole().equals(UserRole.USER)) {
                        userId = user.getUserId();
                        content.setPageUrl(USER_ACTIONS_PAGE);
                    } else {
                        userId = Long.parseLong(userIdStr);
                        content.setPageUrl(ADMIN_USER_ACTIONS_PAGE);
                    }

                    long nextPage = Long.parseLong(nextPageStr);

                    long rowsInTable = userActionDao.rowsInTableForUser(userId);
                    Map<String, Long> calculatedData = paginationCalculate.calculateNextAndPreviewPageValue(
                            nextPage, rowsInTable, rowsOnPage);
                    List<UserAction> actions = userActionDao.findAllByUserIdPageLimit(
                            userId, calculatedData.get(PaginationCalculate.START_POSITION), rowsOnPage);

                    content.putRequestAttribute(PAGINATION_NEXT_PAGE,
                            calculatedData.get(PaginationCalculate.NEXT_PAGE));
                    content.putRequestAttribute(PAGINATION_PREVIEW_PAGE,
                            calculatedData.get(PaginationCalculate.PREVIEW_PAGE));
                    content.putRequestAttribute(PAGINATION_RESULT_LIST, actions);
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
