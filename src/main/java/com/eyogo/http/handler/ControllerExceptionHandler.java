package com.eyogo.http.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*There is no need to create own, because we already have predefined abstract handler - ResponseEntityExceptionHandler,
 so just inherit from it.*/
@ControllerAdvice(basePackages = "com.eyogo.http.controller") // Specify only server-rendered controllers,
// all REST controllers will return 404, rather than error page
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception ex, HttpServletRequest request) {
//        log.error("Failed to return response to {} request:", request.getRequestURI(), ex);
//        return "error/error500";
//    }
}
