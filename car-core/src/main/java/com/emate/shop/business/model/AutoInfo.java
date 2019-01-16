package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class AutoInfo {
    /**
     * 
     * @Table auto_info.id
     */
    private Long id;

    /**
     * 品牌
     * @Table auto_info.brand
     */
    private String brand;

    /**
     * 车系
     * @Table auto_info.autoLine_name
     */
    private String autolineName;

    /**
     * 生成年份
     * @Table auto_info.product_time
     */
    private String productTime;

    /**
     * 排量
     * @Table auto_info.engine_disp
     */
    private String engineDisp;

    /**
     * 机油用量
     * @Table auto_info.oil_amount
     */
    private BigDecimal oilAmount;

    /**
     * 前轮大小
     * @Table auto_info.front_size
     */
    private String frontSize;

    /**
     * 后轮大小
     * @Table auto_info.rear_size
     */
    private String rearSize;

    /**
     * 创建时间
     * @Table auto_info.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table auto_info.update_time
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
    * 获取 品牌
    * @return brand
    */
    public String getBrand() {
        return brand;
    }

    /**
    * 设置 品牌
    * @param brand
    */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
    * 获取 车系
    * @return autolineName
    */
    public String getAutolineName() {
        return autolineName;
    }

    /**
    * 设置 车系
    * @param autolineName
    */
    public void setAutolineName(String autolineName) {
        this.autolineName = autolineName;
    }

    /**
    * 获取 生成年份
    * @return productTime
    */
    public String getProductTime() {
        return productTime;
    }

    /**
    * 设置 生成年份
    * @param productTime
    */
    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    /**
    * 获取 排量
    * @return engineDisp
    */
    public String getEngineDisp() {
        return engineDisp;
    }

    /**
    * 设置 排量
    * @param engineDisp
    */
    public void setEngineDisp(String engineDisp) {
        this.engineDisp = engineDisp;
    }

    /**
    * 获取 机油用量
    * @return oilAmount
    */
    public BigDecimal getOilAmount() {
        return oilAmount;
    }

    /**
    * 设置 机油用量
    * @param oilAmount
    */
    public void setOilAmount(BigDecimal oilAmount) {
        this.oilAmount = oilAmount;
    }

    /**
    * 获取 前轮大小
    * @return frontSize
    */
    public String getFrontSize() {
        return frontSize;
    }

    /**
    * 设置 前轮大小
    * @param frontSize
    */
    public void setFrontSize(String frontSize) {
        this.frontSize = frontSize;
    }

    /**
    * 获取 后轮大小
    * @return rearSize
    */
    public String getRearSize() {
        return rearSize;
    }

    /**
    * 设置 后轮大小
    * @param rearSize
    */
    public void setRearSize(String rearSize) {
        this.rearSize = rearSize;
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