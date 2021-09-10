package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.entity.*;
import com.danko.provider.domain.dao.mapper.ResultSetHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.danko.provider.domain.dao.ColumnName.*;

public class UserResultSetHandler implements ResultSetHandler<User> {

    @Override
    public User resultToObject(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong(USER_ID);
        String firstName = resultSet.getString(USER_FIRST_NAME);
        String lastName = resultSet.getString(USER_LAST_NAME);
        String patronymic = resultSet.getString(USER_PATRONYMIC);
        String contractNumber = resultSet.getString(USER_CONTRACT_NUMBER);
        LocalDateTime contractDate = (resultSet.getTimestamp(USER_CONTRACT_DATE)).toLocalDateTime();
        BigDecimal balance = resultSet.getBigDecimal(USER_BALANCE);
        String name = resultSet.getString(USER_NAME);
        String email = resultSet.getString(USER_EMAIL);
//        String activationCode = resultSet.getString(USER_ACTIVATION_CODE);
        BigDecimal traffic = resultSet.getBigDecimal(USER_TRAFFIC);
        UserRole role = UserRole.valueOf(resultSet.getString(USER_ROLES_ROLE));
        UserStatus status = UserStatus.valueOf(resultSet.getString(USER_STATUSES_STATUS));
//        boolean activationCodeUsed = resultSet.getBoolean(USER_ACTIVATE_CODE_USED);
        long tariffId = resultSet.getLong(USER_TARIFF_ID);
        Tariff tariff = null;
        User user = User.builder().setUserId(userId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPatronymic(patronymic)
                .setContractNumber(contractNumber)
                .setContractDate(contractDate)
                .setBalance(balance)
                .setName(name)
                .setEmail(email)
//                .setActivationCode(activationCode)
                .setRole(role)
                .setStatus(status)
//                .setActivationCodeUsed(activationCodeUsed)
                .setTraffic(traffic)
                .setTariffId(tariffId)
                .setTariff(tariff)
                .build();
        return user;
    }
}
