package com.emate.shop.admin.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.OilRecordWhitelistService;
import com.emate.shop.business.model.OilRecordwhiteList;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.web.aop.AuthAction;
import com.emate.shop.web.validator.Length;
import com.emate.shop.web.validator.Min;
import com.emate.shop.web.validator.Mobile;
import com.emate.shop.web.validator.Regex;
import com.emate.shop.web.validator.Required;

/**
 * 
 * @file RecordWhitelistController.java
 * @author tangweiming
 * @mail weiming.tang@emateglobal.com
 * @since 2018年5月14日
 */
@Controller
@RequestMapping("/oilrecordwhitelist")
public class OilRecordWhitelistController {

    private OilRecordWhitelistService oilRecordWhitelistService;

    @RemoteService
    public void setOilRecordWhitelistService(
            OilRecordWhitelistService oilRecordWhitelistService) {
        this.oilRecordWhitelistService = oilRecordWhitelistService;
    }

    /**
     * 白名单设置
     * @param request
     * @param userAccount
     * @param openQuota
     * @param remark
     * @return
     */
    @AuthAction
    @RequestMapping(value = "/addOilRecordWhiteList", method = RequestMethod.POST)
    @ResponseBody
    public DatasetSimple<Boolean> addOilRecordWhiteList(
            HttpServletRequest request, @Required @Mobile String userAccount,
            @Required BigDecimal openQuota,
            @Length(min = 0, max = 50) String remark) {
        return oilRecordWhitelistService.addOilRecordWhiteList(userAccount,
                openQuota, remark);

    }

    /**
     * 根据用户账号查询出列表信息
     * @param request
     * @param userAccount
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AuthAction
    @ResponseBody
    @RequestMapping("/queryOilRecordWhitelist")
    public DatasetList<OilRecordwhiteList> queryOilRecordWhitelist(
            HttpServletRequest request,  String userAccount,
            @Regex("\\d+") String pageNo, @Regex("\\d+") String pageSize) {
        return oilRecordWhitelistService.queryOilRecordWhitelist(userAccount,
                Integer.valueOf(pageNo), Integer.valueOf(pageSize));
    }

    /**
     * 恢复额度
     * @param request
     * @param response
     * @param id
     * @return
     */
    @AuthAction
    @RequestMapping("/changeOpenQuota")
    @ResponseBody
    public DatasetSimple<Integer> changeOpenQuota(HttpServletRequest request,
            HttpServletResponse response, @Required @Min(1) Long id) {
        return oilRecordWhitelistService.updateOpenQuota(id);
    }

}
