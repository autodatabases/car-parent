package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "products")
public class Products {

    private List<CommonXml> product;

    public Products(List<CommonXml> product) {
        super();
        this.product = product;
    }

    public Products() {
        super();
    }

    @XmlElement(name = "product")
    public List<CommonXml> getProduct() {
        return product;
    }

    public void setProduct(List<CommonXml> product) {
        this.product = product;
    }

}
