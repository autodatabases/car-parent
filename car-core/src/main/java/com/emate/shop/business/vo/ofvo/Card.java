package com.emate.shop.business.vo.ofvo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "card")
public class Card {

	
	public Card(String cardno, String cardpws, String expiretime) {
		super();
		this.cardno = cardno;
		this.cardpws = cardpws;
		this.expiretime = expiretime;
	}
	
	
	public Card() {
		super();
	}


	private String cardno;
	private String cardpws;
	private String expiretime;
	
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getCardpws() {
		return cardpws;
	}
	public void setCardpws(String cardpws) {
		this.cardpws = cardpws;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	@Override
	public String toString() {
		return "card [cardno=" + cardno + ", cardpws=" + cardpws + ", expiretime=" + expiretime + "]";
	}
	
}
