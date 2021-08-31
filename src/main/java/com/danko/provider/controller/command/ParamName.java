package com.danko.provider.controller.command;

public final class ParamName {
    //    General type Actions parameter
    public static final String COMMAND = "command";

    //    Parameters names for forms items (input,select,text,date...) and transfer data

    //    Login form parameters
    public static final String LOGIN_FORM_NAME = "name";
    public static final String LOGIN_FORM_PASSWORD = "password";

    //    User change password form parameters
    public static final String USER_CHANGE_PASSWORD_NEW_PASSWORD = "password";

    //    User change tariff form parameters
    public static final String USER_CHANGE_TARIFF_NEW_TARIFF_ID = "tariffId";

    //  User change locale
    public static final String USER_CHANGE_LOCAL = "newLocal";


    private ParamName() {
    }
}
