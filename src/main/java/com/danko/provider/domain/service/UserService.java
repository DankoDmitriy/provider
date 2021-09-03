package com.danko.provider.domain.service;

import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers() throws ServiceException;

    Optional<User> findByNameAndPassword(String name, String password) throws ServiceException;

    boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl, long tariffId) throws ServiceException;

    boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException;

    boolean verificationOfActivationCode(String activateCode) throws ServiceException;

    BigDecimal updateTariffPlan(long userId, long tariffId) throws ServiceException;
}
