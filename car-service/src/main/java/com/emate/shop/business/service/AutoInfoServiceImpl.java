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

import com.emate.shop.business.api.AutoInfoService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.AutoInfoExample;
import com.emate.shop.business.model.AutoInfoExample.Criteria;
import com.emate.shop.business.model.AutoPartRelation;
import com.emate.shop.business.model.AutoPartRelationExample;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.business.model.AutoposeExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AutoInfoMapper;
import com.emate.shop.mapper.AutoPartRelationMapper;
import com.emate.shop.mapper.AutoposeMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 车型Service
 * @author dong
 *
 */
@Service
public class AutoInfoServiceImpl implements AutoInfoService{

	@Resource
	private AutoInfoMapper autoInfoMapper;
	@Resource
	private DefinedMapper definedMapper;
	@Resource
	private AutoPartRelationMapper autoPartRelationMapper;
	
	@Resource
	private AutoposeMapper autoPoseMapper;
	

	@Override
	@Read
	public DatasetList<Map<String,Object>> adminAutoInfoList(Integer pageNo, Integer pageSize, AutoInfo autoInfo) {
		//统计筛选条件
		AutoInfoExample ex = new AutoInfoExample();
		Criteria criteria = ex.createCriteria();
		if(StringUtils.isNotEmpty(autoInfo.getBrand())){
			criteria.andBrandLike("%"+autoInfo.getBrand()+"%");
		}
		if(StringUtils.isNotEmpty(autoInfo.getAutolineName())){
			criteria.andAutolineNameLike("%"+autoInfo.getAutolineName()+"%");
		}		
		if(StringUtils.isNotEmpty(autoInfo.getEngineDisp())){
			criteria.andEngineDispLike("%"+autoInfo.getEngineDisp()+"%");
		}
		if(StringUtils.isNotEmpty(autoInfo.getProductTime())){
			criteria.andProductTimeLike("%"+autoInfo.getProductTime()+"%");
		}
		//按创建时间倒序
		ex.setOrderByClause(AutoInfoExample.CREATE_TIME_DESC);
		//查询车型数量
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, autoInfoMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		//查询车型List
		List<AutoInfo> autoInfos = autoInfoMapper.selectByExample(ex);
		List<Long> ids = autoInfos.stream().map(AutoInfo::getId).distinct().collect(Collectors.toList());
		ids.add(0L);
		//筛选匹配的机滤和机油
		Map<Long,String> jiyou = new HashMap<Long,String>();
		Map<Long,String> jilv = new HashMap<Long,String>();
		AutoPartRelationExample relationExample = new AutoPartRelationExample();
		relationExample.createCriteria().andAutoIdIn(ids);
		List<AutoPartRelation> relist = autoPartRelationMapper.selectByExample(relationExample);
		relist.forEach(e -> {
			if(e.getPartTypeId()==2){
				jilv.put(e.getAutoId(), e.getMatchId());
			}else if(e.getPartTypeId()==25){
				jiyou.put(e.getAutoId(), e.getMatchId());
			}
		});
		//重新定义结果
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		autoInfos.stream().forEach(auto ->{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("id", auto.getId());
			res.put("brand", auto.getBrand());
			res.put("autolineName", auto.getAutolineName());
			res.put("engineDisp", auto.getEngineDisp());
			res.put("oilAmount", auto.getOilAmount());
			res.put("productTime", auto.getProductTime());
			res.put("jilv", jilv.get(auto.getId()));
			res.put("jiyou",jiyou.get(auto.getId()));
			result.add(res);
		});
		return DatasetBuilder.fromDataList(result, page.createPageInfo());
	}

