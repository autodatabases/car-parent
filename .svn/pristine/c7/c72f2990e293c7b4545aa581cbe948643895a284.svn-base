package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;

import com.emate.shop.business.model.CountermanCareer;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CountermanCareerService {
	
	@Cache(expired=60)
	public DatasetList<CountermanCareer> adminCountermanCareerList(Integer pageNo,Integer pageSize,CountermanCareer countermanCareer);
	
	@Cache(expired=60)
	public DatasetSimple<CountermanCareer> queryCountermanCareerById(String id);
	
	public DatasetSimple<Boolean> addOrUpdateCountermanCareer(CountermanCareer countermanCareer);

	public DatasetSimple<Boolean> deleteCountermanCareerById(String id);
	
}
