package com.emate.tools;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.UrlBase64;


public class PCThreeDESUtil {

 private static final String CRYPT_ALGORITHM = "DESede";
	 
	  //解密
	 public static String decrypt(String value,String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), CRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedByte = UrlBase64.decode(value.getBytes("UTF-8"));
            byte[] decryptedByte = cipher.doFinal(decodedByte);            
            return new String(decryptedByte,"UTF-8");
        } catch(Exception e) {

            throw e;
        }
    }
	//加密
	 public static String encrypt(String value,String key) throws Exception{
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), CRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            
            byte[] encryptedByte = cipher.doFinal(value.getBytes("UTF-8"));
            byte[] encodedByte = UrlBase64.encode(encryptedByte);
            return new String(encodedByte,"UTF-8");
        } catch(Exception e) {

            throw e;
        }
    }
	 
	 /**
	  * @param args
	  */
	public static void main(String[] args) throws Exception {
		String key="C405BC5839533270jUN1d77Y";
		String szSrc="abc|123";
		System.out.println("����ǰ���ַ�" + szSrc);   
		String encoded = encrypt(szSrc,key);
		System.out.println("���ܺ���ַ�" + encoded);

	} 
}
