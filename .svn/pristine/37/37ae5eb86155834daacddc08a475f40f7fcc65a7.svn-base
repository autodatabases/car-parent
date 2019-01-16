package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.business.model.AutoParts;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface AutoPartService {
	
	/**
	 * 配件列表
	 * @return
	 */
	public DatasetList<AutoParts> autoPartList(AutoParts autoPart,Integer pageNo,Integer pageSize);
	
	/**
	 * 新增配件
	 * @return
	 */
	public DatasetSimple<Map<String,String>> addAutoPart(AutoParts autoPart);
	
	/**
	 * 删除配件
	 * @param autoPartId
	 * @return
	 */
	public DatasetSimple<Map<String,String>> delteAutoPart(Long autoPartId);
	
	
	/**
	 * 匹配配件
	 * @param autoPartId
	 * @return
	 */
	public DatasetSimple<Map<String,String>> matchAutoParts(Long autoId,Long autoPartId,Integer type);
	
}
