package com.emate.shop.business.model;

import java.util.Date;

public class Express {
    /**
     * 
     * @Table express.id
     */
    private Long id;

    /**
     * 快递公司名称
     * @Table express.express_name
     */
    private String expressName;

    /**
     * 快递公司代码
     * @Table express.express_code
     */
    private String expressCode;

    /**
     * 
     * @Table express.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table express.update_time
     */
    private Date updateTime;

    /**
     * 
     * @Table express.seq
     */
    private Long seq;

    /**
     * 启用
     * @Table express.status
     */
    private Boolean status;

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
    * 获取 快递公司名称
    * @return expressName
    */
    public String getExpressName() {
        return expressName;
    }

    /**
    * 设置 快递公司名称
    * @param expressName
    */
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    /**
    * 获取 快递公司代码
    * @return expressCode
    */
    public String getExpressCode() {
        return expressCode;
    }

    /**
    * 设置 快递公司代码
    * @param expressCode
    */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
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
    * 获取 
    * @return seq
    */
    public Long getSeq() {
        return seq;
    }

    /**
    * 设置 
    * @param seq
    */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
    * 获取 启用
    * @return status
    */
    public Boolean getStatus() {
        return status;
    }

    /**
    * 设置 启用
    * @param status
    */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}