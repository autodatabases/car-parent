package com.emate.shop.business.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.SellerInfoService;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerExample;
import com.emate.shop.business.model.SellerInfo;
import com.emate.shop.business.model.SellerInfoExample;
import com.emate.shop.business.model.WxUserBind;
import com.emate.shop.business.model.WxUserBindExample;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.SellerInfoMapper;
import com.emate.shop.mapper.SellerMapper;
import com.emate.shop.mapper.WxUserBindMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class SellerInfoServiceImpl implements SellerInfoService{

    @Resource
    private SellerInfoMapper sellerInfoMapper;
    
    @Resource
    private SellerMapper sellerMapper;
    
    @Resource
    private WxUserBindMapper wxUserBindMapper;

	@Override
	public DatasetSimple<Boolean> addSellerInfo(SellerInfo info) {
		info.setUpdateTime(new Date());
		SellerInfoExample sellerEx = new SellerInfoExample();
		sellerEx.createCriteria().andSellerIdEqualTo(info.getSellerId());
		List<SellerInfo> list = sellerInfoMapper.selectByExample(sellerEx);
		if(list.size()>0){
			info.setId(list.get(0).getId());
			if(sellerInfoMapper.updateByPrimaryKeySelective(info)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
			throw new BusinessException("更新商家信息出错！");
		}else{
			info.setStatus(SellerInfo.STATUS_0);//待审核
			info.setCreateTime(new Date());
			if(sellerInfoMapper.insertSelective(info)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
			throw new BusinessException("新增商家信息出错！");
		}
	}
	
	@Write
	@Override
	public DatasetSimple<Boolean> updateSeller(Seller seller){
		if(seller.getSellerPhone()!=null){
			SellerExample sellerEx = new SellerExample();
			sellerEx.createCriteria().andSellerPhoneEqualTo(seller.getSellerPhone());
			List<Seller>  s = sellerMapper.selectByExample(sellerEx);
			s.forEach(one -> {
				if(!one.getId().equals(seller.getId())){
					throw new BusinessException("更新商家信息失败，手机号重复！");
				}
			});
		}
		Seller oldSeller = sellerMapper.selectByPrimaryKey(seller.getId());
		if(!seller.getSellerPhone().equals(oldSeller.getSellerPhone())){
			WxUserBindExample wxUserBindEx = new WxUserBindExample();
			wxUserBindEx.createCriteria().andUserIdEqualTo(seller.getId());
			List<WxUserBind> wxUserBindList = wxUserBindMapper.selectByExample(wxUserBindEx);
			if(!wxUserBindList.isEmpty()){
				WxUserBind wxUserBind = new WxUserBind();
				wxUserBind.setUserName(seller.getSellerPhone());
				wxUserBindMapper.updateByExampleSelective(wxUserBind, wxUserBindEx);
			}
		}
		if(sellerMapper.updateByPrimaryKeySelective(seller)==1){
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("更新商家信息失败！");
	}
	
	/**
	 * 更新商家状态
	 */
	@Write
	@Override
	@Transactional
	public DatasetSimple<Boolean> updateSellerStatus(Seller seller){
		Seller oldSeller = sellerMapper.selectByPrimaryKey(seller.getId());
		if(Objects.isNull(oldSeller)){
			throw new BusinessException("该商家不存在!!");
		}
		if(sellerMapper.updateByPrimaryKeySelective(seller)==1){
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("更新商家信息失败！");
	}
	
	@Write
	@Override
	public DatasetSimple<String> addSeller(Seller seller){
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andSellerPhoneEqualTo(seller.getSellerPhone());
		if(sellerMapper.countByExample(sellerEx)>0){
			throw new BusinessException("新增商家信息失败,商家手机号已经存在！");
		}
		seller.setCreateTime(new Date());
		if(seller.getServicePrice()==null){
			seller.setServicePrice(new BigDecimal("50"));
		}
		if(sellerMapper.insertSelective(seller)==1){
			return DatasetBuilder.fromDataSimple(String.valueOf(seller.getId()));
		}
		throw new BusinessException("新增商家信息失败！");
	}
	
	@Write
	@Override
	public DatasetSimple<SellerInfo> querySellerInfo(Long sellerId) {
		SellerInfoExample sellerEx = new SellerInfoExample();
		sellerEx.createCriteria().andSellerIdEqualTo(sellerId);
		List<SellerInfo> list = sellerInfoMapper.selectByExample(sellerEx);
		if(list.size()>0){
			return DatasetBuilder.fromDataSimple(list.get(0));
		}else{
			return DatasetBuilder.fromMessageSimple("商家资质不存在！");
		}
	}
	
	@Write
	@Override
	@Transactional
	public DatasetSimple<String> deleteSeller(Long sellerId){
		if(sellerMapper.deleteByPrimaryKey(sellerId)==1){
			SellerInfoExample sellerInfoEx = new SellerInfoExample();
			sellerInfoEx.createCriteria().andSellerIdEqualTo(sellerId);
			if(sellerInfoMapper.deleteByExample(sellerInfoEx)==1){
				return DatasetBuilder.fromDataSimple("delete ok!");
			}
			throw new BusinessException("删除商家失败！");
		}
		throw new BusinessException("删除商家失败！");
	}
}
