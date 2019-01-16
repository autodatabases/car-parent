package com.emate.shop.business.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.emate.shop.business.api.CountermanCareerService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.business.model.CountermanCareer;
import com.emate.shop.business.model.CountermanCareerExample;
import com.emate.shop.business.model.CountermanCareerExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CountermanCaiDotMapper;
import com.emate.shop.mapper.CountermanCareerMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 职场
 * @author dong
 *
 */
@Service
public class CountermanCareerServiceImpl implements CountermanCareerService{

	@Resource
	private CountermanCareerMapper countermanCareerMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private CountermanCaiDotMapper countermanCaiDotMapper;
	/**
	 * 查询所有职场
	 */
	@Read
	@Override
	public DatasetList<CountermanCareer> adminCountermanCareerList(Integer pageNo, Integer pageSize,
			CountermanCareer countermanCareer) {
		CountermanCareerExample ex = new CountermanCareerExample();
		Criteria criteria = ex.createCriteria();
		if(countermanCareer.getCareerName()!=null && !"".equals(countermanCareer.getCareerName())){
			criteria.andCareerNameLike("%"+countermanCareer.getCareerName()+"%");
		}
		if(countermanCareer.getCareerCode()!=null && !"".equals(countermanCareer.getCareerCode())){
			criteria.andCareerCodeLike("%"+countermanCareer.getCareerCode()+"%");
		}
		if(countermanCareer.getCaiDotName()!=null && !"".equals(countermanCareer.getCaiDotName())){
			if("萝岗".equals(countermanCareer.getCaiDotName())){
				countermanCareer.setCaiDotName("黄埔_萝岗");
			}
			if("南岗".equals(countermanCareer.getCaiDotName())){
				countermanCareer.setCaiDotName("黄埔_南岗");
			}
			criteria.andCaiDotNameEqualTo(countermanCareer.getCaiDotName());
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanCareerMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanCareer> countermanCareers = countermanCareerMapper.selectByExample(ex);
		for(CountermanCareer countermancareer :countermanCareers){
			if("黄埔_萝岗".equals(countermancareer.getCaiDotName())){
				countermancareer.setCaiDotName("萝岗");
			}
			if("黄埔_南岗".equals(countermancareer.getCaiDotName())){
				countermancareer.setCaiDotName("南岗");
			}
		}
		return DatasetBuilder.fromDataList(countermanCareers, page.createPageInfo());
	}
	/**
	 * 根据id查询职场
	 */
	@Read
	@Override
	public DatasetSimple<CountermanCareer> queryCountermanCareerById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("职场id不能为空");
		}
		CountermanCareer countermanCareer = countermanCareerMapper.selectByPrimaryKey(Long.parseLong(id));
		if(countermanCareer == null){
			throw new BusinessException("职场不存在！");
		}
		if("黄埔_萝岗".equals(countermanCareer.getCaiDotName())){
			countermanCareer.setCaiDotName("萝岗");
		}
		if("黄埔_南岗".equals(countermanCareer.getCaiDotName())){
			countermanCareer.setCaiDotName("南岗");
		}
		return DatasetBuilder.fromDataSimple(countermanCareer);
	}
	/**
	 * 添加或更新职场
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addOrUpdateCountermanCareer(CountermanCareer countermanCareer) {
		if(0==countermanCareer.getCaiDotId()){
			throw new BusinessException("财险网点为空");
		}
		CountermanCaiDot countermanCaiDot = countermanCaiDotMapper.selectByPrimaryKey(countermanCareer.getCaiDotId());
		if(Objects.isNull(countermanCaiDot)){
			throw new BusinessException("查询不到该财险网点");
		}
		countermanCareer.setCaiDotName(countermanCaiDot.getDotName());
		if(countermanCareer.getId()==null){
			CountermanCareerExample ex = new CountermanCareerExample();
			ex.createCriteria().andCareerCodeEqualTo(countermanCareer.getCareerCode());
			List<CountermanCareer> countermanCareers = countermanCareerMapper.selectByExample(ex);
			if(!countermanCareers.isEmpty()){
				throw new BusinessException("该职场已存在");
			}
			countermanCareer.setCreateTime(new Date());
			countermanCareer.setUpdateTime(new Date());
			if(countermanCareerMapper.insertSelective(countermanCareer)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
		}else{
			CountermanCareerExample ex = new CountermanCareerExample();
			ex.createCriteria().andCareerCodeEqualTo(countermanCareer.getCareerCode());
			List<CountermanCareer> countermanCareers = countermanCareerMapper.selectByExample(ex);
			if(!countermanCareers.isEmpty()){
				if(!countermanCareers.get(0).getId().equals(countermanCareer.getId())){
					throw new BusinessException("该职场代码已被存在");
				}
			}
			countermanCareer.setUpdateTime(new Date());
			if(countermanCareerMapper.updateByPrimaryKeySelective(countermanCareer)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
		}
		throw new BusinessException("添加或修改职场信息失败！");
	}
	/**
	 * 删除职场
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> deleteCountermanCareerById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("职场id不能为空");
		}
		countermanCareerMapper.deleteByPrimaryKey(Long.parseLong(id));
		return DatasetBuilder.fromDataSimple(true);
	}
}
