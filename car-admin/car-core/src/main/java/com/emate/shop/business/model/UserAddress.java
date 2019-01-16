package com.emate.shop.business.model;

import java.util.Date;

public class UserAddress {
    /**
     * 
     * @Table user_address.id
     */
    private Long id;

    /**
     * 会员ID
     * @Table user_address.user_id
     */
    private Long userId;

    /**
     * 收货人姓名
     * @Table user_address.user_name
     */
    private String userName;

    /**
     * 省ID
     * @Table user_address.province_id
     */
    private Integer provinceId;

    /**
     * 市ID
     * @Table user_address.city_id
     */
    private Integer cityId;

    /**
     * 区域ID
     * @Table user_address.area_id
     */
    private Integer areaId;

    /**
     * 省市区组合
     * @Table user_address.pro_city_area
     */
    private String proCityArea;

    /**
     * 收货地址
     * @Table user_address.address_content
     */
    private String addressContent;

    /**
     * 收货人手机号
     * @Table user_address.mobile
     */
    private String mobile;

    /**
     * 邮编
     * @Table user_address.zip_code
     */
    private String zipCode;

    /**
     * 默认收货地址
     * @Table user_address.default_address
     */
    private Boolean defaultAddress;

    /**
     * 
     * @Table user_address.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table user_address.update_time
     */
    private Date updateTime;

    /**
     * 新增 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 删除 status
     */
    public static final java.lang.Integer STATUS_9 = 9;
    /**
     * 状态：0，新增，9，删除
     * @Table user_address.status
     */
    private Integer status;

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
    * 获取 会员ID
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 会员ID
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 收货人姓名
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 收货人姓名
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 省ID
    * @return provinceId
    */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
    * 设置 省ID
    * @param provinceId
    */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
    * 获取 市ID
    * @return cityId
    */
    public Integer getCityId() {
        return cityId;
    }

    /**
    * 设置 市ID
    * @param cityId
    */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
    * 获取 区域ID
    * @return areaId
    */
    public Integer getAreaId() {
        return areaId;
    }

    /**
    * 设置 区域ID
    * @param areaId
    */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
    * 获取 省市区组合
    * @return proCityArea
    */
    public String getProCityArea() {
        return proCityArea;
    }

    /**
    * 设置 省市区组合
    * @param proCityArea
    */
    public void setProCityArea(String proCityArea) {
        this.proCityArea = proCityArea;
    }

    /**
    * 获取 收货地址
    * @return addressContent
    */
    public String getAddressContent() {
        return addressContent;
    }

    /**
    * 设置 收货地址
    * @param addressContent
    */
    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }

    /**
    * 获取 收货人手机号
    * @return mobile
    */
    public String getMobile() {
        return mobile;
    }

    /**
    * 设置 收货人手机号
    * @param mobile
    */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
    * 获取 邮编
    * @return zipCode
    */
    public String getZipCode() {
        return zipCode;
    }

    /**
    * 设置 邮编
    * @param zipCode
    */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
    * 获取 默认收货地址
    * @return defaultAddress
    */
    public Boolean getDefaultAddress() {
        return defaultAddress;
    }

    /**
    * 设置 默认收货地址
    * @param defaultAddress
    */
    public void setDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
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
    * 获取 状态：0，新增，9，删除
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，新增，9，删除
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

}