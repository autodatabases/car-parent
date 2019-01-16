package com.emate.shop.business.api.cache;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Cache {
	
	/**
	 * 缓存有效期 单位m,默认30分钟
	 * @return
	 */
	public int expired() default 30;
}
