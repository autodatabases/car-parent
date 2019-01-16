package com.emate.shop.business.service;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.UserService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.AutoInfoExample;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.business.model.AutoposeExample;
import com.emate.shop.business.model.BusinessInfo;
import com.emate.shop.business.model.BusinessInfoExample;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.business.model.CarwashConfigExample;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.business.model.ImportUserInfoExample.Criteria;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.OrdersExample;
import com.emate.shop.business.model.RandomCode;
import com.emate.shop.business.model.RandomCodeExample;
import com.emate.shop.business.model.SalesmanStatistics;
import com.emate.shop.business.model.SalesmanStatisticsExample;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerExample;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.SystemUserExample;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserExample;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.business.model.UserInfoExample;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AutoInfoMapper;
import com.emate.shop.mapper.AutoposeMapper;
import com.emate.shop.mapper.BusinessInfoMapper;
import com.emate.shop.mapper.CarwashConfigMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.mapper.OrdersMapper;
import com.emate.shop.mapper.RandomCodeMapper;
import com.emate.shop.mapper.SalesmanStatisticsMapper;
import com.emate.shop.mapper.SellerMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.mapper.UserInfoMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.PinyinTool;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    StandardPasswordEncoder passwordEncoder;
    
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private AutoposeMapper autoposeMapper;
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private RandomCodeMapper randomCodeMapper;
	
	@Resource
	private SellerMapper sellerMapper;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	
	@Resource
	private SalesmanStatisticsMapper salesmanStatisticsMapper;
	
	@Resource
	private CarwashConfigMapper carwashConfigMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private AutoInfoMapper autoInfoMapper;
	
	/**
	 * 查询用户登录操作
	 */
	@Read
	@Override 
	public DatasetList<User> queryUserByName(String userName) {
		UserExample ex = new UserExample();
		ex.createCriteria().andNameEqualTo(userName);
		return DatasetBuilder.fromDataList(userMapper.selectByExample(ex));
	}
	
	@Read
	@Override
	public DatasetSimple<User> queryUserById(String id) {
		return DatasetBuilder.fromDataSimple(userMapper.selectByPrimaryKey(Long.parseLong(id)));
	}

	/**
	 * 汽车智库
	 * 管理员登录
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<SystemUser> adminLogin(String userName, String password,String ip) {
		SystemUserExample ex = new SystemUserExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		List<SystemUser> user = systemUserMapper.selectByExample(ex);
		if(user.isEmpty()){
			return DatasetBuilder.fromMessageSimple("用户不存在！");
		}
		SystemUser u = user.get(0);
		if(u == null){
			return DatasetBuilder.fromMessageSimple("查询用户为空！");
		}
		if(!passwordEncoder.matches(password, u.getPassword())){
			return DatasetBuilder.fromMessageSimple("用户密码错误！");
		}
		u.setLoginTime(u.getLoginTime()+1);
		u.setUpdateTime(new Date());
		u.setLastLoginTime(new Date());
		u.setLastLoginIp(ip);
		systemUserMapper.updateByPrimaryKeySelective(u);
		return DatasetBuilder.fromDataSimple(u);
	}
	
	/**
	 * 惠+车服用户注册
	 * @param userName
	 * @param password
	 * @param regType 用户注册类型 0,普通用户注册,1,车险用户注册
	 * @return
	 */
	@Override
	@Transactional
	@Write
	public DatasetSimple<User> userRegister(String regType,String userName, String smsCode,String tokenCode,String ip,String chepai,String password) {
		DatasetList<User> users = queryUserByName(userName);
		if(!users.getList().isEmpty()){
			throw new BusinessException("用户已存在！");
		}
		try{
			if(StringUtils.isEmpty(tokenCode)){
				throw new BusinessException("验证码已经失效，请重新获取！");
			}
			if(!passwordEncoder.matches(smsCode+userName, tokenCode)){
				throw new BusinessException("验证码不正确！");
			}
		}catch(Exception e){
			throw new BusinessException("验证码不正确！");
		}
		
		RandomCode code = null;
		ImportUserInfo importUserInfo = null;
		if("1".equals(regType)){ //校验刮刮卡信息和车牌是否绑定信息
			RandomCodeExample randomCodeEx = new RandomCodeExample();
			randomCodeEx.createCriteria().andCodeEqualTo(password)
			.andStatusEqualTo(RandomCode.STATUS_0);
			List<RandomCode> codeList = randomCodeMapper.selectByExample(randomCodeEx);
			if(codeList.isEmpty()){
				throw new BusinessException("随机密码不正确！");
			}
			code = codeList.get(0);
			ImportUserInfoExample importUserInfoExample = new ImportUserInfoExample();
			importUserInfoExample.createCriteria().andChePaiEqualTo(chepai);
			List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importUserInfoExample);
			if(!importUserInfos.isEmpty()){
				importUserInfo = importUserInfos.get(0);
			}else{
				throw new BusinessException("用户没有导入车牌信息！");
			}
			
		}
		// 第一次扫码用户，自动注册
		User newUser = new User();
		newUser.setName(userName);
		newUser.setNickName(importUserInfo == null?null:importUserInfo.getRealName());
		newUser.setUserType(User.USER_TYPE_1);
		newUser.setCreateTime(new Date());
		newUser.setLastLoginIp(ip);
		newUser.setLastLoginTime(new Date());
		userMapper.insertSelective(newUser);

		users = queryUserByName(userName);

		if (users.getList().isEmpty()) {
			throw new BusinessException("注册失败！新增用户失败！");
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(users.getList().get(0).getId());
		userInfo.setCreateTime(new Date());
		userInfo.setUpdateTime(new Date());
		if("1".equals(regType)){//根据用户导入信息匹配用户车型
			userInfo.setLicense(chepai);
		}
		if (userInfoMapper.insertSelective(userInfo) != 1) {
			throw new BusinessException("添加用户信息失败！");
		}
		
		if("1".equals(regType) && code!=null){
			//随机码只能使用一次
			code.setStatus(RandomCode.STATUS_1);
			randomCodeMapper.updateByPrimaryKeySelective(code);
		}
		return DatasetBuilder.fromDataSimple(users.getList().get(0));
	}

	@Write
	@Transactional
	@Override
	public DatasetSimple<Boolean> setCarInfo(Long userId,Long autoId,String pic,String carCode,String mileage,String license) {
		UserInfoExample ex = new UserInfoExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo>  userInfoList = userInfoMapper.selectByExample(ex);
		if(userInfoList.size()!=1){
			throw new BusinessException("用户不存在！");
		}
		UserInfo userInfo = userInfoList.get(0);
		//userInfo.setAutoId(autoId);
		userInfo.setUpdateTime(new Date());
		userInfo.setCarImg(pic);
		//userInfo.setCarCode(carCode);
		userInfo.setLicense(license);
		if(StringUtils.isNotEmpty(mileage) && mileage.matches("\\d+")){
			userInfo.setMileage(Integer.parseInt(mileage));
		}
		return DatasetBuilder.fromDataSimple(userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1);
	}

	@Write
	@Override
	@Transactional
	public DatasetSimple<Boolean> setAddressInfo(Long userId, String address) {
		UserInfoExample ex = new UserInfoExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo>  userInfoList = userInfoMapper.selectByExample(ex);
		if(userInfoList.size()!=1){
			throw new BusinessException("用户不存在！");
		}
		UserInfo userInfo = userInfoList.get(0);
		//userInfo.setUserLocation(address);
		//userInfo.setUpdateTime(new Date());
		return DatasetBuilder.fromDataSimple(userInfoMapper.updateByPrimaryKeySelective(userInfo) == 1);
	}
	/**
	 * 惠车首页信息接口
	 */
	@SuppressWarnings("unchecked")
	@Read
	@Override
	public DatasetSimple<ImportUserInfo> getUserInfo(Long userId) {
		//判断是否提示字段
		String expireTip = "";
		//新保单滴滴券张数
		String surplusCouponValue = null;
		//1.0 根据userId查询userInfo表，根据userId查询user表
		UserInfoExample ex = new UserInfoExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<UserInfo>  userInfoList = userInfoMapper.selectByExample(ex);
		if(userInfoList.isEmpty()){
			throw new BusinessException("用户不存在！");
		}
		User user = userMapper.selectByPrimaryKey(userId);
		UserInfo userInfo = userInfoList.get(0);
		//2.0 若没有绑定保单
		if(StringUtils.isEmpty(userInfo.getLicense())){
			ImportUserInfo importUserInfo = new ImportUserInfo();
			importUserInfo.setId(0L);
			importUserInfo.setBaoyangTimes(0);
			importUserInfo.setXiecheTimes(-1);
			importUserInfo.setDianpingTimes(0);
			if(user.getUserType() == User.USER_TYPE_1){
				importUserInfo.setId(1L);//普通用户
			}else if(user.getUserType() == User.USER_TYPE_2){
				importUserInfo.setId(2L);//业务员
			}
			DatasetSimple<ImportUserInfo> result = DatasetBuilder.fromDataSimple(importUserInfo);
			result.putDataset("orderInfo", DatasetBuilder.fromDataList(new ArrayList<Orders>()));
			result.putDataset("washType", DatasetBuilder.fromDataSimple(1));
			expireTip="0";//未绑定车牌
			result.putDataset("expireTip", DatasetBuilder.fromDataSimple(expireTip));
			result.putDataset("juan", DatasetBuilder.fromDataSimple("0"));
			return result;
		}
		//3.0 若已绑定保单,就根据车牌查询import_user_info表
		ImportUserInfo importUserInfo = null;
		ImportUserInfoExample importex = new ImportUserInfoExample();
		importex.createCriteria().andChePaiEqualTo(userInfo.getLicense());
		importex.setLimitStart(0);
		importex.setLimitEnd(1);
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importex);
		if(importUserInfos.isEmpty()){
			throw new BusinessException("用户未绑定车型！");
		}else{
			importUserInfo = importUserInfos.get(0);
			//4.0 若过期，做过期处理
			if(importUserInfo.getEndTime().before(new Date())){
				//判断是否过期30天
				if(new Date().getTime()-importUserInfo.getEndTime().getTime()
						>30L*24L*60L*60L*1000L){
					expireTip ="1";//已过期30天未续保
				}else{
					expireTip = "2";//已过期但未过期30天未续保
				}
				//服务次数和滴滴券张数清零
				importUserInfo.setBaoyangTimes(0);
				importUserInfo.setPenqiTimes(0);
				importUserInfo.setXiecheTimes(0);
				importUserInfo.setLuntaiTimes(0);
				Map<String, Integer> juanMap = new HashMap<String,Integer>();
				juanMap.put("20", 0);
				juanMap.put("50", 0);
				importUserInfo.setSurplusCouponValue(JacksonHelper.toJSON(juanMap));
				importUserInfo.setUpdateTime(new Date());
				if(importUserInfoMapper.updateByPrimaryKeySelective(importUserInfo)!=1){
					throw new BusinessException("更新用户信息失败！");
				}
				//若存在新的保单，转正
				ImportUserInfoExample importexNew = new ImportUserInfoExample();
				importexNew.createCriteria().andChePaiEqualTo(userInfo.getLicense()+"_newyear");
				importexNew.setLimitStart(0);
				importexNew.setLimitEnd(1);
				List<ImportUserInfo> newimportUserInfos = importUserInfoMapper.selectByExample(importexNew);
				if(newimportUserInfos.size()>0){
					//新保单转正前，先更改旧的保单
					String oldTimeStr = new SimpleDateFormat("yyyyMMdd").format(importUserInfo.getCreateTime());
					importUserInfo.setChePai(importUserInfo.getChePai()+"_oldyear_"+oldTimeStr);
					importUserInfoMapper.updateByPrimaryKeySelective(importUserInfo);
					
					//新保单转正操作
					importUserInfo =  newimportUserInfos.get(0);//
					importUserInfo.setChePai(importUserInfo.getChePai().substring(0,importUserInfo.getChePai().indexOf("_newyear")));
					importUserInfo.setUpdateTime(new Date());
					importUserInfoMapper.updateByPrimaryKeySelective(importUserInfo);
					if("1".equals(expireTip)){
						expireTip = "3";//已过期30天并已续保
					}else{
						expireTip = "4";//已过期但未过期30天并已续保
					}
				}
			//5.0 若未过期，做未过期处理
			}else{
				//先看看新车牌是否导入
				ImportUserInfoExample importexNew = new ImportUserInfoExample();
				importexNew.createCriteria().andChePaiEqualTo(userInfo.getLicense()+"_newyear");
				importexNew.setLimitStart(0);
				importexNew.setLimitEnd(1);
				List<ImportUserInfo> newimportUserInfos = importUserInfoMapper.selectByExample(importexNew);
				if(newimportUserInfos.size()>0){
					//表示已经导入了新的车牌，此时需要累计使用次数
					ImportUserInfo newInfo = newimportUserInfos.get(0);
					surplusCouponValue = newInfo.getSurplusCouponValue();
					importUserInfo.setBaoyangTimes(importUserInfo.getBaoyangTimes()+newInfo.getBaoyangTimes());
					importUserInfo.setPenqiTimes(importUserInfo.getPenqiTimes()+newInfo.getPenqiTimes());
					importUserInfo.setLuntaiTimes(importUserInfo.getLuntaiTimes()+newInfo.getLuntaiTimes());
					//洗车次数特殊处理
					if(StringUtils.isNotEmpty(importUserInfo.getAddress())){
						CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
						carwashConfigEx.createCriteria().andCityNameEqualTo(importUserInfo.getAddress());
						List<CarwashConfig> carwashConfigs = carwashConfigMapper.selectByExample(carwashConfigEx);
						if(!carwashConfigs.isEmpty()&&CarwashConfig.WASH_TYPE_2.equals(carwashConfigs.get(0).getWashType())){
							importUserInfo.setXiecheTimes(importUserInfo.getXiecheTimes()>newInfo.getXiecheTimes()?importUserInfo.getXiecheTimes():newInfo.getXiecheTimes());	
						}else{
							importUserInfo.setXiecheTimes(importUserInfo.getXiecheTimes()+newInfo.getXiecheTimes());//正常洗车次数累加
						}
					}
					if((importUserInfo.getEndTime().getTime()
							-new Date().getTime())
							>30L*24L*60L*60L*1000L){
						expireTip = "5";//未过期并还有30天以上才到期并且已续保
					}else{
						expireTip = "6";//未过期并还有30天以内就到期并且已续保
					}
					
				}else{
					if((importUserInfo.getEndTime().getTime()
							-new Date().getTime())
							>30L*24L*60L*60L*1000L){
						expireTip = "7";//未过期并还有30天以上才到期并且没有续保
					}else{
						expireTip = "8";//未过期并还有30天以内就到期并且没有续保
					}
				}
			}
			
		}
		
		//设置用户绑定的sellerId
		if(importUserInfo.getSeller()!=null && StringUtils.isNotEmpty(importUserInfo.getSeller())){
			SellerExample sellerEx = new SellerExample();
			sellerEx.createCriteria().andSellerNameEqualTo(importUserInfo.getSeller());
			sellerEx.setLimitStart(0);
			sellerEx.setLimitEnd(1);
			List<Seller> slist = sellerMapper.selectByExample(sellerEx);
			if(!slist.isEmpty()){
				importUserInfo.setSeller(String.valueOf(slist.get(0).getId()));
				importUserInfo.setAgent(slist.get(0).getSellerName());
			}else{
				importUserInfo.setSeller(null);
			}
		}else{
			importUserInfo.setSeller(null);
		}
		//如果没有匹配到用户车型信息，去绑定车型里面找
		PinyinTool py = new PinyinTool();
		try {
			importUserInfo.setAutoLine(py.toPinYin(importUserInfo.getAutoBrand(), "", PinyinTool.Type.LOWERCASE));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			Log4jHelper.getLogger().error("转换车型图片失败！");
		}
		if(user.getUserType() == User.USER_TYPE_1){
			importUserInfo.setId(1L);
		}else if(user.getUserType() == User.USER_TYPE_2){
			importUserInfo.setId(2L);
		}else{
			importUserInfo.setId(0L);
		}
		
		//返回用户车型信息
		DatasetSimple<ImportUserInfo> result = DatasetBuilder.fromDataSimple(importUserInfo);
		
		if(StringUtils.isEmpty(surplusCouponValue)){
			String couponValue = importUserInfo.getSurplusCouponValue();
			if(StringUtils.isEmpty(couponValue)){
				result.putDataset("juan", DatasetBuilder.fromDataSimple("0"));
			}else{
				Map<String, Integer> hashMap = new HashMap<String,Integer>();
				hashMap =JacksonHelper.fromJSON(couponValue, Map.class);
				result.putDataset("juan", DatasetBuilder.fromDataSimple(
						hashMap.get("20")+hashMap.get("50")));
			}
			
		}else{
			Map<String, Integer> hashMap = new HashMap<String,Integer>();
			hashMap =JacksonHelper.fromJSON(surplusCouponValue, Map.class);
			String couponValue = importUserInfo.getSurplusCouponValue();
			if(StringUtils.isEmpty(couponValue)){
				result.putDataset("juan", DatasetBuilder.fromDataSimple(hashMap.get("20")
						+hashMap.get("50")));
			}else{
				Map<String, Integer> hashMap1 = new HashMap<String,Integer>();
				hashMap1 =JacksonHelper.fromJSON(couponValue, Map.class);
				Integer juan =hashMap.get("20")+hashMap1.get("20")+hashMap.get("50")+hashMap1.get("50");
				result.putDataset("juan20", DatasetBuilder.fromDataSimple(String.valueOf(juan)));
			}
		}
		
		//判断是否显示提示
		result.putDataset("expireTip", DatasetBuilder.fromDataSimple(expireTip));
		//返回前端用户绑定店铺信息，以及，服务包剩余次数
		//返回用户正在处理的订单列表
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(Orders.STATUS_0);
		statusList.add(Orders.STATUS_1);
		statusList.add(Orders.STATUS_2);
		OrdersExample orderEx = new OrdersExample();
		orderEx.createCriteria()
		.andStatusIn(statusList)
		.andUserIdEqualTo(userId);
		List<Map<String,Object>> orders = new ArrayList<Map<String,Object>>();
		ordersMapper.selectByExample(orderEx).stream()
		//.filter(o -> o.getCode().length()==8)
		.forEach(order ->{
			Map<String,Object> one = new HashMap<String,Object>();
			one.put("orderType", order.getOrderType());
			one.put("orderNo", order.getOrderNo());
			orders.add(one);
		});
		result.putDataset("orderInfo", DatasetBuilder.fromDataList(orders));
		//根据保单地址查询洗车配置渠道
		if(StringUtils.isEmpty(importUserInfo.getAddress())){
			result.putDataset("washType", DatasetBuilder.fromDataSimple(1));
		}else{
			//根据城市名称和保单金额查询洗车配置渠道
			CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
			carwashConfigEx.createCriteria().andCityNameEqualTo(importUserInfo.getAddress());
			List<CarwashConfig> carwashConfigs = carwashConfigMapper.selectByExample(carwashConfigEx);
			if(carwashConfigs.isEmpty()||CarwashConfig.WASH_TYPE_1.equals(carwashConfigs.get(0).getWashType())){
				result.putDataset("washType", DatasetBuilder.fromDataSimple(CarwashConfig.WASH_TYPE_1));
			}else{
				result.putDataset("washType", DatasetBuilder.fromDataSimple(carwashConfigs.get(0).getWashType()));
				Integer count = queryOrders(userId,carwashConfigs.get(0).getCountType());//1:代表每月,2代表每年
				//Integer xiecheTimes=0;
				Integer xiecheTimes = result.getData().getXiecheTimes()-count;
				
				if(xiecheTimes<=0){
					result.getData().setXiecheTimes(0);
				}else{
					result.getData().setXiecheTimes(xiecheTimes);
				};
			}	
		}
		return result;
	}
	/**
	 * 查询当月或当年的洗车订单量
	 * @param userId
	 * @param dateType
	 * @return
	 */
	private Integer queryOrders(Long userId,Integer dateType){
		OrdersExample ordersEx = new OrdersExample();
		com.emate.shop.business.model.OrdersExample.Criteria criteria = ordersEx.createCriteria();
		//设置用户
		criteria.andUserIdEqualTo(userId);
		//设置洗车订单
		criteria.andOrderTypeEqualTo(Orders.ORDER_TYPE_3);
		//设置订单状态
		criteria.andStatusNotEqualTo(Orders.STATUS_4);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		//设置日期条件
		criteria.andCreateTimeLessThanOrEqualTo(now.getTime());
		if(1==dateType){//该用户每月的下单量
			now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
			now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
			now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
			now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
			now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		}else{//该用户每年的的下单量
			now.set(Calendar.MONTH, 0);
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MILLISECOND, 0);
		}
		//设置日期条件
		criteria.andCreateTimeGreaterThanOrEqualTo(now.getTime());
		//查询结果
		int count = ordersMapper.countByExample(ordersEx);
		return count;
	}
	@Override
	public DatasetSimple<ImportUserInfo> getImportUserInfo(Long userId) {
		//根据userId查询userInfo表
		UserInfoExample ex = new UserInfoExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<UserInfo>  userInfoList = userInfoMapper.selectByExample(ex);
		if(userInfoList.isEmpty()){
			throw new BusinessException("用户不存在！");
		}
		//根据userId查询user表
		UserInfo userInfo = userInfoList.get(0);
		//如果已存在保单用户,就根据车牌查询import_user_info表
		ImportUserInfo importUserInfo = null;
		ImportUserInfoExample importex = new ImportUserInfoExample();
		importex.createCriteria().andChePaiEqualTo(userInfo.getLicense());
		importex.setLimitStart(0);
		importex.setLimitEnd(1);
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importex);
		if(importUserInfos.isEmpty()){
			throw new BusinessException("用户未绑定车型！");
		}
		importUserInfo = importUserInfos.get(0);
		return DatasetBuilder.fromDataSimple(importUserInfo);
	}
	
	public DatasetSimple<User> updateUserInfo(Long userId,String nickName,Integer gender){
		User record = userMapper.selectByPrimaryKey(userId);
		if(!Objects.nonNull(record)){
			throw new BusinessException("找不到用户");
		}
		record.setGender(gender);
		record.setNickName(nickName);
		record.setUpdateTime(new Date());
		if(userMapper.updateByPrimaryKeySelective(record) == 1){
			return DatasetBuilder.fromDataSimple(record);
		}
		throw new BusinessException("更新用户失败！");
	}

	/**
	 * 惠+车服用户登录
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<User> userLogin(String userName, String smsCode, String tokenCode, String ip) {
		//先校验验证码
		try{
			if(StringUtils.isEmpty(tokenCode)){
				throw new BusinessException("验证码不正确！");
			}
			if(!passwordEncoder.matches(smsCode+userName, tokenCode)){
				throw new BusinessException("验证码不正确！");
			}
		}catch(Exception e){
			throw new BusinessException("验证码不正确！");
		}
		DatasetList<User> users = queryUserByName(userName);
		if(users.getList().isEmpty()){
			// 第一次扫码用户，自动注册
			User newUser = new User();
			newUser.setName(userName);
			newUser.setUserType(User.USER_TYPE_1);
			newUser.setCreateTime(new Date());
			newUser.setLastLoginIp(ip);
			newUser.setLastLoginTime(new Date());
			if(userMapper.insertSelective(newUser)!=1){
				throw new BusinessException("注册用户失败！");
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(newUser.getId());
			userInfo.setCreateTime(new Date());
			userInfo.setUpdateTime(new Date());
			if (userInfoMapper.insertSelective(userInfo) != 1) {
				throw new BusinessException("添加用户信息失败！");
			}
			users = queryUserByName(userName);
		}
		User u = users.getList().get(0);
		u.setLastLoginIp(ip);
		u.setLastLoginTime(new Date());
		u.setUpdateTime(new Date());
		userMapper.updateByPrimaryKeySelective(u);
		return DatasetBuilder.fromDataSimple(u);
	}
	
	/**
	 * 查看用户管理
	 */
	@Read
	@Override
	public DatasetList<Map<String,String>> userList(User cuser, Integer pageNo,Integer pageSize) {
		UserExample userEx = new UserExample();
		if(StringUtils.isNotEmpty(cuser.getName())){
			userEx.createCriteria().andNameLike("%"+cuser.getName()+"%");
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, userMapper.countByExample(userEx));
		userEx.setLimitStart(page.getStartRow());
		userEx.setLimitEnd(page.getSize());
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		List<User> users = userMapper.selectByExample(userEx);
		if(users.isEmpty()){
			return DatasetBuilder.fromDataList(result, page.createPageInfo());
		}
		
		List<Long> userIds = users.stream().map(User::getId).distinct()
				.collect(Collectors.toList());
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdIn(userIds);
		
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		
		List<String> chepai = userInfos.stream().filter(userInfo-> Objects.nonNull(userInfo.getLicense()))
				.map(UserInfo::getLicense)
				.distinct()
				.collect(Collectors.toList());
		if(chepai==null){
			chepai = new ArrayList<String>();
		}
		chepai.add("0");
		ImportUserInfoExample importEx = new ImportUserInfoExample();
		importEx.createCriteria().andChePaiIn(chepai);
		List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
		
		users.stream().forEach(user -> {
			Map<String,String> one = new HashMap<String,String>();
			one.put("id", user.getId()+"");
			one.put("userName", user.getNickName());
			one.put("phoneNum", user.getName());
			Iterator<UserInfo> userIt = userInfos.iterator();
			while(userIt.hasNext()){
				UserInfo userInfo = userIt.next();
				if(userInfo.getUserId().equals(user.getId())){
					Iterator<ImportUserInfo> importUserIt = importList.iterator();
					while(importUserIt.hasNext()){
						ImportUserInfo importInfo = importUserIt.next();
						if(importInfo.getChePai().equals(userInfo.getLicense())){
							one.put("autoBrand", importInfo.getAutoBrand());
							one.put("autoType", importInfo.getAutoType());
							one.put("engineDisp", importInfo.getEngineDisp());
							one.put("productYear", importInfo.getProductYear());
							one.put("chejia", importInfo.getCheJia());
							one.put("chepai", importInfo.getChePai());
							//importUserIt.remove();
							break;
						}
					}
					userIt.remove();
					break;
				}
			}
			result.add(one);
		});
		
		return DatasetBuilder.fromDataList(result, page.createPageInfo());
	}
	
	/**
	 * 查询用户信息
	 */
	@Read
	public DatasetSimple<Map<String,String>> queryUserForEdit(Long userId) {
		Map<String,String> result = new HashMap<String,String>();
		try {
			User u = userMapper.selectByPrimaryKey(userId);
			result.putAll(BeanUtils.describe(u));
			UserInfoExample userInfoEx = new UserInfoExample();
			userInfoEx.createCriteria().andUserIdEqualTo(userId);
			userInfoEx.setLimitStart(0);
			userInfoEx.setLimitEnd(1);
			List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
			result.putAll(BeanUtils.describe(userInfos.get(0)));
			if(StringUtils.isNotEmpty(userInfos.get(0).getLicense())){
				ImportUserInfoExample importEx = new ImportUserInfoExample();
				importEx.createCriteria().andChePaiEqualTo(userInfos.get(0).getLicense());
				importEx.setLimitStart(0);
				importEx.setLimitEnd(1);
				List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
				result.putAll(BeanUtils.describe(importList.get(0)));
			}
			return DatasetBuilder.fromDataSimple(result);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * create or update user
	 */
	@Write
	@Transactional
	@Override
	public DatasetSimple<Map<String,String>> createOrUpdateUserInfo(User user,UserInfo userInfo,ImportUserInfo importUserInfo) {
		Map<String,String> result = new HashMap<String,String>();
		if(user.getId()!=null){//用户id不为空，修改用户
			user.setUpdateTime(new Date());
			if(userMapper.updateByPrimaryKeySelective(user)!=1){
				throw new BusinessException("更新用户信息出错！errCode:1");
			}
			userInfo.setId(null);
			userInfo.setUserId(user.getId());
			UserInfoExample userInfoEx = new UserInfoExample();
			userInfoEx.createCriteria().andUserIdEqualTo(userInfo.getUserId());
			userInfo.setUpdateTime(new Date());
			if(userInfoMapper.updateByExampleSelective(userInfo, userInfoEx)!=1){
				throw new BusinessException("更新用户信息出错！errCode:2");
			}
			if(StringUtils.isNotEmpty(userInfo.getLicense())){
				importUserInfo.setId(null);
				ImportUserInfoExample importUserEx = new ImportUserInfoExample();
				importUserEx.createCriteria().andChePaiEqualTo(userInfo.getLicense());
				importUserEx.setLimitStart(0);
				importUserEx.setLimitEnd(1);
				List<ImportUserInfo> importUsers = importUserInfoMapper.selectByExample(importUserEx);
				if(importUsers.isEmpty()){
					throw new BusinessException("更新用户信息出错,车牌号不能修改！");
				}
				importUserInfo.setUpdateTime(new Date());
				if(importUserInfoMapper.updateByExampleSelective(importUserInfo, importUserEx)!=1){
					throw new BusinessException("更新用户信息出错！errCode:3");
				}
			}
			result.put("result", "success");
			return DatasetBuilder.fromDataSimple(result);
		}else{//新增用户
			UserExample userEx = new UserExample();
			userEx.createCriteria().andNameEqualTo(user.getName());
			if(userMapper.countByExample(userEx)>0){
				throw new BusinessException("新增用户信息出错！用户已存在！");
			}
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			if(userMapper.insertSelective(user)!=1){
				throw new BusinessException("新增用户信息出错！errCode:1");
			}
			userInfo.setUserId(user.getId());
			userInfo.setCreateTime(new Date());
			userInfo.setUpdateTime(new Date());
			if(userInfoMapper.insertSelective(userInfo)!=1){
				throw new BusinessException("新增用户信息出错！errCode:2");
			}
			if(StringUtils.isNotEmpty(userInfo.getLicense())){
				importUserInfo.setRealName(user.getNickName());
				importUserInfo.setPhone(user.getName());
				importUserInfo.setChePai(userInfo.getLicense());
				ImportUserInfoExample importUserEx = new ImportUserInfoExample();
				importUserEx.createCriteria().andChePaiEqualTo(userInfo.getLicense());
				importUserInfo.setCreateTime(new Date());
				importUserInfo.setUpdateTime(new Date());
				if(importUserInfoMapper.insertSelective(importUserInfo)!=1){
					throw new BusinessException("新增用户信息出错！errCode:3");
				}
			}
			result.put("result", "success");
			return DatasetBuilder.fromDataSimple(result);
		}
	}

	/**
	 * 用户绑定车牌
	 */
	@Write
	@Override
	@Transactional
	public DatasetSimple<Map<String, String>> bind(Long userId, String chepai, String password,String source){
		RandomCode code = null;
		//查找随机码是否存在
		RandomCodeExample randomCodeEx = new RandomCodeExample();
		randomCodeEx.createCriteria().andCodeEqualTo(password)
		.andStatusEqualTo(RandomCode.STATUS_0);
		List<RandomCode> codeList = randomCodeMapper.selectByExample(randomCodeEx);
		if(codeList.isEmpty()){
			throw new BusinessException("随机密码不正确！");
		}
		//根据车牌查找导入保单表,看是否导入保单
		code = codeList.get(0);
		chepai = chepai.toUpperCase();
		ImportUserInfoExample importUserInfoExample = new ImportUserInfoExample();
		importUserInfoExample.createCriteria().andChePaiEqualTo(chepai);
		importUserInfoExample.setLimitStart(0);
		importUserInfoExample.setLimitEnd(1);
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importUserInfoExample);
		if(importUserInfos.isEmpty()){
			throw new BusinessException("没有导入车牌信息！");
		}
		//根据车牌查找user_info表,看车牌是否已绑定
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andLicenseEqualTo(chepai);
		userInfoEx.setLimitStart(0);
		userInfoEx.setLimitEnd(1);
		if(userInfoMapper.countByExample(userInfoEx)>0){
			throw new BusinessException("该车牌号已经被绑定！");
		}
		//根据userId查找user_info表,查看用户是否绑定
		userInfoEx.clear();
		userInfoEx.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> useInfos = userInfoMapper.selectByExample(userInfoEx);
		//若没绑定,在user_info表里,添加一条记录
		if(useInfos.isEmpty()){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);
			userInfo.setCreateTime(new Date());
			userInfo.setUpdateTime(new Date());
			if (userInfoMapper.insertSelective(userInfo) != 1) {
				throw new BusinessException("添加用户信息失败！");
			}
			useInfos = userInfoMapper.selectByExample(userInfoEx);
		}
		//更新user_info表
		UserInfo userInfo = useInfos.get(0);
		userInfo.setLicense(chepai);
		userInfo.setUpdateTime(new Date());
		if(userInfoMapper.updateByPrimaryKeySelective(userInfo)!=1){
			throw new BusinessException("用户绑定信息失败！");
		}
		//修改user表
		User user = userMapper.selectByPrimaryKey(userId);
		if(user!=null){
			User updateUser = new User();
			updateUser.setId(user.getId());
			updateUser.setNickName(importUserInfos.get(0).getRealName());
			userMapper.updateByPrimaryKeySelective(updateUser);
		}
		//关于业务员推荐信息保留
		if(StringUtils.isNotEmpty(source)&&source.matches("^1[34578]\\d{9}$")){
			try{
				SalesmanStatisticsExample salesEx = new SalesmanStatisticsExample();
				com.emate.shop.business.model.SalesmanStatisticsExample.Criteria saleCri = salesEx.createCriteria();
				saleCri.andChepaiEqualTo(chepai);
				salesEx.setLimitStart(0);
				salesEx.setLimitEnd(1);
				List<SalesmanStatistics> salesList = salesmanStatisticsMapper.selectByExample(salesEx);
				if(salesList.isEmpty()){
					SalesmanStatistics statistics = new SalesmanStatistics();
					statistics.setUserId(userId);
					statistics.setPhone(source);
					statistics.setChepai(chepai);
					statistics.setUserPhone(user.getName());
					statistics.setCreateTime(new Date());
					statistics.setUpdateTime(new Date());
					salesmanStatisticsMapper.insertSelective(statistics);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		//随机码只能使用一次
		code.setStatus(RandomCode.STATUS_1);
		randomCodeMapper.updateByPrimaryKeySelective(code);
		Map<String, String> result = new HashMap<String, String>();
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Read
	public DatasetList<Map<String,String>> myCars(Long userId) {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		UserInfoExample ex = new UserInfoExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<UserInfo>  userInfoList = userInfoMapper.selectByExample(ex);
		if(userInfoList.isEmpty()){
			throw new BusinessException("用户不存在！");
		}
		UserInfo userInfo = userInfoList.get(0);
		if(StringUtils.isEmpty(userInfo.getLicense())){ //如果是没有导入车牌号的保单用户
			return DatasetBuilder.fromDataList(result);
		}
		ImportUserInfo importUserInfo = null;
		ImportUserInfoExample importex = new ImportUserInfoExample();
		importex.createCriteria().andChePaiEqualTo(userInfo.getLicense());
		importex.setLimitStart(0);
		importex.setLimitEnd(1);
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importex);
		if(importUserInfos.isEmpty()){
			throw new BusinessException("导入的车型不存在！");
		}
		Map<String,String> one = new HashMap<String,String>();
		importUserInfo = importUserInfos.get(0);
		//如果没有匹配到用户车型信息，去绑定车型里面找
		PinyinTool py = new PinyinTool();
		try {
			one.put("logo", py.toPinYin(importUserInfo.getAutoBrand(), "", PinyinTool.Type.LOWERCASE));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			Log4jHelper.getLogger().error("转换车型图片失败！");
		}
		
		one.put("chePai", importUserInfo.getChePai());
		one.put("autoBrand", importUserInfo.getAutoBrand());
		one.put("autoType", importUserInfo.getAutoType());
		one.put("engineDisp", importUserInfo.getEngineDisp());
		one.put("productYear", importUserInfo.getProductYear());
		result.add(one);
		return DatasetBuilder.fromDataList(result);
	}
	
	@Override
	@Read
	public DatasetList<Map<String,Object>> queryImportErrList(Integer pageNo,Integer pageSize,String flag,ImportUserInfo importUserInfo){
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		ImportUserInfoExample importUserEx = new ImportUserInfoExample();
		Criteria cri = importUserEx.createCriteria();
		if("1".equals(flag)){
			cri.andChePaiNotLike("%_no%");
		}else if("2".equals(flag)){
			cri.andChePaiLike("%_no%");
		}
		if(StringUtils.isNotEmpty(importUserInfo.getChePai())){
			cri.andChePaiLike("%"+importUserInfo.getChePai()+"%");
		}
		if(StringUtils.isNotEmpty(importUserInfo.getRealName())){
			cri.andRealNameLike("%"+importUserInfo.getRealName()+"%");
		}
		if(StringUtils.isNotEmpty(importUserInfo.getPhone())){
			List<String> licenses = definedMapper.queryLicense("%"+importUserInfo.getPhone()+"%");
			licenses.add("0");
			cri.andChePaiIn(licenses);
		}
		if(StringUtils.isNotEmpty(importUserInfo.getBaoDan())){
			cri.andBaoDanLike("%"+importUserInfo.getBaoDan()+"%");
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, importUserInfoMapper.countByExample(importUserEx));
		importUserEx.setLimitStart(page.getStartRow());
		importUserEx.setLimitEnd(page.getSize());
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importUserEx);
		if(importUserInfos.isEmpty()){
			return DatasetBuilder.fromDataList(resultList, page.createPageInfo());
		}
		List<String> chePais = importUserInfos.stream().map(ImportUserInfo::getChePai).distinct().collect(Collectors.toList());
		List<Map<String, String>> phones = definedMapper.queryPhone(chePais);
		Map<String, String> phoneMap = new HashMap<String,String>();
		for(Map<String, String> map :phones){
			phoneMap.put(map.get("license"), map.get("userPhone"));
		}
		for(ImportUserInfo userinfo : importUserInfos){
			Map<String, Object> tranToMap = tranToMap(userinfo);
			tranToMap.put("phone", null);
			if(StringUtils.isNotEmpty(phoneMap.get(userinfo.getChePai()))){
				tranToMap.put("phone", phoneMap.get(userinfo.getChePai()));
			}
			resultList.add(tranToMap);
		}
		return DatasetBuilder.fromDataList(resultList, page.createPageInfo());
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> tranToMap(ImportUserInfo userinfo){
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("address", userinfo.getAddress());
		result.put("agent", userinfo.getAgent());
		result.put("autoBrand", userinfo.getAutoBrand());
		result.put("autoLine", userinfo.getAutoLine());
		result.put("autoType", userinfo.getAutoType());
		result.put("baoDan", userinfo.getBaoDan());
		result.put("baoyangTimes", userinfo.getBaoyangTimes());
		result.put("carFactory", userinfo.getCarFactory());
		result.put("cheJia", userinfo.getCheJia());
		result.put("chePai", userinfo.getChePai());
		result.put("createTime", userinfo.getCreateTime());
		result.put("dianpingTimes", userinfo.getDianpingTimes());
		result.put("discount", userinfo.getDiscount());
		result.put("endTime", userinfo.getEndTime());
		result.put("engineCode", userinfo.getEngineCode());
		result.put("engineDisp", userinfo.getEngineDisp());
		result.put("id", userinfo.getId());
		result.put("luntaiTimes", userinfo.getLuntaiTimes());
		result.put("mileage", userinfo.getMileage());
		result.put("orderPrice", userinfo.getOrderPrice());
		result.put("paymentTime", userinfo.getPaymentTime());
		result.put("penqiTimes", userinfo.getPenqiTimes());
		result.put("phone", userinfo.getPhone());
		result.put("price", userinfo.getPrice());
		result.put("productYear", userinfo.getProductYear());
		result.put("realName", userinfo.getRealName());
		result.put("seller", userinfo.getSeller());
		result.put("serviceValue", userinfo.getServiceValue());
		result.put("signTime", userinfo.getSignTime());
		result.put("source", userinfo.getSource());
		result.put("startTime", userinfo.getStartTime());
		result.put("updateTime", userinfo.getUpdateTime());
		result.put("useType", userinfo.getUseType());
		result.put("xiecheTimes", userinfo.getXiecheTimes());
		//result.put("surplusCouponValue", );
		String surplusCouponValue = userinfo.getSurplusCouponValue();
        if(StringUtils.isEmpty(surplusCouponValue)){
        	result.put("juan50", "0");
            result.put("juan20", "0");
        }else{
        	Map<String, Object> map = new HashMap<String, Object>();
            map = JacksonHelper.fromJSON(surplusCouponValue, Map.class);
            result.put("juan50", map.get("50"));
            result.put("juan20", map.get("20"));
        }
		return result;
	}

	@Override
	@Read
	public DatasetSimple<Map<String, String>> hasUnCompletOrder(Long userId, String orderType) {
		OrdersExample orderEx = new OrdersExample();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(Orders.STATUS_0);
		statusList.add(Orders.STATUS_1);
		statusList.add(Orders.STATUS_2);
		orderEx.createCriteria().andUserIdEqualTo(userId)
		.andOrderTypeEqualTo((byte)Integer.parseInt(orderType))
		.andStatusIn(statusList);
		Map<String,String> result = new HashMap<String,String>();
		if(ordersMapper.selectByExample(orderEx).isEmpty()){
			return DatasetBuilder.fromDataSimple(result);
		}
		throw new BusinessException("有正在处理的订单！");
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> bindBusiness(Long userId, String businessCode,String realName) {
		//组织查询业务员的条件
		BusinessInfoExample busInfoEx = new BusinessInfoExample();
		busInfoEx.createCriteria()
		.andBusinessCodeEqualTo(businessCode)
		.andBusinessNameEqualTo(realName)
		.andPhoneIsNull();
		busInfoEx.setLimitStart(0);
		busInfoEx.setLimitEnd(1);
		List<BusinessInfo> infos = businessInfoMapper.selectByExample(busInfoEx);
		//判断若该业务员为空
		if(infos.isEmpty()){
			throw new BusinessException("没有找到该工号！");
		}
		//绑定业务员时,需要更新user表的user_type属性和向业务员(business_info表)中修改手机号,userId和更新时间
		User user = userMapper.selectByPrimaryKey(userId);
		user.setUserType(User.USER_TYPE_2);
		user.setUpdateTime(new Date());
		//若更新用户失败,抛绑定失败异常
		if(userMapper.updateByPrimaryKeySelective(user)!=1){
			throw new BusinessException("绑定失败,errCode:0！");
		}
		infos.get(0).setPhone(user.getName());
		infos.get(0).setUserId(userId);
		infos.get(0).setUpdateTime(new Date());
		//更新业务员表失败,抛绑定失败异常
		if(businessInfoMapper.updateByPrimaryKeySelective(infos.get(0))!=1){
			throw new BusinessException("绑定失败,errCode:1！");
		}
		//绑定成功返回~
		Map<String,String> result = new HashMap<String,String>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Read
	public DatasetSimple<Map<String, String>> getBusinessInfo(Long userId) {
		BusinessInfoExample businessEx = new BusinessInfoExample();
		businessEx.createCriteria().andUserIdEqualTo(userId);
		List<BusinessInfo> infos = businessInfoMapper.selectByExample(businessEx);
		if(infos.isEmpty()){
			throw new BusinessException("查询失败,errCode:-1！");
		}
		BusinessInfo  info = infos.get(0);
		try {
			return DatasetBuilder.fromDataSimple(BeanUtils.describe(info));
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new BusinessException("查询失败,errCode:0！");
		}
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> deleteOverdueImportInfo() {
//		ImportUserInfoExample importEx = new ImportUserInfoExample();
//		importEx.createCriteria().andEndTimeLessThan(new Date());
//		importEx.setLimitStart(0);
//		importEx.setLimitEnd(50);
//		List<ImportUserInfo> imports = importUserInfoMapper.selectByExample(importEx);
		final AtomicInteger success = new AtomicInteger(0);
		final AtomicInteger fail = new AtomicInteger(0);
//		imports.stream().forEach(info -> {
//			if(info.getBaoyangTimes()>=0 
//					|| info.getPenqiTimes()>=0
//					|| info.getXiecheTimes()>=0){
//				//所有次数清零
//				info.setBaoyangTimes(0);
//				info.setPenqiTimes(0);
//				info.setXiecheTimes(0);
//				info.setUpdateTime(new Date());
//				if(importUserInfoMapper.updateByPrimaryKeySelective(info)==1){
//					success.incrementAndGet();
//				}else{
//					fail.incrementAndGet();
//				}
//			}
//		});
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", success.intValue()+"");
		result.put("fail", fail.intValue()+"");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Read
	public DatasetList<Map<String,String>> queryExpiredUserInfo() {
		
		//组织参数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.MONTH, 1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		
		Date date = now.getTime();
		
		//查询手机号,车牌,到期日期
		List<Map<String, Object>> userInfoList = definedMapper.queryExpiredUserInfo(date);
		
		//组织返回结果
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		Iterator<Map<String, Object>> iterator = userInfoList.iterator();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		while(iterator.hasNext()){
			Map<String, Object> userInfo = iterator.next();
			if(Objects.isNull(userInfo.get("phone"))){
				iterator.remove();
				continue;
			}
			Map<String, String> result= new HashMap<String,String>();
			result.put("phone", String.valueOf(userInfo.get("phone")));
			result.put("license", String.valueOf(userInfo.get("license")));
			result.put("endTime", format.format(date));
			results.add(result);
		}
		return DatasetBuilder.fromDataList(results);
	}
	/**
	 * 更新车服用户和车辆信息
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> updateCarUserInfo(ImportUserInfo importUserInfo, String phone){
		//参数校验
		if(StringUtils.isNotEmpty(importUserInfo.getRealName())&&
				importUserInfo.getRealName().length()>5){
			throw new BusinessException("保单用户名字不能超过5个汉字");
		}
		if(StringUtils.isNotEmpty(phone)){
			if(phone.length()!=11||!phone.startsWith("1")){
				throw new BusinessException("手机号格式不对");
			}
		}
		//根据id查询车辆信息
		ImportUserInfoExample infoEx = new ImportUserInfoExample();
		Criteria c = infoEx.createCriteria();
		c.andIdEqualTo(importUserInfo.getId());
		List<ImportUserInfo> infos = importUserInfoMapper.selectByExample(infoEx);
		if(infos.isEmpty()){
			throw new BusinessException("未查到该车辆信息~~");
		}
		ImportUserInfo infoOld = infos.get(0);
		infoOld.setRealName(importUserInfo.getRealName());
		
		//查看原来是否绑定手机号
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andLicenseEqualTo(infoOld.getChePai());
		List<UserInfo> userInfoOldList = userInfoMapper.selectByExample(userInfoEx);
		
		
		//修改车牌
		if(!importUserInfo.getChePai().equals(infoOld.getChePai())){
			//验证车牌是否已存在
			infoEx.clear();
			infoEx.createCriteria().andChePaiEqualTo(importUserInfo.getChePai());
			List<ImportUserInfo> infoss = importUserInfoMapper.selectByExample(infoEx);
			if(!infoss.isEmpty()){
				throw new BusinessException("该车辆信息已存在,请换车牌");
			}
			//若已绑定,修改绑定的车牌
			if(!userInfoOldList.isEmpty()){
				UserInfo userInfo = userInfoOldList.get(0);
				userInfo.setLicense(importUserInfo.getChePai());
				userInfoMapper.updateByPrimaryKeySelective(userInfo);
			}
			infoOld.setChePai(importUserInfo.getChePai());
		}
		
		//验证车型是否匹配
		infoOld.setAutoLine(importUserInfo.getAutoLine());
		infoOld.setAutoType(importUserInfo.getAutoType());
		infoOld.setProductYear(importUserInfo.getProductYear());
		infoOld.setEngineDisp(importUserInfo.getEngineDisp());
		infoOld.setAutoBrand(importUserInfo.getAutoBrand());
		AutoInfo  autoInfo = canMatchAuto(infoOld);
		if(autoInfo==null){
			//有可能是车的品牌信息有错误，需要去autopose表校验车辆品牌信息
			AutoposeExample autoposeExample = new AutoposeExample();
			autoposeExample.createCriteria().andFactorynameEqualTo(infoOld.getAutoBrand());
			autoposeExample.setLimitStart(0);
			autoposeExample.setLimitEnd(1);
			List<Autopose> autopose = autoposeMapper.selectByExample(autoposeExample);
			if(autopose.isEmpty()){//autopose表也查询不到这时候肯定是匹配不到
				throw new BusinessException("未查到该匹配的车型");
			}else{
				infoOld.setAutoBrand(autopose.get(0).getBrandname());
				autoInfo = canMatchAuto(infoOld);//修正完品牌之后需要再次匹配车辆信息
				if(autoInfo==null){
					throw new BusinessException("未查到该匹配的车型");
				}
			}
		}
		//更新保单信息
		importUserInfoMapper.updateByPrimaryKeySelective(infoOld);
		
		//修改绑定手机号
		if(!userInfoOldList.isEmpty()&&StringUtils.isNotEmpty(phone)){
			UserInfo userInfo = userInfoOldList.get(0);
			User user = userMapper.selectByPrimaryKey(userInfo.getUserId());
			if(!phone.equals(user.getName())){
				UserExample userEx = new UserExample();
				userEx.createCriteria().andNameEqualTo(phone);
				List<User> users = userMapper.selectByExample(userEx);
				if(users.isEmpty()){
					user.setName(phone);
				}else{
					User userNew = users.get(0);
					userInfoEx.clear();
					userInfoEx.createCriteria().andUserIdEqualTo(userNew.getId());
					List<UserInfo> userInfoNew = userInfoMapper.selectByExample(userInfoEx);
					if(StringUtils.isNotEmpty(userInfoNew.get(0).getLicense())){
						throw new BusinessException("该新手机号已绑定该车牌:"+userInfoNew.get(0).getLicense());
					}else{
						userInfoMapper.deleteByPrimaryKey(userInfoNew.get(0).getId());
						userMapper.deleteByPrimaryKey(userNew.getId());
						user.setName(phone);
					}
				}
				userMapper.updateByPrimaryKeySelective(user);
			}
		}else if(userInfoOldList.isEmpty()&&StringUtils.isNotEmpty(phone)){
			throw new BusinessException("用户还没绑定过,后台没权限新增用户~");
		}else if(!userInfoOldList.isEmpty()&&StringUtils.isEmpty(phone)){
			throw new BusinessException("用户已绑定过,后台没权限解绑该用户~");
		}
		return DatasetBuilder.fromDataSimple(true);
	}
	
	/**
	 * 查看用户是否能够匹配车型
	 * @param importUserInfo
	 * @return
	 */
	private AutoInfo canMatchAuto(ImportUserInfo importUserInfo){
		AutoInfoExample autoInfoEx = new AutoInfoExample();
		importUserInfo.setProductYear(importUserInfo.getProductYear().substring(0,4));
		if(importUserInfo.getEngineDisp().indexOf("L")!=-1
				|| importUserInfo.getEngineDisp().indexOf("l")!=-1){
			importUserInfo.setEngineDisp(importUserInfo.getEngineDisp().substring(0,importUserInfo.getEngineDisp().length()-1));
		}
		autoInfoEx.or()
		.andBrandEqualTo(importUserInfo.getAutoBrand())
		.andAutolineNameEqualTo(importUserInfo.getAutoType().toUpperCase())
		.andEngineDispLike(importUserInfo.getEngineDisp()+"%")
		.andProductTimeEqualTo(importUserInfo.getProductYear());
		autoInfoEx.setOrderByClause(AutoInfoExample.ID_ASC);
		autoInfoEx.setLimitStart(0);
		autoInfoEx.setLimitEnd(1);
		List<AutoInfo>  list = autoInfoMapper.selectByExample(autoInfoEx);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> updateCarUserInfoTwo(String orderPrice, String signTime, String startTime,
			String endTime, String paymentTime, String infoId) {
		if(StringUtils.isEmpty(orderPrice)){
			throw new BusinessException("保费收入不能为空");
		}
		if(StringUtils.isEmpty(signTime)){
			throw new BusinessException("签单日期不能为空");
		}
		if(StringUtils.isEmpty(startTime)){
			throw new BusinessException("起始日期不能为空");
		}
		if(StringUtils.isEmpty(endTime)){
			throw new BusinessException("终止日期不能为空");
		}
		if(StringUtils.isEmpty(paymentTime)){
			throw new BusinessException("收付日期不能为空");
		}
		//([1-9]\d*(\.\d*[1-9])?)
		if(!orderPrice.matches("[1-9]\\d*(\\.\\d*[1-9])?")){
			throw new BusinessException("保费格式不对~");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//根据id查询车辆信息
		ImportUserInfoExample infoEx = new ImportUserInfoExample();
		Criteria c = infoEx.createCriteria();
		c.andIdEqualTo(Long.valueOf(infoId));
		List<ImportUserInfo> infos = importUserInfoMapper.selectByExample(infoEx);
		if(infos.isEmpty()){
			throw new BusinessException("未查到该车辆信息~~");
		}
		ImportUserInfo info = infos.get(0);
		//赋值签单日期
		try {
			Date signDate = format.parse(signTime);
			info.setSignTime(signDate);;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("保单签单日期转化异常");
		}
		//赋值起始日期
		try {
			Date startDate = format.parse(startTime);
			info.setStartTime(startDate);;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("保单起始日期转化异常");
		}
		//赋值终止日期
		try {
			Date endDate = format.parse(endTime);
			info.setEndTime(endDate);;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("保单终止日期转化异常");
		}
		//赋值收付日期
		try {
			Date paymentDate = format.parse(paymentTime);
			info.setPaymentTime(paymentDate);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("保单收付日期转化异常");
		}
		if(info.getEndTime().before(info.getStartTime())){
			throw new BusinessException("保单终止日期不能小于起始日期");
		}
		//赋值保费
		BigDecimal orderPrices = new BigDecimal(orderPrice);
		info.setOrderPrice(orderPrices);
		
		importUserInfoMapper.updateByPrimaryKeySelective(info);
		return DatasetBuilder.fromDataSimple(true);
	}
	public static void main(String[] args) {
		String date1 = "2018-02-27";
		String date2 = "2019-02-29";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date parse = format.parse(date1);
			Date parse2 = format.parse(date2);
			System.out.println(parse.before(parse2));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<User> updateUserPhone(String newphone, String license,
			String baoDan, String oldPhone) {
		if(StringUtils.isEmpty(newphone)){
			throw new BusinessException("新手机号不能为空");
		}
		if(StringUtils.isEmpty(license)){
			throw new BusinessException("被绑定车牌不能为空");
		}
		if(StringUtils.isEmpty(baoDan)){
			throw new BusinessException("保单号不能为空");
		}
		if(StringUtils.isEmpty(oldPhone)){
			throw new BusinessException("手机号不能为空");
		}
		//验证旧手机号是否登录过
		UserExample userEx = new UserExample();
		userEx.createCriteria().andNameEqualTo(oldPhone);
		List<User> users = userMapper.selectByExample(userEx);
		if(users.isEmpty()){
			throw new BusinessException("该手机号未登录惠加车服");
		}
		//验证保单是否存在
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		Criteria c = importUserInfoEx.createCriteria();
		c.andChePaiEqualTo(license);
		c.andBaoDanEqualTo(baoDan);
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importUserInfoEx);
		if(importUserInfos.isEmpty()){
			throw new BusinessException("该保单不存在");
		}
		User user = users.get(0);
		//验证旧手机号是否绑定过
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(user.getId());
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		if(userInfos.isEmpty()){
			throw new BusinessException("该手机号未绑定过任何保单");
		}
		UserInfo userInfo = userInfos.get(0);
		if(StringUtils.isEmpty(userInfo.getLicense())){
			throw new BusinessException("该手机号未绑定过任何保单");
		}
		//校验旧手机号跟保单是否彼此绑定
		if(!license.equals(userInfo.getLicense())){
			throw new BusinessException("该手机号与绑定保单号不匹配");
		}
		user.setName(newphone);
		if(userMapper.updateByPrimaryKeySelective(user)!=1){
			throw new BusinessException("绑定失败!");
		};
		
		return DatasetBuilder.fromDataSimple(user);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<String> judgeMessage(String newphone, String smsCode, String tokenCode) {
		try{
			if(StringUtils.isEmpty(tokenCode)){
				throw new BusinessException("验证码不正确！");
			}
			if(!passwordEncoder.matches(smsCode+newphone, tokenCode)){
				throw new BusinessException("验证码不正确！");
			}
		}catch(Exception e){
			throw new BusinessException("验证码不正确！");
		}
		//根据新手机搜索
		UserExample userEx = new UserExample();
		userEx.createCriteria()
			.andNameEqualTo(newphone);
		List<User> newUserList = userMapper.selectByExample(userEx);
		//若存在,删除新手机号信息和关联表
		if(!newUserList.isEmpty()){
			User newUser = newUserList.get(0);
			UserInfoExample userInfoEx = new UserInfoExample();
			userInfoEx.createCriteria().andUserIdEqualTo(newUser.getId());
			List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoEx);
			if(!userInfoList.isEmpty()){
				UserInfo userInfo = userInfoList.get(0);
				if(StringUtils.isNotEmpty(userInfo.getLicense())){
					throw new BusinessException("该新手机号已绑定车牌");
				}
				if(userInfoMapper.deleteByExample(userInfoEx)!=1){
					throw new BusinessException("删除关联表失败,请重试~");
				}
			}
			userMapper.deleteByPrimaryKey(newUser.getId());
		}
		return DatasetBuilder.fromDataSimple("SUCCSS");
	}
}
