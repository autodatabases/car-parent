package com.emate.shop.business.service;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.DidiService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.CouponOrders;
import com.emate.shop.business.model.CouponOrdersExample;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.business.model.OilGoods;
import com.emate.shop.business.model.OilGoodsExample;
import com.emate.shop.business.model.OilLog;
import com.emate.shop.business.model.OilProvider;
import com.emate.shop.business.model.OilProviderExample;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.business.model.UserInfoExample;
import com.emate.shop.business.vo.ofvo.OufeiCardPassword;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CouponOrdersMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.mapper.OilGoodsMapper;
import com.emate.shop.mapper.OilLogMapper;
import com.emate.shop.mapper.OilProviderMapper;
import com.emate.shop.mapper.UserInfoMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.oufei.OufeiOilUtil;

@Service
@PropertySource(value = "classpath:properties/oilcard.properties", ignoreResourceNotFound = true)
public class DidiServiceImpl implements DidiService{

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private CouponOrdersMapper couponOrdersMapper;
	
	@Resource
	private OilGoodsMapper oilGoodsMapper;
	
	@Resource
	private OilProviderMapper oilProviderMapper;
	
    @Resource
    private Environment environment;
    
    @Resource
    private OilLogMapper oilLogMapper;

	/**
	 * 获取滴滴代驾券的兑换情况
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Read
	public DatasetSimple<Map<String, Integer>> getCouponNum(Long userId) {
		
		List<ImportUserInfo> importUserInfoList = getInfo(userId);
		//组织未使用的滴滴代驾券
		Integer unusedCoupon20 = 0;
		Integer unusedCoupon50 = 0;
		for(ImportUserInfo importUserInfo:importUserInfoList){
			String surplusCouponValue = importUserInfo.getSurplusCouponValue();
			if(StringUtils.isEmpty(surplusCouponValue)){
				continue;
			}
			Map<String, Integer> couponMap = new HashMap<String,Integer>();
			couponMap = JacksonHelper.fromJSON(surplusCouponValue, Map.class);
			unusedCoupon20+=couponMap.get("20");
			unusedCoupon50+=couponMap.get("50");
		}
		Integer unusedCoupon = unusedCoupon20+unusedCoupon50;
		//组织已使用的滴滴代驾券
		CouponOrdersExample couponOrdersEx = new CouponOrdersExample();
		couponOrdersEx.createCriteria()
			.andUserIdEqualTo(userId)
			.andOrderStatusEqualTo(CouponOrders.ORDER_STATUS_1);
		List<CouponOrders> couponOrdersList = couponOrdersMapper.selectByExample(couponOrdersEx);
		Integer usedCoupon = couponOrdersList.size();
		Map<String, Integer> result = new HashMap<String,Integer>();
		result.put("unusedCoupon20", unusedCoupon20);//20元的未使用的优惠券
		result.put("unusedCoupon50", unusedCoupon50);//50元的未使用的优惠券
		result.put("unusedCoupon", unusedCoupon);//总的未使用的优惠券
		result.put("usedCoupon", usedCoupon);//已使用的优惠券
		return DatasetBuilder.fromDataSimple(result);
	}

	/**
	 * 获取滴滴代驾券的兑换记录
	 */
	@Override
	@Read
	public DatasetList<Map<String, Object>> getCouponRecord(Integer pageNo, 
			Integer pageSize, Long userId) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		if(Objects.isNull(userId)){
			throw new BusinessException("请先登陆");
		}
		CouponOrdersExample couponOrdersEx = new CouponOrdersExample();
		couponOrdersEx.createCriteria().andUserIdEqualTo(userId);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, couponOrdersMapper.countByExample(couponOrdersEx));
		couponOrdersEx.setLimitStart(page.getStartRow());
		couponOrdersEx.setLimitEnd(page.getSize());;
		couponOrdersEx.setOrderByClause(CouponOrdersExample.CREATE_TIME_DESC);
		List<CouponOrders> couponOrdersList = couponOrdersMapper.selectByExample(couponOrdersEx);
		
		
		for(CouponOrders couponOrders:couponOrdersList){
			Map<String, Object> result = new HashMap<String,Object>();
			result.put("goodName", couponOrders.getGoodIntroduce());
			result.put("goodSecret", couponOrders.getCardSecret());
			result.put("createTime", couponOrders.getCreateTime());
			result.put("expireTime", couponOrders.getExpireTime());
			results.add(result);
		}
		return DatasetBuilder.fromDataList(results, page.createPageInfo());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> addCouponOrders(
			String content, Long userId,String userIp) {
		//1.0滴滴代驾券20，50
		if(!Arrays.asList(new String[] { "20","50"}).contains(content)){
			Log4jHelper.getLogger().error("兑换代驾券面额不符合规则");
			throw new BusinessException("兑换代驾券面额不符合规则");
		}
		//2.0 惠车服用户校验,减少剩余次数
		User user = userMapper.selectByPrimaryKey(userId);
		List<ImportUserInfo> importUserInfoList = getInfo(userId);
		Integer unusedCoupon = 0;
		for(ImportUserInfo importUserInfo:importUserInfoList){
			String surplusCouponValue = importUserInfo.getSurplusCouponValue();
			if(StringUtils.isEmpty(surplusCouponValue)){
				continue;
			}
			Map<String, Integer> couponMap = new HashMap<String,Integer>();
			couponMap = JacksonHelper.fromJSON(surplusCouponValue, Map.class);
        	Integer couponNum = couponMap.get(content);
        	unusedCoupon+=couponNum;
        	if(couponNum>0){
        		couponNum-=1;
            	couponMap.put(content, couponNum);
            	String json = JacksonHelper.toJSON(couponMap);
            	importUserInfo.setSurplusCouponValue(json);
            	if(importUserInfoMapper.updateByPrimaryKeySelective(importUserInfo)!=1){
            		Log4jHelper.getLogger().error("更新滴滴代驾券张数失败");
                    throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
            	};
    			break;
    		}
		}
		if(unusedCoupon<=0){
			Log4jHelper.getLogger().error("您已没有"+content+"元的滴滴代驾券需要兑换");
			throw new BusinessException("您已没有"+content+"元的滴滴代驾券需要兑换");
		}
      
		//3.0 检验该商品状态
		OilGoods oilGoods=checkOilGood(content);
		
		//4.0 调用第三方接口
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andStatusEqualTo(OilProvider.STATUS_1);
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);
        if (oilProviderList.size() != 1) {
            Log4jHelper.getLogger().error("欧飞充值账户查询异常");
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
        OilProvider oilProvider = oilProviderList.get(0);
        OufeiCardPassword oufeiCardPassword = null;
        String orderNo = RandomUtil.getOrderSn();
        try {
            oufeiCardPassword = ofGetCardpwd(user.getName(), orderNo,
                    oilGoods.getProductId(), oilProvider);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("滴滴代驾券异常：" + e.getMessage());
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
        //5.0创建订单
        CouponOrders couponOrders = new CouponOrders();
        couponOrders.setUserId(userId);//用户id
        couponOrders.setOrderNo(orderNo);//订单号
        couponOrders.setLicense(importUserInfoList.get(0).getChePai());
        String cardno = oufeiCardPassword.getCards().getCards().get(0)
                .getCardno();
        couponOrders.setCardNo(cardno);//卡号
        couponOrders.setUserIp(userIp);//登陆ip
        couponOrders.setCreateTime(new Date());//创建时间
        couponOrders.setUpdateTime(new Date());//更新时间
        couponOrders.setProviderOrderNo(oufeiCardPassword.getOrderid());//欧飞订单号
        couponOrders
                .setShopMoney(new BigDecimal(oufeiCardPassword.getOrdercash()));//欧飞价格
        couponOrders.setGoodId(oufeiCardPassword.getCardid());//欧飞产品编号
        couponOrders.setGoodIntroduce(oufeiCardPassword.getCardname());//欧飞商品介绍
        couponOrders.setProvider(oilProvider.getId());//欧飞接口
        String cardpws = oufeiCardPassword.getCards().getCards().get(0)
                .getCardpws();
        couponOrders.setCardSecret(cardpws);//流量面值
        String expiretime = oufeiCardPassword.getCards().getCards().get(0)
                .getExpiretime();
        couponOrders.setExpireTime(expiretime);
        couponOrders.setOrderStatus(CouponOrders.ORDER_STATUS_1);
        couponOrders.setFinishMoney(new BigDecimal(content));//充值金额
        couponOrders.setFinishTime(new Date());
        if (couponOrdersMapper.insertSelective(couponOrders) != 1) {
            Log4jHelper.getLogger().error("添加订单记录失败");
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("orderNo", orderNo);
        result.put("cardSecret", cardpws);
		return DatasetBuilder.fromDataSimple(result);
	}
	
	private List<ImportUserInfo> getInfo(Long userId){
		if(Objects.isNull(userId)){
			throw new BusinessException("请先登陆再进行兑换");
		}
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.or().andUserIdEqualTo(userId);
		List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoEx);
		if(userInfoList.isEmpty()||StringUtils.isEmpty(userInfoList.get(0).getLicense())){
			throw new BusinessException("未绑定车牌");
		}
		String license = userInfoList.get(0).getLicense();
		
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		importUserInfoEx.or().andChePaiEqualTo(license);
		importUserInfoEx.or().andChePaiEqualTo(license+"newyear");
		importUserInfoEx.setOrderByClause(ImportUserInfoExample.CREATE_TIME_ASC);
		List<ImportUserInfo> importUserInfoList = importUserInfoMapper.selectByExample(importUserInfoEx);
		
		if(importUserInfoList.isEmpty()){
			throw new BusinessException("未导入车牌");
		}
		return importUserInfoList;
	}
	/**
	 * 
	 * @param content
	 * @return
	 */
    private OilGoods checkOilGood(String content) {
    	OilGoodsExample oilGoodsEx = new OilGoodsExample();
		if("20".equals(content)){
			oilGoodsEx.or().andProductIdEqualTo("1500120");//欧飞20元滴滴代驾券
		}else{
			oilGoodsEx.or().andProductIdEqualTo("1500122");//欧飞50元滴滴代驾券
		}
    	List<OilGoods> oilGoodsList = oilGoodsMapper.selectByExample(oilGoodsEx);
        if (oilGoodsList.size()!=1) {
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
        OilGoods oilGoods = oilGoodsList.get(0);
        if (2 == oilGoods.getOneStatus()
        		||2 == oilGoods.getStatus()
        		||3 == oilGoods.getStatus()
        		||4 == oilGoods.getStatus()) {
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
        return oilGoods;
    }
    
    /**
     * 获取卡密接口
     * @param phone
     * @param orderNo
     * @param cardid
     * @param oilProvider
     * @param goodType
     * @return
     */
    private OufeiCardPassword ofGetCardpwd(String phone, String orderNo,
            String cardid, OilProvider oilProvider) {
        //1.0获取流量充值请求url
        String chargeFlowUrl = this.environment
                .getRequiredProperty("ofoil.getcardpassword.url");
        //2.0组织流量充值接口请求参数
        Map<String, String> params = new HashMap<String, String>();
        String keyStr = this.environment.getRequiredProperty("ofoil.key");
        String version = this.environment.getRequiredProperty("ofoil.version");
        params.put("userid", oilProvider.getUserName());//用户名
        params.put("userpws", oilProvider.getUserPassword());//用户密码
        params.put("phone", phone);//接受卡密的手机号
        params.put("cardid", cardid);//欧飞产品编号
        params.put("cardnum", "1");//充值金额
        params.put("sporder_id", orderNo);//Sp商家的订单号(商户传给欧飞的唯一编号)
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("sporder_time", format.format(new Date()));//提交时间
        params.put("keyStr", keyStr);//签名串
        params.put("version", version);//固定值
        Log4jHelper.getLogger().info("欧飞滴滴代驾券接口开始调用~~");
        //3.0组织下单日志参数
        OilLog oilLog = new OilLog();
        oilLog.setCreateTime(new Date());
        oilLog.setUpdateTime(new Date());
        oilLog.setOrderType(OilLog.ORDER_TYPE_6);
        oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_0);
        oilLog.setOrderNo(orderNo);
        //4.0调用话费下单接口
        OufeiCardPassword oufeiCardPassword = null;
        try {
            oufeiCardPassword = OufeiOilUtil.getCardPassWordByOf(chargeFlowUrl,
                    params, oilLog);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("欧飞滴滴代驾券调用异常~~" + e.getMessage());
            oilLogMapper.insertSelective(oilLog);
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
        //5.0添加下的那记录
        oilLogMapper.insertSelective(oilLog);
        Log4jHelper.getLogger()
                .info("欧飞滴滴代驾券接口响应:" + JacksonHelper.toJSON(oufeiCardPassword));
        //下单成功
        if ("1".equals(oufeiCardPassword.getRetcode())
                || "1043".equals(oufeiCardPassword.getRetcode())
                || "334".equals(oufeiCardPassword.getRetcode())
                || "105".equals(oufeiCardPassword.getRetcode())
                || "9999".equals(oufeiCardPassword.getRetcode())) {
            return oufeiCardPassword;
            //下单失败
        } else {
            Log4jHelper.getLogger().error("欧飞滴滴代驾券接口调用失败："
                    + OufeiOilUtil.getMsg(oufeiCardPassword.getRetcode()));
            throw new BusinessException("系统维护中，暂时无法出货，请稍后重试");
        }
    }
    public static void main(String[] args) {
		Map<String, Integer> hashMap = new HashMap<String,Integer>();
		hashMap.put("20", 20);
		hashMap.put("50", 20);
		String json = JacksonHelper.toJSON(hashMap);
		System.out.println(json);
	}
}
