package com.emate.shop.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CarWashConfigService;
import com.emate.shop.business.model.CarwashConfig;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 洗车配置渠道接口
 * @author dong
 *
 */
@Controller
@RequestMapping("carwashConfig")
public class CarWashConfigController implements AuthUtil{
   

	private CarWashConfigService carWashConfigService;
	
	@RemoteService
	public void setCarWashConfigService(CarWashConfigService carWashConfigService){
		this.carWashConfigService = carWashConfigService;
	}
	/**
	 * admin查询洗车配置渠道
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param carwashConfig
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryWashConfigList")
    @AuthAction
    public DatasetList<CarwashConfig> queryWashConfigList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CarwashConfig carwashConfig) {
    	return carWashConfigService.adminQueryCarWashConfigList(Integer.valueOf(pageNo),Integer.valueOf(pageSize), carwashConfig);
    }
    /**
     * 根据城市名称查询洗车配置渠道
     * @param request
     * @param cityName
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/getWashConfig", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetList<CarwashConfig> getCarwashConfig(HttpServletRequest request,
			@Required String cityName) throws Exception {
		return carWashConfigService.queryCarWashConfig(cityName);
	}
	/**
	 * 添加或修改洗车配置渠道
	 * @param request
	 * @param carwashConfigs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateWashConfig", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addOrUpdateCarwashConfig(HttpServletRequest request,
			@RequestBody List<CarwashConfig> carwashConfigs) throws Exception {
		return carWashConfigService.addOrUpdateCarWashConfig(carwashConfigs);
	}
	/**
	 * 根据城市删除洗车配置渠道
	 * @param request
	 * @param cityName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delWashConfig", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> delCarwashConfig(HttpServletRequest request,
			@Required String cityName) throws Exception {
		return carWashConfigService.delCarwashConfig(cityName);
	}

}
