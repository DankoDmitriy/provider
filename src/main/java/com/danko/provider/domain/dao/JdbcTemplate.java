package com.danko.provider.domain.dao;

import com.danko.provider.domain.dao.mapper.ResultSetHandler;
import com.danko.provider.domain.entity.AbstractEntity;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class implements basic query operations
 *
 * @param <T> entity type extend AbstractEntity
 */
public class JdbcTemplate<T extends AbstractEntity> {
    private static Logger logger = LogManager.getLogger();
    private static final String COUNT_LINES_PARAMETER = "line";

    private ResultSetHandler<T> resultSetHandler;
    private TransactionManager transactionManager;

    public JdbcTemplate(ResultSetHandler resultSetHandler) {
        this.resultSetHandler = resultSetHandler;
        this.transactionManager = TransactionManager.getInstance();
    }

    /**
     * @param sqlQuery   sql query line
     * @param parameters for sql query
     * @return list of entity or empty list
     * @throws DaoException is thrown when error while query execution occurs
     */
    public List<T> executeSelectQuery(String sqlQuery, Object... parameters) throws DaoException {
        List<T> list = new ArrayList<>();
        Connection connection = transactionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery);) {
            setParametersInPreparedStatement(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = resultSetHandler.resultToObject(resultSet);
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error...Message: {}", e.getMessage());
            throw new DaoException(e);
        }
        return list;
    }

    /**
     * @param sqlQuery   sql query line
     * @param parameters for sql query
     * @return optional of entity
     * @throws DaoException is thrown when error while query execution occurs
     */
    public Optional<T> executeSelectQueryForSingleResult(String sqlQuery, Object... parameters) throws DaoException {
        List<T> list = executeSelectQuery(sqlQuery, parameters);
        if (!list.isEmpty()) {
            return Optional.of(list.get(0));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @param sqlQuery   sql query line
     * @param parameters for sql query
     * @return true when update process finish correct
     * @throws DaoException is thrown when error while query execution occurs
     */
    public boolean executeUpdateQuery(String sqlQuery, Object... parameters) throws DaoException {
        int result = 0;
        Connection connection = transactionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            setParametersInPreparedStatement(statement, parameters);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error...Message: {}", e.getMessage());
            throw new DaoException(e);
        }
        return result == 1;
    }

    /**
     * @param sqlQuery   sql query line
     * @param parameters for sql query
     * @return auto increment id
     * @throws DaoException is thrown when error while query execution occurs
     */
    public long executeInsertQuery(String sqlQuery, Object... parameters) throws DaoException {
        long generatedId = 0;
        Connection connection = transactionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);) {
            setParametersInPreparedStatement(statement, parameters);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            generatedId = resultSet.getLong(1);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error...Message: {}", e.getMessage());
            throw new DaoException(e);
        }
        return generatedId;
    }

    /**
     * @param sqlQuery   sql query line
     * @param parameters for sql query
     * @return count of found rows
     * @throws DaoException is thrown when error while query execution occurs
     */
    public long executeCountRows(String sqlQuery, Object... parameters) throws DaoException {
        long rowsCount = 0;
        Connection connection = transactionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            setParametersInPreparedStatement(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                rowsCount = resultSet.getLong(COUNT_LINES_PARAMETER);
            }
            return rowsCount;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error...Message: {}", e.getMessage());
            throw new DaoException(e);
        }
    }

    private void setParametersInPreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }
}
