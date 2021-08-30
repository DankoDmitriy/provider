package com.danko.provider.domain.dao;

import com.danko.provider.domain.entity.AccountTransaction;
import com.danko.provider.exception.DaoException;

import java.util.List;

public interface AccountTransactionDao extends BaseDao<Long, AccountTransaction>{
    List<AccountTransaction> findAllByUserId(long userId) throws DaoException;
    List<AccountTransaction> findAllByUserIdLimit(long userId) throws DaoException;
}
