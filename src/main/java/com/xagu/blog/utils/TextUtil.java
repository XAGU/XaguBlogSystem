package com.xagu.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xagu
 * Created on 2020/6/24
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public class TextUtil {
    private static final String regEx = "^([a-z0-9A-Z]+[_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmailAddress(String emailAddress) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }
}
