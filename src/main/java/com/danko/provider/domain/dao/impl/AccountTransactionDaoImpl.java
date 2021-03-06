package com.danko.provider.domain.dao.impl;

import com.danko.provider.domain.dao.AccountTransactionDao;
import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.mapper.impl.AccountTransactionResultSetHandler;
import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class AccountTransactionDaoImpl implements AccountTransactionDao {

    private static final String SQL_FIND_ALL_ACCOUNT_TRANSACTIONS = """
            SELECT
            transaction_id, sum, date, transaction_type_type_id, tt.type, users_user_id
            FROM
            account_transactions
            JOIN
            transaction_type AS tt ON account_transactions.transaction_type_type_id = tt.type_id
            ORDER BY date DESC
            """;
    private static final String SQL_FIND_ALL_ACCOUNT_TRANSACTIONS_BY_USER_ID_LIMIT_5 = """
            SELECT
            transaction_id, sum, date, transaction_type_type_id, tt.type, users_user_id
            FROM
            account_transactions
            JOIN
            transaction_type AS tt ON account_transactions.transaction_type_type_id = tt.type_id
            WHERE
            users_user_id=? 
            ORDER BY date DESC 
            LIMIT 5
            """;
    private static final String SQL_FIND_ACCOUNT_TRANSACTIONS_BY_ID = """
            SELECT
            transaction_id, sum, date, transaction_type_type_id, tt.type, users_user_id
            FROM
            account_transactions
            JOIN
            transaction_type AS tt ON account_transactions.transaction_type_type_id = tt.type_id
            WHERE
            transaction_id=?
            """;
    private static final String SQL_ADD_ACCOUNT_TRANSACTION = """
            INSERT INTO account_transactions 
            (sum,  date, users_user_id, transaction_type_type_id)
            VALUES (?,?,?,
            (select type_id from transaction_type where type=?))
            """;
    private static final String SQL_UPDATE_ACCOUNT_TRANSACTION = """
            UPDATE account_transactions SET
            sum=?,  date=?, 
            transaction_type_type_id=(select type_id from transaction_type where type=?), 
            users_user_id=?
            WHERE
            transaction_id=?
            """;
    private static final String SQL_COUNT_ROWS_FOR_USER = """
            SELECT count(*) as line 
            FROM account_transactions
            WHERE users_user_id=?
            """;
    private static final String SQL_FIND_ACCOUNT_TRANSACTIONS_BY_USER_ID_AND_PAGE_NUMBER = """
            SELECT
            transaction_id, sum, date, transaction_type_type_id, tt.type, users_user_id
            FROM
            account_transactions
            JOIN
            transaction_type AS tt ON account_transactions.transaction_type_type_id = tt.type_id
            WHERE
            users_user_id=? 
            ORDER BY date DESC 
            LIMIT ?,?
            """;

    private JdbcTemplate<AccountTransaction> jdbcTemplate;

    public AccountTransactionDaoImpl() {
        jdbcTemplate = new JdbcTemplate<AccountTransaction>(new AccountTransactionResultSetHandler());
    }

    @Override
    public List<AccountTransaction> findAll() throws DaoException {
        List<AccountTransaction> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACCOUNT_TRANSACTIONS);
        return list;
    }


    @Override
    public List<AccountTransaction> findAllByUserIdLimit(long userId) throws DaoException {
        List<AccountTransaction> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACCOUNT_TRANSACTIONS_BY_USER_ID_LIMIT_5, userId);
        return list;
    }

    @Override
    public Optional<AccountTransaction> findById(Long id) throws DaoException {
        Optional<AccountTransaction> optionalAccountTransaction;
        optionalAccountTransaction = jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_ACCOUNT_TRANSACTIONS_BY_ID, id);
        return optionalAccountTransaction;
    }

    @Override
    public boolean update(AccountTransaction accountTransaction) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_ACCOUNT_TRANSACTION,
                accountTransaction.getSum(),
                accountTransaction.getDate(),
                accountTransaction.getType().name(),
                accountTransaction.getUserId(),
                accountTransaction.getTransactionId()
        );
        return result;
    }

    @Override
    public long add(AccountTransaction accountTransaction) throws DaoException {
        long generatedId = jdbcTemplate.executeInsertQuery(SQL_ADD_ACCOUNT_TRANSACTION,
                accountTransaction.getSum(),
                accountTransaction.getDate(),
                accountTransaction.getUserId(),
                accountTransaction.getType().name()
        );
        accountTransaction.setTransactionId(generatedId);
        return generatedId;
    }

    @Override
    public long rowsInTableForUser(long userId) throws DaoException {
        return jdbcTemplate.executeCountRows(SQL_COUNT_ROWS_FOR_USER, userId);
    }

    @Override
    public List<AccountTransaction> findAllByUserIdPageLimit(long userId, long startPosition, long rows) throws DaoException {
        return jdbcTemplate.executeSelectQuery(SQL_FIND_ACCOUNT_TRANSACTIONS_BY_USER_ID_AND_PAGE_NUMBER,
                userId,
                startPosition,
                rows);
    }
}
