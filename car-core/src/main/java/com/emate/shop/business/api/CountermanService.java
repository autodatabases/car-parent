package com.emate.shop.business.api;

import java.util.List;


//import java.util.List;



import java.util.Map;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.Counterman;
//import com.emate.shop.business.model.CountermanInfo;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CountermanService {
	
	@Cache(expired=60)
	public DatasetList<Counterman> adminCountermanList(Integer pageNo,Integer pageSize,Counterman counterman,Long userId);
	
	@Cache(expired=60)
	public DatasetSimple<Counterman> queryCountermanById(String id);
	
	public DatasetSimple<Boolean> addCounterman(Counterman counterman,Long userId);
	
	public DatasetSimple<Boolean> updateCounterman(Counterman counterman,Long userId);
	
	//public DatasetSimple<Map<String,Object>> importCountermanInfo(List<Map<String, Object>> params,Long userId);
	
	
	//@Cache(expired=60)
	//public DatasetList<CountermanInfo> adminCountermanInfoList(Integer pageNo,Integer pageSize,CountermanInfo countermanInfo);
	
	public DatasetSimple<Map<String, String>> bindCounterman(Long userId, String countermanCode,String realName);
	
/*	public DatasetList<Map<String,Object>> getCountermanInfo(Long userId);
	
	public DatasetList<CountermanCareer> getCountermanInfo1(Long userId);*/
	
	public DatasetSimple<Map<String,Object>> h5GetCountermanByUserId(Long userId);
	
	//导入业务员
	public DatasetSimple<Map<String,Object>> importCounterman(List<Map<String, Object>> data,Long userId);
	
	/**
	 * 查询业务员积分记录
	 * @param pageNo
	 * @param pageSize
	 * @param counterman
	 * @param userId
	 * @return
	 */
	public DatasetList<Map<String,Object>> queryScoreRecord(Integer pageNo, Integer pageSize, Counterman counterman,Long userId);
	
	/**
	 * 批量更新业务员积分
	 * @param data
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> batchUpdateCounterman(List<Map<String, Object>> data,Long userId);
	
	/**
	 * 导出业务员积分情况
	 * @return
	 */
	public DatasetList<Map<String,String>> exportCountermanExcel(Long userId);
}
