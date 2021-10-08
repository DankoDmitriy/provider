package com.danko.provider.pool;

import cn.hutool.core.lang.Assert;
import com.danko.provider.exception.DaoException;
import com.danko.provider.validator.InputDataValidator;
import org.easymock.EasyMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

import static org.testng.Assert.assertEquals;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool;
    private Connection connectionProxy;
    private boolean expected = false;

    @BeforeClass
    public void createConnectionPoolAndConnection() throws Exception {
        connectionPool = ConnectionPool.getInstance();
        connectionProxy = connectionPool.getConnection();
    }

    @Test
    public void getConnectionPositiveTest() throws Exception {
        Assert.notNull(connectionProxy);
        connectionPool.releaseConnection(connectionProxy);
    }

    @Test
    public void releaseConnectionNegativeTest() throws Exception {
        Connection connection = EasyMock.mock(Connection.class);
        boolean result = connectionPool.releaseConnection(connection);
        assertEquals(result, expected);
    }

    @AfterClass
    public void destroyConnectionPool() {
        connectionPool.destroyConnectionPool();
    }
}
