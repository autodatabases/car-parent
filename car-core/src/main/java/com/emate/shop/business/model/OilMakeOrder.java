package com.emate.shop.business.model;

import java.util.Date;

public class OilMakeOrder {
    /**
     * id
     * @Table oil_make_order.id
     */
    private Long id;

    /**
     * 制卡主表id
     * @Table oil_make_order.make_record_id
     */
    private Long makeRecordId;

    /**
     * 起始序号
     * @Table oil_make_order.start_code
     */
    private String startCode;

    /**
     * 结束序号
     * @Table oil_make_order.end_code
     */
    private String endCode;

    /**
     * 油卡面额
     * @Table oil_make_order.money
     */
    private String money;

    /**
     * 做卡数量
     * @Table oil_make_order.num
     */
    private Integer num;

    /**
     * 待处理 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 已处理 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 二级订单状态：0，待处理，1，已处理
     * @Table oil_make_order.status
     */
    private Integer status;

    /**
     * 失效时间
     * @Table oil_make_order.dead_time
     */
    private Date deadTime;

    /**
     * 创建时间
     * @Table oil_make_order.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_make_order.update_time
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
    * 获取 制卡主表id
    * @return makeRecordId
    */
    public Long getMakeRecordId() {
        return makeRecordId;
    }

    /**
    * 设置 制卡主表id
    * @param makeRecordId
    */
    public void setMakeRecordId(Long makeRecordId) {
        this.makeRecordId = makeRecordId;
    }

    /**
    * 获取 起始序号
    * @return startCode
    */
    public String getStartCode() {
        return startCode;
    }

    /**
    * 设置 起始序号
    * @param startCode
    */
    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    /**
    * 获取 结束序号
    * @return endCode
    */
    public String getEndCode() {
        return endCode;
    }

    /**
    * 设置 结束序号
    * @param endCode
    */
    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    /**
    * 获取 油卡面额
    * @return money
    */
    public String getMoney() {
        return money;
    }

    /**
    * 设置 油卡面额
    * @param money
    */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
    * 获取 做卡数量
    * @return num
    */
    public Integer getNum() {
        return num;
    }

    /**
    * 设置 做卡数量
    * @param num
    */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
    * 获取 二级订单状态：0，待处理，1，已处理
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 二级订单状态：0，待处理，1，已处理
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 失效时间
    * @return deadTime
    */
    public Date getDeadTime() {
        return deadTime;
    }

    /**
    * 设置 失效时间
    * @param deadTime
    */
    public void setDeadTime(Date deadTime) {
        this.deadTime = deadTime;
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