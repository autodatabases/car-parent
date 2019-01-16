package com.emate.shop.business.model;

import java.util.Date;

public class JicaiOrder {
    /**
     * 
     * @Table jicai_order.id
     */
    private Long id;

    /**
     * 集彩订单编号
     * @Table jicai_order.order_no
     */
    private String orderNo;

    /**
     * 客户手机号
     * @Table jicai_order.customer_phone
     */
    private String customerPhone;

    /**
     * 客户姓名
     * @Table jicai_order.customer_name
     */
    private String customerName;

    /**
     * 未发货 status
     */
    public static final java.lang.Byte STATUS_0 = 0;
    /**
     * 已发货 status
     */
    public static final java.lang.Byte STATUS_1 = 1;
    /**
     * 集彩订单状态：0，未发货，1，已发货
     * @Table jicai_order.status
     */
    private Byte status;

    /**
     * 商品名称
     * @Table jicai_order.good_name
     */
    private String goodName;

    /**
     * 采购数量,单位为个
     * @Table jicai_order.good_num
     */
    private Integer goodNum;

    /**
     * 创建时间
     * @Table jicai_order.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table jicai_order.update_time
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
    * 获取 集彩订单编号
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 集彩订单编号
    * @param orderNo
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    * 获取 客户手机号
    * @return customerPhone
    */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
    * 设置 客户手机号
    * @param customerPhone
    */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
    * 获取 客户姓名
    * @return customerName
    */
    public String getCustomerName() {
        return customerName;
    }

    /**
    * 设置 客户姓名
    * @param customerName
    */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
    * 获取 集彩订单状态：0，未发货，1，已发货
    * @return status
    */
    public Byte getStatus() {
        return status;
    }

    /**
    * 设置 集彩订单状态：0，未发货，1，已发货
    * @param status
    */
    public void setStatus(Byte status) {
        this.status = status;
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
    * 获取 采购数量,单位为个
    * @return goodNum
    */
    public Integer getGoodNum() {
        return goodNum;
    }

    /**
    * 设置 采购数量,单位为个
    * @param goodNum
    */
    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
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