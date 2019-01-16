package com.emate.shop.admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.SurveyRecordService;
import com.emate.shop.business.model.SurveyRecord;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.tools.ExportExcelData;

/**
 * 
 * @author dong
 * 查勘录入信息
 *
 */
@Controller
@RequestMapping("survey")
public class SurveyRecordController implements AuthUtil{
   

	private SurveyRecordService surveyRecordService;
	
	//图片存储地址
	private static String pathOne = "/home/autosmart/carweb-80/webapps/ROOT";
	//private static String pathTwo = "C:\\dong\\workspace\\car-parent\\car-h5\\src\\main\\webapp";
	
	@RemoteService
	public void setSurveyRecordService(SurveyRecordService surveyRecordService){
		this.surveyRecordService = surveyRecordService;
	}
	/**
	 * admin查询查探录入信息
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param surveyRecord
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryrecord")
    public DatasetList<SurveyRecord> queryRecord(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,SurveyRecord surveyRecord) {
    	return surveyRecordService.adminQueryRecord(Integer.valueOf(pageNo),Integer.valueOf(pageSize), surveyRecord);
    }
   
	/**
	 * 导出查勘录入的excel
	 * @param request
	 * @param response
	 * @param date
	 * @throws IOException 
	 */
    @RequestMapping("exportSurveyRecordExcel")
    public void exportSurveyRecordExcel(HttpServletRequest request,HttpServletResponse response,String date){
    	DatasetList<SurveyRecord> data = surveyRecordService.exportRecord(date);
    	List<Map<String, String>> results = new ArrayList<Map<String,String>>();
    	if(data.isSuccess()&&data.getList()!= null){
    		//组织返回结果
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		data.getList().stream().forEach(surveyRecord ->{
    			Map<String, String> result = new HashMap<String,String>();
    			result.put("userName", surveyRecord.getUserName());
    			result.put("userPhone", surveyRecord.getUserPhone());
    			result.put("expiredTime", format.format(surveyRecord.getExpiredTime()));
    			result.put("picture", pathOne+surveyRecord.getLicensePicture());
    			result.put("proposer", surveyRecord.getProposer());
    			results.add(result);
    		});
    	}
    	String fileName = "查勘录入信息"+date;
    	ExportExcelData.export(request, response, results, fileName, 
    			new String[]{"车主姓名","车主联系方式","保险到期日期","驾驶证照片","申请人"},
    			new String[]{"userName","userPhone","expiredTime","picture","proposer"}, "sheet0");
    }
}
