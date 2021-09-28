package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TariffService {
    List<Tariff> findAllTariffs() throws ServiceException;

    Optional<Tariff> findById(long id) throws ServiceException;

    List<Tariff> findAllByStatus(TariffStatus status) throws ServiceException;

    void findAllTariffForCustomer(SessionRequestContent content) throws ServiceException;

    void addTariff(SessionRequestContent content) throws ServiceException;

    void update(SessionRequestContent content) throws ServiceException;

    void findPageTariff(SessionRequestContent content, long rowsOnPage) throws ServiceException;
}
