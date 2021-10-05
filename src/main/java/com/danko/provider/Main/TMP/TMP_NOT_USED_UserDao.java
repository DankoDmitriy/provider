package com.danko.provider.Main.TMP;

public class TMP_NOT_USED_UserDao {

//    List<User> findAllByRole(UserRole role) throws DaoException;

//    @Override
//    public List<User> findAllByRole(UserRole role) throws DaoException {
//        return jdbcTemplate.executeSelectQuery(SQL_FIND_ALL_USERS_BY_ROLE, role.name());
//    }

//    private static final String SQL_FIND_ALL_USERS_BY_ROLE = """
//            SELECT
//            user_id, first_name, last_name, patronymic, contract_number, contract_date, balance, name, email,
//            traffic, ur.role, us.status, tariffs_tariff_id
//            FROM
//            users
//            JOIN
//            user_roles AS ur ON users.user_roles_role_id = ur.role_id
//            JOIN
//            user_statuses AS us ON users.user_statuses_status_id = us.status_id
//            WHERE
//            role_id=(select role_id from user_roles where role=?)
//            ORDER BY
//            user_statuses_status_id
//            """;

}
