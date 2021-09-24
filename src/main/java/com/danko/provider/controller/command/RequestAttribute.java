package com.danko.provider.controller.command;

public final class RequestAttribute {

    //   Common parameter for pagination
    public static final String PAGINATION_USER_ID = "user_id";
    public static final String PAGINATION_USER_ROLE = "userRole";
    public static final String PAGINATION_NEXT_PAGE = "nextPage";
    public static final String PAGINATION_PREVIEW_PAGE = "previewPage";
    public static final String PAGINATION_RESULT_LIST = "resultList";

    //    User personal Attributes
    public static final String USER_PERSONAL_TRANSACTIONS_LIMIT = "transactions";
    public static final String USER_PERSONAL_TRANSACTIONS_ALL = "transactionsAll";
    public static final String USER_PERSONAL_MESSAGE_ERROR = "errorMessage";
    public static final String NEW_PASSWORD = "newPassword";
    //User Tariffs list page
    public static final String USER_TARIFF_LIST = "tariffs";
    public static final String USER_TARIFF_LIST_RESULT_FOR_MESSAGE = "resultWork";
    //User Actions list page
    public static final String USER_ACTION_LIST = "actions";

    //    Activation attribute
    public static final String ACTIVATION_CODE = "activationCode";

    //    Admin attributes
    //    Admin users List Page
    public static final String ADMIN_USERS_LIST = "resultList";
    public static final String ADMIN_USERS_LIST_RESULT_WORK_FOR_MESSAGE = "resultWork";

    //    Admin tariff List Page
    public static final String ADMIN_TARIFFS_LIST = "tariffs";
    public static final String ADMIN_TARIFFS_LIST_RESULT_WORK_FOR_MESSAGE = "resultWork";

    //    Admin ADD users Page
    public static final String ADMIN_TARIFFS_LIST_FOR_NEW_USER = "tariffs";

    //    Admin ADD users Page
    public static final String ADMIN_NEW_USER_CARD_TRANSFER_OBJECT = "trUser";

    //    Admin ADD tariff Page
    public static final String ADMIN_TARIFF_STATUS_LIST_FOR_NEW_TARIFF = "tariffStatuses";
    public static final String ADMIN_TARIFF_WRITE_OFF_LIST_FOR_NEW_TARIFF = "writeOffs";

    //    Admin ADD Express Payment card list generated Page
    public static final String ADMIN_NEW_PAYMENT_CARDS_LIST = "cards";
    public static final String ADMIN_NEW_PAYMENT_CARDS_EXPIRED_DATE = "expiredDate";

    //    Admin Edit tariff Page
    public static final String ADMIN_TARIFF_EDIT = "tariff";
    public static final String ADMIN_TARIFF_STATUS_LIST = "tariffStatuses";
    public static final String ADMIN_TARIFF_WRITE_OFF_LIST = "writeOffs";
    public static final String ADMIN_TARIFF_EDIT_ORIGINAL = "tariffOrigin";

    //    Admin Edit user Page
    public static final String ADMIN_USER_EDIT = "user";
    public static final String ADMIN_USER_EDIT_ORIGINAL = "userOrigin";

    //    Admin user profile Page
    public static final String ADMIN_USER_PROFILE_USER ="userProfile";
    public static final String ADMIN_USER_PROFILE_TRANSACTIONS ="transactionList";
    public static final String ADMIN_USER_PROFILE_ACTIONS ="userActionList";

    private RequestAttribute() {
    }
}
