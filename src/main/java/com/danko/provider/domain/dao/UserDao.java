package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.*;
import com.danko.provider.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    long add(User user, String password, String activationCode) throws DaoException;

    Optional<User> findByNameAndPassword(String name, String password) throws DaoException;

    boolean updatePassword(long userId, String password, String newActivateCode, UserStatus userStatus) throws DaoException;

    boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws DaoException;

    boolean verificationOfActivationCode(String activateCode) throws DaoException;

    boolean updateTariffAndTrafficAndBalanceValue(long userId, long tariffId, BigDecimal traffic, BigDecimal balance) throws DaoException;

    boolean balanceReplenishment(long userId, BigDecimal userBalance) throws DaoException;

    boolean updateContractNumberAndUserName(long userId, String contractNumber, String userName) throws DaoException;



    boolean updateFirstName(long userId, String firstName) throws DaoException;

    boolean updateLastName(long userId, String lastName) throws DaoException;

    boolean updatePatronymic(long userId, String patronymic) throws DaoException;

    boolean updateEmail(long userId, String email) throws DaoException;

    boolean updateStatus(long userId, UserStatus status) throws DaoException;

    boolean updateRole(long userId, UserRole role) throws DaoException;

    long rowsInTableByUserRole(UserRole role) throws DaoException;

    List<User> findAllByUserRolePageLimit(UserRole role, long startPosition, long rows) throws DaoException;

    List<User> searchUsersByParameters(String searchParameter) throws DaoException;
}
