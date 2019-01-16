package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class JilvConfig {
    /**
     * id
     * @Table jilv_config.id
     */
    private Long id;

    /**
     * 所属城市
     * @Table jilv_config.city_name
     */
    private String cityName;

    /**
     * 车辆价格最小值
     * @Table jilv_config.min
     */
    private BigDecimal min;

    /**
     * 车辆价格最大值
     * @Table jilv_config.max
     */
    private BigDecimal max;

    /**
     * 机滤价格
     * @Table jilv_config.jilv_price
     */
    private BigDecimal jilvPrice;

    /**
     * 创建时间
     * @Table jilv_config.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table jilv_config.update_time
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
    * 获取 所属城市
    * @return cityName
    */
    public String getCityName() {
        return cityName;
    }

    /**
    * 设置 所属城市
    * @param cityName
    */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
    * 获取 车辆价格最小值
    * @return min
    */
    public BigDecimal getMin() {
        return min;
    }

    /**
    * 设置 车辆价格最小值
    * @param min
    */
    public void setMin(BigDecimal min) {
        this.min = min;
    }

    /**
    * 获取 车辆价格最大值
    * @return max
    */
    public BigDecimal getMax() {
        return max;
    }

    /**
    * 设置 车辆价格最大值
    * @param max
    */
    public void setMax(BigDecimal max) {
        this.max = max;
    }

    /**
    * 获取 机滤价格
    * @return jilvPrice
    */
    public BigDecimal getJilvPrice() {
        return jilvPrice;
    }

    /**
    * 设置 机滤价格
    * @param jilvPrice
    */
    public void setJilvPrice(BigDecimal jilvPrice) {
        this.jilvPrice = jilvPrice;
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