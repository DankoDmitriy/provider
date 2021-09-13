package com.danko.provider.domain.service;

import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.User;
import com.danko.provider.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static com.danko.provider.controller.command.ParamName.*;

public interface TariffService {
    List<Tariff> findAllTariffs() throws ServiceException;

    Optional<Tariff> findById(long id) throws ServiceException;

    List<Tariff> findAllByStatus(TariffStatus status) throws ServiceException;

    boolean addTariff(String tariffName,
                      String maxSpeed,
                      String minSpeed,
                      String traffic,
                      String price,
                      String tariffStatusStr,
                      String tariffPeriodWriteOfStr) throws ServiceException;
}
