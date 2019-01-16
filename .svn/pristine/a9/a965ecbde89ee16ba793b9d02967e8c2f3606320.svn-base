package com.emate.shop.business.api;

import java.util.List;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CarWashConfigService {
	/**
	 * admin查询
	 * @param pageNo
	 * @param pageSize
	 * @param carwashConfig
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<CarwashConfig> adminQueryCarWashConfigList(Integer pageNo,Integer pageSize,CarwashConfig carwashConfig);
	
	/**
	 * 根据城市查询洗车配置渠道
	 * @param carwashConfig
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<CarwashConfig> queryCarWashConfig(String cityName);
	
	/**
	 * 添加或修改洗车配置渠道
	 * @param carwashConfigs
	 * @return
	 */
	public DatasetSimple<Boolean> addOrUpdateCarWashConfig(List<CarwashConfig> carwashConfigs);
	
	/**
	 * 根据城市删除洗车渠道
	 * @param carwashConfig
	 * @return
	 */
	public DatasetSimple<Boolean> delCarwashConfig(String cityName);
	
}
