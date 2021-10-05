package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.ServiceException;

import java.util.List;

public interface AccountTransactionService {
    List<AccountTransaction> findAllByUserIdLimit(long userId) throws ServiceException;

    void findPageByUserId(SessionRequestContent content, long rowsOnPage) throws ServiceException;
}
