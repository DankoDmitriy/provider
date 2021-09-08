package com.danko.provider.controller.command;

public final class CommandName {
    //    Commands for all roles
    public static final String COMMAND_LOGIN = "LOGIN";
    public static final String COMMAND_LOGOUT = "LOGOUT";
    public static final String COMMAND_HOME = "HOME";
    public static final String COMMAND_ACTIVATION = "ACTIVATION";
    public static final String COMMAND_CHANGE_LOCAL = "LOCAL";

    //    Commands for Admin role
    public static final String COMMAND_ADMIN_USER_LIST = "USERS_LIST";

    public static final String COMMAND_ADMIN_TARIFF_LIST = "TARIFF_LIST";
    //    TODO - Не реализованны.
    public static final String COMMAND_ADMIN_USER_ADD = "USER_ADD";
    public static final String COMMAND_ADMIN_USER_SEARCH = "USER_SEARCH";
    public static final String COMMAND_ADMIN_USER_EDIT = "USER_EDIT";
    public static final String COMMAND_ADMIN_USER_EDIT_STATUS = "USER_EDIT_STATUS";

    public static final String COMMAND_ADMIN_EMPLOYEE_LIST = "EMPLOYEE_LIST";


    public static final String COMMAND_ADMIN_TARIFF_ADD = "TARIFF_ADD";
    public static final String COMMAND_ADMIN_TARIFF_EDIT = "TARIFF_EDIT";

    public static final String COMMAND_ADMIN_CARD_ADD = "CARD_ADD";
    public static final String COMMAND_ADMIN_CARD_SEARCH = "CARD_SEARCH";

    public static final String COMMAND_ADMIN_STATISTICS = "STATISTICS";

    //    Commands for User role
    public static final String COMMAND_USER_PERSONAL_FINANCE_OPERATIONS = "PERSONAL_FINANCE_OPERATIONS";
    public static final String COMMAND_USER_CHANGE_PASSWORD = "USER_CHANGE_PASSWORD";
    public static final String COMMAND_USER_CHANGE_TARIFF = "USER_CHANGE_TARIFF";
    public static final String COMMAND_USER_TARIFF_LIST = "USER_TARIFF_LIST";
    public static final String COMMAND_USER_ACTIONS_LIST = "USER_ACTION_LIST";

    public static final String COMMAND_CHANGE_PAY = "PAY";

    private CommandName() {
    }
}
