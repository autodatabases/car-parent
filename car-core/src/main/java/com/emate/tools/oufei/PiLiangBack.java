package com.emate.tools.oufei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.emate.shop.common.HttpClientHelper;
import com.emate.tools.DBOper;


public class PiLiangBack implements Runnable{

	@Override
	public void run() {
		try {
			piLiangBack();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void piLiangBack() throws IOException{
		//批量改状态
		for (int i = 0; i < 10; i++) {
			Map<String, String> phoneGood = getOrderMap();
			back(phoneGood);			
		}
	}
	private static void back(Map<String, String> params){
		String url = "http://car.emates.cn/oilUser/ofback";
		Map<String, String> requestHeaders = new HashMap<String,String>();
		HttpClientHelper.httpPost(url, params, requestHeaders);
	}
	
	private static Map<String,String> getOrderMap(){
		List<Map<String, String>> goodList = new ArrayList<Map<String,String>>();
		Map<String, String> map1 = new HashMap<String,String>();
		map1.put("ret_code", "1");
		map1.put("err_msg", "充值成功");
		goodList.add(map1);
/*		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("ret_code", "9");
		map2.put("err_msg", "充值失败");
		goodList.add(map2);*/
		Random r = new Random();
		int nextInt = r.nextInt(goodList.size());
		Map<String, String> map3 = goodList.get(nextInt);
		try {
			List<String> orderNoList = DBOper.queryOilOrders();
			Random r1 = new Random();
			int nextInt1 = r1.nextInt(orderNoList.size());
			String orderNo = orderNoList.get(nextInt1);
			map3.put("sporder_id", orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map3;
	}
}
