package com.emate.shop.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AutoPartService;
import com.emate.shop.business.model.AutoParts;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

@Controller
@RequestMapping(value="autopart")
public class AutoPartController implements AuthUtil{
	
	private AutoPartService autoPartService;
	
	@RemoteService
	public void setAutoPartService(AutoPartService autoPartService) {
		this.autoPartService = autoPartService;
	}

	
	/**
	 * 配件列表
	 * @param request
	 * @return 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/autoPartList")
	@ResponseBody
	@AuthAction
	public DatasetList<AutoParts> autoPartList(HttpServletRequest request,
			 AutoParts autoPart,Integer pageNo,Integer pageSize) throws UnsupportedEncodingException{
		return autoPartService.autoPartList(autoPart, pageNo, pageSize);
	}

	/**
	 * 添加配件信息
	 * @param request
	 * @return 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/addAutoPart")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> addAddress(HttpServletRequest request,
			 AutoParts autoPart) throws UnsupportedEncodingException{
		return autoPartService.addAutoPart(autoPart);
	}
	
	/**
	 * 删除配件信息
	 * @param request
	 * @return 
	 * @return
	 * @throws  
	 */
	@RequestMapping(value = "/deleteAutoPart")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> deleteAutoPart(HttpServletRequest request,
			 Long autoPartId) throws UnsupportedEncodingException{
		return autoPartService.delteAutoPart(autoPartId);
	}
	
	
	/**
	 * 新增关联关系
	 * @param request
	 * @return 
	 * @return
	 * @throws  
	 */
	@RequestMapping(value = "/matchAutoParts")
	@ResponseBody
	@AuthAction
	public DatasetSimple<Map<String,String>> matchAutoParts(HttpServletRequest request,
			 @Required @Regex("\\d+")String autoId,
			 @Required @Regex("\\d+")String autoPartId,
			 @Required @Regex("\\d+")String type) throws UnsupportedEncodingException{
		return autoPartService.matchAutoParts(Long.parseLong(autoId),Long.parseLong(autoPartId),Integer.parseInt(type));
	}
}
