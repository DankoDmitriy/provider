package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers() throws ServiceException;

    void findPageByUserRole(SessionRequestContent content, long rowsOnPage) throws ServiceException;

    void login(SessionRequestContent content) throws ServiceException;

    boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl, long tariffId) throws ServiceException;

    boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException;

    boolean verificationOfActivationCode(String activateCode) throws ServiceException;

    boolean updateTariffPlan(long userId, long tariffId) throws ServiceException;

    void activatePaymentCard(SessionRequestContent content) throws ServiceException;

    void addUser(SessionRequestContent content) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    void updateUserPersonalData(SessionRequestContent content) throws ServiceException;

    boolean blockOrUnblock(long userId) throws ServiceException;

    boolean changeRole(long userId) throws ServiceException;

    void searchUsers(SessionRequestContent content) throws ServiceException;
}
