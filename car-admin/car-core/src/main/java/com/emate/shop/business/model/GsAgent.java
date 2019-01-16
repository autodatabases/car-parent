package com.emate.shop.business.model;

import java.util.Date;

public class GsAgent {
    /**
     * id
     * @Table gs_agent.id
     */
    private Long id;

    /**
     * 网点机构
     * @Table gs_agent.dot
     */
    private String dot;

    /**
     * 代理公司
     * @Table gs_agent.agency
     */
    private String agency;

    /**
     * 合作时间
     * @Table gs_agent.cooperate_time
     */
    private Date cooperateTime;

    /**
     * 创建时间
     * @Table gs_agent.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table gs_agent.update_time
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
    * 获取 网点机构
    * @return dot
    */
    public String getDot() {
        return dot;
    }

    /**
    * 设置 网点机构
    * @param dot
    */
    public void setDot(String dot) {
        this.dot = dot;
    }

    /**
    * 获取 代理公司
    * @return agency
    */
    public String getAgency() {
        return agency;
    }

    /**
    * 设置 代理公司
    * @param agency
    */
    public void setAgency(String agency) {
        this.agency = agency;
    }

    /**
    * 获取 合作时间
    * @return cooperateTime
    */
    public Date getCooperateTime() {
        return cooperateTime;
    }

    /**
    * 设置 合作时间
    * @param cooperateTime
    */
    public void setCooperateTime(Date cooperateTime) {
        this.cooperateTime = cooperateTime;
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