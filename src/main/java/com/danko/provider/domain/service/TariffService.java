package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.entity.Tariff;
import com.danko.provider.domain.entity.TariffStatus;
import com.danko.provider.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tariff service.
 */
public interface TariffService {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Tariff> findById(long id) throws ServiceException;

    /**
     * Find all by status list.
     *
     * @param status the status
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Tariff> findAllByStatus(TariffStatus status) throws ServiceException;

    /**
     * Find all tariff for customer.
     *
     * @param content the content
     * @throws ServiceException the service exception
     */
    void findAllTariffForCustomer(SessionRequestContent content) throws ServiceException;

    /**
     * Add tariff.
     *
     * @param content the content
     * @throws ServiceException the service exception
     */
    void addTariff(SessionRequestContent content) throws ServiceException;

    /**
     * Update tariff.
     *
     * @param content the content
     * @throws ServiceException the service exception
     */
    void updateTariff(SessionRequestContent content) throws ServiceException;

    /**
     * Find page tariff.
     *
     * @param content    the content
     * @param rowsOnPage the rows on page
     * @throws ServiceException the service exception
     */
    void findPageTariff(SessionRequestContent content, long rowsOnPage) throws ServiceException;
}
