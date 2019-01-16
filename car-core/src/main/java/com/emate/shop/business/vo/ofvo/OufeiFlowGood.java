package com.emate.shop.business.vo.ofvo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "queryinfo")
public class OufeiFlowGood {
	private String err_msg;//描述
	private String retcode;//错误码
	private String saleprice;//商品价格
	private String productname;//商品描述
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(String saleprice) {
		this.saleprice = saleprice;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	@Override
	public String toString() {
		return "OufeiFlowGood [err_msg=" + err_msg + ", retcode=" + retcode + ", saleprice=" + saleprice
				+ ", productname=" + productname + "]";
	}
	
}
