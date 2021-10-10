package com.danko.provider.domain.dao;

/**
 * The class provides string constants that represent database column names and tables names
 */
public final class ColumnName {

    /**
     * Tables names
     */
    public static final String TABLE_USERS = "users";
    public static final String TABLE_USER_STATUSES = "user_statuses";
    public static final String TABLE_USER_ROLES = "user_roles";
    public static final String TABLE_TARIFFS = "tariffs";
    public static final String TABLE_TARIFF_STATUSES = "tariff_statuses";
    public static final String TABLE_PERIODICITY_WRITE_OFF = "periodicity_write_off";
    public static final String TABLE_USER_ACCOUNT = "user_accounts";
    public static final String TABLE_TRANSACTION_TYPE = "transaction_type";
    public static final String TABLE_ACCOUNT_TRANSACTIONS = "account_transactions";
    public static final String TABLE_ACTION_TYPE = "action_type";
    public static final String TABLE_ACTIONS = "actions";
    public static final String TABLE_CARD_STATUS = "card_status";
    public static final String TABLE_EXPRESS_PAYMENT_CARDS = "express_payment_cards";
    public static final String TABLE_EXPRESS_PAYMENT_CARDS_SERIALS = "express_payment_cards_series";

    /**
     * Table users. Columns names
     */
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
    public static final String USER_ACTIVATE_CODE_USED = "activation_code_used";
    public static final String USER_TRAFFIC = "traffic";
    public static final String USER_ROLE = "user_roles_role_id";
    public static final String USER_STATUS = "user_statuses_status_id";
    public static final String USER_TARIFF_ID = "tariffs_tariff_id";

    /**
     * Table user_statuses. Columns names
     */
    public static final String USER_STATUSES_STATUS_ID = "status_id";
    public static final String USER_STATUSES_STATUS = "status";

    /**
     * Table user_roles. Columns names
     */
    public static final String USER_ROLES_ROLE_ID = "role_id";
    public static final String USER_ROLES_ROLE = "role";

    /**
     * Table tariffs. Columns names
     */
    public static final String TARIFF_TARIFF_ID = "tariff_id";
    public static final String TARIFF_DESCRIPTION = "description";
    public static final String TARIFF_MAX_SPEED = "max_speed";
    public static final String TARIFF_MIN_SPEED = "min_speed";
    public static final String TARIFF_TRAFFIC = "traffic";
    public static final String TARIFF_PRICE = "price";
    public static final String TARIFF_STATUS = "tariff_statuses_status_id";
    public static final String TARIFF_PERIOD = "periodicity_write_off_write_off_id";

    /**
     * Table tariff_statuses. Columns names
     */
    public static final String TARIFF_STATUSES_STATUS_ID = "status_id";
    public static final String TARIFF_STATUSES_STATUS = "status";

    /**
     * Table periodicity_write_off. Columns names
     */
    public static final String PERIODICITY_WRITE_OFF_ID = "write_off_id";
    public static final String PERIODICITY_PERIOD = "period";

    /**
     * Table transaction_type. Columns names
     */
    public static final String TRANSACTION_TYPE_TYPE_ID = "type_id";
    public static final String TRANSACTION_TYPE_TYPE = "type";
    public static final String TRANSACTION_TYPE_DESCRIPTION = "description";

    /**
     * Table account_transactions. Columns names
     */
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String TRANSACTION_SUM = "sum";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_TYPE_ID = "transaction_type_type_id";
    public static final String TRANSACTION_USER_ID = "users_user_id";

    /**
     * Table action_type. Columns names
     */
    public static final String ACTION_TYPE_ACTION_TYPE_ID = "action_type_id";
    public static final String ACTION_TYPE_ACTION_TYPE = "type";

    /**
     * Table actions. Columns names
     */
    public static final String ACTION_ID = "action_id";
    public static final String ACTION_DATE = "date";
    public static final String ACTION_TYPE_ID = "action_type_type_id";
    public static final String ACTION_USER_ID = "users_user_id";
    public static final String ACTION_TARIFF_ID = "tariffs_tariff_id";

    /**
     * Table card_status. Columns names
     */
    public static final String CARD_STATUS_STATUS_ID = "card_status_id";
    public static final String CARD_STATUS_STATUS = "status";

    /**
     * Table express_payment_cards. Columns names
     */
    public static final String PAYMENT_CARD_ID = "card_id";
    public static final String PAYMENT_CARD_AMOUNT = "amount";
    public static final String PAYMENT_CARD_NUMBER = "card_number";
    public static final String PAYMENT_CARD_PIN = "card_pin";
    public static final String PAYMENT_CARD_ACTIVATION_DATE = "activation_date";
    public static final String PAYMENT_CARD_STATUS_ID = "card_status_card_status_id";
    public static final String PAYMENT_CARD_USER_ID = "users_user_id";

    /**
     * Table express_payment_cards_series. Columns names
     */
    public static final String PAYMENT_CARD_SERIAL_ID = "series_id";
    public static final String PAYMENT_CARD_SERIAL_SERIAL = "series";

    private ColumnName() {
    }
}
