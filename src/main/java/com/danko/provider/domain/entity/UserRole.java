package com.danko.provider.domain.entity;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER"),
    GUEST("GUEST");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
