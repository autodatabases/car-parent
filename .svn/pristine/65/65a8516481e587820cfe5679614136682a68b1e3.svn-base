package com.emate.shop.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.business.api.OrderService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.Express;
import com.emate.shop.business.model.FixOrder;
import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.WxUserBind;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.tools.ExportExcelData;
import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.MsgUtil;
import com.emate.wechat.MsgUtil.MSG_TYPE;
import com.emate.wechat.ParamesAPI;
import com.emate.wechat.RefreshTokenUtil;
import net.sf.json.JSONArray;

/**
 * 
 * @author likk
 *
 */
@Controller
@RequestMapping("order")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class OrderController extends BaseController implements AuthUtil{
	
	private static Logger logger = Log4jHelper.getLogger();

	private OrderService orderService;
	
	private UserService userService;
	
	private SmsService smsService;
	
	@Resource
    private Environment environment;
	
	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	//private SellerService sellerService;
	
	@RemoteService
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	private AccessTokenService accessTokenService;
	
	//private SmsService smsService;
	
	@RemoteService
	public void setOrderService(OrderService orderService){
		this.orderService = orderService;
	}
	
	@RemoteService
	public void setAccessTokenService(AccessTokenService accessTokenService){
		this.accessTokenService = accessTokenService;
	}
	
//	@RemoteService
//	public void setSellerService(SellerService sellerService){
//		this.sellerService = sellerService;
//	}
	
//	@RemoteService
//	public void setSmsSerive(SmsService smsService){
//		this.smsService = smsService;
//	}
	
	/**
	 * 导出国寿报表--modify by likk 增加导出保单字段
	 * @param request
	 * @param response
	 * @param date
	 * @param sellerId
	 */
    @RequestMapping("exportChinaLifeExcel")
    public void exportChinaLifeExcel(HttpServletRequest request,HttpServletResponse response,
    		@Required String date,Long sellerId,String status,String city) {
    	DatasetList<Map<String, String>> data = orderService.exportChinaLifeExcel(sellerId,date,status,city);
    	String fileName = "国寿结算"+date;
    	if("-1".equals(status)){
    		fileName = "全部保单"+date;
    	}
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"日期","订单编号","工时费","服务类型","商品名称","数量","产品结算价","产品单价","商家结算价","配送费","客户姓名"
    					,"客户电话","城市","门店地址","门店名称","门店联系方式","保单号","所属公司","车辆品牌","车型","排量","年份","车牌","车架号","公里数"},
    			new String[]{"orderTime","orderNo","serviceMoney","orderType","orderGoods","goodsNum","goodsPrice","oneGoodsPrice","sellerGoodsPrice","expressMoney","userName"
    					,"userPhone","city","sellerAddr","sellerName","sellerPhone","baodan","company","autoBrand","autoType","engineDisp"
    					,"year","chepai","chejia","mileage"}, "sheet0");
    }
    
	/**
	 * 导出国寿报表2
	 * @param request
	 * @param response
	 * @param date
	 * @param status
	 */
    @RequestMapping("exportChinaLifeExcelTwo")
    public void exportChinaLifeExcelTwo(HttpServletRequest request,HttpServletResponse response,
    		@Required String date,String status) {
    	DatasetList<Map<String, String>> data = orderService.exportChinaLifeExcelTwo(date,status);
    	String fileName = "国寿结算"+date;
    	if("-1".equals(status)){
    		fileName = "全部保单"+date;
    	}
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"订单日期","姓名","手机号","车牌","服务类型","服务次数","结算价格","商家名称","商家电话"},
    			new String[]{"orderTime","userName","userPhone","license","orderType","orderNum","totalPrice","sellerName","sellerPhone"}, "sheet0");
    }
    
    @ResponseBody
    @RequestMapping("resetSellerReport")
    @AuthAction
    public DatasetSimple<Map<String,Object>> resetSellerReport(HttpServletRequest request,@Required String date) {
    	return orderService.resetSellerReport(date);
    }
    
    @RequestMapping("exportUnCheckOrderToExcel")
    public void exportUnCheckOrderToExcel(HttpServletRequest request,HttpServletResponse response) {
    	DatasetList<Map<String, String>> data = orderService.exportUnCheckOrderToExcel();
    	StringBuffer strBuffer = new StringBuffer();
    	strBuffer.append(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
    	strBuffer.append("导出的待审核订单列表");
    	String fileName = strBuffer.toString();
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"结算类型","日期","订单号","姓名","车牌","电话","门店名称","门店联系人","门店电话","门店地址"
    					,"机油","机滤","状态","运单号","备注"},
    			new String[]{"settleType","orderTime","orderNo","userName","chepai","userPhone","sellerName","sellerUserName","sellerPhone","sellerAddr"
    					,"jiyou","jilv","s1","s2","remark"}, "sheet0");
    }
	
    @ResponseBody
    @RequestMapping("checktoken")
    public DatasetSimple<String> checkToken(HttpServletRequest request) {
    	try {
			AccessTokenVo.access_token = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,0,this.accessTokenService);
		} catch (Exception e) {
			e.printStackTrace();
			return DatasetBuilder.fromMessageSimple(e.getMessage());
		}
    	return DatasetBuilder.fromDataSimple(AccessTokenVo.access_token);
    }
    
	/**
	 * 管理员查看订单列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryOrderList")
    @AuthAction
    public DatasetList<Map<String,Object>> queryOrderList(HttpServletRequest request,
    		@Required String pageNo,
    		@Required String pageSize,
    		String orderNo, String phone,String license,
    		String orderType,
    		@Required String orderStatus,String startTimes,String endTimes) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return orderService.adminQueryOrderList(userId,orderNo, phone, license,
    			Integer.parseInt(pageNo), Integer.parseInt(pageSize),orderStatus,startTimes,endTimes,orderType);
    }
    
	/**
	 * 管理员订单提醒功能
	 * @param request
	 * @return
	 */
