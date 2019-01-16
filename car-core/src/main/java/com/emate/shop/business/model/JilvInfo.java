package com.emate.shop.business.model;

import java.util.Date;

public class JilvInfo {
    /**
     * 
     * @Table jilv_info.id
     */
    private Long id;

    /**
     * 品牌
     * @Table jilv_info.brand
     */
    private String brand;

    /**
     * 
     * @Table jilv_info.autoLine_name
     */
    private String autolineName;

    /**
     * 发动机排量
     * @Table jilv_info.engine_disp
     */
    private String engineDisp;

    /**
     * 生产年份
     * @Table jilv_info.product_time
     */
    private String productTime;

    /**
     * 推荐的机油
     * @Table jilv_info.jilv
     */
    private String jilv;

    /**
     * 推荐的机油
     * @Table jilv_info.barcode
     */
    private String barcode;

    /**
     * 
     * @Table jilv_info.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table jilv_info.update_time
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
    * 获取 推荐的机油
    * @return jilv
    */
    public String getJilv() {
        return jilv;
    }

    /**
    * 设置 推荐的机油
    * @param jilv
    */
    public void setJilv(String jilv) {
        this.jilv = jilv;
    }

    /**
    * 获取 推荐的机油
    * @return barcode
    */
    public String getBarcode() {
        return barcode;
    }

    /**
    * 设置 推荐的机油
    * @param barcode
    */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
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