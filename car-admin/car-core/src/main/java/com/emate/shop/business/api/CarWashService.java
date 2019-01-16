package com.emate.shop.business.api;

import com.emate.shop.business.model.CarWash;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.Orders;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CarWashService {
	
	/**
	 * 添加发券信息
	 * @return
	 */
	public DatasetSimple<CarWash> addOrUpdateCoupon(CarWash wash);
	
	/**
	 * 添加发券信息
	 * @return
	 */
	public DatasetSimple<CarWashSheng> addOrUpdateCarWash(CarWashSheng carWashSheng);
	
	/**
	 * 添加洗车订单
	 * @return
	 */
	public DatasetSimple<String> addXiCheOrder(Orders order);
	
	/**
	 * 查询发券信息
	 * @return
	 */
	public DatasetSimple<CarWash> queryByOrderNo(String orderNo);
	
	/**
	 * 查询未使用券
	 * @return
	 */
	public DatasetList<CarWash> queryUnUsed(Long userId);
	
	/**
	 * 查询盛大未使用券
	 * @return
	 */
	public DatasetList<CarWashSheng> queryUnUsedSheng(Long userId,Integer washType);
	
	/**
	 * 根据订单号查询洗车劵
	 * @param orderNo
	 * @return
	 */
	DatasetSimple<CarWashSheng> queryShengCouponByOrderNo(String orderNo,Integer washType,String status);
	
	/**
	 * 查询盛大已使用洗车券
	 * @param userId
	 * @param washType
	 * @return
	 */
	public DatasetSimple<Integer> queryUsedSheng(Long userId,Integer washType);
}
	
	
