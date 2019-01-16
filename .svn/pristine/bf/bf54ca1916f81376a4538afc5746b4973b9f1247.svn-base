package com.emate.shop.business.api;

import java.math.BigDecimal;

import com.emate.shop.business.model.OilRecordwhiteList;
import com.emate.shop.datasource.Read;
import com.emate.shop.datasource.Write;
import com.emate.shop.rpc.dto.DatasetList;
import com.emate.shop.rpc.dto.DatasetSimple;

/**
 * 
 * @file RecordWhitelistService.java
 * @author tangweiming
 * @mail weiming.tang@emateglobal.com
 * @since 2018年5月14日
 */
public interface OilRecordWhitelistService {

    @Write
    public DatasetSimple<Boolean> addOilRecordWhiteList(String userAccount,
            BigDecimal openQuota, String remark);

    @Read
    DatasetList<OilRecordwhiteList> queryOilRecordWhitelist(String userAccount,
            Integer pageNo, Integer pageSize);

    @Write
    DatasetSimple<Integer> updateOpenQuota(Long id);

}
