package com.danko.provider.controller.command;

public final class PageUrl {
    //    Administrator URLS
    public static final String ADMIN_USERS_LIST_PAGE = "/jsp/pages/admin/users_list.jsp";
    //    Commons pages
    public static final String LOGIN_PAGE = "/jsp/pages/login.jsp";
    public static final String HOME_PAGE = "/controller?command=home";
    public static final String LOGOUT_PAGE = "/controller?command=logout";

    // USER_PERSONALS_PAGES
    public static final String USER_PROFILE_PAGE = "/jsp/pages/user/profile.jsp";
    public static final String USER_ALL_FINANCE_OPERATIONS_PAGE = "/jsp/pages/user/all_finance_operations.jsp";
    public static final String USER_CHANGE_PASSWORD_PAGE = "/jsp/pages/user/change_password.jsp";
    public static final String USER_TARIFFS_LIST = "/jsp/pages/user/tariffs.jsp";


    private PageUrl() {
    }
}
