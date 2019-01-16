package com.emate.shop.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncrypAES {

    
    // SecretKey 负责保存对称密钥
    private SecretKey deskey;
    // Cipher负责完成加密或解密工作
    private Cipher c;
    // 该字节数组负责保存加密的结果
    private byte[] cipherByte;
 // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
    private byte[] keys = "d07cb8ad".getBytes();
    
    public static EncrypAES getIns(){
    	return new EncrypAES();
    }


    @SuppressWarnings("restriction")
    public EncrypAES() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)  
        try {
	        DESKeySpec desKeySpec = new DESKeySpec(keys);
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        deskey = keyFactory.generateSecret(desKeySpec);
	        c = Cipher.getInstance("DES"); 
		} catch (Exception e) {
			e.printStackTrace();
		}  
    }

    /**
     * 对字符串加密
     * 
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] Encrytor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        c.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] src = str.getBytes();
        // 加密，结果保存进cipherByte
        cipherByte = c.doFinal(src);
        return  Base64.encodeBase64(cipherByte);
    }

    /**
     * 对字符串解密
     * 
     * @param buff
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] Decryptor(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
    	buff = Base64.decodeBase64(buff);
        c.init(Cipher.DECRYPT_MODE, deskey);
        cipherByte = c.doFinal(buff);
        return cipherByte;
    }

    /**
     * @param args
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws Exception {
        EncrypAES de1 = new EncrypAES();
        String msg = "郭XX-搞笑相声全集";
        byte[] encontent = de1.Encrytor(msg);
        System.out.println("加密后:" + new String(encontent));
        byte[] decontent = de1.Decryptor(encontent);
        System.out.println("解密后:" + new String(decontent));
        for(int i=0;i<100;i++){
        	 new Thread(new Runnable() {
     			
     			@Override
     			public void run() {
     				try {
     					 byte[] encontent = EncrypAES.getIns().Encrytor(msg);
     					 System.out.println("加密后:" + new String(encontent));
     					byte[] decontent = EncrypAES.getIns().Decryptor(encontent);
     					System.out.println("解密后:" + new String(decontent));
					} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
					}
     			}
     		}).start();
        }
       
    }

}
