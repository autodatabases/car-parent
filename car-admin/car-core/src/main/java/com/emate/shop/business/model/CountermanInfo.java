package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class CountermanInfo {
    /**
     * 
     * @Table counterman_info.id
     */
    private Long id;

    /**
     * 保单号
     * @Table counterman_info.policy_number
     */
    private String policyNumber;

    /**
     * 续保 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_0 = 0;
    /**
     * 新保单 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_1 = 1;
    /**
     * 转保 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_2 = 2;
    /**
     * 未知保单 serviceType
     */
    public static final java.lang.Integer SERVICE_TYPE_3 = 3;
    /**
     * 业务类型：0，续保，1，新保单，2，转保，3，未知保单
     * @Table counterman_info.service_type
     */
    private Integer serviceType;

    /**
     * 国寿天财 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_0 = 0;
    /**
     * 人工出单 orderType
     */
    public static final java.lang.Integer ORDER_TYPE_1 = 1;
    /**
     * 出单类型：0，国寿天财，1，人工出单
     * @Table counterman_info.order_type
     */
    private Integer orderType;

    /**
     * 收付日期
     * @Table counterman_info.payment_time
     */
    private Date paymentTime;

    /**
     * 保费收入(含税)
     * @Table counterman_info.order_price
     */
    private BigDecimal orderPrice;

    /**
     * 寿险营销员
     * @Table counterman_info.counterman_name
     */
    private String countermanName;

    /**
     * 寿险营销员的代码
     * @Table counterman_info.counterman_code
     */
    private String countermanCode;

    /**
     * 财险职场代码
     * @Table counterman_info.career_code
     */
    private String careerCode;

    /**
     * 财险职场名称
     * @Table counterman_info.career_name
     */
    private String careerName;

    /**
     * 创建时间
     * @Table counterman_info.create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * @Table counterman_info.update_time
     */
    private Date updateTime;

    /**
     * 业务号
     * @Table counterman_info.service_number
     */
    private String serviceNumber;

    /**
     * 类型
     * @Table counterman_info.type
     */
    private String type;

    /**
     * 分公司
     * @Table counterman_info.branch_company
     */
    private String branchCompany;

    /**
     * 所属片区
     * @Table counterman_info.belong_area
     */
    private String belongArea;

    /**
     * 统计日期(起/生效,核保/核批大者)
     * @Table counterman_info.count_time
     */
    private Date countTime;

    /**
     * 起保/生效日期
     * @Table counterman_info.protection_time
     */
    private Date protectionTime;

    /**
     * 终保日期
     * @Table counterman_info.final_time
     */
    private Date finalTime;

    /**
     * 签单日期
     * @Table counterman_info.sign_time
     */
    private Date signTime;

    /**
     * 核保日期(核批日期)
     * @Table counterman_info.underwrite_time
     */
    private Date underwriteTime;

    /**
     * 险种代码
     * @Table counterman_info.insurance_code
     */
    private String insuranceCode;

    /**
     * 险种名称
     * @Table counterman_info.insurance_name
     */
    private String insuranceName;

    /**
     * 销售渠道代码
     * @Table counterman_info.sale_code
     */
    private String saleCode;

    /**
     * 销售渠道名称
     * @Table counterman_info.sale_name
     */
    private String saleName;

    /**
     * 业务来源代码
     * @Table counterman_info.business_code
     */
    private String businessCode;

    /**
     * 业务来源名称
     * @Table counterman_info.business_name
     */
    private String businessName;

    /**
     * 财险业务员代码
     * @Table counterman_info.salesman_code
     */
    private String salesmanCode;

    /**
     * 财险业务员名称
     * @Table counterman_info.salesman_name
     */
    private String salesmanName;

    /**
     * 保费/保费变化(含税)
     * @Table counterman_info.premium_price
     */
    private BigDecimal premiumPrice;

    /**
     * 保费/保费变化(不含税)
     * @Table counterman_info.preminum_two
     */
    private BigDecimal preminumTwo;

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
    * 获取 保单号
    * @return policyNumber
    */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
    * 设置 保单号
    * @param policyNumber
    */
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    /**
    * 获取 业务类型：0，续保，1，新保单，2，转保，3，未知保单
    * @return serviceType
    */
    public Integer getServiceType() {
        return serviceType;
    }

    /**
    * 设置 业务类型：0，续保，1，新保单，2，转保，3，未知保单
    * @param serviceType
    */
    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    /**
    * 获取 出单类型：0，国寿天财，1，人工出单
    * @return orderType
    */
    public Integer getOrderType() {
        return orderType;
    }

    /**
    * 设置 出单类型：0，国寿天财，1，人工出单
    * @param orderType
    */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
    * 获取 收付日期
    * @return paymentTime
    */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
    * 设置 收付日期
    * @param paymentTime
    */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
    * 获取 保费收入(含税)
    * @return orderPrice
    */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
    * 设置 保费收入(含税)
    * @param orderPrice
    */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
    * 获取 寿险营销员
    * @return countermanName
    */
    public String getCountermanName() {
        return countermanName;
    }

    /**
    * 设置 寿险营销员
    * @param countermanName
    */
    public void setCountermanName(String countermanName) {
        this.countermanName = countermanName;
    }

    /**
    * 获取 寿险营销员的代码
    * @return countermanCode
    */
    public String getCountermanCode() {
        return countermanCode;
    }

    /**
    * 设置 寿险营销员的代码
    * @param countermanCode
    */
    public void setCountermanCode(String countermanCode) {
        this.countermanCode = countermanCode;
    }

    /**
    * 获取 财险职场代码
    * @return careerCode
    */
    public String getCareerCode() {
        return careerCode;
    }

    /**
    * 设置 财险职场代码
    * @param careerCode
    */
    public void setCareerCode(String careerCode) {
        this.careerCode = careerCode;
    }

    /**
    * 获取 财险职场名称
    * @return careerName
    */
    public String getCareerName() {
        return careerName;
    }

    /**
    * 设置 财险职场名称
    * @param careerName
    */
    public void setCareerName(String careerName) {
        this.careerName = careerName;
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

    /**
    * 获取 业务号
    * @return serviceNumber
    */
    public String getServiceNumber() {
        return serviceNumber;
    }

    /**
    * 设置 业务号
    * @param serviceNumber
    */
    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    /**
    * 获取 类型
    * @return type
    */
    public String getType() {
        return type;
    }

    /**
    * 设置 类型
    * @param type
    */
    public void setType(String type) {
        this.type = type;
    }

    /**
    * 获取 分公司
    * @return branchCompany
    */
    public String getBranchCompany() {
        return branchCompany;
    }

    /**
    * 设置 分公司
    * @param branchCompany
    */
    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    /**
    * 获取 所属片区
    * @return belongArea
    */
    public String getBelongArea() {
        return belongArea;
    }

    /**
    * 设置 所属片区
    * @param belongArea
    */
    public void setBelongArea(String belongArea) {
        this.belongArea = belongArea;
    }

    /**
    * 获取 统计日期(起/生效,核保/核批大者)
    * @return countTime
    */
    public Date getCountTime() {
        return countTime;
    }

    /**
    * 设置 统计日期(起/生效,核保/核批大者)
    * @param countTime
    */
    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    /**
    * 获取 起保/生效日期
    * @return protectionTime
    */
    public Date getProtectionTime() {
        return protectionTime;
    }

    /**
    * 设置 起保/生效日期
    * @param protectionTime
    */
    public void setProtectionTime(Date protectionTime) {
        this.protectionTime = protectionTime;
    }

    /**
    * 获取 终保日期
    * @return finalTime
    */
    public Date getFinalTime() {
        return finalTime;
    }

    /**
    * 设置 终保日期
    * @param finalTime
    */
    public void setFinalTime(Date finalTime) {
        this.finalTime = finalTime;
    }

    /**
    * 获取 签单日期
    * @return signTime
    */
    public Date getSignTime() {
        return signTime;
    }

    /**
    * 设置 签单日期
    * @param signTime
    */
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    /**
    * 获取 核保日期(核批日期)
    * @return underwriteTime
    */
    public Date getUnderwriteTime() {
        return underwriteTime;
    }

    /**
    * 设置 核保日期(核批日期)
    * @param underwriteTime
    */
    public void setUnderwriteTime(Date underwriteTime) {
        this.underwriteTime = underwriteTime;
    }

    /**
    * 获取 险种代码
    * @return insuranceCode
    */
    public String getInsuranceCode() {
        return insuranceCode;
    }

    /**
    * 设置 险种代码
    * @param insuranceCode
    */
    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    /**
    * 获取 险种名称
    * @return insuranceName
    */
    public String getInsuranceName() {
        return insuranceName;
    }

    /**
    * 设置 险种名称
    * @param insuranceName
    */
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    /**
    * 获取 销售渠道代码
    * @return saleCode
    */
    public String getSaleCode() {
        return saleCode;
    }

    /**
    * 设置 销售渠道代码
    * @param saleCode
    */
    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    /**
    * 获取 销售渠道名称
    * @return saleName
    */
    public String getSaleName() {
        return saleName;
    }

    /**
    * 设置 销售渠道名称
    * @param saleName
    */
    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    /**
    * 获取 业务来源代码
    * @return businessCode
    */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
    * 设置 业务来源代码
    * @param businessCode
    */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
    * 获取 业务来源名称
    * @return businessName
    */
    public String getBusinessName() {
        return businessName;
    }

    /**
    * 设置 业务来源名称
    * @param businessName
    */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
    * 获取 财险业务员代码
    * @return salesmanCode
    */
    public String getSalesmanCode() {
        return salesmanCode;
    }

    /**
    * 设置 财险业务员代码
    * @param salesmanCode
    */
    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    /**
    * 获取 财险业务员名称
    * @return salesmanName
    */
    public String getSalesmanName() {
        return salesmanName;
    }

    /**
    * 设置 财险业务员名称
    * @param salesmanName
    */
    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    /**
    * 获取 保费/保费变化(含税)
    * @return premiumPrice
    */
    public BigDecimal getPremiumPrice() {
        return premiumPrice;
    }

    /**
    * 设置 保费/保费变化(含税)
    * @param premiumPrice
    */
    public void setPremiumPrice(BigDecimal premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    /**
    * 获取 保费/保费变化(不含税)
    * @return preminumTwo
    */
    public BigDecimal getPreminumTwo() {
        return preminumTwo;
    }

    /**
    * 设置 保费/保费变化(不含税)
    * @param preminumTwo
    */
    public void setPreminumTwo(BigDecimal preminumTwo) {
        this.preminumTwo = preminumTwo;
    }
}