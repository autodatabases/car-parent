package com.emate.shop.wechat.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.OrderService;
import com.emate.shop.business.api.SellerService;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.Seller;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.rpc.dto.RpcDtoHelper;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.tools.ExportExcelData;
import com.google.common.base.Objects;

@Controller
@RequestMapping("order")
public class OrderController implements AuthUtil{
	
	private OrderService orderService;
	
	private SellerService sellerService;
	
	@RemoteService
	public void setOrderService(OrderService orderService){
		this.orderService = orderService;
	}
	
	@RemoteService
	public void setSellerService(SellerService sellerService){
		this.sellerService = sellerService;
	}
	
	/**
	 * 查询订单明细 根据订单code
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryOrderDetail")
    @AuthAction
    public DatasetSimple<Map<String,Object>> queryOrderDetail(HttpServletRequest request,String code) {
    	String isByOrderNo = request.getParameter("isByOrderNo");
    	if("yes".equals(isByOrderNo)){
    		return orderService.adminQueryOrderDetail(request.getParameter("orderNo"));
    	}
    	return orderService.sellerQueryOrderDetail(code);
    }
    
    
    /**
	 * 商家确认接单
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("confirmOrder")
    @AuthAction
    public DatasetSimple<Boolean> confirmOrder(HttpServletRequest request,
    		@Required @Regex("\\d+") String orderNo) {
    	DatasetSimple<Orders>  orderData = orderService.h5QueryOrderByNo(orderNo);
    	if(orderData.isSuccess()){
    		Orders order = orderData.getData();
    		if(order.getStatus()!=Orders.STATUS_1){
    			throw new BusinessException("确认订单失败！订单状态不正确！");
    		}
    		if(!Objects.equal(order.getSellerId(), 
    				this.getUserId(request, AuthUtil.CAR_SELLER_TOKEN))){
    			throw new BusinessException("确认订单失败！订单不是该商家订单！");
    		}
    	}else{
    		throw new BusinessException("确认订单失败！订单不存在！");
    	}
    	Orders order = new Orders();
    	order.setId(orderData.getData().getId());
    	order.setStatus(Orders.STATUS_2);
    	return orderService.updateOrder(order);
    }
    
    /**
	 * 商家扫码完成订单
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("completeOrder")
    @AuthAction
    public DatasetSimple<Boolean> completeOrder(HttpServletRequest request,
    		@Required @Regex("\\d+") String orderNo) {
    	String code = request.getParameter("code");
    	DatasetSimple<Orders>  orderData = orderService.h5QueryOrderByNo(orderNo);
    	if(orderData.isSuccess()){
    		Orders order = orderData.getData();
    		if(order.getStatus()!=Orders.STATUS_2){
    			throw new BusinessException("完成订单失败！订单状态不正确！");
    		}
    		if(!order.getCode().equals(code)){
    			throw new BusinessException("完成订单失败！订单券码不正确！");
    		}
    	}else{
    		throw new BusinessException("完成订单失败！订单不存在！");
    	}
    	Orders order = new Orders();
    	order.setId(orderData.getData().getId());
    	order.setStatus(Orders.STATUS_3);
    	order.setFinishTime(new Date());
    	if(orderData.getData().getOrderType() == Orders.ORDER_TYPE_3){//如果是洗车订单 那么需要设置 商家id
    		order.setSellerId(this.getUserId(request,AuthUtil.CAR_SELLER_TOKEN));
    		order.setSupplyId(order.getSellerId());//洗车时自己发货，商家统计报表时候使用
    		Seller s = sellerService.querySellerById(order.getSellerId()+"").getData();
    		if(s!=null){
    			order.setAddressDetail(s.getAddressDetail());
				order.setProvince(s.getProvince());
				order.setCity(s.getCity());
				order.setArea(s.getArea());
				order.setPhone(s.getSellerPhone());
				order.setUserName(s.getName());
    		}
    	}else{
    		if(!Objects.equal(this.getUserId(request,AuthUtil.CAR_SELLER_TOKEN), orderData.getData().getSellerId())){
    			throw new BusinessException("完成订单失败！订单不是该商家订单！");
    		}
    	}
    	return orderService.updateOrder(order);
    }
    
	/**
	 * 查询订单列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryOrderList")
    @AuthAction
    public DatasetList<Map<String,Object>> sellerQueryOrderList(HttpServletRequest request,@Required String orderStatus,
    		@Required Integer pageNo,@Required Integer pageSize) {
    	return orderService.sellerQueryOrderList(getUserId(request,AuthUtil.CAR_SELLER_TOKEN), orderStatus,pageNo,pageSize);
    }
    
    
    /**
	 * 商家获取订单统计
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerGetOrderSummary")
    @AuthAction
    public DatasetSimple<Map<String,Object>> sellerGetOrderSummary(HttpServletRequest request) {
    	return orderService.sellerGetOrderSummary(getUserId(request,AuthUtil.CAR_SELLER_TOKEN));
    }
    
    
    /**
	 * 商家获取金额统计
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerGetMoneySummary")
    @AuthAction
    public DatasetSimple<Map<String,Object>> sellerGetMoneySummary(HttpServletRequest request) {
    	return orderService.sellerGetMoneySummary(getUserId(request,AuthUtil.CAR_SELLER_TOKEN));
    }
    
    /**
	 * 商家今日收入列表
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerTodayIncome")
    @AuthAction
    public DatasetList<Map<String,Object>> sellerTodayIncome(HttpServletRequest request) {
    	return orderService.sellerTodayIncomeList(getUserId(request,AuthUtil.CAR_SELLER_TOKEN));
    }
    
    /**
	 * 商家所有收入明细
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerAllIncome")
    @AuthAction
    public DatasetList<Map<String,Object>> sellerAllIncome(HttpServletRequest request,
    		@Required String date,@Required Integer pageNo,@Required Integer pageSize) {
    	return orderService.sellerAllIncomeList(getUserId(request,AuthUtil.CAR_SELLER_TOKEN),date,pageNo,pageSize);
    }
    
    /**
	 * 商家收入明细
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerIncomeDetail")
    @AuthAction
    public DatasetSimple<Orders> sellerIncomeDetail(HttpServletRequest request,@Required String orderNo) {
    	return orderService.h5QueryOrderByNo(orderNo);
    }
    
    /**
	 * 商家最近一个月的验券记录
	 * @param sellerId
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerCompleteList")
    @AuthAction
    public DatasetList<Map<String,Object>> sellerCompleteList(HttpServletRequest request
    		,@Required Integer pageNo,@Required Integer pageSize) {
    	return orderService.sellerCompleteList(getUserId(request,AuthUtil.CAR_SELLER_TOKEN),pageNo,pageSize);
    }
    
    
	/**
	 * 商家确认结算
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("sellerComfirmReport")
    @AuthAction
    public DatasetSimple<Map<String,Object>> sellerComfirmReport(HttpServletRequest request,@Required String date,@Required @Regex("[0,1]") String invoice) {
    	return orderService.sellerComfirmReport(this.getUserId(request,AuthUtil.CAR_SELLER_TOKEN), date,invoice);
    }
    
    /**
     * 商家导出结算报表
     * @param request
     * @param response
     * @param date
     */
    @RequestMapping("exportSellerExcel")
    public void exportSellerExcel(HttpServletRequest request,HttpServletResponse response,
    		@Required String date) {
    	String token = request.getParameter("token");
    	Log4jHelper.getLogger().error("request get token="+token);
    	if(StringUtils.isEmpty(token)){
    		Log4jHelper.getLogger().error("导出订单失败！token为空！");
    		downloadErr(response);
    		return;
    	}
    	try {
			token = URLDecoder.decode(token,"utf-8");
			token = (String)RpcDtoHelper.decrypt(token);
		} catch (Exception e) {
			e.printStackTrace();
			Log4jHelper.getLogger().error("导出订单失败！token解码失败！token="+token);
			downloadErr(response);
    		return;
		}
    	if(StringUtils.isEmpty(token) || !token.contains("_")){
    		Log4jHelper.getLogger().error("导出订单失败！token格式不正确！");
    		downloadErr(response);
    		return;
    	}
    	String userId = token.split("_")[0];
    	DatasetList<Map<String, String>> data = orderService.exportSellerExcel(Long.parseLong(userId),date);
    	ExportExcelData.export(request, response, data.getList(), "商家结算"+date, 
    			new String[]{"日期","订单编号","车牌号","工时费","服务类型","商品名称","数量","产品结算价","补贴金额"
    					,"补贴类型","门店名称","门店联系方式","结算状态"},
    			new String[]{"orderTime","orderNo","chepai","serviceMoney","orderType","orderGoods","goodsNum","goodsPrice","butie"
    					,"butieType","sellerName","sellerPhone","status"}, "sheet0");
    }
    
