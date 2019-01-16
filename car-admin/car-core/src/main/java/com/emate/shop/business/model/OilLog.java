package com.emate.shop.business.model;

import java.util.Date;

public class OilLog {
    /**
     * 
     * @Table oil_log.id
     */
    private Long id;

    /**
     * 订单号
     * @Table oil_log.order_no
     */
    private String orderNo;

    /**
     * 中石化 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_0 = 0;
    /**
     * 中石油 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_1 = 1;
    /**
     * 话费 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_2 = 2;
    /**
     * 洗车 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_3 = 3;
    /**
     * 代驾 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_4 = 4;
    /**
     * 流量 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_5 = 5;
    /**
     * 卡密 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_6 = 6;
    /**
     * 订单类型：0，中石化，1，中石油，2，话费，3，洗车，4，代驾，5，流量，6，卡密
     * @Table oil_log.order_type
     */
    private Integer orderType;

    /**
     * 下单接口 interfaceType
     */
    public static final java.lang.Integer INTERFACE_TYPE_0 = 0;
    /**
     * 回调接口 interfaceType
     */
    public static final java.lang.Integer INTERFACE_TYPE_1 = 1;
    /**
     * 查询接口 interfaceType
     */
    public static final java.lang.Integer INTERFACE_TYPE_2 = 2;
    /**
     * 订单类型：0，下单接口，1，回调接口，2，查询接口
     * @Table oil_log.interface_type
     */
    private Integer interfaceType;

    /**
     * 请求串
     * @Table oil_log.request_str
     */
    private String requestStr;

    /**
     * 接口返回结果
     * @Table oil_log.return_result
     */
    private String returnResult;

    /**
     * 创建时间
     * @Table oil_log.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_log.update_time
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
    * 获取 订单类型：0，中石化，1，中石油，2，话费，3，洗车，4，代驾，5，流量，6，卡密
    * @return orderType
    */
    public Integer getOrderType() {
        return orderType;
    }

    /**
    * 设置 订单类型：0，中石化，1，中石油，2，话费，3，洗车，4，代驾，5，流量，6，卡密
    * @param orderType
    */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
    * 获取 订单类型：0，下单接口，1，回调接口，2，查询接口
    * @return interfaceType
    */
    public Integer getInterfaceType() {
        return interfaceType;
    }

    /**
    * 设置 订单类型：0，下单接口，1，回调接口，2，查询接口
    * @param interfaceType
    */
    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    /**
    * 获取 请求串
    * @return requestStr
    */
    public String getRequestStr() {
        return requestStr;
    }

    /**
    * 设置 请求串
    * @param requestStr
    */
    public void setRequestStr(String requestStr) {
        this.requestStr = requestStr;
    }

    /**
    * 获取 接口返回结果
    * @return returnResult
    */
    public String getReturnResult() {
        return returnResult;
    }

    /**
    * 设置 接口返回结果
    * @param returnResult
    */
    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
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