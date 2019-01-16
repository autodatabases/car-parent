package com.emate.shop.business.model;

import java.util.Date;

public class OilGoodsAddress {
    /**
     * 
     * @Table oil_goods_address.id
     */
    private Long id;

    /**
     * 油卡面额商品id
     * @Table oil_goods_address.good_id
     */
    private Long goodId;

    /**
     * 所属城市
     * @Table oil_goods_address.address
     */
    private String address;

    /**
     * 所属城市油卡卡号共性
     * @Table oil_goods_address.oil_card_code
     */
    private String oilCardCode;

    /**
     * 正常 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 维护 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 状态：0，正常，1，维护
     * @Table oil_goods_address.status
     */
    private Integer status;

    /**
     * 备注
     * @Table oil_goods_address.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table oil_goods_address.create_time
     */
    private Date createTime;

    /**
     * 更改时间
     * @Table oil_goods_address.update_time
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
    * 获取 油卡面额商品id
    * @return goodId
    */
    public Long getGoodId() {
        return goodId;
    }

    /**
    * 设置 油卡面额商品id
    * @param goodId
    */
    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    /**
    * 获取 所属城市
    * @return address
    */
    public String getAddress() {
        return address;
    }

    /**
    * 设置 所属城市
    * @param address
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
    * 获取 所属城市油卡卡号共性
    * @return oilCardCode
    */
    public String getOilCardCode() {
        return oilCardCode;
    }

    /**
    * 设置 所属城市油卡卡号共性
    * @param oilCardCode
    */
    public void setOilCardCode(String oilCardCode) {
        this.oilCardCode = oilCardCode;
    }

    /**
    * 获取 状态：0，正常，1，维护
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，正常，1，维护
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
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