    private void downloadErr(HttpServletResponse response){
    	ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			response.setContentType("text/html;charset=GBK");
			response.resetBuffer();
			sos.write("<script language=\"JavaScript\">alert('所查询报表无数据，未能导出报表');</script>".getBytes("GBK"));
			sos.flush();
			sos.close();
			return;
		} catch (IOException ioex) {
			Log4jHelper.getLogger().error("导出订单失败！");
		}
    }
    
	/**
	 * 商家维修单统计
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fixOrderSummary")
    @AuthAction
    public DatasetSimple<Map<String,Object>> sellerFixOrderSummary(HttpServletRequest request) {
    	return orderService.sellerFixOrderSummary(this.getUserId(request, AuthUtil.CAR_SELLER_TOKEN));
    }
    
	/**
	 * 商家维修订单列表，根据状态来查询
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fixOrderList")
    @AuthAction
    public DatasetList<Map<String,Object>> sellerFixOrderList(HttpServletRequest request,
    		@Required @Regex("^[1,2,3,4]$")String orderStatus,
    		@Required Integer pageNo,
    		@Required Integer pageSize) {
    	return orderService.fiexOrderList(this.getUserId(request, AuthUtil.CAR_SELLER_TOKEN), orderStatus,"seller",pageNo,pageSize);
    }
    
	/**
	 * 商家维修订单详情
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fixOrderDetail")
    @AuthAction
    public DatasetSimple<Map<String,Object>> sellerFixOrderDetail(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	return orderService.fiexOrderDetail(this.getUserId(request, AuthUtil.CAR_SELLER_TOKEN), orderNo,"seller");
    }
    
	/**
	 * 商家提交定损
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("commitFixRemark")
    @AuthAction
    public DatasetSimple<Map<String,Object>> commitFixRemark(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	return orderService.sellerCommitFixOrder(this.getUserId(request, AuthUtil.CAR_SELLER_TOKEN), orderNo, null, null);
    }
    
	/**
	 * 商家完成订单
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("completFixOrder")
    @AuthAction
    public DatasetSimple<Map<String,Object>> completFixOrder(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	return orderService.sellerCompletFixOrder(this.getUserId(request, AuthUtil.CAR_SELLER_TOKEN), orderNo);
    }
}
