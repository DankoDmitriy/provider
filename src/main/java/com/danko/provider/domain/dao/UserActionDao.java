package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.exception.DaoException;

import java.util.List;

public interface UserActionDao extends BaseDao<Long, UserAction> {
    List<UserAction> findAllByUserId(long userId) throws DaoException;
    long add(UserAction userAction, long userId, long tariffId) throws DaoException;
}
