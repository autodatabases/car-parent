package com.emate.shop.wechat.controller.weixin.msg.util;
/** 
 * 发送消息类 
 * @author Engineer.Jsp
 * @date 2014.10.11 
 */
import java.util.ArrayList;
import java.util.List;

import com.emate.shop.wechat.controller.weixin.msg.resp.Article;
import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.WeixinUtil;

import net.sf.json.JSONArray;

public class SMessage {
	//发送接口
	public static String POST_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	
	//预览接口
	public static String POST_URL_PREVIEW = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	/**
	 * text消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：text
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param content 消息内容
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String STextMsg(String touser,String toparty,String totag,String agentid,String content){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"text\",\"agentid\": %s,\"text\": {\"content\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,content);
	}
	
	/**
	 * image消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：image
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String SImageMsg(String touser,String toparty,String agentid ,String media_id){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"msgtype\": \"image\",\"agentid\": %s,\"image\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,agentid,media_id);
	}
	
	/**
	 * voice消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：voice
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String SVoiceMsg(String touser,String toparty,String totag,String agentid ,String media_id){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"voice\",\"agentid\": %s,\"voice\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,media_id);
	}
	
	/**
	 * video消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：video
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param title 视频消息的标题
	 * @param description 视频消息的描述
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 */
	public static String SVideoMsg(String touser,String toparty,String totag,String agentid,String media_id,String title,String description){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"video\",\"agentid\": %s,\" video\": {\"media_id\": %s,\"title\": %s,\"description\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,media_id,title,description);
	}
	
	/**
	 * file消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：file
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String SFileMsg(String touser,String toparty,String totag,String agentid ,String media_id){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"file\",\"agentid\": %s,\"file\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,media_id);
	}
	
	/**
	 * news消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：news
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param articlesList 图文集合
	 */
	public static String SNewsMsg(String touser,String toparty,String totag,String agentid , String articlesList){
		String postData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"news\",\"agentid\": %s,\"news\": {\"articles\":%s}}";
		return String.format(postData, touser,toparty,totag,agentid,articlesList);
	}
	
	/**
	 * mpnews消息
	 * @param is_to_all用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id 群发到的分组的group_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写group_id 0是未分组 1 黑名单
	 * @param media_id 用于群发的消息的media_id
	 * @param msgtype 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
	 */
	public static String SMpNewsMsg(String is_to_all,String group_id,String media_id){
		String postData = "{"+
							   "\"filter\":{			"+
							      "\"is_to_all\":%s	,"+
							      "\"group_id\":\"%s\"	"+
							   "},						"+
							   "\"mpnews\":{			"+
							      "\"media_id\":\"%s\""+
							   "},						"+
							    "\"msgtype\":\"mpnews\"	"+
							"}";
		return String.format(postData, is_to_all,group_id,media_id);
	}
	
	/**
	 * mpnews消息
	 * @param touser用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param media_id 用于群发的消息的media_id
	 * @param msgtype 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
	 */
	public static String SMpNewsMsgPreView(String touser,String media_id){
		String postData = "{"+
							   "\"touser\":\"%s\",		"+
							   "\"mpnews\":{			"+
							      "\"media_id\":\"%s\""+
							   "},						"+
							    "\"msgtype\":\"mpnews\"	"+
							"}";
		return String.format(postData, touser,media_id);
	}
	
	
	/**
	 * 群发图文消息接口
	 * @param args
	 */
	public static void main(String[] args) {
		
		// eE-f4pS7j7L83Y_KfOp2OHMS17qTUDd3A22qcBXLziY
		 String PostData = SMpNewsMsg("false","0","eE-f4pS7j7L83Y_KfOp2OHMS17qTUDd3A22qcBXLziY");
		// String PostData = SMpNewsMsgPreView("o0rzVwW0JWqt-09BW37SzeoIOlvI","eE-f4pS7j7L83Y_KfOp2OGUBPRb2Lg3VZu70LxUO51k");
		   System.out.println(PostData);
		 int result = WeixinUtil.PostMessage( AccessTokenVo.access_token, "POST", POST_URL, PostData);
		   // 打印结果
			if(0==result){
				System.out.println("操作成功");
			}
			else {
				System.out.println("操作失败");
			}
	}
	
	
	//示例
   public static void main111(String[] args) {
	   /**
	    * news示例
	    * */
	   // 调取凭证
	   String access_token = AccessTokenVo.access_token;
	   // 新建图文
	   Article article1 = new Article();
	   article1.setTitle("news消息测试-1");
	   article1.setDescription("");
	   article1.setPicUrl("http://112.124.111.3/weixinClient/images/weather3.png");
	   article1.setUrl("http://112.124.111.3/weixinClient/images/weather3.png");
	   Article article2 = new Article();
	   article2.setTitle("news消息测试-2");
	   article2.setDescription("");
	   article2.setPicUrl("http://112.124.111.3/weixinClient/images/weather3.png");
	   article2.setUrl("http://112.124.111.3/weixinClient/images/weather3.png");
	   // 整合图文
	   List<Article> list = new ArrayList<Article>();
	   list.add(article1);
	   list.add(article2);
	   // 图文转json
	   String articlesList = JSONArray.fromObject(list).toString();
	   // Post的数据
	   String PostData = SNewsMsg("UserID1|UserID2|UserID3", "PartyID1 | PartyID2", "TagID1 | TagID2", "1", articlesList);
	   int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);
	   // 打印结果
		if(0==result){
			System.out.println("操作成功");
		}
		else {
			System.out.println("操作失败");
		}
   }
}
