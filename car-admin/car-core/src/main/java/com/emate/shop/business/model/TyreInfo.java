package com.emate.shop.business.model;

import java.util.Date;

public class TyreInfo {
    /**
     * 
     * @Table tyre_info.id
     */
    private Long id;

    /**
     * 品牌
     * @Table tyre_info.brand
     */
    private String brand;

    /**
     * 
     * @Table tyre_info.autoLine_name
     */
    private String autolineName;

    /**
     * 排量手动自动
     * @Table tyre_info.autoType_name
     */
    private String autotypeName;

    /**
     * 发动机排量
     * @Table tyre_info.engine_disp
     */
    private String engineDisp;

    /**
     * 生产年份
     * @Table tyre_info.product_time
     */
    private String productTime;

    /**
     * 变速器描述
     * @Table tyre_info.trans_desc
     */
    private String transDesc;

    /**
     * 前轮胎大小
     * @Table tyre_info.front_size
     */
    private String frontSize;

    /**
     * 后轮胎大小
     * @Table tyre_info.rear_size
     */
    private String rearSize;

    /**
     * 
     * @Table tyre_info.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table tyre_info.update_time
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
    * 获取 
    * @return autolineName
    */
    public String getAutolineName() {
        return autolineName;
    }

    /**
    * 设置 
    * @param autolineName
    */
    public void setAutolineName(String autolineName) {
        this.autolineName = autolineName;
    }

    /**
    * 获取 排量手动自动
    * @return autotypeName
    */
    public String getAutotypeName() {
        return autotypeName;
    }

    /**
    * 设置 排量手动自动
    * @param autotypeName
    */
    public void setAutotypeName(String autotypeName) {
        this.autotypeName = autotypeName;
    }

    /**
    * 获取 发动机排量
    * @return engineDisp
    */
    public String getEngineDisp() {
        return engineDisp;
    }

    /**
    * 设置 发动机排量
    * @param engineDisp
    */
    public void setEngineDisp(String engineDisp) {
        this.engineDisp = engineDisp;
    }

    /**
    * 获取 生产年份
    * @return productTime
    */
    public String getProductTime() {
        return productTime;
    }

    /**
    * 设置 生产年份
    * @param productTime
    */
    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    /**
    * 获取 变速器描述
    * @return transDesc
    */
    public String getTransDesc() {
        return transDesc;
    }

    /**
    * 设置 变速器描述
    * @param transDesc
    */
    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    /**
    * 获取 前轮胎大小
    * @return frontSize
    */
    public String getFrontSize() {
        return frontSize;
    }

    /**
    * 设置 前轮胎大小
    * @param frontSize
    */
    public void setFrontSize(String frontSize) {
        this.frontSize = frontSize;
    }

    /**
    * 获取 后轮胎大小
    * @return rearSize
    */
    public String getRearSize() {
        return rearSize;
    }

    /**
    * 设置 后轮胎大小
    * @param rearSize
    */
    public void setRearSize(String rearSize) {
        this.rearSize = rearSize;
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