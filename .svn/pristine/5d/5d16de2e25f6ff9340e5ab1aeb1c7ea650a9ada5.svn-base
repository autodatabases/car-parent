package com.emate.shop.business.api;

import java.util.Map;
import com.emate.shop.business.model.DriverOrderSheng;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface DriverOrderShengService {
	/**
	 * 创建代驾订单
	 * @param order
	 * @return
	 */
	public DatasetSimple<String> createDriverOrder(String driverOrderUrl,Map<String,String> params);
	
	/**
	 * 更改预约时间
	 * @param appointmentTime
	 * @return
	 */
	public DatasetSimple<String> updateAppointmentTime(String orderNo,String appointmentTime,String orderUrl,String source);
	
	
	/**
	 * 代驾订单记录
	 * @param userId
	 * @return
	 */
	public DatasetList<DriverOrderSheng> queryDriverOrderList(Long userId,Integer pageNo,Integer pageSize);
	
	/**
	 * 油卡用户取消订单
	 * @param orderNo
	 * @return
	 */
	public DatasetSimple<String> cancelDriverOrder(String orderNo,String cancelOrderUrl,String source,String key);
	
	/**
	 * admin端查询代驾订单记录
	 * @param orderNo
	 * @param phone
	 * @param pageNo
	 * @param pageSize
	 * @param orderStatus
	 * @return
	 */
	public DatasetList<DriverOrderSheng> adminQueryOrderList(String orderNo,String phone,
			Integer pageNo,Integer pageSize,String orderStatus);
	
	/**
	 * 更改代驾订单备注
	 * @param orderNo
	 * @param remark
	 * @return
	 */
	public DatasetSimple<Map<String,String>> updateDriverRemark(String orderNo,String remark);
	
	/**
	 * 更新代驾订单
	 * @param source
	 * @param orderNo
	 * @param status
	 * @param driverName
	 * @param driverPhone
	 * @return
	 */
	public DatasetSimple<Map<String, String>> updateDriverOrder(String source,
			String orderNo,String status,String driverName,String driverPhone);
	

	public DatasetSimple<DriverOrderSheng> h5queryorderDetail(String orderNo);
}
