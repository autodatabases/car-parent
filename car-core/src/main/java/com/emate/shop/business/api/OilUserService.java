package com.emate.shop.business.api;

import java.util.List;
import java.util.Map;

import com.emate.shop.business.model.GyOilLog;
import com.emate.shop.business.model.GyPhoneLog;
import com.emate.shop.business.model.GyPhoneProduct;
import com.emate.shop.business.model.OilConfig;
import com.emate.shop.business.model.OilGoods;
import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.model.OilOrders;
import com.emate.shop.business.model.OilProvider;
import com.emate.shop.business.model.OilRechargeCode;
import com.emate.shop.business.model.OilTransLog;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.business.vo.OilGoodsTypeVo;
import com.emate.shop.datasource.Read;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

public interface OilUserService {

    /**
     * 根据用户名查询用户
     * @return
     */
    public DatasetSimple<OilUser> getOilUser(Long id);

    /**
     * 油卡用户登录
     * @param userName
     * @param smsCode
     * @param tokenCode
     * @param ip
     * @return
     */
    public DatasetSimple<OilUser> userLogin(String userName, String smsCode,
            String tokenCode, String ip);

    /**
     * 券码充值
     * @param code
     * @return
     */
    public DatasetSimple<String> recharge(Long userId, String rechargeCode);

    /**
     * 记录日志
     * @param code
     * @return
     */
    public DatasetSimple<String> addTransLog(OilTransLog log);

    /**
     * 更新日志
     * @param code
     * @return
     */
    public DatasetSimple<String> updateTransLog(OilTransLog log);

    public DatasetSimple<OilTransLog> queryLogByOrderNo(String orderNo);

    /**
     * 发送油卡
     * @param code
     * @return
     */
    public DatasetSimple<String> sendCard(OilRechargeCode code);

    /**
     * 更新备注信息
     * @param code
     * @return
     */
    public DatasetSimple<String> updateCardRemark(String remark, Long id);

    /**
     * 查询发卡记录
     * @param pageNo
     * @param pageSize
     * @param userPhone
     * @param status
     * @param startNum
     * @param endNum
     * @return
     */
    public DatasetList<Map<String, Object>> queryCodeList(Integer pageNo,
            Integer pageSize, String userPhone, String status, String startNum,
            String endNum, String phone);

    /**
     * 批量发卡
     * @param code
     * @return
     */
    public DatasetSimple<Map<String, Object>> batchSendCard(
            List<Map<String, Object>> params);

    /**
     * 查询用户充值列表
     * @param userId
     * @return
     */
    public DatasetList<OilTransLog> queryLogList(Long userId, String orderNo);

    /**
     * 刷单用户充值列表
     * @param userId
     * @return
     */
    public DatasetList<OilTransLog> queryPhoneLogList(Long userId,
            String orderNo, String userPhone, Integer pageNo, Integer pageSize);

    /**
     * 获取金额统计
     * @param userId
     * @return
     */
    public DatasetSimple<Map<String, String>> getMoneySummary();

    /**
     * 退还用户金额(充值失败时调用)
     * @param userId
     * @return
     */
    public DatasetSimple<String> modifyUserMoney(Long userId, Integer money,
            String orderNo, String orderType);

    /**
     * 修改服务商接口
     * @param oilConfig
     * @return
     */
    public DatasetSimple<OilConfig> updateOilConfig(OilConfig oilConfig);

    /**
     * 查询服务商接口
     * @return
     */
    public DatasetSimple<OilConfig> queryOilConfig();

    /**
     * 高阳讯飞充值成功记录日志
     * @param userId
     * @return
     */
    public DatasetSimple<Map<String, String>> updateGyRechargeLog(GyOilLog log);

    /**
     * 高阳讯飞查询订单
     * @param userId
     * @return
     */
    public DatasetSimple<GyOilLog> queryGyOrderByid(String orderid);

    /**
     * 查询下单成功,未回调的记录
     * @return
     */
    public void updateGyPhoneBy(String ordersarchUrl, String agentId,
            String source, String merchantkey, String backUrl);

    /**
     * 高阳手机充值log
     * @param log
     * @return
     */
    public DatasetSimple<Map<String, String>> addOrUpdateGyPhoneLog(
            GyPhoneLog log);

    /**
     * 高阳订单查询
     * @param orderNo
     * @return
     */
    public DatasetSimple<GyPhoneLog> queryGyLogByOrderNo(String orderNo);

    /**
     * 根据商品id查询高阳话费商品
     * @param prodId
     * @return
     */
    public DatasetSimple<Integer> queryGyProductByProdId(String prodId);

    /**
     * 增加高阳话费下架商品记录
     * @param log
     * @return
     */
    public DatasetSimple<Integer> addGyPhoneProduct(GyPhoneProduct product);

    /**
     * 删除高阳话费下架商品记录
     * @param log
     * @return
     */
    public DatasetSimple<Integer> delGyPhoneProduct(String prodId);

