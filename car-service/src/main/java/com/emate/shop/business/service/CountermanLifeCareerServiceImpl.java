package com.emate.shop.business.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emate.shop.business.api.CountermanLifeCareerService;
import com.emate.shop.business.model.CountermanLifeCareer;
import com.emate.shop.business.model.CountermanLifeCareerExample;
import com.emate.shop.mapper.CountermanLifeCareerMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;



/**
 * 业务员信息操作
 * @author dong
 *
 */
@Service
public class CountermanLifeCareerServiceImpl implements CountermanLifeCareerService{

	@Resource
	private CountermanLifeCareerMapper countermanLifeCareerMapper;
	
	
	@Override
	public DatasetList<CountermanLifeCareer> queryAllCountermanLifeCareer(){
		CountermanLifeCareerExample countermanLifeCareerEx = new CountermanLifeCareerExample();
		List<CountermanLifeCareer> countermanLifeCareers = countermanLifeCareerMapper.selectByExample(countermanLifeCareerEx);
		return DatasetBuilder.fromDataList(countermanLifeCareers);
	}
}
