package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.ServiceOperatorLogService;
import com.emate.shop.business.model.ServiceOperatorLog;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping("servicelog")
public class ServiceOperatorLogController implements AuthUtil{
   

	private ServiceOperatorLogService serviceOperatorLogService;
	
	@RemoteService
	public void setServiceOperatorLogService(ServiceOperatorLogService serviceOperatorLogService) {
		this.serviceOperatorLogService = serviceOperatorLogService;
	}
	
    @ResponseBody
    @RequestMapping("queryServiceLogList")
    @AuthAction
    public DatasetList<ServiceOperatorLog> queryServiceLogList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,
    		String num,String serviceType,String verifyStatus,String chePai) {
    	return serviceOperatorLogService.adminServiceLogList(Integer.valueOf(pageNo), 
    			Integer.valueOf(pageSize),num,serviceType,verifyStatus,chePai);
    }
    
    @ResponseBody
    @RequestMapping("checklog")
    @AuthAction
    public DatasetSimple<String> checkLog(HttpServletRequest request,
    		String id,String verifyStatus) {
    	return serviceOperatorLogService.checkLog(Long.valueOf(id),this.getUserId(request, CAR_ADMIN_TOKEN),verifyStatus);
    }
    
    /**
     * 更新批量备注
     * @param request
     * @return
     */
    @RequestMapping(value="updateremark",method=RequestMethod.POST)
    @ResponseBody
    @AuthAction
    public DatasetSimple<String> updateBatchRemark(HttpServletRequest request,ServiceOperatorLog serviceOperatorLog) {
        return serviceOperatorLogService.updateRemark(serviceOperatorLog.getRemark(), serviceOperatorLog.getId());
    }
}
