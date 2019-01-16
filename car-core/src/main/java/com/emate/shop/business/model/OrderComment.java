package com.emate.shop.business.model;

import java.util.Date;

public class OrderComment {
    /**
     * id
     * @Table order_comment.id
     */
    private Long id;

    /**
     * 订单编号
     * @Table order_comment.order_no
     */
    private String orderNo;

    /**
     * 商家id
     * @Table order_comment.seller_id
     */
    private Long sellerId;

    /**
     * 用户id
     * @Table order_comment.user_id
     */
    private Long userId;

    /**
     * 服务态度星级
     * @Table order_comment.seller_star
     */
    private Integer sellerStar;

    /**
     * 快递满意度星级
     * @Table order_comment.express_star
     */
    private Integer expressStar;

    /**
     * 综合评价
     * @Table order_comment.comment
     */
    private String comment;

    /**
     * 备注后台运营回访记录
     * @Table order_comment.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table order_comment.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table order_comment.update_time
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
    * 获取 服务态度星级
    * @return sellerStar
    */
    public Integer getSellerStar() {
        return sellerStar;
    }

    /**
    * 设置 服务态度星级
    * @param sellerStar
    */
    public void setSellerStar(Integer sellerStar) {
        this.sellerStar = sellerStar;
    }

    /**
    * 获取 快递满意度星级
    * @return expressStar
    */
    public Integer getExpressStar() {
        return expressStar;
    }

    /**
    * 设置 快递满意度星级
    * @param expressStar
    */
    public void setExpressStar(Integer expressStar) {
        this.expressStar = expressStar;
    }

    /**
    * 获取 综合评价
    * @return comment
    */
    public String getComment() {
        return comment;
    }

    /**
    * 设置 综合评价
    * @param comment
    */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
    * 获取 备注后台运营回访记录
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 备注后台运营回访记录
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