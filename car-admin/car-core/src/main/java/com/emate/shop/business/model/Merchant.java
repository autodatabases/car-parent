package com.emate.shop.business.model;

import java.util.Date;

public class Merchant {
    /**
     * id
     * @Table merchant.id
     */
    private Long id;

    /**
     * 商户代码
     * @Table merchant.merchant_code
     */
    private String merchantCode;

    /**
     * 店铺代码
     * @Table merchant.store_code
     */
    private String storeCode;

    /**
     * 商户名称
     * @Table merchant.merchant_name
     */
    private String merchantName;

    /**
     * 商户简称
     * @Table merchant.short_name
     */
    private String shortName;

    /**
     * 店铺名称
     * @Table merchant.store_name
     */
    private String storeName;

    /**
     * 省
     * @Table merchant.province
     */
    private String province;

    /**
     * 城市
     * @Table merchant.city
     */
    private String city;

    /**
     * 区域
     * @Table merchant.area
     */
    private String area;

    /**
     * 地址
     * @Table merchant.address
     */
    private String address;

    /**
     * 油品供应 merchantType
     */
    public static final java.lang.Integer MERCHANT_TYPE_0 = 0;
    /**
     * 商场超市 merchantType
     */
    public static final java.lang.Integer MERCHANT_TYPE_1 = 1;
    /**
     * 饭店服务 merchantType
     */
    public static final java.lang.Integer MERCHANT_TYPE_2 = 2;
    /**
     * 其他采购 merchantType
     */
    public static final java.lang.Integer MERCHANT_TYPE_3 = 3;
    /**
     * 店铺类型：0，油品供应，1，商场超市，2，饭店服务，3，其他采购
     * @Table merchant.merchant_type
     */
    private Integer merchantType;

    /**
     * 创建时间
     * @Table merchant.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table merchant.update_time
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
    * 获取 商户代码
    * @return merchantCode
    */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
    * 设置 商户代码
    * @param merchantCode
    */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
    * 获取 店铺代码
    * @return storeCode
    */
    public String getStoreCode() {
        return storeCode;
    }

    /**
    * 设置 店铺代码
    * @param storeCode
    */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /**
    * 获取 商户名称
    * @return merchantName
    */
    public String getMerchantName() {
        return merchantName;
    }

    /**
    * 设置 商户名称
    * @param merchantName
    */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
    * 获取 商户简称
    * @return shortName
    */
    public String getShortName() {
        return shortName;
    }

    /**
    * 设置 商户简称
    * @param shortName
    */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
    * 获取 店铺名称
    * @return storeName
    */
    public String getStoreName() {
        return storeName;
    }

    /**
    * 设置 店铺名称
    * @param storeName
    */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
    * 获取 省
    * @return province
    */
    public String getProvince() {
        return province;
    }

    /**
    * 设置 省
    * @param province
    */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
    * 获取 城市
    * @return city
    */
    public String getCity() {
        return city;
    }

    /**
    * 设置 城市
    * @param city
    */
    public void setCity(String city) {
        this.city = city;
    }

    /**
    * 获取 区域
    * @return area
    */
    public String getArea() {
        return area;
    }

    /**
    * 设置 区域
    * @param area
    */
    public void setArea(String area) {
        this.area = area;
    }

    /**
    * 获取 地址
    * @return address
    */
    public String getAddress() {
        return address;
    }

    /**
    * 设置 地址
    * @param address
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
    * 获取 店铺类型：0，油品供应，1，商场超市，2，饭店服务，3，其他采购
    * @return merchantType
    */
    public Integer getMerchantType() {
        return merchantType;
    }

    /**
    * 设置 店铺类型：0，油品供应，1，商场超市，2，饭店服务，3，其他采购
    * @param merchantType
    */
    public void setMerchantType(Integer merchantType) {
        this.merchantType = merchantType;
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