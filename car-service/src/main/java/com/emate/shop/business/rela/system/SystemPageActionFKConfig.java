/**
 * 
 */
package com.emate.shop.business.rela.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemActionExample;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.common.model.ModelHelper.ModelFKConfig;
import com.emate.shop.mapper.SystemActionMapper;

/**
 * @file SystemPageActionFKConfig.java
 * @author gaoss
 * @mail gaoss@emateglobal.com
 * @since 2016年8月17日
 */
@Component
public class SystemPageActionFKConfig
        extends ModelFKConfig<SystemPage, SystemAction> {

    @Resource
    private SystemActionMapper systemActionMapper;

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelFKConfig#getFkName()
     */
    @Override
    public String getFkName() {
        return "pageId";
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelFKConfig#getTargetMapper()
     */
    @Override
    public Object getTargetMapper() {
        return this.systemActionMapper;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelFKConfig#getTargetOrder()
     */
    @Override
    public String getTargetOrder() {
        return SystemActionExample.SEQ_ASC;
    }

}
