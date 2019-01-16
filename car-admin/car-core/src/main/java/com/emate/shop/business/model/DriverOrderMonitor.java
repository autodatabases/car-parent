package com.emate.shop.business.model;

import java.util.Date;

public class DriverOrderMonitor {
    /**
     * id
     * @Table driver_order_monitor.id
     */
    private Long id;

    /**
     * 代驾订单号
     * @Table driver_order_monitor.order_no
     */
    private String orderNo;

    /**
     * 等待接单 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 司机已接单 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 司机到达指定地点 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 订单完成 status
     */
    public static final java.lang.Integer STATUS_3 = 3;
    /**
     * 订单取消 status
     */
    public static final java.lang.Integer STATUS_4 = 4;
    /**
     * 修改预约时间 status
     */
    public static final java.lang.Integer STATUS_5 = 5;
    /**
     * 订单监控状态：0，等待接单，1，司机已接单，2，司机到达指定地点，3，订单完成，4，订单取消，5，修改预约时间
     * @Table driver_order_monitor.status
     */
    private Integer status;

    /**
     * 订单详情
     * @Table driver_order_monitor.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table driver_order_monitor.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table driver_order_monitor.update_time
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
    * 获取 代驾订单号
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 代驾订单号
    * @param orderNo
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    * 获取 订单监控状态：0，等待接单，1，司机已接单，2，司机到达指定地点，3，订单完成，4，订单取消，5，修改预约时间
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 订单监控状态：0，等待接单，1，司机已接单，2，司机到达指定地点，3，订单完成，4，订单取消，5，修改预约时间
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 订单详情
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 订单详情
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