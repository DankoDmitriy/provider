package com.danko.provider.domain.dao.impl;

import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.PaymentCardSerialDao;
import com.danko.provider.domain.dao.mapper.impl.PaymentCardSerialResultSetHandler;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.domain.entity.PaymentCardSerial;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PaymentCardSerialDaoImpl implements PaymentCardSerialDao {
    private static Logger logger = LogManager.getLogger();
    private JdbcTemplate<PaymentCardSerial> jdbcTemplate;

    private static final String SQL_ADD_PAYMENT_CARD_SERIAL = """
            INSERT INTO express_payment_cards_series 
            (series)
            VALUES (?)
            """;

    private static final String SQL_FIND_BY_SERIAL = """
            SELECT
            series_id, series
            FROM
            express_payment_cards_series
            WHERE
            series=?
            """;

    public PaymentCardSerialDaoImpl() {
        jdbcTemplate = new JdbcTemplate<PaymentCardSerial>(new PaymentCardSerialResultSetHandler());
    }

    @Override
    public List<PaymentCardSerial> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<PaymentCardSerial> findById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(PaymentCardSerial paymentCardSerial) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long add(PaymentCardSerial paymentCardSerial) throws DaoException {
        return 0;
    }

    @Override
    public Optional<PaymentCardSerial> findBySeries(String series) throws DaoException {
        return jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_BY_SERIAL, series);
    }

    @Override
    public void add(String serial) throws DaoException {
        jdbcTemplate.executeInsertQuery(SQL_ADD_PAYMENT_CARD_SERIAL, serial);
    }
}
