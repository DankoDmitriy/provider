package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.dao.mapper.ResultSetHandler;
import com.danko.provider.domain.entity.PaymentCardCountStatistic;
import com.danko.provider.domain.entity.UserCountStatistic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCountStatisticResultSetHandler extends PaymentCardCountStatistic implements ResultSetHandler<UserCountStatistic> {
    @Override
    public UserCountStatistic resultToObject(ResultSet resultSet) throws SQLException {
        UserCountStatistic userCountStatistic = new UserCountStatistic();
        userCountStatistic.setParameterName(resultSet.getString("parameterName"));
        userCountStatistic.setCount(resultSet.getLong("countData"));
        return userCountStatistic;
    }
}
