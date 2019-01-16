/**
 * 
 */
package com.emate.shop.rpc.dto;

import java.util.List;

/**
 * @file DatasetList.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月3日
 */
public class DatasetList<L> extends Dataset<Object, L> {

    /**
     * @since 2016年7月3日
     */
    private static final long serialVersionUID = 2479939524308210209L;

    public DatasetList() {
        super();
    }

    public DatasetList(List<L> list, PageInfo pageInfo) {
        super(null, list, pageInfo);
    }

    public DatasetList(List<L> list) {
        super(null, list, null);
    }

    public DatasetList(String message) {
        super(message);
    }

}
