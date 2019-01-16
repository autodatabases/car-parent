package com.emate.shop.wechat.controller.weixin.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.emate.shop.wechat.controller.weixin.msg.resp.Article;
import com.emate.shop.wechat.controller.weixin.msg.resp.BaseMessage;
import com.emate.shop.wechat.controller.weixin.msg.resp.NewsMessage;
import com.emate.shop.wechat.controller.weixin.msg.resp.TextMessage;
import com.emate.shop.wechat.controller.weixin.util.MessageUtil;
import com.emate.wechat.ParamesAPI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * 
 * MessageProcess.java
 * @author likk
 * 2015-7-28 下午04:51:44
 *
 */
public class MessageProcess {
	public static String processMsg(String request,BaseMessage baseMsg,HttpServletRequest reg,String sysId){
		//System.out.println("MessageProcess.processMsg === request : " + request);
		
		//用户发送1 返回图文消息 1
		if("0".equals(request)){
			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
			StringBuffer sb = new StringBuffer("请回复一下数字获取信息：\r\n");
			sb.append("1 : 如何购买商品\r\n")
			.append("2 : 关于赠送积分\r\n")
			.append("3 : 如何添加好友\r\n");
			msg.setContent(sb.toString());
			return MessageUtil.textMessageToXml(msg);
		}else if("click_key_1001".equals(request)){
			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
			StringBuffer sb = new StringBuffer("功能尚未开通，敬请等待！");
			msg.setContent(sb.toString());
			return MessageUtil.textMessageToXml(msg);
		}else if("click_key_1002".equals(request)){
			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
			StringBuffer sb = new StringBuffer("功能尚未开通，敬请等待！");
			msg.setContent(sb.toString());
			return MessageUtil.textMessageToXml(msg);
		}
		else if("click_key_2005".equals(request)){
		}else if("click_key_3001".equals(request)){
//			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
//			StringBuffer sb = new StringBuffer();
//			JsonResult  result = WxController.queryUserBind(baseMsg.getToUserName(), reg,sysId);
//			if(result == null || result.getStatusCode() != 200 || result.getReturnObj() == null){
//				sb.append("欢迎使用账号绑定功能\n")
//					.append("您可以点击下面链接进行绑定\n\n")
//						.append("<a href=\"")
//						.append(getBingUrl(baseMsg.getToUserName(),"index/infocenter",sysId)+"\">绑定账号</a>\n\n")
//						.append("更多功能等待您来体验哦~");
//				msg.setContent(sb.toString());
//				return MessageUtil.textMessageToXml(msg);
//			}
//			msg.setContent("您已经绑定过账号！");
//			return MessageUtil.textMessageToXml(msg);
		}else if("click_key_3002".equals(request)){
//			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
//			StringBuffer sb = new StringBuffer();
//			JsonResult  result = WxController.queryUserBind(baseMsg.getToUserName(), reg,sysId);
//			if(result == null || result.getStatusCode() != 200 || result.getReturnObj() == null){
//				sb.append("您还没有绑定账号哦，请先绑定账号！\n\n");
//				sb.append("<a href=\"")
//				.append(getBingUrl(baseMsg.getToUserName(),"index/infocenter",sysId)+"\">绑定账号</a>\n\n");
//			}else{
//				sb.append("欢迎使用积分查询功能，点击下面链接，直接查询您的积分\n\n");
//				sb.append("<a href=\"")
//				.append(appendSigntrue(ParamesAPI.getDomian(sysId)+reg.getContextPath()
//						+ "/wx/weixinLogin?redirectUrl=index/infocenter&sysId=006&openId="
//						+ baseMsg.getToUserName(),sysId)+"\">查询积分</a>\n\n");
//			}
//			msg.setContent(sb.toString());
//			return MessageUtil.textMessageToXml(msg);
		}else if("click_key_3003".equals(request)){
//			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
//			StringBuffer sb = new StringBuffer();
//			JsonResult  result = WxController.queryUserBind(baseMsg.getToUserName(), reg,sysId);
//			if(result == null || result.getStatusCode() != 200 || result.getReturnObj() == null){
//				sb.append("您还没有绑定账号哦，请先绑定账号！\n\n");
//				sb.append("<a href=\"")
//				.append(getBingUrl(baseMsg.getToUserName(),"index/infocenter",sysId)+"\">绑定账号</a>\n\n");
//			}else{
//				sb.append("欢迎使用订单查询功能，点击下面链接，直接查询您的订单\n\n");
//				sb.append("<a href=\"")
//				.append(appendSigntrue(ParamesAPI.getDomian(sysId)+reg.getContextPath()
//						+ "/wx/weixinLogin?redirectUrl=userOrder/orderList&sysId=006&openId="
//						+ baseMsg.getToUserName(),sysId)+"\">查询订单</a>\n\n");
//			}
//			msg.setContent(sb.toString());
//			return MessageUtil.textMessageToXml(msg);
		}else if("click_key_3004".equals(request)){
			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
			StringBuffer sb = new StringBuffer("功能尚未开通，敬请等待！");
			msg.setContent(sb.toString());
			return MessageUtil.textMessageToXml(msg);
		}else if("click_key_3005".equals(request)){
			TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
			StringBuffer sb = new StringBuffer("功能尚未开通，敬请等待！");
			msg.setContent(sb.toString());
			return MessageUtil.textMessageToXml(msg);
		}
		//默认的文本回复
		TextMessage msg = getTextMsgFromBaseMsg(baseMsg);
		msg.setContent(getNoAnwseStr());
		return MessageUtil.textMessageToXml(msg);
		
	}
	
	
	/**
	 * 获取没有绑定的绑定提示text
	 * @return
	 */
	public static String getBingUrl(String openId,String redirectUrl,String sysId){
		
		StringBuffer sb = 
				new StringBuffer(ParamesAPI.getDomian(sysId))
				.append("/m/wx/toBindUser?openId=")
				.append(openId)
				.append("&redirectUrl=")
				.append(redirectUrl)
				.append("&tarSysId=")
				.append(sysId);
		return sb.toString();
	}
	
	
	/**
	 * 关注之后的自动回复
	 * @param openId
	 * @return
	 */
	public static String getSubscribeMsg(String openId,String sysId){
		StringBuffer sb = new StringBuffer();
		sb.append("\n亲爱的小伙伴，终于等到您啦，");
		return sb.toString();
	}
	
	
	/**
	 * 用户的未知请求
	 * @param openId
	 * @return
	 */
	public static String getNoAnwseStr(){
		StringBuffer sb = new StringBuffer("/:rose/:rose/:rose");
		sb.append("喵喵喵/::~亲爱哒~机器人客服还不太懂你的意思，不过没关系哦，/:,@-D")
		.append("\n听说回复数字\"0\"，即可打开导航菜单哦~ \n欢迎各位骚扰调戏哦~")
		.append("机器人客服也会不断的学习新东西为大家服务呢~/:,@f/:,@f/:,@f")
		.append("吭哧吭哧~学习ing~");
		return sb.toString();
	}
	
	
	
