package com.emate.shop.business.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.emate.shop.business.api.MerchantService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Merchant;
import com.emate.shop.business.model.MerchantExample;
import com.emate.shop.business.model.MerchantExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.mapper.MerchantMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;

/**
 * 加油站service
 * @author dong
 *
 */
@Service
public class MerchantServiceImpl implements MerchantService{

	@Resource
	private MerchantMapper merchantMapper;

	@Override
	@Read
	public DatasetList<Merchant> h5MerchantList(Integer pageNo,Integer pageSize,Merchant merchant) {
		MerchantExample ex = new MerchantExample();
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(merchant.getCity())){
			c.andCityEqualTo(merchant.getCity()+"市");
		}
		if(StringUtils.isNotEmpty(merchant.getArea())){
			c.andAreaEqualTo(merchant.getArea());
		}
		if(StringUtils.isNotEmpty(merchant.getMerchantType().toString())){
			c.andMerchantTypeEqualTo(merchant.getMerchantType());
		}
		if(StringUtils.isNotEmpty(merchant.getStoreName())){
			c.andStoreNameLike("%"+merchant.getStoreName()+"%");
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, merchantMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<Merchant> merchants = merchantMapper.selectByExample(ex);
		return DatasetBuilder.fromDataList(merchants,page.createPageInfo());
	}
}
