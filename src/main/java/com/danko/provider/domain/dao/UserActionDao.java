package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.exception.DaoException;

import java.util.List;

public interface UserActionDao extends BaseDao<Long, UserAction> {
    List<UserAction> findAllByUserId(long userId) throws DaoException;

    List<UserAction> findAllByUserIdLimit(long userId) throws DaoException;

    long add(UserAction userAction, long userId, long tariffId) throws DaoException;

    long rowsInTableForUser(long userId) throws DaoException;

    List<UserAction> findAllByUserIdPageLimit(long userId, long startPosition, long rows) throws DaoException;
}
