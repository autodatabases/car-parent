package com.emate.shop.common;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtil {

    /** 年月日时分秒(无下划线) yyMMddHHmmss */
    public static final String DATE_FORMAT = "yyMMddHHmmssSSS";
    
    /** 年月日时分秒(无下划线) yyMMddHHmmss */
    public static final String DATE_FORMAT_TWO = "yyMMdd";

    /**
     * 获取count个随机数
     * @param count 随机数个数
     * @return
     */
    public static String randomNumber(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            //str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }
    public static void main11(String[] args) {
        System.out.println(randomStr(32));
    }
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyMMddHHmmss为格式的当前系统时间+2位随机数
     */
    public static synchronized String getOrderSn() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String sn = df.format(date);
        sn += randomNumber(2);
        return sn;
    }
    /**
     * 获取count个随机数
     * @param count 随机数个数
     * @return
     */
    public static String randomStr(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "123456789QWERTYUIOPLKJHGFDSAZXCVBNM";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            //str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyMMddHHmmss为格式的当前系统时间+2位随机数
     */
    public static synchronized String getDriverOrderNo() {
    	String str = "GDGSCX";
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_TWO);
        String sn = df.format(date);
        str += sn;
        str += randomStr(3);
        return str;
    }
    public static void main(String[] args) {
    	 System.out.println(getDriverOrderNo());
	}

}
