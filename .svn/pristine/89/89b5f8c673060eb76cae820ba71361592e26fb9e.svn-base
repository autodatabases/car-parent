package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.Merchant;
import com.emate.shop.rpc.dto.DatasetList;

public interface MerchantService {
	
	/**
	 * 用户查询加油站列表
	 * @param province
	 * @param city
	 * @param area
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<Merchant> h5MerchantList(Integer pageNo,Integer pageSize,Merchant merchant);

}
