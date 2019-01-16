/**
 * @file OilCardStatusCheckQuartz.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月15日 
 */
package com.emate.shop.timer.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emate.shop.business.api.OilUserService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;

/**
 * 检查更新油卡状态
 * @file OilCardStatusCheckQuartz.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月15日 
 */
@Component
public class OilCardStatusCheckQuartz {

    private OilUserService oilUserService;

    @RemoteService
    public void setOilUserService(OilUserService oilUserService) {
        this.oilUserService = oilUserService;
    }

    /**
     * 油卡定时过期
     * @param
     * @return
     */
    //定时规则 每天上午五点
    @Scheduled(cron = "0 0 5 * * ?")
    public void checkOilCardStatus() {
        Log4jHelper.getLogger().info(
                "==========Begin check oid card status for send every 24h>>>>>>>>>");
        oilUserService.checkOilCardStatus();
        Log4jHelper.getLogger().info(
                "==========after check oid card status for send <<<<<<<<<");
    }
}
