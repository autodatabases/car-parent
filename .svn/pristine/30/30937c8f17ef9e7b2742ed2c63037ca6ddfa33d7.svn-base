/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @file OilCardGameTem.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
@XmlRootElement(name = "gameinfolist")
public class OilCardGameTem {
    private List<OilCardGameInfo> OilCardGameInfos;

    public OilCardGameTem(List<OilCardGameInfo> oilCardGameInfos) {
        super();
        OilCardGameInfos = oilCardGameInfos;
    }

    public OilCardGameTem() {
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
