package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.SurveyRecord;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


public interface SurveyRecordService {
	/**
	 * admin查询勘探录入信息
	 * @param pageNo
	 * @param pageSize
	 * @param surveyRecord
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<SurveyRecord> adminQueryRecord(Integer pageNo,Integer pageSize,SurveyRecord surveyRecord);
	
	/**
	 * 添加查勘录入信息
	 * @param surveyRecord
	 * @return
	 */
	public DatasetSimple<Boolean> addRecord(SurveyRecord surveyRecord);
	
	/**
	 * 导出查勘录入的信息
	 * @return
	 */
	public DatasetList<SurveyRecord> exportRecord(String date);
	
	
}
