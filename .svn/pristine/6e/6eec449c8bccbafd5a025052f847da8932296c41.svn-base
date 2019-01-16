package com.emate.shop.admin.controller;

import java.io.InputStream;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.emate.shop.business.api.AnalysisService;
import com.emate.shop.business.api.GsAgentService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.ExcelUtil;
import com.emate.tools.ExportExcelData;


/**
 * 统计页面
 * @author dong
 *
 */
@Controller
@RequestMapping("/analysis")
public class AnalysisController {

	private AnalysisService analysisService;
	
	private GsAgentService gsAgentService;
	
	@RemoteService
	public void setGsAgentService(GsAgentService gsAgentService) {
		this.gsAgentService = gsAgentService;
	}

	@RemoteService
	public void setAnalysisService(AnalysisService analysisService) {
		this.analysisService = analysisService;
	}
	
    /**
     * 根据月份和地址查询导入保单的一些信息
     * @param request
     * @param response
     * @param date
     * @param address
     */
    @RequestMapping("exportanalysisbaodan")
    public void exportAnalysisBaoDan(HttpServletRequest request,
    		HttpServletResponse response,
    		String date,String address,String source) {
    	DatasetList<Map<String, String>> data = analysisService.analysisBaoDan(date, address,source);
    	//组织sheet名称
    	String year = date.split("-")[0];
		String month = date.split("-")[1];
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(year).append("年").append(month).append("月").append("-");
		String sheetName = stringBuffer.toString();
		//组织excel文件名称
		StringBuffer strBuffer = new StringBuffer();
 		strBuffer.append(year).append("年").append(month).append("月").append(address).append("导入保单用户的保单统计分析");
    	String fileName = strBuffer.toString();
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"日期","保单地址","赠送的总保养次数","赠送的总洗车次数","赠送的总喷漆副数","导入保单总数","导入保单的保费总额","渠道"},
    			new String[]{"month","address","baoTimes","xiTimes","penTimes","baoNum","baoFei","source"}, sheetName);
    }
    
    /**
     * 根据月份和地址查询订单的一些信息
     * @param request
     * @param response
     * @param date
     * @param address
     */
    @RequestMapping("exportanalysisorder")
    public void exportAnalysisOrderExcel(HttpServletRequest request,
    		HttpServletResponse response,String date,String address,String source) {
    	DatasetList<Map<String, String>> data = analysisService.analysisOrders(date, address,source);
    	//组织sheet名称
    	String year = date.split("-")[0];
 		String month = date.split("-")[1];
 		StringBuffer stringBuffer = new StringBuffer();
 		stringBuffer.append(year).append("年").append(month).append("月").append("-");
 		String sheetName = stringBuffer.toString();
 		//组织excel文件名称
 		StringBuffer strBuffer = new StringBuffer();
 		strBuffer.append(year).append("年").append(month).append("月").append(address).append("下单用户的订单统计分析");
     	String fileName = strBuffer.toString();
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"日期","保单地址","订单类型","渠道","订单总数量","订单总金额","保费总金额"},
    			new String[]{"date","address","orderType","source","orderNum","orderPrice","baoPrice"}, sheetName);
    }
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping("importAgentData")
    @ResponseBody
    public DatasetSimple<Map<String,Object>> importGsAgent(HttpServletRequest request){
    	
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	List<MultipartFile> files = multipartRequest.getFiles("gs_agent");
		try {
			for(MultipartFile file : files){
				if(!file.isEmpty()){
					if(file.getOriginalFilename().toLowerCase().endsWith(".xls")||
						file.getOriginalFilename().toLowerCase().endsWith(".xlsx")){
						Log4jHelper.getLogger().info("开始导入国寿代理公司的数据:"+file.getOriginalFilename());
						return gsAgentService.importGsAgentData(readExcelData(file.getInputStream(),file.getOriginalFilename()));
					}else{
						throw new BusinessException("导入数据失败,文件格式不正确~");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("导入国寿代理数据错误!!!"+e.getMessage());
		}
    	return DatasetBuilder.fromMessageSimple("本次导入文件为空!!!");
    }
    //读取excel数据
    private List<Map<String,Object>> readExcelData(InputStream in,String fileName) throws Exception{
    	Workbook book = ExcelUtil.createWorkBook(in, fileName);
    	String[] params = new String[]{"dot","agency","cooperateTime","date","premium",
    			"replaceValue","replaceRate","lossRate"};
    	List<Map<String, Object>> dataList = ExcelUtil.getRecodeMapByExcel(book, 0, params, 1, 500);
    	
    	Iterator<Map<String, Object>> it = dataList.iterator();
    	while(it.hasNext()){
    		Map<String, Object> gsAgentData = it.next();
    		
        	//设置时间格式
    		
    		//合作时间
    		Object cooperateTime = gsAgentData.get("cooperateTime");
    		SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd");
    		if(cooperateTime==null){
    			throw new BusinessException(cooperateTime+"日期为空");
    		}
    		if(cooperateTime instanceof Date){
    			cooperateTime = sm.format(cooperateTime);
    		}else if(!(cooperateTime instanceof CharSequence) ||((String)cooperateTime).split("/").length != 3){
    			throw new BusinessException(cooperateTime+"日期格式不对,应该为 2018/6/16");
    		}
    		gsAgentData.put("cooperateTime", cooperateTime);
    		
    		//年月
    		Object date = gsAgentData.get("date");
    		if(date==null){
    			throw new BusinessException(date+"日期为空");
    		}
    		if(date instanceof Date){
    			date = sm.format(date);
    		}else if(!(date instanceof CharSequence) ||((String)date).split("/").length != 3){
    			throw new BusinessException(date+"日期格式不对,应该为 2018/6/16");
    		}
    		gsAgentData.put("date",date);
    		
    		
			//设置保费,置换产值,置换率,赔付率
    		
    		//设置保费
    		if(Objects.isNull(gsAgentData.get("premium"))){
    			throw new BusinessException("保费不能为空,若没有,请填0");
    		}
    		if(gsAgentData.get("premium") instanceof Number){
    			gsAgentData.put("premium", new BigDecimal(new java.text.DecimalFormat("#.00").format(gsAgentData.get("premium"))));
			}else if(gsAgentData.get("premium") instanceof CharSequence && ((String)gsAgentData.get("premium")).matches("\\d+")){
				gsAgentData.put("premium", new BigDecimal((String)gsAgentData.get("premium")));
			}else{
				throw new BusinessException(gsAgentData.get("premium")+"这个保费格式不对,请修改");
			}
    		//设置置换产值
    		if(Objects.isNull(gsAgentData.get("replaceValue"))){
    			throw new BusinessException("置换产值不能为空,若没有,请填0");
    		}
    		if(gsAgentData.get("replaceValue") instanceof Number){
    			gsAgentData.put("replaceValue", new BigDecimal(new java.text.DecimalFormat("#.00").format(gsAgentData.get("replaceValue"))));
			}else if(gsAgentData.get("replaceValue") instanceof CharSequence && ((String)gsAgentData.get("replaceValue")).matches("\\d+")){
				gsAgentData.put("replaceValue", new BigDecimal((String)gsAgentData.get("replaceValue")));
			}else{
				throw new BusinessException(gsAgentData.get("replaceValue")+"这个置换产值格式不对,请修改");
			}
    		//设置置换率
    		
    		if(Objects.isNull(gsAgentData.get("replaceRate"))){
    			throw new BusinessException("置换率不能为空,若没有,请填0");
    		}
			if(gsAgentData.get("replaceRate") instanceof Number){
				gsAgentData.put("replaceRate", new BigDecimal(new java.text.DecimalFormat("#.0000").format(gsAgentData.get("replaceRate"))));
			}else if(gsAgentData.get("replaceRate") instanceof CharSequence && ((String)gsAgentData.get("replaceRate")).matches("[0-9]+.?[0-9]+%")){
				String substring = ((String)gsAgentData.get("replaceRate")).substring(0,((String)gsAgentData.get("replaceRate")).length()-1);
				gsAgentData.put("replaceRate", new BigDecimal(substring).divide(new BigDecimal("100")));
			}else{
				throw new BusinessException(gsAgentData.get("replaceRate")+"这个置换率格式不对,请修改");
			}
			//设置赔付率
			
    		if(Objects.isNull(gsAgentData.get("lossRate"))){
    			throw new BusinessException("赔付率不能为空,若没有,请填0");
    		}
			if(gsAgentData.get("lossRate") instanceof Number){
				gsAgentData.put("lossRate", new BigDecimal(new java.text.DecimalFormat("#.0000").format(gsAgentData.get("lossRate"))));
			}else if(gsAgentData.get("lossRate") instanceof CharSequence && ((String)gsAgentData.get("lossRate")).matches("[0-9]+.?[0-9]+%")){
				String substri = ((String)gsAgentData.get("lossRate")).substring(0,((String)gsAgentData.get("lossRate")).length()-1);
				gsAgentData.put("lossRate", new BigDecimal(substri).divide(new BigDecimal("100")));
			}else{
				throw new BusinessException(gsAgentData.get("lossRate")+"这个赔付率格式不对,请修改");
			}
			
    	}
    	return dataList;
    }
}
