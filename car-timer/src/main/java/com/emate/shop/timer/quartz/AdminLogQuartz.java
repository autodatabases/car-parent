package com.emate.shop.timer.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emate.shop.business.api.AdminLogService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;

/**
 * @file CleanTimerController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月29日
 */
@Component
public class AdminLogQuartz {
	   private AdminLogService adminLogService;
	   
	   @RemoteService
	   public void setAdminLogService(AdminLogService adminLogService) {
		   this.adminLogService = adminLogService;
	   }

	    @Scheduled(fixedDelay = 3*60L * 1000L)
	    public void sendSmS() {
	    	Log4jHelper.getLogger().info("-----------定时任务清理不活跃的admin用户-------------");
	    	Log4jHelper.getLogger().info("清理结果："+adminLogService.deleteUnActiveLog().getData());
	    }

}
