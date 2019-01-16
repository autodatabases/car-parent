package com.emate.shop.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.JilvConfigService;
import com.emate.shop.business.model.JilvConfig;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 机滤配置controller
 * @author dong
 *
 */
@Controller
@RequestMapping("/jilvconfig")
public class JilvConfigController {
	
	private JilvConfigService jilvConfigService;

	@RemoteService
	public void setJilvConfigService(JilvConfigService jilvConfigService) {
		this.jilvConfigService = jilvConfigService;
	}
	/**
	 * 分页查询机滤配置
	 * @param pageNo
	 * @param pageSize
	 * @param cityName
	 * @return
	 */
	@RequestMapping("/adminpage")
	@ResponseBody
	@AuthAction
	public DatasetList<JilvConfig> adminQueryPage(HttpServletRequest request,@Regex("\\d+") String pageNo,@Regex("\\d+") String pageSize,String cityName){
		
		return jilvConfigService.adminQueryPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize), cityName);
	}
	/**
	 * 根据城市查询机滤配置
	 * @param cityName
	 * @return
	 */
	@RequestMapping("/querybycity")
	@ResponseBody
	@AuthAction
	public DatasetList<JilvConfig> queryByCity(HttpServletRequest request,@Required String cityName){
		
		return jilvConfigService.adminQueryByCityName(cityName);
	}
	
	/**
	 * 修改机滤配置
	 * @param jilvConfigs
	 * @return
	 */
	@RequestMapping(value = "/addconfig",method= RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> adminAddConfig(HttpServletRequest request,@RequestBody List<JilvConfig> jilvConfigs){
		return jilvConfigService.updateConfig(jilvConfigs);
	};
	
	/**
	 * 根据城市删除机滤配置
	 * @param cityName
	 * @return
	 */
	@RequestMapping("/delconfig")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> adminDelConfig(HttpServletRequest request,@Required String cityName){
		return jilvConfigService.delJilvConfig(cityName);
	}
}
