package com.danko.provider.domain.dao;

public final class ColumnName {
    private ColumnName() {
    }

    //    Tables:
    public static final String USERS = "users";
    public static final String USER_STATUSES = "user_statuses";
    public static final String USER_ROLES = "user_roles";
    public static final String TARIFFS = "tariffs";
    public static final String TARIFF_STATUSES = "tariff_statuses";
    public static final String PERIODICITY_WRITE_OFF = "periodicity_write_off";
    public static final String USER_ACCOUNT = "user_accounts";
    public static final String TRANSACTION_TYPE = "transaction_type";
    public static final String ACCOUNT_TRANSACTIONS = "account_transactions";

    //    Table: "users"
    public static final String USER_ID = "user_id";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PATRONYMIC = "patronymic";
    public static final String USER_CONTRACT_NUMBER = "contract_number";
    public static final String USER_CONTRACT_DATE = "contract_date";
    public static final String USER_BALANCE = "balance";
    public static final String USER_NAME = "name";
    public static final String USER_PSW = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_ACTIVATION_CODE = "activation_code";
    public static final String USER_ACTIVATE_CODE_USED ="activation_code_used";
    public static final String USER_TRAFFIC = "traffic";
    public static final String USER_ROLE = "user_roles_role_id";
    public static final String USER_STATUS = "user_statuses_status_id";
    public static final String USER_TARIFF_ID = "tariffs_tariff_id";


    //    Table: "user_statuses"
    public static final String USER_STATUSES_STATUS_ID = "status_id";
    public static final String USER_STATUSES_STATUS = "status";

    //    Table: "user_roles"
    public static final String USER_ROLES_ROLE_ID = "role_id";
    public static final String USER_ROLES_ROLE = "role";

    //    Table: "tariffs"
    public static final String TARIFF_TARIFF_ID = "tariff_id";
    public static final String TARIFF_DESCRIPTION = "description";
    public static final String TARIFF_MAX_SPEED = "max_speed";
    public static final String TARIFF_MIN_SPEED = "min_speed";
    public static final String TARIFF_TRAFFIC = "traffic";
    public static final String TARIFF_PRICE = "price";
    public static final String TARIFF_STATUS = "tariff_statuses_status_id";
    public static final String TARIFF_PERIOD = "periodicity_write_off_write_off_id";

    //    Table: "tariff_statuses"
    public static final String TARIFF_STATUSES_STATUS_ID = "status_id";
    public static final String TARIFF_STATUSES_STATUS = "status";

    //    Table: "periodicity_write_off"
    public static final String PERIODICITY_WRITE_OFF_ID = "write_off_id";
    public static final String PERIODICITY_PERIOD = "period";

    //    Table: "user_accounts"
    //    TODO - DELETE
    public static final String ACCOUNT_ACCOUNT_ID = "account_id";
    public static final String ACCOUNT_BALANCE = "balance";
    public static final String ACCOUNT_USER_ID = "users_user_id";

    //    Table: "transaction_type"
    public static final String TRANSACTION_TYPE_TYPE_ID = "type_id";
    public static final String TRANSACTION_TYPE_TYPE = "type";
    public static final String TRANSACTION_TYPE_DESCRIPTION = "description";

    //    Table: "account_transactions"
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String TRANSACTION_SUM = "sum";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_TYPE_ID = "transaction_type_type_id";
    public static final String TRANSACTION_USER_ID = "users_user_id";

}
