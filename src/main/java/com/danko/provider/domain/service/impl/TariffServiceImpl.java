package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.Router;
import com.danko.provider.controller.command.InputContent;
import com.danko.provider.domain.dao.TariffDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.entity.*;
import com.danko.provider.domain.service.TariffService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import com.danko.provider.validator.InputDataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.PageUrl.*;
import static com.danko.provider.controller.command.ParamName.*;
import static com.danko.provider.controller.command.RequestAttribute.*;

public class TariffServiceImpl implements TariffService {
    private static final Logger logger = LogManager.getLogger();
    private static final List<TariffStatus> TARIFF_STATUSES = Arrays.asList(TariffStatus.values());
    private static final List<PeriodicityWriteOff> PERIODICITY_WRITE_OFFS = Arrays.asList(PeriodicityWriteOff.values());
    private final TariffDao tariffDao;
    private final TransactionManager transactionManager;
    private final InputDataValidator validator;

    public TariffServiceImpl(TariffDao tariffDao, TransactionManager transactionManager) {
        this.tariffDao = tariffDao;
        this.transactionManager = transactionManager;
        this.validator = InputDataValidator.getInstance();
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
    public void addTariff(InputContent content) throws ServiceException {
        String[] tariffName = content.getRequestParameter(TARIFF_ADD_NAME);
        String[] maxSpeed = content.getRequestParameter(TARIFF_ADD_MAX_SPEED);
        String[] minSpeed = content.getRequestParameter(TARIFF_ADD_MIN_SPEED);
        String[] traffic = content.getRequestParameter(TARIFF_ADD_TRAFFIC);
        String[] price = content.getRequestParameter(TARIFF_ADD_PRICE);
        String[] tariffStatusStr = content.getRequestParameter(TARIFF_ADD_STATUS);
        String[] tariffPeriodWriteOfStr = content.getRequestParameter(TARIFF_ADD_PERIOD);

        if (tariffName != null
                && maxSpeed != null
                && minSpeed != null
                && traffic != null
                && price != null
                && tariffStatusStr != null
                && tariffPeriodWriteOfStr != null
                && validator.isTariffDescriptionValid(tariffName[0])
                && validator.isTariffSpeedValid(maxSpeed[0])
                && validator.isTariffSpeedValid(minSpeed[0])
                && validator.isTariffTrafficValid(traffic[0])
                && validator.isTariffPriceValid(price[0])
                && validator.isTariffStatusValid(tariffStatusStr[0])
                && validator.isPeriodicityWriteOffValid(tariffPeriodWriteOfStr[0])
        ) {
            Tariff tariff = Tariff.builder()
                    .setMaxSpeed(Integer.parseInt(maxSpeed[0]))
                    .setMinSpeed(Integer.parseInt(minSpeed[0]))
                    .setDescription(tariffName[0])
                    .setTraffic(new BigDecimal(traffic[0]))
                    .setPrice(new BigDecimal(price[0]))
                    .setStatus(TariffStatus.valueOf(tariffStatusStr[0]))
                    .setPeriod(PeriodicityWriteOff.valueOf(tariffPeriodWriteOfStr[0])).build();
            try {
                try {
                    transactionManager.startTransaction();
                    tariffDao.add(tariff);
                    transactionManager.commit();
                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                } finally {
                    transactionManager.endTransaction();
                }
            } catch (DaoException | ServiceException e) {
                throw new ServiceException(e);
            }
            content.setRedirect(true);
            content.setPageUrl(ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
        } else {
            List<TariffStatus> tariffStatuses = Arrays.asList(TariffStatus.values());
            List<PeriodicityWriteOff> periodicityWriteOffs = Arrays.asList(PeriodicityWriteOff.values());
            content.putRequestAttribute(ADMIN_TARIFF_STATUS_LIST_FOR_NEW_TARIFF, tariffStatuses);
            content.putRequestAttribute(ADMIN_TARIFF_WRITE_OFF_LIST_FOR_NEW_TARIFF, periodicityWriteOffs);
            content.setPageUrl(ADMIN_TARIFF_ADD_PAGE);
        }

    }

    @Override
    public void update(InputContent content) throws ServiceException {
        InputDataValidator validator = InputDataValidator.getInstance();

        String[] tariffId = content.getRequestParameter(TARIFF_EDIT_ID);
        String[] tariffName = content.getRequestParameter(TARIFF_EDIT_NAME);
        String[] maxSpeed = content.getRequestParameter(TARIFF_EDIT_MAX_SPEED);
        String[] minSpeed = content.getRequestParameter(TARIFF_EDIT_MIN_SPEED);
        String[] traffic = content.getRequestParameter(TARIFF_EDIT_TRAFFIC);
        String[] price = content.getRequestParameter(TARIFF_EDIT_PRICE);
        String[] tariffStatusStr = content.getRequestParameter(TARIFF_EDIT_STATUS);
        String[] tariffPeriodWriteOfStr = content.getRequestParameter(TARIFF_EDIT_PERIOD);
        try {
            try {
                if (tariffId != null &&
                        tariffName != null &&
                        maxSpeed != null &&
                        minSpeed != null &&
                        traffic != null &&
                        price != null &&
                        tariffStatusStr != null &&
                        tariffPeriodWriteOfStr != null &&
                        validator.isIdValid(tariffId[0]) &&
                        validator.isTariffDescriptionValid(tariffName[0]) &&
                        validator.isTariffSpeedValid(maxSpeed[0]) &&
                        validator.isTariffSpeedValid(minSpeed[0]) &&
                        validator.isTariffTrafficValid(traffic[0]) &&
                        validator.isTariffPriceValid(price[0]) &&
                        validator.isTariffStatusValid(tariffStatusStr[0]) &&
                        validator.isPeriodicityWriteOffValid(tariffPeriodWriteOfStr[0])
                ) {
                    Tariff tariff = Tariff.builder()
                            .setTariffId(Long.parseLong(tariffId[0]))
                            .setMaxSpeed(Integer.parseInt(maxSpeed[0]))
                            .setMinSpeed(Integer.parseInt(minSpeed[0]))
                            .setDescription(tariffName[0])
                            .setTraffic(new BigDecimal(traffic[0]))
                            .setPrice(new BigDecimal(price[0]))
                            .setStatus(TariffStatus.valueOf(tariffStatusStr[0]))
                            .setPeriod(PeriodicityWriteOff.valueOf(tariffPeriodWriteOfStr[0])).build();

                    Tariff tariffOrigin = stringToObjectTariff(content.getRequestParameter(TARIFF_EDIT_ORIGIN)[0]);
                    if (!tariffOrigin.equals(tariff)) {
                        transactionManager.startTransaction();
                        tariffDao.update(tariff);
                        transactionManager.commit();
                        content.putRequestAttribute(ADMIN_TARIFFS_LIST_RESULT_WORK_FOR_MESSAGE, true);
                    } else {
                        content.putRequestAttribute(ADMIN_TARIFFS_LIST_RESULT_WORK_FOR_MESSAGE, false);
                    }
                    List<Tariff> tariffs = Arrays.asList(tariff);
                    content.putRequestAttribute(PAGINATION_RESULT_LIST, tariffs);
                    content.setPageUrl(ADMIN_TARIFFS_LIST_PAGE);
                } else {
                    if (tariffId != null && validator.isIdValid(tariffId[0])) {
                        transactionManager.startTransaction();
                        Optional<Tariff> optionalTariff = tariffDao.findById(Long.parseLong(tariffId[0]));
                        if (!optionalTariff.isEmpty()) {
                            content.putRequestAttribute(ADMIN_TARIFF_STATUS_LIST, TARIFF_STATUSES);
                            content.putRequestAttribute(ADMIN_TARIFF_WRITE_OFF_LIST, PERIODICITY_WRITE_OFFS);
                            content.putRequestAttribute(ADMIN_TARIFF_EDIT, optionalTariff.get());
                            content.putRequestAttribute(ADMIN_TARIFF_EDIT_ORIGINAL, objectTariffToString(optionalTariff.get()));
                            content.setPageUrl(ADMIN_TARIFF_EDIT_PAGE);
                        } else {
                            content.setRedirect(true);
                            content.setPageUrl(ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
                        }
                    } else {
                        content.setRedirect(true);
                        content.setPageUrl(ADMIN_TARIFFS_LIST_PAGE_REDIRECT);
                    }
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }

    @Override
    public void findPageTariff(InputContent content, long rowsOnPage) throws ServiceException {
        List<Tariff> tariffs;
        long previewPage;
        long rowsInTable;
        long nextPage;
        long startPosition;
        String nextPageStr = content.getRequestParameter(PAGINATION_NEXT_PAGE)[0];
//        InputDataValidator inputDataValidator = InputDataValidator.getInstance();
        try {
            try {
                transactionManager.startTransaction();
                nextPage = Long.parseLong(nextPageStr);
                if (nextPage <= 0) {
                    rowsInTable = tariffDao.rowsInTable();
                    nextPage = 0;
                    previewPage = -1;
                    startPosition = nextPage * rowsOnPage;
                    tariffs = tariffDao.findAllPageLimit(startPosition, rowsOnPage);
                    if (rowsInTable > rowsOnPage) {
                        nextPage++;
                    }
                } else {
                    rowsInTable = tariffDao.rowsInTable();
                    startPosition = nextPage * rowsOnPage;
                    tariffs = tariffDao.findAllPageLimit(startPosition, rowsOnPage);
                    if (rowsInTable > (rowsOnPage * (nextPage + 1))) {
                        previewPage = nextPage - 1;
                        nextPage++;
                    } else {
                        previewPage = nextPage - 1;
                    }
                }
                content.putRequestAttribute(PAGINATION_NEXT_PAGE, nextPage);
                content.putRequestAttribute(PAGINATION_PREVIEW_PAGE, previewPage);
                content.putRequestAttribute(PAGINATION_RESULT_LIST, tariffs);
                content.setPageUrl(ADMIN_TARIFFS_LIST_PAGE);
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e) {
            throw new ServiceException(e);
        }
    }

    private String objectTariffToString(Tariff tariff) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(tariff);
            objectOutputStream.flush();
            return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }

    private Tariff stringToObjectTariff(String tariffStr) {
        try {
            byte[] objToBytes = Base64.getDecoder().decode(tariffStr);
            ByteArrayInputStream bais = new ByteArrayInputStream(objToBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Tariff) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARN, e);
            return null;
        }
    }

}
