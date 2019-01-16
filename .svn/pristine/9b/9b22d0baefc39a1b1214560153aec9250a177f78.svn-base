package com.emate.shop.business.remote;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.rpc.dto.Dataset;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.RpcDtoHelper;
import com.emate.shop.rpc.dto.RpcRequest;
import com.emate.shop.web.aop.SpringAopUtil;

@Controller
@RequestMapping("remote")
public class ServiceController extends SpringAopUtil implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@RequestMapping(produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String service(HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		try {
			String encryptAES = request.getParameter(RpcDtoHelper.PARAMETER_KEY);
			if (Objects.isNull(encryptAES)) {
				return errorMsg("Request param is null.");
			}
			RpcRequest rpcRequest = (RpcRequest) RpcDtoHelper.encryptAES2Object(URLDecoder.decode(encryptAES, "utf-8"));
			Log4jHelper.getLogger().info("RPC Request -->>>>>"+rpcRequest.getServiceClazz()+"."+rpcRequest.getMethedName());
			if (Objects.isNull(rpcRequest)) {
				return errorMsg("Request object is null.");
			}
			if (Objects.isNull(rpcRequest.getServiceClazz()) || Objects.isNull(rpcRequest.getMethedName())) {
				return errorMsg("Request class or method is null.");
			}
			Class<?> clazz = Class.forName(rpcRequest.getServiceClazz());

			List<Class<?>> serviceMethodParameterTypes = Arrays.stream(rpcRequest.getMethedParameterTypes())
					.map(clazzString -> {
						try {
							return Class.forName(clazzString);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}).collect(Collectors.toList());

			Method method = clazz.getMethod(rpcRequest.getMethedName(),
					serviceMethodParameterTypes.toArray(new Class<?>[] {}));
			Object serviceBean = this.applicationContext.getBean(clazz);
			Class<?> returnClazz = method.getReturnType();

			List<Object> args = rpcRequest.getArgs();
			for (int i = 0; i < args.size(); i++) {
				Object arg = args.get(i);
				if (Objects.isNull(arg)) {
					continue;
				}
				if (serviceMethodParameterTypes.get(i).isAssignableFrom(arg.getClass())) {
					continue;
				}
				args.remove(i);
				args.add(i, ConvertUtils.convert(arg, serviceMethodParameterTypes.get(i)));
			}

			// Log4jHelper.getLogger().info("begin invoke
			// >>>>>"+clazz.getCanonicalName()+"."+method.getName());
			Object result = method.invoke(serviceBean, args.toArray());
			Log4jHelper.getLogger().info("Invoke >>>> " + clazz.getName() + "." + method.getName() + " cost "
					+ (System.currentTimeMillis() - startTime) + "ms");

			if (Objects.isNull(result)) {
				return RpcDtoHelper.object2EncryptAES(result);
			}
			if (!Dataset.class.isAssignableFrom(returnClazz)) {
				return errorMsg("the return type is wrong.");
			}

			if (!(result instanceof Dataset)) {
				return errorMsg("result type error.");
			}
			return RpcDtoHelper.object2EncryptAES(result);
		} catch (Throwable e) {
			e.printStackTrace();
			Log4jHelper.getLogger().error(e);
			BusinessException be = this.getBusinessException(e);
			if (Objects.nonNull(be)) {
				return errorMsg(be.getMessage());
			}
			return errorMsg("remote service exception.");
		}
	}

	private String errorMsg(String msg) {
		Log4jHelper.getLogger().error("service执行业务异常： "+msg);
		return RpcDtoHelper.object2EncryptAES(DatasetBuilder.fromMessage(msg));
	}

}
