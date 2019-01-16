
package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.RechargeAccountSwitchingService;
import com.emate.shop.business.model.OilProvider;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.shop.web.validator.Required;

/**
 * 惠+车服后台添加切换充值账号的相关需求
 * @file RechargeAccountSwitching.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月30日 
 */
@Controller
@RequestMapping("/accountSwitching")
public class RechargeAccountSwitchingController implements AuthUtil {

    private RechargeAccountSwitchingService rechargeAccountSwitchingService;

    @RemoteService
    public void setRechargeAccountSwitchingService(
            RechargeAccountSwitchingService rechargeAccountSwitchingService) {
        this.rechargeAccountSwitchingService = rechargeAccountSwitchingService;
    }

    @AuthAction
    @ResponseBody
    @RequestMapping(value = "/query")
    public DatasetSimple<OilProvider> queryOilRechargeAccount(
            HttpServletRequest request) {
        if (this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN) == null) {
            throw new BusinessException("请先登录");
        }

        return rechargeAccountSwitchingService.queryOilProvider();
    }

    @AuthAction
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DatasetSimple<OilProvider> updateOilRechargeAccount(
            HttpServletRequest request, @Required Long id) {
        if (this.getUserId(request, AuthUtil.CAR_ADMIN_TOKEN) == null) {
            throw new BusinessException("请先登录");
        }
        return rechargeAccountSwitchingService.updateOilProvider(id);
    }
}
