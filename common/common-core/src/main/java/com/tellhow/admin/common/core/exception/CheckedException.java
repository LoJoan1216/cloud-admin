package com.tellhow.admin.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/6 14:59
 * @descripe
 */
@NoArgsConstructor
public class CheckedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CheckedException(String message) {
        super(message);
    }

    public CheckedException(Throwable cause) {
        super(cause);
    }

    public CheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
