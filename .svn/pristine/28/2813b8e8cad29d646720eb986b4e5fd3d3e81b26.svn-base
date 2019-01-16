package com.emate.shop.business.vo.gyvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "allproducts")
public class Allproducts {

    private List<Products> products;

    public Allproducts(List<Products> products) {
        super();
        this.products = products;
    }

    public Allproducts() {
        super();
    }

    @XmlElement(name = "products")
    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

}
