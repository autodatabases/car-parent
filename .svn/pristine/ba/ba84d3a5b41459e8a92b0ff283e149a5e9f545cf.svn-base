package com.emate.shop.business.service;

//import java.math.BigDecimal;

import java.util.Date;


import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.CountermanCaiDotService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanCaiDot;
import com.emate.shop.business.model.CountermanCaiDotExample;
import com.emate.shop.business.model.CountermanCaiDotExample.Criteria;
/*import com.emate.shop.business.model.CountermanCareer;
import com.emate.shop.business.model.CountermanCareerExample;*/
import com.emate.shop.business.model.CountermanExample;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.business.model.CountermanGoodExample;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CountermanCaiDotMapper;
import com.emate.shop.mapper.CountermanCareerMapper;
import com.emate.shop.mapper.CountermanGoodMapper;
import com.emate.shop.mapper.CountermanMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;


/**
 * 业务员商品信息
 * @author dong
 *
 */
@Service
public class CountermanCaiDotServiceImpl implements CountermanCaiDotService{

	@Resource
	private CountermanCaiDotMapper countermanCaiDotMapper;
	
	@Resource
	private DefinedMapper definedMapper;
	
	@Resource
	private CountermanMapper countermanMapper;
	
	@Resource
	private CountermanCareerMapper countermanCareerMapper;
	
	@Resource
	private CountermanGoodMapper countermanGoodMapper;
	
	@Resource
	private SystemUserMapper systemUserMapper;

	@Read
	@Override
	public DatasetList<CountermanCaiDot> adminCountermanCaiDotList(Integer pageNo,Integer pageSize,CountermanCaiDot countermanCaiDot,Long userId){
		//查询登录人姓名
		SystemUser systemUser = systemUserMapper.selectByPrimaryKey(userId);
		
		CountermanCaiDotExample ex = new CountermanCaiDotExample();
		Criteria criteria = ex.createCriteria();
		//根据拼音查询财险网点
		if(Objects.nonNull(systemUser)){
			if(!"admin".equals(systemUser.getUserName())){
				criteria.andPinYinEqualTo(systemUser.getUserName());
			}
		}
		//根据财险网点名称
		if(StringUtils.isNotEmpty(countermanCaiDot.getDotName())){
			criteria.andDotNameLike("%"+countermanCaiDot.getDotName()+"%");
		}
		ex.setOrderByClause(CountermanCaiDotExample.CREATE_TIME_DESC);
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, countermanCaiDotMapper.countByExample(ex));
		ex.setLimitStart(page.getStartRow());
		ex.setLimitEnd(page.getSize());
		List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(ex);
/*		for(CountermanCaiDot countermancaiDot :countermanCaiDots){
			if("黄埔_萝岗".equals(countermancaiDot.getDotName())){
				countermancaiDot.setDotName("萝岗");
			}
			if("黄埔_南岗".equals(countermancaiDot.getDotName())){
				countermancaiDot.setDotName("南岗");
			}
		}*/
		return DatasetBuilder.fromDataList(countermanCaiDots, page.createPageInfo());
	}

	@Read
	@Override
	public DatasetSimple<CountermanCaiDot> queryCountermanCaiDotById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("财险网点id不能为空");
		}
		CountermanCaiDot countermanCaiDot = countermanCaiDotMapper.selectByPrimaryKey(Long.parseLong(id));
		if(countermanCaiDot == null){
			throw new BusinessException("该财险网点不存在！");
		}
	/*	if("黄埔_萝岗".equals(countermanCaiDot.getDotName())){
			countermanCaiDot.setDotName("萝岗");
		}
		if("黄埔_南岗".equals(countermanCaiDot.getDotName())){
			countermanCaiDot.setDotName("南岗");
		}*/
		return DatasetBuilder.fromDataSimple(countermanCaiDot);
	}

	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> addOrUpdateCountermanCaiDot(CountermanCaiDot countermanCaiDot) {
/*		if("萝岗".equals(countermanCaiDot.getDotName())){
			countermanCaiDot.setDotName("黄埔_萝岗");
		}
		if("南岗".equals(countermanCaiDot.getDotName())){
			countermanCaiDot.setDotName("黄埔_南岗");
		}*/
		if(countermanCaiDot.getId()==null){
			CountermanCaiDotExample ex = new CountermanCaiDotExample();
			ex.createCriteria().andDotNameEqualTo(countermanCaiDot.getDotName());
			List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(ex);
			if(!countermanCaiDots.isEmpty()){
				throw new BusinessException("该财险网点名称已被使用");
			}
			countermanCaiDot.setCreateTime(new Date());
			countermanCaiDot.setUpdateTime(new Date());
			if(countermanCaiDotMapper.insertSelective(countermanCaiDot)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
		}else{
			CountermanCaiDotExample ex = new CountermanCaiDotExample();
			ex.createCriteria().andDotNameEqualTo(countermanCaiDot.getDotName());
			List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(ex);
			if(!countermanCaiDots.isEmpty()){
				if(!countermanCaiDots.get(0).getId().equals(countermanCaiDot.getId())){
					throw new BusinessException("该财险网点名称已被使用");
				}
			}
			//更新counterman_career(财险职场表)
/*			CountermanCareer countermanCareer = new CountermanCareer();
			countermanCareer.setCaiDotName(countermanCaiDot.getDotName());
			countermanCareer.setCaiDotId(countermanCaiDot.getId());
			definedMapper.updateCountermanCareer(countermanCareer);*/
			
			//更新counterman(寿险业务员表)
			Counterman counterman = new Counterman();
			counterman.setCaiDotId(countermanCaiDot.getId());
			counterman.setCaiDotName(countermanCaiDot.getDotName());
			definedMapper.updateCounterman(counterman);
			
			//更新counterman_good(商品表)
			CountermanGood countermanGood = new CountermanGood();
			countermanGood.setCaiDotId(countermanCaiDot.getId());
			countermanGood.setCaiDotName(countermanCaiDot.getDotName());
			definedMapper.updateCountermanGood(countermanGood);
			
/*			CountermanGoodExample countermanGoodEx = new CountermanGoodExample();
			countermanGoodEx.createCriteria().andCaiDotIdEqualTo(countermanCaiDot.getId());
			
			List<CountermanGood> countermanGoods = countermanGoodMapper.selectByExample(countermanGoodEx);
			if(!countermanGoods.isEmpty()){
				for(CountermanGood countermanGoodss : countermanGoods){
					BigDecimal goodScore = countermanGoodss.getGoodPrice().multiply(countermanCaiDot.getRatio()).setScale(0, BigDecimal.ROUND_UP);
					countermanGoodss.setGoodScore(goodScore);
					countermanGoodss.setUpdateTime(new Date());
					countermanGoodMapper.updateByPrimaryKey(countermanGoodss);
				}
			}*/
			
			countermanCaiDot.setUpdateTime(new Date());
			if(countermanCaiDotMapper.updateByPrimaryKeySelective(countermanCaiDot)==1){
				return DatasetBuilder.fromDataSimple(true);
			}
		}
		throw new BusinessException("添加或更新财险网点信息失败！");
	}
	
	
	/**
	 * 删除财险网点
	 */
	@Override
	@Write
	@Transactional
	public DatasetSimple<Boolean> deleteCountermanCaiDotById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("财险网点id不能为空");
		}
		//判断该财险网点下是否存在寿险业务员
		CountermanExample countermanEx = new CountermanExample();
		countermanEx.createCriteria().andCaiDotIdEqualTo(Long.parseLong(id)).andStatusEqualTo(Counterman.STATUS_0);
		List<Counterman> counterman = countermanMapper.selectByExample(countermanEx);
		if (!counterman.isEmpty()) {
			throw new BusinessException("该财险网点下有寿险业务员,请先修改业务员");
		}
		//判断该财险网点下是否存在财险职场
