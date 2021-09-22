package com.danko.provider.domain.service;

import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

import static com.danko.provider.controller.command.ParamName.*;

public interface PaymentCardService {
    Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws ServiceException;

    void addCards(InputContent content) throws ServiceException;
}
