package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.exception.ServiceException;

/**
 * The interface Payment card service.
 */
public interface PaymentCardService {
    /**
     * Add cards.
     *
     * @param content the content
     * @throws ServiceException the service exception
     */
    void addCards(SessionRequestContent content) throws ServiceException;

    /**
     * Find by number.
     *
     * @param content the content
     * @throws ServiceException the service exception
     */
    void findByNumber(SessionRequestContent content) throws ServiceException;
}
