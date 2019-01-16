package com.emate.shop.business.service;

import java.lang.reflect.InvocationTargetException;


import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
/*import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;*/
import java.util.Date;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
//import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;*/
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.CountermanService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.business.model.CountermanCaiDotExample;
/*import com.emate.shop.business.model.CountermanCareer;
import com.emate.shop.business.model.CountermanCareerExample;*/
import com.emate.shop.business.model.CountermanExample;
import com.emate.shop.business.model.CountermanExample.Criteria;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.business.model.CountermanScoreRecord;
import com.emate.shop.business.model.CountermanScoreRecordExample;
import com.emate.shop.business.model.SystemUser;
/*import com.emate.shop.business.model.CountermanInfo;
import com.emate.shop.business.model.CountermanInfoExample;
//import com.emate.shop.business.model.CountermanLifeCareer;
import com.emate.shop.business.model.CountermanScore;
import com.emate.shop.business.model.CountermanScoreExample;*/
import com.emate.shop.business.model.User;
/*import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;*/
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CountermanCaiDotMapper;
//import com.emate.shop.mapper.CountermanCareerMapper;
//import com.emate.shop.mapper.CountermanInfoMapper;
//import com.emate.shop.mapper.CountermanLifeCareerMapper;
import com.emate.shop.mapper.CountermanMapper;
import com.emate.shop.mapper.CountermanScoreRecordMapper;
//import com.emate.shop.mapper.CountermanScoreMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 业务员信息操作
 * @author dong
 *
 */
@Service
public class CountermanServiceImpl implements CountermanService{

	@Resource
	private CountermanMapper countermanMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;
	
/*	@Resource
	private CountermanInfoMapper countermanInfoMapper;
	
	@Resource
	private CountermanCareerMapper countermanCareerMapper;
	
	@Resource
	private CountermanScoreMapper countermanScoreMapper;*/
	
	@Resource
	private CountermanScoreRecordMapper countermanScoreRecordMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private CountermanCaiDotMapper countermanCaiDotMapper;
	
/*	@Resource
	private CountermanLifeCareerMapper countermanLifeCareerMapper;*/

	@Read
	@Override
	public DatasetList<Counterman> adminCountermanList(Integer pageNo,Integer pageSize,Counterman counterman,Long userId) {
		//查询后台登录姓名
		String pinYin = systemUserMapper.selectByPrimaryKey(userId).getUserName();
		//根据姓名查询财险网点id
		CountermanCaiDotExample countermanCaiDotEx = new CountermanCaiDotExample();
		countermanCaiDotEx.createCriteria().andPinYinEqualTo(pinYin);
		List<CountermanCaiDot> countermanCaiDot = countermanCaiDotMapper.selectByExample(countermanCaiDotEx);
		CountermanExample ex = new CountermanExample();
		Criteria criteria = ex.createCriteria();
		//财险网点
		if(!countermanCaiDot.isEmpty()&&Objects.nonNull(countermanCaiDot.get(0))){
			criteria.andCaiDotIdEqualTo(countermanCaiDot.get(0).getId());
		}
		//业务员姓名
		if(counterman.getName()!=null && !"".equals(counterman.getName())){
			criteria.andNameLike("%"+counterman.getName()+"%");
		}
		//业务员手机号
		if(counterman.getPhone()!=null && !"".equals(counterman.getPhone())){
			criteria.andPhoneLike("%"+counterman.getPhone()+"%");
		}
		//业务员在职状态
		//criteria.andStatusEqualTo(Counterman.STATUS_0);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		ex.setOrderByClause(CountermanExample.CREATE_TIME_DESC);
		List<Counterman> countermans = countermanMapper.selectByExample(ex);
		/*for(Counterman countermanss :countermans){
			if("黄埔_萝岗".equals(countermanss.getCaiDotName())){
				countermanss.setCaiDotName("萝岗");
			}
			if("黄埔_南岗".equals(countermanss.getCaiDotName())){
				countermanss.setCaiDotName("南岗");
			}
		}*/
		return DatasetBuilder.fromDataList(countermans, page.createPageInfo());
	}

