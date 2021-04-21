package com.homebudget.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotAcceptableHandler {

    @ExceptionHandler(value = {ArithmeticException.class, UnsupportedOperationException.class})
    protected ResponseEntity<Object> handleNotAcceptable(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }
}
