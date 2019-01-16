package com.emate.shop.business.model;

import java.util.Date;

public class GsBranch {
    /**
     * id
     * @Table gs_branch.id
     */
    private Long id;

    /**
     * 定损中心 type
     */
    public static final java.lang.Integer TYPE_0 = 0;
    /**
     * 营业网点 type
     */
    public static final java.lang.Integer TYPE_1 = 1;
    /**
     * 部门分类：0，定损中心，1，营业网点
     * @Table gs_branch.type
     */
    private Integer type;

    /**
     * 国寿部门(定损中心,营业网点)名称
     * @Table gs_branch.name
     */
    private String name;

    /**
     * 国寿部门(定损中心,营业网点)详细地址
     * @Table gs_branch.address
     */
    private String address;

    /**
     * 国寿部门(定损中心,营业网点)坐标
     * @Table gs_branch.position
     */
    private String position;

    /**
     * 创建时间
     * @Table gs_branch.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table gs_branch.update_time
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
    * 获取 部门分类：0，定损中心，1，营业网点
    * @return type
    */
    public Integer getType() {
        return type;
    }

    /**
    * 设置 部门分类：0，定损中心，1，营业网点
    * @param type
    */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
    * 获取 国寿部门(定损中心,营业网点)名称
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 国寿部门(定损中心,营业网点)名称
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取 国寿部门(定损中心,营业网点)详细地址
    * @return address
    */
    public String getAddress() {
        return address;
    }

    /**
    * 设置 国寿部门(定损中心,营业网点)详细地址
    * @param address
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
    * 获取 国寿部门(定损中心,营业网点)坐标
    * @return position
    */
    public String getPosition() {
        return position;
    }

    /**
    * 设置 国寿部门(定损中心,营业网点)坐标
    * @param position
    */
    public void setPosition(String position) {
        this.position = position;
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