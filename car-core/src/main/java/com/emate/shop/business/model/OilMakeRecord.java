package com.emate.shop.business.model;

import java.util.Date;

public class OilMakeRecord {
    /**
     * id
     * @Table oil_make_record.id
     */
    private Long id;

    /**
     * 一次制卡数量
     * @Table oil_make_record.num
     */
    private Integer num;

    /**
     * 制卡金额
     * @Table oil_make_record.money
     */
    private String money;

    /**
     * 剩余张数
     * @Table oil_make_record.residue_num
     */
    private Integer residueNum;

    /**
     * 备注
     * @Table oil_make_record.remark
     */
    private String remark;

    /**
     * 待处理 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 处理中 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 处理完成 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 一级订单状态：0，待处理，1，处理中，2，处理完成
     * @Table oil_make_record.status
     */
    private Integer status;

    /**
     * 失效时间
     * @Table oil_make_record.dead_time
     */
    private Date deadTime;

    /**
     * 创建时间
     * @Table oil_make_record.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table oil_make_record.update_time
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
    * 获取 一次制卡数量
    * @return num
    */
    public Integer getNum() {
        return num;
    }

    /**
    * 设置 一次制卡数量
    * @param num
    */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
    * 获取 制卡金额
    * @return money
    */
    public String getMoney() {
        return money;
    }

    /**
    * 设置 制卡金额
    * @param money
    */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
    * 获取 剩余张数
    * @return residueNum
    */
    public Integer getResidueNum() {
        return residueNum;
    }

    /**
    * 设置 剩余张数
    * @param residueNum
    */
    public void setResidueNum(Integer residueNum) {
        this.residueNum = residueNum;
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
    * 获取 一级订单状态：0，待处理，1，处理中，2，处理完成
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 一级订单状态：0，待处理，1，处理中，2，处理完成
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