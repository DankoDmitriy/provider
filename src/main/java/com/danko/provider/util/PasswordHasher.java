package com.danko.provider.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHasher {
    private static Logger logger = LogManager.getLogger();
    private static final String ENCRYPTION_TYPE = "MD5";
    private static final String ENCODING = "utf8";

    private PasswordHasher() {
    }

    public static String hashString(String s) {
        MessageDigest messageDigest = null;
        byte[] bytesPass = null;
        try {
            messageDigest = MessageDigest.getInstance(ENCRYPTION_TYPE);
            messageDigest.update(s.getBytes(ENCODING));
            bytesPass = messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.log(Level.ERROR, "hashcode not generated {}" + e);
        }
        BigInteger bigInteger = new BigInteger(1, bytesPass);
        String resHes = bigInteger.toString(16);
        return resHes;
    }
}
