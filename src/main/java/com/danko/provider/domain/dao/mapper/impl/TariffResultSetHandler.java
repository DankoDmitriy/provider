package com.danko.provider.domain.dao.mapper.impl;

import com.danko.provider.domain.entity.PeriodicityWriteOff;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.dao.mapper.ResultSetHandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.danko.provider.domain.dao.ColumnName.PERIODICITY_PERIOD;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_DESCRIPTION;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_MAX_SPEED;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_MIN_SPEED;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_PRICE;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_STATUSES_STATUS;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_TARIFF_ID;
import static com.danko.provider.domain.dao.ColumnName.TARIFF_TRAFFIC;

public class TariffResultSetHandler implements ResultSetHandler<Tariff> {
    @Override
    public Tariff resultToObject(ResultSet resultSet) throws SQLException {
        long tariffId = resultSet.getLong(TARIFF_TARIFF_ID);
        String description = resultSet.getString(TARIFF_DESCRIPTION);
        int maxSpeed = resultSet.getInt(TARIFF_MAX_SPEED);
        int mixSpeed = resultSet.getInt(TARIFF_MIN_SPEED);
        BigDecimal traffic = resultSet.getBigDecimal(TARIFF_TRAFFIC);
        BigDecimal price = resultSet.getBigDecimal(TARIFF_PRICE);
        TariffStatus status = TariffStatus.valueOf(resultSet.getString(TARIFF_STATUSES_STATUS));
        PeriodicityWriteOff period = PeriodicityWriteOff.valueOf(resultSet.getString(PERIODICITY_PERIOD));
        Tariff tariff = Tariff.builder()
                .setTariffId(tariffId)
                .setDescription(description)
                .setMaxSpeed(maxSpeed)
                .setMinSpeed(mixSpeed)
                .setTraffic(traffic)
                .setPrice(price)
                .setStatus(status)
                .setPeriod(period)
                .build();
        return tariff;
    }
}
