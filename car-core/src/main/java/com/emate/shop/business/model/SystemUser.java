package com.emate.shop.business.model;

import java.util.Date;

public class SystemUser {
    /**
     * 
     * @Table system_user.id
     */
    private Long id;

    /**
     * 用户名
     * @Table system_user.user_name
     */
    private String userName;

    /**
     * 用户头像
     * @Table system_user.user_image
     */
    private String userImage;

    /**
     * 登陆密码
     * @Table system_user.password
     */
    private String password;

    /**
     * 管理员地理位置
     * @Table system_user.address
     */
    private String address;

    /**
     * 创建人ID
     * @Table system_user.creator_id
     */
    private Long creatorId;

    /**
     * 创建人
     * @Table system_user.creator
     */
    private String creator;

    /**
     * 正常 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 冻结 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 删除 status
     */
    public static final java.lang.Integer STATUS_9 = 9;
    /**
     * 状态：0，正常，1，冻结，9，删除
     * @Table system_user.status
     */
    private Integer status;

    /**
     * 上次登录时间
     * @Table system_user.last_login_time
     */
    private Date lastLoginTime;

    /**
     * 上次登录ip地址
     * @Table system_user.last_login_ip
     */
    private String lastLoginIp;

    /**
     * 登录次数
     * @Table system_user.login_time
     */
    private Long loginTime;

    /**
     * 
     * @Table system_user.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table system_user.update_time
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
    * 获取 用户名
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 用户名
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 用户头像
    * @return userImage
    */
    public String getUserImage() {
        return userImage;
    }

    /**
    * 设置 用户头像
    * @param userImage
    */
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    /**
    * 获取 登陆密码
    * @return password
    */
    public String getPassword() {
        return password;
    }

    /**
    * 设置 登陆密码
    * @param password
    */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
    * 获取 管理员地理位置
    * @return address
    */
    public String getAddress() {
        return address;
    }

    /**
    * 设置 管理员地理位置
    * @param address
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
    * 获取 创建人ID
    * @return creatorId
    */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
    * 设置 创建人ID
    * @param creatorId
    */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
    * 获取 创建人
    * @return creator
    */
    public String getCreator() {
        return creator;
    }

    /**
    * 设置 创建人
    * @param creator
    */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
    * 获取 状态：0，正常，1，冻结，9，删除
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，正常，1，冻结，9，删除
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 上次登录时间
    * @return lastLoginTime
    */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
    * 设置 上次登录时间
    * @param lastLoginTime
    */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
    * 获取 上次登录ip地址
    * @return lastLoginIp
    */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
    * 设置 上次登录ip地址
    * @param lastLoginIp
    */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
    * 获取 登录次数
    * @return loginTime
    */
    public Long getLoginTime() {
        return loginTime;
    }

    /**
    * 设置 登录次数
    * @param loginTime
    */
    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
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