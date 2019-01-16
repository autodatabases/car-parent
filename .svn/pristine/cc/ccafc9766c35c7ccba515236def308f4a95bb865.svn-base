package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.OilCardConfigService;
import com.emate.shop.business.model.OilCardConfig;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 设置油卡面额设置
 * @author dong
 *
 */
@Controller
@RequestMapping("/oilcardconfig")
public class oilCardConfigController {
	
	private OilCardConfigService oilCardConfigService;

	@RemoteService
	public void setOilCardConfigService(OilCardConfigService oilCardConfigService) {
		this.oilCardConfigService = oilCardConfigService;
	}
	
	/**
	 * 查询油卡面额
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param oilCardConfig
	 * @return
	 */
	@RequestMapping("/queryall")
	@ResponseBody
	@AuthAction
	public DatasetList<OilCardConfig> adminQueryPage(HttpServletRequest request,
			@Regex("\\d+") String pageNo,@Regex("\\d+") String pageSize,
			OilCardConfig oilCardConfig){
		
		return oilCardConfigService.queryOilCardConfig(oilCardConfig, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
	}
	/**
	 * 根据id查询油卡面额
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/querybyid")
	@ResponseBody
	@AuthAction
	public DatasetSimple<OilCardConfig> queryById(HttpServletRequest request,
			@Required @Regex("\\d+") String id){
		
		return oilCardConfigService.getOilCardConfig(Long.valueOf(id));
	}
	
	/**
	 * 新增或更新油卡面额
	 * @param request
	 * @param oilCardConfig
	 * @return
	 */
	@RequestMapping(value = "/addorupdate",method= RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Integer> addOrUpdateConfig(HttpServletRequest request,
			OilCardConfig oilCardConfig){
		return oilCardConfigService.addOrUpdateOilCardConfig(oilCardConfig);
	};
	
	/**
	 * 更新油卡面额状态
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updatestatus")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Integer> updateStatus(HttpServletRequest request,@Required String id,@Regex("\\d+") String status){
		return oilCardConfigService.updateStatus(Long.valueOf(id), status);
	}
	
	/**
	 * 根据城市删除机滤配置
	 * @param cityName
	 * @return
	 */
	@RequestMapping("/delconfig")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Integer> adminDelConfig(HttpServletRequest request,@Required String id){
		return oilCardConfigService.deloilCardConfig(id);
	}
}
