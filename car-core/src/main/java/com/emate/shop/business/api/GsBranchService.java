package com.emate.shop.business.api;

import java.util.Map;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


public interface GsBranchService {
	/**
	 * 查询所有定损中心
	 * @return
	 */
	public DatasetList<Map<String,Object>> h5GsDataList(String city);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> h5SurveyCenter(Long id);

}
