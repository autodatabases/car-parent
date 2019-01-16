package com.emate.shop.admin.controller;

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
import com.emate.shop.web.aop.AuthAction;

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
	 * 根据品牌查询
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
    public DatasetList<Autopose> queryAutoByKeyword(HttpServletRequest request,String keyword) throws UnsupportedEncodingException {
    	keyword = URLDecoder.decode(keyword,"UTF-8");
    	return brandService.queryAotoPoseByKeyword(keyword);
    }
   
}
