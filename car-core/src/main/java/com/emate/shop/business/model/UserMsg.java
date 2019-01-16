package com.emate.shop.business.model;

import java.util.Date;

public class UserMsg {
    /**
     * 
     * @Table user_msg.id
     */
    private Long id;

    /**
     * 
     * @Table user_msg.user_id
     */
    private Long userId;

    /**
     * 
     * @Table user_msg.msg_title
     */
    private String msgTitle;

    /**
     * 消息内容
     * @Table user_msg.mgs_content
     */
    private String mgsContent;

    /**
     * 更新时间
     * @Table user_msg.update_time
     */
    private Date updateTime;

    /**
     * 创建时间
     * @Table user_msg.create_time
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
    * 获取 
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 
    * @return msgTitle
    */
    public String getMsgTitle() {
        return msgTitle;
    }

    /**
    * 设置 
    * @param msgTitle
    */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    /**
    * 获取 消息内容
    * @return mgsContent
    */
    public String getMgsContent() {
        return mgsContent;
    }

    /**
    * 设置 消息内容
    * @param mgsContent
    */
    public void setMgsContent(String mgsContent) {
        this.mgsContent = mgsContent;
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
}