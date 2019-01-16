package com.emate.shop.business.service.kuaidi100;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.emate.shop.business.api.Kuaidi100Service;
import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.model.OrderExpressExample;
import com.emate.shop.business.vo.kuaidi100.Kuaidi100Response;
import com.emate.shop.business.vo.kuaidi100.NoticeRequestParam;
import com.emate.shop.business.vo.kuaidi100.Result;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.OrderExpressMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class Kuaidi100ServiceImpl implements Kuaidi100Service {
	
	@Resource
	private OrderExpressMapper orderExpressMapper;
	
    /*
     * 查询需要订阅的快递信息
     */
    @Override
    public DatasetList<OrderExpress> queryForPoll(){
    	OrderExpressExample ex = new OrderExpressExample();
    	ex.or().andPollEqualTo(false).andFailTimesLessThanOrEqualTo(5);
    	return DatasetBuilder.fromDataList(orderExpressMapper.selectByExample(ex));
    }

//    public boolean testPoll() {
//        return this.poll("yunda", "3901351005579", "18612610830", "emate",
//                "测试商品", "北京大兴区", "http://www.emateshop.com?id=test");
//    }

    @Write
    @Override
    @Transactional
    public DatasetSimple<Kuaidi100Response> callback(Long id,
            NoticeRequestParam noticeRequestParam) {
    	
    	OrderExpress orderExpress = orderExpressMapper.selectByPrimaryKey(id);
    	if(orderExpress!=null){
    		 if (!StringUtils.isEmpty(noticeRequestParam.getComNew())) {// 新公司不为空，即出错
                 orderExpress.setExpressCode(noticeRequestParam.getComNew());
             } else {
                 if ("abort".equals(noticeRequestParam.getStatus()) && StringUtils.isEmpty(noticeRequestParam.getComNew())
                     && noticeRequestParam.getMessage().contains("3天")) {
                     orderExpress.setPoll(false);
                     orderExpress.setFailTimes(orderExpress.getFailTimes() + 1);
                 }
             }
             if (Objects.nonNull(noticeRequestParam.getLastResult())) {
            	 Result result = noticeRequestParam.getLastResult();
                 String state = result.getState();
                 String data = JacksonHelper.toJSON(result.getData());
                 orderExpress.setExpressStatus(Integer.parseInt(state));// 快递当前签收状态
                 orderExpress.setResponseJson(data);
             }
             orderExpress.setUpdateTime(new Date());
             if(orderExpressMapper.updateByPrimaryKeySelective(orderExpress)!=1){
            	 throw new BusinessException("更新快递信息失败！");
             }
    	}else{
    		throw new BusinessException("找不到物流信息！");
    	}
    	
        // FIXME
        Kuaidi100Response response = new Kuaidi100Response();
        response.setReturnCode("200");
        response.setResult(true);
        response.setMessage("接收成功");
        return DatasetBuilder.fromDataSimple(response);
    }
    
    
    @Override
    @Write
    @Transactional
    public DatasetSimple<Boolean> updateOrderExpress(OrderExpress orderExpress){
    	orderExpress.setUpdateTime(new Date());
    	if(orderExpressMapper.updateByPrimaryKey(orderExpress)!=1){
    		throw new BusinessException("更新物流信息失败！");
    	}
    	return DatasetBuilder.fromDataSimple(true);
    }

}
