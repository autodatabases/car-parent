package com.emate.shop.business.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.business.model.AccessToken;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerExample;
import com.emate.shop.business.model.WxUserBind;
import com.emate.shop.business.model.WxUserBindExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AccessTokenMapper;
import com.emate.shop.mapper.SellerMapper;
import com.emate.shop.mapper.WxUserBindMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class AccessTokenServiceImpl implements AccessTokenService{

    @Resource
    StandardPasswordEncoder passwordEncoder;
    
	@Resource
	private AccessTokenMapper accessTokenMapper;
	
	@Resource
	private SellerMapper sellerMapper;
	
	@Resource
	private WxUserBindMapper wxUserBindMapper;
	
	@Read
	@Override 
	public DatasetSimple<AccessToken> getAccessToken() {
		return DatasetBuilder.fromDataSimple(accessTokenMapper.selectByPrimaryKey(1l));
	}

	@Write
	@Override
	public DatasetSimple<Boolean> updateAccessToken(AccessToken token) {
		return DatasetBuilder.fromDataSimple(accessTokenMapper.updateByPrimaryKeySelective(token) == 1);
	}

	@Write
	@Override
	@Transactional
	public DatasetSimple<WxUserBind> bindUser(String phoneNum, String openId,String code,String tokenCode) {
		if("".equals(tokenCode) || tokenCode == null){
			throw new BusinessException("验证码已经失效，请重新获取！");
		}
		try{
			if(!passwordEncoder.matches(code+phoneNum, tokenCode)){
				throw new BusinessException("验证码不正确！");
			}
		}catch(Exception e){
			throw new BusinessException("验证码不正确！");
		}
		SellerExample ex = new SellerExample();
		ex.createCriteria().andSellerPhoneEqualTo(phoneNum);
		if(sellerMapper.countByExample(ex)<1){
			throw new BusinessException("商家不存在！");
		}
		Seller seller = sellerMapper.selectByExample(ex).get(0);
		
		WxUserBindExample wxex = new WxUserBindExample();
		wxex.createCriteria().andOpenIdEqualTo(openId);
		List<WxUserBind>  bindlist = wxUserBindMapper.selectByExample(wxex);
		if(bindlist.size()>0){//已经绑定了
			if(!phoneNum.equals(bindlist.get(0).getUserName())){
				throw new BusinessException("该微信号已经绑定过其他商家！");
			}
			bindlist.get(0).setUserId(seller.getId());
			return DatasetBuilder.fromDataSimple(bindlist.get(0));
		}
		
		WxUserBind bind = new WxUserBind();
		bind.setUserName(phoneNum);
		bind.setOpenId(openId);
		bind.setUserId(seller.getId());
		bind.setCreateTime(new Date());
		bind.setUpdateTime(new Date());
		if(wxUserBindMapper.insertSelective(bind) != 1){
			throw new BusinessException("绑定用户失败！");
		}
		return DatasetBuilder.fromDataSimple(bind);
	}

	@Read
	@Override
	public DatasetSimple<WxUserBind> queryBind(Long sellerId) {
		WxUserBindExample ex = new WxUserBindExample();
		ex.createCriteria().andUserIdEqualTo(sellerId);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<WxUserBind>  binds = wxUserBindMapper.selectByExample(ex);
		if(binds.isEmpty()){
			return DatasetBuilder.fromMessageSimple("用户未绑定！");
		}
		return DatasetBuilder.fromDataSimple(binds.get(0));
	}

	@Override
	public DatasetSimple<AccessToken> getJsTicket() {
		return DatasetBuilder.fromDataSimple(accessTokenMapper.selectByPrimaryKey(2l));
	}

}
