package com.emate.tools;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Workbook;

import com.emate.shop.exception.BusinessException;

//http://image.tuhu.cn/Images/Logo/biyadi.png

public class ExcelOper{
	public static String file = null;
	
	
	/**
	 * 导入商家信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readSellerFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\seller.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"","province","seller_name","city","area","address_detail","seller_phone","","name"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	
	/**
	 * 导入加油站信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readJiaYouZhanExcel() throws Exception{
		file = "c:\\Users\\likk\\Desktop\\门店信息.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"storeCode","storeName","shortName","","province","city","area","address","merchantType"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	/**
	 * 导入商家信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readAutoInfoExcel() throws Exception{
		file = "E:\\seller.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"id","","","","","you"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	
	/**
	 * 导入电瓶价格信息到数据表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readPriceFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\dianping.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"","","","product_name","product_price"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,100);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("product_name") == null || t.get("product_price")==null){
				it.remove();
				continue;
			}
			System.out.println(t);
		}
		return data;
	}
	
	
	/**
	 * 导入轮胎数据到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readTyreFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\tyre.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"brand","autoLine_name","autoType_name","product_time","front_size","rear_size"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,14610);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("autoType_name")==null){
				it.remove();
				continue;
			}
			t.put("engine_disp",((String)t.get("autoType_name")).split(" ")[0]);
			t.put("trans_desc",((String)t.get("autoType_name")).split(" ")[1]);
			System.out.println(t);
		}
		return data;
	}
	
	/**
	 * 导入机油数据 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readJiyouFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\jiyou.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"brand","autoLine_name","engine_disp","product_time","trans_desc","remark","jiyou"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("brand")==null 
					|| t.get("autoLine_name")==null
					|| t.get("engine_disp")==null
					||  t.get("product_time")==null){
				it.remove();
				continue;
			}
			System.out.println(t);
		}
		System.out.println(data.size());
		return data;
	}
	public static List<Map<String, String>> readOilCardFromExcel() throws Exception{
		file = "E:\\oilphone2.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"phone"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,0,200);
		Iterator<Map<String, Object>> it = data.iterator();
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("phone")==null){
				it.remove();
				continue;
			}
			String str = (String)t.get("phone");
			//System.out.println(str);
			Map<String, String> result = new HashMap<String,String>();
			String[] strs = str.split("-");
			System.out.println(strs[0]+"hao"+strs[1]);
			result.put("startPhone", strs[0]);
			result.put("endPhone", strs[1]);
			results.add(result);
		}
		System.out.println(data.size());
		return results;
	}
	
	/**
	 * 导入机油滤芯数据 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readJilvFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\jilv.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"","","product","jilv","barcode","brand","autoLine_name","engine_disp","","product_time"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(!"机油滤清器 ".equals(t.get("product"))){
				it.remove();
				continue;
			}
			t.put("engine_disp", ((String)t.get("engine_disp")).replaceAll(",", "."));
			System.out.println(t);
		}
		System.out.println(data.size());
		return data;
	}
	
	
	/**
	 * 导入机滤价格信息到数据表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readJilvPriceFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\jilv.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"product_price","product_name","type","","product_price"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,2,paramNames,1,100);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("product_name") == null || t.get("product_price")==null){
				it.remove();
				continue;
			}
			System.out.println(t);
		}
		return data;
	}
	
	
	/**
	 * 导入车型信息表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readAutoInfoFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\auto\\chexing.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"","","brand","","autoLine_name","","product_time","engine_disp","id","oil_amount","","front_size","rear_size"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,9,paramNames,1,30000);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			BigDecimal b =  (BigDecimal)t.get("oil_amount");
			t.put("oil_amount",Math.ceil(b.floatValue()));
//			if(t.get("product_name") == null || t.get("product_price")==null){
//				it.remove();
//				continue;
//			}
			System.out.println(t);
		}
		return data;
	}
	
	/**
	 * 导入配件信息表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readPartFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\auto\\chexing.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		PinyinTool p = new PinyinTool();
		String[] paramNames = new String[]{"","id","parts_type","parts_type_id","brand","","product_code","","detail_name","sale_price","pic","size","properties"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,4,paramNames,1,500);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			String zhongwen = (String)t.get("pic");
			zhongwen = zhongwen.replaceAll("（", "").replaceAll("）", "").replaceAll(" " , "");
			zhongwen = p.toPinYin(zhongwen);
			t.put("pic", zhongwen);
			System.out.println(t);
		}
		return data;
	}
	
	
	/**
	 * 导入车型配件关联表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readAutoPartRelationFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\auto\\relation.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[154];
		paramNames[0] = "";
		paramNames[1] = "auto_id";
		paramNames[2] = "part_type";
		paramNames[3] = "part_type_id";
		for(int i=4;i<paramNames.length;i++){
			paramNames[i] = "match"+(i-3);
		}
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,24112);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			StringBuffer sb = new StringBuffer();
			Stream.of(paramNames).forEach(param -> {
				if(param.indexOf("match")!=-1){
					if(t.get(param)!=null){
						sb.append(t.get(param)).append(",");
					}
					t.remove(param);
				}
			});
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
				t.put("match_id", sb.toString());
			}
			System.out.println(t);
		}
		return data;
	}
	
	
	/**
	 * 导入用户车牌信息
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readUserImportInfoFromExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\导入车牌\\11.25车辆信息.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{
				"",
				"real_name",
				"che_pai",
				"phone",
				"address",
				"auto_brand",
				"auto_type",
				"",
				"engine_disp",
				"product_year",
				"che_jia"
		};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,20);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("che_pai")==null){
				it.remove();
				continue;
			}
			for(String s:t.keySet()){
				t.put(s, String.valueOf(t.get(s)).replaceAll("\n", "").replaceAll("\r", ""));
				if(t.get(s)=="null"){
					t.put(s, null);
				}
			}
			System.out.println(t);
		}
		return data;
	}
	
	/**
	 * 导入用户车牌信息
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readUserImportInfoFromExcelNew() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\文档\\惠+车服提数样.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"real_name","address","phone","che_pai","che_jia","engine_code","auto_brand","auto_type","price","auto_line",
				"engine_disp","product_year","source","bao_dan","","start_time","end_time","order_price","discount","use_type","agent","seller"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,2,100);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("che_pai")==null && t.get("che_jia")==null){
				it.remove();
				continue;
			}
			t.put("engine_disp", new java.text.DecimalFormat("#.0").format(t.get("engine_disp")));
			t.put("order_price", new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("order_price"))));
			t.put("price", new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("order_price"))));
			t.put("baoyang_times", 2);
			/*for(String s:t.keySet()){
				t.put(s, String.valueOf(t.get(s)).replaceAll("\n", "").replaceAll("\r", ""));
				if(t.get(s)=="null"){
					t.put(s, null);
				}
			}*/
		}
		System.out.println(data);
		return data;
	}
	
	/**
	 * 导入业务员信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readcountermanExcel() throws Exception{
		file = "E:\\counterman.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"cai_dot_name","career_code","career_name","counterman_code","name","status","phone"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,30000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	
	/**
	 * 导入业务员信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readOilCardExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\油卡对接\\4.7油卡3.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"date","user_name","phone","che_pai","money","address"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,300);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> m = it.next();
			if(m.get("user_name")==null || m.get("phone")==null 
					|| m.get("money")==null){
				it.remove();
			}
			if(!(m.get("phone").toString()).matches("\\d{11}")){
				throw new BusinessException("手机号码格式错误！");
			}
			System.out.println(m);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	
	/**
	 * 导入业务员信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readSurveyCenterExcel() throws Exception{
		file = "E:\\surveycenter.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"name","address","belong_group","main_cab","region","cooperation","cooperation_time","premium","replace_rate","loss_ration",};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
/*		for(Map<String, Object> map : data){
			System.out.println(map);
		}*/
		//System.out.println("total size : " + data.size());
		return data;
	}
	/**
	 * 导入SurveyCenter信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readSurveyCenterExcelTwo() throws Exception{
		file = "C:\\surveycenter.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"zhigongsi","name","address","yewuyuan",
				"main_cab","loss_ration8","qiandanleiji8","tongbizengzhang8",
				"premium7","weixiuzhanzhi7","replace_rate7","loss_ration7"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,10000);
/*		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());*/
		return data;
	}
	
	/**
	 * 导入财险职场信息到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readCountermanCareerExcel() throws Exception{
		file = "E:\\counterman_career4.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"career_name","belong_area","career_code"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,200);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	/**
	 * 导入寿险营销员的寿险职场代码
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readCounterman1Excel() throws Exception{
		file = "E:\\counterman1.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"","","","counterman_code","","insurance_network"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,30000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	/**
	 * 导入财险网点信息 到数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readCaiDotExcel() throws Exception{
		file = "E:\\cai_dot.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"dot_name"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,29);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	
	/**
	 * 导入寿险职场数据 到 数据库表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readLifeCareerExcel() throws Exception{
		file = "E:\\life_career.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"career_code","career_name"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,150);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readImportUserInfoExcel() throws Exception{
		file = "E:\\importUserInfo.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"che_pai","peiqi_times"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,200);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	//user.xlsx
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readUserExcel() throws Exception{
		file = "C:\\Users\\likk\\Desktop\\油卡对接\\user.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"name","phone"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,2000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	
	/**
	 * 导入国寿网点的代理公司的保单信息
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readGuoShouData() throws Exception{
		file = "E:\\gs_agent_data.xlsx";
		Workbook book = ExcelUtil.createWorkBook(file);
		//2016
/*				String[] paramNames2 = new String[]{"dot","agency","cooperateTime",
				"m11","m12","m13","m14","m15","m16","m17","m18","m19","m110","m111","m112",
				"m21","m22","m23","m24","m25","m26","m27","m28","m29","m210","m211","m212",
				"ratea1","ratea2"};*/
		//2015 2014 2013 2012