    /**
     * 查询高阳话费商品
     * @return
     */
    public DatasetList<GyPhoneProduct> queryGyProduct();

    /**
     * 查询油卡订单日志
     * @return
     */
    public DatasetList<Map<String, Object>> queryOilOrderLog(Integer pageNo,
            Integer pageSize, OilTransLog oilTransLog, Byte oilCompType,
            String oilCardNumber);

    /**
     * 更新油卡用户洗车次数
     * @param oilUser
     */
    public void updateOilUser(OilUser oilUser);

    /**
     * 导出油卡订单信息
     * @param date
     * @return
     */
    public DatasetList<Map<String, String>> exportOilExcel(String oilCompType,
            String date);

    /**
     * 导出账户充值信息
     * @param startCode
     * @param endCode
     * @return
     */
    public DatasetList<Map<String, String>> exportOilCodeExcel(String startCode,
            String endCode);

    /**
     * 更新油卡信息
     * @param startCode
     * @param endCode
     * @param address
     * @param supplier
     * @param buyer
     * @param remark
     * @param proposer
     * @return
     */
    public DatasetSimple<Map<String, String>> updateOilCard(String startCode,
            String endCode, String province, String address, String supplier, String buyer,
            String remark, String proposer);

    /**
     * 查询高阳油卡24小时未回调的油卡订单记录
     * @return
     */
    public void updateGyOilLogByTimer(String gyUrl, String merchantid,
            String key);

    /**
     * 查询欧飞话费10分钟未回调的话费订单记录
     * @return
     */
    public void updateOilOrdersByOfTimer();

    /**
     * 油卡批次列表接口
     * @param pageNo
     * @param pageSize
     * @param openTime
     * @param startNum
     * @param endNum
     * @return
     */
    public DatasetList<Map<String, Object>> queryOilBatchList(Integer pageNo,
            Integer pageSize, String openTime, String startNum, String endNum,
            String supplier);

    /**
     * 油卡明细列表接口
     * @param pageNo
     * @param pageSize
     * @param startNum
     * @param endNum
     * @param startCode
     * @param endCode
     * @return
     */
    public DatasetList<Map<String, Object>> queryOilDetailList(Integer pageNo,
            Integer pageSize, String startNum, String endNum, String startCode,
            String endCode);

    /**
     * 油卡核销列表接口
     * @param pageNo
     * @param pageSize
     * @param startNum
     * @param endNum
     * @param num
     * @param address
     * @param date
     * @param status
     * @return
     */
    public DatasetList<Map<String, Object>> queryOilCheckList(Integer pageNo,
            Integer pageSize, String startNum, String endNum, String num,
            String address, String date, String status, String batchDate,
            Integer batchNum);

    /**
     * 批量冻结或解冻油卡
     * @param startNum
     * @param endNum
     * @param num
     * @param status
     * @return
     */
    public DatasetSimple<Integer> freezeOilCard(String startNum, String endNum,
            String status);

    /**
     * 单独冻结或解冻油卡
     * @param startNum
     * @param endNum
     * @param num
     * @param status
     * @return
     */
    public DatasetSimple<Integer> freezeOilCardTwo(String num, String status);

    /**
     * 导出油卡核销信息列表
     * @param pageNo
     * @param pageSize
     * @param startNum
     * @param endNum
     * @param num
     * @param address
     * @param date
     * @param status
     * @param batchDate
     * @param batchNum
     * @return
     */
    public DatasetList<Map<String, String>> exportOilCheckList(String startNum,
            String endNum, String num, String address, String date,
            String status, String batchDate, Integer batchNum);

    /**
     * 更新批量备注信息
     * @param remark
     * @param id
     * @return
     */
    public DatasetSimple<String> updateBatchRemark(String remark, Long id);

    public DatasetList<Map<Integer, String>> batchLinkage(String openTime,
            String batchNum);

    /**
     * 导出单个用户油卡订单信息
     * @param userPhone
     * @param orderNo
     * @return
     */
    public DatasetList<Map<String, String>> exportUserOilExcel(String userPhone,
            String orderNo);

    /**
     * 根据起始和终止序号查询油卡数据
     * @param startCode
     * @param endCode
     * @return
     */
    public DatasetList<Map<String, Object>> findOilCard(Integer pageNo,
            Integer pageSize, String startCode, String endCode);

    /**
     * 油卡充值
     * @param userId
     * @param oilCardNo
     * @param money
     * @param goodId
     * @param mobile
     * @param cardType
     * @param ip
     * @return
     */
    public DatasetSimple<String> chargeOilCard(Long userId, String oilCardNo,
    		String goodId,String money, String ip,String mobile, String cardType);

    /**
     * 话费充值
     * @param userId
     * @param phone
     * @param money
     * @param userIp
     * @return
     */

    public DatasetSimple<String> chargeMobile(Long userId, String phone,
            String goodId,String money,String userIp);

