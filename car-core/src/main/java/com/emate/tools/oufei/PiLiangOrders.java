package com.emate.tools.oufei;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.emate.shop.common.HttpClientHelper;
import com.emate.tools.ExcelOper;
import com.emate.tools.ExportExcelData;
import com.google.gson.Gson;

public class PiLiangOrders implements Runnable{

	@Override
	public void run() {
		try {
			piLiangOrders();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void piLiangOrders() throws IOException{
		//批量下单
		List<Map<String,String>> orderNoList = new ArrayList<Map<String,String>>();
		for (int i = 0; i < 10; i++) {
			Map<String, String> phoneGood = getPhoneGood();
			String orderNo = order(phoneGood);
			Map<String, String> result = new HashMap<String,String>();
			result.put("orderNo", orderNo);
			orderNoList.add(result);
			System.out.println(orderNo);
					
		}
		HSSFWorkbook wb = ExportExcelData.createExcelFile1(orderNoList, 
			        new String[]{"订单号"},
			        new String[]{"orderNo"},
			        "订单号集合");
		wb.write(new File("C:\\文件\\orderNoList.xls"));
	}
	private static String order(Map<String, String> params){
		String url = "http://car.emates.cn/oilUser/chargemobile";
		Map<String, String> requestHeaders = new HashMap<String,String>();
		requestHeaders.put("Cookie", "CAR_OIL_TOKEN=iVOr957il%252BJ%252FoxdoJW2KLg%253D%253D");
		String httpPost = HttpClientHelper.httpPost(url, params, requestHeaders);
		System.out.println(httpPost);
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String,String> fromJson = gson.fromJson(httpPost, Map.class);
		String str = fromJson.get("data");
		return str;
	}
	
	private static Map<String,String> getPhoneGood(){
		List<Map<String, String>> goodList = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String,String>();
		map.put("goodId", "11");
		map.put("money", "10");
		goodList.add(map);
		Map<String, String> map1 = new HashMap<String,String>();
		map1.put("goodId", "12");
		map1.put("money", "20");
		goodList.add(map1);
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("goodId", "13");
		map2.put("money", "30");
		goodList.add(map2);
		Map<String, String> map3 = new HashMap<String,String>();
		map3.put("goodId", "14");
		map3.put("money", "50");
		goodList.add(map3);
		Map<String, String> map4 = new HashMap<String,String>();
		map4.put("goodId", "15");
		map4.put("money", "100");
		goodList.add(map4);
		Map<String, String> map5 = new HashMap<String,String>();
		map5.put("goodId", "16");
		map5.put("money", "200");
		goodList.add(map5);
		Map<String, String> map6 = new HashMap<String,String>();
		map6.put("goodId", "17");
		map6.put("money", "300");
		goodList.add(map6);
		Map<String, String> map7 = new HashMap<String,String>();
		map7.put("goodId", "18");
		map7.put("money", "500");
		goodList.add(map7);
		Random r = new Random();
		int nextInt = r.nextInt(goodList.size());
		Map<String, String> map8 = goodList.get(nextInt);
		try {
			List<String> data = ExcelOper.readPhoneExcel();
			Random r1 = new Random();
			int nextInt1 = r1.nextInt(data.size());
			String phone = data.get(nextInt1);
			map8.put("phone", phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map8;
	}
}
