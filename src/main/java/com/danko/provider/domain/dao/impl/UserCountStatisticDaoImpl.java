package com.danko.provider.domain.dao.impl;

import com.danko.provider.domain.dao.JdbcTemplate;
import com.danko.provider.domain.dao.UserCountStatisticDao;
import com.danko.provider.domain.dao.mapper.impl.UserActionResultSetHandler;
import com.danko.provider.domain.dao.mapper.impl.UserCountStatisticResultSetHandler;
import com.danko.provider.domain.entity.statisticEntity.UserCountStatistic;
import com.danko.provider.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class UserCountStatisticDaoImpl implements UserCountStatisticDao {
    private JdbcTemplate<UserCountStatistic> jdbcTemplate;

    private static final String USERS_ON_TARIFF_SQL = """
                SELECT 
                description as parameterName, 
                count(*) as countData 
                FROM tariffs 
                join users ON users.tariffs_tariff_id=tariff_id 
                GROUP BY 
                description 
            """;

    private static final String USERS_BY_STATUS_SQL = """
            SELECT 
            status as parameterName, 
            count(*) as countData 
            FROM user_statuses 
            join users as usr ON 
            usr.user_statuses_status_id=status_id and 
            usr.user_roles_role_id!=(select role_id from user_roles where role = "ADMIN" ) 
            GROUP BY 
            status            
            """;

    public UserCountStatisticDaoImpl() {
        jdbcTemplate = new JdbcTemplate<UserCountStatistic>(new UserCountStatisticResultSetHandler());
    }

    @Override
    public List<UserCountStatistic> findAllUsersOnTariffsStatistic() throws DaoException {
        return jdbcTemplate.executeSelectQuery(USERS_ON_TARIFF_SQL);
    }

    @Override
    public List<UserCountStatistic> findAllUsersByStatusStatistic() throws DaoException {
        return jdbcTemplate.executeSelectQuery(USERS_BY_STATUS_SQL);
    }

    @Override
    public List<UserCountStatistic> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserCountStatistic> findById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(UserCountStatistic userCountStatistic) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long add(UserCountStatistic userCountStatistic) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
