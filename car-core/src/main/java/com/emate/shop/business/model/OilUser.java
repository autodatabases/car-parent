package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilUser {
    /**
     * 主键
     * @Table oil_user.id
     */
    private Long id;

    /**
     * 用户手机号
     * @Table oil_user.phone
     */
    private String phone;

    /**
     * 用户姓名
     * @Table oil_user.name
     */
    private String name;

    /**
     * 用户余额
     * @Table oil_user.money
     */
    private BigDecimal money;

    /**
     * 洗车次数
     * @Table oil_user.wash_number
     */
    private Integer washNumber;

    /**
     * 
     * @Table oil_user.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table oil_user.update_time
     */
    private Date updateTime;

    /**
    * 获取 主键
    * @return id
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置 主键
    * @param id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取 用户手机号
    * @return phone
    */
    public String getPhone() {
        return phone;
    }

    /**
    * 设置 用户手机号
    * @param phone
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
    * 获取 用户姓名
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 用户姓名
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取 用户余额
    * @return money
    */
    public BigDecimal getMoney() {
        return money;
    }

    /**
    * 设置 用户余额
    * @param money
    */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
    * 获取 洗车次数
    * @return washNumber
    */
    public Integer getWashNumber() {
        return washNumber;
    }

    /**
    * 设置 洗车次数
    * @param washNumber
    */
    public void setWashNumber(Integer washNumber) {
        this.washNumber = washNumber;
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