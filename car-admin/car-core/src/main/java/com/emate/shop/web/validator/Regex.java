package com.emate.shop.web.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;

import com.emate.shop.common.Log4jHelper;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@ValidatorAnnotation //校验注解需要加此标记注解
public @interface Regex {

    public String value();

    public static class Impl implements ValidateExecutor {

        /**
        * 校验是否匹配正则表达式
        * @param argName 参数名
        * @param paramName 参数中文名，需要代码用@ValidatorParameterName设置
        * @param argValue 参数值
        * @param max 校验设置注解
        * @return 校验消息，null代表校验通过
        */
        @Override
        public String validate(String argName, String paramName,
                Object argValue, Annotation annotation) {
            if (Objects.isNull(argValue)) {
                return null;
            }
            if (!(argValue instanceof CharSequence)) {
                return null;
            }
            Regex regex = (Regex) annotation;
            if (!argValue.toString().matches(regex.value())) {
                String message = paramName + "prarmter not match regex.";
                Log4jHelper.getLogger().info("Validate message:[" + argName
                        + "]" + message + regex.value());
                return message;
            }
            return null;
        }

    }
}
