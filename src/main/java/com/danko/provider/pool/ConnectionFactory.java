package com.danko.provider.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Connection factory.
 */
class ConnectionFactory {
    private static Logger logger = LogManager.getLogger();
    private static final Properties dbProperties = new Properties();
    private static String PROPERTIES_FILE_PATH = "properties/mysql.properties";
    private static final String URL_PARAMETER = "url";
    private static final String DRIVER_PARAMETER = "driver";

    static {
        try {
            InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);

            if (inputStream == null) {
                logger.log(Level.FATAL, "Properties file for connection to data base is not correct. Patch: " + PROPERTIES_FILE_PATH);
                throw new RuntimeException("Properties file for connection to data base is not correct. Patch: " + PROPERTIES_FILE_PATH);
            }

            dbProperties.load(inputStream);
            String driver = dbProperties.getProperty(DRIVER_PARAMETER);
            if (driver == null) {
                logger.log(Level.FATAL, "Driver for database was not found in properties. Patch: " + PROPERTIES_FILE_PATH);
                throw new RuntimeException("Driver for database was not found in properties. Patch: " + PROPERTIES_FILE_PATH);
            }

            String url = dbProperties.getProperty(URL_PARAMETER);
            if (url == null) {
                logger.log(Level.FATAL, "The address to connect to the database was not found in properties. Patch: " + PROPERTIES_FILE_PATH);
                throw new RuntimeException("The address to connect to the database was not found in properties. Patch: " + PROPERTIES_FILE_PATH);
            }
        } catch (IOException e) {
            logger.log(Level.FATAL, "Fatal error..." + e.getMessage());
            throw new RuntimeException("Fatal error..." + e.getMessage());
        }
        logger.log(Level.INFO, "Properties for database have loaded...");
    }

    private ConnectionFactory() {
    }

    /**
     * Create connection connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbProperties.getProperty(URL_PARAMETER), dbProperties);
    }
}