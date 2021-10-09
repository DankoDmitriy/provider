package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.statisticEntity.UserCountStatistic;
import com.danko.provider.exception.DaoException;

import java.util.List;

public interface UserCountStatisticDao extends BaseDao<Long, UserCountStatistic> {
    List<UserCountStatistic> findAllUsersOnTariffsStatistic() throws DaoException;

    List<UserCountStatistic> findAllUsersByStatusStatistic() throws DaoException;
}
