package com.binghetao.exception;

import com.binghetao.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandle(Exception e) {
        return Result.error(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
