package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.PaymentCardCountStatistic;
import com.danko.provider.exception.DaoException;

import java.util.List;

/**
 * Dao for statistic of payments cards
 */
public interface PaymentCardCountStatisticDao extends BaseDao<Long, PaymentCardCountStatistic> {
    /**
     * Select all statistic of all work time
     *
     * @return list of AccountTransaction or empty list
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<PaymentCardCountStatistic> findFullStatisticByGeneratedPaymentCards() throws DaoException;

    /**
     * Select all statistic of not activated payments card
     *
     * @return list of AccountTransaction or empty list
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<PaymentCardCountStatistic> findAllStatisticNotActivatedPaymentCards() throws DaoException;
}
