package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.CountermanLifeCareerService;
import com.emate.shop.business.model.CountermanLifeCareer;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;

@Controller
@RequestMapping("countermanLifeCareer")
public class CountermanLifeCareerController implements AuthUtil{
   

	private CountermanLifeCareerService countermanLifeCareerService;
	
	@RemoteService
	public void setCountermanLifeCareerService(CountermanLifeCareerService countermanLifeCareerService){
		this.countermanLifeCareerService = countermanLifeCareerService;
	}
	
    @ResponseBody
    @RequestMapping("queryAllCountermanLifeCareer")
    @AuthAction
    public DatasetList<CountermanLifeCareer> queryAllCountermanLifeCareerList(HttpServletRequest request) {
    	return countermanLifeCareerService.queryAllCountermanLifeCareer();
    }
}
