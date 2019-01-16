/**
 * 
 */
package com.emate.shop.business.helper;

import java.util.Objects;

import com.emate.shop.business.model.SystemActionExample;
import com.emate.shop.business.model.SystemMenuExample;
import com.emate.shop.business.model.SystemPageExample;
import com.emate.shop.business.model.SystemRoleExample;
import com.emate.shop.business.model.SystemUserExample;
import com.emate.shop.common.NumberHelper;
import com.emate.shop.mapper.SystemActionMapper;
import com.emate.shop.mapper.SystemMenuMapper;
import com.emate.shop.mapper.SystemPageMapper;
import com.emate.shop.mapper.SystemRoleMapper;
import com.emate.shop.mapper.SystemUserMapper;

/**
 * @file SystemAuthDuplicateHelper.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月26日
 */
public interface SystemAuthDuplicateHelper {

    default boolean isMenuNameDuplicate(String menuName, Long menuId,
            SystemMenuMapper mapper) {
        SystemMenuExample example = new SystemMenuExample();
        if (Objects.isNull(menuId)) {
            example.or().andNameEqualTo(menuName);
        } else {
            example.or().andNameEqualTo(menuName).andIdNotEqualTo(menuId);
        }
        return NumberHelper.isNotZero(mapper.countByExample(example));
    }

    default boolean isMenuNameDuplicate(String menuName,
            SystemMenuMapper mapper) {
        return this.isMenuNameDuplicate(menuName, null, mapper);
    }

    default boolean isPageNameDuplicate(String pageName, Long pageId,
            SystemPageMapper mapper) {
        SystemPageExample example = new SystemPageExample();
        if (Objects.isNull(pageId)) {
            example.or().andNameEqualTo(pageName);
        } else {
            example.or().andNameEqualTo(pageName).andIdNotEqualTo(pageId);
        }
        return NumberHelper.isNotZero(mapper.countByExample(example));
    }

    default boolean isPageNameDuplicate(String pageName,
            SystemPageMapper mapper) {
        return this.isPageNameDuplicate(pageName, null, mapper);
    }

    default boolean isActionNameDuplicate(String actionName, Long actionId,
            Long pageId, SystemActionMapper mapper) {
        SystemActionExample example = new SystemActionExample();
        if (Objects.isNull(actionId)) {
            example.or().andNameEqualTo(actionName).andPageIdEqualTo(pageId);
        } else {
            example.or().andNameEqualTo(actionName).andIdNotEqualTo(actionId)
                    .andPageIdEqualTo(pageId);
        }
        return NumberHelper.isNotZero(mapper.countByExample(example));
    }

    default boolean isActionNameDuplicate(String actionName, Long pageId,
            SystemActionMapper mapper) {
        return this.isActionNameDuplicate(actionName, null, pageId, mapper);
    }

    default boolean isUserNameDuplicate(String userName, Long userId,
            SystemUserMapper mapper) {
        SystemUserExample example = new SystemUserExample();
        if (Objects.isNull(userId)) {
            example.or().andUserNameEqualTo(userName);
        } else {
            example.or().andUserNameEqualTo(userName).andIdNotEqualTo(userId);
        }
        return NumberHelper.isNotZero(mapper.countByExample(example));
    }

    default boolean isUserNameDuplicate(String userName,
            SystemUserMapper mapper) {
        return this.isUserNameDuplicate(userName, null, mapper);
    }

    default boolean isRoleNameDuplicate(String roleName, Long roleId,
            SystemRoleMapper mapper) {
        SystemRoleExample example = new SystemRoleExample();
        if (Objects.isNull(roleId)) {
            example.or().andNameEqualTo(roleName);
        } else {
            example.or().andNameEqualTo(roleName).andIdNotEqualTo(roleId);
        }
        return NumberHelper.isNotZero(mapper.countByExample(example));
    }

    default boolean isRoleNameDuplicate(String roleName,
            SystemRoleMapper mapper) {
        return this.isRoleNameDuplicate(roleName, null, mapper);
    }

}
