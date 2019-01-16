package com.emate.shop.business.api;

import com.emate.shop.business.model.AccessToken;
import com.emate.shop.business.model.WxUserBind;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface AccessTokenService {
	
	public DatasetSimple<AccessToken> getAccessToken();
	
	public DatasetSimple<Boolean> updateAccessToken(AccessToken token);
	
	public DatasetSimple<WxUserBind> bindUser(String phoneNum,String openId,String code,String tokenCode);
	
	public DatasetSimple<WxUserBind> queryBind(Long sellerId);
	
	public DatasetSimple<AccessToken> getJsTicket();
}
