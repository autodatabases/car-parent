package com.emate.shop.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.business.api.SellerInfoService;
import com.emate.shop.business.api.SellerService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerInfo;
import com.emate.shop.business.model.WxUserBind;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.wechat.controller.weixin.msg.util.GMessage;
import com.emate.wechat.ParamesAPI;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("user")
public class UserController implements AuthUtil{
	
	private SmsService smsService;
	
	private AccessTokenService accessTokenService;
	
	private SellerInfoService sellerInfoService;
	
	private SellerService sellerService;
	
	@RemoteService
	public void setSellerInfoService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	@RemoteService
	public void setSellerInfoService(SellerInfoService sellerInfoService) {
		this.sellerInfoService = sellerInfoService;
	}

	@RemoteService
	public void setSmsService(SmsService smsService){
		this.smsService = smsService;
	}
	
	@RemoteService
	public void setAccessTokenService(AccessTokenService accessTokenService){
		this.accessTokenService = accessTokenService;
	}
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendSms")
	@ResponseBody
	public DatasetSimple<String> sendSms(HttpServletRequest request,
			HttpServletResponse response,String mobile) throws Exception {
		return smsService.sendSmsCode(mobile,SmsService.SMS_TYPE_0);
	}
	
	/**
	 * 绑定登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bind")
	@ResponseBody
	public DatasetSimple<String> bind(HttpServletRequest request,
			HttpServletResponse response,String mobile,String openId,String code,String tokenCode) throws Exception {
		 DatasetSimple<WxUserBind> result = accessTokenService.bindUser(mobile, openId, code, tokenCode);
		 if(result.isSuccess()){
			 return DatasetBuilder.fromDataSimple(this.getToken(result.getData().getUserId()));
		 }
		 return DatasetBuilder.fromMessageSimple(result.getMessage());
	}
	
	/**
	 * 获取openId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOpenId", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getOpenId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code=request.getParameter("code");
		// 调REST接口
		return GMessage.getUserOpenId(code,ParamesAPI.DEFAULT_SYSID);
	}
	
	

	/**
	 * 设置商家信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setSellerInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> setSellerInfo(HttpServletRequest request,
			HttpServletResponse response,SellerInfo sellerInfo) throws Exception {
		sellerInfo.setSellerId(this.getUserId(request,AuthUtil.CAR_SELLER_TOKEN));
		return sellerInfoService.addSellerInfo(sellerInfo);
	}
	
	
	/**
	 * 查询商家信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSellerInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<SellerInfo> getSellerInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return sellerInfoService.querySellerInfo(this.getUserId(request,AuthUtil.CAR_SELLER_TOKEN));
	}
	
	
	/**
	 * 获取商家详细信息 根据id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSeller")
	@AuthAction
	@ResponseBody
	public DatasetSimple<Map<String,String>> getSeller(HttpServletRequest request) throws Exception {
		DatasetSimple<Seller> sellerData = sellerService.querySellerById(String.valueOf(this.getUserId(request,AuthUtil.CAR_SELLER_TOKEN)));
		Map<String,String> result = new HashMap<String,String>();
		if(sellerData.isSuccess()){
			result.put("sellerName", sellerData.getData().getSellerName());
			result.put("sellerCity", sellerData.getData().getCity());
			return DatasetBuilder.fromDataSimple(result);
		}
		return DatasetBuilder.fromMessageSimple(sellerData.getMessage());
		
		
	}
	
}
