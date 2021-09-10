package com.danko.provider.domain.entity;

public class TransferObject {
    private User user;
    private String password;

    public TransferObject() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
