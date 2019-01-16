/**
 * 
 */
package com.emate.shop.business.rela.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemPageExample;
import com.emate.shop.common.model.ModelHelper.ModelFKConfig;
import com.emate.shop.mapper.SystemPageMapper;

/**
 * @file SystemMenuPageFKConfig.java
 * @author gaoss
 * @mail gaoss@emateglobal.com
 * @since 2016年8月17日
 */
@Component
public class SystemMenuPageFKConfig
        extends ModelFKConfig<SystemMenu, SystemPage> {

    @Resource
    private SystemPageMapper systemPageMapper;

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelFKConfig#getFkName()
     */
    @Override
    public String getFkName() {
        return "menuId";
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelFKConfig#getTargetMapper()
     */
    @Override
    public Object getTargetMapper() {
        return this.systemPageMapper;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelFKConfig#getTargetOrder()
     */
    @Override
    public String getTargetOrder() {
        return SystemPageExample.SEQ_ASC;
    }

}
