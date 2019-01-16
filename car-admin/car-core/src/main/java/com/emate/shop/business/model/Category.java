package com.emate.shop.business.model;

import java.util.Date;

public class Category {
    /**
     * 
     * @Table category.id
     */
    private Long id;

    /**
     * 父级ID：0代表没有父级
     * @Table category.parent_id
     */
    private Long parentId;

    /**
     * 类型ID
     * @Table category.type_id
     */
    private Long typeId;

    /**
     * 类目路径，父子类目ID逗号分隔
     * @Table category.path
     */
    private String path;

    /**
     * 未分级 level
     */
    public static final java.lang.Integer LEVEL_0 = 0;
    /**
     * 一级 level
     */
    public static final java.lang.Integer LEVEL_1 = 1;
    /**
     * 二级 level
     */
    public static final java.lang.Integer LEVEL_2 = 2;
    /**
     * 三级 level
     */
    public static final java.lang.Integer LEVEL_3 = 3;
    /**
     * 类目级别：0，未分级，1，一级，2，二级，3，三级
     * @Table category.level
     */
    private Integer level;

    /**
     * 类目名称
     * @Table category.name
     */
    private String name;

    /**
     * 搜索关键词
     * @Table category.keyword
     */
    private String keyword;

    /**
     * 
     * @Table category.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table category.update_time
     */
    private Date updateTime;

    /**
     * 
     * @Table category.seq
     */
    private Long seq;

    /**
     * 新建 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 删除 status
     */
    public static final java.lang.Integer STATUS_9 = 9;
    /**
     * 状态：0，新建，9，删除
     * @Table category.status
     */
    private Integer status;

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
    * 获取 父级ID：0代表没有父级
    * @return parentId
    */
    public Long getParentId() {
        return parentId;
    }

    /**
    * 设置 父级ID：0代表没有父级
    * @param parentId
    */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
    * 获取 类型ID
    * @return typeId
    */
    public Long getTypeId() {
        return typeId;
    }

    /**
    * 设置 类型ID
    * @param typeId
    */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
    * 获取 类目路径，父子类目ID逗号分隔
    * @return path
    */
    public String getPath() {
        return path;
    }

    /**
    * 设置 类目路径，父子类目ID逗号分隔
    * @param path
    */
    public void setPath(String path) {
        this.path = path;
    }

    /**
    * 获取 类目级别：0，未分级，1，一级，2，二级，3，三级
    * @return level
    */
    public Integer getLevel() {
        return level;
    }

    /**
    * 设置 类目级别：0，未分级，1，一级，2，二级，3，三级
    * @param level
    */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
    * 获取 类目名称
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 类目名称
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取 搜索关键词
    * @return keyword
    */
    public String getKeyword() {
        return keyword;
    }

    /**
    * 设置 搜索关键词
    * @param keyword
    */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    * 获取 状态：0，新建，9，删除
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，新建，9，删除
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }
}