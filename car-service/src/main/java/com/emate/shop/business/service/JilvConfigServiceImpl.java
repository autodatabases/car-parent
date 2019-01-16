package com.emate.shop.business.service;

import java.util.Date;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.JilvConfigService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.JilvConfig;
import com.emate.shop.business.model.JilvConfigExample;
import com.emate.shop.business.model.JilvConfigExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.JilvConfigMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 机滤配置
 * @author dong
 *
 */
@Service
public class JilvConfigServiceImpl implements JilvConfigService {
	
	@Resource
	private JilvConfigMapper jilvConfigMapper;
	
	@Override
	@Read
	public DatasetList<JilvConfig> adminQueryPage(Integer pageNo, Integer pageSize, String cityName) {
		//设置查询条件
		JilvConfigExample jilvConfigEx = new JilvConfigExample();
		if(!StringUtils.isEmpty(cityName)){
			jilvConfigEx.createCriteria().andCityNameEqualTo(cityName);
		}
		//组织分页数
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, jilvConfigMapper.countByExample(jilvConfigEx));
		jilvConfigEx.setLimitStart(page.getStartRow());
		jilvConfigEx.setLimitEnd(page.getSize());
		//查询jilv_config表
		List<JilvConfig> jilvConfigs = jilvConfigMapper.selectByExample(jilvConfigEx);
		//返回结果
		return DatasetBuilder.fromDataList(jilvConfigs, page.createPageInfo());
	}

	@Override
	@Read
	public DatasetList<JilvConfig> adminQueryByCityName(String cityName) {
		
		//判断参数不能为空
		if(StringUtils.isEmpty(cityName)){
			throw new BusinessException("参数:城市名称不能为空~");
		}
		
		//设置查询条件
		JilvConfigExample jilvConfigEx = new JilvConfigExample();
		jilvConfigEx.createCriteria().andCityNameEqualTo(cityName);
		
		//查询jilv_config表
		List<JilvConfig> jilvConfigs = jilvConfigMapper.selectByExample(jilvConfigEx);
		
		return DatasetBuilder.fromDataList(jilvConfigs);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> updateConfig(List<JilvConfig> jilvConfigs) {
		JilvConfigExample jilvConfigEx = new JilvConfigExample();
		Criteria c = jilvConfigEx.createCriteria();
		c.andCityNameEqualTo(jilvConfigs.get(0).getCityName());
		List<JilvConfig> config = jilvConfigMapper.selectByExample(jilvConfigEx);
		if(!config.isEmpty()){
			jilvConfigMapper.deleteByExample(jilvConfigEx);
		}
		for (JilvConfig jilvConfig : jilvConfigs) {
			jilvConfig.setCreateTime(new Date());
			jilvConfig.setUpdateTime(new Date());
			if(jilvConfigMapper.insertSelective(jilvConfig)!=1){
				throw new BusinessException("添加机滤配置失败~");
			};
		}
		return DatasetBuilder.fromDataSimple(true);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> delJilvConfig(String cityName) {
		//校验城市参数
		if(StringUtils.isEmpty(cityName)){
			throw new BusinessException("参数:城市名称不能空");
		}
		//添加城市查询条件
		JilvConfigExample jilvConfigEx = new JilvConfigExample();
		jilvConfigEx.createCriteria().andCityNameEqualTo(cityName);
		
		//修改jilv_config表
		if(!jilvConfigMapper.selectByExample(jilvConfigEx).isEmpty()){
			jilvConfigMapper.deleteByExample(jilvConfigEx);
		}
		//返回结果
		return DatasetBuilder.fromDataSimple(true);
	}
}
