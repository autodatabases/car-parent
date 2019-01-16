package com.emate.shop.h5.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.OrderCommentService;
import com.emate.shop.business.model.OrderComment;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;

@Controller
@RequestMapping("/ordercomment")
public class OrderCommentController {
	
	
	private OrderCommentService orderCommentService;
	
	@RemoteService
	public void setOrderCommentService(OrderCommentService orderCommentService) {
		this.orderCommentService = orderCommentService;
	}
	
	/**
	 * 跳转评价页
	 * @param request
	 * @return
	 */
	@RequestMapping("/evaluate.html")
	public String toOrderComment(HttpServletRequest request){
		return "member/evaluate";
	}
	/**
	 * 添加订单评语
	 * @param orderComment
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addcomment")
	public DatasetSimple<Map<String,String>> addOrderComment(OrderComment orderComment){
		return orderCommentService.addOrderComment(orderComment);
	}
}
