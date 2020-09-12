package com.apiharrypotter.config;

import com.apiharrypotter.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> handleBusiness(RuntimeException ex, WebRequest request) {
        final String bodyOfResponse = ex.getLocalizedMessage();
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.NOT_ACCEPTABLE, request);
    }

}