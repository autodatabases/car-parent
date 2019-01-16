package com.emate.shop.h5.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CountermanGoodService;
import com.emate.shop.business.api.CountermanOrdersService;
import com.emate.shop.business.api.CountermanScoreService;
import com.emate.shop.business.api.CountermanService;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.business.model.CountermanOrders;
import com.emate.shop.business.model.CountermanScoreRecord;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping(value="counterman")
public class CountermanController implements AuthUtil{
	
	private CountermanService countermanService;
	
	@RemoteService
	public void setCountermanService(CountermanService countermanService) {
		this.countermanService = countermanService;
	}
	
	private CountermanGoodService countermanGoodService;
	
	@RemoteService
	public void setCountermanGoodService(CountermanGoodService countermanGoodService) {
		this.countermanGoodService = countermanGoodService;
	}
	
	private CountermanScoreService countermanScoreService;
	
	@RemoteService
	public void setCountermanScoreService(CountermanScoreService countermanScoreService) {
		this.countermanScoreService = countermanScoreService;
	}
	
	private CountermanOrdersService countermanOrdersService;
	
	@RemoteService
	public void setCountermanOrdersService(CountermanOrdersService countermanOrdersService) {
		this.countermanOrdersService = countermanOrdersService;
	}
	/**
	 * 跳转到业务员激活
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tobindCounterman.html",method=RequestMethod.GET)
	@AuthAction
	public String tobindBusiness(HttpServletRequest request) throws Exception {
		return "counterman/bindCounterman";
	}
	
	/**
	 * 业务员详情跳转页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dataInfo.html")
	@AuthAction
	public String centerDataInfo(HttpServletRequest request) throws Exception {
		return "counterman/dataInfo";
	}
	/**
	 * 积分详情页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/point-details.html")
	@AuthAction
	public String centerPointDetails(HttpServletRequest request) throws Exception {
		return "counterman/point-details";
	}
	
	/**
	 * 积分商城页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/point-mall.html")
	@AuthAction
	public String centerPointMall(HttpServletRequest request) throws Exception {
		return "counterman/point-mall";
	}
	/**
	 * 绑定业务员
	 * @param request
	 * @param countermanCode
	 * @param realName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindCounterman")
	@AuthAction
	@ResponseBody
	public DatasetSimple<Map<String,String>> bindBusiness(HttpServletRequest request,
			@Required String countermanCode,
			@Required String realName) throws Exception {
		return countermanService.bindCounterman(this.getUserId(request, AuthUtil.CAR_H5_TOKEN),countermanCode,realName);
	}
/**
	 * 获取信息
	 * @param request
	 * @param response
	 * @param countermanCode
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/getCountermanInfo")
	@ResponseBody
	@AuthAction
	public DatasetList<Map<String,Object>> getCountermanInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return countermanService.getCountermanInfo(this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
	}*/
	/**
	 * 获取信息
	 * @param request
	 * @param response
	 * @param countermanCode
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value = "/getCountermanInfo")
	@ResponseBody
	@AuthAction
	public DatasetList<CountermanCareer> getCountermanInfo1(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DatasetList<CountermanCareer> countermanInfo1 = countermanService.getCountermanInfo1(this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
		//根据价格排序
		Collections.sort(countermanInfo1.getList(),new Comparator<CountermanCareer>(){
			@Override
			public int compare(CountermanCareer o1, CountermanCareer o2) {
				if(o1.getYesterdayPrice().intValue() == o2.getYesterdayPrice().intValue()){
					return 0;
				}
				return o1.getYesterdayPrice().intValue()< o2.getYesterdayPrice().intValue()?1:-1;
			}
			
		});
		return countermanInfo1;
	}*/
	/**
	 * 获取业务员积分信息
	 * @param request
	 * @param response
	 * @param countermanCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterman")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,Object>> getCountermanByUserId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return countermanService.h5GetCountermanByUserId(this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
	}
	/**
	 * 获取积分商城的商品信息
	 * @param request
	 * @param response
	 * @param belongArea
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGoodList")
	@AuthAction
	@ResponseBody
	public DatasetList<CountermanGood> getGoodList(HttpServletRequest request,
			HttpServletResponse response,
			String belongArea,
			Integer pageNo,
			Integer pageSize) throws Exception {
		return countermanGoodService.h5CountermanGoodList(this.getUserId(request, AuthUtil.CAR_H5_TOKEN),pageNo, pageSize, belongArea);
	}
	/**
	 * 获取积分变动记录
	 * @param request
	 * @param response
	 * @param pageNo
	 * @param pageSize
	 * @param countermanId
	 * @param belongArea
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getScoreRecordList")
	@AuthAction
	@ResponseBody
	public DatasetList<CountermanScoreRecord> getScoreRecordList(HttpServletRequest request,
			HttpServletResponse response, 
			Integer pageNo,
			Integer pageSize) throws Exception {
		return countermanScoreService.h5CountermanScoreRecordList(pageNo, pageSize, this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
	}
	/**
	 * 增加用户订单
	 * @param request
	 * @param response
	 * @param countermanOrders
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCountermanOrder")
	@AuthAction
	@ResponseBody
	public DatasetSimple<String> addCountermanOrder(HttpServletRequest request,
			HttpServletResponse response,CountermanOrders countermanOrders) throws Exception {
		return countermanOrdersService.addCountermanOrders(countermanOrders,this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
	}
}
