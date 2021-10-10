package com.danko.provider.domain.dao;


import com.danko.provider.domain.entity.PaymentCardSerial;
import com.danko.provider.exception.DaoException;

import java.util.Optional;

/**
 * Dao for express_payment_cards_series table
 */
public interface PaymentCardSerialDao extends BaseDao<Long, PaymentCardSerial> {
    /**
     * Search serial in table
     *
     * @param series serial
     * @return returns optional with entity or empty optional
     * @throws DaoException
     */
    Optional<PaymentCardSerial> findBySeries(String series) throws DaoException;

    /**
     * Add entity
     *
     * @param serial serial
     * @throws DaoException is thrown when error while query execution occurs
     */
    void add(String serial) throws DaoException;
}
