/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @file OilCardGameResult.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "response")
public class OilCardGameResult {

    private String             code;

    private OilCardGameTem     oilCardGameTem;

    private OilCardGameTemCard oilCardGameTemCard;

    public OilCardGameResult(String code, OilCardGameTem oilCardGameTem,
            OilCardGameTemCard oilCardGameTemCard) {
        super();
        this.code = code;
        this.oilCardGameTem = oilCardGameTem;
        this.oilCardGameTemCard = oilCardGameTemCard;
    }

    public OilCardGameResult() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "gameinfolist")
    public OilCardGameTem getOilCardGameTem() {
        return oilCardGameTem;
    }

    public void setOilCardGameTem(OilCardGameTem oilCardGameTem) {
        this.oilCardGameTem = oilCardGameTem;
    }

    @XmlElement(name = "cardinfolist")
    public OilCardGameTemCard getOilCardGameTemCard() {
        return oilCardGameTemCard;
    }

    public void setOilCardGameTemCard(OilCardGameTemCard oilCardGameTemCard) {
        this.oilCardGameTemCard = oilCardGameTemCard;
    }

    @Override
    public String toString() {
        return "OilCardGameResult [code=" + code + ", oilCardGameTem="
                + oilCardGameTem + ", oilCardGameTemCard=" + oilCardGameTemCard
                + "]";
    }

}
