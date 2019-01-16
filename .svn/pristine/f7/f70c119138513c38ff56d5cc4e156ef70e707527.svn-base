package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;

import com.emate.shop.business.model.ServiceOperatorLog;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
/*
 * 发送服务日志查询
 */
public interface ServiceOperatorLogService {
	/**
	 * 查看赠送服务次数日志
	 * @param pageNo
	 * @param pageSize
	 * @param num
	 * @param serviceType
	 * @param verifyStatus
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<ServiceOperatorLog> adminServiceLogList(Integer pageNo,
			Integer pageSize,String num,
			String serviceType,String verifyStatus,String chePai);
	/**
	 * 审核,添加服务次数
	 * @param serviceId
	 * @param adminId
	 * @param verifyStatus
	 * @return
	 */
	public DatasetSimple<String> checkLog(Long serviceId,Long adminId,String verifyStatus);
	
	/**
	 * 更新批量备注信息
	 * @param remark
	 * @param id
	 * @return
	 */
	public DatasetSimple<String> updateRemark(String remark,Long id);
}
