package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.ServiceException;

import java.util.Optional;

public interface PaymentCardService {
    Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws ServiceException;

    void addCards(SessionRequestContent content) throws ServiceException;

    void findByNumber(SessionRequestContent content) throws ServiceException;
}
