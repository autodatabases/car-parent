package com.emate.shop.business.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.emate.shop.business.api.BrandService;
import com.emate.shop.business.model.Autobrand;
import com.emate.shop.business.model.AutobrandExample;
import com.emate.shop.business.model.Autopose;
import com.emate.shop.business.model.AutoposeExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.mapper.AutobrandMapper;
import com.emate.shop.mapper.AutoposeMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class BrandServiceImpl implements BrandService{

	@Resource
	private AutobrandMapper autobrandMapper;
	
	@Resource
	private AutoposeMapper autoposeMapper;
	

	@Read
	@Override
	public DatasetList<Autobrand> queryBrandList() {
		AutobrandExample ex = new AutobrandExample();
		return DatasetBuilder.fromDataList(autobrandMapper.selectByExample(ex));
	}

	@Read
	@Override
	public DatasetList<Autopose> queryAutoPoseByBrand(String brand) {
		AutoposeExample ex = new AutoposeExample();
		ex.createCriteria().andBrandnameEqualTo(brand);
		return DatasetBuilder.fromDataList(autoposeMapper.selectByExample(ex));
	}

	@Read
	@Override
	public DatasetList<Autopose> queryAotoPoseByKeyword(String keyword) {
		if(StringUtils.isEmpty(keyword)){
			return DatasetBuilder.fromMessageList("参数为空，无查询结果！");
		}
		keyword = "%"+keyword+"%";
		AutoposeExample ex = new AutoposeExample();
		ex.or().andFactorynameLike(keyword);
		String name = keyword;
		if(name.length()>0){
			name = name.substring(0, 1).toUpperCase()+name.substring(1);
		}
		ex.or().andBrandnameLike(name);
		ex.or().andAutolinenameLike(name);
		ex.or().andAutotypenameLike(name);
		return DatasetBuilder.fromDataList(autoposeMapper.selectByExample(ex));
	}
	
	public DatasetSimple<Autopose> queryAutoposeById(Long id){
		return DatasetBuilder.fromDataSimple(autoposeMapper.selectByPrimaryKey(id));
				
	}
}
