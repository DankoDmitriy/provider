package com.danko.provider.domain.entity;

public enum UserStatus {
    ACTIVE("ACTIVE"),
    BLOCK("BLOCK"),
    WAIT_ACTIVATE("WAIT_ACTIVATE");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
