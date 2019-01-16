package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilAccountRecharge {
    /**
     * id
     * @Table oil_account_recharge.id
     */
    private Long id;

    /**
     * 账户id
     * @Table oil_account_recharge.user_id
     */
    private Long userId;

    /**
     * 账户手机号
     * @Table oil_account_recharge.user_phone
     */
    private String userPhone;

    /**
     * 亿特诺美 supplier
     */
    public static final java.lang.Integer SUPPLIER_0 = 0;
    /**
     * 腾双 supplier
     */
    public static final java.lang.Integer SUPPLIER_1 = 1;
    /**
     * 腾母 supplier
     */
    public static final java.lang.Integer SUPPLIER_2 = 2;
    /**
     * 泰奎 supplier
     */
    public static final java.lang.Integer SUPPLIER_3 = 3;
    /**
     * 泰科 supplier
     */
    public static final java.lang.Integer SUPPLIER_4 = 4;
    /**
     * 供应方：0，亿特诺美，1，腾双，2，腾母，3，泰奎，4，泰科
     * @Table oil_account_recharge.supplier
     */
    private Integer supplier;

    /**
     * 发放油卡的序号
     * @Table oil_account_recharge.card_no
     */
    private String cardNo;

    /**
     * 油卡面额
     * @Table oil_account_recharge.money
     */
    private BigDecimal money;

    /**
     * 
     * @Table oil_account_recharge.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table oil_account_recharge.update_time
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
    * 获取 账户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 账户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 账户手机号
    * @return userPhone
    */
    public String getUserPhone() {
        return userPhone;
    }

    /**
    * 设置 账户手机号
    * @param userPhone
    */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
    * 获取 供应方：0，亿特诺美，1，腾双，2，腾母，3，泰奎，4，泰科
    * @return supplier
    */
    public Integer getSupplier() {
        return supplier;
    }

    /**
    * 设置 供应方：0，亿特诺美，1，腾双，2，腾母，3，泰奎，4，泰科
    * @param supplier
    */
    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    /**
    * 获取 发放油卡的序号
    * @return cardNo
    */
    public String getCardNo() {
        return cardNo;
    }

    /**
    * 设置 发放油卡的序号
    * @param cardNo
    */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
    * 获取 油卡面额
    * @return money
    */
    public BigDecimal getMoney() {
        return money;
    }

    /**
    * 设置 油卡面额
    * @param money
    */
    public void setMoney(BigDecimal money) {
        this.money = money;
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