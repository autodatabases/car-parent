package com.emate.shop.h5.controller;

import java.math.BigDecimal;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.CarWashService;
import com.emate.shop.business.api.OrderService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.CarWash;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.User;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.Md5;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.tools.gaoyang.OilSdk;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="carwash")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class CarWashController implements AuthUtil{
	private static SimpleDateFormat sm =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private Environment environment;
	
	private CarWashService carWashService;
	
	private UserService userService;
	
	private OrderService orderService;
	
	private SmsService smsService;

	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	/*private ImportUserInfoService importUserInfoService;
	@RemoteService
	public void setImportUserInfoService(ImportUserInfoService importUserInfoService) {
		this.importUserInfoService = importUserInfoService;
	}*/
	@RemoteService
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
	
	@RemoteService
	public void setCarWashService(CarWashService carWashService) {
		this.carWashService = carWashService;
	}
	
	@RemoteService
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 	跳转到洗车页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/carWash.html")
	public String carWash(HttpServletRequest request) throws Exception {
		return "carWash/kcarWash";
	}
	
	/**
	 * 	跳转到券码显示页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/washCode.html")
	public String washCode(HttpServletRequest request) throws Exception {
		return "carWash/kwashCode";
	}
	
	/**
	 *	查询用户洗车次数、洗车券个数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carWashData")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> carWashData(HttpServletRequest request){
		Long userId = this.getUserId(request, AuthUtil.CAR_H5_TOKEN);
		DatasetSimple<User> userData = userService.queryUserById(userId+"");
		if(!userData.isSuccess() || userData.getData()==null){
			throw new BusinessException("用户不存在！");
		}
		DatasetSimple<ImportUserInfo> importUser = userService.getImportUserInfo(userId);
		if(!importUser.isSuccess()||importUser.getData()==null){
			throw new BusinessException("用户未绑定车型！");
		}
		ImportUserInfo u = importUser.getData();
		Map<String,Object> map = new HashMap<String,Object>();
		//查询洗车次数
		map.put("carWashTime", u.getXiecheTimes()+"");
		//查询洗车券个数
		DatasetList<CarWash> unUsed = carWashService.queryUnUsed(userId);
		map.put("unUsed", unUsed);
		return DatasetBuilder.fromDataSimple(map);
	}
	

	/**
	 * 通过订单号，查询洗车券信息
	 * @param request
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCarWashByOrder")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,Object>> getCarWashByOrder(HttpServletRequest request,String orderNo) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		DatasetSimple<CarWash>  carWashData = carWashService.queryByOrderNo(orderNo);
		if(!carWashData.isSuccess() || carWashData.getData()==null){
			throw new BusinessException("洗车券码获取失败！");
		}
		CarWash carWash = carWashData.getData();
		map.put("carWash", carWash);
		map.put("isExpire", carWash.getCouponExpirDate().getTime()<new Date().getTime());//过期时间小于当前时间  true
		return DatasetBuilder.fromDataSimple(map);
	}
	/**
	 * 用户发送洗车券
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendCarWash")
	@ResponseBody
	@AuthAction
	public DatasetSimple<CarWash> sendCarWash(HttpServletRequest request) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_H5_TOKEN);
		DatasetSimple<User> userData = userService.queryUserById(userId+"");
		if(!userData.isSuccess() || userData.getData()==null){
			throw new BusinessException("用户不存在！");
		}
		User u = userData.getData();
		DatasetSimple<ImportUserInfo> importUserData = userService.getImportUserInfo(userId);
		if(!importUserData.isSuccess() || importUserData.getData()==null){
			throw new BusinessException("用户不存在！");
		}
		//判断  是否存在未使用的洗车券
		DatasetList<CarWash> unUsed = carWashService.queryUnUsed(userId);
		if(!unUsed.isSuccess()){
			throw new BusinessException("查询洗车券失败！");
		}
		if(unUsed.getList()!=null){
			List<CarWash> carWashList = (List<CarWash>) unUsed.getList();
			if(carWashList.size()>0){//存在未使用的洗车券
				Log4jHelper.getLogger().info("存在未使用的洗车券,直接返回.");
				return DatasetBuilder.fromDataSimple(carWashList.get(0));
			}
		}
		//不存在未使用洗车券  查询洗车次数
		ImportUserInfo importUser = importUserData.getData();
		if(importUser.getXiecheTimes()<=0){
			throw new BusinessException("您的洗车次数不够！");
		}
		
		//调用接口之前记录日志
		CarWash wash = new CarWash();
		wash.setUserId(userId);
		wash.setMobile(u.getName());
		wash.setCreateTime(new Date());
		wash.setUpdateTime(new Date());
		wash.setOrderNo(RandomUtil.getOrderSn());
		//wash.setProductNo(1281+"");
		wash.setProductNo(environment.getRequiredProperty("kt.carwash.productNo"));
		Log4jHelper.getLogger().info("获取宽途productNo："+environment.getRequiredProperty("kt.carwash.productNo"));
		DatasetSimple<CarWash>  washData = carWashService.addOrUpdateCoupon(wash);
		if(!washData.isSuccess()||washData.getData()==null){
			throw new BusinessException("新增数据库记录失败！");
		}
		wash = washData.getData();
		//调用接口 
		Log4jHelper.getLogger().info("开始调用接口获取洗车券！！");
		wash = this.getCarWash(wash, u);
		// 调用接口后   发送短信
		this.sendSmsCarWash(wash.getMobile(),wash.getVerifyCode(),sm.format(wash.getCouponExpirDate()));
		//添加洗车订单
		Orders orders = new Orders();
		orders.setOrderNo(wash.getOrderNo());
		orders.setOrderType(Orders.ORDER_TYPE_3);
		orders.setStatus(Orders.STATUS_1);
		orders.setCode(wash.getVerifyCode());
		orders.setUserId(userId);
		orders.setSellerId(0L);
		orders.setMoneyAmount(new BigDecimal(26));
		orders.setGoodsNum(1);
		orders.setOrderRemark("洗车订单");
		orders.setCreateTime(new Date());
		orders.setUpdateTime(new Date());
		carWashService.addXiCheOrder(orders);
		return carWashService.addOrUpdateCoupon(wash);
	}
	
	/**
	 * 获取洗车券下发短信
	 * @param mobile
	 * @param verifyCode
	 * @param expireDate
	 */
	private void sendSmsCarWash(String mobile,String verifyCode,String expireDate){
		JSONArray mobileArray = new JSONArray();
		mobileArray.add(mobile);
		JSONArray params = new JSONArray();
		params.add(verifyCode);
		params.add(expireDate);
		DatasetSimple<String> smsResult = smsService.sendSmsTmp("3059352", mobileArray.toString(),
				params.toString(),SmsService.SMS_TYPE_0);
		if (!smsResult.isSuccess()) {
			Log4jHelper.getLogger().error("下发短信失败！" + smsResult.getMessage());
		}else{
			Log4jHelper.getLogger().info("获取洗车券下发短信：{手机号："+mobile+",核销码："+verifyCode+",短信下发状态："+smsResult.getData().toString()+"}");
		}
	}
	/**
	 * 调用接口获取洗车券
	 * @param wash
	 * @param u
	 * @return
	 */
	private CarWash getCarWash(CarWash wash,User u){
		Map<String,String> stackMap = new HashMap<>();
		stackMap.put("appId", environment.getRequiredProperty("kt.carwash.appId"));
		stackMap.put("reqId", UUID.randomUUID().toString());
		Gson gson = new Gson();
		Map<String, String> bizConetnt = new HashMap<String, String>();
		bizConetnt.put("outerOrderNo", wash.getOrderNo());
		bizConetnt.put("outerUserId", u.getId()+"");
		bizConetnt.put("mobile", u.getName());
		bizConetnt.put("productNo", wash.getProductNo());
		bizConetnt.put("createTime",sm.format(new Date()));
		//设置有效期为n天后   配置文件读取  读取错误时  默认365天
		Calendar c = Calendar.getInstance();
		String expire = environment.getRequiredProperty("kt.carwash.expire");
		if(StringUtils.isNotEmpty(expire)&&Pattern.compile("^[\\d]*$").matcher(expire).matches()){
			c.add(Calendar.DATE,Integer.parseInt(expire));
		}else{
			c.add(Calendar.DATE,365);
		}
		bizConetnt.put("expireDate", sm.format(c.getTime()));
		stackMap.put("bizContent", JSONObject.fromObject(bizConetnt).toString());
		stackMap.put("service", "kt.carwash.order.coupon.create");
		stackMap.put("version", "1.0");
		//stackMap.put("sign", url);
		List<String> param = new ArrayList<String>();
		stackMap.keySet().forEach(value -> {
			param.add(value);
		});
		java.util.Collections.sort(param);
		StringBuffer sb = new StringBuffer();
		param.forEach(value -> {
			sb.append(value).append("=").append(stackMap.get(value)).append("&");
		});
		sb.append("appKey="+environment.getRequiredProperty("kt.carwash.appKey"));
		String signature = Md5.getMd5String(sb.toString());
		//重新清零
		sb.setLength(0);
		param.forEach(value -> {
			sb.append(value).append("=").append(stackMap.get(value)).append("&");
		});
		sb.append("sign="+signature);
		stackMap.put("sign", signature);
		String tradeResult = HttpClientHelper.httpPost(environment.getRequiredProperty("kt.carwash.url"), stackMap);
		Log4jHelper.getLogger().info("调用接口返回结果：\r\n"+tradeResult);
		if(StringUtils.isEmpty(tradeResult)){
			throw new BusinessException("调用接口返回失败！");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> result = gson.fromJson(tradeResult, Map.class);
		if((double)result.get("code")!=0){
			throw new BusinessException("调用接口返回失败！errMsg:"+result.get("msg"));
		}
		String bizContentJson = (String)result.get("bizContent");
		@SuppressWarnings("unchecked")
		Map<String, String> bizContent = gson.fromJson(bizContentJson, Map.class);
		wash.setCouponCode(bizContent.get("couponCode"));
		wash.setCouponDesc(bizContent.get("couponDesc"));
		wash.setCouponName(bizContent.get("couponName"));
		try {
			wash.setCouponValidDate(new SimpleDateFormat("yyyy-MM-dd").parse(bizContent.get("couponValidDate")));
			wash.setCouponExpirDate(new SimpleDateFormat("yyyy-MM-dd").parse(bizContent.get("couponExpirDate")));
		} catch (ParseException e) {
			throw new BusinessException("调用接口返回日期类型错误");
		}
		wash.setVerifyCode(bizContent.get("verifyCode"));
		return wash;
	}
	
	/**
	 * 用户使用完成之后回调函数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/callback")
	@ResponseBody
	public Map<String,String> callback(HttpServletRequest request,String appId,
			String bizContent,String reqId,String service,String version,String sign) throws Exception {
		//解析bizContent
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, String> bizContentData = gson.fromJson(bizContent, Map.class);
		String outerOrderNo = bizContentData.get("outerOrderNo");
		String notifyTime = bizContentData.get("notifyTime");
		String shopName = bizContentData.get("shopName");
		String serviceName = bizContentData.get("serviceName");
		String bizContentReturn = "{\"outerOrderNo\":\""+outerOrderNo+"\"}";
		//校验
		if(StringUtils.isEmpty(appId)){
			return this.makeReturn(appId, bizContentReturn, 1+"", "invalid appId", reqId);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("appId="+appId).append("&bizContent="+bizContent)
			.append("&reqId="+reqId).append("&service="+service)
			.append("&version="+version);
		sb.append("&appKey="+environment.getRequiredProperty("kt.carwash.appKey"));
		Log4jHelper.getLogger().info("消费车券回调请求：\r\n"+sb.toString());
		String signature = Md5.getMd5String(sb.toString());
		Log4jHelper.getLogger().info("消费车券回调请求计算出sign：\r\n"+signature);
		if(!signature.equals(sign)){
			return this.makeReturn(appId, bizContentReturn, 1+"", "invalid sign", reqId);
		}
		DatasetSimple<CarWash>  washData = carWashService.queryByOrderNo(outerOrderNo);
		if(!washData.isSuccess()){
			return this.makeReturn(appId, bizContentReturn, 1+"", "no this order.", reqId);
		}
		CarWash wash = washData.getData();
		wash.setNotifyTime(sm.parse(notifyTime));
		wash.setUpdateTime(new Date());
		wash.setShopName(shopName);
		wash.setServiceName(serviceName);
		carWashService.addOrUpdateCoupon(wash);
		Log4jHelper.getLogger().info("洗车券订单号"+outerOrderNo+"消费成功");
		//完成订单
		DatasetSimple<Orders>  orderData = orderService.h5QueryOrderByNo(outerOrderNo);
		Orders  o = orderData.getData();
		o.setStatus(Orders.STATUS_3);
		orderService.updateOrder(o);
		return this.makeReturn(appId, bizContentReturn, "0", "success", reqId);
	}
	
	/**
	 * 为回调构造返回 
	 * @return
	 */
	private Map<String,String> makeReturn(String appId,
			String bizContentReturn,String code,String msg,
			String reqId){
		StringBuffer sbReturn = new StringBuffer();
		Map<String,String> result = new HashMap<>();
		result.put("appId", appId);
		result.put("reqId", reqId);
		result.put("bizContent", bizContentReturn);
		result.put("code", code);
		result.put("msg", msg);
		//计算sign
		sbReturn.append("appId="+appId)
		.append("&bizContent="+bizContentReturn)
		.append("&code="+code)
		.append("&msg="+msg)
		.append("&reqId="+reqId)
		.append("&appKey="+environment.getRequiredProperty("kt.carwash.appKey"));
		result.put("sign", Md5.getMd5String(sbReturn.toString()));
		return result;
	}
	
	public static void main11(String[] args) {
		String orderNo = RandomUtil.getOrderSn();
		Gson gson = new Gson();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderNo", orderNo);
		data.put("cardNo", "111");
		data.put("amount", 3000);
		data.put("mobileNo", "13051851080");
		data.put("notifyUrl","http://car.emateglobal.com/");
		//data.put("memo", "");
		Map<String, Object> req = new TreeMap<String, Object>();
		req.put("timeStamp", OilSdk.currentDateStr());
		req.put("appId", "test");
		req.put("data", gson.toJson(data));
		req.put("sign", OilSdk.getSign(req,"test"));
		
		String tradeParam = gson.toJson(req);
		String tradeUrl = "http://120.24.236.137:8011/code-service/v1.0/sinopec/recharge";
		String tradeResult = OilSdk.sendPost(tradeUrl, tradeParam);
		Log4jHelper.getLogger().info("调用油卡接口返回结果：\r\n"+tradeResult);
		@SuppressWarnings("unchecked")
		Map<String, Object> result = gson.fromJson(tradeResult, Map.class); 
		if("0000".equals(result.get("resultCode"))){//成功
			System.out.println("nb");
		}
		
	}
	
	public static void main(String[] args) throws ParseException {

	/*	Map<String,String> stackMap = new HashMap<>();
		//String orderNo = RandomUtil.getOrderSn();
		stackMap.put("appId","1000000013");
		stackMap.put("reqId", UUID.randomUUID().toString().substring(0, 32));
		
		Gson gson = new Gson();
		Map<String, Object> bizConetnt = new HashMap<String, Object>();
		bizConetnt.put("outerOrderNo", RandomUtil.getOrderSn());
		bizConetnt.put("outerUserId", "11111");
		bizConetnt.put("mobile", "17710310113");
		bizConetnt.put("productNo", "1281");
		bizConetnt.put("createTime",sm.format(new Date()));
		bizConetnt.put("expireDate", sm.format(new Date()));
		System.err.println(JSONObject.fromObject(bizConetnt).toString());
		stackMap.put("bizContent", JSONObject.fromObject(bizConetnt).toString());
		stackMap.put("service", "kt.carwash.order.coupon.create");
		stackMap.put("version", "1.0");
		//stackMap.put("sign", url);
		List<String> param = new ArrayList<String>();
		stackMap.keySet().forEach(value -> {
			param.add(value);
		});
		java.util.Collections.sort(param);
		StringBuffer sb = new StringBuffer();
		param.forEach(value -> {
			sb.append(value).append("=").append(stackMap.get(value)).append("&");
		});
		sb.append("appKey=615df7bdfd1dd8ddb5b396d18cf57f81");
		//System.err.println(sb.toString());
		String signature = Md5.getMd5String(sb.toString());
		//重新清零
		sb.setLength(0);
		param.forEach(value -> {
			sb.append(value).append("=").append(stackMap.get(value)).append("&");
		});
		sb.append("sign="+signature);
		stackMap.put("sign", signature);
		System.err.println(sb.toString());
		//stackMap.put("signature", signature);
		//调用接口之前记录日志
		CarWash wash = new CarWash();
		//wash.setUserId("1111");
		wash.setMobile("17710310113");
		wash.setCreateTime(new Date());
		wash.setUpdateTime(new Date());
		//wash.setOrderNo(bizConetnt.get("outerOrderNo"));
		//wash.setProductNo(bizConetnt.get("productNo"));
		
		String tradeResult = HttpClientHelper.httpPost("http://113.57.196.238:8701/gateway", stackMap);
		//String tradeResult = OilSdk.sendPost("http://113.57.196.238:8701/gateway?"+sb.toString(),null);
		Log4jHelper.getLogger().info("调用接口返回结果：\r\n"+tradeResult);
		if(StringUtils.isEmpty(tradeResult)){
			throw new BusinessException("调用接口返回失败！");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> result = gson.fromJson(tradeResult, Map.class);
		if(!new Integer(0).equals(result.get("code"))){
			throw new BusinessException("调用接口返回失败！errMsg:"+result.get("msg"));
		}
		String bizContentJson = (String)result.get("bizContent");
		@SuppressWarnings("unchecked")
		Map<String, String> bizContent = gson.fromJson(bizContentJson, Map.class);
		wash.setCouponCode(bizContent.get("couponCode"));
		wash.setCouponDesc(bizContent.get("couponDesc"));
		wash.setCouponName(bizContent.get("couponName"));
		wash.setCouponValidDate(sm.parse(bizContent.get("couponValidDate")));
		wash.setCouponExpirDate(sm.parse(bizContent.get("couponExpirDate")));
		wash.setVerifyCode(bizContent.get("verifyCode"));
		System.err.println(wash.toString());
		*/
		
		
		/*StringBuffer sbReturn = new StringBuffer();
		sbReturn.append("appId=1000000013")
		.append("&bizContent="+"{\"outerOrderNo\":\"kt_10000001\"}")
		.append("&code=0")
		.append("&msg="+"success")
		.append("&reqId="+"760c2b731af941bdb445b58558ab46ba")
		.append("&appKey=615df7bdfd1dd8ddb5b396d18cf57f81");
		System.err.println(Md5.getMd5String(sbReturn.toString())); */
		
		/*JSONArray mobileArray = new JSONArray();
		mobileArray.add("1771031113");
		JSONArray params = new JSONArray();
		//params.add(mobile);
		params.add("154745256526");
		params.add("2018-04-23");
		DatasetSimple<String> smsResult = smsService.sendSmsTmp("3059352", mobileArray.toString(),
				params.toString(),SmsService.SMS_TYPE_0);
		if (!smsResult.isSuccess()) {
			Log4jHelper.getLogger().error("下发短信失败！" + smsResult.getMessage());
		}*/
	}
}
