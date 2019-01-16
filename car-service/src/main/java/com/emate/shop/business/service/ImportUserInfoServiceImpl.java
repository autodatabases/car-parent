package com.emate.shop.business.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.emate.shop.business.api.ImportUserInfoService;
import com.emate.shop.business.model.AutoInfo;
import com.emate.shop.business.model.AutoInfoExample;
import com.emate.shop.business.model.AutoPartRelation;
import com.emate.shop.business.model.AutoPartRelationExample;
import com.emate.shop.business.model.AutoParts;
import com.emate.shop.business.model.AutoPartsExample;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AutoInfoMapper;
import com.emate.shop.mapper.AutoPartRelationMapper;
import com.emate.shop.mapper.AutoPartsMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 导入保单用户
 * @author dong
 *
 */
@Service
public class ImportUserInfoServiceImpl implements ImportUserInfoService{
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private AutoPartRelationMapper autoPartRelationMapper;
	
	@Resource
	private AutoPartsMapper autoPartsMapper;
	
	@Resource
	private AutoInfoMapper autoInfoMapper;
	
	
	
	@Override
	public DatasetList<ImportUserInfo> matchChe() {
		List<ImportUserInfo> arrayList = new ArrayList<ImportUserInfo>();
		new Thread(new Runnable() {
			public void run() {
				ImportUserInfoExample example = new ImportUserInfoExample();
				com.emate.shop.business.model.ImportUserInfoExample.Criteria cri = example.createCriteria();
				cri.andChePaiNotLike("%no%");
				cri.andSellerNotLike("%比亚迪%");
				Long times1=System.currentTimeMillis();
				List<ImportUserInfo> list = importUserInfoMapper.selectByExample(example);
				System.out.println((System.currentTimeMillis()-times1));
				if(list.isEmpty()){
					throw new BusinessException("无保单");
				}
				int num1=0;
				int num2=0;
				int num3=0;
				Long times2=System.currentTimeMillis();
				for (int i = 0; i < list.size(); i++) {
					ImportUserInfo info = list.get(i);
					if(info!=null){
						AutoInfo  autoInfo = canMatchAuto(info);
						if(autoInfo==null){
							num1++;
							System.out.println("无车型:"+info.getChePai()+":"+info.getAutoBrand()+":"+info.getAutoType());
							info.setChePai(info.getChePai()+"_nocar");
							importUserInfoMapper.updateByPrimaryKeySelective(info);
						}else{
							if(matchOilProductNew(autoInfo).getList().isEmpty()){
								num2++;
								System.out.println("无机油:"+info.getChePai()+":"+info.getAutoBrand()+":"+info.getAutoType());
								info.setChePai(info.getChePai()+"_nooil");
								importUserInfoMapper.updateByPrimaryKeySelective(info);
							}
							if(matchJilvProductNew(autoInfo).getList().isEmpty()){
								num3++;
								System.out.println("无机滤:"+info.getChePai()+":"+info.getAutoBrand()+":"+info.getAutoType());
								info.setChePai(info.getChePai()+"_nojilv");
								importUserInfoMapper.updateByPrimaryKeySelective(info);
							}
						}
						}
				}
				System.out.println((System.currentTimeMillis()-times2));
				System.out.println(num1);
				System.out.println(num2);
				System.out.println(num3);
			}
		}).start();
		return DatasetBuilder.fromDataList(arrayList);
	}
	/**
	 * 查看用户是否能够匹配车型
	 * @param importUserInfo
	 * @return
	 */
	private AutoInfo canMatchAuto(ImportUserInfo importUserInfo){
		AutoInfoExample autoInfoEx = new AutoInfoExample();
		importUserInfo.setProductYear(importUserInfo.getProductYear().substring(0,4));
		if(importUserInfo.getEngineDisp().indexOf("L")!=-1
				|| importUserInfo.getEngineDisp().indexOf("l")!=-1){
			importUserInfo.setEngineDisp(importUserInfo.getEngineDisp().substring(0,importUserInfo.getEngineDisp().length()-1));
		}
		autoInfoEx.or()
		.andBrandEqualTo(importUserInfo.getAutoBrand())
		.andAutolineNameEqualTo(importUserInfo.getAutoType().toUpperCase())
		.andEngineDispLike("%"+importUserInfo.getEngineDisp()+"%")
		.andProductTimeEqualTo(importUserInfo.getProductYear());
		autoInfoEx.setOrderByClause(AutoInfoExample.ID_ASC);
		autoInfoEx.setLimitStart(0);
		autoInfoEx.setLimitEnd(1);
		List<AutoInfo>  list = autoInfoMapper.selectByExample(autoInfoEx);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	private DatasetList<Map<String, Object>> matchOilProductNew(AutoInfo autoInfo) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		Long autoId = autoInfo.getId();
		AutoPartRelationExample relationEx = new AutoPartRelationExample();
		relationEx.createCriteria().andAutoIdEqualTo(autoId).andPartTypeIdEqualTo(25);
		List<AutoPartRelation> re = autoPartRelationMapper.selectByExample(relationEx);
		if (re.size() <= 0) {
			return DatasetBuilder.fromDataList(results);
		}
		AutoPartRelation r = re.get(0);
		if (r.getMatchId() == null) {
			return DatasetBuilder.fromDataList(results);
		}
		String[] ids = r.getMatchId().split(",");
		List<Long> longIds = Arrays.asList(ids).stream()
				.map(Long::parseLong)
				.distinct()
				.collect(Collectors.toList());
		longIds.add(0L);
		AutoPartsExample partsExample = new AutoPartsExample();
		partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("嘉实多（Castrol）");
		partsExample.setLimitStart(0);
		partsExample.setLimitEnd(1);
		List<AutoParts> autoPartsList = autoPartsMapper.selectByExample(partsExample);
		if(autoPartsList.isEmpty()){
			return DatasetBuilder.fromDataList(results);
		}
		Map<String, Object> one = new HashMap<String, Object>();
		one.put("product", autoPartsList);
		one.put("amount", autoInfo.getOilAmount());
		results.add(one);
		return DatasetBuilder.fromDataList(results);
	}
	
