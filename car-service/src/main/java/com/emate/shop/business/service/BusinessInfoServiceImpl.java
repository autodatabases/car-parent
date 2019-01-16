package com.emate.shop.business.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.BusinessInfoService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.BusinessInfo;
import com.emate.shop.business.model.BusinessInfoExample;
import com.emate.shop.business.model.BusinessInfoExample.Criteria;
import com.emate.shop.business.model.FixOrderExample;
import com.emate.shop.business.model.User;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.BusinessInfoMapper;
import com.emate.shop.mapper.FixOrderMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class BusinessInfoServiceImpl implements BusinessInfoService{
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private FixOrderMapper fixOrderMapper;
	
	/**
	 * 根据条件查询业务员
	 */
	@Override
	@Read
	public DatasetList<Map<String,Object>> adminBusinessInfoList(Integer pageNo, Integer pageSize,
			BusinessInfo businessInfo) {
		BusinessInfoExample busiEx=new BusinessInfoExample();
		Criteria c = busiEx.createCriteria();
		if(StringUtils.isNotEmpty(businessInfo.getBusinessCode())){
			c.andBusinessCodeEqualTo(businessInfo.getBusinessCode());
		}
		if(StringUtils.isNotEmpty(businessInfo.getBusinessName())){
			c.andBusinessNameLike("%"+businessInfo.getBusinessName()+"%");
		}
		if(StringUtils.isNotEmpty(businessInfo.getPhone())){
			c.andPhoneLike("%"+businessInfo.getPhone()+"%");
		}
		PaginationUtil page = new PaginationUtil(pageNo, pageSize, businessInfoMapper.countByExample(busiEx));
		busiEx.setLimitStart(page.getStartRow());
		busiEx.setLimitEnd(page.getSize());
		List<BusinessInfo> businessInfos = businessInfoMapper.selectByExample(busiEx);
		//重新定义结果
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		businessInfos.stream().forEach(busiInfo ->{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("id", busiInfo.getId());
			res.put("businessCode", busiInfo.getBusinessCode());
			res.put("businessName",busiInfo.getBusinessName());
			res.put("phone", busiInfo.getPhone());
			if(busiInfo.getUserId()!=null){
				res.put("status", "0");//已绑定
			}else{
				res.put("status", "1");//未绑定
			}
			result.add(res);
		});
		return DatasetBuilder.fromDataList(result, page.createPageInfo());
	}
	/**
	 * 根据id查询业务员
	 */
	@Override
	@Read
	public DatasetSimple<BusinessInfo> queryBusinessInfoById(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("传递业务员id为空");
		}
		BusinessInfo businessInfo = businessInfoMapper.selectByPrimaryKey(Long.valueOf(id));
		return DatasetBuilder.fromDataSimple(businessInfo);
	}
	/**
	 * 删除业务员
	 * @param id
	 * @return
	 */
	@Transactional
	@Write
	@Override
	public DatasetSimple<Map<String,String>> deleteBusinessInfo(String id) {
		if(StringUtils.isEmpty(id)){
			throw new BusinessException("传递id为空!!");
		}
		BusinessInfo info = businessInfoMapper.selectByPrimaryKey(Long.valueOf(id));
		if(info==null){
			throw new BusinessException("未查询到该业务员!!");
		}
		
		if(info.getUserId()!=null){
			//查询绑定的用户是否下过维修订单
			FixOrderExample fixOrderExample = new FixOrderExample();
			fixOrderExample.createCriteria().andBusinessIdEqualTo(info.getUserId());
			if(fixOrderMapper.countByExample(fixOrderExample)>0){
				throw new BusinessException("绑定该业务员的用户存在维修订单");
			}
			//若存在,就解绑
			User user = userMapper.selectByPrimaryKey(info.getUserId());
			if(user!=null){
				user.setUserType(User.USER_TYPE_1);
				if(userMapper.updateByPrimaryKey(user)!=1){
					throw new BusinessException("修改用户状态失败");
				};
			}
		}
		//删除此业务员
		if(businessInfoMapper.deleteByPrimaryKey(Long.valueOf(id))!=1){
			throw new BusinessException("删除业务员失败");
		}
		HashMap<String, String> hashMap = new HashMap<String,String>();
		return DatasetBuilder.fromDataSimple(hashMap);
	}

	@Transactional
	@Write
	@Override
	public DatasetSimple<Map<String,String>> addBusinessInfo(BusinessInfo businessInfo) {
		if(StringUtils.isEmpty(businessInfo.getBusinessCode())){
			throw new BusinessException("员工工号不能为空");
		}
		if(StringUtils.isEmpty(businessInfo.getBusinessName())){
			throw new BusinessException("员工姓名不能为空");
		}
		BusinessInfoExample infoExample = new BusinessInfoExample();
		Criteria createCriteria = infoExample.createCriteria();
		createCriteria.andBusinessCodeEqualTo(businessInfo.getBusinessCode());
		if(!(businessInfoMapper.selectByExample(infoExample).isEmpty())){
			throw new BusinessException("该工号已存在");
		}
		businessInfo.setCreateTime(new Date());
		businessInfo.setUpdateTime(new Date());
		if(businessInfoMapper.insertSelective(businessInfo)!=1){
			throw new BusinessException("添加业务员失败");
		};
		HashMap<String, String> hashMap = new HashMap<String,String>();
		return DatasetBuilder.fromDataSimple(hashMap);
	}
}
