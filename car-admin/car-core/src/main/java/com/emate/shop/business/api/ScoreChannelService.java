package com.emate.shop.business.api;

import java.util.List;
import java.util.Map;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.ScoreChannel;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface ScoreChannelService {

	/**
	 * admin查询所有渠道
	 * @param scoreChannel
	 * @return
	 */
	@Cache(expired= 60)
	public DatasetList<Map<String,Object>> adminQueryScoreChannelList(Integer pageNo,Integer pageSize,ScoreChannel scoreChannel);
	
	/**
	 * 根据条件查询
	 * @param scoreChannel
	 * @return
	 */
	public DatasetList<ScoreChannel> queryScoreChannel(ScoreChannel scoreChannel);
	
	/**
	 * 添加积分商城的城市渠道规则
	 * @param scoreChannels
	 * @return
	 */
	public DatasetSimple<Boolean> addOrUpdateScoreChannel(List<ScoreChannel> scoreChannels);
	
	/**
	 * 删除积分商城的该城市该渠道规则
	 * @param scoreChannels
	 * @return
	 */
	public DatasetSimple<Boolean> delScoreChannel(ScoreChannel scoreChannel);
	
}
