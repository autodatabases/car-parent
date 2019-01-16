package com.emate.shop.h5.controller;

import java.text.ParseException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.DriverOrderShengService;
import com.emate.shop.business.model.DriverOrderSheng;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Required;


@Controller
@RequestMapping(value="driver")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class DriverOrderController implements AuthUtil{
	
	private DriverOrderShengService driverOrderService;
	
	
	@Resource
    private Environment environment;
	

	@RemoteService
	public void setDriverOrderService(DriverOrderShengService driverOrderService){
		this.driverOrderService = driverOrderService;
	}
	
	private String checkLoginJson(HttpServletRequest request){
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			throw new BusinessException("用户未登陆！");
		}
		return null;
	}
	
	/**
	 * 代驾主页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order.html")
	public String toOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/daijia/daijia";
	}
	/**
	 * 代驾服务标准页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/services.html")
	public String toServices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/daijia/services";
	}

	
	/**
	 * 代驾订单记录页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/record.html")
	public String orderRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/daijia/daijia_record";
	}
	
	/**
	 * 搜索城市页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/citygps.html")
	public String cityGps(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/daijia/city-GPS";
	}
	
	/**
	 * 订单提交成功界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/success.html")
	public String ordersuccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/daijia/daijia_detail";
	}
	
	/**
	 * 代驾规则页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rule.html")
	public String driverRules(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/daijia/daijia_rules";
	}
	
	/**
	 * 提交订单
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
    @ResponseBody
    @RequestMapping("createOrder")
    public DatasetSimple<Map<String,Object>> createOrder(HttpServletRequest request,
    		@Required String startAddress,@Required String endAddress,
    		@Required String longitude,@Required String latitude,
    		@Required String deLongitude,@Required String deLatitude,
    		@Required String appointmentTime,@Required String money,
    		@Required String city,@Required String userName,
    		@Required String userPhone,@Required String userSex){
    	
    	//检验登录
    	checkLoginJson(request);
    	Long userId = getUserId(request,AuthUtil.CAR_OIL_TOKEN);
    	//校验参数不为空
    	if(StringUtils.isEmpty(startAddress)){
    		throw new BusinessException("起始点参数为空");
    	}
       	if(StringUtils.isEmpty(endAddress)){
    		throw new BusinessException("终点参数为空");
    	}
       	if(StringUtils.isEmpty(appointmentTime)){
    		throw new BusinessException("预约时间参数为空");
       	}
       	if(!city.endsWith("市")){
       		throw new BusinessException("城市参数格式不对~");
       	}
       	//校验金额是否正确
       	ArrayList<String> cityList = new ArrayList<String>();
       	cityList.add("广州市");
       	cityList.add("深圳市");
       	cityList.add("东莞市");
       	if(cityList.contains(city)){
       		if(!"130".equals(money)){
           		throw new BusinessException("代驾金额不对~");
           	}
       	}else{
       		if(!"120".equals(money)){
           		throw new BusinessException("代驾金额不对~");
           	}
       	};
       	
       	//先扣除账户余额
       	//oilUserService.modifyUserMoney(userId, -Integer.valueOf(money));

		//盛大代驾接口参数信息
		//代驾请求地址
    	String orderUrl = environment.getRequiredProperty("shengda.driver.orderurl");
    	//代驾请求source
    	String source = environment.getRequiredProperty("shengda.driver.source");
    	//代驾标志
    	String driverType = environment.getRequiredProperty("shengda.driver.driverType");
    	//代价请求key
    	String key = environment.getRequiredProperty("shengda.driver.key");
    	
    	Map<String, String> params = new HashMap<String,String>();
    	if("1".equals(userSex)){
    		userName= userName+"女士";
    	}else{
    		userName= userName+"先生";
    	}
		params.put("realName", userName);//被代驾人
		params.put("tel", userPhone);//被代驾人手机
		params.put("userName", String.valueOf(userId));//账户手机号
		params.put("city", city);//代驾所属城市
    	params.put("besCode", RandomUtil.getDriverOrderNo());//代驾订单号
    	params.put("startAddress", startAddress);//起始地址
    	params.put("endAddress", endAddress);//终点地址
		params.put("longitude", longitude);//起始点经度
		params.put("latitude", latitude);//起始纬度
		params.put("deLongitude", deLongitude);//终点经度
		params.put("deLatitude", deLatitude);//终点纬度
		params.put("bespeakTime", appointmentTime);//预约时间
    	//params.put("orderUrl", orderUrl);//代驾请求地址
    	params.put("source", source);//代驾资源码
    	params.put("bespType", driverType);//代驾类型
    	params.put("key", key);//代驾key
    	
    	params.put("money", money);//代驾订单金额
    	Log4jHelper.getLogger().info("开始调用代驾订单接口:"+params.toString());
		DatasetSimple<String> serviceResult = driverOrderService.createDriverOrder(orderUrl,params);
		
		Map<String,Object> result = new HashMap<String,Object>();
		if(serviceResult.isSuccess()){
			result.put("orderNo", params.get("besCode"));
			return DatasetBuilder.fromDataSimple(result);
		}else{
			return DatasetBuilder.fromMessageSimple(serviceResult.getMessage());
		}
    }
    
    /**
     * 更新代驾订单预约时间
     * @param request
     * @param orderNo
     * @param appointmentTime
     * @return
     */
    @ResponseBody
    @RequestMapping("updateappointmenttime")
    public DatasetSimple<String> updateAppointmentTime(HttpServletRequest request,
    		@Required String orderNo,@Required String appointmentTime) {
    	//检验登录
    	checkLoginJson(request);
    	
		//盛大代驾接口参数信息
		//代驾请求地址
    	String orderUrl = environment.getRequiredProperty("shengda.driver.updatetimeurl");
    	//代驾请求source
    	String source = environment.getRequiredProperty("shengda.driver.source");
    	
    	DatasetSimple<String> result = driverOrderService.updateAppointmentTime(orderNo, appointmentTime,orderUrl,source);
    	
    	return result;
    }
    
    /**
     * 取消代驾订单
     * @param request
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping("canceldriverorder")
    public DatasetSimple<String> cancelDriverOrder(HttpServletRequest request,
    		@Required String orderNo) {
    	//检验登录
    	checkLoginJson(request);
    	
    	//盛大代驾接口参数信息
    	//代驾请求地址
    	String cancelOrderUrl = environment.getRequiredProperty("shengda.driver.cancelorderurl");
    	//代驾请求source
    	String source = environment.getRequiredProperty("shengda.driver.source");
    	
    	String key = environment.getRequiredProperty("shengda.driver.key");
    	DatasetSimple<String> result = driverOrderService.cancelDriverOrder(orderNo,cancelOrderUrl,source,key);
    	
    	return result;
    }
    
    /**
     *h5端查询代驾订单
     * @param request
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("querydriverorder")
    public DatasetList<DriverOrderSheng> findDriverOrder(HttpServletRequest request,String pageNo,String pageSize) {
    	//检验登录
    	checkLoginJson(request);
    	
    	Long userId = getUserId(request,AuthUtil.CAR_OIL_TOKEN);
    	
    	DatasetList<DriverOrderSheng> result = driverOrderService.queryDriverOrderList(userId,Integer.valueOf(pageNo),Integer.valueOf(pageSize));
    	
    	return result;
    }
    

	@RequestMapping(value = "/orderdetail",method = RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<DriverOrderSheng> driverOrderDetail(HttpServletRequest request,
			String orderNo){
		//调用Service
		DatasetSimple<DriverOrderSheng> orderDetail = driverOrderService.h5queryorderDetail(orderNo);
		return orderDetail;
	}
    
    /**
	 * 用户使用完成之后回调函数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/orderback",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> callback(HttpServletRequest request,String besCode,
			String source,String status,String driverName,String driverPhone) throws Exception {
		//source = GDGSCX;besCode:订单号;
		//status 订单状态:1:派单成功  6:司机已到 2:服务完成 5:取消服务
		//driverName:司机姓名;driverPhone:司机电话
		
		//组织回调日志
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("订单号:"+besCode+";订单状态:"+status+";资源:"+source+
				";司机名称:"+driverName+";司机手机号:"+driverPhone);
		Log4jHelper.getLogger().error("代驾第三方回调结果:"+strBuffer.toString());
		//调用Service
		DatasetSimple<Map<String, String>> updateDriverOrder = driverOrderService.
				updateDriverOrder(source, besCode, status, driverName, driverPhone);
		
		//返回结果
		Map<String, String> result = updateDriverOrder.getData();
		
		return result;
	}
}