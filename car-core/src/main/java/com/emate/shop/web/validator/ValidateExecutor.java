/**
 * 
 */
package com.emate.shop.web.validator;

import java.lang.annotation.Annotation;

/**
 * @file ValidateExecutor.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月5日
 */
public interface ValidateExecutor {

    public String validate(String argName, String paramName, Object argValue,
            Annotation anno);

}
