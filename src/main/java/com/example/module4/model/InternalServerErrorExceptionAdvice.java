package com.example.module4.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InternalServerErrorExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ResponseMessageFile> handleInternalServerError (HttpServerErrorException.InternalServerError error){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageFile("Internal Server Error!"));
    }
}
