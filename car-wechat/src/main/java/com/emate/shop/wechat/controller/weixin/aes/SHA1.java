/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.emate.shop.wechat.controller.weixin.aes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SHA1 class
 *
 * 计算公众平台的消息签名接口.
 */
public class SHA1 {

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException
			  {
		try {
			String[] array = new String[] { token, timestamp, nonce, encrypt };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
	
	
	public static String sha1(String str){
		// SHA1签名生成
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("sha1加密失败！");
		}
		
	}
	
	public static void main(String[] args) {
        //微信jssdk
        String jsapi_ticket =  "kgt8ON7yVITDhtdwci0qeXrcjtLx5oR7BywU6kJegQauo04ooNEto2Y1GmUgYZI3cg1nlItGcPdqwRvJvn5p_g";
		String timestamp = "1439541753";
		String noncestr = "sdrf4qWy1uXmQrEf";
		String url = "http://pre.k.189.cn/m/game/run?uid=2015721535714380670371164566";
		Map<String,String> keyValue = new HashMap<String, String>();
		keyValue.put("jsapi_ticket", jsapi_ticket);
		keyValue.put("timestamp", timestamp);
		keyValue.put("noncestr", noncestr);
		keyValue.put("url", url);
		List<String> param = new ArrayList<String>();
		param.add("jsapi_ticket");
		param.add("timestamp");
		param.add("noncestr");
		param.add("url");
		java.util.Collections.sort(param);
		StringBuffer sb = new StringBuffer();
		for(String s : param){
			sb.append(s).append("=").append(keyValue.get(s)).append("&");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		System.out.println(sb.toString());
		String signature = SHA1.sha1(sb.toString());
		System.out.println(signature);
	}
}
