package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AddressService;
import com.emate.shop.business.model.Regions;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthAction;

/**
 * 
 * @author likk
 *
 */
@Controller
@RequestMapping("address")
public class AddressController {
    

	private AddressService addressService;
	
	@RemoteService
	public void setAddressService(AddressService addressService){
		this.addressService = addressService;
	}
	
	/**
	 * 查询所有省份
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryAllProvince")
    @AuthAction
    public DatasetList<Regions> queryAllProvince(HttpServletRequest request) {
    	return addressService.queryAllProvince();
    }
    
    /**
     * 根据省份查询城市列表
     * @param request
     * @param provinceId
     * @return
     */
    @ResponseBody
    @RequestMapping("queryCityByProvince")
    @AuthAction
    public DatasetList<Regions> queryAllCityByProvince(HttpServletRequest request,String provinceId) {
    	return addressService.queryAllCityByProvince(provinceId);
    }
    
    
    /**
     * 根据城市查询区域列表
     * @param request
     * @param cityId
     * @return
     */
    @ResponseBody
    @RequestMapping("queryAllAreaByCity")
    @AuthAction
    public DatasetList<Regions> queryAllAreaByCity(HttpServletRequest request,String cityId) {
    	return addressService.queryAllAreaByCity(cityId);
    }
   
}
