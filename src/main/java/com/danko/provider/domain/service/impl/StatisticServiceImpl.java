package com.danko.provider.domain.service.impl;

import com.danko.provider.controller.command.SessionRequestContent;
import com.danko.provider.domain.dao.PaymentCardCountStatisticDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.UserCountStatisticDao;
import com.danko.provider.domain.entity.BaseStatistic;
import com.danko.provider.domain.service.StatisticService;
import com.danko.provider.exception.DaoException;
import com.danko.provider.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.danko.provider.controller.command.PageUrl.ADMIN_BASE_STATISTIC;

public class StatisticServiceImpl implements StatisticService {
    private static final Logger logger = LogManager.getLogger();
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
                content.putRequestAttribute("baseStatistic", baseStatistic);
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
