package com.homebudget.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotAcceptableHandler {

    Logger logger = LoggerFactory.getLogger(NotAcceptableHandler.class);

    @ExceptionHandler(value = {ArithmeticException.class, UnsupportedOperationException.class})
    protected ResponseEntity<Object> handleNotAcceptable(Exception ex){
        logger.warn(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }
}
