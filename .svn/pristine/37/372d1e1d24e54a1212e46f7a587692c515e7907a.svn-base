package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.CountermanLifeCareer;
import com.emate.shop.rpc.dto.DatasetList;

public interface CountermanLifeCareerService {
	
	@Cache(expired=60)
	public DatasetList<CountermanLifeCareer> queryAllCountermanLifeCareer();
	
}
