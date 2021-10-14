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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseStatistic that = (BaseStatistic) o;

        if (usersOnTariffs != null ? !usersOnTariffs.equals(that.usersOnTariffs) : that.usersOnTariffs != null)
            return false;
        if (usersByStatus != null ? !usersByStatus.equals(that.usersByStatus) : that.usersByStatus != null)
            return false;
        if (paymentCardsGenerated != null ? !paymentCardsGenerated.equals(that.paymentCardsGenerated) : that.paymentCardsGenerated != null)
            return false;
        return paymentCardsNotActivated != null ? paymentCardsNotActivated.equals(that.paymentCardsNotActivated) : that.paymentCardsNotActivated == null;
    }

    @Override
    public int hashCode() {
        int result = usersOnTariffs != null ? usersOnTariffs.hashCode() : 0;
        result = 31 * result + (usersByStatus != null ? usersByStatus.hashCode() : 0);
        result = 31 * result + (paymentCardsGenerated != null ? paymentCardsGenerated.hashCode() : 0);
        result = 31 * result + (paymentCardsNotActivated != null ? paymentCardsNotActivated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseStatistic{");
        sb.append("usersOnTariffs=").append(usersOnTariffs);
        sb.append(", usersByStatus=").append(usersByStatus);
        sb.append(", paymentCardsGenerated=").append(paymentCardsGenerated);
        sb.append(", paymentCardsNotActivated=").append(paymentCardsNotActivated);
        sb.append('}');
        return sb.toString();
    }
}
