package com.emate.shop.business.model;

public class SellerPolicy {
	
	/**
	 * 是否是定额保养，如果指定了额度，那么就不会走正常流程
	 */
	public Integer baoyangMoney;
	
	/**
	 * 是否是定额喷漆额度
	 */
	public Integer penqiMoney;
	
	public Integer getPenqiMoney() {
		return penqiMoney;
	}


	public void setPenqiMoney(Integer penqiMoney) {
		this.penqiMoney = penqiMoney;
	}


	/**
	 * 保养是否需要后台审核
	 */
	public Boolean baoyangCheck;
	

	/**
	 * 喷漆是否需要后台审核
	 */
	public Boolean penqiCheck;


	public Integer getBaoyangMoney() {
		return baoyangMoney;
	}


	public void setBaoyangMoney(Integer baoyangMoney) {
		this.baoyangMoney = baoyangMoney;
	}


	public Boolean getBaoyangCheck() {
		return baoyangCheck;
	}


	public void setBaoyangCheck(Boolean baoyangCheck) {
		this.baoyangCheck = baoyangCheck;
	}


	public Boolean getPenqiCheck() {
		return penqiCheck;
	}


	public void setPenqiCheck(Boolean penqiCheck) {
		this.penqiCheck = penqiCheck;
	}
}
