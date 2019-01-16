package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CountermanGood {
    /**
     * 
     * @Table counterman_good.id
     */
    private Long id;

    /**
     * 商品名称
     * @Table counterman_good.good_name
     */
    private String goodName;

    /**
     * 商品图片url地址
     * @Table counterman_good.good_img
     */
    private String goodImg;

    /**
     * 商品积分
     * @Table counterman_good.good_score
     */
    private BigDecimal goodScore;

    /**
     * 商品价格
     * @Table counterman_good.good_price
     */
    private BigDecimal goodPrice;

    /**
     * 国寿结算价格
     * @Table counterman_good.guo_price
     */
    private BigDecimal guoPrice;

    /**
     * 商品数量
     * @Table counterman_good.good_number
     */
    private BigDecimal goodNumber;

    /**
     * 财险网点id
     * @Table counterman_good.cai_dot_id
     */
    private Long caiDotId;

    /**
     * 所属财险网点
     * @Table counterman_good.cai_dot_name
     */
    private String caiDotName;

    /**
     * 上架 goodStatus
     */
    public static final java.lang.Integer GOOD_STATUS_0 = 0;
    /**
     * 下架 goodStatus
     */
    public static final java.lang.Integer GOOD_STATUS_1 = 1;
    /**
     * 商品状态：0，上架，1，下架
     * @Table counterman_good.good_status
     */
    private Integer goodStatus;

    /**
     * 商品介绍
     * @Table counterman_good.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table counterman_good.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table counterman_good.update_time
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
    * 获取 商品名称
    * @return goodName
    */
    public String getGoodName() {
        return goodName;
    }

    /**
    * 设置 商品名称
    * @param goodName
    */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
    * 获取 商品图片url地址
    * @return goodImg
    */
    public String getGoodImg() {
        return goodImg;
    }

    /**
    * 设置 商品图片url地址
    * @param goodImg
    */
    public void setGoodImg(String goodImg) {
        this.goodImg = goodImg;
    }

    /**
    * 获取 商品积分
    * @return goodScore
    */
    public BigDecimal getGoodScore() {
        return goodScore;
    }

    /**
    * 设置 商品积分
    * @param goodScore
    */
    public void setGoodScore(BigDecimal goodScore) {
        this.goodScore = goodScore;
    }

    /**
    * 获取 商品价格
    * @return goodPrice
    */
    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    /**
    * 设置 商品价格
    * @param goodPrice
    */
    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    /**
    * 获取 国寿结算价格
    * @return guoPrice
    */
    public BigDecimal getGuoPrice() {
        return guoPrice;
    }

    /**
    * 设置 国寿结算价格
    * @param guoPrice
    */
    public void setGuoPrice(BigDecimal guoPrice) {
        this.guoPrice = guoPrice;
    }

    /**
    * 获取 商品数量
    * @return goodNumber
    */
    public BigDecimal getGoodNumber() {
        return goodNumber;
    }

    /**
    * 设置 商品数量
    * @param goodNumber
    */
    public void setGoodNumber(BigDecimal goodNumber) {
        this.goodNumber = goodNumber;
    }

    /**
    * 获取 财险网点id
    * @return caiDotId
    */
    public Long getCaiDotId() {
        return caiDotId;
    }

    /**
    * 设置 财险网点id
    * @param caiDotId
    */
    public void setCaiDotId(Long caiDotId) {
        this.caiDotId = caiDotId;
    }

    /**
    * 获取 所属财险网点
    * @return caiDotName
    */
    public String getCaiDotName() {
        return caiDotName;
    }

    /**
    * 设置 所属财险网点
    * @param caiDotName
    */
    public void setCaiDotName(String caiDotName) {
        this.caiDotName = caiDotName;
    }

    /**
    * 获取 商品状态：0，上架，1，下架
    * @return goodStatus
    */
    public Integer getGoodStatus() {
        return goodStatus;
    }

    /**
    * 设置 商品状态：0，上架，1，下架
    * @param goodStatus
    */
    public void setGoodStatus(Integer goodStatus) {
        this.goodStatus = goodStatus;
    }

    /**
    * 获取 商品介绍
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 商品介绍
    * @param remark
    */
    public void setRemark(String remark) {
        this.remark = remark;
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