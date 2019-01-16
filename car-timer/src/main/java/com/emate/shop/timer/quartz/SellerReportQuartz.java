package com.emate.shop.timer.quartz;

import java.util.Map;



import javax.annotation.Resource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emate.shop.business.api.OrderService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * @file CleanTimerController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月29日
 */
@Component
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class SellerReportQuartz {

   private OrderService orderService;

   @RemoteService
    public void setOrderService(OrderService orderService) {
	   this.orderService = orderService;
   }

	@Resource
    private Environment env;

    //    @Scheduled(cron = "0/5 * * * * ?")
    //    public void myTest() {
    //        Log4jHelper.getLogger().info("0/5 * * * * ?");
    //    }
    //
    //    @Scheduled(fixedDelay = 10L * 1000L)
    //    public void myTest1() {
    //        Log4jHelper.getLogger().info("fixedDelay=10000");
    //    }

	/**
	 * 每个月1号 定时自动帮商家确认订单
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
    public void sellerReportConfirm() {
    	Log4jHelper.getLogger().info("==========Begin do sellerReportConfirm fixedDelay:1 month>>>>>>>>>");
    	DatasetSimple<Map<String, String>>  result = orderService.sellerComfirmReportQuartz();
    	Log4jHelper.getLogger().info("成功："+result.getData().get("success"));
    	Log4jHelper.getLogger().info("失败："+result.getData().get("fail"));
    	Log4jHelper.getLogger().info("==========End do sellerReportConfirm fixedDelay:1 month<<<<<<<<<");
    }
    

}
