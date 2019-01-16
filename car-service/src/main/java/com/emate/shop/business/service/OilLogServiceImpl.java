package com.emate.shop.business.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.emate.shop.business.api.OilLogService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.OilLog;
import com.emate.shop.business.model.OilLogExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.mapper.OilLogMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;

@Service
public class OilLogServiceImpl implements OilLogService {

    
    @Resource
    private OilLogMapper oilLogMapper;

   
	@Override
	@Read
	public DatasetList<OilLog> oilLogList(Integer pageNo, Integer pageSize, 
			String orderNo) {
		OilLogExample oilLogEx = new OilLogExample();
		//条件查询
		if(StringUtils.isNotEmpty(orderNo)){
			oilLogEx.createCriteria().andOrderNoEqualTo(orderNo);
		}
		//分页
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, 
				oilLogMapper.countByExample(oilLogEx));
		oilLogEx.setLimitStart(page.getStartRow());
		oilLogEx.setLimitEnd(page.getSize());
		oilLogEx.setOrderByClause(OilLogExample.CREATE_TIME_DESC);
		//查询结果
		List<OilLog> oilLogList = oilLogMapper.selectByExample(oilLogEx);
		
		return DatasetBuilder.fromDataList(oilLogList, page.createPageInfo());
	}

}
