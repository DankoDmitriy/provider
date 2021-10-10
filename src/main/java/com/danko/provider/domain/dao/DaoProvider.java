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

/**
 * The class manages objects that implement the interface BaseDao. Release singleton pattern for all dao objects.
 */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoProvider getInstance() {
        while (instance == null) {
            if (isDaoProviderCreated.compareAndSet(false, true)) {
                instance = new DaoProvider();
            }
        }
        return instance;
    }

    /**
     * Gets account transaction dao.
     *
     * @return the account transaction dao
     */
    public AccountTransactionDao getAccountTransactionDao() {
        return accountTransactionDao;
    }

    /**
     * Gets payment card dao.
     *
     * @return the payment card dao
     */
    public PaymentCardDao getPaymentCardDao() {
        return paymentCardDao;
    }

    /**
     * Gets tariff dao.
     *
     * @return the tariff dao
     */
    public TariffDao getTariffDao() {
        return tariffDao;
    }

    /**
     * Gets user action dao.
     *
     * @return the user action dao
     */
    public UserActionDao getUserActionDao() {
        return userActionDao;
    }

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Gets payment card serial dao.
     *
     * @return the payment card serial dao
     */
    public PaymentCardSerialDao getPaymentCardSerialDao() {
        return paymentCardSerialDao;
    }

    /**
     * Gets payment card count statistic dao.
     *
     * @return the payment card count statistic dao
     */
    public PaymentCardCountStatisticDao getPaymentCardCountStatisticDao() {
        return paymentCardCountStatisticDao;
    }

    /**
     * Gets user count statistic dao.
     *
     * @return the user count statistic dao
     */
    public UserCountStatisticDao getUserCountStatisticDao() {
        return userCountStatisticDao;
    }
}
