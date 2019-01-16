package com.emate.shop.business.vo.gyvo;

/**
 * 
 */

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @file OilCardCallBack.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月12日
 */
@XmlRootElement(name = "response")
public class OilCardCallBack {

    private String notifyresult;

    public OilCardCallBack(String notifyresult) {
        super();
        this.notifyresult = notifyresult;
    }

    public OilCardCallBack() {
        super();
    }

    
    public String getNotifyresult() {
        return notifyresult;
    }

    
    public void setNotifyresult(String notifyresult) {
        this.notifyresult = notifyresult;
    }
    
}
