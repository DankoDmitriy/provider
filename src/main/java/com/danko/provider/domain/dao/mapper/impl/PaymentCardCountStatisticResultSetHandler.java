package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.dao.mapper.ResultSetHandler;
import com.danko.provider.domain.entity.statisticEntity.PaymentCardCountStatistic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentCardCountStatisticResultSetHandler implements ResultSetHandler<PaymentCardCountStatistic> {
    @Override
    public PaymentCardCountStatistic resultToObject(ResultSet resultSet) throws SQLException {
        PaymentCardCountStatistic paymentCardCountStatistic = new PaymentCardCountStatistic();
        paymentCardCountStatistic.setAmount(resultSet.getBigDecimal("amount"));
        paymentCardCountStatistic.setCount(resultSet.getLong("countData"));
        paymentCardCountStatistic.setSum(resultSet.getBigDecimal("sum"));
        return paymentCardCountStatistic;
    }
}
