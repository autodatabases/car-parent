package com.emate.shop.business.model;

import java.util.Date;

public class OrderTrace {
    /**
     * 
     * @Table order_trace.id
     */
    private Long id;

    /**
     * 订单编号
     * @Table order_trace.order_no
     */
    private String orderNo;

    /**
     * 订单创建时间
     * @Table order_trace.new_time
     */
    private Date newTime;

    /**
     * 系统确认时间
     * @Table order_trace.confirm_time
     */
    private Date confirmTime;

    /**
     * 到货时间
     * @Table order_trace.deliver_time
     */
    private Date deliverTime;

    /**
     * 订单完成时间
     * @Table order_trace.complet_time
     */
    private Date completTime;

    /**
     * 创建时间
     * @Table order_trace.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table order_trace.update_time
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
    * 获取 订单编号
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 订单编号
    * @param orderNo
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    * 获取 订单创建时间
    * @return newTime
    */
    public Date getNewTime() {
        return newTime;
    }

    /**
    * 设置 订单创建时间
    * @param newTime
    */
    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }

    /**
    * 获取 系统确认时间
    * @return confirmTime
    */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
    * 设置 系统确认时间
    * @param confirmTime
    */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
    * 获取 到货时间
    * @return deliverTime
    */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
    * 设置 到货时间
    * @param deliverTime
    */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
    * 获取 订单完成时间
    * @return completTime
    */
    public Date getCompletTime() {
        return completTime;
    }

    /**
    * 设置 订单完成时间
    * @param completTime
    */
    public void setCompletTime(Date completTime) {
        this.completTime = completTime;
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