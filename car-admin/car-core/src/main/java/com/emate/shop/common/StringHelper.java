package com.emate.shop.common;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StringHelper {

    public static Boolean isEmpty(String string) {
        if (Objects.isNull(string)) {
            return true;
        }
        if (Objects.equals(0, string.length())) {
            return true;
        }
        return false;
    }

    public static Boolean notEmpty(String string) {
        return !isEmpty(string);
    }

    public static Boolean isBlank(String string) {
        if (Objects.isNull(string)) {
            return true;
        }
        if (Objects.equals(0, string.trim().length())) {
            return true;
        }
        return false;
    }

    public static Boolean notBlank(String string) {
        return !isBlank(string);
    }

    public static Stream<String> split(String string, String split) {
        return Arrays.stream(string.split(split));
    }

    public static Stream<String> splitByComma(String string) {
        return split(string, ",");
    }

    public static Stream<String> splitBySemicolon(String string) {
        return split(string, ";");
    }

    public static Stream<String> splitByUnderline(String string) {
        return split(string, "_");
    }

    public static Boolean isInteger(String string) {
        return string.matches("^\\d+$");
    }

    public static String concatByComma(List<String> strings) {
        String result = strings.stream()
                .collect(StringBuilder::new,
                        (builder, string) -> builder.append(string + ","),
                        StringBuilder::append)
                .toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 轉換數據庫字符串
     * @return
     */
    public static String changeDatabaseStrToObj(String source) {
        if (isBlank(source) || isEmpty(source)) {
            return null;
        }
        if (source.indexOf('_') <= 0) {
            return source;
        }
        String[] words = source.split("_");
        StringBuffer sb = new StringBuffer(words[0]);
        for (int i = 1; i < words.length; i++) {
            StringBuilder sbulid = new StringBuilder(words[i]);
            sbulid.setCharAt(0, Character.toUpperCase(sbulid.charAt(0)));
            sb.append(sbulid.toString());
        }
        return sb.toString();
    }

    public static String upperCap(String name) {
        if (Objects.isNull(name) || StringHelper.isEmpty(name)) {
            return name;
        }
        return name.substring(0, 1).toUpperCase()
                + name.substring(1, name.length());
    }

    public static String lowerCap(String name) {
        if (Objects.isNull(name) || StringHelper.isEmpty(name)) {
            return name;
        }
        return name.substring(0, 1).toLowerCase()
                + name.substring(1, name.length());
    }

    public static String dbName2JavaName(String dbName) {
        String javaName = Stream.of(dbName).filter(StringHelper::notBlank)
                .flatMap(StringHelper::splitByUnderline)
                .map(StringHelper::upperCap).collect(StringBuilder::new,
                        StringBuilder::append, StringBuilder::append)
                .toString();
        return lowerCap(javaName);
    }
}
