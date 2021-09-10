package com.danko.provider.controller.command;

public final class PageUrl {
    //    Administrator URLS
    public static final String ADMIN_USERS_LIST_PAGE = "/jsp/pages/admin/admin_users_list.jsp";
    public static final String ADMIN_USER_ADD_PAGE = "/jsp/pages/admin/admin_user_add.jsp";
    public static final String ADMIN_USER_ADD_CARD = "/jsp/pages/admin/admin_new_user_card.jsp";

    public static final String ADMIN_TARIFFS_LIST_PAGE = "/jsp/pages/admin/admin_tariffs_list.jsp";

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
    public static final String USER_ACTIONS_PAGE = "/jsp/pages/user/actions.jsp";
    public static final String USER_ACTIVATE_PAYMENT_CARD = "/jsp/pages/user/payment_card.jsp";

    //Access error page
    public static final String ACCESS_ERROR_PAGE = "/jsp/pages/static_pages/error.jsp";

    private PageUrl() {
    }
}
