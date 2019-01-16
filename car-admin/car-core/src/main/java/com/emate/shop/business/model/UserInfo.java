package com.emate.shop.business.model;

import java.util.Date;

public class UserInfo {
    /**
     * 
     * @Table user_info.id
     */
    private Long id;

    /**
     * 
     * @Table user_info.user_id
     */
    private Long userId;

    /**
     * 省份
     * @Table user_info.province
     */
    private String province;

    /**
     * 城市
     * @Table user_info.city
     */
    private String city;

    /**
     * 区域
     * @Table user_info.area
     */
    private String area;

    /**
     * 车牌号
     * @Table user_info.license
     */
    private String license;

    /**
     * 里程
     * @Table user_info.mileage
     */
    private Integer mileage;

    /**
     * 上传的车架号图片路径
     * @Table user_info.car_img
     */
    private String carImg;

    /**
     * 更新时间
     * @Table user_info.update_time
     */
    private Date updateTime;

    /**
     * 创建时间
     * @Table user_info.create_time
     */
    private Date createTime;

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
    * 获取 
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 省份
    * @return province
    */
    public String getProvince() {
        return province;
    }

    /**
    * 设置 省份
    * @param province
    */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
    * 获取 城市
    * @return city
    */
    public String getCity() {
        return city;
    }

    /**
    * 设置 城市
    * @param city
    */
    public void setCity(String city) {
        this.city = city;
    }

    /**
    * 获取 区域
    * @return area
    */
    public String getArea() {
        return area;
    }

    /**
    * 设置 区域
    * @param area
    */
    public void setArea(String area) {
        this.area = area;
    }

    /**
    * 获取 车牌号
    * @return license
    */
    public String getLicense() {
        return license;
    }

    /**
    * 设置 车牌号
    * @param license
    */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
    * 获取 里程
    * @return mileage
    */
    public Integer getMileage() {
        return mileage;
    }

    /**
    * 设置 里程
    * @param mileage
    */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
    * 获取 上传的车架号图片路径
    * @return carImg
    */
    public String getCarImg() {
        return carImg;
    }

    /**
    * 设置 上传的车架号图片路径
    * @param carImg
    */
    public void setCarImg(String carImg) {
        this.carImg = carImg;
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