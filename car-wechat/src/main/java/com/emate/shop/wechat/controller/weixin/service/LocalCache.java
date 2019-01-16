package com.emate.shop.wechat.controller.weixin.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import com.emate.shop.wechat.controller.weixin.msg.util.GMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 本地缓存 微信中的newlist
 * LocalCache.java
 * @author likk
 * 2015-7-28 下午06:11:13
 *
 */
public class LocalCache {
	
	private static Logger logger = Logger.getLogger(LocalCache.class);
	
	private static boolean isThreadRun = false;
	
	private static Map<String,JSONObject> newsMap = new ConcurrentHashMap<String, JSONObject>();
	private static Map<String,String> imgMap = new ConcurrentHashMap<String, String>();
	
	//从缓存中加载 media
	public static JSONObject getMediaByIdFromLocalCache(String mediaId){
		if(newsMap.containsKey(mediaId)){
			logger.info("从缓存中加载 media---"+mediaId);
			return newsMap.get(mediaId);
		}else{
			newsMap.put(mediaId, GMessage.getNewsByMediaId(mediaId));
		}
		return newsMap.get(mediaId);
	}
	
	
	   /**
	    * 通过图片名称获取图片url
	    * @param mediaId
	    * @return
	    */
	   public static String getImageUrlByImageName(String imgName) {
		if (imgMap.containsKey(imgName)) {
			logger.info("从缓存中加载 pic---" + imgName);
			return imgMap.get(imgName);
		} else {
			
			//缓存所有的图片 name : url
			JSONObject images = GMessage.getImagesList();
			JSONArray imageArray = images.getJSONArray("item");
			for (int i = 0; i < imageArray.size(); i++) {
				JSONObject temp = imageArray.getJSONObject(i);
				imgMap.put(temp.getString("name").substring(0,
						temp.getString("name").indexOf(".")), temp
						.getString("url"));
			}
		}
		if (!imgMap.containsKey(imgName)) {
			logger.error("获取不到资源！ resource name is :" + imgName);
		}
		return imgMap.get(imgName);
	}
	   
	   // 定时清理缓存
	   public static void timeClearCache(){
		   isThreadRun = true;
		   new Thread(new Runnable(){
			@Override
			public void run() {
				int errCount = 0;
				while(true){
					try{
						Thread.sleep(1000 * 60 * 30); //30分钟清理一次缓存
						logger.info("----------开始清理微信缓存------------");
						newsMap.clear();
						imgMap.clear();
					}catch(Exception e){
						errCount ++;
						logger.error("定时清理缓存失败！",e);
						if(errCount >= 10){ //超过10次自动退出
							logger.error("错误次数超过10次，退出线程");
							break;
						}
					}finally{
						logger.info("----------清理微信缓存结束------------");
					}
				}
			}
		   }).start();
	   }
	   
	   static{
		   if(!isThreadRun){
				timeClearCache();
		   }
	   }
	   
}
