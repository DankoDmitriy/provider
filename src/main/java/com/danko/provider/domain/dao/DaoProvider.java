package com.danko.provider.domain.dao;

import com.danko.provider.domain.dao.impl.AccountTransactionDaoImpl;
import com.danko.provider.domain.dao.impl.PaymentCardCountStatisticDaoImpl;
import com.danko.provider.domain.dao.impl.PaymentCardDaoImpl;
import com.danko.provider.domain.dao.impl.PaymentCardSerialDaoImpl;
import com.danko.provider.domain.dao.impl.TariffDaoImpl;
import com.danko.provider.domain.dao.impl.UserActionDaoImpl;
import com.danko.provider.domain.dao.impl.UserCountStatisticDaoImpl;
import com.danko.provider.domain.dao.impl.UserDaoImpl;

import java.util.concurrent.atomic.AtomicBoolean;

public class DaoProvider {
    private static DaoProvider instance;
    private static final AtomicBoolean isDaoProviderCreated = new AtomicBoolean(false);
    private AccountTransactionDao accountTransactionDao = new AccountTransactionDaoImpl();
    private PaymentCardDao paymentCardDao = new PaymentCardDaoImpl();
    private TariffDao tariffDao = new TariffDaoImpl();
    private UserActionDao userActionDao = new UserActionDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private PaymentCardSerialDao paymentCardSerialDao = new PaymentCardSerialDaoImpl();
    private PaymentCardCountStatisticDao paymentCardCountStatisticDao = new PaymentCardCountStatisticDaoImpl();
    private UserCountStatisticDao userCountStatisticDao = new UserCountStatisticDaoImpl();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        while (instance == null) {
            if (isDaoProviderCreated.compareAndSet(false, true)) {
                instance = new DaoProvider();
            }
        }
        return instance;
    }

    public AccountTransactionDao getAccountTransactionDao() {
        return accountTransactionDao;
    }

    public PaymentCardDao getPaymentCardDao() {
        return paymentCardDao;
    }

    public TariffDao getTariffDao() {
        return tariffDao;
    }

    public UserActionDao getUserActionDao() {
        return userActionDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PaymentCardSerialDao getPaymentCardSerialDao() {
        return paymentCardSerialDao;
    }

    public PaymentCardCountStatisticDao getPaymentCardCountStatisticDao() {
        return paymentCardCountStatisticDao;
    }

    public UserCountStatisticDao getUserCountStatisticDao() {
        return userCountStatisticDao;
    }
}
