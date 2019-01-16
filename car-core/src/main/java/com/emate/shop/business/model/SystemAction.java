package com.emate.shop.business.model;

import java.util.Date;

public class SystemAction {
    /**
     * 
     * @Table system_action.id
     */
    private Long id;

    /**
     * 操作描述
     * @Table system_action.name
     */
    private String name;

    /**
     * 页面ID
     * @Table system_action.page_id
     */
    private Long pageId;

    /**
     * 操作url
     * @Table system_action.action_url
     */
    private String actionUrl;

    /**
     * 排序
     * @Table system_action.seq
     */
    private Long seq;

    /**
     * 说明
     * @Table system_action.remark
     */
    private String remark;

    /**
     * 
     * @Table system_action.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table system_action.update_time
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
    * 获取 操作描述
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 操作描述
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取 页面ID
    * @return pageId
    */
    public Long getPageId() {
        return pageId;
    }

    /**
    * 设置 页面ID
    * @param pageId
    */
    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    /**
    * 获取 操作url
    * @return actionUrl
    */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
    * 设置 操作url
    * @param actionUrl
    */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    /**
    * 获取 排序
    * @return seq
    */
    public Long getSeq() {
        return seq;
    }

    /**
    * 设置 排序
    * @param seq
    */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
    * 获取 说明
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 说明
    * @param remark
    */
    public void setRemark(String remark) {
        this.remark = remark;
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
}