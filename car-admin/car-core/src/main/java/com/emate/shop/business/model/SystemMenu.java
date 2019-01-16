package com.emate.shop.business.model;

import java.util.Date;

public class SystemMenu {
    /**
     * 
     * @Table system_menu.id
     */
    private Long id;

    /**
     * 名称
     * @Table system_menu.name
     */
    private String name;

    /**
     * 说明
     * @Table system_menu.remark
     */
    private String remark;

    /**
     * 排序
     * @Table system_menu.seq
     */
    private Long seq;

    /**
     * 
     * @Table system_menu.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table system_menu.update_time
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
    * 获取 名称
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 名称
    * @param name
    */
    public void setName(String name) {
        this.name = name;
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