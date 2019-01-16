package com.emate.shop.business.api;

import java.util.Map;


import com.emate.shop.business.api.cache.Cache;

import com.emate.shop.business.model.CarWashProduct;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface CarWashProductService {
	/**
	 * 查找洗车商品
	 * @param pageNo
	 * @param pageSize
	 * @param carWashProduct
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<CarWashProduct> adminProductList(Integer pageNo,Integer pageSize,CarWashProduct carWashProduct);
	/**
	 * 根据id查找某个洗车商品
	 * @param id
	 * @return
	 */
	@Cache(expired=60)
	public DatasetSimple<CarWashProduct> queryProductById(String id);
	
	/**
	 * 添加或修改某个洗车商品
	 * @param carWashProduct
	 * @return
	 */
	public DatasetSimple<Boolean> editProduct(CarWashProduct carWashProduct);
	
	/**
	 * 根据id删除某个洗车商品
	 * @param id
	 * @return
	 */
	public DatasetSimple<Boolean> delProduct(String id);
	
	/**
	 * 查询购买洗车次数订单
	 * @param pageNo
	 * @param pageSize
	 * @param userPhone
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public DatasetList<Map<String,Object>> payList(Integer pageNo, Integer pageSize,
			String userPhone,String orderNo,String status,Long userId);
	
	/**
	 * 查询洗车劵订单
	 * @param pageNo
	 * @param pageSize
	 * @param userPhone
	 * @param orderNo
	 * @param shopName
	 * @param orderCode
	 * @return
	 */
	public DatasetList<CarWashSheng> shengList(Integer pageNo, Integer pageSize,
			String userPhone,String orderNo,String shopName,String orderCode,Long userId);
	/**
	 * 添加洗车订单
	 * @return
	 */
	public DatasetSimple<Boolean> addWashChargeOrder(String productId,Long userId);

	/**
	 * h5端查询所有洗车商品
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<CarWashProduct> h5WashProduct();
	
	/**
	 * h5端获取洗车劵
	 * @param userId
	 * @param washType
	 * @return
	 */
	public DatasetSimple<CarWashSheng> h5GetWashCoupon(Long userId,String washType);
}
