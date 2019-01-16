package com.emate.shop.business.api;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.Regions;
import com.emate.shop.business.model.UserAddress;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface AddressService {
	
	/**
	 * 查询所有省份
	 * @return
	 */
	@Cache(expired=60*24*3)
	public DatasetList<Regions> queryAllProvince();
	

	/**
	 *  根据查询所有城市
	 * @param provinceId 省份id
	 * @return
	 */
	@Cache(expired=60*24*3)
	public DatasetList<Regions> queryAllCityByProvince(String provinceId);
	
	/**
	 * 根据城市查询区域
	 * @param cityId
	 * @return
	 */
	@Cache(expired=60*24*3)
	public DatasetList<Regions> queryAllAreaByCity(String cityId);
	
	
	/**
	 * 筛选城市根据省份id gd = 20
	 * @param provinceId
	 * @param keyword
	 * @return
	 */
	@Cache
	public DatasetList<Regions> searchByKeyWord(String provinceId,String keyword);
	
	/**
	 * 添加用户收货地址
	 * @param provinceId
	 * @param keyword
	 * @return
	 */
	public DatasetSimple<Boolean> addUserAddress(UserAddress address);
	
	/**
	 * 查询用户收货地址
	 * @param provinceId
	 * @param keyword
	 * @return
	 */
	public DatasetSimple<UserAddress> queryUserAddress(Long userId);
	
}
