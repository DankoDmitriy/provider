package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.exception.ServiceException;

public interface PaymentCardService {
    void addCards(SessionRequestContent content) throws ServiceException;

    void findByNumber(SessionRequestContent content) throws ServiceException;
}
