package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserActionService {
    List<UserAction> findAll() throws ServiceException;

    Optional<UserAction> findById(Long id) throws ServiceException;

    List<UserAction> findAllByUserId(long userId) throws ServiceException;

    List<UserAction> findAllByUserIdLimit(long userId) throws ServiceException;

    long add(UserAction userAction, long userId, long tariffId) throws ServiceException;

    public void findPageByUserId(SessionRequestContent content, long rowsOnPage) throws ServiceException;
}
