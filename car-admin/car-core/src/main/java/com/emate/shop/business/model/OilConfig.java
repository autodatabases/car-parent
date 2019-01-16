package com.emate.shop.business.model;

import java.util.Date;

public class OilConfig {
    /**
     * 
     * @Table oil_config.id
     */
    private Long id;

    /**
     * 追电科技 supplier
     */
    public static final java.lang.Byte SUPPLIER_1 = 1;
    /**
     * 高阳捷迅 supplier
     */
    public static final java.lang.Byte SUPPLIER_2 = 2;
    /**
     * 服务提供商：1，追电科技，2，高阳捷迅
     * @Table oil_config.supplier
     */
    private Byte supplier;

    /**
     * 创建时间
     * @Table oil_config.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_config.update_time
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
    * 获取 服务提供商：1，追电科技，2，高阳捷迅
    * @return supplier
    */
    public Byte getSupplier() {
        return supplier;
    }

    /**
    * 设置 服务提供商：1，追电科技，2，高阳捷迅
    * @param supplier
    */
    public void setSupplier(Byte supplier) {
        this.supplier = supplier;
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