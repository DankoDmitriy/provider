package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.ServiceException;

import java.util.List;

/**
 * The interface Account transaction service.
 */
public interface AccountTransactionService {
    /**
     * Find all by user id limit list.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<AccountTransaction> findAllByUserIdLimit(long userId) throws ServiceException;

    /**
     * Find page by user id.
     *
     * @param content    the content
     * @param rowsOnPage the rows on page
     * @throws ServiceException the service exception
     */
    void findPageByUserId(SessionRequestContent content, long rowsOnPage) throws ServiceException;
}
