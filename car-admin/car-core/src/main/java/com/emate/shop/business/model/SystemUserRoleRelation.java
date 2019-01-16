package com.emate.shop.business.model;

import java.util.Date;

public class SystemUserRoleRelation {
    /**
     * 
     * @Table system_user_role_relation.id
     */
    private Long id;

    /**
     * 用户ID
     * @Table system_user_role_relation.user_id
     */
    private Long userId;

    /**
     * 角色ID
     * @Table system_user_role_relation.role_id
     */
    private Long roleId;

    /**
     * 
     * @Table system_user_role_relation.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table system_user_role_relation.update_time
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
    * 获取 用户ID
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 用户ID
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
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