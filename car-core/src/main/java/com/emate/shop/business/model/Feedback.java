package com.emate.shop.business.model;

import java.util.Date;

public class Feedback {
    /**
     * 
     * @Table feedback.id
     */
    private Long id;

    /**
     * 用户id
     * @Table feedback.user_id
     */
    private Long userId;

    /**
     * 用户名
     * @Table feedback.user_name
     */
    private String userName;

    /**
     * 反馈类型
     * @Table feedback.content
     */
    private String content;

    /**
     * 备注(客服解决与否)
     * @Table feedback.remark
     */
    private String remark;

    /**
     * 
     * @Table feedback.update_time
     */
    private Date updateTime;

    /**
     * 
     * @Table feedback.create_time
     */
    private Date createTime;

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
    * 获取 用户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 用户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 用户名
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 用户名
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 反馈类型
    * @return content
    */
    public String getContent() {
        return content;
    }

    /**
    * 设置 反馈类型
    * @param content
    */
    public void setContent(String content) {
        this.content = content;
    }

    /**
    * 获取 备注(客服解决与否)
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 备注(客服解决与否)
    * @param remark
    */
    public void setRemark(String remark) {
        this.remark = remark;
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
}