package com.emate.shop.h5.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AddressService;
import com.emate.shop.business.api.FeedbackService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.api.UserMsgService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserAddress;
import com.emate.shop.business.model.UserMsg;
import com.emate.shop.common.IPUtil;
import com.emate.shop.common.Md5;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping(value="user")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:web.properties")
public class UserController implements AuthUtil{
	
	@Resource
    private Environment environment;
	
	private UserService userService;
	
	private SmsService smsService;
	
	private AddressService addressService;
	
	private UserMsgService userMsgService;
	
	private FeedbackService feedbackService;
	
	@RemoteService
	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@RemoteService
	public void setUserMsgService(UserMsgService userMsgService) {
		this.userMsgService = userMsgService;
	}

	@RemoteService
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	@RemoteService
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 登录页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login.html")
	public String login(HttpServletRequest request) throws Exception {
		return "login/login";
	}
	
	/**
	 * 注册页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register.html")
	public String register(HttpServletRequest request) throws Exception {
		return "login/register";
	}
	
	/**
	 * 个人中心
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/infocenter.html")
	@AuthAction
	public String infocenter(HttpServletRequest request) throws Exception {
		return "member/member";
	}
	
	/**
	 * 订单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/order.html")
	@AuthAction
	public String order(HttpServletRequest request) throws Exception {
		return "member/order-list";
	}
	
	/**
	 * 反馈
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/feedback.html")
	@AuthAction
	public String feedback(HttpServletRequest request) throws Exception {
		return "member/member-feedback";
	}
	
	/**
	 * 车库
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/garage.html")
	@AuthAction
	public String garage(HttpServletRequest request) throws Exception {
		return "member/member-garage";
	}
	
	/**
	 * 个人设置
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setup.html")
	@AuthAction
	public String setup(HttpServletRequest request) throws Exception {
		return "member/member-setup";
	}
	
	/**
	 * 设置-个人资料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setup-prsldata.html")
	public String setupPrsldata(HttpServletRequest request) throws Exception {
		return "member/setup-prsldata";
	}

	
	/**
	 * 设置-关于我们
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setup-aboutus.html")
	public String setupAboutus(HttpServletRequest request) throws Exception {
		return "member/setup-aboutus";
	}
	
	
	
	/**
	 * 车友圈
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/news.html")
	public String news(HttpServletRequest request) throws Exception {
		return "news/news";
	}
	
	/**
	 * 车友圈
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/news-main.html")
	public String newsMain(HttpServletRequest request) throws Exception {
		return "news/news-main";
	}


	/**
	 * 用户输入车牌号注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public DatasetSimple<String> register(HttpServletRequest request,
			@Required String userName,
			@Required String smsCode,
			@Required String tokenCode,
			String chepai,
			String password,
			@Required String regType) throws Exception {
		if("1".equals(regType)){
			if(StringUtils.isEmpty(chepai) || StringUtils.isEmpty(password)){
				return DatasetBuilder.fromMessageSimple("车险用户必须输入车牌号和刮刮卡密码！");
			}
		}
		DatasetSimple<User> user = userService.userRegister(regType,userName, smsCode, tokenCode, IPUtil.getIP(request),chepai,password);
		if(user.isSuccess()){
			return DatasetBuilder.fromDataSimple(getToken(user.getData().getId()));
		}
		return DatasetBuilder.fromMessageSimple(user.getMessage());
	}
	
	
	/**
	 * 用户输入车牌号绑定车牌号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bind")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> bind(HttpServletRequest request,
			@Required String chepai,
			@Required @Regex("\\d+")String password, String source) throws Exception {
		return userService.bind(this.getUserId(request, AuthUtil.CAR_H5_TOKEN), chepai.toUpperCase(), password,source);
	}
	
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public DatasetSimple<String> login(HttpServletRequest request,
			@Required String userName,
			@Required String smsCode,
			@Required String tokenCode) throws Exception {
		DatasetSimple<User> user = userService.userLogin(userName, smsCode, tokenCode, IPUtil.getIP(request));
		if(user.isSuccess()){
			return DatasetBuilder.fromDataSimple(getToken(user.getData().getId()));
		}
		return DatasetBuilder.fromMessageSimple(user.getMessage());
	}
	
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendSms")
	@ResponseBody
	public DatasetSimple<String> sendSms(HttpServletRequest request,
			@Required String mobile) throws Exception {
		return smsService.sendSmsCode(mobile,SmsService.SMS_TYPE_0);
	}
	
	/**
	 * 添加用户车型
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/setCarInfo")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> setCarInfo(HttpServletRequest request,
			@Required String autoId,
			String pic,
			String carCode,
			String mileage,
			String license
			){
		long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		return userService.setCarInfo(userId, Long.parseLong(autoId),pic,carCode,mileage,license);
	}
	
	/**
	 * 添加用户地理位置
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/setAddressInfo")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> setAddressInfo(HttpServletRequest request,
			@Required String address){
		//long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		//userService.setAddressInfo(userId, address);
		return DatasetBuilder.fromDataSimple(true);
	}
	
	/**
	 * 首页用户信息
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/getCarInfo")
	@ResponseBody
	@AuthAction
	public DatasetSimple<ImportUserInfo> getCarInfo(HttpServletRequest request){
		long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		return userService.getUserInfo(userId);
	}
	
	/**
	 * 我的车库
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/myCars")
	@ResponseBody
	@AuthAction
	public DatasetList<Map<String,String>> myCars(HttpServletRequest request){
		return userService.myCars(this.getUserId(request,AuthUtil.CAR_H5_TOKEN));
	}
	
	/**
	 * 查询用户收货地址
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/getUserAddress")
	@ResponseBody
	@AuthAction
	public DatasetSimple<UserAddress> getUserAddress(HttpServletRequest request){
		long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		return addressService.queryUserAddress(userId);
	}
	
	/**
	 * 查询用户消息列表
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/getMsgList")
	@ResponseBody
	@AuthAction
	public DatasetList<UserMsg> getMsgList(HttpServletRequest request){
		long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		return userMsgService.queryMsgList(userId);
	}

	/**
	 * 查询用户消息详情
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/getMsg")
	@ResponseBody
	@AuthAction
	public DatasetSimple<UserMsg> getMsg(HttpServletRequest request,@Required @Regex("\\d+")String id){
		return userMsgService.queryMsgById(Long.parseLong(id));
	}
	
	/**
	 * 添加用户反馈
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/addFeedBack")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addFeedBack(HttpServletRequest request,@Required String content,@Required String userName){
		if(content.length()>=3500){
			throw new BusinessException("内容过长！");
		}
		return feedbackService.addFeedBack(this.getUserId(request,AuthUtil.CAR_H5_TOKEN), userName, content);
	}
	
	/**
	 * 查询用户信息
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	@AuthAction
	public DatasetSimple<User> getUserInfo(HttpServletRequest request){
		return userService.queryUserById(this.getUserId(request,AuthUtil.CAR_H5_TOKEN)+"");
	}
	
	/**
	 * 更新用户信息
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo")
	@ResponseBody
	@AuthAction
	public DatasetSimple<User> updateUserInfo(HttpServletRequest request,@Required String nickName,@Required @Regex("^[0,1,2]$")String gender){
		return userService.updateUserInfo(this.getUserId(request,AuthUtil.CAR_H5_TOKEN), nickName, Integer.parseInt(gender));
	}
	
	/**
	 * 判断当前用户是否有订单
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/hasUnCompletOrder")
	@ResponseBody
	public DatasetSimple<Map<String,String>> UnCompletOrder(HttpServletRequest request,@Required @Regex("^[0,1,2,3,4]$")String orderType){
		return userService.hasUnCompletOrder(this.getUserId(request, AuthUtil.CAR_H5_TOKEN),orderType);
	}
	
	/**
	 * 用户登录惠生活
	 * @param request
	 * @return 
	 * @return
	 */
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginShop.html")
	@AuthAction
	public String loginShop(HttpServletRequest request,Map<String,Object> stackMap){
		String topic = request.getParameter("topic");
		Long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		DatasetSimple<User> userData = userService.queryUserById(String.valueOf(userId));
		if(userData.isSuccess()){
			String mobile = userData.getData().getName();
			String time = String.valueOf(System.currentTimeMillis());
			String key = environment.getRequiredProperty("car.auto.key");
			String sign = Md5.getMd5String(mobile+"_"+time+"_"+key);
			stackMap.put("mobile", mobile);
			stackMap.put("time", time);
			stackMap.put("sign", sign);
			stackMap.put("domin", environment.getRequiredProperty("car.auto.login.domin"));
			stackMap.put("topic", topic);
			return "login/loginToShop";
		}else{
			return "error";
		}
	}
	
