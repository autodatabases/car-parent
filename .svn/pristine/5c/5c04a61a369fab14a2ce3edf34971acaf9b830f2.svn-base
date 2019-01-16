package com.emate.shop.h5.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.DidiService;
import com.emate.shop.common.IPUtil;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping(value="didi")
public class DiDiController implements AuthUtil{
	
	private DidiService didiService;
	
	@RemoteService
	public void setDidiService(DidiService didiService) {
		this.didiService = didiService;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchange.html")
	@AuthAction
	public String exchange(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "didi/exchange";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mydidi.html")
	@AuthAction
	public String myDidi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "didi/mydidi";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/record.html")
	@AuthAction
	public String toRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "didi/record";
	}
	/**
	 * 获取滴滴出行券的使用张数情况
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcouponnum")
	@AuthAction
	@ResponseBody
	public DatasetSimple<Map<String,Integer>> getCouponNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = this.getUserId(request, "CAR_H5_TOKEN");
		return didiService.getCouponNum(userId);
	}
	
	/**
	 * 获取兑换滴滴代驾券的兑换记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcouponrecord")
	@AuthAction
	@ResponseBody
	public DatasetList<Map<String,Object>> getCouponRecord(
			HttpServletRequest request,HttpServletResponse response,
			@Required @Regex("\\d+")String pageNo,
			@Required @Regex("\\d+")String pageSize) throws Exception {
		
		Long userId = this.getUserId(request, "CAR_H5_TOKEN");
		return didiService.getCouponRecord(Integer.valueOf(pageNo), 
				Integer.valueOf(pageSize), userId);
	}
	
	@RequestMapping(value = "/addcouponorders")
	@AuthAction
	@ResponseBody
	public DatasetSimple<Map<String,Object>> addCouponOrders(
			HttpServletRequest request,HttpServletResponse response,
			String content) throws Exception {
		Long userId = this.getUserId(request, "CAR_H5_TOKEN");
		String ip = IPUtil.getIP(request);
		return didiService.addCouponOrders(content,userId,ip);
	}
	
}
