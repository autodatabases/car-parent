/**
 * 
 */
package com.emate.shop.business.rela.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.business.model.SystemUserExample;
import com.emate.shop.business.model.SystemUserRoleRelation;
import com.emate.shop.common.model.ModelHelper.ModelRelaConfig;
import com.emate.shop.mapper.SystemUserMapper;
import com.emate.shop.mapper.SystemUserRoleRelationMapper;

/**
 * @file SystemRoleUserRelaConfig.java
 * @author gaoss
 * @mail gaoss@emateglobal.com
 * @since 2016年8月17日
 */
@Component
public class SystemRoleUserRelaConfig extends
        ModelRelaConfig<SystemRole, SystemUserRoleRelation, SystemUser> {

    @Resource
    private SystemUserRoleRelationMapper systemUserRoleRelationMapper;

    @Resource
    private SystemUserMapper             systemUserMapper;

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getSrcFkName()
     */
    @Override
    public String getSrcFkName() {
        return "roleId";
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getTargetFkName()
     */
    @Override
    public String getTargetFkName() {
        return "userId";
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getRelaMapper()
     */
    @Override
    public Object getRelaMapper() {
        return this.systemUserRoleRelationMapper;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getTargetMapper()
     */
    @Override
    public Object getTargetMapper() {
        return this.systemUserMapper;
    }

    /* (non-Javadoc)
     * @see com.emate.shop.common.model.ModelHelper.ModelRelaConfig#getTargetOrder()
     */
    @Override
    public String getTargetOrder() {
        return SystemUserExample.ID_ASC;
    }

}
