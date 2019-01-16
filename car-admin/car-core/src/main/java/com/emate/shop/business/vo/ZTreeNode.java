/**
 * 
 */
package com.emate.shop.business.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @file ZTreeNode.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月28日
 */
public class ZTreeNode {

    private String          name;

    private boolean         checked;

    private boolean         nocheck;

    private boolean         open;

    private List<ZTreeNode> children;

    private Object          entity;

    public ZTreeNode() {
        super();
    }

    public static ZTreeNode instance(Object entity, String namePropertyName) {
        ZTreeNode node = new ZTreeNode();
        if (Objects.isNull(entity)) {
            node.setName("I AM NULL");
            return node;
        }
        try {
            node.setName("[" + entity.getClass().getSimpleName() + "]"
                    + BeanUtils.getProperty(entity, namePropertyName));
        } catch (Throwable e) {
            e.printStackTrace();
            node.setName("I HAVE A EXCEPTION");
        }
        node.setEntity(entity);
        return node;
    }

    public static List<ZTreeNode> instances(List<?> entities,
            String namePropertyName) {
        return entities.stream()
                .map(entity -> instance(entity, namePropertyName))
                .collect(Collectors.toList());
    }

    public static void openAll(List<ZTreeNode> nodes) {
        nodes.stream().forEach(node -> {
            node.setOpen(true);
            if (Objects.nonNull(node.getChildren())
                    && !node.getChildren().isEmpty()) {
                openAll(node.getChildren());
            }
        });
    }

    public static void noCheckAll(List<ZTreeNode> nodes) {
        nodes.stream().forEach(node -> {
            node.setNocheck(true);
            if (Objects.nonNull(node.getChildren())
                    && !node.getChildren().isEmpty()) {
                noCheckAll(node.getChildren());
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<ZTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<ZTreeNode> children) {
        this.children = children;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public void addChild(ZTreeNode child) {
        if (Objects.isNull(this.children)) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public void addChildren(List<ZTreeNode> children) {
        children.stream().forEach(this::addChild);
    }

}
