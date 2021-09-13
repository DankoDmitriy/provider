package com.danko.provider.domain.dao;


import com.danko.provider.domain.entity.PaymentCardSerial;
import com.danko.provider.exception.DaoException;

import java.util.Optional;

public interface PaymentCardSerialDao extends BaseDao<Long, PaymentCardSerial> {
    Optional<PaymentCardSerial> findBySeries(String series) throws DaoException;

    void add(String serial) throws DaoException;
}
