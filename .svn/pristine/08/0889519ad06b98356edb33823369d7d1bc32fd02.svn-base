
package com.emate.shop.business.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emate.shop.business.api.RechargeAccountSwitchingService;
import com.emate.shop.business.model.OilProvider;
import com.emate.shop.business.model.OilProviderExample;
import com.emate.shop.mapper.OilProviderMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * @file RechargeAccountSwitchingServiceImpl.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月31日 
 */
@Service
public class RechargeAccountSwitchingServiceImpl
        implements RechargeAccountSwitchingService {

    @Resource
    private OilProviderMapper oilProviderMapper;

    @Override
    public DatasetSimple<OilProvider> queryOilProvider() {
        OilProviderExample oilProviderExample = new OilProviderExample();
        List<OilProvider> oilProviderList = oilProviderMapper
                .selectByExample(oilProviderExample);
        DatasetSimple<OilProvider> fromDataSimple = null;
        for (OilProvider oilProvider : oilProviderList) {
            if (oilProvider.getStatus() == 1) {
                fromDataSimple = DatasetBuilder.fromDataSimple(oilProvider);
            }
        }
        fromDataSimple.putDatasetList("oilProviderList", oilProviderList);
        return fromDataSimple;
    }

    @Override
    public DatasetSimple<OilProvider> updateOilProvider(Long id) {
        //先将当前的充值账户状态置为0
        OilProviderExample oilProviderExample = new OilProviderExample();
        oilProviderExample.or().andStatusEqualTo(1);
        OilProvider oilProvider = oilProviderMapper
                .selectByExample(oilProviderExample).get(0);
        oilProvider.setStatus(0);
        oilProviderMapper.updateByPrimaryKeySelective(oilProvider);
        //再根据id修改对应账户的状态为1
        OilProvider oilProvider1 = new OilProvider();
        oilProvider1.setId(id);
        oilProvider1.setStatus(1);
        oilProviderMapper.updateByPrimaryKeySelective(oilProvider1);
        return this.queryOilProvider();
    }

}
