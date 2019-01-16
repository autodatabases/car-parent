package com.emate.shop.h5.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CarWashProductService;
import com.emate.shop.business.api.CarWashService;
import com.emate.shop.business.api.OilUserService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.model.CarWashProduct;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.QRCodeUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;
import net.sf.json.JSONArray;


@Controller
@RequestMapping(value="washcharge")
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class CarWashChargeController implements AuthUtil{
    @Resource
    private Environment environment;
	
	private CarWashService carWashService;
	
	private SmsService smsService;
	
	private OilUserService oilUserService;
	
	private CarWashProductService carWashProductService;

	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	@RemoteService
	public void setCarWashService(CarWashService carWashService) {
		this.carWashService = carWashService;
	}
	
	@RemoteService
	public void setOilUserService(OilUserService oilUserService) {
		this.oilUserService = oilUserService;
	}
	
	@RemoteService
	public void setCarWashProductService(CarWashProductService carWashProductService) {
		this.carWashProductService = carWashProductService;
	}
	
	private String checkLoginJson(HttpServletRequest request){
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			throw new BusinessException("用户未登陆！");
		}
		return null;
	}
	
	
	/**
	 * 支付页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payfor.html")
	public String payFor(HttpServletRequest request) throws Exception {
		return "oilCard/buyWashCar/payFor";
	}
	
	/**
	 * 购买套餐记录跳转页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/purchaserecord.html")
	public String purchaseRecord(HttpServletRequest request) throws Exception {
		return "oilCard/buyWashCar/purchaseRecord";
	}
	
	/**
	 * 洗车劵主页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/washhome.html")
	public String washHome(HttpServletRequest request) throws Exception {
		return "oilCard/buyWashCar/washHome";
	}
	
	/**
	 * 洗车劵
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/washcode.html")
	public String washCode(HttpServletRequest request) throws Exception {
		return "oilCard/buyWashCar/washCode";
	}
	
	/**
	 * 洗车记录页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/washrecord.html")
	public String washRecord(HttpServletRequest request) throws Exception {
		return "oilCard/buyWashCar/washRecord";
	}
	
	
	/**
	 *	查询用户洗车次数、洗车券个数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carWashData")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> carWashData(HttpServletRequest request){
		checkLoginJson(request);
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		//获取用户信息
		DatasetSimple<OilUser> userData = oilUserService.getOilUser(userId);
		if(!userData.isSuccess() || userData.getData()==null){
			throw new BusinessException("用户不存在！");
		}
		//获取已洗车次数
		Integer usedTime = carWashService.queryUsedSheng(userId, CarWashSheng.WASH_TYPE_1)
				.getData();
		Map<String,Object> map = new HashMap<String,Object>();
		//获取剩余洗车次数
		map.put("carWashTime", userData.getData().getWashNumber()+"");
		//已使用洗车次数
		map.put("usedTime", usedTime+"");
		//查询未使用的洗车券
		DatasetList<CarWashSheng> unUsed = carWashService.queryUnUsedSheng(userId,CarWashSheng.WASH_TYPE_1);
		map.put("unUsed", unUsed);
		//洗车套餐
		DatasetList<CarWashProduct> products = carWashProductService.h5WashProduct();
		map.put("products", products);
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
	public DatasetSimple<Map<String,Object>> getCarWashByOrder(HttpServletRequest request,String orderNo){
		checkLoginJson(request);
		Map<String,Object> map = new HashMap<String,Object>();
		DatasetSimple<CarWashSheng>  carWashData = carWashService.queryShengCouponByOrderNo(orderNo,CarWashSheng.WASH_TYPE_1,"1");
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
	public DatasetSimple<CarWashSheng> sendCarWashSheng(HttpServletRequest request) throws Exception {
		checkLoginJson(request);
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		DatasetSimple<CarWashSheng> shengData = carWashProductService.h5GetWashCoupon(userId, "1");
		if(!shengData.isSuccess()){
			throw new BusinessException("发送洗车劵失败~~");
		}
		CarWashSheng carWashSheng = shengData.getData();
		// 调用接口后   发送短信
		SimpleDateFormat sm =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.sendSmsCarWash(carWashSheng.getPhone(),carWashSheng.getOrderCode(),sm.format(carWashSheng.getCouponExpirDate()));
		return shengData;
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
		params.add("http://t.cn/Rp4dUTj");
		DatasetSimple<String> smsResult = smsService.sendSmsTmp("3112111", mobileArray.toString(),
				params.toString(),SmsService.SMS_TYPE_1);
		if (!smsResult.isSuccess()) {
			Log4jHelper.getLogger().error("下发短信失败！" + smsResult.getMessage());
		}else{
			Log4jHelper.getLogger().info("获取洗车券下发短信：{手机号："+mobile+",核销码："+verifyCode+",短信下发状态："+smsResult.getData().toString()+"}");
		}
	}
	
	/**
	 * 购买洗车次数
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/addpay")
	@ResponseBody
	public DatasetSimple<Boolean> addWashPay(HttpServletRequest request,String productId){
		checkLoginJson(request);
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		return carWashProductService.addWashChargeOrder(productId, userId);
	}
	
	/**
	 * 购买洗车次数记录
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/paylist")
	@ResponseBody
	public DatasetList<Map<String,Object>> payList(HttpServletRequest request,String pageNo,String pageSize){
		checkLoginJson(request);
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		DatasetList<Map<String,Object>> payList = carWashProductService.payList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), null, null, null,userId);
		return payList;
	}
	
	/**
	 * 购买洗车劵记录
	 * @param request
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/shenglist")
	@ResponseBody
	public DatasetList<CarWashSheng> shengList(HttpServletRequest request,String pageNo,String pageSize){
		checkLoginJson(request);
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		DatasetList<CarWashSheng> shengList = carWashProductService.shengList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), null, null, null, null,userId);
		return shengList;
	}
}
