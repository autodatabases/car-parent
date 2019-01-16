
package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.AuthCodeCheckService;
import com.emate.shop.business.model.AuthCodeVo;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Required;
import com.emate.shop.web.validator.ValiParamName;

/**
 * 惠+车服后台添加查看验证码的相关需求 
 * @file AuthCodeCheckController.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月29日 
 */
@Controller
@RequestMapping("/authCode")
public class AuthCodeCheckController implements AuthUtil {

    private AuthCodeCheckService authCodeCheckService;

    @RemoteService
    public void setAuthCodeCheckService(
            AuthCodeCheckService authCodeCheckService) {
        this.authCodeCheckService = authCodeCheckService;
    }

    /**
     * 查看验证码发送列表页面
     * @param
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/check")
    @ResponseBody
    public DatasetList<AuthCodeVo> authCodeCheck(HttpServletRequest request,
            HttpServletResponse response,
            @ValiParamName("联系电话[userPhone]") String userPhone,
            @Required Integer pageNo, @Required Integer pageSize) {
        //输入的手机号长度，字符，格式等不对时点击搜索则提示“请输入正确的手机号”。
        //若输入的手机号并没有发送过验证码信息，且没有登录过系统则提示“信息不存在。
        if (this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN) == null) {
            throw new BusinessException("信息不存在!");
        }
        //@ValiParamName("联系电话[userPhone]")
        return authCodeCheckService.authCodeCheck(userPhone, pageNo, pageSize);
    }

}
