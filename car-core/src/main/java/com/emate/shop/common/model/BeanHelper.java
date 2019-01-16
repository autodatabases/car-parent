/**
 * 
 */
package com.emate.shop.common.model;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @file BeanHelper.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年8月11日
 */
public class BeanHelper {

    public static void copyProperties(Object dest, Object src) {
        try {
            BeanUtils.copyProperties(dest, src);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
		System.out.println(1);
	}
}

class a{
    public static void main(String[] args){
		System.out.println(1);
	}
}
