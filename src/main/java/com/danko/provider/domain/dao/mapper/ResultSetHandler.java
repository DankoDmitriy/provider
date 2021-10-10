package com.danko.provider.domain.dao.mapper;

import com.danko.provider.domain.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface of row mappers that map {@link ResultSet} to entity
 *
 * @param <T> type of entity must extends AbstractEntity
 */
public interface ResultSetHandler<T extends AbstractEntity> {
    /**
     * Maps <code>resultSet</code> to entity
     *
     * @param resultSet result set to be mapped
     * @return entity of type <code>T</code>
     * @throws SQLException is thrown when an error occurs while working with a <code>resultSet</code>
     */
    T resultToObject(ResultSet resultSet) throws SQLException;
}
