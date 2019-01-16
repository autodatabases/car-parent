package com.emate.shop.business.model;

import java.util.Date;

public class SalesmanStatistics {
    /**
     * 
     * @Table salesman_statistics.id
     */
    private Long id;

    /**
     * 用户id
     * @Table salesman_statistics.user_id
     */
    private Long userId;

    /**
     * 用户手机号
     * @Table salesman_statistics.user_phone
     */
    private String userPhone;

    /**
     * 用户车牌
     * @Table salesman_statistics.chepai
     */
    private String chepai;

    /**
     * 
     * @Table salesman_statistics.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table salesman_statistics.update_time
     */
    private Date updateTime;

    /**
     * 
     * @Table salesman_statistics.phone
     */
    private String phone;

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
    * 获取 用户手机号
    * @return userPhone
    */
    public String getUserPhone() {
        return userPhone;
    }

    /**
    * 设置 用户手机号
    * @param userPhone
    */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
    * 获取 用户车牌
    * @return chepai
    */
    public String getChepai() {
        return chepai;
    }

    /**
    * 设置 用户车牌
    * @param chepai
    */
    public void setChepai(String chepai) {
        this.chepai = chepai;
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

    /**
    * 获取 
    * @return phone
    */
    public String getPhone() {
        return phone;
    }

    /**
    * 设置 
    * @param phone
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}