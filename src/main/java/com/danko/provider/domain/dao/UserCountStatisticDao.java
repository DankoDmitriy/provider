package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.UserCountStatistic;
import com.danko.provider.exception.DaoException;

import java.util.List;

/**
 * Dao for statistic of users
 */
public interface UserCountStatisticDao extends BaseDao<Long, UserCountStatistic> {
    /**
     * select all statistic by users on tariffs
     *
     * @return list of AccountTransaction or empty list
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<UserCountStatistic> findAllUsersOnTariffsStatistic() throws DaoException;

    /**
     * select all statistic by users status
     *
     * @return list of AccountTransaction or empty list
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<UserCountStatistic> findAllUsersByStatusStatistic() throws DaoException;
}
