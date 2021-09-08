package com.danko.provider.domain.service;

import com.danko.provider.domain.dao.*;
import com.danko.provider.domain.dao.impl.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class DaoProvider {
    public static DaoProvider instance;
    private static final AtomicBoolean isDaoProviderCreated = new AtomicBoolean(false);
    private AccountTransactionDao accountTransactionDao = new AccountTransactionDaoImpl();
    private PaymentCardDao paymentCardDao = new PaymentCardDaoImpl();
    private TariffDao tariffDao = new TariffDaoImpl();
    private UserActionDao userActionDao = new UserActionDaoImpl();
    private UserDao userDao = new UserDaoImpl();

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
}
