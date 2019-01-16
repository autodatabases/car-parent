package com.emate.shop.business.model;

import java.util.Date;

public class OilGoodsType {
    /**
     * 
     * @Table oil_goods_type.id
     */
    private Long id;

    /**
     * 商品一级类目
     * @Table oil_goods_type.type_name
     */
    private String typeName;

    /**
     * 一级类目 typeGrade
     */
    public static final java.lang.Integer TYPE_GRADE_1 = 1;
    /**
     * 二级类目 typeGrade
     */
    public static final java.lang.Integer TYPE_GRADE_2 = 2;
    /**
     * 三级类目 typeGrade
     */
    public static final java.lang.Integer TYPE_GRADE_3 = 3;
    /**
     * 类目等级：1，一级类目，2，二级类目，3，三级类目
     * @Table oil_goods_type.type_grade
     */
    private Integer typeGrade;

    /**
     * 父类idf
     * @Table oil_goods_type.parent_id
     */
    private Long parentId;

    /**
     * 备注
     * @Table oil_goods_type.remark
     */
    private String remark;

    /**
     * 创建时间
     * @Table oil_goods_type.create_time
     */
    private Date createTime;

    /**
     * 修改时间
     * @Table oil_goods_type.update_time
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
    * 获取 商品一级类目
    * @return typeName
    */
    public String getTypeName() {
        return typeName;
    }

    /**
    * 设置 商品一级类目
    * @param typeName
    */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
    * 获取 类目等级：1，一级类目，2，二级类目，3，三级类目
    * @return typeGrade
    */
    public Integer getTypeGrade() {
        return typeGrade;
    }

    /**
    * 设置 类目等级：1，一级类目，2，二级类目，3，三级类目
    * @param typeGrade
    */
    public void setTypeGrade(Integer typeGrade) {
        this.typeGrade = typeGrade;
    }

    /**
    * 获取 父类idf
    * @return parentId
    */
    public Long getParentId() {
        return parentId;
    }

    /**
    * 设置 父类idf
    * @param parentId
    */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
    * 获取 备注
    * @return remark
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置 备注
    * @param remark
    */
    public void setRemark(String remark) {
        this.remark = remark;
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
    * 获取 修改时间
    * @return updateTime
    */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
    * 设置 修改时间
    * @param updateTime
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}