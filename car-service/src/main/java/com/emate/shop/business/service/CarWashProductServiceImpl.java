package com.emate.shop.business.service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.CarWashProductService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.CarWashPay;
import com.emate.shop.business.model.CarWashPayExample;
import com.emate.shop.business.model.CarWashProduct;
import com.emate.shop.business.model.CarWashProductExample;
import com.emate.shop.business.model.CarWashProductExample.Criteria;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.CarWashShengExample;
import com.emate.shop.business.model.OilAccountRecharge;
import com.emate.shop.business.model.OilAccountRechargeExample;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.business.model.OilUserExample;
import com.emate.shop.business.model.OilcardOrderRelation;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CarWashPayMapper;
import com.emate.shop.mapper.CarWashProductMapper;
import com.emate.shop.mapper.CarWashShengMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.OilAccountRechargeMapper;
import com.emate.shop.mapper.OilUserMapper;
import com.emate.shop.mapper.OilcardOrderRelationMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.ShengDaUtil;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service
@PropertySource(value = "classpath:properties/shengda.properties", ignoreResourceNotFound = true)
public class CarWashProductServiceImpl implements CarWashProductService{

	@Resource
	private CarWashProductMapper carWashProductMapper;
	
	@Resource
	private OilUserMapper oilUserMapper;
	
	@Resource
	private CarWashPayMapper carWashPayMapper;
	
	@Resource
	private CarWashShengMapper carWashShengMapper;
	
	@Resource
	private Environment environment;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private OilAccountRechargeMapper oilAccountRechargeMapper;
	
	@Resource
	private OilcardOrderRelationMapper oilcardOrderRelationMapper;

	@Override
	@Read
	public DatasetList<CarWashProduct> adminProductList(Integer pageNo, Integer pageSize,
			CarWashProduct carWashProduct) {
		CarWashProductExample productEx = new CarWashProductExample();
		Criteria c = productEx.createCriteria();
		if(Objects.nonNull(carWashProduct.getBuyNumber())){
			c.andBuyNumberEqualTo(carWashProduct.getBuyNumber());
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, carWashProductMapper.countByExample(productEx));
		productEx.setLimitStart(page.getStartRow());
		productEx.setLimitEnd(page.getSize());
		productEx.setOrderByClause(CarWashProductExample.BUY_NUMBER_ASC);
		List<CarWashProduct> products = carWashProductMapper.selectByExample(productEx);
		return DatasetBuilder.fromDataList(products, page.createPageInfo());
	}

