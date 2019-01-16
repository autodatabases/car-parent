/**
 * 
 */
package com.emate.shop.business.vo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.emate.shop.business.model.SystemAction;
import com.emate.shop.business.model.SystemMenu;
import com.emate.shop.business.model.SystemPage;

/**
 * @file ResourceTree.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月14日
 */

public class ResourceTree {

    private List<SystemMenu>          systemMenus;

    private List<SystemPage>          systemPages;

    private List<SystemAction>        systemActions;

    private List<SystemMenuWithPages> resourceTree;

    private List<JsTreeModel>         jsTreeModels;

    public List<JsTreeModel> getJsTreeModels() {
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

    public List<SystemMenuWithPages> getResourceTree() {
        if (Objects.isNull(this.resourceTree)) {
            this.resourceTree = this.systemMenus.stream().map(systemMenu -> {
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
        return this.resourceTree;
    }

    public List<SystemMenu> getSystemMenus() {
        return systemMenus;
    }

    public void setSystemMenus(List<SystemMenu> systemMenus) {
        this.systemMenus = systemMenus;
    }

    public List<SystemPage> getSystemPages() {
        return systemPages;
    }

    public void setSystemPages(List<SystemPage> systemPages) {
        this.systemPages = systemPages;
    }

    public List<SystemAction> getSystemActions() {
        return systemActions;
    }

    public void setSystemActions(List<SystemAction> systemActions) {
        this.systemActions = systemActions;
    }
}
