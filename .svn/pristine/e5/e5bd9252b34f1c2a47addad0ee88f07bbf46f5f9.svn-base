package com.emate.shop.business.service;

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

import com.emate.shop.business.api.OrderCommentService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.CarWashShengExample;
import com.emate.shop.business.model.OrderComment;
import com.emate.shop.business.model.OrderCommentExample;
import com.emate.shop.business.model.Seller;
import com.emate.shop.business.model.SellerExample;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.business.model.OrderCommentExample.Criteria;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.OrdersExample;
import com.emate.shop.mapper.CarWashShengMapper;
import com.emate.shop.mapper.OrderCommentMapper;
import com.emate.shop.mapper.OrdersMapper;
import com.emate.shop.mapper.SellerMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class OrderCommentServiceImpl implements OrderCommentService{
	
	@Resource
	private OrderCommentMapper orderCommentMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private SellerMapper sellerMapper;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private CarWashShengMapper carWashShengMapper;
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> addOrderComment(OrderComment orderComment) {
		if(StringUtils.isEmpty(orderComment.getOrderNo())){
			throw new BusinessException("参数为空~~");
		}
		OrdersExample ordersEx = new OrdersExample();
		ordersEx.createCriteria().andOrderNoEqualTo(orderComment.getOrderNo());
		ordersEx.setLimitStart(0);
		ordersEx.setLimitEnd(1);
		List<Orders> orders = ordersMapper.selectByExample(ordersEx);
		if(orders.isEmpty()){
			throw new BusinessException("未查到该订单~~");
		}
		orderComment.setCreateTime(new Date());
		orderComment.setUpdateTime(new Date());
		orderComment.setSellerId(orders.get(0).getSellerId());
		orderComment.setUserId(orders.get(0).getUserId());
		Map<String, String> result = new HashMap<String,String>();
		if(orderCommentMapper.insertSelective(orderComment)==1){
			result.put("result", "success");
		}else{
			result.put("result", "fail");
		}
		return DatasetBuilder.fromDataSimple(result);
	}
	
	@Override
	@Read
	public DatasetList<Map<String, String>> adminQuery(Integer pageSize, Integer pageNo,String orderNo,String userPhone,String sellerName) {
		OrderCommentExample orderCommentEx = new OrderCommentExample();
		Criteria c= orderCommentEx.createCriteria();
		//组织查询条件
		if(StringUtils.isNotEmpty(userPhone)){
			UserExample userEx = new UserExample();
			userEx.createCriteria().andNameLike("%"+userPhone+"%");
			List<Long> userIds = userMapper.selectByExample(userEx).stream().map(User :: getId).distinct().collect(Collectors.toList());
			userIds.add(0L);
			c.andUserIdIn(userIds);
		}
		if(StringUtils.isNotEmpty(sellerName)){
			//车服商家
			SellerExample sellerEx = new SellerExample();
			sellerEx.createCriteria().andSellerNameLike("%"+sellerName+"%");
			List<Long> sellerIds = sellerMapper.selectByExample(sellerEx).stream().map(Seller :: getId).distinct().collect(Collectors.toList());
			sellerIds.add(-1L);
			c.andSellerIdIn(sellerIds);
			//盛大商家
			CarWashShengExample carWashShengEx = new CarWashShengExample();
			carWashShengEx.createCriteria().andShopNameLike("%"+sellerName+"%");
			List<CarWashSheng> carWashShengs = carWashShengMapper.selectByExample(carWashShengEx);
			List<String> orderNos = carWashShengs.stream().map(CarWashSheng :: getOrderNo).distinct().collect(Collectors.toList());
			orderNos.add("0");
			orderCommentEx.or().andOrderNoIn(orderNos);
		}
		if(StringUtils.isNotEmpty(orderNo)){
			c.andOrderNoEqualTo(orderNo);
		}
		orderCommentEx.setOrderByClause(OrderCommentExample.CREATE_TIME_DESC);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, orderCommentMapper.countByExample(orderCommentEx));
		orderCommentEx.setLimitStart(page.getStartRow());
		orderCommentEx.setLimitEnd(page.getSize());
		//查询订单评价表信息
		List<OrderComment> orderComments = orderCommentMapper.selectByExample(orderCommentEx);
		//统计用户信息
		List<Long> userIds = orderComments.stream().map(OrderComment :: getUserId).distinct().collect(Collectors.toList());
		userIds.add(0L);
		UserExample userEx = new UserExample();
		userEx.createCriteria().andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userEx);
		Map<Long, User> userMap = new HashMap<Long,User>();
		users.stream().forEach(user -> {
			userMap.put(user.getId(), user);
		});
		//统计商家信息
		List<Long> sellerIds = orderComments.stream().map(OrderComment :: getSellerId).distinct().collect(Collectors.toList());
		sellerIds.add(0L);
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andIdIn(sellerIds);
		List<Seller> sellers = sellerMapper.selectByExample(sellerEx);
		Map<Long, Seller> sellerMap = new HashMap<Long,Seller>();
		sellers.stream().forEach(seller ->{
			sellerMap.put(seller.getId(), seller);
		});
		//统计洗车订单商家名称
		List<String> orderNos = orderComments.stream().map(OrderComment :: getOrderNo).distinct().collect(Collectors.toList());
		orderNos.add("");
		CarWashShengExample carWashShengEx = new CarWashShengExample();
		carWashShengEx.createCriteria().andOrderNoIn(orderNos);
		List<CarWashSheng> carWashShengs = carWashShengMapper.selectByExample(carWashShengEx);
		
		Map<String, String> carWashShengMap = new HashMap<String, String>();
		carWashShengs.stream().forEach(carWashSheng ->{
			carWashShengMap.put(carWashSheng.getOrderNo(), carWashSheng.getShopName());
		});
		//组织返回结果~
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		orderComments.stream().forEach(orderComm ->{
			Map<String, String> map = new HashMap<String,String>();
			map.put("id", orderComm.getId().toString());
			if(userMap.get(orderComm.getUserId())!=null){
				map.put("userName",userMap.get(orderComm.getUserId()).getNickName());
				map.put("userphone",userMap.get(orderComm.getUserId()).getName());
			}
			map.put("orderNo", orderComm.getOrderNo());
			if(sellerMap.get(orderComm.getSellerId())!=null){
				map.put("sellerName", sellerMap.get(orderComm.getSellerId()).getSellerName());
				map.put("sellerPhone", sellerMap.get(orderComm.getSellerId()).getSellerPhone());
			}else if(StringUtils.isEmpty(carWashShengMap.get(orderComm.getOrderNo()))){
				map.put("sellerName", null);
				map.put("sellerPhone", null);
			}else{
				map.put("sellerName", carWashShengMap.get(orderComm.getOrderNo()));
				map.put("sellerPhone", null);
			}
			map.put("sellerStar", String.valueOf(orderComm.getSellerStar()));
			map.put("expressStar", String.valueOf(orderComm.getExpressStar()));
			map.put("comment", orderComm.getComment());
			map.put("remark", orderComm.getRemark());
			result.add(map);
		});
		return DatasetBuilder.fromDataList(result, page.createPageInfo());
	}

	@Override
	@Read
	public DatasetList<Map<String, String>> queryByCondition(String orderNo, Long sellerId) {
		OrderCommentExample orderCommentEx = new OrderCommentExample();
		Criteria c= orderCommentEx.createCriteria();
		//组织查询条件
		if(!Objects.isNull(sellerId)){
			c.andSellerIdEqualTo(sellerId);
		}
		if(StringUtils.isNotEmpty(orderNo)){
			c.andOrderNoEqualTo(orderNo);
		}
		orderCommentEx.setOrderByClause(OrderCommentExample.CREATE_TIME_DESC);
		List<OrderComment> orderComments = orderCommentMapper.selectByExample(orderCommentEx);
		//统计用户信息
		List<Long> userIds = orderComments.stream().map(OrderComment :: getUserId).distinct().collect(Collectors.toList());
		userIds.add(0L);
		UserExample userEx = new UserExample();
		userEx.createCriteria().andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userEx);
		Map<Long, User> userMap = new HashMap<Long,User>();
		users.stream().forEach(user -> {
			userMap.put(user.getId(), user);
		});
		//统计商家信息
		List<Long> sellerIds = orderComments.stream().map(OrderComment :: getSellerId).distinct().collect(Collectors.toList());
		sellerIds.add(0L);
		SellerExample sellerEx = new SellerExample();
		sellerEx.createCriteria().andIdIn(sellerIds);
		List<Seller> sellers = sellerMapper.selectByExample(sellerEx);
		Map<Long, Seller> sellerMap = new HashMap<Long,Seller>();
		sellers.stream().forEach(seller ->{
			sellerMap.put(seller.getId(), seller);
		});
		//组织返回结果~
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		orderComments.stream().forEach(orderComm ->{
			Map<String, String> map = new HashMap<String,String>();
			map.put("id", orderComm.getId().toString());
			map.put("userName",userMap.get(orderComm.getUserId()).getNickName());
			map.put("userphone",userMap.get(orderComm.getUserId()).getName());
			map.put("orderNo", orderComm.getOrderNo());
			map.put("sellerName", sellerMap.get(orderComm.getSellerId()).getSellerName());
			map.put("sellerPhone", sellerMap.get(orderComm.getSellerId()).getSellerPhone());
			map.put("sellerStar", String.valueOf(orderComm.getSellerStar()));
			map.put("expressStar", String.valueOf(orderComm.getExpressStar()));
			map.put("comment", orderComm.getComment());
			result.add(map);
		});
		return DatasetBuilder.fromDataList(result);
	}
	
	/**
	 * 更新评语记录，添加运营回访记录
	 */
	@Override
	@Transactional
	@Write
	public DatasetSimple<String> updateOrderComment(String id, String remark) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("评语记录id为空！！");
		}
		if(StringUtils.isEmpty(remark)){
			return DatasetBuilder.fromDataSimple("SUCCESS");
		}
		OrderComment orderComment = orderCommentMapper.
				selectByPrimaryKey(Long.valueOf(id));
		if(Objects.isNull(orderComment)){
			throw new BusinessException("未查到该条评语记录！！");
		}
		orderComment.setRemark(remark);
		orderComment.setUpdateTime(new Date());
		if(1!=orderCommentMapper.updateByPrimaryKeySelective(orderComment)){
			throw new BusinessException("更新评语记录失败！！");
		}
		return DatasetBuilder.fromDataSimple("SUCCESS");
	}

}
