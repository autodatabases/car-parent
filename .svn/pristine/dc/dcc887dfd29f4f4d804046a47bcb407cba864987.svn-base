package com.emate.shop.admin.controller;

import java.io.File;

import java.io.InputStream;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.emate.shop.business.api.CountermanGoodService;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.tools.ExcelUtil;

@Controller
@RequestMapping("countermanGood")
public class CountermanGoodController implements AuthUtil{
   

	private CountermanGoodService countermanGoodService;
	
	@RemoteService
	public void setCountermanGoodService(CountermanGoodService countermanGoodService){
		this.countermanGoodService = countermanGoodService;
	}
	
    @ResponseBody
    @RequestMapping("queryCountermanGoodList")
    @AuthAction
    public DatasetList<CountermanGood> queryCountermanList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CountermanGood countermanGood) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return countermanGoodService.adminCountermanGoodList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), countermanGood,userId);
    }

	@RequestMapping(value = "/getCountermanGood", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<CountermanGood> getCountermanGood(HttpServletRequest request,
			@Required @Regex("\\d+")String countermanGoodId) throws Exception {
		DatasetSimple<CountermanGood> countermanGood = countermanGoodService.queryCountermanGoodById(countermanGoodId);
		return countermanGood;
	}
	
	@RequestMapping(value = "/addOrUpdateCountermanGood", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addCounterman(HttpServletRequest request,
			@Required CountermanGood countermanGood) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		return countermanGoodService.addOrUpdateCountermanGood(countermanGood,userId);
	}
	
	@RequestMapping(value = "/updateCountermanGoodStatus", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateCountermanGoodStatus(HttpServletRequest request,
			@Required @Regex("[0,1]") String goodStatus,@Required @Regex("\\d+") String countermanGoodId) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		DatasetSimple<CountermanGood>  countermanGood= countermanGoodService.queryCountermanGoodById(countermanGoodId);
		if(!countermanGood.isSuccess()||Objects.isNull(countermanGood.getData())){
			throw new BusinessException("该商品不存在");
		}
		if("0".equals(goodStatus)){
			countermanGood.getData().setGoodStatus(CountermanGood.GOOD_STATUS_0);
		}else if("1".equals(goodStatus)){
			countermanGood.getData().setGoodStatus(CountermanGood.GOOD_STATUS_1);
		}
		return countermanGoodService.addOrUpdateCountermanGood(countermanGood.getData(),userId);
	}
	
    /**
     * 批量更新业务员商品
     */
	@RequestMapping(value = "batchinsertgood")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> batchInsertGood(HttpServletRequest request,String imgpath) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		try{
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		        List<MultipartFile> fileList = multipartRequest.getFiles("insert_good");  
		        for (MultipartFile mf : fileList) {  
		            if(!mf.isEmpty()){
		            	if (mf.getOriginalFilename().toLowerCase().endsWith("xls")
		            			|| mf.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
		            		Log4jHelper.getLogger().info("开始新增业务员商品数据....."+mf.getOriginalFilename());
			        		return this.countermanGoodService.batchInsertGood(readData(mf.getInputStream(),mf.getOriginalFilename(),request,imgpath),userId);
		        		}else{
		        			throw new BusinessException("导入用户失败!，导入文件格式不对！");
		        		}
		            	
		            }  
		        }  
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusinessException("导入用户异常!"+e.getMessage());
	        }
	        return DatasetBuilder.fromMessageSimple("本次导入文件为空！");
	}
	
	private static List<Map<String, Object>> readData(InputStream in,String fileName,HttpServletRequest request,String imgpath) throws Exception{
		Workbook book = ExcelUtil.createWorkBook(in,fileName);
		String[] paramNames = new String[]{"good_name","good_score","good_price","guo_price"
				,"good_number","remark","cai_dot_name","good_img"};
		//excel类型
		String excelType = "";
		if (fileName.toLowerCase().endsWith("xls")) {
			excelType= "0";
		}else if (fileName.toLowerCase().endsWith("xlsx")) {
			excelType= "1";
		}else{
			throw new BusinessException("导入失败,导入文件格式不对!!!");
		}
		//图片存储地址1
		String pathOne = request.getServletContext().getRealPath(File.separator);
		//数据库存储地址2
		String pathTwo = request.getContextPath();
		
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcelTwo(book, 0, paramNames, 1, 400,excelType, pathOne, pathTwo, imgpath);
		if(data.size()>300){
			throw new BusinessException("一次插入商品数据最好不要超过300条！");
		}
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			//商品状态
			t.put("good_status", CountermanGood.GOOD_STATUS_0);
			//非空校验
			if(t.get("good_name")==null||t.get("good_score")==null
					||t.get("good_price")==null||t.get("guo_price")==null
					||t.get("good_number")==null||t.get("cai_dot_name")==null
					||t.get("good_img")==null){
				throw new BusinessException("必填信息(商品名称,商品积分,商品价格,结算价,商品库存,所属财险网点,商品图片)不能为空");
			}
			
			//商品库存
			if(t.get("good_number") instanceof Number){
				BigDecimal goodNumber = new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("good_number")));
				if(goodNumber.doubleValue()<=0||(!String.valueOf(goodNumber.doubleValue()).endsWith(".0"))){
					throw new BusinessException("商品库存格式必须为正整数！");
				}
				t.put("good_number",goodNumber);
			}else{
				if(t.get("good_number") instanceof CharSequence && ((String)t.get("good_number")).matches("\\d+")){
					t.put("good_number", new BigDecimal((String)t.get("good_number")));
				}else{
					throw new BusinessException("商品库存格式必须为正整数！");
				}
			}
			//商品积分
			if(t.get("good_score") instanceof Number){
				BigDecimal goodScore = new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("good_score")));
				if(goodScore.doubleValue()<=0||(!String.valueOf(goodScore.doubleValue()).endsWith(".0"))){
					throw new BusinessException("商品积分格式必须为正整数！");
				}
				t.put("good_score",goodScore);
			}else{
				if(t.get("good_score") instanceof CharSequence && ((String)t.get("good_score")).matches("\\d+")){
					t.put("good_score", new BigDecimal((String)t.get("good_score")));
				}else{
					throw new BusinessException("商品积分格式必须为正整数！");
				}
			}
			//商品价格
			if(t.get("good_price") instanceof Number){
				BigDecimal goodPrice = new BigDecimal(new java.text.DecimalFormat("#.00").format(t.get("good_price")));
				if(goodPrice.doubleValue()>0){
					t.put("good_price",goodPrice);
				}else{
					throw new BusinessException("商品价格格式必须为正数！");
				}
				
			}else{
				if(t.get("good_price") instanceof CharSequence && ((String)t.get("good_price")).matches("\\d+")){
					t.put("good_price", new BigDecimal((String)t.get("good_price")));
				}else{
					throw new BusinessException("商品价格格式必须为数字！");
				}
			}
			//结算价格
			if(t.get("guo_price") instanceof Number){
				BigDecimal guoPrice = new BigDecimal(new java.text.DecimalFormat("#.00").format(t.get("guo_price")));
				if(guoPrice.doubleValue()>0){
					t.put("guo_price",guoPrice);
				}else{
					throw new BusinessException("结算价格格式必须为正数！");
				}
				
				t.put("guo_price",guoPrice);
			}else{
				if(t.get("guo_price") instanceof CharSequence && ((String)t.get("guo_price")).matches("\\d+")){
					t.put("guo_price", new BigDecimal((String)t.get("guo_price")));
				}else{
					throw new BusinessException("结算价格格式必须为数字！");
				}
			}
		}
		Log4jHelper.getLogger().info(data);
		return data;
	}
}
