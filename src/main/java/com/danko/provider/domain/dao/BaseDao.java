package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.AbstractEntity;
import com.danko.provider.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends AbstractEntity> {
    List<T> findAll() throws DaoException;

    Optional<T> findById(K id) throws DaoException;

    boolean update(T t) throws DaoException;

    long add(T t) throws DaoException;
}
