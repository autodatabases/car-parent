package com.emate.shop.business.vo.gyvo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fill")
public class Fill {

    private Items items;

    public Fill(Items items) {
        super();
        this.items = items;
    }

    public Fill() {
        super();
    }

    @XmlElement(name = "items")
    public Items getItems() {
        return items;
    }

    
    public void setItems(Items items) {
        this.items = items;
    }
    
}
