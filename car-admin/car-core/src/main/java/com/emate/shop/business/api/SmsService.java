package com.emate.shop.business.api;

import com.emate.shop.rpc.dto.DatasetSimple;

public interface SmsService {
	
	public static Integer SMS_TYPE_0 = 0;//表示正常惠+车服短信
	
	public static Integer SMS_TYPE_1= 1;//表示油卡短信
	
	public DatasetSimple<String> sendSmsCode(String mobile,Integer smsType);
	
	public DatasetSimple<String> sendSmsTmp(String templateid, String mobiles, String params,Integer smsType);

}
