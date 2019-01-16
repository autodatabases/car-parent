package com.emate.shop.business.model;

import java.util.Date;

public class OilCardConfig {
    /**
     * 
     * @Table oil_card_config.id
     */
    private Long id;

    /**
     * 欧飞 supplier
     */
    public static final java.lang.Byte SUPPLIER_1 = 1;
    /**
     * 高阳捷迅 supplier
     */
    public static final java.lang.Byte SUPPLIER_2 = 2;
    /**
     * 服务提供商：1，欧飞，2，高阳捷迅
     * @Table oil_card_config.supplier
     */
    private Byte supplier;

    /**
     * 中石化 cardType
     */
    public static final java.lang.Byte CARD_TYPE_0 = 0;
    /**
     * 中石油 cardType
     */
    public static final java.lang.Byte CARD_TYPE_1 = 1;
    /**
     * 手机充值 cardType
     */
    public static final java.lang.Byte CARD_TYPE_2 = 2;
    /**
     * 油卡类型：0，中石化，1，中石油，2，手机充值
     * @Table oil_card_config.card_type
     */
    private Byte cardType;

    /**
     * 欧飞商品id
     * @Table oil_card_config.good_id
     */
    private String goodId;

    /**
     * 面额
     * @Table oil_card_config.content
     */
    private String content;

    /**
     * 正常显示 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 暂时维护 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 禁用 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 面额状态：0，正常显示，1，暂时维护，2，禁用
     * @Table oil_card_config.status
     */
    private Integer status;

    /**
     * 创建时间
     * @Table oil_card_config.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_card_config.update_time
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
    * 获取 服务提供商：1，欧飞，2，高阳捷迅
    * @return supplier
    */
    public Byte getSupplier() {
        return supplier;
    }

    /**
    * 设置 服务提供商：1，欧飞，2，高阳捷迅
    * @param supplier
    */
    public void setSupplier(Byte supplier) {
        this.supplier = supplier;
    }

    /**
    * 获取 油卡类型：0，中石化，1，中石油，2，手机充值
    * @return cardType
    */
    public Byte getCardType() {
        return cardType;
    }

    /**
    * 设置 油卡类型：0，中石化，1，中石油，2，手机充值
    * @param cardType
    */
    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    /**
    * 获取 欧飞商品id
    * @return goodId
    */
    public String getGoodId() {
        return goodId;
    }

    /**
    * 设置 欧飞商品id
    * @param goodId
    */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    /**
    * 获取 面额
    * @return content
    */
    public String getContent() {
        return content;
    }

    /**
    * 设置 面额
    * @param content
    */
    public void setContent(String content) {
        this.content = content;
    }

    /**
    * 获取 面额状态：0，正常显示，1，暂时维护，2，禁用
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 面额状态：0，正常显示，1，暂时维护，2，禁用
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
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