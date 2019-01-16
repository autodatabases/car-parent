package com.emate.shop.business.model;

import java.util.Date;

public class SystemRolePageRelation {
    /**
     * 
     * @Table system_role_page_relation.id
     */
    private Long id;

    /**
     * 角色ID
     * @Table system_role_page_relation.role_id
     */
    private Long roleId;

    /**
     * 页面ID
     * @Table system_role_page_relation.page_id
     */
    private Long pageId;

    /**
     * 
     * @Table system_role_page_relation.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table system_role_page_relation.update_time
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
    * 获取 角色ID
    * @return roleId
    */
    public Long getRoleId() {
        return roleId;
    }

    /**
    * 设置 角色ID
    * @param roleId
    */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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