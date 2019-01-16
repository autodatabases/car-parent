/**
 * 
 */
package com.emate.shop.business.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemPage;
import com.emate.shop.business.model.SystemRole;
import com.emate.shop.business.model.SystemUser;
import com.emate.shop.common.StringHelper;

/**
 * @file SystemUserAuth.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月14日
 */
public class SystemUserAuth {

    private SystemUser                systemUser;

    private List<SystemRole>          systemRoles;

    private List<SystemAction>        systemActions;

    private List<SystemPage>          systemPages;

    private List<SystemMenu>          systemMenus;

    private Set<String>               authUrls;

    private List<SystemMenuWithPages> authTree;

    private List<JsTreeModel>         jsTreeModels;

    public List<JsTreeModel> getJstreeModels() {
        if (Objects.isNull(this.jsTreeModels)) {
            this.jsTreeModels = this.systemMenus.stream().map(systemMenu -> {
                JsTreeModel jstreeModel = new JsTreeModel(systemMenu.getId(),
                        systemMenu.getName(), 1, systemMenu);
                List<JsTreeModel> children = this.systemPages.stream()
                        .filter(systemPage -> Objects.equals(systemMenu.getId(),
                                systemPage.getMenuId()))
                        .map(systemPage -> {
                            JsTreeModel jstreeModel1 = new JsTreeModel(
                                    systemPage.getId(), systemPage.getName(), 2,
                                    systemPage);
                            List<JsTreeModel> children1 = this.systemActions
                                    .stream()
                                    .filter(systemAction -> Objects.equals(
                                            systemPage.getId(),
                                            systemAction.getPageId()))
                                    .map(systemAction -> {
                                        JsTreeModel jstreeModel2 = new JsTreeModel(
                                                systemAction.getId(),
                                                systemAction.getName(), 3,
                                                systemAction);
                                        return jstreeModel2;
                                    }).collect(Collectors.toList());
                            jstreeModel1.setChildren(children1);
                            return jstreeModel1;
                        }).collect(Collectors.toList());
                jstreeModel.setChildren(children);
                return jstreeModel;
            }).collect(Collectors.toList());
        }
        return this.jsTreeModels;
    }

    public List<SystemMenuWithPages> getAuthTree() {
        if (Objects.isNull(this.authTree)) {
            this.authTree = this.systemMenus.stream().map(systemMenu -> {
                List<SystemPageWithActions> systemPageWithActions = this.systemPages
                        .stream()
                        .filter(systemPage -> Objects.equals(
                                systemPage.getMenuId(), systemMenu.getId()))
                        .map(systemPage -> {
                            return new SystemPageWithActions(systemPage,
                                    this.systemActions.stream().filter(
                                            systemAction -> Objects.equals(
                                                    systemAction.getPageId(),
                                                    systemPage.getId()))
                                            .collect(Collectors.toList()));
                        }).collect(Collectors.toList());
                return new SystemMenuWithPages(systemMenu,
                        systemPageWithActions);
            }).collect(Collectors.toList());
        }
        return this.authTree;
    }

    public Set<String> getAuthUrls() {
        if (Objects.isNull(this.authUrls)) {
            this.authUrls = new HashSet<>();
            this.systemPages.parallelStream().map(SystemPage::getPageUrl)
                    .filter(Objects::nonNull)
                    .flatMap(StringHelper::splitByComma)
                    .forEach(this.authUrls::add);
            this.systemActions.parallelStream().map(SystemAction::getActionUrl)
                    .filter(Objects::nonNull)
                    .flatMap(StringHelper::splitByComma)
                    .forEach(this.authUrls::add);
        }
        return this.authUrls;
    }

    public List<SystemRole> getSystemRoles() {
        return systemRoles;
    }

    public void setSystemRoles(List<SystemRole> systemRoles) {
        this.systemRoles = systemRoles;
    }

    public List<SystemAction> getSystemActions() {
        return systemActions;
    }

    public void setSystemActions(List<SystemAction> systemActions) {
        this.systemActions = systemActions;
    }

    public List<SystemPage> getSystemPages() {
        return systemPages;
    }

    public void setSystemPages(List<SystemPage> systemPages) {
        this.systemPages = systemPages;
    }

    public List<SystemMenu> getSystemMenus() {
        return systemMenus;
    }

    public void setSystemMenus(List<SystemMenu> systemMenus) {
        this.systemMenus = systemMenus;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
