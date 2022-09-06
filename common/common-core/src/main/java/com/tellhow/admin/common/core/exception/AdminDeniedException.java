package com.tellhow.admin.common.core.exception;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/6 15:15
 * @descripe 拒绝授权
 */
public class AdminDeniedException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public AdminDeniedException(String message) {
        super(message);
    }

    public AdminDeniedException(Throwable cause) {
        super(cause);
    }

    public AdminDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
