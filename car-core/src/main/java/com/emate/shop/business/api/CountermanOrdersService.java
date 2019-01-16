package com.emate.shop.business.api;

import java.util.Map;
import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.CountermanOrders;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CountermanOrdersService {
	
	@Cache(expired=60)
	public DatasetList<Map<String,Object>> adminCountermanOrdersList(Integer pageNo,Integer pageSize,String countermanPhone,String orderStatus,String startTimes,String endTimes,String orderNo,Long userId);
	
	@Cache(expired=60)
	public DatasetSimple<Map<String,Object>> queryCountermanOrdersById(String id);
	
	public DatasetSimple<String> addCountermanOrders(CountermanOrders countermanOrders,Long userId);
	
	public DatasetSimple<Map<String, Object>> addOrderRemark(String orderNo,String remark);
	
	public DatasetSimple<Map<String,Object>> updateCountermanOrders(String orderNo,String status);
}
