package com.emate.shop.exception;

import com.emate.shop.common.Log4jHelper;

public class ParameterException extends BusinessException {

    private static final long serialVersionUID = -1440698123917657650L;

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Throwable e) {
        super(e);
    }

    public ParameterException(String message, Throwable e) {
        super(message, e);
    }

    @Override
    public void printStackTrace() {
        System.err.println("ParameterException:" + this.getMessage());
        Log4jHelper.getLogger()
                .error("ParameterException:" + this.getMessage());
    }
}
