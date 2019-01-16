package com.emate.shop.business.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.GsAgentService;
import com.emate.shop.business.model.GsAgent;
import com.emate.shop.business.model.GsAgentData;
import com.emate.shop.business.model.GsAgentDataExample;
import com.emate.shop.business.model.GsAgentExample;
import com.emate.shop.business.model.GsAgentExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.GsAgentDataMapper;
import com.emate.shop.mapper.GsAgentMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


@Service
public class GsAgentServiceImpl implements GsAgentService{

	@Resource
	private GsAgentMapper gsAgentMapper;
	
	@Resource
	private GsAgentDataMapper gsAgentDataMapper;
	
	@Resource
	private DefinedMapper definedMapper;

	@Override
	@Read
	public DatasetList<String> h5DotList(String dotName) {
		if(StringUtils.isNotEmpty(dotName)){
			dotName="%"+dotName+"%";
		}
		List<String> result = definedMapper.queryDotList(dotName);
		return DatasetBuilder.fromDataList(result);
	}

	@Override
	@Read
	public DatasetList<GsAgent> h5GsAgentList(String dotName ,String agent) {
		GsAgentExample gsAgentEx = new GsAgentExample();
		Criteria c = gsAgentEx.createCriteria();
		if(StringUtils.isNotEmpty(dotName)){
			c.andDotEqualTo(dotName);
		}
		if(StringUtils.isNotEmpty(agent)){
			c.andAgencyLike("%"+agent+"%");
		}
		List<GsAgent> gsAgents = gsAgentMapper.selectByExample(gsAgentEx);
		return DatasetBuilder.fromDataList(gsAgents);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Read
	public DatasetList<Map<String,Object>> h5QueryGsAgentData(String agentId,String year) {
		if(StringUtils.isEmpty(agentId)){
			throw new BusinessException("代理公司id为空~");
		}
		if(StringUtils.isEmpty(year)){
			 year =String.valueOf(Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR));
		}
		GsAgent gsAgent = gsAgentMapper.selectByPrimaryKey(Long.valueOf(agentId));
		if(Objects.isNull(gsAgent)){
			throw new BusinessException("查询不到该代理公司");
		}
		GsAgentDataExample gsAgentDataEx = new GsAgentDataExample();
		gsAgentDataEx.createCriteria()
		.andAgencyIdEqualTo(Long.valueOf(agentId))
		.andYearEqualTo(year);
		List<GsAgentData> gsAgentData = gsAgentDataMapper.selectByExample(gsAgentDataEx);
		
		BigDecimal baofei = new BigDecimal("0");
		BigDecimal replace = new BigDecimal("0");
		String[] monthArray = {"一月","二月","三月","四月","五月","六月","七月",
				"八月","九月","十月","十一月","十二月"};
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(gsAgentData.isEmpty()){
			DatasetList<Map<String, Object>> result = DatasetBuilder.fromDataList(resultList);
			result.putDataset("gsAgent", DatasetBuilder.fromDataSimple(gsAgent));
			return result;
		}
		Collections.sort(gsAgentData, new Comparator<GsAgentData>(){
			@Override
			public int compare(GsAgentData o1, GsAgentData o2) {
				if(Integer.valueOf(o1.getMonth()) == Integer.valueOf(o2.getMonth())){
					return 0;
				}
				return  Integer.valueOf(o1.getMonth()) > Integer.valueOf(o2.getMonth())?1:-1;
			}
			
		});
		DecimalFormat df = new DecimalFormat("0.00%");
		for(GsAgentData data : gsAgentData){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id", data.getId());
			map.put("agentId", data.getAgencyId());
			map.put("year",data.getYear());
			map.put("month", monthArray[Integer.valueOf(data.getMonth())-1]);
			map.put("premium", data.getPremium());
			map.put("replaceValue", data.getReplaceValue());
			map.put("replaceRate", df.format(data.getReplaceRate().doubleValue()));
			map.put("lossRate", df.format(data.getLossRate().doubleValue()));
			baofei=baofei.add(data.getPremium());
			replace= replace.add(data.getReplaceValue());
			resultList.add(map);
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("agentId", agentId);
		map.put("year",year);
		map.put("month", "总计");
		map.put("premium", baofei);
		map.put("replaceValue", replace);
		map.put("replaceRate", df.format(gsAgentData.get(0).getReplaceRate().doubleValue()));
		map.put("lossRate", df.format(gsAgentData.get(0).getLossRate().doubleValue()));
		resultList.add(map);
		DatasetList<Map<String, Object>> result = DatasetBuilder.fromDataList(resultList);
		result.putDataset("gsAgent", DatasetBuilder.fromDataSimple(gsAgent));
		return result;
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<Map<String, Object>> importGsAgentData(List<Map<String, Object>> dataList) {
		GsAgentExample gsAgentEx = new GsAgentExample();
		GsAgentDataExample gsAgentDataEx = new GsAgentDataExample();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		//循环遍历代理分公司数据
		Calendar now = Calendar.getInstance(Locale.CHINA);
		for(Map<String, Object> agencyData:dataList){
			//查询代理公司是否存在根据网点名称和代理公司名称
			gsAgentEx.clear();
			Criteria c = gsAgentEx.createCriteria();
			c.andDotEqualTo(String.valueOf(agencyData.get("dot")));
			c.andAgencyEqualTo(String.valueOf(agencyData.get("agency")));
			gsAgentEx.setLimitStart(0);
			gsAgentEx.setLimitEnd(1);
			List<GsAgent> gsAgents = gsAgentMapper.selectByExample(gsAgentEx);
			//若不存在该代理公司
			if(gsAgents.isEmpty()){
				GsAgent gsAgent = new GsAgent();
				gsAgent.setDot(String.valueOf(agencyData.get("dot")));
				gsAgent.setAgency(String.valueOf(agencyData.get("agency")));
				if(Objects.isNull(agencyData.get("cooperateTime"))){
					gsAgent.setCooperateTime(null);
				}else{
					Date cooperateTime = null;
					try {
						cooperateTime = format.parse((String)agencyData.get("cooperateTime"));
					} catch (ParseException e) {
						e.printStackTrace();
						throw new BusinessException("日期转化异常!!");
					}
					now.set(Calendar.YEAR, 9999);
					if(cooperateTime.after(now.getTime())){
						throw new BusinessException((String)agencyData.get("cooperateTime")+"日期时间过大");
					}
					gsAgent.setCooperateTime(cooperateTime);
				}
				gsAgent.setUpdateTime(new Date());
				gsAgent.setCreateTime(new Date());
				gsAgentMapper.insertSelective(gsAgent);
				gsAgents = gsAgentMapper.selectByExample(gsAgentEx);
			}
			//根据代理公司id和年月查询是否存在代理公司该月是否存在数据
			String dateStr =(String) agencyData.get("date");
			Date date = null;
			try {
				date = format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new BusinessException("日期转化异常!!!");
			}
			now.setTime(date);
			gsAgentDataEx.clear();
			gsAgentDataEx.createCriteria().andAgencyIdEqualTo(gsAgents.get(0).getId())
			.andYearEqualTo(String.valueOf(now.get(Calendar.YEAR)))
			.andMonthEqualTo(String.valueOf(now.get(Calendar.MONTH)+1));
			List<GsAgentData> gsAgentDatas = gsAgentDataMapper.selectByExample(gsAgentDataEx);
			//若不存在,就插入
			if(gsAgentDatas.isEmpty()){
				GsAgentData gsAgentData = new GsAgentData();
				gsAgentData.setAgencyId(gsAgents.get(0).getId());
				gsAgentData.setYear(String.valueOf(now.get(Calendar.YEAR)));
				gsAgentData.setMonth(String.valueOf(now.get(Calendar.MONTH)+1));
				gsAgentData.setPremium(new BigDecimal((double)agencyData.get("premium")));
				gsAgentData.setReplaceValue(new BigDecimal((double)agencyData.get("replaceValue")));
				gsAgentData.setReplaceRate(new BigDecimal((double)agencyData.get("replaceRate")));
				gsAgentData.setLossRate(new BigDecimal((double)agencyData.get("lossRate")));
				gsAgentData.setCreateTime(new Date());
				gsAgentData.setUpdateTime(new Date());
				gsAgentDataMapper.insertSelective(gsAgentData);
			//若存在,就更新
			}else{
				gsAgentDatas.get(0).setPremium(new BigDecimal((double)agencyData.get("premium")));
				gsAgentDatas.get(0).setReplaceValue(new BigDecimal((double)agencyData.get("replaceValue")));
				gsAgentDatas.get(0).setReplaceRate(new BigDecimal((double)agencyData.get("replaceRate")));
				gsAgentDatas.get(0).setLossRate(new BigDecimal((double)agencyData.get("lossRate")));
				gsAgentDatas.get(0).setUpdateTime(new Date());
				gsAgentDataMapper.updateByPrimaryKeySelective(gsAgentDatas.get(0));
				//最后更新代理公司该年的置换率和赔付率
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("replaceRate", agencyData.get("replaceRate"));
				map.put("lossRate", agencyData.get("replaceRate"));
				map.put("agencyId", agencyData.get("replaceRate"));
				map.put("year", String.valueOf(now.get(Calendar.YEAR)));
				definedMapper.updateAgentData(map);
			}
		}
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("success",true);
		return DatasetBuilder.fromDataSimple(result);
	}
}
