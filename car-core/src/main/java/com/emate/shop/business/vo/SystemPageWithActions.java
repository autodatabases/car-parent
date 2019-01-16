/**
 * 
 */
package com.emate.shop.business.vo;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemPage;

/**
 * @file TEst.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月14日
 */
public class SystemPageWithActions extends SystemPage {

    private List<SystemAction> systemActions;

    public SystemPageWithActions() {
        super();
    }

    public SystemPageWithActions(SystemPage systemPage,
            List<SystemAction> systemActions) {
        try {
            BeanUtils.copyProperties(this, systemPage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        this.systemActions = systemActions;
    }

    public List<SystemAction> getSystemActions() {
        return systemActions;
    }

    public void setSystemActions(List<SystemAction> systemActions) {
        this.systemActions = systemActions;
    }
}
