package com.emate.shop.admin.controller;


import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.DriverOrderShengService;
import com.emate.shop.business.model.DriverOrderSheng;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Required;


/**
 * 代驾订单
 * @author dong
 *
 */
@Controller
@RequestMapping("driverorder")
public class DriverOrderController {
	
	private DriverOrderShengService driverOrderService;
   
	@RemoteService
	public void setDriverOrderService(DriverOrderShengService driverOrderService) {
		this.driverOrderService = driverOrderService;
	}
	
	 /**
	  * 查询代驾订单
	  * @param request
	  * @param pageNo
	  * @param pageSize
	  * @param phone
	  * @param orderStatus
	  * @param orderNo
	  * @return
	  */
    @RequestMapping(value="driverlist",method=RequestMethod.POST)
    @ResponseBody
    @AuthAction
    public DatasetList<DriverOrderSheng> driverList(HttpServletRequest request,@Required Integer pageNo,
    		@Required Integer pageSize,String phone,String orderStatus,String orderNo){
        return driverOrderService.adminQueryOrderList(orderNo, phone,
        		pageNo, pageSize, orderStatus);
    }
    
	/**
	 * 修改订单备注
	 * @param request
	 * @param orderNo
	 * @param remark
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="driverremark",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<Map<String, String>> updateDriverRemark(HttpServletRequest request,
    		@Required String orderNo,@Required String remark) {
    	return driverOrderService.updateDriverRemark(orderNo, remark);
    }
}