	/**
	 * 跳转到业务员激活
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tobindBusiness.html",method=RequestMethod.GET)
	@AuthAction
	public String tobindBusiness(HttpServletRequest request) throws Exception {
		return "business/bindBusiness";
	}
	
	/**
	 * 业务员激活
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindBusiness")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> bindBusiness(HttpServletRequest request,
			@Required @Regex("\\d+")String businessCode,
			@Required String realName) throws Exception {
		return userService.bindBusiness(this.getUserId(request, AuthUtil.CAR_H5_TOKEN),businessCode,realName);
	}
	
	/**
	 * 跳转到绑定成功页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bussinessIndex.html")
	@AuthAction
	public String bussinessIndex(HttpServletRequest request,Map<String,Object> stackMap) throws Exception {
		stackMap.putAll(userService.getBusinessInfo(this.getUserId(request, AuthUtil.CAR_H5_TOKEN)).getData());
		return "business/list";
	}
	
	/**
	 * 业务员提交step1
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bussinessStep1.html")
	@AuthAction
	public String bussinessStep1(HttpServletRequest request) throws Exception {
		return "business/step1";
	}
	
	/**
	 * 业务员提交step2
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bussinessStep2.html")
	@AuthAction
	public String bussinessStep2(HttpServletRequest request) throws Exception {
		return "business/step2";
	}
	
	/**
	 * 业务员提交step2
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bussinessStep3.html")
	@AuthAction
	public String bussinessStep3(HttpServletRequest request) throws Exception {
		return "business/step3";
	}
	
	
	/**
	 * 业务员提交完成
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bussinessStepOK.html")
	@AuthAction
	public String bussinessStepOK(HttpServletRequest request) throws Exception {
		return "business/ok";
	}
	
	
	/**
	 * 业务员订单详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bussinessOrderDetail.html")
	@AuthAction
	public String bussinessOrderDetail(HttpServletRequest request) throws Exception {
		return "business/order-detail";
	}
	
	/**
	 * 忘记密码第一跳
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeNum.html")
	public String forgetPwd1(HttpServletRequest request) throws Exception {
		return "login/changeNum";
	}
	
	/**
	 * 忘记密码第二跳
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInfo.html")
	public String forgetPwd2(HttpServletRequest request) throws Exception {
		return "login/validateInfo";
	}
	
	/**
	 * 更改用户手机号
	 * @param request
	 * @param newphone
	 * @param smsCode
	 * @param tokenCode
	 * @param license
	 * @param baoDan
	 * @param oldPhone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateuserphone")
	@ResponseBody
	public DatasetSimple<String> updateUser(HttpServletRequest request,
			@Required String newphone,@Required String license,
			@Required String baoDan,@Required String oldPhone) throws Exception {
		
		 DatasetSimple<User> user= userService.updateUserPhone(newphone,license,
				 baoDan,oldPhone);
		 
		 if(user.isSuccess()){
				return DatasetBuilder.fromDataSimple(getToken(user.getData().getId()));
		 }else{
			 throw new BusinessException(user.getMessage());
		 }
	}
	

	@RequestMapping(value = "/judgesms")
	@ResponseBody
	public DatasetSimple<String> judgeMessage(HttpServletRequest request,
			@Required String newphone,@Required String smsCode,
			@Required String tokenCode) throws Exception {
		//校验验证码
		return userService.judgeMessage(newphone,smsCode,tokenCode); 
	}
}
