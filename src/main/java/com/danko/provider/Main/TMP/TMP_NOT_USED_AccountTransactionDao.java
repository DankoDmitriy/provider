package com.danko.provider.Main.TMP;

import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.DaoException;

import java.util.List;

public class TMP_NOT_USED_AccountTransactionDao {
//    List<AccountTransaction> findAllByUserId(long userId) throws DaoException;
//@Override
//public List<AccountTransaction> findAllByUserId(long userId) throws DaoException {
//    List<AccountTransaction> list;
//    list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACCOUNT_TRANSACTIONS_BY_USER_ID, userId);
//    return list;
//}

//    private static final String SQL_FIND_ALL_ACCOUNT_TRANSACTIONS_BY_USER_ID = """
//            SELECT
//            transaction_id, sum, date, transaction_type_type_id, tt.type, users_user_id
//            FROM
//            account_transactions
//            JOIN
//            transaction_type AS tt ON account_transactions.transaction_type_type_id = tt.type_id
//            WHERE
//            users_user_id=?
//            ORDER BY date DESC
//            """;

}
