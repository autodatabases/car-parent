/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查询产品
 * @file OilCardSearchProduct.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "response")
public class OilCardSearchProductResult {

    private String            code;

    private OilCardProductTem oilCardProductTem;

    public OilCardSearchProductResult(String code,
            OilCardProductTem oilCardProductTem) {
        super();
        this.code = code;
        this.oilCardProductTem = oilCardProductTem;
    }

    public OilCardSearchProductResult() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "gameproductlist")
    public OilCardProductTem getOilCardProductTem() {
        return oilCardProductTem;
    }

    public void setOilCardProductTem(OilCardProductTem oilCardProductTem) {
        this.oilCardProductTem = oilCardProductTem;
    }

    @Override
    public String toString() {
        return "OilCardSearchProduct [code=" + code + ", oilCardProductTem="
                + oilCardProductTem + "]";
    }

}
