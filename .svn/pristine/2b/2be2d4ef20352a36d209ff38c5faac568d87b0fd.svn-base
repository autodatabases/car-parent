package com.emate.shop.common;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jHelper {

    public static Logger getLogger() {
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();
        if (Objects.nonNull(stackTraceElements)
                && stackTraceElements.length >= 3) {
            String className = Thread.currentThread().getStackTrace()[2]
                    .getClassName();
            return LogManager.getLogger(className);
        }
        return LogManager.getLogger(Log4jHelper.class);
    }
}
