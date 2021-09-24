package com.danko.provider.domain.service;

import com.danko.provider.domain.dao.TransactionManager;
import com.danko.provider.domain.service.impl.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServiceProvider {
    private static ServiceProvider instance;
    private static final AtomicBoolean isServiceProviderCreated = new AtomicBoolean(false);
    private final UserService userService;
    private final TariffService tariffService;
    private final AccountTransactionService accountTransactionService;
    private final UserActionService userActionService;
    private final PaymentCardService paymentCardService;
    private final EmailService emailService;

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
        this.tariffService = new TariffServiceImpl(daoProvider.getTariffDao(), transactionManager);
        this.accountTransactionService = new AccountTransactionServiceImpl(
                daoProvider.getAccountTransactionDao(),
                transactionManager);
        this.userActionService = new UserActionServiceImpl(daoProvider.getUserActionDao(), transactionManager);
        this.paymentCardService = new PaymentCardServiceImpl(
                daoProvider.getPaymentCardDao(),
                daoProvider.getPaymentCardSerialDao(),
                daoProvider.getUserDao(),
                transactionManager);
        this.emailService = new EmailServiceImpl();

    }

    public static ServiceProvider getInstance() {
        while (instance == null) {
            if (isServiceProviderCreated.compareAndSet(false, true)) {
                instance = new ServiceProvider();
            }
        }
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public TariffService getTariffService() {
        return tariffService;
    }

    public AccountTransactionService getAccountTransactionService() {
        return accountTransactionService;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public UserActionService getUserActionService() {
        return userActionService;
    }

    public PaymentCardService getPaymentCardService() {
        return paymentCardService;
    }
}
