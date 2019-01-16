/**
 * 
 */
package com.emate.shop.business.rela.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemRoleActionRelation;
import com.emate.shop.business.model.SystemRoleExample;
import com.emate.shop.common.model.ModelHelper.ModelRelaConfig;
import com.emate.shop.mapper.SystemRoleActionRelationMapper;
import com.emate.shop.mapper.SystemRoleMapper;

/**
 * @file SystemActionRoleRelaConfig.java
 * @author gaoss
 * @mail gaoss@emateglobal.com
 * @since 2016年8月17日
 */
@Component
public class SystemActionRoleRelaConfig extends
        ModelRelaConfig<SystemAction, SystemRoleActionRelation, SystemRole> {

    @Resource
    private SystemRoleActionRelationMapper systemRoleActionRelationMapper;

    @Resource
    private SystemRoleMapper               systemRoleMapper;

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getSrcFkName()
     */
    @Override
    public String getSrcFkName() {
        return "actionId";
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getTargetFkName()
     */
    @Override
    public String getTargetFkName() {
        return "roleId";
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getRelaMapper()
     */
    @Override
    public Object getRelaMapper() {
        return this.systemRoleActionRelationMapper;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getTargetMapper()
     */
    @Override
    public Object getTargetMapper() {
        return this.systemRoleMapper;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getTargetOrder()
     */
    @Override
    public String getTargetOrder() {
        return SystemRoleExample.ID_ASC;
    }

}
