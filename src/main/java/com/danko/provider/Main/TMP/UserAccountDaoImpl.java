package com.danko.provider.Main.TMP;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserAccountDaoImpl implements UserAccountDao {
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_ACCOUNTS = """
            SELECT
            account_id, balance, users_user_id
            FROM
            user_accounts
            """;

    private static final String SQL_FIND_ACCOUNT_BY_ID = """
            SELECT
            account_id, balance, users_user_id
            FROM
            user_accounts
            WHERE
            account_id=?
            """;

    private static final String SQL_ADD_ACCOUNT = """
            INSERT INTO USERS 
            (balance, users_user_id)
            VALUES (?,?)
            """;

    private static final String SQL_UPDATE_ACCOUNT = """
            UPDATE USERS SET
            balance=?, users_user_id=?
            WHERE 
            account_id=?
            """;
    private JdbcTemplate<UserAccount> jdbcTemplate;

    public UserAccountDaoImpl() {
        jdbcTemplate = new JdbcTemplate<UserAccount>(new UserAccountResultSetHandler());
    }

    @Override
    public List<UserAccount> findAll() throws DaoException {
        List<UserAccount> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACCOUNTS);
        return list;
    }

    @Override
    public Optional<UserAccount> findById(Long id) throws DaoException {
        Optional<UserAccount> userAccountOptional;
        userAccountOptional = jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_ACCOUNT_BY_ID, id);
        return userAccountOptional;
    }

    @Override
    public boolean update(UserAccount userAccount) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_ACCOUNT,
                userAccount.getBalance(),
                userAccount.getUserId(),
                userAccount.getAccountId()
        );
        return result;
    }

    @Override
    public long add(UserAccount userAccount) throws DaoException {
        long generatedId = jdbcTemplate.executeInsertQuery(SQL_ADD_ACCOUNT,
                userAccount.getBalance(),
                userAccount.getUserId()
        );
        userAccount.setUserId(generatedId);
        return generatedId;
    }
}
