package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.TariffDao;
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
    private TariffDao tariffDao = new TariffDaoImpl();

    @Override
    public List<Tariff> findAllTariffs() throws ServiceException {
        List<Tariff> list;
        try {
            list = tariffDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find tariffs in database: {}", e);
            throw new ServiceException("Could not find tariffs in database", e);
        }
        return list;
    }

    @Override
    public Optional<Tariff> findById(long id) throws ServiceException {
        Optional<Tariff> optionalTariff = Optional.empty();
        try {
            optionalTariff = tariffDao.findById(id);
            return optionalTariff;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Can't find tariff by id: {}", e);
            throw new ServiceException("Can't find tariff by id", e);
        }
    }

    @Override
    public List<Tariff> findAllByStatus(TariffStatus status) throws ServiceException {
        List<Tariff> list;
        try {
            list = tariffDao.findAllByStatus(status);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Could not find tariffs in database: {}", e);
            throw new ServiceException("Could not find tariffs in database", e);
        }
        return list;
    }
}