	@Override
	@Read
	public DatasetSimple<AutoInfo> queryAutoInfoById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("参数id不能为空");
		}
		AutoInfo autoInfo = autoInfoMapper.selectByPrimaryKey(Long.valueOf(id));
		return  DatasetBuilder.fromDataSimple(autoInfo);
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<Map<String,String>> deleteAutoInfo(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("传递参数id为空");
		}
		AutoPartRelationExample autoPartRelationExample = new AutoPartRelationExample();
		autoPartRelationExample.createCriteria().andAutoIdEqualTo(Long.valueOf(id));
		autoPartRelationMapper.deleteByExample(autoPartRelationExample);
		if(autoInfoMapper.deleteByPrimaryKey(Long.valueOf(id))!=1){
			throw new BusinessException("删除车型失败");
		}
		HashMap<String, String> hashMap = new HashMap<String,String>();
		return DatasetBuilder.fromDataSimple(hashMap);
	}
 
	@Override
	@Transactional
	@Write
	public DatasetSimple<Map<String,String>> addAutoInfo(AutoInfo autoInfo) {
		if(Objects.nonNull(autoInfo.getId())){
			AutoInfo auto = autoInfoMapper.selectByPrimaryKey(autoInfo.getId());
			if(auto==null){
				throw new BusinessException("查询车型失败");
			}
			AutoInfo findAutoInfo = findAutoInfo(autoInfo);
			if(findAutoInfo!=null){
				if(!findAutoInfo.getId().equals(auto.getId())){
					throw new BusinessException("该车型已存在");
				}
			}
			autoInfo.setUpdateTime(new Date());
			if(autoInfoMapper.updateByPrimaryKeySelective(autoInfo)!=1){
				throw new BusinessException("编辑车型失败");
			};
		}else{
			if(findAutoInfo(autoInfo)!=null){
				throw new BusinessException("该车型已存在");
			}
			Long maxId = definedMapper.queryAutoInfoMaxId();
			autoInfo.setId(maxId+1);
			autoInfo.setCreateTime(new Date());
			autoInfo.setUpdateTime(new Date());
			
			if(autoInfoMapper.insertSelective(autoInfo)!=1){
				throw new BusinessException("添加车型失败");
			};
		}
		HashMap<String, String> hashMap = new HashMap<String,String>();
		return DatasetBuilder.fromDataSimple(hashMap);
	}
	
	/**
	 * 匹配车型
	 * @param autoInfo
	 * @return
	 */
	private AutoInfo findAutoInfo(AutoInfo autoInfo){
		AutoInfoExample autoInfoEx = new AutoInfoExample();
		autoInfoEx.or()
		.andBrandLike("%"+autoInfo.getBrand()+"%")
		.andAutolineNameEqualTo(autoInfo.getAutolineName().toUpperCase())
		.andEngineDispLike("%"+autoInfo.getEngineDisp()+"%")
		.andProductTimeEqualTo(autoInfo.getProductTime());
		//.andProductTimeLike(importUserInfo.getProductYear());
		List<AutoInfo>  list = autoInfoMapper.selectByExample(autoInfoEx);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public DatasetSimple<Autopose> queryAutoPoseByCondition(String carType) {
		int index = carType.indexOf("-");
		if(index==-1||index==0||index==carType.length()-1){
			throw new BusinessException("填写的车型格式不正确");
		}
		String[] str = carType.split("-");
		AutoposeExample autoPoseEx = new AutoposeExample();
		autoPoseEx.createCriteria().andBrandnameEqualTo(str[0])
		.andFactorynameEqualTo(str[1]);
		List<Autopose> autoPoses = autoPoseMapper.selectByExample(autoPoseEx);
		if(autoPoses.isEmpty()){
			return DatasetBuilder.fromDataSimple(null);
		}
		return DatasetBuilder.fromDataSimple(autoPoses.get(0));
	}
	/**
	 * 根据品牌验证是否存在
	 */
	@Override
	public DatasetSimple<Boolean> queryAutoposeByBrand(String carBrand) {
		if(StringUtils.isEmpty(carBrand)){
			return DatasetBuilder.fromDataSimple(false);
		}
		AutoposeExample autoPoseEx = new AutoposeExample();
		autoPoseEx.createCriteria().andBrandnameEqualTo(carBrand);
		 int count = autoPoseMapper.countByExample(autoPoseEx);
		if(count<=0){
			return DatasetBuilder.fromDataSimple(false);
		}
		return DatasetBuilder.fromDataSimple(true);
	}
}
