package com.emate.wechat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;


/**
 * 
 * ParamesAPI.java
 * @author likk
 * 2015-12-25 下午02:35:24
 *
 */
public class ParamesAPI {
	private static Logger logger = Logger.getLogger(ParamesAPI.class);
	
	/**
	 * 企业id
	 */
	public static final String DEFAULT_SYSID = "001";
	
	public static ConcurrentHashMap<String, Map<String,String>> CORPS = new ConcurrentHashMap<String,Map<String,String>>();
	//
	//public static String DOMIAN = "http://101.227.240.249";
	
	public static String DOMIAN_KEY = "domian";
	
	// token
	public static String TOKEN_KEY = "token";  
	
	// 安全模式下加密key
	public static String ENCODINGAESKEY = "encodingAESKey"; 
	
	 //appid
	public static String CORPID_KEY = "corpId";
	
	// 微信秘钥
	public static String SECRET_KEY = "secret";
	
	static{
		Properties web = new Properties();
		try {
			web.load(ParamesAPI.class.getClassLoader().getResourceAsStream("web.properties"));
		} catch (IOException e) {
		}
		
		Map<String,String> param = new HashMap<String, String>();
		param.put(DOMIAN_KEY, web.getProperty("wx.domain","http://gz.emateglobal.com"));
		param.put(TOKEN_KEY, web.getProperty("wx.token","1234asdf"));
		param.put(ENCODINGAESKEY, web.getProperty("wx.encodingAESKey","QEUThC5ipGYtOnJGtX6kMMN40Uas8eWj3L94VIqJh9W"));
		param.put(CORPID_KEY, web.getProperty("wx.corpId","wxfd274e1de906b3b4"));
		param.put(SECRET_KEY, web.getProperty("wx.secret","67718d7845d3c39bafacc462dda27bac")); 
		CORPS.put(DEFAULT_SYSID, param);
	}
	/**
	 * 获取不同企业的token
	 * @param sysId
	 * @return
	 */
	public static String getToken(String sysId){
		if(CORPS.get(sysId)!=null){
			if(CORPS.get(sysId).get(TOKEN_KEY)!=null){
				return CORPS.get(sysId).get(TOKEN_KEY);
			}else{
				logger.error("sysId : " + sysId + "token为空！");
				return null;
			}
		}else{
			logger.error("sysId : " + sysId + "找不到配置！");
			return null;
		}
	}
	
	/**
	 * 获取不同企业的domian
	 * @param sysId
	 * @return
	 */
	public static String getDomian(String sysId){
		if(CORPS.get(sysId)!=null){
			if(CORPS.get(sysId).get(DOMIAN_KEY)!=null){
				return CORPS.get(sysId).get(DOMIAN_KEY);
			}else{
				logger.error("sysId : " + sysId + "domian为空！");
				return null;
			}
		}else{
			logger.error("sysId : " + sysId + "找不到配置！");
			return null;
		}
	}
	
	
	/**
	 * 获取不同企业的ENCODINGAESKEY
	 * @param sysId
	 * @return
	 */
	public static String getEncodingKey(String sysId){
		if(CORPS.get(sysId)!=null){
			if(CORPS.get(sysId).get(ENCODINGAESKEY)!=null){
				return CORPS.get(sysId).get(ENCODINGAESKEY);
			}else{
				logger.error("sysId : " + sysId + "ENCODINGAESKEY为空！");
				return null;
			}
		}else{
			logger.error("sysId : " + sysId + "找不到配置！");
			return null;
		}
	}
	
	
	/**
	 * 获取不同企业的CORPID_KEY
	 * @param sysId
	 * @return
	 */
	public static String getCorpId(String sysId){
		if(CORPS.get(sysId)!=null){
			if(CORPS.get(sysId).get(CORPID_KEY)!=null){
				return CORPS.get(sysId).get(CORPID_KEY);
			}else{
				logger.error("sysId : " + sysId + "CORPID_KEY为空！");
				return null;
			}
		}else{
			logger.error("sysId : " + sysId + "找不到配置！");
			return null;
		}
	}
	
	
	/**
	 * 获取不同企业的SECRET_KEY
	 * @param sysId
	 * @return
	 */
	public static String getSecret(String sysId){
		if(CORPS.get(sysId)!=null){
			if(CORPS.get(sysId).get(SECRET_KEY)!=null){
				return CORPS.get(sysId).get(SECRET_KEY);
			}else{
				logger.error("sysId : " + sysId + "SECRET_KEY为空！");
				return null;
			}
		}else{
			logger.error("sysId : " + sysId + "找不到配置！");
			return null;
		}
	}
	
}
