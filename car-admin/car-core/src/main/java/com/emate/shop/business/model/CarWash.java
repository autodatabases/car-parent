package com.emate.shop.business.model;

import java.util.Date;

public class CarWash {
    /**
     * 
     * @Table car_wash.id
     */
    private Long id;

    /**
     * 用户id
     * @Table car_wash.user_id
     */
    private Long userId;

    /**
     * 订单号
     * @Table car_wash.order_no
     */
    private String orderNo;

    /**
     * 接受短信的手机号
     * @Table car_wash.mobile
     */
    private String mobile;

    /**
     * 产品编号
     * @Table car_wash.product_no
     */
    private String productNo;

    /**
     * 洗车券码
     * @Table car_wash.coupon_code
     */
    private String couponCode;

    /**
     * 核销码
     * @Table car_wash.verify_code
     */
    private String verifyCode;

    /**
     * 宽途券名称
     * @Table car_wash.coupon_name
     */
    private String couponName;

    /**
     * 宽途券使用描述
     * @Table car_wash.coupon_desc
     */
    private String couponDesc;

    /**
     * 宽途券生效时间
     * @Table car_wash.coupon_valid_date
     */
    private Date couponValidDate;

    /**
     * 宽途券过期时间
     * @Table car_wash.coupon_expir_date
     */
    private Date couponExpirDate;

    /**
     * 宽途通知时间
     * @Table car_wash.notify_time
     */
    private Date notifyTime;

    /**
     * 店铺名称
     * @Table car_wash.shop_name
     */
    private String shopName;

    /**
     * 服务项目名称
     * @Table car_wash.service_name
     */
    private String serviceName;

    /**
     * 创建时间
     * @Table car_wash.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table car_wash.update_time
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
    * 获取 接受短信的手机号
    * @return mobile
    */
    public String getMobile() {
        return mobile;
    }

    /**
    * 设置 接受短信的手机号
    * @param mobile
    */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
    * 获取 产品编号
    * @return productNo
    */
    public String getProductNo() {
        return productNo;
    }

    /**
    * 设置 产品编号
    * @param productNo
    */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /**
    * 获取 洗车券码
    * @return couponCode
    */
    public String getCouponCode() {
        return couponCode;
    }

    /**
    * 设置 洗车券码
    * @param couponCode
    */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    /**
    * 获取 核销码
    * @return verifyCode
    */
    public String getVerifyCode() {
        return verifyCode;
    }

    /**
    * 设置 核销码
    * @param verifyCode
    */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
    * 获取 宽途券名称
    * @return couponName
    */
    public String getCouponName() {
        return couponName;
    }

    /**
    * 设置 宽途券名称
    * @param couponName
    */
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    /**
    * 获取 宽途券使用描述
    * @return couponDesc
    */
    public String getCouponDesc() {
        return couponDesc;
    }

    /**
    * 设置 宽途券使用描述
    * @param couponDesc
    */
    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    /**
    * 获取 宽途券生效时间
    * @return couponValidDate
    */
    public Date getCouponValidDate() {
        return couponValidDate;
    }

    /**
    * 设置 宽途券生效时间
    * @param couponValidDate
    */
    public void setCouponValidDate(Date couponValidDate) {
        this.couponValidDate = couponValidDate;
    }

    /**
    * 获取 宽途券过期时间
    * @return couponExpirDate
    */
    public Date getCouponExpirDate() {
        return couponExpirDate;
    }

    /**
    * 设置 宽途券过期时间
    * @param couponExpirDate
    */
    public void setCouponExpirDate(Date couponExpirDate) {
        this.couponExpirDate = couponExpirDate;
    }

    /**
    * 获取 宽途通知时间
    * @return notifyTime
    */
    public Date getNotifyTime() {
        return notifyTime;
    }

    /**
    * 设置 宽途通知时间
    * @param notifyTime
    */
    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    /**
    * 获取 店铺名称
    * @return shopName
    */
    public String getShopName() {
        return shopName;
    }

    /**
    * 设置 店铺名称
    * @param shopName
    */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
    * 获取 服务项目名称
    * @return serviceName
    */
    public String getServiceName() {
        return serviceName;
    }

    /**
    * 设置 服务项目名称
    * @param serviceName
    */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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