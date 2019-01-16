package com.emate.shop.timer.quartz;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emate.shop.business.api.Kuaidi100Service;
import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.vo.kuaidi100.Kuaidi100Request;
import com.emate.shop.business.vo.kuaidi100.Kuaidi100Response;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.rpc.aop.RemoteService;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.RpcDtoHelper;

/**
 * @file CleanTimerController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月29日
 */
@Component
@PropertySource(value = "classpath:web.properties", ignoreResourceNotFound = true)
public class ExpressQuartz {

    private Kuaidi100Service kuaidi100Service;

    /**
     * @param dataCleanService the dataCleanService to set
     */
    @RemoteService
    public void setKuaidi100Service(Kuaidi100Service kuaidi100Service) {
        this.kuaidi100Service = kuaidi100Service;
    }

    @Resource
    private Environment environment;

    //    @Scheduled(cron = "0/5 * * * * ?")
    //    public void myTest() {
    //        Log4jHelper.getLogger().info("0/5 * * * * ?");
    //    }
    //
    //    @Scheduled(fixedDelay = 10L * 1000L)
    //    public void myTest1() {
    //        Log4jHelper.getLogger().info("fixedDelay=10000");
    //    }

    //@Scheduled(fixedDelay = 30L * 60L * 1000L, initialDelay = 1 * 30L * 1000L)
    public void pollJob() {
    	
       Log4jHelper.getLogger().info("ExpressJob begin poll express.");
       // 未订阅状态的进行订阅
       DatasetList<OrderExpress> datasetlist = kuaidi100Service.queryForPoll();
       if(!datasetlist.isSuccess()){
    	   Log4jHelper.getLogger().error("kuaidi100Service exception.no data fond."+datasetlist.getMessage());
    	   return;
       }
       if(Objects.isNull(datasetlist.getList()) || datasetlist.getList().size()<=0){
    	   Log4jHelper.getLogger().error("no data fond for this job task.");
    	   return;
       }
       List<OrderExpress> list = datasetlist.getList();
       int successCount = 0;
       for (OrderExpress orderExpress : list) {
    	   //关于快递table对订单的校验暂时注释掉
//           if (OrderExpress.getOrderId() == null) {
//               log.info("快递订阅：没有设置订单ID" + OrderExpress.getId());
//               continue;
//           }
//           Orders order = ordersReadDao.get(OrderExpress.getOrderId().intValue());
//           if (order == null) {
//               log.info("快递订阅：没有查询到订单" + OrderExpress.getOrderId());
//               continue;
//           }
           String company = orderExpress.getExpressCode();
           String number = orderExpress.getExpressNo();
           String mobile = orderExpress.getMobile();
           String seller = "emateglobal";
           String productName = "小保养机油机滤";
           //String expressId = orderExpress.getId() + "";
          // String from = orderExpress.getFromCity();
           String to = "广州市";//order.getAddressAll();
           String callbackUrl = environment.getProperty("kuaidi100.callback.url")
                                + "/callback" + RpcDtoHelper.encrypt(orderExpress.getId()+"");
           Boolean isSuccess = poll(company, number, mobile,
               seller, productName, to, callbackUrl);
           if (isSuccess) {
               orderExpress.setPoll(true);
               successCount++;
           } else {
               orderExpress.setPoll(false);
               orderExpress.setFailTimes(orderExpress.getFailTimes() + 1);
               Log4jHelper.getLogger().info("poll express job err.order express id is : " + orderExpress.getId());
           }
           kuaidi100Service.updateOrderExpress(orderExpress);
       }
       Log4jHelper.getLogger().info("poll express: total " + list.size());
       Log4jHelper.getLogger().info("poll express: success " + successCount);
    }
    
    
    /**
     * kuaidi100物流信息订阅
     * 
     * @param company
     *            订阅的快递公司的编码
     * @param number
     *            订阅的快递单号，单号的最大长度是32个字符
     * @param mobile
     *            收件人的手机号
     * @param seller
     *            寄件商家的名称
     * @param productName
     *            寄给收件人的商品名
     * @param expressId
     *            courier_express中id
     * @param from
     *            出发地城市
     * @param to
     *            目的地城市
     * @return
     */
    public boolean poll(String company, String number, String mobile,
            String seller, String productName, String to, String callbackUrl) {
        String from = "广州市白云区";

        Kuaidi100Request request = new Kuaidi100Request();
        request.setCompany(company);
        request.setNumber(number);
        request.setFrom(from);
        request.setTo(to);
        request.setKey(
                this.environment.getRequiredProperty("kuaidi100.poll.key"));
        request.getParameters().put("callbackurl", callbackUrl);
        request.getParameters().put("salt",
                this.environment.getRequiredProperty("kuaidi100.poll.salt"));
        request.getParameters().put("resultv2", "1");
        request.getParameters().put("mobiletelephone", mobile);
        request.getParameters().put("seller", seller);
        request.getParameters().put("commodity", productName);

        Map<String, String> params = HttpClientHelper.buildMap(
                new String[] { "schema", "param" },
                new String[] { "json", JacksonHelper.toJSON(request) });
        try {
            String ret = HttpClientHelper.httpPost(
                    this.environment.getRequiredProperty("kuaidi100.poll.url"),
                    params);
            Kuaidi100Response resp = JacksonHelper.fromJSON(ret,
                    Kuaidi100Response.class);
            if (resp.getResult() == true) {
                return true;
            } else if (resp.getMessage() != null
                    && resp.getMessage().contains("重复订阅")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
