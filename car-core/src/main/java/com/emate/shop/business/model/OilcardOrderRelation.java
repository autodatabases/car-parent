package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilcardOrderRelation {
    /**
     * id
     * @Table oilcard_order_relation.id
     */
    private Long id;

    /**
     * 用户id
     * @Table oilcard_order_relation.user_id
     */
    private Long userId;

    /**
     * 
     * @Table oilcard_order_relation.card_no
     */
    private String cardNo;

    /**
     * 油卡 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_0 = 0;
    /**
     * 话费 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_1 = 1;
    /**
     * 洗车 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_2 = 2;
    /**
     * 代驾 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_3 = 3;
    /**
     * 流量充值 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_4 = 4;
    /**
     * 卡密 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_5 = 5;
    /**
     * 订单类型：0，油卡，1，话费，2，洗车，3，代驾，4，流量充值，5，卡密
     * @Table oilcard_order_relation.order_type
     */
    private Integer orderType;

    /**
     * 订单号
     * @Table oilcard_order_relation.order_no
     */
    private String orderNo;

    /**
     * 消费金额
     * @Table oilcard_order_relation.money
     */
    private BigDecimal money;

    /**
     * 充值卡剩余金额
     * @Table oilcard_order_relation.card_money
     */
    private BigDecimal cardMoney;

    /**
     * 创建时间
     * @Table oilcard_order_relation.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oilcard_order_relation.update_time
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
    * 获取 用户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 用户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 
    * @return cardNo
    */
    public String getCardNo() {
        return cardNo;
    }

    /**
    * 设置 
    * @param cardNo
    */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
    * 获取 订单类型：0，油卡，1，话费，2，洗车，3，代驾，4，流量充值，5，卡密
    * @return orderType
    */
    public Integer getOrderType() {
        return orderType;
    }

    /**
    * 设置 订单类型：0，油卡，1，话费，2，洗车，3，代驾，4，流量充值，5，卡密
    * @param orderType
    */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
    * 获取 订单号
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 订单号
    * @param orderNo
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    * 获取 消费金额
    * @return money
    */
    public BigDecimal getMoney() {
        return money;
    }

    /**
    * 设置 消费金额
    * @param money
    */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
    * 获取 充值卡剩余金额
    * @return cardMoney
    */
    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    /**
    * 设置 充值卡剩余金额
    * @param cardMoney
    */
    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
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