package com.danko.provider.Main.TMP;

import com.danko.provider.domain.entity.UserAction;
import com.danko.provider.exception.DaoException;

import java.util.List;

public class TMP_NOT_USED_UserActionDao {
//    List<UserAction> findAllByUserId(long userId) throws DaoException;

//    @Override
//    public List<UserAction> findAllByUserId(long userId) throws DaoException {
//        List<UserAction> list;
//        list = jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_ACTIONS_BY_USER_ID, userId);
//        return list;
//    }

//    private static final String SQL_FIND_ALL_ACTIONS_BY_USER_ID = """
//            SELECT
//            action_id, date, at.type, tp.description
//            FROM
//            actions
//            JOIN
//            action_type AS at ON actions.action_type_type_id = at.action_type_id
//            JOIN
//            tariffs AS tp ON actions.tariffs_tariff_id = tp.tariff_id
//            WHERE
//            users_user_id=?
//            ORDER BY date DESC
//            """;


}
