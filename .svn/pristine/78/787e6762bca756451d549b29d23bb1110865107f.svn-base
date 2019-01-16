package com.emate.shop.business.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.SurveyRecordService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.SurveyRecord;
import com.emate.shop.business.model.SurveyRecordExample;
import com.emate.shop.business.model.SurveyRecordExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.SurveyRecordMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class SurveyRecordServiceImpl implements SurveyRecordService{
	@Resource
	private SurveyRecordMapper surveyRecordMapper;

	@Override
	@Read
	public DatasetList<SurveyRecord> adminQueryRecord(Integer pageNo, Integer pageSize, SurveyRecord surveyRecord) {
		SurveyRecordExample surveyRecordEx = new SurveyRecordExample();
		Criteria c = surveyRecordEx.createCriteria();
		if(StringUtils.isNotEmpty(surveyRecord.getUserName())){
			c.andUserNameEqualTo(surveyRecord.getUserName());
		}
		if(StringUtils.isNotEmpty(surveyRecord.getUserPhone())){
			c.andUserPhoneEqualTo(surveyRecord.getUserPhone());
		}
		
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, surveyRecordMapper.countByExample(surveyRecordEx));
		surveyRecordEx.setOrderByClause(SurveyRecordExample.CREATE_TIME_DESC);
		surveyRecordEx.setLimitStart(page.getStartRow());
		surveyRecordEx.setLimitEnd(page.getSize());
		
		List<SurveyRecord> surveyRecords = surveyRecordMapper.selectByExample(surveyRecordEx);
		return DatasetBuilder.fromDataList(surveyRecords, page.createPageInfo());
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addRecord(SurveyRecord surveyRecord) {
		
		if(StringUtils.isEmpty(surveyRecord.getUserName())){
			throw new BusinessException("车主姓名不能为空");
		}
		
		if(StringUtils.isEmpty(surveyRecord.getUserPhone())){
			throw new BusinessException("手机号不能为空");
		}
		
		if(StringUtils.isEmpty(surveyRecord.getLicensePicture())){
			throw new BusinessException("驾驶证照片不能为空");
		}
		
		if(StringUtils.isEmpty(surveyRecord.getProposer()) || surveyRecord.getProposer().length() > 5){
			throw new BusinessException("申请人不能为空或申请人不能大于五个字");
		}
		surveyRecord.setCreateTime(new Date());
		surveyRecord.setUpdateTime(new Date());
		if(surveyRecordMapper.insertSelective(surveyRecord)!=1){
			throw new BusinessException("录入车主信息失败");
		}
		return DatasetBuilder.fromDataSimple(true);
	}

	@Override
	@Read
	public DatasetList<SurveyRecord> exportRecord(String date) {
		//获取所取的年月
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		//配置查询条件
		SurveyRecordExample surveyRecordEx = new SurveyRecordExample();
		Criteria c = surveyRecordEx.createCriteria();
		//组织年月
		Calendar now = Calendar.getInstance(Locale.CHINA);
		now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH)+1);
		now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
		now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
		now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
		now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month-1);
		c.andCreateTimeGreaterThanOrEqualTo(now.getTime());
		//计算出当前参数所给月数+1(下个月的1号凌晨零点)
		now.set(Calendar.MONTH, month);
		c.andCreateTimeLessThan(now.getTime());
		List<SurveyRecord> surveyRecords = surveyRecordMapper.selectByExample(surveyRecordEx);
		return DatasetBuilder.fromDataList(surveyRecords);
	}

	
}
