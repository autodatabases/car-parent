package com.emate.shop.business.model;

import java.math.BigDecimal;
import java.util.Date;

public class GyOilLog {
    /**
     * 
     * @Table gy_oil_log.id
     */
    private Long id;

    /**
     * 用户id
     * @Table gy_oil_log.user_id
     */
    private Long userId;

    /**
     * 高阳油卡产品编号
     * @Table gy_oil_log.gameid
     */
    private String gameid;

    /**
     * 欧飞 supplier
     */
    public static final java.lang.Integer SUPPLIER_0 = 0;
    /**
     * 接口提供商：0，欧飞
     * @Table gy_oil_log.supplier
     */
    private Integer supplier;

    /**
     * 亿特诺美 provider
     */
    public static final java.lang.Integer PROVIDER_0 = 0;
    /**
     * 腾双 provider
     */
    public static final java.lang.Integer PROVIDER_1 = 1;
    /**
     * 腾母 provider
     */
    public static final java.lang.Integer PROVIDER_2 = 2;
    /**
     * 泰奎 provider
     */
    public static final java.lang.Integer PROVIDER_3 = 3;
    /**
     * 泰科 provider
     */
    public static final java.lang.Integer PROVIDER_4 = 4;
    /**
     * 接口提供者：0，亿特诺美，1，腾双，2，腾母，3，泰奎，4，泰科
     * @Table gy_oil_log.provider
     */
    private Integer provider;

    /**
     * 父订单号
     * @Table gy_oil_log.parent_order_no
     */
    private String parentOrderNo;

    /**
     * 代理商订单id
     * @Table gy_oil_log.orderid
     */
    private String orderid;

    /**
     * 高阳订单编号
     * @Table gy_oil_log.jxorderid
     */
    private String jxorderid;

    /**
     * 加油卡号
     * @Table gy_oil_log.gascardid
     */
    private String gascardid;

    /**
     * 中石化 chargetype
     */
    public static final java.lang.Byte CHARGETYPE_1 = 1;
    /**
     * 中石油 chargetype
     */
    public static final java.lang.Byte CHARGETYPE_2 = 2;
    /**
     * 充值类型：1，中石化，2，中石油
     * @Table gy_oil_log.chargetype
     */
    private Byte chargetype;

    /**
     * 充值面额
     * @Table gy_oil_log.parvalue
     */
    private BigDecimal parvalue;

    /**
     * 购买数量
     * @Table gy_oil_log.fillnum
     */
    private Integer fillnum;

    /**
     * 成功 orderstatus
     */
    public static final java.lang.Byte ORDERSTATUS_0 = 0;
    /**
     * 失败 orderstatus
     */
    public static final java.lang.Byte ORDERSTATUS_1 = 1;
    /**
     * 部分成功 orderstatus
     */
    public static final java.lang.Byte ORDERSTATUS_2 = 2;
    /**
     * 待处理 orderstatus
     */
    public static final java.lang.Byte ORDERSTATUS_3 = 3;
    /**
     * 处理中 orderstatus
     */
    public static final java.lang.Byte ORDERSTATUS_4 = 4;
    /**
     * 订单状态：0，成功，1，失败，2，部分成功，3，待处理，4，处理中
     * @Table gy_oil_log.orderstatus
     */
    private Byte orderstatus;

    /**
     * 错误消息
     * @Table gy_oil_log.errdesc
     */
    private String errdesc;

    /**
     * 返回的业务码
     * @Table gy_oil_log.resultcode
     */
    private String resultcode;

    /**
     * 持卡人手机号
     * @Table gy_oil_log.gascardtel
     */
    private String gascardtel;

    /**
     * 持卡人姓名
     * @Table gy_oil_log.gascardname
     */
    private String gascardname;

    /**
     * 
     * @Table gy_oil_log.userip
     */
    private String userip;

    /**
     * 充值请求总面值
     * @Table gy_oil_log.fillmoney
     */
    private BigDecimal fillmoney;

    /**
     * 充值完成总面值
     * @Table gy_oil_log.finishmoney
     */
    private BigDecimal finishmoney;

    /**
     * 接口商户结算价
     * @Table gy_oil_log.shop_money
     */
    private BigDecimal shopMoney;

    /**
     * 订单完成后账户剩余金额
     * @Table gy_oil_log.surplus_money
     */
    private BigDecimal surplusMoney;

    /**
     * 订单发货完成时间
     * @Table gy_oil_log.finishtime
     */
    private Date finishtime;

    /**
     * 请求次数
     * @Table gy_oil_log.request_num
     */
    private Integer requestNum;

    /**
     * 
     * @Table gy_oil_log.create_time
     */
    private Date createTime;

    /**
     * 
     * @Table gy_oil_log.update_time
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
    * 获取 高阳油卡产品编号
    * @return gameid
    */
    public String getGameid() {
        return gameid;
    }

    /**
    * 设置 高阳油卡产品编号
    * @param gameid
    */
    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    /**
    * 获取 接口提供商：0，欧飞
    * @return supplier
    */
    public Integer getSupplier() {
        return supplier;
    }

    /**
    * 设置 接口提供商：0，欧飞
    * @param supplier
    */
    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    /**
    * 获取 接口提供者：0，亿特诺美，1，腾双，2，腾母，3，泰奎，4，泰科
    * @return provider
    */
    public Integer getProvider() {
        return provider;
    }

    /**
    * 设置 接口提供者：0，亿特诺美，1，腾双，2，腾母，3，泰奎，4，泰科
    * @param provider
    */
    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    /**
    * 获取 父订单号
    * @return parentOrderNo
    */
    public String getParentOrderNo() {
        return parentOrderNo;
    }

