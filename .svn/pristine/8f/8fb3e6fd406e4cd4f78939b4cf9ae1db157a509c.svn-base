package com.emate.shop.h5.controller;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.GsAgentService;
import com.emate.shop.business.model.GsAgent;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Required;

/**
 * 国寿销售额统计
 * @author dong
 *
 */
@Controller
@RequestMapping(value="gsagent")
public class GsAgentController implements AuthUtil{
	
	private GsAgentService gsAgentService;

	@RemoteService
	public void setGsAgentService(GsAgentService gsAgentService) {
		this.gsAgentService = gsAgentService;
	}

	/**
	 * 跳转页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dot.html")
	public String centerMap(HttpServletRequest request) throws Exception {
		return "guoshou/dotlist";
	}

	/**
	 * 跳转代理公司列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentlist.html")
	public String centerAgent(HttpServletRequest request) throws Exception {
		return "guoshou/agent_list";
	}
	/**
	 * 跳转代理公司详情页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentdetail.html")
	public String centerAgentDetail(HttpServletRequest request) throws Exception {
		return "guoshou/agent_detail";
	}
	
	/**
	 * 查询网点列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dotlist")
	@ResponseBody
	public DatasetList<String> dotlist(String dotName) throws Exception {
		return gsAgentService.h5DotList(dotName);
	}
	
	/**
	 * 查询代理公司列表
	 * @param daili
	 * @return
	 */
	@RequestMapping(value = "/agentlist")
	@ResponseBody
	public DatasetList<GsAgent> findAgentList(@Required String dotName ,String agent){
		return gsAgentService.h5GsAgentList(dotName,agent);
	}
	
	/**
	 * 根据agentId和year查询代理公司详情
	 * @param agentId
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/queryagentdata")
	@ResponseBody
	public DatasetList<Map<String,Object>> queryAgentData(String agentId,String year){
		return gsAgentService.h5QueryGsAgentData(agentId, year);
	}
}
