/**
 * 
 */
package com.emate.shop.business.api;

import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.vo.kuaidi100.Kuaidi100Response;
import com.emate.shop.business.vo.kuaidi100.NoticeRequestParam;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * @file Kuaidi100Service.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年8月5日
 */
public interface Kuaidi100Service {

    /**
     * 快递100回调接口
     * @param id
     * @param noticeRequestParam
     * @return
     */
    DatasetSimple<Kuaidi100Response> callback(Long id,
            NoticeRequestParam noticeRequestParam);
    
    /**
     * 查询需要订阅的快递100快递列表
     * @return
     */
    public DatasetList<OrderExpress> queryForPoll();
    
    

    /***
     * 更新快递信息
     * @param orderExpress
     * @return
     */
    public DatasetSimple<Boolean> updateOrderExpress(OrderExpress orderExpress);
    
}
