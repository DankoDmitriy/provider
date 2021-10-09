package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.dao.mapper.ResultSetHandler;
import com.danko.provider.domain.entity.PaymentCard;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.danko.provider.domain.dao.ColumnName.CARD_STATUS_STATUS;
import static com.danko.provider.domain.dao.ColumnName.PAYMENT_CARD_ACTIVATION_DATE;
import static com.danko.provider.domain.dao.ColumnName.PAYMENT_CARD_AMOUNT;
import static com.danko.provider.domain.dao.ColumnName.PAYMENT_CARD_ID;

public class PaymentCardResultSetHandler implements ResultSetHandler<PaymentCard> {
    @Override
    public PaymentCard resultToObject(ResultSet resultSet) throws SQLException {
        long cardId = resultSet.getLong(PAYMENT_CARD_ID);
        BigDecimal amount = resultSet.getBigDecimal(PAYMENT_CARD_AMOUNT);
        LocalDateTime activationDate = resultSet.getTimestamp(PAYMENT_CARD_ACTIVATION_DATE).toLocalDateTime();
        PaymentCard.CardStatus cardStatus = PaymentCard.CardStatus.valueOf(resultSet.getString(CARD_STATUS_STATUS));
        PaymentCard paymentCard = PaymentCard.builder()
                .setCardId(cardId)
                .setAmount(amount)
                .setActivationDate(activationDate)
                .setCardStatus(cardStatus)
                .build();
        return paymentCard;
    }
}
