package com.danko.provider.domain.service;

import com.danko.provider.domain.dao.DaoProvider;
import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.service.impl.AccountTransactionServiceImpl;
import com.danko.provider.domain.service.impl.EmailServiceImpl;
import com.danko.provider.domain.service.impl.PaymentCardServiceImpl;
import com.danko.provider.domain.service.impl.StatisticServiceImpl;
import com.danko.provider.domain.service.impl.TariffServiceImpl;
import com.danko.provider.domain.service.impl.UserActionServiceImpl;
import com.danko.provider.domain.service.impl.UserServiceImpl;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Service provider.
 */
public class ServiceProvider {
    private static ServiceProvider instance;
    private static final AtomicBoolean isServiceProviderCreated = new AtomicBoolean(false);
    private final UserService userService;
    private final TariffService tariffService;
    private final AccountTransactionService accountTransactionService;
    private final UserActionService userActionService;
    private final PaymentCardService paymentCardService;
    private final EmailService emailService;
    private final StatisticService statisticService;

    private ServiceProvider() {
        DaoProvider daoProvider = DaoProvider.getInstance();
        TransactionManager transactionManager = TransactionManager.getInstance();
        this.userService = new UserServiceImpl(
                daoProvider.getUserDao(),
                daoProvider.getTariffDao(),
                daoProvider.getUserActionDao(),
                daoProvider.getPaymentCardDao(),
                daoProvider.getAccountTransactionDao(),
                transactionManager);
        this.tariffService = new TariffServiceImpl(
                daoProvider.getTariffDao(),
                transactionManager);
        this.accountTransactionService = new AccountTransactionServiceImpl(
                daoProvider.getAccountTransactionDao(),
                transactionManager);
        this.userActionService = new UserActionServiceImpl(
                daoProvider.getUserActionDao(),
                transactionManager);
        this.paymentCardService = new PaymentCardServiceImpl(
                daoProvider.getPaymentCardDao(),
                daoProvider.getPaymentCardSerialDao(),
                daoProvider.getUserDao(),
                transactionManager);
        this.emailService = new EmailServiceImpl();

        this.statisticService = new StatisticServiceImpl(
                daoProvider.getPaymentCardCountStatisticDao(),
                daoProvider.getUserCountStatisticDao(),
                transactionManager);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ServiceProvider getInstance() {
        while (instance == null) {
            if (isServiceProviderCreated.compareAndSet(false, true)) {
                instance = new ServiceProvider();
            }
        }
        return instance;
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Gets tariff service.
     *
     * @return the tariff service
     */
    public TariffService getTariffService() {
        return tariffService;
    }

    /**
     * Gets account transaction service.
     *
     * @return the account transaction service
     */
    public AccountTransactionService getAccountTransactionService() {
        return accountTransactionService;
    }

    /**
     * Gets email service.
     *
     * @return the email service
     */
    public EmailService getEmailService() {
        return emailService;
    }

    /**
     * Gets user action service.
     *
     * @return the user action service
     */
    public UserActionService getUserActionService() {
        return userActionService;
    }

    /**
     * Gets payment card service.
     *
     * @return the payment card service
     */
    public PaymentCardService getPaymentCardService() {
        return paymentCardService;
    }

    /**
     * Gets statistic service.
     *
     * @return the statistic service
     */
    public StatisticService getStatisticService() {
        return statisticService;
    }
}
