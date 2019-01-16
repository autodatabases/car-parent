/**
 * 
 */
package com.emate.shop.common.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import com.emate.shop.common.NumberHelper;
import com.emate.shop.common.StringHelper;

/**
 * @file ModelHelper.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年8月10日
 */
public class ModelHelper {

    private static <THIS, THAT> Object prepareThatExample4One2Many(
            Long thisKeyValue, String fkName, Object thatExample,
            String thatOrder) {
        condition(thatExample,
                "and" + StringHelper.upperCap(fkName) + "EqualTo", thisKeyValue,
                Long.class);
        addOrder(thatExample, thatOrder);
        return thatExample;
    }

    private static <THIS, RELA, THAT> Object prepareThatExample4Many2Many(
            Long thisKeyValue, String thisKey, String thatKey,
            Object relaMapper, Object relaExample, Object thatExample,
            String thatOrder) {
        condition(relaExample,
                "and" + StringHelper.upperCap(thisKey) + "EqualTo",
                thisKeyValue, Long.class);
        List<RELA> relas = selectByExample(relaMapper, relaExample);
        List<Long> thatIds = relas.stream().map(rela -> {
            return findKeyValue(rela, thatKey);
        }).distinct().collect(Collectors.toList());
        thatIds.add(0L);
        condition(thatExample, "andIdIn", thatIds, List.class);
        addOrder(thatExample, thatOrder);
        return thatExample;
    }

    private static Long findKeyValue(Object object, String keyName) {
        String idString;
        try {
            idString = BeanUtils.getProperty(object, keyName);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (Objects.isNull(idString) || !StringHelper.isInteger(idString)) {
            throw new RuntimeException("key格式错误或者为空");
        }
        Long keyValue = Long.parseLong(idString);
        return keyValue;
    }

    private static void condition(Object example, String conditionName,
            Object parameter, Class<?> parameterType) {
        Object condition;
        try {
            condition = example.getClass().getMethod("or").invoke(example);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
        try {
            condition.getClass().getMethod(conditionName, parameterType)
                    .invoke(condition, parameter);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> selectByExample(Object mapper, Object example) {
        try {
            return (List<T>) mapper.getClass()
                    .getMethod("selectByExample", example.getClass())
                    .invoke(mapper, example);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> int countByExample(Object mapper, Object example) {
        try {
            return (Integer) mapper.getClass()
                    .getMethod("countByExample", example.getClass())
                    .invoke(mapper, example);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static int deleteByExample(Object mapper, Object example) {
        try {
            return (Integer) mapper.getClass()
                    .getMethod("deleteByExample", example.getClass())
                    .invoke(mapper, example);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addOrder(Object example, String order) {
        if (Objects.nonNull(order)) {
            try {
                example.getClass()
                        .getMethod("setOrderByClause", order.getClass())
                        .invoke(example, order);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException
                    | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static abstract class ModelFKConfig<S, T> {

        private Class<T> targetType;

        @SuppressWarnings("unchecked")
        public ModelFKConfig() {
            Type[] types = ((ParameterizedType) this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments();
            this.targetType = (Class<T>) types[1];
        }

        public List<T> query(S srcObj) {
            return this.query(ModelHelper.findKeyValue(srcObj, "id"));
        }

        public long count(S srcObj) {
            return this.count(ModelHelper.findKeyValue(srcObj, "id"));
        }

        public boolean has(S srcObj) {
            return NumberHelper.isNotZero(this.count(srcObj));
        }

        private Object prepareExample(Long id) {
            return ModelHelper.prepareThatExample4One2Many(id, this.getFkName(),
                    this.getTargetExample(), this.getTargetOrder());
        }

        public List<T> query(Long id) {
            return ModelHelper.selectByExample(this.getTargetMapper(),
                    this.prepareExample(id));
        }

        public long count(Long id) {
            return ModelHelper.countByExample(this.getTargetMapper(),
                    this.prepareExample(id));
        }

        public boolean has(Long id) {
            return NumberHelper.isNotZero(this.count(id));
        }

        public int delete(Long id) {
            return ModelHelper.deleteByExample(this.getTargetMapper(),
                    this.prepareExample(id));
        }

        public abstract String getFkName();

        public abstract Object getTargetMapper();

        public abstract String getTargetOrder();

        public Class<T> getTargetType() {
            return targetType;
        }

        public void setTargetType(Class<T> targetType) {
            this.targetType = targetType;
        }

        public Object getTargetExample() {
            try {
                return Class.forName(this.targetType.getName() + "Example")
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException
                    | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static abstract class ModelRelaConfig<S, R, T> {

        private Class<R> relaType;

        private Class<T> targetType;

        public List<T> query(S srcObj) {
            return this.query(ModelHelper.findKeyValue(srcObj, "id"));
        }

        public int count(S srcObj) {
            return this.count(ModelHelper.findKeyValue(srcObj, "id"));
        }

        public boolean has(S srcObj) {
            return NumberHelper.isNotZero(this.count(srcObj));
        }

        private Object prepareExample(Long id) {
            return ModelHelper.prepareThatExample4Many2Many(id,
                    this.getSrcFkName(), this.getTargetFkName(),
                    this.getRelaMapper(), this.getRelaExample(),
                    this.getTargetExample(), this.getTargetOrder());
        }

        public List<T> query(Long id) {
            return ModelHelper.selectByExample(this.getTargetMapper(),
                    this.prepareExample(id));
        }

        public int count(Long id) {
            return ModelHelper.countByExample(this.getTargetMapper(),
                    this.prepareExample(id));
        }

        public boolean has(Long id) {
            return NumberHelper.isNotZero(this.count(id));
        }

        public int delete(Long id) {
            return ModelHelper.deleteByExample(this.getTargetMapper(),
                    this.prepareExample(id));
        }

        public abstract String getSrcFkName();

        public abstract String getTargetFkName();

        public abstract Object getRelaMapper();

        public abstract Object getTargetMapper();

        public abstract String getTargetOrder();

        @SuppressWarnings("unchecked")
        public ModelRelaConfig() {
            Type[] types = ((ParameterizedType) this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments();
            this.relaType = (Class<R>) types[1];
            this.targetType = (Class<T>) types[2];
        }

        public Object getRelaExample() {
            try {
                return Class.forName(this.relaType.getName() + "Example")
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException
                    | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public Object getTargetExample() {
            try {
                return Class.forName(this.targetType.getName() + "Example")
                        .newInstance();
            } catch (InstantiationException | IllegalAccessException
                    | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public Class<R> getRelaType() {
            return relaType;
        }

        public void setRelaType(Class<R> relaType) {
            this.relaType = relaType;
        }

        public Class<T> getTargetType() {
            return targetType;
        }

        public void setTargetType(Class<T> targetType) {
            this.targetType = targetType;
        }
    }
}
