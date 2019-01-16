/**
 * 
 */
package com.emate.shop.business.helper;

import java.util.List;
import java.util.stream.Collectors;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemActionExample;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemPageExample;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemRoleActionRelation;
import com.emate.shop.business.model.SystemRoleActionRelationExample;
import com.emate.shop.business.model.SystemRolePageRelation;
import com.emate.shop.business.model.SystemRolePageRelationExample;
import com.emate.shop.mapper.SystemActionMapper;
import com.emate.shop.mapper.SystemPageMapper;
import com.emate.shop.mapper.SystemRoleActionRelationMapper;
import com.emate.shop.mapper.SystemRolePageRelationMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetList;

/**
 * @file SystemAuthRelaHelper.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月28日
 */
public interface SystemAuthRelaHelper {

    default DatasetList<SystemPage> queryRelaPages(SystemRole role,
            SystemRolePageRelationMapper mapper, SystemPageMapper pageMapper) {
        SystemRolePageRelationExample relationExample = new SystemRolePageRelationExample();
        relationExample.or().andRoleIdEqualTo(role.getId());
        List<SystemRolePageRelation> relations = mapper
                .selectByExample(relationExample);
        List<Long> pageIds = relations.stream()
                .map(SystemRolePageRelation::getPageId)
                .collect(Collectors.toList());
        pageIds.add(0L);
        SystemPageExample pageExample = new SystemPageExample();
        pageExample.or().andIdIn(pageIds);
        pageExample.setOrderByClause(SystemPageExample.SEQ_ASC);
        List<SystemPage> pages = pageMapper.selectByExample(pageExample);
        return DatasetBuilder.fromDataList(pages);
    }

    default DatasetList<SystemAction> queryRelaActions(SystemRole role,
            SystemRoleActionRelationMapper mapper,
            SystemActionMapper actionMapper) {
        SystemRoleActionRelationExample relationExample = new SystemRoleActionRelationExample();
        relationExample.or().andRoleIdEqualTo(role.getId());
        List<SystemRoleActionRelation> relations = mapper
                .selectByExample(relationExample);
        List<Long> actionIds = relations.stream()
                .map(SystemRoleActionRelation::getActionId)
                .collect(Collectors.toList());
        actionIds.add(0L);
        SystemActionExample actionExample = new SystemActionExample();
        actionExample.or().andIdIn(actionIds);
        actionExample.setOrderByClause(SystemActionExample.SEQ_ASC);
        List<SystemAction> actions = actionMapper
                .selectByExample(actionExample);
        return DatasetBuilder.fromDataList(actions);
    }

    //根据menu查询页面
    
    default DatasetList<SystemPage> queryRelaPagesByMenu(SystemMenu menu,SystemPageMapper pageMapper){
    	SystemPageExample pageExample = new SystemPageExample();
    	pageExample.or().andMenuIdEqualTo(menu.getId());
    	return DatasetBuilder.fromDataList(pageMapper.selectByExample(pageExample));
    }
    
    
    //根据页面查询action
    
   default DatasetList<SystemAction> queryRealActionsByPage(SystemPage page,SystemActionMapper actionMapper){
	   SystemActionExample actionExample = new SystemActionExample();
	   actionExample.or().andPageIdEqualTo(page.getId());
	   return DatasetBuilder.fromDataList(actionMapper.selectByExample(actionExample));
   }
    //system/seller

}
