package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.dao.mapper.ResultSetHandler;
import com.danko.provider.domain.entity.UserAction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.danko.provider.domain.dao.ColumnName.ACTION_DATE;
import static com.danko.provider.domain.dao.ColumnName.ACTION_ID;
import static com.danko.provider.domain.dao.ColumnName.ACTION_TYPE_ACTION_TYPE;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_DESCRIPTION;

public class UserActionResultSetHandler implements ResultSetHandler<UserAction> {
    @Override
    public UserAction resultToObject(ResultSet resultSet) throws SQLException {
        long actionId = resultSet.getLong(ACTION_ID);
        LocalDateTime dateTime = (resultSet.getTimestamp(ACTION_DATE)).toLocalDateTime();
        UserAction.ActionType actionType = UserAction.ActionType.valueOf(resultSet.getString(ACTION_TYPE_ACTION_TYPE));
        String tariffName = resultSet.getString(TARIFF_DESCRIPTION);
        UserAction userAction = UserAction.builder()
                .setActionId(actionId)
                .setDateTime(dateTime)
                .setActionType(actionType)
                .setTariffName(tariffName)
                .build();
        return userAction;
    }
}
