package com.emate.shop.h5.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AddressService;
import com.emate.shop.business.model.Regions;
import com.emate.shop.business.model.UserAddress;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;

@Controller
@RequestMapping(value="address")
public class AddressController implements AuthUtil{
	
	private AddressService addressService;
	
	@RemoteService
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	
	/**
	 * 跳转到选择地址页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/chooseCity")
	@AuthAction
	public String chooseCity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "city-GPS";
	}
	
	/**
	 * 跳转到选择地址页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addaddress.html")
	@AuthAction
	public String addAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "repair/add-address";
	}


	/**
	 * 筛选城市根据省份id gd = 20
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchByKeyWord")
	@ResponseBody
	public DatasetList<Regions> searchByKeyWord(HttpServletRequest request,
			String provinceId,
			String keyword) throws Exception {
		return addressService.searchByKeyWord(provinceId, keyword);
	}
	
	
	/**
	 * 查询所有省份
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryAllProvince")
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
    //@AuthAction
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
    //@AuthAction
    public DatasetList<Regions> queryAllAreaByCity(HttpServletRequest request,String cityId) {
    	return addressService.queryAllAreaByCity(cityId);
    }
    
    
	/**
	 * 添加用户收货地址
	 * @param request
	 * @return 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/addAddress")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addAddress(HttpServletRequest request,
			 UserAddress address) throws UnsupportedEncodingException{
		long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		address.setUserId(userId);
		address.setCreateTime(new Date());
		address.setProCityArea(URLDecoder.decode(address.getProCityArea(),"utf-8"));
		address.setDefaultAddress(true);
		address.setAddressContent(URLDecoder.decode(address.getAddressContent(),"utf-8"));
		return addressService.addUserAddress(address);
	}
	
    
	/**
	 * 查询用户收货地址
	 * @param request
	 * @return 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/queryAddress")
	@ResponseBody
	@AuthAction
	public DatasetSimple<UserAddress> queryAddress(HttpServletRequest request) throws UnsupportedEncodingException{
		long userId = this.getUserId(request,AuthUtil.CAR_H5_TOKEN);
		return addressService.queryUserAddress(userId);
	}
	

}
