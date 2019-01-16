package com.emate.wechat;

import java.util.Date;
import org.apache.logging.log4j.Logger;
import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.business.model.AccessToken;
import com.emate.shop.common.Log4jHelper;

public class RefreshTokenUtil {
	private static Logger logger = Log4jHelper.getLogger();

	/**
	 * 从数据库表中查询微信token
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String checkToken(String sysId, int type, AccessTokenService accessTokenService) throws Exception {
		logger.info("------------checkToken----------");
		if (type == 0) {
			if (AccessTokenVo.cacheToken != null && !AccessTokenVo.cacheToken.checkExpried()) {
				logger.info("------------缓存查询token成功！本次token未更新----------");
				return AccessTokenVo.cacheToken.getToken();
			}
		} else {
			if (AccessTokenVo.cacheJsTicket != null && !AccessTokenVo.cacheJsTicket.checkExpried()) {
				logger.info("------------缓存查询ticket成功！本次ticket未更新----------");
				return AccessTokenVo.cacheJsTicket.getToken();
			}
		}

		AccessToken accessToken = null;
		if (type == 0) {
			accessToken = accessTokenService.getAccessToken().getData();
			logger.info("------------从数据库查询token成功！accessToken==----------" + accessToken.getAccessToken());
		} else {
			accessToken = accessTokenService.getJsTicket().getData();
			logger.info("------------从数据库查询ticket成功！ticket==----------" + accessToken.getAccessToken());
		}

		// 设置参数
		AccessTokenVo token = new AccessTokenVo();
		token.setExpiresIn(accessToken.getExpiresIn());
		token.setToken(accessToken.getAccessToken());
		token.setUpdateTime(accessToken.getUpdateTime());

		if (type == 0) {
			AccessTokenVo.cacheToken = token;
		} else {
			AccessTokenVo.cacheJsTicket = token;
		}

		if (token.checkExpried()) {
			if (type == 0) {
				logger.info("------------数据库的token已经过期，http获取新的token----------");
				token = WeixinUtil.getAccessToken(ParamesAPI.getCorpId(sysId), ParamesAPI.getSecret(sysId));
			} else {
				logger.info("------------数据库的ticket已经过期，http获取新的ticket----------");
				token = WeixinUtil.getJsapiTicket();
			}

			if (type == 0) {
				AccessTokenVo.cacheToken = token;
				logger.info("------------获取新的token成功----------" + token.getToken());
			} else {
				AccessTokenVo.cacheJsTicket = token;
				logger.info("------------获取新的ticket成功----------" + token.getToken());
			}
			updateToken(token, sysId, type, accessTokenService);
		}
		return token.getToken();
	}

	/**
	 * 更新新的token到数据库表中
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static void updateToken(AccessTokenVo token, String sysId, int type, AccessTokenService accessTokenService)
			throws Exception {
		AccessToken accessToken = new AccessToken();
		accessToken.setAccessToken(token.getToken());
		accessToken.setExpiresIn(token.getExpiresIn());
		accessToken.setUpdateTime(new Date());
		if (type == 0) {
			accessToken.setId(1l);
		} else {
			accessToken.setId(2l);
		}

		if (accessTokenService.updateAccessToken(accessToken).getData()) {
			if (type == 0) {
				logger.info("更新token成功！token ========" + accessToken.getAccessToken());
			} else {
				logger.info("更新ticket成功！ticket ========" + accessToken.getAccessToken());
			}

		} else {
			logger.info("更新token失败！");
		}
	}
}
