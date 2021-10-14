package com.danko.provider.domain.entity;

public class PaymentCardSerial extends AbstractEntity {
    private long serialId;
    private String serial;

    private PaymentCardSerial() {
    }

    public long getSerialId() {
        return serialId;
    }

    public void setSerialId(long serialId) {
        this.serialId = serialId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentCardSerial that = (PaymentCardSerial) o;

        if (serialId != that.serialId) return false;
        return serial != null ? serial.equals(that.serial) : that.serial == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (serialId ^ (serialId >>> 32));
        result = 31 * result + (serial != null ? serial.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentCardSerial{");
        sb.append("serialId=").append(serialId);
        sb.append(", serial='").append(serial).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private PaymentCardSerial paymentCardSerial;

        public Builder() {
            paymentCardSerial = new PaymentCardSerial();
        }

        public Builder setSerialId(long serialId) {
            paymentCardSerial.setSerialId(serialId);
            return this;
        }

        public Builder setSerial(String serial) {
            paymentCardSerial.setSerial(serial);
            return this;
        }

        public PaymentCardSerial build() {
            return paymentCardSerial;
        }
    }
}
