package com.emate.shop.admin.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.OrderCommentService;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.validator.Regex;

/**
 * 订单评价表
 * @author dong
 *
 */
@Controller
@RequestMapping("/ordercomment")
public class OrderCommentController {

	private OrderCommentService orderCommentService;
	
	@RemoteService
	public void setOrderCommentService(OrderCommentService orderCommentService) {
		this.orderCommentService = orderCommentService;
	}
	
	/**
	 * 分页查询订单评价表
	 * @param pageNo
	 * @param pageSize
	 * @param orderComment
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/adminquery")
	public DatasetList<Map<String,String>> adminquery(@Regex("\\d+")String pageNo,@Regex("\\d+")String pageSize,
			String orderNo,String userPhone,String sellerName){
		DatasetList<Map<String, String>> orderComments = orderCommentService.adminQuery(Integer.valueOf(pageSize), Integer.valueOf(pageNo), orderNo,userPhone,sellerName);
		return orderComments;
	}

	/**
	 * 根据条件查询订单评价
	 * @param orderNo
	 * @param sellerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryBy")
	public DatasetList<Map<String,String>> queryByCondition(String orderNo,String sellerId){
		DatasetList<Map<String, String>> orderComments = orderCommentService.
				queryByCondition(orderNo, Long.valueOf(sellerId));
		return orderComments;
	}
	
	/**
	 * 更新订单评语，添加运营回访记录
	 * @param id
	 * @param remark
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateordercomment")
	public DatasetSimple<String> updateOrderComment(String id,String remark){
		DatasetSimple<String> result = orderCommentService.
					updateOrderComment(id,remark);
		return result;
	}
	
}
