package com.danko.provider.domain.dao.impl;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.mapper.impl.TariffResultSetHandler;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class TariffDaoImpl implements TariffDao {
    private static Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_ALL_TARIFFS = """
            SELECT
            tariff_id, description, max_speed, min_speed, traffic, price, ts.status, pwo.period
            FROM
            tariffs
            JOIN
            tariff_statuses AS ts ON tariffs.tariff_statuses_status_id = ts.status_id
            JOIN
            periodicity_write_off AS pwo ON tariffs.periodicity_write_off_write_off_id = pwo.write_off_id
            """;

    private static final String SQL_FIND_TARIFF_BY_ID = """
            SELECT
            tariff_id, description, max_speed, min_speed, traffic, price, ts.status, pwo.period
            FROM
            tariffs
            JOIN
            tariff_statuses AS ts ON tariffs.tariff_statuses_status_id = ts.status_id
            JOIN
            periodicity_write_off AS pwo ON tariffs.periodicity_write_off_write_off_id = pwo.write_off_id
            WHERE
            tariff_id=?
            """;

    private static final String SQL_FIND_TARIFF_BY_STATUS = """
            SELECT
            tariff_id, description, max_speed, min_speed, traffic, price, ts.status, pwo.period
            FROM
            tariffs
            JOIN
            tariff_statuses AS ts ON tariffs.tariff_statuses_status_id = ts.status_id
            JOIN
            periodicity_write_off AS pwo ON tariffs.periodicity_write_off_write_off_id = pwo.write_off_id
            WHERE
            tariff_statuses_status_id=(SELECT status_id from tariff_statuses where status = ?)
            """;

    private static final String SQL_ADD_TARIFF = """
            INSERT INTO tariffs 
            (description, max_speed, min_speed, traffic, price, 
            tariff_statuses_status_id, periodicity_write_off_write_off_id)
            VALUES(?,?,?,?,?,
            (select status_id from tariff_statuses where status=?),
            (select write_off_id from periodicity_write_off where period=?)
            )
            """;

    private static final String SQL_UPDATE_TARIFF = """
            UPDATE  tariffs SET
            description=?, max_speed=?, min_speed=?, traffic=?, price=?, 
            tariff_statuses_status_id=(select status_id from tariff_statuses where status=?), 
            periodicity_write_off_write_off_id=(select write_off_id from periodicity_write_off where period=?)
            WHERE 
            tariff_id=?
            """;
    private JdbcTemplate<Tariff> jdbcTemplate;

    public TariffDaoImpl() {
        jdbcTemplate = new JdbcTemplate<Tariff>(ConnectionPool.getInstance(), new TariffResultSetHandler());
    }

    @Override
    public List<Tariff> findAll() throws DaoException {
        List<Tariff> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_TARIFFS);
        return list;
    }

    @Override
    public List<Tariff> findAllByStatus(TariffStatus status) throws DaoException {
        List<Tariff> list;
        list = jdbcTemplate.executeSelectQuery(SQL_FIND_TARIFF_BY_STATUS, status.name());
        return list;
    }

    @Override
    public Optional<Tariff> findById(Long id) throws DaoException {
        Optional<Tariff> tariffOptional = jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_TARIFF_BY_ID, id);
        return tariffOptional;
    }

    @Override
    public boolean update(Tariff tariff) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_UPDATE_TARIFF,
                tariff.getDescription(),
                tariff.getMaxSpeed(),
                tariff.getMinSpeed(),
                tariff.getTraffic(),
                tariff.getPrice(),
                tariff.getStatus().name(),
                tariff.getPeriod().name(),
                tariff.getTariffId());
        return result;
    }

    @Override
    public long add(Tariff tariff) throws DaoException {
        long generatedId = jdbcTemplate.executeInsertQuery(SQL_ADD_TARIFF,
                tariff.getDescription(),
                tariff.getMaxSpeed(),
                tariff.getMinSpeed(),
                tariff.getTraffic(),
                tariff.getPrice(),
                tariff.getStatus().name(),
                tariff.getPeriod().name()
        );
        tariff.setTariffId(generatedId);
        return generatedId;
    }
}
