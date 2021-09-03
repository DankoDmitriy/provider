package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentCardDao extends BaseDao<Long, PaymentCard> {
    Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws DaoException;

    List<PaymentCard> findByUserId(long userId) throws DaoException;

    Optional<PaymentCard> findByCardNumber(String cardNumber) throws DaoException;

    boolean activateCard(long cardId, long userId, LocalDateTime activationDate) throws DaoException;

    void add(BigDecimal amount, String cardNumber, String cardPin, PaymentCard.CardStatus cardStatus) throws DaoException;
}
