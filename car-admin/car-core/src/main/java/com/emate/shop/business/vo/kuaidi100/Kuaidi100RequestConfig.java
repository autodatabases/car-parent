/**
 * 
 */
package com.emate.shop.business.vo.kuaidi100;

import com.emate.shop.business.model.OrderExpress;

/**
 * @file Kuaidi100RequestConfig.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年8月17日
 */
public class Kuaidi100RequestConfig {

    private Kuaidi100Request request;

    private String           pollUrl;

    private OrderExpress     orderExpress;

    public Kuaidi100Request getRequest() {
        return request;
    }

    public void setRequest(Kuaidi100Request request) {
        this.request = request;
    }

    public String getPollUrl() {
        return pollUrl;
    }

    public void setPollUrl(String pollUrl) {
        this.pollUrl = pollUrl;
    }

    public OrderExpress getOrderExpress() {
        return orderExpress;
    }

    public void setOrderExpress(OrderExpress orderExpress) {
        this.orderExpress = orderExpress;
    }

}
