package com.emate.shop.business.model;

import java.util.Date;

public class BusinessInfo {
    /**
     * 
     * @Table business_info.id
     */
    private Long id;

    /**
     * 业务员姓名
     * @Table business_info.business_name
     */
    private String businessName;

    /**
     * 员工工号
     * @Table business_info.business_code
     */
    private String businessCode;

    /**
     * 绑定的手机号
     * @Table business_info.phone
     */
    private String phone;

    /**
     * 用户id
     * @Table business_info.user_id
     */
    private Long userId;

    /**
     * 
     * @Table business_info.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table business_info.update_time
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
    * 获取 业务员姓名
    * @return businessName
    */
    public String getBusinessName() {
        return businessName;
    }

    /**
    * 设置 业务员姓名
    * @param businessName
    */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
    * 获取 员工工号
    * @return businessCode
    */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
    * 设置 员工工号
    * @param businessCode
    */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
    * 获取 绑定的手机号
    * @return phone
    */
    public String getPhone() {
        return phone;
    }

    /**
    * 设置 绑定的手机号
    * @param phone
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
    * 获取 用户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 用户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
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