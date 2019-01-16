package com.emate.shop.wechat.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.wechat.controller.weixin.aes.SHA1;
import com.emate.shop.wechat.controller.weixin.aes.WXBizMsgCrypt;
import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.ParamesAPI;
import com.emate.wechat.RefreshTokenUtil;

@Controller
@RequestMapping
public class IndexController implements AuthUtil{
	
	private AccessTokenService accessTokenService;
	
	@RemoteService
	public void setAccessTokenService(AccessTokenService accessTokenService){
		this.accessTokenService = accessTokenService;
	}
	
	@RequestMapping("getWxAppId")
	@ResponseBody
	public DatasetSimple<String> getWxAppId(HttpServletRequest request){
		return DatasetBuilder.fromDataSimple(ParamesAPI.getCorpId(ParamesAPI.DEFAULT_SYSID));
	}
	
	@RequestMapping
	@AuthAction
	public String verifyUserCode(HttpServletRequest request,Map<String,Object> stackMap){
		
		//刷新access_token
		try {
			AccessTokenVo.access_token = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,0,this.accessTokenService);
			AccessTokenVo.js_ticket = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,1,this.accessTokenService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        //微信jssdk
		String timestamp = new Date().getTime()+"";
		String noncestr = WXBizMsgCrypt.getRandomStr();
		String url = ParamesAPI.getDomian(ParamesAPI.DEFAULT_SYSID) + request.getRequestURI();
		url += request.getQueryString() == null ? "":"?"+request.getQueryString();
		stackMap.put("jsapi_ticket", AccessTokenVo.js_ticket);
		stackMap.put("timestamp", timestamp);
		stackMap.put("noncestr", noncestr);
		stackMap.put("url", url);
		List<String> param = new ArrayList<String>();
		stackMap.keySet().forEach(value -> {
			param.add(value);
		});
		java.util.Collections.sort(param);
		StringBuffer sb = new StringBuffer();
		param.forEach(value -> {
			sb.append(value).append("=").append(stackMap.get(value)).append("&");
		});
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		String signature = SHA1.sha1(sb.toString());
		stackMap.put("signature", signature);
		stackMap.put("appId", ParamesAPI.getCorpId(ParamesAPI.DEFAULT_SYSID));
		return "index";
	}
}
