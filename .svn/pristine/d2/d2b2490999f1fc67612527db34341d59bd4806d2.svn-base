package com.emate.shop.business.service;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.SellerService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.AutoInfoExample;
import com.emate.shop.business.model.AutoPartRelation;
import com.emate.shop.business.model.AutoPartRelationExample;
import com.emate.shop.business.model.AutoParts;
import com.emate.shop.business.model.AutoPartsExample;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.business.model.AutoposeExample;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.business.model.CarwashConfigExample;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.business.model.ScoreChannel;
import com.emate.shop.business.model.ScoreChannelExample;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerExample;
import com.emate.shop.business.model.SellerExample.Criteria;
import com.emate.shop.business.model.SellerInfo;
import com.emate.shop.business.model.SellerInfoExample;
import com.emate.shop.business.model.SellerReport;
import com.emate.shop.business.model.SellerReportExample;
import com.emate.shop.business.model.ServiceOperatorLog;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.business.model.UserInfoExample;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AutoInfoMapper;
import com.emate.shop.mapper.AutoPartRelationMapper;
import com.emate.shop.mapper.AutoPartsMapper;
import com.emate.shop.mapper.AutobrandMapper;
import com.emate.shop.mapper.AutoposeMapper;
import com.emate.shop.mapper.CarwashConfigMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.mapper.RegionsMapper;
import com.emate.shop.mapper.ScoreChannelMapper;
import com.emate.shop.mapper.SellerInfoMapper;
import com.emate.shop.mapper.SellerMapper;
import com.emate.shop.mapper.SellerReportMapper;
import com.emate.shop.mapper.ServiceOperatorLogMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.mapper.UserInfoMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 *  商家service
 * @author likk
 *
 */
@Service
public class SellerServiceImpl implements SellerService{

	@Resource
	private SellerMapper sellerMapper;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private SellerInfoMapper sellerInfoMapper;
	
	@Resource
	private AutoInfoMapper autoInfoMapper;
	
	@Resource
	private AutoPartRelationMapper autoPartRelationMapper;
	
	@Resource
	private AutoPartsMapper autoPartsMapper;
	
	@Resource
	private SellerReportMapper sellerReportMapper;
	
	@Resource
	private AutoposeMapper autoposeMapper;
	
	@Resource
	private RegionsMapper regionsMapper;
	
	@Resource
	private ScoreChannelMapper scoreChannelMapper;
	
	@Resource
	private AutobrandMapper brandMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;
	
	@Resource
	private ServiceOperatorLogMapper serviceOperatorLogMapper;
	
	@Resource
	private CarwashConfigMapper carwashConfigMapper;

