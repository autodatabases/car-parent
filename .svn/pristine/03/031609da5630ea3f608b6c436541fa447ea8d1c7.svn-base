package com.emate.shop.business.service;

import java.math.BigDecimal;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.CountermanOrdersService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.business.model.CountermanCaiDotExample;
import com.emate.shop.business.model.CountermanExample;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.business.model.CountermanOrders;
import com.emate.shop.business.model.CountermanOrdersExample;
//import com.emate.shop.business.model.CountermanScore;
//import com.emate.shop.business.model.CountermanScoreExample;
import com.emate.shop.business.model.CountermanScoreRecord;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CountermanCaiDotMapper;
import com.emate.shop.mapper.CountermanGoodMapper;
import com.emate.shop.mapper.CountermanMapper;
import com.emate.shop.mapper.CountermanOrdersMapper;
import com.emate.shop.mapper.CountermanScoreMapper;
import com.emate.shop.mapper.CountermanScoreRecordMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 业务员订单信息操作
 * @author dong
 *
 */
@Service
public class CountermanOrdersServiceImpl implements CountermanOrdersService{

	@Resource
	private CountermanMapper countermanMapper;
	
	@Resource
	private CountermanOrdersMapper countermanOrdersMapper;
	
	@Resource
	private CountermanGoodMapper countermanGoodMapper;
	
	@Resource
	private CountermanScoreMapper countermanScoreMapper;
	
	@Resource
	private CountermanScoreRecordMapper countermanScoreRecordMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;
	
	@Resource
	private CountermanCaiDotMapper countermanCaiDotMapper;
	
