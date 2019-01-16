/**
 * 
 */
package com.emate.shop.web.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.exception.BusinessException;
import com.emate.shop.exception.ParameterException;

/**
 * @file ControllerAop.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月7日
 * 提供工具类
 * 
 */
public class SpringAopUtil {

    public HttpServletRequest findRequestValue(
            ProceedingJoinPoint proceedingJoinPoint) {
        return Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(Objects::nonNull)
                .filter(obj -> obj instanceof HttpServletRequest)
                .map(HttpServletRequest.class::cast).findFirst().orElse(null);
    }

    public HttpServletResponse findResponseValue(
            ProceedingJoinPoint proceedingJoinPoint) {
        return Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(Objects::nonNull)
                .filter(obj -> obj instanceof HttpServletResponse)
                .map(HttpServletResponse.class::cast).findFirst().orElse(null);
    }

    public Method findMethodValue(ProceedingJoinPoint proceedingJoinPoint) {
        return ((MethodSignature) proceedingJoinPoint.getSignature())
                .getMethod();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findModelValue(
            ProceedingJoinPoint proceedingJoinPoint) {
        return Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(Objects::nonNull).filter(obj -> obj instanceof Map)
                .map(Map.class::cast).findFirst().orElse(null);
    }

    public Object findControllerValue(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getThis();
    }

    public Class<?> findControllerClazz(
            ProceedingJoinPoint proceedingJoinPoint) {
        return AopUtils
                .getTargetClass(this.findControllerValue(proceedingJoinPoint));
    }

    public String findRequestUrl(ProceedingJoinPoint proceedingJoinPoint) {
        Method method = this.findMethodValue(proceedingJoinPoint);
        Class<?> clazz = this.findControllerClazz(proceedingJoinPoint);
        RequestMapping requestMappingOnClazz = clazz
                .getAnnotation(RequestMapping.class);
        RequestMapping requestMappingOnMethod = method
                .getAnnotation(RequestMapping.class);
        String url = "/";
        if (Objects.nonNull(requestMappingOnClazz)) {
            url += requestMappingOnClazz.value();
        }
        if (Objects.nonNull(requestMappingOnMethod)) {
            url += "/" + requestMappingOnMethod.value();
        }
        url = url.replaceAll("[/]+", "/");
        return url;
    }

    public Boolean isResponseBody(ProceedingJoinPoint proceedingJoinPoint) {
        return Objects.nonNull(this.findMethodValue(proceedingJoinPoint)
                .getAnnotation(ResponseBody.class));
    }

    public BusinessException getBusinessException(Throwable e) {
        if (e instanceof BusinessException || e instanceof ParameterException) {
            return (BusinessException) e;
        }
        if (Objects.isNull(e.getCause())) {
            return null;
        }
        return this.getBusinessException(e.getCause());
    }

    public Class<?> findReturnType(ProceedingJoinPoint proceedingJoinPoint) {
        return this.findMethodValue(proceedingJoinPoint).getReturnType();
    }

}
