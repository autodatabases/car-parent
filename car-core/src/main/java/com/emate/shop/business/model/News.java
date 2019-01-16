package com.emate.shop.business.model;

import java.util.Date;

public class News {
    /**
     * 
     * @Table news.id
     */
    private Long id;

    /**
     * 新闻标题
     * @Table news.title
     */
    private String title;

    /**
     * 文章内容
     * @Table news.content
     */
    private String content;

    /**
     * 浏览次数
     * @Table news.view
     */
    private Integer view;

    /**
     * 创建时间
     * @Table news.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table news.update_time
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
    * 获取 新闻标题
    * @return title
    */
    public String getTitle() {
        return title;
    }

    /**
    * 设置 新闻标题
    * @param title
    */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * 获取 文章内容
    * @return content
    */
    public String getContent() {
        return content;
    }

    /**
    * 设置 文章内容
    * @param content
    */
    public void setContent(String content) {
        this.content = content;
    }

    /**
    * 获取 浏览次数
    * @return view
    */
    public Integer getView() {
        return view;
    }

    /**
    * 设置 浏览次数
    * @param view
    */
    public void setView(Integer view) {
        this.view = view;
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