package com.danko.provider.controller.command;

public final class PageUrl {
    //    Administrator URLS
    public static final String ADMIN_USERS_LIST_PAGE = "/jsp/pages/admin/admin_users_list.jsp";
    public static final String ADMIN_USERS_LIST_PAGE_REDIRECT = "/controller?command=USERS_LIST&USER_ROLE=USER&nextPage=0&previewPage=-1";

    public static final String ADMIN_USER_ADD_PAGE = "/jsp/pages/admin/admin_user_add.jsp";
    public static final String ADMIN_USER_ADD_PAGE_REDIRECT = "/controller?command=USER_ADD";
    public static final String ADMIN_USER_ADD_CARD = "/jsp/pages/admin/admin_new_user_card.jsp";

    public static final String ADMIN_USER_EDIT_PAGE = "/jsp/pages/admin/admin_user_edit.jsp";
    public static final String ADMIN_USER_EDIT_PAGE_REDIRECT = "/controller?command=USER_EDIT";

    public static final String ADMIN_USER_PROFILE_PAGE = "/jsp/pages/admin/admin_user_profile.jsp";
    public static final String ADMIN_USER_PROFILE_PAGE_REDIRECT = "/controller?command=USER_PROFILE&user_id=";

    public static final String ADMIN_USER_FINANCES_OPERATION_PAGE = "/jsp/pages/admin/admin_user_finance_operations.jsp";
    public static final String ADMIN_USER_FINANCES_OPERATION_PAGE_REDIRECT = "/controller?command=USER_FINANCE&user_id=";

    public static final String ADMIN_USER_ACTIONS_PAGE = "/jsp/pages/admin/admin_user_actions.jsp";
    public static final String ADMIN_USER_ACTIONS_PAGE_REDIRECT = "/controller?command=USER_ACTION&user_id=";

    public static final String ADMIN_TARIFF_ADD_PAGE = "/jsp/pages/admin/admin_tariff_add.jsp";
    public static final String ADMIN_TARIFF_ADD_PAGE_REDIRECT = "/controller?command=TARIFF_ADD";

    public static final String ADMIN_TARIFF_EDIT_PAGE = "/jsp/pages/admin/admin_tariff_edit.jsp";
    public static final String ADMIN_TARIFF_EDIT_PAGE_REDIRECT = "/controller?command=TARIFF_EDIT";

    public static final String ADMIN_TARIFFS_LIST_PAGE = "/jsp/pages/admin/admin_tariffs_list.jsp";
    public static final String ADMIN_TARIFFS_LIST_PAGE_REDIRECT = "/controller?command=TARIFF_LIST&nextPage=0&previewPage=-1";

    public static final String ADMIN_PAYMENTS_CARD_ADD_PAGE = "/jsp/pages/admin/admin_express_payment_card_add.jsp";
    public static final String ADMIN_PAYMENTS_CARD_ADD_PAGE_REDIRECT = "/controller?command=CARD_ADD";
    public static final String ADMIN_PAYMENTS_CARD_GENERATED_PAGE = "/jsp/pages/admin/admin_express_payment_cards_generated_list.jsp";
    public static final String ADMIN_PAYMENTS_CARD_SEARCH = "/jsp/pages/admin/admin_express_payment_card_search.jsp";
    public static final String ADMIN_PAYMENTS_CARD_SEARCH_REDIRECT = "/controller?command=SEARCH_CARD";

    public static final String ADMIN_MAIN_PAGE = "/jsp/pages/admin/admin_main.jsp";

    //    Commons pages
    public static final String LOGIN_PAGE = "/jsp/pages/login.jsp";
    public static final String HOME_PAGE = "/controller?command=home";
    public static final String LOGOUT_PAGE = "/controller?command=logout";
    public static final String ACTIVATE_PAGE = "/controller?command=ACTIVATION&activationCode=";

    // USER_PERSONALS_PAGES
    public static final String USER_PROFILE_PAGE = "/jsp/pages/user/profile.jsp";
    public static final String USER_ALL_FINANCE_OPERATIONS_PAGE = "/jsp/pages/user/all_finance_operations.jsp";
    public static final String USER_CHANGE_PASSWORD_PAGE = "/jsp/pages/user/change_password.jsp";
    public static final String USER_TARIFFS_LIST = "/jsp/pages/user/tariffs.jsp";
    public static final String USER_TARIFFS_LIST_REDIRECT = "/controller?command=USER_TARIFF_LIST";
    public static final String USER_ACTIONS_PAGE = "/jsp/pages/user/actions.jsp";
    public static final String USER_ACTIVATE_PAYMENT_CARD = "/jsp/pages/user/payment_card.jsp";

    //Access error page
    public static final String ACCESS_ERROR_403_PAGE = "/jsp/pages/static_pages/error_403.jsp";

    private PageUrl() {
    }
}
