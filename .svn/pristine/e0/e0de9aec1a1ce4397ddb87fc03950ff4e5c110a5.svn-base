/**
 * 
 */
package com.emate.tools.gaoyang;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @file KeyedDigestMD5.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月8日
 */
public class KeyedDigestMD5 {

    public static byte[] getKeyedDigest(byte[] buffer, byte[] key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(buffer);
            return md5.digest(key);
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getKeyedDigest(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("UTF8"));
            String result = "";
            byte[] temp;
            temp = md5.digest(key.getBytes("UTF8"));
            for (int i = 0; i < temp.length; i++) {
                result += Integer
                        .toHexString((0x000000ff & temp[i]) | 0xffffff00)
                        .substring(6);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
