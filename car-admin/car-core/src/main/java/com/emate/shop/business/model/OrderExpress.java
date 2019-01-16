package com.emate.shop.business.model;

import java.util.Date;

public class OrderExpress {
    /**
     * 
     * @Table order_express.id
     */
    private Long id;

    /**
     * 订单ID
     * @Table order_express.order_id
     */
    private Long orderId;

    /**
     * 订单流水号
     * @Table order_express.order_no
     */
    private String orderNo;

    /**
     * 快递公司ID
     * @Table order_express.express_id
     */
    private Long expressId;

    /**
     * 快递公司名称
     * @Table order_express.express_name
     */
    private String expressName;

    /**
     * 快递公司代码
     * @Table order_express.express_code
     */
    private String expressCode;

    /**
     * 快递单号
     * @Table order_express.express_no
     */
    private String expressNo;

    /**
     * 收件人手机号
     * @Table order_express.mobile
     */
    private String mobile;

    /**
     * 商品名称
     * @Table order_express.product_name
     */
    private String productName;

    /**
     * 出发城市
     * @Table order_express.from_city
     */
    private String fromCity;

    /**
     * 到达城市
     * @Table order_express.to_city
     */
    private String toCity;

    /**
     * 快递信息订阅
     * @Table order_express.poll
     */
    private Boolean poll;

    /**
     * 失败次数
     * @Table order_express.fail_times
     */
    private Integer failTimes;

    /**
     * 在途中 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_0 = 0;
    /**
     * 已揽收 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_1 = 1;
    /**
     * 疑难 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_2 = 2;
    /**
     * 已签收 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_3 = 3;
    /**
     * 退签 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_4 = 4;
    /**
     * 同城派送中 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_5 = 5;
    /**
     * 退回 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_6 = 6;
    /**
     * 转单 expressStatus
     */
    public static final java.lang.Integer EXPRESS_STATUS_7 = 7;
    /**
     * 快递状态：0，在途中，1，已揽收，2，疑难，3，已签收，4，退签，5，同城派送中，6，退回，7，转单
     * @Table order_express.express_status
     */
    private Integer expressStatus;

    /**
     * 快递100返回内容
     * @Table order_express.response_json
     */
    private String responseJson;

    /**
     * 
     * @Table order_express.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table order_express.update_time
     */
    private Date updateTime;

    /**
     * 
     * @Table order_express.seq
     */
    private Long seq;

    /**
     * 新建 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 删除 status
     */
    public static final java.lang.Integer STATUS_9 = 9;
    /**
     * 状态：0，新建，9，删除
     * @Table order_express.status
     */
    private Integer status;

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
    * 获取 订单ID
    * @return orderId
    */
    public Long getOrderId() {
        return orderId;
    }

    /**
    * 设置 订单ID
    * @param orderId
    */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
    * 获取 订单流水号
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 订单流水号
    * @param orderNo
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    * 获取 快递公司ID
    * @return expressId
    */
    public Long getExpressId() {
        return expressId;
    }

    /**
    * 设置 快递公司ID
    * @param expressId
    */
    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    /**
    * 获取 快递公司名称
    * @return expressName
    */
    public String getExpressName() {
        return expressName;
    }

    /**
    * 设置 快递公司名称
    * @param expressName
    */
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    /**
    * 获取 快递公司代码
    * @return expressCode
    */
    public String getExpressCode() {
        return expressCode;
    }

    /**
    * 设置 快递公司代码
    * @param expressCode
    */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /**
    * 获取 快递单号
    * @return expressNo
    */
    public String getExpressNo() {
        return expressNo;
    }

    /**
    * 设置 快递单号
    * @param expressNo
    */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    /**
    * 获取 收件人手机号
    * @return mobile
    */
    public String getMobile() {
        return mobile;
    }

    /**
    * 设置 收件人手机号
    * @param mobile
    */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
    * 获取 商品名称
    * @return productName
    */
    public String getProductName() {
        return productName;
    }

    /**
    * 设置 商品名称
    * @param productName
    */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
    * 获取 出发城市
    * @return fromCity
    */
    public String getFromCity() {
        return fromCity;
    }

    /**
    * 设置 出发城市
    * @param fromCity
    */
    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    /**
    * 获取 到达城市
    * @return toCity
    */
    public String getToCity() {
        return toCity;
    }

    /**
    * 设置 到达城市
    * @param toCity
    */
    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    /**
    * 获取 快递信息订阅
    * @return poll
    */
    public Boolean getPoll() {
        return poll;
    }

    /**
    * 设置 快递信息订阅
    * @param poll
    */
    public void setPoll(Boolean poll) {
        this.poll = poll;
    }

    /**
    * 获取 失败次数
    * @return failTimes
    */
    public Integer getFailTimes() {
        return failTimes;
    }

    /**
    * 设置 失败次数
    * @param failTimes
    */
    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }

    /**
    * 获取 快递状态：0，在途中，1，已揽收，2，疑难，3，已签收，4，退签，5，同城派送中，6，退回，7，转单
    * @return expressStatus
    */
    public Integer getExpressStatus() {
        return expressStatus;
    }

    /**
    * 设置 快递状态：0，在途中，1，已揽收，2，疑难，3，已签收，4，退签，5，同城派送中，6，退回，7，转单
    * @param expressStatus
    */
    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    /**
    * 获取 快递100返回内容
    * @return responseJson
    */
    public String getResponseJson() {
        return responseJson;
    }

    /**
    * 设置 快递100返回内容
    * @param responseJson
    */
    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
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
    * 获取 
    * @return seq
    */
    public Long getSeq() {
        return seq;
    }

    /**
    * 设置 
    * @param seq
    */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
    * 获取 状态：0，新建，9，删除
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，新建，9，删除
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }
}