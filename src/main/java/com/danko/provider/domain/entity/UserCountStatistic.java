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
}
