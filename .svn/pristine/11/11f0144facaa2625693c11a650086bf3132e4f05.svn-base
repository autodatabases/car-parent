package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface FeedbackService {
	
	/**
	 * 添加用户反馈
	 * @param id
	 * @return
	 */
	public DatasetSimple<Boolean> addFeedBack(Long userId,String userName,String content);
	
	/**
	 * 查看用户反馈
	 */
	public DatasetList<Map<String,Object>> queryFeedBack(Integer pageNo,Integer pageSize,String phone,String startTimes,String endTimes);

	/**
	 * 更新用户反馈
	 * @param id
	 * @param remark
	 * @return
	 */
	public DatasetSimple<Map<String,String>> updateFeedback(String id,String remark);
}
