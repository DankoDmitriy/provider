package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.dao.mapper.ResultSetHandler;
import com.danko.provider.domain.entity.PaymentCardSerial;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.danko.provider.domain.dao.ColumnName.PAYMENT_CARD_SERIAL_ID;
import static com.danko.provider.domain.dao.ColumnName.PAYMENT_CARD_SERIAL_SERIAL;

public class PaymentCardSerialResultSetHandler implements ResultSetHandler<PaymentCardSerial> {
    @Override
    public PaymentCardSerial resultToObject(ResultSet resultSet) throws SQLException {
        long serialId = resultSet.getLong(PAYMENT_CARD_SERIAL_ID);
        String serial = resultSet.getString(PAYMENT_CARD_SERIAL_SERIAL);
        return PaymentCardSerial.builder().setSerialId(serialId).setSerial(serial).build();
    }
}
