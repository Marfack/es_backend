package com.es.util;

import java.util.Random;

/**
 * Created on 2021/12/17 16:02
 *
 * @author Marfack
 */
public class StringUtils {

    private StringUtils() {}

    public static String randomVerificationCode() {
        Random r = new Random();
        int len = 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
}
