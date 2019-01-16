package com.emate.shop.business.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.JicaiOrderService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.JicaiOrder;
import com.emate.shop.business.model.JicaiOrderExample;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserExample;
import com.emate.shop.business.model.UserExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.JicaiOrderMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class JiCaiOrderServiceImpl implements JicaiOrderService {
	@Resource
	private JicaiOrderMapper jicaiOrderMapper;
	@Resource
	private UserMapper userMapper;
	
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addJiCaiOrder(JicaiOrder jiCaiOrder) {
		//判断客户是否登录
		if(StringUtils.isNotEmpty(jiCaiOrder.getCustomerPhone())){
			//查询客户姓名
			UserExample userExample = new UserExample();
			Criteria criteria = userExample.createCriteria();
			criteria.andNameEqualTo(jiCaiOrder.getCustomerPhone());
			List<User> userList = userMapper.selectByExample(userExample);
			if(userList.isEmpty()){
				throw new BusinessException("查找不到用户信息,添加订单失败");
			}
			jiCaiOrder.setCustomerName(userList.get(0).getNickName());
			//添加时间
			jiCaiOrder.setCreateTime(new Date());
			jiCaiOrder.setUpdateTime(new Date());
			//插入数据
			if(jicaiOrderMapper.insertSelective(jiCaiOrder)==1){
				return DatasetBuilder.fromDataSimple(true);
			};
			throw new BusinessException("添加集彩订单失败");
		}
		throw new BusinessException("客户信息不全,添加集彩订单失败");
	}

	@Override
	public DatasetList<JicaiOrder> adminJicaiOrderListPage(Integer pageNo, Integer pageSize,
			JicaiOrder jicaiOrder) {
		JicaiOrderExample jicaiOrderExample = new JicaiOrderExample();
		com.emate.shop.business.model.JicaiOrderExample.Criteria cc= jicaiOrderExample.createCriteria();
		if(jicaiOrder.getStatus()!=null&&jicaiOrder.getStatus()!=-1){
			cc.andStatusEqualTo(jicaiOrder.getStatus());
		}
		if(StringUtils.isNotEmpty(jicaiOrder.getOrderNo())){
			cc.andOrderNoEqualTo(jicaiOrder.getOrderNo());
		}
		//集彩订单分页
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, jicaiOrderMapper.countByExample(jicaiOrderExample));
		jicaiOrderExample.setLimitStart(page.getStartRow());
		jicaiOrderExample.setLimitEnd(page.getSize());
		//查询集彩List
		List<JicaiOrder> jicaiList = jicaiOrderMapper.selectByExample(jicaiOrderExample);
		return DatasetBuilder.fromDataList(jicaiList, page.createPageInfo());
	}

	@Override
	public DatasetSimple<Map<String, Object>> updatejicaiOrder(String orderNo) {
			JicaiOrderExample jiEx= new JicaiOrderExample();
			jiEx.createCriteria().andOrderNoEqualTo(orderNo);
			List<JicaiOrder> jicaiOrders = jicaiOrderMapper.selectByExample(jiEx);
			if(jicaiOrders.isEmpty()){
				throw new BusinessException("集彩订单不存在!");
			}
			JicaiOrder jicaiOrder = jicaiOrders.get(0);
			if(!Objects.equals(jicaiOrder.getStatus(), JicaiOrder.STATUS_0)){
				throw new BusinessException("审核失败，订单状态不正确!");
			}
			jicaiOrder.setStatus(JicaiOrder.STATUS_1);;
			jicaiOrder.setUpdateTime(new Date());
			if(jicaiOrderMapper.updateByPrimaryKeySelective(jicaiOrder)!=1){
				throw new BusinessException("审核订单失败，errCode:-1!");
			}
			Map<String, Object>  result = new HashMap<String, Object>();
			result.put("result", "success");
			return DatasetBuilder.fromDataSimple(result);
	}
	@Read
	@Override
	public DatasetSimple<JicaiOrder> jicaiOrderDetail(String orderNo) {
		JicaiOrderExample jicaiOrderEx = new JicaiOrderExample();
		jicaiOrderEx.createCriteria().andOrderNoEqualTo(orderNo);
		List<JicaiOrder> jicaiorders = jicaiOrderMapper.selectByExample(jicaiOrderEx);
		if(jicaiorders.isEmpty()){
			throw new BusinessException("订单不存在!");
		}
		JicaiOrder order = jicaiorders.get(0);
		return DatasetBuilder.fromDataSimple(order);
	}

}
