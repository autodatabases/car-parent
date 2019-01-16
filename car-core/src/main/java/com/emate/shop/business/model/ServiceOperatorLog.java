package com.emate.shop.business.model;

import java.util.Date;

public class ServiceOperatorLog {
    /**
     * 
     * @Table service_operator_log.id
     */
    private Long id;

    /**
     * 操作人
     * @Table service_operator_log.operator
     */
    private String operator;

    /**
     * 车牌
     * @Table service_operator_log.che_pai
     */
    private String chePai;

    /**
     * 待审核 verifyStatus
     */
    public static final java.lang.Integer VERIFY_STATUS_0 = 0;
    /**
     * 审核通过 verifyStatus
     */
    public static final java.lang.Integer VERIFY_STATUS_1 = 1;
    /**
     * 审核不通过 verifyStatus
     */
    public static final java.lang.Integer VERIFY_STATUS_2 = 2;
    /**
     * 审核状态：0，待审核，1，审核通过，2，审核不通过
     * @Table service_operator_log.verify_status
     */
    private Integer verifyStatus;

    /**
     * 电源 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_0 = 0;
    /**
     * 轮胎 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_1 = 1;
    /**
     * 小保养 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_2 = 2;
    /**
     * 洗车 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_3 = 3;
    /**
     * 喷漆 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_4 = 4;
    /**
     * 20元滴滴券 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_5 = 5;
    /**
     * 50元滴滴券 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_6 = 6;
    /**
     * 服务类型：0，电源，1，轮胎，2，小保养，3，洗车，4，喷漆，5，20元滴滴券，6，50元滴滴券
     * @Table service_operator_log.service_type
     */
    private Integer serviceType;

    /**
     * 服务次数（张数）
     * @Table service_operator_log.service_value
     */
    private Integer serviceValue;

    /**
     * ip地址
     * @Table service_operator_log.ip_address
     */
    private String ipAddress;

    /**
     * 备注
     * @Table service_operator_log.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table service_operator_log.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table service_operator_log.update_time
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
    * 获取 操作人
    * @return operator
    */
    public String getOperator() {
        return operator;
    }

    /**
    * 设置 操作人
    * @param operator
    */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
    * 获取 车牌
    * @return chePai
    */
    public String getChePai() {
        return chePai;
    }

    /**
    * 设置 车牌
    * @param chePai
    */
    public void setChePai(String chePai) {
        this.chePai = chePai;
    }

    /**
    * 获取 审核状态：0，待审核，1，审核通过，2，审核不通过
    * @return verifyStatus
    */
    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    /**
    * 设置 审核状态：0，待审核，1，审核通过，2，审核不通过
    * @param verifyStatus
    */
    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    /**
    * 获取 服务类型：0，电源，1，轮胎，2，小保养，3，洗车，4，喷漆，5，20元滴滴券，6，50元滴滴券
    * @return serviceType
    */
    public Integer getServiceType() {
        return serviceType;
    }

    /**
    * 设置 服务类型：0，电源，1，轮胎，2，小保养，3，洗车，4，喷漆，5，20元滴滴券，6，50元滴滴券
    * @param serviceType
    */
    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    /**
    * 获取 服务次数（张数）
    * @return serviceValue
    */
    public Integer getServiceValue() {
        return serviceValue;
    }

    /**
    * 设置 服务次数（张数）
    * @param serviceValue
    */
    public void setServiceValue(Integer serviceValue) {
        this.serviceValue = serviceValue;
    }

    /**
    * 获取 ip地址
    * @return ipAddress
    */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
    * 设置 ip地址
    * @param ipAddress
    */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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