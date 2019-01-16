package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class Seller {
    /**
     * 
     * @Table seller.id
     */
    private Long id;

    /**
     * 用户名
     * @Table seller.name
     */
    private String name;

    /**
     * 店铺名称
     * @Table seller.seller_name
     */
    private String sellerName;

    /**
     * 公司名称
     * @Table seller.company_name
     */
    private String companyName;

    /**
     * 店铺联系方式
     * @Table seller.seller_phone
     */
    private String sellerPhone;

    /**
     * 省份
     * @Table seller.province
     */
    private String province;

    /**
     * 城市
     * @Table seller.city
     */
    private String city;

    /**
     * 区域
     * @Table seller.area
     */
    private String area;

    /**
     * 店铺地址
     * @Table seller.address_detail
     */
    private String addressDetail;

    /**
     * 店铺logo
     * @Table seller.seller_logo
     */
    private String sellerLogo;

    /**
     * 其他 sellerGrade
     */
    public static final java.lang.Integer SELLER_GRADE_0 = 0;
    /**
     * 快修店 sellerGrade
     */
    public static final java.lang.Integer SELLER_GRADE_1 = 1;
    /**
     * 4s店 sellerGrade
     */
    public static final java.lang.Integer SELLER_GRADE_2 = 2;
    /**
     * 修理厂 sellerGrade
     */
    public static final java.lang.Integer SELLER_GRADE_3 = 3;
    /**
     * 店铺类型：0，其他，1，快修店，2，4s店，3，修理厂
     * @Table seller.seller_grade
     */
    private Integer sellerGrade;

    /**
     * 代销 settleType
     */
    public static final java.lang.Integer SETTLE_TYPE_0 = 0;
    /**
     * 直结 settleType
     */
    public static final java.lang.Integer SETTLE_TYPE_1 = 1;
    /**
     * 结算方式：0，代销，1，直结
     * @Table seller.settle_type
     */
    private Integer settleType;

    /**
     * 店铺评分服务
     * @Table seller.score_service
     */
    private String scoreService;

    /**
     * 小保养 properties
     */
    public static final java.lang.Short PROPERTIES_1 = 1;
    /**
     * 洗车 properties
     */
    public static final java.lang.Short PROPERTIES_2 = 2;
    /**
     * 钣金 properties
     */
    public static final java.lang.Short PROPERTIES_4 = 4;
    /**
     * 换轮胎 properties
     */
    public static final java.lang.Short PROPERTIES_8 = 8;
    /**
     * 喷漆 properties
     */
    public static final java.lang.Short PROPERTIES_16 = 16;
    /**
     * 店铺属性：1，小保养，2，洗车，4，钣金，8，换轮胎，16，喷漆
     * @Table seller.properties
     */
    private Short properties;

    /**
     * 四S店的喷漆服务品牌
     * @Table seller.car_brand
     */
    private String carBrand;

    /**
     * 店铺评分描述
     * @Table seller.score_description
     */
    private String scoreDescription;

    /**
     * 商品数量
     * @Table seller.product_number
     */
    private Integer productNumber;

    /**
     * 店铺收藏
     * @Table seller.collection_number
     */
    private Integer collectionNumber;

    /**
     * 店铺总销售金额
     * @Table seller.sale_money
     */
    private BigDecimal saleMoney;

    /**
     * 店铺维修订单数量
     * @Table seller.order_count
     */
    private Integer orderCount;

    /**
     * 店铺完成订单量
     * @Table seller.order_count_over
     */
    private Integer orderCountOver;

    /**
     * 营业时间
     * @Table seller.open_time
     */
    private String openTime;

    /**
     * SEO店铺描述
     * @Table seller.seller_des
     */
    private String sellerDes;

    /**
     * 待审核 auditStatus
     */
    public static final java.lang.Integer AUDIT_STATUS_1 = 1;
    /**
     * 审核通过 auditStatus
     */
    public static final java.lang.Integer AUDIT_STATUS_2 = 2;
    /**
     * 冻结 auditStatus
     */
    public static final java.lang.Integer AUDIT_STATUS_3 = 3;
    /**
     * 审核状态：1，待审核，2，审核通过，3，冻结
     * @Table seller.audit_status
     */
    private Integer auditStatus;

    /**
     * 商家服务费
     * @Table seller.service_price
     */
    private BigDecimal servicePrice;

    /**
     * 商家地图坐标
     * @Table seller.seller_postion
     */
    private String sellerPostion;

    /**
     * 创建时间
     * @Table seller.create_time
     */
    private Date createTime;

    /**
     * 马牌门店 joinType
     */
    public static final java.lang.Integer JOIN_TYPE_0 = 0;
    /**
     * 国寿 joinType
     */
    public static final java.lang.Integer JOIN_TYPE_1 = 1;
    /**
     * 陌拜用户 joinType
     */
    public static final java.lang.Integer JOIN_TYPE_2 = 2;
    /**
     * 加盟类型：0，马牌门店，1，国寿，2，陌拜用户
     * @Table seller.join_type
     */
    private Integer joinType;

    /**
     * 商家排序
     * @Table seller.seller_order
     */
    private Integer sellerOrder;

    /**
     * 商家保养策略
     * @Table seller.seller_policy
     */
    private String sellerPolicy;

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
    * 获取 用户名
    * @return name
    */
    public String getName() {
        return name;
    }

    /**
    * 设置 用户名
    * @param name
    */
    public void setName(String name) {
        this.name = name;
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
    * 获取 公司名称
    * @return companyName
    */
    public String getCompanyName() {
        return companyName;
    }

    /**
    * 设置 公司名称
    * @param companyName
    */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
    * 获取 店铺联系方式
    * @return sellerPhone
    */
    public String getSellerPhone() {
        return sellerPhone;
    }

    /**
    * 设置 店铺联系方式
    * @param sellerPhone
    */
    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
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
    * 获取 店铺地址
    * @return addressDetail
    */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
    * 设置 店铺地址
    * @param addressDetail
    */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
    * 获取 店铺logo
    * @return sellerLogo
    */
    public String getSellerLogo() {
        return sellerLogo;
    }

    /**
    * 设置 店铺logo
    * @param sellerLogo
    */
    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    /**
    * 获取 店铺类型：0，其他，1，快修店，2，4s店，3，修理厂
    * @return sellerGrade
    */
    public Integer getSellerGrade() {
        return sellerGrade;
    }

    /**
    * 设置 店铺类型：0，其他，1，快修店，2，4s店，3，修理厂
    * @param sellerGrade
    */
    public void setSellerGrade(Integer sellerGrade) {
        this.sellerGrade = sellerGrade;
    }

    /**
    * 获取 结算方式：0，代销，1，直结
    * @return settleType
    */
    public Integer getSettleType() {
        return settleType;
    }

    /**
    * 设置 结算方式：0，代销，1，直结
    * @param settleType
    */
    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    /**
    * 获取 店铺评分服务
    * @return scoreService
    */
    public String getScoreService() {
        return scoreService;
    }

    /**
    * 设置 店铺评分服务
    * @param scoreService
    */
    public void setScoreService(String scoreService) {
        this.scoreService = scoreService;
    }

    /**
    * 获取 店铺属性：1，小保养，2，洗车，4，钣金，8，换轮胎，16，喷漆
    * @return properties
    */
    public Short getProperties() {
        return properties;
    }

    /**
    * 设置 店铺属性：1，小保养，2，洗车，4，钣金，8，换轮胎，16，喷漆
    * @param properties
    */
    public void setProperties(Short properties) {
        this.properties = properties;
    }

    /**
    * 获取 四S店的喷漆服务品牌
    * @return carBrand
    */
    public String getCarBrand() {
        return carBrand;
    }

    /**
    * 设置 四S店的喷漆服务品牌
    * @param carBrand
    */
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    /**
    * 获取 店铺评分描述
    * @return scoreDescription
    */
    public String getScoreDescription() {
        return scoreDescription;
    }

    /**
    * 设置 店铺评分描述
    * @param scoreDescription
    */
    public void setScoreDescription(String scoreDescription) {
        this.scoreDescription = scoreDescription;
    }

    /**
    * 获取 商品数量
    * @return productNumber
    */
    public Integer getProductNumber() {
        return productNumber;
    }

    /**
    * 设置 商品数量
    * @param productNumber
    */
    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    /**
    * 获取 店铺收藏
    * @return collectionNumber
    */
    public Integer getCollectionNumber() {
        return collectionNumber;
    }

    /**
    * 设置 店铺收藏
    * @param collectionNumber
    */
    public void setCollectionNumber(Integer collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    /**
    * 获取 店铺总销售金额
    * @return saleMoney
    */
    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    /**
    * 设置 店铺总销售金额
    * @param saleMoney
    */
    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    /**
    * 获取 店铺维修订单数量
    * @return orderCount
    */
    public Integer getOrderCount() {
        return orderCount;
    }

    /**
    * 设置 店铺维修订单数量
    * @param orderCount
    */
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    /**
    * 获取 店铺完成订单量
    * @return orderCountOver
    */
    public Integer getOrderCountOver() {
        return orderCountOver;
    }

    /**
    * 设置 店铺完成订单量
    * @param orderCountOver
    */
    public void setOrderCountOver(Integer orderCountOver) {
        this.orderCountOver = orderCountOver;
    }

    /**
    * 获取 营业时间
    * @return openTime
    */
    public String getOpenTime() {
        return openTime;
    }

    /**
    * 设置 营业时间
    * @param openTime
    */
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    /**
    * 获取 SEO店铺描述
    * @return sellerDes
    */
    public String getSellerDes() {
        return sellerDes;
    }

    /**
    * 设置 SEO店铺描述
    * @param sellerDes
    */
    public void setSellerDes(String sellerDes) {
        this.sellerDes = sellerDes;
    }

    /**
    * 获取 审核状态：1，待审核，2，审核通过，3，冻结
    * @return auditStatus
    */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
    * 设置 审核状态：1，待审核，2，审核通过，3，冻结
    * @param auditStatus
    */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
    * 获取 商家服务费
    * @return servicePrice
    */
    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    /**
    * 设置 商家服务费
    * @param servicePrice
    */
    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    /**
    * 获取 商家地图坐标
    * @return sellerPostion
    */
    public String getSellerPostion() {
        return sellerPostion;
    }

    /**
    * 设置 商家地图坐标
    * @param sellerPostion
    */
    public void setSellerPostion(String sellerPostion) {
        this.sellerPostion = sellerPostion;
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
    * 获取 加盟类型：0，马牌门店，1，国寿，2，陌拜用户
    * @return joinType
    */
    public Integer getJoinType() {
        return joinType;
    }

    /**
    * 设置 加盟类型：0，马牌门店，1，国寿，2，陌拜用户
    * @param joinType
    */
    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    /**
    * 获取 商家排序
    * @return sellerOrder
    */
    public Integer getSellerOrder() {
        return sellerOrder;
    }

    /**
    * 设置 商家排序
    * @param sellerOrder
    */
    public void setSellerOrder(Integer sellerOrder) {
        this.sellerOrder = sellerOrder;
    }

    /**
    * 获取 商家保养策略
    * @return sellerPolicy
    */
    public String getSellerPolicy() {
        return sellerPolicy;
    }

    /**
    * 设置 商家保养策略
    * @param sellerPolicy
    */
    public void setSellerPolicy(String sellerPolicy) {
        this.sellerPolicy = sellerPolicy;
    }
}