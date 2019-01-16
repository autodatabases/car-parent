package com.emate.shop.h5.controller;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.MerchantService;
import com.emate.shop.business.model.Merchant;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthUtil;

@Controller
@RequestMapping(value="merchant")
public class MerchantController implements AuthUtil{
	
	private MerchantService merchantService;
	
	@RemoteService
	public void setSellerService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	/**
	 * 消息列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMerchantList.html")
	public String oilCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "oilCard/oilCard";
	}
	
	/**
	 * 获取加油站列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMerchantList")
	@ResponseBody
	public DatasetList<Merchant> getList(Integer pageNo,Integer pageSize,Merchant merchant) throws Exception {
		DatasetList<Merchant>  list = merchantService.h5MerchantList(pageNo,pageSize,merchant);
		return list;
	}
	
}
