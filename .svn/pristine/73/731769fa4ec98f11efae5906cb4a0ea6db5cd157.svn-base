package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class Orders {
    /**
     * 
     * @Table orders.id
     */
    private Long id;

    /**
     * 订单流水号
     * @Table orders.order_no
     */
    private String orderNo;

    /**
     * 电源 orderType
     */
    public static final java.lang.Byte ORDER_TYPE_0 = 0;
    /**
     * 轮胎 orderType
     */
    public static final java.lang.Byte ORDER_TYPE_1 = 1;
    /**
     * 小保养 orderType
     */
    public static final java.lang.Byte ORDER_TYPE_2 = 2;
    /**
     * 洗车 orderType
     */
    public static final java.lang.Byte ORDER_TYPE_3 = 3;
    /**
     * 喷漆 orderType
     */
    public static final java.lang.Byte ORDER_TYPE_4 = 4;
    /**
     * 其他 orderType
     */
    public static final java.lang.Byte ORDER_TYPE_5 = 5;
    /**
     * 订单类型：0，电源，1，轮胎，2，小保养，3，洗车，4，喷漆，5，其他
     * @Table orders.order_type
     */
    private Byte orderType;

    /**
     * 用户id
     * @Table orders.user_id
     */
    private Long userId;

    /**
     * 会员名称
     * @Table orders.user_name
     */
    private String userName;

    /**
     * 卖家ID
     * @Table orders.seller_id
     */
    private Long sellerId;

    /**
     * 待确认 status
     */
    public static final java.lang.Integer STATUS_0 = 0;
    /**
     * 待服务 status
     */
    public static final java.lang.Integer STATUS_1 = 1;
    /**
     * 服务中 status
     */
    public static final java.lang.Integer STATUS_2 = 2;
    /**
     * 已完成 status
     */
    public static final java.lang.Integer STATUS_3 = 3;
    /**
     * 已取消 status
     */
    public static final java.lang.Integer STATUS_4 = 4;
    /**
     * 已结算 status
     */
    public static final java.lang.Integer STATUS_5 = 5;
    /**
     * 状态：0，待确认，1，待服务，2，服务中，3，已完成，4，已取消，5，已结算
     * @Table orders.status
     */
    private Integer status;

    /**
     * 订单总现金价格
     * @Table orders.money_amount
     */
    private BigDecimal moneyAmount;

    /**
     * 快递费用
     * @Table orders.express_amount
     */
    private BigDecimal expressAmount;

    /**
     * 商家服务完
     * @Table orders.service_money
     */
    private BigDecimal serviceMoney;

    /**
     * 下单IP
     * @Table orders.create_ip
     */
    private String createIp;

    /**
     * 会员收货地址设置ID
     * @Table orders.phone
     */
    private String phone;

    /**
     * 订单备注
     * @Table orders.order_remark
     */
    private String orderRemark;

    /**
     * 发货时间
     * @Table orders.deliver_time
     */
    private Date deliverTime;

    /**
     * 完成时间
     * @Table orders.finish_time
     */
    private Date finishTime;

    /**
     * 天猫 source
     */
    public static final java.lang.Integer SOURCE_0 = 0;
    /**
     * 京东 source
     */
    public static final java.lang.Integer SOURCE_1 = 1;
    /**
     * 订单来源：0，天猫，1，京东
     * @Table orders.source
     */
    private Integer source;

    /**
     * 自己发货 supplyId
     */
    public static final java.lang.Long SUPPLY_ID_0 = 0l;
    /**
     * 商家发货 supplyId
     */
    public static final java.lang.Long SUPPLY_ID_1 = 1l;
    /**
     * 供货商：0，自己发货，1，商家发货
     * @Table orders.supply_id
     */
    private Long supplyId;

    /**
     * 货品id
     * @Table orders.goods_id
     */
    private String goodsId;

    /**
     * 货品数量,机油单位为L，机滤单位为个，电瓶单位为1个
     * @Table orders.goods_num
     */
    private Integer goodsNum;

    /**
     * 供货商名字
     * @Table orders.supply_name
     */
    private String supplyName;

    /**
     * 省份
     * @Table orders.province
     */
    private String province;

    /**
     * 城市
     * @Table orders.city
     */
    private String city;

    /**
     * 区域
     * @Table orders.area
     */
    private String area;

    /**
     * 上门地址
     * @Table orders.address_detail
     */
    private String addressDetail;

    /**
     * 订单券码，用于商家验券
     * @Table orders.code
     */
    private String code;

    /**
     * 公里数
     * @Table orders.mileage
     */
    private Integer mileage;

    /**
     * 车辆价格
     * @Table orders.car_price
     */
    private BigDecimal carPrice;

    /**
     * 运营审核备注
     * @Table orders.operate_remark
     */
    private String operateRemark;

    /**
     * 
     * @Table orders.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table orders.update_time
     */
    private Date updateTime;

    /**
     * 商家价格
     * @Table orders.jilv_price
     */
    private BigDecimal jilvPrice;

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
    * 获取 订单流水号
    * @return orderNo
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    * 设置 订单流水号
    * @param orderNo
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    * 获取 订单类型：0，电源，1，轮胎，2，小保养，3，洗车，4，喷漆，5，其他
    * @return orderType
    */
    public Byte getOrderType() {
        return orderType;
    }

    /**
    * 设置 订单类型：0，电源，1，轮胎，2，小保养，3，洗车，4，喷漆，5，其他
    * @param orderType
    */
    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    /**
    * 获取 用户id
    * @return userId
    */
    public Long getUserId() {
        return userId;
    }

    /**
    * 设置 用户id
    * @param userId
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    * 获取 会员名称
    * @return userName
    */
    public String getUserName() {
        return userName;
    }

    /**
    * 设置 会员名称
    * @param userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
    * 获取 卖家ID
    * @return sellerId
    */
    public Long getSellerId() {
        return sellerId;
    }

    /**
    * 设置 卖家ID
    * @param sellerId
    */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
    * 获取 状态：0，待确认，1，待服务，2，服务中，3，已完成，4，已取消，5，已结算
    * @return status
    */
    public Integer getStatus() {
        return status;
    }

    /**
    * 设置 状态：0，待确认，1，待服务，2，服务中，3，已完成，4，已取消，5，已结算
    * @param status
    */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 获取 订单总现金价格
    * @return moneyAmount
    */
    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    /**
    * 设置 订单总现金价格
    * @param moneyAmount
    */
    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    /**
    * 获取 快递费用
    * @return expressAmount
    */
    public BigDecimal getExpressAmount() {
        return expressAmount;
    }

    /**
    * 设置 快递费用
    * @param expressAmount
    */
    public void setExpressAmount(BigDecimal expressAmount) {
        this.expressAmount = expressAmount;
    }

    /**
    * 获取 商家服务完
    * @return serviceMoney
    */
    public BigDecimal getServiceMoney() {
        return serviceMoney;
    }

    /**
    * 设置 商家服务完
    * @param serviceMoney
    */
    public void setServiceMoney(BigDecimal serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    /**
    * 获取 下单IP
    * @return createIp
    */
    public String getCreateIp() {
        return createIp;
    }

    /**
    * 设置 下单IP
    * @param createIp
    */
    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    /**
    * 获取 会员收货地址设置ID
    * @return phone
    */
    public String getPhone() {
        return phone;
    }

    /**
    * 设置 会员收货地址设置ID
    * @param phone
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
    * 获取 订单备注
    * @return orderRemark
    */
    public String getOrderRemark() {
        return orderRemark;
    }

    /**
    * 设置 订单备注
    * @param orderRemark
    */
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    /**
    * 获取 发货时间
    * @return deliverTime
    */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
    * 设置 发货时间
    * @param deliverTime
    */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
    * 获取 完成时间
    * @return finishTime
    */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
    * 设置 完成时间
    * @param finishTime
    */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
    * 获取 订单来源：0，天猫，1，京东
    * @return source
    */
    public Integer getSource() {
        return source;
    }

    /**
    * 设置 订单来源：0，天猫，1，京东
    * @param source
    */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
    * 获取 供货商：0，自己发货，1，商家发货
    * @return supplyId
    */
    public Long getSupplyId() {
        return supplyId;
    }

    /**
    * 设置 供货商：0，自己发货，1，商家发货
    * @param supplyId
    */
    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    /**
    * 获取 货品id
    * @return goodsId
    */
    public String getGoodsId() {
        return goodsId;
    }

    /**
    * 设置 货品id
    * @param goodsId
    */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
    * 获取 货品数量,机油单位为L，机滤单位为个，电瓶单位为1个
    * @return goodsNum
    */
    public Integer getGoodsNum() {
        return goodsNum;
    }

    /**
    * 设置 货品数量,机油单位为L，机滤单位为个，电瓶单位为1个
    * @param goodsNum
    */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
    * 获取 供货商名字
    * @return supplyName
    */
    public String getSupplyName() {
        return supplyName;
    }

    /**
    * 设置 供货商名字
    * @param supplyName
    */
    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
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
    * 获取 上门地址
    * @return addressDetail
    */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
    * 设置 上门地址
    * @param addressDetail
    */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
    * 获取 订单券码，用于商家验券
    * @return code
    */
    public String getCode() {
        return code;
    }

    /**
    * 设置 订单券码，用于商家验券
    * @param code
    */
    public void setCode(String code) {
        this.code = code;
    }

    /**
    * 获取 公里数
    * @return mileage
    */
    public Integer getMileage() {
        return mileage;
    }

    /**
    * 设置 公里数
    * @param mileage
    */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
    * 获取 车辆价格
    * @return carPrice
    */
    public BigDecimal getCarPrice() {
        return carPrice;
    }

    /**
    * 设置 车辆价格
    * @param carPrice
    */
    public void setCarPrice(BigDecimal carPrice) {
        this.carPrice = carPrice;
    }

    /**
    * 获取 运营审核备注
    * @return operateRemark
    */
    public String getOperateRemark() {
        return operateRemark;
    }

    /**
    * 设置 运营审核备注
    * @param operateRemark
    */
    public void setOperateRemark(String operateRemark) {
        this.operateRemark = operateRemark;
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

    /**
    * 获取 商家价格
    * @return jilvPrice
    */
    public BigDecimal getJilvPrice() {
        return jilvPrice;
    }

    /**
    * 设置 商家价格
    * @param jilvPrice
    */
    public void setJilvPrice(BigDecimal jilvPrice) {
        this.jilvPrice = jilvPrice;
    }
}