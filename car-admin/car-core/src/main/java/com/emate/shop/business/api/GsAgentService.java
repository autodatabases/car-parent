package com.emate.shop.business.api;

import java.util.List;
import java.util.Map;


import com.emate.shop.business.model.GsAgent;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;



public interface GsAgentService {
	/**
	 * 查询所有机构
	 * @return
	 */
	public DatasetList<String> h5DotList(String jigou);
	
	/**
	 * 查询所有代理公司
	 * @return
	 */
	public DatasetList<GsAgent> h5GsAgentList(String dotName,String agent);
	/**
	 * 根据id查询某个代理公司
	 * @return
	 */
	public DatasetList<Map<String,Object>> h5QueryGsAgentData(String agentId,String year);
	
	/**
	 * 导入代理公司数据
	 * @param dataList
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> importGsAgentData(List<Map<String,Object>> dataList);

}
