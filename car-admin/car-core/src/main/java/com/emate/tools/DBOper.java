package com.emate.tools;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;



import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.emate.shop.common.RandomUtil;

//import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class DBOper {
    
    public static void main1(String[] args) throws SQLException, BadHanyuPinyinOutputFormatCombination {
    	DBHelper db = new DBHelper("select * from autoPose");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update autoPose set pinyin = ? where id = ?");
    	PinyinTool py = new PinyinTool();
        while (ret.next()) {  
            String name = ret.getString(4);
            int id = ret.getInt(1);
            if(name.length()>1){
            	name = name.substring(0, 1);
            }
            String a = py.toPinYin(name);
            if(a.length() >1){
            	a = a.substring(0, 1);
            }
            if("长".equals(name)){
            	a = "C";
            }
           
            System.out.println(name + " : " + a);
            updateSql.setString(1,   a);
            updateSql.setInt(2, id);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    //从 excel读取数据 update 到数据库
    public static void updateAutoInfo() throws Exception {
    	String sql = "update auto_info set oil_amount=? where id=?";
		DBHelper db = new DBHelper(sql);
		List<Map<String, Object>> data = ExcelOper.readAutoInfoExcel();
		Iterator<Map<String, Object>> iterator = data.iterator();
		int flag=0;
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			if(next.get("you")==null || next.get("you").equals("null")||next.get("id")==null||next.get("id").equals("null")){
				iterator.remove();
			}
			if(next.get("you").equals("0")){
				flag++;
			}
			
		}
		System.out.println(flag);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <20; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 100; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("you"));
				db.pst.setObject(2, map.get("id"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();//关闭连接  
	}
    
    //数据库中文转换成为拼音
    public static void setImage() throws SQLException, BadHanyuPinyinOutputFormatCombination {
    	DBHelper db = new DBHelper("select id,brandName from autoPose group by brandName,id");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update autoPose set logo = ? where id = ?");
    	PinyinTool py = new PinyinTool();
        while (ret.next()) {  
            String name = ret.getString(2);
            int id = ret.getInt(1);
            String a = py.toPinYin(name,"", PinyinTool.Type.LOWERCASE);
           
            System.out.println(name + " : " + a);
            updateSql.setString(1,   a);
            updateSql.setInt(2, id);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    
    //读取数据库拼音 从途虎 下载汽车 logo 图标
    public static void getImage() throws SQLException, BadHanyuPinyinOutputFormatCombination {
    	DBHelper db = new DBHelper("select brandName from autoPose group by brandName");
    	ResultSet  ret = db.pst.executeQuery();
    	//PreparedStatement updateSql = db.conn.prepareStatement("update autoPose set pinyin = ? where id = ?");
    	PinyinTool py = new PinyinTool();
        while (ret.next()) {  
            String name = ret.getString(1);
            String lower = py.toPinYin(name,"",PinyinTool.Type.LOWERCASE);
            Url.getImgFile(lower);
        }//显示数据  
        //updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    
    //从 excel读取数据 insert 到数据库
    public static void setSeller() throws Exception {
    	DBHelper db = new DBHelper("insert into seller(area,address_detail,province,seller_name,city,name,seller_phone,seller_grade,service_price,seller_postion) values(?,?,?,?,?,?,?,0,50,'116.331398_39.897445')");
		List<Map<String, Object>> data = ExcelOper.readSellerFromExcel();
		AtomicInteger i = new AtomicInteger(0);
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("area"));
			db.pst.setObject(2, map.get("address_detail"));
			db.pst.setObject(3, map.get("province"));
			db.pst.setObject(4, map.get("seller_name"));
			db.pst.setObject(5, map.get("city"));
			db.pst.setObject(6, map.get("name"));
			db.pst.setObject(7, map.get("seller_phone"));
			db.pst.addBatch();
			System.out.println("批处理添加第 :" + i.incrementAndGet()+" 条记录");
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
	}
    
    //从 excel读取数据 insert 到数据库
    public static void setPrice() throws Exception {
    	DBHelper db = new DBHelper("insert into power_price(product_name,product_price,update_time,create_time) values(?,?,now(),now())");
		List<Map<String, Object>> data = ExcelOper.readPriceFromExcel();
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("product_name"));
			db.pst.setObject(2, map.get("product_price"));
			db.pst.addBatch();
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
	}

    //从 生成随机数插入到 insert 到数据库
    public static void randomInsert() throws Exception {
    	DBHelper db = new DBHelper("insert into random_code(code,create_time) values(?,now())");
    	for(int j=0;j<10;j++){
    		db.pst = db.conn.prepareStatement("insert into random_code(code,create_time) values(?,now())");
    		for(int i=0;i<100;i++){
    			db.pst.setObject(1, VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 8, null));
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
		
        db.close();//关闭连接  
	}
    
    
    //从 excel读取数据 insert 到数据库
    public static void setTyre() throws Exception {
    	DBHelper db = new DBHelper("insert into tyre_info(brand,autoLine_name,autoType_name,engine_disp,product_time,trans_desc,front_size,rear_size,create_time,update_time) "
    			+ "values(?,?,?,?,?,?,?,?,now(),now())");
		List<Map<String, Object>> data = ExcelOper.readTyreFromExcel();
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("brand"));
			db.pst.setObject(2, map.get("autoLine_name"));
			db.pst.setObject(3, map.get("autoType_name"));
			db.pst.setObject(4, map.get("engine_disp"));
			db.pst.setObject(5, map.get("product_time"));
			db.pst.setObject(6, map.get("trans_desc"));
			db.pst.setObject(7, map.get("front_size"));
			db.pst.setObject(8, map.get("rear_size"));
			db.pst.addBatch();
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
	}
    
    //从 excel读取数据 insert 到数据库
    public static void setOil() throws Exception {
    	DBHelper db = new DBHelper("insert into engine_oil(brand,autoLine_name,engine_disp,product_time,trans_desc,jiyou,remark,create_time,update_time) "
    			+ "values(?,?,?,?,?,?,?,now(),now())");
		List<Map<String, Object>> data = ExcelOper.readJiyouFromExcel();
		AtomicInteger i = new AtomicInteger(0);
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("brand"));
			db.pst.setObject(2, map.get("autoLine_name"));
			db.pst.setObject(3, map.get("engine_disp"));
			db.pst.setObject(4, map.get("product_time"));
			db.pst.setObject(5, map.get("trans_desc"));
			db.pst.setObject(6, map.get("jiyou"));
			db.pst.setObject(7, map.get("remark"));
			db.pst.addBatch();
			System.out.println("批处理添加第 :" + i.incrementAndGet()+" 条记录");
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
	}
    
    
    //从 excel读取数据 insert 到数据库
    public static void setJilv() throws Exception {
    	DBHelper db = new DBHelper("insert into jilv_info(brand,autoLine_name,engine_disp,product_time,jilv,barcode,create_time,update_time) "
    			+ "values(?,?,?,?,?,?,now(),now())");
		List<Map<String, Object>> data = ExcelOper.readJilvFromExcel();
		AtomicInteger i = new AtomicInteger(0);
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("brand"));
			db.pst.setObject(2, map.get("autoLine_name"));
			db.pst.setObject(3, map.get("engine_disp"));
			db.pst.setObject(4, map.get("product_time"));
			db.pst.setObject(5, map.get("jilv"));
			db.pst.setObject(6, map.get("barcode"));
			db.pst.addBatch();
			System.out.println("批处理添加第 :" + i.incrementAndGet()+" 条记录");
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
	}
    
    
    
    //从读取车辆信息
    public static void autoInfoInsert() throws Exception {
    	String sql = "insert into auto_info(id,brand,autoLine_name,product_time,engine_disp,oil_amount,front_size,rear_size,create_time,update_time) values(?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = ExcelOper.readAutoInfoFromExcel();
    	int count = 0;
    	for(int j=1;j<11;j++){
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<3000;i++){
    			if(count>data.size()-1){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("id"));
    			db.pst.setObject(2, record.get("brand"));
    			db.pst.setObject(3, record.get("autoLine_name"));
    			db.pst.setObject(4, record.get("product_time"));
    			db.pst.setObject(5, record.get("engine_disp"));
    			db.pst.setObject(6, record.get("oil_amount"));
    			db.pst.setObject(7, record.get("front_size"));
    			db.pst.setObject(8, record.get("rear_size"));
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
		
        db.close();//关闭连接  
	}
    
    //读取配件信息 插入到数据库
    public static void autoPartInsert() throws Exception {
    	String sql = "insert into auto_parts(id,parts_type,parts_type_id,brand,product_code,detail_name,sale_price,pic,size,properties,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = ExcelOper.readPartFromExcel();
    	int count = 0;
    	for(int j=1;j<11;j++){
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<3000;i++){
    			if(count>data.size()-1){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("id"));
    			db.pst.setObject(2, record.get("parts_type"));
    			db.pst.setObject(3, record.get("parts_type_id"));
    			db.pst.setObject(4, record.get("brand"));
    			db.pst.setObject(5, record.get("product_code"));
    			db.pst.setObject(6, record.get("detail_name"));
    			db.pst.setObject(7, record.get("sale_price"));
    			db.pst.setObject(8, record.get("pic"));
    			db.pst.setObject(9, record.get("size"));
    			db.pst.setObject(10, record.get("properties"));
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
		
        db.close();//关闭连接  
	}
    
    //读取车型配件管理数据 插入到数据库
    public static void AutoPartRelationInsert() throws Exception {
    	String sql = "insert into auto_part_relation(auto_id,part_type,part_type_id,match_id) values(?,?,?,?)";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = ExcelOper.readAutoPartRelationFromExcel();
    	int count = 0;
    	for(int j=1;j<30;j++){
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<1000;i++){
    			if(count>data.size()-1){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("auto_id"));
    			db.pst.setObject(2, record.get("part_type"));
    			db.pst.setObject(3, record.get("part_type_id"));
    			db.pst.setObject(4, record.get("match_id"));
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
		
        db.close();//关闭连接  
	}
    
    
    //读取车型配件管理数据 插入到数据库
    public static void ImportUserInfoInsert() throws Exception {
    	String sql = "insert into import_user_info(real_name,che_pai,phone,address,auto_brand,auto_type,"
    			+ "engine_disp,product_year,che_jia,baoyang_times,xieche_times,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = ExcelOper.readUserImportInfoFromExcel();
    	int totalCount = data.size()-1;
    	int count = 0;
    	for(int j=1;j<30;j++){
    		if(count>totalCount){
				break;
			}
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<1000;i++){
    			if(count>totalCount){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("real_name"));
    			db.pst.setObject(2, record.get("che_pai"));
    			db.pst.setObject(3, record.get("phone"));
    			db.pst.setObject(4, record.get("address"));
    			db.pst.setObject(5, record.get("auto_brand"));
    			db.pst.setObject(6, record.get("auto_type"));
    			db.pst.setObject(7, record.get("engine_disp"));
    			db.pst.setObject(8, record.get("product_year"));
    			db.pst.setObject(9, record.get("che_jia"));
    			db.pst.setObject(10, 2);
    			db.pst.setObject(11, 0);
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
		
        db.close();//关闭连接  
	}
    
    //从加油站 excel读取数据 insert到加油站数据库
    public static void setJiayouzhan() throws Exception {
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readJiaYouZhanExcel();
		//修改数据
		Iterator<Map<String, Object>> iterator = data.iterator();
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			//String flag = (String)next.get("merchantType");
/*			if("油品供应".equals(flag)){
				next.put("merchantType", 0);
			}else if("商场超市".equals(flag)){
				next.put("merchantType", 1);
			}else if("饭店服务".equals(flag)){
				next.put("merchantType", 2);
			}else if("其他采购".equals(flag)){
				next.put("merchantType", 3);
			}else{
				next.put("merchantType", 0);
			}*/
			next.put("merchantType", 0);
			String city = (String)next.get("city");
			String area = (String)next.get("area");
			String province = (String)next.get("province");
			String address= (String)next.get("address");
			if(address.startsWith(area)){
				address=province+"省"+city+address;
			}else if(address.startsWith(city)){
				address=province+"省"+address;
			}else if(address.startsWith(province)){
				
			}else{
				address=province+"省"+city+area+address;
			}
			next.put("address", address);
		}
		System.out.println(data);
		//System.out.println(data);
		//String format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date());
		//String sql="insert into merchant(merchant_code,store_code,store_name,province,city,area,address,merchant_name,short_name,merchant_type,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?,now(),now())";
		String sql="insert into merchant(store_code,store_name,short_name,province,city,area,address,merchant_type,create_time,update_time) values(?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <30; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 100; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
	/*			db.pst.setObject(1, map.get("merchantCode"));
				db.pst.setObject(2, map.get("storeCode"));
				db.pst.setObject(3, map.get("storeName"));
				db.pst.setObject(4, map.get("province"));
				db.pst.setObject(5, map.get("city"));
				db.pst.setObject(6, map.get("area"));
				db.pst.setObject(7, map.get("address"));
				db.pst.setObject(8, map.get("merchantName"));
				db.pst.setObject(9, map.get("shortName"));
				db.pst.setObject(10, map.get("merchantType"));*/
				
				//db.pst.setObject(1, map.get("merchantCode"));
				db.pst.setObject(1, map.get("storeCode"));
				db.pst.setObject(2, map.get("storeName"));
				db.pst.setObject(3, map.get("shortName"));
				db.pst.setObject(4, map.get("province"));
				db.pst.setObject(5, map.get("city"));
				db.pst.setObject(6, map.get("area"));
				db.pst.setObject(7, map.get("address"));
				db.pst.setObject(8, map.get("merchantType"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();
	}
    //从业务员 excel读取数据 insert到counterman数据库
    public static void setCounterMan() throws Exception {
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readcountermanExcel();
		//修改数据
		Iterator<Map<String, Object>> iterator = data.iterator();
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			String status = (String)next.get("status");
/*			String phone = (String)next.get("phone");
			if(StringUtils.isBlank(phone)){
				System.out.println(next.get("name"));
				//next.put("phone", null);
			}else{
				if(phone.length()>11){
					System.out.println(phone);
					//next.put("phone",phone.trim());;
				}
			}*/
			if("在职".equals(status)){
				next.put("status", 0);
			}else if("离职".equals(status)){
				next.put("status", 1);
			}else{
				next.put("status", 2);
			}
		}
		/*		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());*/
		//System.out.println(data);
		String sql="insert into counterman(name,phone,job_code,job,unit,insurance_network,job_title,attribution_plate,status,create_time,update_time) values(?,?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <50; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 600; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("name"));
				db.pst.setObject(2, map.get("phone"));
				db.pst.setObject(3, map.get("job_code"));
				db.pst.setObject(4, map.get("job"));
				db.pst.setObject(5, map.get("unit"));
				db.pst.setObject(6, map.get("insurance_network"));
				db.pst.setObject(7, map.get("job_title"));
				db.pst.setObject(8, map.get("attribution_plate"));
				db.pst.setObject(9, map.get("status"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();
	}
    
    //从定损中心excel读取数据 insert到survey_center数据库
    public static void setSurveyCenter() throws Exception {
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readSurveyCenterExcel();
		//修改数据
		Iterator<Map<String, Object>> iterator = data.iterator();
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			String cooperation = (String)next.get("cooperation");
			if("是".equals(cooperation)){
				next.put("cooperation", 0);
			}else if("否".equals(cooperation)){
				next.put("cooperation", 1);
			}else{
				next.put("cooperation", 1);
			}
			Object loss_ration = next.get("loss_ration");
			if("null".equals(loss_ration)||Objects.isNull(loss_ration)){
				next.put("loss_ration", 0);
			}
			Object replace_rate = next.get("replace_rate");
			if("null".equals(replace_rate)||Objects.isNull(replace_rate)){
				next.put("replace_rate", 0);
			}
			Object premium = next.get("premium");
			if("null".equals(premium)||Objects.isNull(premium)){
				next.put("premium", 0);
			}
			next.put("premium", new BigDecimal(new java.text.DecimalFormat("#.00").format(next.get("premium"))));
			next.put("replace_rate", new BigDecimal(new java.text.DecimalFormat("#.00000").format(next.get("replace_rate"))));
			next.put("loss_ration", new BigDecimal(new java.text.DecimalFormat("#.00000").format(next.get("loss_ration"))));
		}
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		String sql="insert into survey_center(name,address,belong_group,main_cab,region,cooperation,cooperation_time,loss_ration,premium,replace_rate,create_time,update_time) values(?,?,?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <10; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 100; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("name"));
				db.pst.setObject(2, map.get("address"));
				db.pst.setObject(3, map.get("belong_group"));
				db.pst.setObject(4, map.get("main_cab"));
				db.pst.setObject(5, map.get("region"));
				db.pst.setObject(6, map.get("cooperation"));
				db.pst.setObject(7, map.get("cooperation_time"));
				db.pst.setObject(8, map.get("loss_ration"));
				db.pst.setObject(9, map.get("premium"));
				db.pst.setObject(10, map.get("replace_rate"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();
	}
    
    //从定损中心excel读取数据 insert到survey_center数据库
    public static void setSurveyCenterTwo() throws Exception {
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readSurveyCenterExcelTwo();
		//修改数据
		//Iterator<Map<String, Object>> iterator = data.iterator();
/*		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			Object premium7 = next.get("premium7");
			if("null".equals(premium7)||Objects.isNull(premium7)){
				next.put("premium7", 0);
			}
			Object loss_ration8 = next.get("loss_ration8");
			if("null".equals(loss_ration8)||Objects.isNull(loss_ration8)){
				next.put("loss_ration8", 0);
			}
			Object qiandanleiji8 = next.get("qiandanleiji8");
			if("null".equals(qiandanleiji8)||Objects.isNull(qiandanleiji8)){
				next.put("qiandanleiji8", 0);
			}
			Object tongbizengzhang8 = next.get("tongbizengzhang8");
			if("null".equals(tongbizengzhang8)||Objects.isNull(tongbizengzhang8)){
				next.put("tongbizengzhang8", 0);
			}
			Object weixiuzhanzhi7 = next.get("weixiuzhanzhi7");
			if("null".equals(weixiuzhanzhi7)||Objects.isNull(weixiuzhanzhi7)){
				next.put("weixiuzhanzhi7", 0);
			}
			Object replace_rate7 = next.get("replace_rate7");
			if("null".equals(replace_rate7)||Objects.isNull(replace_rate7)){
				next.put("replace_rate7", 0);
			}
			Object loss_ration7 = next.get("loss_ration7");
			if("null".equals(loss_ration7)||Objects.isNull(loss_ration7)){
				next.put("loss_ration7", 0);
			}
			next.put("premium7", new BigDecimal(new java.text.DecimalFormat("#.00")
					.format(next.get("premium7"))));
			next.put("loss_ration8", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("loss_ration8"))));
			next.put("qiandanleiji8", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("qiandanleiji8"))));
			next.put("tongbizengzhang8", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("tongbizengzhang8"))));
			next.put("weixiuzhanzhi7", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("weixiuzhanzhi7"))));
			next.put("replace_rate7", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("replace_rate7"))));
			next.put("loss_ration7", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("loss_ration7"))));
		}*/
		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		String sql="insert into survey_center(name,address,zhigongsi,yewuyuan,main_cab,cooperation,city,create_time,update_time) values(?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <10; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 100; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("name"));
				db.pst.setObject(2, map.get("address"));
				db.pst.setObject(3, map.get("zhigongsi"));
				db.pst.setObject(4, map.get("yewuyuan"));
				db.pst.setObject(5, map.get("main_cab"));
				db.pst.setObject(6, "1");
				db.pst.setObject(7, "惠州");
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();
	}
    //从定损中心excel读取数据 insert到survey_center数据库
    public static void setSurveyCenterBranchTwo() throws Exception {
    	//查找定损中心
    	String sql = "select * from survey_center where city=?";
		DBHelper db = new DBHelper(sql);
		//根据条件查询survey_center
		db.pst.setObject(1, "惠州");
		ResultSet ret = db.pst.executeQuery();
		HashMap<String, Long> map = new HashMap<String,Long>();
		while (ret.next()) { 
			Long id = ret.getLong(1);
			String name = ret.getString(2);
			map.put(name, id);
		}
		db.close();
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readSurveyCenterExcelTwo();
		//修改数据
		Iterator<Map<String, Object>> iterator = data.iterator();
		

		List<Map<String, Object>> list18 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list17 = new ArrayList<Map<String, Object>>();
		
		while(iterator.hasNext()){
			Map<String, Object> map18 = new HashMap<String, Object>();
			Map<String, Object> map17 = new HashMap<String, Object>();
			Map<String, Object> next = iterator.next();
			Object premium7 = next.get("premium7");
			if("null".equals(premium7)||Objects.isNull(premium7)){
				next.put("premium7", 0);
			}
			Object loss_ration8 = next.get("loss_ration8");
			if("null".equals(loss_ration8)||Objects.isNull(loss_ration8)){
				next.put("loss_ration8", 0);
			}
			Object qiandanleiji8 = next.get("qiandanleiji8");
			if("null".equals(qiandanleiji8)||Objects.isNull(qiandanleiji8)){
				next.put("qiandanleiji8", 0);
			}
			Object tongbizengzhang8 = next.get("tongbizengzhang8");
			if("null".equals(tongbizengzhang8)||Objects.isNull(tongbizengzhang8)){
				next.put("tongbizengzhang8", 0);
			}
			Object weixiuzhanzhi7 = next.get("weixiuzhanzhi7");
			if("null".equals(weixiuzhanzhi7)||Objects.isNull(weixiuzhanzhi7)){
				next.put("weixiuzhanzhi7", 0);
			}
			Object replace_rate7 = next.get("replace_rate7");
			if("null".equals(replace_rate7)||Objects.isNull(replace_rate7)){
				next.put("replace_rate7", 0);
			}
			Object loss_ration7 = next.get("loss_ration7");
			if("null".equals(loss_ration7)||Objects.isNull(loss_ration7)){
				next.put("loss_ration7", 0);
			}
			next.put("premium7", new BigDecimal(new java.text.DecimalFormat("#.00")
					.format(next.get("premium7"))));
			next.put("loss_ration8", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("loss_ration8"))));
			next.put("qiandanleiji8", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("qiandanleiji8"))));
			next.put("tongbizengzhang8", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("tongbizengzhang8"))));
			next.put("weixiuzhanzhi7", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("weixiuzhanzhi7"))));
			next.put("replace_rate7", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("replace_rate7"))));
			next.put("loss_ration7", new BigDecimal(new java.text.DecimalFormat("#.00000")
					.format(next.get("loss_ration7"))));
			next.put("survey_center_id", map.get(next.get("name")));
			
			map18.put("survey_center_id", map.get(next.get("name")));
			map18.put("premium", null);
			map18.put("replace_rate", null);
			map18.put("loss_ration", next.get("loss_ration8"));
			map18.put("qiandanleiji", next.get("qiandanleiji8"));
			map18.put("tongbizengzhang", next.get("tongbizengzhang8"));
			map18.put("weixiuchanzhi", null);
			list18.add(map18);
			map17.put("survey_center_id", map.get(next.get("name")));
			map17.put("premium", next.get("premium7"));
			map17.put("replace_rate", next.get("replace_rate7"));
			map17.put("loss_ration", next.get("loss_ration7"));
			map17.put("qiandanleiji", null);
			map17.put("tongbizengzhang", null);
			map17.put("weixiuchanzhi", next.get("weixiuzhanzhi7"));
			list17.add(map17);
			//map18.put("weixiuchanzhi", "");
		}
