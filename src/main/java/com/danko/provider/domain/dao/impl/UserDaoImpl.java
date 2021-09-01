package com.danko.provider.domain.dao.impl;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.entity.User;
import com.danko.provider.domain.entity.UserStatus;
import com.danko.provider.domain.mapper.impl.UserResultSetHandler;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_USERS = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, activation_code, 
            activation_code_used, traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            """;

    private static final String SQL_FIND_USER_BY_ID = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, activation_code, 
            activation_code_used, traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            WHERE
            USER_ID=?
            """;
    private static final String SQL_FIND_USER_BY_NAME_AND_PASSWORD = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, activation_code, 
            activation_code_used,  traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            WHERE
            name=?
            AND
            password=?
            """;

    private static final String SQL_ADD_USER = """
            INSERT INTO USERS 
            (first_name, last_name, patronymic, contract_number, contract_date, balance, name, password, email, 
            activation_code, activation_code_used, traffic, user_roles_role_id, user_statuses_status_id, tariffs_tariff_id)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,
            (select role_id from user_roles where role=?),
            (select status_id from user_statuses where status=?),
            ?
            )
            """;

    private static final String SQL_UPDATE_USER = """
            UPDATE USERS SET
            first_name=?, last_name=?, patronymic=?, contract_number=?, contract_date=?, balance=?, name=?, password=?, email=?, activation_code=?, 
            activation_code_used=?, traffic=?, user_roles_role_id=(select role_id from user_roles where role=?), 
            user_statuses_status_id=(select status_id from user_statuses where status=?),
            tariffs_tariff_id=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_VERIFICATION_OF_ACTIVATION_CODE = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, activation_code, 
            activation_code_used, traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            WHERE
            activation_code=? 
            AND 
            user_id>0
            """;

    private static final String SQL_UPDATE_ACTIVATION_CODE_STATUS = """
            UPDATE USERS SET
            activation_code_used=?,
            user_statuses_status_id=(select status_id from user_statuses where status=?)
            WHERE 
            activation_code=?
            """;

    private static final String SQL_UPDATE_PASSWORD = """
            UPDATE USERS SET
            password=?,
            activation_code=?,
            activation_code_used=?,
            user_statuses_status_id=(select status_id from user_statuses where status=?)
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_TARIFF_AND_TRAFFIC = """
            UPDATE USERS SET
            tariffs_tariff_id=?,
            traffic=?
            WHERE 
            user_id=?
            """;

    private JdbcTemplate<User> jdbcTemplate;

    public UserDaoImpl() {
        jdbcTemplate = new JdbcTemplate<User>(ConnectionPool.getInstance(), new UserResultSetHandler());
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_USERS);
        return list;
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        Optional<User> userOptional;
        userOptional = jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_USER_BY_ID, id);
        return userOptional;
    }

    @Override
    public Optional<User> findByNameAndPassword(String name, String password) throws DaoException {
        Optional<User> userOptional;
        userOptional = jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_USER_BY_NAME_AND_PASSWORD, name, password);
        return userOptional;
    }

    @Override
    public boolean updatePassword(long userId, String password, String newActivateCode, UserStatus userStatus) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_PASSWORD,
                password,
                newActivateCode,
                false,
                userStatus.name(),
                userId);
        return result;
    }

    @Override
    public boolean updateActivationCodeStatus(String activateCode, UserStatus userStatus) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_ACTIVATION_CODE_STATUS,
                true,
                userStatus.name(),
                activateCode);
        return result;
    }

    @Override
    public boolean verificationOfActivationCode(String activateCode) throws DaoException {
        boolean result = true;
        Optional<User> optionalUser = jdbcTemplate.executeSelectQueryForSingleResult(SQL_VERIFICATION_OF_ACTIVATION_CODE, activateCode);
        if (!optionalUser.isEmpty()) {
            User user = optionalUser.get();
            if (user.isActivationCodeUsed()) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getContractNumber(),
                Timestamp.valueOf(user.getContractDate()),
                user.getBalance(),
                user.getName(),
                user.getEmail(),
                user.getActivationCode(),
                user.getTraffic(),
                user.getRole().getRole(),
                user.getStatus().getStatus(),
                user.getTariffId(),
                user.getUserId());
        return result;
    }

    @Override
    public long add(User user) throws DaoException {
        long generatedId = jdbcTemplate.executeInsertQuery(SQL_ADD_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getContractNumber(),
                Timestamp.valueOf(user.getContractDate()),
                user.getBalance(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                user.getActivationCode(),
                user.isActivationCodeUsed(),
                user.getTraffic(),
                user.getRole().getRole(),
                user.getStatus().getStatus(),
                user.getTariffId());
        user.setUserId(generatedId);
        return generatedId;
    }

    @Override
    public boolean updateTariffAndTrafficValue(long userId, long tariffId, BigDecimal traffic) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_TARIFF_AND_TRAFFIC,
                tariffId,
                traffic,
                userId);
        return result;
    }
}
