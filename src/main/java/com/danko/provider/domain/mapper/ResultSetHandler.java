package com.danko.provider.domain.mapper;

import com.danko.provider.domain.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T extends AbstractEntity> {
    T resultToObject(ResultSet resultSet) throws SQLException;
}
