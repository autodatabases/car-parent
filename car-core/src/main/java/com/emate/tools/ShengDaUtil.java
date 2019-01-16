package com.emate.tools;

import java.io.UnsupportedEncodingException;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;

import net.sf.json.JSONObject;

public class ShengDaUtil {
	//盛大下单接口请求加密
	public static String encrypt(String encryptJsonStr, String key, String key3Des) {
		StringBuffer token = new StringBuffer();
		token.append(encryptJsonStr).append(key);
		String tokenMe = token.toString();
		String md5Str = null;
		try {
			md5Str = DigestUtils.md5Hex(tokenMe.getBytes("UTF-8")).toUpperCase();
			return PCThreeDESUtil.encrypt(encryptJsonStr + "|" + md5Str, key3Des);
		} catch (Exception e) {
			//log.error("PlaceOrderActionTest.encrypt : encrypt is fail", e);
			throw new BusinessException("加密失败");
		}
	}
	public static String decrypt(String decryptJsonStr, String key, String key3Des){
		String serverStr= null;
		try{
			serverStr = PCThreeDESUtil.decrypt(decryptJsonStr, key3Des);
			Log4jHelper.getLogger().info("调用盛大接口响应结果：\r\n"+serverStr);
			String serverStrSpit[] = serverStr.split("[|]");
			StringBuffer stn = new StringBuffer();
			stn.append(serverStrSpit[0]).append(key);
			String sign = DigestUtils.md5Hex(stn.toString().getBytes("UTF-8")).toUpperCase();
			Log4jHelper.getLogger().info("调用盛大接口校验sign：\r\n"+sign);
			if(sign.equals(serverStrSpit[1])){
				return serverStrSpit[0];
			}else{
				throw new BusinessException("校验sign失败~~");
			}
		}catch(Exception e){
			Log4jHelper.getLogger().info("调用盛大接口:解密失败"+e.getMessage());
			throw new BusinessException("调用盛大接口:解密失败");
		} 
	}
	
