package com.emate.shop.business.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.OrderService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.AllTypeExample;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.AutoInfoExample;
import com.emate.shop.business.model.AutoPartRelation;
import com.emate.shop.business.model.AutoPartRelationExample;
import com.emate.shop.business.model.AutoParts;
import com.emate.shop.business.model.AutoPartsExample;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.business.model.AutoposeExample;
import com.emate.shop.business.model.BusinessInfo;
import com.emate.shop.business.model.BusinessInfoExample;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.CarWashShengExample;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.business.model.CarwashConfigExample;
import com.emate.shop.business.model.EngineOil;
import com.emate.shop.business.model.EngineOilExample;
import com.emate.shop.business.model.Express;
import com.emate.shop.business.model.ExpressExample;
import com.emate.shop.business.model.FixOrder;
import com.emate.shop.business.model.FixOrderExample;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.business.model.JilvConfig;
import com.emate.shop.business.model.JilvConfigExample;
import com.emate.shop.business.model.JilvInfo;
import com.emate.shop.business.model.JilvInfoExample;
import com.emate.shop.business.model.NewOrderAlert;
import com.emate.shop.business.model.NewOrderAlertExample;
import com.emate.shop.business.model.OrderComment;
import com.emate.shop.business.model.OrderCommentExample;
import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.model.OrderExpressExample;
import com.emate.shop.business.model.OrderTrace;
import com.emate.shop.business.model.OrderTraceExample;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.OrdersExample;
import com.emate.shop.business.model.OrdersExample.Criteria;
import com.emate.shop.business.model.PowerPrice;
import com.emate.shop.business.model.PowerPriceExample;
import com.emate.shop.business.model.Regions;
import com.emate.shop.business.model.ScoreChannel;
import com.emate.shop.business.model.ScoreChannelExample;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerExample;
import com.emate.shop.business.model.SellerInfo;
import com.emate.shop.business.model.SellerInfoExample;
import com.emate.shop.business.model.SellerPolicy;
import com.emate.shop.business.model.SellerReport;
import com.emate.shop.business.model.SellerReportExample;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemRoleExample;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.SystemUserRoleRelation;
import com.emate.shop.business.model.SystemUserRoleRelationExample;
import com.emate.shop.business.model.TyreInfo;
import com.emate.shop.business.model.TyreInfoExample;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserExample;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.business.model.UserInfoExample;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AdminLogMapper;
import com.emate.shop.mapper.AllTypeMapper;
import com.emate.shop.mapper.AutoInfoMapper;
import com.emate.shop.mapper.AutoPartRelationMapper;
import com.emate.shop.mapper.AutoPartsMapper;
import com.emate.shop.mapper.AutoposeMapper;
import com.emate.shop.mapper.BusinessInfoMapper;
import com.emate.shop.mapper.CarWashShengMapper;
import com.emate.shop.mapper.CarwashConfigMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.EngineOilMapper;
import com.emate.shop.mapper.ExpressMapper;
import com.emate.shop.mapper.FixOrderMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.mapper.JilvConfigMapper;
import com.emate.shop.mapper.JilvInfoMapper;
import com.emate.shop.mapper.NewOrderAlertMapper;
import com.emate.shop.mapper.OrderCommentMapper;
import com.emate.shop.mapper.OrderExpressMapper;
import com.emate.shop.mapper.OrderTraceMapper;
import com.emate.shop.mapper.OrdersMapper;
import com.emate.shop.mapper.PowerPriceMapper;
import com.emate.shop.mapper.RegionsMapper;
import com.emate.shop.mapper.ScoreChannelMapper;
import com.emate.shop.mapper.SellerInfoMapper;
import com.emate.shop.mapper.SellerMapper;
import com.emate.shop.mapper.SellerReportMapper;
import com.emate.shop.mapper.SystemRoleMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.mapper.SystemUserRoleRelationMapper;
import com.emate.shop.mapper.TyreInfoMapper;
import com.emate.shop.mapper.UserInfoMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.mapper.WxUserBindMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.PinyinTool;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private RegionsMapper regionsMapper;

	@Resource
	private AutoposeMapper autoposeMapper;

	@Resource
	private WxUserBindMapper wxUserBindMapper;

	@Resource
	private SellerMapper sellerMapper;

	@Resource
	private PowerPriceMapper powerPriceMapper;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private TyreInfoMapper tyreInfoMapper;
	
	@Resource
	private EngineOilMapper engineOilMapper;
	
	@Resource
	private AllTypeMapper allTypeMapper;
	
	@Resource
	private JilvInfoMapper jilvInfoMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private AutoInfoMapper autoInfoMapper;
	
	@Resource
	private AutoPartRelationMapper autoPartRelationMapper;
	
	@Resource
	private AutoPartsMapper autoPartsMapper;
	
	@Resource
	private SellerReportMapper sellerReportMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;
	
	@Resource
	private SystemUserRoleRelationMapper systemUserRoleRelationMapper;
	
	@Resource
	private SystemRoleMapper systemRoleMapper;
	
	@Resource
	private NewOrderAlertMapper newOrderAlertMapper;
	
	@Resource
	private OrderTraceMapper orderTraceMapper;
	
	@Resource
	private FixOrderMapper fixOrderMapper;
	
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	
	@Resource
	private ExpressMapper expressMapper;
	
	@Resource
	private OrderExpressMapper orderExpressMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private ScoreChannelMapper scoreChannelMapper;
	
	@Resource
	private CarwashConfigMapper carwashConfigMapper;
	
	@Resource
	private SellerInfoMapper sellerInfoMapper;
	
	@Resource
	private AdminLogMapper adminLogMapper;
	
	@Resource
	private OrderCommentMapper orderCommentMapper;
	
	@Resource
	private JilvConfigMapper jilvConfigMapper;
	
	@Resource
	private CarWashShengMapper carWashShengMapper;
	/**
	 * 用户提交订单
	 */
	@Write
	@Override
	@Transactional
	public  DatasetSimple<String> addOrder(Orders order) {
		if (order != null) {
			// 设置区域中文
			if (!StringUtils.isEmpty(order.getProvince())) {
				String provinceId = order.getProvince();
				String cityId = order.getCity();
				String area = order.getArea();
				Regions r = regionsMapper.selectByPrimaryKey(Integer.parseInt(provinceId));
				if (Objects.nonNull(r)) {
					order.setProvince(r.getRegionname());
				}
				r = regionsMapper.selectByPrimaryKey(Integer.parseInt(cityId));
				if (Objects.nonNull(r)) {
					order.setCity(r.getRegionname());
				}
				r = regionsMapper.selectByPrimaryKey(Integer.parseInt(area));
				if (Objects.nonNull(r)) {
					order.setArea(r.getRegionname());
				}
			}
			//电瓶 没有工时费
			order.setServiceMoney(new BigDecimal(0));
			//电瓶没有机滤价格
			order.setJilvPrice(new BigDecimal(0));
			//如果是轮胎 和小保养，设置工时费;
			Seller seller = null;
			if(Objects.nonNull(order.getSellerId())){
				seller = sellerMapper.selectByPrimaryKey(order.getSellerId());
				if(seller == null){
					throw new BusinessException("下单商家不存在！");
				}
				//轮胎的工时费 每条轮胎25
				if(order.getOrderType()==Orders.ORDER_TYPE_1){
					order.setServiceMoney(new BigDecimal(25));
				}
				//小保养的工时费按照不同店铺不一样
				else if(order.getOrderType()==Orders.ORDER_TYPE_2){
					order.setServiceMoney(seller.getServicePrice());
				}
//				else if(order.getOrderType()==Orders.ORDER_TYPE_3){
//					order.setServiceMoney(new BigDecimal(30));
//				}
				
				order.setAddressDetail(seller.getAddressDetail());
				order.setProvince(seller.getProvince());
				order.setCity(seller.getCity());
				order.setArea(seller.getArea());
				order.setPhone(seller.getSellerPhone());
				order.setUserName(seller.getName());
			}
			
			//获取车辆购置价格
			UserInfoExample userEx = new UserInfoExample();
			userEx.createCriteria().andUserIdEqualTo(order.getUserId());
			userEx.setLimitStart(0);
			userEx.setLimitEnd(1);
			List<UserInfo> userInfoList = userInfoMapper.selectByExample(userEx);
			if(userInfoList.isEmpty()){
				throw new BusinessException("创建订单失败,用户信息为空！");
			}
			ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
			importUserInfoEx.createCriteria().andChePaiEqualTo(userInfoList.get(0).getLicense());
			importUserInfoEx.setLimitStart(0);
			importUserInfoEx.setLimitEnd(1);
			List<ImportUserInfo>  importList = importUserInfoMapper.selectByExample(importUserInfoEx);
			if(importList.isEmpty()){
				throw new BusinessException("创建订单失败,导入用户信息为空！");
			}
			ImportUserInfo info = importList.get(0);
			order.setCarPrice(info.getPrice());
			//计算订单总价
			if(order.getOrderType() == Orders.ORDER_TYPE_0){//电瓶
				if(info.getDianpingTimes()<=0){
					info = getNewInfo(info.getChePai());
					if(info == null || info.getBaoyangTimes()<=0){ //没有新的保单导入 或者 新保单次数不足
						throw new BusinessException("创建订单失败,用户保养次数不足！");
					}
				}
				PowerPriceExample powerEx = new PowerPriceExample();
				powerEx.createCriteria().andProductNameEqualTo(order.getOrderRemark());
				List<PowerPrice> plist = powerPriceMapper.selectByExample(powerEx);
				if(!plist.isEmpty()){
					order.setMoneyAmount(plist.get(0).getProductPrice());
				}
				info.setDianpingTimes(info.getDianpingTimes()-1);
				order.setSellerId(-1L); //电瓶设置临时代理
			}else if(order.getOrderType() == Orders.ORDER_TYPE_1){//轮胎
				order.setMoneyAmount(new BigDecimal(500));
				if(info.getLuntaiTimes()<=0){
					info = getNewInfo(info.getChePai());
					if(info == null || info.getLuntaiTimes()<=0){ //没有新的保单导入 或者 新保单次数不足
						throw new BusinessException("创建订单失败,用户保养次数不足！");
					}
				}
				info.setLuntaiTimes(info.getLuntaiTimes()-1);
			}else if(order.getOrderType() == Orders.ORDER_TYPE_2){//小保养
				if(info.getBaoyangTimes()<=0){
					info = getNewInfo(info.getChePai());
					if(info == null || info.getBaoyangTimes()<=0){ //没有新的保单导入 或者 新保单次数不足
						throw new BusinessException("创建订单失败,用户保养次数不足！");
					}
				}
				//这里需要后台判断用户区域，禁止用户跨城市保养
				if(StringUtils.isNotEmpty(info.getAddress()) && !Objects.equals(info.getAddress(), order.getCity())){
					throw new BusinessException("创建订单失败,用户只能在保单地区保养！");
				}
				info.setBaoyangTimes(info.getBaoyangTimes()-1);
				/*
				 * 
				 * 	var priceList = [
						{minPrice:0,maxPrice:200000,totalAmount:600, oilText:"机油 10W-40 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)"},
						{minPrice:200000,maxPrice:300000,totalAmount:1000,oilText:"机油 5W-40 (嘉实多 新磁护)",filterText:"机滤 (曼牌、马勒、博世)"},
						{minPrice:300000,maxPrice:30000000,totalAmount:1500,oilText:"机油 5W-40 (嘉实多 极护)",filterText:"机滤 (曼牌、马勒、博世)"}
					];
				 * */
				//这里处理绑定4s店数据
				if("比亚迪广州饰和销售服务店".equals(order.getOrderRemark())){
					
					order.setMoneyAmount(new BigDecimal(200));
					order.setServiceMoney(new BigDecimal(0));
					order.setOrderRemark("比亚迪专用机油_1_160,比亚迪专用机油滤清器_1_40");
				}else if("宝马".equals(order.getOrderRemark())){
					order.setMoneyAmount(new BigDecimal(600));
					order.setServiceMoney(new BigDecimal(0));
					order.setOrderRemark("宝马迪专用机油_1_500,宝马专用机油滤清器_1_100");
				}else{//下面代码计算小保养的商品价格
					int size4 = 0;
					int size1 = 0;
					int size4Money = 0;
					int size1Money = 0;
					int totalMoney = 0;
					String remarkGoods = null;
					AutoInfo autoInfo = findAutoInfo(order.getUserId());
					if(autoInfo==null){
						throw new BusinessException("创建订单失败,无法匹配用户车辆信息！");
					}
					int oilAmount = autoInfo.getOilAmount()==null?0:autoInfo.getOilAmount().intValue();
					if(oilAmount==0){
						throw new BusinessException("创建订单失败,机油数量为0！");
					}
					if(oilAmount>=4){
						size4 = oilAmount / 4;
						size1 = oilAmount % 4;
					}else{
						size1 = oilAmount;
					}
					int carPrice = order.getCarPrice().intValue();
					
					//优先级最高，判断导入表里面是否存在指定机油等级
					//如果存在，需要优先根据配置好的机油等级来判断用户需要哪些机油
					if(info.getServiceValue()!=null && 
							info.getServiceValue().intValue()!=0){
						int level = info.getServiceValue().intValue();
						if(level == 1){
							size4Money = size4 * 121;
							size1Money = size1 * 33;
							remarkGoods = "嘉实多 金嘉护超净 10W-40";
						}else if(level == 2){
							size4Money = size4 * 249;
							size1Money = size1 * 66;
							remarkGoods = "嘉实多 新磁护 5W-40";
						}else if(level == 3){
							size4Money = size4 * 281;
							size1Money = size1 * 74;
							remarkGoods = "嘉实多 极护 5W-40";
						}
					}else{
						//优先级第二高,若存在配置好的机油等级,按机油等级赠送机油
						List<ScoreChannel> scoreChannels = new ArrayList<ScoreChannel>();
						if(!Objects.isNull(info.getSource())){
							ScoreChannelExample scoreChannelEx = new ScoreChannelExample();
							scoreChannelEx.createCriteria()
							//设置规则类型
							.andRuleTypeEqualTo(ScoreChannel.RULE_TYPE_1)
							//设置渠道
							.andChannelEqualTo(info.getSource())
							//设置城市
							.andCityNameEqualTo(info.getAddress())
							//设置取值范围
							.andMinimumLessThanOrEqualTo(info.getOrderPrice())
							.andMaximumGreaterThan(info.getOrderPrice());
							
							scoreChannels = scoreChannelMapper.selectByExample(scoreChannelEx);
						}
						if(!scoreChannels.isEmpty()){
							if(scoreChannels.size()>1){
								throw new BusinessException("机油匹配有误,请联系客服~~");
							}
							String ruleValue = scoreChannels.get(0).getRuleValue();
							if("1".equals(ruleValue)){
								size4Money = size4 * 121;
								size1Money = size1 * 33;
								remarkGoods = "嘉实多 金嘉护超净 10W-40";
							}else if("2".equals(ruleValue)){
								size4Money = size4 * 249;
								size1Money = size1 * 66;
								remarkGoods = "嘉实多 新磁护 5W-40";
							}else if("3".equals(ruleValue)){
								size4Money = size4 * 281;
								size1Money = size1 * 74;
								remarkGoods = "嘉实多 极护 5W-40";
							}
						//最后,若再导入保单的时候没有配置,并且后台没有配置,
						//按照车辆的价格定机油等级
						}else{
							if(carPrice<200000){
								size4Money = size4 * 121;
								size1Money = size1 * 33;
								remarkGoods = "嘉实多 金嘉护超净 10W-40";
							}else if(carPrice>=200000 && carPrice<300000){
								size4Money = size4 * 249;
								size1Money = size1 * 66;
								remarkGoods = "嘉实多 新磁护 5W-40";
							}else if(carPrice>=300000){
								size4Money = size4 * 281;
								size1Money = size1 * 74;
								remarkGoods = "嘉实多 极护 5W-40";
							}
						}
					}
					
					totalMoney += size4Money;
					totalMoney += size1Money;
					totalMoney += 35;//加上机滤的价格
					totalMoney += 15;//加上运费
					order.setMoneyAmount(new BigDecimal(totalMoney));
					
					JilvConfigExample jilvConfigEx = new JilvConfigExample();
					jilvConfigEx.createCriteria()
					.andCityNameEqualTo(info.getAddress())
					.andMinLessThanOrEqualTo(info.getPrice())
					.andMaxGreaterThan(info.getPrice());
					List<JilvConfig> jilvConfigs = jilvConfigMapper.selectByExample(jilvConfigEx);
					//机滤价格,优先根据配置来
					if(!jilvConfigs.isEmpty()){
						order.setJilvPrice(jilvConfigs.get(0).getJilvPrice());
					//若配置不存在,根据车辆价格算机滤价格
					}else{
						if(carPrice<250000){
							order.setJilvPrice(new BigDecimal(25));
						}else if(carPrice>=250000 && carPrice<400000){
							order.setJilvPrice(new BigDecimal(30));
						}else if(carPrice>=400000){
							order.setJilvPrice(new BigDecimal(60));
						}
					}
					order.setOrderRemark(replacePrice(order.getOrderRemark(),remarkGoods,size4Money,size1Money,35));
				}
				if(seller!=null && StringUtils.isNotEmpty(seller.getSellerPolicy())){
					try{//下面特殊处理策略,出现异常不处理
						SellerPolicy sellerPolicy = JacksonHelper.fromJSON(seller.getSellerPolicy(), SellerPolicy.class);
						if(sellerPolicy.getBaoyangMoney()!=null && sellerPolicy.getBaoyangMoney().intValue()!=0){
							order.setMoneyAmount(new BigDecimal(sellerPolicy.getBaoyangMoney()));
							order.setOrderRemark("4s店专用机油_1_"+(sellerPolicy.getBaoyangMoney()-35)+",4s店专用机滤_1_"+35);
							order.setJilvPrice(new BigDecimal(sellerPolicy.getBaoyangMoney()));
						}
						if(!sellerPolicy.getBaoyangCheck()){
							order.setStatus(Orders.STATUS_1);
							order.setSupplyId(order.getSellerId());
						}
					}catch(Exception e){
					}
				}
			}else if(order.getOrderType() == Orders.ORDER_TYPE_3){
				Calendar now = Calendar.getInstance(Locale.CHINA);
				now.set(Calendar.HOUR_OF_DAY, 0);
				now.set(Calendar.MINUTE, 0);
				now.set(Calendar.SECOND, 0);
				now.set(Calendar.MILLISECOND, 0);
				OrdersExample ordersEx = new OrdersExample();
				ordersEx.createCriteria()
				.andUserIdEqualTo(order.getUserId())
				.andOrderTypeEqualTo(Orders.ORDER_TYPE_3)
				.andStatusNotEqualTo(Orders.STATUS_4)
				.andCreateTimeGreaterThanOrEqualTo(now.getTime());
				int orderNub = ordersMapper.countByExample(ordersEx);
				if(orderNub>=1){
					throw new BusinessException("您今天已洗过,请明天再来~~");
				}
/*				if(info.getXiecheTimes()<=0){
					throw new BusinessException("创建订单失败,用户洗车次数不足！");
				}*/
				//根据条件查询洗车配置渠道
				CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
				carwashConfigEx.createCriteria()
				//保单地址
				.andCityNameEqualTo(info.getAddress())
				//洗车类型
				.andWashTypeEqualTo(CarwashConfig.WASH_TYPE_2);
/*				//保单价格
				.andMinimumLessThanOrEqualTo(info.getOrderPrice())
				//保单价格
				.andMaximumGreaterThan(info.getOrderPrice());
				carwashConfigEx.setLimitStart(0);
				carwashConfigEx.setLimitEnd(1);*/
				List<CarwashConfig> carwashConfigs = carwashConfigMapper.selectByExample(carwashConfigEx);
				if(carwashConfigs.isEmpty()){
					throw new BusinessException("未配置洗车配置渠道,请联系客服~");
				}
				int count = queryOrders(order.getUserId(),carwashConfigs.get(0).getCountType());//1代表每月2代表每年
/*				if(carwashConfigs.get(0).getCarTimes()<=count){
					throw new BusinessException("您的洗车次数不足~");
				}*/
				if(info.getXiecheTimes()<=count){
					info = getNewInfo(info.getChePai());
					if(info == null || info.getXiecheTimes()<=count){ //没有新的保单导入 或者 新保单次数不足
						throw new BusinessException("创建订单失败,用户保养次数不足！");
					}
				}
				//赋值采购价格(跟商家结算)
				order.setServiceMoney(carwashConfigs.get(0).getPurchasePrice());
				//赋值洗车价格(跟国寿结算)
				order.setMoneyAmount(carwashConfigs.get(0).getWashPrice());
				
				//order.setMoneyAmount(new BigDecimal(30));
				//info.setXiecheTimes(info.getXiecheTimes()-1);
			}else if(order.getOrderType() == Orders.ORDER_TYPE_4){
				if(info.getPenqiTimes()-order.getGoodsNum()<0){
					info = getNewInfo(info.getChePai());
					if(info == null || info.getPenqiTimes()-order.getGoodsNum()<0){ //没有新的保单导入 或者 新保单次数不足
						throw new BusinessException("创建订单失败,用户保养次数不足！");
					}
				}
				//这里需要后台判断用户区域，禁止用户跨城市喷漆
				if(StringUtils.isNotEmpty(info.getAddress()) && !Objects.equals(info.getAddress(), order.getCity())){
					throw new BusinessException("创建订单失败,用户只能在保单地区喷漆！");
				}
				order.setServiceMoney(new BigDecimal(0));
				order.setMoneyAmount(new BigDecimal(order.getGoodsNum()*300));
				if(seller!=null && StringUtils.isNotEmpty(seller.getSellerPolicy())){
					try{//下面特殊处理策略,出现异常不处理
						SellerPolicy sellerPolicy = JacksonHelper.fromJSON(seller.getSellerPolicy(), SellerPolicy.class);
						if(sellerPolicy.getPenqiMoney()!=null && sellerPolicy.getPenqiMoney().intValue()!=0){
							order.setMoneyAmount(new BigDecimal(order.getGoodsNum()*sellerPolicy.getPenqiMoney().intValue()));
						}
						if(!sellerPolicy.getPenqiCheck()){
							order.setStatus(Orders.STATUS_2);
							order.setSupplyId(order.getSellerId());
						}
					}catch(Exception e){
					}
				}
				info.setPenqiTimes(info.getPenqiTimes()-order.getGoodsNum());
			}

			if(importUserInfoMapper.updateByPrimaryKeySelective(info)!=1){
				throw new BusinessException("创建订单失败,更新用户服务次数失败！");
			}
			
			//order.setMoneyAmount(moneyAmount);
			
			/**
			 * 循环查询订单code唯一
			 */
			String randomCode = RandomUtil.randomNumber(8);
			OrdersExample orderEx = new OrdersExample();
			orderEx.createCriteria().andCodeEqualTo(randomCode);
			orderEx.setLimitStart(0);
			orderEx.setLimitEnd(1);
			while(ordersMapper.countByExample(orderEx)>0){
				randomCode = RandomUtil.randomNumber(8);
				orderEx.clear();
				orderEx.createCriteria().andCodeEqualTo(randomCode);
			}
			order.setCode(randomCode);
			//查找该用户是否存在未完成的订单
			orderEx.clear();
			List<Integer> notCompletOrder = new ArrayList<Integer>();
			notCompletOrder.add(Orders.STATUS_0);
			notCompletOrder.add(Orders.STATUS_1);
			notCompletOrder.add(Orders.STATUS_2);
			orderEx.createCriteria().andUserIdEqualTo(order.getUserId())
			.andStatusIn(notCompletOrder)
			.andOrderTypeEqualTo(order.getOrderType());
			if(ordersMapper.countByExample(orderEx)>0){
				throw new BusinessException("提交订单失败，同类型有未完成的订单！");
			}
			if (ordersMapper.insert(order) != 1) {
				throw new BusinessException("创建订单失败！");
			}
			
			//新订单提醒服务
			try{
				NewOrderAlert newOrder = new NewOrderAlert();
				newOrder.setUpdateTime(new Date());
				newOrderAlertMapper.insert(newOrder);
				
				//添加订单日志
				OrderTrace orderTrace = new OrderTrace();
				orderTrace.setOrderNo(order.getOrderNo());
				orderTrace.setNewTime(new Date());
				if(order.getStatus()==Orders.STATUS_1){
					orderTrace.setConfirmTime(new Date(System.currentTimeMillis()+1000*10));
				}
				if(order.getOrderType() == Orders.ORDER_TYPE_3){
					orderTrace.setConfirmTime(new Date(System.currentTimeMillis()+1000*10));
					orderTrace.setDeliverTime(new Date(System.currentTimeMillis()+1000*10));
				}
				orderTrace.setCreateTime(new Date());
				orderTrace.setUpdateTime(new Date());
				orderTraceMapper.insertSelective(orderTrace);
			}catch(Exception e){
				Log4jHelper.getLogger().error("添加订单提醒失败！");
			}
			return DatasetBuilder.fromDataSimple(order.getCode());
		}
		throw new BusinessException("创建订单失败，订单为空！");
	}
	
	/**
	 * 查找新的赠送次数
	 * @param chepai
	 * @return
	 */
	private ImportUserInfo getNewInfo(String chepai){
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		//判断是否有新保单
		importUserInfoEx.createCriteria().andChePaiEqualTo(chepai+"_newyear");
		importUserInfoEx.setLimitStart(0);
		importUserInfoEx.setLimitEnd(1);
		List<ImportUserInfo>  newimportList = importUserInfoMapper.selectByExample(importUserInfoEx);
		if(newimportList.size()>0){
			return newimportList.get(0);
		}
		return null;
	}
	/**
	 * 查询当月或当年的洗车订单量
	 * @param userId
	 * @param dateType
	 * @return
	 */
	private int queryOrders(Long userId,Integer dateType){
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
		if(CarwashConfig.COUNT_TYPE_1.equals(dateType)){//该用户每月的下单量
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
	public static String replacePrice(String oldRemak,String newReamrk,int size4Money,int size1Money,int jilvMoney) {
		//处理订单中goodsRemark机油价格以及机滤价格
		String[] allGoods = oldRemak.split(",");
		String[] allJiyou = allGoods[0].split("\\|");
		StringBuffer sb = new StringBuffer();
		for(String s:allJiyou){
			String[] jiyouDetail = s.split("_");
			if(s.indexOf("4L")!=-1){
				jiyouDetail[0] = newReamrk+" 4L";
				jiyouDetail[2] = String.valueOf(size4Money);
			}else if(s.indexOf("1L")!=-1){
				jiyouDetail[0] = newReamrk+" 1L";
				jiyouDetail[2] = String.valueOf(size1Money);
			}
			for(String s1 : jiyouDetail){
				sb.append(s1).append("_");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("|");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		//下面处理机滤价格
		String[] jilvDetail = allGoods[1].split("_");
		jilvDetail[2] = String.valueOf(jilvMoney);//修正机滤价格
		for(String s : jilvDetail){
			sb.append(s).append("_");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	@Read
	@Override
	public DatasetSimple<Orders> h5QueryOrderByNo(String orderNo) {
		OrdersExample ex = new OrdersExample();
		ex.createCriteria().andOrderNoEqualTo(orderNo);
		List<Orders> orders = ordersMapper.selectByExample(ex);
		if (orders.isEmpty()) {
			throw new BusinessException("订单不存在！");
		}
		DatasetSimple<Orders> result = DatasetBuilder.fromDataSimple(orders.get(0));
		String goodsId = orders.get(0).getGoodsId();
		if(StringUtils.isNotEmpty(goodsId) && orders.get(0).getOrderType() == Orders.ORDER_TYPE_2){
			try{
				String jiyouids = goodsId.split(",")[0];
				Long jiyouId = 0L;
				if (jiyouids.indexOf("|") >= 0) {
					jiyouId = Long.parseLong(jiyouids.split("\\|")[0]);
				} else {
					jiyouId = Long.parseLong(jiyouids);
				}

				AutoParts autoParts = autoPartsMapper.selectByPrimaryKey(jiyouId);
				if (Objects.nonNull(autoParts)) {
					result.putDatasetList("jiyou", Arrays.asList(autoParts.getPic()));
				}
				Long jilvId = Long.parseLong(goodsId.split(",")[1]);
				autoParts = autoPartsMapper.selectByPrimaryKey(jilvId);
				if (Objects.nonNull(autoParts)) {
					result.putDatasetList("jilv", Arrays.asList(autoParts.getPic()));
				}
			}catch(Exception e){
				Log4jHelper.getLogger().error("获取保养订单图片错误！");
			}
	
		}
		if(orders.get(0).getSellerId()!=null){
			Seller seller = sellerMapper.selectByPrimaryKey(orders.get(0).getSellerId());
			if(seller != null){
				result.putDatasetList("sellerPosition", Arrays.asList(seller.getSellerPostion()));
			}
		}
		return result;
	}

	@Read
	@Override
	public DatasetSimple<Map<String,Object>> adminQueryOrderDetail(String orderNo) {
		OrdersExample ex = new OrdersExample();
		ex.createCriteria().andOrderNoEqualTo(orderNo);
		ex.setLimitEnd(0);
		ex.setLimitEnd(1);
		List<Orders> orders = ordersMapper.selectByExample(ex);
		if (orders.isEmpty()) {
			return DatasetBuilder.fromMessageSimple("订单不存在！");
		}
		Orders order = orders.get(0);
		return DatasetBuilder.fromDataSimple(getOrderInfo(order));
	}
	
	
	//根据订单生成订单详情
	private Map<String,Object> getOrderInfo(Orders order){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("orderNo", order.getOrderNo());
		result.put("createTime", order.getCreateTime());
		result.put("product", order.getOrderRemark());
		result.put("orderType", order.getOrderType());
		result.put("status", order.getStatus());
		result.put("mileage", order.getMileage());
		result.put("carPrice", order.getCarPrice());
		result.put("goodsNum", order.getGoodsNum());
		result.put("jilvPrice", order.getJilvPrice());
		result.put("supplyId", order.getSupplyId());
		Long userId = order.getUserId();
		UserInfoExample userEx = new UserInfoExample();
		userEx.createCriteria().andUserIdEqualTo(userId);
		userEx.setLimitStart(0);
		userEx.setLimitEnd(1);
		List<UserInfo> users = userInfoMapper.selectByExample(userEx);
		if(!users.isEmpty()){
			String chepai = users.get(0).getLicense();
			ImportUserInfoExample importEx = new ImportUserInfoExample();
			importEx.createCriteria().andChePaiEqualTo(chepai);
			importEx.setLimitStart(0);
			importEx.setLimitEnd(1);
			List<ImportUserInfo> carList = importUserInfoMapper.selectByExample(importEx);
			if(!carList.isEmpty()){
				PinyinTool py = new PinyinTool();
				try {
					result.put("carlogo", py.toPinYin(carList.get(0).getAutoBrand(), "", PinyinTool.Type.LOWERCASE));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
				ImportUserInfo info = carList.get(0);
				if(info.getAutoType()==null){
					info.setAutoType("");
				}
				if(info.getEngineDisp()==null){
					info.setEngineDisp("");
				}
				if(info.getProductYear()==null){
					info.setProductYear("");
				}
				result.put("carinfo", carList.get(0));
			}
			
		}
		User u = userMapper.selectByPrimaryKey(userId);
		if(u != null){
			result.put("userName", u.getNickName());
			result.put("userPhone", u.getName());
		}
		return result;
	}

	/**
	 * 商家端根据订单code查询订单明细
	 */
	@Read
	@Override
	public DatasetSimple<Map<String,Object>> sellerQueryOrderDetail(String code) {
		OrdersExample ex = new OrdersExample();
		ex.createCriteria().andCodeEqualTo(code);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<Orders> orders = ordersMapper.selectByExample(ex);
		if (orders.isEmpty()) {
			return DatasetBuilder.fromMessageSimple("订单不存在！");
		}
		Orders order = orders.get(0);
		return DatasetBuilder.fromDataSimple(getOrderInfo(order));
	}
	/**
	 * 管理员查看订单列表
	 */
	/**
	 * @param orderNo
	 * @param phone
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Read
	@Override
	public DatasetList<Map<String,Object>> adminQueryOrderList(Long adminId,String orderNo,String phone,String license,Integer pageNo,Integer pageSize,
			String orderStatus, String startTimes ,String endTimes,String orderType) {
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(adminId);
		//订单查询条件
		OrdersExample orderex = new OrdersExample();
		Criteria c = orderex.createCriteria();
		if(StringUtils.isNotEmpty(systemUser.getAddress())){
			c.andCityEqualTo(systemUser.getAddress());
		}
		if(StringUtils.isNotEmpty(orderType)){//筛选订单类型
			if(!"-1".equals(orderType)){
				c.andOrderTypeEqualTo(new Byte(orderType));
			}
		}
		//订单号
		if(StringUtils.isNotEmpty(orderNo)){
			c.andOrderNoLike("%"+orderNo+"%");
		}
		//订单完成时间
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(StringUtils.isNotEmpty(startTimes)){
			Date start;
			try {
				start = sf.parse(startTimes);
			} catch (ParseException e) {
				throw new BusinessException("搜索开始日期异常");
			}
			if("3".equals(orderStatus)){
				c.andFinishTimeGreaterThanOrEqualTo(start);
			}else{
				c.andCreateTimeGreaterThanOrEqualTo(start);
			}
		}
		if(StringUtils.isNotEmpty(endTimes)){
			Date end;
			try {
				end = sf.parse(endTimes);
			} catch (ParseException e) {
				throw new BusinessException("搜索结束日期异常");
			}
			if("3".equals(orderStatus)){
				c.andFinishTimeLessThan(end);
			}else{
				c.andCreateTimeLessThanOrEqualTo(end);
			}
		}
		List<Long> userIdss = new ArrayList<Long>();
		//用户手机号查询用户订单
		if(org.apache.commons.lang.StringUtils.isNotEmpty(phone)){
			UserExample userEx = new UserExample();
			userEx.createCriteria().andNameLike("%"+phone+"%");
			List<User> users = userMapper.selectByExample(userEx);
			for(User u:users){
				userIdss.add(u.getId());
			}
			userIdss.add(0L);
		}
		//用户车牌查询用户订单
		List<Long> userIdList = new ArrayList<Long>();
		if(StringUtils.isNotEmpty(license)){
			UserInfoExample userInfoEx = new UserInfoExample();
			userInfoEx.createCriteria().andLicenseLike("%"+license.toUpperCase()+"%");
			List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
			for(UserInfo userInfo:userInfos){
				userIdList.add(userInfo.getUserId());
			}
			userIdList.add(0L);
			
		}
		if(!userIdss.isEmpty()&&!userIdList.isEmpty()){
			userIdss.retainAll(userIdList);
		}else{
			userIdss.addAll(userIdList);
		}
		if(!userIdss.isEmpty() ){
			c.andUserIdIn(userIdss);
		}
		//订单状态
		if(!"-1".equals(orderStatus)){
			c.andStatusEqualTo(Integer.parseInt(orderStatus));
		}
		//设置创建时间降序排列
		if("3".equals(orderStatus)){
			orderex.setOrderByClause(OrdersExample.FINISH_TIME_DESC);;
		}else{
			orderex.setOrderByClause(OrdersExample.CREATE_TIME_DESC);
		}
		int rowCount = ordersMapper.countByExample(orderex);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, rowCount);
		orderex.setLimitStart(page.getStartRow());
		orderex.setLimitEnd(page.getSize());
		List<Orders> orders = ordersMapper.selectByExample(orderex);
		List<String> orderNos = orders.stream().map(Orders::getOrderNo).distinct().collect(Collectors.toList());
		Map<String,Integer> expressStatus = new HashMap<String,Integer>();
		if(orderNos!=null && !orderNos.isEmpty()){
			OrderExpressExample orderExpressExample = new OrderExpressExample();
			orderExpressExample.createCriteria().andOrderNoIn(orderNos);
			List<OrderExpress> express = orderExpressMapper.selectByExample(orderExpressExample);
			express.forEach(e -> {
				expressStatus.put(e.getOrderNo(), e.getExpressStatus());
			});
		}
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		List<Long> sellerIds = orders.stream()
				.map(Orders::getSellerId)
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());
		sellerIds.add(0L);
		
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andIdIn(sellerIds);
		Map<Long,Seller> sellerMap = new HashMap<Long,Seller>();
		List<Seller> sellers = sellerMapper.selectByExample(sellerEx);
		sellers.stream().forEach(seller -> {
			sellerMap.put(seller.getId(), seller);
		});
		
		
		List<Long> userIds = orders.stream()
				.map(Orders::getUserId)
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());
		userIds.add(0L);
		
		UserExample userEx = new UserExample();
		userEx.createCriteria().andIdIn(userIds);
		Map<Long,User> userMap = new HashMap<Long,User>();
		List<User> users = userMapper.selectByExample(userEx);
		users.stream().forEach(user -> {
			userMap.put(user.getId(), user);
		});
		
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdIn(userIds);
		Map<Long,UserInfo> userInfoMap = new HashMap<Long,UserInfo>();
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		userInfos.stream().forEach(userInfo -> {
			userInfoMap.put(userInfo.getUserId(), userInfo);
		});
		
		//盛大洗车订单
		Map<String,CarWashSheng> carWashMap = new HashMap<String,CarWashSheng>();
		if("3".equals(orderType)||StringUtils.isEmpty(orderType)||"-1".equals(orderType)){
			orderNos.add("0");
			CarWashShengExample carWashShengEx = new CarWashShengExample();
			carWashShengEx.createCriteria().andOrderNoIn(orderNos);
			List<CarWashSheng> carWashShengs = carWashShengMapper.selectByExample(carWashShengEx);
			carWashShengs.stream().forEach(carWash -> {
				carWashMap.put(carWash.getOrderNo(), carWash);
			});
		}
		for (Orders order : orders) {
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("createtime",order.getCreateTime());
			result.put("finishtime",order.getFinishTime());
			result.put("orderNo",order.getOrderNo());
			result.put("orderType", order.getOrderType());
			result.put("status", order.getStatus());
			result.put("supplierId", order.getSupplyId());
			result.put("deliverTime", order.getDeliverTime());
			result.put("expressStatus", expressStatus.get(order.getOrderNo()));
			result.put("operateRemark", order.getOperateRemark());
			//如果是电瓶订单
			if(order.getOrderType() == Orders.ORDER_TYPE_0){
				result.put("userName", order.getUserName());
				result.put("userPhone", order.getPhone());
				result.put("sellerName", null);
				result.put("sellerPhone", null);
			}else{
				User u = userMap.get(order.getUserId());
				if(u != null){
					result.put("userName", u.getNickName());
					result.put("userPhone", u.getName());
				}
				UserInfo ui = userInfoMap.get(order.getUserId());
				if(ui != null){
					result.put("license", ui.getLicense());
				}
				Seller s = sellerMap.get(order.getSellerId());
				if(s!=null){
					result.put("sellerName", s.getSellerName());
					result.put("sellerPhone", s.getSellerPhone());
						//if(StringUtils.isNotEmpty(s.getAddressDetail())){
					result.put("sellerAddress", s.getAddressDetail());
					result.put("settleType", s.getSettleType());
					result.put("seller", s.getName());
						//}
				}
				if("3".equals(orderType)||StringUtils.isEmpty(orderType)||"-1".equals(orderType)){
					CarWashSheng car = carWashMap.get(order.getOrderNo());
					if(car!=null){
						result.put("sellerName", car.getShopName());
						result.put("sellerPhone", car.getShopPhone());
						result.put("sellerAddress", car.getShopAddress());
					}
				}
			}
			results.add(result);
		}
		
		return DatasetBuilder.fromDataList(results,page.createPageInfo());
	}

	/**
	 * 根据商家id查询商家订单列表
	 */
	@Override
	@Read
	public DatasetList<Map<String,Object>> sellerQueryOrderList(Long userId,String orderStatus,Integer pageNo,Integer pageSize) {
		OrdersExample orderex = new OrdersExample();
		Criteria c = orderex.createCriteria();
		c.andSellerIdEqualTo(userId);
		if(!"-1".equals(orderStatus)){
			c.andStatusEqualTo(Integer.parseInt(orderStatus));
		}
		orderex.setOrderByClause("create_time desc");
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, ordersMapper.countByExample(orderex));
		orderex.setLimitStart(page.getStartRow());
		orderex.setLimitEnd(page.getSize());
		List<Orders> orders = ordersMapper.selectByExample(orderex);
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for (Orders order : orders) {
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("createtime",order.getCreateTime());
			result.put("orderNo",order.getOrderNo());
			//result.put("code",order.getCode());
			if(order.getOrderType()==Orders.ORDER_TYPE_0){
				result.put("phone",order.getPhone());
				result.put("userName",order.getUserName());
				result.put("addressInfo",order.getProvince()+order.getCity()+order.getArea()+order.getAddressDetail());
			}else{
				User  user = userMapper.selectByPrimaryKey(order.getUserId());
				if(user!=null){
					result.put("phone",user.getName());
					result.put("userName",user.getNickName());
					result.put("addressInfo","--");
				}
			}
			result.put("status",order.getStatus());
			result.put("orderType",order.getOrderType());
			if (order.getUserId()!=null) {
				UserInfoExample userEx = new UserInfoExample();
				userEx.createCriteria().andUserIdEqualTo(order.getUserId());
				List<UserInfo> userList = userInfoMapper.selectByExample(userEx);
				if(!userList.isEmpty()){
					result.put("license",userList.get(0).getLicense());
				}else{
					result.put("license","");
				}
			}
			results.add(result);
		}
		return DatasetBuilder.fromDataList(results,page.createPageInfo());
	}

	@Override
	@Read
	public DatasetList<Map<String,Object>> queryOrderList(Long userId, String status) {
		//根据条件查询订单
		OrdersExample orderex = new OrdersExample();
		Criteria  c = orderex.createCriteria();
		c.andUserIdEqualTo(userId);
		if("-2".equals(status)){//待评价
			c.andStatusEqualTo(Orders.STATUS_3);
		}else if("-1".equals(status)){//全部订单
			
		}else{//其他状态订单
			List<Integer> statusArr = new ArrayList<Integer>();
			for(String s:status.split(",")){
				if(!s.matches("\\d+")){
					throw new BusinessException("参数校验失败！");
				}
				statusArr.add(Integer.parseInt(s));
			}
			c.andStatusIn(statusArr);
		}
			

		orderex.setOrderByClause("create_time desc");
		List<Orders> orders = ordersMapper.selectByExample(orderex);
		if (orders.isEmpty()) {
			return DatasetBuilder.fromDataList(new ArrayList<Map<String,Object>>());
		}
		//判断该用户已经评价的订单
		OrderCommentExample orderCommentEx = new OrderCommentExample();
		orderCommentEx.createCriteria().andUserIdEqualTo(userId);
		List<OrderComment> orderComments = orderCommentMapper.selectByExample(orderCommentEx);
		HashMap<String, Long> commentMap = new HashMap<String,Long>();
		orderComments.stream().forEach(orderComm ->{
			commentMap.put(orderComm.getOrderNo(), orderComm.getId());
		});
		if("-2".equals(status)){//剔除已评价的订单
			Iterator<Orders> iterator = orders.iterator();
			while(iterator.hasNext()){
				Orders order = iterator.next();
				if(!Objects.isNull(commentMap.get(order.getOrderNo()))){
					iterator.remove();
				}
			}
		}
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for (Orders order : orders) {
			Map<String,Object> result = new HashMap<String,Object>();
			if(Orders.ORDER_TYPE_3.equals(order.getOrderType())){
				if(order.getCode().length()==8){
					result.put("washType","2");
				}else{
					result.put("washType","1");
				}
			}
			result.put("createtime",order.getCreateTime());
			result.put("orderNo",order.getOrderNo());
			result.put("carPrice",order.getCarPrice());
			result.put("status",order.getStatus());
			result.put("productName",order.getOrderRemark());
			result.put("orderType", order.getOrderType());
			result.put("goodNum", order.getGoodsNum());
			result.put("orderCode",order.getCode());
			if("-2".equals(status)){
				result.put("comment", 0);//未评价
			}else{
				//判断是否存在评价信息
				if(Orders.STATUS_3.equals(order.getStatus())){
					if(Objects.isNull(commentMap.get(order.getOrderNo()))){
						result.put("comment", 0);//未评价
					}else{
						result.put("comment", commentMap.get(order.getOrderNo()));//已评价
					}
				}else{
					result.put("comment", -1);//不能评价
				}
			}

/*			String goodsId = order.getGoodsId();
			if(StringUtils.isNotEmpty(goodsId) && order.getOrderType() == Orders.ORDER_TYPE_2){
				try{
					String jiyouids = goodsId.split(",")[0];
					Long jiyouId = 0L;
					if (jiyouids.indexOf("|") >= 0) {
						jiyouId = Long.parseLong(jiyouids.split("\\|")[0]);
					} else {
						jiyouId = Long.parseLong(jiyouids);
					}

					AutoParts autoParts = autoPartsMapper.selectByPrimaryKey(jiyouId);
					if (Objects.nonNull(autoParts)) {
						result.put("jiyouPic", Arrays.asList(autoParts.getPic()));
					}
					Long jilvId = Long.parseLong(goodsId.split(",")[1]);
					autoParts = autoPartsMapper.selectByPrimaryKey(jilvId);
					if (Objects.nonNull(autoParts)) {
						result.put("jilvPic", Arrays.asList(autoParts.getPic()));
					}
				}catch(Exception e){
					Log4jHelper.getLogger().error("获取保养订单图片错误！");
				}
		
			}*/
			results.add(result);
		}
		return DatasetBuilder.fromDataList(results);
	}

	private static void transLabel(Autopose auto) {
		//蓝标
		if (!StringUtils.isEmpty(auto.getBluelabel())) {
			//转换电源 D23 60 L T2 M -> D23-60-L-T2-M
			if (auto.getBluelabel().indexOf("-") == -1) {
				auto.setBluelabel(transLabel0(auto.getBluelabel()));
			}
		//绿标
		} else if (!StringUtils.isEmpty(auto.getSilverlabel())) {
			if (auto.getSilverlabel().indexOf("-") == -1) {
				auto.setSilverlabel(transLabel0(auto.getSilverlabel()));
			}
		//此款车型带启停功能
		} else if (!StringUtils.isEmpty(auto.getAgm())) {
			if (auto.getAgm().indexOf("-") == -1) {
				auto.setAgm(transLabel0(auto.getAgm()));
			}
		}
	}
	private static String transLabel0(String needTrans){
		final StringBuilder sb = new StringBuilder();
		for(String s : needTrans.split("  ")){
			Arrays.stream(s.split(" ")).forEach(l -> {
				sb.append(l).append("-");
			});
			sb.deleteCharAt(sb.length() - 1);
			sb.append("_");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	
	/**
	 * 电瓶筛选
	 * 
	 * @param carid
	 * @return
	 */
	@Read
	@Override
	public DatasetSimple<PowerPrice> matchPowerProduct(Long userId) {
		//根据用户id查询userInfo表
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(userId);
		userInfoEx.setLimitStart(0);
		userInfoEx.setLimitEnd(1);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		if(userInfos.isEmpty()){
			throw new BusinessException("用户信息为空！");
		}
		UserInfo userInfo = userInfos.get(0);
		//查看用户是否绑定车牌
		if(StringUtils.isEmpty(userInfo.getLicense())){
			throw new BusinessException("用户没有绑定车型！errCode:1");
		}
		//根据车牌查询importUserInfo表
		ImportUserInfoExample importUserEx = new ImportUserInfoExample();
		importUserEx.createCriteria().andChePaiEqualTo(userInfo.getLicense());
		importUserEx.setLimitStart(0);
		importUserEx.setLimitEnd(1);
		List<ImportUserInfo> importUserList = importUserInfoMapper.selectByExample(importUserEx);
		//判断是否导入车辆信息
		if(importUserList.isEmpty()){
			throw new BusinessException("用户没有绑定车型！errCode:2");
		}
		//根据汽车品牌,车系,生产年份查询autoPose表
		ImportUserInfo importUserInfo = importUserList.get(0);
		AutoposeExample autoposeExample = new AutoposeExample();
		com.emate.shop.business.model.AutoposeExample.Criteria c = autoposeExample.createCriteria();
		c.andBrandnameEqualTo(importUserInfo.getAutoBrand())
				.andAutotypenameLike("%" + importUserInfo.getAutoType().toUpperCase() + "%")
				.andFirstproducttimeLessThanOrEqualTo(importUserInfo.getProductYear())
				.andLastproducttimeGreaterThanOrEqualTo(importUserInfo.getProductYear())
				.andEnginedispLike("%" + importUserInfo.getEngineDisp() + "%");
		autoposeExample.setLimitStart(0);
		autoposeExample.setLimitEnd(1);
		List<Autopose> autoposes = autoposeMapper.selectByExample(autoposeExample);
		Autopose auto = autoposes.get(0);
		transLabel(auto);
		//若蓝标不为空
		if (!StringUtils.isEmpty(auto.getBluelabel())) {
			//根据蓝标查询powerPrice表
			PowerPriceExample powerEx = new PowerPriceExample();
			powerEx.createCriteria().andProductNameEqualTo(auto.getBluelabel());
			powerEx.setLimitStart(0);
			powerEx.setLimitEnd(1);
			List<PowerPrice> power = powerPriceMapper.selectByExample(powerEx);
			if (power.size() > 0) {
				return DatasetBuilder.fromDataSimple(power.get(0));
			}
		//根据绿标查询powerPrice表
		} else if (!StringUtils.isEmpty(auto.getSilverlabel())) {
			PowerPriceExample powerEx = new PowerPriceExample();
			powerEx.createCriteria().andProductNameEqualTo(auto.getSilverlabel());
			powerEx.setLimitStart(0);
			powerEx.setLimitEnd(1);
			List<PowerPrice> power = powerPriceMapper.selectByExample(powerEx);
			if (power.size() > 0) {
				return DatasetBuilder.fromDataSimple(power.get(0));
			}
		//根据agm字段查询powerPrice表
		} else if (!StringUtils.isEmpty(auto.getAgm())) {
			PowerPriceExample powerEx = new PowerPriceExample();
			Stream.of(auto.getAgm().split("_")).forEach(s -> {
				powerEx.or().andProductNameEqualTo(s);
			});
			List<PowerPrice> power = powerPriceMapper.selectByExample(powerEx);
			powerEx.setLimitStart(0);
			powerEx.setLimitEnd(1);
			if (power.size() > 0) {
				return DatasetBuilder.fromDataSimple(power.get(0));
			}
		}
		return DatasetBuilder.fromMessageSimple("找不到车型匹配的电源！");
	}

	@Override
	@Read
	public DatasetList<Map<String, Object>> matchTyreProduct(Long userId) {
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		if(userInfos.isEmpty()){
			throw new BusinessException("用户信息为空！");
		}
		UserInfo userInfo = userInfos.get(0);
		String chepai = userInfo.getLicense();
		ImportUserInfoExample importEx = new ImportUserInfoExample();
		importEx.createCriteria().andChePaiEqualTo(chepai);
		List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
		if(importList.isEmpty()){
			throw new BusinessException("用户绑定车型信息为空！");
		}
		ImportUserInfo importUserInfo = importList.get(0);
		TyreInfoExample tyreInfoEx = new TyreInfoExample();
		tyreInfoEx.createCriteria()
		.andBrandLike("%"+importUserInfo.getAutoBrand()+"%")
		.andAutolineNameEqualTo(importUserInfo.getAutoType())
		.andEngineDispLike("%"+importUserInfo.getEngineDisp()+"%")
		.andProductTimeLike("%"+importUserInfo.getProductYear()+"%");
		List<TyreInfo> matchList = tyreInfoMapper.selectByExample(tyreInfoEx);
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for(TyreInfo t:matchList){
			Map<String,Object> one = new HashMap<String,Object>();
			one.put("product", t.getFrontSize());
			one.put("price", 500);
			results.add(one);
			if(!t.getFrontSize().equals(t.getRearSize())){
				Map<String,Object> other = new HashMap<String,Object>();
				other.put("product", t.getRearSize());
				other.put("price", 500);
				results.add(other);
			}
		}
		
		DatasetList<Map<String, Object>> r =  DatasetBuilder.fromDataList(results);
		AllTypeExample allTypeEx = new AllTypeExample();
		r.putDatasetList("allTyre", allTypeMapper.selectByExample(allTypeEx));
		return r;
	}

	@Override
	@Read
	public DatasetList<Map<String, Object>> matchOilProduct(Long userId) {
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		if(userInfos.isEmpty()){
			throw new BusinessException("用户信息为空！");
		}
		UserInfo userInfo = userInfos.get(0);
		String chepai = userInfo.getLicense();
		ImportUserInfoExample importEx = new ImportUserInfoExample();
		importEx.createCriteria().andChePaiEqualTo(chepai);
		List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
		if(importList.isEmpty()){
			throw new BusinessException("用户绑定车型信息为空！");
		}
		ImportUserInfo importUserInfo = importList.get(0);
		EngineOilExample engineEx = new EngineOilExample();
		importUserInfo.setProductYear(importUserInfo.getProductYear().substring(0,4));
		engineEx.or()
		.andBrandLike("%"+importUserInfo.getAutoBrand()+"%")
		.andAutolineNameEqualTo(importUserInfo.getAutoType())
		.andEngineDispLike("%"+importUserInfo.getEngineDisp()+"%");
		engineEx.or().andBrandLike("%"+importUserInfo.getAutoBrand()+"%")
		.andAutolineNameEqualTo(importUserInfo.getAutoType())
		.andEngineDispEqualTo("*");
		//.andProductTimeLike(importUserInfo.getProductYear());
		List<EngineOil> list = engineOilMapper.selectByExample(engineEx);
		Iterator<EngineOil> it = list.iterator();
		
		//循环遍历机油适配表，删除不匹配的年份
		while(it.hasNext()){
			EngineOil e = it.next();
			String year = e.getProductTime();
			if(!StringUtils.isEmpty(year)){
				String[] s = year.split("--");
				int importYear = Integer.parseInt(importUserInfo.getProductYear());
				if(s.length==1){
					if(year.indexOf("--")==-1){
						if(year.indexOf(importUserInfo.getProductYear()) ==-1){
							it.remove();
						}
					}else{
						int startYear = Integer.parseInt(s[0].substring(0, 4));
						if(importYear<startYear){
							it.remove();
						}
					}
					
				}else if(s.length==2){
					if(year.startsWith("--")){
						int endYear = Integer.parseInt(s[1].substring(0, 4));
						if(importYear>endYear){
							it.remove();
						}
					}else{
						int startYear = Integer.parseInt(s[0].substring(0, 4));
						int endYear =  Integer.parseInt(s[1].substring(0, 4));
						if(importYear<startYear || importYear>endYear){
							it.remove();
						}
					}
				}
			}
		}
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for(EngineOil e : list){
			Map<String,Object> one = new HashMap<String,Object>();
			one.put("product", e.getJiyou());
			one.put("price", 200);
			results.add(one);
		}
		return DatasetBuilder.fromDataList(results);
	}
	
	/**
	 * 根据用户id匹配车型
	 * @param userId
	 * @return
	 */
	private AutoInfo findAutoInfo(Long userId){
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
		ImportUserInfo importUserInfo = importList.get(0);
		AutoInfoExample autoInfoEx = new AutoInfoExample();
		importUserInfo.setProductYear(importUserInfo.getProductYear().substring(0,4));
		if(importUserInfo.getEngineDisp().indexOf("L")!=-1
				|| importUserInfo.getEngineDisp().indexOf("l")!=-1){
			importUserInfo.setEngineDisp(importUserInfo.getEngineDisp().substring(0,importUserInfo.getEngineDisp().length()-1));
		}
		autoInfoEx.or()
		.andBrandEqualTo(importUserInfo.getAutoBrand())
		.andAutolineNameEqualTo(importUserInfo.getAutoType().toUpperCase())
		.andEngineDispLike("%"+importUserInfo.getEngineDisp()+"%")
		.andProductTimeEqualTo(importUserInfo.getProductYear());
		autoInfoEx.setOrderByClause(AutoInfoExample.ID_ASC);
		autoInfoEx.setLimitStart(0);
		autoInfoEx.setLimitEnd(1);
		List<AutoInfo>  list = autoInfoMapper.selectByExample(autoInfoEx);
		if(list.isEmpty()){
			return null;
		}
		list.get(0).setFrontSize(importUserInfo.getPrice().toString());
		//list.get(0).setRearSize(importUserInfo.getOrderPrice().toString());
		//list.get(0).setProductTime(importUserInfo.getAddress()+"_"+importUserInfo.getSource());
		return list.get(0);
	}
	
	@Override
	@Read
	public DatasetList<Map<String, Object>> matchOilProductNew(Long userId) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		AutoInfo autoInfo = findAutoInfo(userId);
		if(autoInfo == null){
			return DatasetBuilder.fromDataList(results);
		}
//		Long autoId = autoInfo.getId();
//		AutoPartRelationExample relationEx = new AutoPartRelationExample();
//		relationEx.createCriteria().andAutoIdEqualTo(autoId).andPartTypeIdEqualTo(25);
//		List<AutoPartRelation> re = autoPartRelationMapper.selectByExample(relationEx);
//		if (re.size() <= 0) {
//			return DatasetBuilder.fromDataList(results);
//		}
//		AutoPartRelation r = re.get(0);
//		if (r.getMatchId() == null) {
//			return DatasetBuilder.fromDataList(results);
//		}
//		String[] ids = r.getMatchId().split(",");
//		List<Long> longIds = Arrays.asList(ids).stream()
//				.map(Long::parseLong)
//				.distinct()
//				.collect(Collectors.toList());
		List<Long> longIds = new ArrayList<>();
		//longIds.add(2338290L);
		//longIds.add(2339019L);
		
		//731326 金嘉护 10W-40 4L;731324 磁护 5W-40 4L;2339017 极护 5W-40  4L
		//1335486 金嘉护 10W-40 1L;731322  磁护   5W-40 1L;2339019  极护   5W-40 1L
		longIds.add(731326L);
		longIds.add(731324L);
		longIds.add(2339017L);
		longIds.add(1335486L);
		longIds.add(731322L);
		longIds.add(2339019L);
		AutoPartsExample partsExample = new AutoPartsExample();
		partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("嘉实多（Castrol）");

		List<AutoParts> autoPartsList = autoPartsMapper.selectByExample(partsExample);
		if(autoPartsList.isEmpty()){
			return DatasetBuilder.fromDataList(results);
		}
		ImportUserInfo importUserInfo = findImportUserInfo(userId);
		List<ScoreChannel> ScoreChannels=new ArrayList<ScoreChannel>();
		if(!Objects.isNull(importUserInfo.getSource())){
			ScoreChannelExample scoreChannelEx = new ScoreChannelExample();
			scoreChannelEx.createCriteria()
			.andRuleTypeEqualTo(ScoreChannel.RULE_TYPE_1)
			.andCityNameEqualTo(importUserInfo.getAddress())
			.andChannelEqualTo(importUserInfo.getSource())
			.andMinimumLessThanOrEqualTo(importUserInfo.getOrderPrice())
			.andMaximumGreaterThan(importUserInfo.getOrderPrice());
			ScoreChannels = scoreChannelMapper.selectByExample(scoreChannelEx);
		}
		Map<String, Object> one = new HashMap<String, Object>();
		one.put("product", autoPartsList);
		one.put("amount", autoInfo.getOilAmount());
		one.put("price", autoInfo.getFrontSize());
		//one.put("orderPrice", autoInfo.getRearSize());
		one.put("isOrderPriece", "0");
		if(!ScoreChannels.isEmpty()){
			one.put("isOrderPriece", "1");
			one.put("oilRule",ScoreChannels.get(0).getRuleValue());
		}
		//优先级最高，判断导入表里面是否存在指定机油等级
		//如果存在，需要优先根据配置好的机油等级来判断用户需要哪些机油
		if(importUserInfo.getServiceValue()!=null && importUserInfo.getServiceValue().intValue()!=0){
			one.put("isOrderPriece", "1");
			one.put("oilRule",importUserInfo.getServiceValue().toString());
		}
		results.add(one);
		return DatasetBuilder.fromDataList(results);
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
	@Read
	public DatasetList<Map<String, Object>> matchJilvProductNew(Long userId) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		AutoInfo autoInfo = findAutoInfo(userId);
		if(autoInfo == null){
			return DatasetBuilder.fromDataList(results);
		}
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
		partsExample.setLimitStart(0);
		partsExample.setLimitEnd(1);
		partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("曼牌（MANNFILTER）");
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
	public DatasetList<Map<String, Object>> matchJilvProduct(Long userId) {
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
		if(userInfos.isEmpty()){
			throw new BusinessException("用户信息为空！");
		}
		UserInfo userInfo = userInfos.get(0);
		String chepai = userInfo.getLicense();
		ImportUserInfoExample importEx = new ImportUserInfoExample();
		importEx.createCriteria().andChePaiEqualTo(chepai);
		List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
		if(importList.isEmpty()){
			throw new BusinessException("用户绑定车型信息为空！");
		}
		ImportUserInfo importUserInfo = importList.get(0);
		JilvInfoExample jilvEx = new JilvInfoExample();
		importUserInfo.setProductYear(importUserInfo.getProductYear().substring(0,4));
		if(importUserInfo.getEngineDisp().indexOf(".0")!=-1){
			Float f = Float.parseFloat(importUserInfo.getEngineDisp().replaceAll("L", "").replaceAll("T", ""));
			importUserInfo.setEngineDisp(f.intValue()+"");
		}else{
			importUserInfo.setEngineDisp(importUserInfo.getEngineDisp().replaceAll("L", "").replaceAll("T", ""));
		}
		
		jilvEx.or()
		.andBrandLike("%"+importUserInfo.getAutoBrand()+"%")
		.andAutolineNameLike("%"+importUserInfo.getAutoType()+"%")
		.andEngineDispLike(importUserInfo.getEngineDisp()+"%");
		//.andProductTimeLike(importUserInfo.getProductYear());
		List<JilvInfo> list = jilvInfoMapper.selectByExample(jilvEx);
		Iterator<JilvInfo> it = list.iterator();
		
		//循环遍历机滤适配表，删除不匹配的年份
		while(it.hasNext()){
			JilvInfo e = it.next();
			String year = e.getProductTime();
			if(!StringUtils.isEmpty(year)){
				String[] s = year.split("-");
				int importYear = Integer.parseInt(importUserInfo.getProductYear());
				if(s.length==1){
					int startYear = Integer.parseInt(s[0].substring(0, 4));
					if(importYear<startYear){
						it.remove();
					}
				}else if(s.length==2){
					if(year.startsWith("-")){
						int endYear = Integer.parseInt(s[1].substring(0, 4));
						if(importYear>endYear){
							it.remove();
						}
					}else{
						int startYear = Integer.parseInt(s[0].substring(0, 4));
						int endYear =  Integer.parseInt(s[1].substring(0, 4));
						if(importYear<startYear || importYear>endYear){
							it.remove();
						}
					}
				}
			}
		}
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for(JilvInfo e : list){
			Map<String,Object> one = new HashMap<String,Object>();
			one.put("product", e.getJilv());
			one.put("price", 200);
			results.add(one);
		}
		return DatasetBuilder.fromDataList(results);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> updateOrder(Orders order) {
		if(order==null || order.getId()==null){
			throw new BusinessException("更新订单失败！订单为空，或者订单id为空！");
		}
		Orders  dbOrder = ordersMapper.selectByPrimaryKey(order.getId());
		if(dbOrder==null){
			throw new BusinessException("更新订单失败！订单为空！");
		}
		order.setUpdateTime(new Date());
		//根据商家结算类型判断订单结算类型
		Seller seller1 = sellerMapper.selectByPrimaryKey(dbOrder.getSellerId());
		if(order.getStatus()==Orders.STATUS_1){
			if(!Objects.nonNull(seller1)){
				throw new BusinessException("更新订单失败！该商家不存在！");
			}
			order.setSupplyId(0L);
			//小保养
			if(Objects.equals(dbOrder.getOrderType(), Orders.ORDER_TYPE_2)&&Objects.equals(Seller.SETTLE_TYPE_1, seller1.getSettleType())){
				order.setSupplyId(seller1.getId());
			}
		}
		//喷漆
		if(Objects.equals(dbOrder.getOrderType(), Orders.ORDER_TYPE_4)){
			order.setSupplyId(seller1.getId());
		}
		if(ordersMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("更新订单失败!errCode:1");
		}
		if(!Objects.equals(dbOrder.getOrderType(), Orders.ORDER_TYPE_4)){
			try{
				OrderTraceExample orderTraceEx = new OrderTraceExample();
				orderTraceEx.createCriteria().andOrderNoEqualTo(dbOrder.getOrderNo());
				List<OrderTrace> orderTraceList = orderTraceMapper.selectByExample(orderTraceEx);
				if(!orderTraceList.isEmpty()){
					OrderTrace trace = orderTraceList.get(0);
					trace.setUpdateTime(new Date());
					if(Objects.equals(order.getStatus(),Orders.STATUS_1)){
						trace.setConfirmTime(new Date());//管理员审核时间
					}else if(Objects.equals(order.getStatus(),Orders.STATUS_2)){//修改为确认到货时间
						trace.setDeliverTime(new Date());
					}else if(Objects.equals(order.getStatus(),Orders.STATUS_3)){
						trace.setCompletTime(new Date());//表示订单完成时间
						//可能后台人员不发货，那么就需要系统自动往前计算1个小时，默认为到货时间
						if(trace.getDeliverTime()==null){
							trace.setDeliverTime(new Date(trace.getCompletTime().getTime()-60*60*1000));
						}
					}
					orderTraceMapper.updateByExample(trace, orderTraceEx);
				}
			}catch(Exception e){
				Log4jHelper.getLogger().error("记录订单日志失败！");
			}
		}

		//如果是完成订单，更新记录商家完成订单数量
		if(order.getStatus()==Orders.STATUS_3){
			Long sellerId = dbOrder.getSellerId();
			if(sellerId == null){//洗车订单
				sellerId = order.getSellerId();
			}
			Seller seller = null;
			if(sellerId != null){
				seller = sellerMapper.selectByPrimaryKey(sellerId);
			}
			if(seller !=null){
				seller.setOrderCountOver(seller.getOrderCountOver()+1);
				sellerMapper.updateByPrimaryKeySelective(seller);
			}
		}
		return DatasetBuilder.fromDataSimple(true);
	}
	
	@Override
	public DatasetSimple<Map<String,Object>> sellerGetOrderSummary(Long sellerId) {
		Map<String,Object> result = new HashMap<String,Object>();
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_1);
		result.put("waiteService", ordersMapper.countByExample(orderEx));
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_2);
		result.put("servicing", ordersMapper.countByExample(orderEx));
		
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		result.put("complate", ordersMapper.countByExample(orderEx));
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Read
	public DatasetSimple<Map<String,Object>> sellerGetMoneySummary(Long sellerId) {
		Map<String,Object> result = new HashMap<String,Object>();
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		c.andFinishTimeGreaterThan(now.getTime());
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		BigDecimal moneyTotel = new BigDecimal(0);
		for(Orders order:orders){
			if(Orders.ORDER_TYPE_2.equals(order.getOrderType())){
				moneyTotel = moneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					moneyTotel = moneyTotel.add(order.getJilvPrice());
				}
			}else if(Orders.ORDER_TYPE_3.equals(order.getOrderType())){
				moneyTotel = moneyTotel.add(order.getServiceMoney());
			}else{
				moneyTotel = moneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					moneyTotel = moneyTotel.add(order.getMoneyAmount());
				}
			}
		}
		result.put("monthMoney", moneyTotel.floatValue());
		
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		c.andFinishTimeGreaterThan(now.getTime());
		orders = ordersMapper.selectByExample(orderEx);
		BigDecimal daymoneyTotel = new BigDecimal(0);
		for(Orders order:orders){
			if(Orders.ORDER_TYPE_2.equals(order.getOrderType())){
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					daymoneyTotel = daymoneyTotel.add(order.getJilvPrice());
				}
			}else if(Orders.ORDER_TYPE_3.equals(order.getOrderType())){
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
			}else{
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					daymoneyTotel = daymoneyTotel.add(order.getMoneyAmount());
				}
			}
		}
		result.put("todayMoney", daymoneyTotel.floatValue());
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Read
	public DatasetList<Map<String,Object>> sellerTodayIncomeList(Long sellerId){
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		c.andFinishTimeGreaterThan(now.getTime());
		orderEx.setOrderByClause(OrdersExample.FINISH_TIME_DESC);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		orders.stream().forEach(order ->{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("finishTime", order.getFinishTime());
			BigDecimal daymoneyTotel = new BigDecimal(0);
			if(Orders.ORDER_TYPE_2.equals(order.getOrderType())){
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					daymoneyTotel = daymoneyTotel.add(order.getJilvPrice());
				}
			}else if(Orders.ORDER_TYPE_3.equals(order.getOrderType())){
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
			}else{
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					daymoneyTotel = daymoneyTotel.add(order.getMoneyAmount());
				}
			}
			res.put("money", daymoneyTotel);
			result.add(res);
		});
		return DatasetBuilder.fromDataList(result);
	}
	
	@Override
	@Read
	public DatasetList<Map<String,Object>> sellerAllIncomeList(Long sellerId,String date,Integer pageNo,Integer pageSize){
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH) + 1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month - 1);
		c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
		// 计算出当前参数所给月数+1
		now.set(Calendar.MONTH, month);
		c.andFinishTimeLessThan(now.getTime());
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, ordersMapper.countByExample(orderEx));
		orderEx.setLimitStart(page.getStartRow());
		orderEx.setLimitEnd(page.getSize());
		orderEx.setOrderByClause(OrdersExample.FINISH_TIME_DESC);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		orders.stream().forEach(order ->{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("finishTime", order.getFinishTime());
			BigDecimal daymoneyTotel = new BigDecimal(0);
			if(order.getOrderType().equals(Orders.ORDER_TYPE_2)){
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){
					daymoneyTotel = daymoneyTotel.add(order.getJilvPrice());
				}
			}else if(order.getOrderType().equals(Orders.ORDER_TYPE_3)){
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
			}else{
				daymoneyTotel = daymoneyTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					daymoneyTotel = daymoneyTotel.add(order.getMoneyAmount()); 
				}
			};
			res.put("money", daymoneyTotel);
			res.put("orderNo", order.getOrderNo());
			res.put("orderType", order.getOrderType());
			result.add(res);
		});
		DatasetList<Map<String, Object>>  returnObj = DatasetBuilder.fromDataList(result,page.createPageInfo());
		SellerReportExample reportEx = new SellerReportExample();
		reportEx.createCriteria()
		.andSellerIdEqualTo(sellerId)
		.andYearEqualTo(year)
		.andMonthEqualTo(month);
		reportEx.setLimitStart(0);
		reportEx.setLimitEnd(1);
		List<SellerReport>  reportList = sellerReportMapper.selectByExample(reportEx);
		if(reportList.isEmpty()){
			returnObj.putDataset("isComplet", DatasetBuilder.fromDataSimple("false"));
		}else{
			if(reportList.get(0).getStatus() == SellerReport.STATUS_1
					|| reportList.get(0).getStatus() == SellerReport.STATUS_3){
				returnObj.putDataset("isComplet", DatasetBuilder.fromDataSimple("true"));
			}else{
				returnObj.putDataset("isComplet", DatasetBuilder.fromDataSimple("false"));
			}
		}
		return returnObj;
		
	}
	
	/**
	 * 商家验券列表(最近一个月的)
	 * @param sellerId
	 * @return
	 */
	@Override
	@Read
	public DatasetList<Map<String,Object>> sellerCompleteList(Long sellerId,Integer pageNo,Integer pageSize){
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.MONTH, -1);//查询上个到现在
		c.andFinishTimeGreaterThan(now.getTime());
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, ordersMapper.countByExample(orderEx));
		orderEx.setLimitStart(page.getStartRow());
		orderEx.setLimitEnd(page.getSize());
		orderEx.setOrderByClause(OrdersExample.FINISH_TIME_DESC);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		orders.stream().forEach(order ->{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("finishTime", order.getFinishTime());
			res.put("code", order.getCode());
			res.put("orderNo", order.getOrderNo());
			result.add(res);
		});
		return DatasetBuilder.fromDataList(result,page.createPageInfo());
	}
	public String fanCompany(String baodan){
		if(StringUtils.isEmpty(baodan)||baodan.length()<15){
			return "无";
		}
		String code=baodan.substring(9, 15);
		return compMap.get(code);
	}

