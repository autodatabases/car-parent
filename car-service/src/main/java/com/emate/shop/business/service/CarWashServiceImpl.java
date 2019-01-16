package com.emate.shop.business.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.CarWashService;
import com.emate.shop.business.model.CarWash;
import com.emate.shop.business.model.CarWashExample;
import com.emate.shop.business.model.CarWashSheng;
import com.emate.shop.business.model.CarWashShengExample;
import com.emate.shop.business.model.CarWashShengExample.Criteria;
import com.emate.shop.business.model.ImportUserInfo;
import com.emate.shop.business.model.ImportUserInfoExample;
import com.emate.shop.business.model.Orders;
import com.emate.shop.business.model.UserInfo;
import com.emate.shop.business.model.UserInfoExample;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CarWashMapper;
import com.emate.shop.mapper.CarWashShengMapper;
import com.emate.shop.mapper.ImportUserInfoMapper;
import com.emate.shop.mapper.OrderTraceMapper;
import com.emate.shop.mapper.OrdersMapper;
import com.emate.shop.mapper.UserInfoMapper;
import com.emate.shop.mapper.UserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.google.common.base.Objects;

@Service
public class CarWashServiceImpl implements CarWashService{

    @Resource
    StandardPasswordEncoder passwordEncoder;
    
	@Resource
	private CarWashMapper carWashMapper;
	
	@Resource
	private CarWashShengMapper carWashShengMapper;
    
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private OrderTraceMapper orderTraceMapper;
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private ImportUserInfoMapper importUserInfoMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	@Transactional
	@Write
	public DatasetSimple<CarWashSheng> addOrUpdateCarWash(CarWashSheng carWashSheng) {
		if(carWashSheng.getId()!=null){
			if(carWashShengMapper.updateByPrimaryKeySelective(carWashSheng)!=1){
				throw new BusinessException("更新发券记录失败！");
			}
		}else{
			if(carWashShengMapper.insertSelective(carWashSheng)!=1){
				throw new BusinessException("添加发券记录失败！");
			}
		}
		return DatasetBuilder.fromDataSimple(carWashSheng);
	}

	@Override
	@Transactional
	@Write
	public DatasetSimple<String> addXiCheOrder(Orders order) {
		//添加订单
		ordersMapper.insertSelective(order);
		//以下获取 user  更新洗车次数
		
		//根据userId查询userInfo表
		UserInfoExample ex = new UserInfoExample();
		ex.createCriteria().andUserIdEqualTo(order.getUserId());
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<UserInfo>  userInfoList = userInfoMapper.selectByExample(ex);
		if(userInfoList.isEmpty()){
			throw new BusinessException("用户不存在！");
		}
		//根据userId查询user表
		//User user = userMapper.selectByPrimaryKey(order.getId());
		UserInfo userInfo = userInfoList.get(0);
		//如果是没有导入车牌号的保单用户
		if(StringUtils.isEmpty(userInfo.getLicense())){ 
			throw new BusinessException("用户未绑定车牌号！");
		}
		//如果已存在保单用户,就根据车牌查询import_user_info表
		ImportUserInfo importUserInfo = null;
		ImportUserInfoExample importex = new ImportUserInfoExample();
		importex.createCriteria().andChePaiEqualTo(userInfo.getLicense());
		importex.setLimitStart(0);
		importex.setLimitEnd(1);
		List<ImportUserInfo> importUserInfos = importUserInfoMapper.selectByExample(importex);
		if(importUserInfos.isEmpty()){
			throw new BusinessException("没有导入车牌号的保单用户！");
		}
		importUserInfo = importUserInfos.get(0);
		if(importUserInfo.getXiecheTimes()<=0){
			//替换新的洗车次数
			importex.clear();
			importex.createCriteria().andChePaiEqualTo(userInfo.getLicense()+"_newyear");
			importex.setLimitStart(0);
			importex.setLimitEnd(1);
			importUserInfos = importUserInfoMapper.selectByExample(importex);
			if(!importUserInfos.isEmpty()){
				importUserInfo = importUserInfos.get(0);
			}
		}
		ImportUserInfo importUserInfoUpdate = new ImportUserInfo();
		importUserInfoUpdate.setId(importUserInfo.getId());
		importUserInfoUpdate.setXiecheTimes(importUserInfo.getXiecheTimes()-1);
		importUserInfoMapper.updateByPrimaryKeySelective(importUserInfoUpdate);
		return DatasetBuilder.fromDataSimple("ok");
	}
	
