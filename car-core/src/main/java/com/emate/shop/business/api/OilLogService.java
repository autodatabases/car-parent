package com.emate.shop.business.api;

import com.emate.shop.business.model.OilLog;
import com.emate.shop.rpc.dto.DatasetList;

public interface OilLogService {

	public DatasetList<OilLog> oilLogList(Integer pageNo,Integer pageSize,
					String orderNo);
}
