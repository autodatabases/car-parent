package com.emate.shop.datasource;

import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class DynamicDataSourceAop {

    @Pointcut("@annotation(com.emate.shop.datasource.Write) || @annotation(com.emate.shop.datasource.Read)")
    public void datasourceaop() {
    }

    @Around("datasourceaop()")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        boolean isWrite = this.isWrite(proceedingJoinPoint);
        if (isWrite) {
            DataSourceContext.setWrite();
        } else {
            DataSourceContext.setRead();
        }
        return proceedingJoinPoint.proceed();
    }

    private boolean isWrite(ProceedingJoinPoint proceedingJoinPoint) {
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature())
                .getMethod();
        return Objects.nonNull(method.getAnnotation(Write.class));
    }
}
