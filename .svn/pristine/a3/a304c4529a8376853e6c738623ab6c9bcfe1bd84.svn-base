package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CountermanCaiDotService;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping("countermanCaiDot")
public class CountermanCaiDotController implements AuthUtil{
   

	private CountermanCaiDotService countermanCaiDotService;
	
	@RemoteService
	public void setCountermanCaiDotService(CountermanCaiDotService countermanCaiDotService){
		this.countermanCaiDotService = countermanCaiDotService;
	}
	
    @ResponseBody
    @RequestMapping("queryCountermanCaiDotList")
    @AuthAction
    public DatasetList<CountermanCaiDot> queryCountermanCaiDotList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CountermanCaiDot countermanCaiDot) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return countermanCaiDotService.adminCountermanCaiDotList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), countermanCaiDot,userId);
    }

	@RequestMapping(value = "/getCountermanCaiDot", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<CountermanCaiDot> getCountermanCaiDot(HttpServletRequest request,
			@Required @Regex("\\d+")String countermanCaiDotId) throws Exception {
		DatasetSimple<CountermanCaiDot> countermanCaiDot = countermanCaiDotService.queryCountermanCaiDotById(countermanCaiDotId);
		return countermanCaiDot;
	}
	
	@RequestMapping(value = "/addOrUpdateCountermanCaiDot", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addCounterman(HttpServletRequest request,
			@Required CountermanCaiDot countermanCaiDot) throws Exception {
		return countermanCaiDotService.addOrUpdateCountermanCaiDot(countermanCaiDot);
	}
	
	/**
	 * 删除财险网点
	 * @param request
	 * @param countermanCaiDotId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCountermanCaiDot", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> deleteCountermanCaiDot(HttpServletRequest request,
			@Required @Regex("\\d+") String countermanCaiDotId) throws Exception {
		return countermanCaiDotService.deleteCountermanCaiDotById(countermanCaiDotId);
	}
	
    @ResponseBody
    @RequestMapping("queryCaiDotList")
    @AuthAction
    public DatasetList<CountermanCaiDot> queryCaiDotList(HttpServletRequest request) {
    	Long userId = this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN);
    	return countermanCaiDotService.queryCaiDotList(userId);
    }
}
