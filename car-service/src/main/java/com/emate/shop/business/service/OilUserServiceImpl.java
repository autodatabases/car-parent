package com.emate.shop.business.service;

import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.convert.LongToBooleanConverter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.emate.shop.business.api.OilUserService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.CarWashPay;
import com.emate.shop.business.model.CarWashPayExample;
import com.emate.shop.business.model.DriverOrderSheng;
import com.emate.shop.business.model.DriverOrderShengExample;
import com.emate.shop.business.model.GyOilLog;
import com.emate.shop.business.model.GyOilLogExample;
import com.emate.shop.business.model.GyPhoneLog;
import com.emate.shop.business.model.GyPhoneLogExample;
import com.emate.shop.business.model.GyPhoneProduct;
import com.emate.shop.business.model.GyPhoneProductExample;
import com.emate.shop.business.model.OilAccountRecharge;
import com.emate.shop.business.model.OilAccountRechargeExample;
import com.emate.shop.business.model.OilBatch;
import com.emate.shop.business.model.OilBatchExample;
import com.emate.shop.business.model.OilConfig;
import com.emate.shop.business.model.OilConfigExample;
import com.emate.shop.business.model.OilGoods;
import com.emate.shop.business.model.OilGoodsAddress;
import com.emate.shop.business.model.OilGoodsAddressExample;
import com.emate.shop.business.model.OilGoodsExample;
import com.emate.shop.business.model.OilGoodsType;
import com.emate.shop.business.model.OilGoodsTypeExample;
import com.emate.shop.business.model.OilLog;
import com.emate.shop.business.model.OilOrders;
import com.emate.shop.business.model.OilOrdersExample;
import com.emate.shop.business.model.OilProvider;
import com.emate.shop.business.model.OilProviderExample;
import com.emate.shop.business.model.OilRechargeCode;
import com.emate.shop.business.model.OilRechargeCodeExample;
import com.emate.shop.business.model.OilRecordwhiteList;
import com.emate.shop.business.model.OilRecordwhiteListExample;
import com.emate.shop.business.model.OilTransLog;
import com.emate.shop.business.model.OilTransLogExample;
import com.emate.shop.business.model.OilTransLogExample.Criteria;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.business.model.OilUserExample;
import com.emate.shop.business.model.OilcardOrderRelation;
import com.emate.shop.business.model.OilcardOrderRelationExample;
import com.emate.shop.business.vo.OilGoodsTypeVo;
import com.emate.shop.business.vo.gyvo.OilCardSearchResult;
import com.emate.shop.business.vo.ofvo.OufeiCardPassword;
import com.emate.shop.business.vo.ofvo.OufeiFlowGood;
import com.emate.shop.business.vo.ofvo.OufeiOrder;
import com.emate.shop.business.vo.ofvo.OufeiPhoneGood;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.CarWashPayMapper;
import com.emate.shop.mapper.DefinedMapper;
import com.emate.shop.mapper.DriverOrderShengMapper;
import com.emate.shop.mapper.GyOilLogMapper;
import com.emate.shop.mapper.GyPhoneLogMapper;
import com.emate.shop.mapper.GyPhoneProductMapper;
import com.emate.shop.mapper.OilAccountRechargeMapper;
import com.emate.shop.mapper.OilBatchMapper;
import com.emate.shop.mapper.OilCardConfigMapper;
import com.emate.shop.mapper.OilConfigMapper;
import com.emate.shop.mapper.OilGoodsAddressMapper;
import com.emate.shop.mapper.OilGoodsMapper;
import com.emate.shop.mapper.OilGoodsTypeMapper;
import com.emate.shop.mapper.OilLogMapper;
import com.emate.shop.mapper.OilOrdersMapper;
import com.emate.shop.mapper.OilProviderMapper;
import com.emate.shop.mapper.OilRechargeCodeMapper;
import com.emate.shop.mapper.OilRecordwhiteListMapper;
import com.emate.shop.mapper.OilTransLogMapper;
import com.emate.shop.mapper.OilUserMapper;
import com.emate.shop.mapper.OilcardOrderRelationMapper;
import com.emate.shop.mapper.RegionsMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.gaoyang.GaoYangOilCardUtil;
import com.emate.tools.gaoyang.GaoYangUtil;
import com.emate.tools.gaoyang.KeyedDigestMD5;
import com.emate.tools.oufei.OufeiOilUtil;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@PropertySource(value = "classpath:properties/oilcard.properties", ignoreResourceNotFound = true)
public class OilUserServiceImpl implements OilUserService {
	
	

    @Resource
    private Environment                environment;

    @Resource
    StandardPasswordEncoder            passwordEncoder;

    @Resource
    private OilUserMapper              oilUserMapper;

    @Resource
    private OilRechargeCodeMapper      oilRechargeCodeMapper;

    @Resource
    private OilTransLogMapper          oilTransLogMapper;

    @Resource
    private DefinedMapper              definedMapper;

    @Resource
    private OilConfigMapper            oilConfigMapper;

    @Resource
    private GyOilLogMapper             gyOilLogMapper;

    @Resource
    private GyPhoneLogMapper           gyPhoneLogMapper;

    @Resource
    private GyPhoneProductMapper       gyPhoneProductMapper;

    @Resource
    private OilBatchMapper             oilBatchMapper;

    @Resource
    private OilAccountRechargeMapper   oilAccountRechargeMapper;

    @Resource
    private OilCardConfigMapper        oilCardConfigMapper;

    @Resource
    private OilcardOrderRelationMapper oilcardOrderRelationMapper;

    @Resource
    private OilOrdersMapper            oilOrdersMapper;

    @Resource
    private CarWashPayMapper           carWashPayMapper;

    @Resource
    private DriverOrderShengMapper     driverOrderShengMapper;

    @Resource
    private OilRecordwhiteListMapper   oilRecordwhiteListMapper;

    @Resource
    private OilProviderMapper          oilProviderMapper;

    @Resource
    private OilLogMapper               oilLogMapper;

    @Resource
    private OilGoodsMapper             oilGoodsMapper;

    @Resource
    private OilGoodsTypeMapper         oilGoodsTypeMapper;
    
    @Resource
    private OilGoodsAddressMapper      oilGoodsAddressMapper;
    
    
    
    private static final String[] GDQT_STR = {"广东-其他","其他"};
    private static final List<String> GDQT_LIST = Arrays.asList(GDQT_STR);
    private static final String[] GDZS_STR = {"广东-赠送","赠送"};
    private static final List<String> GDZS_LIST = Arrays.asList(GDZS_STR);

    /**
     * 惠+车服用户登录
     */
    @Override
    @Write
    @Transactional
    public DatasetSimple<OilUser> userLogin(String userName, String smsCode,
            String tokenCode, String ip) {
        //先校验验证码
        try {
            if (StringUtils.isEmpty(tokenCode)) {
                throw new BusinessException("验证码不正确！");
            }
            if (!passwordEncoder.matches(smsCode + userName, tokenCode)) {
                throw new BusinessException("验证码不正确！");
            }
        } catch (Exception e) {
            throw new BusinessException("验证码不正确！");
        }
        OilUser user = queryUserByPhone(userName);
        if (user == null) {
            // 第一次扫码用户，自动注册
            OilUser newUser = new OilUser();
            newUser.setPhone(userName);
            newUser.setCreateTime(new Date());
            newUser.setMoney(new BigDecimal(0));
            newUser.setUpdateTime(new Date());
            if (oilUserMapper.insertSelective(newUser) != 1) {
                throw new BusinessException("注册用户失败！");
            }
            user = queryUserByPhone(userName);
            if (user == null) {
                throw new BusinessException("注册用户失败！");
            }
        }
        return DatasetBuilder.fromDataSimple(user);
    }

    private OilUser queryUserByPhone(String phone) {
        OilUserExample oilEx = new OilUserExample();
        oilEx.createCriteria().andPhoneEqualTo(phone);
        List<OilUser> users = oilUserMapper.selectByExample(oilEx);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<String> recharge(Long userId, String rechargeCode) {

        //查询账户
        OilUser user = definedMapper.queryOilUserbyId(userId);
        //账户不存在
        if (user == null) {
            throw new BusinessException("用户不存在!");
        }
        Log4jHelper.getLogger().info("账户充值：充值账号：" + user.getPhone() + ",当前余额："
                + user.getMoney().doubleValue() + ",消费的油卡卡密：" + rechargeCode);
        //校验卷码是否存在?
        OilRechargeCodeExample ex = new OilRechargeCodeExample();
        ex.createCriteria().andRechargeCodeEqualTo(rechargeCode);
        List<OilRechargeCode> codes = oilRechargeCodeMapper.selectByExample(ex);
        //不存在:
        if (codes.isEmpty()) {
            throw new BusinessException("充值码无效,请仔细核对充值码");
        }
        //存在但未激活
        OilRechargeCode code = codes.get(0);
        if (OilRechargeCode.STATUS_1.equals(code.getStatus())
                && Objects.isNull(code.getRechargeTime())) {
            throw new BusinessException("该卡片未激活,请联系客服");
        }
        //存在但已使用
        if (OilRechargeCode.STATUS_1.equals(code.getStatus())
                && Objects.nonNull(code.getRechargeTime())) {
            throw new BusinessException("该卡片已使用,请联系客服");
        }
        //存在但已冻结
        if (OilRechargeCode.STATUS_2.equals(code.getStatus())) {
            throw new BusinessException("充值码已冻结,请联系客服");
        }
        //存在但已过期
        if (OilRechargeCode.STATUS_3.equals(code.getStatus())) {
            throw new BusinessException("充值码已过期,请联系客服");
        }
        //新油卡的过期判断
        Date deadTime = code.getDeadTime();
        if (Objects.nonNull(deadTime)) {
            Calendar now = Calendar.getInstance(Locale.CHINA);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            Date time = now.getTime();
            if (time.compareTo(deadTime) > 0) {
                throw new BusinessException("充值码已过期,请联系客服");
            }
            //旧油卡的过期判断
        } else {
            //旧卡是1年零1个月
            if ((new Date().getTime() - code.getCreateTime().getTime()) > 1000
                    * 60 * 60 * 24 * 395L) {
                throw new BusinessException("充值码已过期,请联系客服");
            }
        }
        BigDecimal userMoney = user.getMoney();
        Calendar now = Calendar.getInstance(Locale.CHINA);
        Date time = now.getTime();
        //更新油卡状态
        BigDecimal money = code.getMoney();//充值金额
        code.setRechargeTime(time);//交易时间
        code.setStatus(OilRechargeCode.STATUS_1);//卷码状态
        code.setUpdateTime(time);//更新时间
        //更新卷码记录
        if (oilRechargeCodeMapper.updateByPrimaryKeySelective(code) != 1) {
            throw new BusinessException("更新充值码状态失败,请稍后重试");
        }
        //记录日志
        OilTransLog log = new OilTransLog();
        //创建时间
        log.setCreateTime(time);
        //充值金额
        log.setTardeMoney(money);
        //更新时间
        log.setUpdateTime(time);
        //充值卷码
        log.setRemark(code.getRechargeCode());
        //串码充值
        log.setTransType(OilTransLog.TRANS_TYPE_0);
        //油卡用户id
        log.setUserId(userId);
        //油卡账户手机号
        log.setUserPhone(user.getPhone());
        //油卡账户余额
        log.setUserMoney(userMoney);
        //添加油卡记录
        if (oilTransLogMapper.insertSelective(log) != 1) {
            throw new BusinessException("添加串码充值订单记录失败");
        }
        ;

        //添加油卡详情备份
        List<OilAccountRecharge> accountList = new ArrayList<OilAccountRecharge>();
        //查看账户余额是否做虚拟卡
        OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
        oilAccountRechargeEx.createCriteria().andUserIdEqualTo(userId);
        oilAccountRechargeEx.setLimitStart(0);
        oilAccountRechargeEx.setLimitEnd(1);
        List<OilAccountRecharge> oilAccountRecharges = oilAccountRechargeMapper
                .selectByExample(oilAccountRechargeEx);
        //若不存在虚拟卡,请添加一张虚拟卡
        if (userMoney.compareTo(BigDecimal.ZERO) > 0
                && oilAccountRecharges.isEmpty()) {
            now.add(Calendar.MINUTE, -1);
            Date time1 = now.getTime();
            OilAccountRecharge oilAccountRecharge = new OilAccountRecharge();
            oilAccountRecharge.setCardNo("a" + user.getPhone());
            oilAccountRecharge.setMoney(userMoney);
            oilAccountRecharge.setUserId(userId);
            oilAccountRecharge.setUserPhone(user.getPhone());
            oilAccountRecharge.setCreateTime(time1);
            oilAccountRecharge.setUpdateTime(time1);
            accountList.add(oilAccountRecharge);
        }
        //新增一个子账户余额
        OilAccountRecharge oilAccount = new OilAccountRecharge();
        //油卡序号
        oilAccount.setCardNo(code.getPhone());
        //油卡剩余金额
        oilAccount.setMoney(money);
        //账户id
        oilAccount.setUserId(userId);
        //账户手机号
        oilAccount.setUserPhone(user.getPhone());
        //油卡提供商
        oilAccount.setSupplier(code.getSupplier());
        //创建日期
        oilAccount.setCreateTime(time);
        //更新日期
        oilAccount.setUpdateTime(time);
        accountList.add(oilAccount);
        if (definedMapper.insertAccount(accountList) != accountList.size()) {
            throw new BusinessException("添加油卡子账户失败，请联系客服");
        }
        ;
        //更新账户余额
        Date updateTime = user.getUpdateTime();
        user.setMoney(userMoney.add(money));
        //更新用户姓名
        user.setName(code.getUserName());
        //更新时间
        user.setUpdateTime(time);
        //更新油卡账户
        OilUserExample oilUserEx = new OilUserExample();
        oilUserEx.or().andUpdateTimeEqualTo(updateTime)
                .andIdEqualTo(user.getId());
        if (oilUserMapper.updateByExample(user, oilUserEx) != 1) {
            throw new BusinessException("主账户添加金额失败,请稍后重试");
        }
        Log4jHelper.getLogger()
                .info("账户:" + user.getPhone() + "充值成功，充值金额:" + code.getMoney());

        return DatasetBuilder.fromDataSimple("success");
    }

    @Override
    @Read
    public DatasetSimple<OilUser> getOilUser(Long id) {
        return DatasetBuilder
                .fromDataSimple(oilUserMapper.selectByPrimaryKey(id));
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<String> addTransLog(OilTransLog log) {
        if (oilTransLogMapper.insertSelective(log) != 1) {
            throw new BusinessException("记录用户信息失败!");
        }
        return DatasetBuilder.fromDataSimple("success");
    }

    @Override
    @Read
    public DatasetSimple<OilTransLog> queryLogByOrderNo(String orderNo) {
        OilTransLogExample ex = new OilTransLogExample();
        ex.createCriteria().andOrderSnEqualTo(orderNo);
        List<OilTransLog> l = oilTransLogMapper.selectByExample(ex);
        if (l.isEmpty() || l.get(0) == null) {
            throw new BusinessException("没有订单信息！");
        }
        return DatasetBuilder.fromDataSimple(l.get(0));
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<String> updateTransLog(OilTransLog log) {
        if (oilTransLogMapper.updateByPrimaryKeySelective(log) != 1) {
            throw new BusinessException("更新失败！");
        }
        return DatasetBuilder.fromDataSimple("success");
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<String> sendCard(OilRechargeCode code) {
        if (code == null) {
            throw new BusinessException("发送失败，对象为空！");
        }
        code.setSmsStatus(OilRechargeCode.SMS_STATUS_0);
        code.setRechargeCode(RandomUtil.randomNumber(18));
        code.setStatus(OilRechargeCode.STATUS_0);
        code.setCreateTime(new Date());
        code.setUpdateTime(code.getCreateTime());
        if (oilRechargeCodeMapper.insertSelective(code) != 1) {
            throw new BusinessException("发送失败！errCode : -1");
        }
        return DatasetBuilder.fromDataSimple("success");
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<String> updateCardRemark(String remark, Long id) {
        OilRechargeCode code = new OilRechargeCode();
        code.setId(id);
        code.setRemark(remark);
        if (oilRechargeCodeMapper.updateByPrimaryKeySelective(code) != 1) {
            throw new BusinessException("更新失败！errCode : -1");
        }
        return DatasetBuilder.fromDataSimple("success");
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryCodeList(Integer pageNo,
            Integer pageSize, String userPhone, String status, String startNum,
            String endNum, String phone) {

        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        //根据油卡账户手机号查询七充值的卡密集合
        OilRechargeCodeExample ex = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = ex
                .createCriteria();
        boolean flag1 = StringUtils.isNotEmpty(userPhone);
        boolean flag2 = StringUtils.isNotEmpty(startNum)
                && StringUtils.isNotEmpty(endNum);
        boolean flag3 = StringUtils.isNotEmpty(phone);
        if (flag1 || flag2 || (!flag1 && !flag2 && !flag3)) {
            //根据油卡账户手机号查询七充值的卡密集合
            if (StringUtils.isNotEmpty(userPhone)) {
                List<String> rechargeCodes = definedMapper
                        .queryRechargeCode(userPhone);
                if (!rechargeCodes.isEmpty()) {
                    c.andRechargeCodeIn(rechargeCodes);
                } else {
                    return DatasetBuilder.fromDataList(results);
                }
            }
            //油卡状态作为筛选条件status (0:未使用;1已使用 2 未开通)
            if (Objects.equals("0", status)) {
                c.andStatusEqualTo(OilRechargeCode.STATUS_0);
            } else if (Objects.equals("1", status)) {
                c.andStatusEqualTo(OilRechargeCode.STATUS_1)
                        .andRechargeTimeIsNotNull();
            } else if (Objects.equals("2", status)) {//已冻结
                c.andStatusEqualTo(OilRechargeCode.STATUS_2);
            } else if (Objects.equals("3", status)) {//已作废
                c.andStatusEqualTo(OilRechargeCode.STATUS_3);
            } else if (Objects.equals("9", status)) {//未开通
                c.andStatusEqualTo(OilRechargeCode.STATUS_1)
                        .andRechargeTimeIsNull();
            }
            //序号
            if (StringUtils.isNotEmpty(startNum)
                    && StringUtils.isNotEmpty(endNum)) {
                if (endNum.length() != 9 || startNum.length() != 9) {
                    throw new BusinessException("序号长度必须是9位");
                }
                if (Long.valueOf(startNum) > Long.valueOf(endNum)) {
                    throw new BusinessException("序号最大值小于序号最小值~");
                } else {
                    c.andPhoneGreaterThanOrEqualTo(startNum);
                    c.andPhoneLessThanOrEqualTo(endNum);
                }
            }
        } else {
            //序号
            if (StringUtils.isNotEmpty(phone)) {
                if (phone.length() != 11 || !phone.startsWith("1")) {
                    throw new BusinessException("请输入正确的手机号");
                }
                c.andPhoneEqualTo(phone);
            }
        }

        //排序
        ex.setOrderByClause(OilRechargeCodeExample.CREATE_TIME_DESC);
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilRechargeCodeMapper.countByExample(ex));
        ex.setLimitStart(page.getStartRow());
        ex.setLimitEnd(page.getSize());
        List<OilRechargeCode> codeList = oilRechargeCodeMapper
                .selectByExample(ex);
        //查找被充值账户
        List<String> codes = codeList.stream()
                .map(OilRechargeCode::getRechargeCode).distinct()
                .collect(Collectors.toList());
        codes.add("0");
        OilTransLogExample oilTransLogEx = new OilTransLogExample();
        oilTransLogEx.createCriteria().andRemarkIn(codes);
        List<OilTransLog> oilTransLogs = oilTransLogMapper
                .selectByExample(oilTransLogEx);
        Map<String, String> codeMap = new HashMap<String, String>();
        oilTransLogs.forEach(oilLog -> {
            codeMap.put(oilLog.getRemark(), oilLog.getUserPhone());
        });

        codeList.forEach(codeDb -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", codeDb.getId());
            result.put("userName", codeDb.getUserName());
            result.put("chePai", codeDb.getChePai());
            result.put("phone", codeDb.getPhone());
            result.put("money", codeDb.getMoney());
            result.put("address", codeDb.getAddress());
            result.put("failTime", codeDb.getFailTime());
            result.put("recahrgeTime", codeDb.getRechargeTime());
            result.put("smsStatus", codeDb.getSmsStatus());
            if (codeDb.getStatus() == 1
                    && Objects.isNull(codeDb.getRechargeTime())) {
                result.put("status", 9);
            } else {
                result.put("status", codeDb.getStatus());
            }
            ;
            result.put("smsResult", codeDb.getSmsResult());
            result.put("createTime", codeDb.getCreateTime());
            result.put("openTime", codeDb.getOpenTime());
            result.put("remark", codeDb.getRemark());
            result.put("recharge", codeMap.get(codeDb.getRechargeCode()));
            results.add(result);
        });
        return DatasetBuilder.fromDataList(results, page.createPageInfo());
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<Map<String, Object>> batchSendCard(
            List<Map<String, Object>> params) {
        if (params == null || params.isEmpty()) {
            throw new BusinessException("更新失败！errCode : -1");
        }
        final AtomicInteger successCount = new AtomicInteger(0);
        params.forEach(map -> {
            OilRechargeCode code = new OilRechargeCode();
            code.setSmsStatus(OilRechargeCode.SMS_STATUS_0);
            code.setRechargeCode(RandomUtil.randomNumber(18));
            code.setStatus(OilRechargeCode.STATUS_0);
            code.setCreateTime(new Date());
            code.setUpdateTime(code.getCreateTime());
            code.setUserName(map.get("user_name").toString());
            code.setPhone(map.get("phone").toString());
            code.setChePai(map.get("che_pai").toString());
            code.setAddress(map.get("address").toString());
            code.setMoney(new BigDecimal(map.get("money").toString()));
            if (oilRechargeCodeMapper.insertSelective(code) != 1) {
                throw new BusinessException("发送失败！errCode : -1");
            }
            successCount.incrementAndGet();
        });
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", successCount.intValue());
        result.put("fail", params.size() - successCount.intValue());
        return DatasetBuilder.fromDataSimple(result);
    }

    @Read
    @Override
    public DatasetList<OilTransLog> queryLogList(Long userId, String orderNo) {
        OilTransLogExample ex = new OilTransLogExample();
        Criteria c = ex.createCriteria();
        c.andUserIdEqualTo(userId);
        if (StringUtils.isNotEmpty(orderNo)) {
            c.andOrderSnEqualTo(orderNo);
        }
        ex.setOrderByClause(OilTransLogExample.CREATE_TIME_DESC);
        List<OilTransLog> l = oilTransLogMapper.selectByExample(ex);
        if (!l.isEmpty()) {
            l.forEach(log -> {
                //返回充值油卡卡号或手机号
                if (2 == log.getTransType() || 3 == log.getTransType()) {
                    Gson gson = new Gson();
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = gson.fromJson(log.getRemark(),
                            Map.class);
                    Set<Entry<String, Object>> entry = map.entrySet();
                    for (Entry<String, Object> en : entry) {
                        if ("cardNo".equals(en.getKey())) {
                            log.setUserPhone(String.valueOf(en.getValue()));
                        }
                    }
                }
                if (log.getTransType() != OilTransLog.TRANS_TYPE_0
                        && StringUtils.isNotEmpty(log.getRemark())) {
                    if (log.getRemark().indexOf("成功") != -1) {
                        log.setRemark("0");
                    } else if (log.getRemark().indexOf("处理中") != -1) {
                        log.setRemark("1");
                    } else {//失败
                        log.setRemark("2");
                    }
                }
            });
        }
        GyOilLogExample gyEx = new GyOilLogExample();
        com.emate.shop.business.model.GyOilLogExample.Criteria cr = gyEx
                .createCriteria();
        cr.andUserIdEqualTo(userId);
        if (StringUtils.isNotEmpty(orderNo)) {
            cr.andOrderidEqualTo(orderNo);
        }
        List<GyOilLog> gyList = gyOilLogMapper.selectByExample(gyEx);
        if (!gyList.isEmpty()) {
            gyList.forEach(gyLog -> {
                OilTransLog log = new OilTransLog();
                //log.setUserId(userId);
                if (GyOilLog.CHARGETYPE_1.equals(gyLog.getChargetype())) {
                    log.setTransType(OilTransLog.TRANS_TYPE_3);
                } else if (GyOilLog.CHARGETYPE_2
                        .equals(gyLog.getChargetype())) {
                    log.setTransType(OilTransLog.TRANS_TYPE_2);
                }
                log.setTardeMoney(gyLog.getFillmoney());
                if (gyLog.getOrderstatus() == null) {
                    log.setRemark("1");
                } else if (!GyOilLog.ORDERSTATUS_1
                        .equals(gyLog.getOrderstatus())) {
                    log.setRemark("0");
                } else if (GyOilLog.ORDERSTATUS_1
                        .equals(gyLog.getOrderstatus())) {
                    log.setRemark("2");
                }
                //充值油卡卡号
                log.setUserPhone(gyLog.getGascardid());

                log.setOrderSn(gyLog.getOrderid());
                log.setCreateTime(gyLog.getCreateTime());
                l.add(log);
            });
        }
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        com.emate.shop.business.model.GyPhoneLogExample.Criteria cre = gyPhoneLogEx
                .createCriteria();
        cre.andUserIdEqualTo(userId);

        if (StringUtils.isNotEmpty(orderNo)) {
            cre.andOrderNoEqualTo(orderNo);
        }
        List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                .selectByExample(gyPhoneLogEx);
        if (!gyPhoneLogs.isEmpty()) {
            gyPhoneLogs.forEach(gyPhoneLog -> {
                OilTransLog log = new OilTransLog();
                //log.setUserId(userId);
                log.setTransType(OilTransLog.TRANS_TYPE_4);

                if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_0
                        || gyPhoneLog.getStatus() == GyPhoneLog.STATUS_1) {//处理中
                    log.setRemark("1");
                    log.setTardeMoney(
                            new BigDecimal(gyPhoneLog.getProdContent()));
                } else if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_3) {//充值成功
                    log.setRemark("0");
                    log.setTardeMoney(gyPhoneLog.getOrderMoney());
                } else {//充值失败
                    log.setTardeMoney(
                            new BigDecimal(gyPhoneLog.getProdContent()));
                    log.setRemark("2");
                }
                //高阳充值手机号
                log.setUserPhone(gyPhoneLog.getMobileNum());

                log.setOrderSn(gyPhoneLog.getOrderNo());
                log.setCreateTime(gyPhoneLog.getCreateTime());
                l.add(log);
            });
        }
        Collections.sort(l, new Comparator<OilTransLog>() {

            @Override
            public int compare(OilTransLog gy1, OilTransLog gy2) {
                if (gy1.getCreateTime().getTime() > gy2.getCreateTime()
                        .getTime()) {
                    return -1;
                } else if (gy1.getCreateTime().getTime() < gy2.getCreateTime()
                        .getTime()) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
        PaginationUtil page = new PaginationUtil(1, 300, l.size());
        List<OilTransLog> resultList = new ArrayList<OilTransLog>();
        for (int i = page.getStartRow(); i < l.size(); i++) {
            if (i == (page.getStartRow() + page.getSize())) {
                break;
            }
            resultList.add(l.get(i));
        }
        return DatasetBuilder.fromDataList(resultList, page.createPageInfo());
    }

    @Override
    public DatasetSimple<Map<String, String>> getMoneySummary() {
        Map<String, String> result = new HashMap<>();
        result.put("one", definedMapper.getOilMoneySummary1().toString());
        result.put("two", definedMapper.getOilMoneySummary2().toString());
        result.put("three", definedMapper.getOilMoneySummary3().toString());
        return DatasetBuilder.fromDataSimple(result);
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<String> modifyUserMoney(Long userId, Integer money,
            String orderNo, String orderType) {
        //根据id查询用户
        OilUser user = definedMapper.queryOilUserbyId(userId);

        if (Objects.isNull(user)) {
            throw new BusinessException("找不到用户！");
        }
        //打印油卡用户信息
        if ("0".equals(orderType)) {
            Log4jHelper.getLogger()
                    .info("充值账号：" + user.getPhone() + ",订单号：" + orderNo
                            + ",账户余额：" + user.getMoney().intValue()
                            + ",油卡失败账户添加的金额：" + money);
        } else if ("1".equals(orderType)) {
            Log4jHelper.getLogger()
                    .info("充值账号：" + user.getPhone() + ",订单号：" + orderNo
                            + ",账户余额：" + user.getMoney().intValue()
                            + ",话费失败账户添加的金额：" + money);
        }
        //判断账户余额是否充足
        BigDecimal firstMoney = user.getMoney();
        BigDecimal finalMoney = firstMoney.add(new BigDecimal(money));
        if (firstMoney.intValue() < 0 || finalMoney.intValue() < 0) {
            Log4jHelper.getLogger()
                    .error("用户余额不足,请先充值。当前余额:" + firstMoney.intValue());
            throw new BusinessException(
                    "用户余额不足,请先充值。当前余额:" + firstMoney.intValue());
        }
        //增减账户余额
        user.setMoney(finalMoney);
        user.setUpdateTime(new Date());

        //更新主账户余额
        if (1 != oilUserMapper.updateByPrimaryKeySelective(user)) {
            Log4jHelper.getLogger().error("更新主账户失败");
            throw new BusinessException("更新账户信息失败！！");
        }
        ;
        //查询子账户订单关联表
        OilcardOrderRelationExample oilcardOrderRelationEx = new OilcardOrderRelationExample();
        com.emate.shop.business.model.OilcardOrderRelationExample.Criteria cr = oilcardOrderRelationEx
                .createCriteria();
        cr.andOrderNoEqualTo(orderNo);
        cr.andOrderTypeEqualTo(Integer.valueOf(orderType));
        cr.andUserIdEqualTo(userId);
        List<OilcardOrderRelation> oilcardOrderRelations = oilcardOrderRelationMapper
                .selectByExample(oilcardOrderRelationEx);
        if (oilcardOrderRelations.isEmpty()) {
            Log4jHelper.getLogger().error("查询不到子账户订单关联表");
            throw new BusinessException("查询不到子账户订单关联表");
        }

        //definedMapper.updateOilCardOrder(oilcardOrderRelations);
        //查询子账户信息
        Map<String, BigDecimal> cardMoneys = new HashMap<String, BigDecimal>();
        ArrayList<String> cardNoList = new ArrayList<String>();
        oilcardOrderRelations.stream().forEach(oilcard -> {
            cardNoList.add(oilcard.getCardNo());
            cardMoneys.put(oilcard.getCardNo(), oilcard.getMoney());
        });
        OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
        oilAccountRechargeEx.createCriteria().andCardNoIn(cardNoList);
        List<OilAccountRecharge> oilAccounts = oilAccountRechargeMapper
                .selectByExample(oilAccountRechargeEx);
        if (oilAccounts.isEmpty()) {
            Log4jHelper.getLogger().error("查询不到该主账户的子账户");
            throw new BusinessException("查询不到该主账户的子账户");
        }
        //更新子账户信息
        oilAccounts.stream().forEach(oilAccount -> {
            BigDecimal addMoney = cardMoneys.get(oilAccount.getCardNo());
            if (Objects.nonNull(addMoney)) {
                BigDecimal oilCardmoney = oilAccount.getMoney();
                oilAccount.setMoney(oilCardmoney.add(addMoney));
                oilAccount.setUpdateTime(new Date());
            }
            if (1 != oilAccountRechargeMapper
                    .updateByPrimaryKeySelective(oilAccount)) {
                Log4jHelper.getLogger()
                        .error("更新子账户表失败;表id" + oilAccount.getId());
                throw new BusinessException("更新子账户表失败");
            }
        });
        //更新子账户订单关联表
        for (OilcardOrderRelation oilOrder : oilcardOrderRelations) {
            oilOrder.setUpdateTime(new Date());
            oilOrder.setMoney(BigDecimal.ZERO);
            if (1 != oilcardOrderRelationMapper
                    .updateByPrimaryKeySelective(oilOrder)) {
                Log4jHelper.getLogger()
                        .error("更新子账户和订单关联表失败;表id" + oilOrder.getId());
                throw new BusinessException("更新子账户和订单关联表失败");
            }
        }
        //返回最新账户余额
        return DatasetBuilder
                .fromDataSimple(String.valueOf(user.getMoney().doubleValue()));
    }

    /**
     * 修改服务商接口
     */
    @Write
    @Override
    @Transactional
    public DatasetSimple<OilConfig> updateOilConfig(OilConfig oilConfig) {
        if (Objects.isNull(oilConfig.getSupplier())) {
            throw new BusinessException("请选择服务商~");
        }
        if (OilConfig.SUPPLIER_2.equals(oilConfig.getSupplier())) {
            throw new BusinessException("目前高阳暂不能使用~~");
        }
        if (Objects.isNull(oilConfig.getId())) {
            oilConfig.setCreateTime(new Date());
            oilConfig.setUpdateTime(new Date());
            if (oilConfigMapper.insertSelective(oilConfig) == 1) {
                //return DatasetBuilder.fromDataSimple(oilConfig);
                OilConfigExample oilConfigEx = new OilConfigExample();
                oilConfigEx.setLimitStart(0);
                oilConfigEx.setLimitEnd(1);
                OilConfig oilConfig2 = oilConfigMapper
                        .selectByExample(oilConfigEx).get(0);
                return DatasetBuilder.fromDataSimple(oilConfig2);
            }
            ;
        } else {
            OilConfig config = oilConfigMapper
                    .selectByPrimaryKey(oilConfig.getId());
            if (Objects.isNull(config)) {
                throw new BusinessException("该服务商配置不存在~");
            }
            config.setUpdateTime(new Date());
            config.setSupplier(oilConfig.getSupplier());
            if (oilConfigMapper.updateByPrimaryKeySelective(config) == 1) {
                return DatasetBuilder.fromDataSimple(config);
            }
            ;
        }
        throw new BusinessException("修改服务商接口失败~");
    }

    /**
     * 查询服务商接口
     */
    @Override
    @Read
    public DatasetSimple<OilConfig> queryOilConfig() {
        OilConfigExample oilConfigEx = new OilConfigExample();
        oilConfigEx.setLimitStart(0);
        oilConfigEx.setLimitEnd(1);
        List<OilConfig> oilConfigs = oilConfigMapper
                .selectByExample(oilConfigEx);
        if (oilConfigs.isEmpty()) {
            return DatasetBuilder.fromDataSimple(null);
        }
        return DatasetBuilder.fromDataSimple(oilConfigs.get(0));
    }

    @Override
    @Transactional
    @Write
    public DatasetSimple<Map<String, String>> updateGyRechargeLog(
            GyOilLog log) {
        if (gyOilLogMapper.updateByPrimaryKeySelective(log) != 1) {
            throw new BusinessException("更新油卡订单失败！");
        }
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return DatasetBuilder.fromDataSimple(result);
    }

    @Override
    @Read
    public DatasetSimple<GyOilLog> queryGyOrderByid(String orderid) {
        GyOilLogExample ex = new GyOilLogExample();
        ex.createCriteria().andOrderidEqualTo(orderid);
        List<GyOilLog> list = gyOilLogMapper.selectByExample(ex);
        if (list.isEmpty()) {
            throw new BusinessException("找不到gy订单！");
        }
        return DatasetBuilder.fromDataSimple(list.get(0));
    }

    @SuppressWarnings("static-access")
    @Override
    @Write
    public void updateGyPhoneBy(String ordersarchUrl, String agentId,
            String source, String merchantkey, String backUrl) {
        //根据条件查询下单成功的10分钟后未回调的手机充值记录
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - 10);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(GyPhoneLog.STATUS_0);
        list.add(GyPhoneLog.STATUS_1);
        gyPhoneLogEx.createCriteria()
                .andCreateTimeLessThanOrEqualTo(now.getTime())
                .andStatusIn(list);
        List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                .selectByExample(gyPhoneLogEx);
        for (GyPhoneLog gyPhoneLog : gyPhoneLogs) {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, String> result = GaoYangUtil.orderDirectSearch(
                    ordersarchUrl, agentId, merchantkey,
                    gyPhoneLog.getOrderNo(), "2", backUrl, source);
            if ("2".equals(result.get("resultno"))) {//订单状态:订单充值成功
                gyPhoneLog.setUpdateTime(new Date());
                gyPhoneLog.setStatus(GyPhoneLog.STATUS_3);
                gyPhoneLog.setOrderMoney(
                        new BigDecimal(result.get("finishmoney")));
                gyPhoneLog.setRemark("充值成功:" + result.get("resultno") + ";"
                        + "实际充值金额:" + result.get("finishmoney"));
                //更新手机充值记录日志
                gyPhoneLogMapper.updateByPrimaryKeySelective(gyPhoneLog);
            } else if ("3".equals(result.get("resultno"))) {//订单状态:订单充值部分成功
                gyPhoneLog.setUpdateTime(new Date());
                gyPhoneLog.setStatus(GyPhoneLog.STATUS_3);
                gyPhoneLog.setOrderMoney(
                        new BigDecimal(result.get("finishmoney")));
                gyPhoneLog.setRemark("充值部分成功:" + result.get("resultno") + ";"
                        + "实际充值金额:" + result.get("finishmoney"));

                //若充值面额和实际充值金额不一致
                if (!gyPhoneLog.getProdContent()
                        .equals(result.get("finishmoney"))) {
                    //回复账户余额
                    BigDecimal addMoney = new BigDecimal(
                            gyPhoneLog.getProdContent()).subtract(
                                    new BigDecimal(result.get("finishmoney")));
                    DatasetSimple<String> modifyUserMoney = null;
                    try {
                        modifyUserMoney = modifyUserMoney(
                                gyPhoneLog.getUserId(), addMoney.intValue(),
                                gyPhoneLog.getOrderNo(), "1");
                    } catch (Exception e) {
                        throw new BusinessException(e.getMessage());
                    }
                    if (Objects.isNull(modifyUserMoney)) {
                        throw new BusinessException("查询订单失败！！");
                    }
                    gyPhoneLog.setSurplusMoney(
                            new BigDecimal(modifyUserMoney.getData()));

                }
                gyPhoneLogMapper.updateByPrimaryKeySelective(gyPhoneLog);
            } else if ("4".equals(result.get("resultno"))) {
                gyPhoneLog.setUpdateTime(new Date());
                gyPhoneLog.setStatus(GyPhoneLog.STATUS_4);
                gyPhoneLog.setOrderMoney(new BigDecimal("0"));
                gyPhoneLog.setRemark("充值失败:" + result.get("resultno"));
                //回复账户余额
                DatasetSimple<String> modifyUserMoney = null;
                try {
                    modifyUserMoney = modifyUserMoney(gyPhoneLog.getUserId(),
                            Integer.valueOf(gyPhoneLog.getProdContent()),
                            gyPhoneLog.getOrderNo(), "1");
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage());
                }
                if (Objects.isNull(modifyUserMoney)) {
                    throw new BusinessException("查询订单失败！！");
                }
                gyPhoneLog.setSurplusMoney(
                        new BigDecimal(modifyUserMoney.getData()));
                gyPhoneLogMapper.updateByPrimaryKeySelective(gyPhoneLog);
            }
            Log4jHelper.getLogger().info("话费充值查询订单[{}]结果完成...",
                    result.get("orderid"));
        }
    }

    @Override
    public DatasetSimple<Map<String, String>> addOrUpdateGyPhoneLog(
            GyPhoneLog log) {
        if (Objects.isNull(log.getId())) {
            if (gyPhoneLogMapper.insertSelective(log) != 1) {
                throw new BusinessException("添加高阳手机充值订单失败");
            }
            ;
        } else {
            if (gyPhoneLogMapper.updateByPrimaryKeySelective(log) != 1) {
                throw new BusinessException("修改高阳手机充值订单失败");
            }
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");
        return DatasetBuilder.fromDataSimple(result);
    }

    @Override
    public DatasetSimple<GyPhoneLog> queryGyLogByOrderNo(String orderNo) {

        ArrayList<Integer> statusList = new ArrayList<Integer>();
        statusList.add(GyPhoneLog.STATUS_0);
        statusList.add(GyPhoneLog.STATUS_1);
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        gyPhoneLogEx.createCriteria().andOrderNoEqualTo(orderNo)
                .andStatusIn(statusList);
        List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                .selectByExample(gyPhoneLogEx);
        if (gyPhoneLogs.isEmpty()) {
            throw new BusinessException("订单不存在!!");
        }
        return DatasetBuilder.fromDataSimple(gyPhoneLogs.get(0));
    }

    @Override
    @Read
    public DatasetSimple<Integer> queryGyProductByProdId(String prodId) {
        GyPhoneProductExample gyPhoneProductEx = new GyPhoneProductExample();
        gyPhoneProductEx.createCriteria().andProdIdEqualTo(prodId);
        int count = gyPhoneProductMapper.countByExample(gyPhoneProductEx);
        return DatasetBuilder.fromDataSimple(count);
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<Integer> addGyPhoneProduct(GyPhoneProduct product) {
        int insert = gyPhoneProductMapper.insertSelective(product);
        return DatasetBuilder.fromDataSimple(insert);
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<Integer> delGyPhoneProduct(String prodId) {
        GyPhoneProductExample gyPhoneProductEx = new GyPhoneProductExample();
        gyPhoneProductEx.createCriteria().andProdIdEqualTo(prodId);
        int delete = gyPhoneProductMapper.deleteByExample(gyPhoneProductEx);
        return DatasetBuilder.fromDataSimple(delete);
    }

    @Override
    @Read
    public DatasetList<GyPhoneProduct> queryGyProduct() {
        GyPhoneProductExample gyPhoneProductEx = new GyPhoneProductExample();
        List<GyPhoneProduct> gyPhoneProducts = gyPhoneProductMapper
                .selectByExample(gyPhoneProductEx);
        return DatasetBuilder.fromDataList(gyPhoneProducts);
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryOilOrderLog(Integer pageNo,
            Integer pageSize, OilTransLog oilTransLog, Byte oilCompType,
            String oilCardNumber) {
        //当服务商和订单类型都不确定时
        if (new Byte("3").equals(oilCompType)
                && 7 == oilTransLog.getTransType()) {
            List<Map<String, Object>> results = getOilOrder(oilTransLog,
                    oilCompType, oilCardNumber);
            PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                    results.size());
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (int i = page.getStartRow(); i < results.size(); i++) {
                if (i == (page.getStartRow() + page.getSize())) {
                    break;
                }
                resultList.add(results.get(i));
            }
            return DatasetBuilder.fromDataList(resultList,
                    page.createPageInfo());
        } else {
            return getOilOrderTwo(pageNo, pageSize, oilTransLog, oilCompType,
                    oilCardNumber);
        }

    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getOilOrder(OilTransLog oilTransLog,
            Byte oilComType, String oilCardNumber) {
        ArrayList<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (StringUtils.isEmpty(oilTransLog.getOrderSn())
                && StringUtils.isEmpty(oilTransLog.getUserPhone())
                && StringUtils.isEmpty(oilCardNumber)) {
            throw new BusinessException("若服务商和订单类型不确定,订单号和账户手机号和被充值卡号不能为空");
        } else {
            //根据手机号查询用户id
            Long userId = 0L;
            if (StringUtils.isNotEmpty(oilTransLog.getUserPhone())) {
                OilUserExample oilUserEx = new OilUserExample();
                oilUserEx.createCriteria()
                        .andPhoneEqualTo(oilTransLog.getUserPhone());
                List<OilUser> oilUsers = oilUserMapper
                        .selectByExample(oilUserEx);
                if (oilUsers.isEmpty()) {
                    return results;
                }
                userId = oilUsers.get(0).getId();
            }
            //追电油卡话费电子券账户充值
            OilTransLogExample oilTransLogEx = new OilTransLogExample();
            Criteria c = oilTransLogEx.createCriteria();
            if (StringUtils.isNotEmpty(oilTransLog.getOrderSn())) {
                c.andOrderSnEqualTo(oilTransLog.getOrderSn());
            }
            if (StringUtils.isNotEmpty(oilCardNumber)) {
                c.andRemarkLike("%" + oilCardNumber + "%");
            }
            if (!Objects.equals(userId, 0L)) {
                c.andUserIdEqualTo(userId);
            }
            List<OilTransLog> oilTransLogs = oilTransLogMapper
                    .selectByExample(oilTransLogEx);
            for (OilTransLog oilLog : oilTransLogs) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("supplier", "0");//0:追电1 高阳2欧飞
                result.put("id", oilLog.getId());
                result.put("createTime", format.format(oilLog.getCreateTime()));
                result.put("updateTime", format.format(oilLog.getUpdateTime()));
                result.put("orderSn", oilLog.getOrderSn());
                result.put("userPhone", oilLog.getUserId());
                result.put("fillMoney", oilLog.getTardeMoney());
                result.put("transType", oilLog.getTransType());
                result.put("respMsg", oilLog.getRemark());
                result.put("tardeMoney", null);
                result.put("userMoney", oilLog.getUserId());
                //订单完成后账户余额
                result.put("surplusMoney", oilLog.getUserMoney());
                result.put("cardNo", oilLog.getRemark());
                if (0 != oilLog.getTransType() && 5 != oilLog.getTransType()
                        && 6 != oilLog.getTransType()) {
                    Gson gson = new Gson();
                    Map<String, Object> map = gson.fromJson(oilLog.getRemark(),
                            Map.class);
                    Set<Entry<String, Object>> entry = map.entrySet();
                    switch (oilLog.getTransType()) {
                        case 1:
                            for (Entry<String, Object> en : entry) {
                                if ("subList".equals(en.getKey())) {
                                    List<Map<String, Object>> value = (List<Map<String, Object>>) en
                                            .getValue();
                                    Map<String, Object> detail = value.get(0);
                                    result.put("respMsg",
                                            detail.get("respMsg"));
                                    result.put("cardNo", detail.get("transNo"));
                                    if ("0000".equals(detail.get("respCode"))) {
                                        result.put("tardeMoney",
                                                oilLog.getTardeMoney());
                                    }
                                }
                            }
                            break;
                        default:
                            for (Entry<String, Object> en : entry) {
                                if ("respMsg".equals(en.getKey())) {
                                    result.put("respMsg", en.getValue());
                                } else if ("cardNo".equals(en.getKey())) {
                                    result.put("cardNo", en.getValue());
                                } else if ("transNo".equals(en.getKey())) {
                                    result.put("transNo", en.getValue());
                                }
                                if ("respCode".equals(en.getKey())
                                        && "0000".equals(en.getValue())) {
                                    result.put("tardeMoney",
                                            oilLog.getTardeMoney());
                                }
                            }
                            break;
                    }
                }
                results.add(result);
            }
            //高阳油卡
            GyOilLogExample gyOilLogEx = new GyOilLogExample();
            com.emate.shop.business.model.GyOilLogExample.Criteria criteria = gyOilLogEx
                    .createCriteria();

            if (StringUtils.isNotEmpty(oilTransLog.getOrderSn())) {
                criteria.andOrderidEqualTo(oilTransLog.getOrderSn());
            }
            if (StringUtils.isNotEmpty(oilCardNumber)) {
                criteria.andGascardidEqualTo(oilCardNumber);
            }
            if (!Objects.equals(userId, 0L)) {
                criteria.andUserIdEqualTo(userId);
            }
            List<GyOilLog> gyOilLogs = gyOilLogMapper
                    .selectByExample(gyOilLogEx);
            if (!gyOilLogs.isEmpty()) {
                for (GyOilLog gyOilLog : gyOilLogs) {
                    Map<String, Object> result = new HashMap<String, Object>();
                    if (new Integer("0").equals(gyOilLog.getSupplier())) {
                        result.put("supplier", "2");//0:追电1 高阳2欧飞
                    } else {
                        result.put("supplier", "1");//0:追电1 高阳2欧飞
                    }
                    //result.put("supplier", "1");//0:追电1 高阳
                    result.put("id", gyOilLog.getId());
                    result.put("createTime",
                            format.format(gyOilLog.getCreateTime()));
                    result.put("updateTime",
                            format.format(gyOilLog.getUpdateTime()));
                    result.put("orderSn", gyOilLog.getOrderid());
                    result.put("userPhone", gyOilLog.getUserId());
                    result.put("tardeMoney", gyOilLog.getFinishmoney());
                    result.put("userMoney", gyOilLog.getUserId());
                    result.put("fillMoney", gyOilLog.getFillmoney());
                    result.put("surplusMoney", gyOilLog.getSurplusMoney());
                    if (Objects.equals(GyOilLog.CHARGETYPE_1,
                            gyOilLog.getChargetype())) {
                        result.put("transType", OilTransLog.TRANS_TYPE_3);
                    } else if (Objects.equals(GyOilLog.CHARGETYPE_2,
                            gyOilLog.getChargetype())) {
                        result.put("transType", OilTransLog.TRANS_TYPE_2);
                    }
                    if (new Byte("0").equals(gyOilLog.getOrderstatus())) {
                        result.put("respMsg", "充值成功");
                    } else if (new Byte("1")
                            .equals(gyOilLog.getOrderstatus())) {
                        result.put("respMsg",
                                "充值失败:" + gyOilLog.getResultcode());
                    } else {
                        result.put("respMsg", "订单处理中");
                    }
                    //result.put("respMsg", gyOilLog.getErrdesc());
                    result.put("transNo", gyOilLog.getJxorderid());
                    result.put("cardNo", gyOilLog.getGascardid());
                    results.add(result);
                }
            }
            //高阳话费
            GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
            com.emate.shop.business.model.GyPhoneLogExample.Criteria cri = gyPhoneLogEx
                    .createCriteria();
            if (StringUtils.isNotEmpty(oilTransLog.getOrderSn())) {
                cri.andOrderNoEqualTo(oilTransLog.getOrderSn());
            }
            if (StringUtils.isNotEmpty(oilCardNumber)) {
                cri.andMobileNumEqualTo(oilCardNumber);
            }
            if (!Objects.equals(userId, 0L)) {
                cri.andUserIdEqualTo(userId);
            }
            List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                    .selectByExample(gyPhoneLogEx);
            if (!gyPhoneLogs.isEmpty()) {
                for (GyPhoneLog gyPhoneLog : gyPhoneLogs) {
                    Map<String, Object> result = new HashMap<String, Object>();
                    if (new Integer("0").equals(gyPhoneLog.getSupplier())) {
                        result.put("supplier", "2");//0:追电1 高阳2欧飞
                    } else {
                        result.put("supplier", "1");//0:追电1 高阳2欧飞
                    }
                    //result.put("supplier", "1");//0:追电1 高阳
                    result.put("id", gyPhoneLog.getId());
                    result.put("createTime",
                            format.format(gyPhoneLog.getCreateTime()));
                    result.put("updateTime",
                            format.format(gyPhoneLog.getUpdateTime()));
                    result.put("orderSn", gyPhoneLog.getOrderNo());
                    result.put("userPhone", gyPhoneLog.getUserId());
                    result.put("userMoney", gyPhoneLog.getUserId());
                    result.put("tardeMoney", gyPhoneLog.getOrderMoney());//交易金额
                    result.put("fillMoney", gyPhoneLog.getProdContent());//充值面额
                    //订单完成后账户余额
                    result.put("surplusMoney", gyPhoneLog.getSurplusMoney());
                    result.put("transType", OilTransLog.TRANS_TYPE_4);
                    if (Objects.equals(GyPhoneLog.STATUS_0,
                            gyPhoneLog.getStatus())) {
                        result.put("respMsg", "下单成功");
                    } else if (Objects.equals(GyPhoneLog.STATUS_1,
                            gyPhoneLog.getStatus())) {
                        result.put("respMsg", "下单失败扣款未知");
                    } else if (Objects.equals(GyPhoneLog.STATUS_2,
                            gyPhoneLog.getStatus())) {
                        result.put("respMsg", "下单失败未扣款");
                    } else if (Objects.equals(GyPhoneLog.STATUS_3,
                            gyPhoneLog.getStatus())) {
                        result.put("respMsg", "充值成功");
                    } else if (Objects.equals(GyPhoneLog.STATUS_4,
                            gyPhoneLog.getStatus())) {
                        result.put("respMsg", "充值失败");
                    } else if (Objects.equals(GyPhoneLog.STATUS_5,
                            gyPhoneLog.getStatus())) {
                        result.put("respMsg", "账户扣款失败");
                    }
                    result.put("transNo", null);
                    result.put("cardNo", gyPhoneLog.getMobileNum());
                    results.add(result);
                }
            }

            List<Long> userIds = new ArrayList<Long>();
            userIds.add(0L);
            List<String> codes = new ArrayList<String>();
            codes.add("");
            for (Map<String, Object> map : results) {
                Long uId = (Long) map.get("userPhone");
                userIds.add(uId);
                if (Objects.equals(map.get("transType"), 0)) {
                    codes.add(String.valueOf(map.get("cardNo")));
                }
            }

            OilUserExample userEx = new OilUserExample();
            userEx.createCriteria().andIdIn(userIds);
            List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
            Map<Long, OilUser> userMap = new HashMap<Long, OilUser>();
            oilUsers.stream().forEach(user -> {
                userMap.put(user.getId(), user);
            });

            //串码充值
            OilRechargeCodeExample codeEx = new OilRechargeCodeExample();
            codeEx.createCriteria().andRechargeCodeIn(codes);
            List<OilRechargeCode> oilCodes = oilRechargeCodeMapper
                    .selectByExample(codeEx);
            Map<String, String> oilCodeMap = new HashMap<String, String>();
            oilCodes.stream().forEach(code -> {
                oilCodeMap.put(code.getRechargeCode(), code.getPhone());
            });

            //赋值账户手机号和账户余额
            for (Map<String, Object> map : results) {
                OilUser user = userMap.get(map.get("userPhone"));
                if (Objects.isNull(user)) {
                    map.put("userPhone", null);
                    map.put("userMoney", null);
                } else {
                    map.put("userPhone", user.getPhone());
                    map.put("userMoney", user.getMoney());
                }
                if (Objects.equals(map.get("transType"), 0)) {
                    map.put("cardNo",
                            oilCodeMap.get(String.valueOf(map.get("cardNo"))));
                }

            }
            //根据创建时间排序
            Collections.sort(results, new Comparator<Map<String, Object>>() {

                @Override
                public int compare(Map<String, Object> gy1,
                        Map<String, Object> gy2) {
                    Date parse1;
                    Date parse2;
                    try {
                        parse1 = format
                                .parse(String.valueOf(gy1.get("createTime")));
                        parse2 = format
                                .parse(String.valueOf(gy2.get("createTime")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new BusinessException("日期转换异常~~");
                    }
                    if (parse1.getTime() > parse2.getTime()) {
                        return -1;
                    } else if (parse1.getTime() < parse2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }

            });
            return results;
        }
    }

    @SuppressWarnings("unchecked")
    private DatasetList<Map<String, Object>> getOilOrderTwo(Integer pageNo,
            Integer pageSize, OilTransLog oilTransLog, Byte oilCompType,
            String oilCardNumber) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //根据手机号查询用户id
        Long userId = 0L;
        if (StringUtils.isNotEmpty(oilTransLog.getUserPhone())) {
            OilUserExample oilUserEx = new OilUserExample();
            oilUserEx.createCriteria()
                    .andPhoneEqualTo(oilTransLog.getUserPhone());
            List<OilUser> oilUsers = oilUserMapper.selectByExample(oilUserEx);
            if (oilUsers.isEmpty()) {
                return DatasetBuilder.fromDataList(results);
            }
            userId = oilUsers.get(0).getId();
        }
        PaginationUtil page = null;
        if (OilConfig.SUPPLIER_1.equals(oilCompType)) {
            //追电油卡话费电子券账户充值
            OilTransLogExample oilTransLogEx = new OilTransLogExample();
            Criteria c = oilTransLogEx.createCriteria();
            if (StringUtils.isNotEmpty(oilTransLog.getOrderSn())) {
                c.andOrderSnEqualTo(oilTransLog.getOrderSn());
            }
            if (StringUtils.isNotEmpty(oilCardNumber)) {
                c.andRemarkLike("%" + oilCardNumber + "%");
            }
            if (!Objects.equals(userId, 0L)) {
                c.andUserIdEqualTo(userId);
            }
            c.andTransTypeEqualTo(oilTransLog.getTransType());
            page = new PaginationUtil(pageNo, pageSize,
                    oilTransLogMapper.countByExample(oilTransLogEx));
            oilTransLogEx.setLimitStart(page.getStartRow());
            oilTransLogEx.setLimitEnd(page.getSize());
            oilTransLogEx.setOrderByClause(OilTransLogExample.CREATE_TIME_DESC);
            List<OilTransLog> oilTransLogs = oilTransLogMapper
                    .selectByExample(oilTransLogEx);
            for (OilTransLog oilLog : oilTransLogs) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("supplier", "0");//0:追电1 高阳
                result.put("id", oilLog.getId());
                result.put("createTime", format.format(oilLog.getCreateTime()));
                result.put("updateTime", format.format(oilLog.getUpdateTime()));
                result.put("orderSn", oilLog.getOrderSn());
                result.put("userPhone", oilLog.getUserId());
                //剩余账户余额
                result.put("userMoney", oilLog.getUserId());
                //订单完成后的账户余额
                result.put("surplusMoney", oilLog.getUserMoney());
                //交易金额
                result.put("fillMoney", oilLog.getTardeMoney());
                result.put("transType", oilLog.getTransType());
                result.put("respMsg", oilLog.getRemark());
                //实际完成金额
                result.put("tardeMoney", null);
                result.put("cardNo", oilLog.getRemark());
                if (0 != oilLog.getTransType() && 5 != oilLog.getTransType()
                        && 6 != oilLog.getTransType()) {
                    Gson gson = new Gson();
                    Map<String, Object> map = gson.fromJson(oilLog.getRemark(),
                            Map.class);
                    Set<Entry<String, Object>> entry = map.entrySet();
                    switch (oilLog.getTransType()) {
                        case 1:
                            for (Entry<String, Object> en : entry) {
                                if ("subList".equals(en.getKey())) {
                                    List<Map<String, Object>> value = (List<Map<String, Object>>) en
                                            .getValue();
                                    Map<String, Object> detail = value.get(0);
                                    result.put("respMsg",
                                            detail.get("respMsg"));
                                    result.put("cardNo", detail.get("transNo"));
                                    if ("0000".equals(detail.get("respCode"))) {
                                        result.put("tardeMoney",
                                                oilLog.getTardeMoney());
                                    }
                                }
                            }
                            break;
                        default:
                            for (Entry<String, Object> en : entry) {
                                if ("respMsg".equals(en.getKey())) {
                                    result.put("respMsg", en.getValue());
                                } else if ("cardNo".equals(en.getKey())) {
                                    result.put("cardNo", en.getValue());
                                } else if ("transNo".equals(en.getKey())) {
                                    result.put("transNo", en.getValue());
                                }
                                if ("respCode".equals(en.getKey())
                                        && "0000".equals(en.getValue())) {
                                    result.put("tardeMoney",
                                            oilLog.getTardeMoney());
                                }
                            }
                            break;
                    }
                }
                results.add(result);
            }
        } else {
            if (OilTransLog.TRANS_TYPE_2.equals(oilTransLog.getTransType())
                    || OilTransLog.TRANS_TYPE_3
                            .equals(oilTransLog.getTransType())) {
                //高阳油卡
                GyOilLogExample gyOilLogEx = new GyOilLogExample();
                com.emate.shop.business.model.GyOilLogExample.Criteria criteria = gyOilLogEx
                        .createCriteria();

                if (StringUtils.isNotEmpty(oilTransLog.getOrderSn())) {
                    criteria.andOrderidEqualTo(oilTransLog.getOrderSn());
                }
                if (StringUtils.isNotEmpty(oilCardNumber)) {
                    criteria.andGascardidEqualTo(oilCardNumber);
                }
                if (!Objects.equals(userId, 0L)) {
                    criteria.andUserIdEqualTo(userId);
                }
                if (OilConfig.SUPPLIER_2.equals(oilCompType)) {
                    criteria.andSupplierIsNull();
                } else {
                    criteria.andSupplierEqualTo(0);
                }
                if (OilTransLog.TRANS_TYPE_2
                        .equals(oilTransLog.getTransType())) {
                    criteria.andChargetypeEqualTo(GyOilLog.CHARGETYPE_2);
                } else {
                    criteria.andChargetypeEqualTo(GyOilLog.CHARGETYPE_1);
                }
                page = new PaginationUtil(pageNo, pageSize,
                        gyOilLogMapper.countByExample(gyOilLogEx));
                gyOilLogEx.setLimitStart(page.getStartRow());
                gyOilLogEx.setLimitEnd(page.getSize());
                gyOilLogEx.setOrderByClause(GyOilLogExample.CREATE_TIME_DESC);
                List<GyOilLog> gyOilLogs = gyOilLogMapper
                        .selectByExample(gyOilLogEx);
                if (!gyOilLogs.isEmpty()) {
                    for (GyOilLog gyOilLog : gyOilLogs) {
                        Map<String, Object> result = new HashMap<String, Object>();
                        if (new Integer("0").equals(gyOilLog.getSupplier())) {
                            result.put("supplier", "2");//0:追电1 高阳2欧飞
                        } else {
                            result.put("supplier", "1");//0:追电1 高阳2欧飞
                        }
                        //result.put("supplier", "1");//0:追电1 高阳2欧飞
                        result.put("id", gyOilLog.getId());
                        result.put("createTime",
                                format.format(gyOilLog.getCreateTime()));
                        result.put("updateTime",
                                format.format(gyOilLog.getUpdateTime()));
                        result.put("orderSn", gyOilLog.getOrderid());
                        result.put("userPhone", gyOilLog.getUserId());
                        //账户余额
                        result.put("userMoney", gyOilLog.getUserId());
                        //订单完成后账户余额
                        result.put("surplusMoney", gyOilLog.getSurplusMoney());
                        //实际完成金额
                        result.put("tardeMoney", gyOilLog.getFinishmoney());
                        //交易金额
                        result.put("fillMoney", gyOilLog.getFillmoney());
                        if (Objects.equals(GyOilLog.CHARGETYPE_1,
                                gyOilLog.getChargetype())) {
                            result.put("transType", OilTransLog.TRANS_TYPE_3);
                        } else if (Objects.equals(GyOilLog.CHARGETYPE_2,
                                gyOilLog.getChargetype())) {
                            result.put("transType", OilTransLog.TRANS_TYPE_2);
                        }
                        if (new Byte("0").equals(gyOilLog.getOrderstatus())) {
                            result.put("respMsg", "充值成功");
                        } else if (new Byte("1")
                                .equals(gyOilLog.getOrderstatus())) {
                            result.put("respMsg",
                                    "充值失败:" + gyOilLog.getResultcode());
                        } else if (new Byte("2")
                                .equals(gyOilLog.getOrderstatus())) {
                            result.put("respMsg", "部分成功");
                        } else if (new Byte("3")
                                .equals(gyOilLog.getOrderstatus())) {
                            result.put("respMsg", "待处理");
                        } else {
                            result.put("respMsg", "处理中");
                        }
                        //result.put("respMsg", gyOilLog.getErrdesc());
                        result.put("transNo", gyOilLog.getJxorderid());
                        result.put("cardNo", gyOilLog.getGascardid());
                        results.add(result);
                    }
                }
            } else {
                //高阳话费
                GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
                com.emate.shop.business.model.GyPhoneLogExample.Criteria cri = gyPhoneLogEx
                        .createCriteria();
                if (StringUtils.isNotEmpty(oilTransLog.getOrderSn())) {
                    cri.andOrderNoEqualTo(oilTransLog.getOrderSn());
                }
                if (StringUtils.isNotEmpty(oilCardNumber)) {
                    cri.andMobileNumEqualTo(oilCardNumber);
                }
                if (!Objects.equals(userId, 0L)) {
                    cri.andUserIdEqualTo(userId);
                }
                if (OilConfig.SUPPLIER_2.equals(oilCompType)) {
                    cri.andSupplierIsNull();
                } else {
                    cri.andSupplierEqualTo(0);
                }
                page = new PaginationUtil(pageNo, pageSize,
                        gyPhoneLogMapper.countByExample(gyPhoneLogEx));
                gyPhoneLogEx.setLimitStart(page.getStartRow());
                gyPhoneLogEx.setLimitEnd(page.getSize());
                gyPhoneLogEx
                        .setOrderByClause(GyPhoneLogExample.CREATE_TIME_DESC);
                List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                        .selectByExample(gyPhoneLogEx);
                if (!gyPhoneLogs.isEmpty()) {
                    for (GyPhoneLog gyPhoneLog : gyPhoneLogs) {
                        Map<String, Object> result = new HashMap<String, Object>();
                        if (new Integer("0").equals(gyPhoneLog.getSupplier())) {
                            result.put("supplier", "2");//0:追电1 高阳2欧飞
                        } else {
                            result.put("supplier", "1");//0:追电1 高阳2欧飞
                        }
                        //result.put("supplier", "1");//0:追电1 高阳
                        result.put("id", gyPhoneLog.getId());
                        result.put("createTime",
                                format.format(gyPhoneLog.getCreateTime()));
                        result.put("updateTime",
                                format.format(gyPhoneLog.getUpdateTime()));
                        result.put("orderSn", gyPhoneLog.getOrderNo());
                        result.put("userPhone", gyPhoneLog.getUserId());
                        //账户剩余金额
                        result.put("userMoney", gyPhoneLog.getUserId());
                        //订单完成后账户余额
                        result.put("surplusMoney",
                                gyPhoneLog.getSurplusMoney());
                        //交易金额
                        result.put("tardeMoney", gyPhoneLog.getOrderMoney());
                        //充值面额
                        result.put("fillMoney", gyPhoneLog.getProdContent());
                        result.put("transType", OilTransLog.TRANS_TYPE_4);
                        if (Objects.equals(GyPhoneLog.STATUS_0,
                                gyPhoneLog.getStatus())) {
                            result.put("respMsg", "处理中");
                        } else if (Objects.equals(GyPhoneLog.STATUS_1,
                                gyPhoneLog.getStatus())) {
                            result.put("respMsg", "处理中");
                        } else if (Objects.equals(GyPhoneLog.STATUS_2,
                                gyPhoneLog.getStatus())) {
                            result.put("respMsg", "充值失败");
                        } else if (Objects.equals(GyPhoneLog.STATUS_3,
                                gyPhoneLog.getStatus())) {
                            result.put("respMsg", "充值成功");
                        } else if (Objects.equals(GyPhoneLog.STATUS_4,
                                gyPhoneLog.getStatus())) {
                            result.put("respMsg", "充值失败");
                        } else if (Objects.equals(GyPhoneLog.STATUS_5,
                                gyPhoneLog.getStatus())) {
                            result.put("respMsg", "待处理");
                        }
                        result.put("transNo", null);
                        result.put("cardNo", gyPhoneLog.getMobileNum());
                        results.add(result);
                    }
                }
            }
        }
        List<Long> userIds = new ArrayList<Long>();
        userIds.add(0L);
        List<String> codes = new ArrayList<String>();
        codes.add("");
        for (Map<String, Object> map : results) {
            Long uId = (Long) map.get("userPhone");
            userIds.add(uId);
            if (Objects.equals(map.get("transType"), 0)) {
                codes.add(String.valueOf(map.get("cardNo")));
            }
        }
        OilUserExample userEx = new OilUserExample();
        userEx.createCriteria().andIdIn(userIds);
        List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
        Map<Long, OilUser> userMap = new HashMap<Long, OilUser>();
        oilUsers.stream().forEach(user -> {
            userMap.put(user.getId(), user);
        });
        //串码充值
        OilRechargeCodeExample codeEx = new OilRechargeCodeExample();
        codeEx.createCriteria().andRechargeCodeIn(codes);
        List<OilRechargeCode> oilCodes = oilRechargeCodeMapper
                .selectByExample(codeEx);
        Map<String, String> oilCodeMap = new HashMap<String, String>();
        oilCodes.stream().forEach(code -> {
            oilCodeMap.put(code.getRechargeCode(), code.getPhone());
        });
        for (Map<String, Object> map : results) {
            OilUser user = userMap.get(map.get("userPhone"));
            if (Objects.isNull(user)) {
                map.put("userPhone", null);
                map.put("userMoney", null);
            } else {
                map.put("userPhone", user.getPhone());
                map.put("userMoney", user.getMoney());
            }
            if (Objects.equals(map.get("transType"), 0)) {
                map.put("cardNo",
                        oilCodeMap.get(String.valueOf(map.get("cardNo"))));
            }
        }
        DatasetList<Map<String, Object>> resultList = DatasetBuilder
                .fromDataList(results, page.createPageInfo());
        return resultList;
    }

    @Override
    @Write
    public void updateOilUser(OilUser oilUser) {
        OilUser o = oilUserMapper.selectByPrimaryKey(oilUser.getId());
        if (Objects.isNull(o)) {
            throw new BusinessException("未查到该油卡用户~~");
        }
        if ((oilUserMapper.updateByPrimaryKeySelective(oilUser)) != 1) {
            throw new BusinessException("更新用户信息失败~~");
        }
        ;
    }

    @Override
    @Read
    public DatasetList<Map<String, String>> exportOilExcel(String oilCompType,
            String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        //分解年月
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);

        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH) + 1);
        now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
        now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
        now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
        now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month - 1);
        Date startTime = now.getTime();
        now.set(Calendar.MONTH, month);
        Date endTime = now.getTime();

        if ("1".equals(oilCompType)) {
            OilTransLogExample oilTransLogEx = new OilTransLogExample();
            Criteria c = oilTransLogEx.createCriteria();
            c.andCreateTimeGreaterThanOrEqualTo(startTime);
            c.andCreateTimeLessThan(endTime);
            //追电油卡订单信息
            List<OilTransLog> oilTransLogs = oilTransLogMapper
                    .selectByExample(oilTransLogEx);

            for (OilTransLog oilLog : oilTransLogs) {
                Map<String, String> result = new HashMap<String, String>();
                //服务商
                result.put("oilComp", "追电科技");
                //订单号
                result.put("orderNo", oilLog.getOrderSn());
                //创建时间
                result.put("createTime", format.format(oilLog.getCreateTime()));
                //用户账号
                result.put("userPhone", String.valueOf(oilLog.getUserId()));
                //交易金额
                result.put("money",
                        String.valueOf(oilLog.getTardeMoney().doubleValue()));

                //订单完成后账户余额
                if (Objects.isNull(oilLog.getUserMoney())) {
                    result.put("surplusMoney", "0");
                } else {
                    result.put("surplusMoney", String
                            .valueOf(oilLog.getUserMoney().doubleValue()));
                }

                //订单类型
                if (OilTransLog.TRANS_TYPE_0.equals(oilLog.getTransType())) {
                    result.put("orderType", "串码充值");
                } else if (OilTransLog.TRANS_TYPE_1
                        .equals(oilLog.getTransType())) {
                    result.put("orderType", "电子券充值");
                } else if (OilTransLog.TRANS_TYPE_2
                        .equals(oilLog.getTransType())) {
                    result.put("orderType", "中石油油卡");
                } else if (OilTransLog.TRANS_TYPE_3
                        .equals(oilLog.getTransType())) {
                    result.put("orderType", "中石化油卡");
                } else if (OilTransLog.TRANS_TYPE_4
                        .equals(oilLog.getTransType())) {
                    result.put("orderType", "手机充值");
                } else {
                    result.put("orderType", "未知类型");
                }
                //充值卡号
                result.put("cardNo", oilLog.getRemark());
                //充值状态
                result.put("status", "充值成功");
                if (0 != oilLog.getTransType() && 5 != oilLog.getTransType()
                        && 6 != oilLog.getTransType()) {
                    Gson gson = new Gson();
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = gson.fromJson(oilLog.getRemark(),
                            Map.class);
                    Set<Entry<String, Object>> entry = map.entrySet();
                    switch (oilLog.getTransType()) {
                        case 1:
                            for (Entry<String, Object> en : entry) {
                                if ("subList".equals(en.getKey())) {
                                    @SuppressWarnings("unchecked")
                                    List<Map<String, Object>> value = (List<Map<String, Object>>) en
                                            .getValue();
                                    Map<String, Object> detail = value.get(0);
                                    result.put("status", String
                                            .valueOf(detail.get("respMsg")));
                                    result.put("cardNo",
                                            String.valueOf(oilLog.getUserId()));
                                }
                            }
                            break;
                        default:
                            for (Entry<String, Object> en : entry) {
                                if ("respMsg".equals(en.getKey())) {
                                    result.put("status",
                                            String.valueOf(en.getValue()));
                                } else if ("cardNo".equals(en.getKey())) {
                                    result.put("cardNo",
                                            String.valueOf(en.getValue()));
                                }
                            }
                            break;
                    }
                }
                results.add(result);
            }
        } else {
            //高阳油卡
            GyOilLogExample gyOilLogEx = new GyOilLogExample();
            com.emate.shop.business.model.GyOilLogExample.Criteria c = gyOilLogEx
                    .createCriteria();
            c.andCreateTimeGreaterThanOrEqualTo(startTime)
                    .andCreateTimeLessThan(endTime);
            if ("4".equals(oilCompType)) {
                c.andSupplierEqualTo(0);
            } else {
                c.andSupplierIsNull();
            }

            List<GyOilLog> gyOilLogs = gyOilLogMapper
                    .selectByExample(gyOilLogEx);
            if (!gyOilLogs.isEmpty()) {
                for (GyOilLog gyOilLog : gyOilLogs) {
                    Map<String, String> result = new HashMap<String, String>();
                    //服务商
                    if ("4".equals(oilCompType)) {
                        result.put("oilComp", "欧飞油卡");
                    } else {
                        result.put("oilComp", "高阳油卡");
                    }
                    //result.put("oilComp", "高阳油卡");
                    //订单号
                    result.put("orderNo", gyOilLog.getOrderid());
                    //创建时间
                    result.put("createTime",
                            format.format(gyOilLog.getCreateTime()));
                    //订单状态
                    if (new Byte("0").equals(gyOilLog.getOrderstatus())) {
                        result.put("status", "充值成功");
                    } else if (new Byte("1")
                            .equals(gyOilLog.getOrderstatus())) {
                        result.put("status", "充值失败");
                    } else {
                        result.put("status", "处理中");
                    }
                    //result.put("status", gyOilLog.getErrdesc());
                    //充值卡号
                    result.put("cardNo", gyOilLog.getGascardid());
                    //充值账户
                    result.put("userPhone",
                            String.valueOf(gyOilLog.getUserId()));
                    //充值金额
                    result.put("money", String
                            .valueOf(gyOilLog.getFillmoney().doubleValue()));

                    //订单完成后的账户余额
                    if (Objects.isNull(gyOilLog.getSurplusMoney())) {
                        result.put("surplusMoney", "0");
                    } else {
                        result.put("surplusMoney", String.valueOf(
                                gyOilLog.getSurplusMoney().doubleValue()));
                    }

                    //订单类型
                    if (Objects.equals(GyOilLog.CHARGETYPE_1,
                            gyOilLog.getChargetype())) {
                        result.put("orderType", "中石化油卡");
                    } else if (Objects.equals(GyOilLog.CHARGETYPE_2,
                            gyOilLog.getChargetype())) {
                        result.put("orderType", "中石油油卡");
                    }
                    results.add(result);
                }
            }
            //高阳话费
            GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();

            com.emate.shop.business.model.GyPhoneLogExample.Criteria cr = gyPhoneLogEx
                    .createCriteria();
            cr.andCreateTimeGreaterThanOrEqualTo(startTime)
                    .andCreateTimeLessThan(endTime);
            if ("4".equals(oilCompType)) {
                cr.andSupplierEqualTo(0);
            } else {
                cr.andSupplierIsNull();
            }
            List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                    .selectByExample(gyPhoneLogEx);
            if (!gyPhoneLogs.isEmpty()) {
                for (GyPhoneLog gyPhoneLog : gyPhoneLogs) {
                    Map<String, String> result = new HashMap<String, String>();
                    //服务商
                    if ("4".equals(oilCompType)) {
                        result.put("oilComp", "欧飞油卡");//0:追电1 高阳
                    } else {
                        result.put("oilComp", "高阳油卡");//0:追电1 高阳
                    }
                    //result.put("oilComp", "高阳油卡");//0:追电1 高阳
                    //订单号
                    result.put("orderNo", gyPhoneLog.getOrderNo());
                    //创建时间
                    result.put("createTime",
                            format.format(gyPhoneLog.getCreateTime()));
                    //用户账号
                    result.put("userPhone",
                            String.valueOf(gyPhoneLog.getUserId()));
                    //交易金额
                    result.put("money", gyPhoneLog.getProdContent());

                    //订单完成时的账户余额
                    if (Objects.isNull(gyPhoneLog.getSurplusMoney())) {
                        result.put("surplusMoney", "0");
                    } else {
                        result.put("surplusMoney", String.valueOf(
                                gyPhoneLog.getSurplusMoney().doubleValue()));
                    }
                    //充值卡号
                    result.put("cardNo", gyPhoneLog.getMobileNum());
                    //订单类型
                    result.put("orderType", "手机充值");
                    //订单状态
                    if (Objects.equals(GyPhoneLog.STATUS_0,
                            gyPhoneLog.getStatus())) {
                        result.put("status", "下单成功");
                    } else if (Objects.equals(GyPhoneLog.STATUS_1,
                            gyPhoneLog.getStatus())) {
                        result.put("status", "下单失败扣款未知");
                    } else if (Objects.equals(GyPhoneLog.STATUS_2,
                            gyPhoneLog.getStatus())) {
                        result.put("status", "下单失败未扣款");
                    } else if (Objects.equals(GyPhoneLog.STATUS_3,
                            gyPhoneLog.getStatus())) {
                        result.put("status", "充值成功");
                    } else if (Objects.equals(GyPhoneLog.STATUS_4,
                            gyPhoneLog.getStatus())) {
                        result.put("status", "充值失败");
                    } else if (Objects.equals(GyPhoneLog.STATUS_5,
                            gyPhoneLog.getStatus())) {
                        result.put("status", "账户扣款失败");
                    }

                    results.add(result);
                }
            }
        }
        List<Long> userIds = new ArrayList<Long>();
        userIds.add(0L);
        List<String> codes = new ArrayList<String>();
        codes.add("");
        for (Map<String, String> map : results) {
            Long uId = Long.valueOf(map.get("userPhone"));
            userIds.add(uId);
            if ("串码充值".equals(map.get("orderType"))) {
                codes.add(map.get("cardNo"));
            }
        }
        OilUserExample userEx = new OilUserExample();
        userEx.createCriteria().andIdIn(userIds);
        List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
        Map<Long, String> userMap = new HashMap<Long, String>();
        oilUsers.stream().forEach(user -> {
            userMap.put(user.getId(), user.getPhone());
        });
        //串码充值
        OilRechargeCodeExample codeEx = new OilRechargeCodeExample();
        codeEx.createCriteria().andRechargeCodeIn(codes);
        List<OilRechargeCode> oilCodes = oilRechargeCodeMapper
                .selectByExample(codeEx);
        Map<String, String> oilCodeMap = new HashMap<String, String>();
        oilCodes.stream().forEach(code -> {
            oilCodeMap.put(code.getRechargeCode(), code.getPhone());
        });
        for (Map<String, String> map : results) {
            String userPhone = userMap.get(Long.valueOf(map.get("userPhone")));
            if (StringUtils.isEmpty(userPhone)) {
                map.put("userPhone", null);
            } else {
                map.put("userPhone", userPhone);
            }
            if ("串码充值".equals(map.get("orderType"))) {
                map.put("cardNo",
                        oilCodeMap.get(String.valueOf(map.get("cardNo"))));
            }
            if ("电子券充值".equals(map.get("orderType"))) {
                map.put("cardNo", userPhone);
            }
        }
        return DatasetBuilder.fromDataList(results);
    }

    @Override
    @Write
    public DatasetList<Map<String, String>> exportOilCodeExcel(String startCode,
            String endCode) {
        if (StringUtils.isEmpty(startCode)) {
            throw new BusinessException("起始序号不能为空~");
        }
        if (StringUtils.isEmpty(endCode)) {
            throw new BusinessException("结束序号不能为空~");
        }
        if (Long.valueOf(startCode) > Long.valueOf(endCode)) {
            throw new BusinessException("起始序号不能大于结束序号");
        }
        if (startCode.length() != 9 || endCode.length() != 9) {
            throw new BusinessException("序号必须是9位数字");
        }
        //组织搜索条件
        OilRechargeCodeExample codeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = codeEx
                .createCriteria();
        c.andPhoneGreaterThanOrEqualTo(startCode);
        c.andPhoneLessThanOrEqualTo(endCode);
        List<OilRechargeCode> codes = oilRechargeCodeMapper
                .selectByExample(codeEx);
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        codes.stream().forEach(code -> {
            Map<String, String> result = new HashMap<String, String>();
            result.put("phone", code.getPhone());
            result.put("money", String.valueOf(code.getMoney().intValue()));
            result.put("address", code.getAddress());
            if (0 == code.getStatus()) {
                result.put("status", "未使用");
            } else if (1 == code.getStatus()
                    && Objects.isNull(code.getRechargeTime())) {
                result.put("status", "未开通");
            } else if (1 == code.getStatus()
                    && Objects.nonNull(code.getRechargeTime())) {
                result.put("status", "已使用");
            }
            Calendar now = Calendar.getInstance(Locale.CHINA);
            now.setTime(code.getCreateTime());
            now.add(Calendar.YEAR, 1);
            now.add(Calendar.MONTH, 1);

            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            String expiredTime = format.format(now.getTime());
            result.put("expiredTime", expiredTime);
            results.add(result);
        });
        return DatasetBuilder.fromDataList(results);
    }

    /*
    @Override
    @Write
    @Transactional
    public DatasetSimple<Map<String, String>> updateOilCard(String startCode,
            String endCode, String address, String supplier, String buyer,
            String remark, String proposer) {
        if (StringUtils.isEmpty(endCode) || StringUtils.isEmpty(startCode)) {
            throw new BusinessException("请输入序列号");
        }
        if (endCode.length() != 9 || startCode.length() != 9) {
            throw new BusinessException("请输入正确序列号");
        }
        if (Long.valueOf(startCode) > Long.valueOf(endCode)) {
            throw new BusinessException("请输入正确序列号");
        }
        if (Integer.valueOf(startCode) > 300000000
                || Integer.valueOf(endCode) > 300000000) {
            String str1 = startCode.substring(0, 3);
            String str2 = endCode.substring(0, 3);
            if (!str1.equals(str2)) {
                throw new BusinessException("起始序号和结束序号必须在同一个月生成的");
            }
        }
        if (StringUtils.isEmpty(address)) {
            throw new BusinessException("请输入开通城市");
        }
        if (StringUtils.isEmpty(supplier)) {
            throw new BusinessException("请输入供应商");
        }
        if (StringUtils.isEmpty(buyer)) {
            throw new BusinessException("请输入购买方");
        }
        if (StringUtils.isEmpty(proposer)) {
            throw new BusinessException("请输入申请人");
        }

        OilRechargeCodeExample oilCodeEx = new OilRechargeCodeExample();
        oilCodeEx.createCriteria().andPhoneGreaterThanOrEqualTo(startCode)
                .andPhoneLessThanOrEqualTo(endCode);
        //.andRechargeTimeIsNull()
        //.andStatusEqualTo(OilRechargeCode.STATUS_1);
        List<OilRechargeCode> oilRechargeCodes = oilRechargeCodeMapper
                .selectByExample(oilCodeEx);
        //判断数量对不对
        int count = oilRechargeCodes.size();
        int num = new BigDecimal(endCode).subtract(new BigDecimal(startCode))
                .intValue() + 1;
        if (count != num) {
            throw new BusinessException("存在未处理完的油卡");
        }
        //判断是否存在多种金额
        Set<String> moneySet = new HashSet<String>();
        String str = "该批油卡存在其他状态的油卡：";
        for (OilRechargeCode oilRechargeCode : oilRechargeCodes) {
            BigDecimal money = oilRechargeCode.getMoney();
            moneySet.add(String.valueOf(money.intValue()));
            Date deadTime = oilRechargeCode.getDeadTime();
            Calendar now = Calendar.getInstance(Locale.CHINA);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            Date time = now.getTime();
            if (Objects.nonNull(deadTime)) {
                if (time.compareTo(deadTime) > 0) {
                    throw new BusinessException("该批油卡已过时效时间！！");
                }
            }
            Date rechargeTime = oilRechargeCode.getRechargeTime();
            Integer status = oilRechargeCode.getStatus();
            if (Objects.nonNull(rechargeTime)
                    && OilRechargeCode.STATUS_1.equals(status)) {
                str = str + "已使用,";
            } else if (OilRechargeCode.STATUS_0.equals(status)) {
                str = str + "未使用,";
            } else if (OilRechargeCode.STATUS_2.equals(status)) {
                str = str + "已冻结,";
            } else if (OilRechargeCode.STATUS_3.equals(status)) {
                str = str + "已过期,";
            }
        }
        if (moneySet.size() > 1) {
            throw new BusinessException("存在多种金额的油卡，请重新选择！！！");
        }
        if (!"该批油卡存在其他状态的油卡：".equals(str)) {
            str = str.substring(0, str.length() - 1);
            throw new BusinessException(str);
        }
        //添加开卡批次
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String openTime = format.format(new Date());
        Integer maxBatchNum = definedMapper.findMaxBatchNum(openTime);
        Integer newBatchNum = 1;
        if (maxBatchNum != 0) {
            newBatchNum = maxBatchNum + 1;
        }
        oilCodeEx.clear();
        oilCodeEx.createCriteria().andPhoneGreaterThanOrEqualTo(startCode)
                .andPhoneLessThanOrEqualTo(endCode);
        OilRechargeCode oilRechargeCode = new OilRechargeCode();
        oilRechargeCode.setAddress(address);
        oilRechargeCode.setStatus(OilRechargeCode.STATUS_0);
        oilRechargeCode.setSupplier(Integer.valueOf(supplier));
        oilRechargeCode.setBuyer(buyer);
        oilRechargeCode.setBatchCode(newBatchNum);
        oilRechargeCode.setOpenTime(new Date());
        int updateNum = oilRechargeCodeMapper
                .updateByExampleSelective(oilRechargeCode, oilCodeEx);
        if (updateNum != num || updateNum != count) {
            throw new BusinessException("开通油卡失败,请联系技术");
        }
        BigDecimal money = oilRechargeCodes.get(0).getMoney();
        OilBatch oilBatch = new OilBatch();
        oilBatch.setBatchNumber(newBatchNum);
        oilBatch.setCity(address);
        oilBatch.setMoney(money);
        oilBatch.setCreateTime(new Date());
        oilBatch.setCount(updateNum);
        oilBatch.setOpenTime(openTime);
        oilBatch.setType(0);
        oilBatch.setUpdateTime(new Date());
        oilBatch.setStartNumber(startCode);
        oilBatch.setEndNumber(endCode);
        oilBatch.setRemark(remark);
        oilBatch.setSupplier(Integer.valueOf(supplier));
        oilBatch.setBuyer(buyer);
        oilBatch.setProposer(proposer);
        if (oilBatchMapper.insertSelective(oilBatch) != 1) {
            throw new BusinessException("添加批次记录失败~~");
        }
        ;

        Map<String, String> result = new HashMap<String, String>();
        result.put("number", String.valueOf(updateNum));
        result.put("money", String.valueOf(money.intValue()));
        return DatasetBuilder.fromDataSimple(result);
    }
    */
    
    /**
     * 开通省份加入湖南后的service实现方法
     */
    @Override
    @Write
    @Transactional
    public DatasetSimple<Map<String, String>> updateOilCard(String startCode,
            String endCode, String province, String address, String supplier, String buyer,
            String remark, String proposer) {
        if (StringUtils.isEmpty(endCode) || StringUtils.isEmpty(startCode)) {
            throw new BusinessException("请输入序列号");
        }
        if (endCode.length() != 9 || startCode.length() != 9) {
            throw new BusinessException("请输入正确序列号");
        }
        if (Long.valueOf(startCode) > Long.valueOf(endCode)) {
            throw new BusinessException("请输入正确序列号");
        }
        if (Integer.valueOf(startCode) > 300000000
                || Integer.valueOf(endCode) > 300000000) {
            String str1 = startCode.substring(0, 3);
            String str2 = endCode.substring(0, 3);
            if (!str1.equals(str2)) {
                throw new BusinessException("起始序号和结束序号必须在同一个月生成的");
            }
        }
        if (StringUtils.isEmpty(province)) {
        	throw new BusinessException("请输入开通省份");
        }
        if (StringUtils.isEmpty(address)) {
            throw new BusinessException("请输入开通城市");
        }
        if (StringUtils.isEmpty(supplier)) {
            throw new BusinessException("请输入供应商");
        }
        if (StringUtils.isEmpty(buyer)) {
            throw new BusinessException("请输入购买方");
        }
        if (StringUtils.isEmpty(proposer)) {
            throw new BusinessException("请输入申请人");
        }
      
		
        OilRechargeCodeExample oilCodeEx = new OilRechargeCodeExample();
        oilCodeEx.createCriteria().andPhoneGreaterThanOrEqualTo(startCode)
                .andPhoneLessThanOrEqualTo(endCode);
        //.andRechargeTimeIsNull()
        //.andStatusEqualTo(OilRechargeCode.STATUS_1);
        List<OilRechargeCode> oilRechargeCodes = oilRechargeCodeMapper
                .selectByExample(oilCodeEx);
        //判断数量对不对
        int count = oilRechargeCodes.size();
        int num = new BigDecimal(endCode).subtract(new BigDecimal(startCode))
                .intValue() + 1;
        if (count != num) {
            throw new BusinessException("存在未处理完的油卡");
        }
        //判断是否存在多种金额
        Set<String> moneySet = new HashSet<String>();
        String str = "该批油卡存在其他状态的油卡：";
        for (OilRechargeCode oilRechargeCode : oilRechargeCodes) {
            BigDecimal money = oilRechargeCode.getMoney();
            moneySet.add(String.valueOf(money.intValue()));
            Date deadTime = oilRechargeCode.getDeadTime();
            Calendar now = Calendar.getInstance(Locale.CHINA);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            Date time = now.getTime();
            if (Objects.nonNull(deadTime)) {
                if (time.compareTo(deadTime) > 0) {
                    throw new BusinessException("该批油卡已过时效时间！！");
                }
            }
            Date rechargeTime = oilRechargeCode.getRechargeTime();
            Integer status = oilRechargeCode.getStatus();
            if (Objects.nonNull(rechargeTime)
                    && OilRechargeCode.STATUS_1.equals(status)) {
                str = str + "已使用,";
            } else if (OilRechargeCode.STATUS_0.equals(status)) {
                str = str + "未使用,";
            } else if (OilRechargeCode.STATUS_2.equals(status)) {
                str = str + "已冻结,";
            } else if (OilRechargeCode.STATUS_3.equals(status)) {
                str = str + "已过期,";
            }
        }
        if (moneySet.size() > 1) {
            throw new BusinessException("存在多种金额的油卡，请重新选择！！！");
        }
        if (!"该批油卡存在其他状态的油卡：".equals(str)) {
            str = str.substring(0, str.length() - 1);
            throw new BusinessException(str);
        }
        //添加开卡批次
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String openTime = format.format(new Date());
        Integer maxBatchNum = definedMapper.findMaxBatchNum(openTime);
        Integer newBatchNum = 1;
        if (maxBatchNum != 0) {
            newBatchNum = maxBatchNum + 1;
        }
        oilCodeEx.clear();
        oilCodeEx.createCriteria().andPhoneGreaterThanOrEqualTo(startCode)
                .andPhoneLessThanOrEqualTo(endCode);
        OilRechargeCode oilRechargeCode = new OilRechargeCode();
        oilRechargeCode.setProvince(province);
        oilRechargeCode.setAddress(address);    
        oilRechargeCode.setStatus(OilRechargeCode.STATUS_0);
        oilRechargeCode.setSupplier(Integer.valueOf(supplier));
        oilRechargeCode.setBuyer(buyer);
        oilRechargeCode.setBatchCode(newBatchNum);
        oilRechargeCode.setOpenTime(new Date());
        int updateNum = oilRechargeCodeMapper
                .updateByExampleSelective(oilRechargeCode, oilCodeEx);
        if (updateNum != num || updateNum != count) {
            throw new BusinessException("开通油卡失败,请联系技术");
        }
        BigDecimal money = oilRechargeCodes.get(0).getMoney();
        OilBatch oilBatch = new OilBatch();
        oilBatch.setBatchNumber(newBatchNum);
        oilBatch.setProvince(province);
        oilBatch.setCity(address);
        oilBatch.setMoney(money);
        oilBatch.setCreateTime(new Date());
        oilBatch.setCount(updateNum);
        oilBatch.setOpenTime(openTime);
        oilBatch.setType(0);
        oilBatch.setUpdateTime(new Date());
        oilBatch.setStartNumber(startCode);
        oilBatch.setEndNumber(endCode);
        oilBatch.setRemark(remark);
        oilBatch.setSupplier(Integer.valueOf(supplier));
        oilBatch.setBuyer(buyer);
        oilBatch.setProposer(proposer);
        if (oilBatchMapper.insertSelective(oilBatch) != 1) {
            throw new BusinessException("添加批次记录失败~~");
        }
        ;

        Map<String, String> result = new HashMap<String, String>();
        result.put("number", String.valueOf(updateNum));
        result.put("money", String.valueOf(money.intValue()));
        return DatasetBuilder.fromDataSimple(result);
    }
    
    

    @SuppressWarnings("static-access")
    @Override
    @Transactional
    @Write
    public void updateGyOilLogByTimer(String gyUrl, String merchantid,
            String key) {

        //查询未完成的订单号集合
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - 1);
        now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
        now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
        now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
        now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));

        List<String> orderids = definedMapper
                .queryUnfinishOilLog(now.getTime());

        //循环订单查询高阳订单查询接口
        for (String orderid : orderids) {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            OilCardSearchResult oilCardCallBackVo = GaoYangOilCardUtil
                    .orderquerynew(gyUrl, merchantid, key, orderid);
            if ("3".equals(oilCardCallBackVo.getOrderstatus())) {
                Log4jHelper.getLogger().error("订单正在处理中");
                continue;
            }
            //根据orderid查询油卡订单
            GyOilLogExample gyOilLogExample = new GyOilLogExample();
            gyOilLogExample.createCriteria().andOrderidEqualTo(orderid)
                    .andOrderstatusIsNull();
            List<GyOilLog> gyOilLogs = gyOilLogMapper
                    .selectByExample(gyOilLogExample);
            if (gyOilLogs.isEmpty()) {
                Log4jHelper.getLogger().error("找不到订单");
                continue;
            }
            GyOilLog log = gyOilLogs.get(0);

            //该订单状态
            log.setOrderstatus(new Byte(oilCardCallBackVo.getOrderstatus()));

            //充值完成金额
            log.setFinishmoney(
                    new BigDecimal(oilCardCallBackVo.getFinishmoney()));

            //响应代码
            log.setResultcode(oilCardCallBackVo.getErrorcode());

            //响应消息
            String errorinfo = null;
            try {
                errorinfo = URLDecoder.decode(oilCardCallBackVo.getErrorinfo(),
                        "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log4jHelper.getLogger().error("errorinfo字段解码失败");
                continue;
            }
            log.setErrdesc(errorinfo);

            //完成时间
            Date finishtime = null;
            try {
                finishtime = new SimpleDateFormat("yyyyMMddHHmmss")
                        .parse(oilCardCallBackVo.getFinishtime());
            } catch (ParseException e) {
                e.printStackTrace();
                Log4jHelper.getLogger().error("finishtime字段转移失败");
                continue;
            }
            log.setFinishtime(finishtime);

            //更新用户账户金额
            if ("0".equals(oilCardCallBackVo.getOrderstatus())) {//0成功1失败2部分成功
                Log4jHelper.getLogger().info("高阳油卡充值订单查询ok.");
            } else if ("1".equals(oilCardCallBackVo.getOrderstatus())) {
                Log4jHelper.getLogger().info("高阳油卡充值主动查询,充值失败.回退用户金额。"
                        + log.getFillmoney().intValue());
                DatasetSimple<String> modifyUserMoney = null;
                try {
                    modifyUserMoney = modifyUserMoney(log.getUserId(),
                            log.getFillmoney().intValue(), log.getOrderid(),
                            "0");
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage());
                }
                if (Objects.isNull(modifyUserMoney)) {
                    throw new BusinessException("查询订单失败！！");
                }
                log.setSurplusMoney(new BigDecimal(modifyUserMoney.getData()));
            } else {
                Log4jHelper.getLogger()
                        .info("高阳油卡充值主动查询,充值部分成功.回退用户金额。"
                                + log.getFillmoney().subtract(new BigDecimal(
                                        oilCardCallBackVo.getFinishmoney())));
                DatasetSimple<String> modifyUserMoney = null;
                try {
                    modifyUserMoney = modifyUserMoney(log.getUserId(),
                            log.getFillmoney()
                                    .subtract(new BigDecimal(
                                            oilCardCallBackVo.getFinishmoney()))
                                    .intValue(),
                            log.getOrderid(), "0");
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage());
                }
                if (Objects.isNull(modifyUserMoney)) {
                    throw new BusinessException("查询订单结果失败");
                }
                log.setSurplusMoney(new BigDecimal(modifyUserMoney.getData()));
            }

            //更新订单
            gyOilLogMapper.updateByPrimaryKeySelective(log);

            Log4jHelper.getLogger().info("油卡订单充值订单[{}]主动查询完成...", orderid);
        }
        Log4jHelper.getLogger().info("今日高阳油卡订单主动查询全部完成...");
    }

    @Override
    @Transactional
    @Write
    public void updateOilOrdersByOfTimer() {
        //查询未完成的订单号集合
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        ArrayList<Byte> orderTypeList = new ArrayList<Byte>();
        orderTypeList.add(OilOrders.CHARGE_TYPE_1);
        orderTypeList.add(OilOrders.CHARGE_TYPE_2);
        orderTypeList.add(OilOrders.CHARGE_TYPE_3);
        orderTypeList.add(OilOrders.CHARGE_TYPE_4);
        oilOrdersEx.createCriteria()
                .andOrderStatusEqualTo(OilOrders.ORDER_STATUS_1)
                .andChargeTypeIn(orderTypeList).andRequestNumLessThan(5);
        oilOrdersEx.setOrderByClause(GyOilLogExample.CREATE_TIME_DESC);
        oilOrdersEx.setLimitStart(0);
        oilOrdersEx.setLimitEnd(500);
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);
        //若不存在处理中订单，返回结果
        if (oilOrdersList.isEmpty()) {
            Log4jHelper.getLogger().info("未查询到处理中的订单");
            return;
        }
        //若存在，循环订单查询高阳订单查询接口
        String ofUrl = this.environment.getProperty("ofoil.orderquery.url");
        for (OilOrders oilOrders : oilOrdersList) {
            oilOrders = definedMapper.queryOilOrdersbyId(oilOrders.getId());
            if (!OilOrders.ORDER_STATUS_1.equals(oilOrders.getOrderStatus())) {
                continue;
            }
            Map<String, String> params = new HashMap<String, String>();
            OilProvider oilProvider = oilProviderMapper
                    .selectByPrimaryKey(oilOrders.getProvider());
            if (Objects.isNull(oilProvider)) {
                Log4jHelper.getLogger()
                        .error("未查询到该订单的接口提供者，订单号：" + oilOrders.getOrderNo());
                continue;
            }
            params.put("userid", oilProvider.getUserName());
            String orderNo = oilOrders.getOrderNo();
            //添加查询订单记录
            OilLog oilLog = new OilLog();
            oilLog.setCreateTime(new Date());
            oilLog.setUpdateTime(new Date());
            oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_2);
            oilLog.setOrderNo(orderNo);
            if (OilOrders.CHARGE_TYPE_1.equals(oilOrders.getChargeType())) {
                oilLog.setOrderType(OilLog.ORDER_TYPE_0);
            } else if (OilOrders.CHARGE_TYPE_2
                    .equals(oilOrders.getChargeType())) {
                oilLog.setOrderType(OilLog.ORDER_TYPE_1);
            } else if (OilOrders.CHARGE_TYPE_3
                    .equals(oilOrders.getChargeType())) {
                oilLog.setOrderType(OilLog.ORDER_TYPE_2);
            } else if (OilOrders.CHARGE_TYPE_4
                    .equals(oilOrders.getChargeType())) {
                oilLog.setOrderType(OilLog.ORDER_TYPE_5);
            }
            params.put("spbillid", orderNo);
            String orderstatus;
            try {
                orderstatus = OufeiOilUtil.queryOilOrderByOf(ofUrl, params,
                        oilLog);
            } catch (Exception e) {
                Log4jHelper.getLogger().error("欧飞订单查询接口调用失败");
                e.printStackTrace();
                continue;
            }
            oilLogMapper.insertSelective(oilLog);
            //订单处理中
            if ("0".equals(orderstatus)) {
                Log4jHelper.getLogger().info("订单正在处理中");
                Integer requestNum = oilOrders.getRequestNum();
                oilOrders.setRequestNum(++requestNum);
                Date updateTime = oilOrders.getUpdateTime();
                oilOrders.setUpdateTime(new Date());
                //更新订单信息
                oilOrdersEx.clear();
                oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                        .andUpdateTimeEqualTo(updateTime);
                if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                        oilOrdersEx) != 1) {
                    Log4jHelper.getLogger().error("更新订单状态失败");
                    continue;
                }
                ;
                continue;
            }
            //订单不存在
            if ("-1".equals(orderstatus)) {
                Log4jHelper.getLogger().error("欧飞查找不到该笔订单");

                Integer requestNum = oilOrders.getRequestNum();
                oilOrders.setRequestNum(++requestNum);
                Date updateTime = oilOrders.getUpdateTime();
                oilOrders.setUpdateTime(new Date());
                //更新订单信息
                oilOrdersEx.clear();
                oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                        .andUpdateTimeEqualTo(updateTime);
                if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                        oilOrdersEx) != 1) {
                    Log4jHelper.getLogger().error("更新订单状态失败");
                    continue;
                }
                ;
                continue;
            }
            //订单充值成功
            if ("1".equals(orderstatus)) {
                Log4jHelper.getLogger().info("欧飞充值订单查询ok.");
                //该订单状态
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_2);
                ;
                //充值完成金额
                oilOrders.setFinishMoney(oilOrders.getFillMoney());
                //完成时间
                oilOrders.setFinishTime(new Date());
                //更新请求次数
                Integer requestNum = oilOrders.getRequestNum();
                oilOrders.setRequestNum(++requestNum);

                Date updateTime = oilOrders.getUpdateTime();
                oilOrders.setUpdateTime(new Date());
                //更新订单信息
                oilOrdersEx.clear();
                oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                        .andUpdateTimeEqualTo(updateTime);
                if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                        oilOrdersEx) != 1) {
                    Log4jHelper.getLogger().error("更新订单状态失败");
                    continue;
                }
                ;
                //订单充值失败
            } else if ("9".equals(orderstatus)) {//充值失败

                Log4jHelper.getLogger().info("欧飞充值主动查询,充值失败.回退用户金额。"
                        + oilOrders.getFillMoney().intValue());
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_4);
                oilOrders.setFinishMoney(BigDecimal.ZERO);
                //完成时间
                oilOrders.setFinishTime(new Date());

                //更新请求次数
                Integer requestNum = oilOrders.getRequestNum();
                oilOrders.setRequestNum(++requestNum);

                //用户金额
                OilUser oilUser = definedMapper
                        .queryOilUserbyId(oilOrders.getUserId());
                if (Objects.isNull(oilUser)) {
                    Log4jHelper.getLogger().error("未能查询到下单用户信息");
                    throw new BusinessException("未能查询到下单用户信息");
                }
                BigDecimal surplusMoney = oilUser.getMoney()
                        .add(oilOrders.getFillMoney());
                oilOrders.setSurplusMoney(surplusMoney);

                Date updateTime = oilOrders.getUpdateTime();
                oilOrders.setUpdateTime(new Date());
                //更新订单信息
                oilOrdersEx.clear();
                oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                        .andUpdateTimeEqualTo(updateTime);
                if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                        oilOrdersEx) != 1) {
                    Log4jHelper.getLogger().error("更新订单状态失败");
                    continue;
                }
                ;
                try {
                    addOilUserMoney(oilUser, oilOrders.getOrderNo(),
                            String.valueOf(surplusMoney.doubleValue()),
                            String.valueOf(oilOrders.getChargeType()));
                } catch (Exception e) {
                    Log4jHelper.getLogger().error(e.getMessage());
                    throw new BusinessException(e.getMessage());
                }

                //未知状态码
            } else {
                Log4jHelper.getLogger().info("未知返回码:" + orderstatus);

                Integer requestNum = oilOrders.getRequestNum();
                oilOrders.setRequestNum(++requestNum);
                Date updateTime = oilOrders.getUpdateTime();
                oilOrders.setUpdateTime(new Date());
                //更新订单信息
                oilOrdersEx.clear();
                oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                        .andUpdateTimeEqualTo(updateTime);
                if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                        oilOrdersEx) != 1) {
                    Log4jHelper.getLogger().error("更新订单状态失败");
                    continue;
                }
                ;
                continue;
            }
            Log4jHelper.getLogger().info("欧飞订单充值订单[{}]主动查询完成...",
                    oilOrders.getOrderNo());
        }
        Log4jHelper.getLogger().info("欧飞订单主动查询全部完成...");
    }

    @Override
    @Read
    public DatasetList<OilTransLog> queryPhoneLogList(Long userId,
            String orderNo, String userPhone, Integer pageNo,
            Integer pageSize) {
        if (pageSize > 500) {
            throw new BusinessException("查询信息太多,请减少每页数量");
        }
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        com.emate.shop.business.model.GyPhoneLogExample.Criteria c = gyPhoneLogEx
                .createCriteria();
        //用户id
        c.andUserIdEqualTo(userId);
        //服务商 :欧飞
        c.andSupplierEqualTo(GyPhoneLog.SUPPLIER_0);
        //订单号
        if (StringUtils.isNotEmpty(orderNo)) {
            c.andOrderNoEqualTo(orderNo);
        }
        //被充值手机号
        if (StringUtils.isNotEmpty(userPhone)) {
            c.andMobileNumEqualTo(userPhone);
        }
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                gyPhoneLogMapper.countByExample(gyPhoneLogEx));
        gyPhoneLogEx.setLimitStart(page.getStartRow());
        gyPhoneLogEx.setLimitEnd(page.getSize());
        //根据创建时间排序
        gyPhoneLogEx.setOrderByClause(GyPhoneLogExample.CREATE_TIME_DESC);
        List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                .selectByExample(gyPhoneLogEx);

        //组织返回结果
        List<OilTransLog> results = new ArrayList<OilTransLog>();
        gyPhoneLogs.stream().forEach(gyPhoneLog -> {
            OilTransLog log = new OilTransLog();
            log.setTransType(OilTransLog.TRANS_TYPE_4);
            if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_0
                    || gyPhoneLog.getStatus() == GyPhoneLog.STATUS_1) {//处理中
                log.setRemark("1");
                log.setTardeMoney(new BigDecimal(gyPhoneLog.getProdContent()));
            } else if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_3) {//充值成功
                log.setRemark("0");
                log.setTardeMoney(gyPhoneLog.getOrderMoney());
            } else {//充值失败
                log.setTardeMoney(new BigDecimal(gyPhoneLog.getProdContent()));
                log.setRemark("2");
            }
            //高阳充值手机号
            log.setUserPhone(gyPhoneLog.getMobileNum());
            log.setOrderSn(gyPhoneLog.getOrderNo());
            log.setCreateTime(gyPhoneLog.getCreateTime());
            results.add(log);
        });
        return DatasetBuilder.fromDataList(results, page.createPageInfo());
    }

    /**
     * 油卡管理列表接口
     */
    @Override
    @Read
    public DatasetList<Map<String, Object>> queryOilBatchList(Integer pageNo,
            Integer pageSize, String openTime, String startNum, String endNum,
            String supplier) {
        //组织搜索条件
        OilBatchExample oilBatchEx = new OilBatchExample();
        if (StringUtils.isNotEmpty(startNum)
                && StringUtils.isNotEmpty(endNum)) {
            if (startNum.length() != 9) {
                throw new BusinessException("序号位数只能是9位");
            }
            if (endNum.length() != 9) {
                throw new BusinessException("序号位数只能是9位");
            }
            if (StringUtils.isNotEmpty(openTime)) {
                if (StringUtils.isNotEmpty(supplier)) {
                    oilBatchEx.or().andOpenTimeEqualTo(openTime)
                            .andSupplierEqualTo(Integer.valueOf(supplier))
                            .andStartNumberBetween(startNum, endNum);
                    oilBatchEx.or().andOpenTimeEqualTo(openTime)
                            .andSupplierEqualTo(Integer.valueOf(supplier))
                            .andEndNumberBetween(startNum, endNum)
                            .andEndNumberNotEqualTo(startNum);
                } else {
                    oilBatchEx.or().andOpenTimeEqualTo(openTime)
                            .andStartNumberBetween(startNum, endNum);
                    oilBatchEx.or().andOpenTimeEqualTo(openTime)
                            .andEndNumberBetween(startNum, endNum)
                            .andEndNumberNotEqualTo(startNum);
                }
            } else {
                if (StringUtils.isNotEmpty(supplier)) {
                    oilBatchEx.or()
                            .andSupplierEqualTo(Integer.valueOf(supplier))
                            .andStartNumberBetween(startNum, endNum);
                    oilBatchEx.or()
                            .andSupplierEqualTo(Integer.valueOf(supplier))
                            .andEndNumberBetween(startNum, endNum)
                            .andEndNumberNotEqualTo(startNum);
                } else {
                    oilBatchEx.or().andStartNumberBetween(startNum, endNum);
                    oilBatchEx.or().andEndNumberBetween(startNum, endNum)
                            .andEndNumberNotEqualTo(startNum);
                }

            }
        } else if (StringUtils.isEmpty(startNum)
                && StringUtils.isEmpty(endNum)) {
            if (StringUtils.isNotEmpty(openTime)) {
                if (StringUtils.isNotEmpty(supplier)) {
                    oilBatchEx.or()
                            .andSupplierEqualTo(Integer.valueOf(supplier))
                            .andOpenTimeEqualTo(openTime);
                } else {
                    oilBatchEx.or().andOpenTimeEqualTo(openTime);
                }
            } else {
                if (StringUtils.isNotEmpty(supplier)) {
                    oilBatchEx.or()
                            .andSupplierEqualTo(Integer.valueOf(supplier));
                }
            }
        } else {
            throw new BusinessException("若起始序号和结束序号，一个不能空，另一个一定不为空");
        }
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilBatchMapper.countByExample(oilBatchEx));
        oilBatchEx.setLimitStart(page.getStartRow());
        oilBatchEx.setLimitEnd(page.getSize());

        //按创建时间倒序排列
        oilBatchEx.setOrderByClause(OilBatchExample.CREATE_TIME_DESC);

        List<OilBatch> oilBatchs = oilBatchMapper.selectByExample(oilBatchEx);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        oilBatchs.stream().forEach(oilBatch -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", oilBatch.getId());
            result.put("city", oilBatch.getCity());
            
            
            /*if(Objects.equals("其他",oilBatch.getCity())){
            	result.put("city", "广东-其他");
            }else if(Objects.equals("赠送",oilBatch.getCity())){
            	result.put("city", "广东-赠送");
            }else{
            	result.put("city", oilBatch.getCity());
            }*/
            	
                
            result.put("count", oilBatch.getCount());
            result.put("createTime", oilBatch.getCreateTime());
            result.put("endNumber", oilBatch.getEndNumber());
            result.put("startNumber", oilBatch.getStartNumber());
            result.put("supplier", oilBatch.getSupplier());
            result.put("buyer", oilBatch.getBuyer());
            result.put("money", oilBatch.getMoney());
            result.put("openTime", oilBatch.getOpenTime());
            result.put("remark", oilBatch.getRemark());
            result.put("type", oilBatch.getType());
            Integer batchNumber = oilBatch.getBatchNumber();
            String batchStr = String.valueOf(batchNumber);
            if (batchStr.length() == 1) {
                batchStr = "00" + batchStr;
            } else if (batchStr.length() == 2) {
                batchStr = "0" + batchStr;
            } else if (batchStr.length() == 3) {

            } else {
                throw new BusinessException("批次号转化失败,请联系技术");
            }
            result.put("batchNumber", batchStr);
            resultList.add(result);
        });
        //返回结果
        return DatasetBuilder.fromDataList(resultList, page.createPageInfo());
    }

    /**
     * 油卡明细表
     */
    @Override
    @Read
    public DatasetList<Map<String, Object>> queryOilDetailList(Integer pageNo,
            Integer pageSize, String startNum, String endNum, String startCode,
            String endCode) {
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = oilRechargeCodeEx
                .createCriteria();

        //参数校验
        if (StringUtils.isEmpty(startNum)) {
            throw new BusinessException("开始序号不能为空~");
        }
        if (StringUtils.isEmpty(endNum)) {
            throw new BusinessException("结束序号不能为空~");
        }
        if (startNum.length() != 9) {
            throw new BusinessException("序号位数只能是9位");
        }
        if (endNum.length() != 9) {
            throw new BusinessException("序号位数只能是9位");
        }

        //组织参数
        c.andPhoneBetween(startNum, endNum);
        if (StringUtils.isNotEmpty(startCode)) {
            c.andRechargeCodeGreaterThanOrEqualTo(startCode);
        }
        if (StringUtils.isNotEmpty(endCode)) {
            c.andRechargeCodeLessThanOrEqualTo(endCode);
        }

        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilRechargeCodeMapper.countByExample(oilRechargeCodeEx));
        oilRechargeCodeEx.setLimitStart(page.getStartRow());
        oilRechargeCodeEx.setLimitEnd(page.getSize());
        oilRechargeCodeEx
                .setOrderByClause(OilRechargeCodeExample.CREATE_TIME_DESC);

        //查询结果
        List<OilRechargeCode> oilRechargeCodes = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);
        ArrayList<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        oilRechargeCodes.forEach(codeDb -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", codeDb.getId());
            //序号
            result.put("phone", codeDb.getPhone());
            //面额
            result.put("money", codeDb.getMoney());
            //开通城市
            result.put("address", codeDb.getAddress());
            //油卡状态
            if (codeDb.getStatus() == 1
                    && Objects.isNull(codeDb.getRechargeTime())) {
                result.put("status", 9);
            } else {
                result.put("status", codeDb.getStatus());
            }
            ;
            //开通时间
            result.put("openTime", codeDb.getOpenTime());
            //卡密
            result.put("rechargeCode", codeDb.getRechargeCode());
            results.add(result);
        });
        return DatasetBuilder.fromDataList(results, page.createPageInfo());
    }

    /**
     * 油卡核销列表接口
     */
    @Override
    @Read
    public DatasetList<Map<String, Object>> queryOilCheckList(Integer pageNo,
            Integer pageSize, String startNum, String endNum, String num,
            String address, String date, String status, String batchDate,
            Integer batchNum) {
        //返回最终结果结果
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

        //条件查询
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = oilRechargeCodeEx
                .createCriteria();

        //日期准备
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH) + 1);
        now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
        now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
        now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
        now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        if (StringUtils.isEmpty(batchDate)) {
            //开始序号
            if (StringUtils.isNotEmpty(startNum)) {
                if (startNum.length() != 9) {
                    throw new BusinessException("序号位数只能是9位");
                }
                c.andPhoneGreaterThanOrEqualTo(startNum);
            }
            //结束序号
            if (StringUtils.isNotEmpty(endNum)) {
                if (endNum.length() != 9) {
                    throw new BusinessException("序号位数只能是9位");
                }
                c.andPhoneLessThanOrEqualTo(endNum);
            }
            //序号
            if (StringUtils.isNotEmpty(num)) {
                if (num.length() != 9) {
                    throw new BusinessException("序号位数只能是9位");
                }
                c.andPhoneEqualTo(num);
            }
            
//            //开通省份
//            if (StringUtils.isNotEmpty(province)) {
//            	c.andProvinceEqualTo(province);
//            }
            
            //开通城市
            
            
            if (StringUtils.isNotEmpty(address)) {
            	if(Objects.equals("广东-其他", address)){
            		c.andAddressIn(GDQT_LIST);
            	}else if(Objects.equals("广东-赠送", address)){
            		c.andAddressIn(GDZS_LIST);
            	}else{
            		c.andAddressEqualTo(address);
            	}   
            }
            Date startTime = null;
            Date endTime = null;
            if (StringUtils.isNotEmpty(date)) {
                String[] str = date.split("-");
                Integer year = Integer.valueOf(str[0]);
                Integer month = Integer.valueOf(str[1]);
                now.set(Calendar.YEAR, year);
                now.set(Calendar.MONTH, month - 1);
                startTime = now.getTime();
                now.set(Calendar.MONTH, month);
                endTime = now.getTime();
            }
            //状态
            if ("0".equals(status)) {//未使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_0);
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andOpenTimeGreaterThanOrEqualTo(startTime);
                    c.andOpenTimeLessThan(endTime);
                }
            } else if ("1".equals(status)) {//已使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNotNull();
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andRechargeTimeGreaterThanOrEqualTo(startTime);
                    c.andRechargeTimeLessThan(endTime);
                }
            } else if ("2".equals(status)) {//已冻结
                c.andStatusEqualTo(OilRechargeCode.STATUS_2);
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andUpdateTimeGreaterThanOrEqualTo(startTime);
                    c.andUpdateTimeLessThan(endTime);
                }
            } else if ("3".equals(status)) {//已过期
                c.andStatusEqualTo(OilRechargeCode.STATUS_3);
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andUpdateTimeGreaterThanOrEqualTo(startTime);
                    c.andUpdateTimeLessThan(endTime);
                }
            } else if ("9".equals(status)) {//未开通
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNull();
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andCreateTimeGreaterThanOrEqualTo(startTime);
                    c.andCreateTimeLessThan(endTime);
                }
            } else if ("-1".equals(status)) {//全部
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andCreateTimeGreaterThanOrEqualTo(startTime);
                    c.andCreateTimeLessThan(endTime);
                }
            }
            //核销页面显示的统计数据
            map = queryOilTotal(null, null, null);
        } else {
            String[] str = batchDate.split("-");
            Integer year = Integer.valueOf(str[0]);
            Integer month = Integer.valueOf(str[1]);
            now.set(Calendar.YEAR, year);
            now.set(Calendar.MONTH, month - 1);
            Date startTime = now.getTime();
            now.set(Calendar.MONTH, month);
            Date endTime = now.getTime();
            //开通批次
            if (Objects.nonNull(batchNum)) {
                c.andBatchCodeEqualTo(batchNum);
            }

            //开通年月
            c.andOpenTimeGreaterThanOrEqualTo(startTime);
            c.andOpenTimeLessThan(endTime);
            //状态
            if ("0".equals(status)) {//未使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_0);
            } else if ("1".equals(status)) {//已使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNotNull();
            } else if ("2".equals(status)) {//已冻结
                c.andStatusEqualTo(OilRechargeCode.STATUS_2);
            } else if ("3".equals(status)) {//已过期
                c.andStatusEqualTo(OilRechargeCode.STATUS_3);
            } else if ("9".equals(status)) {//未开通
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNull();
            }
            //核销页面显示的统计数据
            map = queryOilTotal(startTime, endTime, batchNum);
        }
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilRechargeCodeMapper.countByExample(oilRechargeCodeEx));
        oilRechargeCodeEx.setLimitStart(page.getStartRow());
        oilRechargeCodeEx.setLimitEnd(page.getSize());
        oilRechargeCodeEx.setOrderByClause(OilRechargeCodeExample.PHONE_ASC);
        List<OilRechargeCode> codeList = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);

        //查找被充值账户
        List<String> codes = codeList.stream()
                .map(OilRechargeCode::getRechargeCode).distinct()
                .collect(Collectors.toList());
        codes.add("0");
        OilTransLogExample oilTransLogEx = new OilTransLogExample();
        oilTransLogEx.createCriteria().andRemarkIn(codes);
        List<OilTransLog> oilTransLogs = oilTransLogMapper
                .selectByExample(oilTransLogEx);
        Map<String, String> codeMap = new HashMap<String, String>();
        oilTransLogs.forEach(oilLog -> {
            codeMap.put(oilLog.getRemark(), oilLog.getUserPhone());
        });
        //组织整理结果
        codeList.stream().forEach(code -> {
            Map<String, Object> result = new HashMap<String, Object>();
            //油卡序号
            result.put("phone", code.getPhone());
            
            
            /**
             * 开通省份加入湖南省，油卡核销页面搜索展示添加开通省份列
             */
           /* //开通省份
            if(!StringUtils.isEmpty(code.getProvince())){
            	result.put("province",code.getProvince());
            }else{
            	result.put("province", null);
            }*/
            
            
            //开通城市
            result.put("address", code.getAddress());
            
            
            /*if(Objects.equals("其他",code.getAddress())){
            	result.put("address", "广东-其他");
            }else if(Objects.equals("赠送",code.getAddress())){
            	result.put("address", "广东-赠送");
            }else{
            	result.put("address", code.getAddress());
            }*/
            		
            
            //油卡金额
            result.put("moeny", code.getMoney());
            //油卡状态
            result.put("status", code.getStatus());
            //账户手机号
            result.put("userPhone", null);
            //显示时间
            if (0 == code.getStatus()) {//未使用显示开通时间
                if (Objects.isNull(code.getOpenTime())) {
                    result.put("date", null);
                } else {
                    result.put("date", code.getOpenTime());
                }

            } else if (1 == code.getStatus()
                    && Objects.isNull(code.getRechargeTime())) {
                result.put("status", 9);//未开通状态
                //未开通显示生效时间
                result.put("date", code.getCreateTime());
            } else if (1 == code.getStatus()
                    && Objects.nonNull(code.getRechargeTime())) {
                //使用状态显示使用时间
                result.put("date", code.getRechargeTime());
                //账户手机号
                result.put("userPhone", codeMap.get(code.getRechargeCode()));
            } else {
                //过期或冻结显示过期或冻结时间
                result.put("date", code.getUpdateTime());
            }
            //备注
            result.put("remark", code.getRemark());
            //批次号
            if (Objects.isNull(code.getBatchCode())) {
                result.put("batchCode", null);
            } else {
                String batchStr = String.valueOf(code.getBatchCode());
                if (batchStr.length() == 1) {
                    batchStr = "00" + batchStr;
                } else if (batchStr.length() == 2) {
                    batchStr = "0" + batchStr;
                } else if (batchStr.length() == 3) {

                } else {
                    throw new BusinessException("批次号转化失败,请联系技术");
                }
                result.put("batchCode", batchStr);
            }
            //油卡id
            result.put("id", code.getId());
            //供应方
            result.put("supplier", code.getSupplier());
            //购买方
            result.put("buyer", code.getBuyer());
            results.add(result);
        });
        DatasetList<Map<String, Object>> fromDataList = DatasetBuilder
                .fromDataList(results, page.createPageInfo());
        fromDataList.putDataset("total", DatasetBuilder.fromData(map));
        return fromDataList;
    }

    private Map<String, BigDecimal> queryOilTotal(Date startTime, Date endTime,
            Integer batchNum) {
        //核销页面统计数据
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        BigDecimal bigZero = new BigDecimal("0");
        map.put("0", bigZero);//未使用
        map.put("1", bigZero);//已使用
        map.put("2", bigZero);//已冻结
        map.put("3", bigZero);//已过期
        map.put("9", bigZero);//未开通
        map.put("-1", bigZero);//全部
        map.put("money", bigZero);//已使用金额
        //组织统计数据
        List<Map<String, Object>> oilCount = definedMapper
                .queryOilCount(startTime, endTime, batchNum);
        BigDecimal tatal = new BigDecimal("0");
        for (Map<String, Object> oil : oilCount) {
            Integer sta = (Integer) oil.get("status");
            BigDecimal cou = new BigDecimal((Long) oil.get("count"));
            if (sta == 0) {
                map.put("0", cou);//未使用的张数
            } else if (sta == 1) {
                map.put("1", cou);//已使用和未开通的总张数
                BigDecimal total = (BigDecimal) oil.get("totalMoney");
                map.put("money", total);//已使用和未开通的总金额
            } else if (sta == 2) {
                map.put("2", cou);//已冻结的张数
            } else {
                map.put("3", cou);//已过期的张数
            }
            tatal = tatal.add(cou);//总张数
        }
        map.put("-1", tatal);
        //查找未开通的张数和总金额
        List<Map<String, Object>> oilCountTwo = definedMapper
                .queryOilCountTwo(startTime, endTime, batchNum);
        if (!oilCountTwo.isEmpty()) {
            Map<String, Object> map2 = oilCountTwo.get(0);
            BigDecimal cou = new BigDecimal((Long) map2.get("count"));
            BigDecimal total = (BigDecimal) map2.get("totalMoney");//未开通的金额
            if (Objects.nonNull(total)) {
                BigDecimal subtract = map.get("1").subtract(cou);
                map.put("1", subtract);//已使用
                map.put("9", cou);//未开通
                BigDecimal subtract1 = map.get("money").subtract(total);
                map.put("money", subtract1);//已使用的金额
            }
        }
        return map;
    };

    /**
     * 批量冻结和解冻
     */
    @Override
    @Transactional
    @Write
    public DatasetSimple<Integer> freezeOilCard(String startNum, String endNum,
            String status) {
        if (StringUtils.isEmpty(status)) {
            throw new BusinessException("状态不能为空~~");
        }
        if (StringUtils.isEmpty(startNum)) {
            throw new BusinessException("开始序号不能为空");
        }
        if (StringUtils.isEmpty(endNum)) {
            throw new BusinessException("结束序号不能为空");
        }
        if (startNum.length() != 9) {
            throw new BusinessException("开始序号位数只能是9位");
        }
        if (endNum.length() != 9) {
            throw new BusinessException("结束序号位数只能是9位");
        }
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = oilRechargeCodeEx
                .createCriteria();

        //起始序号
        c.andPhoneGreaterThanOrEqualTo(startNum);

        //结束序号
        c.andPhoneLessThanOrEqualTo(endNum);

        //油卡状态
        if ("0".equals(status)) {//冻结
            c.andStatusEqualTo(OilRechargeCode.STATUS_0);
        } else if ("1".equals(status)) {
            c.andStatusEqualTo(OilRechargeCode.STATUS_2);
        } else {
            throw new BusinessException("参数不符合格式");
        }

        int count = oilRechargeCodeMapper.countByExample(oilRechargeCodeEx);
        Integer countTwo = Integer.valueOf(endNum) - Integer.valueOf(startNum);
        if ((count - countTwo) != 1) {
            if ("0".equals(status)) {//冻结
                throw new BusinessException("冻结的张数不对,该序号之间存在其他状态的油卡");
            } else if ("1".equals(status)) {//解冻
                throw new BusinessException("解冻的张数不对,该序号之间存在其他状态的油卡");
            }
        }
        if ("0".equals(status)) {
            int countt = definedMapper.updateOilRechargeCodeTwo(startNum,
                    endNum, "2");
            if (count != countt) {
                definedMapper.updateOilRechargeCodeTwo(startNum, endNum, "0");
                throw new BusinessException("更新张数不对,请联系技术");
            }
        } else if ("1".equals(status)) {
            int countd = definedMapper.updateOilRechargeCodeTwo(startNum,
                    endNum, "0");
            if (count != countd) {
                definedMapper.updateOilRechargeCodeTwo(startNum, endNum, "2");
                throw new BusinessException("更新张数不对,请联系技术");
            }
        }
        return DatasetBuilder.fromDataSimple(count);
    }

    /**
     * 单独解冻和冻结
     */
    @Override
    @Transactional
    @Write
    public DatasetSimple<Integer> freezeOilCardTwo(String num, String status) {
        if (StringUtils.isEmpty(status)) {
            throw new BusinessException("状态不能为空~~");
        }
        if (StringUtils.isEmpty(num)) {
            throw new BusinessException("序号不能为空");
        }
        if (num.length() != 9) {
            throw new BusinessException("序号位数只能是9位");
        }
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = oilRechargeCodeEx
                .createCriteria();

        //序号
        c.andPhoneEqualTo(num);

        //油卡状态
        if ("0".equals(status)) {//冻结
            c.andStatusEqualTo(OilRechargeCode.STATUS_0);
        } else if ("1".equals(status)) {
            c.andStatusEqualTo(OilRechargeCode.STATUS_2);
        } else {
            throw new BusinessException("参数不符合格式");
        }
        List<OilRechargeCode> codes = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);
        if (codes.isEmpty()) {
            if ("0".equals(status)) {//冻结
                throw new BusinessException("不符合冻结要求~");
            } else {//解冻
                throw new BusinessException("不符合解冻要求~");
            }
        }
        if (codes.size() > 1) {
            throw new BusinessException("操作异常,请联系技术~");
        }
        OilRechargeCode code = codes.get(0);
        if ("0".equals(status)) {//冻结
            code.setStatus(OilRechargeCode.STATUS_2);
        } else {//解冻
            code.setStatus(OilRechargeCode.STATUS_0);
        }
        oilRechargeCodeMapper.updateByPrimaryKeySelective(code);
        return DatasetBuilder.fromDataSimple(1);
    }

    /**
     * 油卡核销页面list
     */
    @Override
    @Read
    public DatasetList<Map<String, String>> exportOilCheckList(String startNum,
            String endNum, String num, String address, String date,
            String status, String batchDate, Integer batchNum) {
        //返回最终结果结果
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();

        if (StringUtils.isEmpty(startNum) && StringUtils.isEmpty(endNum)
                && StringUtils.isEmpty(num) && StringUtils.isEmpty(address)
                && StringUtils.isEmpty(date) && StringUtils.isEmpty(batchDate)
                && Objects.isNull(batchNum) && "-1".equals(status)) {
            throw new BusinessException("数据量太多,请选择几个条件");
        }
        //条件查询
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = oilRechargeCodeEx
                .createCriteria();

        //日期准备
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH) + 1);
        now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
        now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
        now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
        now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));

        if (StringUtils.isEmpty(batchDate)) {
            //开始序号
            if (StringUtils.isNotEmpty(startNum)) {
                if (startNum.length() != 9) {
                    throw new BusinessException("序号位数只能是9位");
                }
                c.andPhoneGreaterThanOrEqualTo(startNum);
            }
            //结束序号
            if (StringUtils.isNotEmpty(endNum)) {
                if (endNum.length() != 9) {
                    throw new BusinessException("序号位数只能是9位");
                }
                c.andPhoneLessThanOrEqualTo(endNum);
            }
            //序号
            if (StringUtils.isNotEmpty(num)) {
                if (num.length() != 9) {
                    throw new BusinessException("序号位数只能是9位");
                }
                c.andPhoneEqualTo(num);
            }
            
            //开通城市
            if (StringUtils.isNotEmpty(address)) {
                c.andAddressEqualTo(address);
            }
            Date startTime = null;
            Date endTime = null;
            if (StringUtils.isNotEmpty(date)) {
                String[] str = date.split("-");
                Integer year = Integer.valueOf(str[0]);
                Integer month = Integer.valueOf(str[1]);
                now.set(Calendar.YEAR, year);
                now.set(Calendar.MONTH, month - 1);
                startTime = now.getTime();
                now.set(Calendar.MONTH, month);
                endTime = now.getTime();
            }
            //状态
            if ("0".equals(status)) {//未使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_0);
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andOpenTimeGreaterThanOrEqualTo(startTime);
                    c.andOpenTimeLessThan(endTime);
                }
            } else if ("1".equals(status)) {//已使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNotNull();
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andRechargeTimeGreaterThanOrEqualTo(startTime);
                    c.andRechargeTimeLessThan(endTime);
                }
            } else if ("2".equals(status)) {//已冻结
                c.andStatusEqualTo(OilRechargeCode.STATUS_2);
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andUpdateTimeGreaterThanOrEqualTo(startTime);
                    c.andUpdateTimeLessThan(endTime);
                }
            } else if ("3".equals(status)) {//已过期
                c.andStatusEqualTo(OilRechargeCode.STATUS_3);
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andUpdateTimeGreaterThanOrEqualTo(startTime);
                    c.andUpdateTimeLessThan(endTime);
                }
            } else if ("9".equals(status)) {//未开通
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNull();
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andCreateTimeGreaterThanOrEqualTo(startTime);
                    c.andCreateTimeLessThan(endTime);
                }
            } else if ("-1".equals(status)) {
                if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                    c.andCreateTimeGreaterThanOrEqualTo(startTime);
                    c.andCreateTimeLessThan(endTime);
                }
            }
        } else {
            String[] str = batchDate.split("-");
            Integer year = Integer.valueOf(str[0]);
            Integer month = Integer.valueOf(str[1]);
            now.set(Calendar.YEAR, year);
            now.set(Calendar.MONTH, month - 1);
            Date startTime = now.getTime();
            now.set(Calendar.MONTH, month);
            Date endTime = now.getTime();
            //开通批次
            if (Objects.nonNull(batchNum)) {
                c.andBatchCodeEqualTo(batchNum);
            }
            //开通年月
            c.andOpenTimeGreaterThanOrEqualTo(startTime);
            c.andOpenTimeLessThan(endTime);
            //状态
            if ("0".equals(status)) {//未使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_0);
            } else if ("1".equals(status)) {//已使用
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNotNull();
            } else if ("2".equals(status)) {//已冻结
                c.andStatusEqualTo(OilRechargeCode.STATUS_2);
            } else if ("3".equals(status)) {//已过期
                c.andStatusEqualTo(OilRechargeCode.STATUS_3);
            } else if ("9".equals(status)) {//未开通
                c.andStatusEqualTo(OilRechargeCode.STATUS_1);
                c.andRechargeTimeIsNull();
            }
        }
        oilRechargeCodeEx.setOrderByClause(OilRechargeCodeExample.PHONE_ASC);
        List<OilRechargeCode> codeList = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);

        //查找被充值账户
        List<String> codes = codeList.stream()
                .map(OilRechargeCode::getRechargeCode).distinct()
                .collect(Collectors.toList());
        codes.add("0");
        OilTransLogExample oilTransLogEx = new OilTransLogExample();
        oilTransLogEx.createCriteria().andRemarkIn(codes);
        List<OilTransLog> oilTransLogs = oilTransLogMapper
                .selectByExample(oilTransLogEx);
        Map<String, String> codeMap = new HashMap<String, String>();
        oilTransLogs.forEach(oilLog -> {
            codeMap.put(oilLog.getRemark(), oilLog.getUserPhone());
        });
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        codeList.stream().forEach(code -> {
            Map<String, String> result = new HashMap<String, String>();
            //油卡序号
            result.put("phone", code.getPhone());
            //开通城市
            result.put("address", code.getAddress());
            //油卡金额
            result.put("moeny", String.valueOf(code.getMoney().doubleValue()));

            //账户手机号
            result.put("userPhone", null);
            //显示时间; 油卡状态 ;账户手机号
            if (0 == code.getStatus()) {//未使用显示开通时间
                result.put("status", "未使用");
                if (Objects.isNull(code.getOpenTime())) {
                    result.put("date", null);
                } else {
                    result.put("date", format.format(code.getOpenTime()));
                }

            } else if (1 == code.getStatus()
                    && Objects.isNull(code.getRechargeTime())) {
                result.put("status", "未开通");//未开通状态
                //未开通显示生效时间
                result.put("date", format.format(code.getCreateTime()));
            } else if (1 == code.getStatus()
                    && Objects.nonNull(code.getRechargeTime())) {
                //已使用
                result.put("status", "已使用");
                //使用状态显示使用时间
                result.put("date", format.format(code.getRechargeTime()));
                //账户手机号
                result.put("userPhone", codeMap.get(code.getRechargeCode()));
            } else if (2 == code.getStatus()) {
                //冻结显示冻结时间
                result.put("status", "已冻结");
                result.put("date", format.format(code.getUpdateTime()));
            } else if (3 == code.getStatus()) {
                //过期显示过期时间
                result.put("status", "已过期");
                result.put("date", format.format(code.getUpdateTime()));
            }
            //备注
            result.put("remark", code.getRemark());
            //批次号
            Integer batchCode = code.getBatchCode();
            if (Objects.nonNull(batchCode)) {
                String batchStr = String.valueOf(batchCode);
                if (batchStr.length() == 1) {
                    batchStr = "00" + batchStr;
                } else if (batchStr.length() == 2) {
                    batchStr = "0" + batchStr;
                } else if (batchStr.length() == 3) {

                } else {
                    throw new BusinessException("批次号转化错误,请联系技术");
                }
                result.put("batchCode", batchStr);
            } else {
                result.put("batchCode", null);
            }

            results.add(result);
        });
        return DatasetBuilder.fromDataList(results);
    }

    /**
     * 油卡批次表,更新备注
     */
    @Override
    @Write
    @Transactional
    public DatasetSimple<String> updateBatchRemark(String remark, Long id) {
        OilBatch oilBatch = oilBatchMapper.selectByPrimaryKey(id);
        if (Objects.isNull(oilBatch)) {
            throw new BusinessException("没查到该批次记录~");
        }
        oilBatch.setRemark(remark);
        if (oilBatchMapper.updateByPrimaryKeySelective(oilBatch) != 1) {
            throw new BusinessException("更新失败！");
        }
        return DatasetBuilder.fromDataSimple("success");
    }

    /**
     * 油卡批次表的日期和批次号联动
     */
    @Override
    @Read
    public DatasetList<Map<Integer, String>> batchLinkage(String openTime,
            String batchNum) {
        if (StringUtils.isEmpty(openTime)) {
            throw new BusinessException("请先选择开通时间");
        }
        OilBatchExample oilBatchEx = new OilBatchExample();
        com.emate.shop.business.model.OilBatchExample.Criteria c = oilBatchEx
                .createCriteria();
        c.andOpenTimeEqualTo(openTime);

        if (StringUtils.isNotEmpty(batchNum)) {
            Integer batchCount = Integer.valueOf(batchNum);
            c.andBatchNumberEqualTo(batchCount);
        }
        oilBatchEx.setOrderByClause(OilBatchExample.BATCH_NUMBER_ASC);
        List<OilBatch> oilBatch = oilBatchMapper.selectByExample(oilBatchEx);
        List<Map<Integer, String>> resultList = new ArrayList<Map<Integer, String>>();
        oilBatch.stream().forEach(batch -> {
            Map<Integer, String> result = new HashMap<Integer, String>();
            Integer batchNumber = batch.getBatchNumber();
            String batchStr = String.valueOf(batchNumber);
            if (batchStr.length() == 2) {
                batchStr = "0" + batchStr;
            } else if (batchStr.length() == 1) {
                batchStr = "00" + batchStr;
            } else if (batchStr.length() == 3) {

            } else {
                throw new BusinessException("批次号转化错误,请联系技术");
            }
            result.put(batchNumber, batchStr);
            resultList.add(result);
        });
        return DatasetBuilder.fromDataList(resultList);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Read
    public DatasetList<Map<String, String>> exportUserOilExcel(String userPhone,
            String orderNo) {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        //根据手机号查询用户id
        Long userId = 0L;
        if (StringUtils.isNotEmpty(userPhone)) {
            OilUserExample oilUserEx = new OilUserExample();
            oilUserEx.createCriteria().andPhoneEqualTo(userPhone);
            List<OilUser> oilUsers = oilUserMapper.selectByExample(oilUserEx);
            if (oilUsers.isEmpty()) {
                return DatasetBuilder.fromDataList(results);
            }
            userId = oilUsers.get(0).getId();
        }
        //追电油卡话费电子券账户充值
        OilTransLogExample oilTransLogEx = new OilTransLogExample();
        Criteria c = oilTransLogEx.createCriteria();
        if (StringUtils.isNotEmpty(orderNo)) {
            c.andOrderSnEqualTo(orderNo);
        }
        if (!Objects.equals(userId, 0L)) {
            c.andUserIdEqualTo(userId);
        }
        //剔除串码充值
        c.andTransTypeNotEqualTo(OilTransLog.TRANS_TYPE_0);
        List<OilTransLog> oilTransLogs = oilTransLogMapper
                .selectByExample(oilTransLogEx);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OilTransLog oilLog : oilTransLogs) {
            Map<String, String> result = new HashMap<String, String>();
            result.put("supplier", "追电科技");//服务商
            result.put("orderSn", oilLog.getOrderSn());//订单号

            //交易类型：0，串码充值，1，油卡电子券消费，2，中石油加油卡充值，3，中石化，
            //4，手机充值，5，流量充值，6，扣减金额失败
            Integer transType = oilLog.getTransType();//订单类型
            if (1 == transType) {
                result.put("orderType", "油卡电子券");
            } else if (2 == transType) {
                result.put("orderType", "中石化油卡");
            } else if (3 == transType) {
                result.put("orderType", "中石油油卡");
            } else if (4 == transType) {
                result.put("orderType", "手机充值");
            } else if (5 == transType) {
                result.put("orderType", "流量充值");
            } else {
                result.put("orderType", "未知状态");
            }
            if (StringUtils.isEmpty(userPhone)) {//账户手机号
                result.put("userPhone", String.valueOf(oilLog.getUserId()));
            } else {
                result.put("userPhone", userPhone);
            }
            result.put("cardNo", null);

            result.put("money",
                    String.valueOf(oilLog.getTardeMoney().doubleValue()));
            if (Objects.isNull(oilLog.getUserMoney())) {
                result.put("surplusMoney", null);
            } else {
                result.put("surplusMoney",
                        String.valueOf(oilLog.getUserMoney().doubleValue()));
            }

            result.put("status", null);
            result.put("createTime", format.format(oilLog.getCreateTime()));
            if (0 != oilLog.getTransType() && 5 != oilLog.getTransType()
                    && 6 != oilLog.getTransType()) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(oilLog.getRemark(),
                        Map.class);
                Set<Entry<String, Object>> entry = map.entrySet();
                switch (oilLog.getTransType()) {
                    case 1://电子券
                        for (Entry<String, Object> en : entry) {
                            if ("subList".equals(en.getKey())) {
                                List<Map<String, Object>> value = (List<Map<String, Object>>) en
                                        .getValue();
                                Map<String, Object> detail = value.get(0);
                                result.put("status",
                                        String.valueOf(detail.get("respMsg")));
                                result.put("cardNo",
                                        String.valueOf(detail.get("transNo")));
                            }
                        }
                        break;
                    default:
                        for (Entry<String, Object> en : entry) {
                            if ("respMsg".equals(en.getKey())) {
                                result.put("status",
                                        String.valueOf(en.getValue()));
                            } else if ("cardNo".equals(en.getKey())) {
                                result.put("cardNo",
                                        String.valueOf(en.getValue()));
                            }
                        }
                        break;
                }
            }
            results.add(result);
        }
        //油卡
        GyOilLogExample gyOilLogEx = new GyOilLogExample();
        com.emate.shop.business.model.GyOilLogExample.Criteria criteria = gyOilLogEx
                .createCriteria();

        if (StringUtils.isNotEmpty(orderNo)) {
            criteria.andOrderidEqualTo(orderNo);
        }
        if (!Objects.equals(userId, 0L)) {
            criteria.andUserIdEqualTo(userId);
        }
        List<GyOilLog> gyOilLogs = gyOilLogMapper.selectByExample(gyOilLogEx);
        if (!gyOilLogs.isEmpty()) {
            for (GyOilLog gyOilLog : gyOilLogs) {
                Map<String, String> result = new HashMap<String, String>();
                if (new Integer("0").equals(gyOilLog.getSupplier())) {
                    result.put("supplier", "欧飞油卡");//0:追电1 高阳2欧飞
                } else {
                    result.put("supplier", "高阳油卡");//0:追电1 高阳2欧飞
                }
                result.put("orderSn", gyOilLog.getOrderid());//订单号

                //充值类型：1，中石化；2，中石油
                if (Objects.equals(GyOilLog.CHARGETYPE_1,
                        gyOilLog.getChargetype())) {
                    result.put("orderType", "中石化油卡");
                } else if (Objects.equals(GyOilLog.CHARGETYPE_2,
                        gyOilLog.getChargetype())) {
                    result.put("orderType", "中石油油卡");
                }
                if (StringUtils.isEmpty(userPhone)) {
                    result.put("userPhone",
                            String.valueOf(gyOilLog.getUserId()));//账户手机号
                } else {
                    result.put("userPhone", userPhone);//账户手机号
                }

                result.put("cardNo", gyOilLog.getGascardid());//充值卡号

                result.put("money", String.valueOf(gyOilLog.getFillmoney()));//充值金额
                if (Objects.isNull(gyOilLog.getSurplusMoney())) {//订单完成后的账户余额
                    result.put("surplusMoney", null);
                } else {
                    result.put("surplusMoney", String
                            .valueOf(gyOilLog.getSurplusMoney().doubleValue()));
                }

                //订单状态：0，成功，1，失败，2，部分成功
                if (new Byte("0").equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "充值成功");
                } else if (new Byte("1").equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "充值失败");
                } else {
                    result.put("status", "订单处理中");
                }
                //创建时间
                result.put("createTime",
                        format.format(gyOilLog.getCreateTime()));

                results.add(result);
            }
        }
        //话费
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        com.emate.shop.business.model.GyPhoneLogExample.Criteria cri = gyPhoneLogEx
                .createCriteria();
        if (StringUtils.isNotEmpty(orderNo)) {
            cri.andOrderNoEqualTo(orderNo);
        }
        if (!Objects.equals(userId, 0L)) {
            cri.andUserIdEqualTo(userId);
        }
        List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                .selectByExample(gyPhoneLogEx);
        if (!gyPhoneLogs.isEmpty()) {
            for (GyPhoneLog gyPhoneLog : gyPhoneLogs) {
                Map<String, String> result = new HashMap<String, String>();
                if (new Integer("0").equals(gyPhoneLog.getSupplier())) {//服务商
                    result.put("supplier", "欧飞油卡");//0:追电1 高阳2欧飞
                } else {
                    result.put("supplier", "高阳油卡");//0:追电1 高阳2欧飞
                }
                result.put("orderSn", gyPhoneLog.getOrderNo());//订单号

                result.put("orderType", "手机充值");//订单类型

                if (StringUtils.isEmpty(userPhone)) {
                    result.put("userPhone",
                            String.valueOf(gyPhoneLog.getUserId()));
                } else {
                    result.put("userPhone", userPhone);
                }
                result.put("cardNo", gyPhoneLog.getMobileNum());//充值账号

                result.put("money", gyPhoneLog.getProdContent());//充值面额

                //订单完成后账户余额
                if (Objects.isNull(gyPhoneLog.getSurplusMoney())) {
                    result.put("surplusMoney", null);
                } else {
                    result.put("surplusMoney", String.valueOf(
                            gyPhoneLog.getSurplusMoney().doubleValue()));
                }

                //订单状态
                if (Objects.equals(GyPhoneLog.STATUS_0,
                        gyPhoneLog.getStatus())) {
                    result.put("status", "下单成功");
                } else if (Objects.equals(GyPhoneLog.STATUS_1,
                        gyPhoneLog.getStatus())) {
                    result.put("status", "下单失败扣款未知");
                } else if (Objects.equals(GyPhoneLog.STATUS_2,
                        gyPhoneLog.getStatus())) {
                    result.put("status", "下单失败未扣款");
                } else if (Objects.equals(GyPhoneLog.STATUS_3,
                        gyPhoneLog.getStatus())) {
                    result.put("status", "充值成功");
                } else if (Objects.equals(GyPhoneLog.STATUS_4,
                        gyPhoneLog.getStatus())) {
                    result.put("status", "充值失败");
                } else if (Objects.equals(GyPhoneLog.STATUS_5,
                        gyPhoneLog.getStatus())) {
                    result.put("status", "账户扣款失败");
                }

                //创建时间
                result.put("createTime",
                        format.format(gyPhoneLog.getCreateTime()));
                results.add(result);
            }
        }

        if (StringUtils.isEmpty(userPhone)) {
            List<Long> userIds = new ArrayList<Long>();
            userIds.add(0L);
            for (Map<String, String> map : results) {
                Long uId = Long.valueOf(map.get("userPhone"));
                userIds.add(uId);
            }
            OilUserExample userEx = new OilUserExample();
            userEx.createCriteria().andIdIn(userIds);
            List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
            Map<Long, OilUser> userMap = new HashMap<Long, OilUser>();
            oilUsers.stream().forEach(user -> {
                userMap.put(user.getId(), user);
            });
            //赋值账户手机号和账户余额
            for (Map<String, String> map : results) {
                OilUser user = userMap.get(map.get("userPhone"));
                if (Objects.isNull(user)) {
                    map.put("userPhone", null);
                } else {
                    map.put("userPhone", user.getPhone());
                }
            }
        }
        return DatasetBuilder.fromDataList(results);
    }

    private static List<String> getPhoneList() {
        List<String> phoneList = new ArrayList<String>();
        //13477074830  13687259221  15997746922
        ///13640854620,13640855067,13829751360
        phoneList.add("13477074830");
        phoneList.add("13687259221");
        phoneList.add("15997746922");
        phoneList.add("13640854620");
        phoneList.add("13640855067");
        phoneList.add("13829751360");
        //phoneList.add("13521322513");//我的测试账号
        phoneList.add("17777809752");
        //        phoneList.add("15110141794");//尹晶的
        return phoneList;
    }

    /**
     * 查询每个账户每日消费次数
     * @param userId
     * @param phone
     * @param cardNo
     * @return
     */
    public Integer queryMaxCount(Long userId, String phone, String cardNo) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //创建时间的起始
        Date start = calendar.getTime();
        if (StringUtils.isNotEmpty(phone)) {
            GyPhoneLogExample gyPhoneLogExample = new GyPhoneLogExample();
            List<Integer> status = new ArrayList<Integer>();
            status.add(GyPhoneLog.STATUS_1);
            status.add(GyPhoneLog.STATUS_2);
            status.add(GyPhoneLog.STATUS_4);
            gyPhoneLogExample.createCriteria().andUserIdEqualTo(userId)
                    .andStatusNotIn(status).andMobileNumEqualTo(phone)
                    .andCreateTimeGreaterThanOrEqualTo(start);
            List<GyPhoneLog> gyPhoneLogList = gyPhoneLogMapper
                    .selectByExample(gyPhoneLogExample);
            if (gyPhoneLogList.isEmpty()) {
                return 0;
            }
            return gyPhoneLogList.size();
        }
        if (StringUtils.isNotEmpty(cardNo)) {
            GyOilLogExample gyOilLogExample = new GyOilLogExample();
            gyOilLogExample.createCriteria().andUserIdEqualTo(userId)
                    .andOrderstatusNotEqualTo(GyOilLog.ORDERSTATUS_1)
                    .andGascardidEqualTo(cardNo)
                    .andCreateTimeGreaterThanOrEqualTo(start);
            List<GyOilLog> gyOilLogList = gyOilLogMapper
                    .selectByExample(gyOilLogExample);
            if (gyOilLogList.isEmpty()) {
                return 0;
            }
            return gyOilLogList.size();
        }
        return null;
    }

    /**
     * 查询当日追电账户总消费金额
     * @param userId
     * @return
     */
    private void compareOrderMoney(Long userId) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //创建时间的起始
        Date start = calendar.getTime();
        BigDecimal totalMoney = BigDecimal.ZERO;

        //状态参数
        List<Byte> orderStatusList = new ArrayList<Byte>();
        orderStatusList.add(OilOrders.ORDER_STATUS_1);
        orderStatusList.add(OilOrders.ORDER_STATUS_2);
        orderStatusList.add(OilOrders.ORDER_STATUS_3);
        orderStatusList.add(OilOrders.ORDER_STATUS_0);
        
        //订单类型参数
        List<Byte> chargeTypeList = new ArrayList<Byte>();
        chargeTypeList.add(OilOrders.CHARGE_TYPE_1);
        chargeTypeList.add(OilOrders.CHARGE_TYPE_2);
        chargeTypeList.add(OilOrders.CHARGE_TYPE_3);
        chargeTypeList.add(OilOrders.CHARGE_TYPE_4);
        
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        oilOrdersEx.createCriteria()
        		.andCreateTimeGreaterThanOrEqualTo(start)
                .andOrderStatusIn(orderStatusList)
                .andUserIdEqualTo(userId)
                .andChargeTypeIn(chargeTypeList);
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);
        for (OilOrders oilOrders : oilOrdersList) {
            if (OilOrders.ORDER_STATUS_0.equals(oilOrders.getOrderStatus())
                    || OilOrders.ORDER_STATUS_1
                            .equals(oilOrders.getOrderStatus())) {
                totalMoney = totalMoney.add(oilOrders.getFillMoney());
            } else if (OilOrders.ORDER_STATUS_2
                    .equals(oilOrders.getOrderStatus())
                    || OilOrders.ORDER_STATUS_3
                            .equals(oilOrders.getOrderStatus())) {
                totalMoney = totalMoney.add(oilOrders.getFinishMoney());
            }
        }
        //查询白名单
        OilRecordwhiteListExample example = new OilRecordwhiteListExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<OilRecordwhiteList> oilRecordWhite = oilRecordwhiteListMapper
                .selectByExample(example);
        if (!oilRecordWhite.isEmpty()) {
            BigDecimal openQuota = oilRecordWhite.get(0).getOpenQuota();
            //999代表无限额
            if (openQuota.compareTo(new BigDecimal("999")) != 0
                    && totalMoney.compareTo(openQuota
                            .multiply(new BigDecimal("10000"))) != -1) {
                throw new BusinessException("您的充值金额大于开通金额！");
            }
        } else {
            if (totalMoney.intValue() >= 10000) {
                throw new BusinessException("您今天的消费已超出一万,欢迎明天再来~");
            }
        }
    }
    /**
     * 调用高阳油卡接口
     * @param userId
     * @param money
     * @param oilCardNo
     * @param mobile
     * @param ip
     * @param oilType
     * @param userMoney
     * @param orderId
     * @return
     * @throws Exception
     */
    /*private DatasetSimple<String> gyOilCard(Long userId,String money, 
            String oilCardNo, String mobile,String ip,
            String oilType,BigDecimal userMoney,String orderId) throws Exception{
        //打印油卡开始调用日志
        Log4jHelper.getLogger().info("高阳油卡充值-------->>>begin.money:"+money +
                " oilCardNo:"+oilCardNo+" mobile:"+mobile+" ip:"+ip+" oilType:"+oilType);
        String gameid = "GAME60560";// 中石油任意充 1~1000元任意正整数面值
        if(GaoYangilCardCommitVo.RECHARGE_TYPE_CNPC.equals(oilType)){
            gameid = "GAME60780";
        }else if(GaoYangilCardCommitVo.RECHARGE_TYPE_SNPC.equals(oilType)){
            gameid = "GAME60560";
        }
        
        GaoYangilCardCommitVo commitVo = new GaoYangilCardCommitVo();
        commitVo.setChargetype(oilType);// 1
        commitVo.setFillnum(1);// 购买数量
        commitVo.setGameid(gameid);
        // commitVo.setGascardid("1000111100013840088");//油卡账号
        commitVo.setGascardid(oilCardNo);// 油卡账号
        //commitVo.setGascardname(mobile);
        commitVo.setGascardtel(mobile);
        commitVo.setOrderid(orderId);// 订单号
        commitVo.setParvalue(money);// 面额
        commitVo.setUserip(ip);
        //开始调用高阳油卡充值接口
        //组织接口参数
        String orderUrl = this.environment.getRequiredProperty("gyxj.oilcard.url");//http://114.247.40.65:18030/game/gameEsalesServlet.do?
        String merchantid = this.environment.getRequiredProperty("gyxj.oilcard.merchantid");// guoshou2
        String key = this.environment.getRequiredProperty("gyxj.oilcard.key");//123456789
        OilCardOrderResult cardOrderResult = null;
        try {
            cardOrderResult = GaoYangOilCardUtil.gasdirectfill(orderUrl, merchantid, key, commitVo);
        } catch (Exception e) {
            throw new BusinessException("订单失败,msg:接口调用失败~~");
        }
        //打印油卡充值结果
        Log4jHelper.getLogger().info(JacksonHelper.toJSON(cardOrderResult));
        if(Objects.isNull(cardOrderResult)){
            throw new BusinessException("订单失败,msg:接口调用失败~~");
        }
        //处理高阳返回结果
        if("0".equals(cardOrderResult.getCode())){//下单成功
            //BeansUtils.overwriteAccessibleProperties(sourceBean, destBean);
            GyOilLog log = new GyOilLog();
            BeanUtils.copyProperties(log, commitVo);
            log.setUserId(userId);
            //账户余额
            log.setSurplusMoney(userMoney);
            log.setJxorderid(cardOrderResult.getJxorderid());
            log.setCreateTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(cardOrderResult.getJxorderdate()));
            log.setUpdateTime(new Date());
            log.setFillmoney(new BigDecimal(Integer.valueOf(money)*commitVo.getFillnum()));//设置 充值请求总面值
            if(gyOilLogMapper.insertSelective(log)!=1){
                throw new BusinessException("新增高阳讯飞油卡订单失败！");
            }
        }else{
            throw new BusinessException("订单失败,msg:"+ GaoYangOilCardUtil.getMsg(cardOrderResult.getCode()));
        }
        return DatasetBuilder.fromDataSimple("ok");
    }*/

    /*private void gyMobileCharge(String money,String proId,String phone,Long userId,
            String orderNo,BigDecimal userMoney) throws Exception{
        
        if(StringUtils.isEmpty(proId)){
            throw new BusinessException("商品id没选!");
        }
    
        String productSearchUrl = this.environment.getRequiredProperty("gyphone.productsearchUrl");
        String agentId = this.environment.getRequiredProperty("gyphone.agentid");
        String merchantKey = this.environment.getRequiredProperty("gyphone.merchantkey");
        String source = this.environment.getRequiredProperty("gyphone.source");
        List<Map<String,String>> products =GaoYangUtil.getProducts(productSearchUrl, agentId, merchantKey, source);
        String prodContent ="0";
        if(products.isEmpty()){
            throw new BusinessException("查询数据库无产品返回");
        }
        GyPhoneProductExample gyPhoneProductEx = new GyPhoneProductExample();
        List<GyPhoneProduct> gyPhoneProducts = gyPhoneProductMapper.selectByExample(gyPhoneProductEx);
        Map<String, GyPhoneProduct> gyPhoneMap = new HashMap<String,GyPhoneProduct>();
        gyPhoneProducts.forEach(e -> {
            gyPhoneMap.put(e.getProdId(), e);
        });
        Iterator<Map<String, String>> iterator = products.iterator();
        while(iterator.hasNext()){
            Map<String, String> next = iterator.next();
            if(Objects.nonNull(gyPhoneMap.get(next.get("prodId")))){
                iterator.remove();
                continue;
            }
            if(proId.equals(next.get("prodId"))){
                Log4jHelper.getLogger().info("商品id："+next.get("prodid")+",商品厂商:"+next.get("prodIsptype")
                        +",商品类型:移动电话"+",商品面额："+next.get("prodContent")
                        +",归属地:"+next.get("prodProvinceid")+",商品实价:"+next.get("prodPrice"));
                prodContent = next.get("prodContent");
                break;
            }
        }
        if("0".equals(prodContent)){
            throw new BusinessException("查询无此面额,请选择充值其他金额");
        }
        if(!money.equals(prodContent)){
            throw new BusinessException("面额id跟面额对不上,请重新充值");
        }
        
        //获取高阳充值url和必要参数
        String orderSendUrl = this.environment.getRequiredProperty("gyphone.ordersendUrl");
        String backurl = this.environment.getRequiredProperty("gyphone.callback.url");
        Map<String, String> result = null;
        try {
            result = GaoYangUtil.directFill(orderSendUrl, agentId, merchantKey, phone, 
                    proId, orderNo, "2", backurl, source, "");
        } catch (Exception e2) {
            throw new BusinessException("接口发送失败,请稍后重试");
        }
        if(Objects.isNull(result)){
            throw new BusinessException("接口发送失败,请稍后重试");
        }
        //成功或处理中扣减账户
        if("0000".equals(result.get("resultno"))||
                "0002".equals(result.get("resultno"))||
                "1006".equals(result.get("resultno"))){//下单成功或下单失败但不知是否扣减金额,需要增加手机充值记录,否则不增加
            GyPhoneLog gyPhoneLog = new GyPhoneLog();
            gyPhoneLog.setUserId(userId);
            gyPhoneLog.setOrderNo(orderNo);
            gyPhoneLog.setCreateTime(new Date());
            gyPhoneLog.setUpdateTime(new Date());
            gyPhoneLog.setSurplusMoney(userMoney);
            gyPhoneLog.setMobileNum(phone);
            gyPhoneLog.setProdContent(prodContent);
            if("0000".equals(result.get("resultno"))){
                gyPhoneLog.setStatus(GyPhoneLog.STATUS_0);//下单成功
            }else{
                gyPhoneLog.setStatus(GyPhoneLog.STATUS_1);//扣款未知,查询订单~
            }
            StringBuffer sb = new StringBuffer();
            Set<Entry<String, String>> entrySet = result.entrySet();
            for(Entry<String, String> entry :entrySet){
                sb.append(entry.getKey()+":"+entry.getValue()+",");
            }
            String remark= sb.toString().substring(0, sb.toString().length()-1);
            gyPhoneLog.setRemark(remark);
            gyPhoneLog.setProdId(proId);
            if(gyPhoneLogMapper.insertSelective(gyPhoneLog)!=1){
                throw new BusinessException("添加高阳手机充值订单失败");
            }
        }else{
            GaoYangUtil.getMessage(result.get("resultno"));
        }
    }*/

    /**
     * admin端开卡页面，查询被开的卡详细信息
     */
    @Override
    @Read
    @Transactional
    public DatasetList<Map<String, Object>> findOilCard(Integer pageNo,
            Integer pageSize, String startCode, String endCode) {
        if (StringUtils.isEmpty(startCode)) {
            throw new BusinessException("请输入起始序号");
        }
        if (StringUtils.isEmpty(endCode)) {
            throw new BusinessException("请输入结束序号");
        }
        if (Long.valueOf(startCode) > Long.valueOf(endCode)) {
            throw new BusinessException("起始序号不能大于结束序号");
        }
        if (startCode.length() != 9 || endCode.length() != 9) {
            throw new BusinessException("序号必须是9位数字");
        }
        if (Integer.valueOf(startCode) > 300000000
                || Integer.valueOf(endCode) > 300000000) {
            String str1 = startCode.substring(0, 3);
            String str2 = endCode.substring(0, 3);
            if (!str1.equals(str2)) {
                throw new BusinessException("起始序号和结束序号必须在同一个月生成的");
            }
        }
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria c = oilRechargeCodeEx
                .createCriteria();
        c.andPhoneGreaterThanOrEqualTo(startCode);
        c.andPhoneLessThanOrEqualTo(endCode);
        //c.andRechargeTimeIsNull();
        //c.andStatusEqualTo(OilRechargeCode.STATUS_1);
        int count = oilRechargeCodeMapper.countByExample(oilRechargeCodeEx);
        BigDecimal num = new BigDecimal(endCode)
                .subtract(new BigDecimal(startCode));
        if (count != (num.intValue() + 1)) {
            throw new BusinessException("存在未处理完的油卡");
        }
        List<OilRechargeCode> oilRechargeCodess = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);
        //判断是否存在多种金额
        Set<String> moneySet = new HashSet<String>();
        String str = "该批油卡存在其他状态的油卡：";
        for (OilRechargeCode oilRechargeCode : oilRechargeCodess) {
            BigDecimal money = oilRechargeCode.getMoney();
            moneySet.add(String.valueOf(money.intValue()));
            Date deadTime = oilRechargeCode.getDeadTime();
            Calendar now = Calendar.getInstance(Locale.CHINA);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            Date time = now.getTime();
            if (Objects.nonNull(deadTime)) {
                if (time.compareTo(deadTime) > 0) {
                    throw new BusinessException("该批油卡已过时效时间！！");
                }
            }
            Date rechargeTime = oilRechargeCode.getRechargeTime();
            Integer status = oilRechargeCode.getStatus();
            if (Objects.nonNull(rechargeTime)
                    && OilRechargeCode.STATUS_1.equals(status)) {
                if (!str.contains("已使用,")) {
                    str = str + "已使用,";
                }

            } else if (OilRechargeCode.STATUS_0.equals(status)) {
                if (!str.contains("未使用,")) {
                    str = str + "未使用,";
                }
            } else if (OilRechargeCode.STATUS_2.equals(status)) {
                if (!str.contains("已冻结,")) {
                    str = str + "已冻结,";
                }

            } else if (OilRechargeCode.STATUS_3.equals(status)) {
                if (!str.contains("已过期,")) {
                    str = str + "已过期,";
                }
            }
        }
        if (moneySet.size() > 1) {
            throw new BusinessException("存在多种金额的油卡，请重新选择！！！");
        }
        if (!"该批油卡存在其他状态的油卡：".equals(str)) {
            str = str.substring(0, str.length() - 1);
            throw new BusinessException(str);
        }
        PaginationUtil page = new PaginationUtil(pageNo, pageSize, count);
        oilRechargeCodeEx
                .setOrderByClause(OilRechargeCodeExample.CREATE_TIME_DESC);
        oilRechargeCodeEx.setLimitStart(page.getStartRow());
        oilRechargeCodeEx.setLimitEnd(page.getSize());
        List<OilRechargeCode> oilRechargeCodes = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

        oilRechargeCodes.stream().forEach(oilRechargeCode -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("phone", oilRechargeCode.getPhone());
            result.put("money", oilRechargeCode.getMoney());
            result.put("deadTime", oilRechargeCode.getDeadTime());
            result.put("createTime", oilRechargeCode.getCreateTime());
            results.add(result);
        });
        DatasetList<Map<String, Object>> fromDataList = DatasetBuilder
                .fromDataList(results, page.createPageInfo());
        DatasetSimple<Integer> fromDataSimple = DatasetBuilder
                .fromDataSimple(num.intValue() + 1);
        fromDataList.putDataset("num", fromDataSimple);
        return fromDataList;
    }

    /**
     * 客户端下单，更新主次账户余额
     * @param oilUser
     * @param money
     * @return
     */
    private String changeUserMoney(OilUser oilUser, String money,
            String orderNo, String orderType) {
        Log4jHelper.getLogger().info("被扣账号：" + oilUser.getPhone() + ",当前余额："
                + oilUser.getMoney().intValue() + ",被扣金额：" + money);
        //被扣金额转化
        BigDecimal orderMoney = new BigDecimal(money);
        //剩余金额
        BigDecimal userMoney = oilUser.getMoney().subtract(orderMoney);
        //判断账户余额是否充足
        if (userMoney.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(
                    "用户余额不足,请先充值。" + "当前余额:" + oilUser.getMoney().intValue());
        }
        //扣减主账户余额
        Date updateTime = oilUser.getUpdateTime();
        oilUser.setMoney(userMoney);
        oilUser.setUpdateTime(new Date());
        OilUserExample oilUserEx = new OilUserExample();
        oilUserEx.or().andUpdateTimeEqualTo(updateTime)
                .andIdEqualTo(oilUser.getId());
        if (oilUserMapper.updateByExampleSelective(oilUser, oilUserEx) != 1) {
            Log4jHelper.getLogger().error("扣减主账户余额失败");
            throw new BusinessException("扣减主账户金额失败，请联系客服");
        }
        //扣减次账户余额
        OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
        oilAccountRechargeEx.createCriteria().andUserIdEqualTo(oilUser.getId());
        int count = oilAccountRechargeMapper
                .countByExample(oilAccountRechargeEx);
        if (count <= 0) {
            //添加账户虚拟油卡信息
            String phone = "a" + oilUser.getPhone();
            OilAccountRecharge oilAccountRecharge = new OilAccountRecharge();
            oilAccountRecharge.setCreateTime(new Date());
            oilAccountRecharge.setUpdateTime(new Date());
            oilAccountRecharge.setMoney(oilUser.getMoney());
            oilAccountRecharge.setCardNo(phone);
            oilAccountRecharge.setUserId(oilUser.getId());
            oilAccountRecharge.setUserPhone(oilUser.getPhone());
            oilAccountRecharge.setSupplier(OilAccountRecharge.SUPPLIER_0);
            //添加订单消费记录
            if (oilAccountRechargeMapper
                    .insertSelective(oilAccountRecharge) != 1) {
                throw new BusinessException("添加次账户失败~");
            }

            //新增订单和油卡信息关联表
            OilcardOrderRelation oilcardOrderRelation = new OilcardOrderRelation();
            oilcardOrderRelation.setUserId(oilUser.getId());//用户id
            oilcardOrderRelation.setCardNo(phone);//卡号
            oilcardOrderRelation.setOrderNo(orderNo);//订单号
            oilcardOrderRelation.setMoney(orderMoney);//交易金额
            oilcardOrderRelation.setCardMoney(oilUser.getMoney());//充值卡剩余金额
            //订单类型
            if ("0".equals(orderType)) {
                oilcardOrderRelation
                        .setOrderType(OilcardOrderRelation.ORDER_TYPE_0);
            } else if ("1".equals(orderType)) {
                oilcardOrderRelation
                        .setOrderType(OilcardOrderRelation.ORDER_TYPE_1);
            } else if ("2".equals(orderType)) {
                oilcardOrderRelation
                        .setOrderType(OilcardOrderRelation.ORDER_TYPE_2);
            } else if ("3".equals(orderType)) {
                oilcardOrderRelation
                        .setOrderType(OilcardOrderRelation.ORDER_TYPE_3);
            } else if ("4".equals(orderType)) {
                oilcardOrderRelation
                        .setOrderType(OilcardOrderRelation.ORDER_TYPE_4);
            } else if ("5".equals(orderType)) {
                oilcardOrderRelation
                        .setOrderType(OilcardOrderRelation.ORDER_TYPE_5);
            } else {
                throw new BusinessException("未知订单类型！！");
            }
            oilcardOrderRelation.setCreateTime(new Date());//创建时间
            oilcardOrderRelation.setUpdateTime(new Date());//更新时间
            if (oilcardOrderRelationMapper
                    .insertSelective(oilcardOrderRelation) != 1) {
                throw new BusinessException("添加订单和油卡关联表记录失败！！");
            }
            return String.valueOf(userMoney.doubleValue());
        } else {
            oilAccountRechargeEx.clear();
            oilAccountRechargeEx.createCriteria()
                    .andUserIdEqualTo(oilUser.getId())
                    .andMoneyGreaterThan(BigDecimal.ZERO);
            //根据创建时间升序
            oilAccountRechargeEx.setOrderByClause(
                    OilAccountRechargeExample.CREATE_TIME_ASC);
            //查询次级账户
            List<OilAccountRecharge> oilAccountList = oilAccountRechargeMapper
                    .selectByExample(oilAccountRechargeEx);
            //返回每个次级账户的金额
            if (oilAccountList.isEmpty()) {
                throw new BusinessException("账户扣款异常，请联系客服");
            }
            for (int i = 0; i < oilAccountList.size(); i++) {
                OilAccountRecharge oilAccount = oilAccountList.get(i);
                //for update
                oilAccount = definedMapper
                        .queryOilAccountbyId(oilAccount.getId());
                BigDecimal oilMoney = oilAccount.getMoney();
                if (orderMoney.compareTo(oilMoney) > 0) {
                    if (i == oilAccountList.size() - 1) {
                        throw new BusinessException("子账户余额不足!!!");
                    }
                    //更新次级账户余额
                    oilAccount.setMoney(BigDecimal.ZERO);
                    oilAccount.setUpdateTime(new Date());
                    if (oilAccountRechargeMapper
                            .updateByPrimaryKeySelective(oilAccount) != 1) {
                        throw new BusinessException("更新次级账户余额失败！！");
                    }
                    //新增订单和油卡信息关联表
                    OilcardOrderRelation oilcardOrderRelation = new OilcardOrderRelation();
                    oilcardOrderRelation.setUserId(oilUser.getId());//用户id
                    oilcardOrderRelation.setCardNo(oilAccount.getCardNo());//卡号
                    oilcardOrderRelation.setOrderNo(orderNo);//订单号
                    oilcardOrderRelation.setMoney(oilMoney);//交易金额
                    oilcardOrderRelation.setCardMoney(BigDecimal.ZERO);//充值卡剩余金额
                    //订单类型
                    if ("0".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_0);
                    } else if ("1".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_1);
                    } else if ("2".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_2);
                    } else if ("3".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_3);
                    } else if ("4".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_4);
                    } else if ("5".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_5);
                    } else {
                        throw new BusinessException("未知订单类型！！");
                    }

                    oilcardOrderRelation.setCreateTime(new Date());//创建时间
                    oilcardOrderRelation.setUpdateTime(new Date());//更新时间
                    if (oilcardOrderRelationMapper
                            .insertSelective(oilcardOrderRelation) != 1) {
                        throw new BusinessException("添加订单和油卡关联表记录失败！！");
                    }
                    orderMoney = orderMoney.subtract(oilMoney);
                    continue;
                } else {
                    oilMoney = oilMoney.subtract(orderMoney);
                    oilAccount.setMoney(oilMoney);
                    oilAccount.setUpdateTime(new Date());
                    if (oilAccountRechargeMapper
                            .updateByPrimaryKeySelective(oilAccount) != 1) {
                        throw new BusinessException("更新次级账户余额失败！！");
                    }
                    //新增订单和油卡信息关联表
                    OilcardOrderRelation oilcardOrderRelation = new OilcardOrderRelation();
                    oilcardOrderRelation.setUserId(oilUser.getId());//用户id
                    oilcardOrderRelation.setCardNo(oilAccount.getCardNo());//卡号
                    oilcardOrderRelation.setOrderNo(orderNo);//订单号
                    oilcardOrderRelation.setMoney(orderMoney);//交易金额
                    oilcardOrderRelation.setCardMoney(oilMoney);//充值卡剩余金额
                    //订单类型
                    if ("0".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_0);
                    } else if ("1".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_1);
                    } else if ("2".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_2);
                    } else if ("3".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_3);
                    } else if ("4".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_4);
                    } else if ("5".equals(orderType)) {
                        oilcardOrderRelation.setOrderType(
                                OilcardOrderRelation.ORDER_TYPE_5);
                    } else {
                        throw new BusinessException("未知订单类型！！");
                    }
                    oilcardOrderRelation.setCreateTime(new Date());//创建时间
                    oilcardOrderRelation.setUpdateTime(new Date());//更新时间
                    if (oilcardOrderRelationMapper
                            .insertSelective(oilcardOrderRelation) != 1) {
                        throw new BusinessException("添加订单和油卡关联表记录失败！！");
                    }
                    break;
                }
            }
        }
        return String.valueOf(userMoney.doubleValue());
    }

    /**
     * 客户端下油卡订单
     */
    @Override
    @Write
    @Transactional
    public DatasetSimple<String> chargeOilCard(Long userId, String oilCardNo,
            String goodId, String money, String ip, String mobile,
            String cardType) {

        //1.0判断充值金额不能为空
        if (StringUtils.isEmpty(money)) {
            Log4jHelper.getLogger().error("油卡充值：充值金额为空");
            throw new BusinessException("充值金额为空");
        }
        //2.0判断油卡卡号不能空
        if (StringUtils.isEmpty(oilCardNo)) {
            Log4jHelper.getLogger().error("油卡充值：油卡卡号为空");
            throw new BusinessException("油卡卡号为空");
        }
        //3.0充值油卡是否符合规则
        if ("0".equals(cardType)) {//中石化油卡卡号规则要求
            if (oilCardNo.length() != 19 || !oilCardNo.startsWith("100011")) {
                Log4jHelper.getLogger().error("油卡充值：中国石化加油卡是“100011”开头的19位卡号");
                throw new BusinessException("中国石化加油卡是“100011”开头的19位卡号");
            }
            //中石化判断油卡所属地区是否维护中
            checkOilCardNo(goodId,oilCardNo);
        } else {//中石油油卡卡号规则要求
            if (oilCardNo.startsWith("91")) {//车队卡判断
                Log4jHelper.getLogger().error("油卡充值：该中石油的油卡是车队卡,暂不支持充值");
                throw new BusinessException("该中石油的油卡是车队卡,暂不支持充值");
            }
            if (oilCardNo.length() != 16 || !oilCardNo.startsWith("90")) {
                Log4jHelper.getLogger().error("油卡充值：中国石油加油卡是“90”开头16位卡号");
                throw new BusinessException("中国石油加油卡是“90”开头16位卡号!");
            }
        }

        //4.0 笔数限制
        try {
            queryOilOrdersCount(oilCardNo, "1");
        } catch (Exception e) {
            Log4jHelper.getLogger().error("油卡充值：充值笔数限制失败");
            throw new BusinessException(e.getMessage());
        }

        //5.0 白名单金额限制
        try {
            compareOrderMoney(userId);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("油卡充值：白名单金额限制失败");
            throw new BusinessException(e.getMessage());
        }

        //6.0查看充值油卡面额是否符合规则
        OilGoods oilGoods = null;
        try {
            oilGoods = checkOilGood(goodId, money);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("油卡充值：充值面额检验失败");
            throw new BusinessException(e.getMessage());
        }
        if (Objects.isNull(oilGoods)) {
            Log4jHelper.getLogger().error("油卡充值：商品不存在" + goodId);
            throw new BusinessException("商品不存在，请联系客服");
        }
        String proId = oilGoods.getProductId();

        //油卡用户
        OilUser user = definedMapper.queryOilUserbyId(userId);
        //7.0特殊账号限制油卡充值
        List<String> phoneList = getPhoneList();
        if (phoneList.contains(user.getPhone())) {
            Log4jHelper.getLogger().error("油卡充值：该账号已被拉黑名单");
            throw new BusinessException("该账号已被拉黑名单");
        }
        //8.0分单前，先扣除主次账户余额
        String orderNo = RandomUtil.getOrderSn();
        String userMoney = null;
        try {
            userMoney = changeUserMoney(user, money, orderNo, "0");
        } catch (Exception e) {
            Log4jHelper.getLogger().error("油卡充值：扣减账户余额失败");
            throw new BusinessException(e.getMessage());
        }
        if (StringUtils.isEmpty(userMoney)) {
            Log4jHelper.getLogger().error("油卡充值：扣减账户余额失败");
            throw new BusinessException("修改账户余额失败！！");
        }
        //9.0查询欧飞当前正在使用的账户
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andStatusEqualTo(OilProvider.STATUS_1);
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);
        if (oilProviderList.isEmpty()) {
            Log4jHelper.getLogger().error("油卡充值：未查询到充值油卡所需的充值账户");
            throw new BusinessException("未查询到充值油卡，所需的充值账户，请联系客服修改");
        }
        if (oilProviderList.size() != 1) {
            Log4jHelper.getLogger().error("油卡充值：充值油卡所需的充值账户查询为多个");
            throw new BusinessException("充值油卡，所需的充值账户查询为多个，请联系客服修改");
        }
        OilProvider oilProvider = oilProviderList.get(0);
        //打印油卡开始调用日志
        if ("0".equals(cardType)) {
            Log4jHelper.getLogger()
                    .info("欧飞油卡充值-------->>>begin.goodId:" + goodId
                            + " oilCardNo:" + oilCardNo + " mobile:" + mobile
                            + " ip:" + ip + " oilType:中石化");
        } else {
            Log4jHelper.getLogger()
                    .info("欧飞油卡充值-------->>>begin.goodId:" + goodId
                            + " oilCardNo:" + oilCardNo + " mobile:" + mobile
                            + " ip:" + ip + " oilType:中石油");
        }
        //10.0调用欧飞接口
        OufeiOrder oufeiOrder = null;
        try {
            oufeiOrder = ofOilCard(proId, orderNo, oilCardNo, mobile, cardType,
                    oilProvider);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("油卡充值失败：" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (Objects.isNull(oufeiOrder)) {
            Log4jHelper.getLogger().error("油卡充值：响应结果为空");
            throw new BusinessException("油卡充值接口响应为空");
        }
        ;
        //11.0创建订单信息
        OilOrders oilOrders = new OilOrders();
        oilOrders.setUserId(userId);//用户id
        oilOrders.setOrderNo(orderNo);//父订单号
        if ("0".equals(cardType)) {//中石化
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_1);//油卡类型
        } else {//中石油
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_2);//油卡类型
        }
        oilOrders.setCardPhone(mobile);//持卡人手机号
        oilOrders.setCardNo(oilCardNo);//油卡卡号
        oilOrders.setFillMoney(new BigDecimal(money));//充值金额
        oilOrders.setSupplier(0);//接口提供商
        oilOrders.setSurplusMoney(new BigDecimal(userMoney));//主账户剩余金额
        oilOrders.setUserIp(ip);//用户ip
        oilOrders.setCreateTime(new Date());
        oilOrders.setUpdateTime(new Date());
        oilOrders.setProvider(oilProvider.getId());
        oilOrders.setGoodId(proId);
        if ("1".equals(oufeiOrder.getRetcode())) {
            oilOrders.setProviderOrderNo(oufeiOrder.getOrderid());
            oilOrders.setShopMoney(new BigDecimal(oufeiOrder.getOrdercash()));
            oilOrders.setGoodIntroduce(oufeiOrder.getCardname());
            if ("0".equals(oufeiOrder.getGame_state())) {
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_1);
            } else if ("1".equals(oufeiOrder.getGame_state())) {
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_2);
                oilOrders.setFinishMoney(new BigDecimal(money));
                oilOrders.setFinishTime(new Date());
            } else if ("9".equals(oufeiOrder.getGame_state())) {
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_4);
                oilOrders.setFinishMoney(BigDecimal.ZERO);
                oilOrders.setFinishTime(new Date());
            } else {
                Log4jHelper.getLogger()
                        .error("油卡充值失败：返回结果参数异常" + oufeiOrder.getGame_state());
                throw new BusinessException("返回结果参数异常，请联系客服！！");
            }
        } else {
            oilOrders.setProviderOrderNo(null);
            oilOrders.setShopMoney(null);
            oilOrders.setGoodIntroduce("未知错误，需人工核实");
            oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_1);

        }

        //插入一条父订单记录
        if (oilOrdersMapper.insertSelective(oilOrders) != 1) {
            Log4jHelper.getLogger().error("油卡充值：创建父订单失败");
            throw new BusinessException("创建订单失败");
        }
        //返回结果
        return DatasetBuilder.fromDataSimple(orderNo);
    }

    /**
     * 调用欧飞第三方油卡接口
     * @param gyOilLog
     */
    private OufeiOrder ofOilCard(String goodId, String orderNo, String cardNo,
            String cardPhone, String chargetype, OilProvider oilProvider) {
        //1.0 组织接口所需参数
        String orderUrl = this.environment
                .getRequiredProperty("ofoil.recharge.url");
        String key = this.environment.getRequiredProperty("ofoil.key");
        String version = this.environment.getRequiredProperty("ofoil.version");
        String ret_url = this.environment
                .getRequiredProperty("ofoil.callback.url");
        Map<String, String> params = new HashMap<String, String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderTime = format.format(new Date());
        params.put("userid", oilProvider.getUserName());
        params.put("userpws", oilProvider.getUserPassword());
        params.put("keyStr", key);
        params.put("version", version);//固定值
        params.put("cardnum", "1");//1.任意充需要待充值面值（1的整数倍) 2.卡充充值这里表示数量
        params.put("cardid", goodId);//商品编号以产品部门提供的为准
        params.put("sporder_id", orderNo);//我们的订单编号,商家传给欧飞的唯一编号
        params.put("sporder_time", orderTime);//订单时间 （yyyyMMddHHmmss 如：20070323140214）
        params.put("game_userid", cardNo);//加油卡号（充值账号）中石化：以100011开头共19位、中石油：以90开头共16位
        params.put("gasCardTel", cardPhone);//持卡人手机号码
        params.put("ret_url", ret_url);//订单充值成功后返回的URL地址，可为空 ，具体说明参考接口说明1.3章节
        //2.0组织下单日志参数
        OilLog oilLog = new OilLog();
        oilLog.setCreateTime(new Date());
        oilLog.setUpdateTime(new Date());
        oilLog.setOrderNo(orderNo);
        oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_0);
        if ("1".equals(chargetype)) {//中石油
            params.put("chargeType", "2");//加油卡类型 （1:中石化、2:中石油；默认为1）
            oilLog.setOrderType(OilLog.ORDER_TYPE_1);
        } else {
            params.put("chargeType", "1");//加油卡类型 （1:中石化、2:中石油；默认为1）
            oilLog.setOrderType(OilLog.ORDER_TYPE_0);
        }

        //3.0 调用欧飞油卡充值接口
        OufeiOrder oufeiOrder = null;
        Log4jHelper.getLogger().info("欧飞油卡充值接口开始调用~~~");
        try {
            oufeiOrder = OufeiOilUtil.ChargeCardByOf(orderUrl, params, oilLog);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("欧飞油卡充值接口调用异常~~~");
            throw new BusinessException("油卡充值接口调用异常,请联系客服");
        }
        //4.0 添加下单日志
        oilLogMapper.insertSelective(oilLog);
        //5.0若返回结果为空
        if (Objects.isNull(oufeiOrder)) {
            Log4jHelper.getLogger().error("欧飞油卡充值接口响应为空~~");
            throw new BusinessException("油卡充值接口调用异常,返回值为空！请联系客服");
            //6.0处理返回结果
        } else {
            Log4jHelper.getLogger()
                    .info("欧飞油卡充值接口响应:" + JacksonHelper.toJSON(oufeiOrder));
            //下单成功
            if ("1".equals(oufeiOrder.getRetcode())
                    || "1043".equals(oufeiOrder.getRetcode())
                    || "334".equals(oufeiOrder.getRetcode())
                    || "105".equals(oufeiOrder.getRetcode())
                    || "9999".equals(oufeiOrder.getRetcode())) {
                return oufeiOrder;
                //下单失败
            } else {
                Log4jHelper.getLogger().error("欧飞油卡充值接口调用失败："
                        + OufeiOilUtil.getMsg(oufeiOrder.getRetcode()));
                throw new BusinessException("油卡充值接口调用异常:"
                        + OufeiOilUtil.getMsg(oufeiOrder.getRetcode())
                        + ";请联系客服");
            }
        }
    }

    private void queryOilOrdersCount(String cardNo, String orderType) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        if ("1".equals(orderType)) {
            oilOrdersEx.createCriteria()
                    .andOrderStatusNotEqualTo(OilOrders.ORDER_STATUS_4)
                    .andCardNoEqualTo(cardNo)
                    .andCreateTimeGreaterThanOrEqualTo(startTime);
            int count = oilOrdersMapper.countByExample(oilOrdersEx);
            if (count >= 9) {
                throw new BusinessException(
                        "抱歉，由于您的充值频率过快，为了避免您的油卡被中石化/中石油封停影响您的正常使用，建议您明天再试");
            }
        } else if ("2".equals(orderType)) {
            oilOrdersEx.createCriteria()
                    .andOrderStatusNotEqualTo(OilOrders.ORDER_STATUS_4)
                    .andCardNoEqualTo(cardNo)
                    .andCreateTimeGreaterThanOrEqualTo(startTime)
                    .andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_3);
            int count = oilOrdersMapper.countByExample(oilOrdersEx);
            if (count >= 5) {
                throw new BusinessException(
                        "抱歉，由于您的充值频率过快，为了避免您的被充值手机号被运营商封停影响您的正常使用，建议您明天再试");
            }
        }
    }

    /**
     * 客户端下话费订单：分单
     */
    @Override
    @Write
    @Transactional
    public DatasetSimple<String> chargeMobile(Long userId, String phone,
            String goodId, String money, String userIp) {
        //1.0判断充值金额不能为空
        if (StringUtils.isEmpty(money)) {
            Log4jHelper.getLogger().error("话费充值：充值面额不能为空");
            throw new BusinessException("充值面额不能为空");
        }
        //2.0判断被充值账户不能为空
        if (StringUtils.isEmpty(phone)) {
            Log4jHelper.getLogger().error("话费充值：被充值手机号不能为空!");
            throw new BusinessException("被充值手机号不能为空!");
        }
        //3.0 笔数限制
        try {
            queryOilOrdersCount(phone, "2");
        } catch (Exception e) {
            Log4jHelper.getLogger().error("话费充值：笔数限制异常" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        //4.0 检验面额是否正确
        try {
            checkOilGood(goodId, money);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("话费充值：检验话费面额失败" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        //油卡用户
        OilUser user = definedMapper.queryOilUserbyId(userId);
        //5.0不是特殊账号需要进行每天消费金额
        List<String> phoneList = getPhoneList();
        if (!phoneList.contains(user.getPhone())) {
            try {
                compareOrderMoney(userId);
            } catch (Exception e) {
                Log4jHelper.getLogger().error("话费充值：白名单限制异常" + e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
        //6.0 下单前,扣减用户账户余额
        String orderNo = RandomUtil.getOrderSn();
        String userMoney = null;
        try {
            userMoney = changeUserMoney(user, money, orderNo, "1");
        } catch (Exception e) {
            Log4jHelper.getLogger().error("充值话费扣减账户余额失败" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (StringUtils.isEmpty(userMoney)) {
            Log4jHelper.getLogger().error("充值话费扣减账户余额失败,返回账户余额为空");
            throw new BusinessException("充值话费扣减账户余额失败");
        }
        //7.0查询欧飞当前正在使用的账户
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andStatusEqualTo(OilProvider.STATUS_1);
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);

        if (oilProviderList.size() != 1) {
            Log4jHelper.getLogger().error("查询充值话费所需的充值欧飞账户异常");
            throw new BusinessException("充值话费，所需的充值账户异常，请联系客服修改");
        }
        OilProvider oilProvider = oilProviderList.get(0);
        //8.0 发送欧飞接口
        OufeiOrder oufeiOrder = null;
        try {
            oufeiOrder = ofMobileCharge(money, orderNo, phone, oilProvider);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("话费充值异常：" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        //9.0创建主订单
        OilOrders oilOrders = new OilOrders();
        oilOrders.setUserId(userId);//用户id
        oilOrders.setOrderNo(orderNo);//父订单号
        oilOrders.setChargeType(OilOrders.CHARGE_TYPE_3);//油卡类型
        oilOrders.setCardNo(phone);//被充值手机号
        oilOrders.setFillMoney(new BigDecimal(money));//充值金额
        oilOrders.setSupplier(0);//欧飞
        oilOrders.setSurplusMoney(new BigDecimal(userMoney));//账户剩余金额
        oilOrders.setUserIp(userIp);//用户ip
        oilOrders.setCreateTime(new Date());
        oilOrders.setUpdateTime(new Date());
        oilOrders.setProvider(oilProvider.getId());//接口提供商id
        if ("1".equals(oufeiOrder.getRetcode())) {
            oilOrders.setProviderOrderNo(oufeiOrder.getOrderid());//欧飞订单号
            oilOrders.setShopMoney(new BigDecimal(oufeiOrder.getOrdercash()));//欧飞定价
            oilOrders.setGoodId(oufeiOrder.getCardid());//欧飞产品编号
            oilOrders.setGoodIntroduce(oufeiOrder.getCardname());//欧飞产品介绍
            if ("0".equals(oufeiOrder.getGame_state())) {//订单状态
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_1);//处理中
            } else if ("1".equals(oufeiOrder.getGame_state())) {//充值成功
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_2);
                oilOrders.setFinishMoney(new BigDecimal(money));
                oilOrders.setFinishTime(new Date());
            } else if ("9".equals(oufeiOrder.getGame_state())) {//充值失败
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_4);
                oilOrders.setFinishMoney(BigDecimal.ZERO);
                oilOrders.setFinishTime(new Date());
            } else {//未知订单状态
                Log4jHelper.getLogger()
                        .error("话费充值：返回结果状态参数异常" + oufeiOrder.getGame_state());
                throw new BusinessException("返回结果参数异常，请联系客服！！");
            }
        } else {
            oilOrders.setProviderOrderNo(null);//欧飞订单号
            oilOrders.setShopMoney(null);//欧飞定价
            oilOrders.setGoodId(oufeiOrder.getCardid());//欧飞产品编号
            oilOrders.setGoodIntroduce(oufeiOrder.getCardname());//欧飞产品介绍
            oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_1);//处理中
        }
        if (oilOrdersMapper.insertSelective(oilOrders) != 1) {
            Log4jHelper.getLogger().error("话费充值：添加订单失败");
            throw new BusinessException("添加订单记录失败");
        }
        return DatasetBuilder.fromDataSimple(orderNo);
    }

    /**
     * 调用欧飞第三方话费接口
     * @param gyPhoneLog
     */
    private OufeiOrder ofMobileCharge(String money, String orderNo,
            String phone, OilProvider oilProvider) {
        if (money.contains(".")) {
            money = money.substring(0, money.indexOf("."));
        }
        //1.0获取话费请求url
        String moblieUrl = this.environment
                .getRequiredProperty("ofoil.mobilecharge.url");
        //2.0组织接口请求参数
        Map<String, String> params = new HashMap<String, String>();
        String keyStr = this.environment.getRequiredProperty("ofoil.key");
        String version = this.environment.getRequiredProperty("ofoil.version");
        String ret_url = this.environment
                .getRequiredProperty("ofoil.callback.url");

        params.put("userid", oilProvider.getUserName());//用户名
        params.put("userpws", oilProvider.getUserPassword());//用户密码
        params.put("version", version);//版本号
        params.put("cardid", "140101");//快充
        params.put("cardnum", money);//充值面额
        params.put("sporder_id", orderNo);//Sp商家的订单号(商户传给欧飞的唯一编号)
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("sporder_time", format.format(new Date()));//提交时间
        params.put("game_userid", phone);//被充值手机号码
        params.put("keyStr", keyStr);//签名串
        params.put("ret_url", ret_url);//订单充值有结果回调的URL地址，可为空（不参与MD5验算）
        params.put("version", version);//固定值

        //3.0组织下单日志参数
        OilLog oilLog = new OilLog();
        oilLog.setCreateTime(new Date());
        oilLog.setUpdateTime(new Date());
        oilLog.setOrderType(OilLog.ORDER_TYPE_2);
        oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_0);
        oilLog.setOrderNo(orderNo);
        //4.0调用话费下单接口
        Log4jHelper.getLogger().info("欧飞话费充值接口开始调用~~");
        OufeiOrder oufeiOrder = null;
        try {
            oufeiOrder = OufeiOilUtil.mobileByOf(moblieUrl, params, oilLog);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("欧飞话费充值接口开始调用异常：" + e.getMessage());
            oilLogMapper.insertSelective(oilLog);
            throw new BusinessException("话费充值接口开始调用异常");
        }
        //5.0添加下的那记录
        oilLogMapper.insertSelective(oilLog);
        Log4jHelper.getLogger()
                .info("欧飞话费充值接口响应:" + JacksonHelper.toJSON(oufeiOrder));
        //下单成功
        if ("1".equals(oufeiOrder.getRetcode())
                || "1043".equals(oufeiOrder.getRetcode())
                || "334".equals(oufeiOrder.getRetcode())
                || "105".equals(oufeiOrder.getRetcode())
                || "9999".equals(oufeiOrder.getRetcode())) {
            return oufeiOrder;
            //下单失败
        } else {
            Log4jHelper.getLogger().error("欧飞话费充值接口调用失败："
                    + OufeiOilUtil.getMsg(oufeiOrder.getRetcode()));
            throw new BusinessException("话费充值接口调用异常:"
                    + OufeiOilUtil.getMsg(oufeiOrder.getRetcode()) + ";请联系客服");
        }
    }

    /**
     * 欧飞查询订单结果
     * @param provider
     * @param sporder_id
     * @return
     */
    private String queryOrderStatus(Long provider, String sporder_id) {

        OilProvider oilProvider = oilProviderMapper
                .selectByPrimaryKey(provider);
        if (Objects.isNull(oilProvider)) {
            throw new BusinessException("未查到该接口提供者公司");
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("userid", oilProvider.getUserName());
        params.put("spbillid", sporder_id);
        String orderstatus;
        String ofUrl = this.environment.getProperty("ofoil.orderquery.url");
        try {
            orderstatus = OufeiOilUtil.queryOilOrderByOf(ofUrl, params, null);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("欧飞订单查询接口调用失败");
            e.printStackTrace();
            throw new BusinessException("欧飞订单结果查询失败");
        }
        return orderstatus;
    }

    private void addOilUserMoney(OilUser oilUser, String orderNo,
            String surplusMoney, String orderType) {

        Date updateTime = oilUser.getUpdateTime();

        oilUser.setMoney(new BigDecimal(surplusMoney));

        OilUserExample oilUserEx = new OilUserExample();
        oilUserEx.createCriteria().andUpdateTimeEqualTo(updateTime)
                .andIdEqualTo(oilUser.getId());
        if (oilUserMapper.updateByExampleSelective(oilUser, oilUserEx) != 1) {
            Log4jHelper.getLogger().error("更新主账户金额失败");
            throw new BusinessException("更新主账户金额失败");
        }
        OilcardOrderRelationExample oilcardOrderRelationEx = new OilcardOrderRelationExample();

        //1，中石化，2，中石油，3，手机充值，4，手机流量，
        if ("1".equals(orderType) || "2".equals(orderType)) {
            oilcardOrderRelationEx.createCriteria().andOrderNoEqualTo(orderNo)
                    .andOrderTypeEqualTo(OilcardOrderRelation.ORDER_TYPE_0);
        } else if ("3".equals(orderType)) {
            oilcardOrderRelationEx.createCriteria().andOrderNoEqualTo(orderNo)
                    .andOrderTypeEqualTo(OilcardOrderRelation.ORDER_TYPE_1);
        } else if ("4".equals(orderType)) {
            oilcardOrderRelationEx.createCriteria().andOrderNoEqualTo(orderNo)
                    .andOrderTypeEqualTo(OilcardOrderRelation.ORDER_TYPE_4);
        }
        List<OilcardOrderRelation> oilcardOrderRelationList = oilcardOrderRelationMapper
                .selectByExample(oilcardOrderRelationEx);

        if (oilcardOrderRelationList.isEmpty()) {
            Log4jHelper.getLogger().error("查询子账户和订单关联表信息失败");
            throw new BusinessException("查询子账户和订单关联表信息失败");
        }
        for (OilcardOrderRelation oilcardOrderRelation : oilcardOrderRelationList) {

            //更新子账户和订单关联表信息
            BigDecimal addMoney = oilcardOrderRelation.getMoney();
            oilcardOrderRelation.setMoney(BigDecimal.ZERO);
            oilcardOrderRelation.setUpdateTime(new Date());

            //更新次级账户信息
            OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
            oilAccountRechargeEx.createCriteria()
                    .andCardNoEqualTo(oilcardOrderRelation.getCardNo());
            oilAccountRechargeEx.setLimitStart(0);
            oilAccountRechargeEx.setLimitEnd(1);
            List<OilAccountRecharge> oilAccountRechargeList = oilAccountRechargeMapper
                    .selectByExample(oilAccountRechargeEx);
            if (oilAccountRechargeList.isEmpty()) {
                Log4jHelper.getLogger().error("查询子账户表信息失败");
                throw new BusinessException("查询子账户表信息失败");
            }
            OilAccountRecharge oilAccountRecharge = oilAccountRechargeList
                    .get(0);
            //for update
            oilAccountRecharge = definedMapper
                    .queryOilAccountbyId(oilAccountRecharge.getId());
            BigDecimal accountMoney = oilAccountRecharge.getMoney()
                    .add(addMoney);
            oilAccountRecharge.setMoney(accountMoney);
            oilAccountRecharge.setUpdateTime(new Date());
            //更新关联表
            oilcardOrderRelation.setCardMoney(accountMoney);
            if (oilcardOrderRelationMapper
                    .updateByPrimaryKeySelective(oilcardOrderRelation) != 1) {
                Log4jHelper.getLogger().error("更新子账户和订单关联表信息失败");
                throw new BusinessException("更新子账户和订单关联表信息失败");
            }
            ;
            //更新子账户表
            if (oilAccountRechargeMapper
                    .updateByPrimaryKeySelective(oilAccountRecharge) != 1) {
                Log4jHelper.getLogger().error("更新子账户表信息失败");
                throw new BusinessException("更新子账户表信息失败");
            }
            ;
        }
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryOilcardOrderList(
            Integer pageNo, Integer pageSize, String cardNo) {
        //判断参数
        if (StringUtils.isEmpty(cardNo)) {
            throw new BusinessException("油卡卡号不能为空");
        }
        //组织查询条件
        OilcardOrderRelationExample oilcardOrderRelationEx = new OilcardOrderRelationExample();
        oilcardOrderRelationEx.createCriteria().andCardNoEqualTo(cardNo);
        //分页
        int count = oilcardOrderRelationMapper
                .countByExample(oilcardOrderRelationEx);
        PaginationUtil page = new PaginationUtil(pageNo, pageSize, count);
        oilcardOrderRelationEx.setLimitStart(page.getStartRow());
        oilcardOrderRelationEx.setLimitEnd(page.getSize());
        //查询结果
        List<OilcardOrderRelation> oilcardOrderRelationList = oilcardOrderRelationMapper
                .selectByExample(oilcardOrderRelationEx);

        //组织返回结果
        List<String> oilOrderNoList = new ArrayList<String>();
        List<String> phoneOrderNoList = new ArrayList<String>();
        List<String> washOrderNoList = new ArrayList<String>();
        List<String> driverOrderNoList = new ArrayList<String>();
        oilcardOrderRelationList.stream().forEach(oilcardOrderRelation -> {
            if (OilcardOrderRelation.ORDER_TYPE_0
                    .equals(oilcardOrderRelation.getOrderType())) {
                oilOrderNoList.add(oilcardOrderRelation.getOrderNo());
            } else if (OilcardOrderRelation.ORDER_TYPE_1
                    .equals(oilcardOrderRelation.getOrderType())) {
                phoneOrderNoList.add(oilcardOrderRelation.getOrderNo());
            } else if (OilcardOrderRelation.ORDER_TYPE_2
                    .equals(oilcardOrderRelation.getOrderType())) {
                washOrderNoList.add(oilcardOrderRelation.getOrderNo());
            } else if (OilcardOrderRelation.ORDER_TYPE_3
                    .equals(oilcardOrderRelation.getOrderType())) {
                driverOrderNoList.add(oilcardOrderRelation.getOrderNo());
            }
        });
        Map<String, String> orderMap = new HashMap<String, String>();
        //查询油卡
        if (!oilOrderNoList.isEmpty()) {
            GyOilLogExample gyOilLogEx = new GyOilLogExample();
            gyOilLogEx.createCriteria().andOrderidIn(oilOrderNoList);
            List<GyOilLog> gyOilLogList = gyOilLogMapper
                    .selectByExample(gyOilLogEx);
            for (GyOilLog gyOilLog : gyOilLogList) {
                if (GyOilLog.ORDERSTATUS_0.equals(gyOilLog.getOrderstatus())
                        || GyOilLog.ORDERSTATUS_2
                                .equals(gyOilLog.getOrderstatus())) {
                    orderMap.put(gyOilLog.getOrderid() + "_0", "0");//充值成功
                }
                if (GyOilLog.ORDERSTATUS_1.equals(gyOilLog.getOrderstatus())) {
                    orderMap.put(gyOilLog.getOrderid() + "_0", "1");//充值失败
                }
                if (GyOilLog.ORDERSTATUS_3.equals(gyOilLog.getOrderstatus())
                        || GyOilLog.ORDERSTATUS_4
                                .equals(gyOilLog.getOrderstatus())
                        || Objects.isNull(gyOilLog.getOrderstatus())) {
                    orderMap.put(gyOilLog.getOrderid() + "_0", "2");//处理中
                }
            }

        }

        //查询话费
        if (!phoneOrderNoList.isEmpty()) {
            GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
            gyPhoneLogEx.createCriteria().andOrderNoIn(phoneOrderNoList);
            List<GyPhoneLog> gyPhoneLogList = gyPhoneLogMapper
                    .selectByExample(gyPhoneLogEx);
            for (GyPhoneLog gyPhoneLog : gyPhoneLogList) {
                if (GyPhoneLog.STATUS_3.equals(gyPhoneLog.getStatus())) {
                    orderMap.put(gyPhoneLog.getOrderNo() + "_1", "0");//充值成功
                } else if (GyPhoneLog.STATUS_4.equals(gyPhoneLog.getStatus())
                        || GyPhoneLog.STATUS_2.equals(gyPhoneLog.getStatus())) {
                    orderMap.put(gyPhoneLog.getOrderNo() + "_1", "1");//充值失败
                } else if (GyPhoneLog.STATUS_0.equals(gyPhoneLog.getStatus())
                        || GyPhoneLog.STATUS_1.equals(gyPhoneLog.getStatus())
                        || GyPhoneLog.STATUS_5.equals(gyPhoneLog.getStatus())) {
                    orderMap.put(gyPhoneLog.getOrderNo() + "_1", "2");//处理中
                }
            }
        }
        //查询洗车
        if (!washOrderNoList.isEmpty()) {
            CarWashPayExample carWashPayEx = new CarWashPayExample();
            carWashPayEx.createCriteria().andOrderNoIn(washOrderNoList);
            List<CarWashPay> carWashPayList = carWashPayMapper
                    .selectByExample(carWashPayEx);
            for (CarWashPay carWashPay : carWashPayList) {
                if (CarWashPay.STATUS_2.equals(carWashPay.getStatus())) {
                    orderMap.put(carWashPay.getOrderNo() + "_2", "0");//充值成功
                } else if (CarWashPay.STATUS_3.equals(carWashPay.getStatus())) {
                    orderMap.put(carWashPay.getOrderNo() + "_2", "1");//充值失败
                } else if (CarWashPay.STATUS_0.equals(carWashPay.getStatus())
                        || CarWashPay.STATUS_1.equals(carWashPay.getStatus())) {
                    orderMap.put(carWashPay.getOrderNo() + "_2", "2");//处理中
                }
            }
        }
        //查询代驾
        if (!driverOrderNoList.isEmpty()) {
            DriverOrderShengExample driverOrderShengEx = new DriverOrderShengExample();
            driverOrderShengEx.createCriteria().andOrderNoIn(driverOrderNoList);
            List<DriverOrderSheng> driverOrderShengList = driverOrderShengMapper
                    .selectByExample(driverOrderShengEx);
            for (DriverOrderSheng driverOrderSheng : driverOrderShengList) {
                if (DriverOrderSheng.ORDER_STATUS_3
                        .equals(driverOrderSheng.getOrderStatus())) {
                    orderMap.put(driverOrderSheng.getOrderNo() + "_3", "0");//充值成功
                } else if (DriverOrderSheng.ORDER_STATUS_4
                        .equals(driverOrderSheng.getOrderStatus())) {
                    orderMap.put(driverOrderSheng.getOrderNo() + "_3", "1");//充值失败
                } else {
                    orderMap.put(driverOrderSheng.getOrderNo() + "_3", "2");//处理中
                }
                orderMap.put("orderNo", driverOrderSheng.getOrderNo());
            }

        }
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        for (OilcardOrderRelation oilcardOrderRelation : oilcardOrderRelationList) {
            Map<String, Object> result = new HashMap<String, Object>();
            String orderNo = oilcardOrderRelation.getOrderNo();
            Integer orderType = oilcardOrderRelation.getOrderType();
            result.put("orderNo", orderNo);
            result.put("orderType", orderType);
            result.put("money", oilcardOrderRelation.getMoney());
            String status = orderMap
                    .get(orderNo + "_" + String.valueOf(orderType));
            result.put("status", status);
            result.put("createTime", oilcardOrderRelation.getCreateTime());
            results.add(result);
        }
        return DatasetBuilder.fromDataList(results, page.createPageInfo());
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryNewOilOrder(Integer pageNo,
            Integer pageSize, String userPhone, String orderNo,
            String orderType, String cardNo) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        Long userId = null;
        if (StringUtils.isNotEmpty(userPhone)) {
            OilUserExample oilUserEx = new OilUserExample();
            oilUserEx.createCriteria().andPhoneEqualTo(userPhone);
            oilUserEx.setLimitStart(0);
            oilUserEx.setLimitEnd(1);
            List<OilUser> oilUserList = oilUserMapper
                    .selectByExample(oilUserEx);
            if (oilUserList.isEmpty()) {
                throw new BusinessException("未查到该用户信息");
            }
            userId = oilUserList.get(0).getId();
        }
        com.emate.shop.business.model.OilOrdersExample.Criteria cr = oilOrdersEx
                .createCriteria();
        if (Objects.nonNull(userId)) {
            cr.andUserIdEqualTo(userId);
        } else if (StringUtils.isNotEmpty(userPhone)
                && Objects.isNull(userId)) {
            return DatasetBuilder.fromDataList(results);
        }
        if (StringUtils.isNotEmpty(orderNo)) {
            cr.andOrderNoEqualTo(orderNo);
        }
        //充值类型：1，中石化，2，中石油，3，手机充值，
        //4，手机流量，5，猫眼电影，6，滴滴出行，7，优酷，8，爱奇艺，9，腾讯视频，10，搜狐
        if (StringUtils.isNotEmpty(orderType)) {
            if ("1".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_1);
            } else if ("2".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_2);
            } else if ("3".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_3);
            } else if ("4".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_4);
            } else if ("5".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_5);
            } else if ("6".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_6);
            } else if ("7".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_7);
            } else if ("8".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_8);
            } else if ("9".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_9);
            } else if ("10".equals(orderType)) {
                cr.andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_10);
            }
        }
        if (StringUtils.isNotEmpty(cardNo)) {
            cr.andCardNoEqualTo(cardNo);
        }

        int count = oilOrdersMapper.countByExample(oilOrdersEx);
        PaginationUtil page = new PaginationUtil(pageNo, pageSize, count);
        oilOrdersEx.setLimitStart(page.getStartRow());
        oilOrdersEx.setLimitEnd(page.getSize());
        oilOrdersEx.setOrderByClause(OilOrdersExample.ID_DESC);
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);

        //账户余额和账户手机号
        List<Long> userIds = oilOrdersList.stream().map(OilOrders::getUserId)
                .distinct().collect(Collectors.toList());
        userIds.add(0L);
        OilUserExample oilUserEx = new OilUserExample();
        oilUserEx.createCriteria().andIdIn(userIds);
        List<OilUser> oilUsers = oilUserMapper.selectByExample(oilUserEx);
        Map<Long, String> phoneMap = new HashMap<Long, String>();
        Map<Long, BigDecimal> moneyMap = new HashMap<Long, BigDecimal>();
        oilUsers.stream().forEach(oilUser -> {
            phoneMap.put(oilUser.getId(), oilUser.getPhone());
            moneyMap.put(oilUser.getId(), oilUser.getMoney());
        });
        //接口提供者
        List<Long> providers = oilOrdersList.stream()
                .map(OilOrders::getProvider).distinct()
                .collect(Collectors.toList());
        providers.add(0L);
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andIdIn(providers);
        List<OilProvider> oilProviders = oilProviderMapper
                .selectByExample(oilProviderEx);
        Map<Long, String> providerMap = new HashMap<Long, String>();
        oilProviders.stream().forEach(oilProvider -> {
            providerMap.put(oilProvider.getId(), oilProvider.getCompany());
        });
        //组织返回结果
        for (OilOrders oilOrders : oilOrdersList) {
            Map<String, Object> result = new HashMap<String, Object>();
            //订单类型1，中石化，2，中石油，3，手机充值
            result.put("chargeType", oilOrders.getChargeType());
            //订单号
            result.put("orderNo", oilOrders.getOrderNo());
            //账户手机号
            String usephone = phoneMap.get(oilOrders.getUserId());
            result.put("userPhone", usephone);
            //被充值卡号或手机号
            result.put("cardNo", oilOrders.getCardNo());
            //卡密
            result.put("cardSecret", oilOrders.getCardSecret());
            //充值金额
            result.put("fillMoney", oilOrders.getFillMoney());
            //账户剩余金额
            result.put("surplusMoney", oilOrders.getSurplusMoney());
            //现在账户余额
            BigDecimal userMoney = moneyMap.get(oilOrders.getUserId());
            result.put("userMoney", userMoney);
            //订单状态0，待处理，1，处理中，2，充值成功，3，部分成功，4，充值失败
            result.put("orderStatus", oilOrders.getOrderStatus());
            //创建时间
            result.put("createTime", oilOrders.getCreateTime());
            //是否为特殊订单
            result.put("remark", oilOrders.getRemark());
            //商品id
            result.put("goodId", oilOrders.getGoodId());
            //商品描述
            result.put("goodIntroduce", oilOrders.getGoodIntroduce());
            //接口提供者
            String provider = providerMap.get(oilOrders.getProvider());
            if (StringUtils.isEmpty(provider)) {
                result.put("provider", null);
            } else {
                result.put("provider", provider);
            }
            results.add(result);
        }
        return DatasetBuilder.fromDataList(results, page.createPageInfo());
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryNewChildOilOrder(
            String parentOrderNo, String orderType) {
        if (StringUtils.isEmpty(parentOrderNo)) {
            throw new BusinessException("父订单号为空");
        }
        if (StringUtils.isEmpty(orderType)) {
            throw new BusinessException("订单类型不能为空");
        }
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        if ("3".equals(orderType)) {
            GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
            gyPhoneLogEx.createCriteria()
                    .andParentOrderNoEqualTo(parentOrderNo);
            List<GyPhoneLog> gyPhoneLogList = gyPhoneLogMapper
                    .selectByExample(gyPhoneLogEx);
            if (gyPhoneLogList.isEmpty()) {
                return DatasetBuilder.fromDataList(results);
            }
            List<String> orderNos = gyPhoneLogList.stream()
                    .map(GyPhoneLog::getOrderNo).distinct()
                    .collect(Collectors.toList());
            orderNos.add("0");
            OilcardOrderRelationExample oilcardOrderRelationEx = new OilcardOrderRelationExample();
            oilcardOrderRelationEx.createCriteria().andOrderNoIn(orderNos)
                    .andOrderTypeEqualTo(1);
            List<OilcardOrderRelation> oilcardOrderRelationList = oilcardOrderRelationMapper
                    .selectByExample(oilcardOrderRelationEx);
            List<String> cardNos = oilcardOrderRelationList.stream()
                    .map(OilcardOrderRelation::getCardNo).distinct()
                    .collect(Collectors.toList());
            OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
            oilAccountRechargeEx.createCriteria().andCardNoIn(cardNos);
            List<OilAccountRecharge> oilAccountRechargeList = oilAccountRechargeMapper
                    .selectByExample(oilAccountRechargeEx);
            Map<String, OilAccountRecharge> cardNoMap = new HashMap<String, OilAccountRecharge>();
            oilAccountRechargeList.stream().forEach(oilAccountRecharge -> {
                cardNoMap.put(oilAccountRecharge.getCardNo(),
                        oilAccountRecharge);
            });
            Map<String, List<Map<String, Object>>> cardMap = new HashMap<String, List<Map<String, Object>>>();
            for (OilcardOrderRelation oilcardOrderRelation : oilcardOrderRelationList) {
                Map<String, Object> hashMap2 = new HashMap<String, Object>();
                hashMap2.put("money", oilcardOrderRelation.getMoney());//使用金额
                Integer supplier = cardNoMap
                        .get(oilcardOrderRelation.getCardNo()).getSupplier();
                hashMap2.put("supplier", supplier);//卡片供应商
                BigDecimal cardMoney = cardNoMap
                        .get(oilcardOrderRelation.getCardNo()).getMoney();
                hashMap2.put("cardMoney", cardMoney);//卡内余额

                List<Map<String, Object>> arrayList = null;
                if (Objects.isNull(
                        cardMap.get(oilcardOrderRelation.getOrderNo()))) {
                    arrayList = new ArrayList<Map<String, Object>>();
                } else {
                    arrayList = cardMap.get(oilcardOrderRelation.getOrderNo());
                }
                arrayList.add(hashMap2);
                cardMap.put(oilcardOrderRelation.getOrderNo(), arrayList);
            }
            for (GyPhoneLog gyPhoneLog : gyPhoneLogList) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("orderNo", gyPhoneLog.getOrderNo());
                result.put("provider", gyPhoneLog.getProvider());
                result.put("provider", gyPhoneLog.getProvider());
                result.put("orderMoney", gyPhoneLog.getProdContent());
                if (GyPhoneLog.STATUS_5.equals(gyPhoneLog.getStatus())) {
                    result.put("orderStatus", "0");//待处理
                } else if (GyPhoneLog.STATUS_0.equals(gyPhoneLog.getStatus())
                        || GyPhoneLog.STATUS_1.equals(gyPhoneLog.getStatus())) {
                    result.put("orderStatus", "1");//处理中
                } else if (GyPhoneLog.STATUS_3.equals(gyPhoneLog.getStatus())) {
                    result.put("orderStatus", "2");//充值成功
                } else if (GyPhoneLog.STATUS_2.equals(gyPhoneLog.getStatus())
                        || GyPhoneLog.STATUS_4.equals(gyPhoneLog.getStatus())) {
                    result.put("orderStatus", "3");//充值失败
                }
                //卡的供应商，卡内余额，该笔订单消费卡的金额
                result.put("cardInfo", cardMap.get(gyPhoneLog.getOrderNo()));//卡的供应商
                results.add(result);
            }
        } else {
            GyOilLogExample gyOilLogEx = new GyOilLogExample();
            gyOilLogEx.createCriteria().andParentOrderNoEqualTo(parentOrderNo);
            List<GyOilLog> gyOilLogList = gyOilLogMapper
                    .selectByExample(gyOilLogEx);
            if (gyOilLogList.isEmpty()) {
                return DatasetBuilder.fromDataList(results);
            }
            List<String> orderNos = gyOilLogList.stream()
                    .map(GyOilLog::getOrderid).distinct()
                    .collect(Collectors.toList());
            orderNos.add("0");
            OilcardOrderRelationExample oilcardOrderRelationEx = new OilcardOrderRelationExample();
            oilcardOrderRelationEx.createCriteria().andOrderNoIn(orderNos)
                    .andOrderTypeEqualTo(0);
            List<OilcardOrderRelation> oilcardOrderRelationList = oilcardOrderRelationMapper
                    .selectByExample(oilcardOrderRelationEx);
            List<String> cardNos = oilcardOrderRelationList.stream()
                    .map(OilcardOrderRelation::getCardNo).distinct()
                    .collect(Collectors.toList());
            OilAccountRechargeExample oilAccountRechargeEx = new OilAccountRechargeExample();
            oilAccountRechargeEx.createCriteria().andCardNoIn(cardNos);
            List<OilAccountRecharge> oilAccountRechargeList = oilAccountRechargeMapper
                    .selectByExample(oilAccountRechargeEx);
            Map<String, OilAccountRecharge> cardNoMap = new HashMap<String, OilAccountRecharge>();
            oilAccountRechargeList.stream().forEach(oilAccountRecharge -> {
                cardNoMap.put(oilAccountRecharge.getCardNo(),
                        oilAccountRecharge);
            });
            Map<String, List<Map<String, Object>>> cardMap = new HashMap<String, List<Map<String, Object>>>();
            for (OilcardOrderRelation oilcardOrderRelation : oilcardOrderRelationList) {
                Map<String, Object> hashMap2 = new HashMap<String, Object>();
                hashMap2.put("money", oilcardOrderRelation.getMoney());//使用金额
                Integer supplier = cardNoMap
                        .get(oilcardOrderRelation.getCardNo()).getSupplier();
                hashMap2.put("supplier", supplier);//卡片供应商
                BigDecimal cardMoney = cardNoMap
                        .get(oilcardOrderRelation.getCardNo()).getMoney();
                hashMap2.put("cardMoney", cardMoney);//卡内余额

                List<Map<String, Object>> arrayList = null;
                if (Objects.isNull(
                        cardMap.get(oilcardOrderRelation.getOrderNo()))) {
                    arrayList = new ArrayList<Map<String, Object>>();
                } else {
                    arrayList = cardMap.get(oilcardOrderRelation.getOrderNo());
                }
                arrayList.add(hashMap2);
                cardMap.put(oilcardOrderRelation.getOrderNo(), arrayList);
            }
            for (GyOilLog gyOilLog : gyOilLogList) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("orderNo", gyOilLog.getOrderid());
                result.put("provider", gyOilLog.getProvider());
                result.put("orderMoney", gyOilLog.getFillmoney());
                if (GyOilLog.ORDERSTATUS_3.equals(gyOilLog.getOrderstatus())) {
                    result.put("orderStatus", "0");//待处理
                } else if (GyOilLog.ORDERSTATUS_4
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("orderStatus", "1");//处理中
                } else if (GyOilLog.ORDERSTATUS_2
                        .equals(gyOilLog.getOrderstatus())
                        || GyOilLog.ORDERSTATUS_0
                                .equals(gyOilLog.getOrderstatus())) {
                    result.put("orderStatus", "2");//充值成功
                } else if (GyOilLog.ORDERSTATUS_1
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("orderStatus", "3");//充值失败
                }
                //卡的供应商，卡内余额，该笔订单消费卡的金额
                result.put("cardInfo", cardMap.get(gyOilLog.getOrderid()));//卡的供应商
                results.add(result);
            }
        }
        return DatasetBuilder.fromDataList(results);
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryOilOrders(Long userId,
            String orderNo, String userPhone, String pageNo, String pageSize,
            String chargeType) {

        OilUser oilUser = oilUserMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(oilUser)) {
            throw new BusinessException("查询用户失败~");
        }
        //特殊账号限制油卡充值
        List<String> phoneList = getPhoneList();
        if (phoneList.contains(oilUser.getPhone())) {
            if (StringUtils.isEmpty(pageNo)) {
                pageNo = "1";
            }
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "100";
            }

            if (StringUtils.isNotEmpty(orderNo)) {
                DatasetList<Map<String, Object>> data = h5queryPhoneLogList(
                        userId, orderNo, userPhone, pageNo, pageSize);
                return data;
            } else {
                if (StringUtils.isEmpty(chargeType)
                        || chargeType.equals("200")) {
                    DatasetList<Map<String, Object>> data = h5queryPhoneLogList(
                            userId, orderNo, userPhone, pageNo, pageSize);
                    if ("200".equals(chargeType)) {
                        List<Map<String, Object>> list = data.getList();
                        Map<String, Object> map4 = new HashMap<String, Object>();
                        List<Map<String, Object>> list0 = new ArrayList<Map<String, Object>>();
                        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
                        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
                        List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
                        List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();
                        if (!list.isEmpty()) {
                            list.stream().forEach(map -> {
                                if ("0".equals(map.get("orderStatus"))) {//处理中
                                    Map<String, Object> map0 = new HashMap<String, Object>();
                                    map0.put("orderType", map.get("orderType"));
                                    map0.put("orderNo", map.get("orderNo"));
                                    map0.put("phone", map.get("phone"));
                                    map0.put("createTime",
                                            map.get("createTime"));
                                    map0.put("orderStatus",
                                            map.get("orderStatus"));
                                    map0.put("tardeMoney",
                                            map.get("tardeMoney"));
                                    map0.put("denomination",
                                            map.get("denomination"));
                                    list0.add(map0);
                                } else if ("1".equals(map.get("orderStatus"))) {//充值成功
                                    Map<String, Object> map1 = new HashMap<String, Object>();
                                    map1.put("orderType", map.get("orderType"));
                                    map1.put("orderNo", map.get("orderNo"));
                                    map1.put("phone", map.get("phone"));
                                    map1.put("createTime",
                                            map.get("createTime"));
                                    map1.put("orderStatus",
                                            map.get("orderStatus"));
                                    map1.put("tardeMoney",
                                            map.get("tardeMoney"));
                                    map1.put("denomination",
                                            map.get("denomination"));
                                    list1.add(map1);
                                } else if ("2".equals(map.get("orderStatus"))) {//充值失败
                                    Map<String, Object> map2 = new HashMap<String, Object>();
                                    map2.put("orderType", map.get("orderType"));
                                    map2.put("orderNo", map.get("orderNo"));
                                    map2.put("phone", map.get("phone"));
                                    map2.put("createTime",
                                            map.get("createTime"));
                                    map2.put("orderStatus",
                                            map.get("orderStatus"));
                                    map2.put("tardeMoney",
                                            map.get("tardeMoney"));
                                    map2.put("denomination",
                                            map.get("denomination"));
                                    list2.add(map2);
                                } else {//部分成功
                                    Map<String, Object> map3 = new HashMap<String, Object>();
                                    map3.put("orderType", map.get("orderType"));
                                    map3.put("orderNo", map.get("orderNo"));
                                    map3.put("phone", map.get("phone"));
                                    map3.put("createTime",
                                            map.get("createTime"));
                                    map3.put("orderStatus",
                                            map.get("orderStatus"));
                                    map3.put("tardeMoney",
                                            map.get("tardeMoney"));
                                    map3.put("denomination",
                                            map.get("denomination"));
                                    list3.add(map3);
                                }
                            });
                        }
                        map4.put("list", list);
                        map4.put("list0", list0);
                        map4.put("list1", list1);
                        map4.put("list2", list2);
                        map4.put("list3", list3);
                        list4.add(map4);
                        PaginationUtil page = new PaginationUtil(
                                Integer.valueOf(pageNo),
                                Integer.valueOf(pageSize), list.size());
                        return DatasetBuilder.fromDataList(list4,
                                page.createPageInfo());
                    }
                    return data;
                } else {
                    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
                    PaginationUtil page = new PaginationUtil(1, 100,
                            results.size());
                    return DatasetBuilder.fromDataList(null,
                            page.createPageInfo());
                }
            }
        } else {
            DatasetList<Map<String, Object>> data = h5queryLogList(userId,
                    orderNo, chargeType);
            if ("200".equals(chargeType) || "0".equals(chargeType)) {
                List<Map<String, Object>> list = data.getList();
                Map<String, Object> map4 = new HashMap<String, Object>();
                List<Map<String, Object>> list0 = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();
                if (!list.isEmpty()) {
                    list.stream().forEach(map -> {
                        if ("0".equals(map.get("orderStatus"))) {//处理中
                            Map<String, Object> map0 = new HashMap<String, Object>();
                            map0.put("orderType", map.get("orderType"));
                            map0.put("orderNo", map.get("orderNo"));
                            map0.put("phone", map.get("phone"));
                            map0.put("createTime", map.get("createTime"));
                            map0.put("orderStatus", map.get("orderStatus"));
                            map0.put("tardeMoney", map.get("tardeMoney"));
                            map0.put("denomination", map.get("denomination"));
                            list0.add(map0);
                        } else if ("1".equals(map.get("orderStatus"))) {//充值成功
                            Map<String, Object> map1 = new HashMap<String, Object>();
                            map1.put("orderType", map.get("orderType"));
                            map1.put("orderNo", map.get("orderNo"));
                            map1.put("phone", map.get("phone"));
                            map1.put("createTime", map.get("createTime"));
                            map1.put("orderStatus", map.get("orderStatus"));
                            map1.put("tardeMoney", map.get("tardeMoney"));
                            map1.put("denomination", map.get("denomination"));
                            list1.add(map1);
                        } else if ("2".equals(map.get("orderStatus"))) {//充值失败
                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("orderType", map.get("orderType"));
                            map2.put("orderNo", map.get("orderNo"));
                            map2.put("phone", map.get("phone"));
                            map2.put("createTime", map.get("createTime"));
                            map2.put("orderStatus", map.get("orderStatus"));
                            map2.put("tardeMoney", map.get("tardeMoney"));
                            map2.put("denomination", map.get("denomination"));
                            list2.add(map2);
                        } else {//部分成功
                            Map<String, Object> map3 = new HashMap<String, Object>();
                            map3.put("orderType", map.get("orderType"));
                            map3.put("orderNo", map.get("orderNo"));
                            map3.put("phone", map.get("phone"));
                            map3.put("createTime", map.get("createTime"));
                            map3.put("orderStatus", map.get("orderStatus"));
                            map3.put("tardeMoney", map.get("tardeMoney"));
                            map3.put("denomination", map.get("denomination"));
                            list3.add(map3);
                        }
                    });
                }
                map4.put("list", list);
                map4.put("list0", list0);
                map4.put("list1", list1);
                map4.put("list2", list2);
                map4.put("list3", list3);
                list4.add(map4);
                PaginationUtil page = new PaginationUtil(1, 300, list.size());
                return DatasetBuilder.fromDataList(list4,
                        page.createPageInfo());
            }
            return data;
        }
    }

    private DatasetList<Map<String, Object>> h5queryLogList(Long userId,
            String orderNo, String chargeType) {

        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.set(Calendar.MONTH, 0);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        Date createTime = now.getTime();
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        //查询oilOrders
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        oilOrdersEx.setOrderByClause(OilOrdersExample.CREATE_TIME_DESC);
        if (StringUtils.isNotEmpty(orderNo)) {
            oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                    .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                    .andCreateTimeGreaterThanOrEqualTo(createTime)
                    .andOrderNoEqualTo(orderNo);//订单号
        } else {
            if (Objects.nonNull(chargeType) && StringUtils.isEmpty(orderNo)) {
                List<Byte> list = new ArrayList<Byte>();
                if (chargeType.equals("0")) {
                    list.removeAll(list);
                    byte a = 1;
                    byte b = 2;
                    list.add(a);
                    list.add(b);
                } else if (chargeType.equals("200")) {
                    list.removeAll(list);
                    byte a = 3;
                    byte b = 4;
                    list.add(a);
                    list.add(b);
                } else if (chargeType.equals("100")) {
                    list.removeAll(list);
                    byte a = 7;
                    byte b = 8;
                    byte c = 9;
                    byte d = 10;
                    list.add(a);
                    list.add(b);
                    list.add(c);
                    list.add(d);
                } else if (chargeType.equals("5")) {
                    list.removeAll(list);
                    byte a = 5;
                    list.add(a);
                } else if (chargeType.equals("6")) {
                    list.removeAll(list);
                    byte a = 6;
                    list.add(a);
                } else {
                    PaginationUtil page = new PaginationUtil(1, 300,
                            results.size());
                    return DatasetBuilder.fromDataList(null,
                            page.createPageInfo());
                }
                if (!list.isEmpty()) {
                    //按照充值类型查询
                    oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                            .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                            .andChargeTypeIn(list)//充值类型：1，中石化，2，中石油，3，手机充值，4，手机流量，5，猫眼电影，6，滴滴出行，7，优酷，8，爱奇艺，9，腾讯视频，10，搜狐
                            .andCreateTimeGreaterThanOrEqualTo(createTime);
                }
            } else {
                oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andCreateTimeGreaterThanOrEqualTo(createTime);
            }
        }
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);
        oilOrdersList.stream().forEach(oilOrders -> {
            Map<String, Object> result = new HashMap<String, Object>();
            OilGoodsExample example = new OilGoodsExample();
            OilGoodsTypeExample oilGoodsTypeExample = new OilGoodsTypeExample();
            result.put("orderType", oilOrders.getChargeType());//1中石化2中石油3话费4串码充值
            if (OilOrders.ORDER_STATUS_0.equals(oilOrders.getOrderStatus())
                    || OilOrders.ORDER_STATUS_1
                            .equals(oilOrders.getOrderStatus())) {//处理中
                result.put("orderStatus", "0");//处理中
                result.put("tardeMoney", oilOrders.getFillMoney());
            } else if (OilOrders.ORDER_STATUS_2
                    .equals(oilOrders.getOrderStatus())) {//充值成功
                result.put("orderStatus", "1");//充值成功
                result.put("tardeMoney", oilOrders.getFinishMoney());
            } else if (OilOrders.ORDER_STATUS_4
                    .equals(oilOrders.getOrderStatus())) {//充值失败
                result.put("orderStatus", "2");//充值失败
                result.put("tardeMoney", oilOrders.getFillMoney());
            } else {//部分成功
                result.put("orderStatus", "3");//部分成功
                result.put("tardeMoney", oilOrders.getFinishMoney());
            }

            String goodId = oilOrders.getGoodId();

            if (StringUtils.isNotBlank(goodId)) {
                if (!oilOrders.getChargeType().equals(OilOrders.CHARGE_TYPE_1)//1，中石化
                        && !oilOrders.getChargeType()
                                .equals(OilOrders.CHARGE_TYPE_2)//2，中石油
                        && !oilOrders.getChargeType()
                                .equals(OilOrders.CHARGE_TYPE_3)//3，手机充值 4，手机流量
                        && !oilOrders.getChargeType()
                                .equals(OilOrders.CHARGE_TYPE_4)) {
                    if (StringUtils.isBlank(chargeType)
                            || "100".equals(chargeType)
                            || "5".equals(chargeType) || "6".equals(chargeType)
                            || "7".equals(chargeType) || "8".equals(chargeType)
                            || "9".equals(chargeType)
                            || "10".equals(chargeType)) {
                        example.clear();
                        example.createCriteria().andProductIdEqualTo(goodId);
                        List<OilGoods> oilGoods = this.oilGoodsMapper
                                .selectByExample(example);
                        if (oilGoods.size() > 0) {
                            String goodsName = oilGoods.get(0).getGoodsName();
                            Long typeId = oilGoods.get(0).getTypeId();
                            oilGoodsTypeExample.clear();
                            OilGoodsType oilGoodsType = this.oilGoodsTypeMapper
                                    .selectByPrimaryKey(typeId);

                            String typeName = oilGoodsType.getTypeName();
                            if (goodsName.contains("（")) {
                                result.put("goodsName",
                                        typeName + goodsName.substring(
                                                goodsName.indexOf("（")
                                                        + 1,
                                                goodsName.indexOf("）")));
                            } else {
                                if (goodsName.contains(typeName)) {
                                    result.put("goodsName", goodsName);
                                } else {
                                    result.put("goodsName",
                                            typeName + goodsName);
                                }

                            }
                            if ("5".equals(
                                    oilOrders.getChargeType().toString())) {
                                result.put("indate", "请在一年之内使用，过期作废");
                            } else if ("6".equals(
                                    oilOrders.getChargeType().toString())) {
                                if (goodsName.contains("专车")
                                        || goodsName.contains("快车")) {
                                    if (goodsName.contains("10元")
                                            || goodsName.contains("20元")) {
                                        result.put("indate", "请在6个月之内使用，过期作废");
                                    } else {
                                        result.put("indate", "请在一年之内使用，过期作废");
                                    }
                                } else if (goodsName.contains("代驾")) {
                                    result.put("indate", "请在三个月内使用，过期作废");
                                } 
                            } else {
                                result.put("indate", "请在三个月内使用，过期作废");
                            }
                            result.put("cardSecret", oilOrders.getCardSecret());
                            result.put("cardNo", oilOrders.getCardNo());
                        }
                    }

                } else {
                    if (StringUtils.isBlank(chargeType)
                            || "0".equals(chargeType)
                            || "200".equals(chargeType)
                            || "1".equals(chargeType) || "2".equals(chargeType)
                            || "3".equals(chargeType)
                            || "4".equals(chargeType)) {
                        if (oilOrders.getChargeType()
                                .equals(OilOrders.CHARGE_TYPE_1)
                                || oilOrders.getChargeType()
                                        .equals(OilOrders.CHARGE_TYPE_2)) {
                            example.clear();
                            example.createCriteria()
                                    .andProductIdEqualTo(goodId);
                            List<OilGoods> oilGoods = this.oilGoodsMapper
                                    .selectByExample(example);
                            if (oilGoods.size() > 0) {
                                String denomination = oilGoods.get(0)
                                        .getDenomination();
                                result.put("denomination", denomination);
                            }
                        }
                        if (oilOrders.getChargeType()
                                .equals(OilOrders.CHARGE_TYPE_4)) {
                            result.put("denomination",
                                    oilOrders.getCardSecret());
                        }
                        if (oilOrders.getChargeType()
                                .equals(OilOrders.CHARGE_TYPE_3)) {
                            result.put("denomination",
                                    oilOrders.getFillMoney());
                        }
                    }
                }
            }
            result.put("phone", oilOrders.getCardNo());//被充值手机号
            result.put("createTime", oilOrders.getCreateTime());//创建时间
            result.put("orderNo", oilOrders.getOrderNo());//创建时间
            results.add(result);
        });
        //查询oilTransLog
        if (StringUtils.isEmpty(orderNo) && StringUtils.isEmpty(chargeType)) {
            OilTransLogExample ex = new OilTransLogExample();
            ex.createCriteria().andUserIdEqualTo(userId)
                    .andTransTypeEqualTo(OilTransLog.TRANS_TYPE_0)
                    .andCreateTimeGreaterThanOrEqualTo(createTime);
            ex.setOrderByClause(OilTransLogExample.CREATE_TIME_DESC);
            List<OilTransLog> l = oilTransLogMapper.selectByExample(ex);
            if (!l.isEmpty()) {
                l.stream().forEach(log -> {
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("orderType", "11");//串码充值
                    result.put("orderStatus", "1");//充值成功
                    result.put("tardeMoney", log.getTardeMoney());
                    result.put("phone", log.getUserPhone());//被充值手机号
                    result.put("cardNo", log.getRemark());//充值串码
                    result.put("createTime", log.getCreateTime());//创建时间
                    results.add(result);

                });
            }
        }
        //查询GyOilLog
        GyOilLogExample gyEx = new GyOilLogExample();
        if (StringUtils.isNotEmpty(orderNo)) {
            gyEx.createCriteria().andUserIdEqualTo(userId)
                    .andParentOrderNoIsNull()
                    .andSupplierEqualTo(GyOilLog.SUPPLIER_0)
                    .andCreateTimeGreaterThan(createTime)
                    .andOrderidEqualTo(orderNo);
        } else {
            gyEx.createCriteria().andUserIdEqualTo(userId)
                    .andParentOrderNoIsNull()
                    .andSupplierEqualTo(GyOilLog.SUPPLIER_0)
                    .andCreateTimeGreaterThan(createTime);
        }

        if (StringUtils.isBlank(chargeType) || "0".equals(chargeType)
                || "1".equals(chargeType) || "2".equals(chargeType)) {
            List<GyOilLog> gyList = gyOilLogMapper.selectByExample(gyEx);
            if (!gyList.isEmpty()) {
                gyList.stream().forEach(gyLog -> {
                    Map<String, Object> result = new HashMap<String, Object>();
                    //log.setUserId(userId);
                    if (GyOilLog.CHARGETYPE_1.equals(gyLog.getChargetype())) {
                        result.put("orderType", "1");
                    } else if (GyOilLog.CHARGETYPE_2
                            .equals(gyLog.getChargetype())) {
                        result.put("orderType", "2");
                    }
                    result.put("tardeMoney", gyLog.getFillmoney());
                    if (gyLog.getOrderstatus() == null) {
                        result.put("orderStatus", "0");//处理中
                    } else if (!GyOilLog.ORDERSTATUS_1
                            .equals(gyLog.getOrderstatus())) {
                        result.put("orderStatus", "1");//充值成功
                    } else if (GyOilLog.ORDERSTATUS_1
                            .equals(gyLog.getOrderstatus())) {
                        result.put("orderStatus", "2");//充值失败
                    }
                    //充值油卡卡号
                    result.put("phone", gyLog.getGascardid());//被充值的卡号
                    result.put("orderNo", gyLog.getOrderid());//订单号
                    result.put("createTime", gyLog.getCreateTime());//创建时间
                    results.add(result);
                });
            }

        }
        //查询 gyPhoneLog
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        if (StringUtils.isNotEmpty(orderNo)) {
            gyPhoneLogEx.createCriteria().andUserIdEqualTo(userId)
                    .andParentOrderNoIsNull()
                    .andCreateTimeGreaterThanOrEqualTo(createTime)
                    .andOrderNoEqualTo(orderNo);
        } else {
            gyPhoneLogEx.createCriteria().andUserIdEqualTo(userId)
                    .andParentOrderNoIsNull()
                    .andCreateTimeGreaterThanOrEqualTo(createTime);
        }
        if (StringUtils.isBlank(chargeType) || "200".equals(chargeType)
                || "3".equals(chargeType) || "4".equals(chargeType)) {
            List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                    .selectByExample(gyPhoneLogEx);
            if (!gyPhoneLogs.isEmpty()) {

                gyPhoneLogs.stream().forEach(gyPhoneLog -> {
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("orderType", "3");

                    if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_0
                            || gyPhoneLog.getStatus() == GyPhoneLog.STATUS_1) {//处理中
                        result.put("orderStatus", "0");
                        result.put("tardeMoney",
                                new BigDecimal(gyPhoneLog.getProdContent()));
                    } else if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_3) {//充值成功
                        result.put("orderStatus", "1");
                        result.put("tardeMoney", gyPhoneLog.getOrderMoney());
                    } else {//充值失败
                        result.put("orderStatus", "2");
                        result.put("tardeMoney",
                                new BigDecimal(gyPhoneLog.getProdContent()));
                    }
                    //高阳充值手机号
                    result.put("phone", gyPhoneLog.getMobileNum());
                    result.put("orderNo", gyPhoneLog.getOrderNo());
                    result.put("createTime", gyPhoneLog.getCreateTime());
                    results.add(result);
                });
            }

        }
        Collections.sort(results, new Comparator<Map<String, Object>>() {

            @Override
            public int compare(Map<String, Object> gy1,
                    Map<String, Object> gy2) {
                if (((Date) gy1.get("createTime"))
                        .getTime() > ((Date) gy2.get("createTime")).getTime()) {
                    return -1;
                } else if (((Date) gy1.get("createTime"))
                        .getTime() < ((Date) gy2.get("createTime")).getTime()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        PaginationUtil page = new PaginationUtil(1, 300, results.size());
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (int i = page.getStartRow(); i < results.size(); i++) {
            if (i == (page.getStartRow() + page.getSize())) {
                break;
            }
            resultList.add(results.get(i));
        }
        return DatasetBuilder.fromDataList(resultList, page.createPageInfo());
    };

    private DatasetList<Map<String, Object>> h5queryPhoneLogList(Long userId,
            String orderNo, String userPhone, String pageNo, String pageSize) {
        if (Integer.valueOf(pageSize) > 500) {
            throw new BusinessException("查询信息太多,请减少每页数量");
        }
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.set(Calendar.MONTH, 0);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        Date createTime = now.getTime();
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        //查询oilOrders
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        oilOrdersEx.setOrderByClause(OilOrdersExample.CREATE_TIME_DESC);
        if (StringUtils.isNotEmpty(orderNo)) {
            if (StringUtils.isNotEmpty(userPhone)) {
                oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_3)
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andOrderNoEqualTo(orderNo)//订单号
                        .andCardNoEqualTo(userPhone);//被充值手机号
            } else {
                oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_3)
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andOrderNoEqualTo(orderNo);//订单号
            }

        } else {
            if (StringUtils.isNotEmpty(userPhone)) {
                oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_3)
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andCardNoEqualTo(userPhone);//被充值手机号
            } else {
                oilOrdersEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_3)
                        .andCreateTimeGreaterThanOrEqualTo(createTime);
            }
        }
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);
        oilOrdersList.stream().forEach(oilOrders -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("orderType", "3");//话费
            if (OilOrders.ORDER_STATUS_0.equals(oilOrders.getOrderStatus())
                    || OilOrders.ORDER_STATUS_1
                            .equals(oilOrders.getOrderStatus())) {//处理中
                result.put("orderStatus", "0");//处理中
                result.put("tardeMoney", oilOrders.getFillMoney());
            } else if (OilOrders.ORDER_STATUS_2
                    .equals(oilOrders.getOrderStatus())) {//充值成功
                result.put("orderStatus", "1");//充值成功
                result.put("tardeMoney", oilOrders.getFinishMoney());
            } else if (OilOrders.ORDER_STATUS_4
                    .equals(oilOrders.getOrderStatus())) {//充值失败
                result.put("orderStatus", "2");//充值失败
                result.put("tardeMoney", oilOrders.getFillMoney());
            } else {//部分成功
                result.put("orderStatus", "3");//部分成功
                result.put("tardeMoney", oilOrders.getFinishMoney());
            }
            result.put("denomination", oilOrders.getFillMoney());
            result.put("phone", oilOrders.getCardNo());//被充值手机号
            result.put("createTime", oilOrders.getCreateTime());//创建时间
            result.put("orderNo", oilOrders.getOrderNo());//创建时间
            results.add(result);
        });
        //查询 gyPhoneLog
        GyPhoneLogExample gyPhoneLogEx = new GyPhoneLogExample();
        if (StringUtils.isNotEmpty(orderNo)) {
            if (StringUtils.isNotEmpty(userPhone)) {
                gyPhoneLogEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andParentOrderNoIsNull()
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andOrderNoEqualTo(orderNo)//订单号
                        .andMobileNumEqualTo(userPhone);//被充值手机号
            } else {
                gyPhoneLogEx.createCriteria().andUserIdEqualTo(userId)
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)
                        .andParentOrderNoIsNull()
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andOrderNoEqualTo(orderNo);
            }

        } else {
            if (StringUtils.isNotEmpty(userPhone)) {
                gyPhoneLogEx.createCriteria().andUserIdEqualTo(userId)//用户id
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0)//服务商
                        .andParentOrderNoIsNull()
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andMobileNumEqualTo(userPhone);//被充值手机号
            } else {
                gyPhoneLogEx.createCriteria().andUserIdEqualTo(userId)
                        .andParentOrderNoIsNull()
                        .andCreateTimeGreaterThanOrEqualTo(createTime)
                        .andSupplierEqualTo(GyPhoneLog.SUPPLIER_0);
            }
        }
        //根据创建时间排序
        gyPhoneLogEx.setOrderByClause(GyPhoneLogExample.CREATE_TIME_DESC);
        List<GyPhoneLog> gyPhoneLogs = gyPhoneLogMapper
                .selectByExample(gyPhoneLogEx);
        //组织返回结果
        gyPhoneLogs.stream().forEach(gyPhoneLog -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("orderType", "3");//话费
            if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_0
                    || gyPhoneLog.getStatus() == GyPhoneLog.STATUS_1) {//处理中
                result.put("orderStatus", "0");//处理中
                result.put("tardeMoney",
                        new BigDecimal(gyPhoneLog.getProdContent()));
            } else if (gyPhoneLog.getStatus() == GyPhoneLog.STATUS_3) {//充值成功
                result.put("orderStatus", "1");//充值成功
                result.put("tardeMoney", gyPhoneLog.getOrderMoney());
            } else {//充值失败
                result.put("orderStatus", "2");//充值失败
                result.put("tardeMoney",
                        new BigDecimal(gyPhoneLog.getProdContent()));
            }
            result.put("phone", gyPhoneLog.getMobileNum());//被充值手机号
            result.put("createTime", gyPhoneLog.getCreateTime());//创建时间
            result.put("orderNo", gyPhoneLog.getOrderNo());//创建时间
            results.add(result);
        });
        PaginationUtil page = new PaginationUtil(Integer.valueOf(pageNo),
                Integer.valueOf(pageSize), results.size());
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (int i = page.getStartRow(); i < results.size(); i++) {
            if (i == (page.getStartRow() + page.getSize())) {
                break;
            }
            resultList.add(results.get(i));
        }
        return DatasetBuilder.fromDataList(resultList, page.createPageInfo());
    }

    /**
     * 油卡状态未开通、未使用两种状态定时修改为过期。
     */
    @Override
    @Write
    @Transactional
    public void checkOilCardStatus() {
        OilRechargeCodeExample example = new OilRechargeCodeExample();
        //先生成的油卡，状态先改。
        example.setOrderByClause(OilRechargeCodeExample.ID_ASC);
        //每次更新前500条卡状态信息
        example.setLimitStart(0);
        example.setLimitEnd(500);
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria createCriteria1 = example
                .createCriteria();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria createCriteria2 = example
                .createCriteria();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria createCriteria3 = example
                .createCriteria();
        com.emate.shop.business.model.OilRechargeCodeExample.Criteria createCriteria4 = example
                .createCriteria();
        //组装查询条件 有截止时间，代表是新卡，将当前时间和截止时间做比较；无截止时间，代表是老卡，将当前时间和卡的创建时间做比较。
        //组合1 `status` =0 and dead_time < NOW()
        createCriteria1.andStatusEqualTo(OilRechargeCode.STATUS_0)
                .andDeadTimeLessThan(new Date());
        //组合2 `status` =1 and recharge_time is NULL and dead_time < NOW()
        createCriteria2.andStatusEqualTo(OilRechargeCode.STATUS_1)
                .andRechargeTimeIsNull().andDeadTimeLessThan(new Date());
        //组合3 `status` =0 and create_time < "2017-04-17 00:00:00")离创建时间大于一年零一个月，过期。
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -13);
        Date time = calendar.getTime();
        createCriteria3.andStatusEqualTo(OilRechargeCode.STATUS_0)
                .andCreateTimeLessThan(time);
        //组合4 `status` =1 and recharge_time is NULL and create_time < "2017-04-17 00:00:00")
        createCriteria4.andStatusEqualTo(OilRechargeCode.STATUS_1)
                .andRechargeTimeIsNull().andCreateTimeLessThan(time);
        example.or(createCriteria2);
        example.or(createCriteria3);
        example.or(createCriteria4);
        List<OilRechargeCode> oilRechargeCodeList = this.oilRechargeCodeMapper
                .selectByExample(example);
        if (Optional.of(oilRechargeCodeList).isPresent()
                && oilRechargeCodeList.size() > 0) {
            for (OilRechargeCode oilRechargeCode : oilRechargeCodeList) {
                //修改状态为已过期
                oilRechargeCode.setId(oilRechargeCode.getId());
                oilRechargeCode.setStatus(OilRechargeCode.STATUS_3);
                this.oilRechargeCodeMapper.updateByPrimaryKey(oilRechargeCode);
            }
        }
    }

    @Override
    @Read
    public DatasetList<Map<String, String>> exportOilExcelTwo(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        //分解年月
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);

        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.add(Calendar.DAY_OF_MONTH, -now.get(Calendar.DAY_OF_MONTH) + 1);
        now.add(Calendar.HOUR_OF_DAY, -now.get(Calendar.HOUR_OF_DAY));
        now.add(Calendar.MINUTE, -now.get(Calendar.MINUTE));
        now.add(Calendar.SECOND, -now.get(Calendar.SECOND));
        now.add(Calendar.MILLISECOND, -now.get(Calendar.MILLISECOND));
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month - 1);
        Date startTime = now.getTime();
        now.set(Calendar.MONTH, month);
        Date endTime = now.getTime();

        	OilOrdersExample oilOrdersEx = new OilOrdersExample();
            oilOrdersEx.createCriteria().andCreateTimeBetween(startTime, endTime)
                    .andProviderIsNotNull();
            oilOrdersEx.setOrderByClause(OilOrdersExample.CREATE_TIME_ASC);
        	List<OilOrders> oilOrdersList = oilOrdersMapper
                    .selectByExample(oilOrdersEx);
            //账户手机号
            List<Long> userIds = new ArrayList<Long>();
            userIds.add(0L);
            //接口提供者
            List<Long> providerIds = new ArrayList<Long>();
            providerIds.add(0L);
            if (!oilOrdersList.isEmpty()) {
                for (OilOrders oilOrders : oilOrdersList) {
                    userIds.add(oilOrders.getUserId());
                    providerIds.add(oilOrders.getProvider());
                    Map<String, String> result = new HashMap<String, String>();
                    //服务商
                    result.put("oilComp", "欧飞油卡");
                    //订单号
                    result.put("orderNo", oilOrders.getOrderNo());
                    //创建时间
                    result.put("createTime",
                            format.format(oilOrders.getCreateTime()));
                    //订单状态
                    if (OilOrders.ORDER_STATUS_0
                            .equals(oilOrders.getOrderStatus())) {
                        result.put("status", "待处理");
                    } else if (OilOrders.ORDER_STATUS_1
                            .equals(oilOrders.getOrderStatus())) {
                        result.put("status", "处理中");
                    } else if (OilOrders.ORDER_STATUS_2
                            .equals(oilOrders.getOrderStatus())) {
                        result.put("status", "充值成功");
                    } else if (OilOrders.ORDER_STATUS_3
                            .equals(oilOrders.getOrderStatus())) {
                        result.put("status", "部分成功");
                    } else {
                        result.put("status", "充值失败");
                    }
                    //充值卡号
                    result.put("cardNo", oilOrders.getCardNo());
                    //充值账户
                    result.put("userPhone", String.valueOf(oilOrders.getUserId()));
                    //充值金额
                    result.put("money",
                            String.valueOf(oilOrders.getFillMoney().doubleValue()));
                    //订单完成后的账户余额
                    if (Objects.isNull(oilOrders.getSurplusMoney())) {
                        result.put("surplusMoney", "0");
                    } else {
                        result.put("surplusMoney", String.valueOf(
                                oilOrders.getSurplusMoney().doubleValue()));
                    }
                    //订单类型
                    if (OilOrders.CHARGE_TYPE_1.equals(oilOrders.getChargeType())) {
                        result.put("orderType", "中石化油卡");
                    } else if (OilOrders.CHARGE_TYPE_2
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "中石油油卡");
                    } else if (OilOrders.CHARGE_TYPE_3
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "话费充值");
                    } else if (OilOrders.CHARGE_TYPE_4
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "流量充值");
                    } else if (OilOrders.CHARGE_TYPE_5
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "猫眼电影");
                    } else if (OilOrders.CHARGE_TYPE_6
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "滴滴出行");
                    } else if (OilOrders.CHARGE_TYPE_7
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "优酷视频");
                    } else if (OilOrders.CHARGE_TYPE_8
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "爱奇艺视频");
                    } else if (OilOrders.CHARGE_TYPE_9
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "腾讯视频");
                    } else if (OilOrders.CHARGE_TYPE_10
                            .equals(oilOrders.getChargeType())) {
                        result.put("orderType", "搜狐视频");
                    } else {
                        result.put("orderType", "未知订单类型");
                    }
                    //接口提供者
                    result.put("provider", String.valueOf(oilOrders.getProvider()));
                    results.add(result);
                }
            }
            //根据条件查询账户手机号
            OilUserExample userEx = new OilUserExample();
            userEx.createCriteria().andIdIn(userIds);
            List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
            Map<String, String> userMap = new HashMap<String, String>();
            oilUsers.stream().forEach(user -> {
                userMap.put(String.valueOf(user.getId()), user.getPhone());
            });
            //根据条件查询接口提供者
            OilProviderExample oilProviderEx = new OilProviderExample();
            oilProviderEx.createCriteria().andIdIn(providerIds);
            List<OilProvider> oilProviders = oilProviderMapper
                    .selectByExample(oilProviderEx);
            Map<String, String> providerMap = new HashMap<String, String>();
            oilProviders.stream().forEach(oilProvider -> {
                providerMap.put(String.valueOf(oilProvider.getId()),
                        oilProvider.getCompany());
            });
            for (Map<String, String> map : results) {

                String userPhone = userMap.get(map.get("userPhone"));
                if (StringUtils.isEmpty(userPhone)) {
                    map.put("userPhone", null);
                } else {
                    map.put("userPhone", userPhone);
                }

                String provider = providerMap.get(map.get("provider"));
                if (StringUtils.isEmpty(provider)) {
                    map.put("provider", null);
                } else {
                    map.put("provider", provider);
                }

            }
            return DatasetBuilder.fromDataList(results);

    }
    
    
    @Override
    @Read
    public DatasetList<Map<String, String>> exportOilExcelWithPhone(String phone){
    	if(Objects.isNull(phone)){
    		throw new BusinessException("请输入手机号");
    	}
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	List<Map<String, String>> results = new ArrayList<Map<String, String>>();
    	//根据手机号查找用户id
        OilUserExample userEx = new OilUserExample();
        userEx.createCriteria().andPhoneEqualTo(phone);
        List<OilUser> oilUsers = oilUserMapper.selectByExample(userEx);
        if(oilUsers.size() > 1){
        	throw new BusinessException("该手机号码有异常，请联系技术");
        }
        if(Objects.isNull(oilUsers) || oilUsers.size() == 0){
        	throw new BusinessException("该手机号码不存在");
        }
        Long userId = 0L;
        for(OilUser oilUser : oilUsers){
        	userId = oilUser.getId();
        }
        
        
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        oilOrdersEx.createCriteria().andUserIdEqualTo(userId);    //根据用户id查找订单
        oilOrdersEx.setOrderByClause(OilOrdersExample.CREATE_TIME_ASC);   //时间排序
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);
      //接口提供者
        List<Long> providerIds = new ArrayList<Long>();
        providerIds.add(0L);
        if (!oilOrdersList.isEmpty()) {
            for (OilOrders oilOrders : oilOrdersList) {
            	
                Map<String, String> result = new HashMap<String, String>();
                //服务商
                result.put("oilComp", "欧飞油卡");
                //订单号
                result.put("orderNo", oilOrders.getOrderNo());
                //创建时间
                result.put("createTime",
                        format.format(oilOrders.getCreateTime()));
                //订单状态
                if (OilOrders.ORDER_STATUS_0
                        .equals(oilOrders.getOrderStatus())) {
                    result.put("status", "待处理");
                } else if (OilOrders.ORDER_STATUS_1
                        .equals(oilOrders.getOrderStatus())) {
                    result.put("status", "处理中");
                } else if (OilOrders.ORDER_STATUS_2
                        .equals(oilOrders.getOrderStatus())) {
                    result.put("status", "充值成功");
                } else if (OilOrders.ORDER_STATUS_3
                        .equals(oilOrders.getOrderStatus())) {
                    result.put("status", "部分成功");
                } else {
                    result.put("status", "充值失败");
                }
                //充值卡号
                result.put("cardNo", oilOrders.getCardNo());
                //充值账户
                result.put("userPhone", phone);
                //充值金额
                result.put("money",
                        String.valueOf(oilOrders.getFillMoney().doubleValue()));
                //订单完成后的账户余额
                if (Objects.isNull(oilOrders.getSurplusMoney())) {
                    result.put("surplusMoney", "0");
                } else {
                    result.put("surplusMoney", String.valueOf(
                            oilOrders.getSurplusMoney().doubleValue()));
                }
                //订单类型
                if (OilOrders.CHARGE_TYPE_1.equals(oilOrders.getChargeType())) {
                    result.put("orderType", "中石化油卡充值");
                } else if (OilOrders.CHARGE_TYPE_2
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "中石油油卡充值");
                } else if (OilOrders.CHARGE_TYPE_3
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "话费充值");
                } else if (OilOrders.CHARGE_TYPE_4
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "流量充值");
                } else if (OilOrders.CHARGE_TYPE_5
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "猫眼电影");
                } else if (OilOrders.CHARGE_TYPE_6
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "滴滴出行");
                } else if (OilOrders.CHARGE_TYPE_7
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "优酷视频");
                } else if (OilOrders.CHARGE_TYPE_8
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "爱奇艺视频");
                } else if (OilOrders.CHARGE_TYPE_9
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "腾讯视频");
                } else if (OilOrders.CHARGE_TYPE_10
                        .equals(oilOrders.getChargeType())) {
                    result.put("orderType", "搜狐视频");
                } else {
                    result.put("orderType", "未知类型");
                }
                //接口提供者
                if(oilOrders.getProvider() != null){
                	providerIds.add(oilOrders.getProvider());
                    result.put("provider", String.valueOf(oilOrders.getProvider()));
                }else{
                	result.put("provider", null);
                }    
                results.add(result);
            }
        }

        
        //查询gy_oil_log表
        GyOilLogExample gyOilExample = new GyOilLogExample();
        gyOilExample.createCriteria().andUserIdEqualTo(userId).andParentOrderNoIsNull(); //根据用户id查找父订单号为空的订单
        gyOilExample.setOrderByClause(GyOilLogExample.CREATE_TIME_ASC);
        List<GyOilLog> gyOilList = gyOilLogMapper.selectByExample(gyOilExample);
        if(gyOilList.size() > 0 && gyOilList != null){
        	for(GyOilLog gyOilLog : gyOilList){
        		Map<String, String> result = new HashMap<String, String>();
                //服务商
        		if (new Integer("0").equals(gyOilLog.getSupplier())) {
                    result.put("oilComp", "欧飞油卡");//0:追电1 高阳2欧飞
                } else {
                    result.put("oilComp", "高阳迅捷");//0:追电1 高阳2欧飞
                }

                //result.put("oilComp", "高阳迅捷");
                //订单号
                result.put("orderNo", gyOilLog.getOrderid());
                //创建时间
                result.put("createTime",
                        format.format(gyOilLog.getCreateTime()));
                //订单状态
                if (GyOilLog.ORDERSTATUS_0
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "充值成功");
                } else if (GyOilLog.ORDERSTATUS_1
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "充值失败");
                } else if (GyOilLog.ORDERSTATUS_2
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "部分成功");
                } else if (GyOilLog.ORDERSTATUS_3
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "待处理");
                } else if (GyOilLog.ORDERSTATUS_4
                        .equals(gyOilLog.getOrderstatus())) {
                    result.put("status", "处理中");
                } else {
                	result.put("status", "未知状态");
                }
                //充值卡号
                result.put("cardNo", gyOilLog.getGascardid());
                //充值账户
                result.put("userPhone", phone);
                //充值金额
                result.put("money",
                        String.valueOf(gyOilLog.getFillmoney().doubleValue()));
                //订单完成后的账户余额
                if (Objects.isNull(gyOilLog.getSurplusMoney())) {
                    result.put("surplusMoney", "0");
                } else {
                    result.put("surplusMoney", String.valueOf(
                    		gyOilLog.getSurplusMoney().doubleValue()));
                }
                //订单类型
                if (GyOilLog.CHARGETYPE_1.equals(gyOilLog.getChargetype())) {
                    result.put("orderType", "中石化油卡充值");
                } else if (GyOilLog.CHARGETYPE_2.equals(gyOilLog.getChargetype())) {
                    result.put("orderType", "中石油油卡充值");
                }
                //接口提供者
                if(gyOilLog.getProvider() != null){
                	providerIds.add(Long.valueOf(gyOilLog.getProvider()));
                    result.put("provider", String.valueOf(gyOilLog.getProvider()));
                }else{
                	result.put("provider", null);
                }
                results.add(result);
        	}
        }

        
        
      //查询gy_phone_log表
        GyPhoneLogExample gyPhoneExample = new GyPhoneLogExample();
        gyPhoneExample.createCriteria().andUserIdEqualTo(userId).andParentOrderNoIsNull();  //根据用户id查找父订单号为空的订单
        gyPhoneExample.setOrderByClause(GyPhoneLogExample.CREATE_TIME_ASC);
        List<GyPhoneLog> gyPhoneList = gyPhoneLogMapper.selectByExample(gyPhoneExample);
        if(gyPhoneList.size() > 0 && gyPhoneList != null){
        	for(GyPhoneLog gyPhoneLog : gyPhoneList){
        		Map<String, String> result = new HashMap<String, String>();
                //服务商
        		if (new Integer("0").equals(gyPhoneLog.getSupplier())) {
                    result.put("oilComp", "欧飞油卡");
                } else {
                    result.put("oilComp", "高阳迅捷");
                }
                //result.put("oilComp", "高阳迅捷");
                //订单号
                result.put("orderNo", gyPhoneLog.getOrderNo());
                //创建时间
                result.put("createTime",
                        format.format(gyPhoneLog.getCreateTime()));
                //订单状态 0，下单成功，1，下单失败扣款未知，2，下单失败未扣款，3，充值成功，4，充值失败，5，待处理
                if (GyPhoneLog.STATUS_0
                        .equals(gyPhoneLog.getStatus())) {
                    result.put("status", "下单成功");
                } else if (GyPhoneLog.STATUS_1
                        .equals(gyPhoneLog.getStatus()) || GyPhoneLog.STATUS_2
                        .equals(gyPhoneLog.getStatus())) {
                    result.put("status", "下单失败");
                } else if (GyPhoneLog.STATUS_3
                        .equals(gyPhoneLog.getStatus())) {
                    result.put("status", "充值成功");
                } else if (GyPhoneLog.STATUS_4
                        .equals(gyPhoneLog.getStatus())) {
                    result.put("status", "充值失败");
                } else if (GyPhoneLog.STATUS_5
                        .equals(gyPhoneLog.getStatus())) {
                    result.put("status", "待处理");
                } else {
                	result.put("status", "未知状态");
                }
                //充值卡号
                result.put("cardNo", gyPhoneLog.getMobileNum());
                //充值账户
                result.put("userPhone", phone);
                //充值金额
                result.put("money",gyPhoneLog.getProdContent());
                //订单完成后的账户余额
                if (Objects.isNull(gyPhoneLog.getSurplusMoney())) {
                    result.put("surplusMoney", "0");
                } else {
                    result.put("surplusMoney", String.valueOf(
                    		gyPhoneLog.getSurplusMoney().doubleValue()));
                }
                //订单类型

                result.put("orderType", "话费充值");
              
                //接口提供者
                if(gyPhoneLog.getProvider() != null){
                	providerIds.add(Long.valueOf(gyPhoneLog.getProvider()));
                    result.put("provider", String.valueOf(gyPhoneLog.getProvider()));
                }else{
                	result.put("provider", null);
                }
                results.add(result);
        	}
        }

        
      //查询oil_trans_log表
        OilTransLogExample oilTransLogExample = new OilTransLogExample();
        oilTransLogExample.createCriteria().andUserIdEqualTo(userId).andTransTypeBetween(1,5); //根据用户id查找油卡，话费订单
        oilTransLogExample.setOrderByClause(OilTransLogExample.CREATE_TIME_ASC);
        List<OilTransLog> oilTransList = oilTransLogMapper.selectByExample(oilTransLogExample);
        if(oilTransList.size() > 0 && oilTransList != null){
        	for(OilTransLog oilTransLog : oilTransList){
        		
        		Map<String, String> result = new HashMap<String, String>();
        		

        		Map<String, String> remarkMap = JacksonHelper.fromJSON(oilTransLog.getRemark(), Map.class);
        			
        		
                //服务商
                result.put("oilComp", "追电科技");
                //订单号
                if(StringUtils.isEmpty(oilTransLog.getOrderSn())){
                	result.put("orderNo", null);
                }else{
                	result.put("orderNo", oilTransLog.getOrderSn());
                }
                
                //创建时间
                result.put("createTime",
                        format.format(oilTransLog.getCreateTime()));
                
                if(oilTransLog.getTransType() == 1){
                	JSONObject jObj = JSONObject.fromObject(oilTransLog.getRemark());
                    JSONArray jary=jObj.getJSONArray("subList");
                    if(jary != null){
                    	for (int i=0;i<jary.size();i++) {
                            JSONObject obj = jary.getJSONObject(i);
                            //订单状态
                            if(StringUtils.equals("充值成功", obj.getString("respMsg").trim())){
                            	result.put("status", "充值成功");
                            }else if(StringUtils.equals("充值失败", obj.getString("respMsg").trim())){
                            	result.put("status", "充值失败");
                            }else if(StringUtils.equals("处理中", obj.getString("respMsg").trim())){
                            	result.put("status", "处理中");
                            }else{
                            	result.put("status", "未知状态");
                            } 
                            //充值卡号
                            result.put("cardNo", obj.getString("transNo"));
                        }
                    }
                }else{
                	//订单状态
                	if(StringUtils.equals("充值成功", remarkMap.get("respMsg"))){
                    	result.put("status", "充值成功");
                    }else if(StringUtils.equals("充值失败", remarkMap.get("respMsg"))){
                    	result.put("status", "充值失败");
                    }else if(StringUtils.equals("处理中", remarkMap.get("respMsg"))){
                    	result.put("status", "处理中");
                    }else{
                    	result.put("status", "未知状态");
                    }    	
                	//充值卡号
                    result.put("cardNo", remarkMap.get("cardNo"));
                }

                //充值账户
                result.put("userPhone", phone);
                //充值金额
                result.put("money",String.valueOf(oilTransLog.getTardeMoney().doubleValue()));
                //订单完成后的账户余额
                if (Objects.isNull(oilTransLog.getUserMoney())) {
                    result.put("surplusMoney", "0");
                } else {
                    result.put("surplusMoney", String.valueOf(
                    		oilTransLog.getUserMoney().doubleValue()));
                }
                //订单类型 1，油卡电子券消费   2，中石油加油卡充值，3，中石化，4，手机充值，5，流量充值，
                if(OilTransLog.TRANS_TYPE_1.equals(oilTransLog.getTransType())){
                	result.put("orderType", "油卡电子券消费");
                }else if(OilTransLog.TRANS_TYPE_2.equals(oilTransLog.getTransType())){
                	result.put("orderType", "中石油油卡充值");
                }else if(OilTransLog.TRANS_TYPE_3.equals(oilTransLog.getTransType())){
                	result.put("orderType", "中石化油卡充值");
                }else if(OilTransLog.TRANS_TYPE_4.equals(oilTransLog.getTransType())){
                	result.put("orderType", "话费充值");
                }else{
                	result.put("orderType", "流量充值");
                }

                //接口提供者
                result.put("provider", null);
                results.add(result);
        	}
        }


        
      //根据条件查询接口提供者
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andIdIn(providerIds);
        List<OilProvider> oilProviders = oilProviderMapper
                .selectByExample(oilProviderEx);
        Map<String, String> providerMap = new HashMap<String, String>();
        oilProviders.stream().forEach(oilProvider -> {
            providerMap.put(String.valueOf(oilProvider.getId()),
                    oilProvider.getCompany());
        });
        
        for (Map<String, String> map : results) {
        	
        	if(StringUtils.isNotEmpty(map.get("provider"))) {
        		String provider = providerMap.get(map.get("provider"));
                if (StringUtils.isEmpty(provider)) {
                    map.put("provider", null);
                } else {
                    map.put("provider", provider);
                }
        	}
        }
        
        //对结果按照时间排序
        Collections.sort(results,new Comparator<Map<String, String>>() {
 
			@Override
			public int compare(Map<String, String> m1, Map<String, String> m2) {
				Date date1;
				Date date2;
				try {
					date1 = format.parse(m1.get("createTime"));
					date2 = format.parse(m2.get("createTime"));
					if(date1.getTime() > date2.getTime()){
						return 1;
					}else if(date1.getTime() < date2.getTime()){
						return -1;
					}else{
						return 0;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});

        return DatasetBuilder.fromDataList(results);
    }

    @Override
    @Read
    public DatasetList<OilUser> oilUserList(Integer pageNo, Integer pageSize,
            String phone) {

        OilUserExample oilUserEx = new OilUserExample();
        if (StringUtils.isNotEmpty(phone)) {
            oilUserEx.createCriteria().andPhoneEqualTo(phone);
        }
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilUserMapper.countByExample(oilUserEx));
        oilUserEx.setLimitStart(page.getStartRow());
        oilUserEx.setLimitEnd(page.getSize());
        oilUserEx.setOrderByClause(OilUserExample.CREATE_TIME_DESC);
        //查询结果
        List<OilUser> oilUserList = oilUserMapper.selectByExample(oilUserEx);
        return DatasetBuilder.fromDataList(oilUserList, page.createPageInfo());
    }

    @Override
    @Read
    public DatasetList<OilProvider> oufeiUserInfoList(Integer pageNo,
            Integer pageSize, String company) {
        OilProviderExample oilProviderEx = new OilProviderExample();
        if (StringUtils.isNotEmpty(company)) {
            oilProviderEx.createCriteria().andCompanyLike("%" + company + "%");
        }
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilProviderMapper.countByExample(oilProviderEx));
        oilProviderEx.setLimitStart(page.getStartRow());
        oilProviderEx.setLimitEnd(page.getSize());
        oilProviderEx.setOrderByClause(OilUserExample.CREATE_TIME_DESC);
        //查询结果
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);
        return DatasetBuilder.fromDataList(oilProviderList,
                page.createPageInfo());
    }

    /**
     * 添加欧飞账户
     */
    @Override
    @Write
    @Transactional
    public DatasetSimple<String> addOilProvider(String userName,
            String userPassword, String company) {

        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException("用户名不能为空");
        }
        if (StringUtils.isEmpty(userPassword)) {
            throw new BusinessException("用户密码不能为空");
        }
        if (StringUtils.isEmpty(company)) {
            throw new BusinessException("公司名称不能为空");
        }
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andUserNameEqualTo(userName);
        List<OilProvider> oilProviders = oilProviderMapper
                .selectByExample(oilProviderEx);
        if (!oilProviders.isEmpty()) {
            throw new BusinessException("该账户已存在！！！！");
        }
        OilProvider oilProvider = new OilProvider();
        oilProvider.setUserName(userName);
        oilProvider.setUserPassword(
                KeyedDigestMD5.getKeyedDigest(userPassword, ""));
        oilProvider.setCompany(company);
        oilProvider.setStatus(OilProvider.STATUS_0);
        oilProvider.setCreateTime(new Date());
        oilProvider.setUpdateTime(new Date());
        if (oilProviderMapper.insertSelective(oilProvider) != 1) {
            throw new BusinessException("添加账户失败！！！");
        }
        ;
        return DatasetBuilder.fromDataSimple("SUCCESS");
    }

    /**
     * 
     */
    @Override
    @Transactional
    @Write
    public DatasetSimple<String> updateOilProvider(String providerId,
            String userPassword) {
        if (StringUtils.isEmpty(providerId)) {
            throw new BusinessException("欧飞账户id为空");
        }
        if (StringUtils.isEmpty(userPassword)) {
            throw new BusinessException("欧飞账户密码为空");
        }
        OilProvider oilProvider = oilProviderMapper
                .selectByPrimaryKey(Long.valueOf(providerId));
        if (Objects.isNull(oilProvider)) {
            throw new BusinessException("找不到该欧飞账户");
        }
        String key = KeyedDigestMD5.getKeyedDigest(userPassword, "");
        oilProvider.setUserPassword(key);
        oilProvider.setUpdateTime(new Date());
        if (oilProviderMapper.updateByPrimaryKeySelective(oilProvider) != 1) {
            throw new BusinessException("修改欧飞账户密码失败");
        }
        return DatasetBuilder.fromDataSimple("SUCCESS");
    }

    @Override
    @Read
    public DatasetList<Map<String, String>> exportOilCardOrderExcel(
            String date) {
        OilcardOrderRelationExample oilcardOrderRelationEx = new OilcardOrderRelationExample();
        if (StringUtils.isEmpty(date)) {
            throw new BusinessException("导出时间必填");
        }
        String[] dates = date.split("-");
        if (dates.length != 3) {
            throw new BusinessException("导出时间格式不对");
        }
        int year = Integer.valueOf(dates[0]);
        int month = Integer.valueOf(dates[1]);
        int day = Integer.valueOf(dates[2]);
        Calendar now = Calendar.getInstance(Locale.CHINA);
        //时分秒毫秒归零
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        //赋值年月日
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, (month - 1));
        now.set(Calendar.DAY_OF_MONTH, day);
        Date startTime = now.getTime();
        now.add(Calendar.DAY_OF_MONTH, 1);
        Date endtime = now.getTime();

        oilcardOrderRelationEx.createCriteria()
                .andCreateTimeBetween(startTime, endtime)
                .andMoneyNotEqualTo(BigDecimal.ZERO)
                .andUserIdNotEqualTo(29976L);
        List<OilcardOrderRelation> oilcardOrderRelationList = oilcardOrderRelationMapper
                .selectByExample(oilcardOrderRelationEx);
        //查找被充值账户
        List<String> cardNos = oilcardOrderRelationList.stream()
                .map(OilcardOrderRelation::getCardNo).distinct()
                .collect(Collectors.toList());
        cardNos.add("");
        OilRechargeCodeExample oilRechargeCodeEx = new OilRechargeCodeExample();
        oilRechargeCodeEx.createCriteria().andPhoneIn(cardNos);

        List<OilRechargeCode> oilRechargeCodeList = oilRechargeCodeMapper
                .selectByExample(oilRechargeCodeEx);
        Map<String, String> providerMap = new HashMap<String, String>();
        Map<String, String> addressMap = new HashMap<String, String>();
        oilRechargeCodeList.stream().forEach(oilRechargeCode -> {
            Integer supplier = oilRechargeCode.getSupplier();
            if (Objects.isNull(supplier)) {
                providerMap.put(oilRechargeCode.getPhone(), "0");
            } else {
                providerMap.put(oilRechargeCode.getPhone(),
                        String.valueOf(supplier));
            }
            addressMap.put(oilRechargeCode.getPhone(),
                    oilRechargeCode.getAddress());
        });
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        for (OilcardOrderRelation oilcardOrderRelation : oilcardOrderRelationList) {
            Map<String, String> result = new HashMap<String, String>();
            result.put("orderTime",
                    format.format(oilcardOrderRelation.getCreateTime()));
            result.put("cardNo", oilcardOrderRelation.getCardNo());
            result.put("cardMoney", String
                    .valueOf(oilcardOrderRelation.getMoney().doubleValue()));
            String provider = providerMap.get(oilcardOrderRelation.getCardNo());
            if (StringUtils.isEmpty(provider)) {
                result.put("provider", "北京亿特诺美");
            } else if ("0".equals(provider)) {
                result.put("provider", "北京亿特诺美");
            } else if ("1".equals(provider)) {
                result.put("provider", "上海腾霜");
            } else if ("2".equals(provider)) {
                result.put("provider", "上海腾姆");
            } else if ("3".equals(provider)) {
                result.put("provider", "上海泰奎");
            } else if ("4".equals(provider)) {
                result.put("provider", "上海泰钭");
            } else {
                result.put("provider", "未知供应商");
            }
            String address = addressMap.get(oilcardOrderRelation.getCardNo());
            if (StringUtils.isEmpty(address)) {
                result.put("address", "未知开通城市");
            } else {
                result.put("address", address);
            }
            results.add(result);
        }
        return DatasetBuilder.fromDataList(results);
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> oilCardLiucunList(Integer pageNo,
            Integer pageSize, String date) {
        if (StringUtils.isEmpty(date)) {
            throw new BusinessException("开通日期不能为空");
        }
        String[] strArray = date.split("-");
        int year = Integer.valueOf(strArray[0]);
        int month = Integer.valueOf(strArray[1]);
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month);
        Date endTime = now.getTime();
        now.add(Calendar.MONTH, -1);
        Date startTime = now.getTime();
        //查询列表结果
        List<Map<String, Object>> oilCardLiucunList = definedMapper
                .oilCardLiucunList(startTime, endTime);

        //统计结果
        Integer allOpenNum = 0;
        BigDecimal allOpenMoney = BigDecimal.ZERO;
        BigDecimal allCardMoney = BigDecimal.ZERO;
        BigDecimal allAccountMoney = BigDecimal.ZERO;
        for (Map<String, Object> map : oilCardLiucunList) {
            //开卡总数
            Object openNum = map.get("openNum");
            if (Objects.nonNull(openNum)) {
                allOpenNum += Integer.valueOf(String.valueOf(openNum));
            }
            //开卡总金额
            Object openMoney = map.get("openMoney");
            if (Objects.nonNull(openMoney)) {
                allOpenMoney = allOpenMoney
                        .add(new BigDecimal(String.valueOf(openMoney)));
            } else {
                map.put("openMoney", BigDecimal.ZERO);
            }
            //未使用总金额
            Object cardMoney = map.get("cardMoney");
            if (Objects.nonNull(cardMoney)) {
                allCardMoney = allCardMoney
                        .add(new BigDecimal(String.valueOf(cardMoney)));
            } else {
                map.put("cardMoney", BigDecimal.ZERO);
            }
            //账户总金额
            Object accountMoney = map.get("accountMoney");
            if (Objects.nonNull(accountMoney)) {
                allAccountMoney = allAccountMoney
                        .add(new BigDecimal(String.valueOf(accountMoney)));
            } else {
                map.put("accountMoney", BigDecimal.ZERO);
            }

            //使用若为空
            Object useNum = map.get("useNum");
            if (Objects.isNull(useNum)) {
                map.put("useNum", BigDecimal.ZERO);
            }
            //使用若为空
            Object useMoney = map.get("useMoney");
            if (Objects.isNull(useMoney)) {
                map.put("useMoney", BigDecimal.ZERO);
            }
            //供应商若为空
            Object supplier = map.get("supplier");
            if (Objects.isNull(supplier)) {
                map.put("supplier", "0");
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("allOpenNum", allOpenNum);
        resultMap.put("allOpenMoney", allOpenMoney);
        resultMap.put("allCardMoney", allCardMoney);
        resultMap.put("allAccountMoney", allAccountMoney);
        DatasetSimple<Map<String, Object>> map = DatasetBuilder
                .fromDataSimple(resultMap);
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilCardLiucunList.size());
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (int i = page.getStartRow(); i < oilCardLiucunList.size(); i++) {
            if (i == (page.getStartRow() + page.getSize())) {
                break;
            }
            resultList.add(oilCardLiucunList.get(i));
        }
        DatasetList<Map<String, Object>> results = DatasetBuilder
                .fromDataList(resultList, page.createPageInfo());
        results.putDataset("resultMap", map);
        return results;
    }

    @Override
    @Read
    public DatasetList<Map<String, String>> exportOilCardLiucun(String date,
            String supplier, String address, String money) {
        if (StringUtils.isEmpty(date)) {
            throw new BusinessException("开通日期不能为空");
        }
        String[] strArray = date.split("-");
        int year = Integer.valueOf(strArray[0]);
        int month = Integer.valueOf(strArray[1]);
        Integer day = null;
        if (strArray.length == 3) {
            day = Integer.valueOf(strArray[2]);
            if (StringUtils.isEmpty(supplier)) {
                throw new BusinessException("开通提供者不能为空");
            }
            if (StringUtils.isEmpty(address)) {
                throw new BusinessException("开通城市不能为空");
            }
            if (StringUtils.isEmpty(money)) {
                throw new BusinessException("开通面额不能为空");
            }
        }

        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        if (Objects.isNull(day)) {
            now.set(Calendar.YEAR, year);
            now.set(Calendar.MONTH, month);
        } else {
            now.set(Calendar.YEAR, year);
            now.set(Calendar.MONTH, (month - 1));
            now.set(Calendar.DAY_OF_MONTH, day + 1);
        }
        Date endTime = now.getTime();
        if (Objects.isNull(day)) {
            now.add(Calendar.MONTH, -1);
        } else {
            now.add(Calendar.DAY_OF_MONTH, -1);
        }
        Date startTime = now.getTime();

        List<Map<String, Object>> exportOilCardLiucun = definedMapper
                .exportOilCardLiucun(startTime, endTime, supplier, address,
                        money);
        if (exportOilCardLiucun.isEmpty()) {
            throw new BusinessException("无数据");
        }
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        for (Map<String, Object> map : exportOilCardLiucun) {
            Map<String, String> result = new HashMap<String, String>();
            result.put("openTime", String.valueOf(map.get("openTime")));
            result.put("cardNo", String.valueOf(map.get("cardNo")));
            String provider = String.valueOf(map.get("provider"));
            if (Objects.isNull(map.get("provider"))) {
                result.put("provider", "北京亿特诺美");
            } else if ("0".equals(provider)) {
                result.put("provider", "北京亿特诺美");
            } else if ("1".equals(provider)) {
                result.put("provider", "上海腾霜");
            } else if ("2".equals(provider)) {
                result.put("provider", "上海腾姆");
            } else if ("3".equals(provider)) {
                result.put("provider", "上海泰奎");
            } else if ("4".equals(provider)) {
                result.put("provider", "上海泰钭");
            } else {
                result.put("provider", "未知供应商");
            }
            address = String.valueOf(map.get("address"));
            if (Objects.isNull(map.get("address"))) {
                result.put("address", "未知开通城市");
            } else {
                result.put("address", address);
            }
            result.put("content", String.valueOf(map.get("content")));

            if (Objects.isNull(map.get("cardMoney"))) {
                result.put("cardMoney", "未充值");
            } else {
                result.put("cardMoney", String.valueOf(map.get("cardMoney")));
            }
            if (Objects.isNull(map.get("useTime"))) {
                result.put("useTime", "未使用时间");
            } else {
                result.put("useTime", String.valueOf(map.get("useTime")));
            }
            if (Objects.isNull(map.get("useMoney"))) {
                result.put("useMoney", "未使用");
            } else {
                result.put("useMoney", String.valueOf(map.get("useMoney")));
            }
            resultList.add(result);
        }
        return DatasetBuilder.fromDataList(resultList);
    }

    @Override
    @Transactional
    @Write
    public DatasetSimple<String> chargeFlow(Long userId, String phone,
            String goodId, String perValue, String userIp) {

        //1.0判断被充值账户不能为空
        if (StringUtils.isEmpty(goodId)) {
            Log4jHelper.getLogger().error("手机流量充值：商品id不能为空!");
            throw new BusinessException("商品id不能为空!");
        }

        //2.0判断充值金额不能为空
        if (StringUtils.isEmpty(perValue)) {
            Log4jHelper.getLogger().error("手机流量充值：流量商品面额不能为空");
            throw new BusinessException("流量商品面额不能为空");
        }

        //3.0判断被充值账户不能为空
        if (StringUtils.isEmpty(phone)) {
            Log4jHelper.getLogger().error("手机流量充值：被充值手机号不能为空!");
            throw new BusinessException("被充值手机号不能为空!");
        }

        //4.0 每天消费金额限制
        try {
            compareOrderMoney(userId);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("手机流量充值：白名单限制异常" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        //5.0 检验面额是否正确
        OilGoods oilGoods = null;
        try {
            oilGoods = checkOilGood(goodId, perValue);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("流量充值：检验流量面额失败" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        //6.0 联通手机号笔数限制（同一号码同一档位限充5次/月）
        String flowValue = oilGoods.getDenomination();
        try {
            flowSpecialCheck(oilGoods.getTypeId(), phone, flowValue);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("流量充值：" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        //油卡用户
        OilUser user = definedMapper.queryOilUserbyId(userId);

        //7.0特殊账号不能充值流量
        List<String> phoneList = getPhoneList();
        if (phoneList.contains(user.getPhone())) {
            Log4jHelper.getLogger().error("账户手机号不能进行流量充值");
            throw new BusinessException("该手机号不能进行流量充值");
        }

        //8.0 下单前,扣减用户账户余额
        String orderNo = RandomUtil.getOrderSn();
        String userMoney = null;
        try {
            userMoney = changeUserMoney(user, perValue, orderNo, "4");
        } catch (Exception e) {
            Log4jHelper.getLogger().error("充值流量扣减账户余额失败" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (StringUtils.isEmpty(userMoney)) {
            Log4jHelper.getLogger().error("充值流量扣减账户余额失败,返回账户余额为空");
            throw new BusinessException("充值流量扣减账户余额失败");
        }
        //9.0查询欧飞当前正在使用的账户
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andStatusEqualTo(OilProvider.STATUS_1);
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);
        if (oilProviderList.size() != 1) {
            Log4jHelper.getLogger().error("查询流量充值所需的欧飞充值账异常");
            throw new BusinessException("充值流量，所需的充值账户查询异常，请联系客服修改");
        }
        OilProvider oilProvider = oilProviderList.get(0);
        //10.0 发送欧飞接口
        OufeiOrder oufeiOrder = null;

        try {
            oufeiOrder = ofFlowCharge(phone, flowValue, perValue, orderNo,
                    oilProvider);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("流量充值异常：" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        //11.0创建主订单
        OilOrders oilOrders = new OilOrders();
        oilOrders.setUserId(userId);//用户id
        oilOrders.setOrderNo(orderNo);//父订单号
        oilOrders.setChargeType(OilOrders.CHARGE_TYPE_4);//油卡类型
        oilOrders.setCardNo(phone);//油卡卡号
        oilOrders.setFillMoney(new BigDecimal(perValue));//充值金额
        oilOrders.setSupplier(0);//欧飞订单
        oilOrders.setSurplusMoney(new BigDecimal(userMoney));//剩余金额
        oilOrders.setUserIp(userIp);//登陆ip
        oilOrders.setCreateTime(new Date());//创建时间
        oilOrders.setUpdateTime(new Date());//更新时间
        oilOrders.setProvider(oilProvider.getId());//欧飞接口
        oilOrders.setCardSecret(flowValue);//流量面值
        if ("1".equals(oufeiOrder.getRetcode())) {
            oilOrders.setProviderOrderNo(oufeiOrder.getOrderid());//欧飞订单号
            oilOrders.setShopMoney(new BigDecimal(oufeiOrder.getOrdercash()));//欧飞价格
            oilOrders.setGoodId(oufeiOrder.getCardid());//欧飞产品编号
            oilOrders.setGoodIntroduce(oufeiOrder.getCardname());//欧飞商品介绍
            if ("0".equals(oufeiOrder.getGame_state())) {//订单状态
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_1);
            } else if ("1".equals(oufeiOrder.getGame_state())) {
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_2);
                oilOrders.setFinishMoney(new BigDecimal(perValue));
                oilOrders.setFinishTime(new Date());
            } else if ("9".equals(oufeiOrder.getGame_state())) {
                oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_4);
                oilOrders.setFinishMoney(BigDecimal.ZERO);
                oilOrders.setFinishTime(new Date());
            } else {
                Log4jHelper.getLogger()
                        .error("流量充值：返回结果状态参数异常" + oufeiOrder.getGame_state());
                throw new BusinessException("返回结果参数异常，请联系客服！！");
            }
        } else {
            oilOrders.setProviderOrderNo(null);//欧飞订单号
            oilOrders.setShopMoney(null);//欧飞价格
            oilOrders.setGoodId(null);//欧飞产品编号
            oilOrders.setGoodIntroduce("未知错误，需人工核实");//欧飞商品介绍
            oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_1);
        }

        if (oilOrdersMapper.insertSelective(oilOrders) != 1) {
            Log4jHelper.getLogger().error("流量充值：添加订单失败");
            throw new BusinessException("添加订单记录失败");
        }
        return DatasetBuilder.fromDataSimple(orderNo);
    }

    /**
     * 中石化校验面额
     * @param goodId
     * @param cardNo
     * @return
     */
    private void checkOilCardNo(String goodId, String cardNo) {
    	String str1 = cardNo.substring(0, 8);
    	String str2 = cardNo.substring(0, 10);
    	ArrayList<String> cardNoList = new ArrayList<String>();
    	cardNoList.add(str1);
    	cardNoList.add(str2);
    	OilGoodsAddressExample oilGoodsAddressEx = new OilGoodsAddressExample();
    	oilGoodsAddressEx.createCriteria()
    		.andGoodIdEqualTo(Long.valueOf(goodId))
    		.andOilCardCodeIn(cardNoList)
    		.andStatusEqualTo(OilGoodsAddress.STATUS_1);
    	List<OilGoodsAddress> oilGoodsAddressList = oilGoodsAddressMapper
    			.selectByExample(oilGoodsAddressEx);
    	if(!oilGoodsAddressList.isEmpty()){
    		throw new BusinessException("您所在地区该额度的油卡正在系统维护中，请选择其他额度或稍后重试");
    	}
    }
    
    public static void main(String[] args) {
		String cardNo = "1000111234567890123";
		String substring = cardNo.substring(0, 10);
		System.out.println(substring);
	}
    /**
     * 根据id查询追电商品信息
     * @param proId
     * @param flowValue
     */
    private OilGoods checkOilGood(String goodId, String money) {
        OilGoods oilGoods = oilGoodsMapper
                .selectByPrimaryKey(Long.valueOf(goodId));

        if (Objects.isNull(oilGoods)) {
            throw new BusinessException("未查询到该商品，请联系客服");
        }
        if (new BigDecimal(money).compareTo(oilGoods.getSale()) != 0) {
            throw new BusinessException("商品面值不符，请联系客服");
        }
        if (2 == oilGoods.getOneStatus()) {
            throw new BusinessException("系统维护中，请稍后充值");
        }
        if (2 == oilGoods.getStatus()) {
            throw new BusinessException("该面额商品已下架，请选择其他面额的商品");
        }
        if (3 == oilGoods.getStatus()) {
            throw new BusinessException("该面额商品库存不足，正紧急备货，请稍后重试");
        }
        if (4 == oilGoods.getStatus()) {
            throw new BusinessException("系统维护中，请稍后充值");
        }
        
        return oilGoods;
    }

    /**
     * 流量充值：
     * @param typeId
     * @param phone
     * @param flowValue
     */
    private void flowSpecialCheck(Long typeId, String phone, String flowValue) {
        OilGoodsType oilGoodsType = oilGoodsTypeMapper
                .selectByPrimaryKey(typeId);

        if (oilGoodsType.getTypeName().contains("联通")) {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            //创建时间的起始
            Date start = calendar.getTime();
            //状态参数
            List<Byte> orderStatusList = new ArrayList<Byte>();
            orderStatusList.add(OilOrders.ORDER_STATUS_1);
            orderStatusList.add(OilOrders.ORDER_STATUS_2);
            OilOrdersExample oilOrdersEx = new OilOrdersExample();
            oilOrdersEx.createCriteria()
                    .andCreateTimeGreaterThanOrEqualTo(start)
                    .andOrderStatusIn(orderStatusList)
                    .andChargeTypeEqualTo(OilOrders.CHARGE_TYPE_4)
                    .andCardNoEqualTo(phone).andCardSecretEqualTo(flowValue);
            int count = oilOrdersMapper.countByExample(oilOrdersEx);
            if (count >= 5) {
                throw new BusinessException("中国联通流量充值：同一号码同一档位限充5次/月");
            }
        }
    }

    /**
     * 流量充值接口
     * @param phone
     * @param flowValue
     * @param perValue
     * @param orderNo
     * @param oilProvider
     * @return
     */
    private OufeiOrder ofFlowCharge(String phone, String flowValue,
            String perValue, String orderNo, OilProvider oilProvider) {

        if (perValue.contains(".")) {
            perValue = perValue.substring(0, perValue.indexOf("."));
        }
        //1.0获取流量充值请求url
        String chargeFlowUrl = this.environment
                .getRequiredProperty("ofoil.flowcharge.url");
        //2.0组织流量充值接口请求参数
        Map<String, String> params = new HashMap<String, String>();
        String range = this.environment.getRequiredProperty("ofoil.range");
        String effectStartTime = this.environment
                .getRequiredProperty("ofoil.effectStartTime");
        String effectTime = this.environment
                .getRequiredProperty("ofoil.effectTime");
        String keyStr = this.environment.getRequiredProperty("ofoil.key");
        String version = this.environment.getRequiredProperty("ofoil.version");
        String retUrl = this.environment
                .getRequiredProperty("ofoil.callback.url");
        params.put("userid", oilProvider.getUserName());//用户名
        params.put("userpws", oilProvider.getUserPassword());//用户密码
        params.put("phoneno", phone);//被充值手机号
        params.put("perValue", perValue);//充值金额
        params.put("sporderId", orderNo);//Sp商家的订单号(商户传给欧飞的唯一编号)
        params.put("flowValue", flowValue);//充值面额
        params.put("range", range);//使用范围
        params.put("effectStartTime", effectStartTime);//起始生效时间
        params.put("effectTime", effectTime);//生效时间
        params.put("keyStr", keyStr);//签名串
        params.put("retUrl", retUrl);//订单充值有结果回调的URL地址，可为空（不参与MD5验算）
        params.put("version", version);//固定值

        //3.0组织下单日志参数
        OilLog oilLog = new OilLog();
        oilLog.setCreateTime(new Date());
        oilLog.setUpdateTime(new Date());
        oilLog.setOrderType(OilLog.ORDER_TYPE_5);
        oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_0);
        oilLog.setOrderNo(orderNo);
        //4.0调用话费下单接口

        Log4jHelper.getLogger().info("欧飞流量充值接口开始调用~~");
        OufeiOrder oufeiOrder = null;
        try {
            oufeiOrder = OufeiOilUtil.chargeFlowByOf(chargeFlowUrl, params,
                    oilLog);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("欧飞流量充值接口开始调用异常~~" + e.getMessage());
            oilLogMapper.insertSelective(oilLog);
            throw new BusinessException("流量充值接口开始调用异常");
        }
        //5.0添加下的那记录
        oilLogMapper.insertSelective(oilLog);
        Log4jHelper.getLogger()
                .info("欧飞流量充值接口响应:" + JacksonHelper.toJSON(oufeiOrder));
        //下单成功
        if ("1".equals(oufeiOrder.getRetcode())
                || "1043".equals(oufeiOrder.getRetcode())
                || "334".equals(oufeiOrder.getRetcode())
                || "105".equals(oufeiOrder.getRetcode())
                || "9999".equals(oufeiOrder.getRetcode())) {
            return oufeiOrder;
            //下单失败
        } else {
            Log4jHelper.getLogger().error("欧飞流量充值接口调用失败："
                    + OufeiOilUtil.getMsg(oufeiOrder.getRetcode()));
            throw new BusinessException("流量充值接口调用异常:"
                    + OufeiOilUtil.getMsg(oufeiOrder.getRetcode()) + ";请联系客服");
        }
    }

    @Override
    @Transactional
    @Write
    public DatasetSimple<Map<String, String>> getCardPassword(Long userId,
            String goodId, String money, String userIp, String goodType) {

        //1.0判断被充值账户不能为空
        if (StringUtils.isEmpty(goodId)) {
            Log4jHelper.getLogger().error("获取卡密充值：商品id不能为空!");
            throw new BusinessException("商品id不能为空!");
        }

        //2.0判断充值金额不能为空
        if (StringUtils.isEmpty(money)) {
            Log4jHelper.getLogger().error("获取卡密充值：充值面额不能为空");
            throw new BusinessException("充值面额不能为空");
        }

        //3.0 非特殊账号需要进行每天消费金额限制
        /*try {
            compareOrderMoney(userId);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("购买会员卡和优惠券：白名单限制异常" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }*/

        //4.0 检验面额是否正确
        OilGoods oilGoods = null;
        try {
            oilGoods = checkOilGood(goodId, money);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("购买会员卡和优惠券接口" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (Objects.isNull(oilGoods)) {
            Log4jHelper.getLogger().error("购买会员卡和优惠券接口" + goodId);
            throw new BusinessException("商品不存在，请联系客服");
        }

        //油卡用户
        OilUser user = definedMapper.queryOilUserbyId(userId);
        //特殊账号不能充值会员卡和购买优惠券
        List<String> phoneList = getPhoneList();
        if (phoneList.contains(user.getPhone())) {
            throw new BusinessException("该账户不能进行购买购买会员卡和优惠券");
        }
        //5.0 下单前,扣减用户账户余额
        String orderNo = RandomUtil.getOrderSn();
        String userMoney = null;
        try {
            userMoney = changeUserMoney(user, money, orderNo, "5");
        } catch (Exception e) {
            Log4jHelper.getLogger().error("购买优惠券或视频会员卡" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        if (StringUtils.isEmpty(userMoney)) {
            Log4jHelper.getLogger().error("购买优惠券或视频会员卡扣减账户余额失败,返回账户余额为空");
            throw new BusinessException("购买优惠券或视频会员卡扣减账户余额失败");
        }

        //6.0查询欧飞当前正在使用的账户
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andStatusEqualTo(OilProvider.STATUS_1);
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);
        if (oilProviderList.size() != 1) {
            Log4jHelper.getLogger().error("购买视频会员卡或优惠券所需的欧飞充值账户查询异常");
            throw new BusinessException("购买视频会员卡或优惠券，所需的充值账户查询异常，请联系客服修改");
        }
        OilProvider oilProvider = oilProviderList.get(0);
        //7.0 发送欧飞接口
        OufeiCardPassword oufeiCardPassword = null;
        try {
            oufeiCardPassword = ofGetCardpwd(user.getPhone(), orderNo,
                    oilGoods.getProductId(), oilProvider);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("购买会员卡和优惠券异常：" + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        //8.0创建主订单
        OilOrders oilOrders = new OilOrders();
        oilOrders.setUserId(userId);//用户id
        oilOrders.setOrderNo(orderNo);//父订单号
        if ("5".equals(goodType)) {
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_5);
        } else if ("6".equals(goodType)) {
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_6);
        } else if ("7".equals(goodType)) {
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_7);
        } else if ("8".equals(goodType)) {
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_8);
        } else if ("9".equals(goodType)) {
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_9);
        } else if ("10".equals(goodType)) {
            oilOrders.setChargeType(OilOrders.CHARGE_TYPE_10);
        }
        String cardno = oufeiCardPassword.getCards().getCards().get(0)
                .getCardno();
        oilOrders.setCardNo(cardno);//卡号
        oilOrders.setFillMoney(new BigDecimal(money));//充值金额
        oilOrders.setSupplier(0);//欧飞订单
        oilOrders.setSurplusMoney(new BigDecimal(userMoney));//剩余金额
        oilOrders.setUserIp(userIp);//登陆ip
        oilOrders.setCreateTime(new Date());//创建时间
        oilOrders.setUpdateTime(new Date());//更新时间
        oilOrders.setProviderOrderNo(oufeiCardPassword.getOrderid());//欧飞订单号
        oilOrders
                .setShopMoney(new BigDecimal(oufeiCardPassword.getOrdercash()));//欧飞价格
        oilOrders.setGoodId(oufeiCardPassword.getCardid());//欧飞产品编号
        oilOrders.setGoodIntroduce(oufeiCardPassword.getCardname());//欧飞商品介绍
        oilOrders.setProvider(oilProvider.getId());//欧飞接口
        String cardpws = oufeiCardPassword.getCards().getCards().get(0)
                .getCardpws();
        oilOrders.setCardSecret(cardpws);//流量面值
        String expiretime = oufeiCardPassword.getCards().getCards().get(0)
                .getExpiretime();
        oilOrders.setExpireTime(expiretime);
        oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_2);
        oilOrders.setFinishMoney(new BigDecimal(money));
        oilOrders.setFinishTime(new Date());
        if (oilOrdersMapper.insertSelective(oilOrders) != 1) {
            Log4jHelper.getLogger().error("卡密充值：添加订单失败");
            throw new BusinessException("添加订单记录失败");
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("orderNo", orderNo);
        result.put("cardSecret", cardpws);
        return DatasetBuilder.fromDataSimple(result);
    }

    /**
     * 获取会员卡券，优惠券的订单详情信息
     */
    @Read
    @Override
    public DatasetSimple<Map<String, Object>> getCardInfo(String orderNo) {
        OilOrdersExample example = new OilOrdersExample();
        example.or().andOrderNoEqualTo(orderNo);
        List<OilOrders> oilOrders = oilOrdersMapper.selectByExample(example);
        if (oilOrders.size() > 1) {
            throw new BusinessException("查询到重复的订单信息");
        }
        if (Objects.isNull(oilOrders.get(0))) {
            throw new BusinessException("订单信息查询失败");
        }
        OilOrders orders = oilOrders.get(0);
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("chargeType", orders.getChargeType());//类型
        results.put("finishMoney", orders.getFinishMoney());//价格
        results.put("cardSecret", orders.getCardSecret());//兑换码
        results.put("goodIntroduce", orders.getGoodIntroduce());//商品名称
        results.put("orderNo", orders.getOrderNo());//订单号
        results.put("finishTime", orders.getFinishTime());//下单时间
        //使用有效期
        if (orders.getChargeType() == 5) {
            results.put("cardNo", orders.getCardNo());
            results.put("usefulLife", "请在一年之内完成绑定，绑定后请在七天之内使用，过期作废");
        } else if (orders.getChargeType() == 6) {
            String goodsName = orders.getGoodIntroduce();
            if (goodsName.contains("专车") || goodsName.contains("快车")) {
                if (goodsName.contains("10元") || goodsName.contains("20元")) {
                    results.put("usefulLife", "请在6个月之内使用，过期作废");
                } else {
                    results.put("usefulLife", "请在一年之内使用，过期作废");
                }
            } else if (goodsName.contains("代驾")) {
                results.put("usefulLife", "请在三个月内使用，过期作废");
            } else {
                results.put("usefulLife", "请在一年之内使用，过期作废");
            }
            results.put("cardNo", orders.getCardNo());
        } else if (orders.getChargeType() == 7 || orders.getChargeType() == 8
                || orders.getChargeType() == 9
                || orders.getChargeType() == 10) {
            results.put("usefulLife", "请在三个月内使用，过期作废");
            results.put("cardNo", orders.getCardNo());
        }

        return DatasetBuilder.fromDataSimple(results);
    }

    /**
     * 获取卡密接口
     * @param phone
     * @param orderNo
     * @param cardid
     * @param oilProvider
     * @param goodType
     * @return
     */
    private OufeiCardPassword ofGetCardpwd(String phone, String orderNo,
            String cardid, OilProvider oilProvider) {
        //1.0获取流量充值请求url
        String chargeFlowUrl = this.environment
                .getRequiredProperty("ofoil.getcardpassword.url");
        //2.0组织流量充值接口请求参数
        Map<String, String> params = new HashMap<String, String>();
        String keyStr = this.environment.getRequiredProperty("ofoil.key");
        String version = this.environment.getRequiredProperty("ofoil.version");
        params.put("userid", oilProvider.getUserName());//用户名
        params.put("userpws", oilProvider.getUserPassword());//用户密码
        params.put("phone", phone);//接受卡密的手机号
        params.put("cardid", cardid);//欧飞产品编号
        params.put("cardnum", "1");//充值金额
        params.put("sporder_id", orderNo);//Sp商家的订单号(商户传给欧飞的唯一编号)
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("sporder_time", format.format(new Date()));//提交时间
        params.put("keyStr", keyStr);//签名串
        params.put("version", version);//固定值
        Log4jHelper.getLogger().info("欧飞视频会员卡或优惠券接口开始调用~~");
        //3.0组织下单日志参数
        OilLog oilLog = new OilLog();
        oilLog.setCreateTime(new Date());
        oilLog.setUpdateTime(new Date());
        oilLog.setOrderType(OilLog.ORDER_TYPE_6);
        oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_0);
        oilLog.setOrderNo(orderNo);
        //4.0调用话费下单接口
        OufeiCardPassword oufeiCardPassword = null;
        try {
            oufeiCardPassword = OufeiOilUtil.getCardPassWordByOf(chargeFlowUrl,
                    params, oilLog);
        } catch (Exception e) {
            Log4jHelper.getLogger().error("欧飞获取卡密接口开始调用异常~~" + e.getMessage());
            oilLogMapper.insertSelective(oilLog);
            throw new BusinessException("获取视频会员卡或优惠券接口开始调用异常");
        }
        //5.0添加下的那记录
        oilLogMapper.insertSelective(oilLog);
        Log4jHelper.getLogger()
                .info("欧飞获取卡密接口响应:" + JacksonHelper.toJSON(oufeiCardPassword));
        //下单成功
        if ("1".equals(oufeiCardPassword.getRetcode())
                || "1043".equals(oufeiCardPassword.getRetcode())
                || "334".equals(oufeiCardPassword.getRetcode())
                || "105".equals(oufeiCardPassword.getRetcode())
                || "9999".equals(oufeiCardPassword.getRetcode())) {
            return oufeiCardPassword;
            //下单失败
        } else {
            Log4jHelper.getLogger().error("欧飞获取卡密接口调用失败："
                    + OufeiOilUtil.getMsg(oufeiCardPassword.getRetcode()));
            throw new BusinessException("获取视频会员卡或优惠券接口调用异常:"
                    + OufeiOilUtil.getMsg(oufeiCardPassword.getRetcode())
                    + ";请联系客服");
        }
    }

    @Override
    @Transactional
    @Write
    public void ofBack(String ret_code, String err_msg, String sporder_id,
            String ordersuccesstime) {
        //若回调订单号为空报错
        if (StringUtils.isEmpty(sporder_id)) {
            Log4jHelper.getLogger().error("欧飞回调接口：订单号不能为空");
            throw new BusinessException("订单号不能为空");
        }
        //打印日志
        StringBuffer sb = new StringBuffer();
        sb.append("ret_code=" + ret_code).append("&err_msg=" + err_msg)
                .append("&sporder_id=" + sporder_id)
                .append("&ordersuccesstime=" + ordersuccesstime);
        Log4jHelper.getLogger().info("欧飞回调处理结果:" + sb.toString());
        OilLog oilLog = new OilLog();
        oilLog.setCreateTime(new Date());
        oilLog.setUpdateTime(new Date());
        oilLog.setInterfaceType(OilLog.INTERFACE_TYPE_1);
        oilLog.setRequestStr(sb.toString());
        oilLog.setOrderNo(sporder_id);
        //根据订单号查询话费订单信息
        List<Byte> orderTypeList = new ArrayList<Byte>();
        orderTypeList.add(OilOrders.CHARGE_TYPE_1);
        orderTypeList.add(OilOrders.CHARGE_TYPE_2);
        orderTypeList.add(OilOrders.CHARGE_TYPE_3);
        orderTypeList.add(OilOrders.CHARGE_TYPE_4);
        OilOrdersExample oilOrdersEx = new OilOrdersExample();
        oilOrdersEx.or().andOrderNoEqualTo(sporder_id)
                .andOrderStatusEqualTo(OilOrders.ORDER_STATUS_1)
                .andChargeTypeIn(orderTypeList);
        oilOrdersEx.setLimitStart(0);
        oilOrdersEx.setLimitEnd(1);
        List<OilOrders> oilOrdersList = oilOrdersMapper
                .selectByExample(oilOrdersEx);
        //若订单不存在
        if (oilOrdersList.isEmpty()) {
            Log4jHelper.getLogger().error("欧飞回调，根据订单号查询不到订单信息" + sporder_id);
            oilLog.setReturnResult("欧飞回调失败，订单不存在");
            oilLogMapper.insertSelective(oilLog);
            throw new BusinessException("欧飞回调:订单不存在");
        }
        OilOrders oilOrders = oilOrdersList.get(0);
        oilOrders = definedMapper.queryOilOrdersbyId(oilOrders.getId());
        if(!OilOrders.ORDER_STATUS_1.equals(oilOrders.getOrderStatus())){
        	Log4jHelper.getLogger().error("欧飞回调：订单已不是处理中");
        	throw new BusinessException("欧飞回调:订单不是处理中");
        }
        if (OilOrders.CHARGE_TYPE_1.equals(oilOrders.getChargeType())) {
            oilLog.setOrderType(OilLog.ORDER_TYPE_0);
        } else if (OilOrders.CHARGE_TYPE_2.equals(oilOrders.getChargeType())) {
            oilLog.setOrderType(OilLog.ORDER_TYPE_1);
        } else if (OilOrders.CHARGE_TYPE_3.equals(oilOrders.getChargeType())) {
            oilLog.setOrderType(OilLog.ORDER_TYPE_2);
        } else if (OilOrders.CHARGE_TYPE_4.equals(oilOrders.getChargeType())) {
            oilLog.setOrderType(OilLog.ORDER_TYPE_5);
        } else {
            oilLog.setOrderType(OilLog.ORDER_TYPE_6);
        }
        //订单充值成功
        if ("1".equals(ret_code)) {
            Log4jHelper.getLogger().info("欧飞回调结果:充值成功" + sporder_id);
            oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_2);
            ;//充值成功
            oilOrders.setFinishMoney(oilOrders.getFillMoney());
            ;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            if (StringUtils.isNotEmpty(ordersuccesstime)) {
                try {
                    Date finshiTime = format.parse(ordersuccesstime);
                    oilOrders.setFinishTime(finshiTime);
                } catch (ParseException e) {
                    Log4jHelper.getLogger()
                            .error("ordersuccesstime转化日期失败~~" + sporder_id);
                    e.printStackTrace();
                }
            } else {
                oilOrders.setFinishTime(new Date());
            }
            Date updateTime = oilOrders.getUpdateTime();
            oilOrders.setUpdateTime(new Date());
            //更新订单信息
            oilOrdersEx.clear();
            oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                    .andUpdateTimeEqualTo(updateTime);
            if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                    oilOrdersEx) != 1) {
                Log4jHelper.getLogger().error("更新订单信息失败" + sporder_id);
                throw new BusinessException("更新订单信息失败");
            }
            ;
            //添加回调记录
            oilLog.setReturnResult("充值成功");
            oilLogMapper.insertSelective(oilLog);
            //订单充值失败 
        } else if ("9".equals(ret_code)) {//话费充值失败
            Log4jHelper.getLogger().info("欧飞回调结果:充值失败" + sporder_id);
            oilOrders.setOrderStatus(OilOrders.ORDER_STATUS_4);
            //充值失败
            oilOrders.setFinishMoney(new BigDecimal("0"));
            oilOrders.setFinishTime(new Date());
            //查询欧飞话费订单接口，验证回调结果是否一致
            String orderstatus = queryOrderStatus(oilOrders.getProvider(),
                    sporder_id);
            if (!"9".equals(orderstatus)) {
                Log4jHelper.getLogger().error("欧飞订单回调的和查询的结果不一致" + sporder_id);
                throw new BusinessException("回调的和查询的订单结果不一致");
            }
            //用户金额
            OilUser oilUser = definedMapper
                    .queryOilUserbyId(oilOrders.getUserId());
            if (Objects.isNull(oilUser)) {
                Log4jHelper.getLogger().error("未能查询到下单用户信息" + sporder_id);
                throw new BusinessException("未能查询到下单用户信息");
            }
            BigDecimal surplusMoney = oilUser.getMoney()
                    .add(oilOrders.getFillMoney());
            oilOrders.setSurplusMoney(surplusMoney);
            Date updateTime = oilOrders.getUpdateTime();
            oilOrders.setUpdateTime(new Date());
            //更新订单信息
            oilOrdersEx.clear();
            oilOrdersEx.or().andIdEqualTo(oilOrders.getId())
                    .andUpdateTimeEqualTo(updateTime);
            if (oilOrdersMapper.updateByExampleSelective(oilOrders,
                    oilOrdersEx) != 1) {
                Log4jHelper.getLogger().error("更新订单信息失败" + sporder_id);
                throw new BusinessException("更新订单信息失败");
            }
            ;
            //给子账户和主账户添加金额
            try {
                addOilUserMoney(oilUser, oilOrders.getOrderNo(),
                        String.valueOf(surplusMoney.doubleValue()),
                        String.valueOf(oilOrders.getChargeType()));
            } catch (Exception e) {
                Log4jHelper.getLogger().error(e.getMessage() + sporder_id);
                throw new BusinessException(e.getMessage());
            }
            //添加回调记录
            oilLog.setReturnResult("充值失败");
            oilLogMapper.insertSelective(oilLog);
            //未知状态码   
        } else {
            Log4jHelper.getLogger().info("欧飞回调结果:未知状态码:" + ret_code);
            oilLog.setReturnResult("欧飞回调失败：未知状态码");
            oilLogMapper.insertSelective(oilLog);
            throw new BusinessException("invalid status");
        }
        Log4jHelper.getLogger().info("欧飞订单[{}]回调完成...", sporder_id);
    }

    /**
     * 根据商品类型查询追电商品信息
     */
    @Override
    @Read
    public DatasetList<OilGoods> queryOilGoods(String goodType, String mobile) {
        //goodType 0中石化油卡;1中石油油卡;2猫眼电影;3爱奇艺;4腾讯视频;5搜狐视频;6优酷视频;7话费充值;8流量充值
        if (StringUtils.isEmpty(goodType)) {
            throw new BusinessException("商品类型不能为空");
        }
        String operator = null;
        //若是话费和流量充值，先查询手机运营商
        if ("7".equals(goodType) || "8".equals(goodType)) {
            if (StringUtils.isEmpty(mobile)) {
                throw new BusinessException("手机充值，请先填写被手机号");
            }
            String mobinfoUrl = this.environment
                    .getProperty("ofoil.getmobinfo.url");
            try {
                String mobinfo = OufeiOilUtil.getMobinfoUrl(mobinfoUrl, mobile);
                String[] results = mobinfo.split("\\|");
                if (results.length != 3) {
                    throw new BusinessException("返回结果格式不对~~");
                }
                operator = results[2];
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(operator)) {
                throw new BusinessException("手机运营商查询失败");
            }
        }
        operator = "中国" + operator;
        OilGoodsExample oilGoodsEx = new OilGoodsExample();
        if ("0".equals(goodType)) {//中石化
            oilGoodsEx.createCriteria().andTypeIdEqualTo(6L);
        } else if ("1".equals(goodType)) {//中石油
            oilGoodsEx.createCriteria().andTypeIdEqualTo(7L);
        } else if ("2".equals(goodType)) {//猫眼电影
            oilGoodsEx.createCriteria().andTypeIdEqualTo(14L);
        } else if ("3".equals(goodType)) {//爱奇艺
            oilGoodsEx.createCriteria().andTypeIdEqualTo(17L);
        } else if ("4".equals(goodType)) {//腾讯视频
            oilGoodsEx.createCriteria().andTypeIdEqualTo(16L);
        } else if ("5".equals(goodType)) {//搜狐视频
            oilGoodsEx.createCriteria().andTypeIdEqualTo(19L);
        } else if ("6".equals(goodType)) {//优酷视频
            oilGoodsEx.createCriteria().andTypeIdEqualTo(18L);
        } else if ("7".equals(goodType)) {//话费充值
            OilGoodsTypeExample oilGoodsTypeEx = new OilGoodsTypeExample();
            oilGoodsTypeEx.createCriteria().andParentIdEqualTo(2L)
                    .andTypeNameEqualTo(operator);
            oilGoodsTypeEx.setLimitStart(0);
            oilGoodsTypeEx.setLimitEnd(1);
            List<OilGoodsType> oilGoodsTypes = oilGoodsTypeMapper
                    .selectByExample(oilGoodsTypeEx);
            if (oilGoodsTypes.isEmpty()) {
                throw new BusinessException("商品类型查询失败");
            }
            OilGoodsType oilGoodsType = oilGoodsTypes.get(0);
            oilGoodsEx.createCriteria().andTypeIdEqualTo(oilGoodsType.getId());
        } else if ("8".equals(goodType)) {//流量充值
            OilGoodsTypeExample oilGoodsTypeEx = new OilGoodsTypeExample();
            oilGoodsTypeEx.createCriteria().andParentIdEqualTo(3L)
                    .andTypeNameEqualTo(operator);
            oilGoodsTypeEx.setLimitStart(0);
            oilGoodsTypeEx.setLimitEnd(1);
            List<OilGoodsType> oilGoodsTypes = oilGoodsTypeMapper
                    .selectByExample(oilGoodsTypeEx);
            if (oilGoodsTypes.isEmpty()) {
                throw new BusinessException("商品类型查询失败");
            }
            OilGoodsType oilGoodsType = oilGoodsTypes.get(0);
            oilGoodsEx.createCriteria().andTypeIdEqualTo(oilGoodsType.getId());
        } else {
            throw new BusinessException("未知商品类型，请联系客服");
        }
        oilGoodsEx.setOrderByClause(OilGoodsExample.SALE_ASC);
        List<OilGoods> oilGoodsList = oilGoodsMapper
                .selectByExample(oilGoodsEx);
        DatasetList<OilGoods> result = DatasetBuilder
                .fromDataList(oilGoodsList);
        if ("7".equals(goodType) || "8".equals(goodType)) {
            result.putDataset("operator",
                    DatasetBuilder.fromDataSimple(operator));
        }
        return result;
    }

    @Override
    @Read
    public DatasetSimple<String> checkOilGood(String phone, String goodId,
            String perValue, String orderType) {

        //1.0查询欧飞当前正在使用的账户
        OilProviderExample oilProviderEx = new OilProviderExample();
        oilProviderEx.createCriteria().andStatusEqualTo(OilProvider.STATUS_1);
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderEx);
        if (oilProviderList.size() != 1) {
            Log4jHelper.getLogger().error("话费和流量充值：充值所需的充值账户查询异常");
            throw new BusinessException("充值所需的充值账户查询异常，请联系客服修改");
        }
        OilProvider oilProvider = oilProviderList.get(0);

        //2.0 查询追电商品是否上架
        OilGoods oilGoods = oilGoodsMapper
                .selectByPrimaryKey(Long.valueOf(goodId));
        if (Objects.isNull(oilGoods)) {
            Log4jHelper.getLogger().error("根据商品id未查询到追电商品");
            throw new BusinessException("根据商品id未查询到追电商品");
        }
        BigDecimal sale = oilGoods.getSale();
        if (new BigDecimal(perValue).compareTo(sale) != 0) {
            Log4jHelper.getLogger().error("商品售价不符，请刷新页面重试");
            throw new BusinessException("商品售价不符，请刷新页面重试");
        }
        Integer oneStatus = oilGoods.getOneStatus();
        if (2 == oneStatus) {
            Log4jHelper.getLogger().info("追电商品系统维护，请稍后重试");
            throw new BusinessException("追电商品系统维护，请稍后重试");
        } else {
            Integer status = oilGoods.getStatus();
            if (2 == status) {
                Log4jHelper.getLogger().info("追电商品已下架");
                throw new BusinessException("追电商品已下架");
            } else if (3 == status) {
                Log4jHelper.getLogger().info("追电商品库存不足");
                throw new BusinessException("追电商品库存不足，请联系客服");
            } else if (4 == status) {
                Log4jHelper.getLogger().info("追电商品系统维护，请稍后重试");
                throw new BusinessException("追电商品系统维护，请稍后重试");
            }
        }

        //3.0 查询欧飞商品是否存在
        String version = this.environment.getRequiredProperty("ofoil.version");
        //话费充值商品查询
        if (perValue.contains(".")) {
            perValue = perValue.substring(0, perValue.indexOf("."));
        }
        if ("0".equals(orderType)) {
            String telQueryUrl = this.environment
                    .getProperty("ofoil.telquery.url");
            Map<String, String> params = new HashMap<String, String>();
            params.put("userid", oilProvider.getUserName());
            params.put("userpws", oilProvider.getUserPassword());
            params.put("phoneno", phone);
            params.put("pervalue", perValue);
            params.put("version", version);
            OufeiPhoneGood oufeiPhoneGood = null;
            try {
                oufeiPhoneGood = OufeiOilUtil.queryPhoneGoodByOf(telQueryUrl,
                        params);
            } catch (Exception e) {
                Log4jHelper.getLogger().error("查询话费商品信息失败" + e.getMessage());
                throw new BusinessException("查询话费商品信息失败");
            }
            if (Objects.isNull(oufeiPhoneGood)) {
                Log4jHelper.getLogger().error("查询话费商品信息失败");
                throw new BusinessException("查询话费商品信息失败");
            }
            String retcode = oufeiPhoneGood.getRetcode();
            if (!"1".equals(retcode)) {
                Log4jHelper.getLogger()
                        .error(retcode + "_" + oufeiPhoneGood.getErr_msg());
                throw new BusinessException(
                        retcode + "_" + oufeiPhoneGood.getErr_msg());
            }
            //流量充值商品查询
        } else if ("1".equals(orderType)) {
            String flowCheckUrl = this.environment
                    .getProperty("ofoil.flowcheck.url");
            String range = this.environment.getRequiredProperty("ofoil.range");
            String effectStartTime = this.environment
                    .getRequiredProperty("ofoil.effectStartTime");
            String effectTime = this.environment
                    .getRequiredProperty("ofoil.effectTime");
            Map<String, String> params = new HashMap<String, String>();
            params.put("userid", oilProvider.getUserName());
            params.put("userpws", oilProvider.getUserPassword());
            params.put("phoneno", phone);
            params.put("perValue", perValue);
            params.put("flowValue", oilGoods.getDenomination());
            params.put("range", range);
            params.put("effectStartTime", effectStartTime);
            params.put("effectTime", effectTime);
            params.put("version", version);
            OufeiFlowGood oufeiFlowGood = null;
            try {
                oufeiFlowGood = OufeiOilUtil.queryFlowGoodByOf(flowCheckUrl,
                        params);
            } catch (Exception e) {
                Log4jHelper.getLogger().error("查询流量商品信息失败" + e.getMessage());
                throw new BusinessException("查询流量商品信息失败");
            }
            if (Objects.isNull(oufeiFlowGood)) {
                Log4jHelper.getLogger().error("查询流量商品信息失败");
                throw new BusinessException("查询流量商品信息失败");
            }
            String retcode = oufeiFlowGood.getRetcode();
            if (!"1".equals(retcode)) {
                Log4jHelper.getLogger()
                        .error(retcode + "_" + oufeiFlowGood.getErr_msg());
                throw new BusinessException(
                        retcode + "_" + oufeiFlowGood.getErr_msg());
            }
        } else {
            Log4jHelper.getLogger().error("未知商品类型" + orderType);
            throw new BusinessException("未知商品类型");
        }
        return DatasetBuilder.fromDataSimple("SUCCESS");
    }

    /**
     * 根据商品类型查询追电商品信息
     */
    @Override
    @Read
    public DatasetList<OilGoodsType> queryVideoCard() {
        OilGoodsTypeExample oilGoodsTypeEx = new OilGoodsTypeExample();
        oilGoodsTypeEx.createCriteria().andParentIdEqualTo(5L);

        List<OilGoodsType> oilGoodsTypeList = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeEx);

        return DatasetBuilder.fromDataList(oilGoodsTypeList);
    }

    @Override
    @Read
    public DatasetList<Map<String, Object>> queryDidiGood() {
        OilGoodsTypeExample oilGoodsTypeEx = new OilGoodsTypeExample();
        oilGoodsTypeEx.createCriteria().andParentIdEqualTo(15L);

        List<OilGoodsType> oilGoodsTypeList = oilGoodsTypeMapper
                .selectByExample(oilGoodsTypeEx);
        List<Long> typeIds = oilGoodsTypeList.stream().map(OilGoodsType::getId)
                .distinct().collect(Collectors.toList());
        OilGoodsExample oilGoodsEx = new OilGoodsExample();
        oilGoodsEx.createCriteria().andTypeIdIn(typeIds);
        List<OilGoods> oilGoodsList = oilGoodsMapper
                .selectByExample(oilGoodsEx);
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        for (OilGoodsType oilGoodsType : oilGoodsTypeList) {
            Map<String, Object> result = new HashMap<String, Object>();
            Long typeId = oilGoodsType.getId();
            result.put("typeId", typeId);
            result.put("typeName", oilGoodsType.getTypeName());
            List<OilGoods> arrayList = new ArrayList<OilGoods>();
            for (OilGoods oilGoods : oilGoodsList) {
                if (typeId.equals(oilGoods.getTypeId())) {
                    arrayList.add(oilGoods);
                }
            }
            result.put("oilGoods", arrayList);
            if (arrayList.isEmpty()) {
                continue;
            }
            results.add(result);
        }
        return DatasetBuilder.fromDataList(results);
    }

    /**
     * 订单下拉列表
     */
    @Override
    public DatasetList<OilGoodsTypeVo> queryOilGoodsTypeList() {
        OilGoodsTypeExample example = new OilGoodsTypeExample();
        List<OilGoodsTypeVo> list1 = new ArrayList<OilGoodsTypeVo>();
        List<Long> longs = new ArrayList<Long>();
        longs.add(1L);
        longs.add(2L);
        example.createCriteria().andTypeGradeEqualTo(OilGoodsType.TYPE_GRADE_1)
                .andIdIn(longs);
        List<OilGoodsType> selectByExample = oilGoodsTypeMapper
                .selectByExample(example);

        selectByExample.stream().forEach(oilGoodsType -> {
            OilGoodsTypeVo otv = new OilGoodsTypeVo();
            String typeName = oilGoodsType.getTypeName();
            if (typeName.equals("油卡")) {
                oilGoodsType.setTypeName("油卡充值");
                otv.setOilGoodsType(oilGoodsType);
                otv.setOrderType("0");
                list1.add(otv);
            } else {
                if (typeName.equals("话费")) {
                    oilGoodsType.setTypeName("手机充值");
                    otv.setOilGoodsType(oilGoodsType);
                    otv.setOrderType("200");
                    list1.add(otv);
                }
            }
        });

        example.clear();
        example.createCriteria().andTypeGradeEqualTo(OilGoodsType.TYPE_GRADE_2)
                .andParentIdEqualTo(4L);
        List<OilGoodsType> list = oilGoodsTypeMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("没有订单列表信息");
        }
        list.stream().forEach(oilGoodsType -> {
            OilGoodsTypeVo otv = new OilGoodsTypeVo();
            otv.setOilGoodsType(oilGoodsType);
            String typeName = oilGoodsType.getTypeName();
            if (typeName.equals("猫眼电影")) {
                otv.setOrderType(OilOrders.CHARGE_TYPE_5.toString());
            } else {
                otv.setOrderType(OilOrders.CHARGE_TYPE_6.toString());
            }
            list1.add(otv);
        });

        OilGoodsType oilGoodsType = this.oilGoodsTypeMapper
                .selectByPrimaryKey(5L);
        if (Objects.nonNull(oilGoodsType)) {
            if (oilGoodsType.getTypeName().equals("视频会员卡")) {
                OilGoodsTypeVo otv = new OilGoodsTypeVo();
                otv.setOilGoodsType(oilGoodsType);
                otv.setOrderType("100");
                list1.add(otv);
            }
        }

        return DatasetBuilder.fromDataList(list1);
    }

}
