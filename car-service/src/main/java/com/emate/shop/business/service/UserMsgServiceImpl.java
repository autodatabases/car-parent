package com.emate.shop.business.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emate.shop.business.api.UserMsgService;
import com.emate.shop.business.model.UserMsg;
import com.emate.shop.business.model.UserMsgExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.mapper.UserMsgMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class UserMsgServiceImpl implements UserMsgService{

    
	@Resource
	private UserMsgMapper userMsgMapper;
	
	/**
	 * 查询用户消息列表
	 */
	@Read
	@Override
	public DatasetList<UserMsg> queryMsgList(Long userId) {
		UserMsgExample ex = new UserMsgExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<UserMsg> list = userMsgMapper.selectByExample(ex);
		for(UserMsg msg:list){
			msg.setMgsContent(null);
		}
		return DatasetBuilder.fromDataList(list);
	}

	/**
	 * 查询消息详情
	 */
	@Override
	@Read
	public DatasetSimple<UserMsg> queryMsgById(Long id) {
		return DatasetBuilder.fromDataSimple(userMsgMapper.selectByPrimaryKey(id));
	}
	
}
