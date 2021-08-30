package com.danko.provider.domain.entity;

import java.math.BigDecimal;

public class Tariff extends AbstractEntity {
    private long tariffId;
    private String description;
    private int maxSpeed;
    private int minSpeed;
    private BigDecimal traffic;
    private BigDecimal price;
    private TariffStatus status;
    private PeriodicityWriteOff period;

    public Tariff() {
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public BigDecimal getTraffic() {
        return traffic;
    }

    public void setTraffic(BigDecimal traffic) {
        this.traffic = traffic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TariffStatus getStatus() {
        return status;
    }

    public void setStatus(TariffStatus status) {
        this.status = status;
    }

    public PeriodicityWriteOff getPeriod() {
        return period;
    }

    public void setPeriod(PeriodicityWriteOff period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff tariff = (Tariff) o;

        if (tariffId != tariff.tariffId) return false;
        if (maxSpeed != tariff.maxSpeed) return false;
        if (minSpeed != tariff.minSpeed) return false;
        if (description != null ? !description.equals(tariff.description) : tariff.description != null) return false;
        if (traffic != null ? !traffic.equals(tariff.traffic) : tariff.traffic != null) return false;
        if (price != null ? !price.equals(tariff.price) : tariff.price != null) return false;
        if (status != tariff.status) return false;
        return period == tariff.period;
    }

    @Override
    public int hashCode() {
        int result = (int) (tariffId ^ (tariffId >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + maxSpeed;
        result = 31 * result + minSpeed;
        result = 31 * result + (traffic != null ? traffic.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tariff{");
        sb.append("tariffId=").append(tariffId);
        sb.append(", description='").append(description).append('\'');
        sb.append(", maxSpeed=").append(maxSpeed);
        sb.append(", mixSpeed=").append(minSpeed);
        sb.append(", traffic=").append(traffic);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append(", period=").append(period);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Tariff tariff;

        public Builder() {
            tariff = new Tariff();
        }

        public Builder setTariffId(long id) {
            tariff.setTariffId(id);
            return this;
        }

        public Builder setDescription(String description) {
            tariff.setDescription(description);
            return this;
        }

        public Builder setMaxSpeed(int maxSpeed) {
            tariff.setMaxSpeed(maxSpeed);
            return this;
        }

        public Builder setMinSpeed(int minSpeed) {
            tariff.setMinSpeed(minSpeed);
            return this;
        }

        public Builder setTraffic(BigDecimal traffic) {
            tariff.setTraffic(traffic);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            tariff.setPrice(price);
            return this;
        }

        public Builder setStatus(TariffStatus status) {
            tariff.setStatus(status);
            return this;
        }

        public Builder setPeriod(PeriodicityWriteOff period) {
            tariff.setPeriod(period);
            return this;
        }

        public Tariff build() {
            return tariff;
        }
    }
}
