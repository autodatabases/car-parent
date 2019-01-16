package com.emate.shop.business.api;

import com.emate.shop.business.model.News;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface MsgService {
	
	public DatasetSimple<News> queryNewsById(Long newsId);
	
	public DatasetSimple<Boolean> updateCount(Long newsId);
}

