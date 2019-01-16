package com.emate.shop.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记校验注解类型
 * @file ValidatorAnnotation.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月5日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface ValidatorAnnotation {
}
