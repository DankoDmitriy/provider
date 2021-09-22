package com.danko.provider.domain.service;

import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.entity.TransferObject;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.exception.ServiceException;

import javax.management.relation.Role;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.danko.provider.controller.command.ParamName.USER_EDIT_LAST_NAME;
import static com.danko.provider.controller.command.ParamName.USER_EDIT_PATRONYMIC;

public interface UserService {

    List<User> findAllUsers() throws ServiceException;

    void findPageByUserRole(InputContent content, long rowsOnPage) throws ServiceException;

    Optional<User> findByNameAndPassword(String name, String password) throws ServiceException;

    boolean updatePassword(long userId, String password, String email, String contextPath, String requestUrl, long tariffId) throws ServiceException;

    boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws ServiceException;

    boolean verificationOfActivationCode(String activateCode) throws ServiceException;

    boolean updateTariffPlan(long userId, long tariffId) throws ServiceException;

    BigDecimal activatePaymentCard(String cardNumber,
                                   String cardPin,
                                   long userId,
                                   BigDecimal userBalance,
                                   long tariffId) throws ServiceException;

    void addUser(InputContent content) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    void updateUserPersonalData(InputContent content) throws ServiceException;

    boolean blockOrUnblock(long userId) throws ServiceException;

    boolean changeRole(long userId) throws ServiceException;
}
