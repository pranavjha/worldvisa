package com.worldvisa.business.infra.base;

public class BaseException
extends RuntimeException {
    private static final long serialVersionUID = -3681321888066110599L;

    public BaseException() {
        super();
    }

    public BaseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BaseException(final Throwable cause) {
        super(cause);
    }

    public String getType() {
        return this.getClass().getName();
    }
}
