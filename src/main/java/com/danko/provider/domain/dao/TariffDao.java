package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.UserRole;
import com.danko.provider.exception.DaoException;

import java.util.List;

public interface TariffDao extends BaseDao<Long, Tariff> {
    List<Tariff> findAllByStatus(TariffStatus status) throws DaoException;

    long rowsInTable() throws DaoException;

    List<Tariff> findAllPageLimit(long startPosition, long rows) throws DaoException;
}
