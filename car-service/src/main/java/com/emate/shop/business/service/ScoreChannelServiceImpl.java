package com.emate.shop.business.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.ScoreChannelService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.ScoreChannel;
import com.emate.shop.business.model.ScoreChannelExample;
import com.emate.shop.business.model.ScoreChannelExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.ScoreChannelMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 积分商城的城市渠道规则
 * @author dong
 *
 */
@Service
public class ScoreChannelServiceImpl implements ScoreChannelService {
	
	@Resource
	private ScoreChannelMapper scoreChannelMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Override
	@Read
	public DatasetList<Map<String,Object>> adminQueryScoreChannelList(Integer pageNo,Integer pageSize,ScoreChannel scoreChannel) {
		ScoreChannelExample scoreChannelEx = new ScoreChannelExample();
		//排序
		scoreChannelEx.setOrderByClause(ScoreChannelExample.CREATE_TIME_DESC);
		//筛选条件
		Criteria cr = scoreChannelEx.createCriteria();
		cr.andRuleTypeNotEqualTo(ScoreChannel.RULE_TYPE_1);
		if(StringUtils.isNotEmpty(scoreChannel.getCityName())){
			cr.andCityNameLike("%"+scoreChannel.getCityName() +"%");
		}
		//分页
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, scoreChannelMapper.countByExample(scoreChannelEx));
		scoreChannelEx.setLimitStart(page.getStartRow());
		scoreChannelEx.setLimitEnd(page.getSize());
		//查询结果
		List<ScoreChannel> scoreChannels = scoreChannelMapper.selectByExample(scoreChannelEx);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//匹配机油规则
		ScoreChannelExample scoreChannelExample = new ScoreChannelExample();
		scoreChannelExample.createCriteria()
		.andRuleTypeEqualTo(ScoreChannel.RULE_TYPE_1);
		List<ScoreChannel> selectByExample = scoreChannelMapper.selectByExample(scoreChannelExample);
		for(ScoreChannel scoreChannelss :scoreChannels){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id", scoreChannelss.getId());
			map.put("cityName", scoreChannelss.getCityName());
			map.put("channel", scoreChannelss.getChannel());
			map.put("score", scoreChannelss.getScore());
			map.put("min", scoreChannelss.getMinimum());
			map.put("max", scoreChannelss.getMaximum());
			map.put("carType", scoreChannelss.getCarType());
			String ruleValue = scoreChannelss.getRuleValue();
			if(StringUtils.isEmpty(ruleValue)){
				map.put("baoyangtimes", 0);
				map.put("penqitimes", 0);
				map.put("xichetimes", 0);
				map.put("dianpingtimes", 0);
				map.put("juan20", 0);
				map.put("juan50", 0);
			}else{
				String[] split = ruleValue.split(",");
				map.put("baoyangtimes", split[0]);
				map.put("penqitimes", split[1]);
				map.put("xichetimes", split[2]);
				map.put("dianpingtimes", split[3]);
				if(split.length==6){
					map.put("juan20", split[4]);
					map.put("juan50", split[5]);
				}else{
					map.put("juan20", 0);
					map.put("juan50", 0);
				}
			}
			List<ScoreChannel> arrayList = new ArrayList<ScoreChannel>();
			for(ScoreChannel scoreChannelsss : selectByExample){
				if(scoreChannelsss.getCityName().equals(scoreChannelss.getCityName())&&scoreChannelsss.getChannel().equals(scoreChannelss.getChannel())){
					arrayList.add(scoreChannelsss);
				}
			}
			map.put("jiyou", arrayList);
			resultList.add(map);
		}
		return DatasetBuilder.fromDataList(resultList,page.createPageInfo());
	}

	@Override
	@Read
	public DatasetList<ScoreChannel> queryScoreChannel(ScoreChannel scoreChannel) {
		//筛选条件:
		ScoreChannelExample scoreChannelEx = new ScoreChannelExample();
		scoreChannelEx.createCriteria()
		//渠道名称
		.andChannelEqualTo(scoreChannel.getChannel())
		//城市id
		.andCityNameEqualTo(scoreChannel.getCityName());
		//查询结果
		List<ScoreChannel> scoreChannels = scoreChannelMapper.selectByExample(scoreChannelEx);
		return DatasetBuilder.fromDataList(scoreChannels);
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<Boolean> addOrUpdateScoreChannel(List<ScoreChannel> scoreChannels) {
		if(scoreChannels.isEmpty()){
			throw new BusinessException("请填写城市渠道规则");
		}
		if(!Objects.isNull(scoreChannels.get(0).getScore())){
			if(scoreChannels.get(0).getScore().longValue()>100000000||scoreChannels.get(0).getScore().compareTo(BigDecimal.ZERO)<0){
				throw new BusinessException("兑换积分过大或兑换积分为负数!");
			}
		}
		for(ScoreChannel sco :scoreChannels){
			if(!Objects.isNull(sco.getMinimum())){
				if(sco.getMinimum().longValue()>100000000||sco.getMinimum().compareTo(BigDecimal.ZERO)<0){
					throw new BusinessException("保单金额过大或金额为负数!");
				}
			}
			if(!Objects.isNull(sco.getMaximum())){
				if(sco.getMaximum().longValue()>100000000||sco.getMaximum().compareTo(BigDecimal.ZERO)<0){
					throw new BusinessException("保单金额过大或金额为负数!");
				}
			}
		}
		ScoreChannelExample scoreChannelEx = new ScoreChannelExample();
		scoreChannelEx.createCriteria()
		.andCityNameEqualTo(scoreChannels.get(0).getCityName())
		.andChannelEqualTo(scoreChannels.get(0).getChannel());
		List<ScoreChannel> scoreChannelss = scoreChannelMapper.selectByExample(scoreChannelEx);
		if(!scoreChannelss.isEmpty()){
			ScoreChannel scoreChannel = new ScoreChannel();
			scoreChannel.setChannel(scoreChannels.get(0).getChannel());
			scoreChannel.setCityName(scoreChannels.get(0).getCityName());
			definedMapper.delScoreChannel(scoreChannel);
		}
		for(ScoreChannel scoreChannel:scoreChannels){
			scoreChannel.setCreateTime(new Date());
			scoreChannel.setUpdateTime(new Date());
			if(scoreChannelMapper.insertSelective(scoreChannel)!=1){
				throw new BusinessException("添加城市渠道规则异常");
			}
		}
		return DatasetBuilder.fromDataSimple(true);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> delScoreChannel(ScoreChannel scoreChannel) {
		if(Objects.isNull(scoreChannel.getCityName())){
			throw new BusinessException("缺少城市参数");
		}
		if(StringUtils.isEmpty(scoreChannel.getChannel())){
			throw new BusinessException("缺少渠道参数");
		}
		
		if(definedMapper.delScoreChannel(scoreChannel)>0){
			return DatasetBuilder.fromDataSimple(true);
		}
		throw new BusinessException("删除该城市的渠道异常");
	}

}
