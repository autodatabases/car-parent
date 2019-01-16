package com.emate.shop.business.vo.gyvo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mobile")
public class CommonXml {

    private String name;

    private String value;

    public CommonXml(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public CommonXml() {
        super();
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
