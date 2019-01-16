package com.emate.shop.common;

import java.util.Random;

public class RandomHelper {

    public static final Random RANDOM = new Random();

    public static String randomNumberString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(RANDOM.nextInt(10));
        }
        return stringBuilder.toString();
    }
    /**
     * 保留前边部分的字符，后边字符省略
     * @param s
     * @param maxLength
     * @return
     */
    public static String substring(String s, int maxLength) {
        if (!((s != null) && (s.length() > 0))) {
            return s;
        }
        if (s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength) + "...";
    }

}
