package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class Counterman {
    /**
     * 
     * @Table counterman.id
     */
    private Long id;

    /**
     * user表的id
     * @Table counterman.user_id
     */
    private Long userId;

    /**
     * 寿险业务员姓名
     * @Table counterman.name
     */
    private String name;

    /**
     * 寿险营销员代码
     * @Table counterman.counterman_code
     */
    private String countermanCode;

    /**
     * 寿险营销员手机号
     * @Table counterman.phone
     */
    private String phone;

    /**
     * 业务员积分
     * @Table counterman.score
     */
    private BigDecimal score;

    /**
     * 所属财险网点
     * @Table counterman.cai_dot_id
     */
    private Long caiDotId;

    /**
     * 财险网点
     * @Table counterman.cai_dot_name
     */
    private String caiDotName;

    /**
     * 在职 status
     */
    public static final java.lang.Byte STATUS_0 = 0;
    /**
     * 离职 status
     */
    public static final java.lang.Byte STATUS_1 = 1;
    /**
     * 状态：0，在职，1，离职
     * @Table counterman.status
     */
    private Byte status;

    /**
     * 
     * @Table counterman.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table counterman.update_time
     */
    private Date updateTime;

    /**
     * 寿险职场id
     * @Table counterman.life_career_id
     */
    private Long lifeCareerId;

    /**
     * 寿险职场代码
     * @Table counterman.career_code
     */
    private String careerCode;

    /**
     * 寿险职场名称
     * @Table counterman.career_name
     */
    private String careerName;

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
    * 获取 user表的id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 user表的id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 寿险业务员姓名
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 寿险业务员姓名
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取 寿险营销员代码
    * @return countermanCode
    */
    public String getCountermanCode() {
        return countermanCode;
    }

    /**
    * 设置 寿险营销员代码
    * @param countermanCode
    */
    public void setCountermanCode(String countermanCode) {
        this.countermanCode = countermanCode;
    }

    /**
    * 获取 寿险营销员手机号
    * @return phone
    */
    public String getPhone() {
        return phone;
    }

    /**
    * 设置 寿险营销员手机号
    * @param phone
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
    * 获取 业务员积分
    * @return score
    */
    public BigDecimal getScore() {
        return score;
    }

    /**
    * 设置 业务员积分
    * @param score
    */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
    * 获取 所属财险网点
    * @return caiDotId
    */
    public Long getCaiDotId() {
        return caiDotId;
    }

    /**
    * 设置 所属财险网点
    * @param caiDotId
    */
    public void setCaiDotId(Long caiDotId) {
        this.caiDotId = caiDotId;
    }

    /**
    * 获取 财险网点
    * @return caiDotName
    */
    public String getCaiDotName() {
        return caiDotName;
    }

    /**
    * 设置 财险网点
    * @param caiDotName
    */
    public void setCaiDotName(String caiDotName) {
        this.caiDotName = caiDotName;
    }

    /**
    * 获取 状态：0，在职，1，离职
    * @return status
    */
    public Byte getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，在职，1，离职
    * @param status
    */
    public void setStatus(Byte status) {
        this.status = status;
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

    /**
    * 获取 寿险职场id
    * @return lifeCareerId
    */
    public Long getLifeCareerId() {
        return lifeCareerId;
    }

    /**
    * 设置 寿险职场id
    * @param lifeCareerId
    */
    public void setLifeCareerId(Long lifeCareerId) {
        this.lifeCareerId = lifeCareerId;
    }

    /**
    * 获取 寿险职场代码
    * @return careerCode
    */
    public String getCareerCode() {
        return careerCode;
    }

    /**
    * 设置 寿险职场代码
    * @param careerCode
    */
    public void setCareerCode(String careerCode) {
        this.careerCode = careerCode;
    }

    /**
    * 获取 寿险职场名称
    * @return careerName
    */
    public String getCareerName() {
        return careerName;
    }

    /**
    * 设置 寿险职场名称
    * @param careerName
    */
    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }
}