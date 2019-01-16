package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class ImportUserInfo {
    /**
     * 
     * @Table import_user_info.id
     */
    private Long id;

    /**
     * 真实姓名
     * @Table import_user_info.real_name
     */
    private String realName;

    /**
     * 手机号码
     * @Table import_user_info.phone
     */
    private String phone;

    /**
     * 车牌号
     * @Table import_user_info.che_pai
     */
    private String chePai;

    /**
     * 汽车品牌
     * @Table import_user_info.auto_brand
     */
    private String autoBrand;

    /**
     * 生产厂商
     * @Table import_user_info.car_factory
     */
    private String carFactory;

    /**
     * 车系
     * @Table import_user_info.auto_type
     */
    private String autoType;

    /**
     * 车型
     * @Table import_user_info.auto_line
     */
    private String autoLine;

    /**
     * 发动机排量
     * @Table import_user_info.engine_disp
     */
    private String engineDisp;

    /**
     * 首次生产年份
     * @Table import_user_info.product_year
     */
    private String productYear;

    /**
     * 车架号
     * @Table import_user_info.che_jia
     */
    private String cheJia;

    /**
     * 发动机号
     * @Table import_user_info.engine_code
     */
    private String engineCode;

    /**
     * 保单号
     * @Table import_user_info.bao_dan
     */
    private String baoDan;

    /**
     * 业务来源
     * @Table import_user_info.source
     */
    private String source;

    /**
     * 导入的用户车辆里程数
     * @Table import_user_info.mileage
     */
    private Integer mileage;

    /**
     * 小保养次数
     * @Table import_user_info.baoyang_times
     */
    private Integer baoyangTimes;

    /**
     * 轮胎次数
     * @Table import_user_info.luntai_times
     */
    private Integer luntaiTimes;

    /**
     * 电瓶次数
     * @Table import_user_info.dianping_times
     */
    private Integer dianpingTimes;

    /**
     * 喷漆次数
     * @Table import_user_info.penqi_times
     */
    private Integer penqiTimes;

    /**
     * 洗车次数
     * @Table import_user_info.xieche_times
     */
    private Integer xiecheTimes;

    /**
     * 赠送的优惠券总张数
     * @Table import_user_info.total_coupon_value
     */
    private String totalCouponValue;

    /**
     * 剩余优惠券张数
     * @Table import_user_info.surplus_coupon_value
     */
    private String surplusCouponValue;

    /**
     * 绑定的维修店
     * @Table import_user_info.seller
     */
    private String seller;

    /**
     * 客户(保单)地址
     * @Table import_user_info.address
     */
    private String address;

    /**
     * 保单开始时间
     * @Table import_user_info.start_time
     */
    private Date startTime;

    /**
     * 保单结束时间
     * @Table import_user_info.end_time
     */
    private Date endTime;

    /**
     * 收付日期
     * @Table import_user_info.payment_time
     */
    private Date paymentTime;

    /**
     * 签单日期
     * @Table import_user_info.sign_time
     */
    private Date signTime;

    /**
     * 车辆价格
     * @Table import_user_info.price
     */
    private BigDecimal price;

    /**
     * 保费收入
     * @Table import_user_info.order_price
     */
    private BigDecimal orderPrice;

    /**
     * 手续费折扣
     * @Table import_user_info.discount
     */
    private Integer discount;

    /**
     * 使用类型
     * @Table import_user_info.use_type
     */
    private String useType;

    /**
     * 代理人
     * @Table import_user_info.agent
     */
    private String agent;

    /**
     * 金嘉护超净 10W-40 serviceValue
     */
    public static final java.lang.Short SERVICE_VALUE_1 = 1;
    /**
     * 新磁护 5W-40 serviceValue
     */
    public static final java.lang.Short SERVICE_VALUE_2 = 2;
    /**
     * 极护 5W-40 serviceValue
     */
    public static final java.lang.Short SERVICE_VALUE_3 = 3;
    /**
     * 服务包等级（在导入保单的时候就确定好等级）：1，金嘉护超净 10W-40，2，新磁护 5W-40，3，极护 5W-40
     * @Table import_user_info.service_value
     */
    private Short serviceValue;

    /**
     * 
     * @Table import_user_info.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table import_user_info.update_time
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
    * 获取 真实姓名
    * @return realName
    */
    public String getRealName() {
        return realName;
    }

    /**
    * 设置 真实姓名
    * @param realName
    */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
    * 获取 手机号码
    * @return phone
    */
    public String getPhone() {
        return phone;
    }

    /**
    * 设置 手机号码
    * @param phone
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
    * 获取 车牌号
    * @return chePai
    */
    public String getChePai() {
        return chePai;
    }

    /**
    * 设置 车牌号
    * @param chePai
    */
    public void setChePai(String chePai) {
        this.chePai = chePai;
    }

    /**
    * 获取 汽车品牌
    * @return autoBrand
    */
    public String getAutoBrand() {
        return autoBrand;
    }

    /**
    * 设置 汽车品牌
    * @param autoBrand
    */
    public void setAutoBrand(String autoBrand) {
        this.autoBrand = autoBrand;
    }

    /**
    * 获取 生产厂商
    * @return carFactory
    */
    public String getCarFactory() {
        return carFactory;
    }

    /**
    * 设置 生产厂商
    * @param carFactory
    */
    public void setCarFactory(String carFactory) {
        this.carFactory = carFactory;
    }

    /**
    * 获取 车系
    * @return autoType
    */
    public String getAutoType() {
        return autoType;
    }

    /**
    * 设置 车系
    * @param autoType
    */
    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }

    /**
    * 获取 车型
    * @return autoLine
    */
    public String getAutoLine() {
        return autoLine;
    }

    /**
    * 设置 车型
    * @param autoLine
    */
    public void setAutoLine(String autoLine) {
        this.autoLine = autoLine;
    }

    /**
    * 获取 发动机排量
    * @return engineDisp
    */
    public String getEngineDisp() {
        return engineDisp;
    }

    /**
    * 设置 发动机排量
    * @param engineDisp
    */
    public void setEngineDisp(String engineDisp) {
        this.engineDisp = engineDisp;
    }

    /**
    * 获取 首次生产年份
    * @return productYear
    */
    public String getProductYear() {
        return productYear;
    }

    /**
    * 设置 首次生产年份
    * @param productYear
    */
    public void setProductYear(String productYear) {
        this.productYear = productYear;
    }

    /**
    * 获取 车架号
    * @return cheJia
    */
    public String getCheJia() {
        return cheJia;
    }

    /**
    * 设置 车架号
    * @param cheJia
    */
    public void setCheJia(String cheJia) {
        this.cheJia = cheJia;
    }

    /**
    * 获取 发动机号
    * @return engineCode
    */
    public String getEngineCode() {
        return engineCode;
    }

    /**
    * 设置 发动机号
    * @param engineCode
    */
    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    /**
    * 获取 保单号
    * @return baoDan
    */
    public String getBaoDan() {
        return baoDan;
    }

    /**
    * 设置 保单号
    * @param baoDan
    */
    public void setBaoDan(String baoDan) {
        this.baoDan = baoDan;
    }

    /**
    * 获取 业务来源
    * @return source
    */
    public String getSource() {
        return source;
    }

    /**
    * 设置 业务来源
    * @param source
    */
    public void setSource(String source) {
        this.source = source;
    }

    /**
    * 获取 导入的用户车辆里程数
    * @return mileage
    */
    public Integer getMileage() {
        return mileage;
    }

    /**
    * 设置 导入的用户车辆里程数
    * @param mileage
    */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
    * 获取 小保养次数
    * @return baoyangTimes
    */
    public Integer getBaoyangTimes() {
        return baoyangTimes;
    }

    /**
    * 设置 小保养次数
    * @param baoyangTimes
    */
    public void setBaoyangTimes(Integer baoyangTimes) {
        this.baoyangTimes = baoyangTimes;
    }

    /**
    * 获取 轮胎次数
    * @return luntaiTimes
    */
    public Integer getLuntaiTimes() {
        return luntaiTimes;
    }

    /**
    * 设置 轮胎次数
    * @param luntaiTimes
    */
    public void setLuntaiTimes(Integer luntaiTimes) {
        this.luntaiTimes = luntaiTimes;
    }

    /**
    * 获取 电瓶次数
    * @return dianpingTimes
    */
    public Integer getDianpingTimes() {
        return dianpingTimes;
    }

    /**
    * 设置 电瓶次数
    * @param dianpingTimes
    */
    public void setDianpingTimes(Integer dianpingTimes) {
        this.dianpingTimes = dianpingTimes;
    }

    /**
    * 获取 喷漆次数
    * @return penqiTimes
    */
    public Integer getPenqiTimes() {
        return penqiTimes;
    }

    /**
    * 设置 喷漆次数
    * @param penqiTimes
    */
    public void setPenqiTimes(Integer penqiTimes) {
        this.penqiTimes = penqiTimes;
    }

    /**
    * 获取 洗车次数
    * @return xiecheTimes
    */
    public Integer getXiecheTimes() {
        return xiecheTimes;
    }

    /**
    * 设置 洗车次数
    * @param xiecheTimes
    */
    public void setXiecheTimes(Integer xiecheTimes) {
        this.xiecheTimes = xiecheTimes;
    }

    /**
    * 获取 赠送的优惠券总张数
    * @return totalCouponValue
    */
    public String getTotalCouponValue() {
        return totalCouponValue;
    }

    /**
    * 设置 赠送的优惠券总张数
    * @param totalCouponValue
    */
    public void setTotalCouponValue(String totalCouponValue) {
        this.totalCouponValue = totalCouponValue;
    }

    /**
    * 获取 剩余优惠券张数
    * @return surplusCouponValue
    */
    public String getSurplusCouponValue() {
        return surplusCouponValue;
    }

    /**
    * 设置 剩余优惠券张数
    * @param surplusCouponValue
    */
    public void setSurplusCouponValue(String surplusCouponValue) {
        this.surplusCouponValue = surplusCouponValue;
    }

    /**
    * 获取 绑定的维修店
    * @return seller
    */
    public String getSeller() {
        return seller;
    }

    /**
    * 设置 绑定的维修店
    * @param seller
    */
    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
    * 获取 客户(保单)地址
    * @return address
    */
    public String getAddress() {
        return address;
    }

    /**
    * 设置 客户(保单)地址
    * @param address
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
    * 获取 保单开始时间
    * @return startTime
    */
    public Date getStartTime() {
        return startTime;
    }

    /**
    * 设置 保单开始时间
    * @param startTime
    */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
    * 获取 保单结束时间
    * @return endTime
    */
    public Date getEndTime() {
        return endTime;
    }

    /**
    * 设置 保单结束时间
    * @param endTime
    */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    * 获取 车辆价格
    * @return price
    */
    public BigDecimal getPrice() {
        return price;
    }

    /**
    * 设置 车辆价格
    * @param price
    */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
    * 获取 保费收入
    * @return orderPrice
    */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
    * 设置 保费收入
    * @param orderPrice
    */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
    * 获取 手续费折扣
    * @return discount
    */
    public Integer getDiscount() {
        return discount;
    }

    /**
    * 设置 手续费折扣
    * @param discount
    */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
    * 获取 使用类型
    * @return useType
    */
    public String getUseType() {
        return useType;
    }

    /**
    * 设置 使用类型
    * @param useType
    */
    public void setUseType(String useType) {
        this.useType = useType;
    }

    /**
    * 获取 代理人
    * @return agent
    */
    public String getAgent() {
        return agent;
    }

    /**
    * 设置 代理人
    * @param agent
    */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
    * 获取 服务包等级（在导入保单的时候就确定好等级）：1，金嘉护超净 10W-40，2，新磁护 5W-40，3，极护 5W-40
    * @return serviceValue
    */
    public Short getServiceValue() {
        return serviceValue;
    }

    /**
    * 设置 服务包等级（在导入保单的时候就确定好等级）：1，金嘉护超净 10W-40，2，新磁护 5W-40，3，极护 5W-40
    * @param serviceValue
    */
    public void setServiceValue(Short serviceValue) {
        this.serviceValue = serviceValue;
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