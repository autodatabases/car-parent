package com.emate.shop.web.validator;

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
@Target({ PARAMETER })
@ValidatorAnnotation //校验注解需要加此标记注解
public @interface Max {

    public double value();

    public boolean include() default true;

    public static class Impl implements ValidateExecutor {

        /**
         * 校验最大值限制
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
            if (!(argValue instanceof Number)) {
                return null;
            }
            Max max = (Max) annotation;
            Number value = (Number) argValue;
            if (value.doubleValue() > max.value()) {
                String message = paramName + "不能大于" + max.value();
                Log4jHelper.getLogger()
                        .info("Validate message:[" + argName + "]" + message);
                return message;
            }
            if (value.doubleValue() == max.value() && !max.include()) {
                String message = paramName + "不能等于" + max.value();
                Log4jHelper.getLogger()
                        .info("Validate message:[" + argName + "]" + message);
            }
            return null;
        }
    }
}
