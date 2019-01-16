/**
 * 
 */
package com.emate.shop.rpc.dto;

/**
 * @file DatasetSimple.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月3日
 */
public class DatasetSimple<D> extends Dataset<D, Object> {

    /**
     * @since 2016年7月3日
     */
    private static final long serialVersionUID = 9068292660805348238L;

    public DatasetSimple() {
        super();
    }

    public DatasetSimple(String message) {
        super(message);
    }

    public DatasetSimple(D data) {
        super(data, null, null);
    }

}
