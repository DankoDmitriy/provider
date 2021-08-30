package com.danko.provider.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransaction extends AbstractEntity {
    private long transactionId;
    private BigDecimal sum;
    private LocalDateTime date;
    private TransactionType type;
    private long userId;

    public AccountTransaction() {
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountTransaction that = (AccountTransaction) o;

        if (transactionId != that.transactionId) return false;
        if (userId != that.userId) return false;
        if (sum != null ? !sum.equals(that.sum) : that.sum != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (transactionId ^ (transactionId >>> 32));
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountTransaction{");
        sb.append("transactionId=").append(transactionId);
        sb.append(", sum=").append(sum);
        sb.append(", date=").append(date);
        sb.append(", type=").append(type);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AccountTransaction transaction;

        public Builder() {
            this.transaction = new AccountTransaction();
        }

        public Builder setTransactionId(long transactionId) {
            transaction.setTransactionId(transactionId);
            return this;
        }

        public Builder setSum(BigDecimal sum) {
            transaction.setSum(sum);
            return this;
        }

        public Builder setDate(LocalDateTime date) {
            transaction.setDate(date);
            return this;
        }

        public Builder setType(TransactionType type) {
            transaction.setType(type);
            return this;
        }

        public Builder setUserId(long userId) {
            transaction.setUserId(userId);
            return this;
        }

        public AccountTransaction build() {
            return transaction;
        }
    }
}
