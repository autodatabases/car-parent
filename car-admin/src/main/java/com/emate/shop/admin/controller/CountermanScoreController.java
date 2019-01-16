package com.emate.shop.admin.controller;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emate.shop.business.api.CountermanScoreService;
import com.emate.shop.business.model.CountermanScore;
import com.emate.shop.business.model.CountermanScoreRecord;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;*/
import com.emate.shop.web.aop.AuthUtil;
/*import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;*/


/**
 * 积分
 * @author dong
 *
 */
@Controller
@RequestMapping("countermanScore")
public class CountermanScoreController implements AuthUtil{
   

/*	private CountermanScoreService countermanScoreService;
	
	@RemoteService
	public void setCountermanScoreService(CountermanScoreService countermanScoreService){
		this.countermanScoreService = countermanScoreService;
	}*/
/*	*//**
	 * 后台查询所有积分情况
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param countermanScore
	 * @return
	 *//*
    @ResponseBody
    @RequestMapping("/queryCountermanScoreList")
    @AuthAction
    public DatasetList<CountermanScore> queryCountermanList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CountermanScore countermanScore) {
    	return countermanScoreService.adminCountermanScoreList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), countermanScore);
    }*/
    /**
     * 根据id查询某个积分记录
     * @param request
     * @param countermanScoreId
     * @return
     * @throws Exception
     */
/*	@RequestMapping(value = "/getCountermanScore", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<CountermanScore> getCountermanScore(HttpServletRequest request,
			@Required @Regex("\\d+")String countermanScoreId) throws Exception {
		DatasetSimple<CountermanScore> countermanScore = countermanScoreService.queryCountermanScoreById(countermanScoreId);
		return countermanScore;
	}*/
	/**
	 * 添加或更新积分
	 * @param request
	 * @param countermanScore
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value = "/addOrUpdateCountermanScore", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> addCountermanScore(HttpServletRequest request,
			@Required CountermanScore countermanScore,String remark) throws Exception {
		return countermanScoreService.addOrUpdateCountermanScore(countermanScore,remark,this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN));
	}*/
	
	/**
	 * 后台查询积分记录
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param countermanScoreRecord
	 * @return
	 */
/*    @ResponseBody
    @RequestMapping("/queryCountermanScoreRecordList")
    @AuthAction
    public DatasetList<CountermanScoreRecord> queryCountermanScoreRecordList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,CountermanScoreRecord countermanScoreRecord) {
    	DatasetList<CountermanScoreRecord> adminCountermanScoreRecordList = countermanScoreService.adminCountermanScoreRecordList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), countermanScoreRecord);
    	adminCountermanScoreRecordList.putDataset("countermanScore", countermanScoreService.queryCountermanScoreByCode(countermanScoreRecord.getCountermanCode()));
    	return adminCountermanScoreRecordList;
    }*/
}
