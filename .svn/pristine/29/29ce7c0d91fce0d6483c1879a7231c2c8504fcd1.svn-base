package com.emate.shop.h5.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.tools.PCThreeDESUtil;

import net.sf.json.JSONObject;

public class ShengDaUtils {
	public static void main(String[] args) {
		String key = "e34ebd9bdb8c67d53be50f77f5a8b5d5";
		String key3Des = "C205BC5839533270jUN1d77Y";
		String url = "http://101.231.154.154:8042/ShengDaAutoPlatform/car!receiveOrder";
		Map<String, String> map = new HashMap<String, String>();

		// 请求参数
		String encryptJsonStr="{\"source\":\"GDGSCX\",\"orgSource\":\"GDGSCX\","
				+ "\"order\":\"2017050302\",\"carType\":\"01\","
				+ "\"userInfo\":\"13659766274\",\"generationRule\":\"02\","
				+ "\"userOrderType\":\"order\",\"randStr\":\"2017050303\"}";// 加密
		
		//{"source":"GDGSCX","orgSource":"GDGSCX","order":"2017050302","carType":"01","userInfo":"13659766274","generationRule":"02","userOrderType":"order","randStr":"2017050303"}
		
		String encryptParam = encrypt(encryptJsonStr, key, key3Des);

		map.put("encryptJsonStr", encryptParam);

		String message = null;
		try {
		//log.info("do post testQueryData request:{}", map.toString());
		message = HttpClientHelper.httpPost(url, map);
		//log.info("do post testQueryData response:{}", message);
		JSONObject obj = JSONObject.fromObject(message);
		System.out.println(obj.get("encryptJsonStr"));

		// 先3Des解密，再md5解密
		String serverStr = PCThreeDESUtil.decrypt(obj.get("encryptJsonStr").toString(), key3Des);
		System.out.println("serverStr : " + serverStr);
		String serverStrSpit[] = serverStr.split("[|]");
		StringBuffer stn = new StringBuffer();
		stn.append(serverStrSpit[0]).append(key);
		String sign = DigestUtils.md5Hex(stn.toString().getBytes("UTF-8")).toUpperCase();
		System.out.println("sign : " + sign);
		System.out.println(sign.equals(serverStrSpit[1]));
		} catch (Exception e) {
		//log.error("do post testQueryData error:{}", e.getMessage());
		}
	}
		/**
		*
		* @param encryptJsonStr
		* @param key
		* @return
		*/
		private static String encrypt(String encryptJsonStr, String key, String key3Des) {
			StringBuffer token = new StringBuffer();
			token.append(encryptJsonStr).append(key);
			String tokenMe = token.toString();
			String md5Str = null;
			try {
				md5Str = DigestUtils.md5Hex(tokenMe.getBytes("UTF-8")).toUpperCase();
				return PCThreeDESUtil.encrypt(encryptJsonStr + "|" + md5Str, key3Des);
			} catch (Exception e) {
				//log.error("PlaceOrderActionTest.encrypt : encrypt is fail", e);
				throw new BusinessException("加密失败");
			}
		}
}
