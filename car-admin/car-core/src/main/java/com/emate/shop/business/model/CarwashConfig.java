package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CarwashConfig {
    /**
     * id
     * @Table carwash_config.id
     */
    private Long id;

    /**
     * 城市名称
     * @Table carwash_config.city_name
     */
    private String cityName;

    /**
     * 宽途汽车 washType
     */
    public static final java.lang.Integer WASH_TYPE_1 = 1;
    /**
     * 车服 washType
     */
    public static final java.lang.Integer WASH_TYPE_2 = 2;
    /**
     * 洗车类型：1，宽途汽车，2，车服
     * @Table carwash_config.wash_type
     */
    private Integer washType;

    /**
     * 每月送免费次数 countType
     */
    public static final java.lang.Integer COUNT_TYPE_1 = 1;
    /**
     * 每年送免费次数 countType
     */
    public static final java.lang.Integer COUNT_TYPE_2 = 2;
    /**
     * 计次方式：1，每月送免费次数，2，每年送免费次数
     * @Table carwash_config.count_type
     */
    private Integer countType;

    /**
     * 最小金额
     * @Table carwash_config.minimum
     */
    private BigDecimal minimum;

    /**
     * 最大金额
     * @Table carwash_config.maximum
     */
    private BigDecimal maximum;

    /**
     * 洗车次数
     * @Table carwash_config.car_times
     */
    private Integer carTimes;

    /**
     * 采购价格
     * @Table carwash_config.purchase_price
     */
    private BigDecimal purchasePrice;

    /**
     * 洗车价格
     * @Table carwash_config.wash_price
     */
    private BigDecimal washPrice;

    /**
     * 创建时间
     * @Table carwash_config.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table carwash_config.update_time
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
    * 获取 城市名称
    * @return cityName
    */
    public String getCityName() {
        return cityName;
    }

    /**
    * 设置 城市名称
    * @param cityName
    */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
    * 获取 洗车类型：1，宽途汽车，2，车服
    * @return washType
    */
    public Integer getWashType() {
        return washType;
    }

    /**
    * 设置 洗车类型：1，宽途汽车，2，车服
    * @param washType
    */
    public void setWashType(Integer washType) {
        this.washType = washType;
    }

    /**
    * 获取 计次方式：1，每月送免费次数，2，每年送免费次数
    * @return countType
    */
    public Integer getCountType() {
        return countType;
    }

    /**
    * 设置 计次方式：1，每月送免费次数，2，每年送免费次数
    * @param countType
    */
    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    /**
    * 获取 最小金额
    * @return minimum
    */
    public BigDecimal getMinimum() {
        return minimum;
    }

    /**
    * 设置 最小金额
    * @param minimum
    */
    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    /**
    * 获取 最大金额
    * @return maximum
    */
    public BigDecimal getMaximum() {
        return maximum;
    }

    /**
    * 设置 最大金额
    * @param maximum
    */
    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }

    /**
    * 获取 洗车次数
    * @return carTimes
    */
    public Integer getCarTimes() {
        return carTimes;
    }

    /**
    * 设置 洗车次数
    * @param carTimes
    */
    public void setCarTimes(Integer carTimes) {
        this.carTimes = carTimes;
    }

    /**
    * 获取 采购价格
    * @return purchasePrice
    */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
    * 设置 采购价格
    * @param purchasePrice
    */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
    * 获取 洗车价格
    * @return washPrice
    */
    public BigDecimal getWashPrice() {
        return washPrice;
    }

    /**
    * 设置 洗车价格
    * @param washPrice
    */
    public void setWashPrice(BigDecimal washPrice) {
        this.washPrice = washPrice;
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