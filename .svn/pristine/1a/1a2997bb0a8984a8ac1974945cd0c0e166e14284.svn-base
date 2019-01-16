package com.emate.shop.common;

/**
 * @author likk
 * @time 2014-7-30 下午1:10:08
 * 
 */

public final class Token {

    private String sysId;

    private Long   userId;

    private String username;

    @Override
    public String toString() {
        return "Token [sysId=" + sysId + ", userId=" + userId + ", username="
                + username + ", expireTime=" + expireTime + ", statusCode="
                + statusCode + "]";
    }

    private String expireTime;

    /**
     * 存放解释token的返回值
     */
    private int    statusCode;

    public Token() {

    }

    public Token(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
    
    public boolean isValidate(){
    	return this.getStatusCode() == 200;
    }

    public String generateDigest() throws Exception {
        return TokenUtil.generateDigest(sysId, userId, username, expireTime);
    }
}
