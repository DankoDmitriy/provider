package com.danko.provider.validator;

import java.util.concurrent.atomic.AtomicBoolean;

public class InputDataValidator {
    private static InputDataValidator instance;
    private static final AtomicBoolean isInputDataValidator = new AtomicBoolean(false);
    private static final String LOGIN_NAME_REGEX = "[A-Za-z][0-9a-zA-Z]{7,45}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,20}";

    private InputDataValidator() {
    }

    public static InputDataValidator getInstance() {
        while (instance == null) {
            if (isInputDataValidator.compareAndSet(false, true)) {
                instance = new InputDataValidator();
            }
        }
        return instance;
    }

    public boolean singInParametersValid(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        return login.matches(LOGIN_NAME_REGEX) && password.matches(PASSWORD_REGEX);
    }

    public boolean newUserPasswordValid(String newPassword) {
        return newPassword.matches(PASSWORD_REGEX);
    }
}

