package com.emate.shop.business.vo.ofvo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orderinfo")
public class OufeiOrder {
	private String err_msg;
	private String retcode;
	private String orderid;
	private String cardid;
	private String cardnum;
	private String ordercash;
	private String cardname;
	private String sporder_id;
	private String game_userid;
	private String game_state;
	
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getOrdercash() {
		return ordercash;
	}
	public void setOrdercash(String ordercash) {
		this.ordercash = ordercash;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public String getSporder_id() {
		return sporder_id;
	}
	public void setSporder_id(String sporder_id) {
		this.sporder_id = sporder_id;
	}
	public String getGame_userid() {
		return game_userid;
	}
	public void setGame_userid(String game_userid) {
		this.game_userid = game_userid;
	}
	public String getGame_state() {
		return game_state;
	}
	public void setGame_state(String game_state) {
		this.game_state = game_state;
	}
	@Override
	public String toString() {
		return "OufeiOrder [err_msg=" + err_msg + ", retcode=" + retcode + ", orderid=" + orderid + ", cardid=" + cardid
				+ ", cardnum=" + cardnum + ", ordercash=" + ordercash + ", cardname=" + cardname + ", sporder_id="
				+ sporder_id + ", game_userid=" + game_userid + ", game_state=" + game_state + "]";
	}


}
