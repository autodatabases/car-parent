/**
 * 
 */
package com.emate.shop.business.helper;

import java.util.List;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemActionExample;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemMenuExample;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemPageExample;
import com.emate.shop.mapper.SystemActionMapper;
import com.emate.shop.mapper.SystemMenuMapper;
import com.emate.shop.mapper.SystemPageMapper;

/**
 * @file EntitySeqHelper.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月26日
 */
public interface EntitySeqHelper {

    default Long nextMenuSeq(SystemMenuMapper mapper) {
        SystemMenuExample example = new SystemMenuExample();
        example.setOrderByClause(SystemMenuExample.SEQ_DESC);
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<SystemMenu> menus = mapper.selectByExample(example);
        if (menus.isEmpty()) {
            return 0L;
        }
        return menus.get(0).getSeq() + 1L;
    }

    default Long nextPageSeq(Long menuId, SystemPageMapper mapper) {
        SystemPageExample example = new SystemPageExample();
        example.or().andMenuIdEqualTo(menuId);
        example.setOrderByClause(SystemPageExample.SEQ_DESC);
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<SystemPage> pages = mapper.selectByExample(example);
        if (pages.isEmpty()) {
            return 0L;
        }
        return pages.get(0).getSeq() + 1L;
    }

    default Long nextActionSeq(Long pageId, SystemActionMapper mapper) {
        SystemActionExample example = new SystemActionExample();
        example.or().andPageIdEqualTo(pageId);
        example.setOrderByClause(SystemActionExample.SEQ_DESC);
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<SystemAction> actions = mapper.selectByExample(example);
        if (actions.isEmpty()) {
            return 0L;
        }
        return actions.get(0).getSeq() + 1L;
    }
}
