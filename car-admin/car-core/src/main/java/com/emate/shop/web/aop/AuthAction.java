/**
 * 
 */
package com.emate.shop.web.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAction {

    public AuthActionType actionType() default AuthActionType.REQUIRED_AUTH;
    
    public static enum AuthActionType {
        LOGIN, LOGOUT, REQUIRED_AUTH
    }

}
