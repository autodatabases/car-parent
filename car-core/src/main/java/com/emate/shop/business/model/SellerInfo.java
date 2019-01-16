package com.emate.shop.business.model;

import java.util.Date;

public class SellerInfo {
    /**
     * 
     * @Table seller_info.id
     */
    private Long id;

    /**
     * 商家id
     * @Table seller_info.seller_id
     */
    private Long sellerId;

    /**
     * 店铺名称
     * @Table seller_info.seller_name
     */
    private String sellerName;

    /**
     * 营业执照编号
     * @Table seller_info.yinyye_code
     */
    private String yinyyeCode;

    /**
     * 纳税人识别代码
     * @Table seller_info.taxes_code
     */
    private String taxesCode;

    /**
     * 注册地址
     * @Table seller_info.register_addr
     */
    private String registerAddr;

    /**
     * 注册电话
     * @Table seller_info.register_phone
     */
    private String registerPhone;

    /**
     * 开户名称
     * @Table seller_info.account_name
     */
    private String accountName;

    /**
     * 开户行
     * @Table seller_info.account
     */
    private String account;

    /**
     * 开户行账号
     * @Table seller_info.account_number
     */
    private String accountNumber;

    /**
     * 营业执照副本
     * @Table seller_info.business_licence
     */
    private String businessLicence;

    /**
     * 税务登记副本
     * @Table seller_info.tax_registration
     */
    private String taxRegistration;

    /**
     * 收货负责人
     * @Table seller_info.shouhuo
     */
    private String shouhuo;

    /**
     * 结算负责人
     * @Table seller_info.jiesuan
     */
    private String jiesuan;

    /**
     * 对接负责人
     * @Table seller_info.duijie
     */
    private String duijie;

    /**
     * 保险代理人
     * @Table seller_info.baoxian
     */
    private String baoxian;

    /**
     * 微信号
     * @Table seller_info.weixin
     */
    private String weixin;

    /**
     * 营业执照公司全称
     * @Table seller_info.full_name
     */
    private String fullName;

    /**
     * 维修资质
     * @Table seller_info.wx_zizhi
     */
    private String wxZizhi;

    /**
     * 服务场地面积
     * @Table seller_info.seller_area
     */
    private String sellerArea;

    /**
     * 工位信息
     * @Table seller_info.workbay
     */
    private Short workbay;

    /**
     * 员工数量
     * @Table seller_info.employee
     */
    private Integer employee;

    /**
     * 区域经理
     * @Table seller_info.area_manager
     */
    private String areaManager;

    /**
     * 网店照片
     * @Table seller_info.shop_pic
     */
    private String shopPic;

    /**
     * 是否有网络
     * @Table seller_info.has_network
     */
    private Boolean hasNetwork;

    /**
     * 推荐渠道
     * @Table seller_info.channel
     */
    private String channel;

    /**
     * 服务范围
     * @Table seller_info.service
     */
    private String service;

    /**
     * 待审核 status
     */
    public static final java.lang.Byte STATUS_0 = 0;
    /**
     * 审核通过 status
     */
    public static final java.lang.Byte STATUS_1 = 1;
    /**
     * 审核不通过 status
     */
    public static final java.lang.Byte STATUS_2 = 2;
    /**
     * 审核状态：0，待审核，1，审核通过，2，审核不通过
     * @Table seller_info.status
     */
    private Byte status;

    /**
     * 
     * @Table seller_info.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table seller_info.update_time
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
    * 获取 商家id
    * @return sellerId
    */
    public Long getSellerId() {
        return sellerId;
    }

    /**
    * 设置 商家id
    * @param sellerId
    */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
    * 获取 店铺名称
    * @return sellerName
    */
    public String getSellerName() {
        return sellerName;
    }

    /**
    * 设置 店铺名称
    * @param sellerName
    */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
    * 获取 营业执照编号
    * @return yinyyeCode
    */
    public String getYinyyeCode() {
        return yinyyeCode;
    }

    /**
    * 设置 营业执照编号
    * @param yinyyeCode
    */
    public void setYinyyeCode(String yinyyeCode) {
        this.yinyyeCode = yinyyeCode;
    }

    /**
    * 获取 纳税人识别代码
    * @return taxesCode
    */
    public String getTaxesCode() {
        return taxesCode;
    }

    /**
    * 设置 纳税人识别代码
    * @param taxesCode
    */
    public void setTaxesCode(String taxesCode) {
        this.taxesCode = taxesCode;
    }

    /**
    * 获取 注册地址
    * @return registerAddr
    */
    public String getRegisterAddr() {
        return registerAddr;
    }

    /**
    * 设置 注册地址
    * @param registerAddr
    */
    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    /**
    * 获取 注册电话
    * @return registerPhone
    */
    public String getRegisterPhone() {
        return registerPhone;
    }

    /**
    * 设置 注册电话
    * @param registerPhone
    */
    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    /**
    * 获取 开户名称
    * @return accountName
    */
    public String getAccountName() {
        return accountName;
    }

