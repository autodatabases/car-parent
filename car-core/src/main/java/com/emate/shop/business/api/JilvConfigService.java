package com.emate.shop.business.api;

import java.util.List;

import com.emate.shop.business.model.JilvConfig;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 机滤配置
 * @author dong
 *
 */
public interface JilvConfigService {

	/**
	 * admin分页查找机滤配置
	 * @param pageNo
	 * @param pageSize
	 * @param cityName
	 * @return
	 */
	public DatasetList<JilvConfig> adminQueryPage(Integer pageNo,Integer pageSize,String cityName);
	
	/**
	 * admin根据城市查找机滤配置
	 * @param cityName
	 * @return
	 */
	public DatasetList<JilvConfig> adminQueryByCityName(String cityName);
	
	/**
	 * admin修改或添加机滤配置
	 * @param jilvConfigs
	 * @return
	 */
	public DatasetSimple<Boolean> updateConfig(List<JilvConfig> jilvConfigs);
	
	/**
	 * admin根据城市删除机滤配置
	 * @param cityName
	 * @return
	 */
	public DatasetSimple<Boolean> delJilvConfig(String cityName);
	
}
