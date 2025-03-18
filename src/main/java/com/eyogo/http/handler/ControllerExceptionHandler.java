package com.eyogo.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.eyogo.http.controller")
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
