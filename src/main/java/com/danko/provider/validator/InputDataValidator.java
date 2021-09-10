package com.danko.provider.validator;

import java.util.concurrent.atomic.AtomicBoolean;

public class InputDataValidator {
    private static InputDataValidator instance;
    private static final AtomicBoolean isInputDataValidator = new AtomicBoolean(false);
    private static final String LOGIN_NAME_REGEX = "[0-9a-zA-Z]{8,45}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,20}";
    private static final String FIRST_NAME_REGEX = "[a-zA-ZА-Яа-я]{2,55}";
    private static final String LAST_NAME_REGEX = "[a-zA-ZА-Яа-я]{2,55}";
    private static final String PATRONYMIC_REGEX = "[a-zA-ZА-Яа-я]{2,55}";
    private static final String EMAIL_REGEX = "^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String EMAIL_SYMBOL_NUMBER = ".{8,60}";

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
        return login != null && password != null && login.matches(LOGIN_NAME_REGEX) && password.matches(PASSWORD_REGEX);
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public boolean isFirstNameValid(String firstName) {
        return firstName != null && firstName.matches(FIRST_NAME_REGEX);
    }

    public boolean isLastNameValid(String lastName) {
        return lastName != null && lastName.matches(LAST_NAME_REGEX);
    }

    public boolean isPatronymic(String patronymic) {
        return patronymic != null && patronymic.matches(PATRONYMIC_REGEX);
    }

    public boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX) && email.matches(EMAIL_SYMBOL_NUMBER);
    }

}