	@Override
	@Read
	public DatasetSimple<CarWashProduct> queryProductById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("参数不能为空~~");
		}
		CarWashProduct product = carWashProductMapper.selectByPrimaryKey(Long.valueOf(id));
		return DatasetBuilder.fromDataSimple(product);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> editProduct(CarWashProduct carWashProduct) {
		if(Objects.isNull(carWashProduct.getBuyNumber())){
			throw new BusinessException("购买次数不能为空~~");
		}
		if(carWashProduct.getBuyNumber()<=0){
			throw new BusinessException("购买次数不能小于等于0次");
		}
		if(Objects.isNull(carWashProduct.getPrice())){
			throw new BusinessException("套餐价格不能为空~~");
		}
		if(carWashProduct.getPrice().doubleValue()<=0){
			throw new BusinessException("套餐价格不能小于等于0元");
		}
		CarWashProductExample productEx = new CarWashProductExample();
		productEx.createCriteria()
		.andBuyNumberEqualTo(carWashProduct.getBuyNumber());
		List<CarWashProduct> products = carWashProductMapper.selectByExample(productEx);
	
		carWashProduct.setUpdateTime(new Date());
		if(Objects.nonNull(carWashProduct.getId())){
			if(!products.isEmpty()){
				if(!Objects.equals(products.get(0).getId(),carWashProduct.getId())){
					throw new BusinessException("该洗车商品已存在~~");
				}	
			}
			if((carWashProductMapper.updateByPrimaryKeySelective(carWashProduct))!=1){
				throw new BusinessException("更新洗车商品信息失败");
			};
		}else{
			if(!products.isEmpty()){
				throw new BusinessException("该洗车商品已存在~~");
			}
			carWashProduct.setCreateTime(new Date());
			if((carWashProductMapper.insertSelective(carWashProduct))!=1){
				throw new BusinessException("添加洗车商品失败~~");
			};
		}
		return DatasetBuilder.fromDataSimple(true);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> delProduct(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("参数不能为空~~");
		}
		CarWashProduct product = carWashProductMapper.selectByPrimaryKey(Long.valueOf(id));
		if(Objects.isNull(product)){
			throw new BusinessException("未查到该洗车商品信息");
		}
		if((carWashProductMapper.deleteByPrimaryKey(Long.valueOf(id)))!=1){
			throw new BusinessException("删除洗车商品信息失败");
		};
		return DatasetBuilder.fromDataSimple(true);
	}
	
	@Override
	@Read
	public DatasetList<Map<String,Object>> payList(Integer pageNo, Integer pageSize,
			String userPhone,String orderNo,String status,Long userId) {
		CarWashPayExample ex = new CarWashPayExample();
		com.emate.shop.business.model.CarWashPayExample.Criteria c = ex.createCriteria();
		//用户手机号
		if(!StringUtils.isEmpty(userPhone)){
			OilUserExample oilUserEx= new OilUserExample();
			oilUserEx.createCriteria().andPhoneEqualTo(userPhone);
			List<Long> userIds = oilUserMapper.selectByExample(oilUserEx).stream()
					.map(OilUser::getId).
					distinct().collect(Collectors.toList());
			userIds.add(0L);
			c.andUserIdIn(userIds);
		}
		//充值购买次数订单号
		if(StringUtils.isNotEmpty(orderNo)){
			c.andOrderNoEqualTo(orderNo);
		}
		//订单状态
		if(StringUtils.isNotEmpty(status)){
			c.andStatusEqualTo(Integer.valueOf(status));
		}
		//用户id
		if(Objects.nonNull(userId)){
			c.andUserIdEqualTo(userId);
		}
		PaginationUtil page = new PaginationUtil(pageNo,pageSize,carWashPayMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		ex.setOrderByClause(CarWashPayExample.CREATE_TIME_DESC);
		List<CarWashPay> payList = carWashPayMapper.selectByExample(ex);
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		if(payList.isEmpty()){
			return DatasetBuilder.fromDataList(results, page.createPageInfo());
		}
		List<Long> userIds = payList.stream().map(CarWashPay::getUserId).distinct().collect(Collectors.toList());
		userIds.add(0L);
		OilUserExample userEx= new OilUserExample();
		userEx.createCriteria().andIdIn(userIds);
		
		List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
		Map<Long, String> userMap = new HashMap<Long,String>();
		oilUsers.forEach(user ->{
			userMap.put(user.getId(), user.getPhone());
		});
		for(CarWashPay carWashPay:payList){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id", carWashPay.getId());
			map.put("accountPay", carWashPay.getAccountPay());
			map.put("actualPay", carWashPay.getActualPay());
			map.put("createTime", carWashPay.getCreateTime());
			map.put("orderNo", carWashPay.getOrderNo());
			map.put("status", carWashPay.getStatus());
			map.put("totalPay", carWashPay.getTotalPay());
			map.put("updateTime", carWashPay.getUpdateTime());
			map.put("washNumber", carWashPay.getWashNumber());
			map.put("userId", carWashPay.getUserId());
			map.put("phone", userMap.get(carWashPay.getUserId()));
			results.add(map);
		}
		return DatasetBuilder.fromDataList(results, page.createPageInfo());
	}
	
	@Override
	@Read
	public DatasetList<CarWashSheng> shengList(Integer pageNo, Integer pageSize,
			String userPhone,String orderNo,String shopName,String orderCode,Long userId) {
		CarWashShengExample ex = new CarWashShengExample();
		com.emate.shop.business.model.CarWashShengExample.Criteria c = ex.createCriteria();
		//用户手机号
		if(StringUtils.isNotEmpty(userPhone)){
			c.andPhoneEqualTo(userPhone);
		}
		//洗车劵订单号
		if(StringUtils.isNotEmpty(orderNo)){
			c.andOrderNoEqualTo(orderNo);
		}
		//洗车商家名称
		if(StringUtils.isNotEmpty(shopName)){
			c.andShopNameEqualTo(shopName);
		}
		//洗车劵码
		if(StringUtils.isNotEmpty(orderCode)){
			c.andOrderCodeEqualTo(orderCode);
		}
		if(Objects.nonNull(userId)){
			c.andUserIdEqualTo(userId);
		}
		c.andWashTypeEqualTo(CarWashSheng.WASH_TYPE_1);
		PaginationUtil page = new PaginationUtil(pageNo,pageSize,carWashShengMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		ex.setOrderByClause(CarWashPayExample.CREATE_TIME_DESC);
		List<CarWashSheng> shengList = carWashShengMapper.selectByExample(ex);
		return DatasetBuilder.fromDataList(shengList, page.createPageInfo());
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addWashChargeOrder(String productId,Long userId) {
		
		OilUser oilUser = definedMapper.queryOilUserbyId(userId);
		if(StringUtils.isEmpty(productId)){
			throw new BusinessException("洗车套餐id为空~~");
		}
		CarWashProduct carWashProduct = carWashProductMapper.selectByPrimaryKey(Long.valueOf(productId));
		if(Objects.isNull(carWashProduct)){
			throw new BusinessException("该洗车套餐不存在~");
		}
		if(oilUser.getMoney().doubleValue()<carWashProduct.getPrice().doubleValue()){
			throw new BusinessException("账户余额不足~~");
		}
		Integer number = carWashProduct.getBuyNumber()+carWashProduct.getPresentNumber();
		String orderNo = RandomUtil.getOrderSn();
		//更新账户余额
		String money = String .valueOf(carWashProduct.getPrice().doubleValue());
		changeUserMoney(oilUser, money, orderNo, number);
		//添加订单
		CarWashPay carWashPay = new CarWashPay();
		carWashPay.setOrderNo(orderNo);
		carWashPay.setCreateTime(new Date());
		carWashPay.setUpdateTime(new Date());
		carWashPay.setUserId(oilUser.getId());
		carWashPay.setTotalPay(carWashProduct.getPrice());
		carWashPay.setAccountPay(carWashProduct.getPrice());
		carWashPay.setStatus(CarWashPay.STATUS_2);
		carWashPay.setWashNumber(number);
		if((carWashPayMapper.insertSelective(carWashPay))!=1){
			oilUser.setMoney(oilUser.getMoney().add(carWashProduct.getPrice()));
			oilUser.setWashNumber(oilUser.getWashNumber()-number);
			if(Objects.isNull(oilUser.getCreateTime())){
				oilUser.setCreateTime(new Date());
			}
			oilUser.setUpdateTime(new Date());
			oilUserMapper.updateByPrimaryKeySelective(oilUser);
			throw new BusinessException("添加购买信息失败~");
		};
		return DatasetBuilder.fromDataSimple(true);
	}
	
	private BigDecimal changeUserMoney(OilUser oilUser,String money,String orderNo,Integer washNum){
		Log4jHelper.getLogger().info("购买洗车次数扣减账户余额："
				+ "充值账号："+oilUser.getPhone()
				+",当前余额："+oilUser.getMoney().intValue()
				+",充值金额："+money
				+",购买次数："+washNum);
		BigDecimal orderMoney = new BigDecimal(money);
		BigDecimal userMoney = oilUser.getMoney().subtract(orderMoney);
		//判断账户余额是否充足
		if(oilUser.getMoney().intValue()<0 || userMoney.intValue()<0){
			throw new BusinessException("用户余额不足,请先充值。当前余额:"+oilUser.getMoney().intValue());
		}
		//赋值余额
		oilUser.setMoney(userMoney);
		//赋值洗车次数
		if(Objects.isNull(oilUser.getWashNumber())){
			oilUser.setWashNumber(washNum);
		}else{
			oilUser.setWashNumber(oilUser.getWashNumber()+washNum);
		}
		//修改更新时间
		oilUser.setUpdateTime(new Date());
		if(Objects.isNull(oilUser.getCreateTime())){
			oilUser.setCreateTime(new Date());
		}
		if(oilUserMapper.updateByPrimaryKeySelective(oilUser)!=1){
			throw new BusinessException("扣减金额失败");
		};
		
		OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
		oilAccountRechargeEx.createCriteria()
			.andUserIdEqualTo(oilUser.getId())
			.andMoneyGreaterThan(BigDecimal.ZERO);
		oilAccountRechargeEx.setOrderByClause(OilAccountRechargeExample.CREATE_TIME_ASC);;
		List<OilAccountRecharge> oilAccountList = oilAccountRechargeMapper.selectByExample(oilAccountRechargeEx);
		
		oilAccountRechargeEx.clear();
		oilAccountRechargeEx.createCriteria()
			.andUserIdEqualTo(oilUser.getId());
		int count = oilAccountRechargeMapper.countByExample(oilAccountRechargeEx);
		if(count<=0){
			//添加账户虚拟油卡信息
			OilAccountRecharge oilAccountRecharge = new OilAccountRecharge();
			oilAccountRecharge.setCreateTime(new Date());
			oilAccountRecharge.setUpdateTime(new Date());
			oilAccountRecharge.setMoney(oilUser.getMoney());
			oilAccountRecharge.setCardNo("a"+oilUser.getPhone());
			oilAccountRecharge.setUserId(oilUser.getId());
			oilAccountRecharge.setUserPhone(oilUser.getPhone());
			oilAccountRechargeMapper.insertSelective(oilAccountRecharge);
			//添加订单消费记录
			OilcardOrderRelation oilcardOrder = new OilcardOrderRelation();
			oilcardOrder.setCreateTime(new Date());
			oilcardOrder.setUpdateTime(new Date());
			oilcardOrder.setOrderNo(orderNo);
			oilcardOrder.setOrderType(OilcardOrderRelation.ORDER_TYPE_2);
			oilcardOrder.setCardNo(oilAccountRecharge.getCardNo());
			oilcardOrder.setMoney(orderMoney);
			oilcardOrder.setCardMoney(oilUser.getMoney());//充值卡剩余金额
			oilcardOrder.setUserId(oilUser.getId());
			oilcardOrderRelationMapper.insertSelective(oilcardOrder);
		}else{
			if(oilAccountList.isEmpty()){
				throw new BusinessException("账户扣款异常，请联系客服");
			}
			for (int i = 0; i < oilAccountList.size(); i++) {
				OilAccountRecharge oilAccount = oilAccountList.get(i);
				BigDecimal oilMoney = oilAccount.getMoney();
				if(orderMoney.compareTo(oilMoney)>0){
					if(i==oilAccountList.size()-1){
						throw new BusinessException("子账户余额不足!!!");
					}
					oilAccount.setMoney(BigDecimal.ZERO);
					oilAccount.setUpdateTime(new Date());
					oilAccountRechargeMapper.updateByPrimaryKeySelective(oilAccount);
					orderMoney=orderMoney.subtract(oilMoney);
					OilcardOrderRelation oilcardOrder = new OilcardOrderRelation();
					oilcardOrder.setCreateTime(new Date());
					oilcardOrder.setUpdateTime(new Date());
					oilcardOrder.setOrderNo(orderNo);
					oilcardOrder.setOrderType(OilcardOrderRelation.ORDER_TYPE_2);
					oilcardOrder.setCardNo(oilAccount.getCardNo());
					oilcardOrder.setMoney(oilMoney);
					oilcardOrder.setCardMoney(BigDecimal.ZERO);//充值卡剩余金额
					oilcardOrder.setUserId(oilUser.getId());
					oilcardOrderRelationMapper.insertSelective(oilcardOrder);
					continue;
				}else{
					oilMoney = oilMoney.subtract(orderMoney);
					oilAccount.setMoney(oilMoney);
					oilAccount.setUpdateTime(new Date());
					oilAccountRechargeMapper.updateByPrimaryKeySelective(oilAccount);
					OilcardOrderRelation oilcardOrder = new OilcardOrderRelation();
					oilcardOrder.setCreateTime(new Date());
					oilcardOrder.setUpdateTime(new Date());
					oilcardOrder.setOrderNo(orderNo);
					oilcardOrder.setOrderType(OilcardOrderRelation.ORDER_TYPE_2);
					oilcardOrder.setCardNo(oilAccount.getCardNo());
					oilcardOrder.setMoney(orderMoney);
					oilcardOrder.setCardMoney(oilMoney);//充值卡剩余金额
					oilcardOrder.setUserId(oilUser.getId());
					oilcardOrderRelationMapper.insertSelective(oilcardOrder);
					break;
				}
			}
		}
		return userMoney;
	}
	@Override
	@Read
	public DatasetList<CarWashProduct> h5WashProduct() {
		CarWashProductExample ex = new CarWashProductExample();
		ex.setOrderByClause(CarWashProductExample.BUY_NUMBER_ASC);
		List<CarWashProduct> products = carWashProductMapper.selectByExample(ex);
		return DatasetBuilder.fromDataList(products);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<CarWashSheng> h5GetWashCoupon(Long userId, String washType) {
		//查询用户是否存在
		OilUser oilUser = oilUserMapper.selectByPrimaryKey(userId);
		if(Objects.isNull(oilUser)){
			throw new BusinessException("用户不存在~~");
		}
		//判断  是否存在未使用的洗车券
		CarWashShengExample ex = new CarWashShengExample();
		com.emate.shop.business.model.CarWashShengExample.Criteria c = ex.createCriteria();
		c.andUserIdEqualTo(userId)
		.andFinishTimeIsNull()
		.andCouponExpirDateGreaterThanOrEqualTo(new Date());
		if("1".equals(washType)){
			c.andWashTypeEqualTo(CarWashSheng.WASH_TYPE_1);
		}else if("0".equals(washType)){
			c.andWashTypeEqualTo(CarWashSheng.WASH_TYPE_0);
		}
		ex.setOrderByClause(CarWashShengExample.CREATE_TIME_DESC);
		List<CarWashSheng> shenglist = carWashShengMapper.selectByExample(ex);
		if(!shenglist.isEmpty()&&shenglist.size()>0){
			//存在未使用的洗车券
			Log4jHelper.getLogger().info("存在未使用的洗车券,直接返回.");
			return DatasetBuilder.fromDataSimple(shenglist.get(0));
		}
		//判断是否存在洗车次数
		if(oilUser.getWashNumber()<=0){
			throw new BusinessException("您的洗车次数不够！");
		}
		
		//调用接口之前记录日志
		CarWashSheng carWashSheng = new CarWashSheng();
		carWashSheng.setUserId(userId);
		carWashSheng.setPhone(oilUser.getPhone());
		carWashSheng.setCreateTime(new Date());
		carWashSheng.setUpdateTime(new Date());
		carWashSheng.setOrderNo("d"+RandomUtil.getOrderSn());
		carWashSheng.setStatus(CarWashSheng.STATUS_0);
		carWashSheng.setWashType(CarWashSheng.WASH_TYPE_1);
		if(carWashShengMapper.insertSelective(carWashSheng) != 1){
			throw new BusinessException("新增数据库洗车记录失败！");
		};
		//调用接口 
		Log4jHelper.getLogger().info("开始调用接口获取洗车券！！");
		carWashSheng = this.getShengCarWash(carWashSheng, oilUser);
		
		//更新用户次数
		oilUser.setWashNumber(oilUser.getWashNumber()-1);
		oilUser.setUpdateTime(new Date());
		if(Objects.isNull(oilUser.getCreateTime())){
			oilUser.setCreateTime(new Date());
		}
		if(oilUserMapper.updateByPrimaryKeySelective(oilUser) != 1){
			throw new BusinessException("更新数据库用户信息失败！");
		};
		if(carWashShengMapper.updateByPrimaryKeySelective(carWashSheng) != 1){
			throw new BusinessException("更新数据库洗车记录失败！");
		}
		return DatasetBuilder.fromDataSimple(carWashSheng);
	}
	
	/**
	 * 调用盛大接口获取洗车券
	 * @param wash
	 * @param u
	 * @return
	 */
	private CarWashSheng getShengCarWash(CarWashSheng wash,OilUser u){
		Map<String, String> map = new LinkedMap<String,String>();
		map.put("source", environment.getRequiredProperty("shengda.carwash.source"));//渠道号
		map.put("orgSource", environment.getRequiredProperty("shengda.carwash.orgSource"));//机构来源
		map.put("order", wash.getOrderNo());//订单号
		map.put("carType", environment.getRequiredProperty("shengda.carwash.carType"));//大小车类型 01小车 噢02大车
		map.put("userInfo", u.getPhone());
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
	
}
