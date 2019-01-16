package com.emate.shop.timer.quartz;
/*
import java.util.Map;

import javax.annotation.Resource;*/
import org.springframework.context.annotation.PropertySource;
/*import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;*/
import org.springframework.stereotype.Component;
/*import com.emate.shop.business.api.CountermanScoreService;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;*/

/**
 * 
 * @author dong
 *
 */
@Component
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class CountermanQuartz {

/*   private CountermanScoreService countermanScoreService;

   @RemoteService
    public void setCountermanScoreService(CountermanScoreService countermanScoreService) {
	   this.countermanScoreService = countermanScoreService;
   }

	@Resource
    private Environment env;

	*//**
	 * 每个季度5号 业务员积分定时自动清空
	 *//*
	@Scheduled(cron = "0 0 0 5 1,4,7,10 ? ")
    public void delCountermanScore() {
    	Log4jHelper.getLogger().info("==========Begin do delCountermanScore fixedDelay:1 month>>>>>>>>>");
    	DatasetSimple<Map<String, String>>  result = countermanScoreService.delCountermanScoreQuartz();
    	Log4jHelper.getLogger().info("成功："+result.getData().get("success"));
    	Log4jHelper.getLogger().info("失败："+result.getData().get("fail"));
    	Log4jHelper.getLogger().info("==========End do delCountermanScore fixedDelay:1 month<<<<<<<<<");
    }*/
}
