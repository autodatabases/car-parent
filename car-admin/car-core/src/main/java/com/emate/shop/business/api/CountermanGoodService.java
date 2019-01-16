package com.emate.shop.business.api;

import java.util.List;
import java.util.Map;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CountermanGoodService {
	
	@Cache(expired=60)
	public DatasetList<CountermanGood> adminCountermanGoodList(Integer pageNo,Integer pageSize,CountermanGood countermanGood,Long userId);
	
	@Cache(expired=60)
	public DatasetSimple<CountermanGood> queryCountermanGoodById(String id);
	
	public DatasetSimple<Boolean> addOrUpdateCountermanGood(CountermanGood countermanGood,Long userId);
	
	@Cache(expired=60)
	public DatasetList<CountermanGood> h5CountermanGoodList(Long userId,Integer pageNo,Integer pageSize,String belongArea);
	
	
	public DatasetSimple<Map<String,Object>> batchInsertGood(List<Map<String, Object>> data,Long userId);
	
}
