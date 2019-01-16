package com.emate.shop.business.model;

import java.util.Date;

public class SurveyRecord {
    /**
     * id
     * @Table survey_record.id
     */
    private Long id;

    /**
     * 用户姓名
     * @Table survey_record.user_name
     */
    private String userName;

    /**
     * 用户手机号
     * @Table survey_record.user_phone
     */
    private String userPhone;

    /**
     * 用户驾照图片
     * @Table survey_record.license_picture
     */
    private String licensePicture;

    /**
     * 保险到期日
     * @Table survey_record.expired_time
     */
    private Date expiredTime;

    /**
     * 申请人
     * @Table survey_record.proposer
     */
    private String proposer;

    /**
     * 创建时间
     * @Table survey_record.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table survey_record.update_time
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
    * 获取 用户姓名
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 用户姓名
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 用户手机号
    * @return userPhone
    */
    public String getUserPhone() {
        return userPhone;
    }

    /**
    * 设置 用户手机号
    * @param userPhone
    */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
    * 获取 用户驾照图片
    * @return licensePicture
    */
    public String getLicensePicture() {
        return licensePicture;
    }

    /**
    * 设置 用户驾照图片
    * @param licensePicture
    */
    public void setLicensePicture(String licensePicture) {
        this.licensePicture = licensePicture;
    }

    /**
    * 获取 保险到期日
    * @return expiredTime
    */
    public Date getExpiredTime() {
        return expiredTime;
    }

    /**
    * 设置 保险到期日
    * @param expiredTime
    */
    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
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