package com.emate.shop.rpc.dto;

import java.util.List;
import java.util.Objects;

public class DatasetBuilder {

    public static <D, L> Dataset<D, L> fromMessage(String message) {
        return new Dataset<>(message);
    }

    public static <D, L> Dataset<D, L> fromData(D data) {
        return new Dataset<>(data, null, null);
    }

    public static <D, L> Dataset<D, L> fromList(List<L> list) {
        return new Dataset<>(null, list, null);
    }

    public static <D, L> Dataset<D, L> fromData(D data, PageInfo pageInfo) {
        return new Dataset<>(data, null, pageInfo);
    }

    public static <D, L> Dataset<D, L> fromList(List<L> list,
            PageInfo pageInfo) {
        return new Dataset<>(null, list, pageInfo);
    }

    public static <D, L> Dataset<D, L> fromAll(D data, List<L> list,
            PageInfo pageInfo) {
        return new Dataset<>(data, list, pageInfo);
    }

    public static <D> DatasetSimple<D> fromMessageSimple(String message) {
        return new DatasetSimple<>(message);
    }

    public static <D> DatasetSimple<D> fromDataSimple(D data) {
        return new DatasetSimple<>(data);
    }

    public static <L> DatasetList<L> fromMessageList(String message) {
        return new DatasetList<>(message);
    }

    public static <L> DatasetList<L> fromDataList(List<L> list) {
        return new DatasetList<>(list);
    }

    public static <L> DatasetList<L> fromDataList(List<L> list,
            PageInfo pageInfo) {
        return new DatasetList<>(list, pageInfo);
    }

    public static Object fromMessage(String message, Class<?> type) {
        return transformMessage(fromMessage(message), type);
    }

    public static Object transformMessage(Object dataset, Class<?> type) {
        if (Objects.isNull(dataset) || Objects.isNull(type)
                || !Dataset.class.isAssignableFrom(dataset.getClass())
                || !Dataset.class.isAssignableFrom(type)) {
            return null;
        }
        if (type.isAssignableFrom(dataset.getClass())) {
            return dataset;
        }
        String message = ((Dataset<?, ?>) dataset).getMessage();
        if (DatasetSimple.class.isAssignableFrom(type)) {
            return fromMessageSimple(message);
        } else if (DatasetList.class.isAssignableFrom(type)) {
            return fromMessageList(message);
        }
        return dataset;
    }
}
