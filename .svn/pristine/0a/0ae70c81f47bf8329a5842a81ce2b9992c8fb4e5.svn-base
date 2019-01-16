package com.emate.shop.admin.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.BusinessInfoService;
import com.emate.shop.business.model.BusinessInfo;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
@Controller
@RequestMapping("businessInfo")
public class BusinessInfoController {
	
	private BusinessInfoService businessInfoService;
	
	@RemoteService
	public void setBusinessInfoService(BusinessInfoService businessInfoService) {
		this.businessInfoService = businessInfoService;
	}
	/**
	 * 查询所有业务员
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param businessInfo
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryAll")
    @AuthAction
    public DatasetList<Map<String,Object>> queryBUsinessInfoList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,BusinessInfo businessInfo) {
    	return businessInfoService.adminBusinessInfoList(Integer.valueOf(pageNo),Integer.valueOf(pageSize), businessInfo);
    }
    /**
     * 删除业务员
     * @param request
     * @param businessInfoId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteBusinessInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> deleteAutoInfo(HttpServletRequest request,
			@Required @Regex("\\d+")String businessInfoId) throws Exception {
		return  businessInfoService.deleteBusinessInfo(businessInfoId);
	}
	/**
	 * 添加业务员
	 * @param request
	 * @param response
	 * @param businessInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBusinessInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> addBusinessInfo(HttpServletRequest request,
			HttpServletResponse response,BusinessInfo businessInfo) throws Exception {
		return businessInfoService.addBusinessInfo(businessInfo);
	}
	
	
}
