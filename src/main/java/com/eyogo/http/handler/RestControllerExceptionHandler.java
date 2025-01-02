package com.eyogo.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.eyogo.http.rest")
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
