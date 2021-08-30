package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    Optional<User> findByNameAndPassword(String name, String password) throws DaoException;

    boolean updatePassword(long userId, String password, String newActivateCode, UserStatus userStatus) throws DaoException;

    boolean updateActivationCodeStatus(String activateCode, int status, UserStatus userStatus) throws DaoException;

    boolean verificationOfActivationCode(String activateCode) throws DaoException;

    boolean updateTariffAndTrafficValue(long userId, long tariffId, BigDecimal traffic) throws DaoException;


}