    /**
    * 设置 开户名称
    * @param accountName
    */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
    * 获取 开户行
    * @return account
    */
    public String getAccount() {
        return account;
    }

    /**
    * 设置 开户行
    * @param account
    */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
    * 获取 开户行账号
    * @return accountNumber
    */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
    * 设置 开户行账号
    * @param accountNumber
    */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
    * 获取 营业执照副本
    * @return businessLicence
    */
    public String getBusinessLicence() {
        return businessLicence;
    }

    /**
    * 设置 营业执照副本
    * @param businessLicence
    */
    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    /**
    * 获取 税务登记副本
    * @return taxRegistration
    */
    public String getTaxRegistration() {
        return taxRegistration;
    }

    /**
    * 设置 税务登记副本
    * @param taxRegistration
    */
    public void setTaxRegistration(String taxRegistration) {
        this.taxRegistration = taxRegistration;
    }

    /**
    * 获取 收货负责人
    * @return shouhuo
    */
    public String getShouhuo() {
        return shouhuo;
    }

    /**
    * 设置 收货负责人
    * @param shouhuo
    */
    public void setShouhuo(String shouhuo) {
        this.shouhuo = shouhuo;
    }

    /**
    * 获取 结算负责人
    * @return jiesuan
    */
    public String getJiesuan() {
        return jiesuan;
    }

    /**
    * 设置 结算负责人
    * @param jiesuan
    */
    public void setJiesuan(String jiesuan) {
        this.jiesuan = jiesuan;
    }

    /**
    * 获取 对接负责人
    * @return duijie
    */
    public String getDuijie() {
        return duijie;
    }

    /**
    * 设置 对接负责人
    * @param duijie
    */
    public void setDuijie(String duijie) {
        this.duijie = duijie;
    }

    /**
    * 获取 保险代理人
    * @return baoxian
    */
    public String getBaoxian() {
        return baoxian;
    }

    /**
    * 设置 保险代理人
    * @param baoxian
    */
    public void setBaoxian(String baoxian) {
        this.baoxian = baoxian;
    }

    /**
    * 获取 微信号
    * @return weixin
    */
    public String getWeixin() {
        return weixin;
    }

    /**
    * 设置 微信号
    * @param weixin
    */
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    /**
    * 获取 营业执照公司全称
    * @return fullName
    */
    public String getFullName() {
        return fullName;
    }

    /**
    * 设置 营业执照公司全称
    * @param fullName
    */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
    * 获取 维修资质
    * @return wxZizhi
    */
    public String getWxZizhi() {
        return wxZizhi;
    }

    /**
    * 设置 维修资质
    * @param wxZizhi
    */
    public void setWxZizhi(String wxZizhi) {
        this.wxZizhi = wxZizhi;
    }

    /**
    * 获取 服务场地面积
    * @return sellerArea
    */
    public String getSellerArea() {
        return sellerArea;
    }

    /**
    * 设置 服务场地面积
    * @param sellerArea
    */
    public void setSellerArea(String sellerArea) {
        this.sellerArea = sellerArea;
    }

    /**
    * 获取 工位信息
    * @return workbay
    */
    public Short getWorkbay() {
        return workbay;
    }

    /**
    * 设置 工位信息
    * @param workbay
    */
    public void setWorkbay(Short workbay) {
        this.workbay = workbay;
    }

    /**
    * 获取 员工数量
    * @return employee
    */
    public Integer getEmployee() {
        return employee;
    }

    /**
    * 设置 员工数量
    * @param employee
    */
    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    /**
    * 获取 区域经理
    * @return areaManager
    */
    public String getAreaManager() {
        return areaManager;
    }

    /**
    * 设置 区域经理
    * @param areaManager
    */
    public void setAreaManager(String areaManager) {
        this.areaManager = areaManager;
    }

    /**
    * 获取 网店照片
    * @return shopPic
    */
    public String getShopPic() {
        return shopPic;
    }

    /**
    * 设置 网店照片
    * @param shopPic
    */
    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    /**
    * 获取 是否有网络
    * @return hasNetwork
    */
    public Boolean getHasNetwork() {
        return hasNetwork;
    }

    /**
    * 设置 是否有网络
    * @param hasNetwork
    */
    public void setHasNetwork(Boolean hasNetwork) {
        this.hasNetwork = hasNetwork;
    }

    /**
    * 获取 推荐渠道
    * @return channel
    */
    public String getChannel() {
        return channel;
    }

    /**
    * 设置 推荐渠道
    * @param channel
    */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
    * 获取 服务范围
    * @return service
    */
    public String getService() {
        return service;
    }

    /**
    * 设置 服务范围
    * @param service
    */
    public void setService(String service) {
        this.service = service;
    }

    /**
    * 获取 审核状态：0，待审核，1，审核通过，2，审核不通过
    * @return status
    */
    public Byte getStatus() {
        return status;
    }

    /**
    * 设置 审核状态：0，待审核，1，审核通过，2，审核不通过
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