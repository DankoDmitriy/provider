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
