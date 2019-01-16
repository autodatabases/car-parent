package com.emate.tools.gaoyang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.emate.shop.common.Log4jHelper;
import com.google.gson.Gson;

public class OilSdk {
	public static void main1(String[] args) {  
	    try {  
	        String spec = "http://127.0.0.1:8080/oilUser/sinopec/callback";  
	        URL url = new URL(spec);  
	        System.out.println(url);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setRequestMethod("GET");  
	        conn.setDoInput(true);  
	        conn.setDoOutput(true);  
	        conn.setInstanceFollowRedirects(true);  
	        conn.setRequestProperty("content-type", "text/html");  
	  
	        conn.connect();// 握手  
	        OutputStream os = conn.getOutputStream();// 拿到输出流  
	        PrintWriter out = new PrintWriter(os); 
	        Map<String, String> sub = new HashMap<String, String>();
			sub.put("resultCode", "0000");
			sub.put("orderNo", "17030218051473886");
			sub.put("transNo", "201703021805141474");
			sub.put("cardNo", "111");
			sub.put("tradeAmount", "3000");
			sub.put("respCode", "0000");
			sub.put("respMsg", "充值成功");
			sub.put("rechargeTime", "20170302180516");
	        out.print(new Gson().toJson(sub));  
	  
	        out.flush();  
	        os.flush();  
	        out.close();  
	        os.close();  
	  
	        InputStream is = conn.getInputStream();//拿到输入流  
	        InputStreamReader isr = new InputStreamReader(is);  
	        BufferedReader br = new BufferedReader(isr);  
	        String line,result = null;
			while ((line = br.readLine()) != null) {
				result += line;
			}
	        System.out.println(result);  
	  
	        br.close();  
	        isr.close();  
	        is.close();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
	public static void main(String[] args) {
		Gson gson = new Gson();
		// trade交易
		String tradeParam = gson.toJson(getTradeParam());
		System.out.println(tradeParam);
		String tradeUrl = "http://120.24.236.137:8011/code-service/v1.0/ticket/trade";
		String tradeResult = sendPost(tradeUrl, tradeParam);
		System.out.println(tradeResult);
		Map<String, Object> result = gson.fromJson(tradeResult, Map.class);
		if("0000".equals(result.get("resultCode"))){//成功
			System.out.println("=====================");
		}
		List<Map<String,Object>> subList = (List)result.get("subList");
		System.out.println(subList.size());
		Map<String,Object> m = subList.get(0);
		System.out.println(m.get("respCode"));
		
//		String queryParam = gson.toJson(getQueryParam());
//		System.out.println(queryParam);
//		String queryUrl = "http://120.24.236.137:8011/open/v1.0/ticket/query";
//		String queryResult = sendPost(queryUrl, queryParam);
//		System.out.println(queryResult);
//
//		String nullifyParam = gson.toJson(getNullifyParam());
//		String nullifyUrl = "http://121.40.68.54/open/v1.0/ticket/nullify";
//		String nullifyResult = sendPost(nullifyUrl, nullifyParam);
//		System.out.println(nullifyResult);
	}
	
	

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		Log4jHelper.getLogger().info("===============beging 调用油卡商接口===========");
		Log4jHelper.getLogger().info("======url is : "+url+". param is : " + param);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Log4jHelper.getLogger().info("======执行结果: " + result);
		return result;
	}

	private  static Map<String, Object> getTradeParam() {
		List<Map<String, String>> subList = new ArrayList<Map<String, String>>();

		Map<String, String> sub = new HashMap<String, String>();
		sub.put("mobile", "13051851080");
		sub.put("couponCode", "zy100");
		sub.put("quantity", "2");
		subList.add(sub);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("send", "true");
		data.put("area", "440101");
		data.put("totalQuantity", "2");
		data.put("orderNo", "3543453456456456453");
		data.put("subList", subList);
		Map<String, Object> req = new TreeMap<String, Object>();
		req.put("timeStamp", currentDateStr());
		req.put("appId", "test");
		req.put("data", data);
		req.put("sign", getSign(req,"test"));
		System.out.println(req);
		return req;
	}
	
	private static Map<String, Object> getQueryParam() {
		List<Map<String, String>> subList = new ArrayList<Map<String, String>>();

		Map<String, String> sub = new HashMap<String, String>();
		sub.put("assistCode", "111");
		subList.add(sub);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("subList", subList);
		Map<String, Object> req = new TreeMap<String, Object>();
		req.put("timeStamp", currentDateStr());
		req.put("appId", "test");
		req.put("data", data);
		req.put("sign", getSign(req,"test"));
		return req;
	}

	private static Map<String, Object> getNullifyParam() {
		List<Map<String, String>> subList = new ArrayList<Map<String, String>>();

		Map<String, String> sub = new HashMap<String, String>();
		sub.put("assistCode", "123456789");
		sub.put("couponCode", "zy100");
		sub.put("memo", "nullify");
		subList.add(sub);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("subList", subList);
		Map<String, Object> req = new TreeMap<String, Object>();
		req.put("timeStamp", currentDateStr());
		req.put("appId", "test");
		req.put("data", data);
		req.put("sign", getSign(req,"test"));
		return req;
	}

	public static String getSign(Map<String, Object> req,String appSecret) {
		String paramsString = "";
		Iterator<String> it = req.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (paramsString != null && !"".equals(paramsString)) {
				paramsString = paramsString + "&";
			}
			paramsString = paramsString + key + "=" + req.get(key);
		}
		return Encrypt(paramsString + appSecret);
	}

	public static String currentDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	public static String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}
