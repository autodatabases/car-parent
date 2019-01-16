package com.emate.shop.business.vo.gyvo;

/**
 * 
 */

/**
 * @file OilCardCallBackVo.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月12日
 */
public class OilCardCallBackVo {

    private String orderid;    //订单号 

    private String jxorderid;  //高阳生成的订单号 

    private String orderstatus;//0成功 1失败 2部分成功 N

    private String resultcode;

    private String fillmoney;  //充值请求总面值

    private String finishmoney;//充值完成总面值

    private String finishtime; //订单发货完成时 间 

    private String errdesc;    //错误描述

    private String sign;       //签名

    public OilCardCallBackVo(String orderid, String jxorderid,
            String orderstatus, String resultcode, String fillmoney,
            String finishmoney, String finishtime, String errdesc,
            String sign) {
        super();
        this.orderid = orderid;
        this.jxorderid = jxorderid;
        this.orderstatus = orderstatus;
        this.resultcode = resultcode;
        this.fillmoney = fillmoney;
        this.finishmoney = finishmoney;
        this.finishtime = finishtime;
        this.errdesc = errdesc;
        this.sign = sign;
    }

    public OilCardCallBackVo() {
        super();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getJxorderid() {
        return jxorderid;
    }

    public void setJxorderid(String jxorderid) {
        this.jxorderid = jxorderid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getFillmoney() {
        return fillmoney;
    }

    public void setFillmoney(String fillmoney) {
        this.fillmoney = fillmoney;
    }

    public String getFinishmoney() {
        return finishmoney;
    }

    public void setFinishmoney(String finishmoney) {
        this.finishmoney = finishmoney;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getErrdesc() {
        return errdesc;
    }

    public void setErrdesc(String errdesc) {
        this.errdesc = errdesc;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "OilCardCallBackVo [orderid=" + orderid + ", jxorderid="
                + jxorderid + ", orderstatus=" + orderstatus + ", resultcode="
                + resultcode + ", fillmoney=" + fillmoney + ", finishmoney="
                + finishmoney + ", finishtime=" + finishtime + ", errdesc="
                + errdesc + ", sign=" + sign + "]";
    }

}
