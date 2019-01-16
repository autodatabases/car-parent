package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CountermanScoreRecord {
    /**
     * 
     * @Table counterman_score_record.id
     */
    private Long id;

    /**
     * 寿险营销员的id
     * @Table counterman_score_record.counterman_id
     */
    private Long countermanId;

    /**
     * 寿险营销员的code
     * @Table counterman_score_record.counterman_code
     */
    private String countermanCode;

    /**
     * 修改的积分
     * @Table counterman_score_record.score
     */
    private BigDecimal score;

    /**
     * 后台人员的操作时(赠送,扣减) operationType
     */
    public static final java.lang.Short OPERATION_TYPE_1 = 1;
    /**
     * 购买商品时 operationType
     */
    public static final java.lang.Short OPERATION_TYPE_2 = 2;
    /**
     * 操作类型：1，后台人员的操作时(赠送,扣减)，2，购买商品时
     * @Table counterman_score_record.operation_type
     */
    private Short operationType;

    /**
     * 增加这条积分记录的原因
     * @Table counterman_score_record.record_remark
     */
    private String recordRemark;

    /**
     * 操作人
     * @Table counterman_score_record.operater
     */
    private Long operater;

    /**
     * 财险网点id
     * @Table counterman_score_record.cai_dot_id
     */
    private Long caiDotId;

    /**
     * 创建时间
     * @Table counterman_score_record.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table counterman_score_record.update_time
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
    * 获取 寿险营销员的id
    * @return countermanId
    */
    public Long getCountermanId() {
        return countermanId;
    }

    /**
    * 设置 寿险营销员的id
    * @param countermanId
    */
    public void setCountermanId(Long countermanId) {
        this.countermanId = countermanId;
    }

    /**
    * 获取 寿险营销员的code
    * @return countermanCode
    */
    public String getCountermanCode() {
        return countermanCode;
    }

    /**
    * 设置 寿险营销员的code
    * @param countermanCode
    */
    public void setCountermanCode(String countermanCode) {
        this.countermanCode = countermanCode;
    }

    /**
    * 获取 修改的积分
    * @return score
    */
    public BigDecimal getScore() {
        return score;
    }

    /**
    * 设置 修改的积分
    * @param score
    */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
    * 获取 操作类型：1，后台人员的操作时(赠送,扣减)，2，购买商品时
    * @return operationType
    */
    public Short getOperationType() {
        return operationType;
    }

    /**
    * 设置 操作类型：1，后台人员的操作时(赠送,扣减)，2，购买商品时
    * @param operationType
    */
    public void setOperationType(Short operationType) {
        this.operationType = operationType;
    }

    /**
    * 获取 增加这条积分记录的原因
    * @return recordRemark
    */
    public String getRecordRemark() {
        return recordRemark;
    }

    /**
    * 设置 增加这条积分记录的原因
    * @param recordRemark
    */
    public void setRecordRemark(String recordRemark) {
        this.recordRemark = recordRemark;
    }

    /**
    * 获取 操作人
    * @return operater
    */
    public Long getOperater() {
        return operater;
    }

    /**
    * 设置 操作人
    * @param operater
    */
    public void setOperater(Long operater) {
        this.operater = operater;
    }

    /**
    * 获取 财险网点id
    * @return caiDotId
    */
    public Long getCaiDotId() {
        return caiDotId;
    }

    /**
    * 设置 财险网点id
    * @param caiDotId
    */
    public void setCaiDotId(Long caiDotId) {
        this.caiDotId = caiDotId;
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