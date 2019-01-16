package com.emate.shop.h5.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.GsBranchService;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 定损中心;营业网点,车行
 * @author dong
 *
 */
@Controller
@RequestMapping(value="gsbranch")
public class GsBranchController implements AuthUtil{
	
	private GsBranchService gsBranchService;
	
	@RemoteService
	public void setGsBranchService(GsBranchService gsBranchService) {
		this.gsBranchService = gsBranchService;
	}
	
	/**
	 * 跳转页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showmap.html")
	public String centerMap(HttpServletRequest request) throws Exception {
		return "counterman/showmap";
	}
	
	/**
	 * 跳转页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/carshop-detail.html")
	public String centerDetail(HttpServletRequest request) throws Exception {
		return "counterman/carshop-detail";
	}
	
	/**
	 * 查询所有的定损中心,车行,营业网点
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getgsdata")
	@ResponseBody
	public DatasetList<Map<String, Object>> getSurveyCenterList(String city) throws Exception {
		DatasetList<Map<String, Object>> list = gsBranchService.h5GsDataList(city);
		return list;
	}
	
	/**
	 * 查询某个车行详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSurveyCenter")
	@ResponseBody
	public DatasetSimple<Map<String,Object>> getSurveyCenter(HttpServletRequest request,@Required @Regex("\\d+") String id) throws Exception {
		DatasetSimple<Map<String,Object>> surveyCenter = 
				gsBranchService.h5SurveyCenter(Long.valueOf(id));
		return surveyCenter;
	}
}