/*		String[] paramNames3 = new String[]{"dot","agency","cooperateTime",
				"m11","m12","m13","m14","m15","m16","m17","m18","m19","m110","m111","m112","",
				"m21","m22","m23","m24","m25","m26","m27","m28","m29","m210","m211","m212","",
				"ratea1","ratea2"};*/
		//2011
		String[] paramNames4 = new String[]{"dot","agency","cooperateTime",
				"m11","m12","m13","m14","m15","m16","m17","m18","m19","m110","m111","m112","",
				"ratea2"};
		//2017
/*		String[] paramNames1 = new String[]{"dot","agency","cooperateTime",
				"m11","m12","m13","m14","m15","m1d",
				"m21","m22","m23","m24","m25","m2d",
				"ratea1","ratea2"};*/
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames4,3,9);
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			//设置时间
			SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd");
			if(t.get("cooperateTime") instanceof CharSequence){
				if(((String)t.get("cooperateTime")).split("/").length!=3){
					throw new BusinessException("cooperateTime"+"日期格式不对,应该为 2018/6/16");
				}
				t.put("cooperateTime", sm.parse((String)t.get("cooperateTime")));
			}else if(t.get("cooperateTime")==null){
				t.put("cooperateTime", null);
			}else if(!(t.get("cooperateTime") instanceof Date)){
				throw new BusinessException("cooperateTime"+"日期格式不对，或日期为空");
			}
			
			//设置价格
			//2017
			//String[] nums1 = new String[]{"m11","m12","m13","m14","m15","m1d","m21","m22","m23","m24","m25","m2d"};
			//2016
			//String[] nums2 = new String[]{"m11","m12","m13","m14","m15","m16","m17","m18","m19","m110","m111","m112",
					//"m21","m22","m23","m24","m25","m26","m27","m28","m29","m210","m211","m212"};
			//2015 2014 2013 2012
			//String[] nums3 = new String[]{"m11","m12","m13","m14","m15","m16","m17","m18","m19","m110","m111","m112",
			//"m21","m22","m23","m24","m25","m26","m27","m28","m29","m210","m211","m212"};
			String[] nums4 = new String[]{"m11","m12","m13","m14","m15","m16","m17","m18",
					"m19","m110","m111","m112"};
			
			for(String key:nums4){
				if(t.get(key) instanceof Number){
					t.put(key, new BigDecimal(new java.text.DecimalFormat("#.00").format(t.get(key))));
				}else if(t.get(key) instanceof CharSequence && ((String)t.get(key)).matches("\\d+")){
					t.put(key, new BigDecimal((String)t.get(key)));
				}
				System.out.println(t.get(key));
			}
			String[] nums5 = new String[]{"m21","m22","m23","m24","m25","m26","m27","m28",
					"m29","m210","m211","m212"};
			for(String key:nums5){
				t.put(key,new BigDecimal("0"));
			}
			t.put("ratea1",new BigDecimal("0"));
