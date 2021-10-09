package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.domain.entity.TransactionType;
import com.danko.provider.domain.dao.mapper.ResultSetHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.danko.provider.domain.dao.ColumnName.TRANSACTION_DATE;
import static com.danko.provider.domain.dao.ColumnName.TRANSACTION_ID;
import static com.danko.provider.domain.dao.ColumnName.TRANSACTION_SUM;
import static com.danko.provider.domain.dao.ColumnName.TRANSACTION_TYPE_TYPE;
import static com.danko.provider.domain.dao.ColumnName.TRANSACTION_USER_ID;

public class AccountTransactionResultSetHandler implements ResultSetHandler<AccountTransaction> {
    @Override
    public AccountTransaction resultToObject(ResultSet resultSet) throws SQLException {
        long transactionId = resultSet.getLong(TRANSACTION_ID);
        BigDecimal sum = resultSet.getBigDecimal(TRANSACTION_SUM);
        LocalDateTime date = (resultSet.getTimestamp(TRANSACTION_DATE)).toLocalDateTime();
        TransactionType type = TransactionType.valueOf(resultSet.getString(TRANSACTION_TYPE_TYPE));
        long userId = resultSet.getLong(TRANSACTION_USER_ID);
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .setTransactionId(transactionId)
                .setSum(sum)
                .setDate(date)
                .setType(type)
                .setUserId(userId)
                .build();
        return accountTransaction;
    }
}
