/**
 * 
 */
package com.emate.shop.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.OrderService;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;


/**
 * 登录
 * @author likk
 *
 */
@Controller
@RequestMapping("index")
public class IndexController implements AuthUtil{


	private OrderService orderService;

	@RemoteService
    public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


	/**
     * 管理员首页接口
     * @param request
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping("adminHomeSummary")
    public DatasetSimple<Map<String,Object>> adminHomeSummary(HttpServletRequest request) {
    	return orderService.adminHomeSummary(this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN));
    }
}
