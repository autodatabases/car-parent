/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @file OilCardProductTem.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "gameproductlist")
public class OilCardProductTem {

    private List<OilCardProduct> oilCardProducts;

    public OilCardProductTem(List<OilCardProduct> oilCardProducts) {
        super();
        this.oilCardProducts = oilCardProducts;
    }

    public OilCardProductTem() {
        super();
    }

    @XmlElement(name = "gameproduct")
    public List<OilCardProduct> getOilCardProducts() {
        return oilCardProducts;
    }

    public void setOilCardProducts(List<OilCardProduct> oilCardProducts) {
        this.oilCardProducts = oilCardProducts;
    }

    @Override
    public String toString() {
        return "OilCardProductTem [oilCardProducts=" + oilCardProducts + "]";
    }

}
