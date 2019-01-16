package com.emate.shop.admin.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.FeedbackService;
import com.emate.shop.business.api.ImportUserInfoService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 
 * @author likk
 *
 */
@Controller
@RequestMapping("user")
public class UserController implements AuthUtil{

	private UserService userService;
	
	private FeedbackService feedBackService;
	
	private ImportUserInfoService importUserInfoService;

	@RemoteService
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RemoteService
	public void setFeedBackService(FeedbackService feedBackService) {
		this.feedBackService = feedBackService;
	}
	
	@RemoteService
	public void setImportUserInfoService(ImportUserInfoService importUserInfoService) {
		this.importUserInfoService = importUserInfoService;
	}

	/**
	 * 查询所有用户
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryUserList")
    @AuthAction
    public DatasetList<Map<String,String>> queryUserList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,User user) {
    	return userService.userList(user, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
    }
    
    
	/**
	 * 编辑用户
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryUserForEdit")
    @AuthAction
    public DatasetSimple<Map<String,String>> queryUserForEdit(HttpServletRequest request,
    		@Required @Regex("\\d+")String userId) {
    	return userService.queryUserForEdit(Long.parseLong(userId));
    }
    
    
	/**
	 * 新增or更新用户
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("createOrUpdateUserInfo")
    @AuthAction
    public DatasetSimple<Map<String,String>> createOrUpdateUserInfo(HttpServletRequest request,
    		User user,UserInfo userInfo,ImportUserInfo importUserInfo) {
    	return userService.createOrUpdateUserInfo(user,userInfo,importUserInfo);
    }
    
    
	/**
	 * 查询导入失败的用户列表
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("queryImportErrList")
    @AuthAction
    public DatasetList<Map<String,Object>> queryImportErrList(HttpServletRequest request,
    		@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,String flag,ImportUserInfo importUserInfo) {
    	return userService.queryImportErrList(Integer.parseInt(pageNo), Integer.parseInt(pageSize),flag,importUserInfo);
    }
    @ResponseBody
    @RequestMapping("userBackList")
    @AuthAction
    public DatasetList<Map<String,Object>> userBackList(HttpServletRequest request,@Required @Regex("\\d+")String pageNo,@Required @Regex("\\d+")String pageSize,String phone ,String startTimes,String endTimes){
    	
    	return feedBackService.queryFeedBack(Integer.valueOf(pageNo), Integer.valueOf(pageSize),phone,startTimes,endTimes);
    }
    /**
     * 为用户反馈添加备注
     * @param request
     * @param id
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatefeedback")
    @AuthAction
    public DatasetSimple<Map<String,String>> updateFeedback(HttpServletRequest request,String id,String remark){
    	
    	return feedBackService.updateFeedback(id, remark);
    }
	/**
	 * 查询商家信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMatchChe", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetList<ImportUserInfo> matchChe(HttpServletRequest request){
		return importUserInfoService.matchChe();
	}
	
	@RequestMapping(value = "/updateCarInfo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateImportUserInfo(HttpServletRequest request,
			ImportUserInfo userInfo,String phone) throws Exception {
		DatasetSimple<Boolean> result = userService.updateCarUserInfo(userInfo, phone);
		return result;
	}
	
	@RequestMapping(value = "/updateCarInfoTwo", method = RequestMethod.POST)
	@ResponseBody
	@AuthAction
	public DatasetSimple<Boolean> updateImportUserInfoTwo(HttpServletRequest request,
			String orderPrice,String signTime,String startTime,String endTime,
			String paymentTime,String infoId) throws Exception {
		DatasetSimple<Boolean> result = userService.updateCarUserInfoTwo(orderPrice, 
				signTime, startTime, endTime, paymentTime, infoId);
		return result;
	}
	
}
