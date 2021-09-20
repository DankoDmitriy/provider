package com.danko.provider.domain.dao.impl;

import cn.hutool.core.date.DateTime;
import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserActionDao;
import com.danko.provider.domain.dao.mapper.impl.UserActionResultSetHandler;
import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserActionDaoImpl implements UserActionDao {
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_ACTIONS = """
            SELECT
            action_id, date, at.type, tp.description
            FROM
            actions
            JOIN
            action_type AS at ON actions.action_type_type_id = at.action_type_id
            JOIN
            tariffs AS tp ON actions.tariffs_tariff_id = tp.tariff_id
            ORDER BY date DESC
            """;

    private static final String SQL_FIND_ACTION_BY_ID = """
            SELECT
            action_id, date, at.type, tp.description
            FROM
            actions
            JOIN
            action_type AS at ON actions.action_type_type_id = at.action_type_id
            JOIN
            tariffs AS tp ON actions.tariffs_tariff_id = tp.tariff_id
            WHERE
            action_id=?
            """;

    private static final String SQL_FIND_ALL_ACTIONS_BY_USER_ID = """
            SELECT
            action_id, date, at.type, tp.description
            FROM
            actions
            JOIN
            action_type AS at ON actions.action_type_type_id = at.action_type_id
            JOIN
            tariffs AS tp ON actions.tariffs_tariff_id = tp.tariff_id
            WHERE
            users_user_id=?
            ORDER BY date DESC
            """;

    private static final String SQL_FIND_ALL_ACTIONS_BY_USER_ID_LIMIT_5 = """
            SELECT
            action_id, date, at.type, tp.description
            FROM
            actions
            JOIN
            action_type AS at ON actions.action_type_type_id = at.action_type_id
            JOIN
            tariffs AS tp ON actions.tariffs_tariff_id = tp.tariff_id
            WHERE
            users_user_id=?
            ORDER BY date DESC
            LIMIT 5
            """;

    private static final String SQL_ADD_ACTION = """
            INSERT INTO actions 
            (date, users_user_id, tariffs_tariff_id, action_type_type_id )
            VALUES (?,?,?,
            (select action_type_id from action_type where type=?)
            )
            """;

    private JdbcTemplate<UserAction> jdbcTemplate;

    public UserActionDaoImpl() {
        jdbcTemplate = new JdbcTemplate<UserAction>(new UserActionResultSetHandler());
    }

    @Override
    public List<UserAction> findAll() throws DaoException {
        List<UserAction> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACTIONS);
        return list;
    }

    @Override
    public Optional<UserAction> findById(Long id) throws DaoException {
        Optional<UserAction> userAction;
        userAction = jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_ACTION_BY_ID, id);
        return userAction;
    }

    @Override
    public boolean update(UserAction userAction) throws DaoException {
        return false;
    }


    @Override
    public long add(UserAction userAction) throws DaoException {
        throw new UnsupportedOperationException("This method unsupported...");
    }

    @Override
    public long add(UserAction userAction, long userId, long tariffId) throws DaoException {
        long generatedId = jdbcTemplate.executeInsertQuery(SQL_ADD_ACTION,
                userAction.getDateTime(),
                userId,
                tariffId,
                userAction.getActionType().name()
        );
        return generatedId;
    }

    @Override
    public List<UserAction> findAllByUserIdLimit(long userId) throws DaoException {
        List<UserAction> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACTIONS_BY_USER_ID_LIMIT_5, userId);
        return list;
    }

    @Override
    public List<UserAction> findAllByUserId(long userId) throws DaoException {
        List<UserAction> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACTIONS_BY_USER_ID, userId);
        return list;
    }
}
