/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @file OilCardGameCardTem.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "cardinfolist")
public class OilCardGameTemCard {
    private List<OilCardGameInfo> OilCardGameInfos;

    public OilCardGameTemCard(List<OilCardGameInfo> oilCardGameInfos) {
        super();
        OilCardGameInfos = oilCardGameInfos;
    }

    public OilCardGameTemCard() {
        super();
    }

    @XmlElement(name = "gameinfo")
    public List<OilCardGameInfo> getOilCardGameInfos() {
        return OilCardGameInfos;
    }

    
    public void setOilCardGameInfos(List<OilCardGameInfo> oilCardGameInfos) {
        OilCardGameInfos = oilCardGameInfos;
    }

    @Override
    public String toString() {
        return "OilCardGameTem [OilCardGameInfos=" + OilCardGameInfos + "]";
    }

    
}
