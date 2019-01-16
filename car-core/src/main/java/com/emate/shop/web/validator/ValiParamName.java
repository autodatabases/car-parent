package com.emate.shop.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验参数名称设置
 * @file ValidatorAnnotation.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月5日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface ValiParamName {

    public String value();
}