	@Read
	@Override
	public DatasetSimple<Counterman> queryCountermanById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("业务员id不能为空");
		}
		Counterman counterman = countermanMapper.selectByPrimaryKey(Long.parseLong(id));
		if(counterman == null){
			throw new BusinessException("该业务员不存在！");
		}
		return DatasetBuilder.fromDataSimple(counterman);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addCounterman(Counterman counterman,Long userId) {
		if(StringUtils.isEmpty(counterman.getPhone())){
			throw new BusinessException("手机号不能为空");
		}
		if(StringUtils.isEmpty(counterman.getCountermanCode())){
			throw new BusinessException("工号不能为空");
		}
		if(0==counterman.getCaiDotId()){
			throw new BusinessException("财险网点为空");
		}
		CountermanCaiDot countermanCaiDot = countermanCaiDotMapper.selectByPrimaryKey(counterman.getCaiDotId());
		if(Objects.isNull(countermanCaiDot)){
			throw new BusinessException("查不到所属财险网点");
		}
		
/*		if(0==counterman.getLifeCareerId()){
			throw new BusinessException("请选择寿险职场");
		}
		CountermanLifeCareer countermanLifeCareer = countermanLifeCareerMapper.selectByPrimaryKey(counterman.getLifeCareerId());
		if(Objects.isNull(countermanLifeCareer)){
			throw new BusinessException("查不到所属寿险职场");
		}*/
		//校验手机号是否被使用
		CountermanExample ex = new CountermanExample();
		ex.createCriteria().andPhoneEqualTo(counterman.getPhone()).andStatusEqualTo(Counterman.STATUS_0);
		List<Counterman> countermans = countermanMapper.selectByExample(ex);
		if(!countermans.isEmpty()){
			throw new BusinessException("该手机号已被使用");
		}
		//校验工号是否被使用
		ex.clear();
		ex.createCriteria().andCountermanCodeEqualTo(counterman.getCountermanCode());
		List<Counterman> countermanss = countermanMapper.selectByExample(ex);
		if(!countermanss.isEmpty()){
			throw new BusinessException("该工号已被使用");
		}
		
		counterman.setUserId(0L);
		counterman.setCaiDotName(countermanCaiDot.getDotName());
		//counterman.setCareerCode(countermanLifeCareer.getCareerCode());
		//counterman.setCareerName(countermanLifeCareer.getCareerName());
		counterman.setCreateTime(new Date());
		counterman.setUpdateTime(new Date());
		if(countermanMapper.insertSelective(counterman)==1){
			ex.clear();
			ex.createCriteria().andCountermanCodeEqualTo(counterman.getCountermanCode());
			List<Counterman> countermansss = countermanMapper.selectByExample(ex);
			CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
			countermanScoreRecord.setCountermanId(countermansss.get(0).getId());
			countermanScoreRecord.setCountermanCode(counterman.getCountermanCode());
			countermanScoreRecord.setCreateTime(new Date());
			countermanScoreRecord.setUpdateTime(new Date());
			countermanScoreRecord.setCaiDotId(countermanCaiDot.getId());
			countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_1);
			countermanScoreRecord.setOperater(userId);
			countermanScoreRecord.setScore(counterman.getScore());
			countermanScoreRecordMapper.insertSelective(countermanScoreRecord);
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("添加业务员信息失败！");
		
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> updateCounterman(Counterman counterman,Long userId) {
		if(StringUtils.isEmpty(counterman.getPhone())){
			throw new BusinessException("手机号不能为空");
		}
/*		if(StringUtils.isEmpty(counterman.getCountermanCode())){
			throw new BusinessException("工号不能为空");
		}*/
/*		if(Objects.isNull(counterman.getCaiDotId())){
			throw new BusinessException("请选择财险网点");
		}
		CountermanCaiDot countermanCaiDot = countermanCaiDotMapper.selectByPrimaryKey(counterman.getCaiDotId());
		if(Objects.isNull(countermanCaiDot)){
			throw new BusinessException("查不到所属网点");
		}*/
		
/*		if(0==counterman.getLifeCareerId()){
			throw new BusinessException("请选择寿险职场");
		}
		CountermanLifeCareer countermanLifeCareer = countermanLifeCareerMapper.selectByPrimaryKey(counterman.getLifeCareerId());
		if(Objects.isNull(countermanLifeCareer)){
			throw new BusinessException("查不到所属寿险职场");
		}*/
		//若手机号修改后,查看是否存在相同的手机号
		CountermanExample ex = new CountermanExample();
		ex.createCriteria().andPhoneEqualTo(counterman.getPhone()).andStatusEqualTo(Counterman.STATUS_0);
		List<Counterman> countermans = countermanMapper.selectByExample(ex);
		if(!countermans.isEmpty()){
			if(!countermans.get(0).getId().equals(counterman.getId())){
				throw new BusinessException("该手机号已被使用");
			}
		}
		//把已经页面传过来的数据赋值到一个对象中
		Counterman countermanss = countermanMapper.selectByPrimaryKey(counterman.getId());
		BigDecimal dongPrice = counterman.getScore().subtract(countermanss.getScore());
		countermanss.setName(counterman.getName());
		countermanss.setPhone(counterman.getPhone());
		countermanss.setStatus(counterman.getStatus());
		countermanss.setScore(counterman.getScore());
	
/*		countermanss.setCaiDotId(counterman.getCaiDotId());
		countermanss.setCaiDotName(countermanCaiDot.getDotName());*/
		//countermanss.setLifeCareerId(counterman.getLifeCareerId());
		//countermanss.setCareerCode(countermanLifeCareer.getCareerCode());
		//countermanss.setCareerName(countermanLifeCareer.getCareerName());
		countermanss.setUpdateTime(new Date());
		//查询是否已绑定user表
		if(Counterman.STATUS_1.equals(countermanss.getStatus())){
			if(Objects.nonNull(countermanss.getUserId())){
				User user = userMapper.selectByPrimaryKey(countermanss.getUserId());
				if(Objects.nonNull(user)){
					user.setUserType(User.USER_TYPE_1);
					user.setUpdateTime(new Date());
					userMapper.updateByPrimaryKeySelective(user);
				}
			}
			countermanss.setUserId(0L);	
		}
		if(countermanMapper.updateByPrimaryKey(countermanss)==1){
			if(dongPrice.compareTo(BigDecimal.ZERO) !=0){
				CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
				countermanScoreRecord.setCountermanId(countermanss.getId());
				countermanScoreRecord.setCountermanCode(countermanss.getCountermanCode());
				countermanScoreRecord.setCreateTime(new Date());
				countermanScoreRecord.setUpdateTime(new Date());
				countermanScoreRecord.setCaiDotId(countermanss.getCaiDotId());
				countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_1);
				countermanScoreRecord.setOperater(userId);
				countermanScoreRecord.setScore(dongPrice);
				countermanScoreRecordMapper.insertSelective(countermanScoreRecord);
			}
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("更新业务员信息失败！");
	}
	/*@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> importCountermanInfo(List<Map<String, Object>> params,Long userId) {
		if(params == null || params.isEmpty()){
			return DatasetBuilder.fromMessageSimple("参数为空!");
		}
		final AtomicInteger successCount = new AtomicInteger(0);
		//数据由map转成countermanInfo类
		params.stream().forEach(map -> {
			CountermanInfo info = new CountermanInfo();
			Arrays.stream(CountermanInfo.class.getDeclaredMethods())
			.filter(m -> m.getName().startsWith("set"))
			.filter(m -> m.getName().indexOf("createTime")==-1)
			.filter(m -> m.getName().indexOf("updateTime")==-1)
			.filter(m -> m.getName().indexOf("id")==-1)
			.filter(method -> Objects.equals(1,
                    method.getParameterCount()))
			.filter(method -> Modifier.isPublic(method.getModifiers()))
	         .forEach(method -> {
	        	 String key = method.getName().substring(3);
                   try {
	               		int i = 0;
	               		boolean isSplit = false;
	               		for(char r : key.toCharArray()){
	               			if(i!=0 && (int)r >=65 && (int)r <=90){
	               				isSplit = true;
	               				break;
	               			}
	               			i++;
	               		}
	               		if(isSplit){
	               			key = key.substring(0, i)+"_"+key.substring(i);
	            		}
	               		key = key.toLowerCase();
	               		if("polioy_number".equals(key) 
	               				|| "counterman_name".equals(key)
	               				|| "counterman_code".equals(key)
	               				|| "career_code".equals(key)
	               				|| "career_name".equals(key)
	               				|| "service_number".equals(key)
	               				|| "type".equals(key)
	               				|| "branch_company".equals(key)
	               				|| "belong_area".equals(key)
	               				|| "insurance_code".equals(key)
	               				|| "insurance_name".equals(key)
	               				|| "sale_code".equals(key)
	               				|| "sale_name".equals(key)
	               				|| "business_code".equals(key)
	               				|| "business_name".equals(key)
	               				|| "salesman_code".equals(key)
	               				|| "salesman_name".equals(key)){
	               			if(map.get(key)!=null){
	               				map.put(key, String.valueOf(map.get(key)).trim());
	               			}
	               		}
	               		
	               		if("order_price".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
	               		if("premium_price".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
	               		if("preminum_two".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
                       method.invoke(info,map.get(key));
                   } catch (IllegalAccessException
                           | IllegalArgumentException
                           | InvocationTargetException e) {
                       //注入失败会导致控制器不可用，需要开发人员查找原因
                	   System.out.println(key + ":" + map.get(key));
                       e.printStackTrace();
                       throw new BusinessException("保单数据格式不正确!错误字段："+key + ":" + map.get(key));
                   }
            });
			//若保单中的收付日期为空
			if(Objects.isNull(info.getPaymentTime())){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，没有收付日期:"+info.getPolicyNumber());
			}
			//若保单中的寿险营销员为空
			if(StringUtils.isEmpty(info.getCountermanCode())){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，没有寿险营销员:"+info.getCountermanName());
			}
			//查询是否存在该寿险营销员
			CountermanExample countermanEx = new CountermanExample();
			countermanEx.createCriteria().andCountermanCodeEqualTo(info.getCountermanCode()).andStatusEqualTo(Counterman.STATUS_0);
			countermanEx.setLimitStart(0);
			countermanEx.setLimitEnd(1);
			List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
			if(countermans.isEmpty()){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，查不到寿险营销员:"+info.getCountermanName());
			}
			//查询该寿险营销员是否有所属的财险网点
			if(Objects.isNull(countermans.get(0).getCaiDotId())){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，该寿险营销员:"+info.getCountermanName()+"没有所属的财险网点:");
			}
			//若保单中的财险职场是否为空
			if(StringUtils.isEmpty(info.getCareerCode())){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，没有财险职场:"+info.getCareerCode());
			}
			//查询是否存在该财险职场
			CountermanCareerExample countermanCareerEx = new CountermanCareerExample();
			countermanCareerEx.createCriteria().andCareerCodeEqualTo(info.getCareerCode());
			countermanEx.setLimitStart(0);
			countermanEx.setLimitEnd(1);
			List<CountermanCareer> countermanCareers = countermanCareerMapper.selectByExample(countermanCareerEx);
			if(countermanCareers.isEmpty()){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，查不到该财险职场:"+info.getCareerName());
			}
			//根据保单号判断保单是否重复导入
			CountermanInfoExample ex = new CountermanInfoExample();
			ex.createCriteria().andPolicyNumberEqualTo(info.getPolicyNumber());
			ex.setLimitStart(0);
			ex.setLimitEnd(1);
			List<CountermanInfo> countermanInfos = countermanInfoMapper.selectByExample(ex);
			if(countermanInfos.size()>0){
				Log4jHelper.getLogger().info("导入用户保单信息失败");
				throw new BusinessException("该保单已导入:"+info.getPolicyNumber());
			}
			//判断导入日期跟收付日期是否符合条件(是否在一年？是否在一个季度？是否处在某个特殊日期)
			Calendar now = Calendar.getInstance(Locale.CHINA);
			int month1 = now.get(Calendar.MONTH);
			int year1 =now.get(Calendar.YEAR);
			now.setTime(info.getPaymentTime());
			int month2 =now.get(Calendar.MONTH);
			int year2 =now.get(Calendar.YEAR);
			Map<String, Date> date = getDate(new Date());
			if(new Date().after(date.get("startTime"))&&new Date().before(date.get("endTime"))){
				if(year1!=year2 || (getJiduByMonth(month1)-getJiduByMonth(month2))!=3){
					Log4jHelper.getLogger().info("导入用户保单信息失败");
					throw new BusinessException("现在处于上季度积分结算期,只能导入上季度的保单:"+info.getPolicyNumber());
				}
			}else{
				//判断收付日期是否超出该季度
				if(year1!=year2 || getJiduByMonth(month1)!=getJiduByMonth(month2)){
					Log4jHelper.getLogger().info("导入用户保单信息失败");
					throw new BusinessException("该保单收付日期已经超出当季度:"+info.getPolicyNumber());
				}
			}
			//查找该业务员该月的保费总额
			Map<String, Object> parameterMap = new HashMap<String,Object>();
			//设置寿险业务员代号
			parameterMap.put("countermanCode", info.getCountermanCode());
			//设置出单类型
			parameterMap.put("orderType", info.getOrderType());
			//设置计算出当前参数所给月数
			now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
			now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
			now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
			now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
			now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
			parameterMap.put("startTime", now.getTime());
			//设置计算出当前参数所给月数+1
			now.add(Calendar.MONTH, 1);
			parameterMap.put("endTime", now.getTime());
			//查出该业务员当月的某个出单类型的保费总额
			BigDecimal orderPrices = definedMapper.queryTotalMoneyBycounterman(parameterMap);
			if(Objects.isNull(orderPrices)){
				orderPrices = new BigDecimal("0");
			}
			//插入保单数据（counterman_info表）
			info.setCreateTime(new Date());
			info.setUpdateTime(new Date());
			if(countermanInfoMapper.insertSelective(info)==1){
				successCount.incrementAndGet();
				Log4jHelper.getLogger().info("新增用户保单信息成功: " + JacksonHelper.toPrettyJSON(info));
			}else{
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
			}
			//计算该保单的积分
			int price = orderPrices.intValue();
			int score = 0;
			int price2=orderPrices.add(info.getOrderPrice()).intValue();
			//根据出单类型区分积分计算公式
			if(info.getOrderType()==1){
				if(price<30000){
					if(price2<=30000){
						score=info.getOrderPrice().divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}else if(price2>30000 && price2<=50000){
						BigDecimal subtract = orderPrices.add(info.getOrderPrice()).subtract(new BigDecimal("30000"));
						BigDecimal subtract2 = info.getOrderPrice().subtract(subtract);
						score=subtract.multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()
						+ subtract2.divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}else{
						BigDecimal subtract = new BigDecimal("30000").subtract(orderPrices);
						BigDecimal subtract2 = orderPrices.add(info.getOrderPrice()).subtract(new BigDecimal("50000"));
						score=subtract.divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()	
						+new BigDecimal("20000").multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()
						+ subtract2.multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}
				}else if(price>=30000 && price<50000){
					if(price2<=50000){
						score=info.getOrderPrice().multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}else{
						BigDecimal subtract3 = orderPrices.add(info.getOrderPrice()).subtract(new BigDecimal("50000"));
						BigDecimal subtract4 = info.getOrderPrice().subtract(subtract3);
						score=subtract3.multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()
						+ subtract4.multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}
				}else{
					score=info.getOrderPrice().multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
				}
			}else{
				if(price<30000){
					if(orderPrices.add(info.getOrderPrice()).intValue()<=30000){
						score=info.getOrderPrice().multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}else if(price2>30000 && price2<=50000){
						BigDecimal subtract = orderPrices.add(info.getOrderPrice()).subtract(new BigDecimal("30000"));
						BigDecimal subtract2 = info.getOrderPrice().subtract(subtract);
						score=subtract.multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()
						+ subtract2.multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}else{
						BigDecimal subtract = new BigDecimal("30000").subtract(orderPrices);
						BigDecimal subtract2 = orderPrices.add(info.getOrderPrice()).subtract(new BigDecimal("50000"));
						score=subtract.multiply(new BigDecimal("1.1")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()	
						+new BigDecimal("20000").multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()
						+ subtract2.multiply(new BigDecimal("1.3")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}
				}else if(price>=30000 && price<50000){
					if(orderPrices.add(info.getOrderPrice()).intValue()<=50000){
						score=info.getOrderPrice().multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}else{
						BigDecimal subtract3 = orderPrices.add(info.getOrderPrice()).subtract(new BigDecimal("50000"));
						BigDecimal subtract4 = info.getOrderPrice().subtract(subtract3);
						score=subtract3.multiply(new BigDecimal("1.3")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue()
						+ subtract4.multiply(new BigDecimal("1.2")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
					}
				}else{
					score=info.getOrderPrice().multiply(new BigDecimal("1.3")).divide(new BigDecimal("1"), 0, BigDecimal.ROUND_UP).intValue();
				}
			}
			CountermanScoreExample countermanScoreEx = new CountermanScoreExample();
			countermanScoreEx.createCriteria().andCountermanCodeEqualTo(info.getCountermanCode())
			.andBelongAreaEqualTo(countermans.get(0).getCaiDotName());
			countermanScoreEx.setLimitStart(0);
			countermanScoreEx.setLimitEnd(1);
			List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(countermanScoreEx);
			if(countermanScores.isEmpty()){
				CountermanScore countermanScore = new CountermanScore();
				countermanScore.setBelongArea(countermans.get(0).getCaiDotName());
				countermanScore.setCountermanId(countermans.get(0).getId());;
				countermanScore.setCountermanCode(info.getCountermanCode());
				countermanScore.setCountermanName(countermans.get(0).getName());
				countermanScore.setScore(new BigDecimal(score));
				if(info.getOrderType()==1){
					countermanScore.setChangScore(new BigDecimal(score));
				}else{
					countermanScore.setCaiScore(new BigDecimal(score));
				}
				countermanScore.setNumber(new BigDecimal("1"));
				countermanScore.setTotalPrice(info.getOrderPrice());
				countermanScore.setCreateTime(new Date());
				countermanScore.setUpdateTime(new Date());
				countermanScoreMapper.insertSelective(countermanScore);
			}else{
				countermanScores.get(0).setScore(countermanScores.get(0).getScore().add(new BigDecimal(score)));
				if(info.getOrderType()==1){
					countermanScores.get(0).setChangScore(countermanScores.get(0).getChangScore().add(new BigDecimal(score)));
				}else{
					countermanScores.get(0).setCaiScore(countermanScores.get(0).getCaiScore().add(new BigDecimal(score)));
				}
				countermanScores.get(0).setNumber(countermanScores.get(0).getNumber().add(new BigDecimal("1")));
				countermanScores.get(0).setTotalPrice(countermanScores.get(0).getTotalPrice().add(info.getOrderPrice()));
				countermanScores.get(0).setUpdateTime(new Date());
				countermanScoreMapper.updateByPrimaryKey(countermanScores.get(0));
			}
			//添加积分变动记录
			CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
			countermanScoreRecord.setBelongArea(countermans.get(0).getCaiDotName());
			countermanScoreRecord.setCountermanCode(info.getCountermanCode());
			countermanScoreRecord.setRecordRemark(info.getPolicyNumber());
			countermanScoreRecord.setCountermanId(countermans.get(0).getId());
			countermanScoreRecord.setScore(new BigDecimal(score));
			countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_0);
			countermanScoreRecord.setOperater(userId);
			countermanScoreRecord.setCreateTime(new Date());
			countermanScoreRecord.setUpdateTime(new Date());
			countermanScoreRecordMapper.insertSelective(countermanScoreRecord);
		});
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success", successCount.intValue());
		result.put("fail", params.size()-successCount.intValue());
		return DatasetBuilder.fromDataSimple(result);
	}
	private Map<String, Date> getDate(Date payment){
		Calendar instance = Calendar.getInstance(Locale.CHINA);
		instance.setTime(payment);
		int month = instance.get(Calendar.MONTH);
		int jidu = getJiduByMonth(month);
		instance.set(Calendar.MONTH, jidu);
		instance.add(Calendar.DAY_OF_MONTH, -instance.get(Calendar.DAY_OF_MONTH)+1);
		instance.add(Calendar.HOUR_OF_DAY, -instance.get(Calendar.HOUR_OF_DAY));
		instance.add(Calendar.MINUTE, -instance.get(Calendar.MINUTE));
		instance.add(Calendar.SECOND, -instance.get(Calendar.SECOND));
		instance.add(Calendar.MILLISECOND, -instance.get(Calendar.MILLISECOND));
		Date startTime = instance.getTime();
		instance.add(Calendar.DAY_OF_MONTH, 4);
		Date endTime = instance.getTime();
		HashMap<String, Date> timeMap = new HashMap<String,Date>();
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);
		return timeMap;
	}
	private int getJiduByMonth(int month) {
		int months[] = { 0, 3, 6, 9 };
		if (month >= 0 && month <= 2){ // 1-3月;0,1,2
			return months[0];
		}
		else if (month >= 3 && month <= 5){ // 4-6月;3,4,5
			return months[1];
		}else if (month >= 6 && month <= 8){ // 7-9月;6,7,8
			return months[2];
		}else{
			return months[3];// 10-12月;9,10,11
		}
	}

	@Override
	@Read
	public DatasetList<CountermanInfo> adminCountermanInfoList(Integer pageNo, Integer pageSize,
			CountermanInfo countermanInfo) {
		CountermanInfoExample ex = new CountermanInfoExample();
		com.emate.shop.business.model.CountermanInfoExample.Criteria cri = ex.createCriteria();
		if(StringUtils.isNotEmpty(countermanInfo.getPolicyNumber())){
			cri.andPolicyNumberEqualTo(countermanInfo.getPolicyNumber());
		}
		if(StringUtils.isNotEmpty(countermanInfo.getCountermanCode())){
			cri.andCountermanCodeEqualTo(countermanInfo.getCountermanCode());
		}
		if(StringUtils.isNotEmpty(countermanInfo.getCountermanName())){
			cri.andCountermanNameLike("%"+countermanInfo.getCountermanName()+"%");
		}
		if(StringUtils.isNotEmpty(countermanInfo.getCareerCode())){
			cri.andCareerCodeEqualTo(countermanInfo.getCareerCode());
		}
		if(StringUtils.isNotEmpty(countermanInfo.getCareerName())){
			cri.andCareerNameLike("%"+countermanInfo.getCareerName()+"%");
		}
		ex.setOrderByClause(CountermanInfoExample.CREATE_TIME_DESC);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanInfoMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		return DatasetBuilder.fromDataList(countermanInfoMapper.selectByExample(ex), page.createPageInfo());
	}*/

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> bindCounterman(Long userId, String countermanCode, String realName) {
		//查询用户信息
		User user = userMapper.selectByPrimaryKey(userId);
		if(Objects.isNull(user)){
			throw new BusinessException("手机号没有登陆");
		}
		//组织查询业务员的条件
		CountermanExample countermanEx = new CountermanExample();
		Criteria c = countermanEx.createCriteria();
		c.andCountermanCodeEqualTo(countermanCode)
		.andNameEqualTo(realName);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		//判断若该业务员为空
		if(countermans.isEmpty()){
			throw new BusinessException("没有找到该业务员工号！");
		}
		if(Counterman.STATUS_1.equals(countermans.get(0).getStatus())){
			throw new BusinessException("该业务员已离职,请联系客服修改");
		}
		if(!user.getName().equals(countermans.get(0).getPhone())){
			throw new BusinessException("登陆的手机号跟该工号的手机号不一致:请联系客服修改");
		}
		if(Objects.nonNull(countermans.get(0).getUserId())&&countermans.get(0).getUserId() > 0L){
			throw new BusinessException("该工号已注册,请联系客服修改");
		}
		//绑定业务员时,需要更新user表的user_type属性和向业务员(business_info表)中修改userId和更新时间
		user.setUserType(User.USER_TYPE_2);
		user.setUpdateTime(new Date());
		//若更新用户失败,抛绑定失败异常
		if(userMapper.updateByPrimaryKeySelective(user)!=1){
			throw new BusinessException("绑定失败,errCode:0！");
		}
		countermans.get(0).setUserId(userId);
		countermans.get(0).setUpdateTime(new Date());
		//更新业务员表失败,抛绑定失败异常
		if(countermanMapper.updateByPrimaryKeySelective(countermans.get(0))!=1){
			throw new BusinessException("绑定失败,errCode:1！");
		}
		//绑定成功返回~
		Map<String,String> result = new HashMap<String,String>();
		result.put("result", "success");
		result.put("countermanCode", countermanCode);
		return DatasetBuilder.fromDataSimple(result);
	}
	
/*	@Override
	@Read
	public DatasetList<Map<String, Object>> getCountermanInfo(Long userId){
		//判断业务员代码是否为空
		if(Objects.isNull(userId)){
			throw new BusinessException("请重新登录~~");
		}
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andUserIdEqualTo(userId);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		if(countermans.isEmpty()){
			throw new BusinessException("请绑定业务员~~");
		}
		if(!Counterman.STATUS_0.equals(countermans.get(0).getStatus())){
			throw new BusinessException("您绑定业务员已离职~~");
		}
		String countermanCode = countermans.get(0).getCountermanCode();
		//查询业务员积分:查询条件:所属片区,业务员代码
		CountermanScoreExample countermanScoreEx = new CountermanScoreExample(); 
		com.emate.shop.business.model.CountermanScoreExample.Criteria criteria = countermanScoreEx.createCriteria();
		List<String> belongAreaList = new ArrayList<String>();
		belongAreaList.add("黄埔");
		belongAreaList.add("黄埔_萝岗");
		belongAreaList.add("黄埔_南岗");
		criteria.andBelongAreaIn(belongAreaList);
		criteria.andCountermanCodeEqualTo(countermanCode);
		List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(countermanScoreEx);
		//查询昨天的导入保单的量和保费总额
		Map<String, Object> huangpu = getBaoDanData("黄埔");
		Map<String, Object> luogang = getBaoDanData("黄埔_萝岗");
		Map<String, Object> nangang = getBaoDanData("黄埔_南岗");
		//整理返回结果
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> orderNumberMap = new HashMap<String,Object>();
		orderNumberMap.put("huangpu",huangpu.get("orderNumber"));
		orderNumberMap.put("luogang",luogang.get("orderNumber"));
		orderNumberMap.put("nangang",nangang.get("orderNumber"));
		resultList.add(orderNumberMap);
		Map<String, Object> totalPriceMap = new HashMap<String,Object>();
		totalPriceMap.put("huangpu",huangpu.get("totelPrice"));
		totalPriceMap.put("luogang",luogang.get("totelPrice"));
		totalPriceMap.put("nangang",nangang.get("totelPrice"));
		resultList.add(totalPriceMap);
		Map<String, Object> scoreMap = new HashMap<String,Object>();
		for(CountermanScore countermanScore : countermanScores){
			if("黄埔".equals(countermanScore.getBelongArea())){
				scoreMap.put("huangpu", countermanScore.getScore());
			}else if("黄埔_萝岗".equals(countermanScore.getBelongArea())){
				scoreMap.put("luogang", countermanScore.getScore());
			}else if("黄埔_南岗".equals(countermanScore.getBelongArea())){
				scoreMap.put("nangang", countermanScore.getScore());
			}
		}
		resultList.add(scoreMap);
		return DatasetBuilder.fromDataList(resultList);
	}*/
	
	/*public Map<String, Object> getBaoDanData(String belongArea){
		//获取职场代码
		CountermanCareerExample countermanCareerEx = new CountermanCareerExample();
		countermanCareerEx.createCriteria().andCaiDotNameEqualTo(belongArea);
		List<CountermanCareer> huangpuCareer = countermanCareerMapper.selectByExample(countermanCareerEx);
		List<String> huangpuCareerCode = huangpuCareer.stream().map(CountermanCareer::getCareerCode).distinct().collect(Collectors.toList());
		//设置收付日期筛选条件
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		Date startTime = now.getTime();
		now.add(Calendar.DAY_OF_MONTH, 1);
		Date endTime = now.getTime();
		//查询导入保单表(countermanInfo)
		CountermanInfoExample countermanInfoEx= new CountermanInfoExample();
		com.emate.shop.business.model.CountermanInfoExample.Criteria criteria = countermanInfoEx.createCriteria();
		criteria.andPaymentTimeGreaterThanOrEqualTo(startTime);
		criteria.andPaymentTimeLessThan(endTime);
		criteria.andCareerCodeIn(huangpuCareerCode);
		List<CountermanInfo> countermanInfos = countermanInfoMapper.selectByExample(countermanInfoEx);	
		//统计数据
		HashMap<String, Object> huangpuMap = new HashMap<String,Object>();
		if(countermanInfos.isEmpty()){
			huangpuMap.put("orderNumber", 0);
			huangpuMap.put("totelPrice",0);
			return huangpuMap;
		}
		BigDecimal totelPrice = new BigDecimal(0);
		int orderNumber = countermanInfos.size();
		for(CountermanInfo countermanInfo : countermanInfos){
			totelPrice = totelPrice.add(countermanInfo.getOrderPrice());
		}
		huangpuMap.put("orderNumber", orderNumber);
		huangpuMap.put("totelPrice",totelPrice);
		return huangpuMap;
	}*/

	@Override
	@Read
	public DatasetSimple<Map<String, Object>> h5GetCountermanByUserId(Long userId) {
		//判断业务员代码是否为空
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andUserIdEqualTo(userId);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		if(countermans.isEmpty()){
			throw new BusinessException("请绑定业务员~~");
		}
		if(Counterman.STATUS_1.equals(countermans.get(0).getStatus())){
			throw new BusinessException("您已不在职,请联系客服~");
		}
	/*	CountermanScoreExample countermanScoreEx = new CountermanScoreExample();
		countermanScoreEx.createCriteria().andCountermanCodeEqualTo(countermans.get(0).getCountermanCode());
		countermanScoreEx.setLimitStart(0);
		countermanScoreEx.setLimitEnd(1);
		List<CountermanScore> countermanScore = countermanScoreMapper.selectByExample(countermanScoreEx);*/
		Map<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("countermanCode",countermans.get(0).getCountermanCode());
		hashMap.put("countermanName",countermans.get(0).getName());
		//hashMap.put("careerName",countermans.get(0).getCareerName());
		hashMap.put("score",countermans.get(0).getScore());
		hashMap.put("countermanPhone",countermans.get(0).getPhone());
		hashMap.put("dotName",countermans.get(0).getCaiDotName());
/*		if(countermanScore.isEmpty()){
			hashMap.put("score",0);
		}else{
			hashMap.put("score",countermanScore.get(0).getScore());
		}*/
/*		if("黄埔_萝岗".equals(countermans.get(0).getCaiDotName())){
			hashMap.put("dotName","萝岗");
		}else if("黄埔_南岗".equals(countermans.get(0).getCaiDotName())){
			hashMap.put("dotName","南岗");
		}else{
			hashMap.put("dotName",countermans.get(0).getCaiDotName());
		}*/
		return DatasetBuilder.fromDataSimple(hashMap);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> importCounterman(List<Map<String, Object>> params,Long userId) {
		if(params == null || params.isEmpty()){
			return DatasetBuilder.fromMessageSimple("参数为空!");
		}
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(userId);
		CountermanCaiDotExample caiDotEx= new CountermanCaiDotExample();
		caiDotEx.createCriteria().andPinYinEqualTo(systemUser.getUserName());
		List<CountermanCaiDot> caiDots = countermanCaiDotMapper.selectByExample(caiDotEx);
		final AtomicInteger successCount = new AtomicInteger(0);
		params.stream().forEach(map -> {
			Counterman counterman = new Counterman();
			Arrays.stream(Counterman.class.getDeclaredMethods())
			.filter(m -> m.getName().startsWith("set"))
			.filter(m -> m.getName().indexOf("createTime")==-1)
			.filter(m -> m.getName().indexOf("updateTime")==-1)
			.filter(m -> m.getName().indexOf("userId")==-1)
			.filter(m -> m.getName().indexOf("id")==-1)
			.filter(m -> m.getName().indexOf("careerName")==-1)
			.filter(m -> m.getName().indexOf("careerCode")==-1)
			.filter(m -> m.getName().indexOf("lifeCareerId")==-1)
			.filter(m -> m.getName().indexOf("status")==-1)
			.filter(method -> Objects.equals(1,
                    method.getParameterCount()))
			.filter(method -> Modifier.isPublic(method.getModifiers()))
	         .forEach(method -> {
	        	 String key = method.getName().substring(3);
                   try {
	               		int i = 0;
	               		int first = 0;
	               		int second = 0;
	               		for(char r : key.toCharArray()){
	               			if(i!=0 && (int)r >=65 && (int)r <=90){
	               				if(first==0){
	               					first =i;
	               				}else{
	               					second = i;
	               				}
	               			}
	               			i++;
	               		}
	               		if(second>first){
	               			key = key.substring(0, first)+"_"+key.substring(first,second)+"_"+key.substring(second);
	               		}else if(first!=0){
	               			key = key.substring(0, first)+"_"+key.substring(first);
	               		}
	               			
	               		key = key.toLowerCase();
                       //使用set方法注入代理对象
	               		if("name".equals(key) 
	               				|| "counterman_code".equals(key) 
	               				|| "phone".equals(key)
	               				|| "cai_dot_name".equals(key)){
	               			if(map.get(key)!=null){
	               				map.put(key, String.valueOf(map.get(key)).trim());
	               			}
	               		}
	               		if("score".equals(key) || "score".equals(key)){
	               			map.put(key, new BigDecimal(String.valueOf(map.get(key))));
	               		}
                       method.invoke(counterman,map.get(key));
                   } catch (IllegalAccessException
                           | IllegalArgumentException
                           | InvocationTargetException e) {
                	   System.out.println(key + ":" + map.get(key));
                       e.printStackTrace();
                       throw new BusinessException("业务员数据格式不正确!错误字段："+key + ":" + map.get(key));
                   }
            });
			if(caiDots.isEmpty()&&StringUtils.isEmpty(counterman.getCaiDotName())){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，没有所属的财险网点:"+counterman.getName());
			}
			CountermanCaiDotExample countermanCaiDotEx = new CountermanCaiDotExample();
			countermanCaiDotEx.createCriteria()
			.andDotNameEqualTo(counterman.getCaiDotName());
			List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(countermanCaiDotEx);
			if(countermanCaiDots.isEmpty()||countermanCaiDots.size()>1){
				if(!caiDots.isEmpty()){
					counterman.setCaiDotId(caiDots.get(0).getId());
					counterman.setCaiDotName(caiDots.get(0).getDotName());
				}else{
					Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
					throw new BusinessException("导入失败，查不到所属的财险网点或所属网点不止一个:"+counterman.getName());
				}
			}else{
				//赋值财险网点id
				counterman.setCaiDotId(countermanCaiDots.get(0).getId());
			}

			//校验参数
			if(StringUtils.isEmpty(counterman.getName())){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，没有业务员姓名");
			}
			if(StringUtils.isEmpty(counterman.getPhone())){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，没有业务员手机号");
			}
			if(!counterman.getPhone().matches("^1[34578]\\d{9}$")){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，业务员手机号格式不对");
			}
			if(StringUtils.isEmpty(counterman.getCountermanCode())){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，没有业务员工号");
			}
			//查重
			CountermanExample countermanEx = new CountermanExample();
			countermanEx.createCriteria().andCountermanCodeEqualTo(counterman.getCountermanCode());
			List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
			if(!countermans.isEmpty()){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，业务员工号已存在:"+counterman.getCountermanCode());
			}
			countermanEx.clear();
			countermanEx.createCriteria().andPhoneEqualTo(counterman.getPhone());
			countermanMapper.selectByExample(countermanEx);
			if(!countermanMapper.selectByExample(countermanEx).isEmpty()){
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入失败，业务员手机号已存在:"+counterman.getPhone());
			}
			//更新业务员
			counterman.setStatus(Counterman.STATUS_0);;
			counterman.setUpdateTime(new Date());
			counterman.setCreateTime(new Date());;	
			if(countermanMapper.insertSelective(counterman)==1){
				successCount.incrementAndGet();
				Log4jHelper.getLogger().info("新增用户保单信息成功: " + JacksonHelper.toPrettyJSON(counterman));
				countermanEx.clear();
				countermanEx.createCriteria().andCountermanCodeEqualTo(counterman.getCountermanCode());
				List<Counterman> countermansss = countermanMapper.selectByExample(countermanEx);
				CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
				countermanScoreRecord.setCountermanId(countermansss.get(0).getId());
				countermanScoreRecord.setCountermanCode(counterman.getCountermanCode());
				countermanScoreRecord.setCreateTime(new Date());
				countermanScoreRecord.setUpdateTime(new Date());
				countermanScoreRecord.setCaiDotId(counterman.getCaiDotId());
				countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_1);
				countermanScoreRecord.setOperater(userId);
				countermanScoreRecord.setScore(counterman.getScore());
				countermanScoreRecordMapper.insertSelective(countermanScoreRecord);
			}else{
				Log4jHelper.getLogger().info("新增业务员信息失败: " + JacksonHelper.toPrettyJSON(counterman));
				throw new BusinessException("导入业务员失败，插入数据库异常:"+counterman.getCountermanCode());
			}
			
		});
		//组织返回结果
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success", successCount.intValue());
		result.put("fail", params.size()-successCount.intValue());
		return DatasetBuilder.fromDataSimple(result);
	}
	/**
	 * 根据业务员id查询积分变动记录
	 */
	@Override
	@Read
	public DatasetList<Map<String,Object>> queryScoreRecord(Integer pageNo, Integer pageSize, Counterman counterman,
			Long userId) {
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(userId);
		CountermanCaiDotExample caiDotEx = new CountermanCaiDotExample();
		caiDotEx.createCriteria().andPinYinEqualTo(systemUser.getUserName());
		List<CountermanCaiDot> caiDots = countermanCaiDotMapper.selectByExample(caiDotEx);
		

		CountermanScoreRecordExample ex = new CountermanScoreRecordExample();
		
		com.emate.shop.business.model.CountermanScoreRecordExample.Criteria c = ex.createCriteria();
		//财险网点id
		if(!caiDots.isEmpty()){
			c.andCaiDotIdEqualTo(caiDots.get(0).getId());
		}
		//业务员id
		if(Objects.nonNull(counterman.getId())){
			c.andCountermanIdEqualTo(counterman.getId());
		}
		//根据创建时间排序
		ex.setOrderByClause(CountermanScoreRecordExample.CREATE_TIME_DESC);
		//分页
		PaginationUtil page = new PaginationUtil(pageNo,pageSize,countermanScoreRecordMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanScoreRecord> scoreRecords = countermanScoreRecordMapper.selectByExample(ex);
		List<Long> countermanIds = scoreRecords.stream().map(CountermanScoreRecord::getCountermanId).distinct().collect(Collectors.toList());
		CountermanExample countermanEx = new CountermanExample();
		countermanIds.add(0L);
		countermanEx.createCriteria().andIdIn(countermanIds);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		HashMap<Long, Counterman> countermanMap = new HashMap<Long,Counterman>();
		countermans.forEach(e -> {
			countermanMap.put(e.getId(), e);
		});
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(CountermanScoreRecord scoreRecord:scoreRecords){
			Map<String, Object> result = new HashMap<String,Object>();
			//id
			result.put("id", scoreRecord.getId());
			//操作类型
			result.put("operationType", scoreRecord.getOperationType());
			//操作记录
			result.put("recordRemark", scoreRecord.getRecordRemark());
			//业务员id
			result.put("countermanId", scoreRecord.getCountermanId());
			//业务员工号
			result.put("countermanCode", scoreRecord.getCountermanCode());
			//业务员姓名
			result.put("countermanName", countermanMap.get(scoreRecord.getCountermanId()).getName());
			//业务员手机号
			result.put("countermanPhone", countermanMap.get(scoreRecord.getCountermanId()).getPhone());
			//业务员当前积分
			result.put("countermanScore", countermanMap.get(scoreRecord.getCountermanId()).getScore());
			//变动积分
			result.put("score", scoreRecord.getScore());
			//创建时间
			result.put("createTime", scoreRecord.getCreateTime());
			//更新时间
			result.put("updateTime", scoreRecord.getUpdateTime());
			results.add(result);
		}
		return DatasetBuilder.fromDataList(results,page.createPageInfo());
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<Map<String, Object>> batchUpdateCounterman(List<Map<String, Object>> data,Long userId) {
		
		final AtomicInteger successCount = new AtomicInteger(0);
		for (Map<String, Object> map: data) {
			CountermanExample ex= new CountermanExample();
			ex.createCriteria().andCountermanCodeEqualTo(String.valueOf(map.get("countermanCode")));
			List<Counterman> counterman = countermanMapper.selectByExample(ex);
			if(counterman.isEmpty()){
				throw new BusinessException("没查到该业务员信息~~");
			}
			String scoreStr = String.valueOf(map.get("score"));
			BigDecimal score = new BigDecimal(scoreStr);
			if("0".equals(String.valueOf(map.get("type")))){
				BigDecimal add = counterman.get(0).getScore().add(score);
				counterman.get(0).setScore(add);
			}else{
				BigDecimal subtract = counterman.get(0).getScore().subtract(score);
				if(subtract.intValue()<0){
					counterman.get(0).setScore(new BigDecimal("0"));
				}else{
					counterman.get(0).setScore(subtract);
				}	
			}
			if(countermanMapper.updateByPrimaryKeySelective(counterman.get(0))==1){
				successCount.incrementAndGet();
				Log4jHelper.getLogger().info("更新业务员信息成功: " + JacksonHelper.toPrettyJSON(counterman.get(0)));
				CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
				countermanScoreRecord.setCountermanId(counterman.get(0).getId());
				countermanScoreRecord.setCountermanCode(counterman.get(0).getCountermanCode());
				countermanScoreRecord.setCreateTime(new Date());
				countermanScoreRecord.setUpdateTime(new Date());
				countermanScoreRecord.setCaiDotId(counterman.get(0).getCaiDotId());
				countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_1);
				if("0".equals(String.valueOf(map.get("type")))){
					countermanScoreRecord.setScore(score);
				}else{
					countermanScoreRecord.setScore(new BigDecimal("0").subtract(score));
				}
				countermanScoreRecord.setOperater(userId);
				countermanScoreRecordMapper.insertSelective(countermanScoreRecord);
			};
		}
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success", successCount.intValue());
		result.put("fail", data.size()-successCount.intValue());
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	public DatasetList<Map<String, String>> exportCountermanExcel(Long userId) {
		
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		//根据登录用户查询财险网点
		String pinYin = systemUserMapper.selectByPrimaryKey(userId).getUserName();
		CountermanCaiDotExample ex = new CountermanCaiDotExample();
		ex.createCriteria().andPinYinEqualTo(pinYin);
		List<CountermanCaiDot> caiDots = countermanCaiDotMapper.selectByExample(ex);
		//组织当前月份
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		Date startTime = now.getTime();
		now.add(Calendar.MONTH,1);
		Date endTime = now.getTime();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		//根据条件查询业务员表,查询业务员该月积分,查询业务员积分记录
		CountermanExample countermanEx = new CountermanExample();
		CountermanScoreRecordExample scoreRecordEx = new CountermanScoreRecordExample();
		if(!caiDots.isEmpty()){
			map.put("caiDotId", caiDots.get(0).getId());
			countermanEx.createCriteria().andCaiDotIdEqualTo(caiDots.get(0).getId());
			scoreRecordEx.createCriteria().andCaiDotIdEqualTo(caiDots.get(0).getId());
		}
		List<Map<String, Object>> monthScore = definedMapper.queryScore(map);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		if(countermans.isEmpty()){
			return DatasetBuilder.fromDataList(results);
		}
		Map<Long, Counterman> countermanMap = new HashMap<Long,Counterman>();
		countermans.stream().forEach(counterman ->{
			countermanMap.put(counterman.getId(), counterman);
		});
		Map<String, BigDecimal> monthMap = new HashMap<String,BigDecimal>();
		monthScore.stream().forEach(month ->{
			String countermanCode = String.valueOf(month.get("countermanCode"));
			BigDecimal score = new BigDecimal(String.valueOf(month.get("score")));
			monthMap.put(countermanCode, score);
		});
		scoreRecordEx.setOrderByClause(CountermanScoreRecordExample.COUNTERMAN_CODE_DESC);
		List<CountermanScoreRecord> scoreRecords = countermanScoreRecordMapper.selectByExample(scoreRecordEx);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(scoreRecords.isEmpty()){
			return DatasetBuilder.fromDataList(results);
		}
		for(CountermanScoreRecord scoreRecord:scoreRecords){
			HashMap<String, String> result = new HashMap<String,String>();
			result.put("countermanCode", scoreRecord.getCountermanCode());
			result.put("countermanName", countermanMap.get(scoreRecord.getCountermanId()).getName());
			result.put("countermanScore", String.valueOf(countermanMap.get(scoreRecord.getCountermanId()).getScore().doubleValue()));
			if(Objects.isNull(monthMap.get(scoreRecord.getCountermanCode()))){
				result.put("monthScore", "0");
			}else{
				result.put("monthScore", String.valueOf(monthMap.get(scoreRecord.getCountermanCode()).doubleValue()));
			};
			
			result.put("score", String.valueOf(scoreRecord.getScore().doubleValue()));
			result.put("createTime", format.format(scoreRecord.getCreateTime()));
			results.add(result);
		}
		return DatasetBuilder.fromDataList(results);
	}
}
