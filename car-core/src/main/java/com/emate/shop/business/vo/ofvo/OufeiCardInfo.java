package com.emate.shop.business.vo.ofvo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "queryInfo")
public class OufeiCardInfo {
	
	private String err_msg;
	private String retcode;
	private String game_userid;
	private String username;
	
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
	public String getGame_userid() {
		return game_userid;
	}
	public void setGame_userid(String game_userid) {
		this.game_userid = game_userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "OufeiCardInfo [err_msg=" + err_msg + ", retcode=" + retcode + ", game_userid=" + game_userid
				+ ", username=" + username + "]";
	}
	
}
