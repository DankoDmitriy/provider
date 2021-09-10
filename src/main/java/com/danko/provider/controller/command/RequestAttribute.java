package com.danko.provider.controller.command;

public final class RequestAttribute {
    //    User personal Attributes
    public static final String USER_PERSONAL_TRANSACTIONS_LIMIT = "transactions";
    public static final String USER_PERSONAL_TRANSACTIONS_ALL = "transactionsAll";
    public static final String USER_PERSONAL_MESSAGE_ERROR = "errorMessage";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String USER_TARIFF_LIST = "tariffs";
    public static final String USER_ACTION_LIST = "actions";

    //    Activation attribute
    public static final String ACTIVATION_CODE = "activationCode";

    //    Admin attributes
    //    Admin users List Page
    public static final String ADMIN_USERS_LIST = "users";
    //    Admin tariff List Page
    public static final String ADMIN_TARIFFS_LIST = "tariffs";

    //    Admin ADD users Page
    public static final String ADMIN_TARIFFS_LIST_FOR_NEW_USER = "tariffs";

    //    Admin ADD users Page
    public static final String ADMIN_NEW_USER_CARD_TRANSFER_OBJECT = "trUser";


    private RequestAttribute() {
    }
}