/*    @ResponseBody
    @RequestMapping("hasNewOrder")
    @AuthAction
    public DatasetSimple<Map<String,String>> hasNewOrder(HttpServletRequest request,String userName,String userAgent,String unid) {
    	return orderService.hasNewOrder(unid,userName,IPUtil.getIP(request),userAgent);
    }*/
    
	/**
	 * 管理员发货
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="deliverComplet",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<Map<String,String>> deliverOrder(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo,@Required @Regex("\\d+")String mobile,@Required @Regex("\\d+")String sellerPhone) {
		try {
			
			JSONArray mobileArray = new JSONArray();
			mobileArray.add(mobile);
			JSONArray params = new JSONArray();
			params.add(orderNo);
//			params.add(ShotUrl.toShortUrl(environment.getProperty("sms.domain", "http://gd.emateglobal.com")
//					+ "/order/orderok?orderNo=" + orderNo));
			params.add("http://gd.emateglobal.com");
			params.add(sellerPhone);
			DatasetSimple<String> smsResult = smsService.sendSmsTmp("3031546", mobileArray.toString(),
					params.toString(),SmsService.SMS_TYPE_0);
			if (!smsResult.isSuccess()) {
				logger.error("下发短信失败！" + smsResult.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下发短信失败！", e);
		}
    	return orderService.deliverOrder(orderNo);
    }
    
	/**
	 * 查询订单详情
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryOrderDetail")
    @AuthAction
    public DatasetSimple<Map<String,Object>> queryOrderDetail(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	return orderService.adminQueryOrderDetail(orderNo);
    }
    
	
	/**
	 * 管理员确认订单
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("confirmOrder")
    @AuthAction
    public DatasetSimple<Boolean> confirmOrder(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	DatasetSimple<Orders> orders = orderService.h5QueryOrderByNo(orderNo);
    	if(!orders.isSuccess()){
    		throw new BusinessException("订单找不到！");
    	}
    	Orders order = orders.getData();
    	if(order.getStatus()!=Orders.STATUS_0){
    		throw new BusinessException("订单状态不正确！");
    	}
    	
    	//微信推送订单给商家
    	if(order.getSellerId()!=null){
    		try{
    			AccessTokenVo.access_token = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,0,this.accessTokenService);
    			DatasetSimple<WxUserBind>  wxBindresult  = accessTokenService.queryBind(order.getSellerId());
    			if(!wxBindresult.isSuccess()){
    				return DatasetBuilder.fromMessageSimple("下单失败，商家未绑定微信！");
    			}
    			String orderType = null,userInfo= null,addressInfo=null;
    			if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_0)){
    				orderType = "更换电瓶";
    				userInfo = order.getUserName() + " " + order.getPhone();
    				addressInfo = order.getProvince() + order.getCity() + order.getArea() + order.getAddressDetail();
    			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_1)){
    				orderType = "更换轮胎";
    				addressInfo = "到店服务";
    			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_2)){
    				orderType = "小保养";
    				addressInfo = "到店服务";
    			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_3)){
    				orderType = "洗车";
    				addressInfo = "到店服务";
    			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_4)){
    				orderType = "喷漆";
    				addressInfo = "到店服务";
    			}
    			if(userInfo==null){
    				DatasetSimple<User> userData = userService.queryUserById(order.getUserId()+"");
    				if(userData.isSuccess()){
    					userInfo = userData.getData().getName();
    				}else{
    					userInfo = "--";
    				}
    			}
				WxUserBind bind = wxBindresult.getData();
				String[] params = new String[] { 
						ParamesAPI.getDomian(ParamesAPI.DEFAULT_SYSID)+"/wechat/order/order-details.html?orderNo="+order.getOrderNo(),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()),
						orderType,
						userInfo,
						addressInfo
						};
				MsgUtil.sendTemplateMsg(MSG_TYPE.NewOrder, bind.getOpenId(), params);
    		}catch(Exception e){
    			logger.error("微信推送订单失败！",e);
    			return DatasetBuilder.fromMessageSimple("微信推送订单失败!");
    		}
    	}

		Orders updateOrder = new Orders();
		updateOrder.setId(order.getId());
		if(Orders.ORDER_TYPE_4.equals(order.getOrderType())){
			updateOrder.setStatus(Orders.STATUS_2);
		}else{
			updateOrder.setStatus(Orders.STATUS_1);//系统已经确认
		}
/*		if(0 != Long.parseLong(supplierId) || Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_4)){//如果是喷漆订单那么都是商家的
			updateOrder.setSupplyId(order.getSellerId());//是否是垫货，0表示是自己发货，其他表示是商家垫货
		}else{
			updateOrder.setSupplyId(0L);
		}*/
		DatasetSimple<Boolean> result = orderService.updateOrder(updateOrder);
		if(result.isSuccess() && 
				(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_3)
						|| Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_2))){//下发短信通知
			try {
				JSONArray mobileArray = new JSONArray();
				mobileArray.add(order.getPhone());
				JSONArray params = new JSONArray();
				params.add(orderNo);
//				params.add(ShotUrl.toShortUrl(environment.getProperty("sms.domain", "http://gd.emateglobal.com")
//						+"/wechat/order/order-details.html?orderNo="+order.getOrderNo()));
				params.add("http://gz.emateglobal.com/wechat");
				
				DatasetSimple<User> userData = userService.queryUserById(order.getUserId()+"");
				if(userData.isSuccess()&&userData.getData()!=null){
					params.add(userData.getData().getName());
				}else{
					params.add("--");
				}
				DatasetSimple<String> smsResult = smsService.sendSmsTmp("3039004", mobileArray.toString(),
						params.toString(),SmsService.SMS_TYPE_0);
				if (!smsResult.isSuccess()) {
					logger.error("下发短信失败！" + smsResult.getMessage());
				}else{
					logger.error("下发短信成功！" + smsResult.getData());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("下发短信失败！", e);
			}
		}
    	return result;
    }
    
    /**
	 * 清空新订单提醒
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("deleteNewOrderAlter")
    @AuthAction
    public DatasetSimple<Map<String,String>> deleteNewOrderAlter(HttpServletRequest request){
    	return orderService.deleteNewOrderAlter();
    }
    
	/**
	 * 国寿审核维修订单列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fiexOrderList")
    @AuthAction
    public DatasetList<Map<String,Object>> fiexOrderList(HttpServletRequest request,
    		Integer pageNo,
    		Integer pageSize,
    		FixOrder filter
    		){
    	return orderService.adminFixOrderListPage(filter, pageNo, pageSize);
    }
    
	/**
	 * 国寿审核维修订单详情
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("fiexOrderDetail")
    @AuthAction
    public DatasetSimple<Map<String,Object>> fiexOrderDetail(HttpServletRequest request,@Required @Regex("\\d+")String orderNo){
    	return orderService.fiexOrderDetail(this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN),orderNo,"admin");
    }
    
    
	/**
	 * 国寿审核维修订单
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("checkFixOrder")
    @AuthAction
    public DatasetSimple<Map<String,Object>> checkFixOrder(HttpServletRequest request,@Required @Regex("\\d+")String orderNo){
    	return orderService.adminCheckFixOrder(orderNo);
    }
    
	/**
	 * 查询物流列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryExpressList")
    @AuthAction
    public DatasetList<Express> queryExpressList(HttpServletRequest request){
    	return orderService.queryExpressList();
    }
    
	/**
	 * 管理员发货
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="deliveOrder",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<Map<String,String>> deliveOrder(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo,@Required @Regex("\\d+")String expressId,@Required @Regex("\\d+")String expressNo) {
    	return orderService.deliverOrder(orderNo,Long.parseLong(expressId),expressNo);
    }
    
	/**
	 * 查看物流详情
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="queryExpressInfo",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<OrderExpress> queryExpressInfo(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo) {
    	return orderService.queryExpressInfo(orderNo);
    }
    
    
	/**
	 * 修改运单号码
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="saveExpressStatus",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<Map<String, Object>> saveExpressStatus(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo,@Required @Regex("\\d+")String expressNo) {
    	
    	return orderService.editExpressNo(orderNo,expressNo);
    }
    
    
    /**
     * 导出商家结算列表
     * @param request
     * @param response
     * @param date
     * @param sellerId
     */
    @RequestMapping("exportOrderExcel")
    public void exportOrderExcel(HttpServletRequest request,HttpServletResponse response,
    		@Required String date,@Required Long sellerId) {
    	DatasetList<Map<String, String>> data = orderService.exportSellerExcel(sellerId,date);
    	ExportExcelData.export(request, response, data.getList(), "商家结算"+date, 
    			new String[]{"日期","订单编号","车牌号","工时费","服务类型","商品名称","数量","产品结算价"
    					,"门店地址","门店名称","门店联系方式","结算状态"},
    			new String[]{"orderTime","orderNo","chepai","serviceMoney","orderType","orderGoods","goodsNum","goodsPrice",
    					"sellerAddr","sellerName","sellerPhone","status"}, "sheet0");
    }
    
    /**
     * 导出所有商家结算列表
     * @param request
     * @param response
     * @param date
     */
    @RequestMapping("exportAllOrderExcel")
    public void exportAllOrderExcel(HttpServletRequest request,HttpServletResponse response,@Required String date) {
    	DatasetList<Map<String, String>> data = orderService.exportAllSellerExcel(date);
    	ExportExcelData.export(request, response, data.getList(), "商家结算"+date, 
    			new String[]{"日期","订单编号","车牌号","工时费","服务类型","商品名称","数量","产品结算价"
    					,"门店地址","门店名称","门店联系方式","开户名称","开户账号","开户行","结算金额","结算状态"},
    			new String[]{"orderTime","orderNo","chepai","serviceMoney","orderType","orderGoods","goodsNum","goodsPrice",
    					"sellerAddr","sellerName","sellerPhone","accountName","accountNumber","account","jiePrice","status"}, "sheet0");
    }
    
    
	/**
	 * 修改运单号码
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="addOrderRemark",method=RequestMethod.POST)
    @AuthAction
    public DatasetSimple<Map<String, Object>> addOrderRemark(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo,@Required String remark) {
    	return orderService.addOrderRemark(orderNo, remark);
    }
    
	/**
	 * admin提交定损
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("commitFixRemark")
    @AuthAction
    public DatasetSimple<Map<String,Object>> commitFixRemark(HttpServletRequest request,
    		@Required @Regex("\\d+")String orderNo,
    		@Required String price,
    		@Required String remark) {
    	return orderService.adminCommitFixOrder(orderNo, price, remark);
    }
    
    @RequestMapping("exportOrderCountToExcel")
    public void exportOrderCountToExcel(HttpServletRequest request,HttpServletResponse response,String date,@Regex("[2,3,4]") String orderType) {
    	//DatasetList<Map<String, String>> data = orderService.getOrderCompData(date,orderType);
    	DatasetList<Map<String, String>> data = orderService.getOrderCompDataTwo(date,orderType);
    	int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		List<String> dateStr = new ArrayList<String>();
    	Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
    	dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		String[] a = new String[dateStr.size()+1];
		a[0] = "分支公司名称";
		int i=1;
		for(String s:dateStr){
			a[i++] = s;
		}
		String[] b = new String[dateStr.size()+1];
		b[0] = "comName";
		i=1;
		for(String s:dateStr){
			b[i++] = s;
		}
		String name="每日下单量数据统计";
		if("2".equals(orderType)){
			name = "每日下小保养的订单量数据统计";
		}else if("3".equals(orderType)){
			name = "每日下洗车的订单量数据统计";
		}else{
			name = "每日下喷漆的订单量数据统计";
		}
    	ExportExcelData.export(request, response, data.getList(), name+date, 
    			a,
    			b, "sheet0");
    }
    
    @RequestMapping("exportRegisterCountToExcel")
    public void exportRegisterCountToExcel(HttpServletRequest request,HttpServletResponse response,String date) {
    	DatasetList<Map<String, String>> data = orderService.getRegisterCompData(date);
    	int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		List<String> dateStr = new ArrayList<String>();
    	Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
    	dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		String[] a = new String[dateStr.size()+1];
		a[0] = "分支公司名称";
		int i=1;
		for(String s:dateStr){
			a[i++] = s;
		}
		String[] b = new String[dateStr.size()+1];
		b[0] = "comName";
		i=1;
		for(String s:dateStr){
			b[i++] = s;
		}
    	ExportExcelData.export(request, response, data.getList(), "每日下注册数据统计"+date, 
    			a,
    			b, "sheet0");
    }
	
    
    
    @RequestMapping("exportBaodanCountToExcel")
    public void exportBaodanCountToExcel(HttpServletRequest request,HttpServletResponse response,String date) {
    	DatasetList<Map<String, String>> data = orderService.getBaodnaData(date);
    	int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		List<String> dateStr = new ArrayList<String>();
    	Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
    	dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		String[] a = new String[dateStr.size()+1];
		a[0] = "分支公司名称";
		int i=1;
		for(String s:dateStr){
			a[i++] = s;
		}
		String[] b = new String[dateStr.size()+1];
		b[0] = "comName";
		i=1;
		for(String s:dateStr){
			b[i++] = s;
		}
    	ExportExcelData.export(request, response, data.getList(), "每日保单数据统计"+date, 
    			a,
    			b, "sheet0");
    }
    
    /**
     * 管理员强制完成订单
     * @param request
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping("completeOrder")
    @AuthAction
    public DatasetSimple<String> completeOrder(HttpServletRequest request,
       	@Required @Regex("\\d+") String orderNo) {
       	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
       	return orderService.completeOrder(orderNo,userId);
    }
       
    /**
     * 管理员强制取消订单
     * @param request
     * @param orderNo
     * @return
     */
   @ResponseBody
   @RequestMapping("cancelOrder")
   @AuthAction
   public DatasetSimple<String> cancelOrder(HttpServletRequest request,
       @Required @Regex("\\d+") String orderNo) {
       Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
       return orderService.cancelOrder(orderNo,userId);
   }
}
