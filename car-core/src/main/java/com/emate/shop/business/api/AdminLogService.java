package com.emate.shop.business.api;

import com.emate.shop.rpc.dto.DatasetSimple;

public interface AdminLogService {
	
	public DatasetSimple<String> deleteUnActiveLog();
}
