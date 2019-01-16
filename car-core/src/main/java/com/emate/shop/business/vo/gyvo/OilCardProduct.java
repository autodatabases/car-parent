/**
 * 
 */
package com.emate.shop.business.vo.gyvo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @file OilCardProduct.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "gameproduct")
public class OilCardProduct {

    private String onlineid;  //产品编号

    private String onlinename;//产品名称

    private String gameid;    //商品编号

    private String gamename;  //商品名称

    private String parvalue;  //产品面值

    private String saleprice; //产品单价

    public OilCardProduct(String onlineid, String onlinename, String gameid,
            String gamename, String parvalue, String saleprice) {
        super();
        this.onlineid = onlineid;
        this.onlinename = onlinename;
        this.gameid = gameid;
        this.gamename = gamename;
        this.parvalue = parvalue;
        this.saleprice = saleprice;
    }

    public OilCardProduct() {
        super();
    }

    @XmlElement(name = "onlineid")
    public String getOnlineid() {
        return onlineid;
    }

    public void setOnlineid(String onlineid) {
        this.onlineid = onlineid;
    }

    public String getOnlinename() {
        return onlinename;
    }

    public void setOnlinename(String onlinename) {
        this.onlinename = onlinename;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getParvalue() {
        return parvalue;
    }

    public void setParvalue(String parvalue) {
        this.parvalue = parvalue;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    @Override
    public String toString() {
        return "OilCardProduct [onlineid=" + onlineid + ", onlinename="
                + onlinename + ", gameid=" + gameid + ", gamename=" + gamename
                + ", parvalue=" + parvalue + ", saleprice=" + saleprice + "]";
    }

}
