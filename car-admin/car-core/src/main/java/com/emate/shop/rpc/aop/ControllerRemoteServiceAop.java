package com.emate.shop.rpc.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.emate.shop.business.api.cache.Cache;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RedisUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.exception.ParameterException;
import com.emate.shop.rpc.dto.Dataset;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.RpcDtoHelper;
import com.emate.shop.rpc.dto.RpcRequest;

/**
 * 拦截控制器，注入远程服务代理，校验参数，打印dataMap对象内容日志
 * @file ControllerAop.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月3日
 */
@Aspect
@Order(4)
@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:web.properties")
public class ControllerRemoteServiceAop {

    private static Map<String, Object> PROXYS      = Collections
            .synchronizedMap(new HashMap<>());

    private static Set<String>         CONTROLLERS = Collections
            .synchronizedSet(new HashSet<>());

    @Resource
    private Environment                environment;
    
    public ControllerRemoteServiceAop() {
    	new Thread(() -> {
    		try{
    			while(Objects.isNull(environment)){
    				Thread.sleep(3000);
    				Log4jHelper.getLogger().info("Current environment : " + environment);
    			}
    			RedisUtil.initRedisTemplate(environment);
    		}catch(Exception e){
    			Log4jHelper.getLogger().error("初始化 redis 异常 ：",e);
    		}
    	}).start();
	}

    /**
     * 拦截所有的站内控制器方法
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object controllerMethodPointcut(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log4jHelper.getLogger().debug("远程服务代理");
        this.setRemoteServiceProxys(proceedingJoinPoint);
        return proceedingJoinPoint.proceed();
    }

    /**
     * 利用set方法注入远程服务代理对象
     * @param proceedingJoinPoint
     */
    private void setRemoteServiceProxys(
            ProceedingJoinPoint proceedingJoinPoint) {
        //获取控制器对象
        Object controller = proceedingJoinPoint.getThis();
        //获取控制器类型
        Class<?> controllerClazz = AopUtils.getTargetClass(controller);
        //判断没有注入过远程服务
        if (!CONTROLLERS.contains(controllerClazz.getName())) {
            //获取控制器所有的方法
            Arrays.stream(controllerClazz.getDeclaredMethods())
                    //过滤有@RemoteService注解的方法
                    .filter(this::isRemoteServiceSetter)
                    //过滤set方法
                    .filter(method -> method.getName().startsWith("set"))
                    //过滤方法参数个数为1
                    .filter(method -> Objects.equals(1,
                            method.getParameterCount()))
                    //过滤public方法
                    .filter(method -> Modifier.isPublic(method.getModifiers()))
                    //注入操作
                    .forEach(method -> {
                        //获取代理对象
                        Object proxy = getProxy(method.getParameterTypes()[0],
                                environment);
                        try {
                            //使用set方法注入代理对象
                            method.invoke(controller, proxy);
                        } catch (IllegalAccessException
                                | IllegalArgumentException
                                | InvocationTargetException e) {
                            //注入失败会导致控制器不可用，需要开发人员查找原因
                            e.printStackTrace();
                            throw new ParameterException(e);
                        }
                    });
            //标记已经注入过远程服务
            CONTROLLERS.add(controllerClazz.getName());
        }
    }

    /**
     * 判断是否有@RemoteService注解
     * @param method
     * @return
     */
    private Boolean isRemoteServiceSetter(Method method) {
        return Objects.nonNull(method.getAnnotation(RemoteService.class));
    }

