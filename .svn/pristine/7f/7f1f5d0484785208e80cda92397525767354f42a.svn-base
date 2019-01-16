package com.emate.shop.business.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.OilRecordWhitelistService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.OilRecordwhiteList;
import com.emate.shop.business.model.OilRecordwhiteListExample;
import com.emate.shop.business.model.OilRecordwhiteListExample.Criteria;
import com.emate.shop.business.model.OilUser;
import com.emate.shop.business.model.OilUserExample;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.OilOrdersMapper;
import com.emate.shop.mapper.OilRecordwhiteListMapper;
import com.emate.shop.mapper.OilUserMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 
 * @file OilRecordWhitelistServiceImpl.java
 * @author tangweiming
 * @mail weiming.tang@emateglobal.com
 * @since 2018年5月14日
 */
@Service
public class OilRecordWhitelistServiceImpl
        implements OilRecordWhitelistService {

    @Resource
    private OilRecordwhiteListMapper oilRecordwhiteListMapper;

    @Resource
    private OilUserMapper            oilUserMapper;

    @Resource
    private OilOrdersMapper          oilOrdersMapper;


    /**
     * 白名单设置
     */
    @Override
    @Transactional
    public DatasetSimple<Boolean> addOilRecordWhiteList(String userAccount,
            BigDecimal openQuota, String remark) {
        OilUserExample oilUserExample = new OilUserExample();
        oilUserExample.createCriteria().andPhoneEqualTo(userAccount);
        List<OilUser> oilUsers = this.oilUserMapper
                .selectByExample(oilUserExample);
        if (oilUsers.isEmpty()) {
            throw new BusinessException("该用户不存在");
        }
        //每天限制一万
        Long userId = oilUsers.get(0).getId();
        OilRecordwhiteListExample example = new OilRecordwhiteListExample();
        Criteria criteria = example.createCriteria();
        criteria.andUserAccountEqualTo(userAccount);
        List<OilRecordwhiteList> list = this.oilRecordwhiteListMapper
                .selectByExample(example);
        OilRecordwhiteList oilRecordwhite = null;
        if (!list.isEmpty()) {
            BigDecimal openQuota2 = list.get(0).getOpenQuota();
            if (openQuota2.compareTo(BigDecimal.ONE) == 0
                    && list.get(0).getStatus() == 2) {
                oilRecordwhite = new OilRecordwhiteList();
                oilRecordwhite.setOpenQuota(openQuota);
                oilRecordwhite.setStatus(OilRecordwhiteList.STATUS_1);
                if (StringUtils.isNotBlank(remark)) {
                    oilRecordwhite.setRemark(remark);
                } else {
                    oilRecordwhite.setRemark("");
                }
                oilRecordwhite.setUpdateTime(new Date());
                if (oilRecordwhiteListMapper.updateByExampleSelective(
                        oilRecordwhite, example) == 1) {
                    return DatasetBuilder.fromDataSimple(true);
                }
                throw new BusinessException("修改白名单信息失败！");
            } else {
                throw new BusinessException("请先恢复额度！");
            }
        }
        oilRecordwhite = new OilRecordwhiteList();
        oilRecordwhite.setUserId(userId);
        oilRecordwhite.setUserAccount(userAccount);
        oilRecordwhite.setOpenQuota(openQuota);
        oilRecordwhite.setStatus(OilRecordwhiteList.STATUS_1);
        oilRecordwhite.setRemark(remark);
        oilRecordwhite.setCreateTime(new Date());
        oilRecordwhite.setUpdateTime(new Date());
        if (oilRecordwhiteListMapper.insertSelective(oilRecordwhite) == 1) {
            return DatasetBuilder.fromDataSimple(true);
        }
        throw new BusinessException("添加白名单设置信息失败！");
    }

    @Override
    public DatasetList<OilRecordwhiteList> queryOilRecordWhitelist(
            String userAccount, Integer pageNo, Integer pageSize) {
        //若存在账户参数,需判断手机号是否存在
        if (StringUtils.isNotEmpty(userAccount)) {
            OilUserExample oilUserExample = new OilUserExample();
            oilUserExample.or().andPhoneEqualTo(userAccount);
            List<OilUser> oilUsers = oilUserMapper
                    .selectByExample(oilUserExample);
            if (oilUsers.isEmpty()) {
                throw new BusinessException("该用户不存在");
            }
        }
        //组织查询条件
        OilRecordwhiteListExample oilRecordwhiteListExample = new OilRecordwhiteListExample();
        if (StringUtils.isNotEmpty(userAccount)) {
            oilRecordwhiteListExample.or().andUserAccountEqualTo(userAccount);
        }
        oilRecordwhiteListExample
                .setOrderByClause(OilRecordwhiteListExample.CREATE_TIME_DESC);
        oilRecordwhiteListExample.createCriteria().andStatusEqualTo(OilRecordwhiteList.STATUS_1);
        //分页
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilRecordwhiteListMapper
                        .countByExample(oilRecordwhiteListExample));
        oilRecordwhiteListExample.setLimitStart(page.getStartRow());
        oilRecordwhiteListExample.setLimitEnd(page.getSize());
        //查询表
        List<OilRecordwhiteList> oilRecordwhiteLists = oilRecordwhiteListMapper
                .selectByExample(oilRecordwhiteListExample);
        if (StringUtils.isNotEmpty(userAccount)) {
            if (oilRecordwhiteLists.isEmpty()) {
                throw new BusinessException("该用户不满足额度查询条件");
            }
        }
        //返回的结果
        return DatasetBuilder.fromDataList(oilRecordwhiteLists,
                page.createPageInfo());
    }

    @Override
    @Transactional
    public DatasetSimple<Integer> updateOpenQuota(Long id) {
        Integer flag = 0;
        OilRecordwhiteList oilRecordwhiteList = oilRecordwhiteListMapper
                .selectByPrimaryKey(id);
        oilRecordwhiteList.setUpdateTime(new Date());
        oilRecordwhiteList.setOpenQuota(BigDecimal.ONE);
        oilRecordwhiteList.setStatus(OilRecordwhiteList.STATUS_2);
        flag = oilRecordwhiteListMapper.updateByPrimaryKey(oilRecordwhiteList);
        return DatasetBuilder.fromDataSimple(flag);
    }

}
