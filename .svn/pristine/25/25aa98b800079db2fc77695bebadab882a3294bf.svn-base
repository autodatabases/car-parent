package com.emate.shop.h5.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CarWashService;
import com.emate.shop.business.api.ImportUserInfoService;
import com.emate.shop.business.api.OrderService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.User;
import com.emate.shop.common.HttpClientHelper;
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
import com.emate.tools.ShengDaUtil;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="carwashsheng")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class CarWashShengController implements AuthUtil{
    @Resource
    private Environment environment;
	
	private CarWashService carWashService;
	
	private UserService userService;
	
	private OrderService orderService;
	
	private SmsService smsService;
	
	private ImportUserInfoService importUserInfoService;

	@RemoteService
	public void setImportUserInfoService(ImportUserInfoService importUserInfoService) {
		this.importUserInfoService = importUserInfoService;
	}

	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

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
		return "carWash/carWash";
	}
	
	/**
	 * 	跳转到券码显示页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/washCode.html")
	public String washCode(HttpServletRequest request) throws Exception {
		return "carWash/washCode";
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
		if(u.getXiecheTimes()<=0){
			importUser = importUserInfoService.getCarInfoByLicense(importUser.getData().getChePai()+"_newyear");
			if(importUser!=null && importUser.getData()!=null){
				//查询洗车次数
				map.put("carWashTime", importUser.getData().getXiecheTimes()+"");
			}
		}
		
		//查询洗车券个数
		DatasetList<CarWashSheng> unUsed = carWashService.queryUnUsedSheng(userId,CarWashSheng.WASH_TYPE_0);
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
	public DatasetSimple<Map<String,Object>> getCarWashByOrder(HttpServletRequest request,String orderNo){
		Map<String,Object> map = new HashMap<String,Object>();
		DatasetSimple<CarWashSheng>  carWashData = carWashService.queryShengCouponByOrderNo(orderNo,CarWashSheng.WASH_TYPE_0,"1");
		if(!carWashData.isSuccess() || carWashData.getData()==null){
			throw new BusinessException("洗车券码获取失败！");
		}
		CarWashSheng carWash = carWashData.getData();
		map.put("carWash", carWash);
		map.put("isExpire", carWash.getCouponExpirDate().getTime()<new Date().getTime());//过期时间小于当前时间  true
		return DatasetBuilder.fromDataSimple(map);
	}
	/**
	 * 盛大洗车接口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendCarWashSheng")
	@ResponseBody
	@AuthAction
	public DatasetSimple<CarWashSheng> sendCarWashSheng(HttpServletRequest request) throws Exception {
    	//加入订单时间限制
    	/*SimpleDateFormat smd =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date startTime = null;
		try {
			startTime = smd.parse("2018-02-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("日期转化异常~~");
		}
    	Date endTime = null;
		try {
			endTime = smd.parse("2018-03-08 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("日期转化异常~~");
		}
    	Date now = new Date();
    	if(now.after(startTime) && now.before(endTime)){
    		throw new BusinessException("春节期间2月1日-3月7日由于店铺休息无法接单，请谅解！");
    	}*/
		
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
		DatasetList<CarWashSheng> unUsed = carWashService.queryUnUsedSheng(userId, CarWashSheng.WASH_TYPE_0);
		if(!unUsed.isSuccess()){
			throw new BusinessException("查询洗车券失败！");
		}
		if(unUsed.getList()!=null){
			List<CarWashSheng> carWashList =  unUsed.getList();
			if(carWashList.size()>0){
				CarWashSheng carWashSheng = carWashList.get(0);
				if(Objects.isNull(carWashSheng.getFinishTime())){//存在未使用的洗车券
					Log4jHelper.getLogger().info("存在未使用的洗车券,直接返回.");
					return DatasetBuilder.fromDataSimple(carWashSheng);
				}
				if((new Date().getTime()-carWashSheng.getFinishTime().getTime())<60000){
					Log4jHelper.getLogger().info("发送洗车劵,太过频繁");
					return DatasetBuilder.fromDataSimple(carWashSheng);
				}
			}
		}
		//不存在未使用洗车券  查询洗车次数
		ImportUserInfo importUser = importUserData.getData();
		if(importUser.getXiecheTimes()<=0){
			DatasetSimple<ImportUserInfo>  datainfo = importUserInfoService.getCarInfoByLicense(importUser.getChePai()+"_newyear");
			if(datainfo.isSuccess() && datainfo.getData()!=null){
				if(datainfo.getData().getXiecheTimes()<=0){
					throw new BusinessException("您的洗车次数不够！");
				}
			}else{
				throw new BusinessException("您的洗车次数不够！");
			}
			
		}
		
		//调用接口之前记录日志
		CarWashSheng carWashSheng = new CarWashSheng();
		carWashSheng.setUserId(userId);
		carWashSheng.setPhone(u.getName());
		carWashSheng.setCreateTime(new Date());
		carWashSheng.setUpdateTime(new Date());
		carWashSheng.setOrderNo(RandomUtil.getOrderSn());
		carWashSheng.setStatus(CarWashSheng.STATUS_0);
		DatasetSimple<CarWashSheng>  washData = carWashService.addOrUpdateCarWash(carWashSheng);
		if(!washData.isSuccess()||washData.getData()==null){
			throw new BusinessException("新增数据库洗车记录失败！");
		}
		carWashSheng = washData.getData();
		//调用接口 
		Log4jHelper.getLogger().info("开始调用接口获取洗车券！！");
		carWashSheng = this.getShengCarWash(carWashSheng, u);
		// 调用接口后   发送短信
		SimpleDateFormat sm =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.sendSmsCarWash(carWashSheng.getPhone(),carWashSheng.getOrderCode(),sm.format(carWashSheng.getCouponExpirDate()));
		//添加洗车订单
		Orders orders = new Orders();
		orders.setOrderNo(carWashSheng.getOrderNo());
		orders.setOrderType(Orders.ORDER_TYPE_3);
		orders.setStatus(Orders.STATUS_1);
		orders.setCode(carWashSheng.getOrderCode());
		orders.setUserId(userId);
		orders.setSellerId(0L);
		orders.setMoneyAmount(new BigDecimal(26));
		orders.setGoodsNum(1);
		orders.setOrderRemark("洗车订单");
		orders.setCreateTime(new Date());
		orders.setUpdateTime(new Date());
		carWashService.addXiCheOrder(orders);
		return carWashService.addOrUpdateCarWash(carWashSheng);
	}
	

	
	/**
	 * 调用盛大接口获取洗车券
	 * @param wash
	 * @param u
	 * @return
	 */
	private CarWashSheng getShengCarWash(CarWashSheng wash,User u){
		Map<String, String> map = new LinkedMap<String,String>();
		map.put("source", environment.getRequiredProperty("shengda.carwash.source"));//渠道号
		map.put("orgSource", environment.getRequiredProperty("shengda.carwash.orgSource"));//机构来源
		map.put("order", wash.getOrderNo());//订单号
		map.put("carType", environment.getRequiredProperty("shengda.carwash.carType"));//大小车类型 01小车 噢02大车
		map.put("userInfo", u.getName());
		map.put("generationRule", environment.getRequiredProperty("shengda.carwash.generationRule"));//订单生成规则
		map.put("userOrderType", environment.getRequiredProperty("shengda.carwash.userOrderType"));//唯一标识
		map.put("randStr", RandomUtil.randomNumber(8));//商户随机码
		String params = JSONObject.fromObject(map).toString();
		Log4jHelper.getLogger().info("调用盛大接口请求参数：\r\n"+params);
		//加密
		//des加密
		String key3Des=environment.getRequiredProperty("shengda.carwash.key3Des");
		//MD5加密
		String key=environment.getRequiredProperty("shengda.carwash.key");
		
		String encryptParam = ShengDaUtil.encrypt(params,key,key3Des);
		
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("encryptJsonStr", encryptParam);

		String message = HttpClientHelper.httpPost(environment.getRequiredProperty("shengda.carwash.url"),
				requestMap);
		JSONObject obj = JSONObject.fromObject(message);
		String result = ShengDaUtil.decrypt(String.valueOf(obj.get("encryptJsonStr")), key, key3Des);
		Log4jHelper.getLogger().info("调用盛大接口返回结果：\r\n"+result);
		if(StringUtils.isEmpty(result)){
			throw new BusinessException("调用接口返回失败！");
		}
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String,String> resultMap = gson.fromJson(result, Map.class);
		if(!"SUCCESS".equals(resultMap.get("resultCode"))){
			throw new BusinessException("调用接口返回失败！errMsg:"+resultMap.get("resultDesc"));
		}
		String orderCode = resultMap.get("order");
		String encryptCode = resultMap.get("encryptCode");
		wash.setOrderCode(encryptCode);
		wash.setBarCode(orderCode);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		wash.setCouponValidDate(now.getTime());
		now.add(Calendar.YEAR, 1);
		wash.setCouponExpirDate(now.getTime());
		wash.setStatus(CarWashSheng.STATUS_0);
		return wash;
	}
	
	/**
	 * 生成二维码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getQrCode")
	public void getQrCode(HttpServletRequest request,
			HttpServletResponse respons,String barCode) throws Exception {
		QRCodeUtil.createQRCodeImage(barCode, 180, 180, "png", respons.getOutputStream());
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
		DatasetSimple<String> smsResult = smsService.sendSmsTmp("3084058", mobileArray.toString(),
				params.toString(),SmsService.SMS_TYPE_0);
		if (!smsResult.isSuccess()) {
			Log4jHelper.getLogger().error("下发短信失败！" + smsResult.getMessage());
		}else{
			Log4jHelper.getLogger().info("获取洗车券下发短信：{手机号："+mobile+",核销码："+verifyCode+",短信下发状态："+smsResult.getData().toString()+"}");
		}
	}

	public static void main22(String[] args) {
		Map<String, String> map = new LinkedMap<String,String>();
		map.put("source", "GDGSCX");//渠道号
		map.put("orgSource", "GDGSCX");//机构来源
		map.put("order", RandomUtil.getOrderSn());//订单号
		map.put("carType", "01");//大小车类型 01小车 噢02大车
		map.put("userInfo", "13521322513");
		map.put("generationRule", "01");//订单生成规则
		map.put("userOrderType", "order");//唯一标识
		map.put("randStr", "Cp2016007");//商户随机码
		String str = JSONObject.fromObject(map).toString();
		System.out.println(str);
		//加密
		String key = "e34ebd9bdb8c67d53be50f77f5a8b5d5";
		String key3Des = "C205BC5839533270jUN1d77Y";
		String encryptParam = ShengDaUtil.encrypt(str, key, key3Des);
		System.out.println(encryptParam);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("encryptJsonStr", encryptParam);
		String url = "http://101.231.154.154:8042/ShengDaAutoPlatform/car!receiveOrder";
		String message = HttpClientHelper.httpPost(url, map1);
		
		JSONObject obj = JSONObject.fromObject(message);
		System.out.println(obj.get("encryptJsonStr"));
		// 先3Des解密，再md5解密
		String serverStr = "";
		try {
			//serverStr =new String(DesUtils.decrypt(obj.get("encryptJsonStr").toString().getBytes(), key3Des)) ;
			//serverStr = PCThreeDESUtil.decrypt(obj.get("encryptJsonStr").toString(), key3Des);
			System.out.println("serverStr : " + serverStr);
			String serverStrSpit[] = serverStr.split("[|]");
			StringBuffer stn = new StringBuffer();
			stn.append(serverStrSpit[0]).append(key);
			String sign = DigestUtils.md5Hex(stn.toString().getBytes("UTF-8")).toUpperCase();
			System.out.println("sign : " + sign);
			System.out.println(sign.equals(serverStrSpit[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 用户使用完成之后回调函数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shengcallback",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> callback(HttpServletRequest request,String orderNo,
			String shopName,String finishTime,String status,String shopAddress,String shopPhone,String sign) throws Exception {
		
		Map<String, String> result = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer();
		String key=environment.getRequiredProperty("shengda.carwash.key");
		//根据订单号查询洗车订单
		DatasetSimple<CarWashSheng>  washData = null;
		if(orderNo.startsWith("d")){
			washData=carWashService.queryShengCouponByOrderNo(orderNo,1,"0");
		}else{
			washData=carWashService.queryShengCouponByOrderNo(orderNo,0,"0");
		}
		if(Objects.isNull(washData)||!washData.isSuccess()){
			result.put("code", "0");
			result.put("message", "查询该订单失败~~");
			sb.append("code=").append("0").append("&")
			.append("message").append("查询该订单失败~~").append("&")
			.append("key=").append(key);
			String sign1 = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
			result.put("sign", sign1);
			return result;
		}
		CarWashSheng wash = washData.getData();
		//解码
		shopName =URLDecoder.decode(shopName, "GBK");
		shopAddress =URLDecoder.decode(shopAddress, "GBK");
		shopPhone =URLDecoder.decode(shopPhone, "GBK");
		//解密
		StringBuffer strbuff = new StringBuffer();
		strbuff.append("orderNo=").append(orderNo).append("&")
		.append("shopName=").append(shopName).append("&")
		.append("finishTime=").append(finishTime).append("&")
		.append("status=").append(status).append("&")
		.append("shopAddress=").append(shopAddress).append("&")
		.append("shopPhone=").append(shopPhone).append("&")
		.append("key=").append(key);
		Log4jHelper.getLogger().info("消费车券回调请求:"+strbuff.toString());
		String signature = DigestUtils.md5Hex(
				strbuff.toString().getBytes("utf-8"));
		Log4jHelper.getLogger().info("消费车券回调请求的sign:"+sign);
		Log4jHelper.getLogger().info("消费车券回调请求计算出sign:"+signature);
		//校验
		if(!sign.equals(signature)){
			//失败记录
			Log4jHelper.getLogger().info("洗车券订单号"+orderNo+"解密失败");
			wash.setRemark("解密失败");
			wash.setStatus(CarWashSheng.STATUS_2);
			carWashService.addOrUpdateCarWash(wash);
			//返回结果
			result.put("code", "0");
			result.put("message", "解密失败~~");
			sb.append("code=").append("0").append("&")
			.append("message").append("解密失败~~").append("&")
			.append("key=").append(key);
			String sign1 = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
			result.put("sign", sign1);
			return result;
		}
		//完成日期
		if(StringUtils.isNotEmpty(finishTime)){
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
			wash.setFinishTime(sf.parse(finishTime));
		}
		//完成商铺
		if(StringUtils.isNotEmpty(shopName)){
			wash.setShopName(shopName);
		}
		//完成商铺地址
		if(StringUtils.isNotEmpty(shopAddress)){
			wash.setShopAddress(shopAddress);
		}
		//完成商铺联系方式
		if(StringUtils.isNotEmpty(shopPhone)){
			wash.setShopPhone(shopPhone);
		}
		//订单状态
		if("0".equals(status)){
			wash.setStatus(CarWashSheng.STATUS_0);
		}else if ("1".equals(status)){
			wash.setStatus(CarWashSheng.STATUS_1);
			wash.setRemark("订单消费完成");
		}else{
			wash.setStatus(CarWashSheng.STATUS_2);
		}
		//更新时间
		wash.setUpdateTime(new Date());
		Log4jHelper.getLogger().info("洗车券订单号"+orderNo+"消费成功");
		carWashService.addOrUpdateCarWash(wash);
		if("1".equals(status)&&!orderNo.startsWith("d")){
			//完成订单
			DatasetSimple<Orders>  orderData = orderService.h5QueryOrderByNo(orderNo);
			Orders  o = orderData.getData();
			o.setStatus(Orders.STATUS_3);
			o.setFinishTime(new Date());;
			orderService.updateOrder(o);
		}
		result.put("code", "1");
		result.put("message", "修改订单状态成功");
		sb.append("code=").append("1").append("&")
		.append("message").append("修改订单状态成功").append("&")
		.append("key=").append(key);
		String sign1 = DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
		result.put("sign", sign1);
		return result;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		String orderNo = "17110216242740423";
		String shopName = "立星汽车服务中心(荔城店)";
		String finiTime = "20171031142540";
		String status = "1";
		String shopAddress = "广东省广州市增城区五四路180号(大哥大哥)";
		String shopPhone = "董小姐0769-81395222,123456789";
		
		StringBuffer str = new StringBuffer();
		str.append("orderNo=").append(orderNo).append("&")
		.append("shopName=").append(shopName).append("&")
		.append("finishTime=").append(finiTime).append("&")
		.append("status=").append(status).append("&")
		.append("shopAddress=").append(shopAddress).append("&")
		.append("shopPhone=").append(shopPhone).append("&")
		.append("key=").append("e34ebd9bdb8c67d53be50f77f5a8b5d5");
		 String md5Hex = DigestUtils.md5Hex(str.toString().getBytes("utf-8"));
		 System.out.println("加密后:"+md5Hex);
		 StringBuffer buf = new StringBuffer();
		 shopName = URLEncoder.encode( URLEncoder.encode(shopName,"GBk"),"GBk");
		 shopAddress = URLEncoder.encode( URLEncoder.encode(shopAddress,"GBk"),"GBk");
		 shopPhone = URLEncoder.encode( URLEncoder.encode(shopPhone,"GBk"),"GBk");
		 
		 buf.append("orderNo=").append(orderNo).append("&")
			.append("shopName=").append(shopName).append("&")
			.append("finishTime=").append(finiTime).append("&")
			.append("status=").append(status).append("&")
			.append("shopAddress=").append(shopAddress).append("&")
			.append("shopPhone=").append(shopPhone).append("&")
			.append("sign=").append(md5Hex);
		 System.out.println(buf.toString());
		 
/*		String shopName = URLDecoder.decode(URLDecoder.decode("%CA%A8%C1%EB%D5%F2%B8%DB%CC%A9%D0%D0%C6%FB%B3%B5%B7%FE%CE%F1%D3%D0%CF%DE%B9%AB%CB%BE","GBK"),"GBK");
		System.out.println(shopName);
		String shopAddress = URLDecoder.decode("%CA%A8%C1%EB%D5%F2%B1%A6%B7%E5%C4%CF%C2%B75-15%BA%C5%B1%A6%B7%E5%B4%BA%CC%EC%C9%CC%C6%CC","GBK");
	    System.out.println(shopAddress);*/
	}
}
