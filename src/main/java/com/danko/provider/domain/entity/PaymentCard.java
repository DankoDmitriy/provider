package com.danko.provider.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCard extends AbstractEntity {
    private long cardId;
    private BigDecimal amount;
    private LocalDateTime activationDate;
    private CardStatus cardStatus;

    public enum CardStatus {
        USED,
        NOT_USED;
    }

    public PaymentCard() {
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentCard that = (PaymentCard) o;

        if (cardId != that.cardId) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (activationDate != null ? !activationDate.equals(that.activationDate) : that.activationDate != null)
            return false;
        return cardStatus == that.cardStatus;
    }

    @Override
    public int hashCode() {
        int result = (int) (cardId ^ (cardId >>> 32));
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (activationDate != null ? activationDate.hashCode() : 0);
        result = 31 * result + (cardStatus != null ? cardStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentCard{");
        sb.append("cardId=").append(cardId);
        sb.append(", amount=").append(amount);
        sb.append(", activationDate=").append(activationDate);
        sb.append(", cardStatus=").append(cardStatus);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PaymentCard paymentCard;

        public Builder() {
            paymentCard = new PaymentCard();
        }

        public Builder setCardId(long cardId) {
            paymentCard.setCardId(cardId);
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            paymentCard.setAmount(amount);
            return this;
        }

        public Builder setActivationDate(LocalDateTime activationDate) {
            paymentCard.setActivationDate(activationDate);
            return this;
        }

        public Builder setCardStatus(CardStatus cardStatus) {
            paymentCard.setCardStatus(cardStatus);
            return this;
        }

        public PaymentCard build() {
            return paymentCard;
        }

    }
}
