package com.emate.shop.business.api;

import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerInfo;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface SellerInfoService {
	
	/**
	 * 更新商家信息
	 * @param seller
	 * @return
	 */
	public DatasetSimple<Boolean> addSellerInfo(SellerInfo info);
	
	/**
	 * 更新商家信息
	 * @param seller
	 * @return
	 */
	public DatasetSimple<Boolean> updateSeller(Seller seller);
	
	/**
	 * 添加商家信息
	 * @param seller
	 * @return
	 */
	public DatasetSimple<String> addSeller(Seller seller);
	
	/**
	 * 获取商家信息
	 * @param seller
	 * @return
	 */
	public DatasetSimple<SellerInfo> querySellerInfo(Long sellerId);
	
	/**
	 * 删除商家
	 * @param seller
	 * @return
	 */
	public DatasetSimple<String> deleteSeller(Long sellerId);
	
	/**
	 * 更新商家状态
	 * @param seller
	 * @return
	 */
	public DatasetSimple<Boolean> updateSellerStatus(Seller seller);
	
}
