package com.danko.provider.domain.dao.impl;

import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.PaymentCardCountStatisticDao;
import com.danko.provider.domain.dao.mapper.impl.PaymentCardCountStatisticResultSetHandler;
import com.danko.provider.domain.entity.statisticEntity.PaymentCardCountStatistic;
import com.danko.provider.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class PaymentCardCountStatisticDaoImpl implements PaymentCardCountStatisticDao {
    private JdbcTemplate<PaymentCardCountStatistic> jdbcTemplate;

    private static final String PAYMENT_CARD_GENERATED_ALL_SQL = """
            SELECT 
            amount as amount, 
            count(*) as countData, 
            sum(amount) as sum 
            FROM provider_base.express_payment_cards 
            GROUP BY 
            amount;
            """;

    private static final String PAYMENT_CARDS_NOT_USED_SQL = """
            SELECT 
            amount as amount, 
            count(*) as countData, 
            sum(amount) as sum
            FROM provider_base.express_payment_cards
            where card_status_card_status_id != (select card_status_id  from card_status where status="USED" )
            GROUP BY 
            amount;
            """;

    public PaymentCardCountStatisticDaoImpl() {
        jdbcTemplate = new JdbcTemplate<PaymentCardCountStatistic>(new PaymentCardCountStatisticResultSetHandler());
    }

    @Override
    public List<PaymentCardCountStatistic> findFullStatisticByGeneratedPaymentCards() throws DaoException {
        return jdbcTemplate.executeSelectQuery(PAYMENT_CARD_GENERATED_ALL_SQL);

    }

    @Override
    public List<PaymentCardCountStatistic> findAllStatisticNotActivatedPaymentCards() throws DaoException {
        return jdbcTemplate.executeSelectQuery(PAYMENT_CARDS_NOT_USED_SQL);
    }

    @Override
    public List<PaymentCardCountStatistic> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<PaymentCardCountStatistic> findById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(PaymentCardCountStatistic paymentCardCountStatistic) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long add(PaymentCardCountStatistic paymentCardCountStatistic) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
