package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CountermanCareerService;
import com.emate.shop.business.model.CountermanCareer;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;
/**
 * 职场
 * @author dong
 *
 */
@Controller
@RequestMapping("countermanCareer")
public class CountermanCareerController implements AuthUtil{
   

	private CountermanCareerService countermanCareerService;
	
	@RemoteService
	public void setCountermanCareerService(CountermanCareerService countermanCareerService){
		this.countermanCareerService = countermanCareerService;
	}
	/**
	 * 后台查询所有职场
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param countermanCareer
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/queryCountermanCareerList")
    @AuthAction
    public DatasetList<CountermanCareer> queryCountermanList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CountermanCareer countermanCareer) {
    	return countermanCareerService.adminCountermanCareerList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), countermanCareer);
    }
    /**
     * 根据id查询职场
     * @param request
     * @param countermanCareerId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/getCountermanCareer", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<CountermanCareer> getCounterman(HttpServletRequest request,
			@Required @Regex("\\d+")String countermanCareerId) throws Exception {
		DatasetSimple<CountermanCareer> countermanCareer = countermanCareerService.queryCountermanCareerById(countermanCareerId);
		return countermanCareer;
	}
	/**
	 * 添加或更新职场
	 * @param request
	 * @param countermanCareer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateCountermanCareer", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addCountermanCareer(HttpServletRequest request,
			@Required CountermanCareer countermanCareer) throws Exception {
		return countermanCareerService.addOrUpdateCountermanCareer(countermanCareer);
	}
	/**
	 * 删除职场
	 * @param request
	 * @param countermanCareerId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCountermanCareer", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> deleteCountermanCareer(HttpServletRequest request,
			@Required @Regex("\\d+") String countermanCareerId) throws Exception {
		return countermanCareerService.deleteCountermanCareerById(countermanCareerId);
	}
}
