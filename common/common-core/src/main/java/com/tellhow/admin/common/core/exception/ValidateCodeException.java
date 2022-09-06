package com.tellhow.admin.common.core.exception;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/6 15:16
 * @descripe 验证码异常处理
 */
public class ValidateCodeException extends RuntimeException{

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException() {
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
