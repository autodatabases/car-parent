package com.emate.shop.wechat.controller.weixin.service;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.emate.shop.wechat.controller.weixin.msg.resp.BaseMessage;
import com.emate.shop.wechat.controller.weixin.msg.resp.TextMessage;
import com.emate.shop.wechat.controller.weixin.util.MessageUtil;


/**
 * 处理微信发来的信息
 * @author likk
 *
 */
public class CoreService {
	private static Logger logger = Logger.getLogger(CoreService.class);
	public static String processRequest(HttpServletRequest request,String msg,String sysId) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "未知的推送消息类型！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(msg);

			//System.out.println("Event=="+requestMap.get("Event"));
			
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			//System.out.println("msgType=="+msgType);
			// 回复文本消息
			BaseMessage baseMsg = new BaseMessage();
			baseMsg.setToUserName(fromUserName);
			baseMsg.setFromUserName(toUserName);
			baseMsg.setCreateTime(new Date().getTime());
			baseMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				return MessageProcess.processMsg(content, baseMsg,request,sysId);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "likk提示：您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "likk提示：您发送的是地理位置消息！"; 
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "likk提示：您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "likk提示：您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 自定义菜单点击事件
				if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");
                    logger.info("EventKey="+eventKey);
                    return MessageProcess.processMsg(eventKey, baseMsg,request,sysId);
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_VIEW)){
					String eventKey = requestMap.get("EventKey");	
                    logger.info("EventKey="+eventKey);
					respContent = "likk提示："+requestMap.get("FromUserName")+"--您点击的菜单URL是"+eventKey;
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					String eventKey = requestMap.get("EventKey");	
                    logger.info("EventKey="+eventKey);
                    //requestMap.get("FromUserName")
					respContent = MessageProcess.getSubscribeMsg(fromUserName,sysId);
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					String eventKey = requestMap.get("EventKey");	
                    logger.info("EventKey="+eventKey);
					respContent = "likk提示："+requestMap.get("FromUserName")+"--您点击的菜单URL是"+eventKey;
				}
			}

			//暂时不开放
			TextMessage textMsg = new TextMessage();
			textMsg.setToUserName(baseMsg.getToUserName());
			textMsg.setFromUserName(baseMsg.getFromUserName());
			textMsg.setCreateTime(baseMsg.getCreateTime());
			textMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMsg.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMsg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			respMessage="有异常了。。。";
		}
		return respMessage;
	}

}