/*		String sql1="insert into survey_center_branch(survey_center_id,premium,replace_rate,loss_ration,qiandanleiji,tongbizengzhang,weixiuchanzhi,year,create_time,update_time) values(?,?,?,?,?,?,?,?,now(),now())";
	    DBHelper db1 = new DBHelper(sql1);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <10; j++){
			db1.pst=db1.conn.prepareStatement(sql1);
			for (int i = 0; i < 100; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map1 = list17.get(count);
				count++;
				db1.pst.setObject(1, map1.get("survey_center_id"));
				db1.pst.setObject(2, map1.get("premium"));
				db1.pst.setObject(3, map1.get("replace_rate"));
				db1.pst.setObject(4, map1.get("loss_ration"));
				db1.pst.setObject(5, map1.get("qiandanleiji"));
				db1.pst.setObject(6, map1.get("tongbizengzhang"));
				db1.pst.setObject(7, map1.get("weixiuchanzhi"));
				db1.pst.setObject(8, "2017");
				db1.pst.addBatch();
			}
			int[] executeBatch = db1.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db1.pst.close();
		}
		System.out.println(num);*/
	/*	System.out.println("总共执行的sql"+num);
		System.out.println("-----------------------------------------------------------------");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		for (Map<String, Object> map2 : list18) {
			System.out.println(map2);
		}*/
		String sql1="insert into survey_center_branch(survey_center_id,premium,replace_rate,loss_ration,qiandanleiji,tongbizengzhang,weixiuchanzhi,year,create_time,update_time) values(?,?,?,?,?,?,?,?,now(),now())";
	    DBHelper db1 = new DBHelper(sql1);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <10; j++){
			db1.pst=db1.conn.prepareStatement(sql1);
			for (int i = 0; i < 100; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map1 = list18.get(count);
				count++;
				db1.pst.setObject(1, map1.get("survey_center_id"));
				db1.pst.setObject(2, map1.get("premium"));
				db1.pst.setObject(3, map1.get("replace_rate"));
				db1.pst.setObject(4, map1.get("loss_ration"));
				db1.pst.setObject(5, map1.get("qiandanleiji"));
				db1.pst.setObject(6, map1.get("tongbizengzhang"));
				db1.pst.setObject(7, map1.get("weixiuchanzhi"));
				db1.pst.setObject(8, "2018");
				db1.pst.addBatch();
			}
			int[] executeBatch = db1.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db1.pst.close();
		}
		System.out.println(num);

	}
    //从财险职场分布excel读取数据 insert到counterman_career数据库
    public static void setCounterManCareer() throws Exception {
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readCountermanCareerExcel();
		//修改数据
		Iterator<Map<String, Object>> iterator = data.iterator();
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			Object career_code = next.get("career_code");
			if("null".equals(career_code)||Objects.isNull(career_code)){
				next.remove(career_code);
			}
			String belong_area = (String)next.get("belong_area");
			if("萝岗".equals(belong_area)){
				next.put("belong_area", "黄埔_萝岗");
			}else if("南岗".equals(belong_area)){
				next.put("belong_area", "黄埔_南岗");
			}
		}
		for(Map<String, Object> map : data){
				System.out.println(map);
		}
		String sql="insert into counterman_career(branch_company,belong_area,career_code,career_name,create_time,update_time) values('广州分公司',?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <10; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 50; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("belong_area"));
				db.pst.setObject(2, map.get("career_code"));
				db.pst.setObject(3, map.get("career_name"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();
	}
    //读取油卡发卡数据 插入到数据库
    public static void ImportOilCardInsert() throws Exception {
    	String sql = "insert into oil_recharge_code(user_name,phone,money,che_pai,address,recharge_code,create_time,update_time) values(?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = ExcelOper.readOilCardExcel();
    	int totalCount = data.size()-1;
    	int count = 0;
    	for(int j=1;j<30;j++){
    		if(count>totalCount){
				break;
			}
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<1000;i++){
    			if(count>totalCount){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("user_name"));
    			db.pst.setObject(2, record.get("phone"));
    			db.pst.setObject(3, record.get("money"));
    			db.pst.setObject(4, record.get("che_pai"));
    			db.pst.setObject(5, record.get("address"));
    			db.pst.setObject(6, RandomUtil.randomNumber(18));
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
        db.close();//关闭连接  
	}
    
    //读取油卡发卡数据 插入到数据库
    public static void updateOilCard() throws Exception {
    	String sql = "update  oil_recharge_code set che_pai = ? where phone = ?";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = ExcelOper.readOilCardExcel();
    	int totalCount = data.size()-1;
    	int count = 0;
    	for(int j=1;j<30;j++){
    		if(count>totalCount){
				break;
			}
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<100;i++){
    			if(count>totalCount){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("che_pai"));
    			db.pst.setObject(2, record.get("phone"));
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
        db.close();//关闭连接  
	}
    
    //update数据库counterman
    public static void updateCounterman() throws Exception {
    	String sql = "update counterman set insurance_network=? where counterman_code = ?";
		DBHelper db = new DBHelper(sql);
		List<Map<String, Object>> data = ExcelOper.readCounterman1Excel();
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <200; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 200; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("insurance_network"));
				db.pst.setObject(2, map.get("counterman_code"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();//关闭连接  
	}
    
    //update数据库counterman_cai_dot
    public static void insertCaiDot() throws Exception {
    	String sql = "insert into counterman_cai_dot(dot_name,create_time,update_time) values(?,now(),now())";
		DBHelper db = new DBHelper(sql);
		List<Map<String, Object>> data = ExcelOper.readCaiDotExcel();
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <50; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 30; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("dot_name"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();//关闭连接  
	}
    public static void updateCounterManCareer2() throws Exception {
    	DBHelper db = new DBHelper("select * from counterman_cai_dot");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update counterman_career set cai_dot_id = ? where belong_area = ?");
        while (ret.next()) {  
            String dotName = ret.getString(2);
            int id = ret.getInt(1);
            System.out.println(dotName + " : " + id);
            updateSql.setInt(1,   id);
            updateSql.setString(2, dotName);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    public static void updateCounterMan2() throws Exception {
    	DBHelper db = new DBHelper("select * from counterman_cai_dot");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update counterman set cai_dot_id = ? where cai_dot_name = ?");
        while (ret.next()) {  
            String dotName = ret.getString(2);
            int id = ret.getInt(1);
            System.out.println(dotName + " : " + id);
            updateSql.setInt(1,   id);
            updateSql.setString(2, dotName);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    
    public static void updateCounterManGood() throws Exception {
    	DBHelper db = new DBHelper("select * from counterman_cai_dot");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update counterman_good set cai_dot_id = ? where cai_dot_name = ?");
        while (ret.next()) {  
            String dotName = ret.getString(2);
            int id = ret.getInt(1);
            System.out.println(dotName + " : " + id);
            updateSql.setInt(1,   id);
            updateSql.setString(2, dotName);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    //从 excel读取数据 insert 到数据库的counterman_life_career
    public static void insertCountermanLifeCareer() throws Exception {
    	DBHelper db = new DBHelper("insert into counterman_life_career(career_code,career_name,create_time,update_time) values(?,?,now(),now())");
		List<Map<String, Object>> data = ExcelOper.readLifeCareerExcel();
		AtomicInteger i = new AtomicInteger(0);
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("career_code"));
			db.pst.setObject(2, map.get("career_name"));
			db.pst.addBatch();
			System.out.println("批处理添加第 :" + i.incrementAndGet()+" 条记录");
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
	}
    
    //从业务员 excel读取数据 insert到counterman数据库
    public static void setCounterMan1() throws Exception {
    	//读取数据
		List<Map<String, Object>> data = ExcelOper.readcountermanExcel();
		//修改数据
		Iterator<Map<String, Object>> iterator = data.iterator();
		while(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			String status = (String)next.get("status");
/*			String phone = (String)next.get("phone");
			if(StringUtils.isBlank(phone)){
				System.out.println(next.get("name"));
				//next.put("phone", null);
			}else{
				if(phone.length()>11){
					System.out.println(phone);
					//next.put("phone",phone.trim());;
				}
			}*/
			if("在职".equals(status)){
				next.put("status", 0);
			}else if("离职".equals(status)){
				next.put("status", 1);
			}else{
				next.put("status", 2);
			}
		}
		/*		for(Map<String, Object> map : data){
			System.out.println(map);
		}
		System.out.println("total size : " + data.size());*/
		//System.out.println(data);
		String sql="insert into counterman(name,phone,counterman_code,cai_dot_name,career_code,career_name,status,create_time,update_time) values(?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <100; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 250; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("name"));
				db.pst.setObject(2, map.get("phone"));
				db.pst.setObject(3, map.get("counterman_code"));
				db.pst.setObject(4, map.get("cai_dot_name"));
				db.pst.setObject(5, map.get("career_code"));
				db.pst.setObject(6, map.get("career_name"));
				db.pst.setObject(7, map.get("status"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();
	}
    
    public static void updateCounterMan3() throws Exception {
    	DBHelper db = new DBHelper("select * from counterman_cai_dot");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update counterman set cai_dot_id = ? where cai_dot_name = ?");
        while (ret.next()) {  
            String dotName = ret.getString(2);
            int id = ret.getInt(1);
            System.out.println(dotName + " : " + id);
            updateSql.setInt(1,   id);
            updateSql.setString(2, dotName);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    public static void updateCounterMan4() throws Exception {
    	DBHelper db = new DBHelper("select * from counterman_life_career");
    	ResultSet  ret = db.pst.executeQuery();
    	PreparedStatement updateSql = db.conn.prepareStatement("update counterman set life_career_id = ? where career_code = ?");
        while (ret.next()) {  
            String careerCode = ret.getString(2);
            int id = ret.getInt(1);
            System.out.println(careerCode + " : " + id);
            updateSql.setInt(1,   id);
            updateSql.setString(2, careerCode);
            updateSql.execute();
        }//显示数据  
        updateSql.close();
        ret.close();  
        db.close();//关闭连接  
	}
    //从 excel读取数据 update 到数据库
    public static void queryImportUserInfo() throws Exception {
    	//查找import_user_info表
    	List<Map<String, Object>> data = ExcelOper.readImportUserInfoExcel();
    	String sql = "select * from import_user_info where che_pai=?";
		DBHelper db = new DBHelper(sql);
		AtomicInteger i = new AtomicInteger(0);
		AtomicInteger s = new AtomicInteger(0);
		AtomicInteger f = new AtomicInteger(0);
		//根据条件查询import_user_info
		for(Map<String, Object> map : data){
			db.pst.setObject(1, map.get("che_pai"));
			i.incrementAndGet();
			ResultSet ret = db.pst.executeQuery();
			while (ret.next()) {  
				int id = ret.getInt(1);
				int peiqiTimes = ret.getInt(18);
				System.out.println(peiqiTimes);
				if(Objects.isNull(id)){
					f.incrementAndGet();
				}else{
					s.incrementAndGet();
				}
		    }
		}
		System.out.println("总数:"+i);
		System.out.println("成功数:"+s);
		System.out.println("失败数"+f);
	}
  //update数据库import_user_info
    public static void updateImportUserInfo() throws Exception {
    	String sql = "update import_user_info set penqi_times =? where che_pai = ?";
		DBHelper db = new DBHelper(sql);
		List<Map<String, Object>> data = ExcelOper.readCounterman1Excel();
		int count = 0;
		int num = 0;
		for(int j = 0 ;j <200; j++){
			db.pst=db.conn.prepareStatement(sql);
			for (int i = 0; i < 200; i++) {
				if(count>=data.size()){
					break;
				}
				Map<String, Object> map = data.get(count);
				count++;
				db.pst.setObject(1, map.get("insurance_network"));
				db.pst.setObject(2, map.get("counterman_code"));
				db.pst.addBatch();
			}
			int[] executeBatch = db.pst.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.println("批次中成功量:"+executeBatch[i]);
			}
			System.out.println("每批次执行条数"+executeBatch.length);
			num+=executeBatch.length;
			db.pst.close();
		}
		System.out.println("总共执行的sql"+num);
        db.close();//关闭连接  
	}
    
    
    //
    public static void genOilCardTwo() throws Exception {
    	String sql = "insert into oil_recharge_code(user_name,phone,money,che_pai,address,recharge_code,sms_status,create_time,update_time) values(?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	//SimpleDateFormat sm = new SimpleDateFormat("yyMMdd");
    	//String seqPreFix = sm.format(new Date());
    	int  baseNum = 21001;
    	for(int i=0;i<7000;i++){
    		Map<String, Object> one = new HashMap<String, Object>();
    		one.put("user_name", "system");
    		one.put("phone", "1000"+(baseNum+i));
    		one.put("money", 100);
    		one.put("che_pai", "system");
    		one.put("address", "system");
    		data.add(one);
    	}
    	
    	int totalCount = data.size()-1;
    	int count = 0;
    	for(int j=1;j<30;j++){
    		if(count>totalCount){
				break;
			}
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<1000;i++){
    			if(count>totalCount){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("user_name"));
    			db.pst.setObject(2, record.get("phone"));
    			db.pst.setObject(3, record.get("money"));
    			db.pst.setObject(4, record.get("che_pai"));
    			db.pst.setObject(5, record.get("address"));
    			db.pst.setObject(6, RandomUtil.randomNumber(18));
    			db.pst.setObject(7, 1);
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
        db.close();//关闭连接  
	}
    /**
     * 更新国寿表
     * @throws Exception
     */
    public static void updateGouShou() throws Exception{
    	DBHelper db = new DBHelper("select * from gs_agent where dot = ? and agency = ?");
    	PreparedStatement insertSql = db.conn.prepareStatement("insert into gs_agent(dot,agency,"
    			+ "cooperate_time,create_time,update_time)"
    			+"values(?,?,?,now(),now())");
    	PreparedStatement insertSql2 = db.conn.prepareStatement("insert into gs_agent_data(agency_id,year,month,"
    			+ "premium,replace_value,replace_rate,loss_rate,create_time,update_time)"
    			+"values(?,?,?,?,?,?,?,now(),now())");
		List<Map<String, Object>> data = ExcelOper.readGuoShouData();
        for(Map<String, Object> map:data){
        	Long id = null;
        	db.pst.setObject(1, map.get("dot"));
        	db.pst.setObject(2, map.get("agency"));
        	ResultSet ret = db.pst.executeQuery();
        	while(ret.next()){
        		id = ret.getLong(1);
        	}
        	if(Objects.isNull(id)){
        		insertSql.setObject(1, map.get("dot"));
            	insertSql.setObject(2, map.get("agency"));
            	insertSql.setObject(3, map.get("cooperateTime"));
            	insertSql.execute();
        		ret = db.pst.executeQuery();
        		while(ret.next()){
            		id = ret.getLong(1);
            	}
        	}
            //设置价格
			for (int i = 1; i <= 12; i++) {//需要修改的地方
				String year  = "2011";//需要修改的地方
				insertSql2.setObject(1, id);
				insertSql2.setObject(2, year);
				insertSql2.setObject(3, String.valueOf(i));
				insertSql2.setObject(4, map.get("m1"+i));
				insertSql2.setObject(5, map.get("m2"+i));
				insertSql2.setObject(6, map.get("ratea1"));
				insertSql2.setObject(7, map.get("ratea2"));
				insertSql2.execute();
			}
        }
        insertSql.close();
        insertSql2.close();
        db.close();//关闭连接  
	}
    
    //
    public static void genOilCard() throws Exception {
    	String sql = "insert into oil_recharge_code(user_name,phone,money,che_pai,address,recharge_code,status,sms_status,create_time,update_time) values(?,?,?,?,?,?,?,?,now(),now())";
    	DBHelper db = new DBHelper(sql);
    	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	//SimpleDateFormat sm = new SimpleDateFormat("yyMMdd");
    	//String seqPreFix = sm.format(new Date());
    	int  baseNum = 100341551; //100341550
    	for(int i=0;i<1000;i++){ //
    		Map<String, Object> one = new HashMap<String, Object>();
    		one.put("user_name", "system");
    		one.put("phone", String.valueOf(baseNum+i));//1001  String.valueOf(baseNum+i)
    		one.put("money", 10000); //
    		one.put("che_pai", "system");
    		one.put("address", "system");
    		data.add(one);
    	}
    	
    	int totalCount = data.size()-1;
    	int count = 0;
    	for(int j=1;j<30;j++){
    		if(count>totalCount){
				break;
			}
    		db.pst = db.conn.prepareStatement(sql);
    		for(int i=0;i<1000;i++){
    			if(count>totalCount){
    				break;
    			}
    			Map<String, Object> record = data.get(count);
    			count++;
    			db.pst.setObject(1, record.get("user_name"));
    			db.pst.setObject(2, record.get("phone"));
    			db.pst.setObject(3, record.get("money"));
    			db.pst.setObject(4, record.get("che_pai"));
    			db.pst.setObject(5, record.get("address"));
    			db.pst.setObject(6, RandomUtil.randomNumber(18));
    			db.pst.setObject(7, 1);
    			db.pst.setObject(8, 1);
    			db.pst.addBatch();
    		}
    		System.out.println("第"+j+"次批处理执行数量：" + db.pst.executeBatch().length);
    		db.pst.close();
    	}
        db.close();//关闭连接  
	}
    //从 excel读取数据 再导入excel
    public static void getOilCard() throws Exception {
    	DBHelper db = new DBHelper("select phone,money,recharge_code,status,recharge_time from oil_recharge_code where phone between ? and ? ");
		List<Map<String, String>> data = ExcelOper.readOilCardFromExcel();
		AtomicInteger i = new AtomicInteger(0);
		AtomicInteger z = new AtomicInteger(0);
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		for(Map<String, String> map : data){
			db.pst.setObject(1, map.get("startPhone"));
			db.pst.setObject(2, map.get("endPhone"));
			ResultSet ret = db.pst.executeQuery();
			AtomicInteger j = new AtomicInteger(0);
			while(ret.next()){
				Map<String, String> result = new HashMap<String,String>();
				String phone = ret.getString(1);
				result.put("phone", phone);
				BigDecimal money = ret.getBigDecimal(2);
				result.put("money", String.valueOf(money.intValue()));
				String rechargeCode = ret.getString(3);
				result.put("rechargeCode", rechargeCode);
				int status = ret.getInt(4);
				Date rechargeTime = ret.getDate(5);
				if(status==1&&Objects.isNull(rechargeTime)){
					result.put("status", "冻结");
				}else if(status==1){
					result.put("status", "已使用");
				}else if(status==0){
					result.put("status", "未使用");
				}else{
					result.put("status", "未知状态");
				}
				resultList.add(result);
				j.incrementAndGet();
				z.incrementAndGet();
        	}
			System.out.println("每批多少张"+j);
			System.out.println("批处理第 :" + i.incrementAndGet()+" 条记录");
		}
		db.pst.executeBatch();
        db.close();//关闭连接  
        System.out.println(z);
        HSSFWorkbook wb = ExportExcelData.createExcelFile1(resultList, 
        		new String[]{"序号","面额","卡密","状态"},
        		new String[]{"phone","money","rechargeCode","status"},
        		"数据统计");
        wb.write(new File("E:\\2月数据统计.xls"));
	}
    public static void main(String[] args) throws Exception, BadHanyuPinyinOutputFormatCombination {
    	//getImage();
    	//setImage();
    	//setSeller();
    	//setPrice();
    	//randomInsert();
    	//setOil();
    	//setJilv();
    	//autoInfoInsert();
    	//autoPartInsert();
    	//AutoPartRelationInsert();
    	//ImportUserInfoInsert();
    	//updateAutoInfo();
    	//setCounterMan();
    	//ImportOilCardInsert();
    	//setSurveyCenter();
    	//setCounterManCareer();
    	//updateCounterman();
      //DBHelper.url1 = DBHelper.url;
    	//ImportOilCardInsert();
       	//updateOilCard();
    	//insertCaiDot();
    	
    	//updateCounterManCareer2();
    	
    	//updateCounterMan4();
    	
    	//insertCountermanLifeCareer();
    	
    	//setCounterMan1();
    	
    	//queryImportUserInfo();
    	//setJiayouzhan();
    	//genOilCard();
    	//updateGouShou();
    	//getOilCard();
    	
    	//setSurveyCenterTwo();
    	
    	setSurveyCenterBranchTwo();
	}
}
