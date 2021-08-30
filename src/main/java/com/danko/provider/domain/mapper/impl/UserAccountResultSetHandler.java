package com.danko.provider.domain.mapper.impl;

import com.danko.provider.domain.entity.UserAccount;
import com.danko.provider.domain.mapper.ResultSetHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.danko.provider.domain.dao.ColumnName.*;

public class UserAccountResultSetHandler implements ResultSetHandler<UserAccount> {
    @Override
    public UserAccount resultToObject(ResultSet resultSet) throws SQLException {
        long accountId = resultSet.getLong(ACCOUNT_ACCOUNT_ID);
        long userId = resultSet.getLong(ACCOUNT_USER_ID);
        BigDecimal balance = resultSet.getBigDecimal(ACCOUNT_BALANCE);
        UserAccount account = UserAccount.builder()
                .setAccountId(accountId)
                .setUserId(userId)
                .setBalance(balance)
                .build();
        return account;
    }
}
