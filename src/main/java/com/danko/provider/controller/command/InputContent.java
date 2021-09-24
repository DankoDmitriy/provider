package com.danko.provider.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class InputContent {
    private final HashMap<String, Object> requestAttributes;
    private final Map<String, String[]> requestParameters;
    private final Map<String, Object> sessionAttributes;
    private String pageUrl;
    private boolean redirect;

    public InputContent(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();

        HttpSession session = request.getSession();
        List<String> attributeNames = Collections.list(session.getAttributeNames());
        attributeNames.forEach(s -> {
            Object object = session.getAttribute(s);
            sessionAttributes.put(s, object);
        });
    }

    public void putRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public String[] getRequestParameter(String name) {
        return requestParameters.get(name);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }
}
