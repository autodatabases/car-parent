package com.emate.shop.web.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.ParameterException;
import com.emate.shop.web.validator.ValiParamName;
import com.emate.shop.web.validator.ValidatorAnnotation;

/**
 * 拦截控制器，方法参数校验
 * @file ControllerValidateAop.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月7日
 */
@Aspect
@Order(3)
@Component
public class ControllerValidateAop  extends SpringAopUtil{

    private static final ParameterNameDiscoverer NAME_DISC = new DefaultParameterNameDiscoverer();

    /**
     * 拦截所有的站内控制器方法
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
    	Log4jHelper.getLogger().info(this.findControllerClazz(proceedingJoinPoint).getSimpleName()+"."+this.findMethodValue(proceedingJoinPoint).getName());
        this.validateArgs(proceedingJoinPoint);
        
        Object result = proceedingJoinPoint.proceed();
        if(this.isResponseBody(proceedingJoinPoint)){
        	Log4jHelper.getLogger().debug("Controller Result：\n"+JacksonHelper.toPrettyJSON(result));
        }
        return result;
    }

    private String validateArg(Method method, int argIndex, Object argValue) {
    	//Log4jHelper.getLogger().info("参数校验");
       // Log4jHelper.getLogger().debug(argIndex + ":" + argValue);
        Parameter parameter = method.getParameters()[argIndex];
        MethodParameter methodParameter = new MethodParameter(method, argIndex);
        methodParameter.initParameterNameDiscovery(NAME_DISC);
        String argName = methodParameter.getParameterName();
        ValiParamName validatorParameterName = parameter
                .getAnnotation(ValiParamName.class);
        String paramName = Objects.isNull(validatorParameterName) ? ""
                : validatorParameterName.value();
        if(!"request".equals(argName) && !"response".equals(argName)){
        	  Log4jHelper.getLogger().info(argName + ":" + argValue);
        }
        StringBuilder message = new StringBuilder();
        List<Annotation> vannos = Arrays.stream(parameter.getAnnotations())
                .filter(anno -> Objects.nonNull(anno.annotationType()
                        .getAnnotation(ValidatorAnnotation.class)))
                .collect(Collectors.toList());
        Log4jHelper.getLogger().debug(vannos.size());
        for (int i = 0; i < vannos.size(); i++) {
            Annotation anno = vannos.get(i);
            Arrays.stream(vannos.get(i).annotationType().getClasses())
                    .filter(clazz -> clazz.getName().endsWith("$Impl"))
                    .map(Class::getMethods).flatMap(Arrays::stream)
                    .filter(m -> m.getName().equals("validate")).findFirst()
                    .ifPresent(m -> {
                        try {
                            Object obj = m.getDeclaringClass().newInstance();
                            String msg = (String) m.invoke(obj, argName,
                                    paramName, argValue, anno);
                            if (Objects.nonNull(msg)) {
                                message.append(msg).append(";");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return message.toString();
    }

    /**
     * 校验参数
     * @param proceedingJoinPoint
     */
    private void validateArgs(ProceedingJoinPoint proceedingJoinPoint) {
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature())
                .getMethod();
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < method.getParameterCount(); i++) {
            String m = this.validateArg(method, i, args[i]);
            message.append(m);
        }
        if (message.length() > 0) {
            Log4jHelper.getLogger().info("参数校验失败:" + message);
            throw new ParameterException(message.toString());
        }
    }
}
