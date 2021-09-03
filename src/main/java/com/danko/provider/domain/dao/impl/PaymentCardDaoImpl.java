package com.danko.provider.domain.dao.impl;

import com.danko.provider.connection.ConnectionPool;
import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.mapper.impl.PaymentCardResultSetHandler;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PaymentCardDaoImpl implements PaymentCardDao {
    private static Logger logger = LogManager.getLogger();
    private JdbcTemplate<PaymentCard> jdbcTemplate;
    private static final String SQL_FIND_ALL_PAYMENT_CARDS = """
            SELECT
            card_id, amount, activation_date, cs.status
            FROM
            express_payment_cards            
            JOIN
            card_status AS cs ON express_payment_cards.card_status_card_status_id = cs.card_status_id
            """;

    private static final String SQL_ADD_PAYMENT_CARD = """
            INSERT INTO express_payment_cards 
            (amount, card_number, card_pin, card_status_card_status_id)
            VALUES (?,?,?,
            (select card_status_id from card_status where status=?))
            """;

    private static final String SQL_FIND_PAYMENT_CARD_BY_NUMBER_AND_PIN = """
            SELECT
            card_id, amount, activation_date, cs.status
            FROM
            express_payment_cards            
            JOIN
            card_status AS cs ON express_payment_cards.card_status_card_status_id = cs.card_status_id
            WHERE
            card_number=? AND card_pin=?
            """;

    private static final String SQL_FIND_PAYMENT_CARD_BY_USER_ID = """
            SELECT
            card_id, amount, activation_date, cs.status
            FROM
            express_payment_cards            
            JOIN
            card_status AS cs ON express_payment_cards.card_status_card_status_id = cs.card_status_id
            WHERE
            users_user_id=?
            """;

    private static final String SQL_FIND_PAYMENT_CARD_BY_NUMBER = """
            SELECT
            card_id, amount, activation_date, cs.status
            FROM
            express_payment_cards            
            JOIN
            card_status AS cs ON express_payment_cards.card_status_card_status_id = cs.card_status_id
            WHERE
            card_number=?
            """;

    private static final String SQL_ACTIVATE_PAYMENT_CARD = """
            UPDATE express_payment_cards SET
            users_user_id=?, activation_date=?,
            card_status_card_status_id=(select card_status_id from card_status where status=?)
            WHERE 
            card_id=?
            """;

    public PaymentCardDaoImpl() {
        jdbcTemplate = new JdbcTemplate<PaymentCard>(ConnectionPool.getInstance(), new PaymentCardResultSetHandler());
    }

    @Override
    public List<PaymentCard> findAll() throws DaoException {
        return jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_PAYMENT_CARDS);
    }

    @Override
    public Optional<PaymentCard> findById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(PaymentCard paymentCard) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long add(PaymentCard paymentCard) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws DaoException {
        return jdbcTemplate.executeSelectQueryForSingleResult
                (SQL_FIND_PAYMENT_CARD_BY_NUMBER_AND_PIN, cardNumber, cardPin);
    }

    @Override
    public List<PaymentCard> findByUserId(long userId) throws DaoException {
        return jdbcTemplate.executeSelectQuery(SQL_FIND_PAYMENT_CARD_BY_USER_ID, userId);
    }

    @Override
    public Optional<PaymentCard> findByCardNumber(String cardNumber) throws DaoException {
        return jdbcTemplate.executeSelectQueryForSingleResult(SQL_FIND_PAYMENT_CARD_BY_NUMBER, cardNumber);
    }

    @Override
    public boolean activateCard(long cardId, long userId, LocalDateTime activationDate) throws DaoException {
        boolean result;
        result = jdbcTemplate.executeUpdateQuery(SQL_ACTIVATE_PAYMENT_CARD,
                userId,
                Timestamp.valueOf(activationDate),
                PaymentCard.CardStatus.USED.name(),
                cardId
        );
        return result;
    }

    @Override
    public void add(BigDecimal amount, String cardNumber, String cardPin, PaymentCard.CardStatus cardStatus) throws DaoException {
        jdbcTemplate.executeInsertQuery(SQL_ADD_PAYMENT_CARD, amount, cardNumber, cardPin, cardStatus.name());
    }
}
