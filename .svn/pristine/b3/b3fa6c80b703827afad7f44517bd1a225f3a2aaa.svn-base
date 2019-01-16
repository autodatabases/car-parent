package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilOrders {
    /**
     * 
     * @Table oil_orders.id
     */
    private Long id;

    /**
     * 代理商订单id
     * @Table oil_orders.order_no
     */
    private String orderNo;

    /**
     * 用户id
     * @Table oil_orders.user_id
     */
    private Long userId;

    /**
     * 中石化 chargeType
     */
    public static final java.lang.Byte CHARGE_TYPE_1 = 1;
    /**
     * 中石油 chargeType
     */
    public static final java.lang.Byte CHARGE_TYPE_2 = 2;
    /**
     * 手机充值 chargeType
     */
    public static final java.lang.Byte CHARGE_TYPE_3 = 3;
    /**
     * 充值类型：1，中石化，2，中石油，3，手机充值
     * @Table oil_orders.charge_type
     */
    private Byte chargeType;

    /**
     * 加油卡号或被充值手机号
     * @Table oil_orders.card_no
     */
    private String cardNo;

    /**
     * 接口提供者
     * @Table oil_orders.provider
     */
    private Long provider;

    /**
     * 第三方订单号
     * @Table oil_orders.provider_order_no
     */
    private String providerOrderNo;

    /**
     * 商品id
     * @Table oil_orders.good_id
     */
    private String goodId;

    /**
     * 商品介绍
     * @Table oil_orders.good_introduce
     */
    private String goodIntroduce;

    /**
     * 油卡持卡人手机号
     * @Table oil_orders.card_phone
     */
    private String cardPhone;

    /**
     * 用户ip
     * @Table oil_orders.user_ip
     */
    private String userIp;

    /**
     * 充值请求总面值
     * @Table oil_orders.fill_money
     */
    private BigDecimal fillMoney;

    /**
     * 充值完成总面值
     * @Table oil_orders.finish_money
     */
    private BigDecimal finishMoney;

    /**
     * 接口商户结算价
     * @Table oil_orders.shop_money
     */
    private BigDecimal shopMoney;

    /**
     * 订单完成后账户剩余金额
     * @Table oil_orders.surplus_money
     */
    private BigDecimal surplusMoney;

    /**
     * 待处理 orderStatus
     */
    public static final java.lang.Byte ORDER_STATUS_0 = 0;
    /**
     * 处理中 orderStatus
     */
    public static final java.lang.Byte ORDER_STATUS_1 = 1;
    /**
     * 充值成功 orderStatus
     */
    public static final java.lang.Byte ORDER_STATUS_2 = 2;
    /**
     * 部分成功 orderStatus
     */
    public static final java.lang.Byte ORDER_STATUS_3 = 3;
    /**
     * 充值失败 orderStatus
     */
    public static final java.lang.Byte ORDER_STATUS_4 = 4;
    /**
     * 订单状态：0，待处理，1，处理中，2，充值成功，3，部分成功，4，充值失败
     * @Table oil_orders.order_status
     */
    private Byte orderStatus;

    /**
     * 订单发货完成时间
     * @Table oil_orders.finish_time
     */
    private Date finishTime;

    /**
     * 欧飞 supplier
     */
    public static final java.lang.Integer SUPPLIER_0 = 0;
    /**
     * 订单类型：0，欧飞
     * @Table oil_orders.supplier
     */
    private Integer supplier;

    /**
     * 
     * @Table oil_orders.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table oil_orders.update_time
     */
    private Date updateTime;

    /**
     * 子订单数量
     * @Table oil_orders.count
     */
    private Integer count;

    /**
     * 正常订单 remark
     */
    public static final java.lang.Integer REMARK_0 = 0;
    /**
     * 异常订单 remark
     */
    public static final java.lang.Integer REMARK_1 = 1;
    /**
     * 订单正常与否：0，正常订单，1，异常订单
     * @Table oil_orders.remark
     */
    private Integer remark;

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
    * 获取 代理商订单id
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 代理商订单id
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
    * 获取 充值类型：1，中石化，2，中石油，3，手机充值
    * @return chargeType
    */
    public Byte getChargeType() {
        return chargeType;
    }

    /**
    * 设置 充值类型：1，中石化，2，中石油，3，手机充值
    * @param chargeType
    */
    public void setChargeType(Byte chargeType) {
        this.chargeType = chargeType;
    }

    /**
    * 获取 加油卡号或被充值手机号
    * @return cardNo
    */
    public String getCardNo() {
        return cardNo;
    }

    /**
    * 设置 加油卡号或被充值手机号
    * @param cardNo
    */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
    * 获取 接口提供者
    * @return provider
    */
    public Long getProvider() {
        return provider;
    }

    /**
    * 设置 接口提供者
    * @param provider
    */
    public void setProvider(Long provider) {
        this.provider = provider;
    }

    /**
    * 获取 第三方订单号
    * @return providerOrderNo
    */
    public String getProviderOrderNo() {
        return providerOrderNo;
    }

    /**
    * 设置 第三方订单号
    * @param providerOrderNo
    */
    public void setProviderOrderNo(String providerOrderNo) {
        this.providerOrderNo = providerOrderNo;
    }

    /**
    * 获取 商品id
    * @return goodId
    */
    public String getGoodId() {
        return goodId;
    }

    /**
    * 设置 商品id
    * @param goodId
    */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    /**
    * 获取 商品介绍
    * @return goodIntroduce
    */
    public String getGoodIntroduce() {
        return goodIntroduce;
    }

    /**
    * 设置 商品介绍
    * @param goodIntroduce
    */
    public void setGoodIntroduce(String goodIntroduce) {
        this.goodIntroduce = goodIntroduce;
    }

    /**
    * 获取 油卡持卡人手机号
    * @return cardPhone
    */
    public String getCardPhone() {
        return cardPhone;
    }

    /**
    * 设置 油卡持卡人手机号
    * @param cardPhone
    */
    public void setCardPhone(String cardPhone) {
        this.cardPhone = cardPhone;
    }

    /**
    * 获取 用户ip
    * @return userIp
    */
    public String getUserIp() {
        return userIp;
    }

    /**
    * 设置 用户ip
    * @param userIp
    */
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    /**
    * 获取 充值请求总面值
    * @return fillMoney
    */
    public BigDecimal getFillMoney() {
        return fillMoney;
    }

    /**
    * 设置 充值请求总面值
    * @param fillMoney
    */
    public void setFillMoney(BigDecimal fillMoney) {
        this.fillMoney = fillMoney;
    }

    /**
    * 获取 充值完成总面值
    * @return finishMoney
    */
    public BigDecimal getFinishMoney() {
        return finishMoney;
    }

    /**
    * 设置 充值完成总面值
    * @param finishMoney
    */
    public void setFinishMoney(BigDecimal finishMoney) {
        this.finishMoney = finishMoney;
    }

    /**
    * 获取 接口商户结算价
    * @return shopMoney
    */
    public BigDecimal getShopMoney() {
        return shopMoney;
    }

    /**
    * 设置 接口商户结算价
    * @param shopMoney
    */
    public void setShopMoney(BigDecimal shopMoney) {
        this.shopMoney = shopMoney;
    }

    /**
    * 获取 订单完成后账户剩余金额
    * @return surplusMoney
    */
    public BigDecimal getSurplusMoney() {
        return surplusMoney;
    }

    /**
    * 设置 订单完成后账户剩余金额
    * @param surplusMoney
    */
    public void setSurplusMoney(BigDecimal surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    /**
    * 获取 订单状态：0，待处理，1，处理中，2，充值成功，3，部分成功，4，充值失败
    * @return orderStatus
    */
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**
    * 设置 订单状态：0，待处理，1，处理中，2，充值成功，3，部分成功，4，充值失败
    * @param orderStatus
    */
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
    * 获取 订单发货完成时间
    * @return finishTime
    */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
    * 设置 订单发货完成时间
    * @param finishTime
    */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
    * 获取 订单类型：0，欧飞
    * @return supplier
    */
    public Integer getSupplier() {
        return supplier;
    }

    /**
    * 设置 订单类型：0，欧飞
    * @param supplier
    */
    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
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

    /**
    * 获取 子订单数量
    * @return count
    */
    public Integer getCount() {
        return count;
    }

    /**
    * 设置 子订单数量
    * @param count
    */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
    * 获取 订单正常与否：0，正常订单，1，异常订单
    * @return remark
    */
    public Integer getRemark() {
        return remark;
    }

    /**
    * 设置 订单正常与否：0，正常订单，1，异常订单
    * @param remark
    */
    public void setRemark(Integer remark) {
        this.remark = remark;
    }
}