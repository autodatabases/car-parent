package com.emate.shop.business.api;

import java.util.Map;
import com.emate.shop.business.model.OilMakeOrder;
import com.emate.shop.business.model.OilMakeRecord;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
public interface OilMakeService {
	
	/**
	 * 查询做卡一级主表所有记录
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public DatasetList<OilMakeRecord> queryAllRecord(Integer pageNo,
			Integer pageSize);
	
	/**
	 * 添加一级主表记录
	 * @param oilMakeRecord
	 * @return
	 */
	public DatasetSimple<String> addRecord(String money,String num,String deadTime,String remark);
	/**
	 * 根据条件查询做卡二级次表所有记录
	 * @param recordId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public DatasetList<OilMakeOrder> queryAllOrder(String recordId,
			String startNum,String endNum,Integer pageNo,
			Integer pageSize);
	
	/**
	 * 根据条件查询做卡三级次表所有记录
	 * @param orderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public DatasetList<Map<String,Object>> queryAllCode(String orderId,
			Integer pageNo,Integer pageSize);
	
	/**
	 * 添加二级表记录
	 * @param oilMakeOrder
	 * @return
	 */
	public DatasetSimple<String> addOrder();
	
	/**
	 * 添加三级表记录
	 * @param oilMakeOrder
	 * @return
	 */
	public DatasetSimple<String> addOilRechargeCode();
	
	/**
	 * 导出要做的油卡信息
	 * @param orderId
	 * @return
	 */
	public DatasetList<Map<String,String>> exportOilReChargeCode(
			String orderId);
	/**
	 * 导出要做的油卡信息2
	 * @param recordId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	public DatasetList<Map<String,String>> exportOilReChargeCodeTwo(
			String recordId,String startCode,String endCode);
}
	
	
