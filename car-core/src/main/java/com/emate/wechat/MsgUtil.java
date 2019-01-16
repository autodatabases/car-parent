package com.emate.wechat;

import org.apache.commons.lang.StringUtils;

import com.emate.shop.common.Log4jHelper;

import net.sf.json.JSONObject;
public class MsgUtil {
	private static org.apache.logging.log4j.Logger logger = Log4jHelper.getLogger();

    public static enum MSG_TYPE
    {NewOrder;}
	/**
	 * 订单确认模板
	 */
	private static String ORDER_CONFIRM = "uJnhywtl4Z8AHpUaScSBPdzac1DSpaopFLZ5OLfolTo";
	
	/**
	 * 测试环境的订单确认模板
	 */
	private static String ORDER_CONFIRM_test = "A3kNfuVJwhq8ZPXGi6NXoqNIUpXwqNOHB8iRnHJJW00";
	
	/**
	 * 下单推送
	 * @param toUser
	 * @param param
	 * @return  id createTime customName cartype
	 */
	public static String getOrderConfirmJsonText(String toUser,String[] param){
		TemplateMsg msg = new TemplateMsg();
		if(ParamesAPI.getDomian(ParamesAPI.DEFAULT_SYSID).indexOf("car")!=-1){
			msg.setTemplate_id(ORDER_CONFIRM_test);
		}else{
			msg.setTemplate_id(ORDER_CONFIRM);
		}
		msg.setTouser(toUser);
		msg.setUrl(param[0]);
		JSONObject obj = JSONObject.fromObject(msg);
		JSONObject params = new JSONObject();
		
		//订单标题
		JSONObject first = new JSONObject();
		first.put("value", "亲，您有一个新的订单请注意查收。");
		first.put("color", "#173177");
		params.put("first", first);
		
		//订单交易时间
		JSONObject tradeDateTime = new JSONObject();
		tradeDateTime.put("value", param[1]);
		tradeDateTime.put("color", "#173177");
		params.put("tradeDateTime", tradeDateTime);
		
		//订单类型
		JSONObject orderType = new JSONObject();
		orderType.put("value", param[2]);
		orderType.put("color", "#173177");
		params.put("orderType", orderType);
		
		//客户信息
		JSONObject customerInfo = new JSONObject();
		customerInfo.put("value", param[3]);
		customerInfo.put("color", "#173177");
		params.put("customerInfo", customerInfo);
		
		//参数名称
		JSONObject orderItemName = new JSONObject();
		orderItemName.put("value",  "配送地址");
		orderItemName.put("color", "#173177");
		params.put("orderItemName", orderItemName);
		
		//配送地址
		JSONObject orderItemData = new JSONObject();
		orderItemData.put("value",  param[4]);
		orderItemData.put("color", "#173177");
		params.put("orderItemData", orderItemData);
		
//		JSONObject remark = new JSONObject();
//		remark.put("value", "您的当前积分值是:"+param[2]);
//		remark.put("color", "#173177");
		//params.put("remark", remark);
		
		
		obj.put("data", params);
		return obj.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(sendTemplateMsg(MSG_TYPE.NewOrder,"on3dEwcveBOcQ7LKkutxz_joYydE", new String[]{"测试","1","2","3"}));
	}
	
	/**
	 * 发送模板消息
	 * @param toUser
	 * @param params
	 * @return
	 */
	public static int sendTemplateMsg(MSG_TYPE type,String toUser,String[] params){
		logger.info("==============开始发送模板消息================"+type+"|"+toUser+"|"+params);
		if(type == null || StringUtils.isEmpty(toUser)){
			logger.error("消息类型或者用户名为空，发送失败！");
			return -1;
		}
		String data = null;
		if(type == MSG_TYPE.NewOrder){
			data = getOrderConfirmJsonText(toUser, params);
		}
		return WeixinUtil.PostMessage(AccessTokenVo.access_token, "POST", WeixinUtil.SEND_TEMPLATE_MESSAGE, data);
	}
}
