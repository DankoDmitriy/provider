package com.danko.provider.controller;

public class Router {
    public enum RouteType {
        FORWARD, REDIRECT;
    }

    private String pageUrl;
    private RouteType routeType = RouteType.FORWARD;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }
}
