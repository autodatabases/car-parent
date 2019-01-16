package com.emate.wechat;

/**
 * @author Engineer-Jsp
 * @date 2014.10.09
 * 请求数据通用类*/
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.emate.shop.common.Log4jHelper;

import net.sf.json.JSONObject;
public class WeixinUtil {
	private static Logger logger = Logger.getLogger(WeixinUtil.class);
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
	public static JSONObject HttpRequest(String request , String RequestMethod , String output ){
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			//建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if(output!=null){
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			//流处理
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input,"UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			//关闭连接、释放资源
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
		}
		return jsonObject;
	} 
//	 获取access_token的接口地址（GET）   https://api.weixin.qq.com/cgi-bin/token
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=$APPDI&secret=$SECRET";  
	public static String JS_API = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	public static String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	
	/** 
	 * 获取access_token 
	 *  
	 * @param CorpID 企业Id 
	 * @param SECRET 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret 
	 * @return 
	 */  
	public static AccessTokenVo getAccessToken(String appid, String secret) {  
	    AccessTokenVo accessToken = null;  
	    String requestUrl = access_token_url.replace("$APPDI", appid).replace("$SECRET", secret);  
	    JSONObject jsonObject = HttpRequest(requestUrl, "GET", null);  
	    // 如果请求成功  
	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessTokenVo();  
	            accessToken.setToken(jsonObject.getString("access_token"));  
	            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
	            accessToken.setUpdateTime(new Date());
	            logger.info("获取token成功:"+jsonObject.getString("access_token")+"————"+jsonObject.getInt("expires_in"));
	        } catch (Exception e) {  
	            accessToken = null;  
	            // 获取token失败  
	            String error = String.format("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	            logger.error(error,e);
	        }  
	    }  
	    return accessToken;  
	}
	
	
	/**
	 * 获取jsapi_ticket
	 * @param mediaId
	 */
	public static AccessTokenVo getJsapiTicket(){
		AccessTokenVo token = new AccessTokenVo();
		JSONObject object = WeixinUtil.callWxApi(AccessTokenVo.access_token, "GET", JS_API,null);
		if(object.getInt("errcode") == 0 ){
			token.setToken(object.getString("ticket"));
			token.setExpiresIn(object.getInt("expires_in"));
			return token;
		}
		
		System.out.println("获取不到JsapiTicket");
		return null;
	}
	
	public static void main(String[] args) {
		getAccessToken(ParamesAPI.getCorpId(ParamesAPI.DEFAULT_SYSID),ParamesAPI.getSecret(ParamesAPI.DEFAULT_SYSID));
		//getJsapiTicket();
	}
	
	
//	 菜单创建（POST）   
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=$ACCESS_TOKEN"; 
	public static String menu_delte_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=$ACCESS_TOKEN"; 
	  
	/** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @param agentid  企业应用的id，整型，可在应用的设置页面查看
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("$ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = HttpRequest(url, "POST", jsonMenu);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
	            String error = String.format("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	            System.out.println(error+jsonObject.toString()); 
	        }  
	    }  
	  
	    return result;  
	}  
	
	/** 
	 * 删除菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @param agentid  企业应用的id，整型，可在应用的设置页面查看
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int deleteMenu(String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url  
	    String url = menu_delte_url.replace("$ACCESS_TOKEN", accessToken);  
	    
	    // 调用接口创建菜单  
	    JSONObject jsonObject = HttpRequest(url, "GET", null);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
	            String error = String.format("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	            System.out.println(error); 
	        }  
	    }  
	  
	    return result;  
	}  
	
	public static String URLEncoder(String str){
		String result = str ;
		try {
		result = java.net.URLEncoder.encode(result,"UTF-8");	
		} catch (Exception e) {
        e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileEndWitsh(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType))
			fileEndWitsh = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileEndWitsh = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileEndWitsh = ".amr";
		else if ("video/mp4".equals(contentType))
			fileEndWitsh = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileEndWitsh = ".mp4";
		return fileEndWitsh;
	}
	/**
	 * 数据提交与请求通用方法
	 * @param access_token 凭证
	 * @param RequestMt 请求方式
	 * @param RequestURL 请求地址
	 * @param outstr 提交json数据
	 * */
    public static int PostMessage(String access_token ,String RequestMt , String RequestURL , String outstr){
    	int result = 0;
    	RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
    	JSONObject jsonobject = WeixinUtil.HttpRequest(RequestURL, RequestMt, outstr);
    	 if (null != jsonobject) {  
 	        if (0 != jsonobject.getInt("errcode")) {  
 	            result = jsonobject.getInt("errcode");  
 	            String error = String.format("操作失败 errcode:{} errmsg:{}", jsonobject.getInt("errcode"), jsonobject.getString("errmsg"));  
 	            System.out.println(error+jsonobject.getString("errmsg")); 
 	            Log4jHelper.getLogger().error(error+jsonobject.getString("errmsg"));
 	        }  
 	       Log4jHelper.getLogger().info("发送消息结果： "+ jsonobject.toString());
 	    }
    	return result;
    }
    
    
	/**
	 * 获取素材管理接口
	 * @param access_token 凭证
	 * @param RequestMt 请求方式
	 * @param RequestURL 请求地址
	 * @param outstr 提交json数据
	 * */
    public static JSONObject PostMessageForData(String access_token ,String RequestMt , String RequestURL , String outstr){
    	RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
    	JSONObject jsonobject = WeixinUtil.HttpRequest(RequestURL, RequestMt, outstr);
    	 if (null != jsonobject) {  
 	        if (null != jsonobject.get("errcode")) {  
 	            String error = String.format("操作失败 errcode:{0} errmsg:{1}", jsonobject.getInt("errcode"), jsonobject.getString("errmsg"));  
 	            System.out.println(error); 
 	            return null;
 	        }else{
 	        	System.out.println("================获取数据成功！===============");
 	        }
 	    }
    	return jsonobject;
    }
    
    
	/**
	 * 调用api接口
	 * @param access_token 凭证
	 * @param RequestMt 请求方式
	 * @param RequestURL 请求地址
	 * @param outstr 提交json数据
	 * */
    public static JSONObject callWxApi(String access_token ,String RequestMt , String RequestURL , String outstr){
    	RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
    	JSONObject jsonobject = WeixinUtil.HttpRequest(RequestURL, RequestMt, outstr);
    	 if (null != jsonobject) {  
 	        if (0 != jsonobject.getInt("errcode")) {  
 	            String error = String.format("操作失败 errcode:{} errmsg:{}", jsonobject.getInt("errcode"), jsonobject.getString("errmsg"));  
 	            System.out.println(error); 
 	            return null;
 	        }else{
 	        	System.out.println("================获取数据成功！===============");
 	        }
 	    }
    	return jsonobject;
    }
}  
