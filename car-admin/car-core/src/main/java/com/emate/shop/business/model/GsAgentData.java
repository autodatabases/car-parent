package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class GsAgentData {
    /**
     * id
     * @Table gs_agent_data.id
     */
    private Long id;

    /**
     * 代理公司id
     * @Table gs_agent_data.agency_id
     */
    private Long agencyId;

    /**
     * 年份
     * @Table gs_agent_data.year
     */
    private String year;

    /**
     * 月份
     * @Table gs_agent_data.month
     */
    private String month;

    /**
     * 保费
     * @Table gs_agent_data.premium
     */
    private BigDecimal premium;

    /**
     * 置换产值
     * @Table gs_agent_data.replace_value
     */
    private BigDecimal replaceValue;

    /**
     * 置换率
     * @Table gs_agent_data.replace_rate
     */
    private BigDecimal replaceRate;

    /**
     * 赔付率
     * @Table gs_agent_data.loss_rate
     */
    private BigDecimal lossRate;

    /**
     * 创建时间
     * @Table gs_agent_data.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table gs_agent_data.update_time
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
    * 获取 代理公司id
    * @return agencyId
    */
    public Long getAgencyId() {
        return agencyId;
    }

    /**
    * 设置 代理公司id
    * @param agencyId
    */
    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    /**
    * 获取 年份
    * @return year
    */
    public String getYear() {
        return year;
    }

    /**
    * 设置 年份
    * @param year
    */
    public void setYear(String year) {
        this.year = year;
    }

    /**
    * 获取 月份
    * @return month
    */
    public String getMonth() {
        return month;
    }

    /**
    * 设置 月份
    * @param month
    */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
    * 获取 保费
    * @return premium
    */
    public BigDecimal getPremium() {
        return premium;
    }

    /**
    * 设置 保费
    * @param premium
    */
    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    /**
    * 获取 置换产值
    * @return replaceValue
    */
    public BigDecimal getReplaceValue() {
        return replaceValue;
    }

    /**
    * 设置 置换产值
    * @param replaceValue
    */
    public void setReplaceValue(BigDecimal replaceValue) {
        this.replaceValue = replaceValue;
    }

    /**
    * 获取 置换率
    * @return replaceRate
    */
    public BigDecimal getReplaceRate() {
        return replaceRate;
    }

    /**
    * 设置 置换率
    * @param replaceRate
    */
    public void setReplaceRate(BigDecimal replaceRate) {
        this.replaceRate = replaceRate;
    }

    /**
    * 获取 赔付率
    * @return lossRate
    */
    public BigDecimal getLossRate() {
        return lossRate;
    }

    /**
    * 设置 赔付率
    * @param lossRate
    */
    public void setLossRate(BigDecimal lossRate) {
        this.lossRate = lossRate;
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