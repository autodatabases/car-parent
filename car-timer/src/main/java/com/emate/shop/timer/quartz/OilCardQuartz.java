package com.emate.shop.timer.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.emate.shop.business.api.OilUserService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;


/**
 * 油卡订单查询
 * @author 董夫行
 *
 */
@Component
public class OilCardQuartz {
	
   private OilUserService oilUserService;

   @RemoteService
    public void setOilUserService(OilUserService oilUserService) {
	   this.oilUserService = oilUserService;
   }
    
    /**
     * 欧飞订单查询
     */
    @Scheduled(fixedDelay = 10L * 60L * 1000L, initialDelay = 4L * 60L * 1000L)
    public void  updateGyPhoneResultByOf(){
    	Log4jHelper.getLogger().info("==========Begin update oilOrders for send every 10 minutes by oufei>>>>>>>>>");
    	
    	oilUserService.updateOilOrdersByOfTimer();
    	
    	Log4jHelper.getLogger().info("==========after update oilOrders for send  by oufei<<<<<<<<<");
    }
}
