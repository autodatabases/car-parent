/**
 * 
 */
package com.emate.shop.common;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @file NumberHelper.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月20日
 */
public class NumberHelper {

    public static boolean isZero(Number number) {
        return Objects.isNull(number) || number.doubleValue() == 0.00;
    }

    public static boolean isOne(Number number) {
        return Objects.nonNull(number) && number.doubleValue() == 1.00;
    }

    public static boolean isNotZero(Number number) {
        return !isZero(number);
    }

    public static boolean isNotOne(Number number) {
        return !isOne(number);
    }

    public static String toRadix(Long number, int radix) {
        if (number < 0) {
            return "-" + toRadix(-number, radix);
        }
        String result = "";
        while (number != 0) {
            long f = number % radix;
            if (f >= 10) {
                result += (char) (f - 10 + 'A');
            } else {
                result += (char) (f + '0');
            }
            number /= radix;
        }
        return new StringBuilder(result).reverse().toString();
    }

    public static Long fromRadix(String from, int radix) {
        return Long.parseLong(from, radix);
    }

    public static String toNine(Long[] numbers) {
        String string = Arrays.stream(numbers).map(number -> toRadix(number, 9))
                .collect(StringBuilder::new, (sb, s) -> sb.append(9).append(s),
                        StringBuilder::append)
                .toString();
        if (string.startsWith("9")) {
            string = string.substring(1);
        }
        return string;
    }

    public static Long[] fromNine(String nines) {
        return Arrays.stream(nines.split("9")).filter(StringHelper::notBlank)
                .map(nine -> fromRadix(nine, 9)).collect(Collectors.toList())
                .toArray(new Long[] {});
    }

    public static void main(String[] args) {
        String string = toNine(new Long[] { 34L, 30L });
        System.out.println(string);
        System.out.println(Arrays.toString(fromNine(string)));
    }
}
