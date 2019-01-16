package com.emate.shop.business.api;

import java.util.List;
import java.util.Map;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerReport;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface SellerService {
	
	/**
	 * admin查询所有商家列表
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<Seller> adminSellerList(Integer pageNo,Integer pageSize,Seller seller);
	
	@Cache(expired=60)
	public DatasetSimple<Seller> querySellerById(String id);
	
	/**
	 * 用户查询商家列表
	 * @param province
	 * @param city
	 * @param area
	 * @return
	 */
	public DatasetList<Seller> h5SellerList(Long userId,String type,String province,String city,String area,String sellerType,
			String sellerName,Integer pageNo,Integer pageSize,String isFixSeller);
	
	public DatasetSimple<Map<String,Object>> importUserInfo(List<Map<String, Object>> params);
	
	@Cache(expired=60)
	public DatasetSimple<Map<String,Object>> h5SelerDetail(Long id);
	
	/**
	 * admin查询所有商家结算列表
	 * @param pageNo
	 * @param pageSize
	 * @param date
	 * @param sellerName
	 * @return
	 */
	@Cache(expired=60)
	public DatasetList<Map<String,Object>> adminSellerReportList(Integer pageNo,Integer pageSize,String date,String companyName);
	
	/**
	 * 修改状态
	 * @param sellerReport
	 * @return
	 */

	public DatasetSimple<Boolean> updateSellerReport(SellerReport sellerReport);
	/**
	 * 赠送服务次数
	 * @param params
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> sendService(List<Map<String, Object>> chePais,Integer serviceType,Integer serviceValue,Long userId);
}
