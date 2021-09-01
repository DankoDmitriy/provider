package com.danko.provider.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtil {
    private static Logger logger = LogManager.getLogger();

    public static String requestUrlToDomain(String requestUrl) {
        try {
            URL url = new URL(requestUrl);
            String host = url.getHost();
            String userInfo = url.getUserInfo();
            String scheme = url.getProtocol();
            Integer port = url.getPort();
            URI uri = new URI(scheme, userInfo, host, port, null, null, null);
            return uri.toString();
        } catch (MalformedURLException | URISyntaxException e) {
            logger.log(Level.WARN, "Domain address did not get. {}", e);
            return null;
        }
    }
}
