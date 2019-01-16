package com.emate.shop.business.model;

import java.util.Date;

public class OilBarcode {
    /**
     * 
     * @Table oil_barcode.id
     */
    private Long id;

    /**
     * 机油
     * @Table oil_barcode.jiyou
     */
    private String jiyou;

    /**
     * 容量
     * @Table oil_barcode.capacity
     */
    private String capacity;

    /**
     * 条形码
     * @Table oil_barcode.barcode
     */
    private String barcode;

    /**
     * 
     * @Table oil_barcode.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table oil_barcode.update_time
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
    * 获取 机油
    * @return jiyou
    */
    public String getJiyou() {
        return jiyou;
    }

    /**
    * 设置 机油
    * @param jiyou
    */
    public void setJiyou(String jiyou) {
        this.jiyou = jiyou;
    }

    /**
    * 获取 容量
    * @return capacity
    */
    public String getCapacity() {
        return capacity;
    }

    /**
    * 设置 容量
    * @param capacity
    */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
    * 获取 条形码
    * @return barcode
    */
    public String getBarcode() {
        return barcode;
    }

    /**
    * 设置 条形码
    * @param barcode
    */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
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