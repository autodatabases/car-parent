/**
 * 
 */
package com.emate.shop.web.aop;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.emate.shop.rpc.dto.RpcDtoHelper;

/**
 * @file AuthController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月12日
 */
public interface AuthUtil{
	
	public static int COOKIE_TIME = 60*60*24; //cookie默认有效期
	
	/**
	 * 商家端token
	 */
	public static final String CAR_SELLER_TOKEN = "CAR_SELLER_TOKEN";
	
	/**
	 * 平台端token
	 */
	public static final String CAR_ADMIN_TOKEN = "CAR_ADMIN_TOKEN";
	
	/**
	 * h5端token
	 */
	public static final String CAR_H5_TOKEN = "CAR_H5_TOKEN";
	
	/**
	 * h5端token
	 */
	public static final String CAR_OIL_TOKEN = "CAR_OIL_TOKEN";

    default String encodeUserId(String userId) {
    	String result = null;
    	try {
    		result = RpcDtoHelper
                    .encrypt(userId + "_" + System.currentTimeMillis());
    		result = URLEncoder.encode(result,"utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
        return result;
    }

	default String decodeUserId(String code){
    	if(StringUtils.isEmpty(code)){
    		return null;
    	}
    	try {
			code = URLDecoder.decode(code,"utf-8");
			code = URLDecoder.decode(code,"utf-8");//解码两次
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    	String token = (String)RpcDtoHelper.decrypt(code);
    	if(StringUtils.isEmpty(token) || !token.contains("_")){
    		return null;
    	}
    	String userId = token.split("_")[0];
//    	String time = token.split("_")[1];
//    	if(System.currentTimeMillis() - Long.parseLong(time) > 1000*COOKIE_TIME){
//    		//cookie已经过期
//    		return null;
//    	}
        return userId;
    }
    
    default String getToken(Long userId){
    	return  this.encodeUserId(String.valueOf(userId));
    }
    

    default Long getUserId(HttpServletRequest request,String systemName) {
        if (Objects.isNull(request) || Objects.isNull(request.getCookies())) {
            return null;
        }
        try {
        	String userInfo = Arrays.stream(request.getCookies())
                    .filter(cookie -> Objects.equals(systemName,
                            cookie.getName()))
                    .map(Cookie::getValue).map(this::decodeUserId).findFirst()
                    .orElse(null);
        	if(Objects.nonNull(userInfo)){
        		return Long.parseLong(userInfo);
        	}
        	return null;
        } catch (Throwable e) {
        	e.printStackTrace();
        	return null;
        }
    }

    //根据tokenName判断用户是否登录
    default Boolean isLogin(HttpServletRequest request,String systemName) {
        return Objects.nonNull(this.getUserId(request,systemName));
    }

}
