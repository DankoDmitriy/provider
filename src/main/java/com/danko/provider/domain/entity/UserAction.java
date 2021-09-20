package com.danko.provider.domain.entity;

import java.time.LocalDateTime;

public class UserAction extends AbstractEntity {
    private long actionId;
    private LocalDateTime dateTime;
    private ActionType actionType;
    private String tariffName;

    public enum ActionType {
        CHANGE_PASSWORD,
        CARD_ACTIVATE,
        CHANGE_TARIFF,
        CHANGE_STATUS,
        CHANGE_ROLE;
    }

    public UserAction() {
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAction that = (UserAction) o;

        if (actionId != that.actionId) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (actionType != that.actionType) return false;
        return tariffName != null ? tariffName.equals(that.tariffName) : that.tariffName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (actionId ^ (actionId >>> 32));
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (actionType != null ? actionType.hashCode() : 0);
        result = 31 * result + (tariffName != null ? tariffName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserAction{");
        sb.append("actionId=").append(actionId);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", actionType=").append(actionType);
        sb.append(", tariffName='").append(tariffName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserAction userAction;

        public Builder() {
            userAction = new UserAction();
        }

        public Builder setActionId(long id) {
            userAction.setActionId(id);
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            userAction.setDateTime(dateTime);
            return this;
        }

        public Builder setActionType(ActionType actionType) {
            userAction.setActionType(actionType);
            return this;
        }

        public Builder setTariffName(String tariffName) {
            userAction.setTariffName(tariffName);
            return this;
        }

        public UserAction build() {
            return userAction;
        }
    }
}
