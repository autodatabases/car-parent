package com.emate.shop.business.model;

/**
 * @file AuthCodeVo.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月29日 
 */
public class AuthCodeVo {

    //发送验证码记录id
    private Long   id;

    private String userphone;

    //所属平台
    private String platform;

    private String createTime;

    private String code;

    //有效时间
    private String effectiveTime;

    //接收状态
    private String status;

    //失败反馈
    private String FailureFeedback;

    public AuthCodeVo() {
    }

    public AuthCodeVo(Long id, String userphone, String platform,
            String createTime, String code, String effectiveTime, String status,
            String failureFeedback) {
        this.id = id;
        this.userphone = userphone;
        this.platform = platform;
        this.createTime = createTime;
        this.code = code;
        this.effectiveTime = effectiveTime;
        this.status = status;
        FailureFeedback = failureFeedback;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userphone
     */
    public String getUserphone() {
        return userphone;
    }

    /**
     * @param userphone the userphone to set
     */
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    /**
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the effectiveTime
     */
    public String getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * @param effectiveTime the effectiveTime to set
     */
    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the failureFeedback
     */
    public String getFailureFeedback() {
        return FailureFeedback;
    }

    /**
     * @param failureFeedback the failureFeedback to set
     */
    public void setFailureFeedback(String failureFeedback) {
        FailureFeedback = failureFeedback;
    }

}
