package com.danko.provider.domain.service;

import com.danko.provider.domain.service.impl.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServiceProvider {
    private static ServiceProvider instance;
    private static final AtomicBoolean isServiceProviderCreated = new AtomicBoolean(false);
    private UserService userService = new UserServiceImpl();
    private TariffService tariffService = new TariffServiceImpl();
    private AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
    private EmailService emailService = new EmailServiceImpl();
    private UserActionService userActionService = new UserActionServiceImpl();

    private ServiceProvider() {
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
}
