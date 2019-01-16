package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilRecordwhiteList {
    /**
     * id
     * @Table oil_recordwhite_list.id
     */
    private Long id;

    /**
     * 用户账号
     * @Table oil_recordwhite_list.user_account
     */
    private String userAccount;

    /**
     * 开通额度
     * @Table oil_recordwhite_list.open_quota
     */
    private BigDecimal openQuota;

    /**
     * 开通额度时间
     * @Table oil_recordwhite_list.create_time
     */
    private Date createTime;

    /**
     * 额度修改时间
     * @Table oil_recordwhite_list.update_time
     */
    private Date updateTime;

    /**
     * 备注
     * @Table oil_recordwhite_list.remark
     */
    private String remark;

    /**
     * 默认状态 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 恢复额度状态 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 状态：1，默认状态，2，恢复额度状态
     * @Table oil_recordwhite_list.status
     */
    private Integer status;

    /**
     * 油卡用户id
     * @Table oil_recordwhite_list.user_id
     */
    private Long userId;

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
    * 获取 用户账号
    * @return userAccount
    */
    public String getUserAccount() {
        return userAccount;
    }

    /**
    * 设置 用户账号
    * @param userAccount
    */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
    * 获取 开通额度
    * @return openQuota
    */
    public BigDecimal getOpenQuota() {
        return openQuota;
    }

    /**
    * 设置 开通额度
    * @param openQuota
    */
    public void setOpenQuota(BigDecimal openQuota) {
        this.openQuota = openQuota;
    }

    /**
    * 获取 开通额度时间
    * @return createTime
    */
    public Date getCreateTime() {
        return createTime;
    }

    /**
    * 设置 开通额度时间
    * @param createTime
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
    * 获取 额度修改时间
    * @return updateTime
    */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
    * 设置 额度修改时间
    * @param updateTime
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    * 获取 状态：1，默认状态，2，恢复额度状态
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：1，默认状态，2，恢复额度状态
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 油卡用户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 油卡用户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}