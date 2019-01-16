package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.rpc.dto.DatasetList;

public interface AnalysisService {
	
	/**
	 * 根据月份和地址查询导入保单的一些信息
	 * @param date
	 * @param address
	 * @return
	 */
	public DatasetList<Map<String,String>> analysisBaoDan(String date,String address,String source);
	
	/**
	 * 根据月份和地址查询订单的一些信息
	 * @param date
	 * @param address
	 * @return
	 */
	public DatasetList<Map<String,String>> analysisOrders(String date,String address,String source);
}
