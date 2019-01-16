package com.emate.shop.datasource;

import java.util.Objects;

import com.emate.shop.common.Log4jHelper;

public class DataSourceContext {

    private static final ThreadLocal<String> DS_CTX = new ThreadLocal<>();

    public static void setWrite() {
        Log4jHelper.getLogger().debug("设置数据源:write");
        DS_CTX.set("write");
    }

    public static void setRead() {
        Log4jHelper.getLogger().debug("设置数据源:read");
        DS_CTX.set("read");
    }

    public static String getContext() {
        return DS_CTX.get();
    }

    public static void reset() {
        Log4jHelper.getLogger().debug("删除数据源设置");
        DS_CTX.remove();
    }

    public static boolean isWrite() {
        return Objects.equals("write", DS_CTX.get());
    }
}
