package com.danko.provider.domain.dao;

import com.danko.provider.pool.ConnectionPool;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.DatabaseConnectionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class manages the process of conducting transactions.
 */
public class TransactionManager {
    private static Logger logger = LogManager.getLogger();
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static TransactionManager instance;
    private static final AtomicBoolean isTransactionManagerCreated = new AtomicBoolean(false);
    private ConnectionPool connectionPool;

    private TransactionManager(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static TransactionManager getInstance() {
        while (instance == null) {
            if (isTransactionManagerCreated.compareAndSet(false, true)) {
                instance = new TransactionManager(ConnectionPool.getInstance());
            }
        }
        return instance;
    }

    private void initialValue() throws DaoException {
        try {
            connectionThreadLocal.set(connectionPool.getConnection());
        } catch (DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
    }

    /**
     * The method receives the connection from the connection poll and begins the transaction process.
     *
     * @throws DaoException is thrown when error while query execution occurs
     */
    public void startTransaction() throws DaoException {
        if (connectionThreadLocal.get() == null) {
            initialValue();
        }
        Connection connection = connectionThreadLocal.get();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception in method setAutoCommit: {}", e);
            throw new DaoException("SQL exception in method setAutoCommit.", e);
        }
    }

    /**
     * The method remove connection from the connection poll and finishing the transaction process.
     *
     * @throws DaoException is thrown when error while query execution occurs
     */
    public void endTransaction() throws DaoException {
        Connection connection = connectionThreadLocal.get();
        try {
            connectionThreadLocal.remove();
            connection.setAutoCommit(true);
            connection.close();
            connection = null;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception in method close: {}", e);
            throw new DaoException("SQL exception in method close.", e);
        }
    }

    /**
     * The method commit the transaction.
     *
     * @throws DaoException is thrown when error while query execution occurs
     */
    public void commit() throws DaoException {
        Connection connection = connectionThreadLocal.get();
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception in method commit: {}", e);
            throw new DaoException("SQL exception in method commit.", e);
        }
    }

    /**
     * The method rollback the transaction.
     *
     * @throws DaoException is thrown when error while query execution occurs
     */
    public void rollback() throws DaoException {
        Connection connection = connectionThreadLocal.get();
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception in method rollback: {}", e);
            throw new DaoException("SQL exception in method rollback.", e);
        }
    }

    public Connection getConnection() {
        return connectionThreadLocal.get();
    }
}