/**
 * 导出待审核的订单
 * @param sellerId
 * @return
 */
@Read
public DatasetList<Map<String,String>> exportUnCheckOrderToExcel(){
	OrdersExample orderEx = new OrdersExample();
	Criteria c = orderEx.createCriteria();
	c.andStatusEqualTo(Orders.STATUS_0);
	c.andOrderTypeEqualTo(Orders.ORDER_TYPE_2);
	List<Orders>  orders = ordersMapper.selectByExample(orderEx);
	List<Map<String,String>> result = new ArrayList<Map<String,String>>();
	List<Long> userIds = orders.stream().map(Orders::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
	List<Long> sellerIds = orders.stream().map(Orders::getSellerId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
	userIds.add(0L);
	sellerIds.add(0L);
	
	Map<Long,User> userMap = new HashMap<>();
	UserExample userEx = new UserExample();
	userEx.createCriteria().andIdIn(userIds);
	userMapper.selectByExample(userEx).forEach(user -> {
		userMap.put(user.getId(), user);
	});
	
	Map<Long,Seller> sellerMap = new HashMap<>();
	SellerExample sellerEx = new SellerExample();
	sellerEx.createCriteria().andIdIn(sellerIds);
	sellerMapper.selectByExample(sellerEx).forEach(seller -> {
		sellerMap.put(seller.getId(), seller);
	});
	
	//List<String> chepais = new ArrayList<String>();
	Map<Long,String> chepaiMap = new HashMap<>();
	UserInfoExample userInfoEx = new UserInfoExample();
	userInfoEx.createCriteria().andUserIdIn(userIds);
	userInfoMapper.selectByExample(userInfoEx).forEach(userInfo -> {
		chepaiMap.put(userInfo.getUserId(), userInfo.getLicense());
		//chepais.add(userInfo.getLicense());
	});
//	Map<String,ImportUserInfo> importInfoMap = new HashMap<>();
//	ImportUserInfoExample importUserEx = new ImportUserInfoExample();
//	importUserEx.createCriteria().andChePaiIn(chepais);
//	importUserInfoMapper.selectByExample(importUserEx).forEach(importUserInfo -> {
//		importInfoMap.put(importUserInfo.getChePai(), importUserInfo);
//	});
	orders.stream().forEach(order ->{
		Map<String,String> res = new HashMap<String,String>();
		res.put("orderTime", new SimpleDateFormat("yyyy-MM-dd").format(order.getCreateTime()));
		//用户以及商家信息
		User u = userMap.get(order.getUserId());
		if(u!=null){
			res.put("userName", u.getNickName());
			res.put("userPhone", u.getName());
		}
		res.put("orderNo", order.getOrderNo());
		res.put("serviceMoney", order.getServiceMoney().toString());
		res.put("expressMoney", "15");//配送费15块钱
		res.put("goodsNum", "1");
		res.put("goodsPrice", order.getMoneyAmount().toString());
		Seller s = sellerMap.get(order.getSellerId());
		res.put("settleType", Objects.equals(s.getSettleType(), Seller.SETTLE_TYPE_0)?"商家代销":"商家直结");
		res.put("sellerAddr", s.getAddressDetail());
		res.put("sellerName", s.getSellerName());
		res.put("sellerPhone", s.getSellerPhone());
		res.put("sellerUserName", s.getName());
		res.put("chepai", chepaiMap.get(order.getUserId()));
		res.put("jiyou", order.getOrderRemark().split(",")[0].replaceAll("_", " x "));
		res.put("jilv", order.getOrderRemark().split(",")[1].replaceAll("_", " x "));
		res.put("remark", order.getOperateRemark());
		result.add(res);
	});
	return DatasetBuilder.fromDataList(result);
}


/**
 * 国寿结算到导出excel
 * @param sellerId
 * @return
 */
@Override
@Read
public DatasetList<Map<String,String>> exportChinaLifeExcel(Long sellerId,String date,String status,String city){
	//获取所取的年月
	int year = Integer.parseInt(date.split("-")[0]);
	int month = Integer.parseInt(date.split("-")[1]);
	OrdersExample orderEx = new OrdersExample();
	Criteria c = orderEx.createCriteria();
	//1.添加商家id筛选条件
	if(sellerId!=null){
		c.andSellerIdEqualTo(sellerId);
	}
	if(StringUtils.isNotEmpty(city)){
		c.andCityEqualTo(city);
	}
	//2.添加订单状态筛选条件(已完成订单)
	if("-1".equals(status)){//导出所有订单
		c.andStatusNotEqualTo(Orders.STATUS_4);
	}else if("3".equals(status)){//导出已完成订单
		c.andStatusEqualTo(Orders.STATUS_3);
	}else{
		c.andStatusEqualTo(Orders.STATUS_3);
	}
	
	//3.添加完成订单时间的筛选条件
	//计算出当前参数所给月数
	Calendar now = Calendar.getInstance(Locale.CHINA);
	now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
	now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
	now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
	now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
	now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
	now.set(Calendar.YEAR, year);
	now.set(Calendar.MONTH, month-1);
	//所传年月的1号凌晨零点
	if("-1".equals(status)){//导出所有订单
		c.andCreateTimeGreaterThanOrEqualTo(now.getTime());
	}else if("3".equals(status)){//导出已完成订单
		c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
	}else{
		c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
	}
	//计算出当前参数所给月数+1(下个月的1号凌晨零点)
	now.set(Calendar.MONTH, month);
	
	if("-1".equals(status)){//导出所有订单
		c.andCreateTimeLessThan(now.getTime());
	}else if("3".equals(status)){//导出已完成订单
		c.andFinishTimeLessThan(now.getTime());
	}else{
		c.andFinishTimeLessThan(now.getTime());
	}
	orderEx.setOrderByClause(OrdersExample.CREATE_TIME_ASC);//按照下单时间排序
	//根据筛选条件查询订单
	List<Orders>  orders = ordersMapper.selectByExample(orderEx);
	List<Map<String,String>> result = new ArrayList<Map<String,String>>();
	//获取用户id的list
	List<Long> userIds = orders.stream().map(Orders::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
	//获取商家id的list
	List<Long> sellerIds = orders.stream().map(Orders::getSellerId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
	userIds.add(0L);
	sellerIds.add(0L);
	
	Map<Long,User> userMap = new HashMap<>();
	UserExample userEx = new UserExample();
	//根据用户id的list筛选条件
	userEx.createCriteria().andIdIn(userIds);
	userMapper.selectByExample(userEx).forEach(user -> {
		userMap.put(user.getId(), user);
	});
	//根据商家id的list筛选条件
	Map<Long,Seller> sellerMap = new HashMap<>();
	SellerExample sellerEx = new SellerExample();
	sellerEx.createCriteria().andIdIn(sellerIds);
	sellerMapper.selectByExample(sellerEx).forEach(seller -> {
		sellerMap.put(seller.getId(), seller);
	});
	//根据用户id的list筛选条件
	List<String> chepais = new ArrayList<String>();
	Map<Long,String> chepaiMap = new HashMap<>();
	UserInfoExample userInfoEx = new UserInfoExample();
	userInfoEx.createCriteria().andUserIdIn(userIds);
	userInfoMapper.selectByExample(userInfoEx).forEach(userInfo -> {
		chepaiMap.put(userInfo.getUserId(), userInfo.getLicense());
		chepais.add(userInfo.getLicense());
	});
	//根据车牌list查询保单信息
	Map<String,ImportUserInfo> importInfoMap = new HashMap<>();
	ImportUserInfoExample importUserEx = new ImportUserInfoExample();
	importUserEx.createCriteria().andChePaiIn(chepais);
	importUserInfoMapper.selectByExample(importUserEx).forEach(importUserInfo -> {
		importInfoMap.put(importUserInfo.getChePai(), importUserInfo);
	});
	//组织返回的参数
	Orders o = new Orders();
	o.setMoneyAmount(new BigDecimal("0"));
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	orders.stream().forEach(order ->{
		Map<String,String> res = new HashMap<String,String>();
		//订单完成时间
		if("-1".equals(status)){//导出所有订单
			res.put("orderTime", sd.format(order.getCreateTime()));
		}else if("3".equals(status)){//导出已完成订单
			res.put("orderTime", sd.format(order.getFinishTime()));
		}else{
			res.put("orderTime", sd.format(order.getFinishTime()));
		}
		//订单号
		res.put("orderNo", order.getOrderNo());
		//服务费
		res.put("serviceMoney", order.getServiceMoney().toString());
		//配送费
		res.put("expressMoney", "0");//配送费15块钱
		//跟国寿的订单总价格
		o.setMoneyAmount(o.getMoneyAmount().add(order.getMoneyAmount().add(order.getServiceMoney())));
		String orderType = "";
		if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_0)){
			orderType = "电瓶";
		}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_1)){
			orderType = "轮胎";
		}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_2)){
			orderType = "小保养";
		}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_3)){
			orderType = "洗车";
		}else if(Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_4)){
			orderType = "喷漆";
		}
		//订单类型(小保养;喷漆;洗车等等)
		res.put("orderType", orderType);
		//
		res.put("city", order.getCity());
		//
		res.put("orderGoods", order.getOrderRemark());
		//
		res.put("goodsNum", order.getGoodsNum()+"");
		//
		res.put("goodsPrice", order.getMoneyAmount().toString());
		
		if(order.getSellerId()!=null && order.getSellerId().equals(order.getSupplyId())){
			res.put("sellerGoodsPrice", order.getMoneyAmount().toString());
		}else{
			res.put("sellerGoodsPrice", "0");
		}
		
		res.put("oneGoodsPrice", (Float.parseFloat(res.get("goodsPrice")) / Integer.parseInt(res.get("goodsNum")))+"");
		
		//用户以及商家信息
		User u = userMap.get(order.getUserId());
		if(u!=null){
			//用户姓名
			res.put("userName", u.getNickName());
			//用户手机号
			res.put("userPhone", u.getName());
		}
		Seller s = sellerMap.get(order.getSellerId());
		if(s!=null){
			//商家地址
			res.put("sellerAddr", s.getAddressDetail());
			//商家名称
			res.put("sellerName", s.getSellerName());
			//商家手机号
			res.put("sellerPhone", s.getSellerPhone());
		}else{
			res.put("sellerAddr", "--");
			//商家名称
			res.put("sellerName", "--");
			//商家手机号
			res.put("sellerPhone", "--");
		}
		
		//车辆信息
		try{
			String chepai = chepaiMap.get(order.getUserId());
			ImportUserInfo importInfo = importInfoMap.get(chepai);
			//保单号
			res.put("baodan", importInfo.getBaoDan());
			//下保单的网点
			if(StringUtils.isEmpty(importInfo.getBaoDan())){
				res.put("company", "");
			}else{
				res.put("company",fanCompany(importInfo.getBaoDan()));
			}
			//车辆的一些信息
			res.put("autoBrand", importInfo.getAutoBrand());
			res.put("autoType", importInfo.getAutoType());
			res.put("engineDisp", importInfo.getEngineDisp());
			res.put("year", importInfo.getProductYear());
			res.put("chepai", importInfo.getChePai());
			res.put("chejia", importInfo.getCheJia());
			res.put("city", importInfo.getAddress());
			res.put("mileage", String.valueOf(order.getMileage()));
		}catch(Exception e){
			e.printStackTrace();
			Log4jHelper.getLogger().error("读取导入保单信息错误!");
		}
		
		if(order.getOrderType() == Orders.ORDER_TYPE_2){//小保养订单不同
			res.put("expressMoney", "15");//配送费15块钱
			if(order.getOrderRemark().indexOf("比亚迪专用")!=-1){//比亚迪专用机油没有配送费
				res.put("expressMoney", "0");//配送费15块钱
			}
			res.put("orderGoods", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[0]);
			res.put("goodsNum", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[1]);
			res.put("goodsPrice", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[2]);
			res.put("oneGoodsPrice", (Float.parseFloat(res.get("goodsPrice")) / Integer.parseInt(res.get("goodsNum")))+"");
			res.put("sellerGoodsPrice", "0");
			if(order.getOrderRemark().split(",")[0].split("\\|").length>1){
				Map<String,String> jiyou2 = new HashMap<String,String>();
				jiyou2.put("expressMoney", "0");
				jiyou2.put("orderTime", res.get("orderTime"));
				jiyou2.put("orderNo", res.get("orderNo"));
				jiyou2.put("serviceMoney", "0");
				jiyou2.put("orderType", orderType);
				jiyou2.put("orderGoods", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[0]);
				jiyou2.put("goodsNum", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[1]);
				jiyou2.put("goodsPrice", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[2]);
				jiyou2.put("oneGoodsPrice", (Float.parseFloat(jiyou2.get("goodsPrice")) / Integer.parseInt(jiyou2.get("goodsNum")))+"");
				jiyou2.put("sellerGoodsPrice", "0");
				
				//填充其他非必要参数
				jiyou2.put("userName", "--");
				jiyou2.put("userPhone", "--");
				jiyou2.put("sellerAddr", "--");
				jiyou2.put("sellerName", "--");
				jiyou2.put("sellerPhone", "--");
				jiyou2.put("baodan", res.get("baodan"));
				jiyou2.put("company", res.get("company"));
				jiyou2.put("autoBrand", "--");
				jiyou2.put("autoType", "--");
				jiyou2.put("engineDisp", "--");
				jiyou2.put("year", "--");
				jiyou2.put("chepai", "--");
				jiyou2.put("mileage", "--");
				result.add(jiyou2);
			}
			Map<String,String> jilv = new HashMap<String,String>();
			jilv.put("orderTime", res.get("orderTime"));
			jilv.put("expressMoney", "0");
			jilv.put("orderNo", res.get("orderNo"));
			jilv.put("serviceMoney", "0");
			jilv.put("orderType", orderType);
			jilv.put("orderGoods", order.getOrderRemark().split(",")[1].split("_")[0]);
			jilv.put("goodsNum", order.getOrderRemark().split(",")[1].split("_")[1]);
			jilv.put("goodsPrice", order.getOrderRemark().split(",")[1].split("_")[2]);
			if(order.getSellerId()!=null && order.getSellerId().equals(order.getSupplyId())){
				jilv.put("sellerGoodsPrice", order.getJilvPrice().toString());
			}else{
				jilv.put("sellerGoodsPrice", "0");
			}
			
			//填充其他非必要参数
			jilv.put("userName", "--");
			jilv.put("userPhone", "--");
			jilv.put("sellerAddr", "--");
			jilv.put("sellerName", "--");
			jilv.put("sellerPhone", "--");
			jilv.put("baodan", res.get("baodan"));
			jilv.put("company", res.get("company"));
			jilv.put("autoBrand", "--");
			jilv.put("autoType", "--");
			jilv.put("engineDisp", "--");
			jilv.put("year", "--");
			jilv.put("chepai", "--");
			jilv.put("mileage", "--");
			result.add(jilv);
		}
		result.add(res);
	});
	Map<String,String> orderSummary = new HashMap<String,String>();
	orderSummary.put("orderTime", "本月订单总计："+o.getMoneyAmount().toString());
	result.add(orderSummary);
	return DatasetBuilder.fromDataList(result);
}
	
	/**
	 * 商家结算到导出excel
	 * @param sellerId
	 * @return
	 */
	@Override
	@Read
	public DatasetList<Map<String,String>> exportSellerExcel(Long sellerId,String date){
		Seller s = sellerMapper.selectByPrimaryKey(sellerId);
		if(Objects.isNull(s)){
			throw new BusinessException("商家不存在!!");
		}
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andSellerIdEqualTo(sellerId);
		c.andStatusEqualTo(Orders.STATUS_3);
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
		//计算出当前参数所给月数+1
		now.set(Calendar.MONTH, month);
		c.andFinishTimeLessThan(now.getTime());
		
		c.andOrderTypeNotEqualTo(Orders.ORDER_TYPE_3);//不能导出洗车订单
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		String status = "未结算";
		try{
			SellerReportExample reportEx = new SellerReportExample();
			reportEx.createCriteria()
			.andSellerIdEqualTo(sellerId)
			.andYearEqualTo(year)
			.andMonthEqualTo(month);
			reportEx.setLimitStart(0);
			reportEx.setLimitEnd(1);
			List<SellerReport>  reportList = sellerReportMapper.selectByExample(reportEx);
			if(!reportList.isEmpty()){
				if(Objects.equals(SellerReport.STATUS_3, reportList.get(0).getStatus())){
					status = "平台已结算";
				}else if(Objects.equals(SellerReport.STATUS_1, reportList.get(0).getStatus())){
					status = "商家已结算";
				}
			}
		}catch(Exception e){
			status = "未结算";
		}
		
		List<Long> userId = orders.stream().map(Orders::getUserId).distinct().collect(Collectors.toList());
		userId.add(0L);
		
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdIn(userId);
		
		List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoEx);
		Map<Long,String> chePaiMap = new HashMap<Long,String>();
		userInfoList.forEach(userInfo -> {
			chePaiMap.put(userInfo.getUserId(), userInfo.getLicense());
		});
		
		final String statusFinal = status;
		orders.stream().forEach(order ->{
			Map<String,String> res = new HashMap<String,String>();
			res.put("orderTime", new SimpleDateFormat("yyyy-MM-dd").format(order.getFinishTime()));
			res.put("orderNo", order.getOrderNo());
			res.put("serviceMoney", order.getServiceMoney().toString());
			res.put("chepai", chePaiMap.get(order.getUserId()));
			String orderType = "";
			if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_0)){
				orderType = "电瓶";
			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_1)){
				orderType = "轮胎";
			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_2)){
				orderType = "小保养";
			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_3)){
				orderType = "洗车";
			}else if(Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_4)){
				orderType = "喷漆";
			}
			res.put("orderType", orderType);
			res.put("orderGoods", order.getOrderRemark());
			res.put("goodsNum", "1");
			res.put("goodsPrice", order.getMoneyAmount().toString());
			
			res.put("sellerAddr", s.getAddressDetail());
			res.put("sellerName", s.getSellerName());
			res.put("sellerPhone", s.getSellerPhone());
			res.put("status", statusFinal);
			
			if(order.getOrderType() == Orders.ORDER_TYPE_2){//小保养订单不同
				res.put("orderGoods", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[0]);
				res.put("goodsNum", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[1]);
/*				if(Objects.equals(sellerId, order.getSupplyId())){
					res.put("goodsPrice", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[2]);
				}else{*/
					res.put("goodsPrice", "0");
				//}
				if(order.getOrderRemark().split(",")[0].split("\\|").length>1){
					Map<String,String> jiyou2 = new HashMap<String,String>();
					jiyou2.put("orderTime", res.get("orderTime"));
					jiyou2.put("orderNo", res.get("orderNo"));
					//修改
					jiyou2.put("serviceMoney", "0.00");
					jiyou2.put("orderType", orderType);
					jiyou2.put("orderGoods", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[0]);
					jiyou2.put("goodsNum", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[1]);
/*					if(Objects.equals(sellerId, order.getSupplyId())){
						jiyou2.put("goodsPrice", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[2]);
					}else{*/
						jiyou2.put("goodsPrice","0");
					//}
					result.add(jiyou2);
				}
				Map<String,String> jilv = new HashMap<String,String>();
				jilv.put("orderTime", res.get("orderTime"));
				jilv.put("orderNo", res.get("orderNo"));
				//修改
				jilv.put("serviceMoney", "0.00");
				jilv.put("orderType", orderType);
				jilv.put("orderGoods", order.getOrderRemark().split(",")[1].split("_")[0]);
				jilv.put("goodsNum", order.getOrderRemark().split(",")[1].split("_")[1]);
				if(Objects.equals(sellerId, order.getSupplyId())){
					//jilv.put("goodsPrice", order.getOrderRemark().split(",")[1].split("_")[2]);
					jilv.put("goodsPrice",order.getJilvPrice().toString());
				}else{
					jilv.put("goodsPrice","0");
				}
				result.add(jilv);
			}
			result.add(res);
		});
		return DatasetBuilder.fromDataList(result);
	}
	
	/**
	 * 所有商家已完成订单的导出excel
	 * @param date
	 * @return
	 */
	@Override
	@Read
	public DatasetList<Map<String,String>> exportAllSellerExcel(String date){
		//分解年月
		Date starTime = null;
		Date endTime = null;
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		if(date.contains("年")){
			int year=Integer.valueOf(date.substring(0, date.length()-1));
			now.set(Calendar.YEAR, year);
			starTime=now.getTime();
			now.add(Calendar.YEAR, 1);
			endTime=now.getTime();
		}else{
			int year = Integer.parseInt(date.split("-")[0]);
			int month = Integer.parseInt(date.split("-")[1]);
			now.set(Calendar.YEAR, year);
			now.set(Calendar.MONTH, month-1);
			starTime=now.getTime();
			now.set(Calendar.MONTH, month);
			endTime=now.getTime();
		}
		//根据订单状态(已完成),完成时间(某个月内),
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andStatusEqualTo(Orders.STATUS_3);
		//计算出当前参数所给月数
		c.andFinishTimeGreaterThanOrEqualTo(starTime);
		//计算出当前参数所给月数+1
		c.andFinishTimeLessThan(endTime);
		c.andOrderTypeNotEqualTo(Orders.ORDER_TYPE_3);//洗车订单不需要跟商家结算
		//根据商家排序
		orderEx.setOrderByClause(OrdersExample.SELLER_ID_ASC);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		
		//查看这个月的已结算的商家和未结算的商家
		
		Map<String, String> statusMap = new HashMap<String,String>();
		SellerReportExample reportEx = new SellerReportExample();
		com.emate.shop.business.model.SellerReportExample.Criteria cr = reportEx.createCriteria();
		if(date.contains("年")){
			int year=Integer.valueOf(date.substring(0, date.length()-1));
			cr.andYearEqualTo(year);
		}else{
			int year = Integer.parseInt(date.split("-")[0]);
			int month = Integer.parseInt(date.split("-")[1]);
			cr.andYearEqualTo(year);
			cr.andMonthEqualTo(month);
		}
		
		
		List<SellerReport>  reportList = sellerReportMapper.selectByExample(reportEx);
		if(!reportList.isEmpty()){
			for(SellerReport sellerReport: reportList){
					String sellerKey = String.valueOf(sellerReport.getSellerId())
							+"_"+String.valueOf(sellerReport.getMonth());
					if(Objects.equals(SellerReport.STATUS_3, reportList.get(0).getStatus())){
						statusMap.put(sellerKey, "平台已结算");
					}else if(Objects.equals(SellerReport.STATUS_1, reportList.get(0).getStatus())){
						statusMap.put(sellerKey, "商家已结算");
					}else{
						statusMap.put(sellerKey, "未结算");
					}
			}
		}
		//查询每个订单的车牌信息
		List<Long> userId = orders.stream().map(Orders::getUserId).distinct().collect(Collectors.toList());
		userId.add(0L);
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdIn(userId);
		List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoEx);
		Map<Long,String> chePaiMap = new HashMap<Long,String>();
		userInfoList.forEach(userInfo -> {
			chePaiMap.put(userInfo.getUserId(), userInfo.getLicense());
		});
		
		//查询所有商家详细信息
		List<Long> sellerIds = orders.stream().map(Orders::getSellerId).distinct().collect(Collectors.toList());
		sellerIds.add(0L);
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andIdIn(sellerIds);
		List<Seller> sellers = sellerMapper.selectByExample(sellerEx);
		Map<Long,Seller> sellerMap = new HashMap<Long,Seller>();
		sellers.forEach(seller -> {
			sellerMap.put(seller.getId(), seller);
		});
		//查询所有商家相信详细信息;
		SellerInfoExample sellerInfoEx = new SellerInfoExample();
		sellerInfoEx.createCriteria().andSellerIdIn(sellerIds);
		List<SellerInfo> sellerInfos = sellerInfoMapper.selectByExample(sellerInfoEx);
		Map<Long,SellerInfo> sellerInfoMap = new HashMap<Long,SellerInfo>();
		sellerInfos.forEach(sellerInfo ->{
			sellerInfoMap.put(sellerInfo.getSellerId(), sellerInfo);
		});
		//循环订单,组织返回结果
		orders.stream().forEach(order ->{
			Map<String,String> res = new HashMap<String,String>();
			res.put("orderTime", new SimpleDateFormat("yyyy-MM-dd").format(order.getFinishTime()));
			res.put("orderNo", order.getOrderNo());
			res.put("serviceMoney", order.getServiceMoney().toString());
			res.put("chepai", chePaiMap.get(order.getUserId()));
			String orderType = "";
			if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_0)){
				orderType = "电瓶";
			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_1)){
				orderType = "轮胎";
			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_2)){
				orderType = "小保养";
			}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_3)){
				orderType = "洗车";
			}else if(Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_4)){
				orderType = "喷漆";
			}
			res.put("orderType", orderType);
			res.put("orderGoods", order.getOrderRemark());
			res.put("goodsNum", "1");
			res.put("goodsPrice", order.getMoneyAmount().toString());
			
			res.put("sellerAddr", sellerMap.get(order.getSellerId()).getAddressDetail());
			res.put("sellerName", sellerMap.get(order.getSellerId()).getSellerName());
			res.put("accountName", sellerInfoMap.get(order.getSellerId()).getAccountName());
			res.put("accountNumber", sellerInfoMap.get(order.getSellerId()).getAccountNumber());
			res.put("account", sellerInfoMap.get(order.getSellerId()).getAccount());
			now.setTime(order.getFinishTime());
			String sellerKey = String.valueOf(order.getSellerId())
					+"_"+String.valueOf(now.get(Calendar.MONTH)+1);
			//若结算状态map为空,或者没查找到该商家的结算信息
			if(statusMap.isEmpty()||StringUtils.isBlank(statusMap.get(sellerKey))){
				res.put("status", "未结算");
			}else{
				res.put("status", statusMap.get(sellerKey));
			}
			//计算每一单的结算金额
			BigDecimal sellerPriceTotel = new BigDecimal(0);
			sellerPriceTotel = sellerPriceTotel.add(order.getServiceMoney());
			if(order.getSellerId().equals(order.getSupplyId())){//表示是商家发货
				if(Orders.ORDER_TYPE_2.equals(order.getOrderType())){
					sellerPriceTotel = sellerPriceTotel.add(order.getJilvPrice());
				}else if(Orders.ORDER_TYPE_4.equals(order.getOrderType())){
					sellerPriceTotel = sellerPriceTotel.add(order.getMoneyAmount());
				}
			}
			res.put("jiePrice", sellerPriceTotel.toString());
			if(order.getOrderType() == Orders.ORDER_TYPE_2){//小保养订单不同
				res.put("orderGoods", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[0]);
				res.put("goodsNum", order.getOrderRemark().split(",")[0].split("\\|")[0].split("_")[1]);
				res.put("goodsPrice", "0");
				if(order.getOrderRemark().split(",")[0].split("\\|").length>1){
					Map<String,String> jiyou2 = new HashMap<String,String>();
					jiyou2.put("orderTime", res.get("orderTime"));
					jiyou2.put("orderNo", res.get("orderNo"));
					//修改
					jiyou2.put("serviceMoney", "0.00");
					jiyou2.put("orderType", orderType);
					jiyou2.put("orderGoods", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[0]);
					jiyou2.put("goodsNum", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[1]);
/*					if(Objects.equals(sellerId, order.getSupplyId())){
						jiyou2.put("goodsPrice", order.getOrderRemark().split(",")[0].split("\\|")[1].split("_")[2]);
					}else{*/
						jiyou2.put("goodsPrice","0");
					//}
					result.add(jiyou2);
				}
				Map<String,String> jilv = new HashMap<String,String>();
				jilv.put("orderTime", res.get("orderTime"));
				jilv.put("orderNo", res.get("orderNo"));
				//修改
				jilv.put("serviceMoney", "0.00");
				jilv.put("orderType", orderType);
				jilv.put("orderGoods", order.getOrderRemark().split(",")[1].split("_")[0]);
				jilv.put("goodsNum", order.getOrderRemark().split(",")[1].split("_")[1]);
				if(Objects.equals(order.getSellerId(), order.getSupplyId())){
					//jilv.put("goodsPrice", order.getOrderRemark().split(",")[1].split("_")[2]);
					jilv.put("goodsPrice",order.getJilvPrice().toString());
				}else{
					jilv.put("goodsPrice","0");
				}
				result.add(jilv);
			}
			result.add(res);
		});
		return DatasetBuilder.fromDataList(result);
	}

	@Override
	@Write
	public DatasetSimple<Map<String, Object>> sellerComfirmReport(Long sellerId, String date,String invoice) {
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		SellerReportExample reportEx = new SellerReportExample();
		reportEx.createCriteria()
		.andSellerIdEqualTo(sellerId)
		.andYearEqualTo(year)
		.andMonthEqualTo(month);
		reportEx.setLimitStart(0);
		reportEx.setLimitEnd(1);
		List<SellerReport>  reportList = sellerReportMapper.selectByExample(reportEx);
		Map<String,Object> result = new HashMap<String,Object>();
		if(reportList.isEmpty()){
			//获取当前年月
			Calendar now = Calendar.getInstance(Locale.CHINA);
			int i = now.get(Calendar.MONTH);
			if(month==(i+1)){
				throw new BusinessException("您不能结算当前月的订单信息");
			}
			//获取开始和结束时间
			OrdersExample orderEx = new OrdersExample();
			Criteria c = orderEx.createCriteria();
			//计算出当前参数所给月数
			now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
			now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
			now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
			now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
			now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
			now.set(Calendar.YEAR, year);
			now.set(Calendar.MONTH, month-1);
			c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
			//计算出当前参数所给月数+1
			now.set(Calendar.MONTH, month);
			c.andFinishTimeLessThan(now.getTime());
			//根据条件查询订单
			c.andSellerIdEqualTo(sellerId);
			c.andStatusEqualTo(Orders.STATUS_3);
			//不能包括洗车订单
			//c.andOrderTypeNotEqualTo(Orders.ORDER_TYPE_3);
			c.andSellerIdNotEqualTo(0L);
			List<Orders>  orders = ordersMapper.selectByExample(orderEx);
			//根据订单计算这个月的商家总价格
			BigDecimal sellerPriceTotel = new BigDecimal(0);
			for(Orders order:orders){
				sellerPriceTotel = sellerPriceTotel.add(order.getServiceMoney());
				if(sellerId.equals(order.getSupplyId())){//表示是商家发货
					if(Orders.ORDER_TYPE_2.equals(order.getOrderType())){
						sellerPriceTotel = sellerPriceTotel.add(order.getJilvPrice());
					}else if(Orders.ORDER_TYPE_4.equals(order.getOrderType())){
						sellerPriceTotel = sellerPriceTotel.add(order.getMoneyAmount());
					}
				}
			}
			SellerReport report = new SellerReport();
			report.setSellerId(sellerId);
			report.setYear(year);
			report.setMonth(month);
			report.setStatus(SellerReport.STATUS_0);
			report.setTotalMoney(sellerPriceTotel.multiply(new BigDecimal("0.92")));
			report.setInvoice(SellerReport.INVOICE_0);
			report.setCreateTime(new Date());
			report.setUpdateTime(new Date());
			report.setOrderCount(orders.size());
			result.put("success", sellerReportMapper.insertSelective(report)==1);
			return DatasetBuilder.fromDataSimple(result);
		}else{
			reportList.get(0).setStatus(SellerReport.STATUS_1);
			if("1".equals(invoice)){
				BigDecimal totalMoney = reportList.get(0).getTotalMoney();
				BigDecimal divide = totalMoney.divide(new BigDecimal("0.92"),2);
				reportList.get(0).setTotalMoney(divide);
			}
			reportList.get(0).setInvoice("0".equals(invoice)?SellerReport.INVOICE_0:SellerReport.INVOICE_1);
			reportList.get(0).setUpdateTime(new Date());
			result.put("success", sellerReportMapper.updateByPrimaryKeySelective(reportList.get(0))==1);
			return DatasetBuilder.fromDataSimple(result);
		}
	}
	@Override
	@Write
	public DatasetSimple<Map<String, String>> sellerComfirmReportQuartz(){
		//查询上个月的完成订单
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		//获取当前年月
		Calendar now = Calendar.getInstance(Locale.CHINA);
		//计算这个月的一号
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		c.andFinishTimeLessThan(now.getTime());
		//上个月的1号
		now.add(Calendar.MONTH, -1);
		c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		//根据条件查询订单
		c.andStatusEqualTo(Orders.STATUS_3);
		//c.andOrderTypeNotEqualTo(Orders.ORDER_TYPE_3);//洗车订单不需要跟商家结算
		c.andSellerIdNotEqualTo(0L);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		Set<Long> sellerIds = new HashSet<Long>();
		for(Orders order:orders){
			sellerIds.add(order.getSellerId());
		}
		String date = String.valueOf(year)+"-"+month;
		final AtomicInteger success = new AtomicInteger(0);
		final AtomicInteger fail = new AtomicInteger(0);
		for(Long sellerId : sellerIds){
			DatasetSimple<Map<String, Object>> sellerReports = sellerComfirmReport(sellerId,date,"0");
			if(sellerReports.isSuccess()
					&&sellerReports.getData().get("success")!=null
					&&(boolean)sellerReports.getData().get("success")){
				success.incrementAndGet();
			}else{
				fail.incrementAndGet();
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", success.intValue()+"");
		result.put("fail", fail.intValue()+"");
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Read
	public DatasetSimple<Map<String,Object>> adminHomeSummary(Long adminId) {
		Map<String,Object> result = new HashMap<String,Object>();
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		//今日凌晨零点
		Date today = now.getTime();
		now.add(Calendar.DAY_OF_MONTH, -1);
		//昨日凌晨零点
		Date yesterday = now.getTime();
		
		//已完成订单
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		c.andStatusEqualTo(Orders.STATUS_3);
		result.put("finishOrder", ordersMapper.countByExample(orderEx));
		
		//导入保单总数
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		result.put("importPolicy", importUserInfoMapper.countByExample(importUserInfoEx));
		
		//已加盟商家总数
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andAuditStatusEqualTo(Seller.AUDIT_STATUS_2);
		result.put("seller", sellerMapper.countByExample(sellerEx));
		
		//已注册会员总数
		UserExample userEx = new UserExample();
		result.put("member", userMapper.countByExample(userEx));
		
		//已绑定会员总数
		UserInfoExample userInfoEx = new UserInfoExample();
		com.emate.shop.business.model.UserInfoExample.Criteria userInfoC = userInfoEx.createCriteria();
		userInfoC.andLicenseIsNotNull();
		userInfoC.andLicenseNotEqualTo("");
		result.put("boundMember", userInfoMapper.countByExample(userInfoEx));
		
		//今日创建的订单数
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andCreateTimeGreaterThanOrEqualTo(today);
		result.put("todayOrder", ordersMapper.countByExample(orderEx));
		
		//今日创建的小保养的订单数
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andCreateTimeGreaterThanOrEqualTo(today);
		c.andOrderTypeEqualTo(Orders.ORDER_TYPE_2);
		result.put("todayByOrder", ordersMapper.countByExample(orderEx));
				
		//今日创建的洗车的订单数
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andCreateTimeGreaterThanOrEqualTo(today);
		c.andOrderTypeEqualTo(Orders.ORDER_TYPE_3);
		result.put("todayXiOrder", ordersMapper.countByExample(orderEx));
		
		//昨日创建的订单数
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andCreateTimeBetween(yesterday, today);
		result.put("yesterdayOrder", ordersMapper.countByExample(orderEx));
		
		//昨日创建的小保养的订单数
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andCreateTimeBetween(yesterday, today);
		c.andOrderTypeEqualTo(Orders.ORDER_TYPE_2);
		result.put("yesterdayByOrder", ordersMapper.countByExample(orderEx));
		
		//昨日创建的洗车的订单数
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andCreateTimeBetween(yesterday, today);
		c.andOrderTypeEqualTo(Orders.ORDER_TYPE_3);
		result.put("yesterdayXiOrder", ordersMapper.countByExample(orderEx));
		
		//国寿交易总金额
		result.put("allMoney", definedMapper.queryTotalTradeAmount().intValue());
		
		//订单总量
		orderEx.clear();
		c = orderEx.createCriteria();
		c.andStatusNotEqualTo(Orders.STATUS_4);
		result.put("allOrders", ordersMapper.countByExample(orderEx));
		//昨日绑定会员数
		userInfoEx.clear();
		userInfoC=userInfoEx.createCriteria();
		userInfoC.andLicenseIsNotNull();
		userInfoC.andLicenseNotEqualTo("");
		userInfoC.andCreateTimeBetween(yesterday, today);
		result.put("yesterdayMember", userInfoMapper.countByExample(userInfoEx));
		//今日绑定会员数
		userInfoEx.clear();
		userInfoC=userInfoEx.createCriteria();
		userInfoC.andLicenseIsNotNull();
		userInfoC.andLicenseNotEqualTo("");
		userInfoC.andCreateTimeGreaterThanOrEqualTo(today);
		result.put("todayMember", userInfoMapper.countByExample(userInfoEx));
		
		//今日导入保单
		importUserInfoEx.clear();
		com.emate.shop.business.model.ImportUserInfoExample.Criteria importUserInfoC= importUserInfoEx.createCriteria();
		importUserInfoC.andCreateTimeGreaterThan(today);
		result.put("todayPolicy", importUserInfoMapper.countByExample(importUserInfoEx));
		//昨日导入保单
		importUserInfoEx.clear();
		importUserInfoC = importUserInfoEx.createCriteria();
		importUserInfoC.andCreateTimeBetween(yesterday, today);
		result.put("yesterdayPolicy", importUserInfoMapper.countByExample(importUserInfoEx));
		
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(adminId);
		result.put("adminName", systemUser.getUserName());
		result.put("lastLoginTime", systemUser.getLastLoginTime());
		result.put("lastLoginIp", systemUser.getLastLoginIp());
		result.put("loginTime", systemUser.getLoginTime());
		//result.put("lastLoginIP", systemUser.get);
		if(systemUser.getUserName().equals("admin")){
			result.put("roleName", "超级管理员");
		}else{
			SystemUserRoleRelationExample userRoleEx = new SystemUserRoleRelationExample();
			userRoleEx.createCriteria().andUserIdEqualTo(adminId);
			List<Long> roleIds = systemUserRoleRelationMapper
					.selectByExample(userRoleEx)
					.stream()
					.map(SystemUserRoleRelation::getRoleId)
					.distinct()
					.collect(Collectors.toList());
			roleIds.add(0L);
            SystemRoleExample systemRoleExample = new SystemRoleExample();
            systemRoleExample.or().andIdIn(roleIds);
            List<SystemRole> systemRoles = this.systemRoleMapper
                    .selectByExample(systemRoleExample);
            if(!systemRoles.isEmpty()){
            	result.put("roleName", systemRoles.get(0).getName());
            }else{
            	result.put("roleName", "--");
            }
		}
		return DatasetBuilder.fromDataSimple(result);
	}
	
/*	@Override
	@Read
	public DatasetSimple<Map<String,String>> hasNewOrder(String unid,String userName,String ip,String userAgent) {
		
		if(StringUtils.isNotEmpty(unid) && StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(userAgent)){
			try{
				AdminLogExample ex = new AdminLogExample();
				ex.createCriteria().andUnidEqualTo(unid);
				adminLogMapper.deleteByExample(ex);
				//插入活跃统计
				AdminLog adminLog = new AdminLog();
				adminLog.setUnid(unid);
				adminLog.setUserName(userName);
				adminLog.setIpAddress(ip);
				adminLog.setUserAgent(userAgent);
				adminLog.setCreateTime(new Date());
				adminLog.setUpdateTime(new Date());
				adminLogMapper.insertSelective(adminLog);
			}catch(Exception e){
				e.printStackTrace();
				Log4jHelper.getLogger().info("插入admin活跃统计日志失败！");
			}
		}
		
		NewOrderAlertExample newOrder = new NewOrderAlertExample();
		if(newOrderAlertMapper.countByExample(newOrder)>0){
			Map<String,String> result = new HashMap<String,String>();
			result.put("result", "success");
			return DatasetBuilder.fromDataSimple(result);
		}
		return DatasetBuilder.fromMessageSimple("");
	}*/
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String,String>> deleteNewOrderAlter() {
		NewOrderAlertExample newOrder = new NewOrderAlertExample();
		newOrderAlertMapper.deleteByExample(newOrder);
		Map<String,String> result = new HashMap<String,String>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> deliverOrder(String orderNo) {
		OrdersExample orderEx = new OrdersExample();
		orderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		if(orders.isEmpty()){
			throw new BusinessException("查询不到订单！");
		}
		Orders order = orders.get(0);
		order.setDeliverTime(new Date());
		order.setUpdateTime(new Date());
		if(ordersMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("更新订单发货时间失败！");
		}
		
		//记录订单日志
		try{
			OrderTraceExample orderTraceEx = new OrderTraceExample();
			orderTraceEx.createCriteria().andOrderNoEqualTo(orderNo);
			List<OrderTrace> orderTraceList = orderTraceMapper.selectByExample(orderTraceEx);
			if(!orderTraceList.isEmpty()){
				OrderTrace trace = orderTraceList.get(0);
				trace.setUpdateTime(new Date());
				trace.setDeliverTime(new Date());
				orderTraceMapper.updateByExample(trace, orderTraceEx);
			}
			Map<String, String> result = new HashMap<String,String>();
			result.put("result", "success");
			return DatasetBuilder.fromDataSimple(result);
		}catch(Exception e){
			Log4jHelper.getLogger().error("记录订单日志失败！");
		}
		throw new BusinessException("更新订单发货时间失败！");
	}
	
	@Override
	@Read
	public DatasetSimple<Map<String,Object>> getOrderTrace(String orderNo) {
		OrderTraceExample orderTraceEx = new OrderTraceExample();
		orderTraceEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<OrderTrace> orderTraceList = orderTraceMapper.selectByExample(orderTraceEx);
		OrderExpressExample orderExpressEx = new OrderExpressExample();
		orderExpressEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<OrderExpress> orderExpressList = orderExpressMapper.selectByExample(orderExpressEx);
		if(!orderTraceList.isEmpty()){
			Map<String, Object> result = new HashMap<String,Object>();
			OrderTrace orderTrace = orderTraceList.get(0);
			result.put("id", orderTrace.getId());
			result.put("orderNo", orderTrace.getOrderNo());
			result.put("newTime", orderTrace.getNewTime());
			result.put("confirmTime", orderTrace.getConfirmTime());
			result.put("deliverTime", orderTrace.getDeliverTime());
			result.put("completTime", orderTrace.getCompletTime());
			result.put("createTime", orderTrace.getCreateTime());
			result.put("updateTime", orderTrace.getUpdateTime());
			if(!orderExpressList.isEmpty()){
				OrderExpress orderExpress = orderExpressList.get(0);
				result.put("expressNo",orderExpress.getExpressNo());
			}else{
				result.put("expressNo",null);
			}
			return DatasetBuilder.fromDataSimple(result);
		}
		throw new BusinessException("没有改订单记录！"); 
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> createFixOrder(FixOrder fixOrder) {
		//维修订单的id为空
		if(fixOrder.getId()==null){
			//赋值车牌大写
			fixOrder.setChePai(fixOrder.getChePai().toUpperCase());
			Calendar now = Calendar.getInstance(Locale.CHINA);
			now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
			now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
			now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
			now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
			
			FixOrderExample fixEx = new FixOrderExample();
			fixEx.createCriteria().andCreateTimeGreaterThanOrEqualTo(now.getTime()).andChePaiEqualTo(fixOrder.getChePai());
			if(fixOrderMapper.countByExample(fixEx)>0){
				throw new BusinessException("一天内一个车牌号只能有效记录入一次!"); 
			}
//			ImportUserInfoExample importEx = new ImportUserInfoExample();
//			importEx.createCriteria().andChePaiEqualTo(fixOrder.getChePai());
//			List<ImportUserInfo> importList = importUserInfoMapper.selectByExample(importEx);
//			if(importList.isEmpty()){
//				throw new BusinessException("找不到保单信息！"); 
//			}
//			UserInfoExample userInfoEx = new UserInfoExample();
//			userInfoEx.createCriteria().andLicenseEqualTo(fixOrder.getChePai());
//			
//			List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoEx);
//			if(userInfos.isEmpty()){
//				throw new BusinessException("没有用户绑定该车型！"); 
//			}
			//生成并赋值订单编号
			fixOrder.setOrderNo(RandomUtil.getOrderSn());
			//赋值userId
			fixOrder.setUserId(0L);
			//fixOrder.setUserPhone(userMapper.selectByPrimaryKey(userInfos.get(0).getUserId()).getName());
			//赋值维修订单的创建时间
			fixOrder.setCreateTime(new Date());
			//赋值维修订单的更新时间
			fixOrder.setUpdateTime(new Date());
			//fix_order表添加一条记录
			if(fixOrderMapper.insertSelective(fixOrder)!=1){
				throw new BusinessException("新增维修单失败！");
			}
		}else{
			//设置维修订单的修改时间
			fixOrder.setUpdateTime(new Date());
			//处在选择商家步骤
			if(fixOrder.getStep()==FixOrder.STEP_3){
				//赋值待定损状态
				fixOrder.setAuditStatus(FixOrder.AUDIT_STATUS_1);
				//这里记录商家完成了多少单，也可以在完成的时候记录
				if(fixOrder.getSellerId()!=null){
					try{
						Seller  s = sellerMapper.selectByPrimaryKey(fixOrder.getSellerId());
						s.setOrderCount(s.getOrderCount()+1);
						sellerMapper.updateByPrimaryKeySelective(s);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				
			}
			//修改维修订单
			if(fixOrderMapper.updateByPrimaryKeySelective(fixOrder)!=1){
				throw new BusinessException("更新维修单失败！");
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if(fixOrder.getId()!=null){
			result.put("order", fixOrderMapper.selectByPrimaryKey(fixOrder.getId()));
		}else{
			result.put("order", fixOrder);
		}
		return DatasetBuilder.fromDataSimple(result);
	}
	
	/**
	 * 维修订单列表
	 */
	@Read
	@Override
	 public DatasetList<Map<String,Object>> fiexOrderList(Long userId,String orderStatus,
			 String type,Integer pageNo,Integer pageSize){
			//根据条件查询订单(创建时间,维修订单状态,用户id(业务员还是商户))
			FixOrderExample fixOrderEx = new FixOrderExample();
			//根据创建时间倒序
	    	fixOrderEx.setOrderByClause(FixOrderExample.CREATE_TIME_DESC);
	    	//设置订单状态条件
	    	com.emate.shop.business.model.FixOrderExample.Criteria c = fixOrderEx.createCriteria();
	    	if(StringUtils.isNotEmpty(orderStatus)){
	    		c.andAuditStatusEqualTo((byte)Integer.parseInt(orderStatus));
	    	}
	    	//登录客户端用户id即业务员id;
	    	if("bussiness".equals(type)){
	    		c.andBusinessIdEqualTo(userId);
	    	//商家端用户id
	    	}else if("seller".equals(type)){
	    		c.andSellerIdEqualTo(userId);
	    	}
	    	PaginationUtil page = new PaginationUtil(pageNo, pageSize, fixOrderMapper.countByExample(fixOrderEx));
	    	fixOrderEx.setLimitStart(page.getStartRow());
	    	fixOrderEx.setLimitEnd(page.getSize());
	    	List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
	    	List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
	    	//遍历查询出来的维修订单
	    	orders.stream().forEach(order ->{
	    		Map<String,Object> one = new HashMap<String,Object>();
	    		one.put("id", order.getId());
	    		one.put("orderNo", order.getOrderNo());
	    		one.put("createTime", order.getCreateTime());
	    		one.put("chepai", order.getChePai());
	    		one.put("userName", order.getUserName());
	    		one.put("step", order.getStep());
	    		one.put("location", order.getLocation());
	    		one.put("status", order.getAuditStatus());
	    		one.put("userPhone", order.getUserPhone()==null?"":order.getUserPhone());
	    		one.put("carBrand", order.getCarBrand());
	    		orderList.add(one);
	    	});
	    	return DatasetBuilder.fromDataList(orderList,page.createPageInfo());
	 }

	/**
	 * 维修订单详情
	 */
	@Read
	@Override
	public DatasetSimple<Map<String, Object>> fiexOrderDetail(Long userId,String orderNo,String type) {
		FixOrderExample fixOrderEx = new FixOrderExample();
		fixOrderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
		if(orders.isEmpty()){
			throw new BusinessException("订单不存在!");
		}
		FixOrder order = orders.get(0);
    	if("bussiness".equals(type)){
    		if(!Objects.equals(order.getBusinessId(),userId)){
    			throw new BusinessException("没有权限查询!");
    		}
    	}else if("seller".equals(type)){
    		if(!Objects.equals(order.getSellerId(),userId)){
    			throw new BusinessException("没有权限查询!");
    		}
    	}
		Map<String, Object>  result = new HashMap<String, Object>();
		try {
			result.putAll(BeanUtils.describe(order));
			result.put("createTime", order.getCreateTime());
			result.put("outTime", order.getOutTime());
			result.put("fixTime", order.getFixTime());
			BusinessInfoExample busInfoEx = new BusinessInfoExample();
			busInfoEx.createCriteria().andUserIdEqualTo(order.getBusinessId());
			busInfoEx.setLimitStart(0);
			busInfoEx.setLimitEnd(1);
			List<BusinessInfo> infos = businessInfoMapper.selectByExample(busInfoEx);
			if(!infos.isEmpty()){
				result.put("businessName", infos.get(0).getBusinessName());
				result.put("businessCode", infos.get(0).getBusinessCode());
				result.put("businessPhone", infos.get(0).getPhone());
			}
			if(order.getSellerId() != null){
				Seller s = sellerMapper.selectByPrimaryKey(order.getSellerId());
				if(s!=null){
					result.put("sellerName", s.getSellerName());
					result.put("sellerAddress", s.getAddressDetail());
				}else{
					result.put("sellerName", "未知");
					result.put("sellerAddress", "未知");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DatasetBuilder.fromDataSimple(result);
	}
	
	/**
	 * 商家维修订单统计
	 */
	@Read
	@Override
	public DatasetSimple<Map<String, Object>> sellerFixOrderSummary(Long sellerId) {
		Map<String, Object>  result = new HashMap<String, Object>();
		FixOrderExample fixOrderEx = new FixOrderExample();
		fixOrderEx.createCriteria()
		.andAuditStatusEqualTo(FixOrder.AUDIT_STATUS_1)
		.andSellerIdEqualTo(sellerId);
		result.put("waiteForFix", fixOrderMapper.countByExample(fixOrderEx));
		
		fixOrderEx.clear();
		fixOrderEx.createCriteria()
		.andAuditStatusEqualTo(FixOrder.AUDIT_STATUS_2)
		.andSellerIdEqualTo(sellerId);
		result.put("waiteForCheck", fixOrderMapper.countByExample(fixOrderEx));
		
		fixOrderEx.clear();
		fixOrderEx.createCriteria()
		.andAuditStatusEqualTo(FixOrder.AUDIT_STATUS_3)
		.andSellerIdEqualTo(sellerId);
		result.put("checkOk", fixOrderMapper.countByExample(fixOrderEx));
		
		fixOrderEx.clear();
		fixOrderEx.createCriteria()
		.andAuditStatusEqualTo(FixOrder.AUDIT_STATUS_4)
		.andSellerIdEqualTo(sellerId);
		result.put("complet", fixOrderMapper.countByExample(fixOrderEx));
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> sellerCommitFixOrder(Long sellerId,
			String orderNo,String price,String remark) {
		FixOrderExample fixOrderEx = new FixOrderExample();
		fixOrderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
		if(orders.isEmpty()){
			throw new BusinessException("订单不存在!");
		}
		FixOrder order = orders.get(0);
		if(!Objects.equals(order.getSellerId(),sellerId)){
			throw new BusinessException("无权查看!");
		}
		if(!Objects.equals(order.getAuditStatus(), FixOrder.AUDIT_STATUS_1)){
			throw new BusinessException("提交失败，订单状态不正确!");
		}
		//order.setMoney(new BigDecimal(price));
		//order.setSellerRemark(remark);
		order.setAuditStatus(FixOrder.AUDIT_STATUS_2);
		order.setUpdateTime(new Date());
		if(fixOrderMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("提交失败，更新订单失败!");
		}
		Map<String, Object>  result = new HashMap<String, Object>();
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> sellerCompletFixOrder(Long sellerId,
			String orderNo) {
		FixOrderExample fixOrderEx = new FixOrderExample();
		fixOrderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
		if(orders.isEmpty()){
			throw new BusinessException("订单不存在!");
		}
		FixOrder order = orders.get(0);
		if(!Objects.equals(order.getSellerId(),sellerId)){
			throw new BusinessException("无权查看!");
		}
		if(!Objects.equals(order.getAuditStatus(), FixOrder.AUDIT_STATUS_3)){
			throw new BusinessException("提交失败，订单状态不正确!");
		}
		order.setAuditStatus(FixOrder.AUDIT_STATUS_4);
		order.setUpdateTime(new Date());
		if(fixOrderMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("完成订单失败，errCode:-1!");
		}
		Map<String, Object>  result = new HashMap<String, Object>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Read
	@Override
	public DatasetList<Map<String, Object>> adminFixOrderListPage(FixOrder filter,Integer pageNo,Integer pageSize) {
		FixOrderExample fixOrderEx = new FixOrderExample();
    	fixOrderEx.setOrderByClause(FixOrderExample.CREATE_TIME_DESC);
    	com.emate.shop.business.model.FixOrderExample.Criteria  c = fixOrderEx.createCriteria();
    	c.andStepEqualTo((byte)3);//过滤掉已完成的审核
    	if(filter.getAuditStatus()!=null){
    		c.andAuditStatusEqualTo(filter.getAuditStatus());
    	}
    	if(StringUtils.isNotEmpty(filter.getUserPhone())){
    		c.andUserPhoneLike("%"+filter.getUserPhone()+"%");
    	}
    	
    	if(StringUtils.isNotEmpty(filter.getUserName())){
    		c.andUserNameLike("%"+filter.getUserName()+"%");
    	}
    	
    	if(StringUtils.isNotEmpty(filter.getOrderNo())){
    		c.andOrderNoLike("%"+filter.getOrderNo()+"%");
    	}
    	
    	
    	PaginationUtil page = new PaginationUtil(pageNo, pageSize, fixOrderMapper.countByExample(fixOrderEx));
    	fixOrderEx.setLimitStart(page.getStartRow());
    	fixOrderEx.setLimitEnd(page.getSize());
    	List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
    	Map<Long,BusinessInfo> infoMap = new HashMap<Long,BusinessInfo>();
    	if(!orders.isEmpty()){
    		List<Long> ids = orders.stream().map(FixOrder::getBusinessId).distinct().collect(Collectors.toList());
    		ids.add(0L);
    		BusinessInfoExample busEx = new BusinessInfoExample();
        	busEx.createCriteria().andUserIdIn(ids);
        	businessInfoMapper.selectByExample(busEx).forEach(info -> {
        		infoMap.put(info.getUserId(), info);
        	});
    	}
    	
    	List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
    	orders.stream().forEach(order ->{
    		Map<String,Object> one = new HashMap<String,Object>();
    		one.put("orderNo", order.getOrderNo());
    		one.put("createTime", order.getCreateTime());
    		one.put("step", order.getStep());
    		one.put("chepai", order.getChePai());
    		one.put("userName", order.getUserName());
    		one.put("location", order.getLocation());
    		one.put("status", order.getAuditStatus());
    		one.put("userPhone", order.getUserPhone());
    		one.put("outTime", order.getOutTime());
    		one.put("id", order.getId());
    		one.put("money", order.getMoney());
    		if(infoMap.get(order.getBusinessId())!=null){
    			one.put("businessName", infoMap.get(order.getBusinessId()).getBusinessName());
    			one.put("businessCode", infoMap.get(order.getBusinessId()).getBusinessCode());
    			one.put("businessPhone", infoMap.get(order.getBusinessId()).getPhone());
    		}
    		orderList.add(one);
    	});
    	return DatasetBuilder.fromDataList(orderList,page.createPageInfo());
	}
	
	@Write
	@Override
	@Transactional
	public DatasetSimple<Map<String, Object>> deleteOrder(String orderNo,Long userId) {
		Map<String, Object>  result = new HashMap<String, Object>();
		OrdersExample orderEx = new OrdersExample();
		orderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<Orders> orders = ordersMapper.selectByExample(orderEx);
		if(orders.isEmpty()){
			throw new BusinessException("取消订单失败,订单不存在！");
		}
		Orders order = orders.get(0);
		if(!Objects.equals(order.getUserId(), userId)){
			throw new BusinessException("取消订单失败,没有权限！");
		}
		if(!Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_3)){
			if(!Objects.equals(Orders.STATUS_0, order.getStatus())){
				throw new BusinessException("系统已经审核的订单无法取消！");
			}
		}else{
			if(!Objects.equals(Orders.STATUS_2, order.getStatus())){
				throw new BusinessException("已完成或服务中的订单无法取消！");
			}
		}
		
		if(order.getDeliverTime()!=null){
			throw new BusinessException("已发货订单不能取消！");
		}
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdEqualTo(order.getUserId());
		userInfoEx.setLimitStart(0);
		userInfoEx.setLimitEnd(1);
		List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoEx);
		if(userInfoList.isEmpty()){
			throw new BusinessException("取消订单失败,用户信息为空！");
		}
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		importUserInfoEx.createCriteria().andChePaiEqualTo(userInfoList.get(0).getLicense());
		importUserInfoEx.setLimitStart(0);
		importUserInfoEx.setLimitEnd(1);
		List<ImportUserInfo>  importList = importUserInfoMapper.selectByExample(importUserInfoEx);
		if(importList.isEmpty()){
			throw new BusinessException("取消订单失败,导入用户信息为空！");
		}
		ImportUserInfo info = importList.get(0);
		order.setStatus(Orders.STATUS_4);
		order.setUpdateTime(new Date());
		if(ordersMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("取消订单失败,errCode:-1！");
		}
//		OrderTraceExample orderTraceEx = new OrderTraceExample();
//		orderTraceEx.createCriteria().andOrderNoEqualTo(order.getOrderNo());
//		if(orderTraceMapper.deleteByExample(orderTraceEx)!=1){
//			throw new BusinessException("取消订单失败,errCode:-2！");
//		}
		//计算订单总价
		if(order.getOrderType() == Orders.ORDER_TYPE_0){//电瓶
			info.setDianpingTimes(info.getDianpingTimes()+1);
		}else if(order.getOrderType() == Orders.ORDER_TYPE_1){//轮胎
			info.setLuntaiTimes(info.getLuntaiTimes()+1);
		}else if(order.getOrderType() == Orders.ORDER_TYPE_2){//小保养
			info.setBaoyangTimes(info.getBaoyangTimes()+1);
		}else if(order.getOrderType() == Orders.ORDER_TYPE_3){
			info.setXiecheTimes(info.getXiecheTimes()+1);
		}else if(order.getOrderType() == Orders.ORDER_TYPE_4){
			info.setPenqiTimes(info.getPenqiTimes()+order.getGoodsNum());
		}
		if(importUserInfoMapper.updateByPrimaryKeySelective(info)!=1){
			throw new BusinessException("取消订单失败,errCode:-3！");
		}
		result.put("result", "success");
    	return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> adminCheckFixOrder(String orderNo) {
		FixOrderExample fixOrderEx = new FixOrderExample();
		fixOrderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
		if(orders.isEmpty()){
			throw new BusinessException("订单不存在!");
		}
		FixOrder order = orders.get(0);
		if(!Objects.equals(order.getAuditStatus(), FixOrder.AUDIT_STATUS_2)){
			throw new BusinessException("审核失败，订单状态不正确!");
		}
		order.setAuditStatus(FixOrder.AUDIT_STATUS_3);
		order.setUpdateTime(new Date());
		if(fixOrderMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("审核订单失败，errCode:-1!");
		}
		Map<String, Object>  result = new HashMap<String, Object>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Read
	public DatasetList<Express> queryExpressList() {
		ExpressExample expressEx = new ExpressExample();
		expressEx.setOrderByClause(ExpressExample.SEQ_ASC);
		List<Express> expressList = expressMapper.selectByExample(expressEx);
		return DatasetBuilder.fromDataList(expressList);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> deliverOrder(String orderNo, Long expressId, String expressNo) {
		Express ex = expressMapper.selectByPrimaryKey(expressId);
		if(Objects.isNull(ex)){
			throw new BusinessException("订单发货失败，errCode:-1,物流公司不存在");
		}
		OrdersExample orderEx = new OrdersExample();
		orderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		if(orders.isEmpty()){
			throw new BusinessException("订单发货失败，errCode:-2,订单不存在");
		}
		Orders order = orders.get(0);
		OrderExpress orderExpress = new OrderExpress();
		orderExpress.setOrderId(order.getId());
		orderExpress.setOrderNo(order.getOrderNo());
		orderExpress.setExpressCode(ex.getExpressCode());
		orderExpress.setExpressId(ex.getId());
		orderExpress.setExpressNo(expressNo);
		orderExpress.setExpressName(ex.getExpressName());
		//orderExpress.setFromCity("广州");
		orderExpress.setMobile(order.getPhone());
		//orderExpress.setToCity(toCity);
		orderExpress.setCreateTime(new Date());
		orderExpress.setUpdateTime(new Date());
		if(orderExpressMapper.insertSelective(orderExpress)!=1){
			throw new BusinessException("订单发货失败，errCode:-3,插入订单记录失败");
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Read
	public DatasetSimple<OrderExpress> queryExpressInfo(String orderNo) {
		OrderExpressExample orderEx = new OrderExpressExample();
		orderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<OrderExpress> express = orderExpressMapper.selectByExample(orderEx);
		if(express.isEmpty()){
			throw new BusinessException("找不到物流信息！");
		}
		return DatasetBuilder.fromDataSimple(express.get(0));
	}
	/**
	 * 修改运单号码
	 */

	@Override
	@Read
	public DatasetSimple<Map<String, Object>> editExpressNo(String orderNo, String expressNo) {
		OrderExpressExample orderEx = new OrderExpressExample();
		orderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<OrderExpress> orderExpressList = orderExpressMapper.selectByExample(orderEx);
		if(orderExpressList.isEmpty()){
			throw new BusinessException("找不到物流信息！");
		}
		OrderExpress orderExpress = orderExpressList.get(0);
		orderExpress.setExpressNo(expressNo);
		orderExpress.setPoll(false);
		orderExpress.setFailTimes(0);
		orderExpress.setResponseJson("");
		if(orderExpressMapper.updateByPrimaryKeySelective(orderExpress)!=1){
			throw new BusinessException("修改物流信息失败！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> addOrderRemark(String orderNo, String remark) {
		OrdersExample ordersEx = new OrdersExample();
		ordersEx.createCriteria().andOrderNoEqualTo(orderNo);
		Orders orders = new Orders();
		orders.setOperateRemark(remark);
		if(ordersMapper.updateByExampleSelective(orders, ordersEx)!=1){
			throw new BusinessException("添加订单备注失败！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> adminCommitFixOrder(String orderNo, String price, String remark) {
		FixOrderExample fixOrderEx = new FixOrderExample();
		fixOrderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<FixOrder> orders = fixOrderMapper.selectByExample(fixOrderEx);
		if(orders.isEmpty()){
			throw new BusinessException("订单不存在!");
		}
		FixOrder order = orders.get(0);
		order.setMoney(new BigDecimal(price));
		order.setSellerRemark(remark);
		order.setUpdateTime(new Date());
		if(fixOrderMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("提交失败，更新订单失败!");
		}
		Map<String, Object>  result = new HashMap<String, Object>();
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> resetSellerReport(String date) {
		//根据年月查询sellerReport表的记录并清空
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		SellerReportExample reportEx = new SellerReportExample();
		reportEx.createCriteria()
		.andYearEqualTo(year)
		.andMonthEqualTo(month);
		sellerReportMapper.deleteByExample(reportEx);
		Map<String,Object> result = new HashMap<String,Object>();
		//获取当前年月
		Calendar now = Calendar.getInstance(Locale.CHINA);
		int i = now.get(Calendar.MONTH);
		if(month==(i+1)){
			throw new BusinessException("您不能结算当前月的订单信息");
		}
		//获取开始和结束时间
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		//计算出当前参数所给月数
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
		//计算出当前参数所给月数+1
		now.set(Calendar.MONTH, month);
		c.andFinishTimeLessThan(now.getTime());
		//订单状态(已完成)
		c.andStatusEqualTo(Orders.STATUS_3);
		//不包括洗车
		//c.andOrderTypeNotEqualTo(Orders.ORDER_TYPE_3);
		c.andSellerIdNotEqualTo(0L);
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		Set<Long> sellerIds = new HashSet<Long>();
		for(Orders order:orders){
			sellerIds.add(order.getSellerId());
		}
		for(Long sellerId : sellerIds){
			DatasetSimple<Map<String, Object>> sellerReports = sellerComfirmReport(sellerId,date,"0");
			if(!sellerReports.isSuccess()
					||sellerReports.getData().get("success")==null
					||!(boolean)sellerReports.getData().get("success")){
				throw new BusinessException("增加商家结算失败;商家id:"+sellerId);
			}
		}
		result.put("success", true);
		return DatasetBuilder.fromDataSimple(result);
	}
	public static Map<String,String> compMap = new HashMap<String,String>();//所有公司的map集合
	static{
		compMap.put("440112", "广东省分公司直属第一支公司");
		compMap.put("440113", "广州市番禺支公司");
		compMap.put("440114", "广州市花都支公司");
		compMap.put("440115", "广州市南沙支公司");
		compMap.put("440116", "广州市黄埔支公司");
		compMap.put("440117", "广州市狮岭营业部");
		compMap.put("440118", "咨询服务个代业务部");
		compMap.put("440161", "广州直属营业部");
		compMap.put("440162", "重点客户部");
		compMap.put("440163", "广东省分公司直属第二支公司");
		compMap.put("440164", "广东省分公司直属第三支公司");
		compMap.put("440165", "广州国际业务部");
		compMap.put("440170", "广州车行业务部");
		compMap.put("440171", "电子保险业务部");
		compMap.put("440182", "广州市新塘营业部");
		compMap.put("440183", "广州市增城支公司");
		compMap.put("440184", "广州市从化支公司");
		compMap.put("440187", "江门互动服务部");
		compMap.put("440188", "东莞互动服务部");
		compMap.put("440189", "佛山互动服务部");
		compMap.put("440190", "广州互动业务部");
		compMap.put("440191", "肇庆互动服务部");
		compMap.put("440192", "梅州互动服务部");
		compMap.put("440193", "河源互动服务部");
		compMap.put("440194", "云浮互动服务部");
		compMap.put("440197", "广州公司业务部");
		compMap.put("440198", "广州分公司机关");
		
	}
	
	@Override
	@Read
	public DatasetList<Map<String,String>> getOrderCompData(String date,String orderType){
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		//2.添加订单状态筛选条件(已完成订单)
		//c.andStatusEqualTo(Orders.STATUS_3);
		if("2".equals(orderType)){
			c.andOrderTypeEqualTo(Orders.ORDER_TYPE_2);
		}else if("3".equals(orderType)){
			c.andOrderTypeEqualTo(Orders.ORDER_TYPE_3);
		}else{
			throw new BusinessException("未选择订单类型");
		}
		
		
		//3.添加完成订单时间的筛选条件
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		//所传年月的1号凌晨零点
		c.andCreateTimeGreaterThanOrEqualTo(now.getTime());
		//计算出当前参数所给月数+1(下个月的1号凌晨零点)
		now.set(Calendar.MONTH, month);
		c.andCreateTimeLessThan(now.getTime());
		//根据筛选条件查询订单
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		Map<Long,String> userIdMap = new HashMap<Long,String>();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		orders.forEach(order -> {
			userIdMap.put(order.getUserId(), dataFormat.format(order.getCreateTime()));
		});
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		//获取用户id的list
		Set<Long> userIds = orders.stream().map(Orders::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toSet());
		userIds.add(0L);
		
		//根据用户id的list筛选条件
		List<String> chepais = new ArrayList<String>();
		Map<String,Long> chepaiMap = new HashMap<>();
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdIn(new ArrayList<>(userIds));
		userInfoMapper.selectByExample(userInfoEx).forEach(userInfo -> {
			chepaiMap.put(userInfo.getLicense(),userInfo.getUserId());
			chepais.add(userInfo.getLicense());
		});
		//根据车牌list查询保单信息
		chepais.add("");
		List<String> comBaoDan = new ArrayList<String>();
		Map<String,String> importInfoMap = new HashMap<>();
		ImportUserInfoExample importUserEx = new ImportUserInfoExample();
		importUserEx.createCriteria().andChePaiIn(chepais);
		importUserInfoMapper.selectByExample(importUserEx).forEach(importUserInfo -> {
			importInfoMap.put(importUserInfo.getBaoDan(),importUserInfo.getChePai());
			if(StringUtils.isNotEmpty(importUserInfo.getBaoDan()) && importUserInfo.getBaoDan().length()>15){
				comBaoDan.add(importUserInfo.getBaoDan());
			}
		});
		List<String> dateStr = new ArrayList<String>();
		Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
		dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		for(Map.Entry<String, String> entry : compMap.entrySet()){
			Map<String,String> oneResult = new HashMap<String,String>();
			String code = entry.getKey();
			oneResult.put("comName", entry.getValue());
			for(String s:dateStr){
				oneResult.put(s, 0+"");
			}
			for(String s:comBaoDan){
				if(code.equals(s.substring(9, 15))){
					Long userId = chepaiMap.get(importInfoMap.get(s));
					if(oneResult.get(userIdMap.get(userId))!=null){
						oneResult.put(userIdMap.get(userId), (Integer.parseInt(oneResult.get(userIdMap.get(userId)))+1)+"");
					}
				}
			}
			result.add(oneResult);
		}
		return DatasetBuilder.fromDataList(result);
	}
	@Override
	@Read
	public DatasetList<Map<String,String>> getOrderCompDataTwo(String date,String orderType){
		//组织筛选条件(月初，月末，订单类型)
		HashMap<String, Object> map = new HashMap<String,Object>();
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		//所传年月的1号凌晨零点
		Date startTime = now.getTime();
		//计算出当前参数所给月数+1(下个月的1号凌晨零点)
		now.set(Calendar.MONTH, month);
		
		Date endTime = now.getTime();
		//根据筛选条件查询订单
		map.put("endTime", endTime);
		map.put("startTime", startTime);
		map.put("orderType", orderType);
		List<Map<String, Object>> orderdata = definedMapper.queryOrderAnalysisList(map);
		//组织返回结果
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		List<String> dateStr = new ArrayList<String>();
		Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
		dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		for(Map.Entry<String, String> entry : compMap.entrySet()){
			Map<String,String> oneResult = new HashMap<String,String>();
			String code = entry.getKey();
			oneResult.put("comName", entry.getValue());
			for(String s:dateStr){
				oneResult.put(s, 0+"");
			}
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for(Map<String,Object> s:orderdata){
				String baoDan = String.valueOf(s.get("baoDan"));
				if(StringUtils.isEmpty(baoDan)||baoDan.length()<15){
					continue;
				}
				String dateTime = String.valueOf(s.get("dateTime"));
				Long number = (Long)s.get("number");
				if(code.equals(baoDan.substring(9, 15))){
					if(oneResult.get(dateTime)!=null){
						oneResult.put(dateTime, (Long.parseLong(oneResult.get(dateTime))+number)+"");
					}
				}
			}
			result.add(oneResult);
		}
		return DatasetBuilder.fromDataList(result);
	}
	
	
	@Override
	@Read
	public DatasetList<Map<String,String>> getRegisterCompData(String date){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		UserInfoExample userInfoEx = new UserInfoExample();
		com.emate.shop.business.model.UserInfoExample.Criteria c = userInfoEx.createCriteria();
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		//所传年月的1号凌晨零点
		c.andCreateTimeGreaterThanOrEqualTo(now.getTime());
		//计算出当前参数所给月数+1(下个月的1号凌晨零点)
		now.set(Calendar.MONTH, month);
		c.andCreateTimeLessThan(now.getTime());
		//根据筛选条件查询订单
		List<UserInfo>  infos = userInfoMapper.selectByExample(userInfoEx);
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> chepais = new ArrayList<String>();
		Map<String,String> chepaiMap = new HashMap<>();
		infos.forEach(userInfo -> {
			chepaiMap.put(userInfo.getLicense(),dataFormat.format(userInfo.getCreateTime()));
			chepais.add(userInfo.getLicense());
		});
		//根据车牌list查询保单信息
		List<String> comBaoDan = new ArrayList<String>();
		Map<String,String> importInfoMap = new HashMap<>();
		ImportUserInfoExample importUserEx = new ImportUserInfoExample();
		importUserEx.createCriteria().andChePaiIn(chepais);
		importUserInfoMapper.selectByExample(importUserEx).forEach(importUserInfo -> {
			importInfoMap.put(importUserInfo.getBaoDan(),importUserInfo.getChePai());
			if(StringUtils.isNotEmpty(importUserInfo.getBaoDan()) && importUserInfo.getBaoDan().length()>15){
				comBaoDan.add(importUserInfo.getBaoDan());
			}
		});
		List<String> dateStr = new ArrayList<String>();
		Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
		dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		for(Map.Entry<String, String> entry : compMap.entrySet()){
			Map<String,String> oneResult = new HashMap<String,String>();
			String code = entry.getKey();
			oneResult.put("comName", entry.getValue());
			for(String s:dateStr){
				oneResult.put(s, 0+"");
			}
			for(String s:comBaoDan){
				if(code.equals(s.substring(9, 15))){
					if(oneResult.get(chepaiMap.get(importInfoMap.get(s)))!=null){
						oneResult.put(chepaiMap.get(importInfoMap.get(s)), (Integer.parseInt(oneResult.get(chepaiMap.get(importInfoMap.get(s))))+1)+"");
					}
				}
			}
			result.add(oneResult);
		}
		return DatasetBuilder.fromDataList(result);
	}
	
	@Override
	@Read
	public DatasetList<Map<String,String>> getBaodnaData(String date){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		ImportUserInfoExample importUserEx = new ImportUserInfoExample();
		com.emate.shop.business.model.ImportUserInfoExample.Criteria c = importUserEx.createCriteria();
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		//所传年月的1号凌晨零点
		c.andPaymentTimeGreaterThanOrEqualTo(now.getTime());
		//计算出当前参数所给月数+1(下个月的1号凌晨零点)
		now.set(Calendar.MONTH, month);
		c.andPaymentTimeLessThan(now.getTime());
		//根据车牌list查询保单信息
		List<String> comBaoDan = new ArrayList<String>();
		Map<String,String> importInfoMap = new HashMap<>();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		importUserInfoMapper.selectByExample(importUserEx).forEach(importUserInfo -> {
			if(importUserInfo.getPaymentTime()!=null){
				importInfoMap.put(importUserInfo.getBaoDan(),dataFormat.format(importUserInfo.getPaymentTime()));
			}
			if(StringUtils.isNotEmpty(importUserInfo.getBaoDan()) && importUserInfo.getBaoDan().length()>15){
				comBaoDan.add(importUserInfo.getBaoDan());
			}
		});
		List<String> dateStr = new ArrayList<String>();
		Calendar dateMonth = Calendar.getInstance(Locale.CHINA);
		dateMonth.set(Calendar.YEAR, year);
		dateMonth.set(Calendar.MONTH, month-1);
		dateMonth.add(Calendar.DAY_OF_MONTH, -dateMonth.get(Calendar.DAY_OF_MONTH)+1);
		while(dateMonth.get(Calendar.YEAR)<=year&&dateMonth.get(Calendar.MONTH)<month){
			String monthStr = dateMonth.get(Calendar.MONTH)+1 < 10 ? "0"+(dateMonth.get(Calendar.MONTH)+1) : dateMonth.get(Calendar.MONTH)+1+"";
			String dayStr = dateMonth.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dateMonth.get(Calendar.DAY_OF_MONTH) : dateMonth.get(Calendar.DAY_OF_MONTH)+"";
			dateStr.add(dateMonth.get(Calendar.YEAR)+"-"+(monthStr)+"-"+dayStr);
			dateMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
		for(Map.Entry<String, String> entry : compMap.entrySet()){
			Map<String,String> oneResult = new HashMap<String,String>();
			String code = entry.getKey();
			oneResult.put("comName", entry.getValue());
			for(String s:dateStr){
				oneResult.put(s, 0+"");
			}
			for(String s:comBaoDan){
				if(code.equals(s.substring(9, 15))){
					if(oneResult.get(importInfoMap.get(s))!=null){
						oneResult.put(importInfoMap.get(s), Integer.parseInt(oneResult.get(importInfoMap.get(s)))+1+"");
					}
				}
			}
			result.add(oneResult);
		}
		return DatasetBuilder.fromDataList(result);
	}

	@Override
	@Read
	public DatasetSimple<Integer> QueryXiCheOrder(Long userId) {
		OrdersExample ordersEx = new OrdersExample();
		Criteria ordersCri= ordersEx.createCriteria();
		//设置用户
		ordersCri.andUserIdEqualTo(userId);
		//设置洗车订单
		ordersCri.andOrderTypeEqualTo(Orders.ORDER_TYPE_3);
		//设置订单状态
		ordersCri.andStatusNotEqualTo(Orders.STATUS_4);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		//设置日期条件
		ordersCri.andCreateTimeLessThanOrEqualTo(now.getTime());
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		//设置日期条件
		ordersCri.andCreateTimeGreaterThanOrEqualTo(now.getTime());
		//查询结果
		int count = ordersMapper.countByExample(ordersEx);
		return DatasetBuilder.fromDataSimple(count);
	}
	/**
	 * 国寿人员导出国寿报表
	 */
	@Override
	@Read
	public DatasetList<Map<String, String>> exportChinaLifeExcelTwo(String date, String status) {
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		
		OrdersExample orderEx = new OrdersExample();
		Criteria c = orderEx.createCriteria();
		//2.添加订单状态筛选条件(已完成订单)
		if("-1".equals(status)){//导出所有订单
			c.andStatusNotEqualTo(Orders.STATUS_4);
		}else if("3".equals(status)){//导出已完成订单
			c.andStatusEqualTo(Orders.STATUS_3);
		}else{
			c.andStatusEqualTo(Orders.STATUS_3);
		}
		
		//3.添加完成订单时间的筛选条件
		//计算出当前参数所给月数
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		//所传年月的1号凌晨零点
		if("-1".equals(status)){//导出所有订单
			c.andCreateTimeGreaterThanOrEqualTo(now.getTime());
		}else if("3".equals(status)){//导出已完成订单
			c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
		}else{
			c.andFinishTimeGreaterThanOrEqualTo(now.getTime());
		}
		//计算出当前参数所给月数+1(下个月的1号凌晨零点)
		now.set(Calendar.MONTH, month);
		
		if("-1".equals(status)){//导出所有订单
			c.andCreateTimeLessThan(now.getTime());
		}else if("3".equals(status)){//导出已完成订单
			c.andFinishTimeLessThan(now.getTime());
		}else{
			c.andFinishTimeLessThan(now.getTime());
		}
		orderEx.setOrderByClause(OrdersExample.CREATE_TIME_ASC);//按照下单时间排序
		//根据筛选条件查询订单
		List<Orders>  orders = ordersMapper.selectByExample(orderEx);
		//根据用户id查询用户（user表）详细信息
		List<Long> userIds = orders.stream().map(Orders::getUserId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
		Map<Long,User> userMap = new HashMap<>();
		UserExample userEx = new UserExample();
		//根据用户id的list筛选条件
		userEx.createCriteria().andIdIn(userIds);
		userMapper.selectByExample(userEx).forEach(user -> {
			userMap.put(user.getId(), user);
		});
		//根据用户id查询用户关联表（user_info表）车牌信息
		Map<Long,String> chepaiMap = new HashMap<>();
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria().andUserIdIn(userIds);
		userInfoMapper.selectByExample(userInfoEx).forEach(userInfo -> {
			chepaiMap.put(userInfo.getUserId(), userInfo.getLicense());
		});
		//根据订单sellerId查询所有seller商户
		Map<Long, Seller> sellerMap = new HashMap<Long,Seller>();
		List<Long> sellerIds = orders.stream().map(Orders::getSellerId).distinct().filter(Objects::nonNull).collect(Collectors.toList());
		sellerIds.add(0L);
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andIdIn(sellerIds);
		sellerMapper.selectByExample(sellerEx).forEach(seller -> {
			sellerMap.put(seller.getId(), seller);
		});
		//组织返回结果
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		orders.stream().forEach(order ->{
			HashMap<String, String> map = new HashMap<String,String>();
			//订单完成时间
			if("-1".equals(status)){//导出所有订单
				map.put("orderTime", sd.format(order.getCreateTime()));
			}else if("3".equals(status)){//导出已完成订单
				map.put("orderTime", sd.format(order.getFinishTime()));
			}else{
				map.put("orderTime", sd.format(order.getFinishTime()));
			}
			//赋值用户名字
			map.put("userName",userMap.get(order.getUserId()).getNickName());
			//赋值用户手机号
			map.put("userPhone",userMap.get(order.getUserId()).getName());
			//赋值用户车牌
			map.put("license", chepaiMap.get(order.getUserId()));
			//服务类型
			if(Orders.ORDER_TYPE_2.equals(order.getOrderType())){
				map.put("orderType", "小保养");
			}else if(Orders.ORDER_TYPE_3.equals(order.getOrderType())){
				map.put("orderType", "洗车");
			}else if(Orders.ORDER_TYPE_4.equals(order.getOrderType())){
				map.put("orderType", "喷漆");
			}else if(Orders.ORDER_TYPE_0.equals(order.getOrderType())){
				map.put("orderType", "电瓶");
			}else if(Orders.ORDER_TYPE_1.equals(order.getOrderType())){
				map.put("orderType", "轮胎");
			}else if(Orders.ORDER_TYPE_5.equals(order.getOrderType())){
				map.put("orderType", "其他");
			}
			//服务次数
			if(Orders.ORDER_TYPE_4.equals(order.getOrderType())){
				map.put("orderNum", String.valueOf(order.getGoodsNum()));
			}else{
				map.put("orderNum", "1");
			}
			//结算价格
			if(Orders.ORDER_TYPE_3.equals(order.getOrderType())){
				map.put("totalPrice",order.getMoneyAmount().toString());
			}else{
				map.put("totalPrice",order.getMoneyAmount().add(order.getServiceMoney()).toString());
			}
			Seller s = sellerMap.get(order.getSellerId());
			if(s!=null){
				//商家名称
				map.put("sellerName", s.getSellerName());
				//商家手机号
				map.put("sellerPhone", s.getSellerPhone());
			}else{
				//商家名称
				map.put("sellerName", "--");
				//商家手机号
				map.put("sellerPhone", "--");
			}
			result.add(map);
		});
		return DatasetBuilder.fromDataList(result);
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<String> completeOrder(String orderNo,Long userId) {
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("订单号为空！！！！");
		}
		OrdersExample ordersEx = new OrdersExample();
		ordersEx.createCriteria()
		.andOrderNoEqualTo(orderNo);
		List<Orders> ordersList = ordersMapper.selectByExample(ordersEx);
    	if(ordersList.isEmpty()){
    		throw new BusinessException("订单不存在！！！！");
    	}
    	Orders order = ordersList.get(0);
    	//若是已完成或已取消订单不能强制完成
    	if(order.getStatus()==Orders.STATUS_3){
    		throw new BusinessException("订单已完成!!!!");
    	} 
    	if(order.getStatus()==Orders.STATUS_4){
    		throw new BusinessException("订单已取消!!!!");
    	}
    	//若是盛大洗车,不能强制完成
    	if(Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_3)
    			&&StringUtils.isEmpty(order.getAddressDetail())){
    			throw new BusinessException("该地区订单不能进行此操作!!!!");
    	}
    	//更新订单状态
    	order.setStatus(Orders.STATUS_3);
    	order.setFinishTime(new Date());
    	order.setOperateRemark(order.getOperateRemark()+"--强制完成"
    			+userId);
		order.setUpdateTime(new Date());
		if(ordersMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("强制完成订单失败！！！");
		}
		//若是小保养，更新记录物流信息
		if(Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_2)){
			try{
				OrderTraceExample orderTraceEx = new OrderTraceExample();
				orderTraceEx.createCriteria().andOrderNoEqualTo(order.getOrderNo());
				List<OrderTrace> orderTraceList = orderTraceMapper.selectByExample(orderTraceEx);
				if(!orderTraceList.isEmpty()){
					OrderTrace trace = orderTraceList.get(0);
					trace.setUpdateTime(new Date());
					trace.setCompletTime(new Date());//表示订单完成时间
					//可能后台人员不发货，那么就需要系统自动往前计算1个小时，默认为到货时间
					if(trace.getDeliverTime()==null){
						trace.setDeliverTime(new Date(trace.getCompletTime().getTime()-60*60*1000));
					}
					orderTraceMapper.updateByExample(trace, orderTraceEx);
				}
			}catch(Exception e){
				Log4jHelper.getLogger().error("记录订单日志失败！");
			}
		}
		//更新记录商家完成订单数量
		Long sellerId = order.getSellerId();
		Seller seller = null;
		if(sellerId != null){
			seller = sellerMapper.selectByPrimaryKey(sellerId);
		}
		if(seller !=null){
			seller.setOrderCountOver(seller.getOrderCountOver()+1);
			sellerMapper.updateByPrimaryKeySelective(seller);
		}
		return DatasetBuilder.fromDataSimple("SUCCESS");
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<String> cancelOrder(String orderNo,Long userId) {
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("订单号为空！！！！");
		}
		OrdersExample ordersEx = new OrdersExample();
		ordersEx.createCriteria()
		.andOrderNoEqualTo(orderNo);
		List<Orders> ordersList = ordersMapper.selectByExample(ordersEx);
    	if(ordersList.isEmpty()){
    		throw new BusinessException("订单不存在！！！！");
    	}
    	Orders order = ordersList.get(0);
    	//若是已完成或已取消订单不能强制取消
    	if(order.getStatus()==Orders.STATUS_3){
    		throw new BusinessException("订单已完成!!!!");
    	} 
    	if(order.getStatus()==Orders.STATUS_4){
    		throw new BusinessException("订单已取消!!!!");
    	}
    	//若是盛大洗车,不能强制取消
    	if(Objects.equals(order.getOrderType(), Orders.ORDER_TYPE_3)
    			&&StringUtils.isEmpty(order.getAddressDetail())){
    			throw new BusinessException("该地区订单不能进行此操作!!!!");
    	}
    	//更新订单状态
    	order.setStatus(Orders.STATUS_4);
    	order.setOperateRemark(order.getOperateRemark()+"--强制取消"
    			+userId);
		order.setUpdateTime(new Date());
		if(ordersMapper.updateByPrimaryKeySelective(order)!=1){
			throw new BusinessException("强制取消订单失败！！！");
		}
		//更新绑定用户车牌次数
		if(Objects.isNull(order.getUserId())){
			throw new BusinessException("该订单没有下单用户");
		}
		UserInfoExample userInfoEx = new UserInfoExample();
		userInfoEx.createCriteria()
			.andUserIdEqualTo(order.getUserId());
		userInfoEx.setLimitStart(0);
		userInfoEx.setLimitEnd(1);
		List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoEx);
		if(userInfoList.isEmpty()){
			throw new BusinessException("该订单下单用户没有绑定！！！");
		}
		UserInfo userInfo = userInfoList.get(0);
		if(StringUtils.isEmpty(userInfo.getLicense())){
			throw new BusinessException("该订单下单用户没有绑定车牌！！！");
		}
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		importUserInfoEx.createCriteria()
			.andChePaiEqualTo(userInfo.getLicense());
		List<ImportUserInfo> importUserInfoList = importUserInfoMapper.selectByExample(importUserInfoEx);
		if(importUserInfoList.isEmpty()){
			throw new BusinessException("未查到该订单绑定的车牌信息");
		}
		ImportUserInfo importUserInfo = importUserInfoList.get(0);
		if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_2)){
			Integer baoyangTimes = importUserInfo.getBaoyangTimes();
			importUserInfo.setBaoyangTimes(baoyangTimes+1);
		}else if(Objects.equals(order.getOrderType(),Orders.ORDER_TYPE_4)){
			Integer penqiTimes = importUserInfo.getPenqiTimes();
			importUserInfo.setPenqiTimes(penqiTimes+1);
		}
		if(1!=importUserInfoMapper.updateByPrimaryKeySelective(importUserInfo)){
			throw new BusinessException("更新绑定用户车牌次数失败");
		};
		return DatasetBuilder.fromDataSimple("SUCCESS");
	}
}
