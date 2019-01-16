package com.emate.shop.business.model;

import java.util.Date;

public class AccessToken {
    /**
     * 自增列
     * @Table access_token.id
     */
    private Long id;

    /**
     * 从api.weixin.com获取的token
     * @Table access_token.access_token
     */
    private String accessToken;

    /**
     * 过期时间
     * @Table access_token.expires_in
     */
    private Integer expiresIn;

    /**
     * 接入类型
     * @Table access_token.access_type
     */
    private String accessType;

    /**
     * 更新时间
     * @Table access_token.update_time
     */
    private Date updateTime;

    /**
     * 创建时间
     * @Table access_token.create_time
     */
    private Date createTime;

    /**
    * 获取 自增列
    * @return id
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置 自增列
    * @param id
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取 从api.weixin.com获取的token
    * @return accessToken
    */
    public String getAccessToken() {
        return accessToken;
    }

    /**
    * 设置 从api.weixin.com获取的token
    * @param accessToken
    */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
    * 获取 过期时间
    * @return expiresIn
    */
    public Integer getExpiresIn() {
        return expiresIn;
    }

    /**
    * 设置 过期时间
    * @param expiresIn
    */
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
    * 获取 接入类型
    * @return accessType
    */
    public String getAccessType() {
        return accessType;
    }

    /**
    * 设置 接入类型
    * @param accessType
    */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
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