package com.danko.provider.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class User extends AbstractEntity {
    private long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String contractNumber;
    private LocalDateTime contractDate;
    private BigDecimal balance;
    private String name;
    private String email;
    private BigDecimal traffic;
    private UserRole role;
    private UserStatus status;
    private long tariffId;
    private Tariff tariff;

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDateTime getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDateTime contractDate) {
        this.contractDate = contractDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public BigDecimal getTraffic() {
        return traffic;
    }

    public void setTraffic(BigDecimal traffic) {
        this.traffic = traffic;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (tariffId != user.tariffId) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (patronymic != null ? !patronymic.equals(user.patronymic) : user.patronymic != null) return false;
        if (contractNumber != null ? !contractNumber.equals(user.contractNumber) : user.contractNumber != null)
            return false;
        if (contractDate != null ? !contractDate.equals(user.contractDate) : user.contractDate != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (traffic != null ? !traffic.equals(user.traffic) : user.traffic != null) return false;
        if (role != user.role) return false;
        if (status != user.status) return false;
        return tariff != null ? tariff.equals(user.tariff) : user.tariff == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (contractNumber != null ? contractNumber.hashCode() : 0);
        result = 31 * result + (contractDate != null ? contractDate.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (traffic != null ? traffic.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (tariffId ^ (tariffId >>> 32));
        result = 31 * result + (tariff != null ? tariff.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", patronymic='").append(patronymic).append('\'');
        sb.append(", contractNumber='").append(contractNumber).append('\'');
        sb.append(", contractDate=").append(contractDate);
        sb.append(", balance=").append(balance);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", traffic=").append(traffic);
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append(", tariffId=").append(tariffId);
        sb.append(", tariff=").append(tariff);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder setUserId(long id) {
            user.setUserId(id);
            return this;
        }

        public Builder setFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            user.setPatronymic(patronymic);
            return this;
        }

        public Builder setContractNumber(String contractNumber) {
            user.setContractNumber(contractNumber);
            return this;
        }

        public Builder setContractDate(LocalDateTime contractDate) {
            user.setContractDate(contractDate);
            return this;
        }

        public Builder setBalance(BigDecimal balance) {
            user.setBalance(balance);
            return this;
        }

        public Builder setName(String name) {
            user.setName(name);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setRole(UserRole role) {
            user.setRole(role);
            return this;
        }

        public Builder setStatus(UserStatus status) {
            user.setStatus(status);
            return this;
        }

        public Builder setTraffic(BigDecimal traffic) {
            user.setTraffic(traffic);
            return this;
        }

        public Builder setTariffId(long tariff) {
            user.setTariffId(tariff);
            return this;
        }

        public Builder setTariff(Tariff tariff) {
            user.setTariff(tariff);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
