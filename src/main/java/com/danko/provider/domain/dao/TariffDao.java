package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.exception.DaoException;

import java.util.List;

/**
 * Dao for tariffs table
 */
public interface TariffDao extends BaseDao<Long, Tariff> {
    /**
     * select all tariffs by status
     *
     * @param status tariff status
     * @return list of found entities or empty list.
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Tariff> findAllByStatus(TariffStatus status) throws DaoException;

    /**
     * Count rows in table
     *
     * @return rows in table
     * @throws DaoException is thrown when error while query execution occurs
     */
    long rowsInTable() throws DaoException;

    /**
     * Method select data for pagination
     *
     * @param startPosition Sampling start position
     * @param rows          count select rows from table
     * @return list of found entities or empty list.
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Tariff> findAllPageLimit(long startPosition, long rows) throws DaoException;
}
