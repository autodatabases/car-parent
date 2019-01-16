package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
public class Items {

    private List<CommonXml> item;

    public Items(List<CommonXml> item) {
        super();
        this.item = item;
    }

    public Items() {
        super();
    }

    @XmlElement(name = "item")
    public List<CommonXml> getItem() {
        return item;
    }

    public void setItem(List<CommonXml> item) {
        this.item = item;
    }
}
