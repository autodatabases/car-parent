package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilTransLog {
    /**
     * 
     * @Table oil_trans_log.id
     */
    private Long id;

    /**
     * 订单流水号（如果有）
     * @Table oil_trans_log.order_sn
     */
    private String orderSn;

    /**
     * 油卡用户id
     * @Table oil_trans_log.user_id
     */
    private Long userId;

    /**
     * 用户手机号
     * @Table oil_trans_log.user_phone
     */
    private String userPhone;

    /**
     * 串码充值 transType
     */
    public static final java.lang.Integer TRANS_TYPE_0 = 0;
    /**
     * 油卡电子券消费 transType
     */
    public static final java.lang.Integer TRANS_TYPE_1 = 1;
    /**
     * 中石油加油卡充值 transType
     */
    public static final java.lang.Integer TRANS_TYPE_2 = 2;
    /**
     * 中石化 transType
     */
    public static final java.lang.Integer TRANS_TYPE_3 = 3;
    /**
     * 手机充值 transType
     */
    public static final java.lang.Integer TRANS_TYPE_4 = 4;
    /**
     * 流量充值 transType
     */
    public static final java.lang.Integer TRANS_TYPE_5 = 5;
    /**
     * 扣减金额失败 transType
     */
    public static final java.lang.Integer TRANS_TYPE_6 = 6;
    /**
     * 交易类型：0，串码充值，1，油卡电子券消费，2，中石油加油卡充值，3，中石化，4，手机充值，5，流量充值，6，扣减金额失败
     * @Table oil_trans_log.trans_type
     */
    private Integer transType;

    /**
     * 交易金额
     * @Table oil_trans_log.tarde_money
     */
    private BigDecimal tardeMoney;

    /**
     * 
     * @Table oil_trans_log.user_money
     */
    private BigDecimal userMoney;

    /**
     * 备注，包含充值手机号，充值加油卡卡号
     * @Table oil_trans_log.remark
     */
    private String remark;

    /**
     * 
     * @Table oil_trans_log.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table oil_trans_log.update_time
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
    * 获取 订单流水号（如果有）
    * @return orderSn
    */
    public String getOrderSn() {
        return orderSn;
    }

    /**
    * 设置 订单流水号（如果有）
    * @param orderSn
    */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
    * 获取 油卡用户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 油卡用户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 用户手机号
    * @return userPhone
    */
    public String getUserPhone() {
        return userPhone;
    }

    /**
    * 设置 用户手机号
    * @param userPhone
    */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
    * 获取 交易类型：0，串码充值，1，油卡电子券消费，2，中石油加油卡充值，3，中石化，4，手机充值，5，流量充值，6，扣减金额失败
    * @return transType
    */
    public Integer getTransType() {
        return transType;
    }

    /**
    * 设置 交易类型：0，串码充值，1，油卡电子券消费，2，中石油加油卡充值，3，中石化，4，手机充值，5，流量充值，6，扣减金额失败
    * @param transType
    */
    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    /**
    * 获取 交易金额
    * @return tardeMoney
    */
    public BigDecimal getTardeMoney() {
        return tardeMoney;
    }

    /**
    * 设置 交易金额
    * @param tardeMoney
    */
    public void setTardeMoney(BigDecimal tardeMoney) {
        this.tardeMoney = tardeMoney;
    }

    /**
    * 获取 
    * @return userMoney
    */
    public BigDecimal getUserMoney() {
        return userMoney;
    }

    /**
    * 设置 
    * @param userMoney
    */
    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    /**
    * 获取 备注，包含充值手机号，充值加油卡卡号
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 备注，包含充值手机号，充值加油卡卡号
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
    * 获取 
    * @return updateTime
    */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
    * 设置 
    * @param updateTime
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}