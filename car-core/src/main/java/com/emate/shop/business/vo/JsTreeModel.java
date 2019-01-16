/**
 * 
 */
package com.emate.shop.business.vo;

import java.util.List;

/**
 * @file JsTreeModel.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月14日
 */

public class JsTreeModel {

    private Long              id;

    private String            text;

    private List<JsTreeModel> children;

    private int               level;

    private Object            attrObject;

    public JsTreeModel() {
        super();
    }

    public JsTreeModel(Long id, String text, int level, Object attrObject) {
        super();
        this.id = id;
        this.text = text;
        this.level = level;
        this.attrObject = attrObject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<JsTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<JsTreeModel> children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Object getAttrObject() {
        return attrObject;
    }

    public void setAttrObject(Object attrObject) {
        this.attrObject = attrObject;
    }
}
