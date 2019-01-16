package com.emate.shop.web.aop;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.dto.DatasetBuilder;

/**
 * 拦截控制器，异常处理，优先级最高
 * @file ControllerExceptionAop.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月7日
 */
@Aspect
@Order(1)
@Component
public class ControllerExceptionAop extends SpringAopUtil {

    /**
     * 拦截所有的站内控制器方法
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object result = proceedingJoinPoint.proceed();
            //Map<String, Object> model = this
                    //.findModelValue(proceedingJoinPoint);
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            Log4jHelper.getLogger().error(e);
            BusinessException be = this.getBusinessException(e);
            if (Objects.nonNull(be)) {
            	  Log4jHelper.getLogger().error("业务异常:"+be.getMessage());
                if (this.isResponseBody(proceedingJoinPoint)) {
                    return DatasetBuilder.fromMessage(be.getMessage(),
                            this.findReturnType(proceedingJoinPoint));
                } else {
                    //this.findModelValue(proceedingJoinPoint).put("message",
                           // be.getMessage());
                    this.findResponseValue(proceedingJoinPoint).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return "/page/500";
                }
            } else {
                if (this.isResponseBody(proceedingJoinPoint)) {
                    return DatasetBuilder.fromMessage(e.getMessage(),
                            this.findReturnType(proceedingJoinPoint));
                } else {
                    //this.findModelValue(proceedingJoinPoint).put("message",
                          //  e.getMessage());
                    this.findResponseValue(proceedingJoinPoint).setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    return "/page/500";
                }
            }
        }
    }
}
