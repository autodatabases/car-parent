package com.emate.shop.h5.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.OilUserService;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.model.OilGoods;
import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.business.vo.OilGoodsTypeVo;
import com.emate.shop.common.IPUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
@Controller
@RequestMapping(value="oilUser")
public class OilCardController implements AuthUtil{
	
	private OilUserService oilUserService;
	
	private SmsService smsService;
	
	
	@RemoteService
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	@RemoteService
	public void setOilUserService(OilUserService oilUserService) {
		this.oilUserService = oilUserService;
	}
	
	private String checkLoginJson(HttpServletRequest request){
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			throw new BusinessException("用户未登陆！");
		}
		return null;
	}
	
	/**
	 * 登录页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/oilLogin.html")
	public String login(HttpServletRequest request) throws Exception {
		return "oilCard/oilLogin";
	}
	
	@RequestMapping(value = "/cateyes.html")
	public String toMaoyan(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/cateyes";
	}
	
	@RequestMapping(value = "/didi.html")
	public String toDidi(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/didi";
	}
	
	@RequestMapping(value = "/tovideo.html")
	public String toVideo(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/videoVip";
	}
	
	@RequestMapping(value = "/souhu.html")
	public String souHu(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/video/souhu";
	}
	
	@RequestMapping(value = "/txvideo.html")
	public String txVideo(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/video/txvideo";
	}
	
	@RequestMapping(value = "/youku.html")
	public String youKu(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/video/youku";
	}
	
	@RequestMapping(value = "/aiqiyi.html")
	public String aiQiYi(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/video/aiqiyi";
	}
	/**
	 * 订单列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order-list.html")
	public String order(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/order-list";
	}
	
	/**
	 * 个人信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/oilAccount.html")
	public String oilAccount(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/oilAccount";
	}
	
	/**
	 * 充值页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recharge.html")
	public String recharge(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/recharge";
	}
	
	
	/**
	 * 充值油卡界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/chargecard.html")
	public String chargecard(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/chargecard";
	}
	
	
	/**
	 * 话费充值界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mobile.html")
	public String mobile(HttpServletRequest request) throws Exception {
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/mobile";
	}

	
	/**
	 * 付款结束后: 查看详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/oilCardDetail.html")
	public String oilCardDetail(HttpServletRequest request) throws Exception{
		if(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN)==null){
			return "oilCard/oilLogin";
		}
		return "oilCard/detail";
	}
	
	/**
	 * 发送短信验证码---油卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendSms")
	@ResponseBody
	public DatasetSimple<String> sendSmsForOil(HttpServletRequest request,
			@Required String mobile) throws Exception {
		return smsService.sendSmsCode(mobile,SmsService.SMS_TYPE_1);
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<String> login(HttpServletRequest request,
			@Required String userName,
			@Required String smsCode,
			@Required String tokenCode) throws Exception {
		DatasetSimple<OilUser> user = oilUserService.userLogin(userName, smsCode, tokenCode, IPUtil.getIP(request));
		if(user.isSuccess()){
			return DatasetBuilder.fromDataSimple(getToken(user.getData().getId()));
		}
		return DatasetBuilder.fromMessageSimple(user.getMessage());
	}

	
	/**
	 * 个人信息---油卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/infocenter")
	@ResponseBody
	public DatasetSimple<OilUser> infocenter(HttpServletRequest request) throws Exception {
		checkLoginJson(request);
		DatasetSimple<OilUser> oilUser = oilUserService.
				getOilUser(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN));
		return oilUser;
	}
	
	
	/**
	 * 充值---油卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recharge",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<String> recharge(HttpServletRequest request,
			@Required @Regex("\\d{18}") String rechargeCode) throws Exception {
		//校验登录
		checkLoginJson(request);
		return oilUserService.recharge(this.getUserId(request, AuthUtil.CAR_OIL_TOKEN), rechargeCode);
	}
	
	/**
     * 用户查询自己的订单列表
     * @param request
     * @return
     */
    @RequestMapping(value="queryLogList",method=RequestMethod.POST)
    @ResponseBody
    public DatasetList<Map<String,Object>> queryLogList(HttpServletRequest request,
            String orderNo,String userPhone,String pageNo,
            String pageSize,String orderType) {
        checkLoginJson(request);
        //获取用户id
        Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
        DatasetList<Map<String, Object>> data = oilUserService.queryOilOrders(userId, 
                orderNo, userPhone, pageNo, pageSize,orderType);
        return data;
        
    }
    
    /**
     * 查询订单下拉列表
     * @param request
     * @return
     */
    @RequestMapping(value="queryOilGoodsTypeList",method=RequestMethod.GET)
    @ResponseBody
    public DatasetList<OilGoodsTypeVo> queryOilGoodsTypeList(HttpServletRequest request) {
        /*checkLoginJson(request);
        //获取用户id
        Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);*/
        DatasetList<OilGoodsTypeVo> data = oilUserService.queryOilGoodsTypeList();
        return data;
        
    }
    
	/**
	 * 油卡充值
	 * @param request
	 * @param oilCardNo
	 * @param money
	 * @param goodId
	 * @param mobile
	 * @param orderType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chargeoil",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<String> chargeOil(HttpServletRequest request,
			@Required String oilCardNo,@Required @Regex("\\d+")String money,
			@Required @Regex("\\d+") String goodId, @Required @Regex("\\d{11}")String mobile,
			@Required @Regex("^[0,1]$")String orderType) 
					throws Exception {
		//校验登录
		checkLoginJson(request);
		//获取用户id
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		//ip
		String ip = IPUtil.getIP(request);
		return oilUserService.chargeOilCard(userId, oilCardNo, goodId, money,
				ip,mobile,orderType);//orderType 0:中石化；1：中石油
	}
	
	/**
	 * 话费充值
	 * @param request
	 * @param goodId
	 * @param phone
	 * @param money
	 * @return
	 */
	@RequestMapping(value = "/chargemobile",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<String> chargeMobile(HttpServletRequest request,
			@Required @Regex("\\d+") String goodId,
			@Required @Regex("\\d{11}")String phone,
			@Required @Regex("\\d+") String money){
		//检查登录
		checkLoginJson(request);
		//根据id查询油卡用户信息
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		String ip = IPUtil.getIP(request);
		return oilUserService.chargeMobile(userId, phone, goodId,money,ip);
	}
	
	/**
	 * 流量充值
	 * @param request
	 * @param goodId
	 * @param phone
	 * @param perValue
	 * @return
	 */
	@RequestMapping(value ="/chargeflow",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<String> chargeFlow(HttpServletRequest request,
			@Required @Regex("\\d+") String goodId,
			@Required @Regex("\\d{11}") String phone, 
			@Required String perValue){
		//检查登录
		checkLoginJson(request);
		//根据id查询油卡用户信息
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		String ip = IPUtil.getIP(request);
		return oilUserService.chargeFlow(userId,phone,goodId,perValue,ip);
	}
	
	/**
	 * 欧飞 油卡，话费，流量回调接口
	 * @param ret_code
	 * @param err_msg
	 * @param sporder_id
	 * @param ordersuccesstime
	 */
	@RequestMapping("/ofback")
	@ResponseBody
	public void  ofBack(String ret_code,String err_msg,
			String sporder_id,String ordersuccesstime){
        oilUserService.ofBack(ret_code,err_msg,sporder_id,ordersuccesstime);
        
    }
	
	/**
	 * 获取卡密接口
	 * @param request
	 * @param goodId
	 * @param money
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value ="/getcardsecret",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<Map<String,String>> getCardSecret(HttpServletRequest request,
			@Required @Regex("\\d+") String goodId,@Required String money,
			@Required @Regex("^[5,6,7,8,9]|10$")String orderType){
		//检查登录
		checkLoginJson(request);
		//根据id查询油卡用户信息
		Long userId = this.getUserId(request, AuthUtil.CAR_OIL_TOKEN);
		String ip = IPUtil.getIP(request);
		//orderType :5，猫眼电影，6，滴滴出行，7，优酷，8，爱奇艺，9，腾讯视频，10，搜狐
		return oilUserService.getCardPassword(userId,goodId,money,ip,orderType);
	}
	/**
	 * 查询追电科技商品
	 * @param request
	 * @param goodType
	 * @return
	 */
	@RequestMapping(value = "/queryoilgood",method=RequestMethod.POST)
	@ResponseBody
	public DatasetList<OilGoods> queryOilGood(HttpServletRequest request,
			@Required @Regex("\\d+")String goodType,String mobile){
		//goodType 0中石化油卡;1中石油油卡;2猫眼电影;3爱奇艺;4腾讯视频;5搜狐视频;6优酷视频;7话费充值;8流量充值
		//检查登录
		checkLoginJson(request);
		return oilUserService.queryOilGoods(goodType,mobile);
	}
	/**
	 * 检验追电商品是否可用
	 * @param request
	 * @param goodId
	 * @param phone
	 * @param perValue
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "/checkoilgood",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<String> checkOilGood(HttpServletRequest request,
			@Required @Regex("\\d+") String goodId,
			@Required @Regex("\\d{11}") String phone, 
			@Required String perValue,
			@Required @Regex("^[0,1]$") String orderType){
		//orderType 0话费充值;1流量充值
		//检查登录
		checkLoginJson(request);
		return oilUserService.checkOilGood(phone,goodId,perValue,orderType);
	}
	
	/**
	 * 查询追电科技视频会员卡商品类型
	 * @param request
	 * @param goodType
	 * @return
	 */
	@RequestMapping(value ="/queryvideocard",method=RequestMethod.POST)
	@ResponseBody
	public DatasetList<OilGoodsType> queryVideoCard(HttpServletRequest request){
		//检查登录
		checkLoginJson(request);
		
		return oilUserService.queryVideoCard();
	}
	
	
	/**
	 * 查询滴滴出行追电科技商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/querydidi",method=RequestMethod.POST)
	@ResponseBody
	public DatasetList<Map<String,Object>> queryDidiGood(HttpServletRequest request){
		//检查登录
		checkLoginJson(request);
		
		return oilUserService.queryDidiGood();
	}
	
	/**
	 * 查询追电科会员卡券，优惠券详情信息
	 * @param request
	 * @return 
	 */
	@RequestMapping(value ="/queryvideocardinfo",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<Map<String, Object>> queryVideoCardInfo(HttpServletRequest request,@Required String orderNo){
		//检查登录
		checkLoginJson(request);
		return  oilUserService.getCardInfo(orderNo); 
	}
}
