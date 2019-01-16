package com.emate.shop.wechat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;

/**
 * 用来返回所有ftl页面的controller
 * @author likk
 *
 */
@Controller
@RequestMapping
public class PageController implements AuthUtil{
	
	/**
	 * 登录模块
	 * @param request
	 * @param stackMap
	 * @return
	 */
	@RequestMapping(value="/auth/login.html")
	public String authLogin(HttpServletRequest request,Map<String,Object> stackMap){
		return "auth/login";
	}
	
	
	/**
	 * 商家资质模块
	 * @param request
	 * @param stackMap
	 * @return
	 */
	@RequestMapping(value="/busines/busines-one.html")
	@AuthAction
	public String businesOne(HttpServletRequest request,Map<String,Object> stackMap){
		return "busines/busines-one";
	}
	@RequestMapping(value="/busines/busines-setup.html")
	@AuthAction
	public String businesSetup(HttpServletRequest request,Map<String,Object> stackMap){
		return "busines/busines-setup";
	}
	@RequestMapping(value="/busines/busines-three.html")
	@AuthAction
	public String businesThree(HttpServletRequest request,Map<String,Object> stackMap){
		return "busines/busines-three";
	}
	@RequestMapping(value="/busines/busines-two.html")
	@AuthAction
	public String businesTwo(HttpServletRequest request,Map<String,Object> stackMap){
		return "busines/busines-two";
	}
	@RequestMapping(value="/busines/phone-unbund.html")
	@AuthAction
	public String businesUnbund(HttpServletRequest request,Map<String,Object> stackMap){
		return "busines/phone-unbund";
	}
	@RequestMapping(value="/busines/unbund-ok.html")
	@AuthAction
	public String businesOK(HttpServletRequest request,Map<String,Object> stackMap){
		return "busines/unbund-ok";
	}
	
	/**
	 * 验券记录
	 * @param request
	 * @param stackMap
	 * @return
	 */
	@RequestMapping(value="/coupon/coupon.html")
	@AuthAction
	public String coupon(HttpServletRequest request,Map<String,Object> stackMap){
		return "coupon/coupon";
	}
	
	
	
	/**
	 * 订单模块
	 * @param request
	 * @param stackMap
	 * @return
	 */
	@RequestMapping(value="/order/order.html")
	@AuthAction
	public String order(HttpServletRequest request,Map<String,Object> stackMap){
		return "order/order";
	}
	@RequestMapping(value="/order/order-details.html")
	@AuthAction
	public String orderDetails(HttpServletRequest request,Map<String,Object> stackMap){
		return "order/order-details";
	}
	
	
	/**
	 * 维修订单模块
	 * @param request
	 * @param stackMap
	 * @return
	 */
	@RequestMapping(value="/repair/ok.html")
	@AuthAction
	public String repairOk(HttpServletRequest request,Map<String,Object> stackMap){
		return "repair/ok";
	}
	@RequestMapping(value="/repair/order-detail.html")
	@AuthAction
	public String repairDetail(HttpServletRequest request,Map<String,Object> stackMap){
		return "repair/order-detail";
	}
	@RequestMapping(value="/repair/order.html")
	@AuthAction
	public String repairOrder(HttpServletRequest request,Map<String,Object> stackMap){
		return "repair/order";
	}
	
	
	
	/**
	 * 结算模块
	 * @param request
	 * @param stackMap
	 * @return
	 */
	@RequestMapping(value="/setlment/detailed-details.html")
	@AuthAction
	public String setlmentDetailed(HttpServletRequest request,Map<String,Object> stackMap){
		return "setlment/detailed-details";
	}
	
	@RequestMapping(value="/setlment/setlment-detailed.html")
	@AuthAction
	public String setlmentsetlmentDetailed(HttpServletRequest request,Map<String,Object> stackMap){
		return "setlment/setlment-detailed";
	}
	
	@RequestMapping(value="/setlment/setlment-frozen.html")
	@AuthAction
	public String setlmentfrozen(HttpServletRequest request,Map<String,Object> stackMap){
		return "setlment/setlment-frozen";
	}
	
	@RequestMapping(value="/setlment/setlment-income.html")
	@AuthAction
	public String setlmentincome(HttpServletRequest request,Map<String,Object> stackMap){
		return "setlment/setlment-income";
	}
	
	@RequestMapping(value="/setlment/setlment.html")
	@AuthAction
	public String setlment(HttpServletRequest request,Map<String,Object> stackMap){
		return "setlment/setlment";
	}
}
