package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilGoods {
    /**
     * 
     * @Table oil_goods.id
     */
    private Long id;

    /**
     * 商品编号
     * @Table oil_goods.product_id
     */
    private String productId;

    /**
     * 商品名称
     * @Table oil_goods.goods_name
     */
    private String goodsName;

    /**
     * 商品类目id
     * @Table oil_goods.type_id
     */
    private Long typeId;

    /**
     * 商品价格
     * @Table oil_goods.sale
     */
    private BigDecimal sale;

    /**
     * 面额
     * @Table oil_goods.denomination
     */
    private String denomination;

    /**
     * 上架 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 下架 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 库存不足 status
     */
    public static final java.lang.Integer STATUS_3 = 3;
    /**
     * 系统维护 status
     */
    public static final java.lang.Integer STATUS_4 = 4;
    /**
     * 商品状态：1，上架，2，下架，3，库存不足，4，系统维护
     * @Table oil_goods.status
     */
    private Integer status;

    /**
     * 上架 oneStatus
     */
    public static final java.lang.Integer ONE_STATUS_1 = 1;
    /**
     * 下架 oneStatus
     */
    public static final java.lang.Integer ONE_STATUS_2 = 2;
    /**
     * 一键状态：1，上架，2，下架
     * @Table oil_goods.one_status
     */
    private Integer oneStatus;

    /**
     * 备注
     * @Table oil_goods.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table oil_goods.create_time
     */
    private Date createTime;

    /**
     * 更改时间
     * @Table oil_goods.update_time
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
    * 获取 商品编号
    * @return productId
    */
    public String getProductId() {
        return productId;
    }

    /**
    * 设置 商品编号
    * @param productId
    */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
    * 获取 商品名称
    * @return goodsName
    */
    public String getGoodsName() {
        return goodsName;
    }

    /**
    * 设置 商品名称
    * @param goodsName
    */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
    * 获取 商品类目id
    * @return typeId
    */
    public Long getTypeId() {
        return typeId;
    }

    /**
    * 设置 商品类目id
    * @param typeId
    */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
    * 获取 商品价格
    * @return sale
    */
    public BigDecimal getSale() {
        return sale;
    }

    /**
    * 设置 商品价格
    * @param sale
    */
    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    /**
    * 获取 面额
    * @return denomination
    */
    public String getDenomination() {
        return denomination;
    }

    /**
    * 设置 面额
    * @param denomination
    */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
    * 获取 商品状态：1，上架，2，下架，3，库存不足，4，系统维护
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 商品状态：1，上架，2，下架，3，库存不足，4，系统维护
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 一键状态：1，上架，2，下架
    * @return oneStatus
    */
    public Integer getOneStatus() {
        return oneStatus;
    }

    /**
    * 设置 一键状态：1，上架，2，下架
    * @param oneStatus
    */
    public void setOneStatus(Integer oneStatus) {
        this.oneStatus = oneStatus;
    }

    /**
    * 获取 备注
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 备注
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
    * 获取 更改时间
    * @return updateTime
    */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
    * 设置 更改时间
    * @param updateTime
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}