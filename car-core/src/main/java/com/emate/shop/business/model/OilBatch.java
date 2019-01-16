package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class OilBatch {
    /**
     * id
     * @Table oil_batch.id
     */
    private Long id;

    /**
     * 类型
     * @Table oil_batch.type
     */
    private Integer type;

    /**
     * 起始序号
     * @Table oil_batch.start_number
     */
    private String startNumber;

    /**
     * 结束序号
     * @Table oil_batch.end_number
     */
    private String endNumber;

    /**
     * 油卡数量
     * @Table oil_batch.count
     */
    private Integer count;
    
    /**
     * 开通省份
     */
    private String province;

    /**
     * 开通城市
     * @Table oil_batch.city
     */
    private String city;

    /**
     * 批次号
     * @Table oil_batch.batch_number
     */
    private Integer batchNumber;

    /**
     * 开通时间
     * @Table oil_batch.open_time
     */
    private String openTime;

    /**
     * 油卡面额
     * @Table oil_batch.money
     */
    private BigDecimal money;

    /**
     * 备注
     * @Table oil_batch.remark
     */
    private String remark;

    /**
     * 购买方
     * @Table oil_batch.buyer
     */
    private String buyer;

    /**
     * 申请人
     * @Table oil_batch.proposer
     */
    private String proposer;

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
     * @Table oil_batch.supplier
     */
    private Integer supplier;

    /**
     * 创建时间
     * @Table oil_batch.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_batch.update_time
     */
    private Date updateTime;
    
    /**
     * 设置省份
     */
    public void setProvince(String province){
    	this.province = province;
    }
    
    /**
     * 获取省份
     */
    public String getProvince(){
    	return province;
    }
    
    

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
    * 获取 类型
    * @return type
    */
    public Integer getType() {
        return type;
    }

    /**
    * 设置 类型
    * @param type
    */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
    * 获取 起始序号
    * @return startNumber
    */
    public String getStartNumber() {
        return startNumber;
    }

    /**
    * 设置 起始序号
    * @param startNumber
    */
    public void setStartNumber(String startNumber) {
        this.startNumber = startNumber;
    }

    /**
    * 获取 结束序号
    * @return endNumber
    */
    public String getEndNumber() {
        return endNumber;
    }

    /**
    * 设置 结束序号
    * @param endNumber
    */
    public void setEndNumber(String endNumber) {
        this.endNumber = endNumber;
    }

    /**
    * 获取 油卡数量
    * @return count
    */
    public Integer getCount() {
        return count;
    }

    /**
    * 设置 油卡数量
    * @param count
    */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
    * 获取 开通城市
    * @return city
    */
    public String getCity() {
        return city;
    }

    /**
    * 设置 开通城市
    * @param city
    */
    public void setCity(String city) {
        this.city = city;
    }

    /**
    * 获取 批次号
    * @return batchNumber
    */
    public Integer getBatchNumber() {
        return batchNumber;
    }

    /**
    * 设置 批次号
    * @param batchNumber
    */
    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
    * 获取 开通时间
    * @return openTime
    */
    public String getOpenTime() {
        return openTime;
    }

    /**
    * 设置 开通时间
    * @param openTime
    */
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
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
    * 获取 购买方
    * @return buyer
    */
    public String getBuyer() {
        return buyer;
    }

    /**
    * 设置 购买方
    * @param buyer
    */
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    /**
    * 获取 申请人
    * @return proposer
    */
    public String getProposer() {
        return proposer;
    }

    /**
    * 设置 申请人
    * @param proposer
    */
    public void setProposer(String proposer) {
        this.proposer = proposer;
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