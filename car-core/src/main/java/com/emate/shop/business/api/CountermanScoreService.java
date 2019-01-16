package com.emate.shop.business.api;

//import java.util.Map;


import com.emate.shop.business.api.cache.Cache;
//import com.emate.shop.business.model.CountermanScore;
import com.emate.shop.business.model.CountermanScoreRecord;
import com.emate.shop.rpc.dto.DatasetList;
//import com.emate.shop.rpc.dto.DatasetSimple;

public interface CountermanScoreService {
	/*
	@Cache(expired=60)
	public DatasetList<CountermanScore> adminCountermanScoreList(Integer pageNo,Integer pageSize,CountermanScore countermanScore);
	
	@Cache(expired=60)
	public DatasetSimple<CountermanScore> queryCountermanScoreById(String id);
	
	public DatasetSimple<Boolean> addOrUpdateCountermanScore(CountermanScore countermanScore,String remark,Long userId);
	*/
	@Cache(expired=60)
	public DatasetList<CountermanScoreRecord> h5CountermanScoreRecordList(Integer pageNo,Integer pageSize,Long userId);
	
	/**
	 * 业务员积分季度清零定时任务
	 * @return
	 */
	//public DatasetSimple<Map<String, String>> delCountermanScoreQuartz();
	/**
	 * 根据userId获取积分信息
	 * @param userId
	 * @return
	 */
/*	@Cache(expired=60)
	public DatasetList<CountermanScore> h5GetCountermanScoreByUserId(Long userId);*/
	
	/*@Cache(expired=60)
	public DatasetList<CountermanScoreRecord> adminCountermanScoreRecordList(Integer pageNo,Integer pageSize,CountermanScoreRecord countermanScoreRecord);*/
	
	/*@Cache(expired=60)
	public DatasetSimple<Map<String,Object>> queryCountermanScoreByCode(String code);*/
}
