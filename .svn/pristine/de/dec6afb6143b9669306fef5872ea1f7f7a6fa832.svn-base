package com.emate.shop.business.api;

import java.util.Map;

import com.emate.shop.business.model.OrderComment;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 用户针对订单评语表
 * @author dong
 *
 */
public interface OrderCommentService {
	
	/**
	 * 添加评价表记录
	 * @param orderComment
	 * @return
	 */
	public DatasetSimple<Map<String,String>> addOrderComment(OrderComment orderComment);
	
	
	/**
	 * admin查询订单评语
	 * @param pageSize
	 * @param pageNo
	 * @param orderNo
	 * @param userPhone
	 * @param sellerName
	 * @return
	 */
	public DatasetList<Map<String, String>> adminQuery(Integer pageSize,Integer pageNo,String orderNo,String userPhone,String sellerName);
	
	/**
	 * 根据订单编号或商家id查询用户评语
	 * @param orderNo
	 * @param sellerId
	 * @return
	 */
	public DatasetList<Map<String,String>> queryByCondition(String orderNo,Long sellerId);
	
	/**
	 * 更新订单评语，添加回访记录
	 * @param id
	 * @param remark
	 * @return
	 */
	public DatasetSimple<String> updateOrderComment(String id,String remark);
}
