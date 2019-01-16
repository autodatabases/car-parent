package com.emate.shop.business.api;

import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
public interface ImportUserInfoService {
	/**
	 * 导入保单用户
	 * @param params
	 * @return
	 */
	//public DatasetSimple<Map<String,Object>> importUserInfo(List<Map<String, Object>> params);
	/**
	 * 一键验证正常保单用户是否没有匹配车型
	 * @return
	 */
	public DatasetList<ImportUserInfo> matchChe();
	
	//根据车牌查找车辆信息
	public DatasetSimple<ImportUserInfo> getCarInfoByLicense(String license);
}
