package com.emate.shop.timer.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.emate.shop.business.api.OilMakeService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;


/**
 * 做卡接口
 * @author Administrator
 *
 */
@Component
public class OilMakeQuartz {
	
   private OilMakeService oilMakeService;

   @RemoteService
    public void setOilMakeService(OilMakeService oilMakeService) {
	   this.oilMakeService = oilMakeService;
   }
   
   
    /**
     * 更新做卡一级表
     */
    @Scheduled(fixedDelay = 10L * 60L * 1000L, initialDelay = 2L * 60L * 1000L)
    public void  updateOilMakeRecord(){
    	Log4jHelper.getLogger().info("==========Begin update oilMakeRecord for send every 10 minutes>>>>>>>>>");
    	oilMakeService.addOrder();
    	Log4jHelper.getLogger().info("==========after update oilMakeRecord for send <<<<<<<<<");
    }

    /**
     * 更新做卡二级表
     */
    @Scheduled(fixedDelay = 2L * 60L * 1000L, initialDelay = 6L * 60L * 1000L)
    public void  updateOilMakeOrder(){
    	Log4jHelper.getLogger().info("==========Begin update oilMakeOrder for send every 2 minutes>>>>>>>>>");
    	oilMakeService.addOilRechargeCode();
    	Log4jHelper.getLogger().info("==========after update oilMakeOrder for send <<<<<<<<<");
    }
}
