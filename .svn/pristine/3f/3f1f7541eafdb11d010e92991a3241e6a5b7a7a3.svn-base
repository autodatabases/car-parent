package com.emate.shop.web.aop;

import java.lang.reflect.Method;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.emate.shop.exception.ParameterException;
import com.emate.shop.web.aop.AuthAction.AuthActionType;

/**
 * 拦截控制器，访问权限控制
 * @file ControllerAuthAop.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月7日
 */
@Aspect
@Order(2)
@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:web.properties")
public class ControllerAuthAop extends SpringAopUtil implements AuthUtil {
	 @Resource
	    private Environment                environment;
    /**
     * 拦截所有的站内控制器方法
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(com.emate.shop.web.aop.AuthAction)")
    public void controllerMethodPointcut() {
    }

        @Around("controllerMethodPointcut()")
        public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
                throws Throwable {
            Method method = this.findMethodValue(proceedingJoinPoint);
            AuthAction authAction = method.getAnnotation(AuthAction.class);
            if (Objects.isNull(authAction)) {
                return proceedingJoinPoint.proceed();
            }
            HttpServletRequest request = this.findRequestValue(proceedingJoinPoint);
            if (Objects.isNull(request)) {
                throw new ParameterException(
                        "有权限控制的控制器方法必须有request参数:" + method.getName());
            }
            if (authAction.actionType().equals(AuthActionType.REQUIRED_AUTH)) {
                if (!this.isLogin(request,environment.getRequiredProperty("auth.token.name"))) {
                	if(!this.isResponseBody(proceedingJoinPoint)){
                		return "redirect:"+environment.getProperty("login.url");
                	}
                    throw new ParameterException("user not login.");
                }
            }
            return proceedingJoinPoint.proceed();
        }
}
