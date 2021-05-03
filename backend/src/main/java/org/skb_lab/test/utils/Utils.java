package org.skb_lab.test.utils;

import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static String getHex(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = regex.matcher(emailStr);
        return matcher.find();
    }

}
