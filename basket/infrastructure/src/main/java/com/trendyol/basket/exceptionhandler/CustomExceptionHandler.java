package com.trendyol.basket.exceptionhandler;

import com.trendyol.basket.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exception(RuntimeException exception){
        var apiResponse =
                ApiResponse.ApiResponseBuilder.builder()
                        .setMessage(exception.getMessage())
                        .setStatusCode(400)
                        .setSuccess(false)
                        .build();
        return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception exception){
        var apiResponse =
                ApiResponse.ApiResponseBuilder.builder()
                        .setMessage(exception.getMessage())
                        .setStatusCode(500)
                        .setSuccess(false)
                        .build();
        return new ResponseEntity(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
