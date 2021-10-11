package com.danko.provider.domain.entity;

import java.util.List;

public class BaseStatistic extends AbstractEntity {
    private List<UserCountStatistic> usersOnTariffs;
    private List<UserCountStatistic> usersByStatus;
    private List<PaymentCardCountStatistic> paymentCardsGenerated;
    private List<PaymentCardCountStatistic> paymentCardsNotActivated;

    public BaseStatistic() {
    }

    public List<UserCountStatistic> getUsersOnTariffs() {
        return usersOnTariffs;
    }

    public void setUsersOnTariffs(List<UserCountStatistic> usersOnTariffs) {
        this.usersOnTariffs = usersOnTariffs;
    }

    public List<UserCountStatistic> getUsersByStatus() {
        return usersByStatus;
    }

    public void setUsersByStatus(List<UserCountStatistic> usersByStatus) {
        this.usersByStatus = usersByStatus;
    }

    public List<PaymentCardCountStatistic> getPaymentCardsGenerated() {
        return paymentCardsGenerated;
    }

    public void setPaymentCardsGenerated(List<PaymentCardCountStatistic> paymentCardsGenerated) {
        this.paymentCardsGenerated = paymentCardsGenerated;
    }

    public List<PaymentCardCountStatistic> getPaymentCardsNotActivated() {
        return paymentCardsNotActivated;
    }

    public void setPaymentCardsNotActivated(List<PaymentCardCountStatistic> paymentCardsNotActivated) {
        this.paymentCardsNotActivated = paymentCardsNotActivated;
    }
}
