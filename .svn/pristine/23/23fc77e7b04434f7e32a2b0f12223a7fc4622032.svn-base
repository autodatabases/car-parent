package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 惠车服滴滴代驾券
 * @author 董夫行
 *
 */
public interface DidiService {

	public DatasetSimple<Map<String,Integer>> getCouponNum(Long userId);
	
	public DatasetList<Map<String,Object>> getCouponRecord(Integer pageNo,
			Integer pageSize,Long userId);
	
	public DatasetSimple<Map<String,Object>> addCouponOrders(String content,
			Long userId,String userIp);
}
