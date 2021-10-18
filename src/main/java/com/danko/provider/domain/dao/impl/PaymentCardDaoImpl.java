package com.danko.provider.domain.dao.impl;

import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.PaymentCardDao;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.dao.mapper.impl.PaymentCardResultSetHandler;
import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.danko.provider.domain.dao.ColumnName.PAYMENT_CARD_USER_ID;

public class PaymentCardDaoImpl implements PaymentCardDao {

    private static Logger logger = LogManager.getLogger();
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
            (amount, card_number, card_pin, activation_date, card_status_card_status_id)
            VALUES (?,?,?,?,
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
    private static final String SQL_GET_USER_ID_ACTIVATED_THIS_CARD = """
            SELECT
            users_user_id
            FROM
            express_payment_cards            
            WHERE
            card_id=?
            """;

    private JdbcTemplate<PaymentCard> jdbcTemplate;

    public PaymentCardDaoImpl() {
        jdbcTemplate = new JdbcTemplate<PaymentCard>(new PaymentCardResultSetHandler());
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
    public void add(BigDecimal amount, String cardNumber, String cardPin, PaymentCard.CardStatus cardStatus, LocalDateTime expiredDate) throws DaoException {
        jdbcTemplate.executeInsertQuery(SQL_ADD_PAYMENT_CARD, amount, cardNumber, cardPin, expiredDate, cardStatus.name());
    }

    @Override
    public long getUserIdActivatedCard(long cardId) throws DaoException {
        TransactionManager transactionManager = TransactionManager.getInstance();
        Connection connection = transactionManager.getConnection();
        long userId = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_ID_ACTIVATED_THIS_CARD)) {
            statement.setObject(1, cardId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                userId = resultSet.getLong(PAYMENT_CARD_USER_ID);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error...Message: {}", e.getMessage());
            throw new DaoException(e);
        }
        return userId;
    }
}
