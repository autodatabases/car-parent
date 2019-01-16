package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CarWashPay {
    /**
     * id
     * @Table car_wash_pay.id
     */
    private Long id;

    /**
     * 订单号
     * @Table car_wash_pay.order_no
     */
    private String orderNo;

    /**
     * 用户id
     * @Table car_wash_pay.user_id
     */
    private Long userId;

    /**
     * 订单总金额
     * @Table car_wash_pay.total_pay
     */
    private BigDecimal totalPay;

    /**
     * 账户余额支付
     * @Table car_wash_pay.account_pay
     */
    private BigDecimal accountPay;

    /**
     * 现金实际支付
     * @Table car_wash_pay.actual_pay
     */
    private BigDecimal actualPay;

    /**
     * 购买的洗车次数
     * @Table car_wash_pay.wash_number
     */
    private Integer washNumber;

    /**
     * 待支付 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 支付中 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 支付完成 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 支付失败 status
     */
    public static final java.lang.Integer STATUS_3 = 3;
    /**
     * 充值订单状态：0，待支付，1，支付中，2，支付完成，3，支付失败
     * @Table car_wash_pay.status
     */
    private Integer status;

    /**
     * 创建时间
     * @Table car_wash_pay.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table car_wash_pay.update_time
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
    * 获取 订单总金额
    * @return totalPay
    */
    public BigDecimal getTotalPay() {
        return totalPay;
    }

    /**
    * 设置 订单总金额
    * @param totalPay
    */
    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    /**
    * 获取 账户余额支付
    * @return accountPay
    */
    public BigDecimal getAccountPay() {
        return accountPay;
    }

    /**
    * 设置 账户余额支付
    * @param accountPay
    */
    public void setAccountPay(BigDecimal accountPay) {
        this.accountPay = accountPay;
    }

    /**
    * 获取 现金实际支付
    * @return actualPay
    */
    public BigDecimal getActualPay() {
        return actualPay;
    }

    /**
    * 设置 现金实际支付
    * @param actualPay
    */
    public void setActualPay(BigDecimal actualPay) {
        this.actualPay = actualPay;
    }

    /**
    * 获取 购买的洗车次数
    * @return washNumber
    */
    public Integer getWashNumber() {
        return washNumber;
    }

    /**
    * 设置 购买的洗车次数
    * @param washNumber
    */
    public void setWashNumber(Integer washNumber) {
        this.washNumber = washNumber;
    }

    /**
    * 获取 充值订单状态：0，待支付，1，支付中，2，支付完成，3，支付失败
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 充值订单状态：0，待支付，1，支付中，2，支付完成，3，支付失败
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