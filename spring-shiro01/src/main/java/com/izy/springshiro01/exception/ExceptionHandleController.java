package com.izy.springshiro01.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zouyu
 * @description
 * @date 2020/5/29
 */
@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
      return "无权限访问";
    }
}
