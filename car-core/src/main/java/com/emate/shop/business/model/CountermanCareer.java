package com.emate.shop.business.model;

import java.util.Date;

public class CountermanCareer {
    /**
     * id
     * @Table counterman_career.id
     */
    private Long id;

    /**
     * 所属分公司
     * @Table counterman_career.branch_company
     */
    private String branchCompany;

    /**
     * 所属网点id
     * @Table counterman_career.cai_dot_id
     */
    private Long caiDotId;

    /**
     * 所属财险网点
     * @Table counterman_career.cai_dot_name
     */
    private String caiDotName;

    /**
     * 职场代码
     * @Table counterman_career.career_code
     */
    private String careerCode;

    /**
     * 职场名称
     * @Table counterman_career.career_name
     */
    private String careerName;

    /**
     * 
     * @Table counterman_career.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table counterman_career.update_time
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
    * 获取 所属分公司
    * @return branchCompany
    */
    public String getBranchCompany() {
        return branchCompany;
    }

    /**
    * 设置 所属分公司
    * @param branchCompany
    */
    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    /**
    * 获取 所属网点id
    * @return caiDotId
    */
    public Long getCaiDotId() {
        return caiDotId;
    }

    /**
    * 设置 所属网点id
    * @param caiDotId
    */
    public void setCaiDotId(Long caiDotId) {
        this.caiDotId = caiDotId;
    }

    /**
    * 获取 所属财险网点
    * @return caiDotName
    */
    public String getCaiDotName() {
        return caiDotName;
    }

    /**
    * 设置 所属财险网点
    * @param caiDotName
    */
    public void setCaiDotName(String caiDotName) {
        this.caiDotName = caiDotName;
    }

    /**
    * 获取 职场代码
    * @return careerCode
    */
    public String getCareerCode() {
        return careerCode;
    }

    /**
    * 设置 职场代码
    * @param careerCode
    */
    public void setCareerCode(String careerCode) {
        this.careerCode = careerCode;
    }

    /**
    * 获取 职场名称
    * @return careerName
    */
    public String getCareerName() {
        return careerName;
    }

    /**
    * 设置 职场名称
    * @param careerName
    */
    public void setCareerName(String careerName) {
        this.careerName = careerName;
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