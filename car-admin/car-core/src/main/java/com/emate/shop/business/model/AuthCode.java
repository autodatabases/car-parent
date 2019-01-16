package com.emate.shop.business.model;

import java.util.Date;

public class AuthCode {
    /**
     * id
     * @Table auth_code.id
     */
    private Long id;

    /**
     * 用户手机号
     * @Table auth_code.userPhone
     */
    private String userphone;

    /**
     * 验证码
     * @Table auth_code.code
     */
    private String code;

    /**
     * 发送成功 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 发送失败 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 已过期 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 验证码状态：0，发送成功，1，发送失败，2，已过期
     * @Table auth_code.status
     */
    private Integer status;

    /**
     * 惠加车服 smsType
     */
    public static final java.lang.Integer SMS_TYPE_0 = 0;
    /**
     * 油卡短信 smsType
     */
    public static final java.lang.Integer SMS_TYPE_1 = 1;
    /**
     * 短信类型：0，惠加车服，1，油卡短信
     * @Table auth_code.sms_type
     */
    private Integer smsType;

    /**
     * 短信发送返回结果
     * @Table auth_code.result_code
     */
    private String resultCode;

    /**
     * 返回结果文字说明
     * @Table auth_code.result_msg
     */
    private String resultMsg;

    /**
     * 创建时间
     * @Table auth_code.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table auth_code.update_time
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
    * 获取 用户手机号
    * @return userphone
    */
    public String getUserphone() {
        return userphone;
    }

    /**
    * 设置 用户手机号
    * @param userphone
    */
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    /**
    * 获取 验证码
    * @return code
    */
    public String getCode() {
        return code;
    }

    /**
    * 设置 验证码
    * @param code
    */
    public void setCode(String code) {
        this.code = code;
    }

    /**
    * 获取 验证码状态：0，发送成功，1，发送失败，2，已过期
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 验证码状态：0，发送成功，1，发送失败，2，已过期
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 短信类型：0，惠加车服，1，油卡短信
    * @return smsType
    */
    public Integer getSmsType() {
        return smsType;
    }

    /**
    * 设置 短信类型：0，惠加车服，1，油卡短信
    * @param smsType
    */
    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    /**
    * 获取 短信发送返回结果
    * @return resultCode
    */
    public String getResultCode() {
        return resultCode;
    }

    /**
    * 设置 短信发送返回结果
    * @param resultCode
    */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
    * 获取 返回结果文字说明
    * @return resultMsg
    */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
    * 设置 返回结果文字说明
    * @param resultMsg
    */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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