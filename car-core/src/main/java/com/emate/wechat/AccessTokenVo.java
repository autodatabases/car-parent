package com.emate.wechat;

import java.util.Calendar;
import java.util.Date;


/**
 * 访问token
 * AccessToken.java
 * @author likk
 * 2015-7-22 下午03:01:39
 *
 */
public class AccessTokenVo {
	
	public static AccessTokenVo cacheToken;
	
	public static String access_token = "P9jnucLsdu6fNaLyMoFQfguLrIITXm6VU6GVK-LCyAuTv2_VnPKGPu0VO9oMKd02Vz3a0hQwdfIjeRoqyM5swTlPOKrJ3wHbXEHhQuFHQv24UkQVJ5vLnXRAsH5GUgbpNIViAIASNA";
	
	public static AccessTokenVo cacheJsTicket = null;
	
	public static String js_ticket = null;
	/**
	 * 从微信服务器获取
	 */
    private String token; 
    
    /**
     * 过期时间
     */
    private int expiresIn;
    
    
    
    /**
     * 是否过期
     */
    private boolean expried;
    
    /**
     * 更新时间
     */
    private Date updateTime;
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String toString(){
		return "token = " + getToken() + ";expries = " + getExpiresIn() + ";isExpried = " + isExpried();
	}
	
	public void setExpried(boolean expried) {
		this.expried = expried;
	}
	
	/**
	 * 是否过期
	 * @return
	 */
	public boolean isExpried(){
		return expried;
	}
	
	public boolean checkExpried(){
		if(getUpdateTime() == null){
			return true;
		}
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, -getExpiresIn() + 60 * 2); //当前时间减去过期时间
		return getUpdateTime().before(now.getTime());
	}
}  