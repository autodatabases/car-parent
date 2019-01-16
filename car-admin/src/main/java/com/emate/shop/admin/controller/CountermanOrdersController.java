package com.emate.shop.admin.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CountermanOrdersService;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping("countermanOrders")
public class CountermanOrdersController implements AuthUtil{
   

	private CountermanOrdersService countermanOrdersService;
	
	@RemoteService
	public void setCountermanOrdersService(CountermanOrdersService countermanOrdersService){
		this.countermanOrdersService = countermanOrdersService;
	}
	/**
	 * admin查询所有订单
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param countermanOrders
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryCountermanOrdersList")
    @AuthAction
    public DatasetList<Map<String,Object>> queryCountermanList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,
    		String orderNo, String countermanPhone,
    		@Required @Regex("[a,0,1,2,3]") String orderStatus,String startTimes,String endTimes) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return countermanOrdersService.adminCountermanOrdersList(Integer.parseInt(pageNo),Integer.parseInt(pageSize),countermanPhone,orderStatus,startTimes,endTimes,orderNo,userId);
    }
    /**
     * 查询订单详情
     * @param request
     * @param countermanOrdersId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/getCountermanOrders", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,Object>> getCountermanOrders(HttpServletRequest request,
			@Required @Regex("\\d+")String countermanOrdersId) throws Exception {
		DatasetSimple<Map<String,Object>> countermanOrders = countermanOrdersService.queryCountermanOrdersById(countermanOrdersId);
		return countermanOrders;
	}
	
	/**
	 * 修改订单备注
	 * @param request
	 * @param orderNo
	 * @param remark
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="addOrderRemark",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<Map<String, Object>> addOrderRemark(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo,@Required String remark) {
    	return countermanOrdersService.addOrderRemark(orderNo, remark);
    }
	/**
	 * 审核商家,修改商家状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOrdersStatus", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,Object>> updateOrdersStatus(HttpServletRequest request,
			@Required @Regex("[1,2]") String status,@Required @Regex("\\d+") String orderNo) throws Exception {
		return countermanOrdersService.updateCountermanOrders(orderNo, status);
	}
}
