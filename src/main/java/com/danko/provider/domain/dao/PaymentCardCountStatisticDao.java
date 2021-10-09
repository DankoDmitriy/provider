package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.statisticEntity.PaymentCardCountStatistic;
import com.danko.provider.exception.DaoException;

import java.util.List;

public interface PaymentCardCountStatisticDao extends BaseDao<Long, PaymentCardCountStatistic> {
    List<PaymentCardCountStatistic> findFullStatisticByGeneratedPaymentCards() throws DaoException;

    List<PaymentCardCountStatistic> findAllStatisticNotActivatedPaymentCards() throws DaoException;
}