	@Write
	@Override
	@Transactional
	public synchronized DatasetSimple<String> addCountermanOrders(CountermanOrders countermanOrders,Long userId) {
		//判断业务员代码是否为空
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andUserIdEqualTo(userId);
		countermanEx.setLimitStart(0);
		countermanEx.setLimitEnd(1);
		List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
		if(countermans.isEmpty()){
			throw new BusinessException("请绑定业务员~~");
		}
		/*//查询用户积分是否充足
		CountermanScoreExample countermanScoreExample = new CountermanScoreExample(); 
		countermanScoreExample.createCriteria().andCountermanIdEqualTo(countermans.get(0).getId());
		countermanScoreExample.setLimitStart(0);
		countermanScoreExample.setLimitEnd(1);
		List<CountermanScore> countermanScores = countermanScoreMapper.selectByExample(countermanScoreExample);
		if(countermanScores.isEmpty()||countermanScores.get(0).getScore().intValue() < countermanOrders.getOrderPrice().intValue()){
			throw new BusinessException("您的积分不足!!");
		}*/
		if(countermans.get(0).getScore().intValue() < countermanOrders.getOrderPrice().intValue()){
			throw new BusinessException("您的积分不足!!");
		}
		//查询商品库存
		String[] goodDetails = countermanOrders.getGoodId().split(",");
		StringBuffer goodIds = new StringBuffer();
		StringBuffer goodXinXi= new StringBuffer();
		BigDecimal guoPrice = new BigDecimal("0");
		for(String goodDetail:goodDetails){
				String[] goodDetailArray = goodDetail.split("_");
				CountermanGood good =countermanGoodMapper.selectByPrimaryKey(Long.valueOf(goodDetailArray[0]));
				if(Objects.isNull(good)){
					throw new BusinessException("该商品不存在");
				}
				if(CountermanGood.GOOD_STATUS_0 != good.getGoodStatus()){
					throw new BusinessException("该商品已下架");
				}
				if(good.getGoodNumber().intValue() <= 0 || good.getGoodNumber().intValue() < Integer.valueOf(goodDetailArray[1])){
					throw new BusinessException("该商品:"+good.getGoodName()+"的库存不足,请选择其他商品");
				}
				goodIds.append(goodDetailArray[0]).append(",");
				goodXinXi.append(good.getGoodName()).append("_").append(goodDetailArray[1]).append("_").append(good.getGoodScore().multiply(new BigDecimal(goodDetailArray[1]))).append(",");
				int newGoodNumber = good.getGoodNumber().intValue()-Integer.valueOf(goodDetailArray[1]);
				good.setGoodNumber(new BigDecimal(String.valueOf(newGoodNumber)));
				good.setUpdateTime(new Date());
				countermanGoodMapper.updateByPrimaryKeySelective(good);
				//该订单国寿结算价格
				guoPrice = guoPrice.add(good.getGuoPrice().multiply(new BigDecimal(goodDetailArray[1])));
		}
		goodIds.deleteCharAt(goodIds.length()-1);
		countermanOrders.setGoodId(goodIds.toString());
		goodXinXi.deleteCharAt(goodXinXi.length()-1);
		countermanOrders.setOrderDetail(goodXinXi.toString());
		//创建订单
		countermanOrders.setOrderNo(RandomUtil.getOrderSn());
		countermanOrders.setOrderStatus(CountermanOrders.ORDER_STATUS_1);
		countermanOrders.setCountermanCode(countermans.get(0).getCountermanCode());
		countermanOrders.setCountermanId(countermans.get(0).getId());
		countermanOrders.setCaiDotId(countermans.get(0).getCaiDotId());
		countermanOrders.setGuoPrice(guoPrice);
		countermanOrders.setCreateTime(new Date());
		countermanOrders.setUpdateTime(new Date());
		countermanOrdersMapper.insertSelective(countermanOrders);
		//用户减积分
		BigDecimal score = countermans.get(0).getScore().subtract(countermanOrders.getOrderPrice());
		countermans.get(0).setScore(score);
		countermans.get(0).setUpdateTime(new Date());
		countermanMapper.updateByPrimaryKeySelective(countermans.get(0));
		//积分修改记录
		CountermanScoreRecord countermanScoreRecord = new CountermanScoreRecord();
		countermanScoreRecord.setCaiDotId(countermans.get(0).getCaiDotId());
		countermanScoreRecord.setRecordRemark(countermanOrders.getOrderNo());
		countermanScoreRecord.setCountermanCode(countermans.get(0).getCountermanCode());
		countermanScoreRecord.setCountermanId(countermans.get(0).getId());
		countermanScoreRecord.setOperationType(CountermanScoreRecord.OPERATION_TYPE_2);
		countermanScoreRecord.setScore(countermanOrders.getOrderPrice().multiply(new BigDecimal(-1)));
		countermanScoreRecord.setCreateTime(new Date());
		countermanScoreRecord.setUpdateTime(new Date());
		countermanScoreRecordMapper.insertSelective(countermanScoreRecord);
		
		return DatasetBuilder.fromDataSimple(countermanOrders.getOrderNo());
	}
	@SuppressWarnings("null")
	@Read
	@Override
	public DatasetList<Map<String, Object>> adminCountermanOrdersList(Integer pageNo, Integer pageSize,
			String countermanPhone, String orderStatus, String startTimes, String endTimes,String orderNo,Long userId) {
		//后台登录人姓名
		String pinYin = systemUserMapper.selectByPrimaryKey(userId).getUserName();
		CountermanCaiDotExample countermanCaiDotEx = new CountermanCaiDotExample();
		countermanCaiDotEx.createCriteria().andPinYinEqualTo(pinYin);
		List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(countermanCaiDotEx);
		
		CountermanOrdersExample ex = new CountermanOrdersExample();
		com.emate.shop.business.model.CountermanOrdersExample.Criteria criteria = ex.createCriteria();
		//根据订单编号查询订单
		if(StringUtils.isNotEmpty(orderNo)){
			criteria.andOrderNoLike("%"+orderNo+"%");
		}
		if(!countermanCaiDots.isEmpty()&&countermanCaiDots.get(0)!=null){
			criteria.andCaiDotIdEqualTo(countermanCaiDots.get(0).getId());
		}
		if(StringUtils.isNotEmpty(orderStatus)&&!"a".equals(orderStatus)){
			criteria.andOrderStatusEqualTo(Integer.valueOf(orderStatus));
		}
		//下单时间的开始时间
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(StringUtils.isNotEmpty(startTimes)){
			Date start;
			try {
				start = sf.parse(startTimes);
			} catch (ParseException e) {
				throw new BusinessException("搜索开始日期异常");
			}
			criteria.andCreateTimeGreaterThanOrEqualTo(start);

		}
		//下单时间的结束时间
		if(StringUtils.isNotEmpty(endTimes)){
			Date end;
			try {
				end = sf.parse(endTimes);
			} catch (ParseException e) {
				throw new BusinessException("搜索结束日期异常");
			}
			criteria.andCreateTimeLessThanOrEqualTo(end);

		}
		//根据业务员手机号查询业务员订单
		if(StringUtils.isNotEmpty(countermanPhone)){
			CountermanExample countermanEx = new CountermanExample();
			countermanEx.createCriteria().andPhoneLike("%"+countermanPhone+"%");
			List<Counterman> countermans = countermanMapper.selectByExample(countermanEx);
			List<Long> countermanIds = new ArrayList<Long>();
			for(Counterman counterman:countermans){
				countermanIds.add(counterman.getId());
			}
			countermanIds.add(0L);
			criteria.andCountermanIdIn(countermanIds);
		}
		ex.setOrderByClause(CountermanOrdersExample.CREATE_TIME_DESC);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanOrdersMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanOrders> orders = countermanOrdersMapper.selectByExample(ex);
		ArrayList<Map<String, Object>> ordersMaps = new ArrayList<Map<String,Object>>();
		if(orders.isEmpty()||orders.size()<=0){
			return DatasetBuilder.fromDataList(ordersMaps,page.createPageInfo());
		}
		List<Long> countermanIds = orders.stream().map(CountermanOrders::getCountermanId).collect(Collectors.toList());
		Map<Long, Counterman> countermanMap = new HashMap<Long,Counterman>();
		if(countermanIds !=null || !countermanIds.isEmpty()){
			CountermanExample countermanEx = new CountermanExample();
			countermanEx.createCriteria().andIdIn(countermanIds);
			List<Counterman> countermans= countermanMapper.selectByExample(countermanEx);
			countermans.forEach(e -> {
				countermanMap.put(e.getId(), e);
			});
		}
		for (CountermanOrders order : orders) {
			HashMap<String, Object> ordersMap = new HashMap<String,Object>();
			ordersMap.put("orderId", order.getId());
			ordersMap.put("orderNo", order.getOrderNo());
			ordersMap.put("orderPrice", order.getOrderPrice());
			ordersMap.put("orderCreateTime", order.getCreateTime());
			//ordersMap.put("goodId", order.getGoodId());
			ordersMap.put("orderStatus", order.getOrderStatus());
			ordersMap.put("orderRemark", order.getOrderRemark());
			ordersMap.put("orderDetail", order.getOrderDetail());
			//ordersMap.put("belongArea", order.getBelongArea());
			Counterman counterman = countermanMap.get(order.getCountermanId());
			if(counterman != null){
				ordersMap.put("countermanName",counterman.getName());
				ordersMap.put("countermanphone",counterman.getPhone());
				ordersMap.put("countermanCode",counterman.getCountermanCode());
			}else{
				ordersMap.put("countermanName",null);
				ordersMap.put("countermanphone",null);
				ordersMap.put("countermanCode",null);
			}
			String orderDetails = order.getOrderDetail(); 
			String[] goodDetails = orderDetails.split(",");
			List<Map<String,Object>> goods = new ArrayList<Map<String,Object>>();
			for(String goodDetail : goodDetails){
				HashMap<String, Object> good = new HashMap<String ,Object>();
				String[] goodArray = goodDetail.split("_");
				good.put("goodName", goodArray[0]);
				good.put("goodNumber", goodArray[1]);
				good.put("goodprice", goodArray[2]);
				goods.add(good);
			}
			ordersMap.put("good", goods);
			ordersMaps.add(ordersMap);
		}
		return DatasetBuilder.fromDataList(ordersMaps,page.createPageInfo());
	}

