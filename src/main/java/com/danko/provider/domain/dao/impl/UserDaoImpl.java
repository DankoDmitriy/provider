package com.danko.provider.domain.dao.impl;

import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.UserDao;
import com.danko.provider.domain.entity.*;
import com.danko.provider.domain.dao.mapper.impl.UserResultSetHandler;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_USERS = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, 
            traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            """;

    private static final String SQL_FIND_ALL_USERS_BY_ROLE = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, 
            traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            WHERE 
            role_id=(select role_id from user_roles where role=?)
            ORDER BY
            user_statuses_status_id
            """;

    private static final String SQL_FIND_USER_BY_ID = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, 
            traffic, ur.role, us.status, tariffs_tariff_id
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
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, 
            traffic, ur.role, us.status, tariffs_tariff_id
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
            (first_name, last_name, patronymic, contract_date, balance, password, email, 
            activation_code, activation_code_used, traffic, user_roles_role_id, user_statuses_status_id, tariffs_tariff_id)
            VALUES (?,?,?,?,?,?,?,?,?,?,
            (select role_id from user_roles where role=?),
            (select status_id from user_statuses where status=?),
            ?
            )
            """;

    private static final String SQL_UPDATE_USER = """
            UPDATE USERS SET
            first_name=?, last_name=?, patronymic=?, contract_number=?, contract_date=?, balance=?, name=?, email=?, 
            traffic=?, user_roles_role_id=(select role_id from user_roles where role=?), 
            user_statuses_status_id=(select status_id from user_statuses where status=?),
            tariffs_tariff_id=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_VERIFICATION_OF_ACTIVATION_CODE = """
            SELECT
            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email, 
            traffic, ur.role, us.status, tariffs_tariff_id
            FROM
            users
            JOIN
            user_roles AS ur ON users.user_roles_role_id = ur.role_id
            JOIN
            user_statuses AS us ON users.user_statuses_status_id = us.status_id
            WHERE
            activation_code=? 
            AND 
            activation_code_used=0
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

    private static final String SQL_UPDATE_TARIFF_AND_TRAFFIC_AND_BALANCE = """
            UPDATE USERS SET
            tariffs_tariff_id=?,
            traffic=?,
            balance=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_BALANCE = """
            UPDATE USERS SET
            balance=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_CONTRACT_NUMBER_AND_USER_NAME = """
            UPDATE USERS SET
            contract_number=?, name=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_FIRST_NAME = """
            UPDATE USERS SET
            first_name=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_LAST_NAME = """
            UPDATE USERS SET
            last_name=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_PATRONYMIC = """
            UPDATE USERS SET
            patronymic=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_EMAIL = """
            UPDATE USERS SET
            email=?
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_STATUS = """
            UPDATE USERS SET
            user_statuses_status_id=(select status_id from user_statuses where status=?)
            WHERE 
            user_id=?
            """;

    private static final String SQL_UPDATE_ROLE = """
            UPDATE USERS SET
            user_roles_role_id=(select role_id from user_roles where role=?)
            WHERE 
            user_id=?
            """;

    private JdbcTemplate<User> jdbcTemplate;

    public UserDaoImpl() {
        jdbcTemplate = new JdbcTemplate<User>(new UserResultSetHandler());
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_USERS);
        return list;
    }

    @Override
    public List<User> findAllByRole(UserRole role) throws DaoException {
        List<User> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_USERS_BY_ROLE, role.name());
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
            result = false;
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
                user.getTraffic(),
                user.getRole().getRole(),
                user.getStatus().getStatus(),
                user.getTariffId(),
                user.getUserId());
        return result;
    }

    @Override
    public long add(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long add(User user, String password, String activationCode) throws DaoException {
        long generatedId = jdbcTemplate.executeInsertQuery(SQL_ADD_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                Timestamp.valueOf(user.getContractDate()),
                user.getBalance(),
                password,
                user.getEmail(),
                activationCode,
                0,
                user.getTraffic(),
                user.getRole().getRole(),
                user.getStatus().getStatus(),
                user.getTariffId());
        user.setUserId(generatedId);
        return generatedId;
    }

    @Override
    public boolean updateTariffAndTrafficAndBalanceValue(long userId, long tariffId, BigDecimal traffic, BigDecimal balance) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_TARIFF_AND_TRAFFIC_AND_BALANCE,
                tariffId,
                traffic,
                balance,
                userId);
        return result;
    }

    @Override
    public boolean balanceReplenishment(long userId, BigDecimal userBalance) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_BALANCE,
                userBalance,
                userId);
    }

    @Override
    public boolean updateContractNumberAndUserName(long userId, String contractNumber, String userName) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_CONTRACT_NUMBER_AND_USER_NAME,
                contractNumber,
                userName,
                userId);
    }

    @Override
    public boolean updateFirstName(long userId, String firstName) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_FIRST_NAME,
                firstName,
                userId);
    }

    @Override
    public boolean updateLastName(long userId, String lastName) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_LAST_NAME,
                lastName,
                userId);
    }

    @Override
    public boolean updatePatronymic(long userId, String patronymic) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_PATRONYMIC,
                patronymic,
                userId);
    }

    @Override
    public boolean updateEmail(long userId, String email) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_EMAIL,
                email,
                userId);
    }

    @Override
    public boolean updateStatus(long userId, UserStatus status) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_STATUS, status.name(), userId);
    }

    @Override
    public boolean updateRole(long userId, UserRole role) throws DaoException {
        return jdbcTemplate.executeUpdateQuery(SQL_UPDATE_ROLE, role.name(), userId);
    }
}
