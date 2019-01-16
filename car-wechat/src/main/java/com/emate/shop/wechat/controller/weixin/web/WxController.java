package com.emate.shop.wechat.controller.weixin.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emate.shop.business.api.AccessTokenService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.wechat.controller.weixin.aes.AesException;
import com.emate.shop.wechat.controller.weixin.aes.WXBizMsgCrypt;
import com.emate.shop.wechat.controller.weixin.service.CoreService;
import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.ParamesAPI;
import com.emate.wechat.RefreshTokenUtil;

/**
 * 微信接口
 * WxController.java
 * @author likk
 * 2015-7-20 下午06:13:48
 *
 */
@Controller
@RequestMapping("/wx")
public class WxController {
	
	private AccessTokenService accessTokenService;
	
	@RemoteService
	public void setAccessTokenService(AccessTokenService accessTokenService){
		this.accessTokenService = accessTokenService;
	}

	private static Logger logger = Log4jHelper.getLogger();
	
	@RequestMapping(value = { "/coreJoin" }, method = RequestMethod.GET)
	public void coreJoinGet(final HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 微信加密签名
		String msg_signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		logger.error("request=" + request.getRequestURL());
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		boolean result = false;
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.getToken(ParamesAPI.DEFAULT_SYSID), 
					ParamesAPI.getEncodingKey(ParamesAPI.DEFAULT_SYSID),
					ParamesAPI.getCorpId(ParamesAPI.DEFAULT_SYSID));
			result = wxcpt.verifyUrl(msg_signature, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
		}
		String returnStr = "signature error!";
		//验证失败返回空
		if (result) {
			returnStr = echostr;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		out.print(returnStr);
		out.close();
		out = null;
	}

	@RequestMapping(value = { "/coreJoin" }, method = RequestMethod.POST)
	public void coreJoinPost(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			//刷新access_token
			AccessTokenVo.access_token = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,0,this.accessTokenService);
			AccessTokenVo.js_ticket = RefreshTokenUtil.checkToken(ParamesAPI.DEFAULT_SYSID,1,this.accessTokenService);
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain; charset=utf-8");
		}catch(Exception e){
			logger.error("获取token失败",e);
		}

		// 微信加密签名
		String msg_signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		
		try {
			//从请求中读取整个post数据
			InputStream inputStream = request.getInputStream();
			String postData = IOUtils.toString(inputStream, "UTF-8");
			//System.out.println(postData);
			
			String msg = "";
			WXBizMsgCrypt wxcpt = null;
			wxcpt = new WXBizMsgCrypt(ParamesAPI.getToken(ParamesAPI.DEFAULT_SYSID), ParamesAPI.getEncodingKey(ParamesAPI.DEFAULT_SYSID),
					ParamesAPI.getCorpId(ParamesAPI.DEFAULT_SYSID));
			//解密消息
			msg = wxcpt.decryptMsg(msg_signature, timestamp, nonce, postData);
			//System.out.println("msg=" + msg);
			
			// 调用核心业务类接收消息、处理消息
			String respMessage =CoreService.processRequest(request,msg,ParamesAPI.DEFAULT_SYSID);
			//System.out.println("respMessage=" + respMessage);
			
			String encryptMsg = "";
				//加密回复消息
				encryptMsg = wxcpt.encryptMsg(respMessage, timestamp, nonce);
			// 响应消息
			PrintWriter out = response.getWriter();
			out.print(encryptMsg);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("消息返回失败",e);
		}

	}
}  


