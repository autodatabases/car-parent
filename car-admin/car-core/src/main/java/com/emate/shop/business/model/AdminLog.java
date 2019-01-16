package com.emate.shop.business.model;

import java.util.Date;

public class AdminLog {
    /**
     * 
     * @Table admin_log.id
     */
    private Long id;

    /**
     * 
     * @Table admin_log.unid
     */
    private String unid;

    /**
     * 
     * @Table admin_log.user_name
     */
    private String userName;

    /**
     * 
     * @Table admin_log.user_agent
     */
    private String userAgent;

    /**
     * 
     * @Table admin_log.ip_address
     */
    private String ipAddress;

    /**
     * 
     * @Table admin_log.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table admin_log.update_time
     */
    private Date updateTime;

    /**
    * 获取 
    * @return id
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置 
    * @param id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取 
    * @return unid
    */
    public String getUnid() {
        return unid;
    }

    /**
    * 设置 
    * @param unid
    */
    public void setUnid(String unid) {
        this.unid = unid;
    }

    /**
    * 获取 
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 
    * @return userAgent
    */
    public String getUserAgent() {
        return userAgent;
    }

    /**
    * 设置 
    * @param userAgent
    */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
    * 获取 
    * @return ipAddress
    */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
    * 设置 
    * @param ipAddress
    */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
    * 获取 
    * @return createTime
    */
    public Date getCreateTime() {
        return createTime;
    }

    /**
    * 设置 
    * @param createTime
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
    * 获取 
    * @return updateTime
    */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
    * 设置 
    * @param updateTime
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}