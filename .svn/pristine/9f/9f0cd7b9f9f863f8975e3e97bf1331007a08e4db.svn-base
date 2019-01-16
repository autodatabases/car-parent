package com.emate.shop.common;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;

/**
 * Crypt工具类
 * 
 * @author wuzichang
 * @version 1.0
 * @date 2015-05-21 15:34:02
 */
@SuppressWarnings("restriction")
public enum CryptUtil {

    DES {

        @Override
        public byte[] encrypt(byte[] data, String key)
                throws InvalidKeyException, NoSuchAlgorithmException,
                InvalidKeySpecException, IOException, IllegalBlockSizeException,
                BadPaddingException, NoSuchPaddingException {
            Key k = toKey(new BASE64Decoder().decodeBuffer(key));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return cipher.doFinal(data);
        }

        @Override
        public byte[] decrypt(byte[] data, String key)
                throws InvalidKeyException, NoSuchAlgorithmException,
                InvalidKeySpecException, IOException, IllegalBlockSizeException,
                BadPaddingException, NoSuchPaddingException {
            Key k = toKey((new BASE64Decoder()).decodeBuffer(key));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, k);
            return cipher.doFinal(data);
        }
    };

    /**
     * DES 算法转换密钥
     * 
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    protected Key toKey(byte[] key) throws NoSuchAlgorithmException,
            InvalidKeyException, InvalidKeySpecException {
        SecretKey secretKey = null;
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        secretKey = keyFactory.generateSecret(desKeySpec);
        return secretKey;
    }

    /**
     * 加密
     * 
     * @param data
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     */
    public abstract byte[] encrypt(byte[] data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, IOException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException;

    /**
     * 解密
     * 
     * @param data
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     */
    public abstract byte[] decrypt(byte[] data, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, IOException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException;

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将byte[]转换为String
     * 
     * @param byte[]
     * @return string
     */
    public static String bytes2Hex(byte[] bs) {
        String ret = "";
        for (int i = 0; i < bs.length; i++) {
            String hex = Integer.toHexString(bs[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 字符串加密
     * 
     * @param str
     *            需要加密的字符串
     * @param seed
     *            加密种子
     * @param charset
     *            字符编码
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String seed, String charset)
            throws Exception {
        try {
            return URLEncoder.encode(
                    parseByte2HexStr(DES.encrypt(str.getBytes(charset), seed)),
                    charset);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 字符串解密
     * 
     * @param str
     *            需要解密的字符串
     * @param seed
     *            解密种子
     * @param charset
     *            字符编码
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, String seed, String charset)
            throws Exception {
        try {
            String code = URLDecoder.decode(str, charset);
            byte[] bytes = DES.decrypt(parseHexStr2Byte(code), seed);
            return new String(bytes, charset);
        } catch (Exception e) {
            throw e;
        }
    }
}