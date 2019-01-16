package com.emate.shop.business.service;

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

import com.emate.shop.business.api.FeedbackService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Feedback;
import com.emate.shop.business.model.FeedbackExample;
import com.emate.shop.business.model.FeedbackExample.Criteria;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.business.model.User;
import com.emate.shop.business.model.UserExample;
import com.emate.shop.datasource.Write;
import com.emate.shop.mapper.FeedbackMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class FeedBackServiceImpl implements FeedbackService{

    
	@Resource
	private FeedbackMapper feedbackMapper;
	
	@Resource
	private UserMapper userMapper;

	@Override
	public DatasetSimple<Boolean> addFeedBack(Long userId, String userName, String content) {
		Feedback feedback = new Feedback();
		feedback.setUserId(userId);
		feedback.setUserName(userName);
		feedback.setContent(content);
		feedback.setCreateTime(new Date());
		feedback.setUpdateTime(new Date());
		return DatasetBuilder.fromDataSimple(feedbackMapper.insertSelective(feedback) == 1);
	}

	@Override
	public DatasetList<Map<String, Object>> queryFeedBack(Integer pageNo, Integer pageSize,String phone,String startTimes,String endTimes){
		FeedbackExample ex = new FeedbackExample();
		Criteria criteria = ex.createCriteria();
		if(StringUtils.isNotEmpty(phone)){
			criteria.andUserNameLike("%"+phone+"%");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(org.apache.commons.lang.StringUtils.isNotEmpty(startTimes)){
			Date start;
			try {
				start = sf.parse(startTimes);
			} catch (ParseException e) {
				throw new BusinessException("搜索开始日期异常");
			}
			criteria.andCreateTimeGreaterThanOrEqualTo(start);
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(endTimes)){
			Date end;
			try {
				end = sf.parse(endTimes);
			} catch (ParseException e) {
				throw new BusinessException("搜索结束日期异常");
			}
			criteria.andCreateTimeLessThan(end);
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, feedbackMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		ex.setOrderByClause(FeedbackExample.CREATE_TIME_DESC);
		List<Feedback> feedBackList = feedbackMapper.selectByExample(ex);
		List<Long> userIds = feedBackList.stream().map(Feedback::getUserId).distinct().collect(Collectors.toList());
		UserExample userEx = new UserExample();
		com.emate.shop.business.model.UserExample.Criteria userCr = userEx.createCriteria();
		userIds.add(0L);
		userCr.andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userEx);
		Map<Long, String> map = new HashMap<Long, String>();
		users.stream().forEach(user ->{
			map.put(user.getId(), user.getNickName());
		});
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		feedBackList.stream().forEach(feedBacks ->{
			Map<String, Object> hashMap = new HashMap<String,Object>();
			hashMap.put("id", feedBacks.getId());
			hashMap.put("phone",feedBacks.getUserName());
			hashMap.put("message",feedBacks.getContent());
			hashMap.put("createTime", feedBacks.getCreateTime());
			hashMap.put("name", map.get(feedBacks.getUserId()));
			hashMap.put("remark", feedBacks.getRemark());
			list.add(hashMap);
		});
		return DatasetBuilder.fromDataList(list,page.createPageInfo());
	}
	/**
	 * 修改用户反馈备注
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> updateFeedback(String id, String remark) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("反馈id不能为空~");
		}
		if(StringUtils.isEmpty(remark)){
			throw new BusinessException("备注必填~");
		}
		Feedback feedback = feedbackMapper.selectByPrimaryKey(Long.valueOf(id));
		if(Objects.isNull(feedback)){
			throw new BusinessException("没查到该条用户反馈~");
		}
		feedback.setRemark(remark);
		if(feedbackMapper.updateByPrimaryKeySelective(feedback)!=1){
			throw new BusinessException("修改备注失败");
		};
		Map<String, String> result = new HashMap<String,String>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}
}
