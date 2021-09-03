package com.danko.provider.validator;

public final class InputDataValidator {
    private static final String LOGIN_NAME_REGEX = "[A-Za-z][0-9a-zA-Z]{7,45}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,20}";

    public static boolean singInParametersValid(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        return login.matches(LOGIN_NAME_REGEX) && password.matches(PASSWORD_REGEX);
    }

    public static boolean newUserPasswordValid(String newPassword) {
        return newPassword.matches(PASSWORD_REGEX);
    }

    private InputDataValidator() {
    }

}
