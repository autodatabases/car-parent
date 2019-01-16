package com.emate.shop.business.model;

import java.util.Date;

public class CountermanLifeCareer {
    /**
     * id
     * @Table counterman_life_career.id
     */
    private Long id;

    /**
     * 寿险职场代码
     * @Table counterman_life_career.career_code
     */
    private String careerCode;

    /**
     * 寿险职场名称
     * @Table counterman_life_career.career_name
     */
    private String careerName;

    /**
     * 
     * @Table counterman_life_career.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table counterman_life_career.update_time
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
    * 获取 寿险职场代码
    * @return careerCode
    */
    public String getCareerCode() {
        return careerCode;
    }

    /**
    * 设置 寿险职场代码
    * @param careerCode
    */
    public void setCareerCode(String careerCode) {
        this.careerCode = careerCode;
    }

    /**
    * 获取 寿险职场名称
    * @return careerName
    */
    public String getCareerName() {
        return careerName;
    }

    /**
    * 设置 寿险职场名称
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