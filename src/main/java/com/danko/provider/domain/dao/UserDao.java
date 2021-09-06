package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.*;
import com.danko.provider.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    long add(User user, String password) throws DaoException;

    Optional<User> findByNameAndPassword(String name, String password) throws DaoException;

    boolean updatePassword(long userId, String password, String newActivateCode, UserStatus userStatus) throws DaoException;

    boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws DaoException;

    boolean verificationOfActivationCode(String activateCode) throws DaoException;

    boolean updateTariffAndTrafficValue(long userId, long tariffId, BigDecimal traffic) throws DaoException;

    void balanceReplenishment(long userId, BigDecimal userBalance, long tariffId, PaymentCard paymentCard, UserAction userAction, AccountTransaction accountTransaction) throws DaoException;


}
