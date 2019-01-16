package com.emate.shop.admin.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CarWashProductService;
import com.emate.shop.business.model.CarWashProduct;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping("washproduct")
public class CarWashProductController implements AuthUtil{
   

	private CarWashProductService carWashProductService;
	
	@RemoteService
	public void setCarWashProductService(CarWashProductService carWashProductService){
		this.carWashProductService = carWashProductService;
	}
	/**
	 * 查询洗车商品
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param carWashProduct
	 * @return
	 */
    @ResponseBody
    @RequestMapping("productlist")
    @AuthAction
    public DatasetList<CarWashProduct> productList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CarWashProduct carWashProduct) {
    	return carWashProductService.adminProductList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), carWashProduct);
    }
    
    /**
     * 根据id查询洗车商品
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/getproduct", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<CarWashProduct> getCounterman(HttpServletRequest request,
			@Required @Regex("\\d+")String id) throws Exception {
		return carWashProductService.queryProductById(id);
	}

	/**
	 * 编辑洗车商品
	 * @param request
	 * @param carWashProduct
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editproduct", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> editProduct(HttpServletRequest request,
			CarWashProduct carWashProduct) throws Exception {
		return carWashProductService.editProduct(carWashProduct);
	}
	
	/**
	 * 删除洗车商品
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delproduct", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> delProduct(HttpServletRequest request,
			String id) throws Exception {
		return carWashProductService.delProduct(id);
	}
	
	/**
	 * 查询购买次数订单
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param orderNo
	 * @param status
	 * @param userPhone
	 * @return
	 */
    @ResponseBody
    @RequestMapping("paylist")
    @AuthAction
    public DatasetList<Map<String,Object>> payList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,String orderNo,String status,String userPhone) {
    	return carWashProductService.payList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), userPhone, orderNo, status,null);
    }
    
	/**
	 * 查询洗车劵订单
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param userPhone
	 * @param orderNo
	 * @param shopName
	 * @param orderCode
	 * @return
	 */
    @ResponseBody
    @RequestMapping("shenglist")
    @AuthAction
    public DatasetList<CarWashSheng> shengList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,
    		String userPhone,String orderNo,String shopName,String orderCode) {
    	return carWashProductService.shengList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), userPhone, orderNo, shopName, orderCode,null);
    }
}
