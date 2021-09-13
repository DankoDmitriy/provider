package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.impl.TariffDaoImpl;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class TariffServiceImpl implements TariffService {
    private static Logger logger = LogManager.getLogger();
    //    private TariffDao tariffDao = new TariffDaoImpl();
    private TariffDao tariffDao;
    private TransactionManager transactionManager;

    public TariffServiceImpl(TariffDao tariffDao, TransactionManager transactionManager) {
        this.tariffDao = tariffDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Tariff> findAllTariffs() throws ServiceException {
        try {
            transactionManager.startTransaction();
            return tariffDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find tariffs in database: {}", e);
            throw new ServiceException("Could not find tariffs in database", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
    }

    @Override
    public Optional<Tariff> findById(long id) throws ServiceException {
        Optional<Tariff> optionalTariff = Optional.empty();
        try {
            transactionManager.startTransaction();
            return tariffDao.findById(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find tariff by id: {}", e);
            throw new ServiceException("Can't find tariff by id", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
    }

    @Override
    public List<Tariff> findAllByStatus(TariffStatus status) throws ServiceException {
        List<Tariff> list;
        try {
            transactionManager.startTransaction();
            return tariffDao.findAllByStatus(status);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find tariffs in database: {}", e);
            throw new ServiceException("Could not find tariffs in database", e);
        } finally {
            try {
                transactionManager.endTransaction();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "End transaction error: {}", e);
                throw new ServiceException("End transaction error", e);
            }
        }
    }
}
