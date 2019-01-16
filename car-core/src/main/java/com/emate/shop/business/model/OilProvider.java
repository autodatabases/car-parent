package com.emate.shop.business.model;

import java.util.Date;

public class OilProvider {
    /**
     * id
     * @Table oil_provider.id
     */
    private Long id;

    /**
     * 公司名称
     * @Table oil_provider.company
     */
    private String company;

    /**
     * 用户名
     * @Table oil_provider.user_name
     */
    private String userName;

    /**
     * 密码
     * @Table oil_provider.user_password
     */
    private String userPassword;

    /**
     * 待使用 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 使用中 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 停用 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 欧飞账户状态：0，待使用，1，使用中，2，停用
     * @Table oil_provider.status
     */
    private Integer status;

    /**
     * 创建时间
     * @Table oil_provider.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_provider.update_time
     */
    private Date updateTime;

    /**
    * 获取 id
    * @return id
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置 id
    * @param id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取 公司名称
    * @return company
    */
    public String getCompany() {
        return company;
    }

    /**
    * 设置 公司名称
    * @param company
    */
    public void setCompany(String company) {
        this.company = company;
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
    * 获取 密码
    * @return userPassword
    */
    public String getUserPassword() {
        return userPassword;
    }

    /**
    * 设置 密码
    * @param userPassword
    */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
    * 获取 欧飞账户状态：0，待使用，1，使用中，2，停用
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 欧飞账户状态：0，待使用，1，使用中，2，停用
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 创建时间
    * @return createTime
    */
    public Date getCreateTime() {
        return createTime;
    }

    /**
    * 设置 创建时间
    * @param createTime
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
    * 获取 更新时间
    * @return updateTime
    */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
    * 设置 更新时间
    * @param updateTime
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}