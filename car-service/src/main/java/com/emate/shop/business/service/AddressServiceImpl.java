package com.emate.shop.business.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.AddressService;
import com.emate.shop.business.model.Regions;
import com.emate.shop.business.model.RegionsExample;
import com.emate.shop.business.model.UserAddress;
import com.emate.shop.business.model.UserAddressExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.RegionsMapper;
import com.emate.shop.mapper.UserAddressMapper;
import com.emate.shop.mapper.UserInfoMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class AddressServiceImpl implements AddressService{

	@Resource
	private RegionsMapper regionsMapper;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private UserAddressMapper userAddressMapper;
	
	@Read
	@Override 
	public DatasetList<Regions> queryAllProvince() {
		RegionsExample ex = new RegionsExample();
		ex.createCriteria().andParentidEqualTo(0);
		return DatasetBuilder.fromDataList(regionsMapper.selectByExample(ex));
	}

	@Read
	@Override
	public DatasetList<Regions> queryAllCityByProvince(String provinceId) {
		RegionsExample ex = new RegionsExample();
		ex.createCriteria().andParentidEqualTo(Integer.parseInt(provinceId));
		return DatasetBuilder.fromDataList(regionsMapper.selectByExample(ex));
	}

	@Read
	@Override
	public DatasetList<Regions> queryAllAreaByCity(String cityId) {
		RegionsExample ex = new RegionsExample();
		ex.createCriteria().andParentidEqualTo(Integer.parseInt(cityId));
		return DatasetBuilder.fromDataList(regionsMapper.selectByExample(ex));
	}
	
	@Read
	@Override
	public DatasetList<Regions> searchByKeyWord(String provinceId,String keyword){
		RegionsExample ex = new RegionsExample();
		com.emate.shop.business.model.RegionsExample.Criteria  c = ex.createCriteria();
		if(StringUtils.isNotEmpty(provinceId)){
			c.andParentidEqualTo(Integer.parseInt(provinceId));
		}
		c.andRegionnameLike("%"+keyword+"%")
		.andRegiontypeEqualTo(Regions.REGIONTYPE_2);//查询城市
		return DatasetBuilder.fromDataList(regionsMapper.selectByExample(ex));
	}
	
	@Write
	@Override
	@Transactional
	public DatasetSimple<Boolean> addUserAddress(UserAddress address) {
		UserAddressExample ex = new UserAddressExample();
		ex.createCriteria().andUserIdEqualTo(address.getUserId());
		List<UserAddress>  list = userAddressMapper.selectByExample(ex);
		if(list.size()>0){
			address.setId(list.get(0).getId());
			if(userAddressMapper.updateByPrimaryKeySelective(address)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
			throw new BusinessException("更新用户地址出错！");
		}else{
			if(userAddressMapper.insertSelective(address) == 1){
				return DatasetBuilder.fromDataSimple(true);
			}
			throw new BusinessException("新增用户地址出错！");
		}
	}
	
	/**
	 * 查询用户地址
	 * @param address
	 * @return
	 */
	@Read
	@Override
	public DatasetSimple<UserAddress> queryUserAddress(Long userId) {
		UserAddressExample ex = new UserAddressExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<UserAddress>  list = userAddressMapper.selectByExample(ex);
		if(list.size()>0){
			return DatasetBuilder.fromDataSimple(list.get(0));
		}else{
			return DatasetBuilder.fromMessageSimple("查询用户地址信息为空！");
		}
	}

}
