package com.emate.shop.business.api;

import java.util.Map;
import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.business.model.Express;
import com.emate.shop.business.model.FixOrder;
import com.emate.shop.business.model.OrderExpress;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.PowerPrice;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface OrderService {
	
	public DatasetSimple<String> addOrder(Orders order);
	
	public DatasetSimple<Boolean> updateOrder(Orders order);
	
	@Cache
	public DatasetSimple<Orders> h5QueryOrderByNo(String orderNo);
	
	@Cache
	public DatasetSimple<Map<String,Object>> adminQueryOrderDetail(String orderNo);
	
	/**
	 * 商家查看订单详情
	 * @param orderId
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> sellerQueryOrderDetail(String code);
	
	/**
	 * 商家查询自己的订单列表
	 * @param orderId
	 * @return
	 */
	public DatasetList<Map<String,Object>> sellerQueryOrderList(Long userId,String orderStatus,Integer pageNo,Integer pageSize);
	
	/**
	 * 用户查询自己的订单
	 * @param userId
	 * @param status
	 * @return
	 */
	public DatasetList<Map<String,Object>> queryOrderList(Long userId,String status); 
	
	/**
	 * 管理员查看订单列表
	 * @param orderNo
	 * @param phone
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public DatasetList<Map<String,Object>> adminQueryOrderList(Long adminId,String orderNo,String phone,String license,
			Integer pageNo,Integer pageSize,String orderStatus,String startTimes,String endTimes,String orderType);
	
	/**
	 * 根据用户车型匹配电源
	 * @param carid
	 * @return
	 */
	public DatasetSimple<PowerPrice> matchPowerProduct(Long userId);
	
	/**
	 * 根据用户车型匹配轮胎
	 * @param carid
	 * @return
	 */
	public DatasetList<Map<String,Object>> matchTyreProduct(Long userId);
	
	/**
	 * 根据用户车型匹配机油
	 * @param carid
	 * @return
	 */
	public DatasetList<Map<String,Object>> matchOilProduct(Long userId);
	
	
	/**
	 * 根据用户车型匹配机油 新的匹配接口 按照陈阳提供数据匹配
	 * @2016.10.11
	 * @param carid
	 * @return
	 */
	public DatasetList<Map<String,Object>> matchOilProductNew(Long userId);
	
	
	/**
	 * 根据用户车型匹配机滤 新的匹配接口 按照陈阳提供数据匹配
	 * @2016.10.11
	 * @param carid
	 * @return
	 */
	public DatasetList<Map<String,Object>> matchJilvProductNew(Long userId);
	
	/**
	 * 根据用户车型匹配机滤
	 * @param carid
	 * @return
	 */
	public DatasetList<Map<String,Object>> matchJilvProduct(Long userId);
	
	/**
	 * 商家获取订单统计
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> sellerGetOrderSummary(Long sellerId);
	
	/**
	 * 商家获取金额统计
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> sellerGetMoneySummary(Long sellerId);
	
	/**
	 * 商家验券列表
	 * @param sellerId
	 * @return
	 */
	public DatasetList<Map<String,Object>> sellerCompleteList(Long sellerId,Integer pageNo,Integer pageSize);
	
	/**
	 * 商家今日收入
	 * @param sellerId
	 * @return
	 */
	public DatasetList<Map<String,Object>> sellerTodayIncomeList(Long sellerId);
	
	/**
	 * 商家所有收入明细
	 * @param sellerId
	 * @return
	 */
	public DatasetList<Map<String,Object>> sellerAllIncomeList(Long sellerId,String date,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 导出待审核的订单列表给运营使用
	 * @return
	 */
	public DatasetList<Map<String,String>> exportUnCheckOrderToExcel();
	
	/**
	 * 导出国寿报表
	 * @param sellerId
	 * @return
	 */
	public DatasetList<Map<String,String>> exportChinaLifeExcel(Long sellerId,String date,String status,String city);
	
	/**
	 * 导出商家报表
	 * @param sellerId
	 * @return
	 */
	public DatasetList<Map<String,String>> exportSellerExcel(Long sellerId,String date);
	
	/**
	 * 导出所有商家报表
	 * @param date
	 * @return
	 */
	public DatasetList<Map<String,String>> exportAllSellerExcel(String date);
	
	/**
	 * 商家确认结算
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> sellerComfirmReport(Long sellerId,String date,String invoice);
	
	/**
	 * 重置这个月的商家结算
	 * @param date
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> resetSellerReport(String date);
	/**
	 * 商家确认结算定时任务
	 * @return
	 */
	public DatasetSimple<Map<String, String>> sellerComfirmReportQuartz();
	
	
	/**
	 * admin登录首页
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> adminHomeSummary(Long adminId);
	
	/**
	 * 管理员查询是够有订单
	 * @param sellerId
	 * @return
	 */
	//public DatasetSimple<Map<String,String>> hasNewOrder(String unid,String userName,String ip,String userAgent);
	
	/**
	 * 管理员删除订单
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,String>> deleteNewOrderAlter();
	
	
	/**
	 * 管理员发货
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,String>> deliverOrder(String orderNo);
	
	
	/**
	 * 查询订单记录
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> getOrderTrace(String orderNo);
	
	/**
	 * 创建维修订单
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> createFixOrder(FixOrder fixOrder);
	
	/**
	 * 维修订单列表
	 * @return
	 */
	public DatasetList<Map<String,Object>> adminFixOrderListPage(FixOrder filter,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 维修订单列表
	 * @return
	 */
	public DatasetList<Map<String,Object>> fiexOrderList(Long userId,String orderStatus,String type,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 维修订单详情
	 * @return
	 */
	public DatasetSimple<Map<String,Object>> fiexOrderDetail(Long userId,String orderNo,String type);
	
	
	/**
	 * 商家订单统计
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> sellerFixOrderSummary(Long sellerId);
	
	/**
	 * 商家提交审核
	 * @param sellerId
	 * @param orderNo
	 * @param price
	 * @param remark
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> sellerCommitFixOrder(Long sellerId,
			String orderNo,String price,String remark);
	
	
	/**
	 * 商家完成订单
	 * @param sellerId
	 * @param orderNo
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> sellerCompletFixOrder(Long sellerId,
			String orderNo);
	
	
	/**
	 * 删除订单
	 * @param orderNo
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> deleteOrder(String orderNo,Long userId);
	
	
	public DatasetSimple<Map<String, Object>> adminCheckFixOrder(String orderNo);
	
	
	public DatasetList<Express> queryExpressList();
	
	/**
	 * 管理员发货
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<Map<String,String>> deliverOrder(String orderNo, Long expressId, String expressNo);
	
	/**
	 * 管理员查看物流信息
	 * @param sellerId
	 * @return
	 */
	public DatasetSimple<OrderExpress> queryExpressInfo(String orderNo);
	/**
	 * 修改运单号码
	 * @param orderNo
	 * @param expressNo
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> editExpressNo(String orderNo,String expressNo);
	
	
	/**
	 * 添加订单备注
	 * @param orderNo
	 * @param expressNo
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> addOrderRemark(String orderNo,String remark);
	
	/**
	 * admin提交审核
	 * @param sellerId
	 * @param orderNo
	 * @param price
	 * @param remark
	 * @return
	 */
	public DatasetSimple<Map<String, Object>> adminCommitFixOrder(String orderNo,String price,String remark);
	
	/**
	 * 获取每个月下单量数据
	 * @param date
	 * @return
	 */
	public DatasetList<Map<String,String>> getOrderCompData(String date,String orderType);
	
	/**
	 * 获取每个月下单量数据
	 * @param date
	 * @return
	 */
	public DatasetList<Map<String,String>> getRegisterCompData(String date);
	
	
	/**
	 * 按照收付日期每天统计数据
	 * @param date
	 * @return
	 */
	public DatasetList<Map<String,String>> getBaodnaData(String date);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@Cache
	public DatasetSimple<Integer> QueryXiCheOrder(Long userId);
	
	/**
	 * 导出国寿报表2
	 * @param date
	 * @param status
	 * @return
	 */
	public DatasetList<Map<String,String>> exportChinaLifeExcelTwo(String date,String status);
	
	/**
	 * 获取每个月下单量数据
	 * @param date
	 * @return
	 */
	public DatasetList<Map<String,String>> getOrderCompDataTwo(String date,String orderType);
	
	/**
	 * 强制完成订单
	 * @param orderNo
	 * @return
	 */
	public DatasetSimple<String> completeOrder(String orderNo,Long userId);
	
	/**
	 * 强制取消订单
	 * @param orderNo
	 * @return
	 */
	public DatasetSimple<String> cancelOrder(String orderNo,Long userId);
}
