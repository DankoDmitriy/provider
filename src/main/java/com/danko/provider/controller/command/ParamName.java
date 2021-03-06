package com.danko.provider.controller.command;

/**
 * The class represents string constants of parameters from web forms and HTTP requests
 */
public final class ParamName {

    /**
     * General type Actions parameter.
     */
    public static final String COMMAND = "command";

    /**
     * Login form parameters.
     */
    public static final String LOGIN_FORM_NAME = "name";
    public static final String LOGIN_FORM_PASSWORD = "password";

    /**
     * User change password form parameters.
     */
    public static final String USER_CHANGE_PASSWORD_NEW_PASSWORD = "password";

    /**
     * User change tariff form parameters.
     */
    public static final String USER_CHANGE_TARIFF_NEW_TARIFF_ID = "tariffId";

    /**
     * User change locale.
     */
    public static final String USER_CHANGE_LOCAL = "newLocal";

    /**
     * Login form parameters.
     */
    public static final String CARD_NUMBER = "cardNumber";
    public static final String CARD_PIN = "cardPin";

    /**
     * Admin find card by number Page.
     */
    public static final String FIND_CARD_NUMBER_CARD = "cardNumber";

    /**
     * Admin User add page.
     */
    public static final String USER_ADD_FIRST_NAME = "first_name";
    public static final String USER_ADD_LAST_NAME = "last_name";
    public static final String USER_ADD_PATRONYMIC = "patronymic";
    public static final String USER_ADD_CONTRACT_DATE = "contract_date";
    public static final String USER_ADD_TARIFF_ID = "tariff_id";
    public static final String USER_ADD_E_MAIL = "e_mail";

    /**
     * Admin User search page.
     */
    public static final String USER_SEARCH_FIRST_NAME = "first_name";
    public static final String USER_SEARCH_LAST_NAME = "last_name";
    public static final String USER_SEARCH_PATRONYMIC = "patronymic";
    public static final String USER_SEARCH_CONTRACT_NUMBER = "contract_number";
    public static final String USER_SEARCH_E_MAIL = "e_mail";

    /**
     * Admin Tariff add page.
     */
    public static final String TARIFF_ADD_NAME = "tariff_name";
    public static final String TARIFF_ADD_MAX_SPEED = "max_speed";
    public static final String TARIFF_ADD_MIN_SPEED = "min_speed";
    public static final String TARIFF_ADD_TRAFFIC = "traffic";
    public static final String TARIFF_ADD_PRICE = "price";
    public static final String TARIFF_ADD_STATUS = "status";
    public static final String TARIFF_ADD_PERIOD = "period";

    /**
     * Admin Payment cards add page.
     */
    public static final String PAYMENT_CARD_ADD_SERIES = "series";
    public static final String PAYMENT_CARD_ADD_AMOUNT = "amount";
    public static final String PAYMENT_CARD_ADD_COUNT = "count";
    public static final String PAYMENT_CARD_ADD_DATE_EXPIRED = "date_expired";

    /**
     * Admin Tariff edit page.
     */
    public static final String TARIFF_EDIT_ORIGIN = "tariffOrigin";
    public static final String TARIFF_EDIT_ID = "tariff_id";
    public static final String TARIFF_EDIT_NAME = "tariff_name";
    public static final String TARIFF_EDIT_MAX_SPEED = "max_speed";
    public static final String TARIFF_EDIT_MIN_SPEED = "min_speed";
    public static final String TARIFF_EDIT_TRAFFIC = "traffic";
    public static final String TARIFF_EDIT_PRICE = "price";
    public static final String TARIFF_EDIT_STATUS = "status";
    public static final String TARIFF_EDIT_PERIOD = "period";

    /**
     * Admin User edit page.
     */
    public static final String USER_EDIT_ORIGIN = "userOrigin";
    public static final String USER_EDIT_ID = "user_id";
    public static final String USER_EDIT_FIRST_NAME = "first_name";
    public static final String USER_EDIT_LAST_NAME = "last_name";
    public static final String USER_EDIT_PATRONYMIC = "patronymic";
    public static final String USER_EDIT_E_MAIL = "e_mail";

    /**
     * Admin User Profile page.
     */
    public static final String USER_PROFILE_ID = "user_id";

    private ParamName() {
    }
}
