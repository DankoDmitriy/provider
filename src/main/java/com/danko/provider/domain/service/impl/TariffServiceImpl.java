package com.danko.provider.domain.service.impl;

import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.impl.TariffDaoImpl;
import com.danko.provider.domain.entity.PeriodicityWriteOff;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TariffServiceImpl implements TariffService {
    private static Logger logger = LogManager.getLogger();
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

    @Override
    public boolean addTariff(String tariffName,
                             String maxSpeed,
                             String minSpeed,
                             String traffic,
                             String price,
                             String tariffStatusStr,
                             String tariffPeriodWriteOfStr) throws ServiceException {
        boolean result = true;
        InputDataValidator validator = InputDataValidator.getInstance();
        if (
                validator.isTariffDescriptionValid(tariffName) &&
                        validator.isTariffSpeedValid(maxSpeed) &&
                        validator.isTariffSpeedValid(minSpeed) &&
                        validator.isTariffTrafficValid(traffic) &&
                        validator.isTariffPriceValid(price) &&
                        validator.isTariffStatusValid(tariffStatusStr) &&
                        validator.isPeriodicityWriteOffValid(tariffPeriodWriteOfStr)
        ) {
            Tariff tariff = Tariff.builder()
                    .setMaxSpeed(Integer.parseInt(maxSpeed))
                    .setMinSpeed(Integer.parseInt(minSpeed))
                    .setDescription(tariffName)
                    .setTraffic(new BigDecimal(traffic))
                    .setPrice(new BigDecimal(price))
                    .setStatus(TariffStatus.valueOf(tariffStatusStr))
                    .setPeriod(PeriodicityWriteOff.valueOf(tariffPeriodWriteOfStr)).build();
            try {
                transactionManager.startTransaction();
                tariffDao.add(tariff);
                transactionManager.commit();
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Could not add tariff in database: {}", e);
                throw new ServiceException("Could not find tariffs in database", e);
            } finally {
                try {
                    transactionManager.endTransaction();
                } catch (DaoException e) {
                    logger.log(Level.ERROR, "End transaction error: {}", e);
                    throw new ServiceException("End transaction error", e);
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