/*			if(t.get("ratea1") instanceof Number){
				t.put("ratea1", new BigDecimal(new java.text.DecimalFormat("#.0000").format(t.get("ratea1"))));
			}else if(t.get("ratea1") instanceof CharSequence && ((String)t.get("ratea1")).matches("\\d+")){
				t.put("ratea1", new BigDecimal((String)t.get("ratea1")));
			}else if(t.get("ratea1") instanceof CharSequence && ((String)t.get("ratea1")).endsWith("%")){
				String substring = ((String)t.get("ratea1")).substring(0,((String)t.get("ratea1")).length()-1);
				t.put("ratea1", new BigDecimal(substring).divide(new BigDecimal("100")));
			}*/
			
			if(t.get("ratea2") instanceof Number){
				t.put("ratea2", new BigDecimal(new java.text.DecimalFormat("#.0000").format(t.get("ratea2"))));
			}else if(t.get("ratea2") instanceof CharSequence && ((String)t.get("ratea2")).matches("\\d+")){
				t.put("ratea2", new BigDecimal((String)t.get("ratea2")).divide(new BigDecimal("100")));
			}else if(t.get("ratea2") instanceof CharSequence && ((String)t.get("ratea2")).endsWith("%")){
				String substri = ((String)t.get("ratea2")).substring(0,((String)t.get("ratea2")).length()-1);
				t.put("ratea2", new BigDecimal(substri).divide(new BigDecimal("100")));
			}
			//System.out.println(t.get("ratea2"));
		}
		System.out.println(data);
		return data;
	}
	public static List<Map<String, Object>> readKuanTuExcel() throws Exception{
		file = "E:\\ktxc.xls";
		Workbook book = ExcelUtil.createWorkBook(file);
		String[] paramNames = new String[]{"phone"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,1000);
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());
		return data;
	}
	public static void main(String[] args) throws Exception {
		//readSellerFromExcel();
		//readJilvFromExcel();
		//readAutoInfoFromExcel();
		//readPartFromExcel();
		//readAutoPartRelationFromExcel();
		//readUserImportInfoFromExcel();
		//readUserImportInfoFromExcelNew();
		//readJiaYouZhanExcel();
		//readcountermanExcel();
		//readOilCardExcel();
		//readSurveyCenterExcel();
		//readCountermanCareerExcel();
		//readCounterman1Excel();
		//readCaiDotExcel();
		//readLifeCareerExcel();
		//readcountermanExcel();
		//readImportUserInfoExcel();
		
		//readUserExcel();
		//readGuoShouData();
		//readKuanTuExcel();
		//readOilCardFromExcel();
		readSurveyCenterExcelTwo();
	}
}