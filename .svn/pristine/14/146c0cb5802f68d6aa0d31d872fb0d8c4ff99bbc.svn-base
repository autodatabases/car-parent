package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class FixOrder {
    /**
     * 
     * @Table fix_order.id
     */
    private Long id;

    /**
     * 订单编号
     * @Table fix_order.order_no
     */
    private String orderNo;

    /**
     * 业务员id
     * @Table fix_order.business_id
     */
    private Long businessId;

    /**
     * 用户id
     * @Table fix_order.user_id
     */
    private Long userId;

    /**
     * 出险地点
     * @Table fix_order.location
     */
    private String location;

    /**
     * 用户手机号
     * @Table fix_order.user_phone
     */
    private String userPhone;

    /**
     * 出险人
     * @Table fix_order.user_name
     */
    private String userName;

    /**
     * 出险时间
     * @Table fix_order.out_time
     */
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private Date outTime;

    /**
     * 维修车牌号
     * @Table fix_order.che_pai
     */
    private String chePai;

    /**
     * 汽车品牌
     * @Table fix_order.car_brand
     */
    private String carBrand;

    /**
     * 出险描述
     * @Table fix_order.user_desc
     */
    private String userDesc;

    /**
     * 业务员上传的 车辆照片，多个用,分隔
     * @Table fix_order.user_pic
     */
    private String userPic;

    /**
     * 预约维修时间
     * @Table fix_order.fix_time
     */
    private Date fixTime;

    /**
     * 商家id
     * @Table fix_order.seller_id
     */
    private Long sellerId;

    /**
     * 商家维修定损
     * @Table fix_order.seller_remark
     */
    private String sellerRemark;

    /**
     * 维修单价格
     * @Table fix_order.money
     */
    private BigDecimal money;

    /**
     * 新建 auditStatus
     */
    public static final java.lang.Byte AUDIT_STATUS_0 = 0;
    /**
     * 待定损 auditStatus
     */
    public static final java.lang.Byte AUDIT_STATUS_1 = 1;
    /**
     * 待审核 auditStatus
     */
    public static final java.lang.Byte AUDIT_STATUS_2 = 2;
    /**
     * 审核通过 auditStatus
     */
    public static final java.lang.Byte AUDIT_STATUS_3 = 3;
    /**
     * 已完成 auditStatus
     */
    public static final java.lang.Byte AUDIT_STATUS_4 = 4;
    /**
     * 审核状态：0，新建，1，待定损，2，待审核，3，审核通过，4，已完成
     * @Table fix_order.audit_status
     */
    private Byte auditStatus;

    /**
     * 提交资料 step
     */
    public static final java.lang.Byte STEP_1 = 1;
    /**
     * 提交照片 step
     */
    public static final java.lang.Byte STEP_2 = 2;
    /**
     * 选择商家 step
     */
    public static final java.lang.Byte STEP_3 = 3;
    /**
     * 操作步骤：1，提交资料，2，提交照片，3，选择商家
     * @Table fix_order.step
     */
    private Byte step;

    /**
     * 
     * @Table fix_order.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table fix_order.update_time
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
    * 获取 业务员id
    * @return businessId
    */
    public Long getBusinessId() {
        return businessId;
    }

    /**
    * 设置 业务员id
    * @param businessId
    */
    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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
    * 获取 出险地点
    * @return location
    */
    public String getLocation() {
        return location;
    }

    /**
    * 设置 出险地点
    * @param location
    */
    public void setLocation(String location) {
        this.location = location;
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
    * 获取 出险人
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 出险人
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 出险时间
    * @return outTime
    */
    public Date getOutTime() {
        return outTime;
    }

    /**
    * 设置 出险时间
    * @param outTime
    */
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    /**
    * 获取 维修车牌号
    * @return chePai
    */
    public String getChePai() {
        return chePai;
    }

    /**
    * 设置 维修车牌号
    * @param chePai
    */
    public void setChePai(String chePai) {
        this.chePai = chePai;
    }

    /**
    * 获取 汽车品牌
    * @return carBrand
    */
    public String getCarBrand() {
        return carBrand;
    }

    /**
    * 设置 汽车品牌
    * @param carBrand
    */
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    /**
    * 获取 出险描述
    * @return userDesc
    */
    public String getUserDesc() {
        return userDesc;
    }

    /**
    * 设置 出险描述
    * @param userDesc
    */
    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    /**
    * 获取 业务员上传的 车辆照片，多个用,分隔
    * @return userPic
    */
    public String getUserPic() {
        return userPic;
    }

    /**
    * 设置 业务员上传的 车辆照片，多个用,分隔
    * @param userPic
    */
    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    /**
    * 获取 预约维修时间
    * @return fixTime
    */
    public Date getFixTime() {
        return fixTime;
    }

    /**
    * 设置 预约维修时间
    * @param fixTime
    */
    public void setFixTime(Date fixTime) {
        this.fixTime = fixTime;
    }

    /**
    * 获取 商家id
    * @return sellerId
    */
    public Long getSellerId() {
        return sellerId;
    }

    /**
    * 设置 商家id
    * @param sellerId
    */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
    * 获取 商家维修定损
    * @return sellerRemark
    */
    public String getSellerRemark() {
        return sellerRemark;
    }

    /**
    * 设置 商家维修定损
    * @param sellerRemark
    */
    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    /**
    * 获取 维修单价格
    * @return money
    */
    public BigDecimal getMoney() {
        return money;
    }

    /**
    * 设置 维修单价格
    * @param money
    */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
    * 获取 审核状态：0，新建，1，待定损，2，待审核，3，审核通过，4，已完成
    * @return auditStatus
    */
    public Byte getAuditStatus() {
        return auditStatus;
    }

    /**
    * 设置 审核状态：0，新建，1，待定损，2，待审核，3，审核通过，4，已完成
    * @param auditStatus
    */
    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
    * 获取 操作步骤：1，提交资料，2，提交照片，3，选择商家
    * @return step
    */
    public Byte getStep() {
        return step;
    }

    /**
    * 设置 操作步骤：1，提交资料，2，提交照片，3，选择商家
    * @param step
    */
    public void setStep(Byte step) {
        this.step = step;
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