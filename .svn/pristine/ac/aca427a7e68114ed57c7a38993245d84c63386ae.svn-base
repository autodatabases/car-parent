package com.emate.shop.timer.quartz;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.api.UserService;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import net.sf.json.JSONArray;


/**
 * 
 * @author dong
 *
 */
@Component
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class UserInfoQuartz {

   private UserService userService;

   @RemoteService
    public void setUserService(UserService userService) {
	   this.userService = userService;
   }
   
   private SmsService smsService;
   

   @RemoteService
   public void setSmsService(SmsService smsService) {
	   this.smsService = smsService;
   }
   /**
    * 保单离到期30天时,短信通知保单用户
    */
	@Scheduled(cron = "0 0 1 1/1 * ?")//
    public void sendMessageForExpiredUser() {
    	Log4jHelper.getLogger().info("==========Begin do send message for expired user fixedDelay:1 day>>>>>>>>>");
    	DatasetList<Map<String, String>> result = userService.queryExpiredUserInfo();
    	if(!result.isSuccess()){
    		Log4jHelper.getLogger().error("查询需要发短信的失败！错误消息："+result.getMessage());
    	}else{
    		List<Map<String, String>> userInfoList = result.getList();
    		if(userInfoList.isEmpty()){
    			Log4jHelper.getLogger().error("本次执行无数据！");
    		}else{
    			Log4jHelper.getLogger().error("本次发送短信条数："+userInfoList.size());
    			for(Map<String,String> userInfo:userInfoList){
    				JSONArray mobiles  = new  JSONArray();
					mobiles.add(userInfo.get("phone"));// 用户手机号
					JSONArray params = new JSONArray();
					params.add(userInfo.get("license"));
					params.add(userInfo.get("endTime"));
					DatasetSimple<String>  smsResult = smsService.sendSmsTmp("3882051", mobiles.toString(), params.toString(), SmsService.SMS_TYPE_0);
					@SuppressWarnings("unchecked")
					Map<String,Object> map = JacksonHelper.fromJSON(smsResult.getData(), Map.class);
				    if((int)map.get("code") == 200){
				        Log4jHelper.getLogger().info("短信发送成功:"+userInfo.get("phone"));
				    }else{
				        Log4jHelper.getLogger().info("短信发送失败:"+userInfo.get("phone"));
				   }
    			}
    		}
    	}
    	Log4jHelper.getLogger().info("==========End do send message for expired user fixedDelay:1 day<<<<<<<<<");
    }
}
