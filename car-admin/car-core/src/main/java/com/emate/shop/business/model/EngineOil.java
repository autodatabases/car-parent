package com.emate.shop.business.model;

import java.util.Date;

public class EngineOil {
    /**
     * 
     * @Table engine_oil.id
     */
    private Long id;

    /**
     * 品牌
     * @Table engine_oil.brand
     */
    private String brand;

    /**
     * 
     * @Table engine_oil.autoLine_name
     */
    private String autolineName;

    /**
     * 发动机排量
     * @Table engine_oil.engine_disp
     */
    private String engineDisp;

    /**
     * 生产年份
     * @Table engine_oil.product_time
     */
    private String productTime;

    /**
     * 变速器描述
     * @Table engine_oil.trans_desc
     */
    private String transDesc;

    /**
     * 推荐的机油
     * @Table engine_oil.jiyou
     */
    private String jiyou;

    /**
     * 备注信息
     * @Table engine_oil.remark
     */
    private String remark;

    /**
     * 
     * @Table engine_oil.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table engine_oil.update_time
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
    * 获取 推荐的机油
    * @return jiyou
    */
    public String getJiyou() {
        return jiyou;
    }

    /**
    * 设置 推荐的机油
    * @param jiyou
    */
    public void setJiyou(String jiyou) {
        this.jiyou = jiyou;
    }

    /**
    * 获取 备注信息
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 备注信息
    * @param remark
    */
    public void setRemark(String remark) {
        this.remark = remark;
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