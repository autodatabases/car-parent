package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.Autobrand;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface BrandService {
	
	@Cache(expired=60*24*3)
	public DatasetList<Autobrand> queryBrandList();
	
	@Cache(expired=60*24*3)
	public DatasetList<Autopose> queryAutoPoseByBrand(String brand);
	
	public DatasetList<Autopose> queryAotoPoseByKeyword(String keyword);
	
	@Cache(expired=60*24*3)
	public DatasetSimple<Autopose> queryAutoposeById(Long id);
	
}
