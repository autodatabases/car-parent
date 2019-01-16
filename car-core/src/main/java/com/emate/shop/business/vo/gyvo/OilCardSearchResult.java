/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 订单结果查询
 * @file OilCardSearchResult.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "response")
public class OilCardSearchResult {

    private String code;       //业务返回码

    private String orderid;    //订单号

    private String jxorderid;  //高阳订单号

    private String orderstatus;//发货结果

    private String finishmoney;//充值完成总面

    private String fillmoney;  //充值请求总面值

    private String finishtime; //订单发货完成时 间

    private String errorcode;  //充值错误码

    private String errorinfo;  //充值错误描述

    public OilCardSearchResult(String code, String orderid, String jxorderid,
            String orderstatus, String finishmoney, String fillmoney,
            String finishtime, String errorcode, String errorinfo) {
        super();
        this.code = code;
        this.orderid = orderid;
        this.jxorderid = jxorderid;
        this.orderstatus = orderstatus;
        this.finishmoney = finishmoney;
        this.fillmoney = fillmoney;
        this.finishtime = finishtime;
        this.errorcode = errorcode;
        this.errorinfo = errorinfo;
    }

    public OilCardSearchResult() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getFinishmoney() {
        return finishmoney;
    }

    public void setFinishmoney(String finishmoney) {
        this.finishmoney = finishmoney;
    }

    public String getFillmoney() {
        return fillmoney;
    }

    public void setFillmoney(String fillmoney) {
        this.fillmoney = fillmoney;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    @Override
    public String toString() {
        return "OilCardSearchResult [code=" + code + ", orderid=" + orderid
                + ", jxorderid=" + jxorderid + ", orderstatus=" + orderstatus
                + ", finishmoney=" + finishmoney + ", fillmoney=" + fillmoney
                + ", finishtime=" + finishtime + ", errorcode=" + errorcode
                + ", errorinfo=" + errorinfo + "]";
    }

}
