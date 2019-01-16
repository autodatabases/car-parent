package com.emate.shop.h5.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.business.api.OrderService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.FixOrder;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.PowerPrice;
import com.emate.shop.business.model.WxUserBind;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.QRCodeUtil;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.MsgUtil;
import com.emate.wechat.MsgUtil.MSG_TYPE;
import com.emate.wechat.ParamesAPI;
import com.emate.wechat.RefreshTokenUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="order")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class OrderController implements AuthUtil{
	
	private static Logger logger = Log4jHelper.getLogger();
	
	private OrderService orderService;
	
	private UserService userService;
	
	private SmsService smsService;
	
	private AccessTokenService accessTokenService;
	
	@Resource
    private Environment environment;
	
	
	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	@RemoteService
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RemoteService
	public void setOrderService(OrderService orderService){
		this.orderService = orderService;
	}
	
	@RemoteService
	public void setAccessTokenService(AccessTokenService accessTokenService){
		this.accessTokenService = accessTokenService;
	}
	
	/**
	 * 生成二维码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getQrCode")
	public void getQrCode(HttpServletRequest request,
			HttpServletResponse respons,String orderNo) throws Exception {
		QRCodeUtil.createQRCodeImage(orderNo, 180, 180, "png", respons.getOutputStream());
	}
	
	
	
	/**
	 * 匹配不到商品界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ordererr")
	@AuthAction
	public String orderErr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "repair/powerorder-err";
	}
	
	/**
	 * 订单提交成功界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/success.html")
	@AuthAction
	public String ordersuccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "repair/success";
	}
	
	/**
	 * 订单ok界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderok")
	@AuthAction
	public String orderok(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "repair/powerorder-ok";
	}
	
	/**
	 * 选择轮胎界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tyre-select")
	@AuthAction
	public String tyreSelect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "repair/tyre-select";
	}
	
	
	/**
	 * 订单追踪页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderTrace.html")
	@AuthAction
	public String orderTrace(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "repair/order-tracking";
	}
	
	/**
	 * 提交订单
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
    @ResponseBody
    @RequestMapping("createOrder")
    @AuthAction
    public DatasetSimple<Map<String,Object>> addOrder(HttpServletRequest request,String selectedSellerId,
    		String goodsName,String province,String city,String area,String addressDetail,String userName,
    		String phone,String goodsId,String goodsNum,
    		@Required @Regex("^[0,2,3,4]$")String orderType) throws ParseException {
    	//加入订单时间限制
    	SimpleDateFormat sm =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date startTime = null;
		try {
			startTime = sm.parse("2018-02-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("日期转化异常~~");
		}
    	Date endTime = null;
		try {
			endTime = sm.parse("2018-03-08 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("日期转化异常~~");
		}
    	Date now1 = new Date();
    	if(now1.after(startTime) && now1.before(endTime)){
    		throw new BusinessException("春节期间2月1日-3月7日由于店铺休息无法接单，请谅解！");
    	}
    	
    	//String addressinfo = request.getParameter("addressinfo");
    	Long userId = getUserId(request,AuthUtil.CAR_H5_TOKEN);
		Orders order = new Orders();//创建订单
		order.setUserId(userId);//用户id
		order.setOrderNo(RandomUtil.getOrderSn());//订单号
		order.setGoodsId(goodsId);//商品id
		order.setArea(area);//商家所处区
		order.setCity(city);//商家所处城市
		order.setProvince(province);//商家所处省
		order.setAddressDetail(addressDetail);//商家所处详细地址
		order.setCreateTime(new Date());//创建时间
		order.setUpdateTime(order.getCreateTime());//更新时间
		order.setUserName(userName);//
		order.setPhone(phone);
		order.setOrderRemark(goodsName);
		order.setStatus(Orders.STATUS_0);
		order.setOrderType((byte)Integer.parseInt(orderType));
		
		//设置商品数量，如果是机油，传的值是L
		try{
			if(StringUtils.isNotEmpty(goodsNum)){
				order.setGoodsNum(Integer.parseInt(goodsNum));
			}else{
				order.setGoodsNum(1);
			}
			
		}catch(Exception e){
			order.setGoodsNum(1);
		}
		//商家id
		if(StringUtils.isNotEmpty(selectedSellerId)
				&& selectedSellerId.matches("\\d+")){
			order.setSellerId(Long.parseLong(selectedSellerId));
		}
		//如果是小保养,设置用户里程数
		if(order.getOrderType() == Orders.ORDER_TYPE_2){
			String mileage = request.getParameter("mileage");
			if(mileage!=null && mileage.matches("\\d+")){
				order.setMileage(Integer.parseInt(mileage));
			}else{
				order.setMileage(0);
			}
		}
		//若是洗车,直接服务中
		if(order.getOrderType()==Orders.ORDER_TYPE_3){
			order.setStatus(Orders.STATUS_2);
			//order.setSellerId(null);//洗车不需要绑定商家
		}
		
		DatasetSimple<String> serviceResult = orderService.addOrder(order);
		
		//给用户发送短信(订单号,客服联系方式,车服地址)
		if(serviceResult.isSuccess()){
			try {
				String mobile = userService.queryUserById(userId+"").getData().getName();
				JSONArray mobileArray = new JSONArray();
				mobileArray.add(mobile);

				JSONArray params = new JSONArray();
				params.add(serviceResult.getData());
				params.add("http://gd.emateglobal.com");
				params.add("400-8671993");
//				params.add(ShotUrl.toShortUrl(environment.getProperty("sms.domain", "http://gd.emateglobal.com")
//				+ "/order/orderok?orderNo=" + order.getOrderNo()));
				DatasetSimple<String> smsResult = smsService.sendSmsTmp("3031415", mobileArray.toString(),
						params.toString(),SmsService.SMS_TYPE_0);
				if (!smsResult.isSuccess()) {
					logger.error("下发短信失败！" + smsResult.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("下发短信失败！", e);
			}
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("orderNo", order.getOrderNo());
			return DatasetBuilder.fromDataSimple(result);
		}
    	return DatasetBuilder.fromMessageSimple(serviceResult.getMessage());
    }
    
	/**
	 * 根据订单明细查询订单
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryOrderByOrderNo")
    public DatasetSimple<Orders> queryOrderByOrderNo(HttpServletRequest request,@Required String orderNo) {
    	//Long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
    	DatasetSimple<Orders> order = orderService.h5QueryOrderByNo(orderNo);
    	//if(order.getData().getUserId() != userId){
    		//throw new BusinessException("无权查看!");
    	//}
    	return order;
    }
	
    
	/**
	 * 用户查询自己的订单列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryOrderList")
    @AuthAction
    public DatasetList<Map<String,Object>> queryOrderList(HttpServletRequest request,@Required String status) {
    	return orderService.queryOrderList(this.getUserId(request,AuthUtil.CAR_H5_TOKEN), status);
    }
    
	/**
	 * 查询用户匹配电源
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("matchPowerProduct")
    @AuthAction
    public DatasetSimple<PowerPrice> matchPowerProduct(HttpServletRequest request) {
    	return orderService.matchPowerProduct(getUserId(request,AuthUtil.CAR_H5_TOKEN));
    }
    
    
	/**
	 * 查询用户匹配轮胎
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("matchTyreProduct")
    @AuthAction
    public  DatasetList<Map<String,Object>> matchTyreProduct(HttpServletRequest request) {
    	return orderService.matchTyreProduct(this.getUserId(request,AuthUtil.CAR_H5_TOKEN));
    }
    
    
	/**
	 * 查询用户匹配机油
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("matchOilProduct")
    @AuthAction
    public  DatasetList<Map<String,Object>> matchOilProduct(HttpServletRequest request) {
    	return orderService.matchOilProductNew(this.getUserId(request,AuthUtil.CAR_H5_TOKEN));
    }
    
	/**
	 * 查询用户匹配机滤
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("matchJilvProduct")
    @AuthAction
    public  DatasetList<Map<String,Object>> matchJilvProduct(HttpServletRequest request) {
    	return orderService.matchJilvProductNew(this.getUserId(request,AuthUtil.CAR_H5_TOKEN));
    }
    
	/**
	 * 查询订单记录
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("getOrderTrace")
    @AuthAction
    public  DatasetSimple<Map<String,Object>>  getOrderTrace(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	return orderService.getOrderTrace(orderNo);
    }
    
    
	/**
	 * 提交维修订单
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("createFixOrder")
    @AuthAction
    public DatasetSimple<Map<String,Object>> createFixOrder(HttpServletRequest request,
    		FixOrder fixOrder,@Required @Regex("^[1,2,3]$")String step){
    	//如果处在提交资料流程,需要验证参数是否完整,是否合乎格式
    	if(fixOrder.getStep()==FixOrder.STEP_1){
    		//车牌不能为空
    		if(StringUtils.isEmpty(fixOrder.getChePai())){
    			throw new BusinessException("请输入车牌！");
    		}
    		fixOrder.setOutTime(new Date());
    		//出险人不能为空
    		if(StringUtils.isEmpty(fixOrder.getUserName())){
    			throw new BusinessException("请输入出险人姓名！");
    		}
    		fixOrder.setOutTime(new Date());
    		//添加业务员的id
    		fixOrder.setBusinessId(this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
    	}
    	DatasetSimple<Map<String,Object>> serviceResult = orderService.createFixOrder(fixOrder);
    	//如果是选择商家阶段
    	if(fixOrder.getStep()==FixOrder.STEP_3 && serviceResult.isSuccess()){
    		//如果处在选择商家步骤
    		fixOrder = (FixOrder)serviceResult.getData().get("order");
    		//向商家推送此订单
    		pushOrderToSeller(fixOrder);
    	}
    	//向前端页面发送维修订单的id
    	if(serviceResult.isSuccess()){
    		fixOrder = (FixOrder)serviceResult.getData().get("order");
    		Map<String,Object> result = new HashMap<String,Object>();
    		result.put("id", fixOrder.getId());
    		return DatasetBuilder.fromDataSimple(result);
    	}
    	return serviceResult;
    }
    
    private void pushOrderToSeller(FixOrder fixOrder){
		try{
			AccessTokenVo.access_token = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,0,this.accessTokenService);
			DatasetSimple<WxUserBind>  wxBindresult  = accessTokenService.queryBind(fixOrder.getSellerId());
			if(!wxBindresult.isSuccess()){
				logger.error("微信推送订单失败！");
				return;
			}
			String orderType = "维修订单",userInfo= fixOrder.getUserName() +" " +fixOrder.getUserPhone()==null?"":fixOrder.getUserPhone(),addressInfo="到店服务";
			WxUserBind bind = wxBindresult.getData();
			String[] params = new String[] { 
					ParamesAPI.getDomian(ParamesAPI.DEFAULT_SYSID)+"/wechat/repair/order-detail.html?orderNo="+fixOrder.getOrderNo(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fixOrder.getCreateTime()),
					orderType,
					userInfo,
					addressInfo
					};
			MsgUtil.sendTemplateMsg(MSG_TYPE.NewOrder, bind.getOpenId(), params);
		}catch(Exception e){
			logger.error("微信推送订单失败！",e);
		}
    }
    
	/**
	 * 业务员订单列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fiexOrderList")
    @AuthAction
    public DatasetList<Map<String,Object>> fiexOrderList(HttpServletRequest request,
    		@Regex("^[0,1,2,3,4]$")String orderStatus,
    		@Required Integer pageNo,
    		@Required Integer pageSize){
    	return orderService.fiexOrderList(this.getUserId(request, AuthUtil.CAR_H5_TOKEN),orderStatus,"bussiness",pageNo,pageSize);
    }
    
	/**
	 * 业务员订单详情
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fiexOrderDetail")
    @AuthAction
    public DatasetSimple<Map<String,Object>> fiexOrderDetail(HttpServletRequest request,@Required @Regex("\\d+")String orderNo){
    	return orderService.fiexOrderDetail(this.getUserId(request, AuthUtil.CAR_H5_TOKEN),orderNo,"bussiness");
    }
    
    
 	/**
 	 * 删除订单
 	 * @param request
 	 * @return
 	 */
     @ResponseBody
     @RequestMapping("deleteOrder")
     @AuthAction
     public DatasetSimple<Map<String,Object>> deleteOrder(HttpServletRequest request,@Required @Regex("\\d+")String orderNo){
     	return orderService.deleteOrder(orderNo,this.getUserId(request, AuthUtil.CAR_H5_TOKEN));
     }
}
