package com.emate.shop.business.service;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.CarWashConfigService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.business.model.CarwashConfigExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CarwashConfigMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class CarWashConfigServiceImpl implements CarWashConfigService{
	@Resource
	private CarwashConfigMapper carwashConfigMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Override
	@Read
	public DatasetList<CarwashConfig> adminQueryCarWashConfigList(Integer pageNo,Integer pageSize,CarwashConfig carwashConfig) {
		CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
		//排序
		carwashConfigEx.setOrderByClause(CarwashConfigExample.CREATE_TIME_DESC);
		//筛选条件
		if(StringUtils.isNotEmpty(carwashConfig.getCityName())){
			carwashConfigEx.createCriteria().andCityNameLike("%"+carwashConfig.getCityName() +"%");
		}
		//分页
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, carwashConfigMapper.countByExample(carwashConfigEx));
		carwashConfigEx.setLimitStart(page.getStartRow());
		carwashConfigEx.setLimitEnd(page.getSize());
		//查询结果
		List<CarwashConfig> scoreChannels = carwashConfigMapper.selectByExample(carwashConfigEx);
	
		return DatasetBuilder.fromDataList(scoreChannels,page.createPageInfo());
	}

	@Override
	@Read
	public DatasetList<CarwashConfig> queryCarWashConfig(String cityName) {
		if(StringUtils.isEmpty(cityName)){
			throw new BusinessException("参数城市不能为空");
		}
		//筛选条件:
		CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
		carwashConfigEx.createCriteria()
		//城市名称
		.andCityNameEqualTo(cityName);
		//查询结果
		List<CarwashConfig> carwashConfigs = carwashConfigMapper.selectByExample(carwashConfigEx);
		return DatasetBuilder.fromDataList(carwashConfigs);
	}
	
	@Override
	@Transactional
	@Write
	public DatasetSimple<Boolean> addOrUpdateCarWashConfig(List<CarwashConfig> carwashConfigs) {
		if(carwashConfigs.isEmpty()){
			throw new BusinessException("请填洗车配置渠道~");
		}
		if(!Objects.isNull(carwashConfigs.get(0).getPurchasePrice())){
			if(carwashConfigs.get(0).getPurchasePrice().longValue()>100000000||carwashConfigs.get(0).getPurchasePrice().compareTo(BigDecimal.ZERO)<0){
				throw new BusinessException("采购价格过大或采购价格为负数!");
			}
		}
		if(!Objects.isNull(carwashConfigs.get(0).getWashPrice())){
			if(carwashConfigs.get(0).getWashPrice().longValue()>100000000||carwashConfigs.get(0).getWashPrice().compareTo(BigDecimal.ZERO)<0){
				throw new BusinessException("洗车价格过大或洗车价格为负数!");
			}
		}
		for(CarwashConfig carwashConfig :carwashConfigs){
			if(!Objects.isNull(carwashConfig.getMinimum())){
				if(carwashConfig.getMinimum().longValue()>100000000||carwashConfig.getMinimum().compareTo(BigDecimal.ZERO)<0){
					throw new BusinessException("保单金额过大或金额为负数!");
				}
			}
			if(!Objects.isNull(carwashConfig.getMaximum())){
				if(carwashConfig.getMaximum().longValue()>100000000||carwashConfig.getMaximum().compareTo(BigDecimal.ZERO)<0){
					throw new BusinessException("保单金额过大或金额为负数!");
				}
			}
		}
		CarwashConfigExample carwashConfigEx = new CarwashConfigExample();
		carwashConfigEx.createCriteria()
		.andCityNameEqualTo(carwashConfigs.get(0).getCityName());
		List<CarwashConfig> carwashConfigss = carwashConfigMapper.selectByExample(carwashConfigEx);
		if(!carwashConfigss.isEmpty()){
			definedMapper.delCarwashConfig(carwashConfigs.get(0).getCityName());
		}
		for(CarwashConfig carwashConfig :carwashConfigs){
			carwashConfig.setCreateTime(new Date());
			carwashConfig.setUpdateTime(new Date());
			if(carwashConfigMapper.insertSelective(carwashConfig)!=1){
				throw new BusinessException("添加洗车配置渠道异常");
			}
		}
		return DatasetBuilder.fromDataSimple(true);
	}
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> delCarwashConfig(String cityName) {
		if(Objects.isNull(cityName)){
			throw new BusinessException("缺少城市参数");
		}
		if(definedMapper.delCarwashConfig(cityName)>0){
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("删除洗车配置渠道异常~");
	}
	
}
