package com.emate.shop.business.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.AutoPartService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.AutoPartRelation;
import com.emate.shop.business.model.AutoPartRelationExample;
import com.emate.shop.business.model.AutoParts;
import com.emate.shop.business.model.AutoPartsExample;
import com.emate.shop.business.model.AutoPartsExample.Criteria;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.AutoInfoMapper;
import com.emate.shop.mapper.AutoPartRelationMapper;
import com.emate.shop.mapper.AutoPartsMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class AutoPartServiceImpl implements AutoPartService{

	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private AutoPartsMapper autoPartsMapper;
	
	@Resource
	private AutoPartRelationMapper autoPartRelationMapper;
	
	@Resource
	private AutoInfoMapper autoInfoMapper;
	
	@Override
	public DatasetList<AutoParts> autoPartList(AutoParts autoPart, Integer pageNo, Integer pageSize) {
		AutoPartsExample ex = new AutoPartsExample();
		com.emate.shop.business.model.AutoPartsExample.Criteria c = ex.createCriteria();
		if(autoPart.getPartsTypeId()!=0){
			c.andPartsTypeIdEqualTo(autoPart.getPartsTypeId());
		}
		if(StringUtils.isNotEmpty(autoPart.getProductCode())){
			c.andProductCodeLike("%"+autoPart.getProductCode()+"%");
		}
		if(StringUtils.isNotEmpty(autoPart.getBrand())){
			c.andBrandEqualTo(autoPart.getBrand());
		}
		ex.setOrderByClause(AutoPartsExample.CREATE_TIME_DESC);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, autoPartsMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		return DatasetBuilder.fromDataList(autoPartsMapper.selectByExample(ex), page.createPageInfo());
	}

	
	@Transactional
	@Write
	@Override
	public DatasetSimple<Map<String, String>> addAutoPart(AutoParts autoPart) {
		Map<String, String> result = new HashMap<String, String>();
		autoPart.setId(definedMapper.queryAutoPartsMaxId()+1);
		autoPart.setCreateTime(new Date());
		autoPart.setUpdateTime(new Date());
		if(autoPart.getPartsTypeId()==2){//机油滤清器
			autoPart.setPartsType("机油滤清器");
			if("MAHLE".equals(autoPart.getBrand())){
				autoPart.setBrand("马勒（MAHLE）");
				autoPart.setDetailName("马勒（MAHLE）机油滤清器 " + autoPart.getProductCode());
				autoPart.setPic("peijian_image\\jilv\\MALEMAHLE_OC555.jpg");
			}else if("MANNFILTER".equals(autoPart.getBrand())){
				autoPart.setBrand("曼牌（MANNFILTER）");
				autoPart.setDetailName("曼牌（MANNFILTER）机油滤清器 " + autoPart.getProductCode());
				autoPart.setPic("peijian_image\\jilv\\MANPAIMANNFILTER_HU816X.jpg");
			}else if("BOSCH".equals(autoPart.getBrand())){
				autoPart.setBrand("博世（BOSCH）");
				autoPart.setDetailName("博世（BOSCH）机油滤清器 " + autoPart.getProductCode());
				autoPart.setPic("peijian_image\\jilv\\BOSHIBOSCH_0986AF0026.jpg");
			}
		}else if(autoPart.getPartsTypeId()==25){
			autoPart.setPartsType("机油");
			if("Castrol".equals(autoPart.getBrand())){
				autoPart.setBrand("嘉实多（Castrol）");
				autoPart.setDetailName("嘉实多（Castrol）机油 " + autoPart.getProductCode());
				autoPart.setPic("peijian_image\\jiyou\\JIASHIDUOCastrol_0W-40.jpg");
			}else{
				throw new BusinessException("暂时不支持其他品牌机油添加！");
			}
		}
		if(autoPartsMapper.insertSelective(autoPart)!=1){
			throw new BusinessException("添加配件失败！");
		}
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String, String>> delteAutoPart(Long autoPartId) {
		AutoParts autoPart = autoPartsMapper.selectByPrimaryKey(autoPartId);
		if(autoPart == null){
			throw new BusinessException("删除配件失败！配件不存在");
		}
		AutoPartRelationExample relationEx = new AutoPartRelationExample();
		relationEx.createCriteria().andMatchIdLike("%"+autoPartId+"%");
		List<AutoPartRelation>  relist = autoPartRelationMapper.selectByExample(relationEx);
		if(!relist.isEmpty()){
			throw new BusinessException("删除配件失败！配件存在关联关系");
		}
		autoPartRelationMapper.selectByExample(relationEx);
		
		if(autoPartsMapper.deleteByPrimaryKey(autoPartId)!=1){
			throw new BusinessException("删除配件失败！");
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Map<String,String>> matchAutoParts(Long autoId,Long autoPartId,Integer type){
		if(autoInfoMapper.selectByPrimaryKey(autoId)==null){
			throw new BusinessException("新增关联关系失败，车型不存在！");
		}
		AutoPartsExample partsExample = new AutoPartsExample();
		Criteria criteria = partsExample.createCriteria();
		criteria.andIdEqualTo(autoPartId);
		criteria.andPartsTypeIdEqualTo(Long.valueOf(type));
		List<AutoParts> list = autoPartsMapper.selectByExample(partsExample);
		if(list.isEmpty()){
			throw new BusinessException("新增关联关系失败，配件不存在！");
		}
		AutoPartRelationExample ex = new AutoPartRelationExample();
		ex.createCriteria().andAutoIdEqualTo(autoId).andPartTypeIdEqualTo(type);
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<AutoPartRelation>  relist = autoPartRelationMapper.selectByExample(ex);
		if(!relist.isEmpty()){
			AutoPartRelation re = relist.get(0);
			if(StringUtils.isEmpty(re.getMatchId())){
				re.setMatchId(String.valueOf(autoPartId));
			}else{
				if(re.getMatchId().indexOf(String.valueOf(autoPartId))!=-1){
					throw new BusinessException("新增关联关系失败，已经存在的关联！");
				}
				re.setMatchId(re.getMatchId()+","+String.valueOf(autoPartId));
			}
			if(autoPartRelationMapper.updateByPrimaryKeySelective(re)!=1){
				throw new BusinessException("新增关联关系失败，更新失败！");
			}
		}else{
			AutoPartRelation relation = new AutoPartRelation();
			relation.setAutoId(autoId);
			relation.setMatchId(String.valueOf(autoPartId));
			if(type == 2){
				relation.setPartType("机油滤清器");
			}else{
				relation.setPartType("机油");
			}
			relation.setPartTypeId(type);
			if(autoPartRelationMapper.insertSelective(relation)!=1){
				throw new BusinessException("新增关联关系失败，新增失败！");
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "success");
		return DatasetBuilder.fromDataSimple(result);
	}

}