    /**
    * 设置 父订单号
    * @param parentOrderNo
    */
    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo;
    }

    /**
    * 获取 代理商订单id
    * @return orderid
    */
    public String getOrderid() {
        return orderid;
    }

    /**
    * 设置 代理商订单id
    * @param orderid
    */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
    * 获取 高阳订单编号
    * @return jxorderid
    */
    public String getJxorderid() {
        return jxorderid;
    }

    /**
    * 设置 高阳订单编号
    * @param jxorderid
    */
    public void setJxorderid(String jxorderid) {
        this.jxorderid = jxorderid;
    }

    /**
    * 获取 加油卡号
    * @return gascardid
    */
    public String getGascardid() {
        return gascardid;
    }

    /**
    * 设置 加油卡号
    * @param gascardid
    */
    public void setGascardid(String gascardid) {
        this.gascardid = gascardid;
    }

    /**
    * 获取 充值类型：1，中石化，2，中石油
    * @return chargetype
    */
    public Byte getChargetype() {
        return chargetype;
    }

    /**
    * 设置 充值类型：1，中石化，2，中石油
    * @param chargetype
    */
    public void setChargetype(Byte chargetype) {
        this.chargetype = chargetype;
    }

    /**
    * 获取 充值面额
    * @return parvalue
    */
    public BigDecimal getParvalue() {
        return parvalue;
    }

    /**
    * 设置 充值面额
    * @param parvalue
    */
    public void setParvalue(BigDecimal parvalue) {
        this.parvalue = parvalue;
    }

    /**
    * 获取 购买数量
    * @return fillnum
    */
    public Integer getFillnum() {
        return fillnum;
    }

    /**
    * 设置 购买数量
    * @param fillnum
    */
    public void setFillnum(Integer fillnum) {
        this.fillnum = fillnum;
    }

    /**
    * 获取 订单状态：0，成功，1，失败，2，部分成功，3，待处理，4，处理中
    * @return orderstatus
    */
    public Byte getOrderstatus() {
        return orderstatus;
    }

    /**
    * 设置 订单状态：0，成功，1，失败，2，部分成功，3，待处理，4，处理中
    * @param orderstatus
    */
    public void setOrderstatus(Byte orderstatus) {
        this.orderstatus = orderstatus;
    }

    /**
    * 获取 错误消息
    * @return errdesc
    */
    public String getErrdesc() {
        return errdesc;
    }

    /**
    * 设置 错误消息
    * @param errdesc
    */
    public void setErrdesc(String errdesc) {
        this.errdesc = errdesc;
    }

    /**
    * 获取 返回的业务码
    * @return resultcode
    */
    public String getResultcode() {
        return resultcode;
    }

    /**
    * 设置 返回的业务码
    * @param resultcode
    */
    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    /**
    * 获取 持卡人手机号
    * @return gascardtel
    */
    public String getGascardtel() {
        return gascardtel;
    }

    /**
    * 设置 持卡人手机号
    * @param gascardtel
    */
    public void setGascardtel(String gascardtel) {
        this.gascardtel = gascardtel;
    }

    /**
    * 获取 持卡人姓名
    * @return gascardname
    */
    public String getGascardname() {
        return gascardname;
    }

    /**
    * 设置 持卡人姓名
    * @param gascardname
    */
    public void setGascardname(String gascardname) {
        this.gascardname = gascardname;
    }

    /**
    * 获取 
    * @return userip
    */
    public String getUserip() {
        return userip;
    }

    /**
    * 设置 
    * @param userip
    */
    public void setUserip(String userip) {
        this.userip = userip;
    }

    /**
    * 获取 充值请求总面值
    * @return fillmoney
    */
    public BigDecimal getFillmoney() {
        return fillmoney;
    }

    /**
    * 设置 充值请求总面值
    * @param fillmoney
    */
    public void setFillmoney(BigDecimal fillmoney) {
        this.fillmoney = fillmoney;
    }

    /**
    * 获取 充值完成总面值
    * @return finishmoney
    */
    public BigDecimal getFinishmoney() {
        return finishmoney;
    }

    /**
    * 设置 充值完成总面值
    * @param finishmoney
    */
    public void setFinishmoney(BigDecimal finishmoney) {
        this.finishmoney = finishmoney;
    }

    /**
    * 获取 接口商户结算价
    * @return shopMoney
    */
    public BigDecimal getShopMoney() {
        return shopMoney;
    }

    /**
    * 设置 接口商户结算价
    * @param shopMoney
    */
    public void setShopMoney(BigDecimal shopMoney) {
        this.shopMoney = shopMoney;
    }

    /**
    * 获取 订单完成后账户剩余金额
    * @return surplusMoney
    */
    public BigDecimal getSurplusMoney() {
        return surplusMoney;
    }

    /**
    * 设置 订单完成后账户剩余金额
    * @param surplusMoney
    */
    public void setSurplusMoney(BigDecimal surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    /**
    * 获取 订单发货完成时间
    * @return finishtime
    */
    public Date getFinishtime() {
        return finishtime;
    }

    /**
    * 设置 订单发货完成时间
    * @param finishtime
    */
    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    /**
    * 获取 请求次数
    * @return requestNum
    */
    public Integer getRequestNum() {
        return requestNum;
    }

    /**
    * 设置 请求次数
    * @param requestNum
    */
    public void setRequestNum(Integer requestNum) {
        this.requestNum = requestNum;
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