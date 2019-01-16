package com.emate.shop.h5.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.BrandService;
import com.emate.shop.business.model.Autobrand;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 
 * @author likk
 *
 */
@Controller
@RequestMapping("brand")
public class BrandController {
     

	private BrandService brandService;
	
	@RemoteService
	public void setBrandService(BrandService brandService){
		this.brandService = brandService;
	}
	
	/**
	 * 跳转到car1
	 * @param request
	 * @return
	 */
    @RequestMapping("car1.html")
    @AuthAction
    public String carbrand(HttpServletRequest request) {
    	return "car/car1";
    }
    
	/**
	 * 跳转到car2
	 * @param request
	 * @return
	 */
    @RequestMapping("car2.html")
    @AuthAction
    public String car2(HttpServletRequest request) {
    	return "car/car2";
    }
    
	/**
	 * 跳转到car2
	 * @param request
	 * @return
	 */
    @RequestMapping("car3.html")
    @AuthAction
    public String car3(HttpServletRequest request) {
    	return "car/car3";
    }
    
	/**
	 * 跳转到car2
	 * @param request
	 * @return
	 */
    @RequestMapping("car4.html")
    @AuthAction
    public String car4(HttpServletRequest request) {
    	return "car/car4";
    }
	
	
	/**
	 * 查询所有品牌
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryBrandList")
    @AuthAction
    public DatasetList<Autobrand> queryBrandList(HttpServletRequest request) {
    	return brandService.queryBrandList();
    }
    
	
	/**
	 *  
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @ResponseBody
    @RequestMapping("queryAutoByBrand")
    @AuthAction
    public DatasetList<Autopose> queryAutoByBrand(HttpServletRequest request,String brandName) throws UnsupportedEncodingException {
    	brandName = URLDecoder.decode(brandName,"UTF-8");
    	return brandService.queryAutoPoseByBrand(brandName);
    }
    
	/**
	 * 根据关键字查询
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @ResponseBody
    @RequestMapping("queryAutoByKeyword")
    @AuthAction
    public DatasetList<Autopose> queryAutoByKeyword(HttpServletRequest request,
    		@Required String keyword) throws UnsupportedEncodingException {
    	keyword = URLDecoder.decode(keyword,"UTF-8");
    	return brandService.queryAotoPoseByKeyword(keyword);
    }
    
    
	/**
	 * 根据关键字查询
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @ResponseBody
    @RequestMapping("queryAutoById")
    @AuthAction
    public DatasetSimple<Autopose> queryAutoById(HttpServletRequest request,
    		@Required @Regex(value="\\d+") String id) throws UnsupportedEncodingException {
    	return brandService.queryAutoposeById(Long.parseLong(id));
    }
   
}
