package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Dao for express_payment_cards table
 */
public interface PaymentCardDao extends BaseDao<Long, PaymentCard> {
    /**
     * Finds entity
     *
     * @param cardNumber card
     * @param cardPin    card pin code
     * @return returns optional with entity or empty optional
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<PaymentCard> findByCardNumberAndPin(String cardNumber, String cardPin) throws DaoException;

    /**
     * Finds entity
     *
     * @param cardNumber card number
     * @return returns optional with entity or empty optional
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<PaymentCard> findByCardNumber(String cardNumber) throws DaoException;

    /**
     * Activated payment card
     *
     * @param cardId         card id
     * @param userId         user id
     * @param activationDate activation date
     * @return true when update process finish correct
     * @throws DaoException is thrown when error while query execution occurs
     */
    boolean activateCard(long cardId, long userId, LocalDateTime activationDate) throws DaoException;

    /**
     * Add entity
     *
     * @param amount      amount
     * @param cardNumber  card number
     * @param cardPin     card pin code
     * @param cardStatus  card status
     * @param expiredDate expired dates
     * @throws DaoException is thrown when error while query execution occurs
     */
    void add(BigDecimal amount,
             String cardNumber,
             String cardPin,
             PaymentCard.CardStatus cardStatus,
             LocalDateTime expiredDate) throws DaoException;

    /**
     * Get user identifier
     *
     * @param cardId card id
     * @return returns user identifier activate card
     * @throws DaoException is thrown when error while query execution occurs
     */
    long getUserIdActivatedCard(long cardId) throws DaoException;
}
