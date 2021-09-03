package com.danko.provider.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

//FIXME - переименовать и тип в сервис и.т.д.
public class UserUtil {
    private static Logger logger = LogManager.getLogger();

    private UserUtil() {
    }

    public static String hashString(String s) {
        MessageDigest messageDigest = null;
        byte[] bytesPass = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5"); //в константу
            messageDigest.update(s.getBytes("utf8")); //в константу
            bytesPass = messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.log(Level.ERROR, "hashcode not generated {}" + e);
        }
        BigInteger bigInteger = new BigInteger(1, bytesPass);
        String resHes = bigInteger.toString(16);
        return resHes;
    }

    public static String generationOfActivationCode() {
        return UUID.randomUUID().toString();
    }
}
