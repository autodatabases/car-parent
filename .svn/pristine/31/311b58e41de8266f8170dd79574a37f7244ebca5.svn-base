package com.emate.shop.business.service;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.emate.shop.business.api.AnalysisService;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.business.model.CarwashConfigExample;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CarwashConfigMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.google.common.base.Objects;
/**
 * admin统计
 * @author dong
 *
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private CarwashConfigMapper carwashConfigMapper;
	
	@Override
	@Read
	public DatasetList<Map<String, String>> analysisBaoDan(String date, String address,
			String source) {
		if(StringUtils.isEmpty(date)){
			throw new BusinessException("请选择年月~");
		}
		if(StringUtils.isEmpty(address)||"选择城市".equals(address)){
			throw new BusinessException("请选择保单地址~");
		}
		if(StringUtils.isEmpty(source)){
			throw new BusinessException("请选择渠道");
		}
		String[] dateArr = date.split("-");
		Integer year = Integer.valueOf(dateArr[0]);
		Integer month = Integer.valueOf(dateArr[1]);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.MILLISECOND, 0);
		Date startTime = now.getTime();
		now.set(Calendar.MONTH, month);
		Date endTime = now.getTime();
		ImportUserInfoExample infoEx = new ImportUserInfoExample();
		if ("全部".equals(source)) {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address);
	        } else if ("互动".equals(source)) {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceLike("%" + source + "%");
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceEqualTo("销售渠道");
	        } else if ("电子".equals(source)) {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceLike("%" + source + "%");
	        } else if ("普通中介".equals(source)) {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceEqualTo(source);
	        } else if ("电子保险".equals(source)) {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceLike("%" + source + "%");
	        } else if ("其他".equals(source)) {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceIsNull();
	        } else {
	            infoEx.or().andCreateTimeGreaterThanOrEqualTo(startTime)
	                    .andCreateTimeLessThan(endTime).andAddressEqualTo(address)
	                    .andSourceEqualTo(source);
	        }

		List<ImportUserInfo> infoList = importUserInfoMapper.selectByExample(infoEx);
		if(infoList.isEmpty()){
			return DatasetBuilder.fromDataList(null);
		}
		//导入保单的数量
		int baoNum = infoList.size();
		//导入保单的保费总金额
		BigDecimal baoFei = new BigDecimal("0");
		//导入保单的保养总次数
		Long baoTimes = 0L;
		//导入保单的洗车总次数
		Long xiTimes = 0L;
		//导入保单的喷漆总次数
		Long penTimes = 0L;
		for(ImportUserInfo importUserInfo :infoList){
			baoFei=baoFei.add(importUserInfo.getOrderPrice());
			baoTimes += importUserInfo.getBaoyangTimes();
			xiTimes += importUserInfo.getXiecheTimes();
			penTimes += importUserInfo.getPenqiTimes();
		}
		List<String> chePais = infoList.stream().map(ImportUserInfo ::getChePai).distinct().collect(Collectors.toList());
		//洗车类型
		CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
		carwashConfigEx.createCriteria().andCityNameEqualTo(address);
		List<CarwashConfig> carwashConfigs = carwashConfigMapper.selectByExample(carwashConfigEx);
		if(!carwashConfigs.isEmpty()&&CarwashConfig.COUNT_TYPE_1.equals(carwashConfigs.get(0).getCountType())){
				xiTimes=xiTimes*12;
		}
		List<Map<String, Object>> nums = definedMapper.queryOrderNumByType(chePais);
		for(int i=0;i<nums.size();i++){
			Map<String, Object> countMap = nums.get(i);
			Integer orderType = (Integer)countMap.get("orderType");
			Long orderNum = (Long)countMap.get("orderNum");
			if(Objects.equal(2, orderType)){
					baoTimes += orderNum;
			}else if(Objects.equal(3, orderType)){
				if(carwashConfigs.isEmpty()||CarwashConfig.WASH_TYPE_1.equals(carwashConfigs.get(0).getWashType())){
					xiTimes += orderNum;
				}
			}else if(Objects.equal(4, orderType)){
				penTimes += orderNum;
			}
		}
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		Map<String, String> result = new HashMap<String,String>();
		result.put("month", date);
		result.put("address", address);
		result.put("source", source);
		result.put("baoTimes", String.valueOf(baoTimes));
		result.put("xiTimes", String.valueOf(xiTimes));
		result.put("penTimes", String.valueOf(penTimes));
		result.put("baoNum", String.valueOf(baoNum));
		result.put("baoFei", baoFei.toString());
		results.add(result);
		return DatasetBuilder.fromDataList(results);
	}
	/**
	 * 获取某个月某城市的下单用户的订单总额和订单数量；
	 */
	@Override
	@Read
	public DatasetList<Map<String, String>> analysisOrders(String date,
			String address,String source) {
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		Date startTime = now.getTime();
		now.set(Calendar.MONTH, month);
		Date endTime = now.getTime();
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("address", address);
		map.put("source", source);
		String sourcecopy = source;
		List<Map<String, Object>> monthList = null;
		if ("全部".equals(source)) {
	            monthList = definedMapper.queryOrderData(map);
	        } else if ("互动".equals(source)) {
	            source = "%" + source + "%";
	            map.put("source", source);
	            monthList = definedMapper.queryOrderDataone(map);
	        } else if ("电子".equals(source)) {
	            source = "%" + source + "%";
	            map.put("source", source);
	            monthList = definedMapper.queryOrderDatatwo(map);
	        } else if ("普通中介".equals(source)) {
	            map.put("source", source);
	            monthList = definedMapper.queryOrderDatasix(map);
	        } else if ("电子保险".equals(source)) {
	            source = "%" + source + "%";
	            map.put("source", source);
	            monthList = definedMapper.queryOrderDatathree(map);
	        } else if ("其他".equals(source)) {
	            monthList = definedMapper.queryOrderDatafour(map);
	        } else {
	            monthList = definedMapper.queryOrderDatafive(map);
	        }
	        if (monthList.isEmpty()) {
	            return DatasetBuilder.fromDataList(null);
	        }
		//小保养订单数量
		Integer byNum = 0;
		//洗车订单数量
		Integer xcNum = 0;
		//喷漆订单数量
		Integer pqNum = 0;
		//总订单数量
		Integer totalNum = 0;
		
		//小保养用户的保费总金额
		BigDecimal baoByPrice =new BigDecimal("0");
		//洗车用户的保费总金额
		BigDecimal baoXcPrice = new BigDecimal("0");
		//喷漆用户的保费总金额
		BigDecimal baoPqPrice = new BigDecimal("0");
		//下单用户的保费总金额
		BigDecimal baoTotalPrice = new BigDecimal("0");
		
		//小保养订单总金额
		BigDecimal orderByPrice =new BigDecimal("0");
		//洗车订单总金额
		BigDecimal orderXcPrice = new BigDecimal("0");
		//喷漆订单总金额
		BigDecimal orderPqPrice = new BigDecimal("0");
		//下单用户的订单总金额
		BigDecimal orderTotalPrice = new BigDecimal("0");
		
		totalNum=monthList.size();
		ArrayList<Long> baoByPriceList = new ArrayList<Long>();
		
		ArrayList<Long> baoXcPriceList = new ArrayList<Long>();
		
		ArrayList<Long> baoPqPriceList = new ArrayList<Long>();
		
		ArrayList<Long> baoTotalPriceList = new ArrayList<Long>();
		for(Map<String, Object> data:monthList){
			Long userId = (Long)data.get("userId");
			Integer orderType = (Integer)data.get("orderType");
			BigDecimal baoPrice = (BigDecimal)data.get("baoPrice");
			BigDecimal orderPrice = (BigDecimal)data.get("orderPrice");
			if(Objects.equal(2, orderType)){//小保养
				orderByPrice=orderByPrice.add(orderPrice);
				byNum++;
				if(!baoByPriceList.contains(userId)){
					baoByPrice=baoByPrice.add(baoPrice);
					baoByPriceList.add(userId);
				};
			}else if(Objects.equal(3, orderType)){//洗车
				orderXcPrice = orderXcPrice.add(orderPrice);
				xcNum++;
				if(!baoXcPriceList.contains(userId)){
					baoXcPrice=baoXcPrice.add(baoPrice);
					baoXcPriceList.add(userId);
				};
			}else if(Objects.equal(4, orderType)){//喷漆
				orderPqPrice = orderPqPrice.add(orderPrice);
				pqNum++;
				if(!baoPqPriceList.contains(userId)){
					baoPqPrice=baoPqPrice.add(baoPrice);
					baoPqPriceList.add(userId);
				};
			}
			orderTotalPrice = orderTotalPrice.add(orderPrice);
			if(!baoTotalPriceList.contains(userId)){
				baoTotalPrice=baoTotalPrice.add(baoPrice);
				baoTotalPriceList.add(userId);
			};
		}
		ArrayList<Map<String, String>> results = new ArrayList<Map<String,String>>();
		results.add(getMap("小保养",byNum,orderByPrice,baoByPrice,date,address,sourcecopy));
		results.add(getMap("洗车",xcNum,orderXcPrice,baoXcPrice,date,address,sourcecopy));
		results.add(getMap("喷漆",pqNum,orderPqPrice,baoPqPrice,date,address,sourcecopy));
		results.add(getMap("总计",totalNum,orderTotalPrice,baoTotalPrice,date,address,sourcecopy));
		return DatasetBuilder.fromDataList(results);
	}
	
	public Map<String,String> getMap(String orderType,
			Integer orderNum,BigDecimal orderPrice,BigDecimal baoPrice,
			String date,String address,String source){
		Map<String, String> map = new HashMap<String,String>();
		map.put("orderType", orderType);
		map.put("orderNum", String.valueOf(orderNum));
		map.put("orderPrice", String.valueOf(orderPrice));
		map.put("baoPrice", String.valueOf(baoPrice));
		map.put("date", date);
		map.put("address", address);
		map.put("source", source);
		return map;
	};
}
