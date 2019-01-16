package com.emate.shop.admin.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.JicaiOrderService;
import com.emate.shop.business.model.JicaiOrder;
import com.emate.shop.rpc.aop.RemoteService;

import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 
 * @author dong
 *
 */
@Controller
@RequestMapping("jicaiorder")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class JicaiOrderController extends BaseController implements AuthUtil{
	
	//private static Logger logger = Log4jHelper.getLogger();

	private JicaiOrderService jicaiOrderService;
	
	@RemoteService
	public void setJicaiOrderService(JicaiOrderService jicaiOrderService) {
		this.jicaiOrderService = jicaiOrderService;
	}
	
	/**
	 * 查询集彩订单
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param jicaiOrder
	 * @return
	 */
    @ResponseBody
    @RequestMapping("jicaiOrderList")
    @AuthAction
    public DatasetList<JicaiOrder> jicaiOrderList(HttpServletRequest request,
    		Integer pageNo,
    		Integer pageSize,
    		JicaiOrder jicaiOrder
    		){
    	return jicaiOrderService.adminJicaiOrderListPage(pageNo, pageSize,jicaiOrder);
    }
	/**
	 * 更新集彩订单状态
	 * @param request
	 * @param orderNo
	 * @return
	 */
    @ResponseBody
    @RequestMapping("updatejicaiOrder")
    @AuthAction
    public DatasetSimple<Map<String,Object>> updatejicaiOrder(HttpServletRequest request,@Required @Regex("\\d+")String orderNo){
    	return jicaiOrderService.updatejicaiOrder(orderNo);
    }
    
	/**
	 * 查询集彩订单详情
	 * @param request
	 * @param orderNo
	 * @return
	 */
    @ResponseBody
    @RequestMapping("jicaiOrderDetail")
    @AuthAction
    public DatasetSimple<JicaiOrder> fiexOrderDetail(HttpServletRequest request,@Required @Regex("\\d+")String orderNo){
    	return jicaiOrderService.jicaiOrderDetail(orderNo);
    }
}
