package com.danko.provider.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import java.util.*;

public class InputContent {
    private final HashMap<String, Object> requestAttributes;
    private final Map<String, String[]> requestParameters;
    private final Map<String, Object> sessionAttributes;
    private String nextPage = null;

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

    public void requestAttributesPut(String key, Object value) {
        requestAttributes.put(key, value);
    }

    String[] getRequestParameter(String name) {
        return requestParameters.get(name);
    }

    Object getSessionAttribute(String name) {
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

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
