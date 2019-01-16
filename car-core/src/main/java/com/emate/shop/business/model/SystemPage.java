package com.emate.shop.business.model;

import java.util.Date;

public class SystemPage {
    /**
     * 
     * @Table system_page.id
     */
    private Long id;

    /**
     * 页面名称
     * @Table system_page.name
     */
    private String name;

    /**
     * 页面URL
     * @Table system_page.page_url
     */
    private String pageUrl;

    /**
     * 一级菜单ID
     * @Table system_page.menu_id
     */
    private Long menuId;

    /**
     * 排序
     * @Table system_page.seq
     */
    private Long seq;

    /**
     * 
     * @Table system_page.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table system_page.update_time
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
    * 获取 页面名称
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 页面名称
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取 页面URL
    * @return pageUrl
    */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
    * 设置 页面URL
    * @param pageUrl
    */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
    * 获取 一级菜单ID
    * @return menuId
    */
    public Long getMenuId() {
        return menuId;
    }

    /**
    * 设置 一级菜单ID
    * @param menuId
    */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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