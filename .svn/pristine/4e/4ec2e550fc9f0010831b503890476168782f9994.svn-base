package com.emate.shop.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AutoInfoService;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping("autoinfo")
public class AutoInfoController implements AuthUtil{
    

	private AutoInfoService autoInfoService;
	
	@RemoteService
	public void setAutoService(AutoInfoService autoInfoService) {
		this.autoInfoService = autoInfoService;
	}
	
	/**
	 * 查询所有车型
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param autoInfo
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryAutoInfoList")
    @AuthAction
    public DatasetList<Map<String,Object>> queryAutoList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,AutoInfo autoInfo) {
    	return autoInfoService.adminAutoInfoList(Integer.parseInt(pageNo),Integer.parseInt(pageSize),autoInfo);
    }
    /**
     * 删除该车型
     * @param request
     * @param autoinfoId
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteAutoInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> deleteAutoInfo(HttpServletRequest request,
			@Required @Regex("\\d+")String autoinfoId) throws Exception {
		return  autoInfoService.deleteAutoInfo(autoinfoId);
	}
	
	@RequestMapping(value = "/addAutoInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> addAutoInfo(HttpServletRequest request,
			HttpServletResponse response,AutoInfo autoInfo,@Required @Regex("\\d+")String oilAmount ) throws Exception {
		if(autoInfo.getOilAmount().doubleValue()==0){
			throw new BusinessException("机油数量不能为零");
		}
		return autoInfoService.addAutoInfo(autoInfo);
	}
	
    @ResponseBody
    @RequestMapping("findAutoInfo")
    @AuthAction
    public DatasetSimple<AutoInfo> queryAutoInfo(HttpServletRequest request,
    		@Required @Regex("\\d+")String autoInfoId) {
    	return autoInfoService.queryAutoInfoById(autoInfoId);
    }
    
    @ResponseBody
    @RequestMapping("queryAutoInfoByCondition")
    @AuthAction
    public DatasetSimple<Autopose> queryAutoPoseByCondition(HttpServletRequest request,String carType) {
    	return autoInfoService.queryAutoPoseByCondition(carType);
    }
}
