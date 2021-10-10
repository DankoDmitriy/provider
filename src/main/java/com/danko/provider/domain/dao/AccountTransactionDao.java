package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.DaoException;

import java.util.List;

/**
 * Dao for account_transactions table
 */
public interface AccountTransactionDao extends BaseDao<Long, AccountTransaction> {
    /**
     * Method searches last finance transaction by user id
     *
     * @param userId user id
     * @return list of AccountTransaction or empty list. The limit must be defined in the implementation of the interface
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<AccountTransaction> findAllByUserIdLimit(long userId) throws DaoException;

    /**
     * Method returns rows in table by user id
     *
     * @param userId user id
     * @return count of found rows
     * @throws DaoException is thrown when error while query execution occurs
     */
    long rowsInTableForUser(long userId) throws DaoException;

    /**
     * Method select data for pagination
     *
     * @param userId        user id
     * @param startPosition Sampling start position
     * @param rows          count select rows from table
     * @return list of AccountTransaction or empty list
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<AccountTransaction> findAllByUserIdPageLimit(long userId, long startPosition, long rows) throws DaoException;

}
