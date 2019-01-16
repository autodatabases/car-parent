package com.emate.shop.exception;

import java.util.Objects;

import com.emate.shop.common.Log4jHelper;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -3489073206191573929L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
    }

    public BusinessException(String message, Object... args) {
        super(prepareMessage(message, args));
    }

    @Override
    public void printStackTrace() {
        System.err.println("BusinessException:" + this.getMessage());
        Log4jHelper.getLogger().error("BusinessException:" + this.getMessage());
    }

    private static String prepareMessage(String message, Object... args) {
        if (Objects.isNull(args) || args.length == 0) {
            return message;
        }
        int i = 0;
        while (message.contains("{}") && i < args.length) {
            message = message.replaceFirst("\\{\\}", String.valueOf(args[i]));
            i++;
        }
        return message;
    }

}
