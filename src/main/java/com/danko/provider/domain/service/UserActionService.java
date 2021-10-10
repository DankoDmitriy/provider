package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.exception.ServiceException;

import java.util.List;

/**
 * The interface User action service.
 */
public interface UserActionService {
    /**
     * Find all by user id limit list.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<UserAction> findAllByUserIdLimit(long userId) throws ServiceException;

    /**
     * Find page by user id.
     *
     * @param content    the content
     * @param rowsOnPage the rows on page
     * @throws ServiceException the service exception
     */
    void findPageByUserId(SessionRequestContent content, long rowsOnPage) throws ServiceException;
}
