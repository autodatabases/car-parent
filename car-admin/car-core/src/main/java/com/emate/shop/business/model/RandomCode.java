package com.emate.shop.business.model;

import java.util.Date;

public class RandomCode {
    /**
     * 
     * @Table random_code.id
     */
    private Long id;

    /**
     * 随机字符串
     * @Table random_code.code
     */
    private String code;

    /**
     * 初始状态 status
     */
    public static final java.lang.Byte STATUS_0 = 0;
    /**
     * 已经使用 status
     */
    public static final java.lang.Byte STATUS_1 = 1;
    /**
     * 状态：0，初始状态，1，已经使用
     * @Table random_code.status
     */
    private Byte status;

    /**
     * 
     * @Table random_code.create_time
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
    * 获取 随机字符串
    * @return code
    */
    public String getCode() {
        return code;
    }

    /**
    * 设置 随机字符串
    * @param code
    */
    public void setCode(String code) {
        this.code = code;
    }

    /**
    * 获取 状态：0，初始状态，1，已经使用
    * @return status
    */
    public Byte getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，初始状态，1，已经使用
    * @param status
    */
    public void setStatus(Byte status) {
        this.status = status;
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
}