	@Read
	@Override
	public DatasetList<Seller> adminSellerList(Integer pageNo,Integer pageSize,Seller seller) {
		SellerExample ex = new SellerExample();
		Criteria criteria = ex.createCriteria();
		if(seller.getSellerName()!=null && !"".equals(seller.getSellerName())){
			criteria.andSellerNameLike("%"+seller.getSellerName()+"%");
		}
		if(seller.getSellerGrade()!=null){
			criteria.andSellerGradeEqualTo(seller.getSellerGrade());
		}
		if(seller.getJoinType()!=null){
			criteria.andJoinTypeEqualTo(seller.getJoinType());
		}
		if(StringUtils.isNotEmpty(seller.getProvince())){
			criteria.andProvinceEqualTo(seller.getProvince());
		}
		if(StringUtils.isNotEmpty(seller.getCity())){
			criteria.andCityEqualTo(seller.getCity());
		}
		if(StringUtils.isNotEmpty(seller.getArea())){
			criteria.andAreaEqualTo(seller.getArea());
		}
		if(seller.getAuditStatus()!=null){
			criteria.andAuditStatusEqualTo(seller.getAuditStatus());
		}
		if(seller.getProperties()!=null){
			criteria.andPropertiesFilter(seller.getProperties().toString());
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, sellerMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		return DatasetBuilder.fromDataList(sellerMapper.selectByExample(ex), page.createPageInfo());
	}

	@Read
	@Override
	public DatasetSimple<Seller> querySellerById(String id) {
		Seller seller = sellerMapper.selectByPrimaryKey(Long.parseLong(id));
		if(seller == null){
			throw new BusinessException("账号不存在！");
		}
		return DatasetBuilder.fromDataSimple(seller);
	}

	//如果用户绑定商家，那么只查询用户所属的商家
	@Override
	@Read
	public DatasetList<Seller> h5SellerList(Long userId ,String type,String province,String city,String area,
			String sellerType,String sellerName,Integer pageNo,Integer pageSize,String isFixSeller) {
		SellerExample ex = new SellerExample();
		Criteria c = ex.createCriteria();

		if(StringUtils.isNotEmpty(province)){
			c.andProvinceEqualTo(province);
		}
		if(StringUtils.isNotEmpty(city)){
			c.andCityEqualTo(city);
		}
		if(StringUtils.isNotEmpty(area)){
			c.andAreaEqualTo(area);
		}
		
		if(StringUtils.isNotEmpty(sellerName)){
			c.andSellerNameLike("%"+sellerName+"%");
		}
		c.andPropertiesFilter(type);//用来筛选出不同类型的商家 1 小保养 2 洗车 4 钣金 8 换轮胎 16 喷漆
		c.andAuditStatusEqualTo(Seller.AUDIT_STATUS_2);//审核通过的状态
		if(StringUtils.isNotEmpty(sellerType)){
			if(!sellerType.matches("\\d+")){
				throw new BusinessException("商家等级格式不正确！");
			}
			c.andSellerGradeEqualTo(Integer.parseInt(sellerType));
		}
		if(StringUtils.isNotEmpty(sellerType)){
			if(!sellerType.matches("\\d+")){
				throw new BusinessException("商家等级格式不正确！");
			}
			c.andSellerGradeEqualTo(Integer.parseInt(sellerType));
		}
		//特殊判断喷漆
		ImportUserInfo importUserInfo = findImportUserInfo(userId);
		if("广州".equals(importUserInfo.getAddress())&&"16".equals(type)){
			//如果是喷漆
			if(StringUtils.isEmpty(sellerType)){
				c.andSellerGradeNotEqualTo(Seller.SELLER_GRADE_2);
				Criteria cri = ex.or();
				if(StringUtils.isNotEmpty(province)){
					cri.andProvinceEqualTo(province);
				}
				if(StringUtils.isNotEmpty(city)){
					cri.andCityEqualTo(city);
				}
				if(StringUtils.isNotEmpty(area)){
					cri.andAreaEqualTo(area);
				}
				if(StringUtils.isNotEmpty(sellerName)){
					cri.andSellerNameLike("%"+sellerName+"%");
				}
				cri.andPropertiesFilter(type);//用来筛选出不同类型的商家 1 小保养 2 洗车 4 钣金 8 换轮胎 16 喷漆
				cri.andAuditStatusEqualTo(Seller.AUDIT_STATUS_2);
				cri.andSellerGradeEqualTo(Seller.SELLER_GRADE_2).andCarBrandEqualTo(importUserInfo.getAutoBrand());
			}
			if(StringUtils.isNotEmpty(sellerType)&&Seller.SELLER_GRADE_2.toString().equals(sellerType)){
				if(!sellerType.matches("\\d+")){
					throw new BusinessException("商家等级格式不正确！");
				}
				c.andCarBrandEqualTo(importUserInfo.getAutoBrand());
			}
		}
		if("1".equals(isFixSeller)){
			ex.setOrderByClause(SellerExample.SELLER_ORDER_ASC);
		}
		
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, sellerMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		//ex.setOrderByClause(SellerExample.SCORE_SERVICE_DESC);
		List<Seller> sellers = sellerMapper.selectByExample(ex);
		
		//下面用来筛选出不同类型的商家 1 小保养 2 洗车 3 维修 4 轮胎
//		Iterator<Seller> it = sellers.iterator();
//		while(it.hasNext()){
//			Seller temp = it.next();
//			if((temp.getProperties() & Integer.parseInt(type)) == 0){
//				it.remove();
//			}
//		}
		if(!sellers.isEmpty()){
			List<Long> sellerId = sellers.stream().map(Seller::getId).collect(Collectors.toList());
			SellerInfoExample sellerInfoEx = new SellerInfoExample();
			sellerInfoEx.createCriteria().andSellerIdIn(sellerId);
			List<SellerInfo> infos = sellerInfoMapper.selectByExample(sellerInfoEx);
			Map<Long,SellerInfo> infoMap = new HashMap<Long,SellerInfo>();
			infos.forEach(info -> {
				infoMap.put(info.getSellerId(), info);
			});
			sellers.forEach(seller -> {
				seller.setSellerLogo(infoMap.get(seller.getId()).getShopPic());
			});
		}
		return DatasetBuilder.fromDataList(sellers,page.createPageInfo());
	}
	/**
	 * 根据用户id查询用户的保单
	 * @param userId
	 * @return
	 */
	private ImportUserInfo findImportUserInfo(Long userId){
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(userId);
		userInfoEx.setLimitStart(0);
		userInfoEx.setLimitEnd(1);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		if(userInfos.isEmpty()){
			throw new BusinessException("用户信息为空！");
		}
		UserInfo userInfo = userInfos.get(0);
		String chepai = userInfo.getLicense();
		ImportUserInfoExample importEx = new ImportUserInfoExample();
		importEx.createCriteria().andChePaiEqualTo(chepai);
		importEx.setLimitStart(0);
		importEx.setLimitEnd(1);
		List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
		if(importList.isEmpty()){
			throw new BusinessException("用户绑定车型信息为空！");
		}
		return importList.get(0);
	}
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> importUserInfo(List<Map<String, Object>> params) {
		if(params == null || params.isEmpty()){
			return DatasetBuilder.fromMessageSimple("参数为空!");
		}
		final AtomicInteger successCount = new AtomicInteger(0);
		params.stream().forEach(map -> {
			//1.0 map数据转换成类数据
			ImportUserInfo info = new ImportUserInfo();
			Arrays.stream(ImportUserInfo.class.getDeclaredMethods())
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
                       //使用set方法注入代理对象
	               		if(map.get(key) instanceof Double){
	               			int value = ((Double)map.get(key)).intValue();
	               			map.put(key, value);
	               		}
	               		if("phone".equals(key) 
	               				|| "product_year".equals(key) 
	               				|| "engine_disp".equals(key)
	               				|| "auto_brand".equals(key)
	               				|| "car_factory".equals(key)
	               				|| "auto_type".equals(key)
	               				|| "che_jia".equals(key)
	               				|| "bao_dan".equals(key)
	               				|| "seller".equals(key)
	               				|| "che_pai".equals(key)
	               				|| "auto_line".equals(key)
	               				|| "engine_code".equals(key)
	               				|| "source".equals(key)
	               				|| "address".equals(key)){
	               			if(map.get(key)!=null){
	               				map.put(key, String.valueOf(map.get(key)).trim());
	               			}
	               		}
	               		if("price".equals(key) || "order_price".equals(key)){
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
			//2.0 判断保单所属城市是否为空
			if(StringUtils.isEmpty(info.getAddress())){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，没有保单地址:"+info.getChePai());
			}
			if(info.getAddress().endsWith("市")){
				String address = info.getAddress().substring(0,info.getAddress().length()-1);
				info.setAddress(address);
			}
			//3.0判断保单所属渠道是否为空
			if(StringUtils.isEmpty(info.getSource())){
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败，没有保单渠道:"+info.getChePai());
			}
			//4.0判断车牌是否为空
			if(StringUtils.isEmpty(info.getChePai())){
				if(StringUtils.isEmpty(info.getEngineCode())){
					Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
					throw new BusinessException("导入失败，没有车牌号和发动机号:"+info.getChePai());
				}
				info.setChePai(info.getEngineCode());
			}
			//5.0 判断车型，生产年份，品牌，发动机排量
			if(StringUtils.isEmpty(info.getAutoBrand())
				|| StringUtils.isEmpty(info.getAutoType())
				|| StringUtils.isEmpty(info.getEngineDisp())
				|| StringUtils.isEmpty(info.getProductYear())
				|| info.getPrice()==null
				){
				Log4jHelper.getLogger().error("导入失败,用户保单车型信息不全: " + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败,用户保单车型信息不全:"+JacksonHelper.toJSON(info));
			}
			//6.0根据保单增加次数(保养,喷漆,洗车,电瓶)
			Map<String, Integer> timesMap = addTimes(info);
			info.setBaoyangTimes(timesMap.get("baoyang"));
			info.setPenqiTimes(timesMap.get("penqi"));
			info.setXiecheTimes(timesMap.get("xiche"));
			info.setDianpingTimes(timesMap.get("dianping"));
			Map<String, Integer> couponMap = new HashMap<String,Integer>();
			couponMap.put("20", timesMap.get("juan50"));
			couponMap.put("50", timesMap.get("juan50"));
			String rechargeJson = JacksonHelper.toJSON(couponMap);
			info.setTotalCouponValue(rechargeJson);
			info.setSurplusCouponValue(rechargeJson);
			//7.0 赋值更新时间，创建时间，车牌
			info.setUpdateTime(new Date());
			info.setCreateTime(new Date());
			info.setChePai(info.getChePai().toUpperCase());	
			
			//8.0 为了让保单时间延长1天
			Calendar c = Calendar.getInstance();
			c.setTime(info.getEndTime());
			c.add(Calendar.DAY_OF_YEAR, 1);
			info.setEndTime(c.getTime());
			
			//9.0 删除老的匹配不到的
			ImportUserInfoExample ex = new ImportUserInfoExample();
			ex.createCriteria().andChePaiLike(info.getChePai()+"_no%");
			importUserInfoMapper.deleteByExample(ex);
			
			//10.0 判断用户保单是否重复导入
			ex.clear();
			ex.createCriteria().andBaoDanEqualTo(info.getBaoDan());
			ex.setLimitStart(0);
			ex.setLimitEnd(1);
			List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(ex);
			if(!importUserInfos.isEmpty()){
				Log4jHelper.getLogger().info("导入用户保单信息失败: 原因：该保单已存在，" + JacksonHelper.toPrettyJSON(info));
				throw new BusinessException("导入失败,该保单已重复导入:"+info.getBaoDan());
			}

			
			//11.0 判断是否存在老保单
			ex.clear();
			ex.createCriteria().andChePaiEqualTo(info.getChePai());
			ex.setLimitStart(0);
			ex.setLimitEnd(1);
			List<ImportUserInfo> userInfos = importUserInfoMapper.selectByExample(ex);
			//11.1若存在老保单
			if(userInfos.size()>0){
				ImportUserInfo userInfo = userInfos.get(0);
				//12.11 保证车辆匹配数据一致
				info.setAutoBrand(userInfo.getAutoBrand());
				info.setAutoLine(userInfo.getAutoLine());
				info.setProductYear(userInfo.getProductYear());
				info.setEngineDisp(userInfo.getEngineDisp());
				//12.12 若老保单已经过期，老保单即可标记车牌为 京N2356_oldyear_20170806
				if(userInfo.getEndTime().getTime() <= new Date().getTime()){
					String oldTimeStr = new SimpleDateFormat("yyyyMMdd").format(userInfo.getCreateTime());
					userInfo.setChePai(userInfo.getChePai()+"_oldyear_"+oldTimeStr);
					importUserInfoMapper.updateByPrimaryKeySelective(userInfo);
				//12.13 若老保单未过期,此时会有新老保单会同时保留
				}else{
					info.setChePai(info.getChePai()+"_newyear");//表示是新的一年的车牌
					ImportUserInfoExample infoEx = new ImportUserInfoExample();
					infoEx.createCriteria().andChePaiEqualTo(info.getChePai());
					if(importUserInfoMapper.countByExample(infoEx)>0){
						throw new BusinessException("导入失败,新车已经存在，请不要多次导入！"+info.getChePai());
					}
				}
			//11.2若不存在老保单
			}else{
				AutoInfo  autoInfo = canMatchAuto(info);
				if(autoInfo==null){
					//有可能是车的品牌信息有错误，需要去autopose表校验车辆品牌信息
					AutoposeExample autoposeExample = new AutoposeExample();
					autoposeExample.createCriteria().andFactorynameEqualTo(info.getAutoBrand());
					autoposeExample.setLimitStart(0);
					autoposeExample.setLimitEnd(1);
					List<Autopose> autopose = autoposeMapper.selectByExample(autoposeExample);
					if(autopose.isEmpty()){//autopose表也查询不到这时候肯定是匹配不到
						info.setChePai(info.getChePai()+"_nocar");//标志这个车牌 匹配不到车型
					}else{
						info.setAutoBrand(autopose.get(0).getBrandname());
						autoInfo = canMatchAuto(info);//修正完品牌之后需要再次匹配车辆信息
						if(autoInfo==null){
							info.setChePai(info.getChePai()+"_nocar");//标志这个车牌 匹配不到车型
						}
					}
				}
				if(info.getChePai()!=null&&info.getChePai().indexOf("_nocar")==-1){
					if(matchJilvProductNew(autoInfo).getList().isEmpty()){
						info.setChePai(info.getChePai()+"_nojilv");//标志这个车牌 匹配不到产品
					}
				}
			}
			//12.0 新增用户保单信息
			if(importUserInfoMapper.insertSelective(info)==1){
				successCount.incrementAndGet();
				Log4jHelper.getLogger().info("新增用户保单信息成功: " + info.getChePai());
			}else{
				Log4jHelper.getLogger().info("新增用户保单信息失败: " + JacksonHelper.toPrettyJSON(info));
			}
		});
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success", successCount.intValue());
		result.put("fail", params.size()-successCount.intValue());
		return DatasetBuilder.fromDataSimple(result);
	}
	
	
	private Map<String,Integer> addTimes(ImportUserInfo info){
		Map<String, Integer> timesMap = new HashMap<String,Integer>();
		timesMap.put("baoyang", 0);
		timesMap.put("penqi", 0);
		timesMap.put("xiche", 0);
		timesMap.put("dianping", 0);
		timesMap.put("juan20", 0);
		timesMap.put("juan50", 0);
		
		//根据规则查询小保养,喷漆,洗车,电瓶 次数
		ScoreChannelExample scoreChannelEx = new ScoreChannelExample();
		scoreChannelEx.createCriteria()
		//城市名称
		.andCityNameEqualTo(info.getAddress())
		//渠道名称
		.andChannelEqualTo(info.getSource())
		//取值范围
		.andMinimumLessThanOrEqualTo(info.getOrderPrice())
		.andMaximumGreaterThan(info.getOrderPrice())
		//规则类型
		.andRuleTypeEqualTo(ScoreChannel.RULE_TYPE_0);
		scoreChannelEx.setLimitStart(0);
		scoreChannelEx.setLimitEnd(1);
		List<ScoreChannel>  scoreChannels= scoreChannelMapper.selectByExample(scoreChannelEx);
		if(!scoreChannels.isEmpty()){
			String ruleValue = scoreChannels.get(0).getRuleValue();
			String[] times = ruleValue.split(",");
			timesMap.put("penqi", Integer.valueOf(times[1]));
			timesMap.put("xiche", Integer.valueOf(times[2]));
			timesMap.put("dianping", Integer.valueOf(times[3]));
			if(times.length==6){
				timesMap.put("juan20", Integer.valueOf(times[4]));
				timesMap.put("juan50", Integer.valueOf(times[5]));
			}
			if(StringUtils.isNotEmpty(scoreChannels.get(0).getCarType())){
				String carType = scoreChannels.get(0).getCarType();
				String[] carTypes = carType.split(",");
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(info.getAutoBrand())
				.append("-").append(info.getCarFactory());
				if("全部".equals(carType)||Arrays.asList(carTypes).contains(stringBuffer.toString())){
					timesMap.put("baoyang",Integer.valueOf(times[0]));
				}
				if("全部".equals(carType)){
					timesMap.put("isCheckFactory", 0);
				}
			}
			
		}else{
			throw new BusinessException("匹配不到渠道！车牌号为：" + info.getChePai()+"，城市为："+info.getAddress()+"渠道为："+info.getSource());
		}
		
		//下面代码用来设置用户匹配了那种机油
		scoreChannelEx.clear();
		scoreChannelEx.createCriteria()
		.andRuleTypeEqualTo(ScoreChannel.RULE_TYPE_1)
		.andCityNameEqualTo(info.getAddress())
		.andChannelEqualTo(info.getSource())
		.andMinimumLessThanOrEqualTo(info.getOrderPrice())
		.andMaximumGreaterThan(info.getOrderPrice());
		scoreChannels = scoreChannelMapper.selectByExample(scoreChannelEx);
		if(!scoreChannels.isEmpty() && StringUtils.isNotEmpty(scoreChannels.get(0).getRuleValue()) && scoreChannels.get(0).getRuleValue().matches("\\d+")){
			info.setServiceValue(Short.parseShort(scoreChannels.get(0).getRuleValue()));
		}
		//下面代码关于洗车次数的修改
		//查询该城市的洗车是宽途还是车服
		CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
		carwashConfigEx.createCriteria()
		.andCityNameEqualTo(info.getAddress());
		List<CarwashConfig> carwashConfigs = carwashConfigMapper.selectByExample(carwashConfigEx);
		//若洗车渠道规则不为空,且是车服洗车
		if(!carwashConfigs.isEmpty()&&CarwashConfig.WASH_TYPE_2.equals(carwashConfigs.get(0).getWashType())){
			//循环洗车渠道规则,筛选匹配的洗车渠道规则
			int count =0;
			for(CarwashConfig carwashConfig :carwashConfigs){
				//若存在匹配的洗车渠道规则,重新赋值洗车次数
				if(carwashConfig.getMinimum().compareTo(info.getOrderPrice())<=0&&carwashConfig.getMaximum().compareTo(info.getOrderPrice())>0){
					timesMap.put("xiche", carwashConfig.getCarTimes());
					break;
				}
				count++;
			}
			//若不存在洗车渠道配置,就报异常~
			if(carwashConfigs.size()==count){
				throw new BusinessException("该城市配置了车服洗车渠道,但保单金额没有满足的~");
			}
		}
		if(timesMap.get("isCheckFactory")==null || timesMap.get("isCheckFactory")!=0){
			if(StringUtils.isEmpty(info.getCarFactory())){
				throw new BusinessException("导入失败,该城市设置了保单匹配规则，但是该保单没有填写汽车厂商！");
			}
		}
		return timesMap;
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
	
	private DatasetList<Map<String, Object>> matchJilvProductNew(AutoInfo autoInfo) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		Long autoId = autoInfo.getId();
		AutoPartRelationExample relationEx = new AutoPartRelationExample();
		relationEx.createCriteria().andAutoIdEqualTo(autoId).andPartTypeIdEqualTo(2);
		List<AutoPartRelation> re = autoPartRelationMapper.selectByExample(relationEx);
		if (re.size() <= 0) {
			return DatasetBuilder.fromDataList(results);
		}
		AutoPartRelation r = re.get(0);
		if (StringUtils.isEmpty(r.getMatchId())) {
			return DatasetBuilder.fromDataList(results);
		}
		String[] ids = r.getMatchId().split(",");
		if(ids.length<=0){
			return DatasetBuilder.fromDataList(results);
		}
		List<Long> longIds = new ArrayList<Long>();
		longIds.add(0L);
		Arrays.asList(ids).stream().forEach(id -> {
			longIds.add(Long.parseLong(id));
		});
		AutoPartsExample partsExample = new AutoPartsExample();
		partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("曼牌（MANNFILTER）");
		partsExample.setLimitStart(0);
		partsExample.setLimitEnd(1);
		List<AutoParts> autoPartsList = autoPartsMapper.selectByExample(partsExample);
		if(autoPartsList.isEmpty()){
			partsExample.clear();
			partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("马勒（MAHLE）");
			autoPartsList = autoPartsMapper.selectByExample(partsExample);
		}
		if(autoPartsList.isEmpty()){
			partsExample.clear();
			partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("博世（BOSCH）");
			autoPartsList = autoPartsMapper.selectByExample(partsExample);
		}
		if(autoPartsList.isEmpty()){
			partsExample.clear();
			partsExample.createCriteria().andIdIn(longIds);
			autoPartsList = autoPartsMapper.selectByExample(partsExample);
		}
		if(autoPartsList.isEmpty()){
			return DatasetBuilder.fromDataList(results);
		}
		Map<String, Object> one = new HashMap<String, Object>();
		one.put("product", autoPartsList.get(0));
		results.add(one);
		return DatasetBuilder.fromDataList(results);
	}

	@Override
	@Read
	public DatasetSimple<Map<String, Object>> h5SelerDetail(Long id) {
		Seller seller = sellerMapper.selectByPrimaryKey(id);
		if(!Objects.nonNull(seller)){
			throw new BusinessException("找不到商家！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("addressDetail", seller.getAddressDetail());
		result.put("area", seller.getArea());
		result.put("city", seller.getCity());
		result.put("province", seller.getProvince());
		result.put("sellerName", seller.getSellerName());
		result.put("openTime", seller.getOpenTime());
		result.put("sellerPhone", seller.getSellerPhone());
		result.put("servicePrice", seller.getServicePrice());
		result.put("properties", seller.getProperties());
		result.put("score", seller.getScoreService());
		result.put("completOrder", seller.getOrderCountOver());
		result.put("sellerGrade", seller.getSellerGrade());
		result.put("sellerPosition", seller.getSellerPostion());
		
		
		SellerInfoExample sellerInfoEx = new SellerInfoExample();
		sellerInfoEx.createCriteria().andSellerIdEqualTo(id);
		sellerInfoEx.setLimitStart(0);
		sellerInfoEx.setLimitEnd(1);
		List<SellerInfo> sellerInfos = sellerInfoMapper.selectByExample(sellerInfoEx);
		if(!sellerInfos.isEmpty()){
			result.put("shopPic", sellerInfos.get(0).getShopPic());
			result.put("shopService", sellerInfos.get(0).getService());
		}
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	public DatasetList<Map<String, Object>> adminSellerReportList(Integer pageNo, Integer pageSize, String date,
			String sellerName) {
		//设置sellerReport的筛选条件
		SellerReportExample erx = new SellerReportExample();
		com.emate.shop.business.model.SellerReportExample.Criteria c = erx.createCriteria();
		if(StringUtils.isNotEmpty(sellerName)){
			//设置seller的筛选条件并查询
			SellerExample ex = new SellerExample();
			Criteria criteria = ex.createCriteria();
			criteria.andSellerNameLike("%"+sellerName+"%");
			List<Seller> sellers = sellerMapper.selectByExample(ex);
			List<Long> sellerIds = sellers.stream().map(Seller::getId).distinct().collect(Collectors.toList());
			sellerIds.add(0L);
			c.andSellerIdIn(sellerIds);
		}
		if(date!=null && !"".equals(date)){
			int year = Integer.parseInt(date.split("-")[0]);
			c.andYearEqualTo(year);
			int month = Integer.parseInt(date.split("-")[1]);
			c.andMonthEqualTo(month);
		}
		//根据sellerReport的筛选条件查询sellerReport
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, sellerReportMapper.countByExample(erx));
		erx.setLimitStart(page.getStartRow());
		erx.setLimitEnd(page.getSize());
		List<SellerReport> sellerReports = sellerReportMapper.selectByExample(erx);
		//组织返回结果
		Map<Long,Seller> sellerMap = new HashMap<Long,Seller>();
		Map<Long,SellerInfo> sellerInfoMap = new HashMap<Long,SellerInfo>();
		List<Map<String,Object>> orderReportList = new ArrayList<Map<String,Object>>();
		if(!sellerReports.isEmpty()){
		    List<Long> sellerIds = sellerReports.stream().map(SellerReport::getSellerId).distinct().collect(Collectors.toList());
		    sellerIds.add(0L);
		    SellerExample sex = new SellerExample();
		    sex.createCriteria().andIdIn(sellerIds);
		    List<Seller> selectByExample = sellerMapper.selectByExample(sex);
		    selectByExample.stream().forEach(seller -> {
		        sellerMap.put(seller.getId(), seller);
		    });
		    SellerInfoExample sellerInfoExample = new SellerInfoExample();
		    sellerInfoExample.createCriteria().andSellerIdIn(sellerIds);
		    List<SellerInfo> sellerInfos = sellerInfoMapper.selectByExample(sellerInfoExample);
		    sellerInfos.stream().forEach(sellerInfo -> {
		    	sellerInfoMap.put(sellerInfo.getSellerId(), sellerInfo);
		    });
		    sellerReports.stream().forEach(sellerReport ->{
		    	Map<String,Object> one = new HashMap<String,Object>();
		    	one.put("id", sellerReport.getId());
		    	one.put("sellerId", sellerReport.getSellerId());
		    	one.put("year", sellerReport.getYear());
		    	one.put("month", sellerReport.getMonth());
		    	one.put("num", sellerReport.getOrderCount());
		    	one.put("totalMoney",sellerReport.getTotalMoney());
		    	one.put("status", sellerReport.getStatus());
		    	one.put("invoice", sellerReport.getInvoice());
		    	if(sellerMap.get(sellerReport.getSellerId())!=null){
		    		one.put("sellerName", sellerMap.get(sellerReport.getSellerId()).getSellerName()==null?"":
		    			sellerMap.get(sellerReport.getSellerId()).getSellerName());
		    		one.put("companyName", sellerMap.get(sellerReport.getSellerId()).getCompanyName()==null?"":
		    			sellerMap.get(sellerReport.getSellerId()).getCompanyName());
		    		one.put("accountName", sellerInfoMap.get(sellerReport.getSellerId()).getAccountName()==null?"":
		    			sellerInfoMap.get(sellerReport.getSellerId()).getAccountName());
		    		one.put("account", sellerInfoMap.get(sellerReport.getSellerId()).getAccount()==null?"":
		    			sellerInfoMap.get(sellerReport.getSellerId()).getAccount());
		    		one.put("accountNumber", sellerInfoMap.get(sellerReport.getSellerId()).getAccountNumber()==null?"":
		    			sellerInfoMap.get(sellerReport.getSellerId()).getAccountNumber());
		    	}
		    orderReportList.add(one);
		    });
		}
		return DatasetBuilder.fromDataList(orderReportList, page.createPageInfo());
	}

	@Override
	public DatasetSimple<Boolean> updateSellerReport(SellerReport sellerReport) {
		if(sellerReportMapper.updateByPrimaryKeySelective(sellerReport)==1){
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("更新商家结算信息失败！");
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> sendService(List<Map<String, Object>> chePais, Integer serviceType,
			Integer serviceValue, Long userId) {
		if(chePais.isEmpty()){
			throw new BusinessException("车牌信息为空~");
		}
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(userId);
		for(Map<String, Object> chePai:chePais){
			//添加修改日志
			ServiceOperatorLog serviceOperatorLog = new ServiceOperatorLog();
			//创建和更新时间
			serviceOperatorLog.setCreateTime(new Date());
			serviceOperatorLog.setUpdateTime(new Date());
			//车牌号
			serviceOperatorLog.setChePai(String.valueOf(chePai.get("che_pai")));
			//服务类型
			serviceOperatorLog.setServiceType(Integer.valueOf(serviceType));
			//服务次数
			serviceOperatorLog.setServiceValue(serviceValue);
			//操作人姓名
			serviceOperatorLog.setOperator(systemUser.getUserName());
			//IP地址
			serviceOperatorLog.setIpAddress(systemUser.getLastLoginIp());
			
			//待审核状态
			serviceOperatorLog.setVerifyStatus(ServiceOperatorLog.VERIFY_STATUS_0);
			//备注
			if(chePai.get("remark")!=null){
				serviceOperatorLog.setRemark(String.valueOf(chePai.get("remark")));
			}
			
			if(serviceOperatorLogMapper.insertSelective(serviceOperatorLog)!=1){
				throw new BusinessException("更新服务次数记录异常~~");
			};
		}
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success", true);
		return DatasetBuilder.fromDataSimple(result);
	}
}
