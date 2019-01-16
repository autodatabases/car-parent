package com.emate.shop.common;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

public class ShotUrl {
	private static Logger logger = Logger.getLogger(ShotUrl.class);
	/**
	 * 生成短链接
	 * @param url
	 * @return
	 */
	public static String toShortUrl(String url) {
	    String shortUrl = url;
	    try {
	        // 新浪生成短链接api
	        url = "http://api.t.sina.com.cn/short_url/shorten.json?source=3271760578&url_long="
	              + url;
	        String conResult =  HttpClientHelper.httpGet(url);
	        JSONArray arr = JSONArray.fromObject(conResult);
	        shortUrl = arr.getJSONObject(0).getString("url_short");
	    } catch (Exception e) {
	    		logger.error("[" + url + "]转换短链接时异常：" + e.getMessage());
	        e.printStackTrace();
	    }
	    return shortUrl;
	}
	public static void main(String[] args) {
		String url = "http://sd.auto1768.com:8078/ShengDaPointQuery/point/city.jhtm?source=GDGSCX";
		String shortUrl = toShortUrl(url);
		System.out.println(shortUrl);
	}
}

