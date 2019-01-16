package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.business.model.JicaiOrder;

import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface JicaiOrderService {

	public DatasetSimple<Boolean> addJiCaiOrder(JicaiOrder jiCaiOrder);
	
	public DatasetList<JicaiOrder> adminJicaiOrderListPage(Integer pageNo,Integer pageSize,JicaiOrder jicaiOrder);
	
	public DatasetSimple<Map<String, Object>> updatejicaiOrder(String orderNo);
	
	public DatasetSimple<JicaiOrder> jicaiOrderDetail(String orderNo);
}
