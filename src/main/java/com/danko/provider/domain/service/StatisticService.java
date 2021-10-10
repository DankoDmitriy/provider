package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.exception.ServiceException;

/**
 * The interface Statistic service.
 */
public interface StatisticService {
    /**
     * Search full base statistic.
     *
     * @param content the content
     * @throws ServiceException the service exception
     */
    void searchFullBaseStatistic(SessionRequestContent content) throws ServiceException;
}