    /**
     * 生成远程服务实现的代理对象
     * @param clazz 服务接口
     * @return 代理对象
     */
    public static Object getProxy(Class<?> clazz, Environment environment) {
        //判断是否已有代理缓存
        if (Objects.isNull(PROXYS.get(clazz.getName()))) {
            Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(),
                    new Class<?>[] { clazz },
                    (serviceObj, serviceMethod, args) -> {
                    	String result = null;
                    	Cache cache = serviceMethod.getAnnotation(Cache.class);
                    	String key = null;
                    	boolean isPushCache = false;
                    	//判断是否缓存
                    	if(RedisUtil.isRedisOk && Objects.nonNull(cache)){
                    		key = clazz.getName() + serviceMethod.getName() + JacksonHelper.toJSON(args);
                    		try{
                    			result = RedisUtil.redisQueryObject(key);
                    			if(Objects.isNull(result)){
                        			isPushCache = true;
                        		}else{
                        			Log4jHelper.getLogger().info("=================>>result fetch from cache<<===================");
                        		}
                    		}catch(Exception e){
                    			e.printStackTrace();
                    			Log4jHelper.getLogger().info("=================>>result fetch from cache err<<===================");
                    			result = null;
                    			isPushCache = false;
                    		}
                    		
                    	}
                    	if(Objects.isNull(result)){
                            //构造服务参数
                            RpcRequest rpcRequest = new RpcRequest();
                            rpcRequest.setServiceClazz(clazz.getName());
                            //                        rpcRequest.setServiceMethod(serviceMethod);
                            rpcRequest.setMethedName(serviceMethod.getName());

                            rpcRequest.setMethedParameterTypes(
                                    Arrays.stream(serviceMethod.getParameterTypes())
                                            .map(Class::getName)
                                            .collect(Collectors.toList())
                                            .toArray(new String[] {}));
                            rpcRequest.setArgs(new ArrayList<>(
                                    Arrays.asList(Objects.isNull(args)
                                            ? new Object[] {} : args)));
                            //参数序列化和加密
                            String remoteRequestParams = URLEncoder.encode(RpcDtoHelper
                                    .object2EncryptAES(rpcRequest),"utf-8");
                            long startTime = System.currentTimeMillis();
                            //远程http服务请求
                            result = HttpClientHelper.httpPost(
                                    environment.getRequiredProperty(
                                            "remote.service.url"),
                                    HttpClientHelper.buildMap(
                                            new String[] {
                                                    RpcDtoHelper.PARAMETER_KEY },
                                            new String[] { remoteRequestParams }));
                            Log4jHelper.getLogger().info("invoke "+environment.getProperty(
                                            "remote.service.url") + ":" 
                            + rpcRequest.getServiceClazz()+"."+rpcRequest.getMethedName()+" cost time : [" + (System.currentTimeMillis() - startTime)+"] millions.");
                    	}
                    	if(isPushCache){
                    		 RedisUtil.redisSaveObject(key, result, cache.expired());
                    	}
                        try {
                            //结果解密并反序列化
                            Object object = RpcDtoHelper
                                    .encryptAES2Object(result);
                            if (Objects.isNull(object)) {
                                return object;
                            }
                            //结果类型不正确
                            if (!Dataset.class
                                    .isAssignableFrom(object.getClass())) {
                                throw new BusinessException("remote service return wrong type.");
                            }
                            Dataset<?, ?> dataset = (Dataset<?, ?>) object;
                            if (dataset.isSuccess()) {
                                return dataset;
                            } else {
                            	Log4jHelper.getLogger().error("业务异常:"+ dataset.getMessage());
                                return DatasetBuilder.fromMessage(
                                        dataset.getMessage(),
                                        serviceMethod.getReturnType());
                            }
                        } catch (Exception e) {
                            //如果失败打印异常信息
                            e.printStackTrace();
                            if (Objects.nonNull(result) && !result
                                    .matches("^[A-Z0-9a-z\\+\\=\\/]+$")) {
                                return DatasetBuilder.fromMessage(result,
                                        serviceMethod.getReturnType());
                            }
                            if(e instanceof BusinessException){
                            	 //统一返回消息
                                throw new BusinessException(((BusinessException)e).getMessage());
                            }
                            //统一返回消息
                            throw new BusinessException("decode remote result error.", e);
                        }
                    });
            //生成的代理放入缓存
            PROXYS.put(clazz.getName(), proxy);
        }
        //从缓存取得代理对象
        return PROXYS.get(clazz.getName());
    }
}
