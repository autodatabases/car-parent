/**
 * 
 */
package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.UserService;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.common.IPUtil;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Required;


/**
 * 登录
 * @author likk
 *
 */
@Controller
@RequestMapping("auth")
public class AuthController implements AuthUtil{


	private UserService userService;
	
	@RemoteService
	public void setUserService(UserService userService){
		this.userService = userService;
	}

    /**
     * 管理员登录
     * @param request
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping("login")
    public DatasetSimple<String> adminLogin(HttpServletRequest request,HttpServletResponse resp, @Required String userName,@Required String password) {
    	DatasetSimple<SystemUser> data = userService.adminLogin(userName, password,IPUtil.getIP(request));
    	if(!data.isSuccess()){ //登录失败
    		return DatasetBuilder.fromMessageSimple(data.getMessage());
    	}
    	SystemUser user = data.getData();
    	return DatasetBuilder.fromDataSimple(getToken(user.getId()));
    }
}
