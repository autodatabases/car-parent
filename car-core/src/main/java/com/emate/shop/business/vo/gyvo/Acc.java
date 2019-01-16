package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "acc")
public class Acc {

    private List<CommonXml> mobiles;

    public Acc(List<CommonXml> mobiles) {
        super();
        this.mobiles = mobiles;
    }

    public Acc() {
        super();
    }

    @XmlElement(name = "mobile")
    public List<CommonXml> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<CommonXml> mobiles) {
        this.mobiles = mobiles;
    }

}