	/**
	 * 从基本消息中提取文本消息
	 * @param baseMsg
	 * @return
	 */
	public static TextMessage getTextMsgFromBaseMsg(BaseMessage baseMsg){
		TextMessage msg = new TextMessage();
		msg.setToUserName(baseMsg.getToUserName());
		msg.setFromUserName(baseMsg.getFromUserName());
		msg.setCreateTime(baseMsg.getCreateTime());
		msg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		return msg;
	}
	
	/**
	 * 根据mediaId 以及 pic名称返回图文列表
	 * @param baseMsg
	 * @param mediaIds
	 * @param pics
	 * @return
	 */
	public static String replayNewMsg(BaseMessage baseMsg,String[] mediaIds,String[] pics,String sourceUrl){
		//回复第一个mediaId eE-f4pS7j7L83Y_KfOp2OGUBPRb2Lg3VZu70LxUO51k
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(baseMsg.getToUserName());
		newsMessage.setFromUserName(baseMsg.getFromUserName());
		newsMessage.setCreateTime(baseMsg.getCreateTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		List<Article> articles = new ArrayList<Article>();
		
		int picIndex = 0;
		for(int k=0;k<mediaIds.length;k++){
			JSONObject news = LocalCache.getMediaByIdFromLocalCache(mediaIds[k]);
			JSONArray itemArray = news.getJSONArray("news_item");
			for(int i=0;i<itemArray.size();i++){
				Article a = new Article();
				JSONObject temp = itemArray.getJSONObject(i);
				a.setDescription(temp.getString("digest"));
				a.setPicUrl(LocalCache.getImageUrlByImageName(pics[picIndex++]));
				a.setTitle(temp.getString("title"));
				if(sourceUrl != null){
					a.setUrl(sourceUrl);
				}else{
					a.setUrl(temp.getString("url"));
				}
				articles.add(a);
			}
		}
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		return MessageUtil.newsMessageToXml(newsMessage);
	}
	
}
