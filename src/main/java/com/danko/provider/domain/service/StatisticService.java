package com.danko.provider.domain.service;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.exception.ServiceException;

public interface StatisticService {
    void searchFullBaseStatistic(SessionRequestContent content) throws ServiceException;
}
