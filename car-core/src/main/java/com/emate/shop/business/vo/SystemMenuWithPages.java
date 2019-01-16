/**
 * 
 */
package com.emate.shop.business.vo;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.emate.shop.business.model.SystemMenu;

/**
 * @file Test.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月14日
 */

public class SystemMenuWithPages extends SystemMenu {

    private List<SystemPageWithActions> systemPages;

    public SystemMenuWithPages() {
        super();
    }

    public SystemMenuWithPages(SystemMenu systemMenu,
            List<SystemPageWithActions> systemPages) {
        try {
            BeanUtils.copyProperties(this, systemMenu);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.systemPages = systemPages;
    }

    public List<SystemPageWithActions> getSystemPages() {
        return systemPages;
    }

    public void setSystemPages(List<SystemPageWithActions> systemPages) {
        this.systemPages = systemPages;
    }
}
