/*
 * Copyright 2016 juor & Co., Ltd.
 */
package com.taihe.springbootsparksubmit.exception;


import com.taihe.springbootsparksubmit.constant.ErrorCodes;
import com.taihe.springbootsparksubmit.result.Result;

/**
 * 应用程序错误
 *
 * @author Lyn
 */
public class ApplicationException extends Exception {

    private static final long serialVersionUID = 2156274765383407719L;

    private int code;

    private String mesasge;

    public ApplicationException(int code, String message) {
        this.code = code;
        this.mesasge = message;
    }

    public ApplicationException(String message) {
        this.code = ErrorCodes.BUSINESS;
        this.mesasge = message;
    }

    public ApplicationException(Result<?> result) {
        this.code = result.getCode();
        this.mesasge = result.getMessage();
    }

    @Override
    public String getMessage() {
        return mesasge;
    }

    public <T> Result<T> getResult() {
        return Result.error(code, mesasge);
    }
}
