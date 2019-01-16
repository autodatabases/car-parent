/**
 * 
 */
package com.emate.shop.business.check;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.emate.shop.business.rela.system.SystemActionRoleRelaConfig;
import com.emate.shop.business.rela.system.SystemMenuPageFKConfig;
import com.emate.shop.business.rela.system.SystemPageActionFKConfig;
import com.emate.shop.business.rela.system.SystemPageRoleRelaConfig;
import com.emate.shop.business.rela.system.SystemRoleUserRelaConfig;
import com.emate.shop.exception.BusinessException;

/**
 * @file SystemDeleteCheck.java
 * @author gaoss
 * @mail gaoss@emateglobal.com
 * @since 2016年8月17日
 */
@Component
public class SystemDeleteCheck {


    @Resource
    private SystemMenuPageFKConfig        systemMenuPageFKConfig;

    @Resource
    private SystemPageActionFKConfig      systemPageActionFKConfig;

    @Resource
    private SystemPageRoleRelaConfig      systemPageRoleRelaConfig;

    @Resource
    private SystemActionRoleRelaConfig    systemActionRoleRelaConfig;

    @Resource
    private SystemRoleUserRelaConfig      systemRoleUserRelaConfig;



    public void menuDeleteCheck(Long menuId) {
        // 页面
        if (this.systemMenuPageFKConfig.has(menuId)) {
            throw new BusinessException("该菜单下{}有在用的页面,不能删除", menuId);
        }
    }

    public void pageDeleteCheck(Long pageId) {
        // 按钮
        if (this.systemPageActionFKConfig.has(pageId)) {
            throw new BusinessException("该页面下{}有在用的按钮,不能删除", pageId);
        }
        // 角色
        if (this.systemPageRoleRelaConfig.has(pageId)) {
            throw new BusinessException("该页面下{}有在用的角色,不能删除", pageId);
        }
    }

    public void actionDeleteCheck(Long actionId) {
        // 角色
        if (this.systemActionRoleRelaConfig.has(actionId)) {
            throw new BusinessException("该资源下{}有在用的角色,不能删除", actionId);
        }
    }

    public void roleDeleteCheck(Long roleId) {
        // 用户
        if (this.systemRoleUserRelaConfig.has(roleId)) {
            throw new BusinessException("该角色下{}有在用的用户,不能删除", roleId);
        }
    }

}
