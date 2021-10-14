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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentCardCountStatistic that = (PaymentCardCountStatistic) o;

        if (count != that.count) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return sum != null ? sum.equals(that.sum) : that.sum == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (int) (count ^ (count >>> 32));
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentCardCountStatistic{");
        sb.append("amount=").append(amount);
        sb.append(", count=").append(count);
        sb.append(", sum=").append(sum);
        sb.append('}');
        return sb.toString();
    }
}
