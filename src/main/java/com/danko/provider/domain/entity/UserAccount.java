package com.danko.provider.domain.entity;

import java.math.BigDecimal;

public class UserAccount extends AbstractEntity {
    private long accountId;
    private long userId;
    private BigDecimal balance;

    public UserAccount() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserAccount userAccount;

        public Builder() {
            userAccount = new UserAccount();
        }

        public Builder setAccountId(long accountId) {
            userAccount.setAccountId(accountId);
            return this;
        }

        public Builder setUserId(long userId) {
            userAccount.setUserId(userId);
            return this;
        }

        public Builder setBalance(BigDecimal balance) {
            userAccount.setBalance(balance);
            return this;
        }

        public UserAccount build() {
            return userAccount;
        }
    }
}
