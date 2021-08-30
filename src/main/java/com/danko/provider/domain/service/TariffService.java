package com.danko.provider.domain.service;

import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.domain.entity.User;
import com.danko.provider.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TariffService {
    List<Tariff> findAllTariffs() throws ServiceException;

    Optional<Tariff> findById(long id) throws ServiceException;

    List<Tariff> findAllByStatus(TariffStatus status) throws ServiceException;
}
