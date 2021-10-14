package com.danko.provider.domain.entity;

public class UserCountStatistic extends AbstractEntity {
    private String parameterName;
    private long count;

    public UserCountStatistic() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCountStatistic that = (UserCountStatistic) o;

        if (count != that.count) return false;
        return parameterName != null ? parameterName.equals(that.parameterName) : that.parameterName == null;
    }

    @Override
    public int hashCode() {
        int result = parameterName != null ? parameterName.hashCode() : 0;
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserCountStatistic{");
        sb.append("parameterName='").append(parameterName).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