/*		CountermanCareerExample countermanCareerEx = new CountermanCareerExample();
		countermanCareerEx.createCriteria().andCaiDotIdEqualTo(Long.parseLong(id));
		List<CountermanCareer> countermanCareers = countermanCareerMapper.selectByExample(countermanCareerEx);
		if (!countermanCareers.isEmpty()) {
			throw new BusinessException("该财险网点下有财险职场,请先修改财险职场");
		}*/
		
		//判断该财险网点下是否存在商品
		CountermanGoodExample countermanGoodEx = new CountermanGoodExample();
		countermanGoodEx.createCriteria().andCaiDotIdEqualTo(Long.parseLong(id));
		List<CountermanGood> countermanGoods = countermanGoodMapper.selectByExample(countermanGoodEx);
		if (!countermanGoods.isEmpty()) {
			throw new BusinessException("该财险网点下有商品,请先修改商品");
		}
		countermanCaiDotMapper.deleteByPrimaryKey(Long.parseLong(id));
		return DatasetBuilder.fromDataSimple(true);
	}
	
	@Override
	@Read
	public DatasetList<CountermanCaiDot> queryCaiDotList(Long userId) {
		String userName = systemUserMapper.selectByPrimaryKey(userId).getUserName();
		CountermanCaiDotExample ex = new CountermanCaiDotExample();
		if(StringUtils.isNotEmpty(userName)){
			if(!"admin".equals(userName)){
				ex.createCriteria().andPinYinEqualTo(userName);
			}
		}
		List<CountermanCaiDot> countermanCaiDots = countermanCaiDotMapper.selectByExample(ex);
/*		for(CountermanCaiDot countermanCaiDot:countermanCaiDots){
			if("黄埔_萝岗".equals(countermanCaiDot.getDotName())){
				countermanCaiDot.setDotName("萝岗");
			}
			if("黄埔_南岗".equals(countermanCaiDot.getDotName())){
				countermanCaiDot.setDotName("南岗");
			}
		}*/
		return DatasetBuilder.fromDataList(countermanCaiDots);
	}
}
