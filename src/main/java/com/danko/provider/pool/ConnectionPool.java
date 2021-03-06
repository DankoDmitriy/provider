package com.danko.provider.pool;

import com.danko.provider.exception.DatabaseConnectionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Database connection pool
 */
public class ConnectionPool {
    private static Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static final int DEFAULT_POOL_SIZE = 5;
    private static final String POOL_SIZE_PROPERTIES = "pollSize";
    private static final String PROPERTIES_FILE_PATH = "properties/pool.properties";

    private static final AtomicBoolean isConnectionPoolCreated = new AtomicBoolean(false);

    private int poolSize;
    private BlockingQueue<ConnectionProxy> freeConnections;
    private BlockingQueue<ConnectionProxy> busyConnections;
    private boolean poolCloseFlag;

    private ConnectionPool() {
        Properties poolProperties = new Properties();
        try {
            InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);
            if (inputStream == null) {
                logger.log(Level.WARN, "Properties file for Connection pool is not correct. " +
                        "The default parameters are used. Patch: " + PROPERTIES_FILE_PATH);
                setDefaultParameters();
            } else {
                poolProperties.load(inputStream);
                poolSize = Integer.valueOf(poolProperties.getProperty(POOL_SIZE_PROPERTIES));
                if (poolSize <= 0) {
                    logger.log(Level.WARN, "Data in the properties file for the Connection pool is not correct. " +
                            "The default parameters are used. Patch: " + PROPERTIES_FILE_PATH);
                    setDefaultParameters();
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error with properties file." + e);
            setDefaultParameters();
        }

        freeConnections = new LinkedBlockingQueue<>(poolSize);
        busyConnections = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ConnectionProxy connectionProxy = new ConnectionProxy(connection);
                freeConnections.add(connectionProxy);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error with creating connection: {}", e.getMessage());
            }
        }
        if (freeConnections.isEmpty()) {
            logger.log(Level.FATAL, "Connection pool could not be created...");
            throw new RuntimeException("Connection pool could not be created...");
        } else if (freeConnections.size() == poolSize) {
            logger.log(Level.INFO, "Connection pool created.");
        } else if (freeConnections.size() < poolSize) {
            logger.log(Level.WARN, "Connection pool created, default pool size: {}, current size: {}",
                    poolSize, freeConnections.size());
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        while (instance == null) {
            if (isConnectionPoolCreated.compareAndSet(false, true)) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws DatabaseConnectionException the database connection exception
     */
    public Connection getConnection() throws DatabaseConnectionException {
        ConnectionProxy connection = null;
        if (!poolCloseFlag) {
            try {
                connection = freeConnections.take();
                try {
                    if (connection.isClosed()) {
                        connection = new ConnectionProxy(ConnectionFactory.createConnection());
                    }
                } catch (SQLException e) {
                    logger.log(Level.ERROR, "Error occurred while creating a connection instead of a previously closed one : {}", e.getMessage());
                }
                busyConnections.put(connection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "This thread has interrupted {} {}", e.getMessage(), e.getStackTrace());
                throw new DatabaseConnectionException(e.getMessage() + " " + e.getStackTrace());
            }
        }
        return connection;
    }

    /**
     * Release connection boolean.
     *
     * @param connection the connection
     * @return the boolean
     * @throws DatabaseConnectionException the database connection exception
     */
    public boolean releaseConnection(Connection connection) throws DatabaseConnectionException {
        boolean result = false;
        if (connection instanceof ConnectionProxy & busyConnections.contains(connection) & busyConnections.remove(connection) & !poolCloseFlag) {
            try {
                freeConnections.put((ConnectionProxy) connection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "This thread has interrupted {} {}", e.getMessage(), e.getStackTrace());
                throw new DatabaseConnectionException(e.getMessage() + " " + e.getStackTrace());
            }
            result = true;
        } else {
            logger.log(Level.ERROR, "Input connection is not instanceof ConnectionProxy or not included in the connection pool");
        }
        return result;
    }

    /**
     * Destroy connection pool.
     */
    public void destroyConnectionPool() {
        poolCloseFlag = true;
        destroyConnectionQueue(freeConnections);
        destroyConnectionQueue(busyConnections);
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error deregister driver {}: {} {}", driver, e.getMessage(), e.getStackTrace());
            }
        }
    }

    private void destroyConnectionQueue(BlockingQueue<ConnectionProxy> connections) {
        while (!connections.isEmpty()) {
            try {
                connections.take().reallyClose();
            } catch (SQLException e) {
                logger.log(Level.WARN, "Connection is not close:" + e.getMessage());

            } catch (InterruptedException e) {
                logger.log(Level.WARN, "Current thread was interrupted:" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    private void setDefaultParameters() {
        poolSize = DEFAULT_POOL_SIZE;
    }
}