package com.emate.shop.wechat.controller.weixin.msg.util;


import org.apache.log4j.Logger;

import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.ParamesAPI;
import com.emate.wechat.WeixinUtil;

/**
 * 获取微信运维上传的素材列表
 */
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * 
 * GMessage.java
 * @author likk
 * 2015-7-29 上午11:19:46
 *
 */
public class GMessage {
	private static Logger logger = Logger.getLogger(GMessage.class);
	
	
	/**
	 * 
	 * 
mediaId = fyBqz63QKiO6IgJ3ICVCfhG_RR8amh4CQV2kSjMDvao
18:08:44,978 ERROR GMessage:106 - 	我要换礼

	 * 
	 * 
	 * 
	 * @return
	 */
	
	
	
	
	public static String getToken(){
		return AccessTokenVo.access_token;
	} 
	//发送接口
	public static String POST_URL_BATCHGET = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	public static String POST_URL_GET = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	
	//根据code获取微信openid
	public static String URL_GET_USER_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	/**
	 * text消息
	 * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count 返回素材的数量，取值在1到20之间
	 * */
	public static String getNewsList(String type,String offset,String count){
		String PostData = "{\"type\": \"%s\",\"offset\": \"%s\",\"count\": \"%s\"}";
		return String.format(PostData, type,offset,count);
	}
	
	/**
	 * 获取素材
	 * @param mediaId
	 */
	public static String getMediaPostData(String mediaId){
		String PostData = "{\"media_id\": \"%s\"}";
		return String.format(PostData, mediaId);
	}
	
	//示例
   public static void main(String[] args) {
	   getImagesList();
	  // getNewsByMediaId("eE-f4pS7j7L83Y_KfOp2OGUBPRb2Lg3VZu70LxUO51k");
	   //getNewList();
   }
   
   //获取用户openid
   public static JSONObject getUserOpenId(String code,String sysId){
	   String url = URL_GET_USER_OPENID.replace("APPID", ParamesAPI.getCorpId(sysId))
	   .replace("SECRET", ParamesAPI.getSecret(sysId)).replace("CODE", code);
	   JSONObject object = WeixinUtil.PostMessageForData(getToken(), "POST", url, null);
	   return object;
   }
   
   
   /**
    * 获取new图文消息列表
    */
   public static void getNewList(){
	   String PostData = getNewsList("news","0","20");
	   JSONObject object = WeixinUtil.PostMessageForData(getToken(), "POST", POST_URL_BATCHGET, PostData);
	   if(object != null){
		   int itemCount = object.getInt("item_count");
		   JSONArray itemArray = object.getJSONArray("item");
		   for(int i=0;i<itemCount;i++){
			   JSONObject item = itemArray.getJSONObject(i);
			   System.out.println("mediaId = " + item.getString("media_id"));
			   JSONArray contentArray = item.getJSONObject("content").getJSONArray("news_item");
			   for(int j=0;j<contentArray.size();j++){
				   logger.error("\t" +contentArray.getJSONObject(j).getString("title"));
			   }
			   //getNewsByMediaId(item.getString("media_id"));
		   }
	   }
   }
   
   
   /**
    * 获取图片列表
    */
   public static JSONObject getImagesList(){
	   String PostData = getNewsList("image","0","20");
	   JSONObject obj = WeixinUtil.PostMessageForData(getToken(),"POST", POST_URL_BATCHGET, PostData);
	   
	   //判断图片数量是否超出20个 如果超过需要多次查找
	   int totalCount = obj.getInt("total_count");
	   if(totalCount > 20){
		   int forCount = totalCount / 20; //获取for循环次数
		   JSONObject tmp = null;
		   for(int i = 1; i<= forCount;i++){
			   PostData = getNewsList("image",String.valueOf(i * 20) ,"20"); //每次取 20个
			   tmp = WeixinUtil.PostMessageForData(getToken(),"POST", POST_URL_BATCHGET, PostData);
			   obj.getJSONArray("item").addAll(tmp.getJSONArray("item"));
		   }
	   }
	   logger.info("从微信服务器获取图片列表:  " + obj.toString());
	   return obj;
   }
   
  /**
   * 
   * 通用的获取单个资源
   * 
   * 关于为什么要调用批量接口返回newList然后逐个匹配的问题
   * 批量接口返回的文章列表中的封面背景图片才是真正的资源id
   * 方便后台回复 图文消息给用户
   * @param mediaId
   * @return
   */
   public static JSONObject getNewsByMediaId(String mediaId){
	   String PostData = getMediaPostData(mediaId);
	   JSONObject object = WeixinUtil.PostMessageForData(getToken(), "POST", POST_URL_GET, PostData);
	   return object;
   }
   
   
   
   /**
    * 通过mediaId获取图片url
    * @param mediaId
    * @return
    */
   public static String getImageUrlByMediaId(String mediaId){
	   JSONObject images = getImagesList();
	   
	   JSONArray imageArray = images.getJSONArray("item");
	   for(int i=0;i<imageArray.size();i++){
		   JSONObject temp = imageArray.getJSONObject(i);
		   if(mediaId.equals(temp.getString("media_id"))){
			   return temp.getString("url");
		   }
	   }
	   logger.error("获取不到资源！ resource id is :" + mediaId);
	   return null;
    }
   
   /**
    * 通过图片名称获取图片url
    * @param mediaId
    * @return
    */
   public static String getImageUrlByImageName(String imgName){
	   JSONObject images = getImagesList();
	   
	   JSONArray imageArray = images.getJSONArray("item");
	   for(int i=0;i<imageArray.size();i++){
		   JSONObject temp = imageArray.getJSONObject(i);
		   if(temp.getString("name").indexOf(imgName)>=0){
			   return temp.getString("url");
		   }
	   }
	   logger.error("获取不到资源！ resource name is :" + imgName);
	   return null;
    }
   
}
