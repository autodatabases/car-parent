package com.emate.shop.admin.controller;



import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.OilLogService;
import com.emate.shop.business.model.OilLog;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 第三方请求响应日志信息
 * @author 董夫行
 *
 */
@Controller
@RequestMapping("oillog")
public class OilLogController implements AuthUtil{
   

	private OilLogService oilLogService;
	
	@RemoteService
	public void setOilLogService(OilLogService oilLogService){
		this.oilLogService = oilLogService;
	}
	/**
	 * 查询第三方日志
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param orderNo
	 * @return
	 */
    @ResponseBody
    @RequestMapping("oilloglist")
    @AuthAction
    public DatasetList<OilLog> queryWashConfigList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,
    		@Required @Regex("\\d+")String pageSize,
    		String orderNo) {
    	return oilLogService.oilLogList(Integer.valueOf(pageNo),Integer.valueOf(pageSize), orderNo);
    }
}
