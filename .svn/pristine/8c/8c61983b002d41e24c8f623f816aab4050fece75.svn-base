package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.UserMsg;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface UserMsgService {
	
	@Cache
	public DatasetList<UserMsg> queryMsgList(Long userId);
	
	public DatasetSimple<UserMsg> queryMsgById(Long id);
}
