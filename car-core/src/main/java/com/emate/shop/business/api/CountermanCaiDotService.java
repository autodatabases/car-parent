package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CountermanCaiDotService {
	@Cache(expired=60)
	public DatasetList<CountermanCaiDot> adminCountermanCaiDotList(Integer pageNo,Integer pageSize,CountermanCaiDot countermanCaiDot,Long userId);
	
	@Cache(expired=60)
	public DatasetSimple<CountermanCaiDot> queryCountermanCaiDotById(String id);
	
	public DatasetSimple<Boolean> addOrUpdateCountermanCaiDot(CountermanCaiDot countermanCaiDot);
	
	public DatasetSimple<Boolean> deleteCountermanCaiDotById(String id);
	
	public DatasetList<CountermanCaiDot> queryCaiDotList(Long userId);
}
