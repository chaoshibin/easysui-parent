package com.easysui.core.exception;

/**
 * <p>
 *
 * </p>
 *
 * @author chao
 * @since 2019/8/28
 **/
public class NecessaryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NecessaryException(String message) {
        super(message);
    }

    public NecessaryException(Throwable cause) {
        super(cause);
    }
}