package com.danko.provider.controller.command;

/**
 * The class represents string constants of parameters from HttpSession
 */
public final class SessionAttribute {

    /**
     * Common parameters for all user roles.
     */
    public static final String SESSION_USER = "user";
    public static final String IS_LOGIN_ERROR = "isLoginError";
    public static final String SESSION_LOCAL = "local";

    /**
     * Locals parameters for all user roles.
     */
    public static final String ENGLISH_LOCAL = "en_EN";
    public static final String DEFAULT_LOCAL = "ru_RU";

    private SessionAttribute() {
    }
}
