package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.PaymentCardCountStatisticDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserCountStatisticDao;
import com.danko.provider.domain.entity.BaseStatistic;
import com.danko.provider.domain.service.StatisticService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;

import static com.danko.provider.controller.command.PageUrl.ADMIN_BASE_STATISTIC;
import static com.danko.provider.controller.command.RequestAttribute.ADMIN_BASE_STATISTIC_RESULT;

public class StatisticServiceImpl implements StatisticService {
    private final PaymentCardCountStatisticDao paymentCardCountStatisticDao;
    private final UserCountStatisticDao userCountStatisticDao;
    private final TransactionManager transactionManager;

    public StatisticServiceImpl(PaymentCardCountStatisticDao paymentCardCountStatisticDao,
                                UserCountStatisticDao userCountStatisticDao,
                                TransactionManager transactionManager) {
        this.paymentCardCountStatisticDao = paymentCardCountStatisticDao;
        this.userCountStatisticDao = userCountStatisticDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public void searchFullBaseStatistic(SessionRequestContent content) throws ServiceException {
        try {
            try {
                BaseStatistic baseStatistic = new BaseStatistic();
                transactionManager.startTransaction();
                baseStatistic.setUsersOnTariffs(userCountStatisticDao.findAllUsersOnTariffsStatistic());
                baseStatistic.setUsersByStatus(userCountStatisticDao.findAllUsersByStatusStatistic());
                baseStatistic.setPaymentCardsGenerated(paymentCardCountStatisticDao.findFullStatisticByGeneratedPaymentCards());
                baseStatistic.setPaymentCardsNotActivated(paymentCardCountStatisticDao.findAllStatisticNotActivatedPaymentCards());
                content.putRequestAttribute(ADMIN_BASE_STATISTIC_RESULT, baseStatistic);
                content.setPageUrl(ADMIN_BASE_STATISTIC);
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transactionManager.endTransaction();
            }
        } catch (DaoException | ServiceException e1) {
            throw new ServiceException(e1);
        }
    }
}
