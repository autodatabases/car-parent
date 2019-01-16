package com.emate.shop.h5.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.SurveyRecordService;
import com.emate.shop.business.model.SurveyRecord;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;


@Controller
@RequestMapping(value="surveyrecord")
public class SurveyRecordController implements AuthUtil{
	
	private SurveyRecordService surveyRecordService;
	
	@RemoteService
	public void setSurveyRecordService(SurveyRecordService surveyRecordService) {
		this.surveyRecordService = surveyRecordService;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/takenode.html",method=RequestMethod.GET)
	public String addRecord(HttpServletRequest request){
		return "takenode/takenode";
	}
	/**
	 * 保存查勘录入信息
	 * @param request
	 * @param response
	 * @param surveyRecord
	 * @return
	 */
	@RequestMapping(value = "/addrecord",method=RequestMethod.POST)
	@ResponseBody
	public DatasetSimple<Boolean> addSurveyRecord(HttpServletRequest request,
			HttpServletResponse response,SurveyRecord surveyRecord){
		return surveyRecordService.addRecord(surveyRecord);
	}
}
