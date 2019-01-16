package com.emate.shop.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class CookieUtil {
	
	public static void setCookie(HttpServletResponse response, String key, String value) {
		try{
			Cookie cookie = new Cookie(key, URLEncoder.encode(value,"utf-8"));
			response.addCookie(cookie);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static String getCookie(HttpServletRequest request, String key) {
		if(StringUtils.isEmpty(key)){
			return null;
		}
		Cookie[] cookieArray = request.getCookies();
		if(cookieArray != null){
			for(Cookie cookie : cookieArray){
				if(cookie != null && key.equals(cookie.getName())){
					try{
						return URLDecoder.decode(cookie.getValue(), "utf-8");
					}catch(Exception e){
						throw new RuntimeException(e);
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String ss = URLEncoder.encode("likk","utf-8");
		System.out.println(ss);
		System.out.println(URLDecoder.decode(ss, "utf-8"));
	}
}
