package com.emate.shop.common;

public class GenerateShortUrlUtil {
	private static String POST_URL = "http://dwz.cn/create.php";
	public static String  generateShortUrl(String url) {  
		return HttpClientHelper.httpPost(POST_URL, 
				HttpClientHelper.buildMap(new String[]{"url"}, new String[]{url}));
	}
	
	public static void main(String[] args) {
		System.out.println(GenerateShortUrlUtil.generateShortUrl("http://www.baidu.com"));
	}
}
