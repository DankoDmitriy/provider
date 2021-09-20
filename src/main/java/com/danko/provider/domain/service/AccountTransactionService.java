package com.danko.provider.domain.service;

import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface AccountTransactionService {
    List<AccountTransaction> findAll() throws ServiceException;

    List<AccountTransaction> findAllByUserId(long userId) throws ServiceException;

    List<AccountTransaction> findAllByUserIdLimit(long userId) throws ServiceException;

    Optional<AccountTransaction> findById(long id) throws ServiceException;

    List<AccountTransaction> findPageByUserId(long userId, long rowsOnPage, long nextPage) throws ServiceException;

    long rowsInTableForUser(long userId) throws ServiceException;
}
