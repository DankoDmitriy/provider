package com.danko.provider.pool;

import cn.hutool.core.lang.Assert;
import com.danko.provider.exception.DaoException;
import com.danko.provider.validator.InputDataValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool;
    private Connection connectionProxy;

    @BeforeClass
    public void createConnectionPoolAndConnection() throws Exception {
        connectionPool = ConnectionPool.getInstance();
        connectionProxy = connectionPool.getConnection();
    }

    @Test
    public void getConnection() throws Exception {
        Assert.notNull(connectionProxy);
        connectionPool.releaseConnection(connectionProxy);
    }

    @AfterClass
    public void destroyConnectionPool() {
        connectionPool.destroyConnectionPool();
    }
}
