/**
 * 
 */
package com.emate.shop.timer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.business.api.Kuaidi100Service;
import com.emate.shop.business.vo.kuaidi100.Kuaidi100Response;
import com.emate.shop.business.vo.kuaidi100.NoticeRequestParam;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.shop.rpc.dto.RpcDtoHelper;

/**
 * @file Kuaidi100CallbackController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年8月5日
 */
@Controller
@RequestMapping("kuaidi100")
public class Kuaidi100CallbackController {

    private Kuaidi100Service kuaidi100Service;

    @RemoteService
    public void setKuaidi100Service(Kuaidi100Service kuaidi100Service) {
        this.kuaidi100Service = kuaidi100Service;
    }
    public static void main(String[] args) {
    	
		System.out.println(RpcDtoHelper.decrypt("MqJINe25ZAM="));
	}

    @ResponseBody
    @RequestMapping(path = "callback{id}")
    public Kuaidi100Response callback(HttpServletRequest request,
            @PathVariable String id, String param) {
    	String idlong = null;
    	try{
    		idlong = RpcDtoHelper.decrypt(id);
    	}catch(Exception e){
    		e.printStackTrace();
    		Log4jHelper.getLogger().error("解析订单失败id = " + id);
    		return getErrResponse("解析订单失败！");
    	}
    	if(idlong == null || !idlong.matches("\\d+")){
    		return getErrResponse("解析订单失败！");
    	}
    	Log4jHelper.getLogger().info("快递100回调执行 id = " + id + ";param = " + param);
        NoticeRequestParam noticeRequestParam = JacksonHelper.fromJSON(param,
                NoticeRequestParam.class);
        DatasetSimple<Kuaidi100Response>  result = this.kuaidi100Service.callback(Long.parseLong(idlong), noticeRequestParam);
        Log4jHelper.getLogger().info("快递100回调执行结果 :" + JacksonHelper.toJSON(result));
        if(result.isSuccess()){
        	   return result.getData();
        }
        return getErrResponse("解析订单失败！");
    }
    
    private Kuaidi100Response getErrResponse(String msg){
    	Kuaidi100Response resp = new Kuaidi100Response();
		resp.setMessage(msg);
		resp.setResult(false);
		resp.setReturnCode("-1");
		return resp;
    }
}
