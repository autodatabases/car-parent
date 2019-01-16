package com.emate.shop.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.emate.shop.common.Log4jHelper;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Log4jHelper.getLogger().debug("使用数据源:" + DataSourceContext.getContext());
        return DataSourceContext.getContext();
    }

}
