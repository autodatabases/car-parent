package com.emate.shop.business.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.OilCardConfigService;
import com.emate.shop.business.constants.PaginationUtil;
import com.emate.shop.business.model.OilCardConfig;
import com.emate.shop.business.model.OilCardConfigExample;
import com.emate.shop.business.model.OilCardConfigExample.Criteria;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.mapper.OilCardConfigMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

@Service
public class OilCardConfigServiceImpl implements OilCardConfigService {

    @Resource
    private OilCardConfigMapper oilCardConfigMapper;

    @Override
    @Read
    public DatasetSimple<OilCardConfig> getOilCardConfig(Long id) {
        OilCardConfig oilCardConfig = oilCardConfigMapper
                .selectByPrimaryKey(id);
        return DatasetBuilder.fromDataSimple(oilCardConfig);
    }

    @Override
    @Read
    public DatasetList<OilCardConfig> queryOilCardConfig(
            OilCardConfig oilCardConfig, Integer pageNo, Integer pageSize) {
        OilCardConfigExample oilEx = new OilCardConfigExample();
        Criteria c = oilEx.createCriteria();
        if (Objects.nonNull(oilCardConfig.getCardType())) {
            c.andCardTypeEqualTo(oilCardConfig.getCardType());
        }
        if (StringUtils.isNotEmpty(oilCardConfig.getContent())) {
            c.andContentEqualTo(oilCardConfig.getContent());
        }
        if (Objects.nonNull(oilCardConfig.getStatus())) {
            c.andStatusEqualTo(oilCardConfig.getStatus());
        }
        if (Objects.nonNull(oilCardConfig.getSupplier())) {
            c.andSupplierEqualTo(oilCardConfig.getSupplier());
        }
        PaginationUtil page = new PaginationUtil(pageNo, pageSize,
                oilCardConfigMapper.countByExample(oilEx));
        oilEx.setLimitStart(page.getStartRow());
        oilEx.setLimitEnd(page.getSize());
        List<OilCardConfig> oilCardConfigs = oilCardConfigMapper
                .selectByExample(oilEx);
        return DatasetBuilder.fromDataList(oilCardConfigs,
                page.createPageInfo());
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<Integer> addOrUpdateOilCardConfig(
            OilCardConfig oilCardConfig) {
        if (Objects.isNull(oilCardConfig.getCardType())) {
            throw new BusinessException("油卡充值类型不能为空~~");
        }
        if (Objects.isNull(oilCardConfig.getStatus())) {
            throw new BusinessException("油卡充值面额状态不能为空~~");
        }
        if (StringUtils.isEmpty(oilCardConfig.getContent())) {
            throw new BusinessException("油卡充值面额不能为空~~");
        }
        if (Objects.isNull(oilCardConfig.getSupplier())) {
            throw new BusinessException("服务提供者不能为空~~");
        }
        Byte supplier = oilCardConfig.getSupplier();
        if (new Byte("1").equals(supplier)
                && !new Byte("2").equals(oilCardConfig.getCardType())) {
            if (Objects.isNull(oilCardConfig.getGoodId())) {
                throw new BusinessException("欧飞产品编号不能空~~");
            }
            OilCardConfigExample oilCardConfigEx = new OilCardConfigExample();
            oilCardConfigEx.createCriteria()
                    .andSupplierEqualTo(oilCardConfig.getSupplier())
                    .andGoodIdEqualTo(oilCardConfig.getGoodId());
            List<OilCardConfig> oilCardConfigs = oilCardConfigMapper
                    .selectByExample(oilCardConfigEx);
            if (!oilCardConfigs.isEmpty()) {
                throw new BusinessException("欧飞油卡产品编号不能重复~");
            }
        }
        Integer flag = 0;
        if (Objects.isNull(oilCardConfig.getId())) {
            OilCardConfigExample oilCardConfigEx = new OilCardConfigExample();
            oilCardConfigEx.createCriteria()
                    .andSupplierEqualTo(oilCardConfig.getSupplier())
                    .andCardTypeEqualTo(oilCardConfig.getCardType())
                    .andContentEqualTo(oilCardConfig.getContent());
            List<OilCardConfig> oilCardConfigs = oilCardConfigMapper
                    .selectByExample(oilCardConfigEx);
            if (!oilCardConfigs.isEmpty()) {
                throw new BusinessException("该油卡类型的面额已存在~");
            }
            oilCardConfig.setCreateTime(new Date());
            oilCardConfig.setUpdateTime(new Date());
            flag = oilCardConfigMapper.insertSelective(oilCardConfig);
        } else {
            oilCardConfig.setUpdateTime(new Date());
            flag = oilCardConfigMapper
                    .updateByPrimaryKeySelective(oilCardConfig);
        }
        return DatasetBuilder.fromDataSimple(flag);
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<Integer> updateStatus(Long id, String status) {
        if (Objects.isNull(id)) {
            throw new BusinessException("油卡面额id不能为空");
        }
        if (StringUtils.isEmpty(status)) {
            throw new BusinessException("油卡面额状态不能为空");
        }
        OilCardConfig config = oilCardConfigMapper.selectByPrimaryKey(id);
        if (Objects.isNull(config)) {
            throw new BusinessException("没有查到该油卡面额~");
        }
        config.setStatus(Integer.valueOf(status));
        int flag = oilCardConfigMapper.updateByPrimaryKeySelective(config);
        return DatasetBuilder.fromDataSimple(flag);
    }

    @Override
    @Write
    @Transactional
    public DatasetSimple<Integer> deloilCardConfig(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("油卡面额id不能为空");
        }
        int flag = oilCardConfigMapper.deleteByPrimaryKey(Long.valueOf(id));
        return DatasetBuilder.fromDataSimple(flag);
    }

    @Override
    @Read
    public DatasetSimple<OilCardConfig> getOilCardConfigByCon(String cardType,
            String content, String supplier) {

        OilCardConfigExample oilCardConfigEx = new OilCardConfigExample();
        Criteria c = oilCardConfigEx.createCriteria();
        if ("0".equals(cardType)) {
            c.andCardTypeEqualTo(OilCardConfig.CARD_TYPE_0);
        } else {
            c.andCardTypeEqualTo(OilCardConfig.CARD_TYPE_1);
        }
        if ("1".equals(supplier)) {
            c.andGoodIdEqualTo(content);
        } else {
            c.andContentEqualTo(content);
        }
        c.andSupplierEqualTo(Byte.valueOf(supplier));
        List<OilCardConfig> oilCardConfigs = oilCardConfigMapper
                .selectByExample(oilCardConfigEx);
        if (oilCardConfigs.size() != 1) {
            throw new BusinessException("查询油卡面额失败");
        }
        return DatasetBuilder.fromDataSimple(oilCardConfigs.get(0));
    }

}
