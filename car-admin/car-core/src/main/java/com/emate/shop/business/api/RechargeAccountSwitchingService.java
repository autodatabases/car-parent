
package com.emate.shop.business.api;

import com.emate.shop.business.model.OilProvider;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 惠+车服后台添加切换充值账号的相关需求
 * @file RechargeAccountSwitchingService.java
 * @author liyao
 * @mail yao.li@emateglobal.com
 * @since 2018年5月31日 
 */
public interface RechargeAccountSwitchingService {

    /**
     * @param
     * @return
     */
    @Read
    DatasetSimple<OilProvider> queryOilProvider();

    /**
     * @param
     * @return
     */
    @Write
    DatasetSimple<OilProvider> updateOilProvider(Long id);

}
