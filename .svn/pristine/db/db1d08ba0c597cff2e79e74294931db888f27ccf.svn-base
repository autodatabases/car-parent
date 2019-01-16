package com.emate.shop.admin.controller;

import java.io.InputStream;



import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*import java.io.InputStream;


import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;*/
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.emate.shop.business.api.CountermanService;
import com.emate.shop.business.model.Counterman;
import com.emate.shop.common.Log4jHelper;
//import com.emate.shop.business.model.CountermanInfo;
//import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
//import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
//import com.emate.tools.ExcelUtil;
import com.emate.tools.ExcelUtil;
import com.emate.tools.ExportExcelData;

@Controller
@RequestMapping("counterman")
public class CountermanController implements AuthUtil{
   

	private CountermanService countermanService;
	
	@RemoteService
	public void setCountermanService(CountermanService countermanService){
		this.countermanService = countermanService;
	}
	/**
	 * 查询业务员列表
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param counterman
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryCountermanList")
    @AuthAction
    public DatasetList<Counterman> queryCountermanList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,Counterman counterman) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return countermanService.adminCountermanList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), counterman,userId);
    }
    
    /**
     * 修改业务员状态
     * @param request
     * @param status
     * @param countermanId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/updateCountermanStatus", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateCountermanStatus(HttpServletRequest request,
			@Required @Regex("[0,1]") String status,@Required @Regex("\\d+") String countermanId) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		DatasetSimple<Counterman>  counterman= countermanService.queryCountermanById(countermanId);
		if(!counterman.isSuccess()||Objects.isNull(counterman.getData())){
			throw new BusinessException("该业务员不存在");
		}
		if("0".equals(status)){
			counterman.getData().setStatus(Counterman.STATUS_0);
		}else{
			counterman.getData().setStatus(Counterman.STATUS_1);
		}
		return countermanService.updateCounterman(counterman.getData(),userId);
	}
	/**
	 * 根据id查询某个业务员
	 * @param request
	 * @param countermanId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterman", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Counterman> getCounterman(HttpServletRequest request,
			@Required @Regex("\\d+")String countermanId) throws Exception {
		DatasetSimple<Counterman> counterman = countermanService.queryCountermanById(countermanId);
		return counterman;
	}
	/**
	 * 添加或编辑业务员
	 * @param request
	 * @param counterman
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateCounterman", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addCounterman(HttpServletRequest request,
			@Required Counterman counterman) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		if(Objects.isNull(counterman.getId())){
			 return countermanService.addCounterman(counterman,userId);
		}else{
			return countermanService.updateCounterman(counterman,userId);
		}
	}
	
	/**
	 *	导入保单用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importcounterman")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> importCounterman(HttpServletRequest request) throws Exception {
	      Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		try{
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		        List<MultipartFile> fileList = multipartRequest.getFiles("insert_counterman");  
		        for (MultipartFile mf : fileList) {  
		            if(!mf.isEmpty()){
		            	if (mf.getOriginalFilename().toLowerCase().endsWith("xls")
		            			|| mf.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
		            		Log4jHelper.getLogger().info("开始导入业务员数据....."+mf.getOriginalFilename());
			        		return this.countermanService.importCounterman(readDataFromExcel(mf.getInputStream(),mf.getOriginalFilename()),userId);
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
	
	private static List<Map<String, Object>> readDataFromExcel(InputStream in,String fileName) throws Exception{
		Workbook book = ExcelUtil.createWorkBook(in,fileName);
		String[] paramNames = new String[]{"name","counterman_code","phone","score","cai_dot_name"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,600);
		if(data.size()>500){
			throw new BusinessException("一次导入数据最好不要超过500条！");
		}
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("name")==null||t.get("counterman_code")==null
					||t.get("phone")==null||t.get("cai_dot_name")==null||t.get("score")==null){
				throw new BusinessException("必填信息(业务员姓名,手机号,工号,所属财险网点名称,积分)不能为空");
			}
			t.put("phone", String.valueOf(t.get("phone")));
			t.put("counterman_code", String.valueOf(t.get("counterman_code")));
			if(t.get("score") instanceof Number){
				BigDecimal score = new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("score")));
				if(score.doubleValue()<=0||(!String.valueOf(score.doubleValue()).endsWith(".0"))){
					throw new BusinessException("业务员积分格式必须为正整数！");
				}
				t.put("score", score);
			}else{
				if(t.get("score") instanceof CharSequence && ((String)t.get("score")).matches("\\d+")){
						t.put("score", new BigDecimal((String)t.get("score")));
				}else{
						throw new BusinessException("业务员积分格式必须为正整数！");
				}
			}
		}
		Log4jHelper.getLogger().info(data);
		return data;
	}
	/**
	 * 根据业务员id查询业务员积分变动记录
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param counterman
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryscorerecord")
    @AuthAction
    public DatasetList<Map<String,Object>> queryScoreRecordList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,Counterman counterman) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return countermanService.queryScoreRecord(Integer.parseInt(pageNo), Integer.parseInt(pageSize), counterman,userId);
    }
    
    /**
	 * 批量更新业务员积分
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "batchupdatecounterman")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> batchUpdateCounterman(HttpServletRequest request) throws Exception {
		Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
		try{
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		        List<MultipartFile> fileList = multipartRequest.getFiles("update_counterman");  
		        for (MultipartFile mf : fileList) {  
		            if(!mf.isEmpty()){
		            	if (mf.getOriginalFilename().toLowerCase().endsWith("xls")
		            			|| mf.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
		            		Log4jHelper.getLogger().info("开始更新业务员积分数据....."+mf.getOriginalFilename());
			        		return this.countermanService.batchUpdateCounterman(readData(mf.getInputStream(),mf.getOriginalFilename()),userId);
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
	
	private static List<Map<String, Object>> readData(InputStream in,String fileName) throws Exception{
		Workbook book = ExcelUtil.createWorkBook(in,fileName);
		String[] paramNames = new String[]{"countermanCode","type","score"};
		List<Map<String, Object>> data = ExcelUtil.getRecodeMapByExcel(book,0,paramNames,1,600);
		if(data.size()>500){
			throw new BusinessException("一次更新数据最好不要超过500条！");
		}
		Iterator<Map<String, Object>> it = data.iterator();
		while(it.hasNext()){
			Map<String, Object> t = it.next();
			if(t.get("countermanCode")==null&&t.get("type")==null
					&&t.get("score")==null){
				it.remove();
				continue;
			}
			if(t.get("countermanCode")==null||t.get("type")==null
					||t.get("score")==null){
				throw new BusinessException("必填信息(业务员工号,类型,积分)不能为空");
			}
			t.put("countermanCode", String.valueOf(t.get("countermanCode")));
			if(String.valueOf(t.get("type")).contains("增加")){
				t.put("type", "0");
			}else{
				t.put("type", "1");
			}
			if(t.get("score") instanceof Number){
				BigDecimal score = new BigDecimal(new java.text.DecimalFormat("#.0").format(t.get("score")));
				if(score.doubleValue()<=0||(!String.valueOf(score.doubleValue()).endsWith(".0"))){
					throw new BusinessException("业务员积分格式必须为正整数！");
				}
				t.put("score",score);
			}else{
				if(t.get("score") instanceof CharSequence && ((String)t.get("score")).matches("\\d+")){
					t.put("score", new BigDecimal((String)t.get("score")));
				}else{
					throw new BusinessException("业务员积分格式必须为正整数！");
				}
			}
		}
		Log4jHelper.getLogger().info(data);
		return data;
	}
	
    @RequestMapping("exportCountermanExcel")
    public void exportCountermanExcel(HttpServletRequest request,HttpServletResponse response) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	DatasetList<Map<String, String>> data = countermanService.exportCountermanExcel(userId);
    	String fileName = "业务员积分统计";
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"业务员工号","业务员姓名","累计积分","本期积分","充减积分","时间"},
    			new String[]{"countermanCode","countermanName","countermanScore",
    					"monthScore","score","createTime"}, "sheet0");
    }
}
