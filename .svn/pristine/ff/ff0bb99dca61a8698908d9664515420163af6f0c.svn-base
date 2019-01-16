package com.emate.shop.admin.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.OilMakeService;
import com.emate.shop.business.model.OilMakeOrder;
import com.emate.shop.business.model.OilMakeRecord;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
import com.emate.tools.ExportExcelData;

/**
 * 
 */
@Controller
@RequestMapping("/oilmake")
public class OilMakeController {
	
	private OilMakeService oilMakeService;

	@RemoteService
	public void setOilMakeService(OilMakeService oilMakeService) {
		this.oilMakeService = oilMakeService;
	}
	
	/**
	 * 添加做卡一级主表记录
	 * @param request
	 * @param oilMakeRecord
	 * @return
	 */
	@RequestMapping(value = "/addRecord",method= RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<String> addRecord(HttpServletRequest request,
			String money,String num,String deadTime,String remark){
		return oilMakeService.addRecord(money,num,deadTime,remark);
	}
	
	/**
	 *查询做卡一级主表所有记录
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/queryallrecord")
	@ResponseBody
	@AuthAction
	public DatasetList<OilMakeRecord> queryAllRecord(HttpServletRequest request,
			@Required @Regex("\\d+") String pageNo,
			@Required @Regex("\\d+") String pageSize){
		return oilMakeService.queryAllRecord(Integer.valueOf(pageNo),
				Integer.valueOf(pageSize));
	}
	
	/**
	 *根据条件查询做卡二级表所有记录
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/queryallorder")
	@ResponseBody
	@AuthAction
	public DatasetList<OilMakeOrder> queryAllOrder(HttpServletRequest request,
			String startNum,String endNum,
			@Required @Regex("\\d+") String recordId,
			@Required @Regex("\\d+") String pageNo,
			@Required @Regex("\\d+") String pageSize){
		return oilMakeService.queryAllOrder(recordId,startNum,endNum,Integer.valueOf(pageNo),
				Integer.valueOf(pageSize));
	}
	
	/**
	 *根据条件查询做卡三级表所有记录
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/queryallcode")
	@ResponseBody
	@AuthAction
	public DatasetList<Map<String,Object>> queryAllCode(HttpServletRequest request,
			@Required @Regex("\\d+") String orderId,
			@Required @Regex("\\d+") String pageNo,
			@Required @Regex("\\d+") String pageSize){
		return oilMakeService.queryAllCode(orderId,Integer.valueOf(pageNo),
				Integer.valueOf(pageSize));
	}
	
    @RequestMapping("exportoilrechargecode")
    public void exportOilReChargeCode(HttpServletRequest request,
    		HttpServletResponse response,String orderId,
    		String startCode,String endCode) {
    	DatasetList<Map<String, String>> data = oilMakeService.exportOilReChargeCode(orderId);
    	String fileName = "油卡详情";
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"序号","卡密","油卡面额","失效时间"},
    			new String[]{"phone","rechargeCode","money","deadTime"}, 
    			"sheet0");
    }
    
    @RequestMapping("exportoilrechargecodetwo")
    public void exportOilReChargeCodeTwo(HttpServletRequest request,
    		HttpServletResponse response,String recordId,
    		String startCode,String endCode) {
    	DatasetList<Map<String, String>> data = oilMakeService.exportOilReChargeCodeTwo(recordId,startCode,endCode);
    	String fileName = "油卡详情";
    	ExportExcelData.export(request, response, data.getList(), fileName, 
    			new String[]{"序号","卡密","油卡面额","失效时间"},
    			new String[]{"phone","rechargeCode","money","deadTime"}, 
    			"sheet0");
    }
}
