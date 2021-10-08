package com.danko.provider.controller.listener;

import com.danko.provider.pool.ConnectionPool;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.DaoProvider;
import com.danko.provider.domain.service.ServiceProvider;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TransactionManager.getInstance();
        ConnectionPool.getInstance();
        DaoProvider.getInstance();
        ServiceProvider.getInstance();
        InputDataValidator.getInstance();
        logger.log(Level.INFO, "Context was created. Created: ConnectionPool,ServiceProvider,InputDataValidator.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyConnectionPool();
        logger.log(Level.INFO, "ConnectionPool was has destroyed.");
    }
}
