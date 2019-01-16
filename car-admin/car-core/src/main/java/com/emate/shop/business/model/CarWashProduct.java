package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CarWashProduct {
    /**
     * id
     * @Table car_wash_product.id
     */
    private Long id;

    /**
     * 购买次数
     * @Table car_wash_product.buy_number
     */
    private Integer buyNumber;

    /**
     * 赠送次数
     * @Table car_wash_product.present_number
     */
    private Integer presentNumber;

    /**
     * 
     * @Table car_wash_product.price
     */
    private BigDecimal price;

    /**
     * 
     * @Table car_wash_product.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table car_wash_product.update_time
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
    * 获取 购买次数
    * @return buyNumber
    */
    public Integer getBuyNumber() {
        return buyNumber;
    }

    /**
    * 设置 购买次数
    * @param buyNumber
    */
    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    /**
    * 获取 赠送次数
    * @return presentNumber
    */
    public Integer getPresentNumber() {
        return presentNumber;
    }

    /**
    * 设置 赠送次数
    * @param presentNumber
    */
    public void setPresentNumber(Integer presentNumber) {
        this.presentNumber = presentNumber;
    }

    /**
    * 获取 
    * @return price
    */
    public BigDecimal getPrice() {
        return price;
    }

    /**
    * 设置 
    * @param price
    */
    public void setPrice(BigDecimal price) {
        this.price = price;
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