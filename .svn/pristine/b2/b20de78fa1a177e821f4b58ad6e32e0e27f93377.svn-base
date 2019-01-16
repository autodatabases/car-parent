package com.emate.shop.business.api;

import java.util.Map;


import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface AutoInfoService {
	/**
	 * 查询所有车型
	 * @param pageNo
	 * @param pageSize
	 * @param autoInfo
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<Map<String,Object>> adminAutoInfoList(Integer pageNo,Integer pageSize,AutoInfo autoInfo);
	/**
	 * 根据id查询一个车型信息
	 * @param id
	 * @return
	 */
	@Cache(expired=60)
	public DatasetSimple<AutoInfo> queryAutoInfoById(String id);
	/**
	 * 删除车型
	 * @param id
	 * @return
	 */
	public DatasetSimple<Map<String,String>> deleteAutoInfo(String id);
	/**
	 * 新增车型
	 * @param autoInfo
	 * @return
	 */
	public DatasetSimple<Map<String,String>> addAutoInfo(AutoInfo autoInfo);
	
	/**
	 * 根据条件查询所有车型
	 * @param carType
	 * @return
	 */
	@Cache(expired=60)
	public DatasetSimple<Autopose> queryAutoPoseByCondition(String carType);
	
	/**
	 * 根据汽车品牌是否存在该品牌
	 * @param carType
	 * @return
	 */
	@Cache(expired=60)
	public DatasetSimple<Boolean> queryAutoposeByBrand(String carBrand);
}
