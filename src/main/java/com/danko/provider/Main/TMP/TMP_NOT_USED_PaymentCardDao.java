package com.danko.provider.Main.TMP;

import com.danko.provider.domain.entity.PaymentCard;
import com.danko.provider.exception.DaoException;

import java.util.List;

public class TMP_NOT_USED_PaymentCardDao {

//    List<PaymentCard> findByUserId(long userId) throws DaoException;

//    @Override
//    public List<PaymentCard> findByUserId(long userId) throws DaoException {
//        return jdbcTemplate.executeSelectQuery(SQL_FIND_PAYMENT_CARD_BY_USER_ID, userId);
//    }

//    private static final String SQL_FIND_PAYMENT_CARD_BY_USER_ID = """
//            SELECT
//            card_id, amount, activation_date, cs.status
//            FROM
//            express_payment_cards
//            JOIN
//            card_status AS cs ON express_payment_cards.card_status_card_status_id = cs.card_status_id
//            WHERE
//            users_user_id=?
//            """;

}