    /**
     * 查询某张油卡的详细记录
     */
    public DatasetList<Map<String, Object>> queryOilcardOrderList(
            Integer pageNo, Integer pageSize, String cardNo);

    /**
     * 新查询油卡订单记录
     * @param pageNo
     * @param pageSize
     * @param userPhone
     * @param orderNo
     * @return
     */
    public DatasetList<Map<String, Object>> queryNewOilOrder(Integer pageNo,
            Integer pageSize, String userPhone, String orderNo,
            String orderType,String cardNo);

    /**
     * 新查询油卡订单记录子订单
     * @param pageNo
     * @param pageSize
     * @param userPhone
     * @param orderNo
     * @return
     */
    public DatasetList<Map<String, Object>> queryNewChildOilOrder(
            String parentOrderNo, String orderType);

    /**
     * h5查询油卡订单记录
     * @param userId
     * @param orderNo
     * @param userPhone
     * @param pageNo
     * @param pageSize
     * @return
     */
    public DatasetList<Map<String, Object>> queryOilOrders(Long userId,
            String orderNo, String userPhone, String pageNo, String pageSize,String chargeType);

    /**
     * 油卡定时过期
     * @param
     * @return Boolean
     */
    public void checkOilCardStatus();
    
    /**
     * 欧飞对账，导出欧飞油卡和话费接口
     * @param date
     * @return
     */
    public DatasetList<Map<String, String>> exportOilExcelTwo(String date);
    
    /**
     * 单个用户导出欧飞油卡和花费接口
     * @param phone
     * @return
     */
    public DatasetList<Map<String, String>> exportOilExcelWithPhone(String phone);
    
    /**
     * 查询追电科技用户信息表
     * @param pageNo
     * @param pageSize
     * @param phone
     * @return
     */
    public DatasetList<OilUser> oilUserList(Integer pageNo,Integer pageSize,String phone);
    
    /**
     * 查询欧飞用户信息表
     * @param pageNo
     * @param pageSize
     * @param company
     * @return
     */
    public DatasetList<OilProvider> oufeiUserInfoList(Integer pageNo,Integer pageSize,String company);
    
    /**
     * 添加欧飞账户
     * @param userName
     * @param userPassword
     * @param company
     * @param status
     * @return
     */
    public DatasetSimple<String> addOilProvider(String userName,String userPassword,
    		String company);
    
    /**
     * 添加欧飞账户
     * @param userName
     * @param userPassword
     * @param company
     * @param status
     * @return
     */
    public DatasetSimple<String> updateOilProvider(String providerId,String userPassword);
    
    /**
     * 导出昨日消费油卡信息
     * @return
     */
    public DatasetList<Map<String, String>> exportOilCardOrderExcel(String date);
    
    /**
     * 留存统计列表
     * @param pageNo
     * @param pageSize
     * @param date
     * @return
     */
    public DatasetList<Map<String,Object>> oilCardLiucunList(Integer pageNo,Integer pageSize,String date);
    
    /**
     * 根据开通时间导出需要的油卡信息
     * @param date
     * @return
     */
    public DatasetList<Map<String, String>> exportOilCardLiucun(String date,
    		String supplier,String address,String money);
    /**
     * 手机流量充值
     * @param userId
     * @param phone
     * @param flowValue
     * @param userIp
     * @return
     */
    public DatasetSimple<String> chargeFlow(Long userId, String phone,String goodId,String perValue,
    		String userIp);
    
    /**
     * 流量充值回调
     * @param ret_code
     * @param err_msg
     * @param sporder_id
     * @param ordersuccesstime
     */
    public void ofBack(String ret_code, String err_msg, String sporder_id,
            String ordersuccesstime);
    
    /**
     * 查询订单下拉列表
     * @return
     */
    @Read
    public DatasetList<OilGoodsTypeVo> queryOilGoodsTypeList();
    
    /**
     * 获取视频会员卡和优惠券
     * @param userId
     * @param goodId
     * @param money
     * @param userIp
     * @return
     */
    public DatasetSimple<Map<String,String>> getCardPassword(Long userId, String goodId,String money,
    		String userIp,String goodType);
    
    /**
     * 获取会员卡券，优惠券的订单详情信息
     * @param orderNo
     * @return
     */
    @Read
    public DatasetSimple<Map<String, Object>> getCardInfo(String orderNo);
    /**
     * h5端根据商品类型查询追电商品
     * @param goodType
     * @return
     */
    public DatasetList<OilGoods> queryOilGoods(String goodType,String mobile);
    
    /**
     * 检验追电商品是否可用
     * @param phone
     * @param goodId
     * @param perValue
     * @param orderType
     * @return
     */
    public DatasetSimple<String> checkOilGood(String phone,String goodId,
    		String perValue,String orderType);
    
    /**
     * 查询商品终极商品类型
     * @return
     */
    public DatasetList<OilGoodsType> queryVideoCard();
    
    /**
     * 查询滴滴出行商品
     * @return
     */
    public DatasetList<Map<String,Object>> queryDidiGood();
    
}
