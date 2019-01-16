package com.emate.shop.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.emate.shop.business.api.GsBranchService;
import com.emate.shop.business.model.GsBranch;
import com.emate.shop.business.model.GsBranchExample;
import com.emate.shop.business.model.SurveyCenter;
import com.emate.shop.business.model.SurveyCenterBranch;
import com.emate.shop.business.model.SurveyCenterBranchExample;
import com.emate.shop.business.model.SurveyCenterExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.mapper.GsBranchMapper;
import com.emate.shop.mapper.SurveyCenterBranchMapper;
import com.emate.shop.mapper.SurveyCenterMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


@Service
public class GsBranchServiceImpl implements GsBranchService{

	
	@Resource
	private SurveyCenterMapper surveyCenterMapper;
	
	@Resource
	private GsBranchMapper gsBranchMapper;
	
	@Resource
	private SurveyCenterBranchMapper surveyCenterBranchMapper;
	
	@Override
	@Read
	public DatasetList<Map<String,Object>> h5GsDataList(String city) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		
		if("惠州".equals(city)){
			//车行信息
			SurveyCenterExample surveyCenterEx = new SurveyCenterExample();
			surveyCenterEx.or().andCityEqualTo(city);
			List<SurveyCenter> surveyCenters = surveyCenterMapper.selectByExample(surveyCenterEx);
			if(!surveyCenters.isEmpty()){
				for(SurveyCenter surveyCenter:surveyCenters){
					Map<String, Object> result = new HashMap<String,Object>();
					result.put("id", surveyCenter.getId());
					result.put("name", surveyCenter.getName());
					result.put("address", surveyCenter.getAddress());
					result.put("position", surveyCenter.getCenterPosition());
					result.put("cooperation", surveyCenter.getCooperation());//该车行是否与国寿合作
					result.put("type", "2");//2:车行;0:定损中心;1:营业网点;
					results.add(result);
				}
			}
		}else{
			//车行信息
			SurveyCenterExample surveyCenterEx = new SurveyCenterExample();
			surveyCenterEx.or().andCityEqualTo("广州");
			List<SurveyCenter> surveyCenters = surveyCenterMapper.selectByExample(surveyCenterEx);
			if(!surveyCenters.isEmpty()){
				for(SurveyCenter surveyCenter:surveyCenters){
					Map<String, Object> result = new HashMap<String,Object>();
					result.put("id", surveyCenter.getId());
					result.put("name", surveyCenter.getName());
					result.put("address", surveyCenter.getAddress());
					result.put("position", surveyCenter.getCenterPosition());
					result.put("cooperation", surveyCenter.getCooperation());//该车行是否与国寿合作
					result.put("type", "2");//2:车行;0:定损中心;1:营业网点;
					results.add(result);
				}
			}
			//定损中心,营业网点
			GsBranchExample gsBranchEx = new GsBranchExample();
			List<GsBranch> gsBranchs = gsBranchMapper.selectByExample(gsBranchEx);
			if(!gsBranchs.isEmpty()){
				for(int i=0;i<gsBranchs.size();i++){
					for(int j = gsBranchs.size()-1;j>i;j--){
						GsBranch gsBranch = gsBranchs.get(i);
						GsBranch gsBranch2 = gsBranchs.get(j);
						if(gsBranch.getType()==gsBranch2.getType()
								&&gsBranch.getPosition().equals(gsBranch2.getPosition())){
							StringBuffer sb = new StringBuffer();
							sb.append(gsBranch.getName())
							.append(",").append(gsBranch2.getName());
							gsBranch.setName(sb.toString());
							gsBranchs.remove(j);
						}
					}
				}
				for(GsBranch gsBranch :gsBranchs){
					Map<String, Object> result = new HashMap<String,Object>();
					result.put("name", gsBranch.getName());
					result.put("address", gsBranch.getAddress());
					result.put("position", gsBranch.getPosition());
					result.put("type", gsBranch.getType());//0:定损中心;1:营业网点;2:车行;
					results.add(result);
				}
			}
		}
		
		return DatasetBuilder.fromDataList(results);
	}
	
	@Override
	@Read
	public DatasetSimple<Map<String,Object>> h5SurveyCenter(Long id) {
		SurveyCenter surveyCenter = surveyCenterMapper.selectByPrimaryKey(id);
		
		SurveyCenterBranchExample surveyCenterBranchEx = new SurveyCenterBranchExample();
		surveyCenterBranchEx.or().andSurveyCenterIdEqualTo(id);
		List<SurveyCenterBranch> surveyCenterBranchs = surveyCenterBranchMapper.selectByExample(surveyCenterBranchEx);
	
		SurveyCenterBranch surveyCenterBranch = new SurveyCenterBranch();
		surveyCenterBranch.setCreateTime(surveyCenter.getUpdateTime());
		surveyCenterBranch.setUpdateTime(surveyCenter.getCreateTime());
		surveyCenterBranch.setLossRation(surveyCenter.getLossRation());
		surveyCenterBranch.setPremium(surveyCenter.getPremium());
		surveyCenterBranch.setReplaceRate(surveyCenter.getReplaceRate());
		surveyCenterBranch.setYear("2016");
		surveyCenterBranchs.add(surveyCenterBranch);
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("id", surveyCenter.getId());
		result.put("address", surveyCenter.getAddress());
		result.put("belongGroup", surveyCenter.getBelongGroup());
		result.put("centerPosition", surveyCenter.getCenterPosition());
		result.put("city", surveyCenter.getCity());
		result.put("cooperation", surveyCenter.getCooperation());
		result.put("cooperationTime", surveyCenter.getCooperationTime());
		result.put("mainCab", surveyCenter.getMainCab());
		result.put("name", surveyCenter.getName());
		result.put("region", surveyCenter.getRegion());
		result.put("yewuyuan", surveyCenter.getYewuyuan());
		result.put("zhigongsi", surveyCenter.getZhigongsi());
		result.put("createTime", surveyCenter.getCreateTime());
		result.put("updateTime", surveyCenter.getUpdateTime());
		result.put("surveyCenterBranch", surveyCenterBranchs);
		
		return DatasetBuilder.fromDataSimple(result);
	}

}
