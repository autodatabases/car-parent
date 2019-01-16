/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 下单结果
 * @file OrderResult.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "response")
public class OilCardOrderResult {

    private String code;       //返回状态码

    private String jxorderid;  //高阳生成的订单号 

    private String orderid;    //代理商下单时订单号

    private String jxorderdate;//高阳收单时间

    public OilCardOrderResult(String code, String jxorderid, String orderid,
            String jxorderdate) {
        super();
        this.code = code;
        this.jxorderid = jxorderid;
        this.orderid = orderid;
        this.jxorderdate = jxorderdate;
    }

    public OilCardOrderResult() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJxorderid() {
        return jxorderid;
    }

    public void setJxorderid(String jxorderid) {
        this.jxorderid = jxorderid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getJxorderdate() {
        return jxorderdate;
    }

    public void setJxorderdate(String jxorderdate) {
        this.jxorderdate = jxorderdate;
    }

    @Override
    public String toString() {
        return "OilCardRechargeResult [code=" + code + ", jxorderid="
                + jxorderid + ", orderid=" + orderid + ", jxorderdate="
                + jxorderdate + "]";
    }

}