	/**
	 * 代驾订单接口
	 * @param driverOrderUrl
	 * @param params
	 * @return
	 */
	public static Map<String, String> driverOrder(String driverOrderUrl,Map<String,String> params){
        //组织MD5串
    	StringBuffer md5StrBuffer = new StringBuffer();
        md5StrBuffer.append(params.get("source"))
        .append(params.get("realName"))
        .append(params.get("tel"))
        .append(params.get("bespType"))
        .append(params.get("key"));
        String md5Str = null;
		try {
			md5Str = DigestUtils.md5Hex(md5StrBuffer.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log4jHelper.getLogger().info("代驾订单请求异常:MD5加密失败");
            throw new BusinessException("代驾订单请求响应异常...");
		}
		Log4jHelper.getLogger().info("订单号:"+params.get("besCode")+";hexSign:"+md5Str);
        params.put("hexSign", md5Str);
        params.remove("key");
       //梳理请求串
        StringBuffer requestUrl = new StringBuffer();
        requestUrl.append(driverOrderUrl)
        	.append("source=").append(params.get("source")).append("&")
        	.append("realName=").append(params.get("realName")).append("&")
        	.append("tel=").append(params.get("tel")).append("&")
        	.append("bespeakTime=").append(params.get("bespeakTime")).append("&")
        	.append("userName=").append(params.get("userName")).append("&")
        	.append("besCode=").append(params.get("besCode")).append("&")
        	.append("city=").append(params.get("city")).append("&")
        	.append("longitude=").append(params.get("longitude")).append("&")
        	.append("latitude=").append(params.get("latitude")).append("&")
        	.append("deLongitude=").append(params.get("deLongitude")).append("&")
        	.append("deLatitude=").append(params.get("deLatitude")).append("&")
        	.append("bespType=").append(params.get("bespType")).append("&")
        	.append("hexSign=").append(params.get("hexSign")).append("&")
        	.append("startAddress=").append(params.get("startAddress")).append("&")
        	.append("endAddress=").append(params.get("endAddress"));
        Log4jHelper.getLogger().info("盛大代驾订单请求串:"+requestUrl.toString());
        //发送请求
        String resultString = HttpClientHelper.httpPost(driverOrderUrl,params);
        //若响应结果为空
        if (StringUtils.isEmpty(resultString)) {
        	Log4jHelper.getLogger().info("代驾订单请求异常:返回参数为空");
            throw new BusinessException("代驾订单请求响应异常...");
        }
        //System.out.println(resultString);
        //代驾订单响应日志
        Log4jHelper.getLogger().info("代驾订单接口响应:油卡用户"+params.get("userName")+
        		"为"+params.get("realName")+":"+params.get("tel")+"下单;响应结果:"+resultString);
        //组织返回结果
        JSONObject obj = JSONObject.fromObject(resultString);
        Map<String,String> map = new HashMap<String,String>();
        map.put("resultCode", String.valueOf(obj.get("resultCode")));
        map.put("resultDesc", String.valueOf(obj.get("resultDesc")));
        return map;
    }
	
	/**
	 * 代驾修改预约时间接口
	 * @param orderUrl
	 * @param params
	 * @return
	 */
	public static Map<String, String> updateAppointmentTime(String orderUrl,Map<String,String> params){
       //梳理请求串
        StringBuffer requestUrl = new StringBuffer();
        requestUrl.append(orderUrl)
        	.append("source=").append(params.get("source")).append("&")
        	.append("besCode=").append(params.get("besCode")).append("&")
        	.append("bespeakTime=").append(params.get("bespeakTime"));
        Log4jHelper.getLogger().info("盛大代驾订单修改预约时间请求串:"+requestUrl.toString());
        //发送请求
        String resultString = HttpClientHelper.httpPost(orderUrl,params);
        
        //代驾订单响应日志
        Log4jHelper.getLogger().info("代驾订单修改预约时间接口响应:代驾订单号"+params.get("besCode")+
        		";响应结果:"+resultString);
        
        //若响应结果为空
        if (StringUtils.isEmpty(resultString)) {
        	Log4jHelper.getLogger().info("代驾订单修改预约时间请求异常:返回参数为空");
            throw new BusinessException("代驾订单修改预约时间请求响应异常...");
        }
        System.out.println(resultString);
        //组织返回结果
        JSONObject obj = JSONObject.fromObject(resultString);
        Map<String,String> map = new HashMap<String,String>();
        map.put("resultCode", String.valueOf(obj.get("resultCode")));
        map.put("resultDesc", String.valueOf(obj.get("resultDesc")));
        return map;
    }
	
	/**
	 * 代驾修改预约时间接口
	 * @param orderUrl
	 * @param params
	 * @return
	 */
	public static Map<String, String> cancelDriverOrder(String cancelOrderUrl,Map<String,String> params){
       
		//组织MD5串
    	StringBuffer md5StrBuffer = new StringBuffer();
        md5StrBuffer.append(params.get("source"))
        .append(params.get("besCode"))
        .append(params.get("key"));
        String md5Str = null;
		try {
			md5Str = DigestUtils.md5Hex(md5StrBuffer.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log4jHelper.getLogger().info("取消代驾订单请求异常:MD5加密失败");
            throw new BusinessException("取消代驾订单请求响应异常...");
		}
		Log4jHelper.getLogger().info("订单号:"+params.get("besCode")+";hexSign:"+md5Str);
        params.put("hexSign", md5Str);
        params.remove("key");
        //梳理请求串
        StringBuffer requestUrl = new StringBuffer();
        requestUrl.append(cancelOrderUrl)
        	.append("source=").append(params.get("source")).append("&")
        	.append("besCode=").append(params.get("besCode")).append("&")
        	.append("status=").append(params.get("status")).append("&")
        	.append("hexSign=").append(params.get("hexSign"));
        Log4jHelper.getLogger().info("盛大代驾订单取消接口请求串:"+requestUrl.toString());
        System.out.println(requestUrl.toString());
        //发送请求
        String resultString = HttpClientHelper.httpPost(cancelOrderUrl,params);
        
        //代驾订单响应日志
        Log4jHelper.getLogger().info("代驾订单取消订单接口响应:代驾订单号"+params.get("besCode")+
        		";响应结果:"+resultString);
        
        //若响应结果为空
        if (StringUtils.isEmpty(resultString)) {
        	Log4jHelper.getLogger().info("代驾订单修改预约时间请求异常:返回参数为空");
            throw new BusinessException("代驾订单修改预约时间请求响应异常...");
        }
        //组织返回结果
        JSONObject obj = JSONObject.fromObject(resultString);
        Map<String,String> map = new HashMap<String,String>();
        map.put("resultCode", String.valueOf(obj.get("resultCode")));
        map.put("resultDesc", String.valueOf(obj.get("resultDesc")));
        return map;
    }
	
	
    public static void main(String[] args) {
		/*String driverOrderUrl = "http://ssdl.auto1768.com.cn:8068/ShengDaBesPlatform/thirdBes/bespeak/createBespeakOrder_third?";
		Map<String, String> params = new HashMap<String,String>();
		params.put("source", "GDGSCX");//商户渠道标识
		params.put("realName", "董哥哥");
		params.put("tel", "13521322513");
		params.put("bespeakTime", "2018-01-18 16:50:00");
		params.put("userName", "13521322513");
		params.put("besCode", RandomUtil.getDriverOrderNo());
		params.put("city", "北京市");
		params.put("longitude", "116.455959");//起始点经度
		params.put("latitude", "39.852562");//起始纬度
		params.put("deLongitude", "116.344805");//终点经度
		params.put("deLatitude", "40.039005");//终点纬度
		params.put("bespType", "DB");
		params.put("key", "e76818eb6d0a372c5ee491d5c4809b03");
		params.put("startAddress", "成寿寺地铁A口");
		params.put("endAddress", "阳光南里小区北门");*/
		
/*        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = format.format(new Date());
		params.put("sourceApp", "11");
		params.put("timestamp", timestamp);
		params.put("digest", "1754831207F5C1AB210933DD9385A954");*/
        
	/*	Map<String, String> driverOrder = driverOrder(driverOrderUrl,params);
		System.out.println(driverOrder.toString());*/
    	
    	//取消订单接口
/*    	String cancelOrderUrl = "http://ssdl.auto1768.com.cn:8068/ShengDaBesPlatform/thirdBes/bespeak/cancelBespeakOrder_third?";
    	Map<String, String> params = new HashMap<String,String>();
    	params.put("source", "GDGSCX");//商户渠道标识
    	params.put("besCode", "GDGSCX180305UB9");//商户渠道标识
    	params.put("status", "5");//商户渠道标识
    	params.put("key", "e76818eb6d0a372c5ee491d5c4809b03");//商户渠道标识
    	Map<String, String> cancelDriverOrder = cancelDriverOrder(cancelOrderUrl,params);
    	System.out.println(cancelDriverOrder.toString());*/
		
    	//修改预约时间订单接口
    	String updateOrderUrl = "http://ssdl.auto1768.com.cn:8068/ShengDaBesPlatform/thirdBes/bespeak/updateBespeakOrder_third?";
    	Map<String, String> params = new HashMap<String,String>();
    	params.put("source", "GDGSCX");//商户渠道标识
    	params.put("besCode", "GDGSCX180305K5T");//商户渠道标识
    	params.put("bespeakTime", "2018-03-05 18:41:00");//商户渠道标识
    	//params.put("key", "e76818eb6d0a372c5ee491d5c4809b03");//商户渠道标识
    	Map<String, String> cancelDriverOrder = updateAppointmentTime(updateOrderUrl,params);
    	System.out.println(cancelDriverOrder.toString());
    }
}
