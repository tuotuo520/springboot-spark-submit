package com.taihe.springbootsparksubmit.result;/*
 * Copyright 2016 juor & Co., Ltd.
 */


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.taihe.springbootsparksubmit.constant.ErrorCodes;
import com.taihe.springbootsparksubmit.exception.ApplicationException;
import com.taihe.springbootsparksubmit.result.ErrorMessageSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.concurrent.Callable;


/**
 * 执行结果
 *
 * @author Lyn
 */
@ApiModel("结果")
@NoArgsConstructor
public class Result<T> {


    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("错误代码")
    private Integer code;

    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("错误消息")
    private String message;

    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("结果数据")
    private T data;

    private static ErrorMessageSource messageSource = new ErrorMessageSource();

    @JsonIgnore
    public boolean isOk() {
        return code == null || code == 0;
    }

    public static <T> Result<T> ok() {
        Result<T> ret = new Result<T>();
        ret.code = 200;
        ret.message = "ok";
        return ret;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> ret = ok();
        ret.data = data;
        return ret;
    }

    public static <T> Result<T> ok(T data, String message) {
        Result<T> ret = ok();
        ret.data = data;
        ret.message = message;
        return ret;
    }

    public static <T> Result<T> notFound() {
        return error(ErrorCodes.NOT_FOUND);
    }

    public static <T> Result<T> notFound(String message) {
        return error(ErrorCodes.NOT_FOUND, message);
    }

    public static <T> Result<T> illegal() {
        return Result.error(ErrorCodes.ILLEGAL_OPERATE);
    }

    public static <T> Result<T> illegal(String message) {
        return Result.error(ErrorCodes.ILLEGAL_OPERATE, message);
    }

    public static <T> Result<T> error(int code, String message) {
        Result<T> ret = new Result<T>();
        ret.code = code;
        ret.message = message;
        return ret;
    }

    public static <T> Result<T> error(int code, String message, T data) {
        Result<T> ret = new Result<T>();
        ret.code = code;
        ret.message = message;
        ret.data = data;
        return ret;
    }

    public static <T> Result<T> error(int code, String message, Callable<T> callable) {
        Result<T> ret = new Result<T>();
        ret.code = code;
        ret.message = message;
        try {
            ret.data = callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public static <T> Result<T> error(int code) {
        Result<T> ret = new Result<T>();
        ret.code = code;
        ret.message = messageSource.getMessage(code);
        return ret;
    }

    public static <T> Result<T> error(String message) {
        return error(ErrorCodes.BUSINESS, message);
    }

    public static <T> Result<T> error() {
        return error(ErrorCodes.BUSINESS);
    }

    public static <T> Result<T> error(Result<?> result) {
        return error(result.code, result.message);
    }

    public static <T> Result<T> error(Errors errors) {
        ObjectError error = errors.getAllErrors().get(0);
        String title = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
        String message = title + "," + error.getDefaultMessage();
        return error(ErrorCodes.INVALID_ARGUMENTS, message);
    }


    @ApiModelProperty("服务器时间戳")
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "[code=" + code + ", message=" + message + "]";
    }

    public T throwExIfError() throws ApplicationException {
        if (isOk()) {
            return data;
        }
        throw new ApplicationException(this);
    }

    public <NT> Result<NT> newObject() {
        return Result.error(this);
    }

}