	@Read
	@Override
	public DatasetSimple<Map<String,Object>> queryCountermanOrdersById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("订单id不能为空");
		}
		CountermanOrders order = countermanOrdersMapper.selectByPrimaryKey(Long.valueOf(id));
		if(Objects.isNull(order)){
			throw new BusinessException("未查询到该订单");
		}
		HashMap<String, Object> ordersMap = new HashMap<String,Object>();
		ordersMap.put("orderId", order.getId());
		ordersMap.put("orderNo", order.getOrderNo());
		ordersMap.put("orderPrice", order.getOrderPrice());
		ordersMap.put("orderCreateTime", order.getCreateTime());
		ordersMap.put("goodId", order.getGoodId());
		ordersMap.put("orderStatus", order.getOrderStatus());
		ordersMap.put("orderRemark", order.getOrderRemark());
		Counterman counterman = countermanMapper.selectByPrimaryKey(order.getCountermanId());
		if(counterman != null){
			ordersMap.put("countermanName",counterman.getName());
			ordersMap.put("countermanphone",counterman.getPhone());
			ordersMap.put("countermanCode",counterman.getCountermanCode());
		}else{
			ordersMap.put("countermanName",null);
			ordersMap.put("countermanphone",null);
			ordersMap.put("countermanCode",null);
		}
		return DatasetBuilder.fromDataSimple(ordersMap);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, Object>> addOrderRemark(String orderNo, String remark) {
		CountermanOrdersExample countermanOrdersEx = new CountermanOrdersExample();
		countermanOrdersEx.createCriteria().andOrderNoEqualTo(orderNo);
		CountermanOrders countermanOrders = new CountermanOrders();
		countermanOrders.setOrderRemark(remark);;
		if(countermanOrdersMapper.updateByExampleSelective( countermanOrders, countermanOrdersEx)!=1){
			throw new BusinessException("添加订单备注失败！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	public DatasetSimple<Map<String,Object>> updateCountermanOrders(String orderNo,String status) {
		if(StringUtils.isEmpty(orderNo)){
			throw new BusinessException("订单编号为空");
		}
		if(StringUtils.isEmpty(status)){
			throw new BusinessException("订单状态没传");
		}
		CountermanOrdersExample countermanOrdersEx = new CountermanOrdersExample();
		countermanOrdersEx.createCriteria().andOrderNoEqualTo(orderNo);
		CountermanOrders countermanOrders = new CountermanOrders();
		if("1".equals(status)){
			countermanOrders.setOrderStatus(CountermanOrders.ORDER_STATUS_1);
		}
		if("2".equals(status)){
			countermanOrders.setOrderStatus(CountermanOrders.ORDER_STATUS_2);
		}
		if(countermanOrdersMapper.updateByExampleSelective( countermanOrders, countermanOrdersEx)!=1){
			throw new BusinessException("修改订单状态失败！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		return DatasetBuilder.fromDataSimple(result);
	}
}
