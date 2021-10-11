package com.danko.provider.domain.entity;

import java.math.BigDecimal;

public class PaymentCardCountStatistic extends AbstractEntity {
    private BigDecimal amount;
    private long count;
    private BigDecimal sum;

    public PaymentCardCountStatistic() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