	private DatasetList<Map<String, Object>> matchJilvProductNew(AutoInfo autoInfo) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		Long autoId = autoInfo.getId();
		AutoPartRelationExample relationEx = new AutoPartRelationExample();
		relationEx.createCriteria().andAutoIdEqualTo(autoId).andPartTypeIdEqualTo(2);
		relationEx.setLimitStart(0);
		relationEx.setLimitEnd(1);
		List<AutoPartRelation> re = autoPartRelationMapper.selectByExample(relationEx);
		if (re.size() <= 0) {
			return DatasetBuilder.fromDataList(results);
		}
		AutoPartRelation r = re.get(0);
		if (StringUtils.isEmpty(r.getMatchId())) {
			return DatasetBuilder.fromDataList(results);
		}
		String[] ids = r.getMatchId().split(",");
		if(ids.length<=0){
			return DatasetBuilder.fromDataList(results);
		}
		List<Long> longIds = new ArrayList<Long>();
		longIds.add(0L);
		Arrays.asList(ids).stream().forEach(id -> {
			longIds.add(Long.parseLong(id));
		});
		AutoPartsExample partsExample = new AutoPartsExample();
		partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("曼牌（MANNFILTER）");
		partsExample.setLimitStart(0);
		partsExample.setLimitEnd(1);
		List<AutoParts> autoPartsList = autoPartsMapper.selectByExample(partsExample);
		if(autoPartsList.isEmpty()){
			partsExample.clear();
			partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("马勒（MAHLE）");
			autoPartsList = autoPartsMapper.selectByExample(partsExample);
		}
		if(autoPartsList.isEmpty()){
			partsExample.clear();
			partsExample.createCriteria().andIdIn(longIds).andBrandEqualTo("博世（BOSCH）");
			autoPartsList = autoPartsMapper.selectByExample(partsExample);
		}
		if(autoPartsList.isEmpty()){
			partsExample.clear();
			partsExample.createCriteria().andIdIn(longIds);
			autoPartsList = autoPartsMapper.selectByExample(partsExample);
		}
		if(autoPartsList.isEmpty()){
			return DatasetBuilder.fromDataList(results);
		}
		Map<String, Object> one = new HashMap<String, Object>();
		one.put("product", autoPartsList.get(0));
		results.add(one);
		return DatasetBuilder.fromDataList(results);
	}
	@Override
	public DatasetSimple<ImportUserInfo> getCarInfoByLicense(String license) {
		ImportUserInfoExample importUserInfoEx = new ImportUserInfoExample();
		//判断是否有新保单
		importUserInfoEx.createCriteria().andChePaiEqualTo(license);
		importUserInfoEx.setLimitStart(0);
		importUserInfoEx.setLimitEnd(1);
		List<ImportUserInfo>  newimportList = importUserInfoMapper.selectByExample(importUserInfoEx);
		if(newimportList.size()>0){
			return DatasetBuilder.fromDataSimple(newimportList.get(0));
		}
		return DatasetBuilder.fromDataSimple(null);
	}
	
	

}
