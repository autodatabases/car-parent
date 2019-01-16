package com.emate.shop.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.emate.shop.business.model.Counterman;
import com.emate.shop.business.model.CountermanCareer;
import com.emate.shop.business.model.CountermanGood;
import com.emate.shop.business.model.DriverOrderSheng;
import com.emate.shop.business.model.OilAccountRecharge;
import com.emate.shop.business.model.OilOrders;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.business.model.OilcardOrderRelation;
import com.emate.shop.business.model.ScoreChannel;

public interface DefinedMapper {
	//查询车型最大id
    long queryAutoInfoMaxId();
    
    //查询配件最大id
    long queryAutoPartsMaxId();
    
    //查询保单交易额
    Float queryTotalTradeAmount();
    
    //查询业务员每月保单保费总额
    BigDecimal queryTotalMoneyBycounterman(Map<String,Object> map);
    
    //更新countermanCareer表
    int updateCountermanCareer(CountermanCareer countermanCareer);
    
    //更新counterman表
    int updateCounterman(Counterman counterman);
    
    //更新counterman_good表
    int updateCountermanGood(CountermanGood countermanGood);
    
    //删除ScoreChannnel表
    int delScoreChannel(ScoreChannel scoreChannel);
    
    //删除CarwashConfig表
    int delCarwashConfig( @Param("cityName")String cityName);
    
    //查询国寿网点列表
    List<String> queryDotList(@Param("dotName")String dotName);
    
    //查询某个月各个中支每天的订单量
    List<Map<String,Object>> queryOrderAnalysisList(Map<String,Object> map);
    
    //查询油卡统计
    Float getOilMoneySummary1();
    
    //查询油卡统计
    Float getOilMoneySummary2();
    
    //查询油卡统计
    Float getOilMoneySummary3();
    
    //查询该月导入该城市保单的下的各类型订单的数量
    List<Map<String,Object>> queryOrderNumByType(List<String> chePais);
    
    //该月该城市下各类型订单的数量,金额 
    List<Map<String, Object>> queryOrderData(Map<String, Object> map);
    
    //该月该城市下各类型订单的数量,金额
    List<Map<String,Object>> queryOrderDataone(Map<String,Object> map);
    
    //该月该城市下各类型订单的数量,金额
    List<Map<String,Object>> queryOrderDatatwo(Map<String,Object> map);
    
    //该月该城市下各类型订单的数量,金额
    List<Map<String,Object>> queryOrderDatathree(Map<String,Object> map);
    
    //该月该城市下各类型订单的数量,金额
    List<Map<String,Object>> queryOrderDatafour(Map<String,Object> map);
    
    //该月该城市下各类型订单的数量,金额
    List<Map<String,Object>> queryOrderDatafive(Map<String,Object> map);
    
    //该月该城市下各类型订单的数量,金额
    List<Map<String, Object>> queryOrderDatasix(Map<String, Object> map);
    
    
    //该月该城市下各类型订单的数量,金额
    int updateAgentData(Map<String,Object> map);
    
    //查询业务员的积分变动
    List<Map<String,Object>> queryScore(Map<String,Object> map);
    
    //根据手机号查询车牌信息
    List<String> queryLicense(@Param("userPhone") String userPhone);
    
    //根据手机号查询车牌信息
    List<Map<String,String>> queryPhone(List<String> chepais);
    
    //根据油卡用户手机号查询充值秘钥
    List<String> queryRechargeCode(@Param("userPhone")String userPhone);
    
    //查询国寿网点列表
    List<String> queryUnfinishOilLog(Date date);
    
    //查询国寿网点列表
    List<Map<String,Object>> queryExpiredUserInfo(Date date);
    
    //更新代驾订单的预约时间
    int updateDriverOrder(Map<String,Object> map);
    
    //开通油卡信息
    int updateOilRechargeCodeTwo( @Param("startNum")String startNum,@Param("endNum")String endNum,@Param("status") String status);
    
    //查询最大批次号
    int findMaxBatchNum( @Param("openTime")String openTime);
    
    //查询油卡统计
    List<Map<String,Object>> queryOilCount(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("batchNum") Integer batchNum);
    
    //查询油卡统计2
    List<Map<String,Object>> queryOilCountTwo(@Param("startTime") Date startTime,@Param("endTime")Date endTime,@Param("batchNum") Integer batchNum);
    
    //查询最大批次号
    String queryRoleByName( @Param("adminId")Long adminId);
    
    //添加账户油卡详情
    int insertAccount(List<OilAccountRecharge> accountList);
    
    OilUser queryOilUserbyId( @Param("id")Long id);
    
    int updateAccount(List<Map<String,Object>> oilAccount);
    
    int updateOilCardOrder(List<OilcardOrderRelation> oilOrderList);
    //查询做卡二级表的最大序号
    String queryOilMakeOrderMaxEndCode();
    
    //留存统计
    List<Map<String,Object>> oilCardLiucunList(@Param("startTime") Date startTime,@Param("endTime")Date endTime);
    
    //留存统计
    List<Map<String,Object>> exportOilCardLiucun(@Param("startTime") Date startTime,
    		@Param("endTime")Date endTime,@Param("supplier")String supplier,
    		@Param("address")String address,@Param("money")String money);
    //查询子账户
    OilAccountRecharge queryOilAccountbyId( @Param("id") Long id);
    
    //查询订单
    OilOrders queryOilOrdersbyId( @Param("id") Long id);
    
    //查询订单
    DriverOrderSheng queryDriverOrdersbyId( @Param("id") Long id);
}