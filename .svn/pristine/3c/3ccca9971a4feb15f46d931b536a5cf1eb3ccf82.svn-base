package com.emate.shop.h5.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.SellerService;
import com.emate.shop.business.model.Seller;
import com.emate.shop.common.MapDistance;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping(value="seller")
public class SellerController implements AuthUtil{
	
	private SellerService sellerService;
	
	@RemoteService
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	/**
	 * 跳转到商家详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dot-detail")
	@AuthAction
	public String dotDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "dot/dot-details";
	}
	
	/**
	 * 获取商家列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getList")
	@AuthAction
	@ResponseBody
	public DatasetList<Map<String,Object>> getList(HttpServletRequest request,
			HttpServletResponse response, String latStr, 
			String lngStr,
			String province,
			String city,
			String area,
			String orderType,
			String isFixSeller,
			String sellerType,
			Integer pageNo,
			Integer pageSize,
			@Required @Regex("\\d+")String type) throws Exception {
		Long userId = getUserId(request,AuthUtil.CAR_H5_TOKEN);
		DatasetList<Seller>  list = sellerService.h5SellerList(userId,type,province,city,area,sellerType,request.getParameter("sellerName"),pageNo,pageSize,isFixSeller);
		
		//如果前端传过来的是地理位置，那么需要解析地理位置
		if(StringUtils.isNotEmpty(latStr) && StringUtils.isNotEmpty(lngStr)){
			for(Seller s:list.getList()){
				if(StringUtils.isNotEmpty(s.getSellerPostion())){
					String[] postion = s.getSellerPostion().split("_");
					s.setSellerDes(MapDistance.distance(
							Double.parseDouble(latStr), Double.parseDouble(lngStr), Double.parseDouble(postion[0]), Double.parseDouble(postion[1])));
				}else{
					s.setSellerDes("0");
				}
			}
		}
		if("score".equals(orderType)){//按照平分来排序
			Collections.sort(list.getList(),new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					if(Float.parseFloat(((Seller)o1).getScoreService()) == Float.parseFloat(((Seller)o2).getScoreService())){
						return 0;
					}
					return  Float.parseFloat(((Seller)o1).getScoreService()) < Float.parseFloat(((Seller)o2).getScoreService())?1:-1;
				}
			});
				
		}else if("distance".equals(orderType))//按照距离排序
		{
			if(StringUtils.isNotEmpty(latStr) && StringUtils.isNotEmpty(lngStr)){
				Collections.sort(list.getList(),new Comparator(){
					@Override
					public int compare(Object o1, Object o2) {
						if(Float.parseFloat(((Seller)o1).getSellerDes()) == Float.parseFloat(((Seller)o2).getSellerDes())){
							return 0;
						}
						return Float.parseFloat(((Seller)o1).getSellerDes()) > Float.parseFloat(((Seller)o2).getSellerDes())?1:-1;
					}
				});
			}
		}
		//屏蔽无用字段
		List<Map<String, Object>> arrayList = new ArrayList<Map<String,Object>>();
		list.getList().forEach(s->{
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id", s.getId());
			map.put("sellerDes", s.getSellerDes());
			map.put("sellerLogo", s.getSellerLogo());
			map.put("sellerGrade", s.getSellerGrade());
			map.put("sellerName", s.getSellerName());
			map.put("orderCountOver", s.getOrderCountOver());
			map.put("scoreService", s.getScoreService());
			map.put("addressDetail", s.getAddressDetail());
			arrayList.add(map);
		});
		return DatasetBuilder.fromDataList(arrayList,list.getPageInfo());
	}

	/**
	 * 获取商家详细信息 根据id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSeller")
	@AuthAction
	@ResponseBody
	public DatasetSimple<Map<String,Object>> getSeller(HttpServletRequest request,
			HttpServletResponse response, @Required @Regex("\\d+")String sellerId) throws Exception {
		return sellerService.h5SelerDetail(Long.parseLong(sellerId));
	}
}
