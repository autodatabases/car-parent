package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.BusinessInfo;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 业务员的增删改查
 * @author dong
 *
 */
public interface BusinessInfoService {

	/**
	 * 业务员查询
	 * @param pageNo
	 * @param pageSize
	 * @param businessInfo
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<Map<String,Object>> adminBusinessInfoList(Integer pageNo,Integer pageSize,BusinessInfo businessInfo);
	/**
	 * 根据id查询业务员
	 * @param id
	 * @return
	 */
	@Cache(expired=60)
	public DatasetSimple<BusinessInfo> queryBusinessInfoById(String id);
	
	/**
	 * 删除业务员信息
	 * @param id
	 * @return
	 */
	public DatasetSimple<Map<String,String>> deleteBusinessInfo(String id);
	
	/**
	 * 添加业务员信息
	 * @param businessInfo
	 * @return
	 */
	
	public DatasetSimple<Map<String,String>> addBusinessInfo(BusinessInfo businessInfo);
}
