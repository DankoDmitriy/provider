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

    //   User Pay page.
    public static final String CARD_NUMBER = "cardNumber";
    public static final String CARD_PIN = "cardPin";


    //   Admin Users_list page.
    public static final String USER_ID = "user_id";

    //   Admin Tariffs_list page.
    public static final String TARIFF_ID = "tariff_id";

    //   Admin User add page.
    public static final String USER_ADD_FIRST_NAME = "first_name";
    public static final String USER_ADD_LAST_NAME = "last_name";
    public static final String USER_ADD_PATRONYMIC = "patronymic";
    public static final String USER_ADD_CONTRACT_DATE = "contract_date";
    public static final String USER_ADD_TARIFF_ID = "tariff_id";
    public static final String USER_ADD_E_MAIL = "e_mail";

    private ParamName() {
    }
}
