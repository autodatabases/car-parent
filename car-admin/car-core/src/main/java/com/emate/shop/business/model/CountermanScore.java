package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CountermanScore {
    /**
     * 
     * @Table counterman_score.id
     */
    private Long id;

    /**
     * 寿险业务员id
     * @Table counterman_score.counterman_id
     */
    private Long countermanId;

    /**
     * 业务员代码
     * @Table counterman_score.counterman_code
     */
    private String countermanCode;

    /**
     * 业务员姓名
     * @Table counterman_score.counterman_name
     */
    private String countermanName;

    /**
     * 片区名称
     * @Table counterman_score.belong_area
     */
    private String belongArea;

    /**
     * 国寿天财积分
     * @Table counterman_score.cai_score
     */
    private BigDecimal caiScore;

    /**
     * 常规积分
     * @Table counterman_score.chang_score
     */
    private BigDecimal changScore;

    /**
     * 积分
     * @Table counterman_score.score
     */
    private BigDecimal score;

    /**
     * 出单量
     * @Table counterman_score.number
     */
    private BigDecimal number;

    /**
     * 销售总额
     * @Table counterman_score.total_price
     */
    private BigDecimal totalPrice;

    /**
     * 
     * @Table counterman_score.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table counterman_score.update_time
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
    * 获取 寿险业务员id
    * @return countermanId
    */
    public Long getCountermanId() {
        return countermanId;
    }

    /**
    * 设置 寿险业务员id
    * @param countermanId
    */
    public void setCountermanId(Long countermanId) {
        this.countermanId = countermanId;
    }

    /**
    * 获取 业务员代码
    * @return countermanCode
    */
    public String getCountermanCode() {
        return countermanCode;
    }

    /**
    * 设置 业务员代码
    * @param countermanCode
    */
    public void setCountermanCode(String countermanCode) {
        this.countermanCode = countermanCode;
    }

    /**
    * 获取 业务员姓名
    * @return countermanName
    */
    public String getCountermanName() {
        return countermanName;
    }

    /**
    * 设置 业务员姓名
    * @param countermanName
    */
    public void setCountermanName(String countermanName) {
        this.countermanName = countermanName;
    }

    /**
    * 获取 片区名称
    * @return belongArea
    */
    public String getBelongArea() {
        return belongArea;
    }

    /**
    * 设置 片区名称
    * @param belongArea
    */
    public void setBelongArea(String belongArea) {
        this.belongArea = belongArea;
    }

    /**
    * 获取 国寿天财积分
    * @return caiScore
    */
    public BigDecimal getCaiScore() {
        return caiScore;
    }

    /**
    * 设置 国寿天财积分
    * @param caiScore
    */
    public void setCaiScore(BigDecimal caiScore) {
        this.caiScore = caiScore;
    }

    /**
    * 获取 常规积分
    * @return changScore
    */
    public BigDecimal getChangScore() {
        return changScore;
    }

    /**
    * 设置 常规积分
    * @param changScore
    */
    public void setChangScore(BigDecimal changScore) {
        this.changScore = changScore;
    }

    /**
    * 获取 积分
    * @return score
    */
    public BigDecimal getScore() {
        return score;
    }

    /**
    * 设置 积分
    * @param score
    */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
    * 获取 出单量
    * @return number
    */
    public BigDecimal getNumber() {
        return number;
    }

    /**
    * 设置 出单量
    * @param number
    */
    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    /**
    * 获取 销售总额
    * @return totalPrice
    */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
    * 设置 销售总额
    * @param totalPrice
    */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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