	@Override
	@Read
	public DatasetList<CarWashSheng> queryUnUsedSheng(Long userId,Integer washType) {
		CarWashShengExample ex = new CarWashShengExample();
		Criteria c = ex.createCriteria();
		c.andUserIdEqualTo(userId)
		.andFinishTimeIsNull()
		.andCouponExpirDateGreaterThanOrEqualTo(new Date());
		if(Objects.equal(1, washType)){
			c.andWashTypeEqualTo(washType);
		}else if(Objects.equal(0, washType)){
			c.andWashTypeEqualTo(CarWashSheng.WASH_TYPE_0);
		}
		ex.setOrderByClause(CarWashShengExample.CREATE_TIME_DESC);
		List<CarWashSheng> list = carWashShengMapper.selectByExample(ex);
		return DatasetBuilder.fromDataList(list);
	}
	
	@Override
	@Read
	public DatasetSimple<CarWashSheng> queryShengCouponByOrderNo(String orderNo,Integer washType,String status) {
		CarWashShengExample carWashShengEx = new CarWashShengExample();
		Criteria c = carWashShengEx.createCriteria();
		c.andOrderNoEqualTo(orderNo);
		if(Objects.equal(1, washType)){
			c.andWashTypeEqualTo(washType);
		}else if (Objects.equal(0, washType)){
			c.andWashTypeEqualTo(CarWashSheng.WASH_TYPE_0);
		}
		if("0".equals(status)){//0:未完成或解密失败的,1:全部
			c.andFinishTimeIsNull();
			c.andStatusNotEqualTo(CarWashSheng.STATUS_1);
		}
		List<CarWashSheng> list = carWashShengMapper.selectByExample(carWashShengEx);
		if(list.size()!=1){
			throw new BusinessException("查询数量不为1");
		}
		return DatasetBuilder.fromDataSimple(list.get(0));
	}
	
	@Override
	@Read
	public DatasetSimple<Integer> queryUsedSheng(Long userId, Integer washType) {
		CarWashShengExample ex = new CarWashShengExample();
		Criteria c = ex.createCriteria();
		c.andUserIdEqualTo(userId);
		c.andStatusEqualTo(CarWashSheng.STATUS_1);
		if(Objects.equal(0, washType)){
			c.andWashTypeEqualTo(washType);
		}else if(Objects.equal(1, washType)){
			c.andWashTypeEqualTo(washType);
		}
		int number = carWashShengMapper.countByExample(ex);
		
		return DatasetBuilder.fromDataSimple(number);
	}
	
	//------------------------------------------------------------------------
	@Override
	@Transactional
	@Write
	public DatasetSimple<CarWash> addOrUpdateCoupon(CarWash wash) {
		if(wash.getId()!=null){
			if(carWashMapper.updateByPrimaryKeySelective(wash)!=1){
				throw new BusinessException("更新发券记录失败！");
			}
		}else{
			if(carWashMapper.insertSelective(wash)!=1){
				throw new BusinessException("添加发券记录失败！");
			}
		}
		return DatasetBuilder.fromDataSimple(wash);
	}
	
	@Override
	public DatasetSimple<CarWash> queryByOrderNo(String orderNo) {
		CarWashExample ex = new CarWashExample();
		ex.createCriteria().andOrderNoEqualTo(orderNo);
		List<CarWash> list = carWashMapper.selectByExample(ex);
		if(list.size()!=1){
			throw new BusinessException("查询数量不为1");
		}
		return DatasetBuilder.fromDataSimple(list.get(0));
	}
	
	@Override
	public DatasetList<CarWash> queryUnUsed(Long userId) {
		CarWashExample ex = new CarWashExample();
		ex.createCriteria().andUserIdEqualTo(userId)
		.andNotifyTimeIsNull().andCouponExpirDateGreaterThanOrEqualTo(new Date());
		List<CarWash> list = carWashMapper.selectByExample(ex);
		return DatasetBuilder.fromDataList(list);
	}
}
